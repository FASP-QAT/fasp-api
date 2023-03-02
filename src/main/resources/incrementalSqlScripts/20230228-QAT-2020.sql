INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'Can Edit All Equivalency Units','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO `fasp`.`us_business_function` VALUES('ROLE_BF_EDIT_EQIVALENCY_UNIT_ALL',@MAX,1,now(),1,now());
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'Can Edit only own Equivalency Units','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO `fasp`.`us_business_function` VALUES('ROLE_BF_EDIT_EQIVALENCY_UNIT_OWN',@MAX,1,now(),1,now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_EQIVALENCY_UNIT_ALL',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_DATASET_ADMIN','ROLE_BF_EDIT_EQIVALENCY_UNIT_OWN',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_TRAINER_ADMIN','ROLE_BF_EDIT_EQIVALENCY_UNIT_ALL',1,now(),1,now());
