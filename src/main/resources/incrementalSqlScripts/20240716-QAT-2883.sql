INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.funderTypeHead.funderType','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding Source Type');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de source de financement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de fonte de financiamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de fuente de financiamiento');-- sp



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.funderType.funderTypeName','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding Source Type Name');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du type de source de financement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do tipo de fonte de financiamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del tipo de fuente de financiamiento');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.funderType.funderTypeCode','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding Source Type Display Name');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d’affichage du type de source de financement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de exibição do tipo de fonte de financiamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de fuente de financiación Nombre para mostrar');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.funderType.funderTypeNameText','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding Source Type name is required');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nom du type de source de financement est requis');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O nome do tipo de fonte de financiamento é obrigatório');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El nombre del tipo de fuente de financiamiento es obligatorio');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.funderType.funderTypeCodeText','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding Source Type display name is required');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nom d’affichage du type de source de financement est requis');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O nome de exibição do tipo de fonte de financiamento é obrigatório');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El nombre para mostrar del tipo de fuente de financiación es obligatorio');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.shipment.groupByFundingSourceType','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Group by Funding Source Type');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Regrouper par type de source de financement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agrupar por tipo de fonte de financiamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agrupar por tipo de fuente de financiamiento');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.report.fundingSourceTypeUsdAmount','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'* The funding source type amount is in USD');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'* Le montant du type de source de financement est en USD');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'* O valor do tipo de fonte de financiamento está em USD');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'*El monto del tipo de fuente de financiamiento es en USD');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.shipment.shipmentFundingSourceType','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipment Funding Source Type');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de source de financement des expéditions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de fonte de financiamento da remessa');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de fuente de financiación del envío');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.shipment.selectFundingSourceType','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Funding Source Type');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez le type de source de financement');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o tipo de fonte de financiamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el tipo de fuente de financiamiento');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.shipment.groupBy','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Group By');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par groupe');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agrupar por');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agrupar por');-- sp