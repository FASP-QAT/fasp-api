ALTER TABLE `fasp`.`rm_equivalency_unit` 
ADD COLUMN `PROGRAM_ID` INT UNSIGNED NULL AFTER `REALM_ID`,
ADD INDEX `fk_rm_equivalency_unit_programId_idx` (`PROGRAM_ID` ASC);

ALTER TABLE `fasp`.`rm_equivalency_unit` 
ADD CONSTRAINT `fk_rm_equivalency_unit_programId`
  FOREIGN KEY (`PROGRAM_ID`)
  REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`rm_equivalency_unit` 
ADD COLUMN `NOTES` TEXT NULL AFTER `LABEL_ID`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_equivalency_unit` AS
    SELECT 
        `ut`.`EQUIVALENCY_UNIT_ID` AS `EQUIVALENCY_UNIT_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`PROGRAM_ID` AS `PROGRAM_ID`,
        GROUP_CONCAT(`euha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
        `ut`.`NOTES` AS `NOTES`,
        `ut`.`ACTIVE` AS `ACTIVE`,
        `ut`.`CREATED_BY` AS `CREATED_BY`,
        `ut`.`CREATED_DATE` AS `CREATED_DATE`,
        `ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        ((`rm_equivalency_unit` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)))
        LEFT JOIN `rm_equivalency_unit_health_area` `euha` ON ((`ut`.`EQUIVALENCY_UNIT_ID` = `euha`.`EQUIVALENCY_UNIT_ID`)))
    GROUP BY `ut`.`EQUIVALENCY_UNIT_ID`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_all_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `p`.`ACTIVE` AS `ACTIVE`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    GROUP BY `p`.`PROGRAM_ID`;

UPDATE rm_equivalency_unit_mapping eum SET eum.PROGRAM_ID=null where eum.PROGRAM_ID=0;
UPDATE rm_equivalency_unit eum SET eum.PROGRAM_ID=null where eum.PROGRAM_ID=0;

UPDATE rm_equivalency_unit_mapping eum SET eum.LAST_MODIFIED_DATE=now();
UPDATE rm_equivalency_unit eum  SET eum.LAST_MODIFIED_DATE=now();