/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 16-May-2022
 */

delete u.* from us_role_business_function u where u.BUSINESS_FUNCTION_ID='ROLE_BF_ADD_EDIT_TREE';
delete u.* from us_business_function u where u.BUSINESS_FUNCTION_ID='ROLE_BF_ADD_EDIT_TREE';
delete l.* from ap_label l where l.LABEL_ID=36818;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Edit Version Settings',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_VERSION_SETTINGS',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_VERSION_SETTINGS','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_VERSION_SETTINGS','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_EDIT_VERSION_SETTINGS','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting - Edit Planning Unit Settings',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_PLANNING_UNIT_SETTINGS',@MAX,'1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_PLANNING_UNIT_SETTINGS','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_PLANNING_UNIT_SETTINGS','1',NOW(),'1',NOW());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_EDIT_PLANNING_UNIT_SETTINGS','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecast Viewer',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'8');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_role`VALUES ( 'ROLE_FORECAST_VIEWER',@MAX,'1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_FORECASTING_UNIT','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_PLANNING_UNIT','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_DATASET','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_PLANNING_UNIT_SETTING','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_TREE','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_ADD_TREE','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_TREE_TEMPLATE','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_USAGE_TEMPLATE','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_MONTHLY_FORECAST','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_LIST_FORECAST_SUMMARY','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());
insert into us_role_business_function values (null,'ROLE_FORECAST_VIEWER','ROLE_BF_APPLICATION_DASHBOARD','1',NOW(),'1',NOW());
