INSERT INTO `fasp`.`ap_label` values (null, 'Update Unit - Forecasting Unit', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`us_business_function` values ('ROLE_BF_UPDATE_UNIT_FOR_FU', @labelId, 1, now(), 1, now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_UPDATE_UNIT_FOR_FU',1,now(),1,now());

INSERT INTO `fasp`.`ap_label` values (null, 'Update Unit - Planning Unit', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`us_business_function` values ('ROLE_BF_UPDATE_UNIT_FOR_PU', @labelId, 1, now(), 1, now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_UPDATE_UNIT_FOR_PU',1,now(),1,now());
