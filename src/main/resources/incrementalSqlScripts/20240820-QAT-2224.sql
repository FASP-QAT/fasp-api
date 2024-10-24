CREATE TABLE `fasp`.`rm_batch_inventory` (
  `BATCH_INVENTORY_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT UNSIGNED NOT NULL,
  `PLANNING_UNIT_ID` INT UNSIGNED NOT NULL,
  `INVENTORY_DATE` DATE NOT NULL,
  `MAX_VERSION_ID` INT UNSIGNED NOT NULL,
  `TMP_ID` INT UNSIGNED NULL,
  PRIMARY KEY (`BATCH_INVENTORY_ID`));


ALTER TABLE `fasp`.`rm_batch_inventory` 
ADD INDEX `fk_rm_batch_inventory_programId_idx` (`PROGRAM_ID` ASC) VISIBLE,
ADD INDEX `fk_rm_batch_inventory_planningUnitId_idx` (`PLANNING_UNIT_ID` ASC) VISIBLE,
ADD INDEX `idx_rm_batch_inventory_inventoryDate` (`INVENTORY_DATE` ASC) VISIBLE;
;
ALTER TABLE `fasp`.`rm_batch_inventory` 
ADD CONSTRAINT `fk_rm_batch_inventory_programId`
  FOREIGN KEY (`PROGRAM_ID`)
  REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_batch_inventory_planningUnitId`
  FOREIGN KEY (`PLANNING_UNIT_ID`)
  REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_batch_inventory` 
ADD INDEX `idx_rm_batch_inventory_maxVersionId` (`MAX_VERSION_ID` ASC) VISIBLE;
;

CREATE TABLE `fasp`.`rm_batch_inventory_trans` (
  `BATCH_INVENTORY_TRANS_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `BATCH_INVENTORY_ID` INT UNSIGNED NOT NULL,
  `BATCH_ID` INT UNSIGNED NOT NULL,
  `QTY` INT UNSIGNED NULL,
  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NULL,
  `VERSION_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`BATCH_INVENTORY_TRANS_ID`),
  INDEX `fk_rm_batch_inventory_trans_batchInventoryId_idx` (`BATCH_INVENTORY_ID` ASC) VISIBLE,
  INDEX `fk_rm_batch_inventory_trans_batchId_idx` (`BATCH_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_batch_inventory_trans_batchInventoryId`
    FOREIGN KEY (`BATCH_INVENTORY_ID`)
    REFERENCES `fasp`.`rm_batch_inventory` (`BATCH_INVENTORY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_batch_inventory_trans_batchId`
    FOREIGN KEY (`BATCH_ID`)
    REFERENCES `fasp`.`rm_batch_info` (`BATCH_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `fasp`.`rm_batch_inventory_trans` 
CHANGE COLUMN `LAST_MODIFIED_BY` `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL ,
ADD INDEX `fk_rm_batch_inventory_trans_lmb_idx` (`LAST_MODIFIED_BY` ASC) VISIBLE;
;
ALTER TABLE `fasp`.`rm_batch_inventory_trans` 
ADD CONSTRAINT `fk_rm_batch_inventory_trans_lmb`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_batch_inventory_trans` 
ADD INDEX `idx_rm_batch_inventory_trans_versionId` (`VERSION_ID` ASC) VISIBLE;
;


USE `fasp`;
DROP procedure IF EXISTS `getBatchInventoryData`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getBatchInventoryData`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getBatchInventoryData`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_ACTIVE TINYINT(1), CUT_OFF_DATE DATE)
BEGIN
    SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUmitActive= PLANNING_UNIT_ACTIVE;
    IF @versionId = -1 THEN 
        SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;
    SET @cutOffDate = CUT_OFF_DATE;
    SET @useCutOff = false;
    IF @cutOffDate is not null && LENGTH(@cutOffDate)!=0 THEN
        SET @useCutOff = true;
    END IF;
    
    SELECT bi.BATCH_INVENTORY_ID, bi.PROGRAM_ID, bt.VERSION_ID, bi.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, bi.INVENTORY_DATE, bt.BATCH_INVENTORY_TRANS_ID, bt.BATCH_ID, bt.QTY, b.BATCH_NO, b.AUTO_GENERATED, b.EXPIRY_DATE, b.CREATED_DATE `BATCH_CREATED_DATE`, bi.CREATED_DATE, bt.LAST_MODIFIED_DATE, cb.USER_ID `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`
    FROM (SELECT bi.BATCH_INVENTORY_ID,bt.BATCH_ID, MAX(bt.VERSION_ID) MAX_VERSION_ID FROM rm_batch_inventory bi LEFT JOIN rm_batch_inventory_trans bt ON bi.BATCH_INVENTORY_ID=bt.BATCH_INVENTORY_ID WHERE (@versionId=-1 OR bt.VERSION_ID<=@versionId) AND bi.PROGRAM_ID=@programId GROUP BY bt.BATCH_INVENTORY_ID,bt.BATCH_ID) tb
    LEFT JOIN rm_batch_inventory bi ON tb.BATCH_INVENTORY_ID=bi.BATCH_INVENTORY_ID
    LEFT JOIN rm_batch_inventory_trans bt ON bt.BATCH_ID=tb.BATCH_ID and bt.VERSION_ID=tb.MAX_VERSION_ID
    LEFT JOIN rm_program_planning_unit ppu ON bi.PROGRAM_ID=ppu.PROGRAM_ID AND bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_batch_info b ON bt.BATCH_ID=b.BATCH_ID 
    LEFT JOIN us_user cb ON bi.CREATED_BY=cb.USER_ID
    LEFT JOIN us_user lmb ON bt.LAST_MODIFIED_BY=lmb.USER_ID
    WHERE (@planningUnitActive = FALSE OR ppu.ACTIVE) AND (@useCutOff = FALSE OR (@useCutOff = TRUE AND bi.INVENTORY_DATE>=DATE_SUB(@cutOffDate,INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH))) AND bt.ACTIVE
    ORDER BY bi.PLANNING_UNIT_ID, bi.INVENTORY_DATE, bt.BATCH_ID;
    
END$$

DELIMITER ;
;



ALTER TABLE `fasp`.`rm_batch_inventory` 
ADD COLUMN `CREATED_DATE` DATETIME NULL AFTER `TMP_ID`,
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NULL AFTER `CREATED_DATE`,
ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NULL AFTER `CREATED_BY`,
ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NULL AFTER `LAST_MODIFIED_DATE`,
ADD INDEX `fk_rm_batch_inventory_createdBy_idx` (`CREATED_BY` ASC) VISIBLE,
ADD INDEX `fk_rm_batch_inventory_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC) VISIBLE;
UPDATE rm_batch_inventory bi SET bi.CREATED_DATE=now(), bi.CREATED_BY=1, bi.LAST_MODIFIED_DATE=now(), bi.LAST_MODIFIED_BY=1;
ALTER TABLE `fasp`.`rm_batch_inventory` 
CHANGE COLUMN `TMP_ID` `TMP_ID` INT UNSIGNED NULL DEFAULT NULL AFTER `LAST_MODIFIED_BY`,
CHANGE COLUMN `CREATED_DATE` `CREATED_DATE` DATETIME NOT NULL ,
CHANGE COLUMN `CREATED_BY` `CREATED_BY` INT UNSIGNED NOT NULL ,
CHANGE COLUMN `LAST_MODIFIED_DATE` `LAST_MODIFIED_DATE` DATETIME NOT NULL ,
CHANGE COLUMN `LAST_MODIFIED_BY` `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL ;

ALTER TABLE `fasp`.`rm_batch_inventory` 
ADD CONSTRAINT `fk_rm_batch_inventory_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_batch_inventory_lastModifiedBy`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `fasp`.`rm_batch_inventory_trans` 
ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 AFTER `LAST_MODIFIED_DATE`;

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
        SUM(o.SHIPMENT) `SHIPMENT`, SUM(o.SHIPMENT_WPS) `SHIPMENT_WPS`, SUM(o.ADJUSTMENT) `ADJUSTMENT`, SUM(o.STOCK) `STOCK`, SUM(o.INVENTORY_QTY) `INVENTORY_QTY`  
        FROM ( 
            SELECT 
            tc.PROGRAM_ID, tc.CONSUMPTION_ID `TRANS_ID`, tc.PLANNING_UNIT_ID, LEFT(tc.CONSUMPTION_DATE, 7) `TRANS_DATE`, tc.BATCH_ID, tc.EXPIRY_DATE `EXPIRY_DATE`, tc.SHELF_LIFE,
            SUM(FORECASTED_CONSUMPTION) `FORECASTED_CONSUMPTION`, SUM(ACTUAL_CONSUMPTION) `ACTUAL_CONSUMPTION`, 
            null `SHIPMENT`, null `SHIPMENT_WPS`,null `ADJUSTMENT`, null  `STOCK`, 0 `INVENTORY_QTY` 
            FROM (
                SELECT 
                    c.PROGRAM_ID, c.CONSUMPTION_ID, ct.REGION_ID, ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, 
                    ctbi.BATCH_ID `BATCH_ID`, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                    SUM(IF(ct.ACTUAL_FLAG=1, COALESCE(ROUND(ctbi.CONSUMPTION_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))), ct.CONSUMPTION_QTY), null)) `ACTUAL_CONSUMPTION`, 
                    SUM(IF(ct.ACTUAL_FLAG=0, COALESCE(ROUND(ctbi.CONSUMPTION_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))), ct.CONSUMPTION_QTY), null)) `FORECASTED_CONSUMPTION`
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

            SELECT 
                s.PROGRAM_ID, s.SHIPMENT_ID `TRANS_ID`, st.PLANNING_UNIT_ID, LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7) `TRANS_DATE`, stbi.BATCH_ID, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                SUM(IF(st.SHIPMENT_STATUS_ID IN (1,3,4,5,6,7,9), COALESCE(ROUND(stbi.BATCH_SHIPMENT_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) ,st.SHIPMENT_QTY),0)) `SHIPMENT`, 
                SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9), COALESCE(ROUND(stbi.BATCH_SHIPMENT_QTY * IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) ,st.SHIPMENT_QTY), 0)) `SHIPMENT_WPS`, 
                null  `ADJUSTMENT_MULTIPLIED_QTY`, null  `STOCK_MULTIPLIED_QTY`, 0 `INVENTORY_QTY` 
            FROM (
                SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
            ) ts
            LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
            LEFT JOIN rm_shipment_trans_batch_info stbi ON st.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID
            LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID
            LEFT JOIN rm_realm_country_planning_unit rcpu ON st.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
            LEFT JOIN rm_program_planning_unit ppu ON s.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
            WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 AND stbi.BATCH_ID IS NOT NULL  AND ppu.PLANNING_UNIT_ID IS NOT NULL 
            GROUP BY s.PROGRAM_ID, st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE), stbi.BATCH_ID

            UNION

            SELECT 
                i.PROGRAM_ID, i.INVENTORY_ID `TRANS_ID`, rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`, itbi.BATCH_ID, bi.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                null `SHIPMENT`, null `SHIPMENT_WPS`, SUM(COALESCE(itbi.ADJUSTMENT_QTY, it.ADJUSTMENT_QTY)*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `ADJUSTMENT`,  SUM(COALESCE(itbi.ACTUAL_QTY, it.ACTUAL_QTY)*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))) `STOCK`, 0 `INVENTORY_QTY` 
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

            SELECT 
                bi.PROGRAM_ID, bi.BATCH_INVENTORY_ID `TRANS_ID`, bi.PLANNING_UNIT_ID, LEFT(bi.INVENTORY_DATE,7) `TRANS_DATE`, bt.BATCH_ID, rm_batch_info.EXPIRY_DATE, IFNULL(ppu.SHELF_LIFE,24) `SHELF_LIFE`,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, 
                null `SHIPMENT`, null `SHIPMENT_WPS`, null `ADJUSTMENT`,  null `STOCK`, bt.QTY `INVENTORY_QTY` 
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

update rm_program_planning_unit ppu set ppu.LAST_MODIFIED_DATE=now();