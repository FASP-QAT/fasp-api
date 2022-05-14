SET FOREIGN_KEY_CHECKS=0;
TRUNCATE table rm_tree_template_node_data_pu;
TRUNCATE table rm_tree_template_node_data_fu;
TRUNCATE table rm_tree_template_node_data_modeling;
TRUNCATE table rm_tree_template_node_data_override;
TRUNCATE table rm_tree_template_node_data;
TRUNCATE table rm_tree_template_node;
TRUNCATE table rm_tree_template;
TRUNCATE table rm_equivalency_unit_health_area;
TRUNCATE table rm_equivalency_unit_mapping;
TRUNCATE table rm_equivalency_unit;
SET FOREIGN_KEY_CHECKS=1;
update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='The minimum values needed for the various features are below:\n* TES, Holt-Winters: At least 24 months of historical data\n* ARIMA: With seasonality : At least 13 months historical data required.Without seasonality : At least 2 months historical data required.\n* Moving Average, Semi-Averages, and Linear Regression: At least 3 months of historical data'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Les valeurs minimales nécessaires pour les différentes fonctionnalités sont les suivantes :\n* TES, Holt-Winters : au moins 24 mois de données historiques\n* ARIMA : avec saisonnalité : au moins 13 mois de données historiques requises. Sans saisonnalité :  au moins 2 mois dhistorique données requises.\n* Moyenne mobile, semi-moyennes et régression linéaire : au moins 3 mois de données historiques'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Los valores mínimos necesarios para las distintas características son los siguientes:\n* TES, Holt-Winters: al menos 24 meses de datos históricos\n* ARIMA: con estacionalidad: se requieren al menos 13 meses de datos históricos. Sin estacionalidad: al menos 2 meses de historial datos requeridos.\n* Promedio móvil, semipromedios y regresión lineal: al menos 3 meses de datos históricos'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Os valores mínimos necessários para os vários recursos estão abaixo:\n* TES, Holt-Winters: pelo menos 24 meses de dados históricos\n* ARIMA: com sazonalidade: pelo menos 13 meses de dados históricos necessários.Sem sazonalidade : pelo menos 2 meses de histórico dados necessários.\n* Média móvel, semi-médias e regressão linear: pelo menos três meses de dados históricos'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=4;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.minDataRequiredToExtrapolateNote1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'NOTE: You have ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'REMARQUE : Vous avez ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'NOTA: Tienes ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'NOTA: Você tem ');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.minDataRequiredToExtrapolateNote2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' month(s) of historical data.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' mois de données historiques.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' mes(es) de datos históricos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' mês(s) de dados históricos.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,"static.importIntoQATSupplyPlan.conversionFactor","1");
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor (Forecast Planning Unit to Supply Plan)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (prévision en plan dapprovisionnement)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (previsão para plano de fornecimento)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (pronóstico a plan de suministro)');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.manageTreePage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to move to manage tree page?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous passer à la page de gestion de l arborescence ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres pasar a administrar la página del árbol?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Deseja mover para gerenciar a página da árvore?');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.deleteTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Are you sure you want to delete this tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous vraiment supprimer cet arbre ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Estás seguro de que quieres eliminar este árbol?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tem certeza de que deseja excluir esta árvore.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.manageTreeTreeList','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage Tree – Tree list');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer l arborescence – Liste des arborescences');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrar árbol: lista de árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar Árvore - Lista de Árvores');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.purpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Purpose');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'But');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Objetivo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Objetivo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.enableUsersTo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enable users to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permettre aux utilisateurs de');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permitir a los usuarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permita que os usuários');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.listExistingTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View a list of their existing trees');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voir une liste de leurs arbres existants');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver una lista de sus árboles existentes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ver uma lista de suas árvores existentes');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.editExistingTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit an existing tree by clicking any row');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier un arbre existant en cliquant sur n importe quelle ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Edite un árbol existente haciendo clic en cualquier fila');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Edite uma árvore existente clicando em qualquer linha');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.deleteDuplicateExistingTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete or duplicate existing trees by right clicking on a row');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer ou dupliquer des arbres existants en faisant un clic droit sur une ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elimine o duplique árboles existentes haciendo clic derecho en una fila');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir ou duplicar árvores existentes clicando com o botão direito em uma linha');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.newTreeToLoadedProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add a new tree to a loaded program by clicking on the Add Tree dropdown in the top right corner of the screen. New trees can be built:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajoutez un nouvel arbre à un programme chargé en cliquant sur le menu déroulant Ajouter un arbre dans le coin supérieur droit de l écran. De nouveaux arbres peuvent être construits :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregue un nuevo árbol a un programa cargado haciendo clic en el menú desplegable Agregar árbol en la esquina superior derecha de la pantalla. Se pueden construir nuevos árboles:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicione uma nova árvore a um programa carregado clicando no menu suspenso Adicionar árvore no canto superior direito da tela. Novas árvores podem ser construídas:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.manuallySelectAddTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'manually - select Add Tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'manuellement - sélectionnez Ajouter un arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'manualmente - seleccione Agregar árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'manualmente - selecione Adicionar árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.nameOfDesiredTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'from a tree template - select the name of the desired template.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'à partir d un modèle d arborescence - sélectionnez le nom du modèle souhaité.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'de una plantilla de árbol: seleccione el nombre de la plantilla deseada.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'de um modelo de árvore - selecione o nome do modelo desejado.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.useThisScreen','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Using this screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisation de cet écran');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usando esta pantalla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usando esta tela');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.loadedToBuildTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A forecast program must first be loaded in order to build a tree (either manually or from a tree template.)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un programme de prévision doit d abord être chargé afin de construire un arbre (soit manuellement, soit à partir d un modèle d arbre.)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Primero se debe cargar un programa de pronóstico para construir un árbol (ya sea manualmente o desde una plantilla de árbol).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Um programa de previsão deve primeiro ser carregado para construir uma árvore (manualmente ou a partir de um modelo de árvore).');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.addForecastPlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Before building and editing a tree, first add the forecast program s planning units in the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Avant de construire et de modifier un arbre, ajoutez d abord les unités de planification du programme de prévision dans la');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Antes de construir y editar un árbol, primero agregue las unidades de planificación del programa de pronóstico en el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Antes de construir e editar uma árvore, primeiro adicione as unidades de planejamento do programa de previsão na');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.screenBeforeBuilding','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen before building');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'écran avant construction');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla antes de construir');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela antes de construir');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.buildSimilarTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Building a tree similar to an existing tree? Duplicate an existing tree by right clicking on a row and selecting “duplicate” edit. If you want to keep the structure of the tree constant and only change the numbers, build only one tree and use the scenario feature instead.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Construire un arbre similaire à un arbre existant ? Dupliquez un arbre existant en cliquant avec le bouton droit sur une ligne et en sélectionnant "dupliquer" l édition. Si vous souhaitez conserver la structure de l arbre constante et ne modifier que les nombres, créez un seul arbre et utilisez plutôt la fonction de scénario.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Construyendo un árbol similar a un árbol existente? Duplica un árbol existente haciendo clic con el botón derecho en una fila y seleccionando la edición "duplicar". Si desea mantener constante la estructura del árbol y solo cambiar los números, cree solo un árbol y use la función de escenario en su lugar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Construir uma árvore semelhante a uma árvore existente? Duplique uma árvore existente clicando com o botão direito do mouse em uma linha e selecionando “duplicar” editar. Se você quiser manter a estrutura da árvore constante e alterar apenas os números, construa apenas uma árvore e use o recurso de cenário.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.submitHelpDeskTicket','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Submit a HelpDesk ticket if there is a missing template that would benefit the QAT community.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Soumettez un ticket HelpDesk s il manque un modèle qui profiterait à la communauté QAT.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíe un ticket de HelpDesk si falta una plantilla que beneficiaría a la comunidad de QAT.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Envie um tíquete de HelpDesk se houver um modelo ausente que beneficie a comunidade QAT.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.treeDetails','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Details');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails de l arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles del árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes da árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTree.missingPlanningUnits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Missing Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification manquantes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de planificación faltantes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de planejamento ausentes');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTreeTemp.templateName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Template Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre de la plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do modelo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.listTreeTemp.templateDetails','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Template Details');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails du modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles de la plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes do modelo');-- pr


-- 2022-05-12

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitTree.noPUorFUMapping','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You cannot commit tree with PU nodes/FU nodes that do have Planning unit/Forecasting unit mapped to it');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous ne pouvez pas valider l`arborescence avec des nœuds PU/FU qui ont une unité de planification/unité de prévision mappée dessus');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No puede comprometer el árbol con nodos PU/nodos FU que tienen asignada una unidad de planificación/unidad de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você não pode consolidar a árvore com nós PU/nós FU que tenham unidade de planejamento/unidade de previsão mapeada para ela');-- pr

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());


delete u.* from us_role_business_function u where u.ROLE_ID='ROLE_PROGRAM_ADMIN' and u.BUSINESS_FUNCTION_ID='ROLE_BF_FORECASTING_MODULE';
delete u.* from us_role_business_function u where u.ROLE_ID='ROLE_PROGRAM_USER' and u.BUSINESS_FUNCTION_ID='ROLE_BF_FORECASTING_MODULE';

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_USER','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_REALM_COUNTRY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_ORGANIZATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_ORGANIZATION_TYPE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_FORECASTING_UNIT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_PLANNING_UNIT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_PRODUCT_CATEGORY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_PLANNING_UNIT_CAPACITY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_TRACER_CATEGORY','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_HEALTH_AREA','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_LIST_TREE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_ADMIN','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());





INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_REALM_COUNTRY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_ORGANIZATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_ORGANIZATION_TYPE','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_FORECASTING_UNIT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_PLANNING_UNIT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_PRODUCT_CATEGORY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_PLANNING_UNIT_CAPACITY','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_TRACER_CATEGORY','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_HEALTH_AREA','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_VERSION_SETTINGS','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_IMPORT_DATASET','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_EXPORT_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LOAD_DELETE_DATASET','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_COMMIT_DATASET','1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_EXTRAPOLATION','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_LIST_TREE_TEMPLATE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_MODELING_VALIDATION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_PRODUCT_VALIDATION','1',NOW(),'1',NOW());



INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_COMPARE_AND_SELECT','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_COMPARE_VERSION','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_DATASET_USER','ROLE_BF_CONSUMPTION_FORECAST_ERROR','1',NOW(),'1',NOW());

delete b.* from us_role_business_function b where b.ROLE_ID='ROLE_PROGRAM_ADMIN' and find_in_set(b.BUSINESS_FUNCTION_ID,'ROLE_BF_COMMIT_DATASET,ROLE_BF_COMPARE_AND_SELECT,ROLE_BF_COMPARE_VERSION,ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT,ROLE_BF_CONSUMPTION_FORECAST_ERROR,ROLE_BF_EXPORT_DATASET,ROLE_BF_EXTRAPOLATION,ROLE_BF_IMPORT_DATASET,ROLE_BF_LOAD_DELETE_DATASET,ROLE_BF_MODELING_VALIDATION,ROLE_BF_PRODUCT_VALIDATION,ROLE_BF_VERSION_SETTINGS');

delete b.* from us_role_business_function b where b.ROLE_ID='ROLE_PROGRAM_USER' and find_in_set(b.BUSINESS_FUNCTION_ID,'ROLE_BF_COMMIT_DATASET,ROLE_BF_COMPARE_AND_SELECT,ROLE_BF_COMPARE_VERSION,ROLE_BF_CONSUMPTION_DATA_ENTRY_ADJUSTMENT,ROLE_BF_CONSUMPTION_FORECAST_ERROR,ROLE_BF_EXPORT_DATASET,ROLE_BF_EXTRAPOLATION,ROLE_BF_IMPORT_DATASET,ROLE_BF_LOAD_DELETE_DATASET,ROLE_BF_MODELING_VALIDATION,ROLE_BF_PRODUCT_VALIDATION,ROLE_BF_VERSION_SETTINGS');

USE `fasp`;
DROP procedure IF EXISTS `getForecastError`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getForecastError`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getForecastError`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT (10), VAR_VIEW_BY INT(10), VAR_UNIT_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REGION_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT(10))
BEGIN
	SET @programId = VAR_PROGRAM_ID; 
    SET @versionId = VAR_VERSION_ID;
    SET @unitId= VAR_UNIT_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @viewBy = VAR_VIEW_BY;
    SET @regionIds = VAR_REGION_IDS;
    SET @equivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID;
    
    IF @versionId = -1 THEN 
        SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;

    SELECT 
 		r.REGION_ID, r.LABEL_ID, r.LABEL_EN, r.LABEL_FR, r.LABEL_SP, r.LABEL_PR,
 		ct.CONSUMPTION_DATE, 
 		SUM(IF(ct.ACTUAL_FLAG=0, ct.CONSUMPTION_QTY, null)) `FORECAST_QTY`, SUM(IF(ct.ACTUAL_FLAG=0,ct.DAYS_OF_STOCK_OUT, null)) `FORECAST_DAYS_OF_STOCK_OUT`,
 		SUM(IF(ct.ACTUAL_FLAG=1, ct.CONSUMPTION_QTY, null)) `ACTUAL_QTY`, SUM(IF(ct.ACTUAL_FLAG=1,ct.DAYS_OF_STOCK_OUT, null)) `ACTUAL_DAYS_OF_STOCK_OUT`,
 		COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU) `CONVERT_TO_EU`
 	FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID) tc 
 		LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
 		LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
 		LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
 		LEFT JOIN rm_program p ON p.PROGRAM_ID=@programId
 		LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
		LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
		LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
		LEFT JOIN rm_equivalency_unit_mapping eum1 ON fu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum1.REALM_ID=rc.REALM_ID AND eum1.PROGRAM_ID=@programId
		LEFT JOIN rm_equivalency_unit_mapping eum2 ON fu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.EQUIVALENCY_UNIT_ID=@equivalencyUnitId AND eum2.REALM_ID=rc.REALM_ID AND eum2.PROGRAM_ID is null
	WHERE ct.CONSUMPTION_DATE BETWEEN @startDate and @stopDate AND (@regionIds='' OR FIND_IN_SET(ct.REGION_ID,@regionIds)) AND ((@viewBy=1 AND ct.PLANNING_UNIT_ID=@unitId) OR (@viewBy=2 AND pu.FORECASTING_UNIT_ID=@unitId))
	GROUP BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE;
    
END$$

DELIMITER ;
;

select min(s.STATIC_LABEL_ID) into @min from ap_static_label s where s.LABEL_CODE='static.tooltip.LagInMonth';

delete l.* from ap_static_label_languages l where l.STATIC_LABEL_ID=@min;
delete l.* from ap_static_label l where l.STATIC_LABEL_ID=@min;

select min(s.STATIC_LABEL_ID) into @min from ap_static_label s where s.LABEL_CODE='static.common.dataEnteredIn';

delete l.* from ap_static_label_languages l where l.STATIC_LABEL_ID=@min;
delete l.* from ap_static_label l where l.STATIC_LABEL_ID=@min;


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.compareAndSelectForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare And Select Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer et sélectionner les prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar y seleccionar pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Compare e selecione a previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.totalForecastQuantity','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Forecasted Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale prévue');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total pronosticada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total prevista');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.stockOrUnmedDemand','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock or Unmet Demand');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock ou demande non satisfaite');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock o Demanda Insatisfecha');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque ou Demanda Não Atendida');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.differenceBetweenMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Difference between months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Différence entre les mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Diferencia entre meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Diferença entre meses');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.usageTemplate.persons','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Person(s)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# Personne(s)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# persona(s)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# Pessoas)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.usageTemplate.personsUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Person(s) Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Personne(s) Unité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de persona(s)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de pessoa(s)');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.usageTemplate.timesPerFrequency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of times/Frequency');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# de fois/Fréquence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de veces/Frecuencia');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nº de vezes/Frequência');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.usageTemplate.periodUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Period Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de période');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Periodo Unidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Período');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.stringNotAllowed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'String value not allowed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur de chaîne non autorisée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de cadena no permitido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor da string não permitido');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.decimalNotAllowed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Decimal value not allowed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur décimale non autorisée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor decimal no permitido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor decimal não permitido');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.10digitWholeNumber','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter 10 digit whole number');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un nombre entier à 10 chiffres');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor ingrese un número entero de 10 dígitos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número inteiro de 10 dígitos');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.max99MonthAllowed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Maximum 99 months are allowed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un maximum de 99 mois est autorisé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permite un máximo de 99 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Máximo de 99 meses são permitidos');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.negativeValueNotAllowed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Negative value not allowed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur négative non autorisée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor negativo no permitido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor negativo não permitido');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.max10Digit4AfterDecimal','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 10 digit number and 4 digits after decimal are allowed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 10 chiffres et 4 chiffres après la virgule sont autorisés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 10 dígitos y 4 dígitos después del decimal.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número máximo de 10 dígitos e 4 dígitos após decimal são permitidos.');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.stockEndOf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock (end of');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock (fin de');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Existencias (fin de');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque (final de');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.existingShipments','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Existing Shipments (');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions existantes (');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos existentes (');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas existentes (');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSetting.desiredMonthsOfStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Months of Stock (end of');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock souhaités (fin');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses deseados de existencias (final de');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de Estoque Desejados (final de');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.equivalencyUnit.duplicateEUFUP','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate equivalency unit, forecasting unit, program combination not allow');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité d équivalence en double, unité de prévision, combinaison de programmes non autorisée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de equivalencia duplicada, unidad de previsión, combinación de programas no permitida');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de equivalência duplicada, unidade de previsão, combinação de programa não permitida');-- pr

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Version Comparison' WHERE `STATIC_LABEL_LANGUAGE_ID`='199818'; 