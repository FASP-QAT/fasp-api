UPDATE `fasp`.`ap_static_label` SET `LABEL_CODE`='static.user.orgAndCountry' WHERE `STATIC_LABEL_ID`='371';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organization & Country' WHERE `STATIC_LABEL_LANGUAGE_ID`='1477';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organización y país' WHERE `STATIC_LABEL_LANGUAGE_ID`='1478';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organisation & Pays' WHERE `STATIC_LABEL_LANGUAGE_ID`='1479';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organização e País' WHERE `STATIC_LABEL_LANGUAGE_ID`='1480';
ALTER TABLE `fasp`.`us_user` CHANGE COLUMN `PHONE` `ORG_AND_COUNTRY` VARCHAR(100) CHARACTER SET 'utf8' NULL DEFAULT NULL ;
