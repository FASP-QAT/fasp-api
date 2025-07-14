/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Jan-2023
 */

ALTER TABLE `rm_tree_template` 
DROP COLUMN `BRANCH`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template` AS
    SELECT 
        `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tt`.`REALM_ID` AS `REALM_ID`,
        `tt`.`LABEL_ID` AS `LABEL_ID`,
        `tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `tt`.`MONTHS_IN_PAST` AS `MONTHS_IN_PAST`,
        `tt`.`MONTHS_IN_FUTURE` AS `MONTHS_IN_FUTURE`,
        `tt`.`CREATED_BY` AS `CREATED_BY`,
        `tt`.`CREATED_DATE` AS `CREATED_DATE`,
        `tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tt`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `tt`.`NOTES` AS `NOTES`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.startNode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Noeud de démarrage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo de inicio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó inicial');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.branchTemplateNotes1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You are adding a child node to a');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous ajoutez un nœud enfant à un');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Está agregando un nodo secundario a un');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você está adicionando um nó filho a um');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.branchTemplateNotes2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'node, therefore only templates starting with');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'nœud, donc uniquement les modèles commençant par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'nodo, por lo tanto, solo las plantillas que comienzan con');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'nó, portanto, apenas modelos começando com');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.branchTemplateNotes3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'are shown. See');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'sont indiqués. Voir');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'son exhibidos. Ver');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'são mostrados. Ver');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.branchTemplateNotes4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'for the full list.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pour la liste complète.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'para la lista completa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'para a lista completa.');-- pr