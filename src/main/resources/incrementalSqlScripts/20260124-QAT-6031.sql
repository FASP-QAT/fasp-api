ALTER TABLE `fasp`.`rm_supply_plan_amc` 
	ADD COLUMN `MOS_WTBDPS` DECIMAL(24,8) NULL AFTER `MOS_WPS`, 
    ADD COLUMN `OPENING_BALANCE_WTBDPS` DECIMAL(24,8) NULL AFTER `OPENING_BALANCE_WPS`, 
    ADD COLUMN `MANUAL_PLANNED_SHIPMENT_WTBD_QTY` DECIMAL(24,8) NULL AFTER `MANUAL_PLANNED_SHIPMENT_QTY`, 
    ADD COLUMN `ERP_PLANNED_SHIPMENT_WTBD_QTY` DECIMAL(24,8) NULL AFTER `ERP_PLANNED_SHIPMENT_QTY`, 
    ADD COLUMN `EXPIRED_STOCK_WTBDPS` DECIMAL(24,8) NULL AFTER `EXPIRED_STOCK_WPS`, 
    ADD COLUMN `CLOSING_BALANCE_WTBDPS` DECIMAL(24,8) NULL AFTER `CLOSING_BALANCE_WPS`, 
    ADD COLUMN `UNMET_DEMAND_WTBDPS` DECIMAL(24,8) NULL AFTER `UNMET_DEMAND_WPS`, 
    ADD COLUMN `NATIONAL_ADJUSTMENT_WTBDPS` DECIMAL(24,8) NULL AFTER `NATIONAL_ADJUSTMENT_WPS`
;

-- 15min on my local
UPDATE rm_supply_plan_amc 
SET 
	`MOS_WTBDPS`=`MOS_WPS`, 
    `OPENING_BALANCE_WTBDPS`=`OPENING_BALANCE_WPS`, 
    `MANUAL_PLANNED_SHIPMENT_WTBD_QTY`=`MANUAL_PLANNED_SHIPMENT_QTY`, 
    `ERP_PLANNED_SHIPMENT_WTBD_QTY`=`ERP_PLANNED_SHIPMENT_QTY`, 
    `EXPIRED_STOCK_WTBDPS`=`EXPIRED_STOCK_WPS`, 
    `CLOSING_BALANCE_WTBDPS`=`CLOSING_BALANCE_WPS`, 
    `UNMET_DEMAND_WTBDPS`=`UNMET_DEMAND_WPS`, 
    `NATIONAL_ADJUSTMENT_WTBDPS`=`NATIONAL_ADJUSTMENT_WPS`;

ALTER TABLE `fasp`.`rm_supply_plan_batch_qty` 
	ADD COLUMN `SHIPMENT_QTY_WTBDPS` DECIMAL(24,8) NULL AFTER `SHIPMENT_QTY_WPS`, 
    ADD COLUMN `OPENING_BALANCE_WTBDPS` DECIMAL(24,8) NULL AFTER `OPENING_BALANCE_WPS`, 
    ADD COLUMN `EXPIRED_STOCK_WTBDPS` DECIMAL(24,8) NULL AFTER `EXPIRED_STOCK_WPS`, 
    ADD COLUMN `CALCULATED_CONSUMPTION_WTBDPS` DECIMAL(24,8) NULL AFTER `CALCULATED_CONSUMPTION_WPS`, 
    ADD COLUMN `CLOSING_BALANCE_WTBDPS` DECIMAL(24,8) NULL AFTER `CLOSING_BALANCE_WPS`
;
-- 1813 sec
UPDATE rm_supply_plan_batch_qty
SET 
	`SHIPMENT_QTY_WTBDPS`=`SHIPMENT_QTY_WPS`, 
    `OPENING_BALANCE_WTBDPS`=`OPENING_BALANCE_WPS`, 
    `EXPIRED_STOCK_WTBDPS`=`EXPIRED_STOCK_WPS`, 
    `CALCULATED_CONSUMPTION_WTBDPS`=`CALCULATED_CONSUMPTION_WPS`, 
    `CLOSING_BALANCE_WTBDPS`=`CLOSING_BALANCE_WPS`;

ALTER table tmp_nsp
    ADD COLUMN `SHIPMENT_WTBDPS` DECIMAL(24,8) NULL AFTER `SHIPMENT_WPS`, 
    ADD COLUMN `MANUAL_PLANNED_SHIPMENT_WTBD` DECIMAL(24,8) NULL AFTER `MANUAL_PLANNED_SHIPMENT`, 
    ADD COLUMN `ERP_PLANNED_SHIPMENT_WTBD` DECIMAL(24,8) NULL AFTER `ERP_PLANNED_SHIPMENT`;

USE `fasp`;
DROP procedure IF EXISTS `stockStatusMatrix`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrix`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusMatrix`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS INT(10), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18a Part 1
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- removePlannedShipments = 0 means that you want to retain all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 1 means that you want to remove all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 2 means that you want to remove the shipments that have Funding Source as TBD and are in the Planned stage when the Version was saved.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    
    DECLARE curMn DATE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_mn CURSOR FOR SELECT mn.MONTH FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE and VAR_STOP_DATE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @mnSqlString = "";
    SET @varRemovePlannedShipments = VAR_REMOVE_PLANNED_SHIPMENTS; 
    OPEN cursor_mn;
        read_loop: LOOP
        FETCH cursor_mn INTO curMn;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', CASE @varRemovePlannedShipments WHEN 0 THEN tssm.CLOSING_BALANCE WHEN 1 THEN tssm.CLOSING_BALANCE_WPS WHEN 2 THEN tssm.CLOSING_BALANCE_WTBDPS END, null)) `CLOSING_BALANCE_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', CASE @varRemovePlannedShipments WHEN 0 THEN IF(tssm.PLAN_BASED_ON=1, tssm.MOS, tssm.MAX_STOCK_QTY) WHEN 1 THEN IF(tssm.PLAN_BASED_ON=1, tssm.MOS_WPS, tssm.MAX_STOCK_QTY) WHEN 2 THEN IF(tssm.PLAN_BASED_ON=1, tssm.MOS_WTBDPS, tssm.MAX_STOCK_QTY) END, null)) `MOS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', CASE @varRemovePlannedShipments WHEN 0 THEN tssm.SHIPMENT_QTY WHEN 1 THEN tssm.SHIPMENT_QTY-tssm.MANUAL_PLANNED_SHIPMENT_QTY-tssm.ERP_PLANNED_SHIPMENT_QTY WHEN 2 THEN tssm.SHIPMENT_QTY-tssm.MANUAL_PLANNED_SHIPMENT_QTY-tssm.ERP_PLANNED_SHIPMENT_QTY+tssm.MANUAL_PLANNED_SHIPMENT_WTBD_QTY+tssm.ERP_PLANNED_SHIPMENT_WTBD_QTY END, null)) `SHIPMENT_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', CASE @varRemovePlannedShipments WHEN 0 THEN tssm.EXPIRED_STOCK WHEN 1 THEN tssm.EXPIRED_STOCK_WPS WHEN 2 THEN tssm.EXPIRED_STOCK_WTBDPS END, null)) `EXPIRED_STOCK_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', tssm.AMC, null)) `AMC_",curMn,"` ");
		SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', tssm.STOCK_MULTIPLIED_QTY, null)) `STOCK_QTY_",curMn,"` ");
    END LOOP;
    CLOSE cursor_mn;
	SET @varVersionId = VAR_VERSION_ID;
    IF @varVersionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;
   
    DROP TEMPORARY TABLE IF EXISTS tmp_stock_status_matrix;
    CREATE TEMPORARY TABLE tmp_stock_status_matrix
    SELECT
        amc.TRANS_DATE, amc.PLANNING_UNIT_ID, ppu.MIN_MONTHS_OF_STOCK, ppu.MIN_QTY `MIN_STOCK_QTY`, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.PLAN_BASED_ON, amc.MAX_STOCK_QTY, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS, amc.CLOSING_BALANCE_WTBDPS, amc.MOS, amc.MOS_WPS, amc.MOS_WTBDPS, ppu.NOTES, amc.SHIPMENT_QTY, amc.MANUAL_PLANNED_SHIPMENT_QTY, amc.ERP_PLANNED_SHIPMENT_QTY, amc.MANUAL_PLANNED_SHIPMENT_WTBD_QTY, amc.ERP_PLANNED_SHIPMENT_WTBD_QTY, amc.EXPIRED_STOCK, amc.EXPIRED_STOCK_WPS, amc.EXPIRED_STOCK_WTBDPS, amc.AMC, amc.STOCK_MULTIPLIED_QTY
    FROM rm_supply_plan_amc amc
    LEFT JOIN rm_program_planning_unit ppu ON amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.PROGRAM_ID=ppu.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  
    WHERE
        amc.PROGRAM_ID=VAR_PROGRAM_ID
        AND amc.VERSION_ID=@varVersionId
        AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
        AND ppu.ACTIVE AND pu.ACTIVE
        AND (VAR_PLANNING_UNIT_IDS='' OR FIND_IN_SET(amc.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS));
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   tssm.PLAN_BASED_ON, tssm.MIN_MONTHS_OF_STOCK, tssm.MIN_STOCK_QTY, tssm.REORDER_FREQUENCY_IN_MONTHS, tssm.MAX_STOCK_QTY, tssm.NOTES ");
    SET @sqlString = CONCAT(@sqlString, @mnSqlString);
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN tmp_stock_status_matrix tssm ON mn.MONTH=tssm.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON tssm.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN '",VAR_START_DATE,"' AND '",VAR_STOP_DATE,"' ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY tssm.PLANNING_UNIT_ID ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `stockStatusDetails`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusDetails`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS INT(10), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18a Part 2
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- removePlannedShipments = 0 means that you want to retain all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 1 means that you want to remove all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 2 means that you want to remove the shipments that have Funding Source as TBD and are in the Planned stage when the Version was saved.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup 
    -- Current month is always included in AMC
    
    SET @varVersionId = VAR_VERSION_ID;
    IF @varVersionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;

	SET @varRemovePlannedShipments = VAR_REMOVE_PLANNED_SHIPMENTS;
    SELECT
        amc.TRANS_DATE, 
        amc.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR,
        IF(amc.ACTUAL, amc.ACTUAL_CONSUMPTION_QTY, amc.FORECASTED_CONSUMPTION_QTY) `CONSUMPTION_QTY`, amc.ACTUAL,
        amc.STOCK_MULTIPLIED_QTY, 
        ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_QTY `MIN_STOCK_QTY`, amc.MAX_STOCK_QTY, 
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.CLOSING_BALANCE WHEN 1 THEN amc.CLOSING_BALANCE_WPS WHEN 2 THEN amc.CLOSING_BALANCE_WTBDPS END `CLOSING_BALANCE`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.MOS WHEN 1 THEN amc.MOS_WPS WHEN 2 THEN amc.MOS_WTBDPS END `MOS`, amc.AMC, 
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.SHIPMENT_QTY WHEN 1 THEN amc.SHIPMENT_QTY - amc.MANUAL_PLANNED_SHIPMENT_QTY - amc.ERP_PLANNED_SHIPMENT_QTY WHEN 2 THEN amc.SHIPMENT_QTY - amc.MANUAL_PLANNED_SHIPMENT_QTY - amc.ERP_PLANNED_SHIPMENT_QTY + amc.MANUAL_PLANNED_SHIPMENT_WTBD_QTY + amc.ERP_PLANNED_SHIPMENT_WTBD_QTY END   `SHIPPED_QTY`
    FROM rm_supply_plan_amc amc
    LEFT JOIN rm_program_planning_unit ppu ON amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.PROGRAM_ID=ppu.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  
    WHERE
        amc.PROGRAM_ID=VAR_PROGRAM_ID
        AND amc.VERSION_ID=@varVersionId
        AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
        AND ppu.ACTIVE AND pu.ACTIVE
        AND (VAR_PLANNING_UNIT_IDS='' OR FIND_IN_SET(amc.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS));
END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `buildNewSupplyPlanRegion`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`buildNewSupplyPlanRegion`;
;

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
        PROGRAM_ID, VERSION_ID, PLANNING_UNIT_ID, TRANS_DATE, REGION_ID, 
        FORECASTED_CONSUMPTION, ACTUAL_CONSUMPTION, ADJUSTED_CONSUMPTION, ADJUSTMENT, STOCK, REGION_COUNT, 
        MANUAL_PLANNED_SHIPMENT, MANUAL_PLANNED_SHIPMENT_WTBD, MANUAL_SUBMITTED_SHIPMENT, MANUAL_APPROVED_SHIPMENT, MANUAL_SHIPPED_SHIPMENT, MANUAL_RECEIVED_SHIPMENT, MANUAL_ONHOLD_SHIPMENT, 
        ERP_PLANNED_SHIPMENT, ERP_PLANNED_SHIPMENT_WTBD, ERP_SUBMITTED_SHIPMENT, ERP_APPROVED_SHIPMENT, ERP_SHIPPED_SHIPMENT, ERP_RECEIVED_SHIPMENT, ERP_ONHOLD_SHIPMENT 
    )
    SELECT 
        @programId `PROGRAM_ID`, @versionId, m.`PLANNING_UNIT_ID`, m.`TRANS_DATE`, o.`REGION_ID`, 
        SUM(o.`FORECASTED_CONSUMPTION`), SUM(o.`ACTUAL_CONSUMPTION`), SUM(o.`ADJUSTED_CONSUMPTION`), SUM(o.`ADJUSTMENT`), SUM(o.`STOCK`), @regionCount, 
        SUM(o.`MANUAL_PLANNED_SHIPMENT`), SUM(o.`MANUAL_PLANNED_SHIPMENT_WTBD`), SUM(o.`MANUAL_SUBMITTED_SHIPMENT`), SUM(o.`MANUAL_APPROVED_SHIPMENT`), SUM(o.`MANUAL_SHIPPED_SHIPMENT`), SUM(o.`MANUAL_RECEIVED_SHIPMENT`), SUM(o.`MANUAL_ONHOLD_SHIPMENT`), 
        SUM(o.`ERP_PLANNED_SHIPMENT`), SUM(o.`ERP_PLANNED_SHIPMENT_WTBD`), SUM(o.`ERP_SUBMITTED_SHIPMENT`), SUM(o.`ERP_APPROVED_SHIPMENT`), SUM(o.`ERP_SHIPPED_SHIPMENT`), SUM(o.`ERP_RECEIVED_SHIPMENT`), SUM(o.`ERP_ONHOLD_SHIPMENT`)
    FROM 
        (
        SELECT a3.PLANNING_UNIT_ID, mn.MONTH `TRANS_DATE` 
        FROM 
            (
            SELECT a2.PLANNING_UNIT_ID, MIN(a2.TRANS_DATE) `MIN_TRANS_DATE`, MAX(a2.TRANS_DATE) `MAX_TRANS_DATE` 
            FROM 
                (
                SELECT tc.`PLANNING_UNIT_ID`, `TRANS_DATE`
                FROM 
                    (
                    SELECT 
                        ct.PLANNING_UNIT_ID, LEFT(ct.`CONSUMPTION_DATE`,7) `TRANS_DATE`
                    FROM 
                        (
                        SELECT c.`CONSUMPTION_ID`, MAX(ct.`VERSION_ID`) `MAX_VERSION_ID` FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.`CONSUMPTION_ID`=ct.`CONSUMPTION_ID` WHERE c.`PROGRAM_ID`=@programId AND ct.`VERSION_ID`<=@versionId AND ct.`CONSUMPTION_TRANS_ID` IS NOT NULL GROUP BY c.`CONSUMPTION_ID`
                    ) tc
                    LEFT JOIN rm_consumption c ON c.`CONSUMPTION_ID`=tc.`CONSUMPTION_ID`
                    LEFT JOIN rm_consumption_trans ct ON c.`CONSUMPTION_ID`=ct.`CONSUMPTION_ID` AND tc.`MAX_VERSION_ID`=ct.`VERSION_ID`
                    WHERE ct.`ACTIVE`
                    GROUP BY c.`PROGRAM_ID`, ct.`PLANNING_UNIT_ID`, ct.`CONSUMPTION_DATE`, ct.`REGION_ID`
                ) tc 
                GROUP BY tc.`PLANNING_UNIT_ID`, tc.`TRANS_DATE`

                UNION

                SELECT 
                    st.PLANNING_UNIT_ID, LEFT(COALESCE(st.`RECEIVED_DATE`, st.`EXPECTED_DELIVERY_DATE`),7) `TRANS_DATE`
                    FROM 
                        (
                        SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
                    ) ts
                    LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
                    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
                    WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 
                    GROUP BY st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)

                UNION

                SELECT 
                    rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`
                FROM 
                    (
                    SELECT i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID
                ) ti
                LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID
                LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID
                LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
                WHERE it.ACTIVE
                GROUP BY rcpu.PLANNING_UNIT_ID, it.INVENTORY_DATE
            ) as a2 GROUP BY a2.PLANNING_UNIT_ID
        ) as a3 
        LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND ppu.PLANNING_UNIT_ID=a3.PLANNING_UNIT_ID
        LEFT JOIN mn ON LEFT(mn.MONTH,7) BETWEEN LEFT(DATE_SUB(CONCAT(a3.MIN_TRANS_DATE,'-01'), INTERVAL ppu.MONTHS_IN_FUTURE_FOR_AMC+1 MONTH),7) AND LEFT(IF(DATE_ADD(CONCAT(a3.MAX_TRANS_DATE,'-01'), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH)<(DATE_ADD(DATE(now()),INTERVAL 60 MONTH)),(DATE_ADD(DATE(now()),INTERVAL 60 MONTH)),IF(DATE_ADD(DATE(now()),INTERVAL 120 MONTH) < DATE_ADD(CONCAT(a3.MAX_TRANS_DATE,'-01'), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH),(DATE_ADD(DATE(now()),INTERVAL 120 MONTH)),DATE_ADD(CONCAT(a3.MAX_TRANS_DATE,'-01'), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH))),7)
        WHERE ppu.PLANNING_UNIT_ID IS NOT NULL
    ) AS m 
    LEFT JOIN 
        (
        SELECT 
            tc.`PROGRAM_ID`, tc.`PLANNING_UNIT_ID`, LEFT(tc.`CONSUMPTION_DATE`, 7) `TRANS_DATE`, tc.`REGION_ID`, 
            SUM(tc.`FORECASTED_CONSUMPTION`) `FORECASTED_CONSUMPTION`, SUM(tc.`ACTUAL_CONSUMPTION`) `ACTUAL_CONSUMPTION`, SUM(tc.`ADJUSTED_CONSUMPTION`) `ADJUSTED_CONSUMPTION`, null `ADJUSTMENT`, null `STOCK`, 
            null `MANUAL_PLANNED_SHIPMENT`, null `MANUAL_PLANNED_SHIPMENT_WTBD`, null `MANUAL_SUBMITTED_SHIPMENT`, null `MANUAL_APPROVED_SHIPMENT`, null `MANUAL_SHIPPED_SHIPMENT`, null `MANUAL_RECEIVED_SHIPMENT`, null `MANUAL_ONHOLD_SHIPMENT`, 
            null `ERP_PLANNED_SHIPMENT`, null `ERP_PLANNED_SHIPMENT_WTBD`, null `ERP_SUBMITTED_SHIPMENT`, null `ERP_APPROVED_SHIPMENT`, null `ERP_SHIPPED_SHIPMENT`, null `ERP_RECEIVED_SHIPMENT`, null `ERP_ONHOLD_SHIPMENT`
        FROM 
            (
            SELECT 
                c.`PROGRAM_ID`, ct.`PLANNING_UNIT_ID`, ct.`CONSUMPTION_DATE`, ct.`REGION_ID`, 
                ct.`ACTIVE`, 
                SUM(IF(ct.`ACTUAL_FLAG`=0, ct.`CONSUMPTION_QTY`, null)) `FORECASTED_CONSUMPTION`,
                SUM(IF(ct.`ACTUAL_FLAG`=1, ct.`CONSUMPTION_QTY`, null)) `ACTUAL_CONSUMPTION`,
                SUM(IF(DAY(LAST_DAY(ct.`CONSUMPTION_DATE`)) - IFNULL(ct.DAYS_OF_STOCK_OUT, 0) <= 0, IF(ct.`ACTUAL_FLAG` = 1, ct.`CONSUMPTION_QTY`, NULL), IF(ct.`ACTUAL_FLAG` = 1, ct.`CONSUMPTION_QTY` * DAY(LAST_DAY(ct.`CONSUMPTION_DATE`)) / (DAY(LAST_DAY(ct.`CONSUMPTION_DATE`)) - IFNULL(ct.DAYS_OF_STOCK_OUT, 0)), NULL))) `ADJUSTED_CONSUMPTION`
                
            FROM 
                (
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
            null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, null `ADJUSTED_CONSUMPTION`, null `ADJUSTMENT`, null `STOCK`,
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=1, st.`SHIPMENT_QTY`, null )) `MANUAL_PLANNED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=1 AND fs.FUNDING_SOURCE_TYPE_ID!=5, st.`SHIPMENT_QTY`, null )) `MANUAL_PLANNED_SHIPMENT_WTBD`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=3, st.`SHIPMENT_QTY`, null )) `MANUAL_SUBMITTED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=4, st.`SHIPMENT_QTY`, null )) `MANUAL_APPROVED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID` IN (5,6), st.`SHIPMENT_QTY`, null )) `MANUAL_SHIPPED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=7, st.`SHIPMENT_QTY`, null )) `MANUAL_RECEIVED_SHIPMENT`, 
            SUM(IF((st.ERP_FLAG IS NULL OR st.ERP_FLAG=0) AND st.`SHIPMENT_STATUS_ID`=9, st.`SHIPMENT_QTY`, null )) `MANUAL_ONHOLD_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=1, st.`SHIPMENT_QTY`, null )) `ERP_PLANNED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=1 AND fs.FUNDING_SOURCE_TYPE_ID!=5, st.`SHIPMENT_QTY`, null )) `ERP_PLANNED_SHIPMENT_WTBD`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=3, st.`SHIPMENT_QTY`, null )) `ERP_SUBMITTED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=4, st.`SHIPMENT_QTY`, null )) `ERP_APPROVED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID` IN (5,6), st.`SHIPMENT_QTY`, null )) `ERP_SHIPPED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID` = 7, st.`SHIPMENT_QTY`, null )) `ERP_RECEIVED_SHIPMENT`, 
            SUM(IF(st.`ERP_FLAG`=1 AND st.`SHIPMENT_STATUS_ID`=9, st.`SHIPMENT_QTY`, null )) `ERP_ONHOLD_SHIPMENT`
        FROM 
            (
            SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
        ) ts
        LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
        LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 
        GROUP BY s.PROGRAM_ID, st.PLANNING_UNIT_ID, LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7)

        UNION

        SELECT 
            i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`, it.REGION_ID,
            null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, null `ADJUSTED_CONSUMPTION`, SUM(it.ADJUSTMENT_QTY*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `ADJUSTMENT`,  SUM(it.ACTUAL_QTY*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `STOCK`,
            null `MANUAL_PLANNED_SHIPMENT`, null `MANUAL_PLANNED_SHIPMENT_WTBD`, null `MANUAL_SUBMITTED_SHIPMENT`, null `MANUAL_APPROVED_SHIPMENT`, null `MANUAL_SHIPPED_SHIPMENT`, null `MANUAL_RECEIVED_SHIPMENT`, null `MANUAL_ONHOLD_SHIPMENT`, 
            null `ERP_PLANNED_SHIPMENT`, null `ERP_PLANNED_SHIPMENT_WTBD`, null `ERP_SUBMITTED_SHIPMENT`, null `ERP_APPROVED_SHIPMENT`, null `ERP_SHIPPED_SHIPMENT`, null `ERP_RECEIVED_SHIPMENT`, null `ERP_ONHOLD_SHIPMENT`
        FROM 
            (
            SELECT i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID
        ) ti
        LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID
        LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID
        LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
        WHERE it.ACTIVE
        GROUP BY i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, it.INVENTORY_DATE, it.REGION_ID
    ) AS o ON m.PLANNING_UNIT_ID=o.PLANNING_UNIT_ID AND LEFT(m.TRANS_DATE,7)=o.TRANS_DATE GROUP BY m.PLANNING_UNIT_ID, LEFT(m.TRANS_DATE,7), o.REGION_ID;
           
    -- Update the UseActualConsumption field = 1 
    -- IF All Regions have reported Consumption or if Sum(ActualConsumption)>Sum(ForecastedConsumption)
    -- ELSE UseActualConsumption field = 0
    UPDATE tmp_nsp tn LEFT JOIN (SELECT tn.PLANNING_UNIT_ID, tn.TRANS_DATE, SUM(IF(tn.ACTUAL_CONSUMPTION IS NOT NULL, 1,0)) `COUNT_OF_ACTUAL_CONSUMPTION`, SUM(tn.ACTUAL_CONSUMPTION) `TOTAL_ACTUAL_CONSUMPTION`, SUM(tn.FORECASTED_CONSUMPTION) `TOTAL_FORECASTED_CONSUMPTION` FROM tmp_nsp tn WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL GROUP BY tn.PLANNING_UNIT_ID, tn.TRANS_DATE) rcount ON tn.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND tn.TRANS_DATE=rcount.TRANS_DATE SET tn.USE_ACTUAL_CONSUMPTION=IF(rcount.COUNT_OF_ACTUAL_CONSUMPTION=@regionCount, 1, IF(rcount.TOTAL_ACTUAL_CONSUMPTION>rcount.TOTAL_FORECASTED_CONSUMPTION, 1, 0)) WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL;
        
    -- Update the RegionStockCount field based on the number of Regions that have reported Stock
    UPDATE tmp_nsp tn LEFT JOIN (SELECT tn.PLANNING_UNIT_ID, tn.TRANS_DATE, COUNT(tn.STOCK) CNT FROM tmp_nsp tn WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL GROUP BY tn.PLANNING_UNIT_ID, tn.TRANS_DATE, tn.REGION_ID) rcount ON tn.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND tn.TRANS_DATE=rcount.TRANS_DATE SET tn.REGION_STOCK_COUNT = rcount.CNT WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId AND tn.REGION_ID IS NOT NULL;
        
    -- To get the range for AMC calculations
    -- SELECT MIN(sp.TRANS_DATE), ADDDATE(MAX(sp.TRANS_DATE), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH) INTO @startMonth, @stopMonth  FROM rm_supply_plan sp LEFT JOIN rm_program_planning_unit ppu ON sp.PROGRAM_ID=ppu.PROGRAM_ID AND sp.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID WHERE sp.PROGRAM_ID=@programId and sp.VERSION_ID=@versionId;
    
    SELECT 
        tn.PLANNING_UNIT_ID, tn.TRANS_DATE, IFNULL(ppu.SHELF_LIFE, 24) SHELF_LIFE, tn.REGION_ID, tn.FORECASTED_CONSUMPTION, tn.ACTUAL_CONSUMPTION, tn.ADJUSTED_CONSUMPTION,
        tn.USE_ACTUAL_CONSUMPTION, tn.ADJUSTMENT, tn.STOCK, tn.REGION_STOCK_COUNT, tn.REGION_COUNT,
        tn.MANUAL_PLANNED_SHIPMENT, tn.MANUAL_PLANNED_SHIPMENT_WTBD, tn.MANUAL_SUBMITTED_SHIPMENT, tn.MANUAL_APPROVED_SHIPMENT, tn.MANUAL_SHIPPED_SHIPMENT, tn.MANUAL_RECEIVED_SHIPMENT, tn.MANUAL_ONHOLD_SHIPMENT, 
        tn.ERP_PLANNED_SHIPMENT, tn.ERP_PLANNED_SHIPMENT_WTBD, tn.ERP_SUBMITTED_SHIPMENT, tn.ERP_APPROVED_SHIPMENT, tn.ERP_SHIPPED_SHIPMENT, tn.ERP_RECEIVED_SHIPMENT, tn.ERP_ONHOLD_SHIPMENT
    FROM tmp_nsp tn LEFT JOIN rm_program_planning_unit ppu ON tn.PROGRAM_ID=ppu.PROGRAM_ID AND tn.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID WHERE tn.PROGRAM_ID=@programId AND tn.VERSION_ID=@versionId 
    order by tn.PLANNING_UNIT_ID,tn.TRANS_DATE,tn.REGION_ID
    ; 

END$$

DELIMITER ;
;


USE `fasp`;
DROP procedure IF EXISTS `buildNewSupplyPlanBatch`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`buildNewSupplyPlanBatch`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildNewSupplyPlanBatch`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
BEGIN
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID; 
    
        SELECT 
        o.PROGRAM_ID, @versionId, o.PLANNING_UNIT_ID, DATE(CONCAT(o.TRANS_DATE,"-01")) `TRANS_DATE`, o.BATCH_ID, o.EXPIRY_DATE, o.SHELF_LIFE,
        SUM(o.FORECASTED_CONSUMPTION) `FORECASTED_CONSUMPTION`, SUM(o.ACTUAL_CONSUMPTION) `ACTUAL_CONSUMPTION`, 
        SUM(o.SHIPMENT) `SHIPMENT`, SUM(o.SHIPMENT_WPS) `SHIPMENT_WPS`, SUM(o.SHIPMENT_WTBDPS) `SHIPMENT_WTBDPS`, SUM(o.ADJUSTMENT) `ADJUSTMENT`, SUM(o.STOCK) `STOCK`, SUM(o.INVENTORY_QTY) `INVENTORY_QTY`  
        FROM ( 
        
			-- Consumption
            SELECT 
            tc.PROGRAM_ID, tc.CONSUMPTION_ID `TRANS_ID`, tc.PLANNING_UNIT_ID, LEFT(tc.CONSUMPTION_DATE, 7) `TRANS_DATE`, tc.BATCH_ID, tc.EXPIRY_DATE `EXPIRY_DATE`, tc.SHELF_LIFE,
            SUM(FORECASTED_CONSUMPTION) `FORECASTED_CONSUMPTION`, SUM(ACTUAL_CONSUMPTION) `ACTUAL_CONSUMPTION`, 
            null `SHIPMENT`, null `SHIPMENT_WPS`, null `SHIPMENT_WTBDPS`, null `ADJUSTMENT`, null  `STOCK`, 0 `INVENTORY_QTY` 
            FROM (
                SELECT 
                    c.PROGRAM_ID, c.CONSUMPTION_ID, ct.REGION_ID, ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, 
                    ctbi.BATCH_ID `BATCH_ID`, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                    SUM(IF(ct.ACTUAL_FLAG=1, COALESCE(ctbi.CONSUMPTION_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)), ct.CONSUMPTION_QTY), null)) `ACTUAL_CONSUMPTION`, 
                    SUM(IF(ct.ACTUAL_FLAG=0, COALESCE(ctbi.CONSUMPTION_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)), ct.CONSUMPTION_QTY), null)) `FORECASTED_CONSUMPTION`
                FROM (
                    SELECT c.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId AND ct.VERSION_ID<=@versionId AND ct.CONSUMPTION_TRANS_ID IS NOT NULL GROUP BY c.CONSUMPTION_ID
                ) tc
                LEFT JOIN rm_consumption c ON c.CONSUMPTION_ID=tc.CONSUMPTION_ID
                LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
                LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID
                LEFT JOIN rm_batch_info bi ON ctbi.BATCH_ID=bi.BATCH_ID
                LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
                LEFT JOIN rm_program_planning_unit ppu ON c.PROGRAM_ID=ppu.PROGRAM_ID AND ct.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
                WHERE ct.ACTIVE AND ctbi.BATCH_ID IS NOT NULL AND ppu.PLANNING_UNIT_ID IS NOT NULL 
                GROUP BY c.PROGRAM_ID, ct.REGION_ID, ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, ctbi.BATCH_ID
            ) tc 
            GROUP BY tc.PROGRAM_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.BATCH_ID

            UNION
			
            -- Shipment
            SELECT 
                s.PROGRAM_ID, s.SHIPMENT_ID `TRANS_ID`, st.PLANNING_UNIT_ID, LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7) `TRANS_DATE`, stbi.BATCH_ID, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                SUM(IF(st.SHIPMENT_STATUS_ID IN (1,3,4,5,6,7,9), COALESCE(stbi.BATCH_SHIPMENT_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) ,st.SHIPMENT_QTY),0)) `SHIPMENT`, 
                SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9), COALESCE(stbi.BATCH_SHIPMENT_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) ,st.SHIPMENT_QTY), 0)) `SHIPMENT_WPS`, 
                SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9) OR (st.SHIPMENT_STATUS_ID=1 AND fs.FUNDING_SOURCE_TYPE_ID!=5), COALESCE(stbi.BATCH_SHIPMENT_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) ,st.SHIPMENT_QTY), 0)) `SHIPMENT_WTBDPS`, 
                null  `ADJUSTMENT_MULTIPLIED_QTY`, null  `STOCK_MULTIPLIED_QTY`, 0 `INVENTORY_QTY` 
            FROM (
                SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
            ) ts
            LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
            LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
            LEFT JOIN rm_shipment_trans_batch_info stbi ON st.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID
            LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID
            LEFT JOIN rm_realm_country_planning_unit rcpu ON st.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
            LEFT JOIN rm_program_planning_unit ppu ON s.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
            WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 AND stbi.BATCH_ID IS NOT NULL  AND ppu.PLANNING_UNIT_ID IS NOT NULL 
            GROUP BY s.PROGRAM_ID, st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE), stbi.BATCH_ID

            UNION

            -- Inventory
            SELECT 
                i.PROGRAM_ID, i.INVENTORY_ID `TRANS_ID`, rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`, itbi.BATCH_ID, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                null `SHIPMENT`, null `SHIPMENT_WPS`, null `SHIPMENT_WTBDPS`, SUM(COALESCE(itbi.ADJUSTMENT_QTY, it.ADJUSTMENT_QTY)*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `ADJUSTMENT`,  SUM(COALESCE(itbi.ACTUAL_QTY, it.ACTUAL_QTY)*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `STOCK`, 0 `INVENTORY_QTY` 
            FROM (
                SELECT i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID
            ) ti
            LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID
            LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID
            LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID
            LEFT JOIN rm_batch_info bi ON itbi.BATCH_ID=bi.BATCH_ID
            LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
            LEFT JOIN rm_program_planning_unit ppu ON i.PROGRAM_ID=ppu.PROGRAM_ID AND rcpu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
            WHERE it.ACTIVE AND itbi.BATCH_ID IS NOT NULL AND ppu.PLANNING_UNIT_ID IS NOT NULL 
            GROUP BY i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, it.INVENTORY_DATE, itbi.BATCH_ID

            UNION

            -- Batch Inventory for Expiry
            SELECT 
                bi.PROGRAM_ID, bi.BATCH_INVENTORY_ID `TRANS_ID`, bi.PLANNING_UNIT_ID, LEFT(bi.INVENTORY_DATE,7) `TRANS_DATE`, bt.BATCH_ID, rm_batch_info.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                null `SHIPMENT`, null `SHIPMENT_WPS`, null `SHIPMENT_WTBDPS`, null `ADJUSTMENT`,  null `STOCK`, bt.QTY `INVENTORY_QTY` 
            FROM (
                SELECT bi.PROGRAM_ID, bi.BATCH_INVENTORY_ID, MAX(bt.VERSION_ID) MAX_VERSION_ID FROM rm_batch_inventory bi LEFT JOIN rm_batch_inventory_trans bt ON bi.BATCH_INVENTORY_ID=bt.BATCH_INVENTORY_ID WHERE bi.PROGRAM_ID=@programId AND bt.VERSION_ID<=@versionId AND bt.BATCH_INVENTORY_TRANS_ID IS NOT NULL GROUP BY bi.BATCH_INVENTORY_ID
            ) tbi
            LEFT JOIN rm_batch_inventory bi ON bi.BATCH_INVENTORY_ID=tbi.BATCH_INVENTORY_ID
            LEFT JOIN rm_batch_inventory_trans bt ON bt.BATCH_INVENTORY_ID=bi.BATCH_INVENTORY_ID AND bt.VERSION_ID=tbi.MAX_VERSION_ID
            LEFT JOIN rm_batch_info ON bt.BATCH_ID=rm_batch_info.BATCH_ID
            LEFT JOIN rm_program_planning_unit ppu ON bi.PROGRAM_ID=ppu.PROGRAM_ID AND bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID            
            WHERE bt.ACTIVE
        ) AS o 
        GROUP BY o.PROGRAM_ID, o.PLANNING_UNIT_ID, o.TRANS_DATE, o.BATCH_ID ORDER BY o.PROGRAM_ID, o.PLANNING_UNIT_ID, o.TRANS_DATE, IFNULL(o.EXPIRY_DATE,'2999-12-31');

END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `getExpiredStock`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getExpiredStock`;
;

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
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ppu.ACTIVE `PPU_ACTIVE`,
        bi.BATCH_ID, bi.BATCH_NO, bi.AUTO_GENERATED, spbq.EXPIRY_DATE, ppu.CATALOG_PRICE `COST`, bi.CREATED_DATE, IF (@includePlannedShipments=1, spbq.EXPIRED_STOCK, spbq.EXPIRED_STOCK_WPS) `EXPIRED_STOCK`,
        timestampdiff(MONTH, CONCAT(LEFT(bi.CREATED_DATE,7),"-01"),spbq.EXPIRY_DATE) `SHELF_LIFE`,
        bTrans.TRANS_DATE `BATCH_TRANS_DATE`,
        bTrans.OPENING_BALANCE `BATCH_OPENING_BALANCE`, bTrans.OPENING_BALANCE_WPS `BATCH_OPENING_BALANCE_WPS`, bTrans.OPENING_BALANCE_WTBDPS `BATCH_OPENING_BALANCE_WTBDPS`,
        bTrans.EXPIRED_STOCK `BATCH_EXPIRED_STOCK`, bTrans.EXPIRED_STOCK_WPS `BATCH_EXPIRED_STOCK_WPS`, bTrans.EXPIRED_STOCK_WTBDPS `BATCH_EXPIRED_STOCK_WTBDPS`, 
        bTrans.STOCK_MULTIPLIED_QTY `BATCH_STOCK_MULTIPLIED_QTY`, bTrans.ADJUSTMENT_MULTIPLIED_QTY `BATCH_ADJUSTMENT_MULTIPLIED_QTY`, bTrans.ACTUAL_CONSUMPTION_QTY `BATCH_CONSUMPTION_QTY`,
        bTrans.SHIPMENT_QTY `BATCH_SHIPMENT_QTY`, bTrans.SHIPMENT_QTY_WPS `BATCH_SHIPMENT_QTY_WPS`, bTrans.SHIPMENT_QTY_WTBDPS `BATCH_SHIPMENT_QTY_WTBDPS`,
        bTrans.CALCULATED_CONSUMPTION `BATCH_CALCULATED_CONSUMPTION_QTY`, bTrans.CALCULATED_CONSUMPTION_WPS `BATCH_CALCULATED_CONSUMPTION_QTY_WPS`, bTrans.CALCULATED_CONSUMPTION_WTBDPS `BATCH_CALCULATED_CONSUMPTION_QTY_WTBDPS`,
        bTrans.CLOSING_BALANCE `BATCH_CLOSING_BALANCE`, bTrans.CLOSING_BALANCE_WPS `BATCH_CLOSING_BALANCE_WPS`, bTrans.CLOSING_BALANCE_WTBDPS `BATCH_CLOSING_BALANCE_WTBDPS`
    FROM rm_supply_plan_batch_qty spbq 
    LEFT JOIN rm_batch_info bi ON spbq.BATCH_ID=bi.BATCH_ID 
    LEFT JOIN vw_program p ON spbq.PROGRAM_ID=p.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON spbq.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
    LEFT JOIN rm_supply_plan_batch_qty bTrans ON bTrans.PROGRAM_ID=spbq.PROGRAM_ID AND bTrans.VERSION_ID=spbq.VERSION_ID AND bTrans.PLANNING_UNIT_ID=spbq.PLANNING_UNIT_ID AND bTrans.BATCH_ID=spbq.BATCH_ID
    WHERE pu.ACTIVE AND spbq.PROGRAM_ID=@programId AND spbq.VERSION_ID=@versionId AND spbq.TRANS_DATE BETWEEN @startDate AND @stopDate AND (@includePlannedShipments=1 AND spbq.EXPIRED_STOCK>0 OR @includePlannedShipments=0 AND spbq.EXPIRED_STOCK_WPS>0)
    ORDER BY spbq.EXPIRY_DATE, pu.LABEL_EN, spbq.BATCH_ID, bTrans.TRANS_DATE;
    
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.removePlannedShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Remove Planned Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer les envois planifiés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar envíos planificados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remover remessas planejadas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.removeTBDFundingSourceShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Remove TBD Funding Source Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer les expéditions de la source de financement à déterminer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar envíos de fuente de financiación por determinar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remover remessas com fonte de financiamento a ser definida');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showQuantity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher la quantité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar cantidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar quantidade');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showIcon','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Icon');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher l\'icône');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar icono');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar ícone');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.minMax','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min / Max');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min / Max');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mínimo/Máximo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mín. / Máx.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.selectStockStatus','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Stock Status');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionner l\'état du stock');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el estado del stock');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o status do estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.totalShipmentQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Shipment Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale expédiée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total de envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total de remessas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.planningUnitTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on planning unit name to navigate to the Supply Plan Report for this product. Hover over to view planning unit notes.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur le nom de l\'unité de planification pour accéder au rapport du plan d\'approvisionnement de ce produit. Survolez l\'unité de planification pour afficher ses notes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haz clic en el nombre de la unidad de planificación para acceder al Informe del Plan de Suministro de este producto. Pasa el cursor por encima para ver las notas de la unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique no nome da unidade de planejamento para acessar o Relatório do Plano de Suprimentos deste produto. Passe o cursor sobre a unidade para visualizar as notas.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.planByTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program admins can choose to plan products by months of stock (MOS) or quantity. Most products are better planned by MOS, while some low consumption, higher expiry products are better planned by quantity.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les administrateurs de programme peuvent choisir de planifier les produits en fonction du nombre de mois de stock (MOS) ou de la quantité. La plupart des produits sont mieux planifiés en fonction du MOS, tandis que certains produits à faible consommation et à date de péremption plus longue sont mieux planifiés en fonction de la quantité.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los administradores del programa pueden optar por planificar los productos según los meses de existencias (MOS) o la cantidad. La mayoría de los productos se planifican mejor según los meses de existencias, mientras que algunos productos de bajo consumo y fecha de caducidad próxima se planifican mejor según la cantidad.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os administradores do programa podem optar por planejar os produtos por meses de estoque (MES) ou por quantidade. A maioria dos produtos é melhor planejada por MES, enquanto alguns produtos de baixo consumo e maior prazo de validade são melhor planejados por quantidade.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.minMaxTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If planning by MOS, max MOS = min MOS + reorder interval. If planning by quantity, the max quantity = min quantity + reorder interval * AMC for that month, and the max value displayed here is the average of all the monthly max values. Min and reorder interval settings can be updated by program admins.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En cas de planification par MOS, le MOS max = MOS min + intervalle de réapprovisionnement. En cas de planification par quantité, la quantité max = quantité min + intervalle de réapprovisionnement * AMC pour ce mois, et la valeur max affichée ici est la moyenne de toutes les valeurs max mensuelles. Les paramètres de quantité minimale et d\'intervalle de réapprovisionnement peuvent être modifiés par les administrateurs du programme.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si la planificación se basa en MOS, el MOS máximo = MOS mínimo + intervalo de reorden. Si la planificación se basa en cantidad, la cantidad máxima = cantidad mínima + intervalo de reorden * AMC para ese mes, y el valor máximo que se muestra aquí es el promedio de todos los valores máximos mensuales. Los administradores del programa pueden actualizar la configuración del intervalo mínimo y de reorden.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o planejamento for feito por MOS (Mercado de Estoque Mínimo), o MOS máximo = MOS mínimo + intervalo de reposição. Se o planejamento for feito por quantidade, a quantidade máxima = quantidade mínima + intervalo de reposição * Custo Médio de Manutenção (CMM) para aquele mês, e o valor máximo exibido aqui é a média de todos os valores máximos mensais. As configurações de quantidade mínima e intervalo de reposição podem ser atualizadas pelos administradores do programa.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.noPlanningUnits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No planning units are present in this program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce programme ne comporte aucune unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En este programa no hay unidades de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este programa não contém unidades de planejamento.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.noData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'There is no available data for the planning unit(s) during the selected report period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune donnée n\'est disponible pour la ou les unités de planification pendant la période de rapport sélectionnée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay datos disponibles para la(s) unidad(es) de planificación durante el período de informe seleccionado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não existem dados disponíveis para a(s) unidade(s) de planejamento durante o período do relatório selecionado.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.reportPeriodTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Default: 3 months past + 15 months in future. Please select the period you would like to view.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par défaut : 3 mois précédents + 15 mois suivants. Veuillez sélectionner la période que vous souhaitez consulter.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por defecto: 3 meses pasados + 15 meses futuros. Seleccione el período que desea consultar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Padrão: 3 meses anteriores + 15 meses posteriores. Selecione o período que deseja visualizar.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.versionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'* for final, ** for final approved, Default = latest version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'* Version finale, ** Version finale approuvée, Par défaut = dernière version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'* para versión final, ** para versión final aprobada, Predeterminado = última versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'* para versão final, ** para versão final aprovada, Padrão = versão mais recente');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showQuantityTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If unchecked, stock is represented by months of stock (MOS) (default). If checked, stock is represented by quantity.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette option n\'est pas cochée, le stock est représenté par mois de stock (par défaut). Si elle est cochée, le stock est représenté par quantité.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no está marcada, el stock se representa por meses de existencias (MOS) (opción predeterminada). Si está marcada, el stock se representa por cantidad.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se não estiver selecionado, o estoque será representado por meses de estoque (MOS) (padrão). Se estiver selecionado, o estoque será representado pela quantidade.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showIconTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, this displays the shipment and expiry icons. There is a shipment icon for every month with shipment(s), including all shipment statuses except cancelled, on-hold and any inactive shipments. There is an expiry icon for every month with a projected expiry.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette option est activée, les icônes d\'expédition et d\'expiration s\'affichent. Une icône d\'expédition est présente pour chaque mois comportant au moins un envoi, quel que soit son statut, à l\'exception des envois annulés, en attente ou inactifs. Une icône d\'expiration est également présente pour chaque mois présentant une date d\'expiration prévue.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcada, se muestran los iconos de envío y vencimiento. Hay un icono de envío para cada mes con envíos, incluyendo todos los estados de envío excepto cancelados, en espera y los envíos inactivos. Hay un icono de vencimiento para cada mes con una fecha de vencimiento prevista.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se selecionada, esta opção exibe os ícones de envio e validade. Há um ícone de envio para cada mês com envios, incluindo todos os status de envio, exceto cancelado, em espera e envios inativos. Há um ícone de validade para cada mês com data de validade prevista.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.removePlannedShipmentsTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, this shows the resulting stock (status) if shipments with status = planned are removed. Note that by definition, this removes all TBD shipments since TBD shipments are not allowed past planned status.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette option est cochée, le stock résultant (statut) s\'affiche après suppression des expéditions dont le statut est « Planifié ». Notez que, par définition, toutes les expéditions « À déterminer » sont supprimées, car ces expéditions ne sont pas autorisées après le statut « Planifié ».');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcada, esta opción muestra el stock resultante (estado) si se eliminan los envíos con estado = planificado. Tenga en cuenta que, por definición, esto elimina todos los envíos TBD, ya que estos no se permiten más allá del estado planificado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se selecionada, esta opção mostra o estoque (status) resultante caso os embarques com status = planejado sejam removidos. Observe que, por definição, isso remove todos os embarques com status a definir, visto que embarques com status a definir não podem ultrapassar o status planejado.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.removeTBDFundingSourceShipmentsTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, this shows the resulting stock (status) if shipments with funding source = TBD are removed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette option est cochée, elle affiche le stock résultant (statut) si les expéditions dont la source de financement est à déterminer sont supprimées.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcada, esta opción muestra el stock resultante (estado) si se eliminan los envíos con fuente de financiación = TBD.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se selecionada, esta opção mostra o estoque (status) resultante caso os envios com fonte de financiamento = A definir sejam removidos.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.consumptionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual consumption where available. Otherwise, forecasted consumption.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle lorsque disponible. Sinon, consommation prévisionnelle.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real cuando esté disponible. En caso contrario, consumo previsto.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real, quando disponível. Caso contrário, consumo previsto.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.amcTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Average Monthly Consumption. Dynamic value based on user-entered number of months past & future in the Planning Unit settings screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation mensuelle moyenne. Valeur dynamique basée sur le nombre de mois passés et futurs saisi par l\'utilisateur dans l\'écran des paramètres de l\'unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo mensual promedio. Valor dinámico basado en el número de meses pasados y futuros introducidos por el usuario en la pantalla de configuración de la unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo médio mensal. Valor dinâmico baseado no número de meses passados e futuros inserido pelo usuário na tela de configurações da Unidade de Planejamento.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.mosTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months of Stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses de existencias');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusMatrix.statusTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock status relative to minimum and maximum levels (either in MOS or quantity) as set in the Planning Unit settings screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'État des stocks par rapport aux niveaux minimum et maximum (en MOS ou en quantité) tels que définis dans l\'écran des paramètres de l\'unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estado del stock en relación con los niveles mínimo y máximo (ya sea en MOS o en cantidad) establecidos en la pantalla de configuración de la unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Status do estoque em relação aos níveis mínimo e máximo (em MOS ou quantidade), conforme definido na tela de configurações da Unidade de Planejamento.');-- pr