CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `consumptionForecastedVsActual`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_ID INT(10), VAR_REPORT_VIEW INT(10))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 2
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId must be a valid PlanningUnitId
    -- startDate and stopDate are the date range for which you want to run the report
    -- reportView = 1 - Data is reported in terms of Planning Unit
    -- reportView = 2 - Data is reported in terms of Forecasting Unit
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @reportView = VAR_REPORT_VIEW;
    
	SELECT 
		mn.MONTH, 
        IF(@reportView = 1, c.ACTUAL_CONSUMPTION, (c.ACTUAL_CONSUMPTION*c.MULTIPLIER)) `ACTUAL_CONSUMPTION`, 
        IF(@reportView = 1, c.FORECASTED_CONSUMPTION, (c.FORECASTED_CONSUMPTION*c.MULTIPLIER)) `FORECASTED_CONSUMPTION`
	FROM mn 
		LEFT JOIN 
			(
            SELECT spa.TRANS_DATE, pu.MULTIPLIER, SUM(spa.ACTUAL_CONSUMPTION_QTY) `ACTUAL_CONSUMPTION`, SUM(spa.FORECASTED_CONSUMPTION_QTY) `FORECASTED_CONSUMPTION` 
            FROM rm_supply_plan_amc spa 
            LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
            WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId and spa.PLANNING_UNIT_ID=@planningUnitId AND spa.TRANS_DATE BETWEEN @startDate AND @stopDate 
            GROUP BY spa.TRANS_DATE
		) c ON mn.MONTH=c.TRANS_DATE
	WHERE mn.MONTH BETWEEN @startDate AND @stopDate ORDER BY mn.MONTH;
END
