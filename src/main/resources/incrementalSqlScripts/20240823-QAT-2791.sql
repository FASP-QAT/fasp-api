/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 23-Aug-2024
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.bulkExtrapolation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Bulk Extraploation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extraploation en masse');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extraploação em massa');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extraplotación masiva');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.optimizeTES&ARIMA','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Optimize TES & ARIMA');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Optimiser TES & ARIMA');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Otimize TES e ARIMA');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Optimizar TES y ARIMA');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.missingTES&ARIMA','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Missing TES & ARIMA');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'TES et ARIMA manquants');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Faltando TES e ARIMA');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Faltan TES y ARIMA');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.extrapolateUsingDefaultParams','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolate using default parameters');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapoler en utilisant les paramètres par défaut');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolar usando parâmetros padrão');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolar utilizando parámetros predeterminados');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.extrapolateUsingOptimizedArimaAndTes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolate with optimized ARIMA & TES');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapoler avec ARIMA et TES optimisés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapole com ARIMA e TES otimizados');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolar con ARIMA y TES optimizados');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.bulkExtrapolationSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Bulk Extraploation done successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extraploation groupée effectuée avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extraploação em massa realizada com sucesso');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extraploación masiva realizada con éxito');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.extrapolateTES&ARIMAUsingDefaultParams','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolate ARIMA & TES using default parameters');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapoler ARIMA & TES en utilisant les paramètres par défaut');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolar ARIMA e TES usando parâmetros padrão');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolar ARIMA y TES usando parámetros predeterminados');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.extrapolation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolations');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapolations');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolações');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolaciones');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolation.bulkExtrapolationNotPossible','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolation not possible as selected program planning units does not have enough data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapolation impossible car les unités de planification du programme sélectionnées ne disposent pas de suffisamment de données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A extrapolação não é possível porque as unidades de planejamento do programa selecionadas não possuem dados suficientes');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La extrapolación no es posible porque las unidades de planificación de programas seleccionadas no tienen datos suficientes');-- sp