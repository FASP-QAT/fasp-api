CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getStockStatusForProgram`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_DT DATE, VAR_TRACER_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 28
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- dt is the month for which you want to run the report
    -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
    -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    -- if a Month does not have Consumption then it is excluded from the AMC calculations
    -- MinMonthsOfStock is Max of MinMonth of Stock taken from the Program-planning Unit and 3
    -- MaxMonthsOfStock is Min of Min of MinMonthOfStock+ReorderFrequency and 15
    -- tracerCategoryIds is a list of the Tracer Category Ids that the user wants to run the report for. Empty will indicate they want to run it for all Tracer Categories.	
	    
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @dt = VAR_DT;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    ppu.MIN_MONTHS_OF_STOCK, (ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS) `MAX_MONTHS_OF_STOCK`, ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.CLOSING_BALANCE,0), IFNULL(amc.CLOSING_BALANCE_WPS,0)) `STOCK`,  ");
    SET @sqlString = CONCAT(@sqlString, "    amc.AMC `AMC`,  ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.MOS,NULL), IFNULL(amc.MOS_WPS,NULL)) `MoS`, ");
    SET @sqlString = CONCAT(@sqlString, "    a3.LAST_STOCK_DATE `STOCK_COUNT_DATE` ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=@programId AND amc.VERSION_ID=@versionId AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND amc.TRANS_DATE=@dt ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT a2.PLANNING_UNIT_ID, MAX(a2.TRANS_DATE) LAST_STOCK_DATE FROM rm_supply_plan_amc a2 WHERE a2.PROGRAM_ID=@programId AND a2.VERSION_ID=@versionId AND a2.TRANS_DATE<=@dt AND a2.REGION_COUNT=a2.REGION_COUNT_FOR_STOCK GROUP BY a2.PLANNING_UNIT_ID) a3 ON amc.PLANNING_UNIT_ID=a3.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_TRACER_CATEGORY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, " AND fu.TRACER_CATEGORY_ID IN (",VAR_TRACER_CATEGORY_IDS,") ");
    END IF;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END
