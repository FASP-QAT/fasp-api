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
        LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        WHERE 
            st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
            AND (
                length(@fundingSourceProcurementAgentIds)=0 
                OR (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) 
                OR (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))
                OR (@reportView=3 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(pa.PROCUREMENT_AGENT_TYPE_ID, @fundingSourceProcurementAgentIds)))
                OR (@reportView=4 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(fs.FUNDING_SOURCE_TYPE_ID, @fundingSourceProcurementAgentIds)))
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
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curHealthAreaId,-1),",p.HEALTH_AREA_ID))");
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
        SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.FUNDING_SOURCE_ID=",fspaId,", st.SHIPMENT_QTY, 0)) `FSPA_",fspaCode,"` ");
    END LOOP getFSPA;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "       rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "       ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT pv.PROGRAM_ID, pv.VERSION_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) `MAX_VERSION_ID` ");
    SET @sqlString = CONCAT(@sqlString, "   FROM ");
    SET @sqlString = CONCAT(@sqlString, "           ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID         ");
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
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "               st.PLANNING_UNIT_ID = @planningUnitId ");
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "           st.ACTIVE AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "           AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "           AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
    IF @reportView = 1 THEN 
        SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSEIF @reportView = 2 THEN 
        SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)) ");
    ELSEIF @reportView = 3 THEN 
        SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(pa.PROCUREMENT_AGENT_TYPE_ID, @fundingSourceProcurementAgentIds)) ");
    ELSEIF @reportView = 4 THEN 
        SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(fs.FUNDING_SOURCE_TYPE_ID, @fundingSourceProcurementAgentIds)) ");    
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY rc.REALM_COUNTRY_ID ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END