ALTER TABLE `fasp`.`rm_organisation` 
CHANGE COLUMN `ORGANISATION_CODE` `ORGANISATION_CODE` VARCHAR(10) CHARACTER SET 'utf8mb3' NOT NULL COMMENT '10 character Unique Code for each Organisation' ;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisation.organisationcodemax10digittext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display name length should be 10 characters long'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La longueur du nom d’affichage doit être de 10 caractères'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La longitud del nombre para mostrar debe tener 10 caracteres.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O comprimento do nome de exibição deve ter 10 caracteres'); -- pr