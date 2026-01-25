USE `fasp`;
DROP procedure IF EXISTS `stockStatusDetails`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusDetails`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_REMOVE_PLANNED_SHIPMENTS_THAT_FAIL_LEAD_TIME TINYINT(1), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18a Part 2
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- removePlannedShipments = 1 means that you want to remove the shipments that are in the Planned stage while running this report.
    -- removePlannedShipments = 0 means that you want to retain the shipments that are in the Planned stage while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 1 means that you want to remove the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 0 means that you want to retain the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    
    SET @varVersionId = VAR_VERSION_ID;
    IF @varVersionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;
   
    SELECT
        amc.TRANS_DATE, 
        amc.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR,
        IF(amc.ACTUAL, amc.ACTUAL_CONSUMPTION_QTY, amc.FORECASTED_CONSUMPTION_QTY) `CONSUMPTION_QTY`, amc.ACTUAL,
        amc.STOCK_MULTIPLIED_QTY, 
        ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_QTY `MIN_STOCK_QTY`, amc.MAX_STOCK_QTY, 
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.CLOSING_BALANCE_WPS, amc.CLOSING_BALANCE) `CLOSING_BALANCE`,
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.MOS_WPS, amc.MOS) `MOS`, amc.AMC, 
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, (amc.SHIPMENT_QTY - amc.MANUAL_PLANNED_SHIPMENT_QTY - amc.ERP_PLANNED_SHIPMENT_QTY), amc.SHIPMENT_QTY)  `SHIPPED_QTY`
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
DROP procedure IF EXISTS `stockStatusMatrix`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrix`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrix`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_REMOVE_PLANNED_SHIPMENTS_THAT_FAIL_LEAD_TIME TINYINT(1), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18a Part 1 
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- removePlannedShipments = 1 means that you want to remove the shipments that are in the Planned stage while running this report.
    -- removePlannedShipments = 0 means that you want to retain the shipments that are in the Planned stage while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 1 means that you want to remove the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 0 means that you want to retain the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    
    DECLARE curMn DATE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_mn CURSOR FOR SELECT mn.MONTH FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE and VAR_STOP_DATE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @mnSqlString = "";
    OPEN cursor_mn;
        read_loop: LOOP
        FETCH cursor_mn INTO curMn;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', IF(",VAR_REMOVE_PLANNED_SHIPMENTS,", tssm.CLOSING_BALANCE_WPS, tssm.CLOSING_BALANCE), null)) `CLOSING_BALANCE_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', IF(",VAR_REMOVE_PLANNED_SHIPMENTS,", IF(tssm.PLAN_BASED_ON=1, tssm.MOS_WPS, tssm.MAX_STOCK_QTY), IF(tssm.PLAN_BASED_ON=1, tssm.MOS, tssm.MAX_STOCK_QTY)), null)) `MOS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', tssm.SHIPMENT_QTY, null)) `SHIPMENT_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', IF(",VAR_REMOVE_PLANNED_SHIPMENTS,", tssm.EXPIRED_STOCK_WPS, tssm.EXPIRED_STOCK), null)) `EXPIRED_STOCK_QTY_",curMn,"` ");
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
        amc.TRANS_DATE, amc.PLANNING_UNIT_ID, ppu.MIN_MONTHS_OF_STOCK, ppu.MIN_QTY `MIN_STOCK_QTY`, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.PLAN_BASED_ON, amc.MAX_STOCK_QTY, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS, amc.MOS, amc.MOS_WPS, ppu.NOTES, amc.SHIPMENT_QTY, amc.EXPIRED_STOCK, amc.EXPIRED_STOCK_WPS, amc.AMC, amc.STOCK_MULTIPLIED_QTY
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

