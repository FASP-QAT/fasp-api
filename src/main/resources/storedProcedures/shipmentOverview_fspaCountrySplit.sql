CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `shipmentOverview_fspaCountrySplit`(IN VAR_CUR_USER_ID INT(10), IN VAR_REALM_ID INT(10), IN VAR_START_DATE DATE, IN VAR_STOP_DATE DATE, IN VAR_REALM_COUNTRY_IDS TEXT, IN VAR_PROGRAM_IDS TEXT, IN VAR_VERSION_ID INT(10), IN VAR_FSPA VARCHAR(2), IN VAR_FSPA_IDS TEXT, IN VAR_PLANNING_UNIT_IDS TEXT, IN VAR_SHIPMENT_STATUS_IDS TEXT)
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
    SET @sqlString = CONCAT(@sqlString, "rcpu.REALM_COUNTRY_ID `P_ID`, c.COUNTRY_CODE `P_CODE`, c.LABEL_ID `P_LABEL_ID`, c.LABEL_EN `P_LABEL_EN`, c.LABEL_FR `P_LABEL_FR`, c.LABEL_SP `P_LABEL_SP`, c.LABEL_PR `P_LABEL_PR`, ");
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
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND (LENGTH(@shipmentStatusIds)=0 OR FIND_IN_SET(st.SHIPMENT_STATUS_ID,@shipmentStatusIds)) ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	AND ((@fspa='FS' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fspaIds))) OR (@fspa='PA' AND (LENGTH(@fspaIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID,@fspaIds)))) ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY FSPA_ID, rcpu.REALM_COUNTRY_ID, pu.PLANNING_UNIT_ID");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END