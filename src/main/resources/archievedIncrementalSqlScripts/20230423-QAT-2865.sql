ALTER TABLE `fasp`.`rm_supply_plan_batch_qty` ADD INDEX `idx_transDate` (`TRANS_DATE` ASC);
ALTER TABLE `fasp`.`rm_supply_plan_batch_qty` ADD INDEX `idx_expiredStock` (`EXPIRED_STOCK` ASC);
ALTER TABLE `fasp`.`rm_supply_plan_batch_qty` ADD INDEX `idx_expiredStockWps` (`EXPIRED_STOCK_WPS` ASC);


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
        bTrans.OPENING_BALANCE `BATCH_OPENING_BALANCE`, bTrans.OPENING_BALANCE_WPS `BATCH_OPENING_BALANCE_WPS`,
        bTrans.EXPIRED_STOCK `BATCH_EXPIRED_STOCK`, bTrans.EXPIRED_STOCK_WPS `BATCH_EXPIRED_STOCK_WPS`, 
        bTrans.STOCK_MULTIPLIED_QTY `BATCH_STOCK_MULTIPLIED_QTY`, bTrans.ADJUSTMENT_MULTIPLIED_QTY `BATCH_ADJUSTMENT_MULTIPLIED_QTY`, bTrans.ACTUAL_CONSUMPTION_QTY `BATCH_CONSUMPTION_QTY`,
        bTrans.SHIPMENT_QTY `BATCH_SHIPMENT_QTY`, bTrans.SHIPMENT_QTY_WPS `BATCH_SHIPMENT_QTY_WPS`,
        bTrans.CALCULATED_CONSUMPTION `BATCH_CALCULATED_CONSUMPTION_QTY`, bTrans.CALCULATED_CONSUMPTION_WPS `BATCH_CALCULATED_CONSUMPTION_QTY_WPS`,
        bTrans.CLOSING_BALANCE `BATCH_CLOSING_BALANCE`, bTrans.CLOSING_BALANCE_WPS `BATCH_CLOSING_BALANCE_WPS`
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


