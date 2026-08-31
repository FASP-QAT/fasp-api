INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoSP.forecastBlank','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast is blank'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La prévision est vide'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El pronóstico está en blanco'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A previsão está em branco'); -- pr