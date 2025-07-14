USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_CountryShipmentSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_CountryShipmentSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 4
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    SET @realmCountryIds =  VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;    

    SET @sqlString = "";
    
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM(IF(st.SHIPMENT_STATUS_ID IN (1,2,3,9), ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `PLANNED_SHIPMENT_AMT`, SUM(IF(st.SHIPMENT_STATUS_ID IN (4,5,6,7), ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `ORDERED_SHIPMENT_AMT` ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, " GROUP BY rc.REALM_COUNTRY_ID ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_FundingSourceCountrySplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_FundingSourceCountrySplit`(VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10),  
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 3 for FundingSource
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR 
        SELECT fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE
        FROM 
            (
            SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` 
            FROM 
                ( 
                SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID 
                FROM rm_program p 
                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	
                LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId
                LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
                LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID 
                WHERE 
                    TRUE AND rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
                    AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))
                    AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2))
                GROUP BY p.PROGRAM_ID 
            ) pv 
            LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID 
            LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID 
            WHERE 
                st.PLANNING_UNIT_ID = @planningUnitId 
            GROUP BY s.SHIPMENT_ID 
        ) AS s1 
        LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID 
        LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        WHERE 
            st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            AND (
                length(@fundingSourceProcurementAgentIds)=0 
                OR (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) 
                OR (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))
            )
            AND (@includePlannedShipments = 1 OR (@includePlannedShipments = 0 AND st.SHIPMENT_STATUS_ID != 1))
        GROUP BY st.FUNDING_SOURCE_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    

    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
        FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
            LEAVE getFSPA;
	END IF;
	SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.FUNDING_SOURCE_ID=",fspaId,", ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `FSPA_",fspaCode,"` ");
    END LOOP getFSPA;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "   FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY rc.REALM_COUNTRY_ID ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_FundingSourceDateSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_FundingSourceDateSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE,  
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 2 for FundingSource
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR 
        SELECT fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE
        FROM 
            (
            SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` 
            FROM 
                ( 
                SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID 
                FROM rm_program p 
                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	
                LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId
                LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
                LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID 
                WHERE 
                    TRUE AND rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
                    AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))
                    AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2))
                GROUP BY p.PROGRAM_ID 
            ) pv 
            LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID 
            LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID 
            WHERE 
                st.PLANNING_UNIT_ID = @planningUnitId 
            GROUP BY s.SHIPMENT_ID 
        ) AS s1 
        LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID 
        LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        WHERE 
            st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            AND (
                length(@fundingSourceProcurementAgentIds)=0 
                OR (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) 
                OR (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))
            )
            AND (@includePlannedShipments = 1 OR (@includePlannedShipments = 0 AND st.SHIPMENT_STATUS_ID != 1))
        GROUP BY st.FUNDING_SOURCE_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
	
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
	FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
            LEAVE getFSPA;
        END IF;
	SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.FUNDING_SOURCE_ID=",fspaId,", ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `FSPA_",fspaCode,"` ");
    END LOOP getFSPA;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	CONCAT(LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7),'-01') `TRANS_DATE` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "   FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
	SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;

    SET @sqlString = CONCAT(@sqlString, "GROUP BY LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7)");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ProcurementAgentCountrySplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ProcurementAgentCountrySplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10),  
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Report no 21 Part 3 for ProcurementAgent
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR 
        SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE
        FROM 
            (
            SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` 
            FROM 
                ( 
                SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID 
                FROM rm_program p 
                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	
                LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId
                LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
                LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID 
                WHERE 
                    TRUE AND rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
                    AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))
                    AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2))
                GROUP BY p.PROGRAM_ID 
            ) pv 
            LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID 
            LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID 
            WHERE 
                st.PLANNING_UNIT_ID = @planningUnitId 
            GROUP BY s.SHIPMENT_ID 
        ) AS s1 
        LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID 
        LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        WHERE 
            st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            AND (
                length(@fundingSourceProcurementAgentIds)=0 
                OR (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) 
                OR (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))
            )
            AND (@includePlannedShipments = 1 OR (@includePlannedShipments = 0 AND st.SHIPMENT_STATUS_ID != 1))
        GROUP BY st.PROCUREMENT_AGENT_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
	
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
	FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
            LEAVE getFSPA;
	END IF;
	SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.PROCUREMENT_AGENT_ID=",fspaId,", ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `FSPA_",fspaCode,"` ");
    END LOOP getFSPA;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "   FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY rc.REALM_COUNTRY_ID ");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ProcurementAgentDateSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ProcurementAgentDateSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 2 for ProcurementAgent
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE finished INTEGER DEFAULT 0; 
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR 
        SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE
        FROM 
            (
            SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` 
            FROM 
                ( 
                SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID 
                FROM rm_program p 
                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	
                LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId
                LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
                LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID 
                WHERE 
                    TRUE AND rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
                    AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))
                    AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2))
                GROUP BY p.PROGRAM_ID 
            ) pv 
            LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID 
            LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID 
            WHERE 
                st.PLANNING_UNIT_ID = @planningUnitId 
            GROUP BY s.SHIPMENT_ID 
        ) AS s1 
        LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID 
        LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        WHERE 
            st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            AND (
                length(@fundingSourceProcurementAgentIds)=0 
                OR (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) 
                OR (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))
            )
            AND (@includePlannedShipments = 1 OR (@includePlannedShipments = 0 AND st.SHIPMENT_STATUS_ID != 1))
        GROUP BY st.PROCUREMENT_AGENT_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
	
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
        FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
            LEAVE getFSPA;
	END IF;
	SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.PROCUREMENT_AGENT_ID=",fspaId,", ((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD), 0)) `FSPA_",fspaCode,"` ");
    END LOOP getFSPA;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	CONCAT(LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7),'-01') `TRANS_DATE` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "   FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7)");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ShipmentList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ShipmentList`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_REPORT_VIEW INT(10), 
    VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), 
    VAR_PLANNING_UNIT_ID INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), 
    VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 1
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT; 
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    SET @realmCountryIds =  VAR_REALM_COUNTRY_IDS;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;

    SET @sqlString = "";
    
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `TRANS_DATE`, rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD) `AMOUNT`, ");
    SET @sqlString = CONCAT(@sqlString, "	IF(@reportView=1, fs.FUNDING_SOURCE_ID, pa.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, IF(@reportView=1, fs.FUNDING_SOURCE_CODE, pa.PROCUREMENT_AGENT_CODE) `FUNDING_SOURCE_PROCUREMENT_AGENT_CODE`, IF(@reportView=1, fs.LABEL_ID, pa.LABEL_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_ID`, IF(@reportView=1, fs.LABEL_EN, pa.LABEL_EN) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_EN`, IF(@reportView=1, fs.LABEL_FR, pa.LABEL_FR) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_FR`, IF(@reportView=1, fs.LABEL_SP, pa.LABEL_SP) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_SP`, IF(@reportView=1, fs.LABEL_PR, pa.LABEL_PR) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR` ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=@planningUnitId");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    IF @approvedSupplyPlanOnly THEN 
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSE
        SET @sqlString = CONCAT(@sqlString, "		AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    END IF;
	
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_fundingSourceSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_fundingSourceSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10),  
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_PROGRAM_IDS TEXT, 
    VAR_FUNDING_SOURCE_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_SHIPMENT_STATUS_IDS VARCHAR(255),  
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 1 Funding Source Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fundingSourceIds = VAR_FUNDING_SOURCE_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, SUM((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD) `TOTAL_COST`  ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID,@programIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fundingSourceIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY st.FUNDING_SOURCE_ID");
	-- SELECT @sqlString;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_planningUnitSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_planningUnitSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_PROGRAM_IDS TEXT,  
    VAR_FUNDING_SOURCE_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT,  
    VAR_SHIPMENT_STATUS_IDS VARCHAR(255), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 2 PlanningUnit Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fundingSourceIds = VAR_FUNDING_SOURCE_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.MULTIPLIER, SUM(IF(st.SHIPMENT_STATUS_ID IN (1,2,3,9),st.SHIPMENT_QTY,0)) `PLANNED_SHIPMENT_QTY`, SUM(IF(st.SHIPMENT_STATUS_ID IN (4,5,6,7),st.SHIPMENT_QTY,0)) `ORDERED_SHIPMENT_QTY` ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID,@programIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fundingSourceIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY st.PLANNING_UNIT_ID");
	-- SELECT @sqlString;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_procurementAgentSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_procurementAgentSplit`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_STOP_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_PROGRAM_IDS TEXT, 
    VAR_FUNDING_SOURCE_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_SHIPMENT_STATUS_IDS VARCHAR(255), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 3 ProcurementAgent Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
    DECLARE done INT DEFAULT 0;
    DECLARE procurementAgentId INT(10) DEFAULT 0;
    DECLARE procurementAgentCode VARCHAR(10) DEFAULT "";
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE procurement_cursor CURSOR FOR SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE
        FROM 
            (
            SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` 
            FROM 
                ( 
                SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID 
                FROM rm_program p 
                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	
                LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(ppu.PLANNING_UNIT_ID,@planningUnitIds))
                LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
                LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID 
                WHERE 
                    TRUE AND rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
                    AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))
                    AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2))
                    AND (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds))
                GROUP BY p.PROGRAM_ID 
            ) pv 
            LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID 
            LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID 
            WHERE 
                (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds))
                AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fundingSourceIds))
                AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds))
                AND st.ACTIVE
                AND st.SHIPMENT_STATUS_ID != 8 
                AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            GROUP BY s.SHIPMENT_ID 
        ) AS s1 
        LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID 
        LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        GROUP BY st.PROCUREMENT_AGENT_ID;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @sqlStringProcurementAgent = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fundingSourceIds = VAR_FUNDING_SOURCE_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;

    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET done = 0;
	OPEN procurement_cursor;
    getProcurementAgent: LOOP
	FETCH procurement_cursor into procurementAgentId, procurementAgentCode;
        IF done = 1 THEN 
            LEAVE getProcurementAgent;
	END IF;
	SET @sqlStringProcurementAgent = CONCAT(@sqlStringProcurementAgent, " ,SUM(IF(st.PROCUREMENT_AGENT_ID=",procurementAgentId,", st.SHIPMENT_QTY, 0)) `PA_",procurementAgentCode,"` ");
    END LOOP getProcurementAgent;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.MULTIPLIER, SUM(st.SHIPMENT_QTY) `SHIPMENT_QTY` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringProcurementAgent);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID 	");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID,@programIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (@approvedSupplyPlanOnly=0 OR (pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fundingSourceIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY st.PLANNING_UNIT_ID");
	-- SELECT @sqlString;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;

