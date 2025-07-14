/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Feb-2021
 */

ALTER TABLE `fasp`.`ap_language` ADD COLUMN `COUNTRY_CODE` VARCHAR(5) NOT NULL AFTER `LANGUAGE_NAME`; 
INSERT INTO `fasp`.`ap_label_source`(`SOURCE_ID`,`SOURCE_DESC`) VALUES ( NULL,'ap_language'); 
INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'English','Anglais','Inglés','inglês','1',NOW(),'1',NOW(),'35'); 
UPDATE ap_language SET LANGUAGE_NAME=LAST_INSERT_ID() WHERE language_id=1;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'French','français','Francés','Francês','1',NOW(),'1',NOW(),'35'); 
UPDATE ap_language SET LANGUAGE_NAME=LAST_INSERT_ID() WHERE language_id=2;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Spanish','Espagnol','Español','Espanhol','1',NOW(),'1',NOW(),'35'); 
UPDATE ap_language SET LANGUAGE_NAME=LAST_INSERT_ID() WHERE language_id=3;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Portuguese','Portugais','Portugués','Português','1',NOW(),'1',NOW(),'35'); 
UPDATE ap_language SET LANGUAGE_NAME=LAST_INSERT_ID() WHERE language_id=4;

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Russian','russe','Ruso','Russo','1',NOW(),'1',NOW(),'35'); 
UPDATE ap_language SET LANGUAGE_NAME=LAST_INSERT_ID() WHERE language_id=5;

ALTER TABLE `fasp`.`ap_language` CHANGE `LANGUAGE_NAME` `LABEL_ID` INT(10) NOT NULL COMMENT 'Language name, no need for a Label here since the Language name will be in the required language'; 

UPDATE `fasp`.`ap_language` SET `COUNTRY_CODE`='us' WHERE `LANGUAGE_ID`='1';
UPDATE `fasp`.`ap_language` SET `COUNTRY_CODE`='wf' WHERE `LANGUAGE_ID`='2'; 
UPDATE `fasp`.`ap_language` SET `COUNTRY_CODE`='es' WHERE `LANGUAGE_ID`='3'; 
UPDATE `fasp`.`ap_language` SET `COUNTRY_CODE`='pt' WHERE `LANGUAGE_ID`='4'; 
UPDATE `fasp`.`ap_language` SET `COUNTRY_CODE`='in' WHERE `LANGUAGE_ID`='5'; 
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.language.countryCode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'1','Flag Code'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'2','Code du drapeau'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'3','Código de bandera'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'4','Código de Bandeira'); 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.language.countrycodetext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'1','Flag Code'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'2','Entrez le code du drapeau'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'3','Ingrese el código de la bandera'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'4','Insira o código da bandeira'); 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.language.countrycode2chartext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'1','Flag Code'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'2','Un maximum de deux caractères est autorisé'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'3','Se permiten dos caracteres como máximo'); 
INSERT INTO `fasp`.`ap_static_label_languages`(`STATIC_LABEL_LANGUAGE_ID`,`STATIC_LABEL_ID`,`LANGUAGE_ID`,`LABEL_TEXT`) VALUES ( NULL,@MAX,'4','São permitidos no máximo dois caracteres'); 