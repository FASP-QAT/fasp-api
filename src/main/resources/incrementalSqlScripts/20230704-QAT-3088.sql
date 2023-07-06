DROP Table if exists `rm_procurement_agent_forecasting_unit`;
CREATE TABLE `fasp`.`rm_procurement_agent_forecasting_unit` (
  `FORECASTING_UNIT_PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL,
  `FOREACSTING_UNIT_ID` INT(10) UNSIGNED NULL,
  `PRODUCT_ID_NO_PACK` VARCHAR(9) NOT NULL,
  `PRODUCT_NAME_NO_PACK` VARCHAR(255) NOT NULL,
  `UNIT_LABEL` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`FORECASTING_UNIT_PROCUREMENT_AGENT_ID`),
  INDEX `idx_ProductIdNoPack` (`PRODUCT_ID_NO_PACK` ASC) VISIBLE,
  INDEX `idx_ProductNameNoPack` (`PRODUCT_NAME_NO_PACK` ASC) VISIBLE,
  INDEX `fk_rm_procurement_agent_forecasting_unit_forecastingUnitId_idx` (`FOREACSTING_UNIT_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_pa_fu_forecastingUnitId`
    FOREIGN KEY (`FOREACSTING_UNIT_ID`)
    REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` CHANGE COLUMN `FORECASTING_UNIT_PROCUREMENT_AGENT_ID` `FORECASTING_UNIT_PROCUREMENT_AGENT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT ;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` DROP FOREIGN KEY `fk_rm_pa_fu_forecastingUnitId`;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` ADD COLUMN `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL AFTER `FORECASTING_UNIT_PROCUREMENT_AGENT_ID`, ADD INDEX `fk_rm_pafu_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID` ASC) VISIBLE;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` RENAME INDEX `fk_rm_procurement_agent_forecasting_unit_forecastingUnitId_idx` TO `fk_rm_pafu_forecastingUnitId_idx`;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` ALTER INDEX `fk_rm_pafu_forecastingUnitId_idx` VISIBLE;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` 
ADD CONSTRAINT `fk_rm_pafu_forecastingUnitId`
  FOREIGN KEY (`FOREACSTING_UNIT_ID`)
  REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `fk_rm_pafu_procurementAgentId`
  FOREIGN KEY (`PROCUREMENT_AGENT_ID`)
  REFERENCES `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

DROP TABLE IF EXISTS `tmp_sku_code`;
CREATE TABLE `fasp`.`tmp_sku_code` (
  `SKU_CODE` VARCHAR(15) NOT NULL,
  `LABEL` VARCHAR(255) NOT NULL,
  `UNIT` VARCHAR(255) NOT NULL,
  `PF_MAX_ID` INT UNSIGNED NULL,
  `PF_MIN_ID` INT UNSIGNED NULL,
  PRIMARY KEY (`SKU_CODE`, `LABEL`, `UNIT`));

ALTER TABLE `fasp`.`tmp_sku_code` 
ADD COLUMN `FORECASTING_UNIT_ID` INT UNSIGNED NULL AFTER `PF_MIN_ID`;
