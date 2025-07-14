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
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_OPENING_BALANCE`, s2.`FINAL_OPENING_BALANCE`/@varRcpuMultiplier) `FINAL_OPENING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ACTUAL_CONSUMPTION_QTY`, s2.`ACTUAL_CONSUMPTION_QTY`/@varRcpuMultiplier) `ACTUAL_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FORECASTED_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`/@varRcpuMultiplier) `FORECASTED_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CONSUMPTION_QTY`, s2.`FINAL_CONSUMPTION_QTY`/@varRcpuMultiplier) `FINAL_CONSUMPTION_QTY`,
            s2.`ACTUAL`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`SQTY`, s2.`SQTY`/@varRcpuMultiplier) `SQTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ADJUSTMENT`, s2.`ADJUSTMENT`/@varRcpuMultiplier) `ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`NATIONAL_ADJUSTMENT`, s2.`NATIONAL_ADJUSTMENT`/@varRcpuMultiplier) `NATIONAL_ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`EXPIRED_STOCK`, s2.`EXPIRED_STOCK`/@varRcpuMultiplier) `EXPIRED_STOCK`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_CLOSING_BALANCE`/@varRcpuMultiplier) `FINAL_CLOSING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`AMC`, s2.`AMC`/@varRcpuMultiplier) `AMC`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`UNMET_DEMAND`, s2.`UNMET_DEMAND`/@varRcpuMultiplier) `UNMET_DEMAND`,
            s2.`REGION_COUNT`, s2.`REGION_COUNT_FOR_STOCK`, s2.`PLAN_BASED_ON`,
            s2.`MIN_STOCK_MOS`, s2.`MAX_STOCK_MOS`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`MIN_STOCK_QTY`, s2.`MIN_STOCK_QTY`/@varRcpuMultiplier) `MIN_STOCK_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`MAX_STOCK_QTY`, s2.`MAX_STOCK_QTY`/@varRcpuMultiplier) `MAX_STOCK_QTY`
        FROM (
            SELECT 
                mn.`MONTH` `TRANS_DATE`, ppu.`NOTES` `PPU_NOTES`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`OPENING_BALANCE`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`OPENING_BALANCE`)) `FINAL_OPENING_BALANCE`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ACTUAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ACTUAL_CONSUMPTION_QTY`)) `ACTUAL_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FORECASTED_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FORECASTED_CONSUMPTION_QTY`)) `FORECASTED_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FINAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FINAL_CONSUMPTION_QTY`)) `FINAL_CONSUMPTION_QTY`,
                BIT_AND(sma.`ACTUAL`) `ACTUAL`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`SHIPMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`SHIPMENT_QTY`)) `SQTY`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ADJUSTMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ADJUSTMENT_QTY`)) `ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`NATIONAL_ADJUSTMENT`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`NATIONAL_ADJUSTMENT`)) `NATIONAL_ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`EXPIRED_STOCK`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`EXPIRED_STOCK`)) `EXPIRED_STOCK`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`CLOSING_BALANCE`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`CLOSING_BALANCE`)) `FINAL_CLOSING_BALANCE`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`AMC`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`AMC`)) `AMC`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`UNMET_DEMAND`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`UNMET_DEMAND`)) `UNMET_DEMAND`, 
                SUM(sma.`REGION_COUNT`) `REGION_COUNT`, SUM(sma.`REGION_COUNT_FOR_STOCK`) `REGION_COUNT_FOR_STOCK`, IF(BIT_AND(IF(ppu.PLAN_BASED_ON=2,true,false)),2,1) `PLAN_BASED_ON`,
                sma.`MIN_STOCK_MOS`, sma.`MAX_STOCK_MOS`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`MIN_STOCK_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`MIN_STOCK_QTY`)) `MIN_STOCK_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`MAX_STOCK_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`MAX_STOCK_QTY`)) `MAX_STOCK_QTY`
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
        IF(@varEquivalencyUnitId != 0, st.`SHIPMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), st.SHIPMENT_QTY/@varRcpuMultiplier)
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
        GROUP BY s.SHIPMENT_ID
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
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_OPENING_BALANCE`, s2.`FINAL_OPENING_BALANCE`/@varRcpuMultiplier) `FINAL_OPENING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ACTUAL_CONSUMPTION_QTY`, s2.`ACTUAL_CONSUMPTION_QTY`/@varRcpuMultiplier) `ACTUAL_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FORECASTED_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`/@varRcpuMultiplier) `FORECASTED_CONSUMPTION_QTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CONSUMPTION_QTY`, s2.`FINAL_CONSUMPTION_QTY`/@varRcpuMultiplier) `FINAL_CONSUMPTION_QTY`,
            s2.`ACTUAL`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`SQTY`, s2.`SQTY`/@varRcpuMultiplier) `SQTY`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`ADJUSTMENT`, s2.`ADJUSTMENT`/@varRcpuMultiplier) `ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`NATIONAL_ADJUSTMENT`, s2.`NATIONAL_ADJUSTMENT`/@varRcpuMultiplier) `NATIONAL_ADJUSTMENT`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`EXPIRED_STOCK`, s2.`EXPIRED_STOCK`/@varRcpuMultiplier) `EXPIRED_STOCK`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_CLOSING_BALANCE`/@varRcpuMultiplier) `FINAL_CLOSING_BALANCE`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`AMC`, s2.`AMC`/@varRcpuMultiplier) `AMC`,
            IF(@varEquivalencyUnitId = 0 && @varViewBy = 1, s2.`UNMET_DEMAND`, s2.`UNMET_DEMAND`/@varRcpuMultiplier) `UNMET_DEMAND`,
            s2.`REGION_COUNT`, s2.`REGION_COUNT_FOR_STOCK`, s2.`PLAN_BASED_ON`, 
            s2.`MIN_STOCK_QTY`, s2.`MIN_STOCK_MOS`, s2.`MAX_STOCK_QTY`, s2.`MAX_STOCK_MOS`
        FROM (
            SELECT 
                mn.`MONTH` `TRANS_DATE`, GROUP_CONCAT(IF(ppu.`NOTES` IS NOT NULL  AND TRIM(ppu.`NOTES`)!='', CONCAT(p.PROGRAM_CODE, ":", ppu.`NOTES`), null) separator "|") `PPU_NOTES`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`OPENING_BALANCE`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`OPENING_BALANCE`)) `FINAL_OPENING_BALANCE`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ACTUAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ACTUAL_CONSUMPTION_QTY`)) `ACTUAL_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FORECASTED_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FORECASTED_CONSUMPTION_QTY`)) `FORECASTED_CONSUMPTION_QTY`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`FINAL_CONSUMPTION_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`FINAL_CONSUMPTION_QTY`)) `FINAL_CONSUMPTION_QTY`,
                BIT_AND(sma.`ACTUAL`) `ACTUAL`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`SHIPMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`SHIPMENT_QTY`)) `SQTY`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`ADJUSTMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`ADJUSTMENT_QTY`)) `ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`NATIONAL_ADJUSTMENT`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`NATIONAL_ADJUSTMENT`)) `NATIONAL_ADJUSTMENT`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`EXPIRED_STOCK`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`EXPIRED_STOCK`)) `EXPIRED_STOCK`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`CLOSING_BALANCE`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`CLOSING_BALANCE`)) `FINAL_CLOSING_BALANCE`,
                SUM(IF(@varEquivalencyUnitId != 0, sma.`AMC`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`AMC`)) `AMC`, 
                SUM(IF(@varEquivalencyUnitId != 0, sma.`UNMET_DEMAND`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), sma.`UNMET_DEMAND`)) `UNMET_DEMAND`, 
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
        IF(@varEquivalencyUnitId != 0, st.`SHIPMENT_QTY`*pu.`MULTIPLIER`/COALESCE(eum1.`CONVERT_TO_EU`,eum2.`CONVERT_TO_EU`,eum3.`CONVERT_TO_EU`), st.SHIPMENT_QTY/@varRcpuMultiplier)
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
        GROUP BY s.SHIPMENT_ID    
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

ALTER TABLE `fasp`.`rm_equivalency_unit_mapping` CHANGE `CONVERT_TO_EU` `CONVERT_TO_EU` DECIMAL(22,8) UNSIGNED NOT NULL; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.openingBalanceTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Equal to the previous month’s Ending Balance. Bolded if there is an actual inventory count in the previous month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Égal au solde final du mois précédent. En gras s`il y a un inventaire réel au cours du mois précédent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Equivale al saldo final del mes anterior. En negrita si hay un recuento de inventario real en el mes anterior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Igual ao saldo final do mês anterior. Em negrito se houver uma contagem de inventário real no mês anterior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.openingBalanceTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ opening balances each month. Bolded if all programs have an actual inventory count in the previous month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des soldes d`ouverture des programmes sélectionnés chaque mois. En gras si tous les programmes ont un inventaire réel au cours du mois précédent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los saldos iniciales de los programas seleccionados cada mes. En negrita si todos los programas tienen un recuento de inventario real en el mes anterior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos saldos de abertura dos programas selecionados a cada mês. Em negrito se todos os programas tiverem uma contagem de inventário real no mês anterior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.openingBalanceTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ opening balances after converting to EU. Bolded if all programs have an actual inventory count in the previous month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des soldes d`ouverture des programmes sélectionnés et des unités de planification après conversion vers l`UE. En gras si tous les programmes ont un inventaire réel au cours du mois précédent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los saldos iniciales de los programas y unidades de planificación seleccionados después de la conversión a la UE. En negrita si todos los programas tienen un recuento de inventario real en el mes anterior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos saldos iniciais dos programas selecionados e das unidades de planejamento após a conversão para a UE. Em negrito se todos os programas tiverem uma contagem de inventário real no mês anterior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.forecastedTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly estimate of the quantities expected to be consumed or dispensed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Estimation mensuelle des quantités prévues à consommer ou à distribuer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estimación mensual de las cantidades que se espera consumir o dispensar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estimativa mensal das quantidades que se espera que sejam consumidas ou dispensadas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.forecastedTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected program’s forecasted consumption values each month, when available.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation prévues du programme sélectionné chaque mois, lorsqu`elles sont disponibles.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo previstos del programa seleccionado cada mes, cuando estén disponibles.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de consumo previstos do programa selecionado a cada mês, quando disponível.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.forecastedTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ forecasted consumption values after converting to EU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation prévues des programmes sélectionnés et des unités de planification après conversion aux normes européennes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo previstos de los programas y unidades de planificación seleccionados después de la conversión a la UE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de consumo previstos dos programas selecionados e das unidades de planeamento após a conversão para a UE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.actualTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly reported quantity that was actually consumed or dispensed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité mensuelle déclarée qui a été réellement consommée ou distribuée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mensual reportada que realmente fue consumida o dispensada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mensal relatada que foi realmente consumida ou dispensada');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.actualTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ actual consumption values each month, when available.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation réelles des programmes sélectionnés chaque mois, lorsqu`elles sont disponibles.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo reales de los programas seleccionados cada mes, cuando esté disponible.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores reais de consumo dos programas selecionados a cada mês, quando disponível.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.actualTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ actual consumption values after converting to EU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation réelles des programmes sélectionnés après conversion en UE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo reales de los programas seleccionados después de la conversión a la UE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de consumo real dos programas selecionados após a conversão para a UE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.consensusTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly reported quantity that was actually consumed or dispensed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité mensuelle déclarée qui a été réellement consommée ou distribuée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mensual reportada que realmente fue consumida o dispensada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mensal relatada que foi realmente consumida ou dispensada');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.consensusTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ actual consumption values each month (if available); otherwise, forecasted consumption values. May include both forecasted and actual values.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation réelles des programmes sélectionnés chaque mois (si disponibles) ; sinon, valeurs de consommation prévues. Peut inclure à la fois les valeurs prévues et réelles.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo reales de los programas seleccionados cada mes (si están disponibles); de lo contrario, valores de consumo previstos. Puede incluir valores previstos y reales.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de consumo real dos programas selecionados a cada mês (se disponível); caso contrário, valores de consumo previstos. Pode incluir valores previstos e reais.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.consensusTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ actual consumption values after converting to EU (if available); otherwise forecasted consumption values. May include both forecasted and actual values');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de consommation réelles des programmes sélectionnés et des unités de planification après conversion en UE (si disponible) ; sinon, valeurs de consommation prévues. Peut inclure à la fois les valeurs prévues et réelles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de consumo reales de los programas y unidades de planificación seleccionados después de la conversión a la UE (si está disponible); de lo contrario, valores de consumo previstos. Puede incluir valores previstos y reales');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de consumo real dos programas selecionados e das unidades de planejamento após a conversão para a UE (se disponível); caso contrário, valores de consumo previstos. Pode incluir valores previstos e reais');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.shipmentQtyTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ shipment quantities after converting to EU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des quantités expédiées des programmes sélectionnés et des unités de planification après conversion vers l`UE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de las cantidades de envío de los programas y unidades de planificación seleccionados después de la conversión a la UE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma das quantidades de remessas de programas selecionados e unidades de planejamento após a conversão para a UE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.adjustmentQtyTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modifications to inventory quantities for the month, either positive or negative');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifications des quantités de stock pour le mois, qu`elles soient positives ou négatives');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Modificaciones en las cantidades de inventario del mes, ya sean positivas o negativas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modificações nas quantidades de estoque do mês, positivas ou negativas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.adjustmentQtyTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ adjustment values each month, manual or automatic');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs de réglage des programmes sélectionnés chaque mois, manuel ou automatique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de ajuste de los programas seleccionados cada mes, manual o automático');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de ajuste dos programas selecionados a cada mês, manual ou automático');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.adjustmentQtyTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ adjustment values each month after converting to EU, manual or automatic');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs d`ajustement des programmes sélectionnés et des unités de planification chaque mois après conversion vers l`UE, manuelle ou automatique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de ajuste de los programas seleccionados y de las unidades de planificación cada mes después de la conversión a UE, manual o automática');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores de ajuste dos programas selecionados e das unidades de planejamento a cada mês após a conversão para UE, manual ou automático');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.expiredStockTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Estimated quantity of stock expected to expire or become unusable by the end of the month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité estimée de stock qui devrait expirer ou devenir inutilisable d`ici la fin du mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad estimada de existencias que se espera que caduquen o se vuelvan inutilizables a finales de mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade estimada de estoque que deverá expirar ou se tornar inutilizável até o final do mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.expiredStockTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ expired stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme du stock expiré des programmes sélectionnés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma del stock vencido de los programas seleccionados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma do estoque expirado dos programas selecionados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.expiredStockTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ expired stock after converting to EU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des stocks expirés des programmes sélectionnés et des unités de planification après conversion à l`UE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma del stock vencido de programas seleccionados y unidades de planificación después de la conversión a la UE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma do estoque expirado de programas selecionados e unidades de planejamento após a conversão para a UE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.endingBalanceTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Quantity of stock at the end of the month. Actual inventory count is bolded');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité de stock à la fin du mois. Le nombre réel de stocks est indiqué en gras');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de existencias al final del mes. El recuento real del inventario está en negrita.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade de estoque no final do mês. A contagem real do estoque está em negrito');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.endingBalanceTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ ending balances each month. Bolded if all programs have an actual inventory count in the current month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des soldes de fin de mois des programmes sélectionnés. En gras si tous les programmes ont un inventaire réel au cours du mois en cours');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los saldos finales de los programas seleccionados cada mes. En negrita si todos los programas tienen un recuento de inventario real en el mes actual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos saldos finais dos programas selecionados a cada mês. Em negrito se todos os programas tiverem uma contagem de inventário real no mês atual');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.endingBalanceTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of selected programs’ & planning units’ ending balances each month after converting to EU. Bolded if all programs have an actual inventory count in the current month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des soldes de clôture des programmes sélectionnés et des unités de planification chaque mois après la conversion vers l`UE. En gras si tous les programmes ont un inventaire réel au cours du mois en cours');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los saldos finales de los programas y unidades de planificación seleccionados cada mes después de la conversión a la UE. En negrita si todos los programas tienen un recuento de inventario real en el mes actual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos saldos finais dos programas selecionados e das unidades de planejamento a cada mês após a conversão para UE. Em negrito se todos os programas tiverem uma contagem de inventário real no mês atual');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.amcTooltipSingleProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dynamic value based on user-entered number of months past & future in the Update PU screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur dynamique basée sur le nombre de mois passés et futurs saisi par l`utilisateur dans l`écran de mise à jour du PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor dinámico basado en el número de meses pasados y futuros ingresados por el usuario en la pantalla Actualizar PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor dinâmico com base no número de meses passados e futuros inserido pelo usuário na tela Atualizar PU');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.amcTooltipMultiProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of the individually calculated AMC values for selected programs each month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs AMC calculées individuellement pour les programmes sélectionnés chaque mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores de AMC calculados individualmente para programas seleccionados cada mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores AMC calculados individualmente para programas selecionados a cada mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.amcTooltipEU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of the individually calculated AMC values for selected programs & planning units each month after converting to EU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des valeurs AMC calculées individuellement pour les programmes et unités de planification sélectionnés chaque mois après conversion vers l`UE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de los valores AMC calculados individualmente para programas y unidades de planificación seleccionados cada mes después de la conversión a la UE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma dos valores AMC calculados individualmente para programas e unidades de planejamento selecionados a cada mês após a conversão para a UE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.onlyShowPUsThatArePartOfAllPrograms','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only show PUs/ARUs that are available in ALL selected programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher uniquement les PU/ARU disponibles dans TOUS les programmes sélectionnés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar solo las PU/ARU que estén disponibles en TODOS los programas seleccionados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar apenas PUs/ARUs que estão disponíveis em TODOS os programas selecionados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.doYouWantToAggregate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to aggregate selected programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous regrouper les programmes sélectionnés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres agregar programas seleccionados?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você deseja agregar programas selecionados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.shipmemtTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hover over each shipment box to see its assigned PU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Passez la souris sur chaque boîte d`expédition pour voir son PU attribué');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pase el cursor sobre cada casilla de envío para ver su PU asignado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Passe o mouse sobre cada caixa de remessa para ver sua PU atribuída');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.graphAggregatedBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View Graph Disaggregated By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher le graphique désagrégé par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver gráfico desagregado por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ver gráfico desagregado por');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.programPlanningUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program-Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de planification des programmes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de Planificación de Programas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Planejamento de Programas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.consensus','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consensus');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consensus');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consenso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consenso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.consensusConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consensus Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consensus de consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo por consenso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consenso de Consumo');-- pr

update rm_consumption_trans ct 
left join rm_realm_country_planning_unit rcpu on ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
set ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*IF(rcpu.CONVERSION_METHOD=1,rcpu.CONVERSION_NUMBER,1/rcpu.CONVERSION_NUMBER);

update rm_shipment_trans st 
left join rm_realm_country_planning_unit rcpu on st.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
set st.SHIPMENT_QTY=st.SHIPMENT_RCPU_QTY*IF(rcpu.CONVERSION_METHOD=1,rcpu.CONVERSION_NUMBER,1/rcpu.CONVERSION_NUMBER) 
where st.ERP_FLAG=0;