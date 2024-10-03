USE `fasp`;
DROP procedure IF EXISTS `stockStatusReportVertical`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVertical`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusReportVertical`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_REPORTING_UNIT_ID INT(10), VAR_VIEW_BY INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16 Individual
    -- %%%%%%%%%%%%%%%%%%%%%
    
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varProgramId = VAR_PROGRAM_ID;
    SET @varReportingUnitId = VAR_REPORTING_UNIT_ID;
    SET @varViewBy = VAR_VIEW_BY; -- 1 for PU, 2 for ARU
    SET @varEquivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID; -- Non zero if the report is to be showing in terms of equivalencyUnitId

    DROP TEMPORARY TABLE IF EXISTS `tmp_supply_plan_amc`;
    CREATE TEMPORARY TABLE `tmp_supply_plan_amc` (
      `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT,
      `PROGRAM_ID` int unsigned NOT NULL,
      `PLANNING_UNIT_ID` int unsigned NOT NULL,
      `TRANS_DATE` date NOT NULL,
      `AMC` decimal(24,8) DEFAULT NULL,
      `AMC_COUNT` int DEFAULT NULL,
      `OPENING_BALANCE` decimal(24,8) DEFAULT NULL,
      `SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
      `FORECASTED_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `ACTUAL_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `FINAL_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `ACTUAL` tinyint(1) DEFAULT NULL,
      `ADJUSTMENT_QTY` decimal(24,8) DEFAULT NULL,
      `STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `REGION_COUNT` int unsigned NOT NULL,
      `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL,
      `EXPIRED_STOCK` decimal(24,8) DEFAULT NULL,
      `CLOSING_BALANCE` decimal(24,8) DEFAULT NULL,
      `UNMET_DEMAND` decimal(24,8) DEFAULT NULL,
      `NATIONAL_ADJUSTMENT` decimal(24,8) DEFAULT NULL,
      `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`),
      KEY `idx_rm_supply_plan_amc_transDate` (`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

    
    IF @varViewBy = 1 THEN
        SET @varPlanningUnitId = @varReportingUnitId;
    ELSEIF @varViewBy = 2 THEN  
        SELECT rcpu.PLANNING_UNIT_ID INTO @varPlanningUnitId FROM rm_realm_country_planning_unit rcpu WHERE @varViewBy=2 AND rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=@varReportingUnitId;
    END IF;

    IF @varEquivalencyUnitId != 0 THEN
         SELECT eu.`EQUIVALENCY_UNIT_ID`, eu.`LABEL_EN`, eu.`LABEL_FR`, eu.`LABEL_SP`, eu.`LABEL_PR`, 1 INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_equivalency_unit eu WHERE eu.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId;
     ELSEIF @varViewBy = 1 THEN
         SELECT pu.`PLANNING_UNIT_ID`, pu.`LABEL_EN`, pu.`LABEL_FR`, pu.`LABEL_SP`, pu.`LABEL_PR`, 1 INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_planning_unit pu WHERE pu.PLANNING_UNIT_ID=@varReportingUnitId;
     ELSEIF @varViewBy = 2 THEN
         SELECT rcpu.`REALM_COUNTRY_PLANNING_UNIT_ID`, rcpu.`LABEL_EN`, rcpu.`LABEL_FR`, rcpu.`LABEL_SP`, rcpu.`LABEL_PR`, IF (rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2, 1/rcpu.CONVERSION_NUMBER, 0)) INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_realm_country_planning_unit rcpu WHERE rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=@varReportingUnitId;
     END IF;

    INSERT INTO tmp_supply_plan_amc 
    SELECT 
        null,
        sma.`PROGRAM_ID`, 
        sma.`PLANNING_UNIT_ID`, 
        sma.`TRANS_DATE`,
        sma.`AMC`,
        sma.`AMC_COUNT`, 
        sma.`OPENING_BALANCE`,
        sma.`SHIPMENT_QTY`,
        sma.`FORECASTED_CONSUMPTION_QTY`,
        sma.`ACTUAL_CONSUMPTION_QTY`,
        IF(sma.`ACTUAL`, sma.`ACTUAL_CONSUMPTION_QTY`, sma.`FORECASTED_CONSUMPTION_QTY`) `FINAL_CONSUMPTION_QTY`,
        sma.`ACTUAL`,
        sma.`ADJUSTMENT_MULTIPLIED_QTY` `ADJUSTMENT_QTY`,
        sma.`STOCK_MULTIPLIED_QTY` `STOCK_QTY`,
        sma.`REGION_COUNT`,
        sma.`REGION_COUNT_FOR_STOCK`,
        sma.`EXPIRED_STOCK`,
        sma.`CLOSING_BALANCE`,
        sma.`UNMET_DEMAND`,
        sma.`NATIONAL_ADJUSTMENT`,
        sma.`MIN_STOCK_MOS`,
        sma.`MIN_STOCK_QTY`,
        sma.`MAX_STOCK_MOS`,
        sma.`MAX_STOCK_QTY`
    FROM vw_program p 
    LEFT JOIN rm_supply_plan_amc sma  ON p.PROGRAM_ID=sma.PROGRAM_ID 
    WHERE 
        p.PROGRAM_ID=@varProgramId
        AND sma.VERSION_ID = p.CURRENT_VERSION_ID 
        AND sma.PLANNING_UNIT_ID=@varPlanningUnitId
        AND sma.TRANS_DATE BETWEEN @varStartDate AND @varStopDate;

    SELECT 
        @ruId `RU_ID`, 0 `RU_LABEL_ID`, @ruLabelEn `RU_LABEL_EN`, @ruLabelFr `RU_LABEL_FR`, @ruLabelSp `RU_LABEL_SP`, @ruLabelPr `RU_LABEL_PR`, 
        s3.`TRANS_DATE`, s3.`PPU_NOTES`, 
        s3.`FINAL_OPENING_BALANCE`,
        s3.`ACTUAL_CONSUMPTION_QTY`, s3.`FORECASTED_CONSUMPTION_QTY`, 
        s3.`FINAL_CONSUMPTION_QTY`,
        s3.`ACTUAL`,
        s3.`SQTY` ,
        s3.`ADJUSTMENT`,
        s3.`NATIONAL_ADJUSTMENT`,
        s3.`EXPIRED_STOCK`,
        s3.`FINAL_CLOSING_BALANCE`,
        s3.`AMC`, s3.`UNMET_DEMAND`, s3.`REGION_COUNT`, s3.`REGION_COUNT_FOR_STOCK`,
        s3.`FINAL_CLOSING_BALANCE`/s3.`AMC` `MOS`, 
        sh.`SHIPMENT_ID`, sh.`SHIPMENT_QTY`, sh.`EDD`, sh.`NOTES`, sh.`ORDER_NO`, sh.`PRIME_LINE_NO`, sl.`RO_NO`, sl.`RO_PRIME_LINE_NO`,
        p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
        fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.`LABEL_ID` `FUNDING_SOURCE_LABEL_ID`, fs.`LABEL_EN` `FUNDING_SOURCE_LABEL_EN`, fs.`LABEL_FR` `FUNDING_SOURCE_LABEL_FR`, fs.`LABEL_SP` `FUNDING_SOURCE_LABEL_SP`, fs.`LABEL_PR` `FUNDING_SOURCE_LABEL_PR`, 
        pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.`LABEL_FR` `PROCUREMENT_AGENT_LABEL_FR`, pa.`LABEL_SP` `PROCUREMENT_AGENT_LABEL_SP`, pa.`LABEL_PR` `PROCUREMENT_AGENT_LABEL_PR`, 
        ds.`DATA_SOURCE_ID`, ds.`LABEL_ID` `DATA_SOURCE_LABEL_ID`, ds.`LABEL_EN` `DATA_SOURCE_LABEL_EN`, ds.`LABEL_FR` `DATA_SOURCE_LABEL_FR`, ds.`LABEL_SP` `DATA_SOURCE_LABEL_SP`, ds.`LABEL_PR` `DATA_SOURCE_LABEL_PR`, 
        ss.`SHIPMENT_STATUS_ID`, ss.`LABEL_ID` `SHIPMENT_STATUS_LABEL_ID`, ss.`LABEL_EN` `SHIPMENT_STATUS_LABEL_EN`, ss.`LABEL_FR` `SHIPMENT_STATUS_LABEL_FR`, ss.`LABEL_SP` `SHIPMENT_STATUS_LABEL_SP`, ss.`LABEL_PR` `SHIPMENT_STATUS_LABEL_PR`,
        s3.`MIN_STOCK_MOS`, s3.`MAX_STOCK_MOS`, s3.`MIN_STOCK_QTY`, s3.`MAX_STOCK_QTY`, 
        ppu.`REORDER_FREQUENCY_IN_MONTHS`, ppu.`MIN_MONTHS_OF_STOCK`, ppu.`LOCAL_PROCUREMENT_LEAD_TIME`, ppu.`SHELF_LIFE`, ppu.`MONTHS_IN_FUTURE_FOR_AMC`, ppu.`MONTHS_IN_PAST_FOR_AMC`, ppu.`PLAN_BASED_ON`, ppu.`MIN_QTY`, ppu.`DISTRIBUTION_LEAD_TIME`, ppu.`NOTES`
    FROM (
        SELECT 
            s2.`TRANS_DATE`, s2.`PPU_NOTES`, 
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_OPENING_BALANCE`, s2.`FINAL_OPENING_BALANCE`*@varRcpuMultiplier) `FINAL_OPENING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ACTUAL_CONSUMPTION_QTY`, s2.`ACTUAL_CONSUMPTION_QTY`*@varRcpuMultiplier) `ACTUAL_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FORECASTED_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`*@varRcpuMultiplier) `FORECASTED_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CONSUMPTION_QTY`, s2.`FINAL_CONSUMPTION_QTY`*@varRcpuMultiplier) `FINAL_CONSUMPTION_QTY`,
            s2.`ACTUAL`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`SQTY`, s2.`SQTY`*@varRcpuMultiplier) `SQTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ADJUSTMENT`, s2.`ADJUSTMENT`*@varRcpuMultiplier) `ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`NATIONAL_ADJUSTMENT`, s2.`NATIONAL_ADJUSTMENT`*@varRcpuMultiplier) `NATIONAL_ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`EXPIRED_STOCK`, s2.`EXPIRED_STOCK`*@varRcpuMultiplier) `EXPIRED_STOCK`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_CLOSING_BALANCE`*@varRcpuMultiplier) `FINAL_CLOSING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`AMC`, s2.`AMC`*@varRcpuMultiplier) `AMC`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`UNMET_DEMAND`, s2.`UNMET_DEMAND`*@varRcpuMultiplier) `UNMET_DEMAND`,
            s2.`REGION_COUNT`, s2.`REGION_COUNT_FOR_STOCK`, s2.`PLAN_BASED_ON`,
            s2.`MIN_STOCK_MOS`, s2.`MAX_STOCK_MOS`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`MIN_STOCK_QTY`, s2.`MIN_STOCK_QTY`*@varRcpuMultiplier) `MIN_STOCK_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`MAX_STOCK_QTY`, s2.`MAX_STOCK_QTY`*@varRcpuMultiplier) `MAX_STOCK_QTY`
        FROM (
            SELECT 
                mn.`MONTH` `TRANS_DATE`, ppu.`NOTES` `PPU_NOTES`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`OPENING_BALANCE`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`OPENING_BALANCE`)) `FINAL_OPENING_BALANCE`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ACTUAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ACTUAL_CONSUMPTION_QTY`)) `ACTUAL_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FORECASTED_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FORECASTED_CONSUMPTION_QTY`)) `FORECASTED_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FINAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FINAL_CONSUMPTION_QTY`)) `FINAL_CONSUMPTION_QTY`,
                BIT_AND(sma.`ACTUAL`) `ACTUAL`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`SHIPMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`SHIPMENT_QTY`)) `SQTY`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ADJUSTMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ADJUSTMENT_QTY`)) `ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`NATIONAL_ADJUSTMENT`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`NATIONAL_ADJUSTMENT`)) `NATIONAL_ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`EXPIRED_STOCK`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`EXPIRED_STOCK`)) `EXPIRED_STOCK`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`CLOSING_BALANCE`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`CLOSING_BALANCE`)) `FINAL_CLOSING_BALANCE`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`AMC`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`AMC`)) `AMC`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`UNMET_DEMAND`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`UNMET_DEMAND`)) `UNMET_DEMAND`, 
                SUM(sma.`REGION_COUNT`) `REGION_COUNT`, SUM(sma.`REGION_COUNT_FOR_STOCK`) `REGION_COUNT_FOR_STOCK`, IF(BIT_AND(IF(ppu.PLAN_BASED_ON=2,true,false)),2,1) `PLAN_BASED_ON`,
                sma.`MIN_STOCK_MOS`, sma.`MAX_STOCK_MOS`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`MIN_STOCK_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`MIN_STOCK_QTY`)) `MIN_STOCK_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`MAX_STOCK_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`MAX_STOCK_QTY`)) `MAX_STOCK_QTY`
            FROM mn 
            LEFT JOIN tmp_supply_plan_amc sma ON mn.`MONTH`=sma.`TRANS_DATE` 
            LEFT JOIN rm_planning_unit pu ON sma.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
            LEFT JOIN rm_program_planning_unit ppu ON sma.PROGRAM_ID=ppu.PROGRAM_ID AND sma.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
            LEFT JOIN rm_equivalency_unit eu1 ON eu1.PROGRAM_ID=sma.PROGRAM_ID AND eu1.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId 
            LEFT JOIN rm_equivalency_unit_mapping eum1 ON eu1.EQUIVALENCY_UNIT_ID=eum1.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eu1.PROGRAM_ID=eum1.PROGRAM_ID
            LEFT JOIN rm_equivalency_unit eu2 ON eu2.PROGRAM_ID is null AND eu2.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
            LEFT JOIN rm_equivalency_unit_mapping eum2 ON eu2.EQUIVALENCY_UNIT_ID=eum2.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID=sma.PROGRAM_ID
            LEFT JOIN rm_equivalency_unit eu3 ON eu3.PROGRAM_ID is null AND eu3.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
            LEFT JOIN rm_equivalency_unit_mapping eum3 ON eu3.EQUIVALENCY_UNIT_ID=eum3.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum3.FORECASTING_UNIT_ID AND eum3.PROGRAM_ID is null

            WHERE mn.`MONTH` BETWEEN @varStartDate AND @varStopDate 
            GROUP BY mn.`MONTH`
        ) AS s2
    ) s3
    LEFT JOIN 
        (
        SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, s.PROGRAM_ID, st.PLANNING_UNIT_ID, IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, st.SHIPMENT_QTY, 
        IF(@varEquivalencyUnitId != 0, st.`SHIPMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), st.SHIPMENT_QTY*@varRcpuMultiplier)
        ) `SHIPMENT_QTY` , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID, st.NOTES, st.ORDER_NO, st.PRIME_LINE_NO, st.DATA_SOURCE_ID
        FROM 
            (
            SELECT s.SHIPMENT_ID, p.PROGRAM_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM vw_program p LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@varProgramId AND st.PLANNING_UNIT_ID=@varPlanningUnitId AND st.VERSION_ID<=p.CURRENT_VERSION_ID AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN vw_planning_unit pu ON pu.PLANNING_UNIT_ID=@varPlanningUnitId
        LEFT JOIN rm_equivalency_unit eu1 ON eu1.PROGRAM_ID=s.PROGRAM_ID AND eu1.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId 
        LEFT JOIN rm_equivalency_unit_mapping eum1 ON eu1.EQUIVALENCY_UNIT_ID=eum1.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eu1.PROGRAM_ID=eum1.PROGRAM_ID
        LEFT JOIN rm_equivalency_unit eu2 ON eu2.PROGRAM_ID is null AND eu2.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
        LEFT JOIN rm_equivalency_unit_mapping eum2 ON eu2.EQUIVALENCY_UNIT_ID=eum2.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID=s.PROGRAM_ID
        LEFT JOIN rm_equivalency_unit eu3 ON eu3.PROGRAM_ID is null AND eu3.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
        LEFT JOIN rm_equivalency_unit_mapping eum3 ON eu3.EQUIVALENCY_UNIT_ID=eum3.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum3.FORECASTING_UNIT_ID AND eum3.PROGRAM_ID is null
        WHERE 
            st.ACTIVE 
            AND st.SHIPMENT_STATUS_ID != 8 
            AND st.ACCOUNT_FLAG
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @varStartDate AND @varStopDate 
    ) sh ON LEFT(s3.TRANS_DATE,7)=LEFT(sh.EDD,7)
    LEFT JOIN rm_shipment_linking sl ON sh.SHIPMENT_ID=sl.CHILD_SHIPMENT_ID AND sl.ACTIVE
    LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_data_source ds ON sh.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
    LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
    LEFT JOIN vw_program p ON p.PROGRAM_ID=@varProgramId
    LEFT JOIN vw_planning_unit pu ON pu.PLANNING_UNIT_ID=@varPlanningUnitId
    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@varProgramId AND ppu.PLANNING_UNIT_ID=@varPlanningUnitId;
END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `stockStatusReportVerticalAggregated`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVerticalAggregated`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusReportVerticalAggregated`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_IDS TEXT, VAR_REPORTING_UNIT_IDS TEXT, VAR_VIEW_BY INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16 Aggregated
    -- %%%%%%%%%%%%%%%%%%%%%
    
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varReportingUnitIds = VAR_REPORTING_UNIT_IDS;
    SET @varViewBy = VAR_VIEW_BY; -- 1 for PU, 2 for ARU
    SET @varEquivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID; -- Non zero if the report is to be showing in terms of equivalencyUnitId

    DROP TEMPORARY TABLE IF EXISTS `tmp_supply_plan_amc`;
    CREATE TEMPORARY TABLE `tmp_supply_plan_amc` (
      `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT,
      `PROGRAM_ID` int unsigned NOT NULL,
      `PLANNING_UNIT_ID` int unsigned NOT NULL,
      `TRANS_DATE` date NOT NULL,
      `AMC` decimal(24,8) DEFAULT NULL,
      `AMC_COUNT` int DEFAULT NULL,
      `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `OPENING_BALANCE` decimal(24,8) DEFAULT NULL,
      `SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
      `FORECASTED_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `ACTUAL_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `FINAL_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
      `ACTUAL` tinyint(1) DEFAULT NULL,
      `ADJUSTMENT_QTY` decimal(24,8) DEFAULT NULL,
      `STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `REGION_COUNT` int unsigned NOT NULL,
      `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL,
      `EXPIRED_STOCK` decimal(24,8) DEFAULT NULL,
      `CLOSING_BALANCE` decimal(24,8) DEFAULT NULL,
      `UNMET_DEMAND` decimal(24,8) DEFAULT NULL,
      `NATIONAL_ADJUSTMENT` decimal(24,8) DEFAULT NULL,
      PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`),
      KEY `idx_rm_supply_plan_amc_transDate` (`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

    
    IF @varViewBy = 1 THEN
        SET @varPlanningUnitIds = @varReportingUnitIds;
    ELSEIF @varViewBy = 2 THEN  
        SELECT GROUP_CONCAT(DISTINCT rcpu.PLANNING_UNIT_ID) INTO @varPlanningUnitIds FROM rm_realm_country_planning_unit rcpu WHERE (@varViewBy=2 AND FIND_IN_SET(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, @varReportingUnitIds));
    END IF;

    IF @varEquivalencyUnitId != 0 THEN
         SELECT eu.`EQUIVALENCY_UNIT_ID`, eu.`LABEL_EN`, eu.`LABEL_FR`, eu.`LABEL_SP`, eu.`LABEL_PR`, 1 INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_equivalency_unit eu WHERE eu.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId;
     ELSEIF @varViewBy = 1 THEN
         SELECT pu.`PLANNING_UNIT_ID`, pu.`LABEL_EN`, pu.`LABEL_FR`, pu.`LABEL_SP`, pu.`LABEL_PR`, 1 INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_planning_unit pu WHERE pu.PLANNING_UNIT_ID=@varReportingUnitIds;
     ELSEIF @varViewBy = 2 THEN
         SELECT rcpu.`REALM_COUNTRY_PLANNING_UNIT_ID`, rcpu.`LABEL_EN`, rcpu.`LABEL_FR`, rcpu.`LABEL_SP`, rcpu.`LABEL_PR`, IF (rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2, 1/rcpu.CONVERSION_NUMBER, 0)) INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_realm_country_planning_unit rcpu WHERE rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=@varReportingUnitIds;
     END IF;

    INSERT INTO tmp_supply_plan_amc 
    SELECT 
        null,
        sma.`PROGRAM_ID`, 
        sma.`PLANNING_UNIT_ID`, 
        sma.`TRANS_DATE`,
        sma.`AMC`,
        sma.`AMC_COUNT`, 
        sma.`MIN_STOCK_QTY`,
        sma.`MIN_STOCK_MOS`,
        sma.`MAX_STOCK_QTY`,
        sma.`MAX_STOCK_MOS`,
        sma.`OPENING_BALANCE`,
        sma.`SHIPMENT_QTY`,
        sma.`FORECASTED_CONSUMPTION_QTY`,
        sma.`ACTUAL_CONSUMPTION_QTY`,
        IF(sma.`ACTUAL`, sma.`ACTUAL_CONSUMPTION_QTY`, sma.`FORECASTED_CONSUMPTION_QTY`) `FINAL_CONSUMPTION_QTY`,
        sma.`ACTUAL`,
        sma.`ADJUSTMENT_MULTIPLIED_QTY` `ADJUSTMENT_QTY`,
        sma.`STOCK_MULTIPLIED_QTY` `STOCK_QTY`,
        sma.`REGION_COUNT`,
        sma.`REGION_COUNT_FOR_STOCK`,
        sma.`EXPIRED_STOCK`,
        sma.`CLOSING_BALANCE`,
        sma.`UNMET_DEMAND`,
        sma.`NATIONAL_ADJUSTMENT`
    FROM vw_program p 
    LEFT JOIN rm_supply_plan_amc sma  ON p.PROGRAM_ID=sma.PROGRAM_ID 
    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=p.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=sma.PLANNING_UNIT_ID 
    WHERE 
        FIND_IN_SET(p.PROGRAM_ID, @varProgramIds) 
        AND sma.VERSION_ID = p.CURRENT_VERSION_ID 
        AND FIND_IN_SET(sma.PLANNING_UNIT_ID, @varPlanningUnitIds) 
        AND sma.TRANS_DATE BETWEEN @varStartDate AND @varStopDate AND ppu.ACTIVE;

    SELECT 
        @ruId `RU_ID`, 0 `RU_LABEL_ID`, @ruLabelEn `RU_LABEL_EN`, @ruLabelFr `RU_LABEL_FR`, @ruLabelSp `RU_LABEL_SP`, @ruLabelPr `RU_LABEL_PR`, 
        s3.`TRANS_DATE`, s3.`PPU_NOTES`,
        s3.`FINAL_OPENING_BALANCE`,
        s3.`ACTUAL_CONSUMPTION_QTY`, s3.`FORECASTED_CONSUMPTION_QTY`, 
        s3.`FINAL_CONSUMPTION_QTY`,
        s3.`ACTUAL`,
        s3.`SQTY` ,
        s3.`ADJUSTMENT`,
        s3.`NATIONAL_ADJUSTMENT`,
        s3.`EXPIRED_STOCK`,
        s3.`FINAL_CLOSING_BALANCE`,
        s3.`AMC`, s3.`UNMET_DEMAND`, s3.`REGION_COUNT`, s3.`REGION_COUNT_FOR_STOCK`,
        s3.`FINAL_CLOSING_BALANCE`/s3.`AMC` `MOS`, s3.`PLAN_BASED_ON`, s3.`MIN_STOCK_QTY`, s3.`MIN_STOCK_MOS`, s3.`MAX_STOCK_QTY`, s3.`MAX_STOCK_MOS`,
        sh.`SHIPMENT_ID`, sh.`SHIPMENT_QTY`, sh.`EDD`, sh.`NOTES`, sh.`ORDER_NO`, sh.`PRIME_LINE_NO`, sl.`RO_NO`, sl.`RO_PRIME_LINE_NO`,
        p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
        fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.`LABEL_ID` `FUNDING_SOURCE_LABEL_ID`, fs.`LABEL_EN` `FUNDING_SOURCE_LABEL_EN`, fs.`LABEL_FR` `FUNDING_SOURCE_LABEL_FR`, fs.`LABEL_SP` `FUNDING_SOURCE_LABEL_SP`, fs.`LABEL_PR` `FUNDING_SOURCE_LABEL_PR`, 
        pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.`LABEL_FR` `PROCUREMENT_AGENT_LABEL_FR`, pa.`LABEL_SP` `PROCUREMENT_AGENT_LABEL_SP`, pa.`LABEL_PR` `PROCUREMENT_AGENT_LABEL_PR`, 
        ds.`DATA_SOURCE_ID`, ds.`LABEL_ID` `DATA_SOURCE_LABEL_ID`, ds.`LABEL_EN` `DATA_SOURCE_LABEL_EN`, ds.`LABEL_FR` `DATA_SOURCE_LABEL_FR`, ds.`LABEL_SP` `DATA_SOURCE_LABEL_SP`, ds.`LABEL_PR` `DATA_SOURCE_LABEL_PR`, 
        ss.`SHIPMENT_STATUS_ID`, ss.`LABEL_ID` `SHIPMENT_STATUS_LABEL_ID`, ss.`LABEL_EN` `SHIPMENT_STATUS_LABEL_EN`, ss.`LABEL_FR` `SHIPMENT_STATUS_LABEL_FR`, ss.`LABEL_SP` `SHIPMENT_STATUS_LABEL_SP`, ss.`LABEL_PR` `SHIPMENT_STATUS_LABEL_PR`
    FROM (
        SELECT 
            s2.`TRANS_DATE`, s2.`PPU_NOTES`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_OPENING_BALANCE`, s2.`FINAL_OPENING_BALANCE`*@varRcpuMultiplier) `FINAL_OPENING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ACTUAL_CONSUMPTION_QTY`, s2.`ACTUAL_CONSUMPTION_QTY`*@varRcpuMultiplier) `ACTUAL_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FORECASTED_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`*@varRcpuMultiplier) `FORECASTED_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CONSUMPTION_QTY`, s2.`FINAL_CONSUMPTION_QTY`*@varRcpuMultiplier) `FINAL_CONSUMPTION_QTY`,
            s2.`ACTUAL`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`SQTY`, s2.`SQTY`*@varRcpuMultiplier) `SQTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ADJUSTMENT`, s2.`ADJUSTMENT`*@varRcpuMultiplier) `ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`NATIONAL_ADJUSTMENT`, s2.`NATIONAL_ADJUSTMENT`*@varRcpuMultiplier) `NATIONAL_ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`EXPIRED_STOCK`, s2.`EXPIRED_STOCK`*@varRcpuMultiplier) `EXPIRED_STOCK`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_CLOSING_BALANCE`*@varRcpuMultiplier) `FINAL_CLOSING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`AMC`, s2.`AMC`*@varRcpuMultiplier) `AMC`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`UNMET_DEMAND`, s2.`UNMET_DEMAND`*@varRcpuMultiplier) `UNMET_DEMAND`,
            s2.`REGION_COUNT`, s2.`REGION_COUNT_FOR_STOCK`, s2.`PLAN_BASED_ON`, 
            s2.`MIN_STOCK_QTY`, s2.`MIN_STOCK_MOS`, s2.`MAX_STOCK_QTY`, s2.`MAX_STOCK_MOS`
        FROM (
            SELECT 
                mn.`MONTH` `TRANS_DATE`, GROUP_CONCAT(IF(ppu.`NOTES` IS NOT NULL, CONCAT(p.PROGRAM_CODE, ":", ppu.`NOTES`), null) separator "|") `PPU_NOTES`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`OPENING_BALANCE`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`OPENING_BALANCE`)) `FINAL_OPENING_BALANCE`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ACTUAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ACTUAL_CONSUMPTION_QTY`)) `ACTUAL_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FORECASTED_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FORECASTED_CONSUMPTION_QTY`)) `FORECASTED_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FINAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FINAL_CONSUMPTION_QTY`)) `FINAL_CONSUMPTION_QTY`,
                BIT_AND(sma.`ACTUAL`) `ACTUAL`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`SHIPMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`SHIPMENT_QTY`)) `SQTY`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ADJUSTMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ADJUSTMENT_QTY`)) `ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`NATIONAL_ADJUSTMENT`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`NATIONAL_ADJUSTMENT`)) `NATIONAL_ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`EXPIRED_STOCK`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`EXPIRED_STOCK`)) `EXPIRED_STOCK`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`CLOSING_BALANCE`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`CLOSING_BALANCE`)) `FINAL_CLOSING_BALANCE`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`AMC`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`AMC`)) `AMC`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`UNMET_DEMAND`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`UNMET_DEMAND`)) `UNMET_DEMAND`, 
                SUM(sma.`REGION_COUNT`) `REGION_COUNT`, SUM(sma.`REGION_COUNT_FOR_STOCK`) `REGION_COUNT_FOR_STOCK`, IF(BIT_AND(IF(ppu.PLAN_BASED_ON=2,true,false)),2,1) `PLAN_BASED_ON`, 
                AVG(sma.`MIN_STOCK_QTY`) `MIN_STOCK_QTY`, AVG(sma.`MIN_STOCK_MOS`) `MIN_STOCK_MOS`, AVG(sma.`MAX_STOCK_QTY`) `MAX_STOCK_QTY`, AVG(sma.`MAX_STOCK_MOS`) `MAX_STOCK_MOS`
            FROM mn 
            LEFT JOIN tmp_supply_plan_amc sma ON mn.`MONTH`=sma.`TRANS_DATE` 
            LEFT JOIN vw_program p ON sma.PROGRAM_ID=p.PROGRAM_ID
            LEFT JOIN rm_planning_unit pu ON sma.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
            LEFT JOIN rm_program_planning_unit ppu ON sma.PROGRAM_ID=ppu.PROGRAM_ID AND sma.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
            LEFT JOIN rm_equivalency_unit eu1 ON eu1.PROGRAM_ID=sma.PROGRAM_ID AND eu1.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId 
            LEFT JOIN rm_equivalency_unit_mapping eum1 ON eu1.EQUIVALENCY_UNIT_ID=eum1.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eu1.PROGRAM_ID=eum1.PROGRAM_ID
            LEFT JOIN rm_equivalency_unit eu2 ON eu2.PROGRAM_ID is null AND eu2.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
            LEFT JOIN rm_equivalency_unit_mapping eum2 ON eu2.EQUIVALENCY_UNIT_ID=eum2.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID=sma.PROGRAM_ID
            LEFT JOIN rm_equivalency_unit eu3 ON eu3.PROGRAM_ID is null AND eu3.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
            LEFT JOIN rm_equivalency_unit_mapping eum3 ON eu3.EQUIVALENCY_UNIT_ID=eum3.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum3.FORECASTING_UNIT_ID AND eum3.PROGRAM_ID is null

            WHERE mn.`MONTH` BETWEEN @varStartDate AND @varStopDate 
            GROUP BY mn.`MONTH`
        ) AS s2
    ) s3
    LEFT JOIN 
        (
        SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, s.PROGRAM_ID, st.PLANNING_UNIT_ID,IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, st.SHIPMENT_QTY, 
        IF(@varEquivalencyUnitId != 0, st.`SHIPMENT_QTY`*pu.`MULTIPLIER`*COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), st.SHIPMENT_QTY*@varRcpuMultiplier)
        ) `SHIPMENT_QTY` , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID, st.NOTES, st.ORDER_NO, st.PRIME_LINE_NO, st.DATA_SOURCE_ID
        FROM 
            (
            SELECT s.SHIPMENT_ID, p.PROGRAM_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM vw_program p LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=s.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID WHERE FIND_IN_SET(s.PROGRAM_ID, @varProgramIds) AND FIND_IN_SET(st.PLANNING_UNIT_ID, @varPlanningUnitIds) AND st.VERSION_ID<=p.CURRENT_VERSION_ID AND st.SHIPMENT_TRANS_ID IS NOT NULL AND ppu.ACTIVE GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_equivalency_unit eu1 ON eu1.PROGRAM_ID=s.PROGRAM_ID AND eu1.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId 
        LEFT JOIN rm_equivalency_unit_mapping eum1 ON eu1.EQUIVALENCY_UNIT_ID=eum1.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eu1.PROGRAM_ID=eum1.PROGRAM_ID
        LEFT JOIN rm_equivalency_unit eu2 ON eu2.PROGRAM_ID is null AND eu2.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
        LEFT JOIN rm_equivalency_unit_mapping eum2 ON eu2.EQUIVALENCY_UNIT_ID=eum2.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID=s.PROGRAM_ID
        LEFT JOIN rm_equivalency_unit eu3 ON eu3.PROGRAM_ID is null AND eu3.EQUIVALENCY_UNIT_ID=@varEquivalencyUnitId
        LEFT JOIN rm_equivalency_unit_mapping eum3 ON eu3.EQUIVALENCY_UNIT_ID=eum3.EQUIVALENCY_UNIT_ID AND pu.FORECASTING_UNIT_ID=eum3.FORECASTING_UNIT_ID AND eum3.PROGRAM_ID is null
        WHERE 
            st.ACTIVE 
            AND st.SHIPMENT_STATUS_ID != 8 
            AND st.ACCOUNT_FLAG
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @varStartDate AND @varStopDate 
    ) sh ON LEFT(s3.TRANS_DATE,7)=LEFT(sh.EDD,7)
    LEFT JOIN rm_shipment_linking sl ON sh.SHIPMENT_ID=sl.CHILD_SHIPMENT_ID AND sl.ACTIVE
    LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_data_source ds ON sh.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
    LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
    LEFT JOIN vw_program p ON sh.PROGRAM_ID=p.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON sh.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID;
END$$

DELIMITER ;
;

