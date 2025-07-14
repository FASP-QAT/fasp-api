update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Erro de previsão calculado usando erro percentual absoluto ponderado (WAPE). O erro exibido é calculado sempre com base na previsão total'
where l.LABEL_CODE='static.tooltip.ForecastError' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Erro de previsão calculado usando erro percentual absoluto ponderado (WAPE). O erro exibido é calculado sempre com base na previsão total'
where l.LABEL_CODE='static.tooltip.ForecastError' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Erreur de prévision calculée à l’aide du pourcentage d’erreur absolu pondéré (WAPE). L`erreur affichée est toujours calculée sur la base de la prévision totale'
where l.LABEL_CODE='static.tooltip.ForecastError' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Forecast Error calculated using Weighted Absolute Percentage Error (WAPE). The error displayed is calculated always based on the total forecast'
where l.LABEL_CODE='static.tooltip.ForecastError' and ll.LANGUAGE_ID=1;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.noOfMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of months included in calculation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois inclus dans le calcul');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses incluídos no cálculo');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses incluidos en el cálculo');-- sp
