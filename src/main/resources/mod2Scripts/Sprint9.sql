/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Apr-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.errorOccured','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'An error occurred while trying to calculate extrapolation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une erreur s`est produite lors de la tentative de calcul de l`extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se produjo un error al intentar calcular la extrapolación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocorreu um erro ao tentar calcular a extrapolação');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.consumptionDataEntryAndAdjustment.nothingToInterpolate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'There is nothing to interpolate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Il n`y a rien à interpoler');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay nada que interpolar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não há nada para interpolar');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.consumptionDataEntryAndAdjustment.interpolatedDataFor','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Interpolated data for: ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données interpolées pour: ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos interpolados para: ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados interpolados para: ');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrpolation.graphTitlePart1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast for ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévisions pour ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico para ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão para ');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrpolation.graphTitlePart2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' in ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' dans ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' en ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' dentro ');-- pr

UPDATE ap_static_label l 
LEFT JOIN ap_static_label_languages ll ON l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
SET ll.LABEL_TEXT='Transfer'
WHERE l.LABEL_CODE='static.tree.transferToNode' AND ll.LANGUAGE_ID=1;

UPDATE ap_static_label l 
LEFT JOIN ap_static_label_languages ll ON l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
SET ll.LABEL_TEXT='Transférer'
WHERE l.LABEL_CODE='static.tree.transferToNode' AND ll.LANGUAGE_ID=2;

UPDATE ap_static_label l 
LEFT JOIN ap_static_label_languages ll ON l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
SET ll.LABEL_TEXT='Transferir'
WHERE l.LABEL_CODE='static.tree.transferToNode' AND ll.LANGUAGE_ID=3;

UPDATE ap_static_label l 
LEFT JOIN ap_static_label_languages ll ON l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
SET ll.LABEL_TEXT='Transferir'
WHERE l.LABEL_CODE='static.tree.transferToNode' AND ll.LANGUAGE_ID=4;