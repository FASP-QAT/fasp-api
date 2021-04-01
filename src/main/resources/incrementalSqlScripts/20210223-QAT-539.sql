/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 23-Feb-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.actualBalance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual balance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde réel');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo real');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.projectedBalance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Projected balance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde projeté');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo proyectado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo projetado');-- pr