INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.runDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Run Date:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date d`exécution:');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha de ejecución:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de execução:');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.runTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Run Time:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Durée:');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiempo de ejecución:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo de execução:');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.v','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'v');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'v');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'v');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'v');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.approved','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ordered (Submitted, Approved)​');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Commandé (soumis, approuvé)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pedido (enviado, aprobado)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Solicitado (enviado, aprovado)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qty','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cant.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Qty');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.funding','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Financement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fondos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Financiamento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.procAgent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Proc Agent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agent Proc');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agente de proceso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agente Proc');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.adj','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adj');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Adj');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Adj.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adj');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.max','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Max');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Max');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Máx.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.past','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'past');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'passée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pasada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'passado');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.currentAndFuture','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'current + future');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'actuel + futur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'presente + futuro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'atual + futuro');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.consumptionMsg','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption (* indicates actual)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation (* indique le réel)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo (* indica real)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo (* indica real)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.inventoryMsg','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock (* indicates adjustment)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock (* indique l`ajustement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock (* indica ajuste)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque (* indica ajuste)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.notesDetailsCon','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'DD-MMM-YY (Region | Data Source) Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'JJ-MMM-AA (Région | Source de données) Remarques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'DD-MMM-YY (Región | Fuente de datos) Notas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'DD-MMM-AA (Região | Fonte de Dados) Notas');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.notesDetailsShip','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'DD-MMM-YY (Data Source) Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'JJ-MMM-AA (source de données) Remarques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de DD-MMM-YY (fuente de datos)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'DD-MMM-AA (fonte de dados) Notas');-- pr