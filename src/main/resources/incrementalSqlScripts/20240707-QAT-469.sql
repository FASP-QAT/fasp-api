DROP TABLE IF EXISTS `fasp`.`ap_security`;
CREATE TABLE `fasp`.`ap_security` (
  `SECURITY_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `METHOD` VARCHAR(45) NULL,
  `URL` VARCHAR(255) NOT NULL,
  `BF` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`SECURITY_ID`));

-- ALTER TABLE `fasp`.`temp_security` CHANGE COLUMN `METHOD` `METHOD` INT NOT NULL ;
ALTER TABLE `fasp`.`ap_security` CHANGE COLUMN `METHOD` `METHOD` INT NOT NULL ;

ALTER TABLE `fasp`.`ap_security` ADD UNIQUE INDEX `index2` (`METHOD` ASC, `URL` ASC, `BF` ASC) VISIBLE;
ALTER TABLE `fasp`.`ap_security` CHANGE COLUMN `BF` `BF` VARCHAR(50) NOT NULL ;



TRUNCATE TABLE ap_security;

INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/budget', 'ROLE_BF_ADD_BUDGET');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/budget', 'ROLE_BF_EDIT_BUDGET');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/budget', 'ROLE_BF_LIST_BUDGET');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/budget/*', 'ROLE_BF_LIST_BUDGET');
INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/budget/programIds', 'ROLE_BF_LIST_BUDGET');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/budget/realmId/*', 'ROLE_BF_LIST_BUDGET');

INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/country', 'ROLE_BF_LIST_COUNTRY');
INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/country', 'ROLE_BF_ADD_COUNTRY');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/country', 'ROLE_BF_EDIT_COUNTRY');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/country/*', 'ROLE_BF_LIST_COUNTRY');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/country/*', 'ROLE_BF_LIST_COUNTRY');

INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/currency', 'ROLE_BF_ADD_CURRENCY');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/currency', 'ROLE_BF_LIST_CURRENCY');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/currency', 'ROLE_BF_EDIT_CURRENCY');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/currency/*', 'ROLE_BF_LIST_CURRENCY');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/currency/*', 'ROLE_BF_LIST_CURRENCY');

INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/dataSource', 'ROLE_BF_ADD_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSource', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/dataSource', 'ROLE_BF_EDIT_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');

INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/dataSourceType', 'ROLE_BF_ADD_DATA_SOURCE_TYPE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSourceType', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/dataSourceType', 'ROLE_BF_EDIT_DATA_SOURCE_TYPE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');

INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/dimension', 'ROLE_BF_ADD_DIMENSION');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/dimension', 'ROLE_BF_EDIT_DIMENSION');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dimension', 'ROLE_BF_LIST_DIMENSION');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION');

INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');

INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/program/supplyPlan/list', 'ROLE_BF_LIST_PROGRAM');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/program/dataset/list', 'ROLE_BF_LIST_DATASET');
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/user/details', '');