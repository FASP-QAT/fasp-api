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