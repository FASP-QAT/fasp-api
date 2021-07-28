UPDATE `fasp`.`ap_static_label` SET `LABEL_CODE`='static.user.orgAndCountry' WHERE `STATIC_LABEL_ID`='371';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organization & Country' WHERE `STATIC_LABEL_LANGUAGE_ID`='1477';
ALTER TABLE `fasp`.`us_user` CHANGE COLUMN `PHONE` `ORG_AND_COUNTRY` VARCHAR(15) CHARACTER SET 'utf8' NULL DEFAULT NULL ;
