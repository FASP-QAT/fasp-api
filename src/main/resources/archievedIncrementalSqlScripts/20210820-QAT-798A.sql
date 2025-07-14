/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 20-Aug-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validNoDoubleSpace.string','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No spaces are allowed at starting & ending of the string, Also no double spaces are allowed in the string');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun espace n est autorisé au début et à la fin de la chaîne. De même, aucun espace double n est autorisé dans la chaîne');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se permiten espacios al comienzo y al final de la cadena, tampoco se permiten espacios dobles en la cadena');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não são permitidos espaços no início e no final da string, também não são permitidos espaços duplos na string');
