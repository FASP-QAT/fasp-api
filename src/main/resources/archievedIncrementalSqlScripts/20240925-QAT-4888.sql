INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoSupplyPlan.forecastRestrictionNotes','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast consumption cannot be imported when forecast period is over.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La consommation prévue ne peut pas être importée une fois la période de prévision terminée.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O consumo previsto não pode ser importado quando o período de previsão terminar.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El consumo previsto no se puede importar cuando finaliza el período de pronóstico.');-- sp
