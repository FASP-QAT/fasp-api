INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecast.compareAndSelectRangeInfo','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Between the highest and lowest consumption forecast');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entre la prévision de consommation la plus élevée et la plus basse');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entre a previsão de maior e menor consumo');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Entre la previsión de consumo más alta y más baja');-- sp
