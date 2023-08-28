/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 28-Aug-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.createTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create Tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer un arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar árvore');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.createTreeWithoutPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create Tree Without Adding Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer une arborescence sans ajouter d`unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear árbol sin agregar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar árvore sem adicionar unidades de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addAbovePUs','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add selected planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter les unités de planification sélectionnées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar unidades de planificación seleccionadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar unidades de planejamento selecionadas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.youMustBeOnlineToCreatePU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: You must be online to create planning units.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Vous devez être en ligne pour créer des unités de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Debe estar en línea para crear unidades de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: você deve estar on-line para criar unidades de planejamento.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.allTemplatePUAreInProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All template Planning Units are in the program, active, and checked for Tree Forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Toutes les unités de planification modèles sont dans le programme, actives et vérifiées pour la prévision d`arborescence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todas las unidades de planificación de plantilla están en el programa, activas y verificadas para el pronóstico del árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todas as unidades de planejamento modelo estão no programa, ativas e verificadas para Tree Forecast.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.updateSelectedPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update selected planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour les unités de planification sélectionnées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar unidades de planificación seleccionadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar unidades de planejamento selecionadas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addBranchWithoutPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Branch Without Adding Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter une branche sans ajouter d`unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar sucursal sin agregar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar filial sem adicionar unidades de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.addBranch','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Branch');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter une succursale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar sucursal');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar filial');-- pr

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Planning Units that are missing, inactive or unchecked for Tree Forecast in current program: (To see full list of current Planning Units, see'
where l.LABEL_CODE='static.listTree.missingPlanningUnits' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Unités de planification manquantes, inactives ou non cochées pour la prévision d`arborescence dans le programme actuel : (Pour voir la liste complète des unités de planification actuelles, voir'
where l.LABEL_CODE='static.listTree.missingPlanningUnits' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Unidades de planificación que faltan, están inactivas o no están marcadas para el Pronóstico de árbol en el programa actual: (Para ver la lista completa de las Unidades de planificación actuales, consulte'
where l.LABEL_CODE='static.listTree.missingPlanningUnits' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Unidades de planejamento ausentes, inativas ou desmarcadas para Tree Forecast no programa atual: (Para ver a lista completa das unidades de planejamento atuais, consulte'
where l.LABEL_CODE='static.listTree.missingPlanningUnits' and ll.LANGUAGE_ID=4;