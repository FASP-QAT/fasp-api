CREATE TABLE `fasp`.`rm_budget_program` (
  `BUDGET_PROGRAM_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `BUDGET_ID` INT UNSIGNED NOT NULL,
  `PROGRAM_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`BUDGET_PROGRAM_ID`),
  INDEX `fk_budget_program_budgetId_idx` (`BUDGET_ID` ASC) ,
  INDEX `fk_budget_program_programId_idx` (`PROGRAM_ID` ASC) ,
  CONSTRAINT `fk_budget_program_budgetId`
    FOREIGN KEY (`BUDGET_ID`)
    REFERENCES `fasp`.`rm_budget` (`BUDGET_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budget_program_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO rm_budget_program SELECT null, b.BUDGET_ID, b.PROGRAM_ID FROM rm_budget b;
UPDATE rm_budget b left join rm_program p ON b.PROGRAM_ID=p.PROGRAM_ID SET b.BUDGET_CODE=CONCAT(p.PROGRAM_ID,'-',b.BUDGET_CODE) WHERE LENGTH(CONCAT(p.PROGRAM_ID,'-',b.BUDGET_CODE))<=30;

ALTER TABLE `fasp`.`rm_budget` DROP FOREIGN KEY `fk_budget_programId`;
ALTER TABLE `fasp`.`rm_budget` DROP INDEX `fk_budget_programId1_idx` ;

ALTER TABLE `fasp`.`rm_budget` CHANGE COLUMN `PROGRAM_ID` `REALM_ID` INT UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realmthat this budget is for' ;
UPDATE rm_budget b LEFT JOIN rm_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID SET b.REALM_ID=fs.REALM_ID;

ALTER TABLE `fasp`.`rm_budget` ADD INDEX `fk_rm_budget_realmId_idx` (`REALM_ID` ASC)

ALTER TABLE `fasp`.`rm_budget` ADD CONSTRAINT `fk_rm_budget_realmId`
  FOREIGN KEY (`REALM_ID`)
  REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_budget` AS
    SELECT 
        `b`.`BUDGET_ID` AS `BUDGET_ID`,
        `b`.`BUDGET_CODE` AS `BUDGET_CODE`,
        `b`.`REALM_ID` AS `REALM_ID`,
        `b`.`FUNDING_SOURCE_ID` AS `FUNDING_SOURCE_ID`,
        `b`.`LABEL_ID` AS `LABEL_ID`,
        `b`.`CURRENCY_ID` AS `CURRENCY_ID`,
        `b`.`BUDGET_AMT` AS `BUDGET_AMT`,
        `b`.`CONVERSION_RATE_TO_USD` AS `CONVERSION_RATE_TO_USD`,
        `b`.`START_DATE` AS `START_DATE`,
        `b`.`STOP_DATE` AS `STOP_DATE`,
        `b`.`NOTES` AS `NOTES`,
        `b`.`ACTIVE` AS `ACTIVE`,
        `b`.`CREATED_BY` AS `CREATED_BY`,
        `b`.`CREATED_DATE` AS `CREATED_DATE`,
        `b`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `b`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `bl`.`LABEL_EN` AS `LABEL_EN`,
        `bl`.`LABEL_FR` AS `LABEL_FR`,
        `bl`.`LABEL_SP` AS `LABEL_SP`,
        `bl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_budget` `b`
        LEFT JOIN `ap_label` `bl` ON ((`b`.`LABEL_ID` = `bl`.`LABEL_ID`)));

DROP PROCEDURE `fasp`.`budgetReport`;