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
-- static.bulk.editAllEntity
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.editAllEntity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit All - {{entityname}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier tout - {{entityname}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar todo - {{entityname}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar tudo - {{entityname}}');-- pr

-- static.bulk.action
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.action','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Action');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Action');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Acción');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ação');-- pr

-- static.bulk.created
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.created','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Created');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Creado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criado');-- pr

-- static.bulk.edited
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.edited','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edited');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifié');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editado');-- pr

-- static.bulk.changes
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.changes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Details of Changes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails des modifications');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles de los cambios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes das alterações');-- pr

-- static.bulk.reviewChanges
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.reviewChanges','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Review Changes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vérifier les modifications');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Revisar cambios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Rever alterações');-- pr

-- static.bulk.reviewPrompt
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.reviewPrompt','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please review the proposed changes below. Click Okay to update or Cancel to return.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez vérifier les modifications ci-dessous. Cliquez sur Okay pour mettre à jour ou sur Annuler pour revenir.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, revise los cambios propuestos a continuación. Haga clic en Aceptar para actualizar o en Cancelar para regresar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, reveja as alterações propostas abaixo. Clique em Okay para atualizar ou em Cancelar para voltar.');-- pr

-- static.bulk.downloadTemplateBulkAdding
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.downloadTemplateBulkAdding','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Download template for bulk adding');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Télécharger le modèle pour l`ajout groupé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Descargar plantilla para agregar en masa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Baixar modelo para adição em massa');-- pr

-- static.bulk.uploadTemplate
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.uploadTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Upload Template');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Téléverser le modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Subir plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar modelo');-- pr

-- static.bulk.addRows
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.addRows','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Rows');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter des lignes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar filas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar linhas');-- pr

-- static.bulk.rowsAdded
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.rowsAdded','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'rows added');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'lignes ajoutées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'filas agregadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'linhas adicionadas');-- pr

-- static.bulk.importSuccess
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.importSuccess','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'rows successfully imported from template');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'lignes importées avec succès à partir du modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'filas importadas con éxito desde la plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'linhas importadas com sucesso a partir do modelo');-- pr

-- static.bulk.importError
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.bulk.importError','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Failed to parse template file. Please ensure it matches the downloaded template format.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Échec de l`analyse du fichier modèle. Veuillez vous assurer qu`il correspond au format du modèle téléchargé.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error al analizar el archivo de plantilla. Asegúrese de que coincida con el formato de plantilla descargado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Falha ao analisar o arquivo de modelo. Certifique-se de que corresponda ao formato do modelo baixado.');-- pr

-- static.program.viewProgramsUsingEntity
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.viewProgramsUsingEntity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View Programs using {{entityname}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les programmes utilisant {{entityname}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver programas que usan {{entityname}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ver programas usando {{entityname}}');-- pr

-- static.program.programsUsingEntity
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.programsUsingEntity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Programs using {{entityname}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmes utilisant {{entityname}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programas que usan {{entityname}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programas usando {{entityname}}');-- pr

-- static.program.noProgramsFound
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.noProgramsFound','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No programs are currently using this unit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun programme n`utilise actuellement cette unité.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualmente ningún programa está utilizando esta unidad.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum programa está usando esta unidade no momento.');-- pr
