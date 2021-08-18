UPDATE `fasp`.`ap_static_label` SET `LABEL_CODE`='static.user.orgAndCountry' WHERE `STATIC_LABEL_ID`='371';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organization & Country' WHERE `STATIC_LABEL_LANGUAGE_ID`='1477';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organización y país' WHERE `STATIC_LABEL_LANGUAGE_ID`='1478';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organisation & Pays' WHERE `STATIC_LABEL_LANGUAGE_ID`='1479';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Organização e País' WHERE `STATIC_LABEL_LANGUAGE_ID`='1480';
ALTER TABLE `fasp`.`us_user` CHANGE COLUMN `PHONE` `ORG_AND_COUNTRY` VARCHAR(100) CHARACTER SET 'utf8' NULL DEFAULT NULL ;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.user.org&CountryText','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Organization & Country');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez l organisation et le pays');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese Organización y País');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira Organização e País');
