SET @dt='2021-09-01 00:00:00';

ALTER TABLE `fasp`.`rm_tracer_category` ADD COLUMN `HEALTH_AREA_ID` INT(10) UNSIGNED NULL AFTER `LABEL_ID`,ADD INDEX `fk_rm_tracer_category_healthAreaId_idx` (`HEALTH_AREA_ID` ASC);
ALTER TABLE `fasp`.`rm_tracer_category` ADD CONSTRAINT `fk_rm_tracer_category_healthAreaId`  FOREIGN KEY (`HEALTH_AREA_ID`)  REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;

UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=1;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=2;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=1 WHERE TRACER_CATEGORY_ID=3;

UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=5;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=8 WHERE TRACER_CATEGORY_ID=6;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=7;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=8;

UPDATE rm_tracer_category SET HEALTH_AREA_ID=6 WHERE TRACER_CATEGORY_ID=10;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=11;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=12;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=13;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=5 WHERE TRACER_CATEGORY_ID=14;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=5 WHERE TRACER_CATEGORY_ID=15;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=10 WHERE TRACER_CATEGORY_ID=16;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=6 WHERE TRACER_CATEGORY_ID=17;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=1 WHERE TRACER_CATEGORY_ID=18;

UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=20;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=21;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=22;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=23;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=24;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=7 WHERE TRACER_CATEGORY_ID=25;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=4 WHERE TRACER_CATEGORY_ID=26;

UPDATE rm_tracer_category SET HEALTH_AREA_ID=3 WHERE TRACER_CATEGORY_ID=28;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=29;
UPDATE rm_tracer_category SET HEALTH_AREA_ID=2 WHERE TRACER_CATEGORY_ID=30;

INSERT INTO ap_label_source VALUES (38, 'ap_usage_type');
INSERT INTO ap_label_source VALUES (39, 'ap_node_type');
INSERT INTO ap_label_source VALUES (40, 'ap_usage_period');
INSERT INTO ap_label_source VALUES (41, 'ap_modeling_type');
INSERT INTO ap_label_source VALUES (42, 'ap_forecast_method_type');
INSERT INTO ap_label_source VALUES (43, 'rm_forecast_method');
INSERT INTO ap_label_source VALUES (44, 'rm_equivalency_unit');
INSERT INTO ap_label_source VALUES (45, 'rm_forecast_tree_template');
INSERT INTO ap_label_source VALUES (46, 'rm_forecast_tree_template_node');
INSERT INTO ap_label_source VALUES (47, 'rm_usage_template');
INSERT INTO ap_label_source VALUES (48, 'rm_forecast_tree');
INSERT INTO ap_label_source VALUES (49, 'rm_forecast_tree_node');
INSERT INTO ap_label_source VALUES (50, 'rm_scenario');

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tracer_category` AS
    SELECT 
        `tc`.`TRACER_CATEGORY_ID` AS `TRACER_CATEGORY_ID`,
        `tc`.`REALM_ID` AS `REALM_ID`,
        `tc`.`HEALTH_AREA_ID` AS `HEALTH_AREA_ID`,
        `tc`.`LABEL_ID` AS `LABEL_ID`,
        `tcl`.`LABEL_EN` AS `LABEL_EN`,
        `tcl`.`LABEL_FR` AS `LABEL_FR`,
        `tcl`.`LABEL_SP` AS `LABEL_SP`,
        `tcl`.`LABEL_PR` AS `LABEL_PR`,
        `tc`.`ACTIVE` AS `ACTIVE`,
        `tc`.`CREATED_BY` AS `CREATED_BY`,
        `tc`.`CREATED_DATE` AS `CREATED_DATE`,
        `tc`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`, 
        `tc`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`
    FROM
        (`rm_tracer_category` `tc`
        LEFT JOIN `ap_label` `tcl` ON ((`tc`.`LABEL_ID` = `tcl`.`LABEL_ID`)));


CREATE TABLE `fasp`.`ap_usage_type` (
  `USAGE_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usageType, 1-For Discrete, 2-For Continuous', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`USAGE_TYPE_ID`),
  UNIQUE INDEX `USAGE_TYPE_ID_UNIQUE` (`USAGE_TYPE_ID` ASC),
  INDEX `fk_ap_usage_type_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ap_usage_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_ap_usage_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_ap_usage_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_usage_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'Discrete', null, null, null, 1, @dt, 1, @dt, 38);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Continuous', null, null, null, 1, @dt, 1, @dt, 38);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_usage_type` AS
    SELECT 
        `ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`ap_usage_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

CREATE TABLE `fasp`.`ap_node_type` (
  `NODE_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique node type id, 1-Aggregation, 2-Number, 3-Percentage, 4-Forecasting Unit, 5-Planning Unit', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`NODE_TYPE_ID`),
  UNIQUE INDEX `NODE_TYPE_ID_UNIQUE` (`NODE_TYPE_ID` ASC),
  INDEX `fk_ap_node_type_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ap_node_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_ap_node_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_ap_node_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_node_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'Aggregation', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Number', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Percentage', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Forecasting Unit', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Planning Unit', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_node_type` AS
    SELECT 
        `ut`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`ap_node_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

CREATE TABLE `fasp`.`ap_usage_period` (
  `USAGE_PERIOD_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage set', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CONVERT_TO_MONTH` DECIMAL(13,8) NOT NULL COMMENT 'Multiply by this to convert the UsagePeriod to Months', 
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`USAGE_PERIOD_ID`),
  UNIQUE INDEX `USAGE_PERIOD_ID_UNIQUE` (`USAGE_PERIOD_ID` ASC),
  INDEX `fk_ap_usage_period_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ap_usage_period_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_ap_usage_period_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_ap_usage_period_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_usage_period_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'day(s)', null, null, null, 1, @dt, 1, @dt, 40);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_period VALUES (null, @labelId, 365/12, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'week(s)', null, null, null, 1, @dt, 1, @dt, 40);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_period VALUES (null, @labelId, 52/12, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'month(s)', null, null, null, 1, @dt, 1, @dt, 40);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_period VALUES (null, @labelId, 1, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'year(s)', null, null, null, 1, @dt, 1, @dt, 40);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_usage_period VALUES (null, @labelId, 1/12, 1, 1, @dt, 1, @dt);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_usage_period` AS
    SELECT 
        `ut`.`USAGE_PERIOD_ID` AS `USAGE_PERIOD_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
        `ut`.`CONVERT_TO_MONTH` AS `CONVERT_TO_MONTH`,
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
        (`ap_usage_period` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));


CREATE TABLE `fasp`.`ap_modeling_type` (
  `MODELING_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each modeling type 1.Target (#), 2. Linear (#), 3. Linear (%), 4. Exponential (%)', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`MODELING_TYPE_ID`),
  UNIQUE INDEX `MODELING_TYPE_ID_UNIQUE` (`MODELING_TYPE_ID` ASC),
  INDEX `fk_ap_modeling_type_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ap_modeling_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_ap_modeling_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_ap_modeling_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_modeling_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'Target (#)', null, null, null, 1, @dt, 1, @dt, 41);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_modeling_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Linear (#)', null, null, null, 1, @dt, 1, @dt, 41);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_modeling_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Linear (%)', null, null, null, 1, @dt, 1, @dt, 41);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_modeling_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Exponential (%)', null, null, null, 1, @dt, 1, @dt, 41);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_modeling_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_modeling_type` AS
    SELECT 
        `ut`.`MODELING_TYPE_ID` AS `MODELING_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`ap_modeling_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));


CREATE TABLE `fasp`.`ap_forecast_method_type` (
  `FORECAST_METHOD_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each foreacastMethodType 1-Tree, 2-Consumption', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`FORECAST_METHOD_TYPE_ID`),
  UNIQUE INDEX `FORECAST_METHOD_TYPE_ID_UNIQUE` (`FORECAST_METHOD_TYPE_ID` ASC),
  INDEX `fk_ap_forecast_method_type_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ap_forecast_method_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_ap_forecast_method_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_ap_forecast_method_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_forecast_method_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'Tree', null, null, null, 1, @dt, 1, @dt, 42);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_forecast_method_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Consumption', null, null, null, 1, @dt, 1, @dt, 42);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_forecast_method_type VALUES (null, @labelId, 1, 1, @dt, 1, @dt);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_method_type` AS
    SELECT 
        `ut`.`FORECAST_METHOD_TYPE_ID` AS `FORECAST_METHOD_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`ap_forecast_method_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));


CREATE TABLE `fasp`.`rm_forecast_method` (
  `FORECAST_METHOD_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usageType, 1-For Discrete, 2-For Continuous', 
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Realm that this record belongs to',
  `FORECAST_METHOD_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'ForecastMethodType for this ForecastMethod', 
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED	 NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`FORECAST_METHOD_ID`),
  UNIQUE INDEX `FORECAST_METHOD_ID_UNIQUE` (`FORECAST_METHOD_ID` ASC),
  INDEX `fk_rm_forecast_method_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_rm_forecast_method_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_rm_forecast_method_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_forecast_method_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_method_forecastMethodType`
    FOREIGN KEY (`FORECAST_METHOD_TYPE_ID`)
    REFERENCES `fasp`.`ap_forecast_method_type` (`FORECAST_METHOD_TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_method_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_method_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO ap_label VALUES (null, 'Morbidity', null, null, null, 1, @dt, 1, @dt, 43);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO rm_forecast_method VALUES (null, 1, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Demographic', null, null, null, 1, @dt, 1, @dt, 43);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO rm_forecast_method VALUES (null, 1, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Consumption', null, null, null, 1, @dt, 1, @dt, 43);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO rm_forecast_method VALUES (null, 1, 2, @labelId, 1, 1, @dt, 1, @dt);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_method` AS
    SELECT 
        `ut`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`FORECAST_METHOD_TYPE_ID` AS `FORECAST_METHOD_TYPE_ID`,	
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`rm_forecast_method` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));



CREATE TABLE `fasp`.`rm_equivalency_unit` (
  `EQUIVALENCY_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each EquivalencyUnit', 
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Realm that this record belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED	 NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_ID`),
  UNIQUE INDEX `EQUIVALENCY_UNIT_ID_UNIQUE` (`EQUIVALENCY_UNIT_ID` ASC),
  INDEX `fk_rm_equivalency_unit_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_rm_equivalency_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
  CONSTRAINT `fk_rm_equivalency_unit_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_unit_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_unit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_unit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_equivalency_unit` AS
    SELECT 
        `ut`.`EQUIVALENCY_UNIT_ID` AS `EQUIVALENCY_UNIT_ID`,
	`ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`rm_equivalency_unit` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

INSERT INTO ap_label VALUES (null, 'Patient Month of ARV', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Male Condom (Latex) Lubricated, 53 mm', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Artemether/Lumefantrine 20/120 mg Tablet', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Regular Malaria Treatment', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'VL/EID Tests', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'CYP', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Flavored Condoms', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);
INSERT INTO ap_label VALUES (null, 'Severe Malaria Treatment', null, null, null, 1, @dt, 1, @dt, 44);	SELECT last_insert_id() into @labelId;	INSERT INTO rm_equivalency_unit VALUES (null, 1, @labelId, 1, 1, @dt, 1, @dt);

ALTER TABLE `fasp`.`rm_program` 
ADD COLUMN `PROGRAM_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Indicates what type of Program this is. 1 - SupplyPlan 2 - Forecast' AFTER `LAST_MODIFIED_DATE`;

UPDATE rm_program p SET p.`PROGRAM_TYPE_ID`=1;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE p.`PROGRAM_TYPE_ID`=1
    GROUP BY `p`.`PROGRAM_ID`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_dataset` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 2)
    GROUP BY `p`.`PROGRAM_ID`;



CREATE TABLE `fasp`.`rm_equivalency_unit_mapping` (
  `EQUIVALENCY_UNIT_MAPPING_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `EQUIVALENCY_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `CONVERT_TO_EU` DECIMAL(18,4) UNSIGNED NOT NULL,
  `NOTES` TEXT NULL,
  `REALM_ID` INT(10) UNSIGNED NOT NULL,
  `PROGRAM_ID` INT(10) UNSIGNED NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_MAPPING_ID`),
  INDEX `fk_rm_equivalency_mapping_equivalencyUnitId_idx` (`EQUIVALENCY_UNIT_ID` ASC),
  INDEX `fk_rm_equivalency_mapping_forecastingUnitId_idx` (`FORECASTING_UNIT_ID` ASC),
  INDEX `fk_rm_equivalency_mapping_realmId_idx` (`REALM_ID` ASC),
  INDEX `fk_rm_equivalency_mapping_programId_idx` (`PROGRAM_ID` ASC),
  INDEX `fk_rm_equivalency_mapping_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_rm_equivalency_mapping_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
  CONSTRAINT `fk_rm_equivalency_mapping_equivalencyUnitId`
    FOREIGN KEY (`EQUIVALENCY_UNIT_ID`)
    REFERENCES `fasp`.`rm_equivalency_unit` (`EQUIVALENCY_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_mapping_forecastingUnitId`
    FOREIGN KEY (`FORECASTING_UNIT_ID`)
    REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_mapping_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_mapping_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_mapping_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_mapping_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO rm_equivalency_unit_mapping VALUES (null, 1, 204, 30., '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 1, 2665, 30., '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 2, 915, 1, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 2, 983, 1, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 2, 928, 1, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 4, 164, 0.1667, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 4, 164, 0.0417, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 4, 164, 1, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 5, 455, 0.0104, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 6, 928, 120, '', 1, null, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 2, 928, 1, '', 1, 2030, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 2, 227, 1, '', 1, 2030, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 7, 1813, 1, '', 1, 2030, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 7, 1814, 1, '', 1, 2030, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 6, 222, 1, '', 1, 2030, 1, 1, @dt, 1, @dt);
INSERT INTO rm_equivalency_unit_mapping VALUES (null, 8, 1708, 6, '', 1, 2030, 1, 1, @dt, 1, @dt);

ALTER TABLE `fasp`.`rm_program` 
CHANGE COLUMN `AIR_FREIGHT_PERC` `AIR_FREIGHT_PERC` DECIMAL(5,2) UNSIGNED NULL COMMENT 'Percentage of Order Qty when Mode = Air' ,
CHANGE COLUMN `SEA_FREIGHT_PERC` `SEA_FREIGHT_PERC` DECIMAL(5,2) UNSIGNED NULL COMMENT 'Percentage of Order Qty when Mode = Sea' ,
CHANGE COLUMN `PLANNED_TO_SUBMITTED_LEAD_TIME` `PLANNED_TO_SUBMITTED_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL COMMENT 'No of days for an Order to move from Planed to Draft status' ,
CHANGE COLUMN `SUBMITTED_TO_APPROVED_LEAD_TIME` `SUBMITTED_TO_APPROVED_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL COMMENT 'No of days for an Order to move from Submitted to Approved status, this will be used only in the case the Procurement Agent is TBD' ,
CHANGE COLUMN `APPROVED_TO_SHIPPED_LEAD_TIME` `APPROVED_TO_SHIPPED_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL ,
CHANGE COLUMN `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL COMMENT 'No of days for an Order to move from Delivered to Received status' ,
CHANGE COLUMN `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL ,
CHANGE COLUMN `ARRIVED_TO_DELIVERED_LEAD_TIME` `ARRIVED_TO_DELIVERED_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL ;

ALTER TABLE `fasp`.`rm_program_version` 
ADD COLUMN `FORECAST_START_DATE` DATETIME NULL AFTER `SENT_TO_ARTMIS`,
ADD COLUMN `FORECAST_STOP_DATE` DATETIME NULL AFTER `FORECAST_START_DATE`;

ALTER TABLE `fasp`.`us_user` 
ADD COLUMN `DEFAULT_MODULE_ID` INT(10) UNSIGNED NOT NULL AFTER `JIRA_ACCOUNT_ID`;

insert into ap_static_label values (null, 'static.message.user.moduleChangeError', 1);
SELECT last_insert_id() into @id;
SELECT @id;
INSERT INTO ap_static_label_languages values (null, @id, 1, 'An error occurred while setting the default Module for user');


CREATE TABLE `fasp`.`rm_usage_template` (
  `USAGE_TEMPLATE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key of Realm Id that the Program belongs to',
  `PROGRAM_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key to indicate which Program Id',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate forecasting id',
  `LAG_IN_MONTHS` INT(10) UNSIGNED NOT NULL COMMENT '# of months to wait before using',
  `USAGE_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT '1 for Discrete, 2 for Continuous',
  `NO_OF_PATIENTS` INT(10) UNSIGNED NOT NULL COMMENT '# of Patients this usage will be used for',
  `NO_OF_FORECASTING_UNITS` INT(10) UNSIGNED NOT NULL COMMENT '# of Forecasting Units ',
  `ONE_TIME_USAGE` TINYINT(1) UNSIGNED NOT NULL,
  `USAGE_FREQUENCY_USAGE_PERIOD_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',
  `USAGE_FREQUENCY_COUNT` DECIMAL(16,4) UNSIGNED NULL COMMENT '# of UsagePeriod that this is given over',
  `REPEAT_USAGE_PERIOD_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',
  `REPEAT_COUNT` DECIMAL(16,4) UNSIGNED NULL COMMENT '# of times it is repeated for the Discrete type',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL ,
  `CREATED_DATE` DATETIME NOT NULL ,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL ,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL ,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`USAGE_TEMPLATE_ID`),
INDEX `fk_rm_usage_template_realmId_idx` (`REALM_ID` ASC),
INDEX `fk_rm_usage_template_programId_idx` (`PROGRAM_ID` ASC),
INDEX `fk_rm_usage_template_labelId_idx` (`LABEL_ID` ASC),
INDEX `fk_rm_usage_template_forecastingUnitId_idx` (`FORECASTING_UNIT_ID` ASC),
INDEX `fk_rm_usage_template_usageTypeId_idx` (`USAGE_TYPE_ID` ASC),
INDEX `fk_rm_usage_template_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID` ASC),
INDEX `fk_rm_usage_template_createdBy_idx` (`CREATED_BY` ASC),
INDEX `fk_rm_usage_template_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
CONSTRAINT `fk_rm_usage_template_realmId`
  FOREIGN KEY (`REALM_ID`)
  REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_programId`
  FOREIGN KEY (`PROGRAM_ID`)
  REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_labelId`
  FOREIGN KEY (`LABEL_ID`)
  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_forecastingUnitId`
  FOREIGN KEY (`FORECASTING_UNIT_ID`)
  REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_usageTypeId`
  FOREIGN KEY (`USAGE_TYPE_ID`)
  REFERENCES `fasp`.`ap_usage_type` (`USAGE_TYPE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_usageFrequencyUsagePeriodId`
  FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`)
  REFERENCES `fasp`.`ap_usage_period` (`USAGE_PERIOD_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_repeatUsagePeriodId`
  FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`)
  REFERENCES `fasp`.`ap_usage_period` (`USAGE_PERIOD_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
CONSTRAINT `fk_rm_usage_template_lastModifiedBy`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);

-- Shubham script start date-16th sep

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastProgram.forecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Base de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conjunto de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conjunto de dados');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMethod.forecastMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Method');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Méthode de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Método de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Método de previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.usagePeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage Period');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Période dutilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Período de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Período de Uso');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingType.modelingType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de modélisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de modelado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de Modelagem');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.healthareas','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Technical Areas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Domaines techniques');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Areas tecnicas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Áreas Técnicas');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.conversionFactor','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor To Month');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion en mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión al mes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão para mês');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.equivalancyUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Equivalency Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité d équivalence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Equivalência');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastProgram.MaxvalueofStockoutdays','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Value Of Stock Out Days');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur maximale des jours de rupture de stock');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor máximo de los días agotados');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor máximo de dias de falta de estoque');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mapForecastingUnitToEquivalancyUnit.conversionToFu','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion To FU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion en UF');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión a FU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão para FU');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.dataSettext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrer l ensemble de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingresar Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrar Program');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.datasetDisplayName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Display Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d affichage du programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre de visualización del programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de exibição do programa');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.useRegions','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use Regions?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utiliser les régions ?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Usar regiones?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usar regiões?');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMethod.methodology','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Method Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de méthode de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de método de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de método de previsão');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMethod.historicalData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMethod.tree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Arbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Árvore');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.validRegionstext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select regions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez les régions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar regiones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione as regiões');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.conversionFactorTest','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 5 digit number and 4 digits after decimal are allowed.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 5 chiffres et 4 chiffres après la virgule sont autorisés.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 5 dígitos y 4 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'São permitidos no máximo 5 dígitos e 4 dígitos após o decimal.');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.addUpdateMessage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data added/updated successfully.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données ajoutées/mises à jour avec succès.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos agregados / actualizados con éxito.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados adicionados / atualizados com sucesso.');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.forecastDatasetModule','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Program Module');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Module de jeu de données de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Módulo de conjunto de datos de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Módulo de conjunto de dados de previsão');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.supplyPlanning','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Planning');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Planification de l approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La planificación del suministro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planejamento de Abastecimento');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.forecastingModule','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FORECASTING MODULE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MODULE DE PRÉVISION');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MÓDULO DE PRONÓSTICO');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MÓDULO DE PREVISÃO');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.supplyPlanningModule','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'SUPPLY PLANNING MODULE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MODULE DE PLANIFICATION DE L APPROVISIONNEMENT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MÓDULO DE PLANIFICACIÓN DE SUMINISTROS');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MÓDULO DE PLANEJAMENTO DE FORNECIMENTO');


ALTER TABLE `fasp`.`us_user` CHANGE `DEFAULT_MODULE_ID` `DEFAULT_MODULE_ID` INT(10) UNSIGNED DEFAULT '2' NOT NULL; 
UPDATE `fasp`.`us_user` SET `DEFAULT_MODULE_ID`='2'; 


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecast Program Admin',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'8');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_role`(`ROLE_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_DATASET_ADMIN',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecast Program User',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'8');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_role`(`ROLE_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_DATASET_USER',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Usage Period',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_USAGE_PERIOD',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Usage Period',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_USAGE_PERIOD',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Usage Period',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_USAGE_PERIOD',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Modeling Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_MODELING_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Modeling Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_MODELING_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Modeling Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_MODELING_TYPE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Forecast Method',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_FORECAST_METHOD',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Forecast Method',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_FORECAST_METHOD',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Forecast Method',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_FORECAST_METHOD',@MAX,'1',NOW(),'1',NOW());


-- INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_USAGE_PERIOD','1',NOW(),'1',NOW());
-- INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());



-- INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_APPLICATION_DASHBOARD','1',NOW(),'1',NOW()); 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.customWarningMessage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Custom warning text to warn users about changing masters');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Texte d avertissement personnalisé pour avertir les utilisateurs de la modification des maîtres');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Texto de advertencia personalizado para advertir a los usuarios sobre el cambio de maestros');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Texto de aviso personalizado para alertar os usuários sobre a alteração dos mestres');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.customWarningEquivalencyUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this page to define equivalency units, which are used on the Monthly Forecast screen. If you don‘t see an equivalency unit you need, please click on the "Manage Equivalency Unit" button.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette page pour définir les unités d équivalence, qui sont utilisées sur l écran Prévision mensuelle. Si vous ne voyez pas d unité d équivalence dont vous avez besoin, veuillez cliquer sur le bouton "Gérer l unité d équivalence".');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta página para definir las unidades de equivalencia, que se utilizan en la pantalla Pronóstico mensual. Si no ve una unidad de equivalencia que necesita, haga clic en el botón "Administrar unidad de equivalencia".');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta página para definir unidades de equivalência, que são usadas na tela de Previsão Mensal. Se você não encontrar uma unidade de equivalência de que precisa, clique no botão "Gerenciar Unidade de Equivalência".');
-- Shubham script end date-16th sep

-- Anchal's script for access control screen
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.programAndProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.pr','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'PR - ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'RP - ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'PR - ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'PR - ');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.fr','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FR - ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'FP - ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'FR - ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'FR - ');-- pr

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`, 
        `p`.`PROGRAM_TYPE_ID`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 1)
    GROUP BY `p`.`PROGRAM_ID`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_dataset` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 2)
    GROUP BY `p`.`PROGRAM_ID`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_dimension` AS
    SELECT 
        `d`.`DIMENSION_ID` AS `DIMENSION_ID`,
        `d`.`LABEL_ID` AS `LABEL_ID`,
        `d`.`ACTIVE` AS `ACTIVE`,
        `d`.`CREATED_BY` AS `CREATED_BY`,
        `d`.`CREATED_DATE` AS `CREATED_DATE`,
        `d`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `d`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`ap_dimension` `d`
        LEFT JOIN `ap_label` `l` ON ((`d`.`LABEL_ID` = `l`.`LABEL_ID`)));

insert into ap_label values (null, 'Tree unit', null, null, null, 1, now(), 1, now(), 4);
SELECT last_insert_id() into @labelId;
insert into ap_dimension values (null, @labelId, 1, 1, now(), 1, now());
SELECT last_insert_id() into @dimensionId;

INSERT INTO ap_label values (null, 'Patients', null, null, null, 1, @dt, 1, @dt, 17);
SELECT last_insert_id() into @labelId;
INSERT INTO ap_unit values (null, @dimensionId, @labelId, 'Patients', 1, 1, @dt, 1, @dt);
INSERT INTO ap_label values (null, 'Clients', null, null, null, 1, @dt, 1, @dt, 17);
SELECT last_insert_id() into @labelId;
INSERT INTO ap_unit values (null, @dimensionId, @labelId, 'Clients', 1, 1, @dt, 1, @dt);
INSERT INTO ap_label values (null, 'Customers', null, null, null, 1, @dt, 1, @dt, 17);
SELECT last_insert_id() into @labelId;
INSERT INTO ap_unit values (null, @dimensionId, @labelId, 'Customers', 1, 1, @dt, 1, @dt);
INSERT INTO ap_label values (null, 'People', null, null, null, 1, @dt, 1, @dt, 17);
SELECT last_insert_id() into @labelId;
INSERT INTO ap_unit values (null, @dimensionId, @labelId, 'People', 1, 1, @dt, 1, @dt);



SET FOREIGN_KEY_CHECKS=0;
-- MySql	
DROP TABLE IF EXISTS rm_tree_template;
Create table `rm_tree_template` (`TREE_TEMPLATE_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique forecast tree template id',	
`REALM_ID` int(10) UNSIGNED NOT NULL COMMENT 'Foreign key that maps to the Realm this Template is for',	
`LABEL_ID` int(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',	
`FORECAST_METHOD_ID` int(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Forecast method is used for this Tree',	
PRIMARY KEY(`TREE_TEMPLATE_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node;
Create table `rm_tree_template_node` (`NODE_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique node id for tree',	
`TREE_TEMPLATE_ID` int(10) UNSIGNED NOT NULL COMMENT 'TreeTemplateId that this Node belongs to',	
`PARENT_NODE_ID` int(10) UNSIGNED NULL COMMENT 'Node Id of the parent. Null if this is the root.',	
`SORT_ORDER` varchar(300) NOT NULL COMMENT 'Sort order of the Node in the tree',	
`LEVEL_NO` int(10) UNSIGNED NOT NULL COMMENT 'Level that this node appears on ',	
`NODE_TYPE_ID` int(10) UNSIGNED NOT NULL COMMENT 'What type of Node is this',	
`UNIT_ID` int(10) UNSIGNED NULL COMMENT 'Indicates the Unit for this Node',	
`MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS` tinyint(1) UNSIGNED NOT NULL COMMENT 'If true any manual changes will cascade into future months. If false the manual change is only for that month and does not cascase into future months',	
`LABEL_ID` int(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',	
PRIMARY KEY(`NODE_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node_data;
Create table `rm_tree_template_node_data` (`NODE_DATA_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique node data id for the node and scenario',	
`NODE_ID` int(10) UNSIGNED NOT NULL COMMENT 'node id ',	
`MONTH` date NOT NULL COMMENT 'Indicates the month that this Data is for, Defaults to the StartDate of the Forecast Program. Cannot be later than start of Forecast Program',	
`DATA_VALUE` decimal(14,4) NULL COMMENT 'Based on the NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent',	
`NODE_DATA_FU_ID` int(10) UNSIGNED NULL COMMENT '',	
`NODE_DATA_PU_ID` int(10) UNSIGNED NULL COMMENT '',	
`NOTES` text NULL COMMENT 'Notes that describe the Node',	
PRIMARY KEY(`NODE_DATA_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node_data_fu;
Create table `rm_tree_template_node_data_fu` (`NODE_DATA_FU_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',	
`FORECASTING_UNIT_ID` int(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate forecasting id',	
`LAG_IN_MONTHS` int(10) UNSIGNED NOT NULL COMMENT '# of months to wait before using',	
`USAGE_TYPE_ID` int(10) UNSIGNED NOT NULL COMMENT '1 for Discrete, 2 for Continuous',	
`NO_OF_PERSONS` int(10) UNSIGNED NOT NULL COMMENT '# of Patients this usage will be used for',	
`FORECASTING_UNITS_PER_PERSON` decimal(16,4) UNSIGNED NOT NULL COMMENT '# of Forecasting Units ',	
`ONE_TIME_USAGE` tinyint(1) UNSIGNED NOT NULL COMMENT '',	
`USAGE_FREQUENCY` decimal(16,4) UNSIGNED NULL COMMENT '# of times the Forecasting Unit is given per Usage',	
`USAGE_FREQUENCY_USAGE_PERIOD_ID` int(10) UNSIGNED NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',	
`REPEAT_COUNT` decimal(16,4) UNSIGNED NULL COMMENT '# of times it is repeated for the Discrete type',	
`REPEAT_USAGE_PERIOD_ID` int(10) UNSIGNED NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',	
PRIMARY KEY(`NODE_DATA_FU_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node_data_pu;
Create table `rm_tree_template_node_data_pu` (`NODE_DATA_PU_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each PU Conversion',	
`PLANNING_UNIT_ID` int(10) UNSIGNED NOT NULL COMMENT 'What Palnning Unit does this Node convert to',	
`SHARE_PLANNING_UNIT` tinyint(1) UNSIGNED NOT NULL COMMENT 'If 1 that means this Planning Unit is to be shared with others and therefore maintain the decimal, if it is not shared you need to round to 1',	
`REFILL_MONTHS` int(10) UNSIGNED NOT NULL COMMENT '# of moths over which refulls are taken',	
PRIMARY KEY(`NODE_DATA_PU_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node_data_modeling;
Create table `rm_tree_template_node_data_modeling` (`NODE_DATA_MODELING_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for NodeScaleUp',	
`NODE_DATA_ID` int(10) UNSIGNED NOT NULL COMMENT 'Node that this ScaleUp referrs to',	
`START_DATE` date NOT NULL COMMENT 'Start date that the Scale up is applicable from. Startes from the Forecast Program Start',	
`STOP_DATE` date NOT NULL COMMENT 'Stop date that the Scale up is applicable from. Defaults to Forecast Program End but user can override',	
`MODELING_TYPE_ID` int(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate scale type id',	
`DATA_VALUE` decimal(14,2) NULL COMMENT 'Data value could be a number of a % based on the ScaleTypeId',	
`TRANSFER_NODE_ID` int(10) UNSIGNED NOT NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.',	
`NOTES` text NULL COMMENT 'Notes to desribe this scale up',	
PRIMARY KEY(`NODE_DATA_MODELING_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	
	
	
-- MySql	
DROP TABLE IF EXISTS rm_tree_template_node_data_override;
Create table `rm_tree_template_node_data_override` (`NODE_DATA_OVERRIDE_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Override Id',	
`NODE_DATA_ID` int(10) UNSIGNED NOT NULL COMMENT 'The Node Data Id that this Override is for',	
`MONTH` date NOT NULL COMMENT 'Month that this Override is for',	
`MANUAL_CHANGE` decimal(16,4) NOT NULL COMMENT 'The manual change value',	
`SEASONALITY_PERC` decimal(16,4) NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes',	
PRIMARY KEY(`NODE_DATA_OVERRIDE_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;	

SET FOREIGN_KEY_CHECKS=1;    
			
			
ALTER TABLE `rm_tree_template` ADD INDEX `fk_treeTemplate_realmId_idx` (`REALM_ID` ASC);	ALTER TABLE `rm_tree_template` ADD CONSTRAINT `fk_treeTemplate_realmId_idx`  FOREIGN KEY (`REALM_ID`)  REFERENCES `rm_realm` (`REALM_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template` ADD INDEX `fk_treeTemplate_labelId_idx` (`LABEL_ID` ASC);	ALTER TABLE `rm_tree_template` ADD CONSTRAINT `fk_treeTemplate_labelId_idx`  FOREIGN KEY (`LABEL_ID`)  REFERENCES `ap_label` (`LABEL_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template` ADD INDEX `fk_treeTemplate_forecastMethodId_idx` (`FORECAST_METHOD_ID` ASC);	ALTER TABLE `rm_tree_template` ADD CONSTRAINT `fk_treeTemplate_forecastMethodId_idx`  FOREIGN KEY (`FORECAST_METHOD_ID`)  REFERENCES `rm_forecast_method` (`FORECAST_METHOD_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `FORECAST_METHOD_ID`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template` ADD INDEX `fk_treeTemplate_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplate_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template` ADD CONSTRAINT `fk_treeTemplate_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplate_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_treeTemplateId_idx` (`TREE_TEMPLATE_ID` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_treeTemplateId_idx`  FOREIGN KEY (`TREE_TEMPLATE_ID`)  REFERENCES `rm_tree_template` (`TREE_TEMPLATE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_parentNodeId_idx` (`PARENT_NODE_ID` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_parentNodeId_idx`  FOREIGN KEY (`PARENT_NODE_ID`)  REFERENCES `rm_tree_template_node` (`NODE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			ALTER TABLE `rm_tree_template_node` ADD INDEX `idx_treeTemplateNode_sortOrder` (`SORT_ORDER` ASC);
			
ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_nodeTypeId_idx` (`NODE_TYPE_ID` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_nodeTypeId_idx`  FOREIGN KEY (`NODE_TYPE_ID`)  REFERENCES `ap_node_type` (`NODE_TYPE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_unitId_idx` (`UNIT_ID` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_unitId_idx`  FOREIGN KEY (`UNIT_ID`)  REFERENCES `ap_unit` (`UNIT_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_labelId_idx` (`LABEL_ID` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_labelId_idx`  FOREIGN KEY (`LABEL_ID`)  REFERENCES `ap_label` (`LABEL_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template_node` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `LABEL_ID`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node` ADD INDEX `fk_treeTemplateNode_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNode_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node` ADD CONSTRAINT `fk_treeTemplateNode_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNode_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node_data` ADD INDEX `fk_treeTemplateNodeData_nodeId_idx` (`NODE_ID` ASC);	ALTER TABLE `rm_tree_template_node_data` ADD CONSTRAINT `fk_treeTemplateNodeData_nodeId_idx`  FOREIGN KEY (`NODE_ID`)  REFERENCES `rm_tree_template_node` (`NODE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
			
ALTER TABLE `rm_tree_template_node_data` ADD INDEX `fk_treeTemplateNodeData_nodeDataFuId_idx` (`NODE_DATA_FU_ID` ASC);	ALTER TABLE `rm_tree_template_node_data` ADD CONSTRAINT `fk_treeTemplateNodeData_nodeDataFuId_idx`  FOREIGN KEY (`NODE_DATA_FU_ID`)  REFERENCES `rm_tree_template_node_data_fu` (`NODE_DATA_FU_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template_node_data` ADD INDEX `fk_treeTemplateNodeData_nodeDataPuId_idx` (`NODE_DATA_PU_ID` ASC);	ALTER TABLE `rm_tree_template_node_data` ADD CONSTRAINT `fk_treeTemplateNodeData_nodeDataPuId_idx`  FOREIGN KEY (`NODE_DATA_PU_ID`)  REFERENCES `rm_tree_template_node_data_pu` (`NODE_DATA_PU_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node_data` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `NOTES`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node_data` ADD INDEX `fk_treeTemplateNodeData_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNodeData_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node_data` ADD CONSTRAINT `fk_treeTemplateNodeData_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNodeData_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node_data_fu` ADD INDEX `fk_treeTemplateNodeDataFu_forecastingUnitId_idx` (`FORECASTING_UNIT_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_fu` ADD CONSTRAINT `fk_treeTemplateNodeDataFu_forecastingUnitId_idx`  FOREIGN KEY (`FORECASTING_UNIT_ID`)  REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node_data_fu` ADD INDEX `fk_treeTemplateNodeDataFu_usageTypeId_idx` (`USAGE_TYPE_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_fu` ADD CONSTRAINT `fk_treeTemplateNodeDataFu_usageTypeId_idx`  FOREIGN KEY (`USAGE_TYPE_ID`)  REFERENCES `ap_usage_type` (`USAGE_TYPE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
			
			
			
ALTER TABLE `rm_tree_template_node_data_fu` ADD INDEX `fk_treeTemplateNodeDataFu_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_fu` ADD CONSTRAINT `fk_treeTemplateNodeDataFu_usageFrequencyUsagePeriodId_idx`  FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`)  REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node_data_fu` ADD INDEX `fk_treeTemplateNodeDataFu_repeatUsagePeriodId_idx` (`REPEAT_USAGE_PERIOD_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_fu` ADD CONSTRAINT `fk_treeTemplateNodeDataFu_repeatUsagePeriodId_idx`  FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`)  REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
ALTER TABLE `rm_tree_template_node_data_fu` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `REPEAT_USAGE_PERIOD_ID`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node_data_fu` ADD INDEX `fk_treeTemplateNodeDataFu_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNodeDataFu_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node_data_fu` ADD CONSTRAINT `fk_treeTemplateNodeDataFu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNodeDataFu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node_data_pu` ADD INDEX `fk_treeTemplateNodeDataPu_planningUnitId_idx` (`PLANNING_UNIT_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_pu` ADD CONSTRAINT `fk_treeTemplateNodeDataPu_planningUnitId_idx`  FOREIGN KEY (`PLANNING_UNIT_ID`)  REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
			
ALTER TABLE `rm_tree_template_node_data_pu` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `REFILL_MONTHS`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node_data_pu` ADD INDEX `fk_treeTemplateNodeDataPu_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNodeDataPu_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node_data_pu` ADD CONSTRAINT `fk_treeTemplateNodeDataPu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNodeDataPu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node_data_modeling` ADD INDEX `fk_treeTemplateNodeDataModeling_nodeDataId_idx` (`NODE_DATA_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_modeling` ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_nodeDataId_idx`  FOREIGN KEY (`NODE_DATA_ID`)  REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
			
ALTER TABLE `rm_tree_template_node_data_modeling` ADD INDEX `fk_treeTemplateNodeDataModeling_modelingTypeId_idx` (`MODELING_TYPE_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_modeling` ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_modelingTypeId_idx`  FOREIGN KEY (`MODELING_TYPE_ID`)  REFERENCES `ap_modeling_type` (`MODELING_TYPE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node_data_modeling` ADD INDEX `fk_treeTemplateNodeDataModeling_transferNodeId_idx` (`TRANSFER_NODE_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_modeling` ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_transferNodeId_idx`  FOREIGN KEY (`TRANSFER_NODE_ID`)  REFERENCES `rm_tree_template_node` (`NODE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
ALTER TABLE `rm_tree_template_node_data_modeling` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `NOTES`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node_data_modeling` ADD INDEX `fk_treeTemplateNodeDataModeling_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNodeDataModeling_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node_data_modeling` ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	
			
			
			
			
ALTER TABLE `rm_tree_template_node_data_override` ADD INDEX `fk_treeTemplateNodeDataOverride_nodeDataId_idx` (`NODE_DATA_ID` ASC);	ALTER TABLE `rm_tree_template_node_data_override` ADD CONSTRAINT `fk_treeTemplateNodeDataOverride_nodeDataId_idx`  FOREIGN KEY (`NODE_DATA_ID`)  REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;		
			
			
			
ALTER TABLE `rm_tree_template_node_data_override` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `SEASONALITY_PERC`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_tree_template_node_data_override` ADD INDEX `fk_treeTemplateNodeDataOverride_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_treeTemplateNodeDataOverride_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);	ALTER TABLE `rm_tree_template_node_data_override` ADD CONSTRAINT `fk_treeTemplateNodeDataOverride_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, ADD CONSTRAINT `fk_treeTemplateNodeDataOverride_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;	SELECT u.UNIT_ID into @unitId FROM vw_unit u where u.UNIT_CODE='People';

SELECT u.UNIT_ID into @unitId FROM vw_unit u where u.UNIT_CODE='People';

INSERT INTO ap_label values (null, 'Demographic Condoms Template', null, null, null, 1, @dt, 1, @dt, 45);
SELECT last_insert_id() into @labelId;
INSERT INTO `fasp`.`rm_tree_template` (`TREE_TEMPLATE_ID`, `REALM_ID`, `LABEL_ID`, `FORECAST_METHOD_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (NULL, '1', @labelId, '1', '9', @dt, '9', @dt, '1');

INSERT INTO ap_label values (null, 'Demographic ARV Template', null, null, null, 1, @dt, 1, @dt, 45);
SELECT last_insert_id() into @labelId;
INSERT INTO `fasp`.`rm_tree_template` (`TREE_TEMPLATE_ID`, `REALM_ID`, `LABEL_ID`, `FORECAST_METHOD_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (NULL, '1', @labelId, '1', '9', @dt, '9', @dt, '1');

INSERT INTO ap_label values (null, 'Country population', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, null, "00", 1, 2, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 100829000, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Male population', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 1, "00.01", 2, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 59.7, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Female population', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 1, "00.02", 2, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 40.3, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Sexually active men', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 2, "00.01.01", 3, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 75.4, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Men who use Condoms', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 4, "00.01.01.01", 4, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 36.8, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'No logo Condoms', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 5, "00.01.01.01.01", 5, 4, null, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data_fu values (null, 915, 0, 2, 1, 120, 0, 1, 4, null, null, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeDataFuId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 70, @nodeDataFuId, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Strawberry Condoms', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 5, "00.01.01.01.02", 5, 4, null, 1, @labelId, 1, @dt, 1, @dt, 1);
insert into rm_tree_template_node_data_fu values (null, 911, 0, 2, 1, 120, 0, 1, 4, null, null, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeDataFuId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 30, @nodeDataFuId, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'No logo condom, pack of 1', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 6, "00.01.01.01.01.01", 6, 5, null, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_tree_template_node_data_pu values (null, 4148, 0, 2, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeDataPuId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 100, null, @nodeDataPuId, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Strawberry condom pack of 1', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 7, "00.01.01.01.02.01",6, 5, null, 1, @labelId, 1, @dt, 1, @dt, 1);
insert into rm_tree_template_node_data_pu values (null, 4159, 0, 2, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeDataPuId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 100, null, @nodeDataPuId, "", 1, @dt, 1, @dt, 1);

UPDATE `fasp`.`rm_tree_template_node_data` SET `NODE_ID`='7' WHERE `NODE_DATA_ID`='7';
UPDATE `fasp`.`rm_tree_template_node_data` SET `NODE_ID`='9' WHERE `NODE_DATA_ID`='9';


-- Anishaslabels script

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.forecasting','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FORECASTING');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'PRÉVISION');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'PRONOSTICO');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'PREVISÃO');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.module.supplyPlanningMod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'SUPPLY PLANNING');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'PLANIFICATION DE L APPROVISIONNEMENT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'LA PLANIFICACIÓN DEL SUMINISTRO');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'PLANEJAMENTO DE FORNECIMENTO');

INSERT INTO ap_label VALUES (null, 'Sayana Press', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 42, 0, 2, 1, 1, 0, 4, 4, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Depo Provera - STG', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 223, 0, 2, 1, 1, 0, 4, 4, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Implanon', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 229, 0, 2, 1, 1, 0, 4, 0.4, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Jadelle', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 230, 0, 2, 1, 1, 0, 4, 0.2, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Condom Estimate', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 228, 0, 2, 1, 10, 0, 3, 1, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Condom CYP', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 228, 0, 2, 1, 120, 0, 4, 1, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Microgynon', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 37, 0, 2, 1, 1, 0, 4, 13, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Adult ARV - TLD', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 4201, 1, 2, 1, 1, 0, 1, 1, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'AL 6x1', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 164, 0, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'X Vaccine STG', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 4311, 0, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Mask for clinic', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 523, 0, 1, 4, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Mask for surgery', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 523, 0, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'X Vaccine STG', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 4288, 0, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'X Vaccine STG', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 4288, 1, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Burn Cream (Severe)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 1853, 0, 1, 1, 0.5, 0, 1, 3, 1, 14, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Burn Cream (Moderate)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 1853, 0, 1, 1, 0.25, 0, 1, 1, 1, 7, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'HIV PrEP (phase 1 - 1 month)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 205, 0, 1, 1, 1, 0, 1, 1, 3, 1, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'HIV PrEP (phase 2 - 3 months)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 205, 2, 1, 1, 1, 0, 1, 1, 3, 3, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Eclampsia (loading MgS04)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 2336, 0, 1, 1, 2, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Eclampsia (loading Lidocaine)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 3077, 0, 1, 1, 0.2, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Eclampsia (maintenance MgSO4)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 2336, 0, 1, 1, 1, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Eclampsia (maintenance lidocaine)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 3077, 0, 1, 1, 0.2, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Cotton Gauze for X', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 2925, 0, 1, 1, 15, 1, null, null, null, null, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Pediatric SPAQ (malaria prophylaxis)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 2040, 0, 1, 1, 1, 0, 3, 1, 3, 4, 1, @dt, 1, @dt, 1);
INSERT INTO ap_label VALUES (null, 'Pediatric SPAQ (malaria prophylaxis)', null, null, null, 1, @dt, 1, @dt, 38);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO rm_usage_template VALUES (null, 1, null, @labelId, 2038, 0, 1, 1, 1, 0, 3, 1, 3, 4, 1, @dt, 1, @dt, 1);



USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_usage_template` AS
    SELECT 
        `ut`.`USAGE_TEMPLATE_ID` AS `USAGE_TEMPLATE_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ut`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,
        `ut`.`LAG_IN_MONTHS` AS `LAG_IN_MONTHS`,
        `ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,
        `ut`.`NO_OF_PATIENTS` AS `NO_OF_PATIENTS`,
        `ut`.`NO_OF_FORECASTING_UNITS` AS `NO_OF_FORECASTING_UNITS`,
        `ut`.`ONE_TIME_USAGE` AS `ONE_TIME_USAGE`,
        `ut`.`USAGE_FREQUENCY_USAGE_PERIOD_ID` AS `USAGE_FREQUENCY_USAGE_PERIOD_ID`,
        `ut`.`USAGE_FREQUENCY_COUNT` AS `USAGE_FREQUENCY_COUNT`,
        `ut`.`REPEAT_USAGE_PERIOD_ID` AS `REPEAT_USAGE_PERIOD_ID`,
        `ut`.`REPEAT_COUNT` AS `REPEAT_COUNT`,
        `ut`.`ACTIVE` AS `ACTIVE`,
        `ut`.`CREATED_DATE` AS `CREATED_DATE`,
        `ut`.`CREATED_BY` AS `CREATED_BY`,
        `ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `l`.`LABEL_ID` AS `LABEL_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_usage_template` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template` AS
    SELECT 
        `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tt`.`REALM_ID` AS `REALM_ID`,
        `tt`.`LABEL_ID` AS `LABEL_ID`,
        `tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `tt`.`CREATED_BY` AS `CREATED_BY`,
        `tt`.`CREATED_DATE` AS `CREATED_DATE`,
        `tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tt`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));

USE `fasp`;
CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template_node` AS
    SELECT 
        `ttn`.`NODE_ID` AS `NODE_ID`,
        `ttn`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `ttn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `ttn`.`SORT_ORDER` AS `SORT_ORDER`,
        `ttn`.`LEVEL_NO` AS `LEVEL_NO`,
        `ttn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ttn`.`UNIT_ID` AS `UNIT_ID`,
        `ttn`.`MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS` AS `MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS`,
        `ttn`.`LABEL_ID` AS `LABEL_ID`,
        `ttn`.`CREATED_BY` AS `CREATED_BY`,
        `ttn`.`CREATED_DATE` AS `CREATED_DATE`,
        `ttn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ttn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ttn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template_node` `ttn`
        LEFT JOIN `ap_label` `l` ON ((`ttn`.`LABEL_ID` = `l`.`LABEL_ID`)))
    ORDER BY `ttn`.`TREE_TEMPLATE_ID` , `ttn`.`SORT_ORDER`;

-- Tree labels
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.datasetmanagement','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Management');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gestion de programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Gestión de programas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gestão do Programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.listtree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'List Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Arborescence de la liste');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Árbol de lista');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Árvore de Lista');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.createTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create tree template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer un modèle d arbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear plantilla de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar modelo de árvore');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.scenarioName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Scenarios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Scénarios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Escenarios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cenários');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.region','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Region');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Région');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Región');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Região');


-- INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.treeName','1');
-- SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
-- INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree name');
-- INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom de larbre');
-- INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del árbol');
-- INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome da árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.createManualTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create Manual tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer un arbre manuel');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear árbol manual');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar árvore manual');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.createTreeFromTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Templates');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèles d arborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantillas de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelos de Árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.buildTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Build Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Construire un arbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Construir árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Construire un arbre');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.copyRow','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Copy Row');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Copier la ligne');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Copiar fila');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Copiar linha');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.loadDeleteDataSet','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Load or delete program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Charger ou supprimer un programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cargar o eliminar programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar ou deletar programa');


CREATE TABLE `rm_forecast_tree` (
  `TREE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique forecast tree template id',
  `PROGRAM_ID` int(10) unsigned NOT NULL COMMENT 'Foreign key that maps to the Program this Tree is for',
  `LABEL_ID` int(10) unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `FORECAST_METHOD_ID` int(10) unsigned NOT NULL COMMENT 'Foreign key to indicate which Forecast method is used for this Tree',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`TREE_ID`),
  KEY `fk_forecastTree_programId_idx` (`PROGRAM_ID`),
  KEY `fk_forecastTree_labelId_idx` (`LABEL_ID`),
  KEY `fk_forecastTree_forecastMethodId_idx` (`FORECAST_METHOD_ID`),
  KEY `fk_forecastTree_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTree_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTree_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTree_forecastMethodId_idx` FOREIGN KEY (`FORECAST_METHOD_ID`) REFERENCES `rm_forecast_method` (`FORECAST_METHOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTree_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTree_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTree_programId_idx` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

Create table `rm_scenario` (
`SCENARIO_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Scenario Id',	
`TREE_ID` int(10) UNSIGNED NOT NULL COMMENT 'Foreign key for the tree Id',	
`LABEL_ID` int(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',	
PRIMARY KEY(`SCENARIO_ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `rm_scenario` ADD INDEX `fk_scenario_treeId_idx` (`TREE_ID` ASC);	ALTER TABLE `rm_scenario` ADD CONSTRAINT `fk_scenario_treeId_idx`  FOREIGN KEY (`TREE_ID`)  REFERENCES `rm_forecast_tree` (`TREE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;
ALTER TABLE `rm_scenario` ADD INDEX `fk_scenario__idx` (`LABEL_ID` ASC);	ALTER TABLE `rm_scenario` ADD CONSTRAINT `fk_scenario__idx`  FOREIGN KEY (`LABEL_ID`)  REFERENCES `ap_label` (`LABEL_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;
ALTER TABLE `rm_scenario` ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `LABEL_ID`, ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`, ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`, ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`, ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LAST_MODIFIED_DATE`;	ALTER TABLE `rm_scenario` ADD INDEX `fk_scenario_createdBy_idx` (`CREATED_BY` ASC), ADD INDEX `fk_scenario_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);

CREATE TABLE `rm_forecast_tree_node` (
  `NODE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node id for tree',
  `TREE_ID` int(10) unsigned NOT NULL COMMENT 'TreeId that this Node belongs to',
  `PARENT_NODE_ID` int(10) unsigned DEFAULT NULL COMMENT 'Node Id of the parent. Null if this is the root.',
  `SORT_ORDER` varchar(300) NOT NULL COMMENT 'Sort order of the Node in the tree',
  `LEVEL_NO` int(10) unsigned NOT NULL COMMENT 'Level that this node appears on ',
  `NODE_TYPE_ID` int(10) unsigned NOT NULL COMMENT 'What type of Node is this',
  `UNIT_ID` int(10) unsigned DEFAULT NULL COMMENT 'Indicates the Unit for this Node',
  `MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS` tinyint(1) unsigned NOT NULL COMMENT 'If true any manual changes will cascade into future months. If false the manual change is only for that month and does not cascase into future months',
  `LABEL_ID` int(10) unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `fk_forecastTreeNode_treeId_idx` (`TREE_ID`),
  KEY `fk_forecastTreeNode_parentNodeId_idx` (`PARENT_NODE_ID`),
  KEY `idx_forecastTreeNode_sortOrder` (`SORT_ORDER`),
  KEY `fk_forecastTreeNode_nodeTypeId_idx` (`NODE_TYPE_ID`),
  KEY `fk_forecastTreeNode_unitId_idx` (`UNIT_ID`),
  KEY `fk_forecastTreeNode_labelId_idx` (`LABEL_ID`),
  KEY `fk_forecastTreeNode_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNode_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNode_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_nodeTypeId_idx` FOREIGN KEY (`NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_parentNodeId_idx` FOREIGN KEY (`PARENT_NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_treeId_idx` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNode_unitId_idx` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_tree_node_data_fu` (
  `NODE_DATA_FU_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',
  `FORECASTING_UNIT_ID` int(10) unsigned NOT NULL COMMENT 'Foreign key to indicate forecasting id',
  `LAG_IN_MONTHS` int(10) unsigned NOT NULL COMMENT '# of months to wait before using',
  `USAGE_TYPE_ID` int(10) unsigned NOT NULL COMMENT '1 for Discrete, 2 for Continuous',
  `NO_OF_PERSONS` int(10) unsigned NOT NULL COMMENT '# of Patients this usage will be used for',
  `FORECASTING_UNITS_PER_PERSON` decimal(16,4) unsigned NOT NULL COMMENT '# of Forecasting Units ',
  `ONE_TIME_USAGE` tinyint(1) unsigned NOT NULL,
  `USAGE_FREQUENCY` decimal(16,4) unsigned NOT NULL COMMENT '# of times the Forecasting Unit is given per Usage',
  `USAGE_FREQUENCY_USAGE_PERIOD_ID` int(10) unsigned NOT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',
  `REPEAT_COUNT` decimal(16,4) unsigned DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type',
  `REPEAT_USAGE_PERIOD_ID` int(10) unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_FU_ID`),
  KEY `fk_forecastTreeNodeDataFu_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_forecastTreeNodeDataFu_usageTypeId_idx` (`USAGE_TYPE_ID`),
  KEY `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID`),
  KEY `fk_forecastTreeNodeDataFu_repeatUsagePeriodId_idx` (`REPEAT_USAGE_PERIOD_ID`),
  KEY `fk_forecastTreeNodeDataFu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataFu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataFu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataFu_forecastingUnitId_idx` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataFu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataFu_repeatUsagePeriodId_idx` FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx` FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataFu_usageTypeId_idx` FOREIGN KEY (`USAGE_TYPE_ID`) REFERENCES `ap_usage_type` (`USAGE_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_tree_node_data_pu` (
  `NODE_DATA_PU_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each PU Conversion',
  `PLANNING_UNIT_ID` int(10) unsigned NOT NULL COMMENT 'What Palnning Unit does this Node convert to',
  `SHARE_PLANNING_UNIT` tinyint(1) unsigned NOT NULL COMMENT 'If 1 that means this Planning Unit is to be shared with others and therefore maintain the decimal, if it is not shared you need to round to 1',
  `REFILL_MONTHS` int(10) unsigned NOT NULL COMMENT '# of moths over which refulls are taken',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_PU_ID`),
  KEY `fk_forecastTreeNodeDataPu_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_forecastTreeNodeDataPu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataPu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataPu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataPu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataPu_planningUnitId_idx` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_tree_node_data` (
  `NODE_DATA_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node data id for the node and scenario',
  `NODE_ID` int(10) unsigned NOT NULL COMMENT 'node id ',
  `SCENARIO_ID` int (10) unsigned NOT NULL COMMENT 'Scenario that this Node data is for',
  `MONTH` date NOT NULL COMMENT 'Indicates the month that this Data is for, Defaults to the StartDate of the Forecast Program. Cannot be later than start of Forecast Program',
  `DATA_VALUE` decimal(14,4) DEFAULT NULL COMMENT 'Based on the forecast_tree_node.NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent',
  `NODE_DATA_FU_ID` int(10) unsigned DEFAULT NULL,
  `NODE_DATA_PU_ID` int(10) unsigned DEFAULT NULL,
  `NOTES` text COMMENT 'Notes that describe the Node',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeData_nodeId_idx` (`NODE_ID`),
  KEY `fk_forecastTreeNodeData_scenarioId_idx` (`SCENARIO_ID`),
  KEY `fk_forecastTreeNodeData_nodeDataFuId_idx` (`NODE_DATA_FU_ID`),
  KEY `fk_forecastTreeNodeData_nodeDataPuId_idx` (`NODE_DATA_PU_ID`),
  KEY `fk_forecastTreeNodeData_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeData_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeData_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeData_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeData_nodeDataFuId_idx` FOREIGN KEY (`NODE_DATA_FU_ID`) REFERENCES `rm_forecast_tree_node_data_fu` (`NODE_DATA_FU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeData_nodeDataPuId_idx` FOREIGN KEY (`NODE_DATA_PU_ID`) REFERENCES `rm_forecast_tree_node_data_pu` (`NODE_DATA_PU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeData_nodeId_idx` FOREIGN KEY (`NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeData_scenarioId_idx` FOREIGN KEY (`SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_tree_node_data_modeling` (
  `NODE_DATA_MODELING_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for NodeScaleUp',
  `NODE_DATA_ID` int(10) unsigned NOT NULL COMMENT 'Node that this ScaleUp referrs to',
  `START_DATE` date NOT NULL COMMENT 'Start date that the Scale up is applicable from. Startes from the Forecast Program Start',
  `STOP_DATE` date NOT NULL COMMENT 'Stop date that the Scale up is applicable from. Defaults to Forecast Program End but user can override',
  `MODELING_TYPE_ID` int(10) unsigned NOT NULL COMMENT 'Foreign key to indicate scale type id',
  `DATA_VALUE` decimal(14,2) DEFAULT NULL COMMENT 'Data value could be a number of a % based on the ScaleTypeId',
  `TRANSFER_NODE_ID` int(10) unsigned NOT NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.',
  `NOTES` text COMMENT 'Notes to desribe this scale up',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_MODELING_ID`),
  KEY `fk_forecastTreeNodeDataModeling_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeDataModeling_modelingTypeId_idx` (`MODELING_TYPE_ID`),
  KEY `fk_forecastTreeNodeDataModeling_transferNodeId_idx` (`TRANSFER_NODE_ID`),
  KEY `fk_forecastTreeNodeDataModeling_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataModeling_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataModeling_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_modelingTypeId_idx` FOREIGN KEY (`MODELING_TYPE_ID`) REFERENCES `ap_modeling_type` (`MODELING_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_transferNodeId_idx` FOREIGN KEY (`TRANSFER_NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_tree_node_data_override` (
  `NODE_DATA_OVERRIDE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Override Id',
  `NODE_DATA_ID` int(10) unsigned NOT NULL COMMENT 'The Node Data Id that this Override is for',
  `MONTH` date NOT NULL COMMENT 'Month that this Override is for',
  `MANUAL_CHANGE` decimal(16,4) NOT NULL COMMENT 'The manual change value',
  `SEASONALITY_PERC` decimal(16,4) DEFAULT NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes',
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_OVERRIDE_ID`),
  KEY `fk_forecastTreeNodeDataOverride_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeDataOverride_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataOverride_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataOverride_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataOverride_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastTreeNodeDataOverride_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `fasp`.`rm_forecast_tree` ADD COLUMN `VERSION_ID` INT(10) UNSIGNED NOT NULL AFTER `PROGRAM_ID`;


ALTER TABLE `fasp`.`rm_forecast_tree` ADD CONSTRAINT `fk_forecastTree_versionId_idx` FOREIGN KEY (`PROGRAM_ID` , `VERSION_ID`) REFERENCES `fasp`.`rm_program_version` (`PROGRAM_ID` , `VERSION_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` DROP FOREIGN KEY `fk_forecastTreeNodeDataModeling_transferNodeId_idx`;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` CHANGE COLUMN `TRANSFER_NODE_ID` `TRANSFER_NODE_ID` INT(10) UNSIGNED NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` ADD CONSTRAINT `fk_forecastTreeNodeDataModeling_transferNodeId_idx`  FOREIGN KEY (`TRANSFER_NODE_ID`)  REFERENCES `fasp`.`rm_forecast_tree_node` (`NODE_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,6) NOT NULL COMMENT 'Data value could be a number of a % based on the ScaleTypeId' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` DROP FOREIGN KEY `fk_forecastTreeNodeDataModeling_transferNodeId_idx`;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` CHANGE COLUMN `TRANSFER_NODE_ID` `TRANSFER_NODE_DATA_ID` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.' , DROP INDEX `fk_forecastTreeNodeDataModeling_transferNodeId_idx` , ADD INDEX `fk_forecastTreeNodeDataModeling_transferNodeDataId_idx_idx` (`TRANSFER_NODE_DATA_ID` ASC);
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` ADD CONSTRAINT `fk_forecastTreeNodeDataModeling_transferNodeDataId_idx`  FOREIGN KEY (`TRANSFER_NODE_DATA_ID`)  REFERENCES `fasp`.`rm_forecast_tree_node_data` (`NODE_DATA_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;



USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree` AS
    SELECT 
        `ft`.`TREE_ID` AS `TREE_ID`,
        `ft`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ft`.`VERSION_ID` AS `VERSION_ID`,
        `ft`.`LABEL_ID` AS `LABEL_ID`,
        `ft`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `ft`.`CREATED_BY` AS `CREATED_BY`,
        `ft`.`CREATED_DATE` AS `CREATED_DATE`,
        `ft`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ft`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ft`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_tree` `ft`
        LEFT JOIN `ap_label` `l` ON ((`ft`.`LABEL_ID` = `l`.`LABEL_ID`)));


USE `fasp`;
DROP procedure IF EXISTS `getVersionId`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getVersionId`(PROGRAM_ID INT(10), VERSION_TYPE_ID INT(10), VERSION_STATUS_ID INT(10), NOTES TEXT, FORECAST_START_DATE DATETIME, FORECAST_STOP_DATE DATETIME, CREATED_BY INT(10), CREATED_DATE DATETIME)
BEGIN
	SET @programId = PROGRAM_ID;
	SET @cbUserId = CREATED_BY;
	SET @createdDate = CREATED_DATE;
	SET @versionTypeId = VERSION_TYPE_ID;
	SET @versionStatusId = VERSION_STATUS_ID;
    SET @forecastStartDate = FORECAST_START_DATE;
    SET @forecastStopDate = FORECAST_STOP_DATE;
	SET @notes = NOTES;
    INSERT INTO `fasp`.`rm_program_version`
        (`PROGRAM_ID`, `VERSION_ID`, `VERSION_TYPE_ID`, `VERSION_STATUS_ID`,
        `NOTES`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,
        `SENT_TO_ARTMIS`, `FORECAST_START_DATE`, `FORECAST_STOP_DATE`)
    SELECT
        @programId, IFNULL(MAX(pv.VERSION_ID)+1,1), @versionTypeId, @versionStatusId, 
        @notes, @cbUserId, @createdDate, @cbUserId, @createdDate, 
        0, @forecastStartDate, @forecastStopDate
    FROM rm_program_version pv WHERE pv.`PROGRAM_ID`=@programId;
	SELECT pv.VERSION_ID INTO @versionId FROM rm_program_version pv WHERE pv.`PROGRAM_VERSION_ID`= LAST_INSERT_ID();
	UPDATE rm_program p SET p.CURRENT_VERSION_ID=@versionId WHERE p.PROGRAM_ID=@programId;
	SELECT pv.VERSION_ID, pv.NOTES, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE,
		pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`,
		pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,
		vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, 
		vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` 
	FROM rm_program_version pv 
	LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID
	LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID 
	LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID
	LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID
	LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID
	LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID
	WHERE pv.VERSION_ID=@versionId AND pv.PROGRAM_ID=@programId;
END$$

DELIMITER ;



USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree_node` AS
    SELECT 
        `tn`.`NODE_ID` AS `NODE_ID`,
        `tn`.`TREE_ID` AS `TREE_ID`,
        `tn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `tn`.`SORT_ORDER` AS `SORT_ORDER`,
        `tn`.`LEVEL_NO` AS `LEVEL_NO`,
        `tn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `tn`.`UNIT_ID` AS `UNIT_ID`,
        `tn`.`MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS` AS `MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS`,
        `tn`.`LABEL_ID` AS `LABEL_ID`,
        `tn`.`CREATED_BY` AS `CREATED_BY`,
        `tn`.`CREATED_DATE` AS `CREATED_DATE`,
        `tn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_tree_node` `tn`
        LEFT JOIN `ap_label` `l` ON ((`tn`.`LABEL_ID` = `l`.`LABEL_ID`)));


INSERT INTO ap_label values (null, 'Tanzania Condoms & ARV', null, null, null, 1, @dt, 1, @dt, 45);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_program values (null, "TZA-CON/ARV-MOH", 1, 44 , 1, @labelId, 1, "Testing for Condoms & ARV", null, null, null, null, null, null, null, null, 1, 1, 9, @dt, 9, @dt, 2);
SELECT last_insert_id() into @programId;
INSERT INTO rm_program_version values (null, @programId, 1, 1, 1, "Loaded during testing", 9, @dt, 9, @dt, 1, "2020-01-01", "2024-12-31");
INSERT INTO rm_program_health_area values (null, @programId, 8);
INSERT INTO rm_program_region VALUES (null, @programId, 70, 1, 1, @dt, 1, @dt);

INSERT INTO rm_forecast_tree SELECT null, @programId, 1, tt.LABEL_ID, tt.FORECAST_METHOD_ID, tt.CREATED_BY, tt.CREATED_DATE, tt.LAST_MODIFIED_BY, tt.LAST_MODIFIED_DATE, 1 FROM rm_tree_template tt WHERE tt.TREE_TEMPLATE_ID=1;
SELECT last_insert_id() into @treeId;
INSERT INTO ap_label values (null, 'Default scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId1;
INSERT INTO ap_label values (null, 'High scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId2;
INSERT INTO rm_forecast_tree_node SELECT null, 1, ttn.PARENT_NODE_ID, ttn.SORT_ORDER, ttn.LEVEL_NO, ttn.NODE_TYPE_ID, ttn.UNIT_ID, ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS, ttn.LABEL_ID, ttn.CREATED_BY, ttn.CREATED_DATE, ttn.LAST_MODIFIED_BY, ttn.LAST_MODIFIED_DATE, ttn.ACTIVE FROM rm_tree_template_node ttn where ttn.TREE_TEMPLATE_ID=1;
INSERT INTO rm_forecast_tree_node_data_fu SELECT null, tnd.FORECASTING_UNIT_ID, tnd.LAG_IN_MONTHS, tnd.USAGE_TYPE_ID, tnd.NO_OF_PERSONS, tnd.FORECASTING_UNITS_PER_PERSON, tnd.ONE_TIME_USAGE, tnd.USAGE_FREQUENCY, tnd.USAGE_FREQUENCY_USAGE_PERIOD_ID, tnd.REPEAT_COUNT, tnd.REPEAT_USAGE_PERIOD_ID, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_fu tnd;
INSERT INTO rm_forecast_tree_node_data_fu SELECT null, tnd.FORECASTING_UNIT_ID, tnd.LAG_IN_MONTHS, tnd.USAGE_TYPE_ID, tnd.NO_OF_PERSONS, tnd.FORECASTING_UNITS_PER_PERSON, tnd.ONE_TIME_USAGE, tnd.USAGE_FREQUENCY, tnd.USAGE_FREQUENCY_USAGE_PERIOD_ID, tnd.REPEAT_COUNT, tnd.REPEAT_USAGE_PERIOD_ID, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_fu tnd;
INSERT INTO rm_forecast_tree_node_data_pu SELECT null, tnd.PLANNING_UNIT_ID, tnd.SHARE_PLANNING_UNIT, tnd.REFILL_MONTHS, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_pu tnd;
INSERT INTO rm_forecast_tree_node_data_pu SELECT null, tnd.PLANNING_UNIT_ID, tnd.SHARE_PLANNING_UNIT, tnd.REFILL_MONTHS, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_pu tnd;
INSERT INTO rm_forecast_tree_node_data SELECT null, tnd.NODE_ID, @scenarioId1, tnd.MONTH, tnd.DATA_VALUE, tnd.NODE_DATA_FU_ID, tnd.NODE_DATA_PU_ID, tnd.NOTES, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data tnd;
INSERT INTO rm_forecast_tree_node_data SELECT null, tnd.NODE_ID, @scenarioId2, tnd.MONTH, tnd.DATA_VALUE*1.1, tnd.NODE_DATA_FU_ID, tnd.NODE_DATA_PU_ID, tnd.NOTES, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data tnd;
UPDATE `fasp`.`rm_forecast_tree_node_data` SET `DATA_VALUE`='100' WHERE `NODE_DATA_ID`='23';
UPDATE `fasp`.`rm_forecast_tree_node_data` SET `DATA_VALUE`='100' WHERE `NODE_DATA_ID`='24';


UPDATE rm_forecast_tree_node_data tnd SET tnd.NODE_DATA_FU_ID=tnd.NODE_DATA_FU_ID+3 WHERE tnd.SCENARIO_ID=@scenarioId2 AND tnd.NODE_DATA_FU_ID IS NOT NULL;
UPDATE rm_forecast_tree_node_data tnd SET tnd.NODE_DATA_PU_ID=tnd.NODE_DATA_PU_ID+3 WHERE tnd.SCENARIO_ID=@scenarioId2 AND tnd.NODE_DATA_PU_ID IS NOT NULL;

-- shubham scrit for dataset & equivalency unit
INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Equivalency Unit',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_EQUIVALENCY_UNIT',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Equivalency Unit',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_EQUIVALENCY_UNIT',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Equivalency Unit',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_EQUIVALENCY_UNIT',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Equivalency Unit Mapping',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_EQUIVALENCY_UNIT_MAPPING',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Equivalency Unit Mapping',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_EQUIVALENCY_UNIT_MAPPING',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Equivalency Unit Mapping',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Forecast Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_DATASET',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Forecast Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_DATASET',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Forecast Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_DATASET',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_DATASET','1',NOW(),'1',NOW());


-- INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_DATASET','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_EQUIVALENCY_UNIT','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_APPLICATION_DASHBOARD','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());

INSERT INTO rm_organisation_country VALUES (null, 1, 44, 1, 1, @dt, 1, @dt);


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_scenario` AS
    SELECT 
        `s`.`SCENARIO_ID` AS `SCENARIO_ID`,
        `s`.`TREE_ID` AS `TREE_ID`,
        `s`.`LABEL_ID` AS `LABEL_ID`,
        `s`.`CREATED_BY` AS `CREATED_BY`,
        `s`.`CREATED_DATE` AS `CREATED_DATE`,
        `s`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `s`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `s`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_scenario` `s`
        LEFT JOIN `ap_label` `l` ON ((`s`.`LABEL_ID` = `l`.`LABEL_ID`)));

-- shubham script for usage template
INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Usage Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_USAGE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Usage Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_USAGE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Usage Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_USAGE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_USAGE_TEMPLATE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_USAGE_TEMPLATE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_USAGE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_USAGE_TEMPLATE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_USAGE_TEMPLATE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_USAGE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_USAGE_TEMPLATE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.conversionTOFUTest','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 14 digit number and 4 digits after decimal are allowed.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 14 chiffres et 4 chiffres après la virgule sont autorisés.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 14 dígitos y 4 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'São permitidos no máximo 14 dígitos e 4 dígitos após o decimal.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.equivalancyUnits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Equivalency Units');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités d équivalence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de equivalência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.lagInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lag In Months');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lag en mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Retraso en meses');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atraso em meses');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.people','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# People');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# Personnes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# Gente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# Pessoas');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.fuPerPersonPerTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Of FU / Person / Time');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# d UF / Personne / Temps');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# De FU / persona / tiempo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de FU / Pessoa / Tempo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.onTimeUsage?','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'One Time Usage?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisation unique ?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Uso único?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uso único?');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.usageFrequency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage Frequency');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fréquence d utilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Frecuencia de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Freqüência de Uso');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.fuPerPersonPerMonth?','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Of FU / Person / Month');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# d UF / Personne / Mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# De FU / persona / mes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de FU / pessoa / mês');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.fuRequired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# OF FU Required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nbre d UF requis');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# DE FU requerido');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº DE FU necessário');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.usageInWords','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage In Words');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisation dans les mots');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Uso en palabras');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uso em palavras');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.continuous','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Continuous');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Continue');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Continua');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Contínua');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.discrete','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Discrete');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Discrète');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Discreta');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Discreta');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.dataSet','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Base de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conjunto de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conjunto de dados');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.dataSetManager','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Progarm Manager');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gestionnaire de jeux de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrador de conjuntos de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciador de conjunto de dados');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.equivalancyUnitManage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage Equivalency Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de gestion des équivalences');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Gestionar unidad de equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar Unidade de Equivalência');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.type','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Taper');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Escribe');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.conversionToFu','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion to FU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion en UF');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión a FU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão para FU');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.usageName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d utilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de Uso');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.usageFrequencyTest','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 12 digit number and 4 digits after decimal are allowed.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 12 chiffres et 4 chiffres après la virgule sont autorisés.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 12 dígitos y 4 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'São permitidos no máximo 12 dígitos e 4 dígitos após o decimal.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.calculateUsageFrequency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculate Usage Frequency');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculer la fréquence d utilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calcular la frecuencia de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calcular a frequência de uso');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.usageTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle d utilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de uso');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.every','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Every');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cada');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cada');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.frequency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Frequency');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La fréquence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Frecuencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Frequência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usagePeriod.conversionFactorTestString','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 5 digit number and 8 digits after decimal are allowed.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 5 chiffres et 8 chiffres après la virgule sont autorisés.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 5 dígitos y 8 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'São permitidos no máximo 5 dígitos e 8 dígitos após o decimal.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalancyUnit.equivalancyUnitName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Equivalency Unit (Name)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité d équivalence (Nom)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de equivalencia (nombre)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Equivalência (Nome)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.fuPerPersonPerMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of FU / Person / Month');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# d UF / Personne / Mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de FU / persona / mes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de FU / Pessoa / Mês');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.realmLevel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Realm Level');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Niveau du royaume');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel de reino');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível de reino');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.datasetLevel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Level');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Niveau de l ensemble de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel de conjunto de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível do conjunto de dados');


CREATE TABLE `ap_node_type_rule` (
  `NODE_TYPE_RULE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NODE_TYPE_ID` int(10) unsigned NOT NULL,
  `CHILD_NODE_TYPE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`NODE_TYPE_RULE_ID`),
  KEY `fk_nodeTypeRule_nodeTypeId_idx` (`NODE_TYPE_ID`),
  KEY `fk_nodeTypeRule_childNodeTypeId_idx` (`CHILD_NODE_TYPE_ID`),
  CONSTRAINT `fk_nodeTypeRule_childNodeTypeId` FOREIGN KEY (`CHILD_NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_nodeTypeRule_nodeTypeId` FOREIGN KEY (`NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `fasp`.`ap_node_type` ADD COLUMN `MODELING_ALLOWED` TINYINT(1) UNSIGNED NOT NULL AFTER `LABEL_ID`;

UPDATE `fasp`.`ap_node_type` SET `MODELING_ALLOWED`='0', LAST_MODIFIED_DATE='2021-10-12 00:00:00' WHERE `NODE_TYPE_ID`='1';
UPDATE `fasp`.`ap_node_type` SET `MODELING_ALLOWED`='1', LAST_MODIFIED_DATE='2021-10-12 00:00:00' WHERE `NODE_TYPE_ID`='2';
UPDATE `fasp`.`ap_node_type` SET `MODELING_ALLOWED`='1', LAST_MODIFIED_DATE='2021-10-12 00:00:00' WHERE `NODE_TYPE_ID`='3';
UPDATE `fasp`.`ap_node_type` SET `MODELING_ALLOWED`='1', LAST_MODIFIED_DATE='2021-10-12 00:00:00' WHERE `NODE_TYPE_ID`='4';
UPDATE `fasp`.`ap_node_type` SET `MODELING_ALLOWED`='1', LAST_MODIFIED_DATE='2021-10-12 00:00:00' WHERE `NODE_TYPE_ID`='5';


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_node_type` AS
    SELECT 
        `ut`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
        `ut`.`MODELING_ALLOWED` AS `MODELING_ALLOWED`,
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
        (`ap_node_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`ap_node_type_rule` 
DROP FOREIGN KEY `fk_nodeTypeRule_childNodeTypeId`;
ALTER TABLE `fasp`.`ap_node_type_rule` 
CHANGE COLUMN `CHILD_NODE_TYPE_ID` `CHILD_NODE_TYPE_ID` INT(10) UNSIGNED NULL ;
ALTER TABLE `fasp`.`ap_node_type_rule` 
ADD CONSTRAINT `fk_nodeTypeRule_childNodeTypeId`
  FOREIGN KEY (`CHILD_NODE_TYPE_ID`)
  REFERENCES `fasp`.`ap_node_type` (`NODE_TYPE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


INSERT INTO ap_node_type_rule VALUES (null, 1, 1);
INSERT INTO ap_node_type_rule VALUES (null, 1, 2);
INSERT INTO ap_node_type_rule VALUES (null, 2, 3);
INSERT INTO ap_node_type_rule VALUES (null, 2, 4);
INSERT INTO ap_node_type_rule VALUES (null, 3, 3);
INSERT INTO ap_node_type_rule VALUES (null, 3, 4);
INSERT INTO ap_node_type_rule VALUES (null, 4, 5);
INSERT INTO ap_node_type_rule VALUES (null, 5, null);


ALTER TABLE `fasp`.`rm_equivalency_unit` ADD COLUMN `HEALTH_AREA_ID` INT(10) unsigned NULL AFTER `REALM_ID`, ADD INDEX `fk_rm_equivalency_unit_healthAreaId_idx` (`HEALTH_AREA_ID` ASC);
UPDATE rm_equivalency_unit_mapping eum LEFT JOIN rm_equivalency_unit eu ON eum.EQUIVALENCY_UNIT_ID=eu.EQUIVALENCY_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON eum.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID LEFT JOIN rm_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID SET eu.HEALTH_AREA_ID=tc.HEALTH_AREA_ID;
UPDATE rm_equivalency_unit eu set eu.HEALTH_AREA_ID=3 where eu.EQUIVALENCY_UNIT_ID=3;
ALTER TABLE `fasp`.`rm_equivalency_unit` CHANGE COLUMN `HEALTH_AREA_ID` `HEALTH_AREA_ID` INT(10) unsigned NOT NULL;
ALTER TABLE `fasp`.`rm_equivalency_unit` 
ADD CONSTRAINT `fk_rm_equivalency_unit_healthAreaId`
  FOREIGN KEY (`HEALTH_AREA_ID`)
  REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_equivalency_unit` AS
    SELECT 
        `ut`.`EQUIVALENCY_UNIT_ID` AS `EQUIVALENCY_UNIT_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`HEALTH_AREA_ID` AS `HEALTH_AREA_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`rm_equivalency_unit` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`rm_usage_template` ADD COLUMN `NOTES` TEXT NULL AFTER `REPEAT_COUNT`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_usage_template` AS
    SELECT 
        `ut`.`USAGE_TEMPLATE_ID` AS `USAGE_TEMPLATE_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        `ut`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ut`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,
        `ut`.`LAG_IN_MONTHS` AS `LAG_IN_MONTHS`,
        `ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,
        `ut`.`NO_OF_PATIENTS` AS `NO_OF_PATIENTS`,
        `ut`.`NO_OF_FORECASTING_UNITS` AS `NO_OF_FORECASTING_UNITS`,
        `ut`.`ONE_TIME_USAGE` AS `ONE_TIME_USAGE`,
        `ut`.`USAGE_FREQUENCY_USAGE_PERIOD_ID` AS `USAGE_FREQUENCY_USAGE_PERIOD_ID`,
        `ut`.`USAGE_FREQUENCY_COUNT` AS `USAGE_FREQUENCY_COUNT`,
        `ut`.`REPEAT_USAGE_PERIOD_ID` AS `REPEAT_USAGE_PERIOD_ID`,
        `ut`.`REPEAT_COUNT` AS `REPEAT_COUNT`,
        `ut`.`NOTES` AS `NOTES`, 
        `ut`.`ACTIVE` AS `ACTIVE`,
        `ut`.`CREATED_DATE` AS `CREATED_DATE`,
        `ut`.`CREATED_BY` AS `CREATED_BY`,
        `ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `l`.`LABEL_ID` AS `LABEL_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_usage_template` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`rm_program` 
DROP INDEX `unq_program_programCode` ,
ADD UNIQUE INDEX `unq_program_programCode` (`PROGRAM_CODE` ASC, `REALM_ID` ASC, `PROGRAM_TYPE_ID` ASC);


CREATE TABLE `fasp`.`rm_forecast_tree_region` (
  `FORECAST_TREE_REGION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `TREE_ID` INT(10) UNSIGNED NOT NULL,
  `REGION_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`FORECAST_TREE_REGION_ID`),
  INDEX `fk_rm_forecast_tree_region_treeId_idx` (`TREE_ID` ASC),
  INDEX `fk_rm_forecast_tree_region_regionId_idx` (`REGION_ID` ASC),
  CONSTRAINT `fk_rm_forecast_tree_region_treeId`
    FOREIGN KEY (`TREE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree` (`TREE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_tree_region_regionId`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
INSERT INTO rm_forecast_tree_region VALUES (null, @treeId, 70);


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.manageProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer le programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrar programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.forecastingProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasting Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa de Previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.equivalencyUnit.conversionToEU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion To EU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion vers l UE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión a la UE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão para UE');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.calculatorReminderText','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For products used more than once,right click to open the Interval to frequency calculator');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour les produits utilisés plus d une fois, faites un clic droit pour ouvrir le calculateur d intervalle à fréquence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para productos utilizados más de una vez, haga clic derecho para abrir la calculadora de intervalo a frecuencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para produtos usados ​​mais de uma vez, clique com o botão direito para abrir a calculadora de intervalo para frequência');



INSERT INTO ap_label values (null, 'ARV Tree', null, null, null, 1, @dt, 1, @dt, 48);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_forecast_tree VALUES (null, @programId, 1, @labelId, 1, 1, @dt, 1, @dt, 1);

SELECT last_insert_id() into @treeId;
INSERT INTO ap_label values (null, 'Most likely scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId1;
INSERT INTO ap_label values (null, 'Over estimation', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId2;
INSERT INTO ap_label values (null, 'Under estimation', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId3;

INSERT INTO ap_label values (null, 'ARV Patients', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, null, "00", 1, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId;
INSERT INTO ap_label values (null, 'Adults', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01", 2, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Children', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.02", 2, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId-1;

INSERT INTO ap_label values (null, 'Adults 1st Line', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.01", 3, 2, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 45386964, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 53820401, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 39102354, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Adults 2nd Line', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.02", 3, 2, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 55442475, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 60420158, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 48391200, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId - 1;
INSERT INTO ap_label values (null, 'TLD', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.01.01", 4, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 36.8, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 33.5, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 45.9, null, null, "", 1, @dt, 1, @dt, 1);
INSERT INTO rm_forecast_tree_region values (null, @treeId, 70);


INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 1, '2021-01-01', '2021-12-31', 3, 0.0875, null, 'An increase of 0.0875% every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 1, '2022-01-01', '2025-12-31', 3, 0.0975, null, 'An increase of 0.0975% every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 4, '2021-01-01', '2021-12-31', 4, 0.05, null, 'An increase of 0.05% of sexually active men every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 4, '2022-01-01', '2025-12-31', 4, 0.073, null, 'An increase of 0.073% of sexually active men every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 6, '2021-01-01', '2025-12-31', 2, -8750, 7, '8750 men move over from No logo to Strawberry condoms every month', 9, now(), 9, now(), 1);

INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2021-01-01', '2025-12-31', 2, 25500, null, 'An increase of 25500 number of Patients every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2021-01-01', '2021-12-31', 2, -4760, 45, '4760 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2022-01-01', '2022-12-31', 2, -5500, 45, '5500 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2023-01-01', '2023-12-31', 2, -6340, 45, '6340 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2024-01-01', '2024-12-31', 2, -7120, 45, '7120 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2025-01-01', '2025-12-31', 2, -8030, 45, '8030 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);

CREATE TABLE `fasp`.`rm_equivalency_unit_health_area` (
  `EQUIVALENCY_UNIT_HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `EQUIVALENCY_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_HEALTH_AREA_ID`),
  INDEX `fk_rm_equivalency_unit_health_area_equivalencyUnitId_idx` (`EQUIVALENCY_UNIT_ID` ASC),
  INDEX `fk_rm_equivalency_unit_health_area_healthAreaId_idx` (`HEALTH_AREA_ID` ASC),
  CONSTRAINT `fk_rm_equivalency_unit_health_area_equivalencyUnitId`
    FOREIGN KEY (`EQUIVALENCY_UNIT_ID`)
    REFERENCES `fasp`.`rm_equivalency_unit` (`EQUIVALENCY_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_equivalency_unit_health_area_healthAreaId`
    FOREIGN KEY (`HEALTH_AREA_ID`)
    REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO rm_equivalency_unit_health_area SELECT null, eu.EQUIVALENCY_UNIT_ID, eu.HEALTH_AREA_ID from rm_equivalency_unit eu;

ALTER TABLE `fasp`.`rm_equivalency_unit` DROP FOREIGN KEY `fk_rm_equivalency_unit_healthAreaId`;
ALTER TABLE `fasp`.`rm_equivalency_unit` DROP COLUMN `HEALTH_AREA_ID`, DROP INDEX `fk_rm_equivalency_unit_healthAreaId_idx` ;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_equivalency_unit` AS
    SELECT 
        `ut`.`EQUIVALENCY_UNIT_ID` AS `EQUIVALENCY_UNIT_ID`,
        `ut`.`REALM_ID` AS `REALM_ID`,
        GROUP_CONCAT(`euha`.`HEALTH_AREA_ID`) AS `HEALTH_AREA_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
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
        (`rm_equivalency_unit` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))
        LEFT JOIN rm_equivalency_unit_health_area euha ON ut.EQUIVALENCY_UNIT_ID=euha.EQUIVALENCY_UNIT_ID)
    GROUP BY ut.EQUIVALENCY_UNIT_ID;
INSERT INTO rm_equivalency_unit_health_area values (null, 2, 2),(null, 7, 2);

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.treeName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom de larbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome da Árvore');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.selectregion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select a region');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner une région');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una región');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma região');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.selecttemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionner un modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar plantilla');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o modelo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.selecttracercategory','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Tracer Category');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Select Tracer Category');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar categoría de trazador');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a categoria de rastreador');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.managetree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrar árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.addtree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter un arbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar Árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.typeofuse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Type Of Use');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type dutilisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de Uso');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.forecastmethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select a method');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner une méthode');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un método');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione um método');

ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` DROP FOREIGN KEY `fk_treeTemplateNodeDataModeling_transferNodeId_idx`;
ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` CHANGE COLUMN `TRANSFER_NODE_ID` `TRANSFER_NODE_DATA_ID` INT(10) UNSIGNED NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.' ,DROP INDEX `fk_treeTemplateNodeDataModeling_transferNodeId_idx` ,ADD INDEX `fk_treeTemplateNodeDataModeling_transferNodeId_idx_idx` (`TRANSFER_NODE_DATA_ID` ASC);
ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` ADD CONSTRAINT `fk_treeTemplateNodeDataModeling_transferNodeId_idx`  FOREIGN KEY (`TRANSFER_NODE_DATA_ID`)  REFERENCES `fasp`.`rm_tree_template_node_data` (`NODE_DATA_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;

CREATE TABLE `fasp`.`rm_forecast_consumption_unit` (
  `CONSUMPTION_UNIT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int(10) unsigned NOT NULL,
  `FORECASTING_UNIT_ID` int(10) unsigned NOT NULL,
  `DATA_TYPE` INT(10) UNSIGNED NOT NULL,
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NULL,
  `OTHER_UNIT_LABEL_ID` INT(10) UNSIGNED NULL,
  `OTHER_UNIT_MULTIPLIER_FOR_FU` DECIMAL(16,4) UNSIGNED NULL,
  `VERSION_ID` INT(10) UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`CONSUMPTION_UNIT_ID`),
  KEY `fk_rm_forecast_consumption_unit_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_forecast_consumption_unit_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_rm_forecast_consumption_unit_planningUnitId_idx` (`PLANNING_UNIT_ID` ASC),
  KEY `fk_rm_forecast_consumption_unit_labelId_idx` (`OTHER_UNIT_LABEL_ID` ASC),
  KEY `fk_rm_forecast_consumption_unit_createdBy_idx` (`CREATED_BY` ASC),
  CONSTRAINT `fk_rm_forecast_consumption_unit_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION, 
  CONSTRAINT `fk_rm_forecast_consumption_unit_forecastingUnitId` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_unit_planningUnitId`    FOREIGN KEY (`PLANNING_UNIT_ID`)    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_unit_labelId`  FOREIGN KEY (`OTHER_UNIT_LABEL_ID`)  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_unit_createdBy`    FOREIGN KEY (`CREATED_BY`)    REFERENCES `fasp`.`us_user` (`USER_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION);


ALTER TABLE `fasp`.`rm_forecast_consumption_unit` ADD INDEX `idx_rm_forecast_consumption_unit_versionId` (`VERSION_ID` ASC);


CREATE TABLE `fasp`.`rm_forecast_consumption` (
  `CONSUMPTION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL,
  `CONSUMPTION_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `REGION_ID` INT(10) UNSIGNED NOT NULL,
  `MONTH` DATE NOT NULL,
  `ACTUAL_CONSUMPTION` DECIMAL(16,4) UNSIGNED NULL,
  `REPORTING_RATE` DECIMAL(6,2) UNSIGNED NULL,
  `DAYS_OF_STOCK_OUT` INT(10) UNSIGNED NULL,
  `EXCLUDE` TINYINT(1) UNSIGNED NOT NULL,
  `VERSION_ID` INT(10) UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`CONSUMPTION_ID`),
  INDEX `fk_rm_forecast_consumption_programId_idx` (`PROGRAM_ID` ASC),
  INDEX `fk_rm_forecast_consumption_consumptionUnitId_idx` (`CONSUMPTION_UNIT_ID` ASC),
  INDEX `fk_rm_forecast_consumption_regionId_idx` (`REGION_ID` ASC),
  INDEX `fk_rm_forecast_consumption_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `idx_rm_forecast_consumption_versionId` (`VERSION_ID` ASC),
  CONSTRAINT `fk_rm_forecast_consumption_programId`    FOREIGN KEY (`PROGRAM_ID`)    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_consumptionUnitId`    FOREIGN KEY (`CONSUMPTION_UNIT_ID`)    REFERENCES `fasp`.`rm_forecast_consumption_unit` (`CONSUMPTION_UNIT_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_regionId`    FOREIGN KEY (`REGION_ID`)    REFERENCES `fasp`.`rm_region` (`REGION_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_createdBy`    FOREIGN KEY (`CREATED_BY`)    REFERENCES `fasp`.`us_user` (`USER_ID`)    ON DELETE NO ACTION    ON UPDATE NO ACTION);


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_consumption_unit` AS
    SELECT 
        `cu`.`CONSUMPTION_UNIT_ID` AS `CONSUMPTION_UNIT_ID`,
        `cu`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `cu`.`DATA_TYPE` AS `DATA_TYPE`,
        `cu`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,
        `cu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,
        `cu`.`OTHER_UNIT_LABEL_ID` AS `OTHER_UNIT_LABEL_ID`,
        `cu`.`OTHER_UNIT_MULTIPLIER_FOR_FU` AS `OTHER_UNIT_MULTIPLIER_FOR_FU`,
        `cu`.`VERSION_ID` AS `VERSION_ID`,
        `cu`.`CREATED_BY` AS `CREATED_BY`,
        `cu`.`CREATED_DATE` AS `CREATED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_consumption_unit` `cu`
        LEFT JOIN `ap_label` `l` ON ((`cu`.`OTHER_UNIT_LABEL_ID` = `l`.`LABEL_ID`)));

INSERT INTO ap_label_source VALUES (null, 'rm_forecast_consumption_unit');
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '911', '1', NULL, NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '915', '2', '4148', NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO ap_label values (null, "10 Bottles of TLD 90", null, null, null, 1, now(), 1, now(), 51);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '2665', '3', NULL, @labelId, 900, '1', '1', '2021-10-27 00:00:00');

INSERT INTO ap_label values (null, "North", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @northId;
INSERT INTO rm_program_region values (null, @programId, @northId, 1, 1, now(), 1, now());

INSERT INTO ap_label values (null, "South", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @southId;
INSERT INTO rm_program_region values (null, @programId, @southId, 1, 1, now(), 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-01-01', 5000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-02-01', 6500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-03-01', 6200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-04-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-05-01', 6800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-06-01', 6400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-07-01', 5800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-08-01', 5900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-09-01', 6300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-10-01', 6900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-11-01', 7500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-12-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-01-01', 7100, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-02-01', 8400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-03-01', 8300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-04-01', 9000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-05-01', 7600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-06-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-07-01', 6700, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-01-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-02-01', 3800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-03-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-04-01', 3650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-05-01', 3700, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-06-01', 3200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-07-01', 3780, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-08-01', 3900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-09-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-10-01', 3280, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-11-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-12-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-01-01', 3730, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-02-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-03-01', 3380, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-04-01', 3840, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-05-01', 3480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-06-01', 3370, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-07-01', 3600, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 2, @southId, '2020-01-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-02-01', 5586, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-03-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-04-01', 5365, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-05-01', 5439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-06-01', 4704, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-07-01', 5556, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-08-01', 5733, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-09-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-10-01', 4821, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-11-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-12-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-01-01', 5483, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-02-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-03-01', 4968, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-04-01', 5644, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-05-01', 5115, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-06-01', 4953, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-07-01', 5292, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-01-01', 540, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-02-01', 480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-03-01', 580, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-04-01', 500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-05-01', 560, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-06-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-07-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-08-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-09-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-10-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-11-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-12-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-01-01', 600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-02-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-03-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-04-01', 650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-05-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-06-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-07-01', 680, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 3, @southId, '2020-01-01', 1274, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-02-01', 1132, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-03-01', 1368, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-04-01', 1180, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-05-01', 1321, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-06-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-07-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-08-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-09-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-10-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-11-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-12-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-01-01', 1416, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-02-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-03-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-04-01', 1534, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-05-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-06-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-07-01', 1604, null, null, 0, 1, 1, now());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.usageTemplateText','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Usage templates on this screen are available when building forecasting unit nodes in forecast trees.Usage templates are set at the realm (across all programs) or program level.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les modèles d utilisation sur cet écran sont disponibles lors de la création de nœuds d unité de prévision dans les arbres de prévision. Les modèles d utilisation sont définis au niveau du domaine (dans tous les programmes) ou au niveau du programme.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las plantillas de uso en esta pantalla están disponibles cuando se construyen nodos de unidad de pronóstico en árboles de pronóstico. Las plantillas de uso se establecen en el ámbito (en todos los programas) o en el nivel del programa.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os modelos de uso nesta tela estão disponíveis ao construir nós de unidade de previsão em árvores de previsão. Os modelos de uso são definidos no domínio (em todos os programas) ou no nível do programa.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.versionSettings.versionSettings','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version Settings');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres de version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configurações de versão');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configuración de versión');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.programDiscription','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Notes de version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de versão');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.dateCommitted','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Date Committed');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date dengagement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Data de Compromisso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fecha de compromiso');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.commitedbyUser','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Commited by User');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Engagé par lutilisateur');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comprometido pelo usuário');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Comprometido por el usuario');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.forecastStart','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Start');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Début de la prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Previsão de Início');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inicio del pronóstico');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.forecastEnd','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast End');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fin de la prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fim da previsão');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fin del pronóstico');


ALTER TABLE `fasp`.`rm_tree_template` ADD COLUMN `MONTHS_IN_PAST` INT(10) UNSIGNED NULL AFTER `FORECAST_METHOD_ID`, ADD COLUMN `MONTHS_IN_FUTURE` INT(10) UNSIGNED NULL AFTER `MONTHS_IN_PAST`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template` AS
    SELECT 
        `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tt`.`REALM_ID` AS `REALM_ID`,
        `tt`.`LABEL_ID` AS `LABEL_ID`,
        `tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `tt`.`MONTHS_IN_PAST` AS `MONTHS_IN_PAST`,
        `tt`.`MONTHS_IN_FUTURE` AS `MONTHS_IN_FUTURE`,
        `tt`.`CREATED_BY` AS `CREATED_BY`,
        `tt`.`CREATED_DATE` AS `CREATED_DATE`,
        `tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tt`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));


ALTER TABLE `fasp`.`rm_tree_template_node_data` CHANGE COLUMN `MONTH` `MONTH` INT(10) NOT NULL COMMENT 'Indicates the month that this Data is for, is always a +ve number starting from 0 which is for the Start month. Cannot be greater than the MonthsInPast+MonthsInFuture+1' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` CHANGE COLUMN `START_DATE` `START_DATE` INT(10) UNSIGNED NOT NULL COMMENT 'Start date that the Modeling is applicable from. Starts from the Forecast Program Start' , CHANGE COLUMN `STOP_DATE` `STOP_DATE` INT(10) UNSIGNED NOT NULL COMMENT 'Stop date that the Modeling is applicable from. Defaults to Forecast Program End but user can override' ;


UPDATE rm_tree_template tt set tt.MONTHS_IN_PAST=0, tt.MONTHS_IN_FUTURE=36 WHERE tt.TREE_TEMPLATE_ID=1; 
UPDATE rm_tree_template tt set tt.MONTHS_IN_PAST=0, tt.MONTHS_IN_FUTURE=24 WHERE tt.TREE_TEMPLATE_ID=2; 

UPDATE rm_tree_template_node_data ttnd SET ttnd.MONTH=0;


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.monthsInPast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months In Past');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois passés');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses en el pasado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses No Passado');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.monthsInFuture','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months In Future');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois à venir');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses en el futuro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses No Futuro');

ALTER TABLE `fasp`.`rm_program_version` ADD COLUMN `DAYS_IN_MONTH` INT(10) UNSIGNED NULL AFTER `FORECAST_STOP_DATE`;
UPDATE `fasp`.`rm_program_version` SET `DAYS_IN_MONTH`='28' WHERE `PROGRAM_VERSION_ID`='1643';


USE `fasp`;
DROP procedure IF EXISTS `getVersionId`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getVersionId`(PROGRAM_ID INT(10), VERSION_TYPE_ID INT(10), VERSION_STATUS_ID INT(10), NOTES TEXT, FORECAST_START_DATE DATETIME, FORECAST_STOP_DATE DATETIME, DAYS_IN_MONTH INT(10), CREATED_BY INT(10), CREATED_DATE DATETIME)
BEGIN
	SET @programId = PROGRAM_ID;
	SET @cbUserId = CREATED_BY;
	SET @createdDate = CREATED_DATE;
	SET @versionTypeId = VERSION_TYPE_ID;
	SET @versionStatusId = VERSION_STATUS_ID;
    SET @forecastStartDate = FORECAST_START_DATE;
    SET @forecastStopDate = FORECAST_STOP_DATE;
    SET @daysInMonth = DAYS_IN_MONTH;
	SET @notes = NOTES;
    INSERT INTO `fasp`.`rm_program_version`
        (`PROGRAM_ID`, `VERSION_ID`, `VERSION_TYPE_ID`, `VERSION_STATUS_ID`,
        `NOTES`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,
        `SENT_TO_ARTMIS`, `FORECAST_START_DATE`, `FORECAST_STOP_DATE`, `DAYS_IN_MONTH`)
    SELECT
        @programId, IFNULL(MAX(pv.VERSION_ID)+1,1), @versionTypeId, @versionStatusId, 
        @notes, @cbUserId, @createdDate, @cbUserId, @createdDate, 
        0, @forecastStartDate, @forecastStopDate, @daysInMonth
    FROM rm_program_version pv WHERE pv.`PROGRAM_ID`=@programId;
	SELECT pv.VERSION_ID INTO @versionId FROM rm_program_version pv WHERE pv.`PROGRAM_VERSION_ID`= LAST_INSERT_ID();
	UPDATE rm_program p SET p.CURRENT_VERSION_ID=@versionId WHERE p.PROGRAM_ID=@programId;
	SELECT pv.VERSION_ID, pv.NOTES, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.DAYS_IN_MONTH,
		pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`,
		pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,
		vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, 
		vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` 
	FROM rm_program_version pv 
	LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID
	LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID 
	LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID
	LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID
	LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID
	LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID
	WHERE pv.VERSION_ID=@versionId AND pv.PROGRAM_ID=@programId;
END$$

DELIMITER ;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.noOfDaysInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Of Days In Month');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de jours dans le mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# De días en el mes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de dias do mês');

ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,6) NULL DEFAULT NULL COMMENT 'Data value could be a number of a % based on the ModelingTypeId' ;

INSERT INTO rm_tree_template_node_data_modeling values (null, 1, 0, 11, 3, 0.0875, null, ' An increase of 0.0875% every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 1, 12, 48, 3, 0.0975, null, ' An increase of 0.0975% every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 4, 0, 11, 4, 0.05, null, ' An increase of 0.05% of sexually active men every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 4, 12, 48, 4, 0.073, null, ' An increase of 0.073% of sexually active men every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 6, 0, 48, 2, -8750, 7, ' 8750 men move over from No logo to Strawberry condoms every month', 1, now(), 1, now(), 1);

USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanActualConsumption`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanActualConsumption`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_LIST TEXT, REGION_LIST VARCHAR(255), START_DATE DATE, STOP_DATE DATE)
BEGIN
	SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUnitList = PLANNING_UNIT_LIST;
    SET @regionList = REGION_LIST;
    SET @startDate = START_DATE;
    SET @stopDate = STOP_DATE;  
    
    SELECT 
        ct.CONSUMPTION_DATE, ct.CONSUMPTION_QTY, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `REG_LABEL_ID`, r.LABEL_EN `REG_LABEL_EN`, r.LABEL_FR `REG_LABEL_FR`, r.LABEL_SP `REG_LABEL_SP`, r.LABEL_PR `REG_LABEL_PR`
    FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID) tc 
    LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
    LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
    WHERE ct.CONSUMPTION_DATE BETWEEN @startDate AND @stopDate   
    AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @planningUnitList) 
    AND ct.ACTIVE AND ct.ACTUAL_FLAG AND FIND_IN_SET(ct.REGION_ID, @regionList)
    ORDER BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE;
END$$

DELIMITER ;


INSERT INTO ap_label VALUES (null, 'Linear (% point)', null, null, null, 1, now(), 1, now(), 41);
SELECT last_insert_id() into @labelId;
INSERT INTO ap_modeling_type values (null, @labelId, 1, 1, now(), 1, now());

ALTER TABLE `fasp`.`rm_forecast_tree` ADD COLUMN `NOTES` TEXT NULL AFTER `ACTIVE`;
ALTER TABLE `fasp`.`rm_tree_template` ADD COLUMN `NOTES` TEXT NULL AFTER `ACTIVE`;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template` AS
    SELECT 
        `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tt`.`REALM_ID` AS `REALM_ID`,
        `tt`.`LABEL_ID` AS `LABEL_ID`,
        `tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `tt`.`MONTHS_IN_PAST` AS `MONTHS_IN_PAST`,
        `tt`.`MONTHS_IN_FUTURE` AS `MONTHS_IN_FUTURE`,
        `tt`.`CREATED_BY` AS `CREATED_BY`,
        `tt`.`CREATED_DATE` AS `CREATED_DATE`,
        `tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tt`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `tt`.`NOTES` AS `NOTES`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree` AS
    SELECT 
        `ft`.`TREE_ID` AS `TREE_ID`,
        `ft`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ft`.`VERSION_ID` AS `VERSION_ID`,
        `ft`.`LABEL_ID` AS `LABEL_ID`,
        `ft`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `ft`.`CREATED_BY` AS `CREATED_BY`,
        `ft`.`CREATED_DATE` AS `CREATED_DATE`,
        `ft`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ft`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ft`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `ft`.`NOTES` AS `NOTES`
    FROM
        (`rm_forecast_tree` `ft`
        LEFT JOIN `ap_label` `l` ON ((`ft`.`LABEL_ID` = `l`.`LABEL_ID`)));

UPDATE `fasp`.`rm_forecast_consumption_unit` SET `PLANNING_UNIT_ID`='2733' WHERE `CONSUMPTION_UNIT_ID`='3';
UPDATE `fasp`.`rm_forecast_consumption_unit` SET `PLANNING_UNIT_ID`='4159' WHERE `CONSUMPTION_UNIT_ID`='1';

ALTER TABLE `fasp`.`rm_forecast_consumption_unit` DROP FOREIGN KEY `fk_rm_forecast_consumption_unit_planningUnitId`, DROP FOREIGN KEY `fk_rm_forecast_consumption_unit_forecastingUnitId`;
ALTER TABLE `fasp`.`rm_forecast_consumption_unit` DROP COLUMN `FORECASTING_UNIT_ID`, CHANGE COLUMN `PLANNING_UNIT_ID` `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL , DROP INDEX `fk_rm_forecast_consumption_unit_forecastingUnitId_idx` ;
ALTER TABLE `fasp`.`rm_forecast_consumption_unit` ADD CONSTRAINT `fk_rm_forecast_consumption_unit_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_consumption_unit` AS
    SELECT 
        `cu`.`CONSUMPTION_UNIT_ID` AS `CONSUMPTION_UNIT_ID`,
        `cu`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `cu`.`DATA_TYPE` AS `DATA_TYPE`,
        `cu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,
        `cu`.`OTHER_UNIT_LABEL_ID` AS `OTHER_UNIT_LABEL_ID`,
        `cu`.`OTHER_UNIT_MULTIPLIER_FOR_FU` AS `OTHER_UNIT_MULTIPLIER_FOR_FU`,
        `cu`.`VERSION_ID` AS `VERSION_ID`,
        `cu`.`CREATED_BY` AS `CREATED_BY`,
        `cu`.`CREATED_DATE` AS `CREATED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_consumption_unit` `cu`
        LEFT JOIN `ap_label` `l` ON ((`cu`.`OTHER_UNIT_LABEL_ID` = `l`.`LABEL_ID`)));
UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5', `DATA_VALUE`='-0.5' WHERE `NODE_DATA_MODELING_ID`='5';
UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5' WHERE `NODE_DATA_MODELING_ID`='3';
UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5' WHERE `NODE_DATA_MODELING_ID`='4';


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Import From QAT Supply Plan',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_IMPORT_FROM_QAT_SUPPLY_PLAN',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Import From QAT Supply Plan',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_IMPORT_FROM_QAT_SUPPLY_PLAN',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Import From QAT Supply Plan',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_ADD_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_EDIT_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN','1',NOW(),'1',NOW());




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.importFromQATSupplyPlan','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import From QAT Supply Plan');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importer du plan d approvisionnement QAT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importar desde el plan de suministro QAT');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importar do plano de abastecimento QAT');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.ProgramAndPlanningUnits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program and Planning Units');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de programme et de planification');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de programación y planificación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa e unidades de planejamento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.belongsSameCountry','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Program and Forecast Program should belong to same Country');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le programme de plan d approvisionnement et le programme de prévision doivent appartenir au même pays');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El programa del plan de suministro y el programa de previsión deben pertenecer al mismo país');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O Programa de Plano de Abastecimento e o Programa de Previsão devem pertencer ao mesmo país');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.selectSupplyPlanProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select supply plan program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner le programme de plan d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el programa del plan de suministros');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o programa de plano de abastecimento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.pleaseSelectSupplyPlanVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select supply plan version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner la version du plan d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione la versión del plan de suministros');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a versão do plano de abastecimento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.pleaseSelectForecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select forecast program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner le programme de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el programa de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o programa de previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanPlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Planning Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de planification du plan d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de planificación del plan de suministro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Planejamento do Plano de Abastecimento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.forecastPlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Planning Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de planification des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de planificación de previsiones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de planejamento de previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.conversionFactor','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor (Supply Plan to Forecast Planning Unit)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (plan d approvisionnement en unité de planification prévisionnelle)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (plan de suministro a unidad de planificación de previsión)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (plano de fornecimento para unidade de planejamento de previsão)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'équivalence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'equivalência');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme de plan d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa de plan de suministro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa de Plano de Abastecimento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.forecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa de previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.Range','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Range');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Varier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Distancia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Faixa');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanRegion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Region');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Région du plan d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Región del plan de suministro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'equivalência');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.isRegionInForecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Is region in forecast program?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La région est-elle dans le programme de prévision ?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Está la región en el programa de pronóstico?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A região está no programa de previsão?');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.Import','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importar');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.noRegionToImportInto','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No region to import into');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune région dans laquelle importer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay región a la que importar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma região para importar para');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Consumption');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Plan d approvisionnement Consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plan d approvisionnement Consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo do Plano de Abastecimento');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.multiplier','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multiplier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Multiplicateur');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Multiplicadora');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Multiplicadora');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.currentQATConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Current QAT Consumption');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation QAT actuelle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consommation QAT actuelle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo atual de QAT');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.confirmAlert','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Selected rows will be imported. Note that imported data will override any existing consumption for those months, region & planning units.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les lignes sélectionnées seront importées. Notez que les données importées remplaceront toute consommation existante pour ces mois, régions et unités de planification.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se importarán las filas seleccionadas. Tenga en cuenta que los datos importados anularán cualquier consumo existente para esos meses, regiones y unidades de planificación.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'As linhas selecionadas serão importadas. Observe que os dados importados substituirão qualquer consumo existente para esses meses, regiões e unidades de planejamento.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.actualConsumption(SupplyPlanModule)','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Consumption(Supply Plan Module)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle (module de plan d approvisionnement)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real (módulo de plan de suministro)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real (Módulo de Plano de Abastecimento)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.conversionFactor(SupplyPlantoForecast)','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor(Supply Plan to Forecast)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (plan d approvisionnement à prévision)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (plan de suministro a pronóstico)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (plano de fornecimento para previsão)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.convertedActualConsumption(SupplyPlanModule)','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Converted Actual Consumption(Supply Plan Module)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle convertie (module de plan d approvisionnement)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real convertido (módulo de plan de suministro)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real convertido (Módulo de Plano de Abastecimento)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.currentActualConsumption(ForecastModule)','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Current Actual Consumption(Forecast Module)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle actuelle (module de prévision)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real actual (módulo de pronóstico)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real atual (módulo de previsão)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.dataAlreadyExistsInForecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data already exists in Forecast Program');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les données existent déjà dans le programme de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los datos ya existen en el programa de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os dados já existem no programa de previsão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importFromQATSupplyPlan.allValuesBelowAreInSupplyPlanningUnits.','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All values below are in supply planning units.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Toutes les valeurs ci-dessous sont exprimées en unités de planification des approvisionnements.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los valores siguientes están en unidades de planificación de aprovisionamiento.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todos os valores abaixo estão em unidades de planejamento de abastecimento.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.ForecastPeriodInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Period (Months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Période de prévision (mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Período de previsión (meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Período de previsão (meses)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.freight%','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Freight %');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cargaison %');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Transporte %');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% De frete');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.forecastThresholdHigh','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Threshold (high, %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuil de prévision (élevé, %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Umbral de pronóstico (alto,%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Limite de previsão (alto,%)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.ForecastThresholdLow','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Threshold (low, %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuil de prévision (bas, %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Umbral de pronóstico (bajo,%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Limite de previsão (baixo,%)');

ALTER TABLE `fasp`.`rm_program_version` ADD COLUMN `FREIGHT_PERC` DECIMAL(12,2) NULL AFTER `DAYS_IN_MONTH`,ADD COLUMN `FORECAST_THRESHOLD_HIGH_PERC` DECIMAL(12,2) NULL AFTER `FREIGHT_PERC`,ADD COLUMN `FORECAST_THRESHOLD_LOW_PERC` DECIMAL(12,2) NULL AFTER `FORECAST_THRESHOLD_HIGH_PERC`;


USE `fasp`;
DROP procedure IF EXISTS `getVersionId`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getVersionId`(PROGRAM_ID INT(10), VERSION_TYPE_ID INT(10), VERSION_STATUS_ID INT(10), NOTES TEXT, FORECAST_START_DATE DATETIME, FORECAST_STOP_DATE DATETIME, DAYS_IN_MONTH INT(10), FREIGHT_PERC DECIMAL, FORECAST_THRESHOLD_HIGH_PERC DECIMAL, FORECAST_THRESHOLD_LOW_PER DECIMAL, CCREATED_BY INT(10), CREATED_DATE DATETIME)
BEGIN
	SET @programId = PROGRAM_ID;
	SET @cbUserId = CREATED_BY;
	SET @createdDate = CREATED_DATE;
	SET @versionTypeId = VERSION_TYPE_ID;
	SET @versionStatusId = VERSION_STATUS_ID;
    SET @forecastStartDate = FORECAST_START_DATE;
    SET @forecastStopDate = FORECAST_STOP_DATE;
    SET @daysInMonth = DAYS_IN_MONTH;
    SET @freightPerc = FREIGHT_PERC;
    SET @forecastThresholdHighPerc = FORECAST_THRESHOLD_HIGH_PERC;
    SET @forecastThresholdLowPerc = FORECAST_THRESHOLD_LOW_PERC;
	SET @notes = NOTES;
    INSERT INTO `fasp`.`rm_program_version`
        (`PROGRAM_ID`, `VERSION_ID`, `VERSION_TYPE_ID`, `VERSION_STATUS_ID`,
        `NOTES`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,
        `SENT_TO_ARTMIS`, `FORECAST_START_DATE`, `FORECAST_STOP_DATE`, `DAYS_IN_MONTH`, `FREIGHT_PERC`,
        `FORECAST_THRESHOLD_HIGH_PERC`, `FORECAST_THRESHOLD_LOW_PERC`)
    SELECT
        @programId, IFNULL(MAX(pv.VERSION_ID)+1,1), @versionTypeId, @versionStatusId, 
        @notes, @cbUserId, @createdDate, @cbUserId, @createdDate, 
        0, @forecastStartDate, @forecastStopDate, @daysInMonth, @freightPerc, 
        @forecastThresholdHighPerc, @forecastThresholdLowPerc
    FROM rm_program_version pv WHERE pv.`PROGRAM_ID`=@programId;
	SELECT pv.VERSION_ID INTO @versionId FROM rm_program_version pv WHERE pv.`PROGRAM_VERSION_ID`= LAST_INSERT_ID();
	UPDATE rm_program p SET p.CURRENT_VERSION_ID=@versionId WHERE p.PROGRAM_ID=@programId;
	SELECT pv.VERSION_ID, pv.NOTES, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.DAYS_IN_MONTH,
		pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`,
		pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,
		vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, 
		vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` 
	FROM rm_program_version pv 
	LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID
	LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID 
	LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID
	LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID
	LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID
	LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID
	WHERE pv.VERSION_ID=@versionId AND pv.PROGRAM_ID=@programId;
END$$

DELIMITER ;

CREATE TABLE `rm_dataset_planning_unit` (
  `PROGRAM_PLANNING_UNIT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int(10) unsigned NOT NULL,
  `VERSION_ID` int(10) unsigned NOT NULL,
  `PLANNING_UNIT_ID` int(10) unsigned NOT NULL,
  `CONSUMPTION_FORECAST` tinyint(1) unsigned NOT NULL,
  `TREE_FORECAST` tinyint(1) unsigned NOT NULL,
  `STOCK` int(10) DEFAULT NULL,
  `EXISTING_SHIPMENTS` int(10) DEFAULT NULL,
  `MONTHS_OF_STOCK` int(10) unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int(10) unsigned DEFAULT NULL,
  `PRICE` decimal(16,4) unsigned DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`),
  KEY `fk_rm_dataset_planning_unit_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_dataset_planning_unit_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_dataset_planning_unit_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `idx_rm_dataset_planning_unit_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_dataset_planning_unit_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_dataset_planning_unit_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_dataset_planning_unit_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PROCUREMENT_AGENT_ID`, `PRICE`) VALUES (@programId, '1', '4148', '1', '1', '1', '0.03');
INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PRICE`) VALUES (@programId, '1', '4149', '1', '1', '0.045');
INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PROCUREMENT_AGENT_ID`, `PRICE`) VALUES (@programId, '1', '2733', '1', '0', '1', '5.49');

ALTER TABLE `fasp`.`ap_node_type` ADD COLUMN `TREE_TEMPLATE_ALLOWED` TINYINT(1) UNSIGNED NOT NULL AFTER `MODELING_ALLOWED`, ADD COLUMN `FORECAST_TREE_ALLOWED` TINYINT(1) UNSIGNED NOT NULL AFTER `TREE_TEMPLATE_ALLOWED`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_node_type` AS
    SELECT 
        `ut`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
        `ut`.`MODELING_ALLOWED` AS `MODELING_ALLOWED`,
        `ut`.`TREE_TEMPLATE_ALLOWED` AS `TREE_TEMPLATE_ALLOWED`,
        `ut`.`FORECAST_TREE_ALLOWED` AS `FORECAST_TREE_ALLOWED`,
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
        (`ap_node_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

INSERT INTO ap_label VALUES (null, 'Extrapolation', null, null, null, 1, @dt, 1, @dt, 39);
SELECT LAST_INSERT_ID() INTO @labelId;
INSERT INTO ap_node_type VALUES (null, @labelId, 0, 0, 1, 1, 1, @dt, 1, @dt);
INSERT INTO ap_node_type_rule VALUES (null, 1, 6), (null, 6, 3), (null, 6, 4);

ALTER TABLE `fasp`.`rm_forecast_tree_node_data` ADD COLUMN `MANUAL_CHANGES_EFFECT_FUTURE` TINYINT(1) UNSIGNED NOT NULL AFTER `NOTES`;
ALTER TABLE `fasp`.`rm_tree_template_node_data` ADD COLUMN `MANUAL_CHANGES_EFFECT_FUTURE` TINYINT(1) UNSIGNED NOT NULL AFTER `NOTES`;

ALTER TABLE `fasp`.`rm_forecast_consumption_unit` ADD COLUMN `CONSUMPTION_NOTES` TEXT NULL AFTER `OTHER_UNIT_MULTIPLIER_FOR_FU`;

CREATE TABLE `ap_extrapolation_method` (
  `EXTRAPOLATION_METHOD_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`EXTRAPOLATION_METHOD_ID`),
  KEY `fk_ap_extrapolation_method_labelId_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_extrapolation_method_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `fasp`.`ap_label_source` (`SOURCE_ID`, `SOURCE_DESC`) VALUES ('52', 'ap_extrapolation_method');

INSERT INTO ap_label VALUES (null, 'TES Low Confidence', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'TES Holts-Winters', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'TES High Confidence', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'ARIMA', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'Linear Regression', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'Semi-Averages', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);
INSERT INTO ap_label VALUES (null, 'Moving Averages', null, null, null, 1, @dt, 1, @dt, 52);	SELECT LAST_INSERT_ID() INTO @labelId;	INSERT INTO ap_extrapolation_method VALUES (null, @labelId);


CREATE TABLE `rm_forecast_consumption_extrapolation` (
  `CONSUMPTION_EXTRAPOLATION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int(10) unsigned NOT NULL,
  `PLANNING_UNIT_ID` int(10) unsigned NOT NULL,
  `REGION_ID` int(10) unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `TES_LOWER` decimal(16,2) DEFAULT NULL,
  `TES_MIDDLE` decimal(16,2) DEFAULT NULL,
  `TES_UPPER` decimal(16,2) DEFAULT NULL,
  `ARMIA` decimal(16,2) DEFAULT NULL,
  `LINEAR_REGRESSION` decimal(16,2) DEFAULT NULL,
  `SEMI_AVERAGE` decimal(16,2) DEFAULT NULL,
  `MOVING_AVERAGE` decimal(16,2) DEFAULT NULL,
  `VERSION_ID` int(10) unsigned NOT NULL,
  `CREATED_DATE` date NOT NULL,
  `CREATED_BY` int(10) unsigned NOT NULL,
  PRIMARY KEY (`CONSUMPTION_EXTRAPOLATION_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_regionId_idx` (`REGION_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_createdBy_idx` (`CREATED_BY`),
  KEY `idx_rm_forecast_consumption_extrapolation_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_forecast_consumption_extrapolation_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_extrapolation_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_extrapolation_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecast_consumption_extrapolation_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_consumption_extrapolation_list` (
  `CONSUMPTION_EXTRAPOLATION_LIST_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int(10) unsigned NOT NULL,
  `PLANNING_UNIT_ID` int(10) unsigned NOT NULL,
  `REGION_ID` int(10) unsigned NOT NULL,
  `VERSION_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`CONSUMPTION_EXTRAPOLATION_LIST_ID`),
  KEY `fk_rm_fce_list_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_fce_list_set_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_fce_list_set_regionId_idx` (`REGION_ID`),
  KEY `idx_rm_fce_list_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_fce_list_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_fce_list_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_fce_list_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rm_forecast_consumption_extrapolation_settings` (
  `CONSUMPTION_EXTRAPOLATION_SETTINGS_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CONSUMPTION_EXTRAPOLATION_LIST_ID` int(10) unsigned NOT NULL,
  `EXTRAPOLATION_METHOD_ID` int(10) unsigned NOT NULL,
  `JSON_PROPERTIES` VARCHAR(255) NULL,
  PRIMARY KEY (`CONSUMPTION_EXTRAPOLATION_SETTINGS_ID`),
  KEY `fk_rm_fce_settings_consumptionExtrapolationListId` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`),
  CONSTRAINT `fk_rm_fce_settings_consumptionExtrapolationListId` FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_LIST_ID`) REFERENCES `rm_forecast_consumption_extrapolation_list` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `fasp`.`rm_forecast_tree_node_data_mom` (
  `NODE_DATA_MOM_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` INT(10) UNSIGNED NOT NULL,
  `MONTH` DATE NOT NULL,
  `START_VALUE` DECIMAL(16,2) UNSIGNED NOT NULL,
  `END_VALUE` DECIMAL(16,2) UNSIGNED NOT NULL,
  `CALCULATED_VALUE` DECIMAL(16,2) UNSIGNED NOT NULL,
  `DIFFERENCE` DECIMAL(16,2) NOT NULL,
  `SEASONALITY_PERC` DECIMAL(6,2) NOT NULL,
  `MANUAL_CHANGE` DECIMAL(16,2) NOT NULL,
  PRIMARY KEY (`NODE_DATA_MOM_ID`),
  INDEX `fk_rm_forecast_tree_node_data_mom_nodeDataId_idx` (`NODE_DATA_ID` ASC),
  CONSTRAINT `fk_rm_forecast_tree_node_data_mom_nodeDataId`
    FOREIGN KEY (`NODE_DATA_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data` (`NODE_DATA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4149', '70', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4149', '73', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4148', '70', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '2733', '70', '1');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('4', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('4', '6', null);

ALTER TABLE `fasp`.`rm_scenario` ADD COLUMN `NOTES` TEXT NULL AFTER `ACTIVE`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_scenario` AS
    SELECT 
        `s`.`SCENARIO_ID` AS `SCENARIO_ID`,
        `s`.`TREE_ID` AS `TREE_ID`,
        `s`.`LABEL_ID` AS `LABEL_ID`,
        `s`.`CREATED_BY` AS `CREATED_BY`,
        `s`.`CREATED_DATE` AS `CREATED_DATE`,
        `s`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `s`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `s`.`ACTIVE` AS `ACTIVE`,
        `s`.`NOTES` AS `NOTES`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_scenario` `s`
        LEFT JOIN `ap_label` `l` ON ((`s`.`LABEL_ID` = `l`.`LABEL_ID`)));

CREATE TABLE `fasp`.`rm_dataset_planning_unit_selected` (
  `PROGRAM_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `REGION_ID` INT(10) UNSIGNED NOT NULL,
  `SCENARIO_ID` INT(10) UNSIGNED NULL,
  `EXTRAPOLATION_SETTINGS_ID` INT(10) UNSIGNED NULL,
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`));

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` ADD COLUMN `TOTAL_FORECAST` BIGINT(20) UNSIGNED NULL AFTER `EXTRAPOLATION_SETTINGS_ID`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`),
ADD INDEX `fk_rm_dataset_planning_unit_selected_regionId_idx` (`REGION_ID` ASC),
ADD INDEX `fk_rm_dataset_planning_unit_selected_scenarioId_idx` (`SCENARIO_ID` ASC),
ADD INDEX `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId_idx` (`EXTRAPOLATION_SETTINGS_ID` ASC);
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_programPlanningUnitId`
  FOREIGN KEY (`PROGRAM_PLANNING_UNIT_ID`)
  REFERENCES `fasp`.`rm_dataset_planning_unit` (`PROGRAM_PLANNING_UNIT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_regionId`
  FOREIGN KEY (`REGION_ID`)
  REFERENCES `fasp`.`rm_region` (`REGION_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_scenarioId`
  FOREIGN KEY (`SCENARIO_ID`)
  REFERENCES `fasp`.`rm_scenario` (`SCENARIO_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId`
  FOREIGN KEY (`EXTRAPOLATION_SETTINGS_ID`)
  REFERENCES `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_SETTINGS_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `SCENARIO_ID`) VALUES ('1', '70', '1');
INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `SCENARIO_ID`) VALUES ('2', '70', '2');
INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `EXTRAPOLATION_SETTINGS_ID`) VALUES ('3', '70', '19');


ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD COLUMN `CONSUMPTION_DATA_TYPE_ID` INT(10) UNSIGNED NULL COMMENT 'null=Not a Consumption Unit, 1=Forecast, 2=PlanningUnit, 3=Other Unit' AFTER `PRICE`,
ADD COLUMN `OTHER_LABEL_ID` INT(10) UNSIGNED NULL AFTER `CONSUMPTION_DATA_TYPE_ID`,
ADD COLUMN `OTHER_MULTIPLIER` DECIMAL(16,4) UNSIGNED NULL AFTER `OTHER_LABEL_ID`,
ADD COLUMN `CONSUMPTION_NOTES` TEXT NULL AFTER `OTHER_MULTIPLIER`;


ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD INDEX `fk_rm_dataset_planning_unit_otherLabelId_idx` (`OTHER_LABEL_ID` ASC);
ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD CONSTRAINT `fk_rm_dataset_planning_unit_otherLabelId`
  FOREIGN KEY (`OTHER_LABEL_ID`)
  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD INDEX `idx_rm_dataset_planning_unit_consumptionDataTypeId` (`CONSUMPTION_DATA_TYPE_ID` ASC);

ALTER TABLE `fasp`.`rm_forecast_consumption` 
DROP FOREIGN KEY `fk_rm_forecast_consumption_consumptionUnitId`;
ALTER TABLE `fasp`.`rm_forecast_consumption` 
CHANGE COLUMN `CONSUMPTION_UNIT_ID` `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL ,
ADD INDEX `fk_rm_forecast_consumption_planningUnitId_idx` (`PLANNING_UNIT_ID` ASC),
DROP INDEX `fk_rm_forecast_consumption_consumptionUnitId_idx` ;
ALTER TABLE `fasp`.`rm_forecast_consumption` 
ADD CONSTRAINT `fk_rm_forecast_consumption_planningUnitId`
  FOREIGN KEY (`PLANNING_UNIT_ID`)
  REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- MySQL Workbench Synchronization
-- Generated: 2021-12-10 01:23
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Akil Mahimwala

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER SCHEMA `fasp`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
DROP FOREIGN KEY `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId`;

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation` 
DROP FOREIGN KEY `fk_rm_forecast_consumption_extrapolation_regionId`,
DROP FOREIGN KEY `fk_rm_forecast_consumption_extrapolation_programId`,
DROP FOREIGN KEY `fk_rm_forecast_consumption_extrapolation_planningUnitId`,
DROP FOREIGN KEY `fk_rm_forecast_consumption_extrapolation_createdBy`;

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_settings` 
DROP FOREIGN KEY `fk_rm_fce_settings_consumptionExtrapolationListId`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
DROP COLUMN `CONSUMPTION_NOTES`,
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `OTHER_MULTIPLIER`,
ADD COLUMN `CREATED_DATE` DATETIME NULL DEFAULT NULL AFTER `CREATED_BY`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
CHANGE COLUMN `EXTRAPOLATION_SETTINGS_ID` `CONSUMPTION_EXTRAPOLATION_ID` INT(10) UNSIGNED NULL DEFAULT NULL ,
ADD INDEX `fk_rm_dataset_planning_unit_selected_extrapolationSettingsI_idx` (`CONSUMPTION_EXTRAPOLATION_ID` ASC) ,
DROP INDEX `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId_idx` ;

ALTER TABLE `fasp`.`rm_forecast_consumption` 
CHANGE COLUMN `CONSUMPTION_ID` `ACTUAL_CONSUMPTION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `CREATED_DATE` `CREATED_DATE` DATETIME NULL DEFAULT NULL , RENAME TO  `fasp`.`rm_forecast_actual_consumption` ;

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation` 
DROP COLUMN `CREATED_BY`,
DROP COLUMN `CREATED_DATE`,
DROP COLUMN `VERSION_ID`,
DROP COLUMN `MOVING_AVERAGE`,
DROP COLUMN `SEMI_AVERAGE`,
DROP COLUMN `LINEAR_REGRESSION`,
DROP COLUMN `ARMIA`,
DROP COLUMN `TES_UPPER`,
DROP COLUMN `TES_MIDDLE`,
DROP COLUMN `TES_LOWER`,
DROP COLUMN `MONTH`,
CHANGE COLUMN `CONSUMPTION_EXTRAPOLATION_ID` `CONSUMPTION_EXTRAPOLATION_DATA_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `PROGRAM_ID` `CONSUMPTION_EXTRAPOLATION_ID` INT(10) UNSIGNED NOT NULL ,
CHANGE COLUMN `PLANNING_UNIT_ID` `MONTH` INT(10) UNSIGNED NOT NULL COMMENT '	' ,
CHANGE COLUMN `REGION_ID` `AMT` DECIMAL(16,2) UNSIGNED NOT NULL ,
ADD INDEX `fk_rm_forecast_consumption_extrapolation_data_consumptionEx_idx` (`CONSUMPTION_EXTRAPOLATION_ID` ASC) ,
DROP INDEX `idx_rm_forecast_consumption_extrapolation_versionId` ,
DROP INDEX `fk_rm_forecast_consumption_extrapolation_createdBy_idx` ,
DROP INDEX `fk_rm_forecast_consumption_extrapolation_regionId_idx` ,
DROP INDEX `fk_rm_forecast_consumption_extrapolation_planningUnitId_idx` ,
DROP INDEX `fk_rm_forecast_consumption_extrapolation_programId_idx` 
, RENAME TO  `fasp`.`rm_forecast_consumption_extrapolation_data` ;

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_list` 
ADD COLUMN `EXTRAPOLATION_METHOD_ID` INT(10) UNSIGNED NOT NULL AFTER `REGION_ID`,
ADD COLUMN `JSON_PROPERTIES` VARCHAR(255) NULL AFTER `VERSION_ID`,
ADD COLUMN `CONSUMPTION_NOTES` TEXT NULL DEFAULT NULL AFTER `JSON_PROPERTIES`,
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `CONSUMPTION_NOTES`,
ADD COLUMN `CREATED_DATE` DATETIME NULL DEFAULT NULL AFTER `CREATED_BY`,
CHANGE COLUMN `CONSUMPTION_EXTRAPOLATION_LIST_ID` `CONSUMPTION_EXTRAPOLATION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
ADD INDEX `fk_rm_forecast_consumption_extrapolation_extrapolationMetho_idx` (`EXTRAPOLATION_METHOD_ID` ASC) 
, RENAME TO  `fasp`.`rm_forecast_consumption_extrapolation` ;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId`
  FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_ID`)
  REFERENCES `fasp`.`rm_forecast_consumption_extrapolation` (`CONSUMPTION_EXTRAPOLATION_ID`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

UPDATE fasp.rm_dataset_planning_unit dpu LEFT JOIN rm_forecast_consumption_unit fcu ON dpu.PROGRAM_ID=fcu.PROGRAM_ID AND dpu.PLANNING_UNIT_ID=fcu.PLANNING_UNIT_ID 
SET 
dpu.CONSUMPTION_DATA_TYPE_ID=fcu.DATA_TYPE,
dpu.OTHER_LABEL_ID=fcu.OTHER_UNIT_LABEL_ID,
dpu.OTHER_MULTIPLIER=fcu.OTHER_UNIT_MULTIPLIER_FOR_FU,
dpu.CREATED_BY=COALESCE(fcu.CREATED_BY,1),
dpu.CREATED_DATE=COALESCE(fcu.CREATED_DATE,now());

TRUNCATE TABLE rm_forecast_consumption_extrapolation;
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '2733', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '2733', '70', '1', '6', null, now(), 1);

DROP TABLE rm_forecast_consumption_extrapolation_settings;
DROP TABLE rm_forecast_consumption_unit;
DROP VIEW `fasp`.`vw_forecast_consumption_unit`;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

ALTER TABLE `fasp`.`rm_forecast_actual_consumption` CHANGE COLUMN `ACTUAL_CONSUMPTION` `AMOUNT` DECIMAL(16,4) UNSIGNED NULL DEFAULT NULL ;


USE `fasp`;
CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_extrapolation_method` AS
    SELECT 
        `em`.`EXTRAPOLATION_METHOD_ID` AS `EXTRAPOLATION_METHOD_ID`,
        `em`.`LABEL_ID` AS `LABEL_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`ap_extrapolation_method` `em`
        LEFT JOIN `ap_label` `l` ON ((`em`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` CHANGE COLUMN `AMT` `AMOUNT` DECIMAL(16,2) UNSIGNED NOT NULL ;

UPDATE `fasp`.`rm_dataset_planning_unit` SET `CONSUMPTION_DATA_TYPE_ID` = '1' WHERE (`PROGRAM_PLANNING_UNIT_ID` = '2');

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation` 
ADD INDEX `fk_rm_fce_list_createdBy_idx` (`CREATED_BY` ASC) ;
;
ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation` 
ADD CONSTRAINT `fk_rm_fce_list_extrapolationMethodId`
  FOREIGN KEY (`EXTRAPOLATION_METHOD_ID`)
  REFERENCES `fasp`.`ap_extrapolation_method` (`EXTRAPOLATION_METHOD_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_fce_list_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` 
ADD CONSTRAINT `fk_rm_fced_consumptionExtrapolationDataId`
  FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_ID`)
  REFERENCES `fasp`.`rm_forecast_consumption_extrapolation` (`CONSUMPTION_EXTRAPOLATION_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD INDEX `fk_rm_dataset_planning_unit_createdBy_idx` (`CREATED_BY` ASC) ;
;
ALTER TABLE `fasp`.`rm_dataset_planning_unit` 
ADD CONSTRAINT `fk_rm_dataset_planning_unit_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
DROP FOREIGN KEY `fk_rm_dataset_planning_unit_selected_extrapolationSettingsId`;
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` 
ADD CONSTRAINT `fk_rm_dataset_planning_unit_selected_consumptionExtrapolationId`
  FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_ID`)
  REFERENCES `fasp`.`rm_forecast_consumption_extrapolation` (`CONSUMPTION_EXTRAPOLATION_ID`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;


ALTER TABLE `fasp`.`rm_dataset_planning_unit` ADD COLUMN `CONSUMPTION_NOTES` TEXT NULL DEFAULT NULL AFTER `PRICE`;

ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation` DROP COLUMN `CONSUMPTION_NOTES`;

ALTER TABLE `fasp`.`rm_forecast_tree_node` DROP COLUMN `MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree_node` AS
    SELECT 
        `tn`.`NODE_ID` AS `NODE_ID`,
        `tn`.`TREE_ID` AS `TREE_ID`,
        `tn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `tn`.`SORT_ORDER` AS `SORT_ORDER`,
        `tn`.`LEVEL_NO` AS `LEVEL_NO`,
        `tn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `tn`.`UNIT_ID` AS `UNIT_ID`,
        `tn`.`LABEL_ID` AS `LABEL_ID`,
        `tn`.`CREATED_BY` AS `CREATED_BY`,
        `tn`.`CREATED_DATE` AS `CREATED_DATE`,
        `tn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_tree_node` `tn`
        LEFT JOIN `ap_label` `l` ON ((`tn`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`ct_supply_plan_commit_request` ADD COLUMN `JSON` LONGTEXT NULL AFTER `FAILED_REASON`;

ALTER TABLE `fasp`.`ct_supply_plan_commit_request` RENAME TO  `fasp`.`ct_commit_request` ;


USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getVersionId`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getVersionId`(PROGRAM_ID INT(10), VERSION_TYPE_ID INT(10), VERSION_STATUS_ID INT(10), NOTES TEXT, FORECAST_START_DATE DATETIME, FORECAST_STOP_DATE DATETIME, DAYS_IN_MONTH INT(10), FREIGHT_PERC DECIMAL, FORECAST_THRESHOLD_HIGH_PERC DECIMAL, FORECAST_THRESHOLD_LOW_PERC DECIMAL, CREATED_BY INT(10), CREATED_DATE DATETIME)
BEGIN
	SET @programId = PROGRAM_ID;
	SET @cbUserId = CREATED_BY;
	SET @createdDate = CREATED_DATE;
	SET @versionTypeId = VERSION_TYPE_ID;
	SET @versionStatusId = VERSION_STATUS_ID;
        SET @forecastStartDate = FORECAST_START_DATE;
        SET @forecastStopDate = FORECAST_STOP_DATE;
        SET @daysInMonth = DAYS_IN_MONTH;
        SET @freightPerc = FREIGHT_PERC;
        SET @forecastThresholdHighPerc = FORECAST_THRESHOLD_HIGH_PERC;
        SET @forecastThresholdLowPerc = FORECAST_THRESHOLD_LOW_PERC;
	SET @notes = NOTES;
        INSERT INTO `fasp`.`rm_program_version`
            (`PROGRAM_ID`, `VERSION_ID`, `VERSION_TYPE_ID`, `VERSION_STATUS_ID`,
            `NOTES`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,
            `SENT_TO_ARTMIS`, `FORECAST_START_DATE`, `FORECAST_STOP_DATE`, `DAYS_IN_MONTH`, `FREIGHT_PERC`,
            `FORECAST_THRESHOLD_HIGH_PERC`, `FORECAST_THRESHOLD_LOW_PERC`)
        SELECT
            @programId, IFNULL(MAX(pv.VERSION_ID)+1,1), @versionTypeId, @versionStatusId, 
            @notes, @cbUserId, @createdDate, @cbUserId, @createdDate, 
            0, @forecastStartDate, @forecastStopDate, @daysInMonth, @freightPerc, 
            @forecastThresholdHighPerc, @forecastThresholdLowPerc
        FROM rm_program_version pv WHERE pv.`PROGRAM_ID`=@programId;
            SELECT pv.VERSION_ID INTO @versionId FROM rm_program_version pv WHERE pv.`PROGRAM_VERSION_ID`= LAST_INSERT_ID();
            UPDATE rm_program p SET p.CURRENT_VERSION_ID=@versionId WHERE p.PROGRAM_ID=@programId;
            SELECT pv.VERSION_ID, pv.NOTES, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.DAYS_IN_MONTH, pv.FREIGHT_PERC, pv.FORECAST_THRESHOLD_HIGH_PERC, pv.FORECAST_THRESHOLD_LOW_PERC,
                    pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`,
                    pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,
                    vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, 
                    vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` 
            FROM rm_program_version pv 
            LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID
            LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID 
            LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID
            LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID
            LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID
            LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID
            WHERE pv.VERSION_ID=@versionId AND pv.PROGRAM_ID=@programId;
END$$

DELIMITER ;

UPDATE `fasp`.`ap_label_source` SET `SOURCE_DESC` = 'rm_dataset_planning_unit' WHERE (`SOURCE_ID` = '51');


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
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    GROUP BY `p`.`PROGRAM_ID`;

-- Tree labels
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.+AddTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'+ Add Tree');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'+ Ajouter un arbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'+ Agregar árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'+ Adicionar Árvore');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.blank','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(blank)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Vide)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(blanca)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(em branco)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.node','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validation.fieldRequired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This is required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ceci est nécessaire');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esto es requerido');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Isso é obrigatório');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validation.selectForecastMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select forecast method');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner la méthode de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el método de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o método de previsão');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validation.selectTreeName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter tree name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nom de larbre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor ingrese el nombre del árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor insira o nome da árvore');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.%of','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% of');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% de');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% de');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% de');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthStart','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Month Start)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Début du mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Inicio del mes)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Início do mês)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.calculatedChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated Change (+/- %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation calculée (+/- %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio calculado (+/-%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança calculada (+/-%)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.manualChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manual Change (+/- %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modification manuelle (+/- %)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio manual (+/-%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança manual (+/-%)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.MonthEnd','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Month End)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(La fin du mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Fin de mes)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Fim do mês)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthStartNoSeasonality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Month Start (no seasonality)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Début du mois (pas de saisonnalité)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Inicio del mes (sin estacionalidad)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Início do mês (sem sazonalidade)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.calculatedChange+-','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated change (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation calculée (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio calculado (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração calculada (+/-)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthlyEndNoSeasonality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly End (no seasonality)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fin du mois (pas de saisonnalité)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fin de mes (sin estacionalidad)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fim do mês (sem sazonalidade)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.seasonalityIndex','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Seasonality Index');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Indice de saisonnalité');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Índice de estacionalidad');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Índice de Sazonalidade');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.manualChange+-','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manual Change (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement manuel (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio manual (+/-)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança manual (+/-)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validation.pleaseEnterValidDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter valid date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez entrer une date valide');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor ingrese una fecha válida');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor insira uma data válida');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.transferToNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Transfer To Node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Transfert vers le nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Transferir al nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Transferir para o nó');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.Note','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Noter');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de modélisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de modelado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de modelagem');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthlyChange%','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly Change (%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation mensuelle (%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio mensual (%)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança Mensal (%)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.MonthlyChange#','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly Change (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation mensuelle (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio mensual (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança Mensal (#)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculater','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculater');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculatrice de modélisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculadora de modelado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculadora de modelagem');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.calculatedChangeForMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated change for month');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation calculée pour le mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio calculado por mes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração calculada para o mês');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.usageTemplate.requires','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(s) requires');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(s) nécessite');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(s) requiere');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(s) requer');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.timesPer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'times per');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'fois par');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'veces por');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'vezes por');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.for','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'for');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pour');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'por');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'por');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.forEach','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversion');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.weNeed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'we need');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'nous avons besoin');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nosotras necesitamos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'nós precisamos');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.parent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Parente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Madre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mãe');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeTitle','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Title');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Titre du nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Título de nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Título do Nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.lagMessage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lag is the delay between the parent node date and the user consumption the product. This is often for phased treatement.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le décalage est le délai entre la date du nœud parent et la consommation du produit par lutilisateur. Cest souvent pour un traitement échelonné.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lag es la demora entre la fecha del nodo principal y el consumo del producto por parte del usuario. A menudo, esto es para tratamiento por fases.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lag é o atraso entre a data do nó pai e o consumo do produto pelo usuário. Isso geralmente é para tratamento em fases.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de Nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.percentageOfParent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage of Parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de parents');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de padres');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem do pai');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.parentValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Parent Value');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur parente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de los padres');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor Pai');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Value');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do Nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.conversion.ConversionFactorFUPU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor (FU:PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (FU:PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (FU: PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (FU: PU)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.QATEstimateForIntervalEvery_months','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT estimate for interval (Every _ months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Estimation QAT pour lintervalle (tous les _ mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estimación de QAT para el intervalo (cada _ meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estimativa QAT para intervalo (a cada _ meses)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.consumptionIntervalEveryXMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption interval (Every X months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de consommation (Tous les X mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Intervalo de consumo (cada X meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de consumo (a cada X meses)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.willClientsShareOnePU?','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Will Clients share one PU?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les clients partageront-ils une seule unité centrale ?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Las clientas compartirán una PU?');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os clientes compartilharão um PU?');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.copyFromTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Copy from Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Copier à partir du modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Copiar de plantilla');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Copiar do modelo');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.lagInMonth0Immediate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lag in months (0=immediate)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Décalage en mois (0=immédiat)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Retraso en meses (0 = inmediato)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atraso em meses (0 = imediato)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.requires','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'requires');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'a besoin');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'requiere');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'requer');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.singleUse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Single Use');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Usage unique');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'De un solo uso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uso único');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.#OfFURequiredForPeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of FU required for period');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# dUF requis pour la période');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de FU requerida para el período');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# de FU necessário para o período');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.#OfMonthsInPeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Of Months In Period');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois dans la période');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# De meses en el período');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de meses no período');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.#OfFU/month/','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of FU / month /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# Of FU / mois /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# Of FU / mes /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# Of FU / mês /');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.#OfFU/','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of FU /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# dUF /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de FU /');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# de FU /');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.viewMonthlyData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View monthly data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les données mensuelles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver datos mensuales');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ver dados mensais');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculaterTool:','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculater Tool:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Outil de calcul de modélisation :');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Herramienta de calculadora de modelado:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ferramenta Calculadora de Modelagem:');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.targetDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Target Date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha objetivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data Alvo');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.startValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Value');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur de départ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor inicial');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor inicial');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.StartPercentage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Percentage');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de départ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de inicio');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem inicial');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.targetEnding','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Target Ending');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fin cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Finalización del objetivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Finalidade Alvo');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.or','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'or');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ou');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'o');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'ou');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.Change(#)','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changer (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio (#)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudar (#)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.CalculatedMonth-on-MonthChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated Month-on-Month change');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation mensuelle calculée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio mes a mes calculado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança calculada mês a mês');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthlyData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly Data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données mensuelles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos mensuales');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados Mensais');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.tableDisplays','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Table displays');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tableaux daffichage');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pantallas de mesa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Telas de mesa');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.forNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'for Node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pour le nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'para nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'para Nó');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.asA%OfParent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'as a % of parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'en % des parents');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Como% de madre');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'como um% dos pais');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.pleaseSaveAndDoARecalculateAfterDragAndDrop.','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please save and do a recalculate after drag and drop.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez enregistrer et recalculer après un glisser-déposer.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Guarde y vuelva a calcular después de arrastrar y soltar.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Salve e faça um recálculo após arrastar e soltar.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.calculated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculate');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calcular');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calcular');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.Add/EditTreeData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/Edit Tree Data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/Modifier les données de larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar / editar datos de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar / Editar Dados da Árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.scenarioName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Scenario Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du scénario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del escenario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do Cenário');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.Add/EditNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/Edit Node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/Modifier un nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar / editar nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar / Editar Nó');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.Modeling/Transfer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling/Transfer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modélisation/Transfert');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Modelado / Transferencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelagem / Transferência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données de nœud');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos de nodo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados do Nó');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.exportWordDoc','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Export to word doc');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exporter vers word doc');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Exportar a documento de Word');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exportar para Word Doc');
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` ADD COLUMN `NOTES` TEXT NULL AFTER `TOTAL_FORECAST`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit` ADD COLUMN `HIGHER_THEN_CONSUMPTION_THRESHOLD` DECIMAL(14,2) NULL AFTER `PRICE`, ADD COLUMN `LOWER_THEN_CONSUMPTION_THRESHOLD` DECIMAL(14,2) NULL AFTER `HIGHER_THEN_CONSUMPTION_THRESHOLD`;

UPDATE `fasp`.`ap_label_source` SET `SOURCE_DESC` = 'rm_tree_template' WHERE (`SOURCE_ID` = '45');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_DESC` = 'rm_tree_template_node' WHERE (`SOURCE_ID` = '46');

insert into ap_label select null, LABEL_EN, LABEL_FR, LABEL_SP, LABEL_PR, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, 46 FROM vw_forecast_tree_node ftn where ftn.TREE_ID=2;
SELECT last_insert_id() into @labelId;
select min(LABEL_ID) into @oldLabelId from rm_forecast_tree_node ftn where ftn.TREE_ID=2;
ALTER TABLE `fasp`.`rm_tree_template_node` DROP COLUMN `MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS`;
USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template_node` AS
    SELECT 
        `ttn`.`NODE_ID` AS `NODE_ID`,
        `ttn`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `ttn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `ttn`.`SORT_ORDER` AS `SORT_ORDER`,
        `ttn`.`LEVEL_NO` AS `LEVEL_NO`,
        `ttn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ttn`.`UNIT_ID` AS `UNIT_ID`,
        `ttn`.`LABEL_ID` AS `LABEL_ID`,
        `ttn`.`CREATED_BY` AS `CREATED_BY`,
        `ttn`.`CREATED_DATE` AS `CREATED_DATE`,
        `ttn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ttn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ttn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template_node` `ttn`
        LEFT JOIN `ap_label` `l` ON ((`ttn`.`LABEL_ID` = `l`.`LABEL_ID`)))
    ORDER BY `ttn`.`TREE_TEMPLATE_ID` , `ttn`.`SORT_ORDER`;
insert into rm_tree_template_node SELECT ftn.NODE_ID, 2, ftn.PARENT_NODE_ID, ftn.SORT_ORDER, ftn.LEVEL_NO, ftn.NODE_TYPE_ID, ftn.UNIT_ID, ftn.LABEL_ID+@labelId-@oldLabelId, ftn.CREATED_BY, ftn.CREATED_DATE, ftn.LAST_MODIFIED_BY, ftn.LAST_MODIFIED_DATE, ftn.ACTIVE from rm_forecast_tree_node ftn where ftn.TREE_ID=2;
INSERT INTO rm_tree_template_node_data SELECT NODE_DATA_ID, NODE_ID, 0, DATA_VALUE, NODE_DATA_FU_ID, NODE_DATA_PU_ID, NOTES, MANUAL_CHANGES_EFFECT_FUTURE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, ACTIVE FROM rm_forecast_tree_node_data ftnd where ftnd.NODE_ID between 16 and 21 AND ftnd.SCENARIO_ID=3;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.main','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Main');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Principale');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Principal');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Principal');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.modeling','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La modélisation');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Modelado');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelagem');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.serverProcessing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Server Processing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Traitement du serveur');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Procesamiento del servidor');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Processamento de servidor');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.draftVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Draft Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Brouillon');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión preliminar');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão preliminar');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.finalVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Final Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version finale');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión final');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão final');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.forecastValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Validation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Validation des prévisions');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Validación de pronóstico');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Validação de previsão');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.noForecastSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No forecast selected');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune prévision sélectionnée');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se seleccionó ninguna previsión');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma previsão selecionada');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.forecastSummary','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Summary');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Résumé des prévisions');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Resumen de previsión');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Resumo da previsão');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.compare&Select','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare & Select');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer et sélectionner');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar y seleccionar');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Comparar e selecionar');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.consumptionForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision de consommation');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Previsión de consumo');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão de Consumo');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.dataEntry&Adjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Entry & Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie et ajustement des données');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Entrada y ajuste de datos');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada e ajuste de dados');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.extrapolation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapolation');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolación');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolação');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.monthsMissingActualConsumptionValues','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months missing actual consumption values (gap)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois manquant des valeurs de consommation réelles (écart)');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses sin valores de consumo reales (brecha)');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses sem valores reais de consumo (intervalo)');-- portugis


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.treeForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Forecast(s)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Arbre prévision(s)');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico del árbol (s)');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão (ões) da árvore');-- portugis


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.branchesMissingPlanningUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Branches Missing Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de planification des succursales manquantes');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de planificación de sucursales faltantes');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Filiais ausentes unidade de planejamento');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.NodesWithChildrenThatDoNotAddUpTo100Prcnt','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Nodes with children that don’t add up to 100%');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœuds avec des enfants dont la somme ne correspond pas à 100 %');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodos con niños que no suman hasta el 100%');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nós com filhos que não somam 100%');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.puThatDoNotHaveAtleast24MonthsOfActualConsumptionValues','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning units that do not have at least 24 months of actual consumption values');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification qui n`ont pas au moins 24 mois de valeurs de consommation réelles');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de planificación que no tienen al menos 24 meses de valores de consumo reales');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de planejamento que não têm pelo menos 24 meses de valores reais de consumo');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.puThatDoesNotAppearOnAnyTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning unit that does not appear on any tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de planification qui n`apparaît sur aucun arbre');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de planificación que no aparece en ningún árbol');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de planejamento que não aparece em nenhuma árvore');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.treeScenarios','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Scenarios');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Scénarios d`arbre');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Escenarios de árboles');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cenários de árvores');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.commitTree.treeNodes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Nodes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœuds d`arborescence');-- french
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodos de árboles');-- spanish
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nós de árvore');-- portugis

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.message.invalidStringLength','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version Notes should not exceed 1000 characters');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les notes de version ne doivent pas dépasser 1000 caractères');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las notas de la versión no deben exceder los 1000 caracteres');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'As notas da versão não devem exceder 1000 caracteres');



INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Compare and Select Forecast',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_COMPARE_AND_SELECT',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.compareAndSelect','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare and Select Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer et sélectionner les prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar y seleccionar pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Compare e selecione a previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.actuals','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actuals (Adjusted)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données réelles (ajustées)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos reales (ajustados)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Reais (ajustados)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.dataSaved','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data saved successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données enregistrées avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos guardados exitosamente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados salvos com sucesso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.backTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Back to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Retour à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'De regreso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'De volta a');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.consExtrapolation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Cons Extrapolation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Inconvénients extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolación de contras');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolação de contras');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.continueTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Continue to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Continuer à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Continuar a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Continua a');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.monthlyForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévisions mensuelles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico mensual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão Mensal');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.forecastPeriod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Periodo de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Período de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.missingData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Missing Data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données manquantes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos perdidos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados ausentes');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.selectOne','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select one forecast for');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une prévision pour');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una previsión para');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma previsão para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.andRegion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'and Region');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'et Région');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'y Región');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'e região');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.display?','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Affichage?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Monitor?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibição?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.typeTitle','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'C indicates a consumption forecast, T indicates a Tree Forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'C indique une prévision de consommation, T indique une prévision d`arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'C indica un pronóstico de consumo, T indica un pronóstico de árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'C indica uma previsão de consumo, T indica uma Previsão de Árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.selectAsForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select as forecast?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionner comme prévision ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Seleccionar como pronóstico?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione como previsão?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.forForecastPeriod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour la période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o período de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.totalForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision totale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico total');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão Total');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.forecastError','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur de prévision (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de pronóstico (%)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de previsão (%)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.forecastErrorMonths','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error (# Months Used)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur de prévision (nombre de mois utilisés)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de pronóstico (# meses usados)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de previsão (# meses usados)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.compareToConsumptionForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare to Consumption Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer aux prévisions de consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar con el pronóstico de consumo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Compare com a previsão de consumo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.cons','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Cons');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les inconvénients');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Contras');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Contras');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.belowLowestConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% below the lowest consumption forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% inférieur à la prévision de consommation la plus basse.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% por debajo de la previsión de consumo más bajo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% abaixo da previsão de menor consumo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.aboveHighestConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% above the highest consumption forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% au-dessus de la prévision de consommation la plus élevée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% por encima de la previsión de mayor consumo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% acima da previsão de maior consumo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.yAxisIn','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Y-axis in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'axe Y dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eje Y en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Eixo Y em');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.showOnlyForecastPeriod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show only Forecast Period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher uniquement la période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar solo el período de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar apenas o período de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.startMonthForGraph','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Month for Graph/Table');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de début pour le graphique/tableau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mes de inicio para gráfico / tabla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mês inicial para gráfico / tabela');-- pr


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Product Validation',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_PRODUCT_VALIDATION',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.level','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.text','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Text');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Texte');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Texto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Texto');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.productValidation.cost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.productValidation.subTotal','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'SubTotal');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Total');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Total parcial');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Subtotal');-- pr

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Modeling Validation',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_MODELING_VALIDATION',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingValidation.levelUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Nível');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.node','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingValidation.displayBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibir por');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingValidation.number','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.modelingValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Validations');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Validation de la modélisation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Validación de modelado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Validação de modelagem');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.productValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Product Validations');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Validation du produit');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Validación de producto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Validação de Produto');-- pr

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Compare Version',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_COMPARE_VERSION',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.compareVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer les versions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Comparar versão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareVersion.compareWithVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare With Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer avec la version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar con la versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Comparar com a versão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareVersion.selectedForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Selected Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision sélectionnée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão Selecionada');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareVersion.forecastQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité prévue');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cant. De previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Qtd prevista');-- pr

ALTER TABLE `fasp`.`ap_node_type` ADD COLUMN `EXTRAPOLATION_ALLOWED` TINYINT UNSIGNED NOT NULL AFTER `MODELING_ALLOWED`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_node_type` AS
    SELECT 
        `ut`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ut`.`LABEL_ID` AS `LABEL_ID`,
        `ut`.`MODELING_ALLOWED` AS `MODELING_ALLOWED`,
        `ut`.`EXTRAPOLATION_ALLOWED` AS `EXTRAPOLATION_ALLOWED`,
        `ut`.`TREE_TEMPLATE_ALLOWED` AS `TREE_TEMPLATE_ALLOWED`,
        `ut`.`FORECAST_TREE_ALLOWED` AS `FORECAST_TREE_ALLOWED`,
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
        (`ap_node_type` `ut`
        LEFT JOIN `ap_label` `l` ON ((`ut`.`LABEL_ID` = `l`.`LABEL_ID`)));

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.addTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Template Added Successfully');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle darborescence ajouté avec succès');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de árbol agregada con éxito');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de árvore adicionado com sucesso');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.editTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Template Updated Successfully');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle darborescence mis à jour avec succès');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de árbol actualizada correctamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de árvore atualizado com sucesso');

update rm_forecast_actual_consumption a 
set a.PLANNING_UNIT_ID = '4148'
where a.PLANNING_UNIT_ID =1;

UPDATE rm_forecast_actual_consumption a 
SET a.PLANNING_UNIT_ID = '4149'
WHERE a.PLANNING_UNIT_ID =2;

UPDATE rm_forecast_actual_consumption a 
SET a.PLANNING_UNIT_ID = '2733'
WHERE a.PLANNING_UNIT_ID =3;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Extrapolation',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EXTRAPOLATION',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.extrapolation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolação');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.adjustedActuals','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjusted Actuals');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réels ajustés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos reales ajustados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Reais ajustados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.movingAverages','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Moving Averages');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Moyennes mobiles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Promedios móviles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Médias móveis');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.semiAverages','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Semi-Averages');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demi-moyennes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Semi-Promedios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Semi-médias');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.linearRegression','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linear Regression');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Régression linéaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Regresión lineal');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Regressão linear');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.tesLower','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'TES (Lower Confidence Bound)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'TES (limite de confiance inférieure)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TES (Límite de Confianza Inferior)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TES (Limite de Confiança Inferior)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.tes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'TES');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'TES');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TES');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TES');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.tesUpper','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'TES (Upper Confidence Bound)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'TES (limite de confiance supérieure)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TES (Límite superior de confianza)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TES (Limite de Confiança Superior)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.arima','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ARIMA');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ARIMA');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'ARIMA');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'ARIMA');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.dataEntryAndAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Entry and Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie et ajustement de la consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingreso y Ajuste de Consumos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada e Ajuste de Consumo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.showGuidance','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Guidance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les conseils');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar guía');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar orientação');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.dateRangeForHistoricData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select date range for historical data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une plage de dates pour les données historiques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar rango de fechas para datos históricos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o intervalo de datas para dados históricos');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.selectExtrapolationMethod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select the Extrapolation methods to be used');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez les méthodes d`extrapolation à utiliser');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione los métodos de extrapolación que se utilizarán');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione os métodos de extrapolação a serem usados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.noOfMonths','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of Months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de meses');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.tripleExponential','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Triple-Exponential Smoothing (TES, Holt-Winters)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lissage triple exponentiel (TES, Holt-Winters)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suavizado triple exponencial (TES, Holt-Winters)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suavização Exponencial Tripla (TES, Holt-Winters)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.confidenceLevel','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Confidence level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un niveau de confiance');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel de confianza');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível de confiança');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.seasonality','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Seasonality');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisonnalité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'estacionalidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sazonalidade');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.alpha','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Alpha');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alpha');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alfa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alfa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.beta','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Beta');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Bêta');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Beta');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Beta');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.gamma','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Gamma');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gamma');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Gama');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gama');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.arimaFull','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Autoregressive Integrated Moving Average (ARIMA)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Moyenne mobile intégrée autorégressive (ARIMA)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Media móvil integrada autorregresiva (ARIMA)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Média Móvel Integrada Autoregressiva (ARIMA)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.p','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'p');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'p');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pags');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'p');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.d','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'d');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ré');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'D');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'d');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.q','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'q');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'q');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'q');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'q');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.errors','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Errors');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'les erreurs');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'errores');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erros');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.rmse','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'RMSE');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'RMSE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'RMSE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'RMSE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.mape','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MAPE');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'CARTE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MAPA');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MAPE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.mse','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MSE');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MSE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MSE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MSE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.wape','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'WAPE');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'WAPE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'WAPE');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'WAPE');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.rSquare','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'R^2');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'R^2');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'R^2');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'R^2');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.dataCheck','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Check');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vérification des données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comprobación de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Verificação de dados');-- pr


UPDATE ap_node_type nt set TREE_TEMPLATE_ALLOWED=1 where nt.NODE_TYPE_ID<6;
UPDATE ap_node_type nt set FORECAST_TREE_ALLOWED=1 where nt.NODE_TYPE_ID<6;
UPDATE ap_node_type nt set EXTRAPOLATION_ALLOWED=1 where nt.NODE_TYPE_ID=6;

INSERT INTO fasp.ap_label(LABEL_ID,LABEL_EN,LABEL_FR,LABEL_SP,LABEL_PR,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE,SOURCE_ID) VALUES ( NULL,'Commit Dataset',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');

SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;

INSERT INTO fasp.us_business_function(BUSINESS_FUNCTION_ID,LABEL_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( 'ROLE_BF_COMMIT_DATASET',@MAX,'1',NOW(),'1',NOW());


INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.for','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.and','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'and');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'et');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'y');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'e');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrpolate.selectYourExtrapolationParameters','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Region, select your extrapolation parameters:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Région, sélectionnez vos paramètres d`extrapolation :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Región, seleccione sus parámetros de extrapolación:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Região, selecione seus parâmetros de extrapolação:');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.lowestError','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lowest Error');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur la plus faible');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error más bajo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro mais baixo');-- pr

ALTER TABLE `fasp`.`ap_extrapolation_method` 
ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `LABEL_ID`,
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NOT NULL AFTER `ACTIVE`,
ADD COLUMN `CREATED_DATE` DATETIME NULL AFTER `CREATED_BY`,
ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL AFTER `CREATED_DATE`,
ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NULL AFTER `LAST_MODIFIED_BY`,
ADD INDEX `fk_ap_extrapolation_method_createdBy_idx` (`CREATED_BY` ASC),
ADD INDEX `fk_ap_extrapolation_method_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC);

UPDATE ap_extrapolation_method SET ACTIVE=1, CREATED_BY=1, CREATED_DATE='2020-06-01 00:00:00', LAST_MODIFIED_BY=1, LAST_MODIFIED_DATE='2020-06-01 00:00:00';
UPDATE ap_extrapolation_method SET ACTIVE=0 WHERE EXTRAPOLATION_METHOD_ID in (1,3);

ALTER TABLE `fasp`.`ap_extrapolation_method` 
CHANGE COLUMN `CREATED_DATE` `CREATED_DATE` DATETIME NOT NULL ,
CHANGE COLUMN `LAST_MODIFIED_DATE` `LAST_MODIFIED_DATE` DATETIME NOT NULL ;

ALTER TABLE `fasp`.`ap_extrapolation_method` 
ADD CONSTRAINT `fk_ap_extrapolation_method_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ap_extrapolation_method_lastModifiedBy`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_extrapolation_method` AS
    SELECT 
        `em`.`EXTRAPOLATION_METHOD_ID` AS `EXTRAPOLATION_METHOD_ID`,
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

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolate.noDataFound','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No actual consumption data found');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune donnée de consommation réelle trouvée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se encontraron datos de consumo real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum dado de consumo real encontrado');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.reportingRate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reporting Rate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Taux de signalement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tasa de informes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Taxa de Relatório');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.stockedOut','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stockout Rate (days)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Taux de rupture de stock (jours)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tasa de desabastecimiento (días)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Taxa de ruptura (dias)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.stockedOutPer','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stockout Rate (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Taux de rupture de stock (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tasa de desabastecimiento (%)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Taxa de ruptura de estoque (%)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.adjustedConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjusted Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation ajustée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo ajustado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Ajustado');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.convertedToPlanningUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Converted to Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Converti en unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Convertido a Unidad de Planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Convertido em Unidade de Planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.dataType','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Type');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de dados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.other','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Other');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Autre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Otro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'De outros');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.showInPlanningUnits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show everything in planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tout afficher dans les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar todo en unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar tudo em unidades de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.regionalPer','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Regional%');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Régional%');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Regional%');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Regional%');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.consumptionNotes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarques sur la consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de consumo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de consumo');-- pr

INSERT INTO fasp.ap_label(LABEL_ID,LABEL_EN,LABEL_FR,LABEL_SP,LABEL_PR,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE,SOURCE_ID) VALUES ( NULL,'Consumption data entry and adjustment',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');

SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;

INSERT INTO fasp.us_business_function(BUSINESS_FUNCTION_ID,LABEL_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( 'ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT',@MAX,'1',NOW(),'1',NOW());


INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO fasp.us_role_business_function(ROLE_BUSINESS_FUNCTION_ID,ROLE_ID,BUSINESS_FUNCTION_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitTree.commitFailed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'An error occurred while processing the commit. Please try again');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une erreur s`est produite lors du traitement de la validation. Veuillez réessayer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocurrió un error al procesar la confirmación. Inténtalo de nuevo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocorreu um erro ao processar a confirmação. Por favor, tente novamente');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.NoPriceTypeAvailable','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No price type available');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun type de prix disponible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay tipo de precio disponible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum tipo de preço disponível');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.custom','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Custom');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Personnalisé');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Personalizada');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Personalizado');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.forecastQuantity','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité prévue');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade prevista');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.totalForecastQuantity','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Forecasted Qunatity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale prévue');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total prevista');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total prevista');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.allRegions','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All Regions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Toutes les régions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todas las regiones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todas as regiões');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.returnToMonthlyForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Return To Monthly Forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Retour aux prévisions mensuelles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Regresar al Pronóstico Mensual');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Retornar à previsão mensal');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.display','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Affichage');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Monitor');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibição');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.regionalView','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Regional View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vue régionale');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vista regional');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Visualização Regional');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.nationalView','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'National View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vue nationale');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vista Nacional');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Visão Nacional');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.hideCalculations','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Calculations');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer les calculs');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar cálculos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar cálculos');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévoir');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.endOf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(end of');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Fin de');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(final de');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(fim do');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.existingShipments','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Existing Shipments');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions existantes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos existentes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas existentes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.desiredMonthsOfStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Months of Stock');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock souhaités');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses deseados de existencias');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de Estoque Desejados');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.desiredStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Stock');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock souhaité');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Acción deseada');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque Desejado');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.procurementSurplus','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Surplus/Gap');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Excédent/déficit d approvisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Superávit/brecha de adquisiciones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excedente/Lacuna de Aquisição');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.priceType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Price Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de prix');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de precio');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de preço');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.unitPrice','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unit Price');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prix ​​unitaire');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precio unitario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preço unitário');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.ProcurementsNeeded','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurements Needed');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Achats nécessaires');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Adquisiciones necesarias');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Aquisições Necessárias');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.productCost','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Product Cost');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût du produit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo del producto');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo do produto');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.freight','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Freight');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cargaison');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Transporte');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Frete');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.forecastSummary','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Summary');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Résumé des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Resumen de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Resumo da previsão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.yAxisInEquivalencyUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Y axis in equivalency unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Axe Y en unité d équivalence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eje Y en unidad de equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Eixo Y na unidade de equivalência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastReport.xAxisAggregateByYear','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'X-axis Aggregate By Year');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agrégat de l axe X par année');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregado del eje X por año');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agregado do eixo X por ano');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.dataEntryAndAdjustments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Entry and Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie et ajustement des données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingreso y ajuste de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada e Ajuste de Dados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.UpdateversionSettings.UpdateversionSettings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update Version settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour les paramètres de version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar la configuración de la versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar configurações de versão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePlanningUnit.updatePlanningUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar Unidade de Planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.LoadConsumptionFromSupplyPlanning.LoadConsumptionFromSupplyPlanning','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Load Consumption from Supply Planning');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Charger la consommation à partir de la planification des approvisionnements');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo de carga de la planificación de suministro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo de Carga do Planejamento de Fornecimento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitProgram.commitProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Commit Program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme d`engagement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa de compromiso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa de Compromisso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.TreeForecast.TreeForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision d`arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico del árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão de árvore');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.UpdateplanningUnitSetting.UpdateplanningUnitSetting','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Unit Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres d`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configuración de la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configurações da Unidade de Planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.manageProgramInfo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update Program Info');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour les informations sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar información del programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar informações do programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.TreeTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Templates');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèles d`arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantillas de árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelos de árvore');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ConsumptionBasedForecast.ConsumptionBasedForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption-Based Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision basée sur la consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico basado en el consumo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão com base no consumo');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ForecastAnalysisOutput.ForecastAnalysisOutput','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Analysis Output');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sortie de l`analyse des prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Salida de análisis de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saída de Análise de Previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.Versioncomarition','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version Camparison');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version Camparison');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparación de versiones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão Camparison');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.MonthlyForecast.MonthlyForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévisions mensuelles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico Mensual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão Mensal');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ForecastSummary.ForecastSummary','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Summary');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Résumé des prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Resumen de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Resumo da previsão');-- pr

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_pu` CHANGE COLUMN `REFILL_MONTHS` `REFILL_MONTHS` INT UNSIGNED NULL COMMENT '# of moths over which refulls are taken' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data_pu` CHANGE COLUMN `REFILL_MONTHS` `REFILL_MONTHS` INT UNSIGNED NULL COMMENT '# of moths over which refulls are taken' ;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Planning Unit Setting',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_PLANNING_UNIT_SETTING',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Planning Unit Setting',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_PLANNING_UNIT_SETTING',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Planning Unit Setting',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_PLANNING_UNIT_SETTING',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Monthly Forecast',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_MONTHLY_FORECAST',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_MONTHLY_FORECAST','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_MONTHLY_FORECAST','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_MONTHLY_FORECAST','1',NOW(),'1',NOW());




INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Forecast Summary',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_FORECAST_SUMMARY',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Forecast Summary',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_FORECAST_SUMMARY',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_FORECAST_SUMMARY','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_FORECAST_SUMMARY','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_FORECAST_SUMMARY','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_FORECAST_SUMMARY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_EDIT_FORECAST_SUMMARY','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_FORECAST_SUMMARY','1',NOW(),'1',NOW());

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` CHANGE COLUMN `TOTAL_FORECAST` `TOTAL_FORECAST` DOUBLE(16,2) UNSIGNED NULL DEFAULT NULL ;
ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` CHANGE COLUMN `MONTH` `MONTH` DATE NOT NULL COMMENT '' ;
ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(16,2) UNSIGNED NULL ;

-- tree business function
INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Tree Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_TREE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_TREE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Tree Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_TREE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_TREE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Tree',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_TREE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_TREE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Tree',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;

INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_EDIT_TREE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_EDIT_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_EDIT_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_ADD_EDIT_TREE','1',NOW(),'1',NOW());

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.selectedExtraploationMethods','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Selected Extraploation Methods');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Méthodes d\`extraplomation sélectionnées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Métodos de extraploación seleccionados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Métodos de Extraploação Selecionados');-- p


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.pipeline.interpolateMissingValues','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Interpolate Missing values');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Interpoler les valeurs manquantes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Interpolar valores perdidos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Interpolar valores ausentes');-- p

INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_TREE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_ADD_TREE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Tree Template',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_TREE_TEMPLATE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_TREE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Tree',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_TREE',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_TREE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_EDIT_TREE','1',NOW(),'1',NOW());


ALTER TABLE `fasp`.`rm_tree_template_node_data_override` CHANGE COLUMN `MONTH` `MONTH_NO` INT NOT NULL COMMENT 'Month that this Override is for' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_override` CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(16,4) NULL COMMENT 'The manual change value' ;

ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` CHANGE `START_DATE` `START_DATE` INT(10) NOT NULL COMMENT 'Start date that the Modeling is applicable from. Starts from the Forecast Program Start', CHANGE `STOP_DATE` `STOP_DATE` INT(10) NOT NULL COMMENT 'Stop date that the Modeling is applicable from. Defaults to Forecast Program End but user can override'; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.positiveIntegerWithLength','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 10 digit positive number and 2 digits after decimal are allowed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre positif maximum de 10 chiffres et 2 chiffres après la virgule sont autorisés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número positivo máximo de 10 dígitos y 2 dígitos después del decimal.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número positivo máximo de 10 dígitos e 2 dígitos após o decimal são permitidos.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.calendardays','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calendar Days');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Jours calendaires');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Días del calendario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dias do calendário');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.onlyPositiveIntegerGreaterThan0AreAllowed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter valid positive numbers greater than 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez des nombres positifs valides supérieurs à 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca números positivos válidos mayores que 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira números positivos válidos maiores que 0');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.committedDate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Committed date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date d`engagement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha comprometida');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data confirmada');-- pr