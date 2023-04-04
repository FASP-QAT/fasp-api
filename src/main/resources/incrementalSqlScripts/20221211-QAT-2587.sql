/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Dec-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realmCountryPlanningUnit.duplicateSKU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate SKU Code');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dupliquer le code SKU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Código SKU duplicado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Código SKU duplicado');-- pr