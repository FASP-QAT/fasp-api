INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.import.percentOfSupplyPlan','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% of Supply Plan');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% du plan d\'approvisionnement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% do Plano de Fornecimento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% del plan de suministro');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.import.forecastRegionFor','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Region for');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Région de prévision pour');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Região de previsão para');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Región de pronóstico para');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importFromQATSupplyPlan.supplyPlanProgrmRegion','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Program (Region)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme du plan d\'approvisionnement (région)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa Plano de Fornecimento (Região)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa Plan de Abastecimiento (Región)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importFromQATSupplyPlan.forecastProgramRegion','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Program (Region)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme de prévisions (région)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa de Previsão (Região)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa de pronóstico (región)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.percentOfSupplyPlanInfo','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage of monthly actual consumption quantity from the Supply Plan program that will be imported into the Forecast program');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de la quantité de consommation réelle mensuelle du programme Plan d\'approvisionnement qui sera importée dans le programme Prévisions');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem da quantidade de consumo real mensal do programa Plano de Fornecimento que será importada para o programa Previsão');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de la cantidad de consumo real mensual del programa Plan de suministro que se importará al programa Pronóstico');-- sp