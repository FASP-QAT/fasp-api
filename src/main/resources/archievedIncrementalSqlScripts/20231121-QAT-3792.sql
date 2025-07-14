INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyplan.inconsistent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Found inconsistency. Please refresh the page.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Incohérence trouvée. Veuillez actualiser la page.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Inconsistencia encontrada. Por favor actualice la página.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inconsistência encontrada. Atualize a página.'); -- pr


