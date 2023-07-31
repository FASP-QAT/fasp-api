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




# ######################################################################################################################
# Do not run directly this is to be run only after the full import has been completed
# ######################################################################################################################

CREATE TABLE `fasp`.`tmp_pafu` (
  `PRODUCT_ID_NO_PACK` VARCHAR(9) NOT NULL,
  `MAX_PAFU_ID` INT NOT NULL,
  PRIMARY KEY (`PRODUCT_ID_NO_PACK`));

-- Get the last FORECASTING_UNIT_PROCUREMENT_AGENT_ID from the list
INSERT INTO tmp_pafu SELECT pafu.PRODUCT_ID_NO_PACK, MAX(pafu.FORECASTING_UNIT_PROCUREMENT_AGENT_ID) FROM rm_procurement_agent_forecasting_unit pafu where pafu.FOREACSTING_UNIT_ID<=6721 group by pafu.PRODUCT_ID_NO_PACK;

ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` ADD COLUMN `MATCHED` TINYINT NULL AFTER `UNIT_LABEL`;
-- Mark the latest records as Matched
UPDATE rm_procurement_agent_forecasting_unit pafu LEFT JOIN tmp_pafu ON pafu.FORECASTING_UNIT_PROCUREMENT_AGENT_ID=tmp_pafu.MAX_PAFU_ID SET pafu.MATCHED=1 WHERE tmp_pafu.MAX_PAFU_ID is not null;
-- Mark all FU's that are not part of the latest list as Active=0
UPDATE rm_forecasting_unit fu LEFT JOIN (SELECT pafu.FOREACSTING_UNIT_ID FROM rm_procurement_agent_forecasting_unit pafu WHERE pafu.MATCHED is null AND pafu.FOREACSTING_UNIT_ID NOT IN (SELECT pafu.FOREACSTING_UNIT_ID FROM rm_procurement_agent_forecasting_unit pafu WHERE pafu.MATCHED group by pafu.FOREACSTING_UNIT_ID) group by pafu.FOREACSTING_UNIT_ID) tfu ON fu.FORECASTING_UNIT_ID=tfu.FOREACSTING_UNIT_ID SET fu.ACTIVE=0, fu.LAST_MODIFIED_DATE=now(), fu.LAST_MODIFIED_BY=1 WHERE tfu.FOREACSTING_UNIT_ID IS NOT NULL;
-- Mark all PU's whose FU's are Active=0 as Active=0
UPDATE vw_planning_unit pu left join rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID SET pu.ACTIVE=0, pu.LAST_MODIFIED_BY=1, pu.LAST_MODIFIED_DATE=now() WHERE pu.ACTIVE AND fu.ACTIVE=0;
 