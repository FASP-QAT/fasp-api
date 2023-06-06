/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 14-May-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastValidation.noConsumptionExtrapolationNotesFound','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No consumption extrapolation notes found'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune note d`extrapolation de consommation trouvée'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se han encontrado notas de extrapolación de consumo'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma nota de extrapolação de consumo encontrada'); -- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastValidation.consumptionExtrapolationNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Extrapolation Notes'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarques sur l`extrapolation de la consommation'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de extrapolación de consumo'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de Extrapolação de Consumo'); -- pr




