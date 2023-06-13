/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 08-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.validationFailedAndCannotInterpolate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Validation failed so cannot interpolate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La validation a échoué, donc impossible d`interpoler');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La validación falló, por lo que no se puede interpolar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A validação falhou, portanto não é possível interpolar');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.daysOfStockOutMustBeLessInCaseOfActualConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter value lesser than number of days when you have an actual consumption greater than 0 or update the actual consumption to 0.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez entrer une valeur inférieure au nombre de jours lorsque vous avez une consommation réelle supérieure à 0 ou mettre à jour la consommation réelle à 0.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un valor menor que el número de días en los que tiene un consumo real mayor que 0 o actualice el consumo real a 0.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um valor menor que o número de dias quando você tiver um consumo real maior que 0 ou atualize o consumo real para 0.');-- pr