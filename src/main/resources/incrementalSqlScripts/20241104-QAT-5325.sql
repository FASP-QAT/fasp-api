INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.optimiseTESAndARIMASuccess','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Optimize TES & ARIMA done successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Optimisation de TES et ARIMA effectuée avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Optimización de TES y ARIMA realizada con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Otimização de TES e ARIMA feita com sucesso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.missingTESAndARIMASuccess','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Missing Extrapolation for TES & ARIMA done successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapolation manquante pour TES et ARIMA effectuée avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Falta extrapolación para TES y ARIMA realizada con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolação ausente para TES e ARIMA feita com sucesso');-- pr