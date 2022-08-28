ALTER TABLE `fasp`.`rm_tree_template` 
ADD COLUMN `BRANCH` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `NOTES`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template` AS
    SELECT 
        `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tt`.`REALM_ID` AS `REALM_ID`,
        `tt`.`LABEL_ID` AS `LABEL_ID`,
        `tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `tt`.`MONTHS_IN_PAST` AS `MONTHS_IN_PAST`,
        `tt`.`MONTHS_IN_FUTURE` AS `MONTHS_IN_FUTURE`,
        `tt`.`CREATED_BY` AS `CREATED_BY`,
        `tt`.`CREATED_DATE` AS `CREATED_DATE`,
        `tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tt`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `tt`.`NOTES` AS `NOTES`,
        `tt`.`BRANCH` AS `BRANCH`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));
