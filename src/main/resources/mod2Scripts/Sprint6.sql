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


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Change from previous month'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Changement par rapport au mois précédent'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Cambio del mes anterior'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alteração do mês anterior'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Node Forecast (No seasonality)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Prévision de nœud (pas de saisonnalité)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Pronóstico de Nodo (Sin estacionalidad)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Previsão de nós (sem sazonalidade)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Node Forecast');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Node Forecast');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Node Forecast');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.levelDetails','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level Details');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails du niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles del nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes do nível');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.levelName','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do nível');-- pr

