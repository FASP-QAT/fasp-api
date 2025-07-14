/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 13-May-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.exportAsPDF','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Export as PDF');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exporter au format PDF');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Exportar como PDF');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exportar como PDF');-- pr