DROP PROCEDURE `fasp`.`shipmentOverview_fundingSourceSplit`;
DROP PROCEDURE `fasp`.`shipmentOverview_fundingSourceTypeSplit`;
-- DROP PROCEDURE `fasp`.`shipmentOverviewPlanningUnitQuantity`;
DROP PROCEDURE `fasp`.`shipmentOverview_planningUnitSplit`;
DROP PROCEDURE `fasp`.`shipmentOverview_procurementAgentSplit`;
DROP PROCEDURE `fasp`.`shipmentOverview_procurementAgentTypeSplit`;

USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_planningUnitQuantity`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentOverview_planningUnitQuantity`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_planningUnitQuantity`(IN VAR_CUR_USER_ID INT(10), IN VAR_REALM_ID INT(10), IN VAR_START_DATE DATE, IN VAR_STOP_DATE DATE, IN VAR_REALM_COUNTRY_IDS TEXT, IN VAR_PROGRAM_IDS TEXT, IN VAR_VERSION_ID INT(10), IN VAR_FSPA VARCHAR(2), IN VAR_FSPA_IDS TEXT, IN VAR_PLANNING_UNIT_IDS TEXT, IN VAR_SHIPMENT_STATUS_IDS TEXT)
BEGIN
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 1 Planning Unit Quantity
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- VersionId will only be used if there is a Single ProgramId selected
 -- If you want to select the Latest Version pass VersionId = -1
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- fspa = FS for Funding Source and fspa = PA for Procurement Agent
 -- fspaIds is the list of Funding Sources / Procurement Agents that you want to run the report for
 -- Empty fspaIds means you want to run for all the Funding Sources / Procurement Agents
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses

    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_CUR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
        read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
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
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fspa = VAR_FSPA;
    SET @fspaIds = VAR_FSPA_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;
	
    SELECT LOCATE(",", @programIds) INTO @varCommaLocation;
    SELECT IF(@programIds <> "" AND @varCommaLocation = 0, IF(@versionId=-1, null, @versionId), null) into @versionId;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, SUM(st.SHIPMENT_QTY) `SHIPMENT_QTY`, ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.FUNDING_SOURCE_ID `FSPA_ID`, fspa.FUNDING_SOURCE_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR` ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.PROCUREMENT_AGENT_ID `FSPA_ID`, fspa.PROCUREMENT_AGENT_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR` ");
    END IF;
    
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "       ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=COALESCE(@versionId, p.CURRENT_VERSION_ID) ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       	(LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       	AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fspa ON st.FUNDING_SOURCE_ID=fspa.FUNDING_SOURCE_ID ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent fspa ON st.PROCUREMENT_AGENT_ID=fspa.PROCUREMENT_AGENT_ID ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND ((@fspa='FS' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fspaIds))) OR (@fspa='PA' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID,@fspaIds)))) ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY pu.PLANNING_UNIT_ID, FSPA_ID");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END$$

DELIMITER ;
;



USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_fspaCost`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentOverview_fspaCost`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_fspaCost`(IN VAR_CUR_USER_ID INT(10), IN VAR_REALM_ID INT(10), IN VAR_START_DATE DATE, IN VAR_STOP_DATE DATE, IN VAR_REALM_COUNTRY_IDS TEXT, IN VAR_PROGRAM_IDS TEXT, IN VAR_VERSION_ID INT(10), IN VAR_FSPA VARCHAR(2), IN VAR_FSPA_IDS TEXT, IN VAR_PLANNING_UNIT_IDS TEXT, IN VAR_SHIPMENT_STATUS_IDS TEXT)
BEGIN
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 2 FSPA based Cost
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- VersionId will only be used if there is a Single ProgramId selected
 -- If you want to select the Latest Version pass VersionId = -1
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- fspa = FS for Funding Source and fspa = PA for Procurement Agent
 -- fspaIds is the list of Funding Sources / Procurement Agents that you want to run the report for
 -- Empty fspaIds means you want to run for all the Funding Sources / Procurement Agents
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses

    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_CUR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
        read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
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
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fspa = VAR_FSPA;
    SET @fspaIds = VAR_FSPA_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;
	
    SELECT LOCATE(",", @programIds) INTO @varCommaLocation;
    SELECT IF(@programIds <> "" AND @varCommaLocation = 0, IF(@versionId=-1, null, @versionId), null) into @versionId;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.FUNDING_SOURCE_ID `FSPA_ID`, fspa.FUNDING_SOURCE_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.PROCUREMENT_AGENT_ID `FSPA_ID`, fspa.PROCUREMENT_AGENT_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "SUM((st.PRODUCT_COST+st.FREIGHT_COST)*s.CONVERSION_RATE_TO_USD) `TOTAL_COST` ");
    
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "       ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=COALESCE(@versionId, p.CURRENT_VERSION_ID) ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       	(LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       	AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fspa ON st.FUNDING_SOURCE_ID=fspa.FUNDING_SOURCE_ID ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent fspa ON st.PROCUREMENT_AGENT_ID=fspa.PROCUREMENT_AGENT_ID ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND ((@fspa='FS' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fspaIds))) OR (@fspa='PA' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID,@fspaIds)))) ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY FSPA_ID");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_fspaProgramSplit`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentOverview_fspaProgramSplit`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_fspaProgramSplit`(IN VAR_CUR_USER_ID INT(10), IN VAR_REALM_ID INT(10), IN VAR_START_DATE DATE, IN VAR_STOP_DATE DATE, IN VAR_REALM_COUNTRY_IDS TEXT, IN VAR_PROGRAM_IDS TEXT, IN VAR_VERSION_ID INT(10), IN VAR_FSPA VARCHAR(2), IN VAR_FSPA_IDS TEXT, IN VAR_PLANNING_UNIT_IDS TEXT, IN VAR_SHIPMENT_STATUS_IDS TEXT)
BEGIN
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 3a FSPA-Program-PU based Cost and Quantity 
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- VersionId will only be used if there is a Single ProgramId selected
 -- If you want to select the Latest Version pass VersionId = -1
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- fspa = FS for Funding Source and fspa = PA for Procurement Agent
 -- fspaIds is the list of Funding Sources / Procurement Agents that you want to run the report for
 -- Empty fspaIds means you want to run for all the Funding Sources / Procurement Agents
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses

    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_CUR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
        read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
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
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fspa = VAR_FSPA;
    SET @fspaIds = VAR_FSPA_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;
	
    SELECT LOCATE(",", @programIds) INTO @varCommaLocation;
    SELECT IF(@programIds <> "" AND @varCommaLocation = 0, IF(@versionId=-1, null, @versionId), null) into @versionId;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.FUNDING_SOURCE_ID `FSPA_ID`, fspa.FUNDING_SOURCE_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.PROCUREMENT_AGENT_ID `FSPA_ID`, fspa.PROCUREMENT_AGENT_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "p.PROGRAM_ID `P_ID`, p.PROGRAM_CODE `P_CODE`, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "pu.PLANNING_UNIT_ID `PU_ID`, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "SUM(st.SHIPMENT_QTY) `SHIPMENT_QTY`, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST` ");
    
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "       ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=COALESCE(@versionId, p.CURRENT_VERSION_ID) ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       	(LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       	AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fspa ON st.FUNDING_SOURCE_ID=fspa.FUNDING_SOURCE_ID ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent fspa ON st.PROCUREMENT_AGENT_ID=fspa.PROCUREMENT_AGENT_ID ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND ((@fspa='FS' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fspaIds))) OR (@fspa='PA' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID,@fspaIds)))) ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY FSPA_ID, p.PROGRAM_ID, pu.PLANNING_UNIT_ID");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_fspaCountrySplit`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentOverview_fspaCountrySplit`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_fspaCountrySplit`(IN VAR_CUR_USER_ID INT(10), IN VAR_REALM_ID INT(10), IN VAR_START_DATE DATE, IN VAR_STOP_DATE DATE, IN VAR_REALM_COUNTRY_IDS TEXT, IN VAR_PROGRAM_IDS TEXT, IN VAR_VERSION_ID INT(10), IN VAR_FSPA VARCHAR(2), IN VAR_FSPA_IDS TEXT, IN VAR_PLANNING_UNIT_IDS TEXT, IN VAR_SHIPMENT_STATUS_IDS TEXT)
BEGIN
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 3b FSPA-Country-PU based Cost and Quantity 
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- VersionId will only be used if there is a Single ProgramId selected
 -- If you want to select the Latest Version pass VersionId = -1
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- fspa = FS for Funding Source and fspa = PA for Procurement Agent
 -- fspaIds is the list of Funding Sources / Procurement Agents that you want to run the report for
 -- Empty fspaIds means you want to run for all the Funding Sources / Procurement Agents
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses

    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_CUR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
        read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
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
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fspa = VAR_FSPA;
    SET @fspaIds = VAR_FSPA_IDS;
    SET @shipmentStatusIds = VAR_SHIPMENT_STATUS_IDS;
	
    SELECT LOCATE(",", @programIds) INTO @varCommaLocation;
    SELECT IF(@programIds <> "" AND @varCommaLocation = 0, IF(@versionId=-1, null, @versionId), null) into @versionId;

    SET @sqlString = "";
 
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.FUNDING_SOURCE_ID `FSPA_ID`, fspa.FUNDING_SOURCE_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "fspa.PROCUREMENT_AGENT_ID `FSPA_ID`, fspa.PROCUREMENT_AGENT_CODE `FSPA_CODE`, fspa.LABEL_ID `FSPA_LABEL_ID`, fspa.LABEL_EN `FSPA_LABEL_EN`, fspa.LABEL_FR `FSPA_LABEL_FR`, fspa.LABEL_SP `FSPA_LABEL_SP`, fspa.LABEL_PR `FSPA_LABEL_PR`, ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "rc.REALM_COUNTRY_ID `P_ID`, c.COUNTRY_CODE `P_CODE`, c.LABEL_ID `P_LABEL_ID`, c.LABEL_EN `P_LABEL_EN`, c.LABEL_FR `P_LABEL_FR`, c.LABEL_SP `P_LABEL_SP`, c.LABEL_PR `P_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "pu.PLANNING_UNIT_ID `PU_ID`, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "SUM(st.SHIPMENT_QTY) `SHIPMENT_QTY`, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST` ");
    
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "       ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=COALESCE(@versionId, p.CURRENT_VERSION_ID) ");
    SET @sqlString = CONCAT(@sqlString, "       WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       	(LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       	AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "       ) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON s1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s ON s1.SHIPMENT_ID=s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    IF @fspa = "FS" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fspa ON st.FUNDING_SOURCE_ID=fspa.FUNDING_SOURCE_ID ");
    ELSEIF @fspa = "PA" THEN
        SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent fspa ON st.PROCUREMENT_AGENT_ID=fspa.PROCUREMENT_AGENT_ID ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL AND ppu.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND ((@fspa='FS' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fspaIds))) OR (@fspa='PA' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID,@fspaIds)))) ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY FSPA_ID, rc.REALM_COUNTRY_ID, pu.PLANNING_UNIT_ID");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentOverview.planningUnitQuantityByPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Unit Quantity by');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité de l\'unité de planification par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de unidad de planificación por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade de unidades de planejamento por');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.aggregateByCountry','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Aggregate by Country');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agrégation par pays');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregado por país');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agregado por país');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.hideCalculations','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Calculations');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer les calculs');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar cálculos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar cálculos');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.collapsePlanningUnits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Collapse Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification de l\'effondrement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de planificación de colapso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de Planejamento de Colapso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.totalPUCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Planning Unit Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total de l\'unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo unitario total de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo unitário total de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.totalFreightCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Freight Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total du transport');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total del flete');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total do frete');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.totalCostPerc','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Cost (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total (%)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total (%)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.shipmentByPlanningUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipments (by Planning Unit)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions (par unité de planification)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos (por Unidad de Planificación)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas (por unidade de planejamento)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.note','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All cost calculations are displayed in USD and any percentage below 2% cannot be displayed in the pie chart.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous les calculs de coûts sont affichés en dollars américains et tout pourcentage inférieur à 2 % ne peut pas être affiché dans le graphique circulaire.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los cálculos de costos se muestran en USD y cualquier porcentaje inferior al 2 % no se puede mostrar en el gráfico circular.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todos os cálculos de custos são exibidos em dólares americanos (USD) e qualquer porcentagem abaixo de 2% não pode ser exibida no gráfico de pizza.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.qtyMayRepresentMultipleShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'May represent one or multiple shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Peut représenter un ou plusieurs envois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puede representar uno o varios envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pode representar uma ou várias remessas.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.totalPlanningUnitCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Planning Unit Cost = Quantity * Planning Unit Cost (user-entered), at the shipment-level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût unitaire de planification total = Quantité * Coût unitaire de planification (saisi par l\'utilisateur), au niveau de l\'expédition');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo unitario de planificación total = Cantidad * Costo unitario de planificación (ingresado por el usuario), a nivel de envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo Unitário Total de Planejamento = Quantidade * Custo Unitário de Planejamento (inserido pelo usuário), no nível da remessa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.totalFreightCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Freight Cost is calculated based on Transportation Mode and Planning Unit Cost (automatic or entered manually), at the shipment-level.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le coût total du fret est calculé en fonction du mode de transport et du coût unitaire de planification (automatique ou saisi manuellement), au niveau de l\'expédition.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El costo total del flete se calcula en función del modo de transporte y el costo unitario de planificación (automático o ingresado manualmente), a nivel de envío.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O custo total do frete é calculado com base no modal de transporte e no custo unitário de planejamento (automático ou inserido manualmente), no nível da remessa.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.totalCostGlobalDemand','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Cost = Total Freight Cost + Total Planning Unit Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total = Coût total du transport + Coût total de l\'unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total = costo total del flete + costo total de la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total = Custo total do frete + Custo unitário total de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.totalCostPercGlobalDemand','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For Procurement Agents/Funders, this is the percentage across all Procurement Agents/Funders. \n\nFor Programs/Countries, this is the percentage within the specific Procurement Agent/Funder. \n\nFor Planning Units, this is the percentage within the Program/Country');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour les organismes d\'achat/financeurs, il s\'agit du pourcentage pour l\'ensemble des organismes d\'achat/financeurs. \n\nPour les programmes/pays, il s\'agit du pourcentage au sein de l\'organisme d\'achat/financeur concerné. \n\nPour les unités de planification, il s\'agit du pourcentage au sein du programme/pays.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para los agentes de adquisiciones/financiadores, este es el porcentaje de todos los agentes de adquisiciones/financiadores. Para los programas/países, este es el porcentaje dentro del agente de adquisiciones/financiador específico. Para las unidades de planificación, este es el porcentaje dentro del programa/país.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para Agentes/Financiadores de Compras, esta é a porcentagem em todos os Agentes/Financiadores de Compras. \n\nPara Programas/Países, esta é a porcentagem dentro do Agente/Financiador de Compras específico. \n\nPara Unidades de Planejamento, esta é a porcentagem dentro do Programa/País.');-- pr