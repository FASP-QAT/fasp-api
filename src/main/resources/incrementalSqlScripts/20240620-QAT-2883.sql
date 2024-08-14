CREATE TABLE `rm_funding_source_type` (
  `FUNDING_SOURCE_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_ID` int unsigned NOT NULL,
  `FUNDING_SOURCE_TYPE_CODE` varchar(10) NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`FUNDING_SOURCE_TYPE_ID`),
  UNIQUE KEY `unq_rm_funding_source_type_codeRealm` (`REALM_ID`,`FUNDING_SOURCE_TYPE_CODE`),
  KEY `fk_rm_funding_source_type_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_funding_source_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_funding_source_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_funding_source_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_funding_source_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


INSERT INTO `fasp`.`ap_label_source` (`SOURCE_DESC`, `SOURCE_TEXT`) VALUES ('rm_funding_source_type', 'Funding Source Type');

insert into ap_label values(null,"Default Funding Source Type",null, null, null,1,now(),1,now(),56);
select max(l.LABEL_ID) into @max1 from ap_label l ;
INSERT INTO rm_funding_source_type VALUES (null, 1, 'DEF', @max1, 1, 9, now(), 9, now());

ALTER TABLE `fasp`.`rm_funding_source` 
ADD COLUMN `FUNDING_SOURCE_TYPE_ID` INT UNSIGNED NOT NULL AFTER `ALLOWED_IN_BUDGET`,
ADD INDEX `fk_rm_funding_source_fundingSourceTypeId_idx` (`FUNDING_SOURCE_TYPE_ID` ASC) VISIBLE;
;
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1;
ALTER TABLE `fasp`.`rm_funding_source` 
ADD CONSTRAINT `fk_rm_funding_source_fundingSourceTypeId`
  FOREIGN KEY (`FUNDING_SOURCE_TYPE_ID`)
  REFERENCES `fasp`.`rm_funding_source_type` (`FUNDING_SOURCE_TYPE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_funding_source` AS
    SELECT 
        `fs`.`FUNDING_SOURCE_ID` AS `FUNDING_SOURCE_ID`,
        `fs`.`FUNDING_SOURCE_CODE` AS `FUNDING_SOURCE_CODE`,
        `fs`.`FUNDING_SOURCE_TYPE_ID` AS `FUNDING_SOURCE_TYPE_ID`,
        `fs`.`REALM_ID` AS `REALM_ID`,
        `fs`.`LABEL_ID` AS `LABEL_ID`,
        `fs`.`ACTIVE` AS `ACTIVE`,
        `fs`.`ALLOWED_IN_BUDGET` AS `ALLOWED_IN_BUDGET`,
        `fs`.`CREATED_BY` AS `CREATED_BY`,
        `fs`.`CREATED_DATE` AS `CREATED_DATE`,
        `fs`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `fs`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `fsl`.`LABEL_EN` AS `LABEL_EN`,
        `fsl`.`LABEL_FR` AS `LABEL_FR`,
        `fsl`.`LABEL_SP` AS `LABEL_SP`,
        `fsl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_funding_source` `fs`
        LEFT JOIN `ap_label` `fsl` ON ((`fs`.`LABEL_ID` = `fsl`.`LABEL_ID`)));

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_funding_source_type` AS
    SELECT 
        `pat`.`FUNDING_SOURCE_TYPE_ID` AS `FUNDING_SOURCE_TYPE_ID`,
        `pat`.`REALM_ID` AS `REALM_ID`,
        `pat`.`FUNDING_SOURCE_TYPE_CODE` AS `FUNDING_SOURCE_TYPE_CODE`,
        `pat`.`LABEL_ID` AS `LABEL_ID`,
        `pat`.`ACTIVE` AS `ACTIVE`,
        `pat`.`CREATED_BY` AS `CREATED_BY`,
        `pat`.`CREATED_DATE` AS `CREATED_DATE`,
        `pat`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `pat`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_funding_source_type` `pat`
        LEFT JOIN `ap_label` `l` ON ((`pat`.`LABEL_ID` = `l`.`LABEL_ID`)));
