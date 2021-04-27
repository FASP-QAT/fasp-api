/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 16-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDataEntry.expiryDateMustBeGreaterThanEDD','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expiry date must be greater than received date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La date d`expiration doit être postérieure à la date de réception');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fecha de vencimiento debe ser mayor que la fecha de recepción');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A data de expiração deve ser maior que a data de recebimento');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.confirmBatchInfo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Confirm batch information to continue');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Confirmer les informations sur le lot pour continuer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Confirme la información del lote para continuar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Confirme as informações do lote para continuar');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.batchLedger.note','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'*Auto-Allocated: Consumption or Adjustment as calculated by QAT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'*Alloué automatiquement: consommation ou ajustement calculé par QAT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'*Asignación automática: consumo o ajuste calculado por QAT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'*Alocado automaticamente: Consumo ou ajuste conforme calculado pelo QAT');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.userEnteredBatches','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-Entered Batches');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lots saisis par l`utilisateur');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lotes ingresados ​​por el usuario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lotes inseridos pelo usuário');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.autoAllocated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-Allocated*');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alloué automatiquement*');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Asignado automáticamente*');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alocado automaticamente*');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.expiry','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expiry');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expiration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Expiración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Termo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.batchLedger','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Batch Ledger');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Grand livre de lots');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Libro mayor de lotes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Batch Ledger');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.batchInfoNote','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,"Click on 'Expired Quantity' to display batch ledger");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"Cliquez sur 'Quantité expirée' pour afficher le grand livre des lots");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,"Haga clic en 'Cantidad vencida' para mostrar el libro mayor de lotes");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"Clique em 'Quantidade Expirada' para exibir o razão do lote");

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.expiryReport.batchInfoNote','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,"Click on 'Expired/Expiring Quantity' to display batch ledger");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"Cliquez sur 'Quantité expirée / expirante' pour afficher le grand livre des lots");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,"Haga clic en 'Cantidad vencida / vencida' para mostrar el libro mayor de lotes");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"Clique em 'Quantidade Expirada / Expirando' para exibir o razão do lote");

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.missingBatchNote','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,"Batches are created by shipments. If you don't see the desired batch in the dropdown, please edit shipment data.");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"Les lots sont créés par envois. Si vous ne voyez pas le lot souhaité dans la liste déroulante, veuillez modifier les données d'expédition.");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,"Los lotes se crean mediante envíos. Si no ve el lote deseado en el menú desplegable, edite los datos del envío.");
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"Os lotes são criados por remessas. Se você não vir o lote desejado na lista suspensa, edite os dados de envio.");

DROP TABLE IF EXISTS `rm_supply_plan_old`;
DROP TABLE IF EXISTS `rm_supply_plan_batch_info_old`;
DROP TABLE IF EXISTS `rm_supply_plan_batch_info`;
DROP TABLE IF EXISTS `rm_supply_plan`;

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
        timestampdiff(MONTH, CONCAT(LEFT(bi.CREATED_DATE,7),"-01"),bi.EXPIRY_DATE) `SHELF_LIFE`,
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
    WHERE ppu.ACTIVE AND pu.ACTIVE AND spbq.PROGRAM_ID=@programId AND spbq.VERSION_ID=@versionId AND spbq.TRANS_DATE BETWEEN @startDate AND @stopDate AND (@includePlannedShipments=1 AND spbq.EXPIRED_STOCK>0 OR @includePlannedShipments=0 AND spbq.EXPIRED_STOCK_WPS>0)
    ORDER BY bi.EXPIRY_DATE, pu.LABEL_EN, spbq.BATCH_ID, bTrans.TRANS_DATE;
    
END$$

DELIMITER ;

