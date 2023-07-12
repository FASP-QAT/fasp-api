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

ALTER TABLE `fasp`.`rm_budget_program` ADD UNIQUE INDEX `unq_budget_program_budgetId_programId` (`BUDGET_ID` ASC, `PROGRAM_ID` ASC);

INSERT INTO rm_budget_program SELECT null, b.BUDGET_ID, b.PROGRAM_ID FROM rm_budget b;
UPDATE rm_budget b left join rm_program p ON b.PROGRAM_ID=p.PROGRAM_ID SET b.BUDGET_CODE=CONCAT(p.PROGRAM_ID,'-',b.BUDGET_CODE) WHERE LENGTH(CONCAT(p.PROGRAM_ID,'-',b.BUDGET_CODE))<=30;

ALTER TABLE `fasp`.`rm_budget` DROP FOREIGN KEY `fk_budget_programId`;
ALTER TABLE `fasp`.`rm_budget` DROP INDEX `fk_budget_programId1_idx` ;

ALTER TABLE `fasp`.`rm_budget` CHANGE COLUMN `PROGRAM_ID` `REALM_ID` INT UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realmthat this budget is for' ;
UPDATE rm_budget b LEFT JOIN rm_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID SET b.REALM_ID=fs.REALM_ID;

ALTER TABLE `fasp`.`rm_budget` ADD INDEX `fk_rm_budget_realmId_idx` (`REALM_ID` ASC);

ALTER TABLE `fasp`.`rm_budget` ADD CONSTRAINT `fk_rm_budget_realmId`
  FOREIGN KEY (`REALM_ID`)
  REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
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


ALTER TABLE `fasp`.`rm_budget` DROP INDEX `unq_budgetCode` ;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commit.untaggedShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This program contains shipments tagged to a budget that is no longer associated with this program. To proceed with committing, please re-assign those shipments to a different budget or re-assign the budget to this program.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce programme contient des expéditions associées à un budget qui n`est plus associé à ce programme. Pour procéder à l`engagement, veuillez réaffecter ces envois à un budget différent ou réaffecter le budget à ce programme.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este programa contiene envíos etiquetados a un presupuesto que ya no está asociado con este programa. Para continuar con la confirmación, vuelva a asignar esos envíos a un presupuesto diferente o vuelva a asignar el presupuesto a este programa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este programa contém remessas marcadas para um orçamento que não está mais associado a este programa. Para prosseguir com a confirmação, reatribua essas remessas a um orçamento diferente ou reatribua o orçamento a este programa.');-- pr