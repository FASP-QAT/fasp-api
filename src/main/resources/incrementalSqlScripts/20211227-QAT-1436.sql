INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.readonlyData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Readonly Data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données en lecture seule');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos de solo lectura');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados somente leitura');



