
CREATE TABLE `rm_integration_program_history` (
  `INTEGRATION_PROGRAM_HISTORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTEGRATION_PROGRAM_ID` int unsigned NOT NULL,
  `INTEGRATION_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_TYPE_ID` int unsigned NOT NULL COMMENT 'null for all versionTypes, for value for actual',
  `VERSION_STATUS_ID` int unsigned NOT NULL COMMENT 'null for all versionStatuses, for value for actual',
  `ACTIVE` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`INTEGRATION_PROGRAM_HISTORY_ID`),
  KEY `fk_rm_integration_program_history_integrationProgramId_idx` (`INTEGRATION_PROGRAM_ID`),
  KEY `fk_rm_integration_program_history_integrationId_idx` (`INTEGRATION_ID`),
  KEY `fk_rm_integration_program_history_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_integration_program_history_versionTypeId_idx` (`VERSION_TYPE_ID`),
  KEY `fk_rm_integration_program_history_versionStatusId_idx` (`VERSION_STATUS_ID`),
  CONSTRAINT `fk_rm_integration_program_history_integrationProgramId` FOREIGN KEY (`INTEGRATION_PROGRAM_ID`) REFERENCES `rm_integration_program` (`INTEGRATION_PROGRAM_ID`),
  CONSTRAINT `fk_rm_integration_program_history_integrationId` FOREIGN KEY (`INTEGRATION_ID`) REFERENCES `ap_integration` (`INTEGRATION_ID`),
  CONSTRAINT `fk_rm_integration_program_history_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`),
  CONSTRAINT `fk_rm_integration_program_history_versionStatusId` FOREIGN KEY (`VERSION_STATUS_ID`) REFERENCES `ap_version_status` (`VERSION_STATUS_ID`),
  CONSTRAINT `fk_rm_integration_program_history_versionTypeId` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;


DROP TRIGGER IF EXISTS `fasp`.`rm_integration_program_AFTER_INSERT`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` TRIGGER `rm_integration_program_AFTER_INSERT` AFTER INSERT ON `rm_integration_program` FOR EACH ROW BEGIN
INSERT INTO rm_integration_program_history (
		INTEGRATION_PROGRAM_HISTORY_ID, INTEGRATION_PROGRAM_ID, INTEGRATION_ID, PROGRAM_ID, VERSION_TYPE_ID, 
		VERSION_STATUS_ID, `ACTIVE`, LAST_MODIFIED_BY, LAST_MODIFIED_DATE
	)
    VALUES (
		null, new.INTEGRATION_PROGRAM_ID, new.INTEGRATION_ID, new.PROGRAM_ID, new.VERSION_TYPE_ID, 
        new.VERSION_STATUS_ID, new.`ACTIVE`, new.LAST_MODIFIED_BY, new.LAST_MODIFIED_DATE
	);
END$$
DELIMITER ;


DROP TRIGGER IF EXISTS `fasp`.`rm_integration_program_AFTER_UPDATE`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` TRIGGER `rm_integration_program_AFTER_UPDATE` AFTER UPDATE ON `rm_integration_program` FOR EACH ROW BEGIN
INSERT INTO rm_integration_program_history (
		INTEGRATION_PROGRAM_HISTORY_ID, INTEGRATION_PROGRAM_ID, INTEGRATION_ID, PROGRAM_ID, VERSION_TYPE_ID, 
		VERSION_STATUS_ID, `ACTIVE`, LAST_MODIFIED_BY, LAST_MODIFIED_DATE
	)
    VALUES (
		null, new.INTEGRATION_PROGRAM_ID, new.INTEGRATION_ID, new.PROGRAM_ID, new.VERSION_TYPE_ID, 
        new.VERSION_STATUS_ID, new.`ACTIVE`, new.LAST_MODIFIED_BY, new.LAST_MODIFIED_DATE
	);
END$$
DELIMITER ;


ALTER TABLE `fasp`.`rm_integration_program_completed` 
CHANGE COLUMN `COMPLETED_DATE` `COMPLETED_DATE` DATETIME NULL ;

ALTER TABLE `fasp`.`rm_integration_program_completed` 
ADD COLUMN `INTEGRATION_PROGRAM_ID` INT NULL AFTER `INTEGRATION_ID`;

ALTER TABLE `fasp`.`rm_integration_program_completed` 
ADD COLUMN `PROGRAM_ID` INT UNSIGNED NULL AFTER `INTEGRATION_PROGRAM_ID`,
CHANGE COLUMN `INTEGRATION_PROGRAM_ID` `INTEGRATION_PROGRAM_ID` INT UNSIGNED NULL DEFAULT NULL ;

DROP TRIGGER IF EXISTS `fasp`.`rm_program_version_trans_AFTER_INSERT`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` TRIGGER `rm_program_version_trans_AFTER_INSERT` AFTER INSERT ON `rm_program_version_trans` FOR EACH ROW BEGIN
	INSERT INTO rm_integration_program_completed 
	SELECT 
		new.PROGRAM_VERSION_TRANS_ID, ip.INTEGRATION_ID, ip.INTEGRATION_PROGRAM_ID, pv.PROGRAM_ID, null
	FROM rm_program_version pv 
	LEFT JOIN rm_integration_program ip ON pv.PROGRAM_ID=ip.PROGRAM_ID AND NEW.VERSION_TYPE_ID=ip.VERSION_TYPE_ID AND NEW.VERSION_STATUS_ID=ip.VERSION_STATUS_ID AND ip.ACTIVE
	WHERE new.PROGRAM_VERSION_ID=pv.PROGRAM_VERSION_ID AND ip.INTEGRATION_PROGRAM_ID IS NOT NULL;
END$$
DELIMITER ;


