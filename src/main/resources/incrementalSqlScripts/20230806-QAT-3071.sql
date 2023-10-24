USE `fasp`;
DROP procedure IF EXISTS `getForecastErrorNew`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getForecastErrorNew`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getForecastErrorNew`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT (10), VAR_VIEW_BY INT(10), VAR_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REGION_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PREVIOUS_MONTHS INT(10), VAR_DAYS_OF_STOCK_OUT TINYINT (1))
BEGIN 
    -- If the view is EquivalencyUnit then all PU or FU selected must be from the same EU
    -- If the view is FU then the PU's selected must be from the same FU
    -- If the view is PU then you cannot multi-select
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
        mn.MONTH, pr.REGION_ID,
        r.REGION_ID, r.LABEL_ID, r.LABEL_EN, r.LABEL_FR, r.LABEL_SP, r.LABEL_PR,
        SUM(IF(mn.`MONTH`=c1.`CONSUMPTION_DATE`, c1.`ADJUSTED_ACTUAL_CONSUMPTION`, null)) `ADJUSTED_ACTUAL_CONSUMPTION`, 
        SUM(IF(mn.`MONTH`=c1.`CONSUMPTION_DATE`, c1.`FORECAST_CONSUMPTION`, null)) `FORECAST_CONSUMPTION`, 
        SUM(c1.`ADJUSTED_ACTUAL_CONSUMPTION`) `TOTAL_ADJUSTED_ACTUAL_CONSUMPTION`, 
        SUM(c1.`FORECAST_CONSUMPTION`) `TOTAL_FORECAST_CONSUMPTION`, 
        SUM(ABS(c1.`ADJUSTED_ACTUAL_CONSUMPTION`-c1.`FORECAST_CONSUMPTION`)) `TOTAL_ABS_DIFF_CONSUMPTION`,
        SUM(IF(mn.`MONTH`=c1.`CONSUMPTION_DATE`, c1.`DAYS_OF_STOCK_OUT`,null)) `DAYS_OF_STOCK_OUT`,
        n.`ADJUSTED_ACTUAL_CONSUMPTION` `NTL_ADJUSTED_ACTUAL_CONSUMPTION`,
        n.`FORECAST_CONSUMPTION` `NTL_FORECAST_CONSUMPTION`,
        n.`TOTAL_ADJUSTED_ACTUAL_CONSUMPTION` `NTL_TOTAL_ADJUSTED_ACTUAL_CONSUMPTION`,
        n.`TOTAL_FORECAST_CONSUMPTION` `NTL_TOTAL_FORECAST_CONSUMPTION`,
        n.`TOTAL_ABS_DIFF_CONSUMPTION` `NTL_TOTAL_ABS_DIFF_CONSUMPTION`
    FROM mn 
    LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
    LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=p.PROGRAM_ID AND (FIND_IN_SET(pr.REGION_ID, @regionIds) OR @regionIds='')
    LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID
    LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
    LEFT JOIN 
        (
        SELECT 
            ct.CONSUMPTION_DATE, ct.`REGION_ID`, pu.MULTIPLIER, eum.CONVERT_TO_EU, SUM(IF(ct.`ACTUAL_FLAG`=1, ct.`DAYS_OF_STOCK_OUT`, null)) `DAYS_OF_STOCK_OUT`,
            SUM(IF(ct.`ACTUAL_FLAG`=1, IF(ct.`CONSUMPTION_QTY` is null, null, IF(@daysOfStockOut=1, DAY(LAST_DAY(ct.`CONSUMPTION_DATE`))/(DAY(LAST_DAY(ct.`CONSUMPTION_DATE`))-ct.`DAYS_OF_STOCK_OUT`)*ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)), ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)))), null)) `ADJUSTED_ACTUAL_CONSUMPTION`,
            SUM(IF(ct.`ACTUAL_FLAG`=0, ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)), null)) `FORECAST_CONSUMPTION`
        FROM (
            SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID
        ) tc
        LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.`CONSUMPTION_ID`
        LEFT JOIN rm_consumption_trans ct ON cons.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
        LEFT JOIN rm_planning_unit pu ON ct.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID`
        LEFT JOIN rm_program_planning_unit ppu ON ct.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=@programId
        LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN rm_equivalency_unit_mapping eum ON pu.FORECASTING_UNIT_ID=eum.FORECASTING_UNIT_ID AND eum.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum.REALM_ID=rc.REALM_ID AND (eum.PROGRAM_ID=@programId OR eum.PROGRAM_ID is NULL)
        WHERE
            ct.CONSUMPTION_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @stopDate AND ct.ACTIVE AND ppu.ACTIVE
            AND (@regionIds='' OR FIND_IN_SET(ct.`REGION_ID`, @regionIds) )
            AND ((@viewBy=1 AND FIND_IN_SET(ct.PLANNING_UNIT_ID,@unitIds)) OR (@viewBy=2 AND FIND_IN_SET(pu.`FORECASTING_UNIT_ID`, @unitIds))) GROUP BY ct.CONSUMPTION_DATE, ct.`REGION_ID`
    ) AS c1 ON c1.CONSUMPTION_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH AND pr.REGION_ID=c1.REGION_ID
    LEFT JOIN (
        SELECT 
            mn.MONTH,
            SUM(IF(mn.`MONTH`=c1.`CONSUMPTION_DATE`, c1.`ADJUSTED_ACTUAL_CONSUMPTION`, null)) `ADJUSTED_ACTUAL_CONSUMPTION`, 
            SUM(IF(mn.`MONTH`=c1.`CONSUMPTION_DATE`, c1.`FORECAST_CONSUMPTION`, null)) `FORECAST_CONSUMPTION`, 
            SUM(c1.`ADJUSTED_ACTUAL_CONSUMPTION`) `TOTAL_ADJUSTED_ACTUAL_CONSUMPTION`, 
            SUM(c1.`FORECAST_CONSUMPTION`) `TOTAL_FORECAST_CONSUMPTION`, 
            SUM(ABS(c1.`ADJUSTED_ACTUAL_CONSUMPTION`-c1.`FORECAST_CONSUMPTION`)) `TOTAL_ABS_DIFF_CONSUMPTION`
        FROM mn 
        LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
        LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=p.PROGRAM_ID AND (FIND_IN_SET(pr.REGION_ID, @regionIds) OR @regionIds='')
        LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN 
            (
            SELECT 
                ct.CONSUMPTION_DATE, ct.`REGION_ID`, pu.MULTIPLIER, eum.CONVERT_TO_EU,
                SUM(IF(ct.`ACTUAL_FLAG`=1, IF(ct.`CONSUMPTION_QTY` is null, null, IF(@daysOfStockOut=1, DAY(LAST_DAY(ct.`CONSUMPTION_DATE`))/(DAY(LAST_DAY(ct.`CONSUMPTION_DATE`))-ct.`DAYS_OF_STOCK_OUT`)*ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)), ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)))), null)) `ADJUSTED_ACTUAL_CONSUMPTION`,
                SUM(IF(ct.`ACTUAL_FLAG`=0, ct.`CONSUMPTION_QTY`*IF(@equivalencyUnitId!=0, pu.MULTIPLIER/eum.CONVERT_TO_EU, IF(@viewBy=1, 1, pu.MULTIPLIER)), null)) `FORECAST_CONSUMPTION`
            FROM (
                SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID
            ) tc
            LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.`CONSUMPTION_ID`
            LEFT JOIN rm_consumption_trans ct ON cons.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
            LEFT JOIN rm_planning_unit pu ON ct.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID`
            LEFT JOIN rm_program_planning_unit ppu ON ct.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=@programId
            LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
            LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
            LEFT JOIN rm_equivalency_unit_mapping eum ON pu.FORECASTING_UNIT_ID=eum.FORECASTING_UNIT_ID AND eum.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum.REALM_ID=rc.REALM_ID AND (eum.PROGRAM_ID=@programId OR eum.PROGRAM_ID is NULL)
            WHERE
                ct.CONSUMPTION_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @stopDate AND ct.ACTIVE AND ppu.ACTIVE
                AND (@regionIds='' OR FIND_IN_SET(ct.`REGION_ID`, @regionIds) )
                AND ((@viewBy=1 AND FIND_IN_SET(ct.PLANNING_UNIT_ID,@unitIds)) OR (@viewBy=2 AND FIND_IN_SET(pu.`FORECASTING_UNIT_ID`, @unitIds))) GROUP BY ct.CONSUMPTION_DATE, ct.`REGION_ID`
        ) AS c1 ON c1.CONSUMPTION_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @previousMonths MONTH) AND mn.MONTH AND pr.REGION_ID=c1.REGION_ID
        WHERE mn.MONTH BETWEEN @startDate AND @stopDate
        GROUP BY mn.MONTH
    ) n ON mn.MONTH=n.MONTH
    WHERE mn.MONTH BETWEEN @startDate AND @stopDate
    GROUP BY mn.MONTH, pr.REGION_ID;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastErrorReport.missingDataNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'`Current month does not contain actual consumption and forecasted consumption` OR `Current month does not contain actual consumption`.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'« Le mois en cours ne contient pas la consommation réelle ni la consommation prévue » OU « Le mois en cours ne contient pas la consommation réelle ».');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'`El mes actual no contiene el consumo real ni el consumo previsto` O `El mes actual no contiene el consumo real`.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'`O mês atual não contém o consumo real e o consumo previsto` OU `O mês atual não contém o consumo real`.');-- pr