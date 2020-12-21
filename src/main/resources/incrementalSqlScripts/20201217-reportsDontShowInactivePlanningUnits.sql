UPDATE rm_program p SET p.ORGANISATION_ID=1, p.PROGRAM_CODE='ETH-LAB-MOH' WHERE p.PROGRAM_ID=2137;

-- QAT-362 changes for swapping the Planning Unit around
UPDATE rm_program_planning_unit ppu set ppu.PLANNING_UNIT_ID=1073 WHERE ppu.PROGRAM_ID=2034 AND ppu.PLANNING_UNIT_ID=1074;
UPDATE rm_realm_country_planning_unit rcpu SET rcpu.PLANNING_UNIT_ID=1073 WHERE rcpu.REALM_COUNTRY_ID=6 AND rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=57;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.PLANNING_UNIT_ID=1073 WHERE c.PROGRAM_ID=2034 AND ct.PLANNING_UNIT_ID=1074;
UPDATE rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID LEFT JOIN rm_shipment_trans_batch_info stbi ON st.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID SET st.PLANNING_UNIT_ID=1073, bi.PLANNING_UNIT_ID=1073 WHERE s.PROGRAM_ID=2034 AND st.PLANNING_UNIT_ID=1074;
UPDATE rm_problem_report pr SET pr.DATA3=1073 WHERE pr.PROGRAM_ID=2034 AND pr.DATA3=1074;
-- 

UPDATE `rm_realm_problem` SET `ACTIVE`='0' WHERE `REALM_PROBLEM_ID`='5';
UPDATE `rm_realm_problem` SET `ACTIVE`='0' WHERE `REALM_PROBLEM_ID`='6';
UPDATE `rm_realm_problem` SET `ACTIVE`='0' WHERE `REALM_PROBLEM_ID`='7';
UPDATE `rm_realm_problem` SET `DATA3`='18,3' WHERE `REALM_PROBLEM_ID`='11';
UPDATE `rm_realm_problem` SET `DATA3`='23' WHERE `REALM_PROBLEM_ID`='13';
UPDATE `rm_realm_problem` SET `DATA3`='26' WHERE `REALM_PROBLEM_ID`='14';

USE `fasp`;
DROP procedure IF EXISTS `buildNewSupplyPlanRegion`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildNewSupplyPlanRegion`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
BEGIN
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    
    SELECT COUNT(*) INTO @currentCount FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=@programId AND spa.VERSION_ID=@versionId;
    -- Get the Region count for this Program
    SELECT count(*) INTO @regionCount FROM rm_program_region pr WHERE pr.PROGRAM_ID=@programId;
        
    DELETE tn.* FROM tmp_nsp tn WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId;
        
    -- DELETE nsps.* FROM rm_nsp_summary nsps WHERE nsps.PROGRAM_ID=@programId AND nsps.VERSION_ID=@versionId;
    -- DELETE nspr.* FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId;
       
    -- Populate the nsp_region table with all the raw data that we have for Consumption, Inventory and Shipment per Region
    INSERT INTO tmp_nsp (
        PROGRAM_ID, VERSION_ID, PLANNING_UNIT_ID, TRANS_DATE, REGION_ID, -- 5
        FORECASTED_CONSUMPTION, ACTUAL_CONSUMPTION, ADJUSTMENT, STOCK, REGION_COUNT, -- 5
        MANUAL_PLANNED_SHIPMENT, MANUAL_SUBMITTED_SHIPMENT, MANUAL_APPROVED_SHIPMENT, MANUAL_SHIPPED_SHIPMENT, MANUAL_RECEIVED_SHIPMENT, MANUAL_ONHOLD_SHIPMENT, -- 6
        ERP_PLANNED_SHIPMENT, ERP_SUBMITTED_SHIPMENT, ERP_APPROVED_SHIPMENT, ERP_SHIPPED_SHIPMENT, ERP_RECEIVED_SHIPMENT, ERP_ONHOLD_SHIPMENT -- 6
    )
    SELECT 
        o.`PROGRAM_ID`, @versionId, o.`PLANNING_UNIT_ID`, DATE(CONCAT(o.`TRANS_DATE`,"-01")) , o.`REGION_ID`, 
        SUM(o.`FORECASTED_CONSUMPTION`), SUM(o.`ACTUAL_CONSUMPTION`), SUM(o.`ADJUSTMENT`), SUM(o.`STOCK`), @regionCount, 
        SUM(o.`MANUAL_PLANNED_SHIPMENT`), SUM(o.`MANUAL_SUBMITTED_SHIPMENT`), SUM(o.`MANUAL_APPROVED_SHIPMENT`), SUM(o.`MANUAL_SHIPPED_SHIPMENT`), SUM(o.`MANUAL_RECEIVED_SHIPMENT`), SUM(o.`MANUAL_ONHOLD_SHIPMENT`), 
        SUM(o.`ERP_PLANNED_SHIPMENT`), SUM(o.`ERP_SUBMITTED_SHIPMENT`), SUM(o.`ERP_APPROVED_SHIPMENT`), SUM(o.`ERP_SHIPPED_SHIPMENT`), SUM(o.`ERP_RECEIVED_SHIPMENT`), SUM(o.`ERP_ONHOLD_SHIPMENT`)
    FROM (
        SELECT 
            tc.`PROGRAM_ID`, tc.`PLANNING_UNIT_ID`, LEFT(tc.`CONSUMPTION_DATE`, 7) `TRANS_DATE`, tc.`REGION_ID`, 
            SUM(tc.`FORECASTED_CONSUMPTION`) `FORECASTED_CONSUMPTION`, SUM(tc.`ACTUAL_CONSUMPTION`) `ACTUAL_CONSUMPTION`, null `ADJUSTMENT`, null `STOCK`, 
            null `MANUAL_PLANNED_SHIPMENT`, null `MANUAL_SUBMITTED_SHIPMENT`, null `MANUAL_APPROVED_SHIPMENT`, null `MANUAL_SHIPPED_SHIPMENT`, null `MANUAL_RECEIVED_SHIPMENT`, null `MANUAL_ONHOLD_SHIPMENT`, 
            null `ERP_PLANNED_SHIPMENT`, null `ERP_SUBMITTED_SHIPMENT`, null `ERP_APPROVED_SHIPMENT`, null `ERP_SHIPPED_SHIPMENT`, null `ERP_RECEIVED_SHIPMENT`, null `ERP_ONHOLD_SHIPMENT`
        FROM (
            SELECT 
                c.`PROGRAM_ID`, ct.`PLANNING_UNIT_ID`, ct.`CONSUMPTION_DATE`, ct.`REGION_ID`, 
                ct.`ACTIVE`, 
                SUM(IF(ct.`ACTUAL_FLAG`=0, ct.`CONSUMPTION_QTY`, null)) `FORECASTED_CONSUMPTION`,
                SUM(IF(ct.`ACTUAL_FLAG`=1, ct.`CONSUMPTION_QTY`, null)) `ACTUAL_CONSUMPTION`
            FROM (
                SELECT c.`CONSUMPTION_ID`, MAX(ct.`VERSION_ID`) `MAX_VERSION_ID` FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.`CONSUMPTION_ID`=ct.`CONSUMPTION_ID` WHERE c.`PROGRAM_ID`=@programId AND ct.`VERSION_ID`<=@versionId AND ct.`CONSUMPTION_TRANS_ID` IS NOT NULL GROUP BY c.`CONSUMPTION_ID`
            ) tc
            LEFT JOIN rm_consumption c ON c.`CONSUMPTION_ID`=tc.`CONSUMPTION_ID`
            LEFT JOIN rm_consumption_trans ct ON c.`CONSUMPTION_ID`=ct.`CONSUMPTION_ID` AND tc.`MAX_VERSION_ID`=ct.`VERSION_ID`
            WHERE ct.`ACTIVE`
            GROUP BY c.`PROGRAM_ID`, ct.`PLANNING_UNIT_ID`, ct.`CONSUMPTION_DATE`, ct.`REGION_ID`
        ) tc 
        GROUP BY tc.`PROGRAM_ID`, tc.`PLANNING_UNIT_ID`, tc.`CONSUMPTION_DATE`, tc.`REGION_ID`

        UNION

        SELECT 
            s.`PROGRAM_ID`, st.`PLANNING_UNIT_ID`, LEFT(COALESCE(st.`RECEIVED_DATE`, st.`EXPECTED_DELIVERY_DATE`),7) `TRANS_DATE`, null `REGION_ID`,
            null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, null `ADJUSTMENT`, null `STOCK`,
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=1, st.`SHIPMENT_QTY`, null )) `MANUAL_PLANNED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=3, st.`SHIPMENT_QTY`, null )) `MANUAL_SUBMITTED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=4, st.`SHIPMENT_QTY`, null )) `MANUAL_APPROVED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID` IN (5,6), st.`SHIPMENT_QTY`, null )) `MANUAL_SHIPPED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=7, st.`SHIPMENT_QTY`, null )) `MANUAL_RECEIVED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=9, st.`SHIPMENT_QTY`, null )) `MANUAL_ONHOLD_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=1, st.`SHIPMENT_QTY`, null )) `ERP_PLANNED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=3, st.`SHIPMENT_QTY`, null )) `ERP_SUBMITTED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=4, st.`SHIPMENT_QTY`, null )) `ERP_APPROVED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID` IN (5,6), st.`SHIPMENT_QTY`, null )) `ERP_SHIPPED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID` = 7, st.`SHIPMENT_QTY`, null )) `ERP_RECEIVED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=9, st.`SHIPMENT_QTY`, null )) `ERP_ONHOLD_SHIPMENT`
        FROM (
            SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
        ) ts
        LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
        WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 
        GROUP BY s.PROGRAM_ID, st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)

        UNION

        SELECT 
            i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`, it.REGION_ID,
            null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, SUM(it.ADJUSTMENT_QTY*rcpu.MULTIPLIER) `ADJUSTMENT`,  SUM(it.ACTUAL_QTY*rcpu.MULTIPLIER) `STOCK`,
            null `MANUAL_PLANNED_SHIPMENT`, null `MANUAL_SUBMITTED_SHIPMENT`, null `MANUAL_APPROVED_SHIPMENT`, null `MANUAL_SHIPPED_SHIPMENT`, null `MANUAL_RECEIVED_SHIPMENT`, null `MANUAL_ONHOLD_SHIPMENT`, 
            null `ERP_PLANNED_SHIPMENT`, null `ERP_SUBMITTED_SHIPMENT`, null `ERP_APPROVED_SHIPMENT`, null `ERP_SHIPPED_SHIPMENT`, null `ERP_RECEIVED_SHIPMENT`, null `ERP_ONHOLD_SHIPMENT`
        FROM (
            SELECT i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID
        ) ti
        LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID
        LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID
        LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
        WHERE it.ACTIVE
        GROUP BY i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, it.INVENTORY_DATE, it.REGION_ID
            
    ) AS o GROUP BY o.PROGRAM_ID, o.PLANNING_UNIT_ID, o.TRANS_DATE, o.REGION_ID;
           
    -- Update the UseActualConsumption field = 1 
    -- IF All Regions have reported Consumption or if Sum(ActualConsumption)>Sum(ForecastedConsumption)
    -- ELSE UseActualConsumption field = 0
    UPDATE tmp_nsp tn LEFT JOIN (SELECT tn.PLANNING_UNIT_ID, tn.TRANS_DATE, SUM(IF(tn.ACTUAL_CONSUMPTION IS NOT NULL, 1,0)) `COUNT_OF_ACTUAL_CONSUMPTION`, SUM(tn.ACTUAL_CONSUMPTION) `TOTAL_ACTUAL_CONSUMPTION`, SUM(tn.FORECASTED_CONSUMPTION) `TOTAL_FORECASTED_CONSUMPTION` FROM tmp_nsp tn WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL GROUP BY tn.PLANNING_UNIT_ID, tn.TRANS_DATE) rcount ON tn.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND tn.TRANS_DATE=rcount.TRANS_DATE SET tn.USE_ACTUAL_CONSUMPTION=IF(rcount.COUNT_OF_ACTUAL_CONSUMPTION=@regionCount, 1, IF(rcount.TOTAL_ACTUAL_CONSUMPTION>rcount.TOTAL_FORECASTED_CONSUMPTION, 1, 0)) WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL;
        
    -- Update the RegionStockCount field based on the number of Regions that have reported Stock
    UPDATE tmp_nsp tn LEFT JOIN (SELECT tn.PLANNING_UNIT_ID, tn.TRANS_DATE, COUNT(tn.STOCK) CNT FROM tmp_nsp tn WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL GROUP BY tn.PLANNING_UNIT_ID, tn.TRANS_DATE, tn.REGION_ID) rcount ON tn.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND tn.TRANS_DATE=rcount.TRANS_DATE SET tn.REGION_STOCK_COUNT = rcount.CNT WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL;
        
    -- To get the range for AMC calculations
    -- SELECT MIN(sp.TRANS_DATE), ADDDATE(MAX(sp.TRANS_DATE), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH) INTO @startMonth, @stopMonth  FROM rm_supply_plan sp LEFT JOIN rm_program_planning_unit ppu ON sp.PROGRAM_ID=ppu.PROGRAM_ID AND sp.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID WHERE sp.PROGRAM_ID=@programId and sp.VERSION_ID=@versionId;
    
    SELECT 
        tn.PLANNING_UNIT_ID, tn.TRANS_DATE, IFNULL(ppu.SHELF_LIFE, 24) SHELF_LIFE, tn.REGION_ID, tn.FORECASTED_CONSUMPTION, tn.ACTUAL_CONSUMPTION,
        tn.USE_ACTUAL_CONSUMPTION, tn.ADJUSTMENT, tn.STOCK, tn.REGION_STOCK_COUNT, tn.REGION_COUNT,
        tn.MANUAL_PLANNED_SHIPMENT, tn.MANUAL_SUBMITTED_SHIPMENT, tn.MANUAL_APPROVED_SHIPMENT, tn.MANUAL_SHIPPED_SHIPMENT, tn.MANUAL_RECEIVED_SHIPMENT, tn.MANUAL_ONHOLD_SHIPMENT, 
        tn.ERP_PLANNED_SHIPMENT, tn.ERP_SUBMITTED_SHIPMENT, tn.ERP_APPROVED_SHIPMENT, tn.ERP_SHIPPED_SHIPMENT, tn.ERP_RECEIVED_SHIPMENT, tn.ERP_ONHOLD_SHIPMENT
    FROM tmp_nsp tn LEFT JOIN rm_program_planning_unit ppu ON tn.PROGRAM_ID=ppu.PROGRAM_ID AND tn.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId -- AND tn.PLANNING_UNIT_ID=8293
    ;

END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `programProductCatalog`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `programProductCatalog`(VAR_PROGRAM_ID INT(10), VAR_PRODUCT_CATEGORY_ID INT, VAR_TRACER_CATEGORY_ID INT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 1
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- Program Id must be a valid Program Id, cannot be -1 (Any)
    -- Return the list of Program-Planning Units and their corresponding fields
    -- TracerCategory and ProductCategory are used as Filters for the report and can be = -1 which means Any

	SET @programId = VAR_PROGRAM_ID;
    SET @productCategoryId = VAR_PRODUCT_CATEGORY_ID;
    SET @tracerCategoryId = VAR_TRACER_CATEGORY_ID;
    SET @pcSortOrder = '';
    IF @productCategoryId != -1 THEN
        SELECT pc.SORT_ORDER INTO @pcSortOrder FROM rm_product_category pc WHERE pc.PRODUCT_CATEGORY_ID=@productCategoryId;
    END IF;
	
    SELECT 
		p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 
		pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_Fr `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`,
		tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`,
		fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, 
		fu.GENERIC_LABEL_ID `GENERIC_LABEL_ID`, ful.LABEL_EN `GENERIC_LABEL_EN`, ful.LABEL_FR `GENERIC_LABEL_FR`, ful.LABEL_SP `GENERIC_LABEL_SP`, ful.LABEL_PR `GENERIC_LABEL_PR`,
		fuu.UNIT_ID `FUNIT_ID`, fuu.UNIT_CODE `FUNIT_CODE`, fuu.LABEL_ID `FUNIT_LABEL_ID`, fuu.LABEL_EN `FUNIT_LABEL_EN`, fuu.LABEL_FR `FUNIT_LABEL_FR`, fuu.LABEL_SP `FUNIT_LABEL_SP`, fuu.LABEL_PR `FUNIT_LABEL_PR`,
		pu.PLANNING_UNIT_ID, pu.MULTIPLIER `FORECASTING_TO_PLANNING_UNIT_MULTIPLIER`, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
		puu.UNIT_ID `PUNIT_ID`, puu.UNIT_CODE `PUNIT_CODE`, puu.LABEL_ID `PUNIT_LABEL_ID`, puu.LABEL_EN `PUNIT_LABEL_EN`, puu.LABEL_FR `PUNIT_LABEL_FR`, puu.LABEL_SP `PUNIT_LABEL_SP`, puu.LABEL_PR `PUNIT_LABEL_PR`,
		ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.CATALOG_PRICE, ppu.SHELF_LIFE, IF(ppu.ACTIVE AND pu.ACTIVE, TRUE, FALSE) `ACTIVE`
	FROM rm_program_planning_unit ppu 
	LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID
	LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
	LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
	LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID
	LEFT JOIN ap_label ful ON fu.GENERIC_LABEL_ID=ful.LABEL_ID
	LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID
	LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
	LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID
	WHERE 
		ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE 
		AND (pc.SORT_ORDER LIKE  CONCAT(@pcSortOrder,'%'))
		AND (tc.TRACER_CATEGORY_ID = @tracerCategoryId OR @tracerCategoryId=-1);

END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `stockStatusMatrix`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusMatrix`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS VARCHAR(255), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 18
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
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
--    SET @planningUnitId = VAR_PLANNING_UNIT_ID;
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
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "    AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY ppu.PLANNING_UNIT_ID, YEAR(mn.MONTH)");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `getStockStatusForProgram`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getStockStatusForProgram`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_DT DATE, VAR_TRACER_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 28
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
	-- dt is the month for which you want to run the report
    -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
    -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    -- if a Month does not have Consumption then it is excluded from the AMC calculations
    -- MinMonthsOfStock is Max of MinMonth of Stock taken from the Program-planning Unit and 3
    -- MaxMonthsOfStock is Min of Min of MinMonthOfStock+ReorderFrequency and 15
    -- tracerCategoryIds is a list of the Tracer Category Ids that the user wants to run the report for. Empty will indicate they want to run it for all Tracer Categories.
    
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @dt = VAR_DT;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    ppu.MIN_MONTHS_OF_STOCK, (ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS) `MAX_MONTHS_OF_STOCK`, ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.CLOSING_BALANCE,0), IFNULL(amc.CLOSING_BALANCE_WPS,0)) `STOCK`,  ");
    SET @sqlString = CONCAT(@sqlString, "    IFNULL(amc.AMC,0) `AMC`,  ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.MOS,0), IFNULL(amc.MOS_WPS,0)) `MoS`, ");
    SET @sqlString = CONCAT(@sqlString, "    a3.LAST_STOCK_DATE `STOCK_COUNT_DATE` ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=@programId AND amc.VERSION_ID=@versionId AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND amc.TRANS_DATE=@dt ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT a2.PLANNING_UNIT_ID, MAX(a2.TRANS_DATE) LAST_STOCK_DATE FROM rm_supply_plan_amc a2 WHERE a2.PROGRAM_ID=@programId AND a2.VERSION_ID=@versionId AND a2.TRANS_DATE<=@dt AND a2.REGION_COUNT=a2.REGION_COUNT_FOR_STOCK GROUP BY a2.PLANNING_UNIT_ID) a3 ON amc.PLANNING_UNIT_ID=a3.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_TRACER_CATEGORY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, " AND fu.TRACER_CATEGORY_ID IN (",VAR_TRACER_CATEGORY_IDS,") ");
    END IF;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `globalConsumption`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `globalConsumption`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REPORT_VIEW INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 3
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- realmId must be a valid realm that you want to run this Global report for
    -- RealmCountryIds is the list of Countries that you want to run the report for. Empty means all Countries
    -- ProgramIds is the list of Programs that you want to run the report for. Empty means all Programs
    -- PlanningUnitIds is the list of PlanningUnits that you want to run the report for. Empty means all Planning Units
    -- startDate and stopDate are the range between which you want to run the report for`
    -- reportView = 1 shows the Consumption in PlanningUnits
    -- reportView = 2 shows the Consumption in ForecastingUnits
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @reportView = VAR_REPORT_VIEW;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
	
    SET @sqlString = "";
    
	SET @sqlString = CONCAT(@sqlString, "SELECT sma.TRANS_DATE, rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`, SUM(IF(@reportView=1, sma.FORECASTED_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY*pu.MULTIPLIER)) `FORECASTED_CONSUMPTION`, SUM(IF(@reportView=1, sma.ACTUAL_CONSUMPTION_QTY, sma.ACTUAL_CONSUMPTION_QTY*pu.MULTIPLIER)) `ACTUAL_CONSUMPTION` ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_supply_plan_amc sma ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        sma.PROGRAM_ID, ");
    SET @sqlString = CONCAT(@sqlString, "        MAX(sma.VERSION_ID) MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "    FROM rm_supply_plan_amc sma ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_program_version pv ON sma.PROGRAM_ID=pv.PROGRAM_ID AND sma.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE TRUE ");
    IF @approvedSupplyPlanOnly = 1 THEN
        SET @sqlString = CONCAT(@sqlString, "        AND pv.VERSION_TYPE_ID=2 ");
        SET @sqlString = CONCAT(@sqlString, "        AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND sma.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
	END IF;
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY sma.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS f ON sma.PROGRAM_ID=f.PROGRAM_ID AND sma.VERSION_ID=f.MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON sma.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_planning_unit pu ON sma.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON sma.PROGRAM_ID=ppu.PROGRAM_ID AND sma.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    sma.TRANS_DATE BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "    AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "    AND f.PROGRAM_ID IS NOT NULL ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND sma.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID in (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY sma.TRANS_DATE, rc.REALM_COUNTRY_ID ");
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;

END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `forecastMetricsComparision`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `forecastMetricsComparision`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_PREVIOUS_MONTHS INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 5
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- realmId since it is a Global report need to include Realm
    -- startDate - date that the report is to be run for
    -- realmCountryIds list of countries that we need to run the report for
    -- programIds is the list of programs that we need to run the report for
    -- planningUnitIds is the list of planningUnits that we need to run the report for
    -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
    -- current month is always included in the calculation
    -- only consider those months that have both a Forecasted and Actual consumption
    -- WAPE Formulae
    -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months)) 

	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @previousMonths = VAR_PREVIOUS_MONTHS;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    
    SET @sqlString = "";
    
	SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    spa.TRANS_DATE, ");
    SET @sqlString = CONCAT(@sqlString, "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR  `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, spa.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION`, ");
--    SET @sqlString = CONCAT(@sqlString, "    c2.ACTUAL_CONSUMPTION_TOTAL, c2.DIFF_CONSUMPTION_TOTAL, c2.MONTH_COUNT, IF(spa.ACTUAL=1 AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, c2.DIFF_CONSUMPTION_TOTAL*100/c2.ACTUAL_CONSUMPTION_TOTAL, null) FORECAST_ERROR ");
    SET @sqlString = CONCAT(@sqlString, "    c2.ACTUAL_CONSUMPTION_TOTAL, c2.DIFF_CONSUMPTION_TOTAL, c2.MONTH_COUNT, c2.DIFF_CONSUMPTION_TOTAL*100/c2.ACTUAL_CONSUMPTION_TOTAL FORECAST_ERROR ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "           WHERE TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "               AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "               AND spa.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "           GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE=@startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        @startDate `TRANS_DATE`, p.PROGRAM_ID, pu.PLANNING_UNIT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, spa.ACTUAL_CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(spa.ACTUAL_CONSUMPTION_QTY-spa.FORECASTED_CONSUMPTION_QTY), null)) `DIFF_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, 1, 0)) `MONTH_COUNT` ");
    SET @sqlString = CONCAT(@sqlString, "    FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "        ( ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "            AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "            AND spa.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    ) f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE ");
    SET @sqlString = CONCAT(@sqlString, "        TRUE ");
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND p.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY ppu.PROGRAM_ID, ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") c2 ON spa.PROGRAM_ID=c2.PROGRAM_ID AND spa.TRANS_DATE=c2.TRANS_DATE AND spa.PLANNING_UNIT_ID=c2.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    TRUE AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND p.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_fundingSourceSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_fundingSourceSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_FUNDING_SOURCE_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_SHIPMENT_STATUS_IDS VARCHAR(255), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 1 Funding Source Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @realmId = VAR_REALM_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    
	SET @sqlString = "";
 
	SET @sqlString = CONCAT(@sqlString, "SELECT fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, SUM(s1.TOTAL_COST) `TOTAL_COST` ");
	SET @sqlString = CONCAT(@sqlString, "FROM ( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, st.FUNDING_SOURCE_ID, (IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD + IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) TOTAL_COST ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE");
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND p.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	IF LENGTH(VAR_SHIPMENT_STATUS_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
	END IF;
	IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
	END IF;
	IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,") ");
	END IF;
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) s1 ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_funding_source fs ON s1.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY fs.FUNDING_SOURCE_ID");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_planningUnitSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_planningUnitSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT, VAR_SHIPMENT_STATUS_IDS VARCHAR(255), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 2 PlanningUnit Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @realmId = VAR_REALM_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    
	SET @sqlString = "";
 
	SET @sqlString = CONCAT(@sqlString, "SELECT pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.MULTIPLIER, SUM(IF(s1.SHIPMENT_STATUS_ID IN (1,2,3,9),s1.SHIPMENT_QTY,0)) `PLANNED_SHIPMENT_QTY`, SUM(IF(s1.SHIPMENT_STATUS_ID IN (4,5,6,7),s1.SHIPMENT_QTY,0)) `ORDERED_SHIPMENT_QTY` ");
	SET @sqlString = CONCAT(@sqlString, "FROM ( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, st.PLANNING_UNIT_ID, st.SHIPMENT_STATUS_ID, st.SHIPMENT_QTY ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE");
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND p.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	IF LENGTH(VAR_SHIPMENT_STATUS_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
	END IF;
	IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
	END IF;
	IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,") ");
	END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) s1 ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_planning_unit pu ON s1.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY pu.PLANNING_UNIT_ID");
 
 PREPARE S1 FROM @sqlString; 
 EXECUTE S1;
END$$

DELIMITER ;





USE `fasp`;
DROP procedure IF EXISTS `shipmentOverview_procurementAgentSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentOverview_procurementAgentSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT, VAR_SHIPMENT_STATUS_IDS VARCHAR(255), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 20 - Part 3 ProcurementAgent Split
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 -- Only Month and Year will be considered for StartDate and StopDate
 -- Must be a valid RealmId that you want to see the Global Report for
 -- RealmCountryIds is the list of Realm Countries you want to run the report for 
 -- Empty RealmCountryIds means you want to run the report for all the Realm Countries
 -- ProgramIds is the list of the Programs that you want to run the report for
 -- Empty ProgramIds means you want to run the report for all the Programs in the Realm
 -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
 -- Empty PlanningUnitIds means you want to run the report for all the Planning Units
 -- FundingSourceIds is the list of Funding Sources that you want to run the report for
 -- Empty FundingSourceIds means you want to run for all the Funding Sources
 -- ShipmentStatusIds is the list of ShipmentStatuses that you want to run the report for
 -- Empty ShipmentStatusIds means you want to run for all Shipment Statuses
 
	DECLARE done INT DEFAULT 0;
    DECLARE procurementAgentId INT(10) DEFAULT 0;
    DECLARE procurementAgentCode VARCHAR(10) DEFAULT "";
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE procurement_cursor CURSOR FOR SELECT PROCUREMENT_AGENT_ID, PROCUREMENT_AGENT_CODE FROM rm_procurement_agent WHERE REALM_ID=VAR_REALM_ID;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @realmId = VAR_REALM_ID;
	SET @sqlStringProcurementAgent = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
 	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    SET done = 0;
	OPEN procurement_cursor;
    getProcurementAgent: LOOP
		FETCH procurement_cursor into procurementAgentId, procurementAgentCode;
        IF done = 1 THEN 
			LEAVE getProcurementAgent;
		END IF;
		SET @sqlStringProcurementAgent = CONCAT(@sqlStringProcurementAgent, " ,SUM(IF(s1.PROCUREMENT_AGENT_ID=",procurementAgentId,", s1.SHIPMENT_QTY, 0)) `PA_",procurementAgentCode,"` ");
	END LOOP getProcurementAgent;
    
	SET @sqlString = "";
 
	SET @sqlString = CONCAT(@sqlString, "SELECT pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.MULTIPLIER, SUM(s1.SHIPMENT_QTY) `SHIPMENT_QTY` ");
	SET @sqlString = CONCAT(@sqlString, @sqlStringProcurementAgent);
	SET @sqlString = CONCAT(@sqlString, "FROM ( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, st.PLANNING_UNIT_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_QTY ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId AND ppu.ACTIVE AND pu.ACTIVE");
        IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "       AND p.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	IF LENGTH(VAR_SHIPMENT_STATUS_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
	END IF;
	IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
	END IF;
	IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,") ");
	END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) s1 ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_planning_unit pu ON s1.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY pu.PLANNING_UNIT_ID");
 
	PREPARE S1 FROM @sqlString;
	EXECUTE S1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `shipmentDetails`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate
    -- Only a single ProgramId can be selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program

	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	pu.MULTIPLIER, ");
	SET @sqlString = CONCAT(@sqlString, "	s.SHIPMENT_ID, ");
	SET @sqlString = CONCAT(@sqlString, "	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	st.SHIPMENT_QTY, st.ORDER_NO, st.LOCAL_PROCUREMENT, st.ERP_FLAG, st.EMERGENCY_ORDER, ");
	SET @sqlString = CONCAT(@sqlString, "	COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, ");
	SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, ");
	SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, ");
	SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD + IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `TOTAL_COST`, ");
	SET @sqlString = CONCAT(@sqlString, "	st.NOTES ");
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT ");
	SET @sqlString = CONCAT(@sqlString, "		s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
	SET @sqlString = CONCAT(@sqlString, "	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, ") AS s ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa on st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON st.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "WHERE ");
	SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND ppu.ACTUVE AND pu.ACTIVE");
	SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "	AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
-- 	SET @sqlString = CONCAT(@sqlString, "GROUP BY st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentDetailsFundingSource`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsFundingSource`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10))
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 b
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate
    -- Only a single ProgramId can be selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program

	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    SET @reportView = VAR_REPORT_VIEW;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	COUNT(st.SHIPMENT_ID) `ORDER_COUNT`, ");
    SET @sqlString = CONCAT(@sqlString, "	IF(@reportView=1, SUM(st.SHIPMENT_QTY), SUM(st.SHIPMENT_QTY*pu.MULTIPLIER)) `QUANTITY`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST` ");
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT ");
	SET @sqlString = CONCAT(@sqlString, "		s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
	SET @sqlString = CONCAT(@sqlString, "	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, ") AS s ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "WHERE ");
	SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "	AND st.SHIPMENT_STATUS_ID!=8 ");
	SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "	AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, " GROUP BY st.FUNDING_SOURCE_ID");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentDetailsMonth`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsMonth`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 c
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate
    -- Only a single ProgramId can be selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program

	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   mn.MONTH, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`PLANNED_COST`,0)) `PLANNED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SUBMITTED_COST`,0)) `SUBMITTED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`APPROVED_COST`,0)) `APPROVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SHIPPED_COST`,0)) `SHIPPED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ARRIVED_COST`,0)) `ARRIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`RECEIVED_COST`,0)) `RECEIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ONHOLD_COST`,0)) `ONHOLD_COST` ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "   ( ");
    SET @sqlString = CONCAT(@sqlString, "   SELECT ");
    SET @sqlString = CONCAT(@sqlString, "       CONCAT(LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7),'-01') `DT`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=1, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `PLANNED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=3, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SUBMITTED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=4, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `APPROVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=5, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SHIPPED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=6, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ARRIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=7, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `RECEIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=8, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ONHOLD_COST` ");
    SET @sqlString = CONCAT(@sqlString, "    FROM ");
    SET @sqlString = CONCAT(@sqlString, "        ( ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT ");
    SET @sqlString = CONCAT(@sqlString, "            s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "        FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "            AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString, "            AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    ) AS s ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE ");
    SET @sqlString = CONCAT(@sqlString, "        st.ACTIVE  AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "        AND st.SHIPMENT_STATUS_ID!=8 ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "	    AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "        AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, ") AS s1 ON mn.MONTH =s1.DT ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY mn.MONTH");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `procurementAgentShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `procurementAgentShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 13
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- procurementAgentIds is a list of the ProcurementAgents that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,")) ");
    END IF;
	
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PROCUREMENT_AGENT_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `fundingSourceShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `fundingSourceShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 15
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- fundingSourceIds is a list of the FundingSources that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")) ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
	
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.FUNDING_SOURCE_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `aggregateShipmentByProduct`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `aggregateShipmentByProduct`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 24 
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- includePlannedShipments = 0 means the report will include all shipments that are Active and not Cancelled
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `annualShipmentCost`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `annualShipmentCost`(VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PROCUREMENT_AGENT_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_FUNDING_SOURCE_IDS TEXT, VAR_SHIPMENT_STATUS_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REPORT_BASED_ON INT)
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 22 
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- reportBasedOn = 1 means the report will be run using startDate and stopDate based on Shipped Date
    -- reportBasedOn = 2 means the report will be run using startDate and stopDate based on Delivered date if available or Expected Delivery Date
    -- If ProcurementAgent has not been selected as yet in the Shipment, that Shipment will be excluded
    -- 
    -- 
	SET @programId = VAR_PROGRAM_ID;
	SET @startDate = CONCAT(VAR_START_DATE, ' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @reportBasedOn=VAR_REPORT_BASED_ON; -- 1 = Shipped and 2 = RECEIVED
    SET @versionId = VAR_VERSION_ID;
    DROP TABLE IF EXISTS `tmp_year`;
	CREATE TABLE `tmp_year` (`YR` INT(10) UNSIGNED NOT NULL,PRIMARY KEY (`YR`)) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SET @dtPtr = @startDate;
    SET @sql2 = "";
	SET @isFirst = TRUE;
    REPEAT
		INSERT INTO tmp_year VALUES (YEAR(@dtPtr));
        SET @sql2 = CONCAT(@sql2, ", SUM(IF(b1.SHIP_YR=",YEAR(@dtPtr),", (IFNULL(b1.PRODUCT_COST,0)+IFNULL(b1.FREIGHT_COST,0)), 0)) `YR-",YEAR(@dtPtr),"` ");
        SET @dtPtr = DATE_ADD(@dtPtr, INTERVAL 1 YEAR);
	UNTIL YEAR(@dtPtr)>YEAR(@stopDate) END REPEAT;
    
    SET @sql1 = "";
	SET @sql1 = CONCAT(@sql1, "	SELECT ");
	SET @sql1 = CONCAT(@sql1, "		b1.PROCUREMENT_AGENT_ID, b1.PROCUREMENT_AGENT_CODE, b1.PROCUREMENT_AGENT_LABEL_ID, b1.PROCUREMENT_AGENT_LABEL_EN, b1.PROCUREMENT_AGENT_LABEL_FR, b1.PROCUREMENT_AGENT_LABEL_SP, b1.PROCUREMENT_AGENT_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.FUNDING_SOURCE_ID, b1.FUNDING_SOURCE_CODE, b1.FUNDING_SOURCE_LABEL_ID, b1.FUNDING_SOURCE_LABEL_EN, b1.FUNDING_SOURCE_LABEL_FR, b1.FUNDING_SOURCE_LABEL_SP, b1.FUNDING_SOURCE_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.PLANNING_UNIT_ID, b1.PLANNING_UNIT_LABEL_ID, b1.PLANNING_UNIT_LABEL_EN, b1.PLANNING_UNIT_LABEL_FR, b1.PLANNING_UNIT_LABEL_SP, b1.PLANNING_UNIT_LABEL_PR ");
	SET @sql1 = CONCAT(@sql1, "		",@sql2);
   	SET @sql1 = CONCAT(@sql1, " FROM tmp_year y ");
	SET @sql1 = CONCAT(@sql1, " LEFT JOIN ( ");
	SET @sql1 = CONCAT(@sql1, " 	SELECT ");
	IF @reportBasedOn=1 THEN 
		SET @sql1 = CONCAT(@sql1, " 	YEAR(st.PLANNED_DATE) SHIP_YR, ");
	ELSE
		SET @sql1 = CONCAT(@sql1, " 	YEAR(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)) SHIP_YR, ");
	END IF;
	SET @sql1 = CONCAT(@sql1, " 		pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
	SET @sql1 = CONCAT(@sql1, " 		fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD `PRODUCT_COST`, st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD `FREIGHT_COST` ");
    SET @sql1 = CONCAT(@sql1, " 	FROM ");
    SET @sql1 = CONCAT(@sql1, "			(");
    SET @sql1 = CONCAT(@sql1, "				SELECT ");
	SET @sql1 = CONCAT(@sql1, "					s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sql1 = CONCAT(@sql1, "				FROM rm_shipment s ");
	SET @sql1 = CONCAT(@sql1, "				LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sql1 = CONCAT(@sql1, "				WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sql1 = CONCAT(@sql1, "				GROUP BY s.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, "		) s ");
	SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment_trans  st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_budget b on st.BUDGET_ID=b.BUDGET_ID ");
	SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_funding_source fs on fs.FUNDING_SOURCE_ID=b.FUNDING_SOURCE_ID ");
	SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_procurement_agent pa on pa.PROCUREMENT_AGENT_ID=st.PROCUREMENT_AGENT_ID ");
	SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_planning_unit pu on pu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_program_planning_unit ppu on ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sql1 = CONCAT(@sql1, " 	WHERE ");
	SET @sql1 = CONCAT(@sql1, " 		st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sql1 = CONCAT(@sql1, " 		AND (st.SHIPMENT_STATUS_ID != 8) ");
	SET @sql1 = CONCAT(@sql1, " 		AND (@programId = -1 OR s.PROGRAM_ID = @programId) ");
	IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,") ");
    END IF;
	IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
	IF LENGTH(VAR_SHIPMENT_STATUS_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
    END IF;
     
	-- If you want on Order Date change here
	IF @reportBasedOn=1 THEN 
		SET @sql1 = CONCAT(@sql1, " 	AND st.PLANNED_DATE BETWEEN @startDate AND @stopDate ");
	ELSE
		SET @sql1 = CONCAT(@sql1, " 	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	END IF;
	IF LENGTH(VAR_FUNDING_SOURCE_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND b.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")   ");
    END IF;
    
	SET @sql1 = CONCAT(@sql1, " 	GROUP BY s.SHIPMENT_ID ");
	SET @sql1 = CONCAT(@sql1, " ) as b1 ON y.YR=b1.SHIP_YR ");
	SET @sql1 = CONCAT(@sql1, " GROUP BY b1.PROCUREMENT_AGENT_ID, b1.FUNDING_SOURCE_ID, b1.PLANNING_UNIT_ID ");
	SET @sql1 = CONCAT(@sql1, " HAVING PROCUREMENT_AGENT_ID IS NOT NULL ");
  	-- select @sql1;
 	PREPARE s1 FROM @sql1;
 	EXECUTE s1;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `getExpiredStock`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getExpiredStock`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 10
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId cannot be -1 (All) it must be a valid ProgramId
    -- versionId can be -1 or a valid VersionId for that Program. If it is -1 then the last committed Version is automatically taken.
    -- StartDate is the date that you want to run the report for
    -- StopDate is the date that you want to run the report for
    -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
    -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SELECT 
        p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, 
        bi.BATCH_ID, bi.BATCH_NO, bi.AUTO_GENERATED, bi.EXPIRY_DATE, bi.CREATED_DATE, IF (@includePlannedShipments=1, spbq.EXPIRED_STOCK, spbq.EXPIRED_STOCK_WPS) `EXPIRED_STOCK`,
        timestampdiff(MONTH, CONCAT(LEFT(bi.CREATED_DATE,7),"-01"),bi.EXPIRY_DATE) `SHELF_LIFE`
    FROM rm_supply_plan_batch_qty spbq 
    LEFT JOIN rm_batch_info bi ON spbq.BATCH_ID=bi.BATCH_ID 
    LEFT JOIN vw_program p ON spbq.PROGRAM_ID=p.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON spbq.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
    WHERE ppu.ACTIVE AND pu.ACTIVE AND spbq.PROGRAM_ID=@programId AND spbq.VERSION_ID=@versionId AND spbq.TRANS_DATE BETWEEN @startDate AND @stopDate AND (@includePlannedShipments=1 AND spbq.EXPIRED_STOCK>0 OR @includePlannedShipments=0 AND spbq.EXPIRED_STOCK_WPS>0);
    
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `costOfInventory`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `costOfInventory`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(11), VAR_DT DATE, INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 8
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	-- ProgramId cannot be -1 (All) must be a valid ProgramId
    -- Version Id can be -1 or a Valid Version Id. If it is -1 then the Most recent committed Version is automatically taken.
    -- Dt is the date that you want to run the report for
    -- Include Planned shipments = 1 means that Shipments that are in the Draft, Planned or Submitted stage will also be considered in the calculations
    -- Include Planned shipments = 0 means that Shipments that are in the Draft, Planned or Submitted stage will not be considered in the calculations
    -- Price per unit is taken from the ProgramPlanningUnit level
    -- Cost = Closing inventory for that Planning Unit x Catalog Price
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @startDate = VAR_DT;
    SET @includePlannedShipments = INCLUDE_PLANNED_SHIPMENTS;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
        ppu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
        ppu.CATALOG_PRICE, IFNULL(IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),0) STOCK, 
        IFNULL(IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),0)*ppu.CATALOG_PRICE `COST`, ppu.CATALOG_PRICE, IF(amc.REGION_COUNT>amc.REGION_COUNT_FOR_STOCK,1,0) CALCULATED
    FROM rm_program_planning_unit ppu
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_supply_plan_amc amc ON ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND amc.PROGRAM_ID=@programId AND amc.VERSION_ID=@versionId AND amc.TRANS_DATE=@startDate
    WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE;

END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `inventoryTurns`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `inventoryTurns`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 9
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId cannot be -1 (All) it must be a valid ProgramId
    -- versionId can be -1 or a valid VersionId for that Program. If it is -1 then the last committed Version is automatically taken.
    -- StartDate is the date that you want to run the report for
    -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
    -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
    -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period

	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @startDate = VAR_START_DATE;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
		ppu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
		SUM(s2.CONSUMPTION_QTY) `TOTAL_CONSUMPTION`, 
		AVG(s2.STOCK) `AVG_STOCK`,
		COUNT(s2.CONSUMPTION_QTY) `NO_OF_MONTHS`,
		SUM(s2.CONSUMPTION_QTY)/AVG(s2.STOCK) `INVENTORY_TURNS`
	FROM rm_program_planning_unit ppu 
	LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN 
        (
        SELECT 
            spa.TRANS_DATE, spa.PLANNING_UNIT_ID, 
            SUM(IF(spa.ACTUAL IS NULL, NULL, IF(spa.ACTUAL=1, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY))) `CONSUMPTION_QTY`,
            SUM(IF(@includePlannedShipments, spa.CLOSING_BALANCE, spa.CLOSING_BALANCE_WPS)) `STOCK`
        FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=@programId AND spa.VERSION_ID=@versionId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL 11 MONTH) AND @startDate
        GROUP BY spa.TRANS_DATE, spa.PLANNING_UNIT_ID
        HAVING `CONSUMPTION_QTY` IS NOT NULL
    ) s2 ON ppu.PLANNING_UNIT_ID=s2.PLANNING_UNIT_ID
	WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE 
	GROUP BY ppu.PLANNING_UNIT_ID;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `stockAdjustmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockAdjustmentReport`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 12
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
-- VAR_PROGRAM_ID must be a valid Program cannot be All i.e. -1
-- VAR_VERSION_ID must be a valid Version for the PROGRAM_ID or can be -1 in which case it will default to the latest Version of the Program, 
-- VAR_START_DATE AND VAR_STOP_DATE are the Date range between which the Stock Adjustment will be run. Only the month and year are considered while running the report
-- VAR_PLANNING_UNIT_IDS are the Quoted, Comma separated list of the Planning Unit Ids that you want to run the report for. If you want to run it for all Planning Units in the Program leave it empty

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
IF @versionID = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
END IF;
SET @startDt = LEFT(VAR_START_DATE,7);
SET @stopDt = LEFT(VAR_STOP_DATE,7);

SET @sqlString = "";

SET @sqlString = CONCAT(@sqlString, "SELECT ");
SET @sqlString = CONCAT(@sqlString, "	p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,");
SET @sqlString = CONCAT(@sqlString, "   pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,");
SET @sqlString = CONCAT(@sqlString, "   ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`, ");
SET @sqlString = CONCAT(@sqlString, "	it.INVENTORY_DATE, it.ADJUSTMENT_QTY*rcpu.MULTIPLIER `STOCK_ADJUSTMENT_QTY`, lmb.USER_ID `LAST_MODIFIED_BY_USER_ID`, lmb.USERNAME `LAST_MODIFIED_BY_USERNAME`, it.LAST_MODIFIED_DATE, it.NOTES");
SET @sqlString = CONCAT(@sqlString, " FROM ");
SET @sqlString = CONCAT(@sqlString, "	( ");
SET @sqlString = CONCAT(@sqlString, "    SELECT ");
SET @sqlString = CONCAT(@sqlString, "		i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID ");
SET @sqlString = CONCAT(@sqlString, "	FROM rm_inventory i ");
SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, "	WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL AND it.ACTIVE ");
SET @sqlString = CONCAT(@sqlString, "	GROUP BY i.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, ") ti ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_inventory i ON ti.INVENTORY_ID=i.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON i.PROGRAM_ID=p.PROGRAM_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=p.PROGRAM_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_data_source ds ON it.DATA_SOURCE_ID=ds.DATA_SOURCE_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN us_user lmb ON it.LAST_MODIFIED_BY=lmb.USER_ID ");
SET @sqlString = CONCAT(@sqlString, "WHERE it.ADJUSTMENT_QTY IS NOT NULL AND LEFT(it.INVENTORY_DATE,7) BETWEEN @startDt AND @stopDt AND ppu.ACTIVE AND pu.ACTIVE ");
IF LENGTH(VAR_PLANNING_UNIT_IDS) >0 THEN
 	SET @sqlString = CONCAT(@sqlString, " AND pu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
END IF;

PREPARE s2 FROM @sqlString;
EXECUTE s2;
END$$

DELIMITER ;




USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_CountryShipmentSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_CountryShipmentSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 4
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
 	SET @sqlString = CONCAT(@sqlString, "	SUM(IF(s1.SHIPMENT_STATUS_ID IN (1,2,3,9), s1.AMOUNT, 0)) `PLANNED_SHIPMENT_AMT`, SUM(IF(s1.SHIPMENT_STATUS_ID IN (4,5,6,7), s1.AMOUNT, 0)) `ORDERED_SHIPMENT_AMT` ");
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, rc.REALM_COUNTRY_ID, st.SHIPMENT_STATUS_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT` ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		IF @reportView = 1 THEN 
			SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
        ELSE
			SET @sqlString = CONCAT(@sqlString, "		AND st.PROCUREMENT_AGENT_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
        END IF;
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON s1.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY s1.REALM_COUNTRY_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_FundingSourceCountrySplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_FundingSourceCountrySplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 3 for FundingSource
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE fspa_cursor CURSOR FOR SELECT FUNDING_SOURCE_ID, FUNDING_SOURCE_CODE FROM rm_funding_source WHERE REALM_ID=VAR_REALM_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    
	SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
		FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
			LEAVE getFSPA;
		END IF;
		SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=",fspaId,", s1.AMOUNT, 0)) `FSPA_",fspaCode,"` ");
	END LOOP getFSPA;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
 	SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, rc.REALM_COUNTRY_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT` ");
	SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON s1.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY s1.REALM_COUNTRY_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_FundingSourceDateSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_FundingSourceDateSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 2 for FundingSource
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE finished INTEGER DEFAULT 0;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR SELECT FUNDING_SOURCE_ID, FUNDING_SOURCE_CODE FROM rm_funding_source WHERE REALM_ID=VAR_REALM_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
	
    SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
		FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
			LEAVE getFSPA;
		END IF;
		SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=",fspaId,", s1.AMOUNT, 0)) `FSPA_",fspaCode,"` ");
	END LOOP getFSPA;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	CONCAT(LEFT(s1.EDD,7),'-01') `TRANS_DATE` ");
 	SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, rc.REALM_COUNTRY_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT` ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString); 
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY LEFT(s1.EDD,7)");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ProcurementAgentCountrySplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ProcurementAgentCountrySplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 3 for ProcurementAgent
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE fspa_cursor CURSOR FOR SELECT PROCUREMENT_AGENT_ID, PROCUREMENT_AGENT_CODE FROM rm_procurement_agent WHERE REALM_ID=VAR_REALM_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");

	SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
		FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
			LEAVE getFSPA;
		END IF;
		SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=",fspaId,", s1.AMOUNT, 0)) `FSPA_",fspaCode,"` ");
	END LOOP getFSPA;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR` ");
 	SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, rc.REALM_COUNTRY_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT` ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.PROCUREMENT_AGENT_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON s1.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY s1.REALM_COUNTRY_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ProcurementAgentDateSplit`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ProcurementAgentDateSplit`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 2 for ProcurementAgent
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE fspaId INT(10) DEFAULT 0;
    DECLARE fspaCode VARCHAR(10) DEFAULT '';
    DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE fspa_cursor CURSOR FOR SELECT PROCUREMENT_AGENT_ID, PROCUREMENT_AGENT_CODE FROM rm_procurement_agent WHERE REALM_ID=VAR_REALM_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
	SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @sqlStringFSPA = "";
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    SET done = 0;
    OPEN fspa_cursor;
    getFSPA: LOOP
		FETCH fspa_cursor into fspaId, fspaCode;
        IF done THEN 
			LEAVE getFSPA;
		END IF;
		SET @sqlStringFSPA= CONCAT(@sqlStringFSPA, " ,SUM(IF(s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=",fspaId,", s1.AMOUNT, 0)) `FSPA_",fspaCode,"` ");
	END LOOP getFSPA;
    
	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	CONCAT(LEFT(s1.EDD,7),'-01') `TRANS_DATE` ");
 	SET @sqlString = CONCAT(@sqlString, @sqlStringFSPA);
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, rc.REALM_COUNTRY_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT` ");
	SET @sqlString = CONCAT(@sqlString, "	FROM (");
	SET @sqlString = CONCAT(@sqlString, "	    SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "	    WHERE p.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly=1 THEN 
        SET @sqlString = CONCAT(@sqlString, "	    AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	    GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "		AND st.PROCUREMENT_AGENT_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	) AS s1 ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY LEFT(s1.EDD,7)");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `shipmentGlobalDemand_ShipmentList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentGlobalDemand_ShipmentList`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_ID INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1), VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 21 Part 1
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
	SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @reportView = VAR_REPORT_VIEW;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
	SET @sqlString = "";
    
	SET @sqlString = CONCAT(@sqlString, "SELECT ");
	SET @sqlString = CONCAT(@sqlString, "	CONCAT(LEFT(s1.EDD,7),'-01') `TRANS_DATE`, s1.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	SUM(s1.AMOUNT) `AMOUNT`, ");
	SET @sqlString = CONCAT(@sqlString, "	IF(@reportView=1, fs.FUNDING_SOURCE_ID, pa.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, IF(@reportView=1, fs.FUNDING_SOURCE_CODE, pa.PROCUREMENT_AGENT_CODE) `FUNDING_SOURCE_PROCUREMENT_AGENT_CODE`, IF(@reportView=1, fs.LABEL_ID, pa.LABEL_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_ID`, IF(@reportView=1, fs.LABEL_EN, pa.LABEL_EN) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_EN`, IF(@reportView=1, fs.LABEL_FR, pa.LABEL_FR) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_FR`, IF(@reportView=1, fs.LABEL_SP, pa.LABEL_SP) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_SP`, IF(@reportView=1, fs.LABEL_PR, pa.LABEL_PR) `FUNDING_SOURCE_PROCUREMENT_AGENT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString, "	ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR` ");
	SET @sqlString = CONCAT(@sqlString, "FROM ");
	SET @sqlString = CONCAT(@sqlString, "	( ");
	SET @sqlString = CONCAT(@sqlString, "	SELECT s.SHIPMENT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, rc.REALM_COUNTRY_ID, IF(@reportView=1, st.FUNDING_SOURCE_ID,st.PROCUREMENT_AGENT_ID) `FUNDING_SOURCE_PROCUREMENT_AGENT_ID`, (IFNULL(st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD,0) + IFNULL(st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD,0)) `AMOUNT`, st.SHIPMENT_STATUS_ID ");
	SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "	    ( ");
    SET @sqlString = CONCAT(@sqlString, "       SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID FROM rm_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID WHERE TRUE ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "       AND pv.VERSION_TYPE_ID=2 and pv.VERSION_STATUS_ID=2 ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "       GROUP BY p.PROGRAM_ID) pv ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=p.CURRENT_VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_program_planning_unit ppu ON pv.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
	SET @sqlString = CONCAT(@sqlString, "		rc.REALM_ID=@realmId ");
	SET @sqlString = CONCAT(@sqlString, "		AND s.SHIPMENT_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
	SET @sqlString = CONCAT(@sqlString, "		AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
	SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 8 ");
    IF @includePlannedShipments = 0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_STATUS_ID != 1 ");
    END IF;
	SET @sqlString = CONCAT(@sqlString, "		AND st.PLANNING_UNIT_ID = @planningUnitId ");
    IF LENGTH(VAR_REALM_COUNTRY_IDS) > 0 THEN 
		SET @sqlString = CONCAT(@sqlString, "		AND rc.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS) > 0 THEN
		IF @reportView = 1 THEN 
			SET @sqlString = CONCAT(@sqlString, "		AND st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
        ELSE
			SET @sqlString = CONCAT(@sqlString, "		AND st.PROCUREMENT_AGENT_ID IN (",VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS,") ");
        END IF;
    END IF;
	SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString, ") AS s1 ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON rc.REALM_COUNTRY_ID = s1.REALM_COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa ON s1.FUNDING_SOURCE_PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
	SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss ON s1.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
	SET @sqlString = CONCAT(@sqlString, "GROUP BY MONTH(s1.EDD)");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;



ALTER TABLE `rm_budget` DROP INDEX `unq_budgetCode` , ADD UNIQUE INDEX `unq_budgetCode` (`BUDGET_CODE` ASC, `PROGRAM_ID` ASC);

delete from rm_problem_report_trans where PROBLEM_REPORT_ID in (select PROBLEM_REPORT_ID from fasp.rm_problem_report  where REALM_PROBLEM_ID in (11,13,14));
delete from fasp.rm_problem_report  where REALM_PROBLEM_ID in (11,13,14);