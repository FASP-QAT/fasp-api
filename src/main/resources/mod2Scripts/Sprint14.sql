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
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Construire un arbre similaire à un arbre existant ? Dupliquez un arbre existant en cliquant avec le bouton droit sur une ligne et en sélectionnant "dupliquer" l'édition. Si vous souhaitez conserver la structure de l'arbre constante et ne modifier que les nombres, créez un seul arbre et utilisez plutôt la fonction de scénario.');-- fr
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
