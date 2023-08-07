USE `fasp`;
DROP procedure IF EXISTS `getForecastError`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getForecastError`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getForecastError`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT (10), VAR_VIEW_BY INT(10), VAR_UNIT_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REGION_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT(10))
BEGIN
    SET @programId = VAR_PROGRAM_ID; 
    SET @versionId = VAR_VERSION_ID;
    SET @unitId= VAR_UNIT_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @viewBy = VAR_VIEW_BY;
    SET @regionIds = VAR_REGION_IDS;
    SET @equivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID;
    
    IF @versionId = -1 THEN 
        SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;

    SELECT 
 		r.REGION_ID, r.LABEL_ID, r.LABEL_EN, r.LABEL_FR, r.LABEL_SP, r.LABEL_PR,
 		ct.CONSUMPTION_DATE, 
 		SUM(IF(ct.ACTUAL_FLAG=0, ct.CONSUMPTION_QTY, null)) `FORECAST_QTY`, 
 		SUM(IF(ct.ACTUAL_FLAG=1, ct.CONSUMPTION_QTY, null)) `ACTUAL_QTY`, SUM(IF(ct.ACTUAL_FLAG=1,ifnull(ct.DAYS_OF_STOCK_OUT,0), null)) `DAYS_OF_STOCK_OUT`,
 		COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU) `CONVERT_TO_EU`
 	FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID) tc 
 		LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
 		LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
 		LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
 		LEFT JOIN rm_program p ON p.PROGRAM_ID=@programId
 		LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
		LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
		LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
		LEFT JOIN rm_equivalency_unit_mapping eum1 ON fu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum1.REALM_ID=rc.REALM_ID AND eum1.PROGRAM_ID=@programId
		LEFT JOIN rm_equivalency_unit_mapping eum2 ON fu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum2.REALM_ID=rc.REALM_ID AND eum2.PROGRAM_ID is null
	WHERE ct.CONSUMPTION_DATE BETWEEN @startDate and @stopDate AND (@regionIds='' OR FIND_IN_SET(ct.REGION_ID,@regionIds)) AND ((@viewBy=1 AND ct.PLANNING_UNIT_ID=@unitId) OR (@viewBy=2 AND pu.FORECASTING_UNIT_ID=@unitId))
	GROUP BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE;
    
END$$

DELIMITER ;
;


