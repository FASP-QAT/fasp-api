/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 21-Jun-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.dateValidation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please use (YYYY-MM-DD) date format');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez utiliser le format de date (AAAA-MM-JJ)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice el formato de fecha (AAAA-MM-DD)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use o formato de data (AAAA-MM-DD)');