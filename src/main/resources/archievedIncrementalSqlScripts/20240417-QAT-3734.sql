INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.alternateReportingUnit.multiply','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multiply');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Multiplier');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Multiplicar');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Multiplicar');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.alternateReportingUnit.divide','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Divide');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Diviser');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dividir');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Dividir');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.unit.conversionMethod','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Method');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Méthode de conversion');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Método de conversão');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Método de conversión');-- sp
