INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.estimateTimeBulkExtrapolation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Estimated time for bulk extrapolation in seconds : ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Temps estimé pour l`extrapolation en masse (en secondes) : ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiempo estimado para la extrapolación masiva en segundos: ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo estimado para extrapolação em massa em segundos: ');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.estimateTimeOptimiseTESAndArmia','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Estimated time for Optimize TES & ARIMA in seconds : ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Temps estimé pour l`optimisation des TES et ARIMA (en secondes) : ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiempo estimado para optimizar TES y ARIMA en segundos: ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo estimado para otimizar o TES e o ARIMA em segundos: ');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.estimateTimeMissingTes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Estimated time for Missing TES & ARIMA in seconds : ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Temps estimé pour l`absence des TES et ARIMA (en secondes) : ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiempo estimado para la falta de TES y ARIMA en segundos: ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo estimado para TES e ARIMA em falta em segundos: ');-- pr