-- #########################################################################################################
-- One time run
-- #########################################################################################################
ALTER TABLE `fasp`.`us_user_acl` ADD COLUMN `ROLE_ID` VARCHAR(50) NULL AFTER `USER_ID`;

INSERT INTO ap_label VALUES (null, 'View Master Data', null, null, null, 1, now(), 1, now(), 24);
INSERT INTO us_business_function VALUES ('ROLE_BF_LIST_MASTER_DATA', LAST_INSERT_ID(), 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES 
    (null, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_INTERNAL_USER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_REALM_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_TRAINER_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_PROGRAM_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_PROGRAM_USER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_VIEW_DATA_ENTRY', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_REPORT_USER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_DATASET_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_DATASET_USER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_FORECAST_VIEWER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now()),
    (null, 'ROLE_GUEST_USER', 'ROLE_BF_LIST_MASTER_DATA', 1, now(), 1, now());

INSERT INTO us_role_business_function VALUES (null, 'ROLE_INTERNAL_USER', 'ROLE_BF_ADD_MODELING_TYPE', 1, now(), 1, now()), (null, 'ROLE_REALM_ADMIN', 'ROLE_BF_ADD_MODELING_TYPE', 1, now(), 1, now());
UPDATE us_user u set u.REALM_ID=null where u.USER_ID=36;
-- #########################################################################################################

DROP TABLE IF EXISTS `fasp`.`temp_security`;
CREATE TABLE `fasp`.`temp_security` (
  `SECURITY_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `METHOD` INT NOT NULL,
  `URL_LIST` TEXT NOT NULL,
  `BF_LIST` TEXT NOT NULL,
  PRIMARY KEY (`SECURITY_ID`));

DROP TABLE IF EXISTS `fasp`.`ap_security`;

CREATE TABLE `fasp`.`ap_security` (
  `SECURITY_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `METHOD` int NOT NULL,
  `URL` VARCHAR(50) NOT NULL,
  `BF` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`SECURITY_ID`));
ALTER TABLE `fasp`.`ap_security` ADD UNIQUE INDEX `index2` (`METHOD` ASC, `URL` ASC, `BF` ASC) VISIBLE;

TRUNCATE TABLE temp_security;

INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/currency', 'ROLE_BF_ADD_CURRENCY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/currency', 'ROLE_BF_LIST_CURRENCY~ROLE_BF_ADD_BUDGET~ROLE_BF_TICKETING');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/currency', 'ROLE_BF_EDIT_CURRENCY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/currency/*', 'ROLE_BF_LIST_CURRENCY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/currency/*', 'ROLE_BF_LIST_CURRENCY');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/dataSource', 'ROLE_BF_ADD_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSource', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/dataSource', 'ROLE_BF_EDIT_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSource/**', 'ROLE_BF_LIST_DATA_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/dataSourceType', 'ROLE_BF_ADD_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSourceType', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/dataSourceType', 'ROLE_BF_EDIT_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dataSourceType/**', 'ROLE_BF_LIST_DATA_SOURCE_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/dimension', 'ROLE_BF_ADD_DIMENSION');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/dimension', 'ROLE_BF_EDIT_DIMENSION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dimension', 'ROLE_BF_LIST_DIMENSION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/equivalencyUnit', 'ROLE_BF_LIST_EQUIVALENCY_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/equivalencyUnit', 'ROLE_BF_ADD_EQUIVALENCY_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/equivalencyUnit/**', 'ROLE_BF_LIST_EQUIVALENCY_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/equivalencyUnit/**', 'ROLE_BF_LIST_EQUIVALENCY_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/equivalencyUnit/mapping', 'ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/equivalencyUnit/mapping', 'ROLE_BF_ADD_EQUIVALENCY_UNIT_MAPPING');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/equivalencyUnit/mapping/**', 'ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/forecastingUnit', 'ROLE_BF_ADD_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/forecastingUnit', 'ROLE_BF_EDIT_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/forecastingUnit/**', 'ROLE_BF_LIST_FORECASTING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastMethod', 'ROLE_BF_LIST_FORECAST_METHOD');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/forecastMethod', 'ROLE_BF_ADD_FORECAST_METHOD');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/forecastMethod/*', 'ROLE_BF_LIST_FORECAST_METHOD');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/fundingSource', 'ROLE_BF_ADD_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/fundingSource', 'ROLE_BF_EDIT_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSource', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSource/**', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSource/**', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSource/**', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/fundingSourceType', 'ROLE_BF_ADD_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/fundingSourceType', 'ROLE_BF_EDIT_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSourceType', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSourceType/**', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/fundingSourceType/**', 'ROLE_BF_LIST_FUNDING_SOURCE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/healthArea', 'ROLE_BF_ADD_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/healthArea', 'ROLE_BF_EDIT_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/healthArea/**', 'ROLE_BF_LIST_HEALTH_AREA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integration', 'ROLE_BF_LIST_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/integration', 'ROLE_BF_ADD_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/integration', 'ROLE_BF_EDIT_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integration/*', 'ROLE_BF_LIST_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integration/*', 'ROLE_BF_LIST_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integrationProgram', 'ROLE_BF_ADD_INTEGRATION_PROGRAM');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/integrationProgram', 'ROLE_BF_ADD_INTEGRATION_PROGRAM');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integrationProgram/**', 'ROLE_BF_ADD_INTEGRATION_PROGRAM');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/integrationProgram/**', 'ROLE_BF_MANUAL_INTEGRATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/integrationProgram/**', 'ROLE_BF_ADD_INTEGRATION_PROGRAM');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/logout', '');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/modelingType', 'ROLE_BF_LIST_MODELING_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/modelingType', 'ROLE_BF_ADD_MODELING_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/modelingType/*', 'ROLE_BF_LIST_MODELING_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/organisation', 'ROLE_BF_ADD_ORGANIZATION');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/organisation', 'ROLE_BF_EDIT_ORGANIZATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisation', 'ROLE_BF_LIST_ORGANIZATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisation/**', 'ROLE_BF_LIST_ORGANIZATION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisation/**', 'ROLE_BF_LIST_ORGANIZATION');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/organisationType', 'ROLE_BF_ADD_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/organisationType', 'ROLE_BF_EDIT_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisationType', 'ROLE_BF_LIST_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisationType/**', 'ROLE_BF_LIST_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisationType/**', 'ROLE_BF_LIST_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/organisationType/**', 'ROLE_BF_LIST_ORGANIZATION_TYPE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit', 'ROLE_BF_ADD_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/planningUnit', 'ROLE_BF_EDIT_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/capacity/**', 'ROLE_BF_LIST_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/capacity/**', 'ROLE_BF_LIST_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/capacity/**', 'ROLE_BF_LIST_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/planningUnit/capacity', 'ROLE_BF_MAP_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/capacity/**', 'ROLE_BF_LIST_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/capacity/**', 'ROLE_BF_LIST_PLANNING_UNIT_CAPACITY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit/productCategoryList/active/realmCountryId/*', 'ROLE_BF_MANUAL_TAGGING');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/planningUnit/**', 'ROLE_BF_LIST_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/procurementAgent', 'ROLE_BF_ADD_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementAgent', 'ROLE_BF_EDIT_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/procurementAgent/**', 'ROLE_BF_ADD_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementAgent/**', 'ROLE_BF_EDIT_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgent/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/procurementAgentType', 'ROLE_BF_ADD_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementAgentType', 'ROLE_BF_EDIT_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgentType', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgentType/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementAgentType/**', 'ROLE_BF_LIST_PROCUREMENT_AGENT');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/procurementUnit', 'ROLE_BF_ADD_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/procurementUnit', 'ROLE_BF_EDIT_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/procurementUnit/**', 'ROLE_BF_LIST_PROCUREMENT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/productCategory', 'ROLE_BF_MANAGE_PRODUCT_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/productCategory/**', 'ROLE_BF_LIST_PRODUCT_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/productCategory/**', 'ROLE_BF_LIST_PRODUCT_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/productCategory/**', 'ROLE_BF_LIST_PRODUCT_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/realm', 'ROLE_BF_CREATE_REALM');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/realm', 'ROLE_BF_EDIT_REALM');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realm', 'ROLE_BF_LIST_REALM');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realm/*', 'ROLE_BF_LIST_REALM');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/realmCountry', 'ROLE_BF_MAP_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/realmCountry', 'ROLE_BF_MAP_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry', 'ROLE_BF_LIST_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_LIST_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/realmCountry/**', 'ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_LIST_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_LIST_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/realmCountry/**', 'ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/realmCountry/**', 'ROLE_BF_LIST_REALM_COUNTRY');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/region', 'ROLE_BF_MAP_REGION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/region', 'ROLE_BF_MAP_REGION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/region/**', 'ROLE_BF_MAP_REGION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/region/**', 'ROLE_BF_MAP_REGION');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/role', 'ROLE_BF_ADD_ROLE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/role', 'ROLE_BF_ADD_ROLE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/role', 'ROLE_BF_ADD_ROLE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/role/{roleId}', 'ROLE_BF_ADD_ROLE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/supplier', 'ROLE_BF_ADD_SUPPLIER');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/supplier', 'ROLE_BF_EDIT_SUPPLIER');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/supplier', 'ROLE_BF_LIST_SUPPLIER');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/supplier/**', 'ROLE_BF_LIST_SUPPLIER');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/supplier/**', 'ROLE_BF_LIST_SUPPLIER');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/tracerCategory', 'ROLE_BF_ADD_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/tracerCategory', 'ROLE_BF_EDIT_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/tracerCategory', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/tracerCategory/**', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/tracerCategory/**', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/tracerCategory/**', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/tracerCategory/**', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/tracerCategory/**', 'ROLE_BF_LIST_TRACER_CATEGORY');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/unit', 'ROLE_BF_ADD_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/unit', 'ROLE_BF_EDIT_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/unit', 'ROLE_BF_LIST_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/unit/**', 'ROLE_BF_LIST_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/unit/**', 'ROLE_BF_LIST_UNIT');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/usagePeriod', 'ROLE_BF_LIST_USAGE_PERIOD');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/usagePeriod', 'ROLE_BF_ADD_USAGE_PERIOD');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/usagePeriod/**', 'ROLE_BF_LIST_USAGE_PERIOD');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/usageTemplate', 'ROLE_BF_LIST_USAGE_TEMPLATE');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/usageTemplate', 'ROLE_BF_EDIT_USAGE_TEMPLATE,ROLE_BF_EDIT_USAGE_TEMPLATE_ALL,ROLE_BF_EDIT_USAGE_TEMPLATE_OWN');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/usageTemplate/**', 'ROLE_BF_LIST_USAGE_TEMPLATE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/usageTemplate/**', 'ROLE_BF_LIST_USAGE_TEMPLATE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/user', 'ROLE_BF_LIST_USER');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user', 'ROLE_BF_ADD_USER');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/user', 'ROLE_BF_EDIT_USER');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/user/*', 'ROLE_BF_LIST_USER');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/agreement', '');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/user/*', '');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/language', '');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/module/*', '');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/theme/*', '');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/user/programId/*', 'ROLE_BF_LIST_USER');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/user/realmId/*', 'ROLE_BF_LIST_USER');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/updateExpiredPassword', '');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/user/updatePassword', '');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/problemReport/createManualProblem', 'ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/problemStatus', 'ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/sync/allMasters/forPrograms/*', '');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/treeTemplate', 'ROLE_BF_VIEW_TREE_TEMPLATES');
INSERT IGNORE INTO temp_security VALUES (null, 2, '/api/treeTemplate', 'ROLE_BF_ADD_TREE_TEMPLATE');
INSERT IGNORE INTO temp_security VALUES (null, 3, '/api/treeTemplate', 'ROLE_BF_EDIT_TREE_TEMPLATE');
INSERT IGNORE INTO temp_security VALUES (null, 1, '/api/treeTemplate/*', 'ROLE_BF_VIEW_TREE_TEMPLATES');

-- Blanket access for now to be removed once all Access urls are put
INSERT IGNORE INTO ap_security VALUES (null, 1, '/api/**', '');
INSERT IGNORE INTO ap_security VALUES (null, 2, '/api/**', '');
INSERT IGNORE INTO ap_security VALUES (null, 3, '/api/**', '');
