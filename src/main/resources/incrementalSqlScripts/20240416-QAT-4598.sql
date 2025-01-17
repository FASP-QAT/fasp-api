INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.importIntoSP','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import Into Supply Plan'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importer dans le plan d`approvisionnement'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importar al plan de suministro'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importar para o plano de fornecimento'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoSP.forecastBlank','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast is blank'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La prévision est vide'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El pronóstico está en blanco'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A previsão está em branco'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoSP.dataExists','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data already exists in Supply Plan Program'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les données existent déjà dans le programme du plan d`approvisionnement'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los datos ya existen en el programa del plan de suministro.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os dados já existem no Programa Plano de Fornecimento'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoSP.dataExistsGray','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data exists in Supply Plan Program and is past {{month}} months, so it cannot be imported.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les données existent dans le programme du plan d`approvisionnement et sont passées {{month}} mois, il ne peut donc pas être importé.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los datos existen en el programa del plan de suministro y ya pasaron {{month}} meses, por lo que no se puede importar.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os dados existem no Programa Plano de Fornecimento e são passados {{month}} meses, portanto não pode ser importado.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMonthlyErrorReport.showConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show consumption adjusted for stock out?'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher la consommation ajustée aux ruptures de stock ?'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Mostrar el consumo ajustado por desabastecimiento?'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar consumo ajustado por ruptura de estoque?'); -- pr
