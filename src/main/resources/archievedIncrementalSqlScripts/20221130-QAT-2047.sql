INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastErrorReport.noEquivalencyUnitData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No equivalency unit data available for the selected forecasting unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune donnée d`unité d`équivalence disponible pour l`unité de prévision sélectionnée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum dado de unidade de equivalência disponível para a unidade de previsão selecionada');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay datos de unidad de equivalencia disponibles para la unidad de pronóstico seleccionada');-- sp
