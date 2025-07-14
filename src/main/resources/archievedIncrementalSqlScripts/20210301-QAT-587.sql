USE `fasp`;
DROP procedure IF EXISTS `procurementAgentShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `procurementAgentShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 13
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- procurementAgentIds is a list of the ProcurementAgents that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
    SET @sqlString = CONCAT(@sqlString,"	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
    SET @sqlString = CONCAT(@sqlString,"FROM ");
    SET @sqlString = CONCAT(@sqlString,"	(");
    SET @sqlString = CONCAT(@sqlString,"	SELECT ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"	WHERE ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString,"WHERE ");
    SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
    SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
        SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,")) ");
    END IF;
	
    SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PROCUREMENT_AGENT_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `fundingSourceShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `fundingSourceShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 15
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- fundingSourceIds is a list of the FundingSources that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
    SET @sqlString = CONCAT(@sqlString,"	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
    SET @sqlString = CONCAT(@sqlString,"FROM ");
    SET @sqlString = CONCAT(@sqlString,"	(");
    SET @sqlString = CONCAT(@sqlString,"	SELECT ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"	WHERE ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString,"WHERE ");
    SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
    SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")) ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
	SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
	
    SET @sqlString = CONCAT(@sqlString,"GROUP BY st.FUNDING_SOURCE_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `shipmentDetails`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate
    -- Only a single ProgramId can be selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program

    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	pu.MULTIPLIER, ");
    SET @sqlString = CONCAT(@sqlString, "	s.SHIPMENT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	st.SHIPMENT_QTY, st.ORDER_NO, st.LOCAL_PROCUREMENT, st.ERP_FLAG, st.EMERGENCY_ORDER, ");
    SET @sqlString = CONCAT(@sqlString, "	COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD + IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `TOTAL_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	st.NOTES ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT ");
    SET @sqlString = CONCAT(@sqlString, "		s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "	FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "		AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS s ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa on st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE AND st.SHIPMENT_STATUS_ID!=8 ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
	SET @sqlString = CONCAT(@sqlString, "	AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY s.SHIPMENT_ID");
    
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
                AND st.ACTIVE AND st.ACCOUNT_FLAG
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
            st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 
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
            st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 
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
            st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
            st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
    SET @sqlString = CONCAT(@sqlString, "	    st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
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
DROP procedure IF EXISTS `annualShipmentCost`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `annualShipmentCost`(VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PROCUREMENT_AGENT_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_FUNDING_SOURCE_IDS TEXT, VAR_SHIPMENT_STATUS_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REPORT_BASED_ON INT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 22 
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- reportBasedOn = 1 means the report will be run using startDate and stopDate based on Shipped Date
    -- reportBasedOn = 2 means the report will be run using startDate and stopDate based on Delivered date if available or Expected Delivery Date
    -- If ProcurementAgent has not been selected as yet in the Shipment, that Shipment will be excluded
    -- 
    -- 
    SET @programId = VAR_PROGRAM_ID;
    SET @startDate = CONCAT(VAR_START_DATE, ' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @reportBasedOn=VAR_REPORT_BASED_ON; -- 1 = Shipped and 2 = RECEIVED
    SET @versionId = VAR_VERSION_ID;
    DROP TABLE IF EXISTS `tmp_year`;
	CREATE TABLE `tmp_year` (`YR` INT(10) UNSIGNED NOT NULL,PRIMARY KEY (`YR`)) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @dtPtr = @startDate;
    SET @sql2 = "";
    SET @isFirst = TRUE;
    REPEAT
	INSERT INTO tmp_year VALUES (YEAR(@dtPtr));
        SET @sql2 = CONCAT(@sql2, ", SUM(IF(b1.SHIP_YR=",YEAR(@dtPtr),", (IFNULL(b1.PRODUCT_COST,0)+IFNULL(b1.FREIGHT_COST,0)), 0)) `YR-",YEAR(@dtPtr),"` ");
        SET @dtPtr = DATE_ADD(@dtPtr, INTERVAL 1 YEAR);
    UNTIL YEAR(@dtPtr)>YEAR(@stopDate) END REPEAT;
    
    SET @sql1 = "";
    SET @sql1 = CONCAT(@sql1, "	SELECT ");
    SET @sql1 = CONCAT(@sql1, "		b1.PROCUREMENT_AGENT_ID, b1.PROCUREMENT_AGENT_CODE, b1.PROCUREMENT_AGENT_LABEL_ID, b1.PROCUREMENT_AGENT_LABEL_EN, b1.PROCUREMENT_AGENT_LABEL_FR, b1.PROCUREMENT_AGENT_LABEL_SP, b1.PROCUREMENT_AGENT_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.FUNDING_SOURCE_ID, b1.FUNDING_SOURCE_CODE, b1.FUNDING_SOURCE_LABEL_ID, b1.FUNDING_SOURCE_LABEL_EN, b1.FUNDING_SOURCE_LABEL_FR, b1.FUNDING_SOURCE_LABEL_SP, b1.FUNDING_SOURCE_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.PLANNING_UNIT_ID, b1.PLANNING_UNIT_LABEL_ID, b1.PLANNING_UNIT_LABEL_EN, b1.PLANNING_UNIT_LABEL_FR, b1.PLANNING_UNIT_LABEL_SP, b1.PLANNING_UNIT_LABEL_PR ");
    SET @sql1 = CONCAT(@sql1, "		",@sql2);
    SET @sql1 = CONCAT(@sql1, " FROM tmp_year y ");
    SET @sql1 = CONCAT(@sql1, " LEFT JOIN ( ");
    SET @sql1 = CONCAT(@sql1, " 	SELECT ");
    IF @reportBasedOn=1 THEN 
	SET @sql1 = CONCAT(@sql1, " 	YEAR(st.PLANNED_DATE) SHIP_YR, ");
    ELSE
	SET @sql1 = CONCAT(@sql1, " 	YEAR(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)) SHIP_YR, ");
    END IF;
    SET @sql1 = CONCAT(@sql1, " 		pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD `PRODUCT_COST`, st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD `FREIGHT_COST` ");
    SET @sql1 = CONCAT(@sql1, " 	FROM ");
    SET @sql1 = CONCAT(@sql1, "			(");
    SET @sql1 = CONCAT(@sql1, "				SELECT ");
    SET @sql1 = CONCAT(@sql1, "					s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
    SET @sql1 = CONCAT(@sql1, "				FROM rm_shipment s ");
    SET @sql1 = CONCAT(@sql1, "				LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, "				WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sql1 = CONCAT(@sql1, "				GROUP BY s.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, "		) s ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment_trans  st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_budget b on st.BUDGET_ID=b.BUDGET_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_funding_source fs on fs.FUNDING_SOURCE_ID=b.FUNDING_SOURCE_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_procurement_agent pa on pa.PROCUREMENT_AGENT_ID=st.PROCUREMENT_AGENT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_planning_unit pu on pu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_program_planning_unit ppu on ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sql1 = CONCAT(@sql1, " 	WHERE ");
    SET @sql1 = CONCAT(@sql1, " 		st.ACTIVE AND st.ACCOUNT_FLAG AND AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sql1 = CONCAT(@sql1, " 		AND (st.SHIPMENT_STATUS_ID != 8) ");
    SET @sql1 = CONCAT(@sql1, " 		AND (@programId = -1 OR s.PROGRAM_ID = @programId) ");
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    IF LENGTH(VAR_SHIPMENT_STATUS_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
    END IF;
    
    -- If you want on Order Date change here
    IF @reportBasedOn=1 THEN 
	SET @sql1 = CONCAT(@sql1, " 	AND st.PLANNED_DATE BETWEEN @startDate AND @stopDate ");
    ELSE
	SET @sql1 = CONCAT(@sql1, " 	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND b.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")   ");
    END IF;
    
    SET @sql1 = CONCAT(@sql1, " 	GROUP BY s.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, " ) as b1 ON y.YR=b1.SHIP_YR ");
    SET @sql1 = CONCAT(@sql1, " GROUP BY b1.PROCUREMENT_AGENT_ID, b1.FUNDING_SOURCE_ID, b1.PLANNING_UNIT_ID ");
    SET @sql1 = CONCAT(@sql1, " HAVING PROCUREMENT_AGENT_ID IS NOT NULL ");
    -- select @sql1;
    PREPARE s1 FROM @sql1;
    EXECUTE s1;
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `aggregateShipmentByProduct`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `aggregateShipmentByProduct`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 24 
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- includePlannedShipments = 0 means the report will include all shipments that are Active and not Cancelled
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
    SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
    SET @sqlString = CONCAT(@sqlString,"FROM ");
    SET @sqlString = CONCAT(@sqlString,"	(");
    SET @sqlString = CONCAT(@sqlString,"	SELECT ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"	WHERE ");
    SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString,"WHERE ");
    SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
    SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
	SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `budgetReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `budgetReport`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS TEXT)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 29
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- ProgramId and Version Id that you want to run the report for
    -- StartDate and StopDate only those budgets whose start date falls in between the StartDate and StopDate will show in the report
    -- FundingSourceIds - Comma separated list of the Id's. Empty indicates all Funding Source Ids
    -- ShippingStatusIds - Comma separated list of the Id's. Empty indicates all Shipping Status Ids
    -- Returns the Budget Amts in the Currency of the Budget
    -- Amts are in Millions
    
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    
    SELECT IF(@versionId=-1, MAX(pv.VERSION_ID), @versionId) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR, ");
    SET @sqlString = CONCAT(@sqlString, "	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    c.CURRENCY_ID, c.CURRENCY_CODE, c.LABEL_ID `CURRENCY_LABEL_ID`, c.LABEL_EN `CURRENCY_LABEL_EN`, c.LABEL_FR `CURRENCY_LABEL_FR`, c.LABEL_SP `CURRENCY_LABEL_SP`, c.LABEL_PR `CURRENCY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	b.BUDGET_AMT/1000000 `BUDGET_AMT`, IFNULL(ua.PLANNED_BUDGET,0)/b.CONVERSION_RATE_TO_USD `PLANNED_BUDGET_AMT`, IFNULL(ua.ORDERED_BUDGET,0)/b.CONVERSION_RATE_TO_USD `ORDERED_BUDGET_AMT`,  b.START_DATE, b.STOP_DATE ");
    SET @sqlString = CONCAT(@sqlString, "FROM vw_budget b ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON b.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_currency c ON c.CURRENCY_ID=b.CURRENCY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN "); 
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT "); 
    SET @sqlString = CONCAT(@sqlString, "		st.BUDGET_ID, "); 
    SET @sqlString = CONCAT(@sqlString, "		SUM(IF(st.SHIPMENT_STATUS_ID IN (1), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s1.CONVERSION_RATE_TO_USD),0))/1000000 `PLANNED_BUDGET`, "); -- Only Planned
    SET @sqlString = CONCAT(@sqlString, "		SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s1.CONVERSION_RATE_TO_USD),0))/1000000 `ORDERED_BUDGET` "); -- Submitted, Approved, Shipped, Arrived, Received and On-hold
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "		( ");
    SET @sqlString = CONCAT(@sqlString, "		SELECT ");
    SET @sqlString = CONCAT(@sqlString, "			s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "		FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString, "		LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "		WHERE ");
    SET @sqlString = CONCAT(@sqlString, "			s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "			AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString, "			AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "		GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	) s1 ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID !=8 ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY st.BUDGET_ID ");
    SET @sqlString = CONCAT(@sqlString, ") as ua ON ua.BUDGET_ID=b.BUDGET_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	b.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "	AND (b.START_DATE BETWEEN @startDate AND @stopDate OR b.STOP_DATE BETWEEN @startDate AND @stopDate OR @startDate BETWEEN b.START_DATE AND b.STOP_DATE) ");
    IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "   AND b.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	AND b.ACTIVE AND p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, " ORDER BY b.START_DATE, b.STOP_DATE ");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;