INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.conversionToPu','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion to PU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion en PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão para PU');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión a PU');-- sp	


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.conversionToFu','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion to FU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conversion en FU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conversão para FU');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conversión a FU');-- sp
