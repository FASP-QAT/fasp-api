CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `shipmentGlobalDemand_CountrySplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 1 
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
        SELECT 
            CASE @varReportView
                WHEN 1 THEN fs.FUNDING_SOURCE_ID
                WHEN 2 THEN pa.PROCUREMENT_AGENT_ID
                WHEN 3 THEN fst.FUNDING_SOURCE_TYPE_ID
                WHEN 4 THEN pat.PROCUREMENT_AGENT_TYPE_ID
            END AS `FSPA_ID`,
            CASE @varReportView
                WHEN 1 THEN fs.FUNDING_SOURCE_CODE
                WHEN 2 THEN pa.PROCUREMENT_AGENT_CODE
                WHEN 3 THEN fst.FUNDING_SOURCE_TYPE_CODE
                WHEN 4 THEN pat.PROCUREMENT_AGENT_TYPE_CODE
            END AS `FSPA_CODE`
        FROM vw_program p 
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID AND s.MAX_VERSION_ID<=p.CURRENT_VERSION_ID
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
        LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID
        LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        LEFT JOIN vw_procurement_agent_type pat ON pa.PROCUREMENT_AGENT_TYPE_ID=pat.PROCUREMENT_AGENT_TYPE_ID
        WHERE 
            TRUE AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL 
            AND (LENGTH(@varRealmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @varRealmCountryIds))
            AND (LENGTH(@varProgramIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @varProgramIds)) 
            AND FIND_IN_SET(st.PLANNING_UNIT_ID, @varPlanningUnitIds)
            AND st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8
            AND (@varIncludePlannedShipments=1 OR st.SHIPMENT_STATUS_ID in (3,4,5,6,7))
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @varStartDate AND @varStopDate
            AND (
                (@varReportView=1 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @varFundingSourceProcurementAgentIds))) OR 
                (@varReportView=2 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @varFundingSourceProcurementAgentIds))) OR 
                (@varReportView=3 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(fs.FUNDING_SOURCE_TYPE_ID, @varFundingSourceProcurementAgentIds))) OR 
                (@varReportView=4 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(pa.PROCUREMENT_AGENT_ID, @varFundingSourceProcurementAgentIds)))
            )
        GROUP BY `FSPA_ID`;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @varRealmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @varFundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
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

    SET @varRealmId = VAR_REALM_ID;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varEquivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID;
    SET @varPlanningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @varReportView = VAR_REPORT_VIEW;
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varIncludePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    SET @sqlStringFSPA = "";

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
        FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
            LEAVE getFSPA;
        END IF;
        IF @varReportView = 1 THEN
            SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.FUNDING_SOURCE_ID=",fspaId,", IF(@varEquivalencyUnitId=0,st.SHIPMENT_QTY,st.SHIPMENT_QTY*pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)), null)) `FSPA_",fspaCode,"` ");
        ELSEIF @varReportView = 2 THEN
            SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(st.PROCUREMENT_AGENT_ID=",fspaId,", IF(@varEquivalencyUnitId=0,st.SHIPMENT_QTY,st.SHIPMENT_QTY*pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)), null)) `FSPA_",fspaCode,"` ");
        ELSEIF @varReportView = 3 THEN
            SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(fs.FUNDING_SOURCE_TYPE_ID=",fspaId,", IF(@varEquivalencyUnitId=0,st.SHIPMENT_QTY,st.SHIPMENT_QTY*pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)), null)) `FSPA_",fspaCode,"` ");
        ELSEIF @varReportView = 4 THEN
            SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(pa.PROCUREMENT_AGENT_TYPE_ID=",fspaId,", IF(@varEquivalencyUnitId=0,st.SHIPMENT_QTY,st.SHIPMENT_QTY*pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)), null)) `FSPA_",fspaCode,"` ");
        END IF;
    END LOOP;
    CLOSE fspa_cursor;

    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "       rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
    SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
    SET @sqlString = CONCAT(@sqlString, "FROM vw_program p  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID AND s.MAX_VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit_mapping eum1 ON eum1.REALM_ID=@varRealmId AND pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId AND eum1.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit eu1 ON eum1.EQUIVALENCY_UNIT_ID=eu1.EQUIVALENCY_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit_mapping eum2 ON eum2.REALM_ID=@varRealmId AND pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId AND eum2.PROGRAM_ID is null ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit eu2 ON eum2.EQUIVALENCY_UNIT_ID=eu2.EQUIVALENCY_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent_type pat ON pa.PROCUREMENT_AGENT_TYPE_ID=pat.PROCUREMENT_AGENT_TYPE_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE  ");
    SET @sqlString = CONCAT(@sqlString, "    TRUE AND ppu.ACTIVE AND pu.ACTIVE AND ppu.PROGRAM_PLANNING_UNIT_ID IS NOT NULL  ");
    SET @sqlString = CONCAT(@sqlString, "    AND (LENGTH(@varRealmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @varRealmCountryIds)) ");
    SET @sqlString = CONCAT(@sqlString, "    AND (LENGTH(@varProgramIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @varProgramIds))  ");
    SET @sqlString = CONCAT(@sqlString, "    AND FIND_IN_SET(st.PLANNING_UNIT_ID, @varPlanningUnitIds) ");
    SET @sqlString = CONCAT(@sqlString, "    AND st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID != 8 ");
    SET @sqlString = CONCAT(@sqlString, "    AND (@varIncludePlannedShipments=1 OR st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) ");
    SET @sqlString = CONCAT(@sqlString, "    AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @varStartDate AND @varStopDate ");
    SET @sqlString = CONCAT(@sqlString, "    AND ( ");
    SET @sqlString = CONCAT(@sqlString, "            (@varReportView=1 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @varFundingSourceProcurementAgentIds))) OR  ");
    SET @sqlString = CONCAT(@sqlString, "            (@varReportView=2 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @varFundingSourceProcurementAgentIds))) OR  ");
    SET @sqlString = CONCAT(@sqlString, "            (@varReportView=3 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(fs.FUNDING_SOURCE_TYPE_ID, @varFundingSourceProcurementAgentIds))) OR  ");
    SET @sqlString = CONCAT(@sqlString, "            (@varReportView=4 AND (LENGTH(@varFundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(pa.PROCUREMENT_AGENT_ID, @varFundingSourceProcurementAgentIds))) ");
    SET @sqlString = CONCAT(@sqlString, "    ) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY rc.REALM_COUNTRY_ID");

    PREPARE S1 FROM @sqlString; 
    EXECUTE S1;
END