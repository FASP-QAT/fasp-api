update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='This scenario name already exists. Please choose a name that is not a duplicate.'
where l.LABEL_CODE='static.tree.duplicateScenarioName' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ce nom de scénario existe déjà. Veuillez choisir un nom qui nest pas un doublon.'
where l.LABEL_CODE='static.tree.duplicateScenarioName' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Este nombre de escenario ya existe. Elija un nombre que no sea un duplicado.'
where l.LABEL_CODE='static.tree.duplicateScenarioName' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Este nome de cenário já existe. Escolha um nome que não seja duplicado.'
where l.LABEL_CODE='static.tree.duplicateScenarioName' and ll.LANGUAGE_ID=4;


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addScenario','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter un scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar cenário');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.editScenario','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit/Update Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier/Mettre à jour le scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar/Actualizar Escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar/atualizar cenário');-- pr