INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (2, '/api/planningUnit/bulk', 'ROLE_BF_EDIT_PLANNING_UNIT');

INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (2, '/api/planningUnit/bulk', 'ROLE_BF_ADD_PLANNING_UNIT');

INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (2, '/api/forecastingUnit/bulk', 'ROLE_BF_EDIT_FORECASTING_UNIT');

INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (2, '/api/forecastingUnit/bulk', 'ROLE_BF_ADD_FORECASTING_UNIT');

-- static.planningUnit.bulkEdit
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.planningUnit.bulkEdit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Bulk Edit / Create Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Bulk Edit / Create Planning Units');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Bulk Edit / Create Planning Units');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Bulk Edit / Create Planning Units');-- pr

-- static.forecastingUnit.bulkEdit
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastingUnit.bulkEdit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Bulk Edit / Create Forecasting Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Bulk Edit / Create Forecasting Units');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Bulk Edit / Create Forecasting Units');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Bulk Edit / Create Forecasting Units');-- pr

-- static.bulk.editAll
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.editAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit All');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier tout');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar todo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar tudo');-- pr

-- static.bulk.newCreatedName
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.newCreatedName','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Newly Created Unit Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom de l`unité nouvellement créée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre de la unidad recién creada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome da unidade recém-criada');-- pr

-- static.bulk.submissionSummary
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.submissionSummary','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Bulk Submission Summary');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Résumé de soumission en masse');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Resumen de envío masivo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Resumo do envio em massa');-- pr

-- static.bulk.unitsEdited
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.unitsEdited','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of Units Edited');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d`unités modifiées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de unidades editadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de unidades editadas');-- pr

-- static.bulk.unitsCreated
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.unitsCreated','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of Units Created');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d`unités créées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de unidades creadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de unidades criadas');-- pr

-- static.bulk.duplicateInSheet
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.duplicateInSheet','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate unit name within the table');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d`unité en double dans le tableau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre de unidad duplicado en la tabla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de unidade duplicado na tabela');-- pr

-- static.bulk.validationErrors
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.validationErrors','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Validation Errors Found Across Pages');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreurs de validation trouvées sur les pages');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Errores de validación encontrados en las páginas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erros de validação encontrados nas páginas');-- pr

-- static.bulk.goToPage
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.goToPage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Go to Page');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aller à la page');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ir a la página');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ir para a página');-- pr

-- static.bulk.noChanges
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.noChanges','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No changes have been made to submit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune modification à soumettre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay cambios para enviar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma alteração a enviar.');-- pr

-- static.bulk.row
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.row','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Row');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fila');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Linha');-- pr

-- static.bulk.page
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.page','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Page');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Page');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Página');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Página');-- pr

-- static.bulk.id
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.id','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ID');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ID');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'ID');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'ID');-- pr

-- static.bulk.name
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.name','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome');-- pr
