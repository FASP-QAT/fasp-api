CREATE TABLE `fasp`.`rm_integration_manual` (
  `MANUAL_INTEGRATION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT UNSIGNED NOT NULL,
  `VERSION_ID` INT UNSIGNED NOT NULL,
  `INTEGRATION_ID` INT UNSIGNED NOT NULL,
  `CREATED_BY` INT UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `COMPLETED_DATE` DATETIME NULL,
  PRIMARY KEY (`MANUAL_INTEGRATION_ID`),
  INDEX `fk_rm_integration_manual_programId_idx` (`PROGRAM_ID` ASC) VISIBLE,
  INDEX `fk_rm_integration_manual_versionId_idx` (`PROGRAM_ID` ASC, `VERSION_ID` ASC) VISIBLE,
  INDEX `fk_rm_integration_manual_integrationId_idx` (`INTEGRATION_ID` ASC) VISIBLE,
  INDEX `fk_rm_integration_manual_createdBy_idx` (`CREATED_BY` ASC) VISIBLE,
  CONSTRAINT `fk_rm_integration_manual_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_versionId`
    FOREIGN KEY (`PROGRAM_ID` , `VERSION_ID`)
    REFERENCES `fasp`.`rm_program_version` (`PROGRAM_ID` , `VERSION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_integrationId`
    FOREIGN KEY (`INTEGRATION_ID`)
    REFERENCES `fasp`.`ap_integration` (`INTEGRATION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



