/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 26-Oct-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastingUnit.forecastingUnitId','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasting Unit Id');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Identifiant de l unité de prévision');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'ID de unidad de previsión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Id da unidade de previsão');