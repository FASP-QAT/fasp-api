/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 16-May-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.recommendToChangeBatchName','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Recommended to change the batch number');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Recommandé de changer le numéro de lot');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Recomendado para cambiar el número de lote');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Recomendado para alterar o número do lote');-- pr