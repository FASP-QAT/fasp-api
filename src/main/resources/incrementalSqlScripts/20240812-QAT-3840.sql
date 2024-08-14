CREATE TABLE `rm_program_funding_source` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `FUNDING_SOURCE_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`FUNDING_SOURCE_ID`),
  KEY `fk_rm_program_funding_source_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_program_funding_source_fundingSourceId_idx` (`FUNDING_SOURCE_ID`),
  KEY `fk_rm_program_funding_source_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_program_funding_source_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_funding_source_fundingSourceId` FOREIGN KEY (`FUNDING_SOURCE_ID`) REFERENCES `rm_funding_source` (`FUNDING_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_funding_source_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO rm_program_funding_source SELECT p.PROGRAM_ID, fs.FUNDING_SOURCE_ID, 1, now() FROM vw_program p LEFT JOIN rm_funding_source fs ON fs.ACTIVE WHERE p.ACTIVE;

update rm_funding_source fs SET fs.LAST_MODIFIED_DATE=now();