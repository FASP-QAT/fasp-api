CREATE DEFINER=`faspUser`@`%` PROCEDURE `getForecastErrorNew`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT (10), VAR_VIEW_BY INT(10), VAR_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REGION_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PREVIOUS_MONTHS INT(10), VAR_DAYS_OF_STOCK_OUT TINYINT (1))
BEGIN 
    SET @programId = VAR_PROGRAM_ID; 
    SET @versionId = VAR_VERSION_ID; -- Can be -1 for the latest Program
    SET @unitIds= VAR_UNIT_IDS; -- PU or FU based on viewBy
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @viewBy = VAR_VIEW_BY; -- 1 for PU and 2 for FU
    SET @regionIds = VAR_REGION_IDS; -- empty if all Regions
    SET @equivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID; -- If the output is to be in EquivalencyUnit then this is a non zero id
    SET @previousMonths = VAR_PREVIOUS_MONTHS; -- The number of months that we need to average the Consumption for WAPE. Does not include current month which is always included.
    SET @daysOfStockOut = VAR_DAYS_OF_STOCK_OUT; -- Boolean field that if true means we should consider the Days of Stock Out valued and adjust the consumption accordingly. Only adjusts for Actual Consumption.
    
    IF @versionId = -1 THEN 
        SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;
    
    
    SELECT 
        mn.MONTH, r.REGION_ID, r.LABEL_ID, r.LABEL_EN, r.LABEL_FR, r.LABEL_SP, r.LABEL_PR,
        SUM(IF(c1.CONSUMPTION_DATE=mn.MONTH, IF(@equivalencyUnitId=0, IF(@viewBy=1,	c1.FORECAST_CONSUMPTION, c1.FORECAST_CONSUMPTION*c1.MULTIPLIER), c1.FORECAST_CONSUMPTION*c1.MULTIPLIER*COALESCE(eum1.CONVERT_TO_EU,eum2.CONVERT_TO_EU)),null)) `FORECAST_CONSUMPTION`, 
        SUM(IF(c1.CONSUMPTION_DATE=mn.MONTH, IF(@equivalencyUnitId=0, IF(@viewBy=1,	IF(@daysOfStockOut, c1.ADJUSTED_ACTUAL_CONSUMPTION, c1.ACTUAL_CONSUMPTION), IF(@daysOfStockOut, c1.ADJUSTED_ACTUAL_CONSUMPTION, c1.ACTUAL_CONSUMPTION)*c1.MULTIPLIER), IF(@daysOfStockOut, c1.ADJUSTED_ACTUAL_CONSUMPTION, c1.ACTUAL_CONSUMPTION)*c1.MULTIPLIER*COALESCE(eum1.CONVERT_TO_EU,eum2.CONVERT_TO_EU)),null)) `ACTUAL_CONSUMPTION`, 
        SUM(IF(c1.CONSUMPTION_DATE=mn.MONTH, c1.DAYS_OF_STOCK_OUT, null)) `DAYS_OF_STOCK_OUT`,
        SUM(IF(c1.CONSUMPTION_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH, ABS(c1.FORECAST_CONSUMPTION-IF(@daysOfStockOut,c1.ADJUSTED_ACTUAL_CONSUMPTION,c1.ACTUAL_CONSUMPTION)), null)) SUM_OF_FORECAST_MINUS_ACTUAL,
        SUM(IF(c1.CONSUMPTION_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH, IF(@daysOfStockOut,c1.ADJUSTED_ACTUAL_CONSUMPTION,c1.ACTUAL_CONSUMPTION), null)) SUM_OF_ACTUAL
    FROM mn 
    LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
    LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=p.PROGRAM_ID AND (FIND_IN_SET(pr.REGION_ID, @regionIds) OR @regionIds='')
    LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID
    LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
    LEFT JOIN vw_planning_unit pu ON FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIds) AND @viewBy=1
    LEFT JOIN vw_forecasting_unit fu ON FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIds) AND @viewBy=2
    LEFT JOIN rm_equivalency_unit_mapping eum1 ON COALESCE(fu.FORECASTING_UNIT_ID,pu.FORECASTING_UNIT_ID)=eum1.FORECASTING_UNIT_ID AND eum1.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum1.REALM_ID=rc.REALM_ID AND eum1.PROGRAM_ID=@programId
    LEFT JOIN rm_equivalency_unit_mapping eum2 ON COALESCE(fu.FORECASTING_UNIT_ID,pu.FORECASTING_UNIT_ID)=eum2.FORECASTING_UNIT_ID AND eum2.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum2.REALM_ID=rc.REALM_ID AND eum2.PROGRAM_ID is null
    LEFT JOIN 
        (
        SELECT 
            ct.CONSUMPTION_DATE, ct.`REGION_ID`, ct.PLANNING_UNIT_ID, pu.FORECASTING_UNIT_ID, pu.MULTIPLIER,
            SUM(IF(ct.`ACTUAL_FLAG`=1, ct.`CONSUMPTION_QTY`, null)) `ACTUAL_CONSUMPTION`,
            SUM(IF(ct.`ACTUAL_FLAG`=1, IF(ct.CONSUMPTION_QTY is null, null, IF(@daysOfStockOut=1, DAY(LAST_DAY(ct.CONSUMPTION_DATE))/(DAY(LAST_DAY(ct.CONSUMPTION_DATE))-ct.`DAYS_OF_STOCK_OUT`)*ct.`CONSUMPTION_QTY`, ct.`CONSUMPTION_QTY`)), null)) `ADJUSTED_ACTUAL_CONSUMPTION`,
            SUM(IF(ct.`ACTUAL_FLAG`=0, ct.`CONSUMPTION_QTY`, null)) `FORECAST_CONSUMPTION`,
            SUM(IF(ct.`ACTUAL_FLAG`=1, ct.`DAYS_OF_STOCK_OUT`, null)) `DAYS_OF_STOCK_OUT`
        FROM (
            SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID
        ) tc
        LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.`CONSUMPTION_ID`
        LEFT JOIN rm_consumption_trans ct ON cons.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
        LEFT JOIN rm_planning_unit pu ON ct.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID`
        LEFT JOIN rm_program_planning_unit ppu ON ct.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=@programId
        WHERE
            ct.CONSUMPTION_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @stopDate AND ct.ACTIVE AND ppu.ACTIVE
            AND (@regionIds='' OR FIND_IN_SET(ct.`REGION_ID`, @regionIds) )
            AND ((@viewBy=1 AND FIND_IN_SET(ct.PLANNING_UNIT_ID,@unitIds)) OR (@viewBy=2 AND FIND_IN_SET(pu.`FORECASTING_UNIT_ID`, @unitIds))) GROUP BY ct.CONSUMPTION_DATE, ct.`REGION_ID`, ct.PLANNING_UNIT_ID
    ) AS c1 ON c1.CONSUMPTION_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH AND pr.REGION_ID=c1.REGION_ID AND c1.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    WHERE mn.MONTH BETWEEN @startDate AND @stopDate
    GROUP BY mn.MONTH, pr.PROGRAM_ID, pr.REGION_ID; 
END