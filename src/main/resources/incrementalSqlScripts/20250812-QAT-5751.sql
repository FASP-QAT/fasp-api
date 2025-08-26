/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 12-Aug-2025
 */

ALTER TABLE `fasp`.`rm_inventory_trans` ADD COLUMN `ADD_NEW_BATCH` TINYINT(3) UNSIGNED DEFAULT '0' NOT NULL AFTER `ACTIVE`; 



USE `fasp`;
DROP procedure IF EXISTS `getInventoryDataNew`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getInventoryDataNew`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getInventoryDataNew`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_ACTIVE TINYINT(1), CUT_OFF_DATE DATE)
BEGIN
    SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUnitActive = PLANNING_UNIT_ACTIVE;
    SET @cutOffDate = CUT_OFF_DATE; 
    SET @useCutOff = false;
    IF @cutOffDate is not null && LENGTH(@cutOffDate)!=0 THEN
        SET @useCutOff = true;
    END IF;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @oldRCPU = 0;
    SET @oldAdjustment = 0;
    SET @bal = 0;
    
    SELECT @useCutOff, a.*, itbi.INVENTORY_TRANS_BATCH_INFO_ID, itbi.BATCH_ID, bi.PLANNING_UNIT_ID `BATCH_PLANNING_UNIT_ID`, bi.BATCH_NO, bi.AUTO_GENERATED, bi.EXPIRY_DATE, itbi.ACTUAL_QTY `BATCH_ACTUAL_QTY`, bi.CREATED_DATE `BATCH_CREATED_DATE`, itbi.ADJUSTMENT_QTY `BATCH_ADJUSTMENT_QTY` 
    FROM (
        SELECT 
            der.*, 
            @oldAdjustment:=IF(@oldRCPU!=der.REALM_COUNTRY_PLANNING_UNIT_ID, 0, @oldAdjustment) `oldAdjustment`,
            @bal:=IF(@oldRCPU!=der.REALM_COUNTRY_PLANNING_UNIT_ID, 0, @bal+@oldAdjustment) `EXPECTED_BAL`, 
            @oldRCPU := der.REALM_COUNTRY_PLANNING_UNIT_ID `oldRCPU`,
            @oldAdjustment:=der.ADJUSTMENT_QTY
        FROM (
            SELECT 
                it.INVENTORY_ID, it.INVENTORY_DATE, it.ACTUAL_QTY, it.ADJUSTMENT_QTY, IF (rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2, 1/rcpu.CONVERSION_NUMBER, 0)) `MULTIPLIER`, pu.MULTIPLIER `CONVERSION_FACTOR`, it.VERSION_ID, it.NOTES, it.INVENTORY_TRANS_ID,
                p.PROGRAM_ID, pl.LABEL_ID `PROGRAM_LABEL_ID`, pl.LABEL_EN `PROGRAM_LABEL_EN`, pl.LABEL_FR `PROGRAM_LABEL_FR`, pl.LABEL_SP `PROGRAM_LABEL_SP`, pl.LABEL_PR `PROGRAM_LABEL_PR`,
                r.REGION_ID, rl.LABEL_ID `REGION_LABEL_ID`, rl.LABEL_EN `REGION_LABEL_EN`, rl.LABEL_FR `REGION_LABEL_FR`, rl.LABEL_SP `REGION_LABEL_SP`, rl.LABEL_PR `REGION_LABEL_PR`,
                rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, rcpul.LABEL_ID `REALM_COUNTRY_PLANNING_UNIT_LABEL_ID`, rcpul.LABEL_EN `REALM_COUNTRY_PLANNING_UNIT_LABEL_EN`, rcpul.LABEL_FR `REALM_COUNTRY_PLANNING_UNIT_LABEL_FR`, rcpul.LABEL_SP `REALM_COUNTRY_PLANNING_UNIT_LABEL_SP`, rcpul.LABEL_PR `REALM_COUNTRY_PLANNING_UNIT_LABEL_PR`,
                pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
                fu.FORECASTING_UNIT_ID, ful.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ful.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ful.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ful.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, ful.LABEL_PR `FORECASTING_UNIT_LABEL_PR`,
                pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`,
                ds.DATA_SOURCE_ID, dsl.LABEL_ID `DATA_SOURCE_LABEL_ID`, dsl.LABEL_EN `DATA_SOURCE_LABEL_EN`, dsl.LABEL_FR `DATA_SOURCE_LABEL_FR`, dsl.LABEL_SP `DATA_SOURCE_LABEL_SP`, dsl.LABEL_PR `DATA_SOURCE_LABEL_PR`,
                u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_SP `UNIT_LABEL_SP`, ul.LABEL_PR `UNIT_LABEL_PR`,
                cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, i.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, it.LAST_MODIFIED_DATE, it.ACTIVE, it.ADD_NEW_BATCH
            FROM (SELECT i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND (it.VERSION_ID<=@versionId OR @versionId=-1) GROUP BY i.INVENTORY_ID) tc 
            LEFT JOIN rm_inventory i ON tc.INVENTORY_ID=i.INVENTORY_ID
            LEFT JOIN rm_inventory_trans it ON tc.INVENTORY_ID=it.INVENTORY_ID AND tc.MAX_VERSION_ID=it.VERSION_ID
            LEFT JOIN rm_program p ON i.PROGRAM_ID=p.PROGRAM_ID
            LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID
            LEFT JOIN rm_region r ON it.REGION_ID=r.REGION_ID
            LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID
            LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
            LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID
            LEFT JOIN rm_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
            LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID
            LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
            LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID
            LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
            LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID
            LEFT JOIN rm_data_source ds ON it.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
            LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID
            LEFT JOIN ap_unit u ON rcpu.UNIT_ID=u.UNIT_ID
            LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID
            LEFT JOIN us_user cb ON i.CREATED_BY=cb.USER_ID
            LEFT JOIN us_user lmb ON it.LAST_MODIFIED_BY=lmb.USER_ID
        ) as der 
        ORDER BY der.PLANNING_UNIT_ID, der.REALM_COUNTRY_PLANNING_UNIT_ID, der.REGION_ID, der.INVENTORY_DATE
    ) a 
    LEFT JOIN rm_inventory_trans_batch_info itbi ON a.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID
    LEFT JOIN rm_batch_info bi ON itbi.BATCH_ID=bi.BATCH_ID
    LEFT JOIN rm_program_planning_unit ppu ON a.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=@programId
    WHERE (@planningUnitActive = FALSE OR ppu.ACTIVE) AND (@useCutOff = FALSE OR (@useCutOff = TRUE AND a.INVENTORY_DATE>=@cutOffDate))
    ORDER BY a.PLANNING_UNIT_ID, a.REALM_COUNTRY_PLANNING_UNIT_ID, a.REGION_ID, a.INVENTORY_DATE, bi.EXPIRY_DATE, bi.BATCH_ID;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.addNewBatch','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Input New Batch and Expiry Information');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisir les informations relatives au nouveau lot et à la date d`expiration');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la información del nuevo lote y la fecha de vencimiento.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Introduza o novo lote e as informações de validade');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDataEntry.expiryDateMustBeGreaterThanAdjustmentDate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expiry date must be greater than Inventory Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La date d`expiration doit être supérieure à la date d`inventaire.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fecha de vencimiento debe ser posterior a la fecha de inventario.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A data de validade deve ser posterior à data de stock.');-- pr