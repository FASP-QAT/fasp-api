CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrixGlobal`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_REMOVE_PLANNED_SHIPMENTS_THAT_FAIL_LEAD_TIME TINYINT(1), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT, VAR_REPORT_VIEW INT(10))
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18b
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    
    -- startDate and stopDate are the period for which you want to run the report
    -- realmCountryIds is the list of Countries that you want to run the report for; Empty means all countries
    -- programIds is the list of Programs that you want to run the report for; Empty means all programs
    -- versionId Only to be used when a Single Program is selected. So if you get VersionId<>0 it means only the first ProgramId should be used from the list; -1 would mean that use the latest VersionId
    -- equivalencyUnitId When passed as 0 it means that No EquivalencyUnitId was selected and therefore only a single PlanningUnit can be selected; If an EquivalencyUnitId is selected it means that multiple PlanningUnits can be selected since the report is to be showing in terms of EU
    -- planningUnitIds is the list of Planning Units that you want to include in the report; Empty means all PlanningUnitIds; When EU is 0 then only a single PU can be selected
    -- stockStatusIds is the list of Stock Statuses that you want to show in the report; For now this is not possible need to go back to FASP about this option.
    -- removePlannedShipments = 1 means that you want to remove the shipments that are in the Planned stage while running this report.
    -- removePlannedShipments = 0 means that you want to retain the shipments that are in the Planned stage while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 1 means that you want to remove the shipments that are in the Planned stage and fail the Lead Time while running this report.; For now this is not possible need to go back to FASP about this option.
    -- removePlannedShipmentsThatFailLeadTime = 0 means that you want to retain the shipments that are in the Planned stage and fail the Lead Time while running this report.; For now this is not possible need to go back to FASP about this option.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed; For now this is not possible need to go back to FASP about this option.
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed; For now this is not possible need to go back to FASP about this option.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    -- reportView=1 means show in terms of MoS and Qty based on the PlannedBasedOn setting; If reportView=2 it means show everything in terms of Qty but retain the color coding and StockStatusId
    
    DECLARE curMn DATE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_mn CURSOR FOR SELECT mn.MONTH FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE and VAR_STOP_DATE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @mnSqlString = "";
    OPEN cursor_mn;
        read_loop: LOOP
        FETCH cursor_mn INTO curMn;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @mnSqlString = CONCAT(@mnSqlString, "   	, GROUP_CONCAT(IF(mn.MONTH='",curMn,"', amc2.PLANNING_UNIT_IDS, null)) `PLANNING_UNIT_IDS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.MOS, null)) `MOS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_STATUS_ID, null)) `STOCK_STATUS_ID_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.CLOSING_BALANCE, null)) `CLOSING_BALANCE_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_MULTIPLIED_QTY, null)) `STOCK_MULTIPLIED_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.SHIPMENT_QTY, null)) `SHIPMENT_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.EXPIRED_STOCK, null)) `EXPIRED_STOCK_",curMn,"` ");
    END LOOP;
    CLOSE cursor_mn;
    
    SET @varVersionId = VAR_VERSION_ID;
    IF VAR_VERSION_ID = 0 THEN 
		SET @varVersionId = null;
	ELSEIF VAR_VERSION_ID = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;
    -- SELECT @varVersionId;
    DROP TABLE IF EXISTS tmp_amc;
    CREATE TABLE tmp_amc
    SELECT 
        amc.TRANS_DATE, p.PROGRAM_ID, p.REALM_COUNTRY_ID,
        amc.PLANNING_UNIT_ID, ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, amc.MIN_STOCK_QTY, amc.MAX_STOCK_QTY, 
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.CLOSING_BALANCE_WPS, amc.CLOSING_BALANCE) `CLOSING_BALANCE`,
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.MOS_WPS, amc.MOS) `MOS`,
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, (amc.SHIPMENT_QTY-amc.MANUAL_PLANNED_SHIPMENT_QTY-amc.ERP_PLANNED_SHIPMENT_QTY), amc.SHIPMENT_QTY) `SHIPMENT_QTY`,
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.EXPIRED_STOCK_WPS, amc.EXPIRED_STOCK) `EXPIRED_STOCK_QTY`,
        amc.AMC, amc.STOCK_MULTIPLIED_QTY, IF(VAR_EQUIVALENCY_UNIT_ID=0, 1, pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)) `CONVERSION`
    FROM vw_program p 
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND (LENGTH(VAR_PLANNING_UNIT_IDS)=0 OR FIND_IN_SET(ppu.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS))
    LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=p.PROGRAM_ID AND amc.VERSION_ID=COALESCE(@varVersionId, p.CURRENT_VERSION_ID) AND amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
    LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum1 ON pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.PROGRAM_ID=p.PROGRAM_ID AND eum1.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum2 ON pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID IS NULL AND eum2.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    WHERE TRUE AND p.ACTIVE
        AND (LENGTH(VAR_REALM_COUNTRY_IDS)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, VAR_REALM_COUNTRY_IDS))
        AND (LENGTH(VAR_PROGRAM_IDS)=0 OR FIND_IN_SET(p.PROGRAM_ID, VAR_PROGRAM_IDS))
        AND ppu.PLANNING_UNIT_ID is not null;

	SET @interimSql = "SELECT 
                                IF(@reportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID) `ID`,
                                amc.TRANS_DATE,
                                GROUP_CONCAT(DISTINCT amc.PLANNING_UNIT_ID) `PLANNING_UNIT_IDS`,
                                CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END `MOS`,
                                IF(SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,1,2) `PLAN_BASED_ON`,
                                IF(
                                    SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,
                                    CASE 
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END IS NULL THEN -1 
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END = 0 THEN 0
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END < SUM(amc.MIN_MONTHS_OF_STOCK) THEN 1
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END <= SUM(amc.MIN_MONTHS_OF_STOCK+amc.REORDER_FREQUENCY_IN_MONTHS) THEN 2
                                        ELSE 3
                                    END,
                                    CASE 
                                        WHEN SUM(amc.CLOSING_BALANCE)=0 THEN 0
                                        WHEN SUM(amc.CLOSING_BALANCE)<SUM(amc.MIN_STOCK_QTY) THEN 1
                                        WHEN SUM(amc.CLOSING_BALANCE)<=SUM(amc.MAX_STOCK_QTY) THEN 2
                                        ELSE 3
                                    END
                                ) `STOCK_STATUS_ID`,
                                SUM(amc.CLOSING_BALANCE*amc.CONVERSION) `CLOSING_BALANCE`,
                                CASE WHEN COUNT(amc.STOCK_MULTIPLIED_QTY)=count(*) THEN SUM(amc.STOCK_MULTIPLIED_QTY*amc.CONVERSION) ELSE NULL END `STOCK_QTY`,
                                SUM(amc.SHIPMENT_QTY*amc.CONVERSION) `SHIPMENT_QTY`,
                                SUM(amc.EXPIRED_STOCK_QTY*amc.CONVERSION) `EXPIRED_STOCK_QTY`
                            FROM tmp_amc amc
                            group by amc.TRANS_DATE, IF(@reportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID)";
	
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   amc2.`ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_ID, c.LABEL_ID) `LABEL_ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_EN, c.LABEL_EN) `LABEL_EN`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_FR, c.LABEL_FR) `LABEL_FR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_SP, c.LABEL_SP) `LABEL_SP`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_PR, c.LABEL_PR) `LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.PROGRAM_CODE, c.COUNTRY_CODE) `CODE` ");
    SET @sqlString = CONCAT(@sqlString, @mnSqlString);
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (",@interimSql,") amc2 ON mn.MONTH=amc2.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON amc2.ID=p.PROGRAM_ID AND @reportView=1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON amc2.ID=rc.REALM_COUNTRY_ID AND @reportView=2 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN '",VAR_START_DATE,"' AND '",VAR_STOP_DATE,"' ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY amc2.ID ");
    -- SELECT * FROM tmp_amc;
    -- SELECT @sqlString;
    -- PREPARE S2 FROM @interimSql;
    -- EXECUTE S2;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END