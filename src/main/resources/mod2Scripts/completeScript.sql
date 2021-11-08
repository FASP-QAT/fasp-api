SET @dt='2021-09-01 00:00:00';

ALTER TABLE `fasp`.`rm_tracer_category` 
ADD COLUMN `HEALTH_AREA_ID` INT(10) UNSIGNED NULL AFTER `LABEL_ID`,
ADD INDEX `fk_rm_tracer_category_healthAreaId_idx` (`HEALTH_AREA_ID` ASC);
ALTER TABLE `fasp`.`rm_tracer_category` 
ADD CONSTRAINT `fk_rm_tracer_category_healthAreaId`
  FOREIGN KEY (`HEALTH_AREA_ID`)
  REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

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


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


-- INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());

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
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 70, null, @nodeDataPuId, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Strawberry condom pack of 1', null, null, null, 1, @dt, 1, @dt, 46);
SELECT last_insert_id() into @labelId;
insert into rm_tree_template_node values (null, 1, 7, "00.01.01.01.02.01",6, 5, null, 1, @labelId, 1, @dt, 1, @dt, 1);
insert into rm_tree_template_node_data_pu values (null, 4159, 0, 2, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeDataPuId;
insert into rm_tree_template_node_data values (null, @nodeId, '2021-01-01', 30, null, @nodeDataPuId, "", 1, @dt, 1, @dt, 1);

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
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Managment');
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
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Scenarion Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du scénario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del escenario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do Cenário');


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


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_ADD_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_DATASET','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_DATASET','1',NOW(),'1',NOW());


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
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES ('2551', '911', '1', NULL, NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES ('2551', '915', '2', '4148', NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO ap_label values (null, "10 Bottles of TLD 90", null, null, null, 1, now(), 1, now(), 51);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES ('2551', '2665', '3', NULL, @labelId, 900, '1', '1', '2021-10-27 00:00:00');

INSERT INTO ap_label values (null, "North", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @northId;
INSERT INTO rm_program_region values (null, 2551, @northId, 1, 1, now(), 1, now());

INSERT INTO ap_label values (null, "South", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @southId;
INSERT INTO rm_program_region values (null, 2551, @southId, 1, 1, now(), 1, now());

INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-01-01', 5000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-02-01', 6500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-03-01', 6200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-04-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-05-01', 6800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-06-01', 6400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-07-01', 5800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-08-01', 5900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-09-01', 6300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-10-01', 6900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-11-01', 7500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2020-12-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-01-01', 7100, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-02-01', 8400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-03-01', 8300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-04-01', 9000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-05-01', 7600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-06-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 1, 70, '2021-07-01', 6700, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-01-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-02-01', 3800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-03-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-04-01', 3650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-05-01', 3700, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-06-01', 3200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-07-01', 3780, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-08-01', 3900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-09-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-10-01', 3280, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-11-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-12-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-01-01', 3730, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-02-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-03-01', 3380, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-04-01', 3840, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-05-01', 3480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-06-01', 3370, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-07-01', 3600, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, 2551, 2, @southId, '2020-01-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-02-01', 5586, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-03-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-04-01', 5365, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-05-01', 5439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-06-01', 4704, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-07-01', 5556, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-08-01', 5733, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-09-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-10-01', 4821, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-11-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2020-12-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-01-01', 5483, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-02-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-03-01', 4968, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-04-01', 5644, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-05-01', 5115, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-06-01', 4953, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 2, @northId, '2021-07-01', 5292, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-01-01', 540, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-02-01', 480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-03-01', 580, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-04-01', 500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-05-01', 560, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-06-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-07-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-08-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-09-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-10-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-11-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-12-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-01-01', 600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-02-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-03-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-04-01', 650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-05-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-06-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-07-01', 680, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, 2551, 3, @southId, '2020-01-01', 1274, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-02-01', 1132, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-03-01', 1368, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-04-01', 1180, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-05-01', 1321, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-06-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-07-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-08-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-09-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-10-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-11-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2020-12-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-01-01', 1416, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-02-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-03-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-04-01', 1534, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-05-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-06-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, 2551, 3, @northId, '2021-07-01', 1604, null, null, 0, 1, 1, now());

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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'program Discription');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Description du programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'descrição do programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'descripción del programa');

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

