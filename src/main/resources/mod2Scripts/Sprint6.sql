INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.message.importSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import successful');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importation réussie');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importación exitosa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importação bem-sucedida');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.tracerCategoryInvalidSelection','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Selected Forcasting unit`s Tracer category does not match');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La catégorie Tracer de l`unité de prévision sélectionnée ne correspond pas');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La categoría Tracer de la unidad de pronóstico seleccionada no coincide');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A categoria Tracer da unidade de previsão selecionada não corresponde');-- pr
