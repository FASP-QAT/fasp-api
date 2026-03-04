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
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrix`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS INT(10), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
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
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS INT(10), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
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
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `buildNewSupplyPlanRegion`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
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
DROP procedure IF EXISTS `buildNewSupplyPlanRegion`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`buildNewSupplyPlanRegion`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `buildNewSupplyPlanRegion`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
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
DROP procedure IF EXISTS `stockStatusMatrixGlobal`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrixGlobal`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrixGlobal`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT, VAR_REPORT_VIEW INT(10))
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18b
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    
    -- startDate and stopDate are the period for which you want to run the report
    -- realmCountryIds is the list of Countries that you want to run the report for; Empty means all countries
    -- programIds is the list of Programs that you want to run the report for; Empty means all programs
    -- versionId Only to be used when a Single Program is selected. So if you get VersionId<>0 it means only the first ProgramId should be used from the list; -1 would mean that use the latest VersionId
    -- equivalencyUnitId When passed as 0 it means that No EquivalencyUnitId was selected and therefore only a single PlanningUnit can be selected; If an EquivalencyUnitId is selected it means that multiple PlanningUnits can be selected since the report is to be showing in terms of EU
    -- planningUnitIds is the list of Planning Units that you want to include in the report; Empty means all PlanningUnitIds; When EU is 0 then only a single PU can be selected
    -- stockStatusIds is the list of Stock Statuses that you want to show in the report; For now this is not possible need to go back to FASP about this option.
    -- removePlannedShipments = 0 means that you want to retain all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 1 means that you want to remove all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 2 means that you want to remove the shipments that have Funding Source as TBD and are in the Planned stage when the Version was saved.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed; For now this is not possible need to go back to FASP about this option.
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed; For now this is not possible need to go back to FASP about this option.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    -- reportView=1 means show in terms of MoS and Qty based on the PlannedBasedOn setting; If reportView=2 it means show everything in terms of Qty but retain the color coding and StockStatusId
    
    DECLARE curMn DATE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_mn CURSOR FOR SELECT mn.MONTH FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE and VAR_STOP_DATE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @varRemovePlannedShipments = VAR_REMOVE_PLANNED_SHIPMENTS;
    SET @varReportView = VAR_REPORT_VIEW;
    SET @mnSqlString = "";
    
    OPEN cursor_mn;
        read_loop: LOOP
        FETCH cursor_mn INTO curMn;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @mnSqlString = CONCAT(@mnSqlString, "   	, GROUP_CONCAT(IF(mn.MONTH='",curMn,"', amc2.PLANNING_UNIT_IDS, null)) `PLANNING_UNIT_IDS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.MOS, null)) `MOS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.AMC, null)) `AMC_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_STATUS_ID, null)) `STOCK_STATUS_ID_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.CLOSING_BALANCE, null)) `CLOSING_BALANCE_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_MULTIPLIED_QTY, null)) `STOCK_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.SHIPMENT_QTY, null)) `SHIPMENT_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.EXPIRED_STOCK_QTY, null)) `EXPIRED_STOCK_QTY_",curMn,"` ");
    END LOOP;
    CLOSE cursor_mn;
    
    SET @varVersionId = VAR_VERSION_ID;
    IF VAR_VERSION_ID = 0 THEN 
	SET @varVersionId = null;
    ELSEIF VAR_VERSION_ID = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;
    -- SELECT @varVersionId;
    DROP TABLE IF EXISTS tmp_amc;
    CREATE TABLE tmp_amc
    SELECT 
        amc.TRANS_DATE, p.PROGRAM_ID, p.REALM_COUNTRY_ID,
        amc.PLANNING_UNIT_ID, ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, amc.MIN_STOCK_QTY, amc.MAX_STOCK_QTY,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.CLOSING_BALANCE WHEN 1 THEN amc.CLOSING_BALANCE_WPS WHEN 2 THEN amc.CLOSING_BALANCE_WTBDPS END `CLOSING_BALANCE`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.MOS WHEN 1 THEN amc.MOS_WPS WHEN 2 THEN amc.MOS_WTBDPS END `MOS`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.SHIPMENT_QTY WHEN 1 THEN amc.SHIPMENT_QTY-amc.MANUAL_PLANNED_SHIPMENT_QTY-amc.ERP_PLANNED_SHIPMENT_QTY WHEN 2 THEN amc.SHIPMENT_QTY-amc.MANUAL_PLANNED_SHIPMENT_QTY-amc.ERP_PLANNED_SHIPMENT_QTY+amc.MANUAL_PLANNED_SHIPMENT_WTBD_QTY+amc.ERP_PLANNED_SHIPMENT_WTBD_QTY END `SHIPMENT_QTY`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.EXPIRED_STOCK WHEN 1 THEN amc.EXPIRED_STOCK_WPS WHEN 2 THEN amc.EXPIRED_STOCK_WTBDPS END `EXPIRED_STOCK_QTY`,
        amc.AMC, amc.STOCK_MULTIPLIED_QTY, IF(VAR_EQUIVALENCY_UNIT_ID=0, 1, pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)) `CONVERSION`
    FROM vw_program p 
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND (LENGTH(VAR_PLANNING_UNIT_IDS)=0 OR FIND_IN_SET(ppu.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS))
    LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=p.PROGRAM_ID AND amc.VERSION_ID=COALESCE(@varVersionId, p.CURRENT_VERSION_ID) AND amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
    LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum1 ON pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.PROGRAM_ID=p.PROGRAM_ID AND eum1.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum2 ON pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID IS NULL AND eum2.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    WHERE TRUE AND p.ACTIVE
        AND (LENGTH(VAR_REALM_COUNTRY_IDS)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, VAR_REALM_COUNTRY_IDS))
        AND (LENGTH(VAR_PROGRAM_IDS)=0 OR FIND_IN_SET(p.PROGRAM_ID, VAR_PROGRAM_IDS))
        AND ppu.PLANNING_UNIT_ID is not null;

	SET @interimSql = "SELECT 
                                IF(@varReportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID) `ID`,
                                amc.TRANS_DATE, SUM(amc.AMC*amc.CONVERSION) `AMC`,
                                GROUP_CONCAT(DISTINCT amc.PLANNING_UNIT_ID) `PLANNING_UNIT_IDS`,
                                CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END `MOS`,
                                IF(SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,1,2) `PLAN_BASED_ON`,
                                IF(
                                    SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,
                                    CASE 
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END IS NULL THEN -1 
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END = 0 THEN 0
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END < SUM(amc.MIN_MONTHS_OF_STOCK) THEN 1
                                        WHEN CASE WHEN COUNT(amc.MOS)=COUNT(*) THEN SUM(amc.MOS) ELSE null END <= SUM(amc.MIN_MONTHS_OF_STOCK+amc.REORDER_FREQUENCY_IN_MONTHS) THEN 2
                                        ELSE 3
                                    END,
                                    CASE 
                                        WHEN SUM(amc.CLOSING_BALANCE)=0 THEN 0
                                        WHEN SUM(amc.CLOSING_BALANCE)<SUM(amc.MIN_STOCK_QTY) THEN 1
                                        WHEN SUM(amc.CLOSING_BALANCE)<=SUM(amc.MAX_STOCK_QTY) THEN 2
                                        ELSE 3
                                    END
                                ) `STOCK_STATUS_ID`,
                                SUM(amc.CLOSING_BALANCE*amc.CONVERSION) `CLOSING_BALANCE`,
                                CASE WHEN COUNT(amc.STOCK_MULTIPLIED_QTY)=count(*) THEN SUM(amc.STOCK_MULTIPLIED_QTY*amc.CONVERSION) ELSE NULL END `STOCK_MULTIPLIED_QTY`,
                                SUM(amc.SHIPMENT_QTY*amc.CONVERSION) `SHIPMENT_QTY`,
                                SUM(amc.EXPIRED_STOCK_QTY*amc.CONVERSION) `EXPIRED_STOCK_QTY`
                            FROM tmp_amc amc
                            group by amc.TRANS_DATE, IF(@reportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID)";
	
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   amc2.`ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_ID, c.LABEL_ID) `LABEL_ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_EN, c.LABEL_EN) `LABEL_EN`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_FR, c.LABEL_FR) `LABEL_FR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_SP, c.LABEL_SP) `LABEL_SP`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_PR, c.LABEL_PR) `LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.PROGRAM_CODE, c.COUNTRY_CODE) `CODE` ");
    SET @sqlString = CONCAT(@sqlString, @mnSqlString);
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (",@interimSql,") amc2 ON mn.MONTH=amc2.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON amc2.ID=p.PROGRAM_ID AND @varReportView=1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON amc2.ID=rc.REALM_COUNTRY_ID AND @varReportView=2 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN '",VAR_START_DATE,"' AND '",VAR_STOP_DATE,"' ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY amc2.ID ");
    -- SELECT * FROM tmp_amc;
    -- SELECT @sqlString;
    -- PREPARE S2 FROM @interimSql;
    -- EXECUTE S2;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;
;

