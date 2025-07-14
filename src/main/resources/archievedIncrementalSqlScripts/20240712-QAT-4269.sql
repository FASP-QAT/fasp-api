INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.phaseInPhaseOut','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Phase In/Out Product');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Produit d’entrée/sortie prévu');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Producto previsto de entrada/salida gradual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão de entrada/saída do produto');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.endValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'End Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur finale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor final');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor final');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.whatIf.validEndValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter End Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la valeur finale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el valor final');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o valor final');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.whatIf.validStartValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Start Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la valeur de départ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el valor inicial');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o valor inicial');-- pr