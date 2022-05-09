/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 04-May-2022
 */

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Supply planning module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_SUPPLY_PLANNING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_FORECASTING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

update ap_label l set l.LABEL_EN='SP - QAT Forecast Import' where l.LABEL_EN='QAT Forecast Import';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Period' where l.LABEL_EN='Add Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Period' where l.LABEL_EN='Edit Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Period' where l.LABEL_EN='List Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Add Modeling Type' where l.LABEL_EN='Add Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Edit Modeling Type' where l.LABEL_EN='Edit Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - List Modeling Type' where l.LABEL_EN='List Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Method' where l.LABEL_EN='Add Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Method' where l.LABEL_EN='Edit Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Method' where l.LABEL_EN='List Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit' where l.LABEL_EN='Add Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit' where l.LABEL_EN='Edit Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit' where l.LABEL_EN='List Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit Mapping' where l.LABEL_EN='Add Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit Mapping' where l.LABEL_EN='Edit Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit Mapping' where l.LABEL_EN='List Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Program' where l.LABEL_EN='Add Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Program' where l.LABEL_EN='Edit Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Program' where l.LABEL_EN='List Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Template' where l.LABEL_EN='Add Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Template' where l.LABEL_EN='Edit Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Template' where l.LABEL_EN='List Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Import From QAT Supply Plan' where l.LABEL_EN='Add Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Edit Import From QAT Supply Plan' where l.LABEL_EN='Edit Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - List Import From QAT Supply Plan' where l.LABEL_EN='List Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Compare and Select Forecast' where l.LABEL_EN='Compare and Select Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Product Validation' where l.LABEL_EN='Product Validation';
update ap_label l set l.LABEL_EN='Forecasting - Modeling Validation' where l.LABEL_EN='Modeling Validation';
update ap_label l set l.LABEL_EN='Forecasting - Compare Version' where l.LABEL_EN='Compare Version';
update ap_label l set l.LABEL_EN='Forecasting - Extrapolation' where l.LABEL_EN='Extrapolation';
update ap_label l set l.LABEL_EN='Forecasting - Commit Dataset' where l.LABEL_EN='Commit Dataset';
update ap_label l set l.LABEL_EN='Forecasting - Consumption data entry and adjustment' where l.LABEL_EN='Consumption data entry and adjustment';
update ap_label l set l.LABEL_EN='Forecasting - Add Planning Unit Setting' where l.LABEL_EN='Add Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - Edit Planning Unit Setting' where l.LABEL_EN='Edit Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Planning Unit Setting' where l.LABEL_EN='List Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Monthly Forecast' where l.LABEL_EN='List Monthly Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Summary' where l.LABEL_EN='Edit Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Summary' where l.LABEL_EN='List Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Tree Template' where l.LABEL_EN='List Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree Template' where l.LABEL_EN='Add Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - List Tree' where l.LABEL_EN='List Tree';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree' where l.LABEL_EN='Add Tree';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree Template' where l.LABEL_EN='Edit Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree' where l.LABEL_EN='Edit Tree';

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Version Settings',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_VERSION_SETTINGS',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Import Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_IMPORT_DATASET',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Export Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EXPORT_DATASET',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Load and Delete Program',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LOAD_DELETE_DATASET',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Consumption forecast error',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_CONSUMPTION_FORECAST_ERROR',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());