/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 13-Jul-2021
 */

CREATE TABLE `rm_organisation_type` (
  `ORGANISATION_TYPE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation type',
  `REALM_ID` int(10) unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm this Organisation type belongs to',
  `LABEL_ID` int(10) unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Organisation type is Active. False indicates this Organisation type has been Deactivated',
  `CREATED_BY` int(10) unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '3 ',
  PRIMARY KEY (`ORGANISATION_TYPE_ID`),
  KEY `FK_rm_organisation_type_label` (`LABEL_ID`),
  KEY `FK_rm_organisation_type_realm` (`REALM_ID`),
  CONSTRAINT `FK_rm_organisation_type_label` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`),
  CONSTRAINT `FK_rm_organisation_type_realm` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table used to define the organization types for each Realm\nNote: An Organisation type can only be created and administered by a Realm Admin'


-- ALTER TABLE `fasp`.`rm_organisation_type` ADD CONSTRAINT `FK_rm_organisation_type_label` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`);

-- ALTER TABLE `fasp`.`rm_organisation_type` ADD CONSTRAINT `FK_rm_organisation_type_realm` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`);

SET FOREIGN_KEY_CHECKS = 0;

DELIMITER $$

USE `fasp`$$

DROP VIEW IF EXISTS `vw_organisation_type`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`faspUser`@`%` SQL SECURITY DEFINER VIEW `vw_organisation_type` AS 
SELECT
  `ot`.`ORGANISATION_TYPE_ID`    AS `ORGANISATION_TYPE_ID`,
  `ot`.`REALM_ID`           AS `REALM_ID`,
  `ot`.`LABEL_ID`           AS `LABEL_ID`,
  `ot`.`ACTIVE`             AS `ACTIVE`,
  `ot`.`CREATED_BY`         AS `CREATED_BY`,
  `ot`.`CREATED_DATE`       AS `CREATED_DATE`,
  `ot`.`LAST_MODIFIED_BY`   AS `LAST_MODIFIED_BY`,
  `ot`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
  `ol`.`LABEL_EN`          AS `LABEL_EN`,
  `ol`.`LABEL_FR`          AS `LABEL_FR`,
  `ol`.`LABEL_SP`          AS `LABEL_SP`,
  `ol`.`LABEL_PR`          AS `LABEL_PR`
FROM (`rm_organisation_type` `ot`
   LEFT JOIN `ap_label` `ol`
     ON ((`ot`.`LABEL_ID` = `ol`.`LABEL_ID`)))$$

DELIMITER ;


INSERT INTO `fasp`.`ap_label_source`(`SOURCE_ID`,`SOURCE_DESC`) VALUES ( NULL,'rm_organization_type'); 

ALTER TABLE `fasp`.`rm_organisation` ADD COLUMN `ORGANISATION_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'organisation type id mapped with organisation' AFTER `REALM_ID`, CHANGE `LABEL_ID` `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages'; 

ALTER TABLE `fasp`.`rm_organisation` ADD CONSTRAINT `FK_rm_organisation_type_id` FOREIGN KEY (`ORGANISATION_TYPE_ID`) REFERENCES `rm_organisation_type` (`ORGANISATION_TYPE_ID`);

DELIMITER $$

USE `fasp`$$

DROP VIEW IF EXISTS `vw_organisation`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`faspUser`@`%` SQL SECURITY DEFINER VIEW `vw_organisation` AS 
SELECT
  `o`.`ORGANISATION_ID`    AS `ORGANISATION_ID`,
  `o`.`REALM_ID`           AS `REALM_ID`,
  `o`.`LABEL_ID`           AS `LABEL_ID`,
  `ot`.`LABEL_ID`           AS `TYPE_LABEL_ID`,
  `o`.`ORGANISATION_TYPE_ID`  AS `ORGANISATION_TYPE_ID`,
  `o`.`ORGANISATION_CODE`  AS `ORGANISATION_CODE`,
  `o`.`ACTIVE`             AS `ACTIVE`,
  `o`.`CREATED_BY`         AS `CREATED_BY`,
  `o`.`CREATED_DATE`       AS `CREATED_DATE`,
  `o`.`LAST_MODIFIED_BY`   AS `LAST_MODIFIED_BY`,
  `o`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
  `ol`.`LABEL_EN`          AS `LABEL_EN`,
  `ol`.`LABEL_FR`          AS `LABEL_FR`,
  `ol`.`LABEL_SP`          AS `LABEL_SP`,
  `ol`.`LABEL_PR`          AS `LABEL_PR`,
  `otl`.`LABEL_EN`          AS `TYPE_LABEL_EN`,
  `otl`.`LABEL_FR`          AS `TYPE_LABEL_FR`,
  `otl`.`LABEL_SP`          AS `TYPE_LABEL_SP`,
  `otl`.`LABEL_PR`          AS `TYPE_LABEL_PR`
FROM (`rm_organisation` `o`
   LEFT JOIN `ap_label` `ol`
     ON ((`o`.`LABEL_ID` = `ol`.`LABEL_ID`))
     LEFT JOIN `rm_organisation_type` `ot`
     ON ((`o`.`ORGANISATION_TYPE_ID` = `ot`.`ORGANISATION_TYPE_ID`))
     LEFT JOIN `ap_label` `otl`
     ON ((`ot`.`LABEL_ID` = `otl`.`LABEL_ID`)))$$

DELIMITER ;

SET FOREIGN_KEY_CHECKS = 1;