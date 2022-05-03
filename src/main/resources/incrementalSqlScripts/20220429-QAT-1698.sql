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
 
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
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

    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @tracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    YEAR(mn.MONTH) YR, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    fu.TRACER_CATEGORY_ID, pu.MULTIPLIER, ");
    SET @sqlString = CONCAT(@sqlString, "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP `UNIT_LABEL_SP`, u.LABEL_PR `UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=1, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Jan`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=2, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Feb`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=3, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Mar`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=4, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Apr`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=5, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `May`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=6, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Jun`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=7, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Jul`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=8, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Aug`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=9, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Sep`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=10, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Oct`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=11, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Nov`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=12, IF(@includePlannedShipments, amc.MOS, amc.MOS_WPS),null)) `Dec` ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc amc ON ppu.PROGRAM_ID=amc.PROGRAM_ID AND amc.VERSION_ID=@versionId AND mn.MONTH=amc.TRANS_DATE AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    mn.MONTH BETWEEN @startDate and @stopDate AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(@planningUnitIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "    AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, @planningUnitIds) ");
    END IF;
    IF LENGTH(@tracerCategoryIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "    AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, @tracerCategoryIds) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY ppu.PLANNING_UNIT_ID, YEAR(mn.MONTH)");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;
;


