ALTER TABLE `fasp`.`rm_forecast_tree` ADD COLUMN `TREE_ANCHOR_ID` INT UNSIGNED NULL AFTER `TREE_ID`;

CREATE TABLE `rm_forecast_tree_anchor` (
  `TREE_ANCHOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `TREE_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`TREE_ANCHOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

UPDATE rm_forecast_tree ft SET ft.TREE_ANCHOR_ID=null;
ALTER TABLE `fasp`.`rm_forecast_tree` ADD INDEX `fk_rm_forecast_tree_treeAnchorId_idx` (`TREE_ANCHOR_ID` ASC);

ALTER TABLE `fasp`.`rm_forecast_tree` 
ADD CONSTRAINT `fk_rm_forecast_tree_treeAnchorId`
  FOREIGN KEY (`TREE_ANCHOR_ID`)
  REFERENCES `fasp`.`rm_forecast_tree_anchor` (`TREE_ANCHOR_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_forecast_tree_anchor` ADD COLUMN `TREE_NAME` VARCHAR(255) NOT NULL AFTER `PROGRAM_ID`;

INSERT INTO rm_forecast_tree_anchor SELECT NULL, ft.PROGRAM_ID, ft.LABEL_EN, MIN(ft.TREE_ID), MIN(ft.CREATED_DATE) FROM vw_forecast_tree ft group by ft.PROGRAM_ID, ft.LABEL_EN ORDER by ft.CREATED_DATE;

UPDATE rm_forecast_tree t 
left join ap_label l on t.LABEL_ID=l.LABEL_ID
LEFT JOIN rm_forecast_tree_anchor ta ON t.PROGRAM_ID=ta.PROGRAM_ID AND ta.TREE_NAME=l.LABEL_EN 
SET t.TREE_ANCHOR_ID=ta.TREE_ANCHOR_ID;

USE `fasp`;
CREATE 
    OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree` AS
    SELECT 
        `ft`.`TREE_ID` AS `TREE_ID`,
        `ft`.`TREE_ANCHOR_ID` AS `TREE_ANCHOR_ID`,
        `ft`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `ft`.`VERSION_ID` AS `VERSION_ID`,
        `ft`.`LABEL_ID` AS `LABEL_ID`,
        `ft`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,
        `ft`.`CREATED_BY` AS `CREATED_BY`,
        `ft`.`CREATED_DATE` AS `CREATED_DATE`,
        `ft`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ft`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ft`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`,
        `ft`.`NOTES` AS `NOTES`
    FROM
        (`rm_forecast_tree` `ft`
        LEFT JOIN `ap_label` `l` ON ((`ft`.`LABEL_ID` = `l`.`LABEL_ID`)));


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.goTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Go to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aller à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ir a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Vá para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.updatePUSettings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update Planning Unit Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour les paramètres d`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar la configuración de la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar configurações da unidade de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.localV','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Local v');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'V locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'V local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'local v');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.serverV','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Server v');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Serveur v');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'servidor v');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'servidor v');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.exculdeConsumptionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, QAT will exclude any changes to this planning unit on version {{localVersionId}} (local) and revert to the latest server version {{serverVersionId}}.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, QAT exclura toute modification apportée à cette unité de planification sur la version {{localVersionId}} (local) et reviendra à la dernière version du serveur {{serverVersionId}}.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se marca, QAT excluirá cualquier cambio en esta unidad de planificación en la versión {{localVersionId}} (local) y volverá a la última versión del servidor {{serverVersionId}}.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcado, o QAT excluirá quaisquer alterações nesta unidade de planejamento na versão {{localVersionId}} (local) e reverterá para a versão mais recente do servidor {{serverVersionId}}.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.exculdeTreeTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, QAT will exclude any changes to this tree on version {{localVersionId}} (local) and revert to the latest server version {{serverVersionId}}.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, QAT exclura toute modification apportée à cette arborescence sur la version {{localVersionId}} (local) et reviendra à la dernière version du serveur {{serverVersionId}}.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se marca, QAT excluirá cualquier cambio en este árbol en la versión {{localVersionId}} (local) y volverá a la última versión del servidor {{serverVersionId}}.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcado, o QAT excluirá quaisquer alterações nesta árvore na versão {{localVersionId}} (local) e reverterá para a versão mais recente do servidor {{serverVersionId}}.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.treeAndConsumptionLastModifiedDateTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This date refers to when the data element was last edited in this particular version.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cette date fait référence à la dernière fois que lélément de données a été modifié dans cette version particulière.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta fecha se refiere a cuándo se editó por última vez el elemento de datos en esta versión en particular.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta data refere-se a quando o elemento de dados foi editado pela última vez nesta versão específica.');-- pr

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Note: In order to commit to the server, users must resolve all conflicts first by right-clicking on the line items and selecting to either “Accept local version” or “Accept server version”.'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Remarque : Pour s`engager sur le serveur, les utilisateurs doivent d`abord résoudre tous les conflits en cliquant avec le bouton droit sur les éléments de campagne et en sélectionnant "Accepter la version locale" ou "Accepter la version du serveur".'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Nota: Para comprometerse con el servidor, los usuarios deben resolver todos los conflictos primero haciendo clic derecho en las líneas de pedido y seleccionando "Aceptar versión local" o "Aceptar versión del servidor".'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Observação: para se comprometer com o servidor, os usuários devem resolver todos os conflitos primeiro clicando com o botão direito nos itens de linha e selecionando “Aceitar versão local” ou “Aceitar versão do servidor”.'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.exclude','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Exclude local changes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exclure les modifications locales');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Excluir cambios locales');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir alterações locais');-- pr