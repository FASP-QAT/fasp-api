CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `forecastMetricsMonthly`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_ID INT(10), VAR_PREVIOUS_MONTHS INT(10))
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 4
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- startDate and stopDate are the range that you want to run the report for
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitIt must be a single Planning Unit cannot be multi-select or -1 for all 
    -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
    -- current month is always included in the calculation
    -- WAPE Formulae
    -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months)) 

	SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    SET @previousMonths = VAR_PREVIOUS_MONTHS;
    
    SELECT fe.MONTH, fe.MONTH_COUNT, fe.ACTUAL_CONSUMPTION_HISTORY, fe.DIFF_CONSUMPTION_HISTORY, if (fe.ACTUAL AND fe.ACTUAL_CONSUMPTION IS NOT NULL AND fe.FORECASTED_CONSUMPTION IS NOT NULL, fe.DIFF_CONSUMPTION_HISTORY*100/fe.ACTUAL_CONSUMPTION_HISTORY, null) FORECAST_ERROR, fe.ACTUAL_CONSUMPTION, fe.FORECASTED_CONSUMPTION, fe.ACTUAL FROM (
	SELECT 
		mn.MONTH, 
        SUM(IF(c1.ACTUAL AND c1.ACTUAL_CONSUMPTION IS NOT NULL AND c1.FORECASTED_CONSUMPTION IS NOT NULL,1,0)) `MONTH_COUNT`,
        SUM(IF(c1.ACTUAL AND c1.ACTUAL_CONSUMPTION IS NOT NULL AND c1.FORECASTED_CONSUMPTION IS NOT NULL, c1.ACTUAL_CONSUMPTION,null)) ACTUAL_CONSUMPTION_HISTORY, 
        SUM(IF(c1.ACTUAL AND c1.ACTUAL_CONSUMPTION IS NOT NULL AND c1.FORECASTED_CONSUMPTION IS NOT NULL, ABS(c1.FORECASTED_CONSUMPTION-c1.ACTUAL_CONSUMPTION),null)) DIFF_CONSUMPTION_HISTORY, 
        c2.ACTUAL, c2.ACTUAL_CONSUMPTION, c2.FORECASTED_CONSUMPTION
	FROM mn 
	LEFT JOIN 
		(
        SELECT spa.TRANS_DATE, spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, spa.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION` 
        FROM rm_supply_plan_amc spa
        LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId and spa.PLANNING_UNIT_ID=@planningUnitId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @stopDate 
	) c1 ON c1.TRANS_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH
    LEFT JOIN 
		(
        SELECT spa.TRANS_DATE, spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, spa.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION` 
        FROM rm_supply_plan_amc spa
        LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId and spa.PLANNING_UNIT_ID=@planningUnitId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @stopDate 
        GROUP BY spa.TRANS_DATE 
	) c2 ON c2.TRANS_DATE=mn.MONTH
	WHERE mn.MONTH BETWEEN @startDate AND @stopDate
	GROUP BY mn.MONTH) fe;
END
