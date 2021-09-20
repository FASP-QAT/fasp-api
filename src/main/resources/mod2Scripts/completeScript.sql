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
  `CONVERT_TO_MONTH` DECIMAL(9,4) NOT NULL COMMENT 'Multiply by this to converts the UsagePeriod to Months', 
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
  `CONVERT_TO_FU` DECIMAL(18,4) UNSIGNED NOT NULL,
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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dataset');
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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Equivalancy Unit');
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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter DataSet');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrer l ensemble de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingresar DataSet');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrar DataSet');




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.datasetDisplayName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dataset Display Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d affichage de l ensemble de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre para mostrar del conjunto de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de exibição do conjunto de dados');




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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Dataset Module');
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


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Dataset Admin',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'8');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_role`(`ROLE_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_DATASET_ADMIN',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Dataset User',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'8');
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


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_FORECAST_METHOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_USAGE_PERIOD','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_MODELING_TYPE','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_FORECAST_METHOD','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_APPLICATION_DASHBOARD','1',NOW(),'1',NOW()); 
-- Shubham script end date-16th sep

-- Anchal's script for access control screen
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.programAndDataset','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program/Dataset');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme/Ensemble de données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa / conjunto de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa / conjunto de dados');-- pr

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