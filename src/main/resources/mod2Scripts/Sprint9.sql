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

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolations.showFits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Fits');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les coupes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar ajustes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar ajustes');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.missingDataNotePart1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You have months with no actual consumption data, which will cause unexpected results in the extrapolation. Return to the ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez des mois sans données de consommation réelle, ce qui entraînera des résultats inattendus dans l`extrapolation. Retour à la ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiene meses sin datos de consumo real, lo que provocará resultados inesperados en la extrapolación. Volver a la ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você tem meses sem dados reais de consumo, o que causará resultados inesperados na extrapolação. Retorne para ');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.missingDataNotePart2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen to fill in or interpolate the missing data.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'écran pour compléter ou interpoler les données manquantes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla para completar o interpolar los datos faltantes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela para preencher ou interpolar os dados ausentes.');-- pr

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

ALTER TABLE `fasp`.`rm_forecast_actual_consumption` ADD COLUMN `PU_AMOUNT` DECIMAL(16,4) UNSIGNED NULL AFTER `ADJUSTED_AMOUNT`, CHANGE COLUMN `EXCLUDE` `ADJUSTED_AMOUNT` DECIMAL(16,4) UNSIGNED NULL;
