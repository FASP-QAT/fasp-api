INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.import.doNoImportCheckbox','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do not import all unmapped planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ne pas importer toutes les unités de planification non mappées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No importe todas las unidades de planificación no asignadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não importe todas as unidades de planejamento não mapeadas');-- pr