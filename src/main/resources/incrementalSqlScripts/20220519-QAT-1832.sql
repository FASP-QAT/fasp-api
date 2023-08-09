ALTER TABLE `fasp`.`ap_extrapolation_method` ADD COLUMN `SORT_ORDER` INT(10) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_extrapolation_method` AS
    SELECT 
        `em`.`EXTRAPOLATION_METHOD_ID` AS `EXTRAPOLATION_METHOD_ID`,
        `em`.`SORT_ORDER` AS `SORT_ORDER`,
        `em`.`LABEL_ID` AS `LABEL_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `em`.`ACTIVE` AS `ACTIVE`,
        `em`.`CREATED_BY` AS `CREATED_BY`,
        `em`.`CREATED_DATE` AS `CREATED_DATE`,
        `em`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `em`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`
    FROM
        (`ap_extrapolation_method` `em`
        LEFT JOIN `ap_label` `l` ON ((`em`.`LABEL_ID` = `l`.`LABEL_ID`)));

UPDATE ap_extrapolation_method em set em.SORT_ORDER=1 where em.EXTRAPOLATION_METHOD_ID=7;
UPDATE ap_extrapolation_method em set em.SORT_ORDER=2 where em.EXTRAPOLATION_METHOD_ID=6;
UPDATE ap_extrapolation_method em set em.SORT_ORDER=3 where em.EXTRAPOLATION_METHOD_ID=5;
UPDATE ap_extrapolation_method em set em.SORT_ORDER=4 where em.EXTRAPOLATION_METHOD_ID=2;
UPDATE ap_extrapolation_method em set em.SORT_ORDER=5 where em.EXTRAPOLATION_METHOD_ID=4;

UPDATE `fasp`.`ap_extrapolation_method` SET `LAST_MODIFIED_DATE`=NOW() WHERE `EXTRAPOLATION_METHOD_ID`='2'; 
UPDATE `fasp`.`ap_extrapolation_method` SET `LAST_MODIFIED_DATE`=NOW() WHERE `EXTRAPOLATION_METHOD_ID`='4'; 
UPDATE `fasp`.`ap_extrapolation_method` SET `LAST_MODIFIED_DATE`=NOW() WHERE `EXTRAPOLATION_METHOD_ID`='5'; 
UPDATE `fasp`.`ap_extrapolation_method` SET `LAST_MODIFIED_DATE`=NOW() WHERE `EXTRAPOLATION_METHOD_ID`='6'; 
UPDATE `fasp`.`ap_extrapolation_method` SET `LAST_MODIFIED_DATE`=NOW() WHERE `EXTRAPOLATION_METHOD_ID`='7'; 
