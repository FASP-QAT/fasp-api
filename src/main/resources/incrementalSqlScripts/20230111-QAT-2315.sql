INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.hidePlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar Unidade de Planejamento');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar unidad de planificación');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.hideFUAndPU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Forecasting Unit & Planning Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer l`unité de prévision et l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar unidade de previsão e unidade de planejamento');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar unidad de previsión y unidad de planificación');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.hideTreeValidation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Tree Validation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer la validation de l`arborescence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar validação da árvore');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar validación de árbol');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.autoCalculate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto Calculate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul automatique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calcular automaticamente');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calcular automáticamente');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.displayDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date d`affichage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de Exibição');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar fecha');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.conversionFUPU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion (FU:PU)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion (FU:PU)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão (FU:PU)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión (FU:PU)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.extrapolate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Extrapoler');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Extrapolar');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Extrapolar');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.startMonthForHistoricData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Month for Historical Data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de début pour les données historiques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mês de início para dados históricos');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mes de inicio para datos históricos');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.startMonthForHistoricData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change the start month, please go back to the Node Data screen and change the month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour changer le mois de début, veuillez revenir à l`écran Node Data et changer le mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar o mês de início, volte para a tela de dados do nó e altere o mês');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar el mes de inicio, vuelva a la pantalla Datos del nodo y cambie el mes');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.tripleExponentialSmoothing','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Triple-Exponential Smoothing (Holts-Winters)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lissage triple exponentiel (Holts-Winters)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suavização Exponencial Tripla (Holts-Winters)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suavizado triple exponencial (Holts-Winters)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.chooseMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose Method');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez la méthode');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o método');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija el método');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.interpolate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Interpolate');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Interpoler');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Interpolar');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Interpolar');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.option.extrapolationMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select extrapolation method');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez la méthode d`extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o método de extrapolação');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar método de extrapolación');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.QATForecastImport.perOfForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% of Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% de la prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% da previsão');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% de previsión');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.QATForecastImport.forcastConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasted Consumption (Forecast Module)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation prévue (module de prévision)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Previsto (Módulo Previsão)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo previsto (módulo de previsión)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.QATForecastImport.convertedForecastConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Converted Forecasted Consumption (to be imported)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation prévisionnelle convertie (à importer)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Previsto Convertido (a ser importado)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo previsto convertido (a importar)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.QATForecastImport.currentForecastConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Current Forecasted Consumption (Supply Plan Module)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation actuelle prévue (module Plan d`approvisionnement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Atual Previsto (Módulo Plano de Suprimentos)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo Pronosticado Actual (Módulo Plan de Abastecimiento)');-- sp
