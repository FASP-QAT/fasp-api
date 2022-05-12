INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,"static.importIntoQATSupplyPlan.conversionFactor","1");
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor (Forecast Planning Unit to Supply Plan)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (prévision en plan dapprovisionnement)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (previsão para plano de fornecimento)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (pronóstico a plan de suministro)');
