/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 28-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastSummary.priceNotAvaiable','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Price is missing - please add in Update Planning Unit screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le prix est manquant - veuillez l`ajouter dans l`écran Mettre à jour l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Falta el precio; agréguelo en la pantalla Actualizar unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Falta o preço - adicione na tela Atualizar unidade de planejamento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastSummary.priceIsMissing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Price is missing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le prix est manquant');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Falta el precio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'preço está faltando');-- pr