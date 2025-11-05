USE `fasp`;
DROP procedure IF EXISTS `stockStatusMatrix`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrix`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusMatrix`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_TRACER_CATEGORY_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
   
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- tracerCategoryId is the list of Tracer Categories that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
    -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
   
    SET @varProgramId = VAR_PROGRAM_ID;
    SET @varVersionId = VAR_VERSION_ID;
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varPlanningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @varTracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @varIncludePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
   
    IF @varVersionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@varProgramId;
    END IF;
   
    DROP TEMPORARY TABLE IF EXISTS tmp_stock_status_matrix;
    CREATE TEMPORARY TABLE tmp_stock_status_matrix
SELECT
            amc.TRANS_DATE, amc.PLANNING_UNIT_ID, fu.TRACER_CATEGORY_ID, pu.MULTIPLIER, IF(ppu.PLAN_BASED_ON=1,ppu.MIN_MONTHS_OF_STOCK,ppu.MIN_QTY) `MIN_MONTHS_OF_STOCK`, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.PLAN_BASED_ON, amc.MAX_STOCK_QTY, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS, amc.MOS, amc.MOS_WPS, ppu.MIN_QTY
FROM rm_supply_plan_amc amc
LEFT JOIN rm_program_planning_unit ppu ON amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.PROGRAM_ID=ppu.PROGRAM_ID
LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  
LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  
WHERE
            amc.PROGRAM_ID=@varProgramId
            AND amc.VERSION_ID=@varVersionId
            AND amc.TRANS_DATE between @varStartDate AND @varStopDate
            AND ppu.ACTIVE AND pu.ACTIVE
            AND (@varTracerCAtegoryIds='' OR FIND_IN_SET(fu.TRACER_CATEGORY_ID, @varTracerCategoryIds))
            AND (@varPlanningUnitIds='' OR FIND_IN_SET(amc.PLANNING_UNIT_ID, @varPlanningUnitIds));

    SELECT
YEAR(mn.MONTH) YR,    
pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,    
fu.TRACER_CATEGORY_ID, pu.MULTIPLIER,    
u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP `UNIT_LABEL_SP`, u.LABEL_PR `UNIT_LABEL_PR`,    
pu.MULTIPLIER,    
IF(ssm.PLAN_BASED_ON=1,ssm.MIN_MONTHS_OF_STOCK,ssm.MIN_QTY) `MIN_MONTHS_OF_STOCK`,    
ssm.REORDER_FREQUENCY_IN_MONTHS,    ssm.PLAN_BASED_ON, ssm.MAX_STOCK_QTY, AVG(ssm.MAX_STOCK_QTY) `MAX_STOCK_QTY`,
SUM(IF(MONTH(mn.MONTH)=1, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Jan Stock`,    
SUM(IF(MONTH(mn.MONTH)=2, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Feb Stock`,    
SUM(IF(MONTH(mn.MONTH)=3, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Mar Stock`,    
SUM(IF(MONTH(mn.MONTH)=4, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Apr Stock`,    
SUM(IF(MONTH(mn.MONTH)=5, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `May Stock`,    
SUM(IF(MONTH(mn.MONTH)=6, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Jun Stock`,    
SUM(IF(MONTH(mn.MONTH)=7, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Jul Stock`,    
SUM(IF(MONTH(mn.MONTH)=8, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Aug Stock`,    
SUM(IF(MONTH(mn.MONTH)=9, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Sep Stock`,    
SUM(IF(MONTH(mn.MONTH)=10, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Oct Stock`,    
SUM(IF(MONTH(mn.MONTH)=11, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Nov Stock`,    
SUM(IF(MONTH(mn.MONTH)=12, IF(@varIncludePlannedShipments, ssm.CLOSING_BALANCE, ssm.CLOSING_BALANCE_WPS),null)) `Dec Stock`,    
   
SUM(IF(MONTH(mn.MONTH)=1, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Jan`,    
SUM(IF(MONTH(mn.MONTH)=2, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Feb`,    
SUM(IF(MONTH(mn.MONTH)=3, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Mar`,    
SUM(IF(MONTH(mn.MONTH)=4, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Apr`,    
SUM(IF(MONTH(mn.MONTH)=5, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `May`,    
SUM(IF(MONTH(mn.MONTH)=6, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Jun`,    
SUM(IF(MONTH(mn.MONTH)=7, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Jul`,    
SUM(IF(MONTH(mn.MONTH)=8, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Aug`,    
SUM(IF(MONTH(mn.MONTH)=9, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Sep`,    
SUM(IF(MONTH(mn.MONTH)=10, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Oct`,    
SUM(IF(MONTH(mn.MONTH)=11, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Nov`,    
SUM(IF(MONTH(mn.MONTH)=12, IF(@varIncludePlannedShipments, IF(ssm.PLAN_BASED_ON=1,ssm.MOS,ssm.MAX_STOCK_QTY), IF(ssm.PLAN_BASED_ON=1,ssm.MOS_WPS,ssm.MAX_STOCK_QTY)),null)) `Dec`
    FROM mn
LEFT JOIN tmp_stock_status_matrix ssm ON mn.MONTH=ssm.TRANS_DATE
LEFT JOIN vw_planning_unit pu ON ssm.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  
LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  
LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID
    WHERE mn.MONTH BETWEEN @varStartDate AND @varStopDate
    GROUP BY ssm.PLANNING_UNIT_ID, YEAR(mn.MONTH);
   
END$$

DELIMITER ;
;
