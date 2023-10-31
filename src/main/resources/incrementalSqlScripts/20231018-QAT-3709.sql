INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addSuccessMessageAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click add branch to add the branch template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Ajouter une branche pour ajouter le modèle de branche à votre arborescence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han agregado correctamente. Haga clic en Agregar rama para agregar la plantilla de rama a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em adicionar ramo para adicionar o modelo de ramo à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addSuccessMessageSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click add branch without adding planning units to add the branch template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Ajouter une branche sans ajouter d`unités de planification pour ajouter le modèle de branche à votre arborescence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han agregado correctamente. Haga clic en agregar rama sin agregar unidades de planificación para agregar la plantilla de rama a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em adicionar ramificação sem adicionar unidades de planejamento para adicionar o modelo de ramificação à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.updateSuccessMessageAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been updated successfully. Please click add branch to add the branch template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été mises à jour avec succès. Veuillez cliquer sur Ajouter une branche pour ajouter le modèle de branche à votre arborescence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han actualizado correctamente. Haga clic en Agregar rama para agregar la plantilla de rama a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram atualizadas com sucesso. Clique em adicionar ramo para adicionar o modelo de ramo à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.updateSuccessMessageSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been updated successfully. Please click add branch without adding planning units to add the branch template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été mises à jour avec succès. Veuillez cliquer sur Ajouter une branche sans ajouter d`unités de planification pour ajouter le modèle de branche à votre arborescence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han actualizado correctamente. Haga clic en agregar rama sin agregar unidades de planificación para agregar la plantilla de rama a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram atualizadas com sucesso. Clique em adicionar ramificação sem adicionar unidades de planejamento para adicionar o modelo de ramificação à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.addSuccessMessageSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click create tree without adding planning units to add the tree template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Créer un arbre sans ajouter d`unités de planification pour ajouter le modèle d`arbre à votre arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han agregado correctamente. Haga clic en crear árbol sin agregar unidades de planificación para agregar la plantilla de árbol a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em criar árvore sem adicionar unidades de planejamento para adicionar o modelo de árvore à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.addSuccessMessageAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click create tree to add the tree template to your tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Créer un arbre pour ajouter le modèle d`arbre à votre arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han agregado correctamente. Haga clic en crear árbol para agregar la plantilla de árbol a su árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em criar árvore para adicionar o modelo de árvore à sua árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.listTree.addSuccessMessageAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click create tree to duplicate the tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Créer un arbre pour dupliquer l`arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han agregado correctamente. Haga clic en crear árbol para duplicar el árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em criar árvore para duplicar a árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.listTree.addSuccessMessageSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been added successfully. Please click create tree without adding planning unit to duplicate the tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été ajoutées avec succès. Veuillez cliquer sur Créer un arbre sans ajouter d`unité de planification pour dupliquer l`arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se agregaron correctamente. Haga clic en crear árbol sin agregar unidad de planificación para duplicar el árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram adicionadas com sucesso. Clique em criar árvore sem adicionar unidade de planejamento para duplicar a árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.listTree.updateSuccessMessageAll','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been updated successfully. Please click create tree to duplicate the tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été mises à jour avec succès. Veuillez cliquer sur Créer un arbre pour dupliquer l`arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han actualizado correctamente. Haga clic en crear árbol para duplicar el árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram atualizadas com sucesso. Clique em criar árvore para duplicar a árvore.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.listTree.updateSuccessMessageSelected','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your selected planning units have been updated successfully. Please click create tree without adding planning unit to duplicate the tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vos unités de planification sélectionnées ont été mises à jour avec succès. Veuillez cliquer sur Créer un arbre sans ajouter d`unité de planification pour dupliquer l`arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las unidades de planificación seleccionadas se han actualizado correctamente. Haga clic en crear árbol sin agregar unidad de planificación para duplicar el árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Suas unidades de planejamento selecionadas foram atualizadas com sucesso. Clique em criar árvore sem adicionar unidade de planejamento para duplicar a árvore.');-- pr