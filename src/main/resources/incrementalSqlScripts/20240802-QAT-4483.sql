
USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVerticalAggregated`;
;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVerticalMultiSelect`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusReportVerticalAggregated`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_IDS TEXT, VAR_REPORTING_UNIT_IDS TEXT, VAR_VIEW_BY INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16
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
      `OPENING_BALANCE` bigint DEFAULT NULL,
      `SHIPMENT_QTY` bigint DEFAULT NULL,
      `FORECASTED_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `ACTUAL_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `FINAL_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `ACTUAL` tinyint(1) DEFAULT NULL,
      `ADJUSTMENT_QTY` bigint DEFAULT NULL,
      `STOCK_QTY` bigint DEFAULT NULL,
      `REGION_COUNT` int unsigned NOT NULL,
      `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL,
      `EXPIRED_STOCK` bigint DEFAULT NULL,
      `CLOSING_BALANCE` bigint DEFAULT NULL,
      `UNMET_DEMAND` bigint DEFAULT NULL,
      `NATIONAL_ADJUSTMENT` bigint DEFAULT NULL,
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
         SELECT rcpu.`REALM_COUNTRY_PLANNING_UNIT_ID`, rcpu.`LABEL_EN`, rcpu.`LABEL_FR`, rcpu.`LABEL_SP`, rcpu.`LABEL_PR`, rcpu.`MULTIPLIER` INTO @ruId, @ruLabelEn, @ruLabelFr, @ruLabelSp, @ruLabelPr, @varRcpuMultiplier FROM vw_realm_country_planning_unit rcpu WHERE rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=@varReportingUnitIds;
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
        sma.`NATIONAL_ADJUSTMENT`
    FROM vw_program p 
    LEFT JOIN rm_supply_plan_amc sma  ON p.PROGRAM_ID=sma.PROGRAM_ID 
    WHERE 
        FIND_IN_SET(p.PROGRAM_ID, @varProgramIds) 
        AND sma.VERSION_ID = p.CURRENT_VERSION_ID 
        AND FIND_IN_SET(sma.PLANNING_UNIT_ID, @varPlanningUnitIds) 
        AND sma.TRANS_DATE BETWEEN @varStartDate AND @varStopDate;

    SELECT 
        @ruId `RU_ID`, 0 `RU_LABEL_ID`, @ruLabelEn `RU_LABEL_EN`, @ruLabelFr `RU_LABEL_FR`, @ruLabelSp `RU_LABEL_SP`, @ruLabelPr `RU_LABEL_PR`, 
        s3.`TRANS_DATE`, 
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
        s3.`FINAL_CLOSING_BALANCE`/s3.`AMC` `MOS`, s3.`PLAN_BASED_ON`, 
        sh.`SHIPMENT_ID`, sh.`SHIPMENT_QTY`, sh.`EDD`, sh.`NOTES`, sh.`ORDER_NO`, sh.`PRIME_LINE_NO`, sl.`RO_NO`, sl.`RO_PRIME_LINE_NO`,
        p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
        fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.`LABEL_ID` `FUNDING_SOURCE_LABEL_ID`, fs.`LABEL_EN` `FUNDING_SOURCE_LABEL_EN`, fs.`LABEL_FR` `FUNDING_SOURCE_LABEL_FR`, fs.`LABEL_SP` `FUNDING_SOURCE_LABEL_SP`, fs.`LABEL_PR` `FUNDING_SOURCE_LABEL_PR`, 
        pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.`LABEL_FR` `PROCUREMENT_AGENT_LABEL_FR`, pa.`LABEL_SP` `PROCUREMENT_AGENT_LABEL_SP`, pa.`LABEL_PR` `PROCUREMENT_AGENT_LABEL_PR`, 
        ds.`DATA_SOURCE_ID`, ds.`LABEL_ID` `DATA_SOURCE_LABEL_ID`, ds.`LABEL_EN` `DATA_SOURCE_LABEL_EN`, ds.`LABEL_FR` `DATA_SOURCE_LABEL_FR`, ds.`LABEL_SP` `DATA_SOURCE_LABEL_SP`, ds.`LABEL_PR` `DATA_SOURCE_LABEL_PR`, 
        ss.`SHIPMENT_STATUS_ID`, ss.`LABEL_ID` `SHIPMENT_STATUS_LABEL_ID`, ss.`LABEL_EN` `SHIPMENT_STATUS_LABEL_EN`, ss.`LABEL_FR` `SHIPMENT_STATUS_LABEL_FR`, ss.`LABEL_SP` `SHIPMENT_STATUS_LABEL_SP`, ss.`LABEL_PR` `SHIPMENT_STATUS_LABEL_PR`
    FROM (
        SELECT 
            s2.`TRANS_DATE`, 
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
            s2.`REGION_COUNT`, s2.`REGION_COUNT_FOR_STOCK`, s2.`PLAN_BASED_ON`
        FROM (
            SELECT 
                mn.`MONTH` `TRANS_DATE`, 
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
                SUM(sma.`REGION_COUNT`) `REGION_COUNT`, SUM(sma.`REGION_COUNT_FOR_STOCK`) `REGION_COUNT_FOR_STOCK`, IF(BIT_AND(IF(ppu.PLAN_BASED_ON=2,true,false)),2,1) `PLAN_BASED_ON`
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
        SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, s.PROGRAM_ID, st.PLANNING_UNIT_ID, st.SHIPMENT_QTY , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID, st.NOTES, st.ORDER_NO, st.PRIME_LINE_NO, st.DATA_SOURCE_ID
        FROM 
            ( 
            SELECT s.SHIPMENT_ID, p.PROGRAM_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM vw_program p LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE FIND_IN_SET(s.PROGRAM_ID, @varProgramIds) AND FIND_IN_SET(st.PLANNING_UNIT_ID, @varPlanningUnitIds) AND st.VERSION_ID<=p.CURRENT_VERSION_ID AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
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


USE `fasp`;
DROP procedure IF EXISTS `getConsumptionInfoForSSVReport`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getConsumptionInfoForSSVReport`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getConsumptionInfoForSSVReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_IDS TEXT, VAR_REPORTING_UNIT_IDS TEXT, VAR_VIEW_BY INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16a
    -- %%%%%%%%%%%%%%%%%%%%%
    
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varReportingUnitIds = VAR_REPORTING_UNIT_IDS;
    SET @varViewBy = VAR_VIEW_BY;
        
    IF @varViewBy = 1 THEN -- PlanningUnitIds
	SET @varPlanningUnitIds = @varReportingUnitIds;
    ELSEIF @varViewBy = 2 THEN -- ARU RealmCountryPlanningUnits
        SELECT GROUP_CONCAT(DISTINCT rcpu.PLANNING_UNIT_ID) INTO @varPlanningUnitIds FROM rm_realm_country_planning_unit rcpu WHERE FIND_IN_SET(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, @varReportingUnitIds);
    END IF;

    SELECT 
        cons.CONSUMPTION_ID, ct.CONSUMPTION_DATE, ct.ACTUAL_FLAG, ct.NOTES,
        p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `REGION_LABEL_ID`, r.LABEL_EN `REGION_LABEL_EN`, r.LABEL_FR `REGION_LABEL_FR`, r.LABEL_SP `REGION_LABEL_SP`, r.LABEL_PR `REGION_LABEL_PR`,
        ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`
    FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_program p LEFT JOIN rm_consumption c ON p.PROGRAM_ID=c.PROGRAM_ID LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE ct.VERSION_ID<=p.CURRENT_VERSION_ID AND FIND_IN_SET(c.PROGRAM_ID, @varProgramIds) AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @varPlanningUnitIds) GROUP BY ct.CONSUMPTION_ID) tc 
    LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
    LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_program p ON cons.PROGRAM_ID=p.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
    LEFT JOIN vw_data_source ds ON ct.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
    WHERE ct.CONSUMPTION_DATE BETWEEN @varStartDate AND @varStopDate AND ct.ACTIVE
    ORDER BY ct.CONSUMPTION_DATE, p.PROGRAM_CODE, pu.LABEL_EN, ct.REGION_ID, ct.ACTUAL_FLAG;
END$$

DELIMITER ;
;


USE `fasp`;
DROP procedure IF EXISTS `getInventoryInfoForSSVReport`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getInventoryInfoForSSVReport`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getInventoryInfoForSSVReport`  (VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_IDS TEXT, VAR_REPORTING_UNIT_IDS TEXT, VAR_VIEW_BY INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16b
    -- %%%%%%%%%%%%%%%%%%%%%
	
    SET @varStartDate = VAR_START_DATE;
    SET @varStopDate = VAR_STOP_DATE;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varReportingUnitIds = VAR_REPORTING_UNIT_IDS;
    SET @varViewBy = VAR_VIEW_BY;

    IF @varViewBy = 1 THEN -- PlanningUnitIds
	SET @varPlanningUnitIds = @varReportingUnitIds;
    ELSEIF @varViewBy = 2 THEN -- ARU RealmCountryPlanningUnits
        SELECT GROUP_CONCAT(DISTINCT rcpu.PLANNING_UNIT_ID) INTO @varPlanningUnitIds FROM rm_realm_country_planning_unit rcpu WHERE FIND_IN_SET(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, @varReportingUnitIds);
    END IF;
    
    SELECT 
	it.INVENTORY_ID, it.INVENTORY_DATE, it.NOTES,
	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `REGION_LABEL_ID`, r.LABEL_EN `REGION_LABEL_EN`, r.LABEL_FR `REGION_LABEL_FR`, r.LABEL_SP `REGION_LABEL_SP`, r.LABEL_PR `REGION_LABEL_PR`,
	ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`
    FROM (SELECT it.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_program p LEFT JOIN rm_inventory i ON p.PROGRAM_ID=i.PROGRAM_ID LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID WHERE it.VERSION_ID<=p.CURRENT_VERSION_ID AND FIND_IN_SET(i.PROGRAM_ID, @varProgramIds) AND FIND_IN_SET(rcpu.PLANNING_UNIT_ID, @varPlanningUnitIds) GROUP BY it.INVENTORY_ID) tc 
    LEFT JOIN rm_inventory i ON tc.INVENTORY_ID=i.INVENTORY_ID
    LEFT JOIN rm_inventory_trans it ON tc.INVENTORY_ID=it.INVENTORY_ID AND tc.MAX_VERSION_ID=it.VERSION_ID
    LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
    LEFT JOIN vw_program p ON i.PROGRAM_ID=p.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_region r ON it.REGION_ID=r.REGION_ID
    LEFT JOIN vw_data_source ds ON it.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
    WHERE it.INVENTORY_DATE BETWEEN @varSartDate AND @varStopDate AND it.ACTIVE
    ORDER BY it.INVENTORY_DATE,p.PROGRAM_CODE,pu.LABEL_EN,it.REGION_ID,i.INVENTORY_ID;
END$$

DELIMITER ;
;


USE `fasp`;
DROP procedure IF EXISTS `stockStatusReportVertical`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVertical`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusReportVertical`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_PLANNING_UNIT_ID INT(10))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16
    -- %%%%%%%%%%%%%%%%%%%%%
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;

    SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    DROP TEMPORARY TABLE IF EXISTS `tmp_supply_plan_amc`;
    CREATE TEMPORARY TABLE `tmp_supply_plan_amc` (
      `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT,
      `PROGRAM_ID` int unsigned NOT NULL, 
      `VERSION_ID` int unsigned NOT NULL,
      `PLANNING_UNIT_ID` int unsigned NOT NULL,
      `TRANS_DATE` date NOT NULL,
      `AMC` decimal(24,8) DEFAULT NULL,
      `AMC_COUNT` int DEFAULT NULL,
      `MOS` decimal(24,8) DEFAULT NULL,
      `MOS_WPS` decimal(24,8) DEFAULT NULL,
      `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL,
      `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL,
      `OPENING_BALANCE` bigint DEFAULT NULL,
      `OPENING_BALANCE_WPS` bigint DEFAULT NULL,
      `MANUAL_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `MANUAL_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `MANUAL_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `MANUAL_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `MANUAL_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `MANUAL_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL,
      `ERP_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL,
      `SHIPMENT_QTY` bigint DEFAULT NULL,
      `FORECASTED_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `ACTUAL_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `ADJUSTED_CONSUMPTION_QTY` bigint DEFAULT NULL,
      `ACTUAL` tinyint(1) DEFAULT NULL,
      `ADJUSTMENT_MULTIPLIED_QTY` bigint DEFAULT NULL,
      `STOCK_MULTIPLIED_QTY` bigint DEFAULT NULL,
      `REGION_COUNT` int unsigned NOT NULL,
      `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL,
      `EXPIRED_STOCK` bigint DEFAULT NULL,
      `EXPIRED_STOCK_WPS` bigint DEFAULT NULL,
      `CLOSING_BALANCE` bigint DEFAULT NULL,
      `CLOSING_BALANCE_WPS` bigint DEFAULT NULL,
      `UNMET_DEMAND` bigint DEFAULT NULL,
      `UNMET_DEMAND_WPS` bigint DEFAULT NULL,
      `NATIONAL_ADJUSTMENT` bigint DEFAULT NULL,
      `NATIONAL_ADJUSTMENT_WPS` bigint DEFAULT NULL,
      PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`),
      KEY `idx_tmp_supply_plan_amc_programId` (`PROGRAM_ID`),
      KEY `idx_tmp_supply_plan_amc_planningUnitId` (`PLANNING_UNIT_ID`),
      KEY `idx_rm_supply_plan_amc_transDate` (`TRANS_DATE`),
      KEY `idx_rm_supply_plan_amc_versionId` (`VERSION_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

    INSERT INTO tmp_supply_plan_amc SELECT sma.* FROM rm_supply_plan_amc sma  WHERE sma.PROGRAM_ID = @programId AND sma.VERSION_ID = @versionId AND sma.PLANNING_UNIT_ID = @planningUnitId AND sma.TRANS_DATE BETWEEN @startDate AND @stopDate;

    SET @prvMonthClosingBal = 0;
    SELECT 
        s2.`TRANS_DATE`, 
        s2.`PLANNING_UNIT_ID`, s2.`PLANNING_UNIT_LABEL_ID`, s2.`PLANNING_UNIT_LABEL_EN`, s2.`PLANNING_UNIT_LABEL_FR`, s2.`PLANNING_UNIT_LABEL_SP`, s2.`PLANNING_UNIT_LABEL_PR`,
        COALESCE(s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `FINAL_OPENING_BALANCE`,
        s2.`ACTUAL_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`, 
        IF(s2.`ACTUAL`, s2.`ACTUAL_CONSUMPTION_QTY`,s2.`FORECASTED_CONSUMPTION_QTY`) `FINAL_CONSUMPTION_QTY`,
        s2.`ACTUAL`,
        s2.`SQTY` ,
        s2.`ADJUSTMENT`,
        s2.`NATIONAL_ADJUSTMENT`,
        s2.EXPIRED_STOCK,
        COALESCE(s2.`FINAL_CLOSING_BALANCE`, @prvMonthClosingBal) `FINAL_CLOSING_BALANCE`,
        s2.AMC, s2.UNMET_DEMAND,s2.REGION_COUNT,s2.REGION_COUNT_FOR_STOCK,
        s2.`MoS`,
        s2.`MIN_MONTHS_OF_STOCK`,
        s2.`MAX_MONTHS_OF_STOCK`,
        s2.MIN_STOCK_QTY,
        s2.MAX_STOCK_QTY,
        s2.PLAN_BASED_ON,s2.DISTRIBUTION_LEAD_TIME,
        s2.`SHIPMENT_ID`, s2.`SHIPMENT_QTY`, s2.`EDD`, s2.`NOTES`, s2.`ORDER_NO`, s2.`PRIME_LINE_NO`, s2. `RO_NO`, s2.`RO_PRIME_LINE_NO`,
        s2.`FUNDING_SOURCE_ID`, s2.`FUNDING_SOURCE_CODE`, s2.`FUNDING_SOURCE_LABEL_ID`, s2.`FUNDING_SOURCE_LABEL_EN`, s2.`FUNDING_SOURCE_LABEL_FR`, s2.`FUNDING_SOURCE_LABEL_SP`, s2.`FUNDING_SOURCE_LABEL_PR`, 
        s2.PROCUREMENT_AGENT_ID, s2.PROCUREMENT_AGENT_CODE, s2.`PROCUREMENT_AGENT_LABEL_ID`, s2.`PROCUREMENT_AGENT_LABEL_EN`, s2.`PROCUREMENT_AGENT_LABEL_FR`, s2.`PROCUREMENT_AGENT_LABEL_SP`, s2.`PROCUREMENT_AGENT_LABEL_PR`, 
        s2.DATA_SOURCE_ID, s2.`DATA_SOURCE_LABEL_ID`, s2.`DATA_SOURCE_LABEL_EN`, s2.`DATA_SOURCE_LABEL_FR`, s2.`DATA_SOURCE_LABEL_SP`, s2.`DATA_SOURCE_LABEL_PR`, 
        s2.SHIPMENT_STATUS_ID, s2.`SHIPMENT_STATUS_LABEL_ID`, s2.`SHIPMENT_STATUS_LABEL_EN`, s2.`SHIPMENT_STATUS_LABEL_FR`, s2.`SHIPMENT_STATUS_LABEL_SP`, s2.`SHIPMENT_STATUS_LABEL_PR`,
        @prvMonthClosingBal:=COALESCE(s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `PRV_CLOSING_BAL`
    FROM (
	SELECT 
        mn.MONTH `TRANS_DATE`, 
        pu.`PLANNING_UNIT_ID`, pu.`LABEL_ID` `PLANNING_UNIT_LABEL_ID`, pu.`LABEL_EN` `PLANNING_UNIT_LABEL_EN`, pu.`LABEL_FR` `PLANNING_UNIT_LABEL_FR`, pu.`LABEL_SP` `PLANNING_UNIT_LABEL_SP`, pu.`LABEL_PR` `PLANNING_UNIT_LABEL_PR`,
        sma.OPENING_BALANCE `FINAL_OPENING_BALANCE`, 
        sma.ACTUAL_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY, 
        sma.ACTUAL,
        sma.SHIPMENT_QTY SQTY,
        sma.ADJUSTMENT_MULTIPLIED_QTY `ADJUSTMENT`,
        sma.`NATIONAL_ADJUSTMENT`,
        sma.EXPIRED_STOCK,
        sma.CLOSING_BALANCE `FINAL_CLOSING_BALANCE`,
        sma.AMC, sma.UNMET_DEMAND,sma.REGION_COUNT,sma.REGION_COUNT_FOR_STOCK,
        sma.MOS `MoS`,
        ppu.PLAN_BASED_ON,
        ppu.DISTRIBUTION_LEAD_TIME,
        IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK) `MIN_MONTHS_OF_STOCK`, 
        IF(
            IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS<r.MIN_MOS_MAX_GAURDRAIL, 
            r.MIN_MOS_MAX_GAURDRAIL, 
            IF(
                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS>r.MAX_MOS_MAX_GAURDRAIL,
                r.MAX_MOS_MAX_GAURDRAIL,
                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS
            )
        ) `MAX_MONTHS_OF_STOCK`,
        sma.MIN_STOCK_QTY,
        sma.MAX_STOCK_QTY,
        sh.SHIPMENT_ID, sh.SHIPMENT_QTY, sh.`EDD`, sh.`NOTES`, sh.`ORDER_NO`, sh.`PRIME_LINE_NO`, sl.`RO_NO`, sl.`RO_PRIME_LINE_NO`,
        fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, 
        ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`, 
        ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_Fr `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`
    FROM
        mn 
        LEFT JOIN tmp_supply_plan_amc sma ON mn.MONTH=sma.TRANS_DATE 
        LEFT JOIN 
            (
            SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, st.SHIPMENT_QTY , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID, st.NOTES, st.ORDER_NO, st.PRIME_LINE_NO, st.DATA_SOURCE_ID
            FROM 
                (
                SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID 
            ) AS s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
            WHERE 
                st.ACTIVE 
                AND st.SHIPMENT_STATUS_ID != 8 
                AND st.ACCOUNT_FLAG
                AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
                AND st.PLANNING_UNIT_ID =@planningUnitId
        ) sh ON LEFT(sma.TRANS_DATE,7)=LEFT(sh.EDD,7)
        LEFT JOIN rm_shipment_linking sl ON sh.SHIPMENT_ID=sl.CHILD_SHIPMENT_ID AND sl.ACTIVE
        LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        LEFT JOIN vw_data_source ds ON sh.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
        LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
        LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND ppu.PLANNING_UNIT_ID=@planningUnitId
        LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program p ON p.PROGRAM_ID=@programId
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID
    WHERE
        mn.MONTH BETWEEN @startDate AND @stopDate 
    ORDER BY mn.MONTH, sh.sHIPMENT_ID) AS s2;
    
END$$

DELIMITER ;
;

