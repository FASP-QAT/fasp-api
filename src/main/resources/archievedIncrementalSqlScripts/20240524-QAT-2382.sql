INSERT INTO `fasp`.`ap_label` values (null, 'Update Tracer Category - Forecasting Unit', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`us_business_function` values ('ROLE_BF_UPDATE_TRACER_CATEGORY_FOR_FU', @labelId, 1, now(), 1, now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_UPDATE_TRACER_CATEGORY_FOR_FU',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_INTERNAL_USER','ROLE_BF_UPDATE_TRACER_CATEGORY_FOR_FU',1,now(),1,now());

INSERT INTO `fasp`.`ap_label` values (null, 'Update Planning Unit Category - Forecasting Unit', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`us_business_function` values ('ROLE_BF_UPDATE_PLANNING_UNIT_CATEGORY_FOR_FU', @labelId, 1, now(), 1, now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_UPDATE_PLANNING_UNIT_CATEGORY_FOR_FU',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_INTERNAL_USER','ROLE_BF_UPDATE_PLANNING_UNIT_CATEGORY_FOR_FU',1,now(),1,now());