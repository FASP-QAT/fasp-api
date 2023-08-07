/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-Sep-2022
 */


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.cleanUpConfirmation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to clean up this program?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous nettoyer ce programme ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres limpiar este programa?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Deseja limpar este programa?');-- pr

