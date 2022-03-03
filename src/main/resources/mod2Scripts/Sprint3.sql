
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` ADD COLUMN `CALCULATED_MMD_VALUE` DECIMAL(16,2) UNSIGNED NOT NULL AFTER `CALCULATED_VALUE`;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_pu` ADD COLUMN `PU_PER_VISIT` INT UNSIGNED NOT NULL AFTER `REFILL_MONTHS`;
ALTER TABLE `fasp`.`rm_tree_template_node_data_pu` ADD COLUMN `PU_PER_VISIT` INT UNSIGNED NOT NULL AFTER `REFILL_MONTHS`;
UPDATE rm_tree_template_node_data_pu SET PU_PER_VISIT = 1;
UPDATE rm_forecast_tree_node_data_pu SET PU_PER_VISIT = 1;

ALTER TABLE `fasp`.`rm_usage_template` ADD COLUMN `UNIT_ID` INT UNSIGNED NULL AFTER `LABEL_ID`, ADD INDEX `fk_rm_usage_template_unitId_idx` (`UNIT_ID` ASC);
UPDATE rm_usage_template ut SET ut.UNIT_ID=91;
ALTER TABLE `fasp`.`rm_usage_template` ADD CONSTRAINT `fk_rm_usage_template_unitId`  FOREIGN KEY (`UNIT_ID`)  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_dataset_planning_unit` ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `CREATED_DATE`;
UPDATE rm_dataset_planning_unit dpu SET dpu.ACTIVE=1;
ALTER TABLE `fasp`.`rm_usage_template` DROP FOREIGN KEY `fk_rm_usage_template_unitId`;
ALTER TABLE `fasp`.`rm_usage_template` CHANGE COLUMN `UNIT_ID` `UNIT_ID` INT UNSIGNED NOT NULL ;
ALTER TABLE `fasp`.`rm_usage_template` ADD CONSTRAINT `fk_rm_usage_template_unitId`  FOREIGN KEY (`UNIT_ID`)  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_usage_template` AS
    SELECT 
        `ut`.`USAGE_TEMPLATE_ID` AS `USAGE_TEMPLATE_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ut`.`UNIT_ID` AS `UNIT_ID`,
        `ut`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,
        `ut`.`LAG_IN_MONTHS` AS `LAG_IN_MONTHS`,
        `ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,
        `ut`.`NO_OF_PATIENTS` AS `NO_OF_PATIENTS`,
        `ut`.`NO_OF_FORECASTING_UNITS` AS `NO_OF_FORECASTING_UNITS`,
        `ut`.`ONE_TIME_USAGE` AS `ONE_TIME_USAGE`,
        `ut`.`USAGE_FREQUENCY_USAGE_PERIOD_ID` AS `USAGE_FREQUENCY_USAGE_PERIOD_ID`,
        `ut`.`USAGE_FREQUENCY_COUNT` AS `USAGE_FREQUENCY_COUNT`,
        `ut`.`REPEAT_USAGE_PERIOD_ID` AS `REPEAT_USAGE_PERIOD_ID`,
        `ut`.`REPEAT_COUNT` AS `REPEAT_COUNT`,
        `ut`.`NOTES` AS `NOTES`,
        `ut`.`ACTIVE` AS `ACTIVE`,
        `ut`.`CREATED_DATE` AS `CREATED_DATE`,
        `ut`.`CREATED_BY` AS `CREATED_BY`,
        `ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `l`.`LABEL_ID` AS `LABEL_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_usage_template` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));
