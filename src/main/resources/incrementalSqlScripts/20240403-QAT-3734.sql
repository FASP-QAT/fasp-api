/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  akil
 * Created: 04-Apr-2024
 */

ALTER TABLE `fasp`.`rm_realm_country_planning_unit` 
ADD COLUMN `CONVERSION_METHOD` INT UNSIGNED NOT NULL COMMENT '1 for Multiply and 2 for Divide' AFTER `UNIT_ID`,
CHANGE COLUMN `MULTIPLIER` `CONVERSION_NUMBER` DECIMAL(16,6) UNSIGNED NOT NULL ;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_realm_country_planning_unit` AS
    SELECT 
        `rcpu`.`REALM_COUNTRY_PLANNING_UNIT_ID` AS `REALM_COUNTRY_PLANNING_UNIT_ID`,
        `rcpu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,
        `rcpu`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        `rcpu`.`LABEL_ID` AS `LABEL_ID`,
        `rcpu`.`SKU_CODE` AS `SKU_CODE`,
        `rcpu`.`UNIT_ID` AS `UNIT_ID`,
        `rcpu`.`CONVERSION_METHOD` AS `CONVERSION_METHOD`,
        `rcpu`.`CONVERSION_NUMBER` AS `CONVERSION_NUMBER`,
        `rcpu`.`GTIN` AS `GTIN`,
        `rcpu`.`ACTIVE` AS `ACTIVE`,
        `rcpu`.`CREATED_BY` AS `CREATED_BY`,
        `rcpu`.`CREATED_DATE` AS `CREATED_DATE`,
        `rcpu`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `rcpu`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `rcpul`.`LABEL_EN` AS `LABEL_EN`,
        `rcpul`.`LABEL_FR` AS `LABEL_FR`,
        `rcpul`.`LABEL_SP` AS `LABEL_SP`,
        `rcpul`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_realm_country_planning_unit` `rcpu`
        LEFT JOIN `ap_label` `rcpul` ON ((`rcpu`.`LABEL_ID` = `rcpul`.`LABEL_ID`)));
