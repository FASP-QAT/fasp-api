INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.changeAMC','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change AMC');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changer l`AMC');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambiar AMC');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alterar AMC');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.monthInPastValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select Months in Past for AMC');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner les mois passés pour AMC.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione Meses pasados para AMC');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione meses anteriores para AMC');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.monthInFutureValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select Months in Future for AMC');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner les mois à venir pour AMC');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione Meses en el futuro para AMC');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione Meses no Futuro para AMC');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.whatIf.removePlannedShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Remove all Planned shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer toutes les expéditions planifiées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar todos los envíos planificados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remover todas as remessas planejadas');-- pr

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Remove only those shipments that do not comply with the lead time' where ap_static_label.LABEL_CODE='static.whatIf.removePlannedShipmentsNotInLeadTimes' 
and ap_static_label_languages.LANGUAGE_ID=1;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Supprimez uniquement les expéditions qui ne respectent pas les délais' where ap_static_label.LABEL_CODE='static.whatIf.removePlannedShipmentsNotInLeadTimes' 
and ap_static_label_languages.LANGUAGE_ID=2;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Retire sólo aquellos envíos que no cumplan con el plazo de entrega' where ap_static_label.LABEL_CODE='static.whatIf.removePlannedShipmentsNotInLeadTimes' 
and ap_static_label_languages.LANGUAGE_ID=3;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Remova apenas as remessas que não cumprem o prazo de entrega' where ap_static_label.LABEL_CODE='static.whatIf.removePlannedShipmentsNotInLeadTimes' 
and ap_static_label_languages.LANGUAGE_ID=4;

