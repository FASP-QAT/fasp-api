INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.dataEnteredInTableEx','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'e.g. If I enter 1,000 units, this will be converted into');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'par exemple. Si j`entre 1 000 unités, cela sera converti en');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'por exemplo. Se eu inserir 1.000 unidades, isso será convertido em');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'p.ej. Si ingreso 1,000 unidades, esto se convertirá en');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.planningUnits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'unidades de planejamento');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'unidades de planificación');-- sp
