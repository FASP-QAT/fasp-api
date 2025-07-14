INSERT INTO ap_label VALUES (null, 'Funnel node', 'Nœud d\`entonnoir', 'Nodo de embudo', 'Nó de funil', 1, now(), 1, now(), 39);
INSERT INTO ap_node_type VALUES (null, LAST_INSERT_ID(), 0, 0, 1, 1, 1, 1, now(), 1, now());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.sourceNodeDesc','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Source Node (available to be aggregated by Funnel Nodes)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud source (disponible pour être agrégé par des nœuds d`entonnoir)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo de origen (disponible para ser agregado por nodos de embudo)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó de origem (disponível para ser agregado por nós de funil)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.source','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Source');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Source');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fuente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fonte');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.parentName','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Parent Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom des parents');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del padre');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome dos Pais');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.aggregatedBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Aggregated by the following funnel nodes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agrégé par les nœuds d\`entonnoir suivants');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregado por los siguientes nodos de embudo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agregado pelos seguintes nós de funil');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.notUsed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Not used by any nodes.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Non utilisé par aucun nœud.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No utilizada por ningún nodo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não usado por nenhum nó.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.hideFunnel','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Funnel node connections');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer les connexions des nœuds d\`entonnoir');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar conexiones de nodos de embudo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar conexões do nó do funil');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.funnelNode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funnel Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud d\`entonnoir');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo de embudo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó Funil');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.sourceNode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Source Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud source');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo de origen');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó de origem');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.availableToBeAggregated','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Available to be aggregated by funnel nodes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Disponible pour être agrégé par nœuds d\`entonnoir');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Disponible para ser agregada por nodos de embudo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Disponível para ser agregado por nós de funil');-- pr
 
CREATE TABLE `fasp`.`rm_forecast_tree_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_TREE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_SCENARIO_ID` INT UNSIGNED NOT NULL,
  `SOURCE_NODE_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  INDEX `fk_rm_ftndownward_agg_nodeId_idx` (`TARGET_NODE_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_scenarioId_idx` (`SOURCE_SCENARIO_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_sourceNodeId_idx` (`SOURCE_NODE_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_treeId_idx` (`SOURCE_TREE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftn_downward_agg_nodeId`
    FOREIGN KEY (`TARGET_NODE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_treeId`
    FOREIGN KEY (`SOURCE_TREE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree` (`TREE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_scenarioId`
    FOREIGN KEY (`SOURCE_SCENARIO_ID`)
    REFERENCES `fasp`.`rm_scenario` (`SCENARIO_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_sourceNodeId`
    FOREIGN KEY (`SOURCE_NODE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
ALTER TABLE `fasp`.`rm_forecast_tree_node` ADD COLUMN `DOWNWARD_AGGREGATION_ALLOWED` TINYINT UNSIGNED NOT NULL DEFAULT 0 AFTER `COLLAPSED`;
UPDATE rm_forecast_tree_node tn SET tn.DOWNWARD_AGGREGATION_ALLOWED=0;
USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree_node` AS
    SELECT 
        `tn`.`NODE_ID` AS `NODE_ID`,
        `tn`.`TREE_ID` AS `TREE_ID`,
        `tn`.`COLLAPSED` AS `COLLAPSED`,
        `tn`.`DOWNWARD_AGGREGATION_ALLOWED` AS `DOWNWARD_AGGREGATION_ALLOWED`,
        `tn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `tn`.`SORT_ORDER` AS `SORT_ORDER`,
        `tn`.`LEVEL_NO` AS `LEVEL_NO`,
        `tn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `tn`.`UNIT_ID` AS `UNIT_ID`,
        `tn`.`LABEL_ID` AS `LABEL_ID`,
        `tn`.`CREATED_BY` AS `CREATED_BY`,
        `tn`.`CREATED_DATE` AS `CREATED_DATE`,
        `tn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `tn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `tn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_forecast_tree_node` `tn`
        LEFT JOIN `ap_label` `l` ON ((`tn`.`LABEL_ID` = `l`.`LABEL_ID`)));

ALTER TABLE `fasp`.`rm_tree_template_node` ADD COLUMN `DOWNWARD_AGGREGATION_ALLOWED` TINYINT UNSIGNED NOT NULL DEFAULT 0 AFTER `COLLAPSED`;
UPDATE rm_tree_template_node tn SET tn.DOWNWARD_AGGREGATION_ALLOWED=0;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template_node` AS
    SELECT 
        `ttn`.`NODE_ID` AS `NODE_ID`,
        `ttn`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `ttn`.`COLLAPSED` AS `COLLAPSED`,
        `ttn`.`DOWNWARD_AGGREGATION_ALLOWED` AS `DOWNWARD_AGGREGATION_ALLOWED`,
        `ttn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,
        `ttn`.`SORT_ORDER` AS `SORT_ORDER`,
        `ttn`.`LEVEL_NO` AS `LEVEL_NO`,
        `ttn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,
        `ttn`.`UNIT_ID` AS `UNIT_ID`,
        `ttn`.`LABEL_ID` AS `LABEL_ID`,
        `ttn`.`CREATED_BY` AS `CREATED_BY`,
        `ttn`.`CREATED_DATE` AS `CREATED_DATE`,
        `ttn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `ttn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ttn`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template_node` `ttn`
        LEFT JOIN `ap_label` `l` ON ((`ttn`.`LABEL_ID` = `l`.`LABEL_ID`)))
    ORDER BY `ttn`.`TREE_TEMPLATE_ID` , `ttn`.`SORT_ORDER`;

INSERT INTO ap_node_type_rule VALUES (null, 2, 6);
INSERT INTO ap_node_type_rule VALUES (null, 3, 6);
INSERT INTO ap_node_type_rule VALUES (null, 6, 3);
INSERT INTO ap_node_type_rule VALUES (null, 6, 4);
INSERT INTO ap_node_type_rule VALUES (null, 1, 6);



CREATE TABLE `fasp`.`rm_tree_template_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_TREE_TEMPLATE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_NODE_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  INDEX `fk_rm_forecast_tree_node_downward_aggregation_targetNodeId_idx` (`TARGET_NODE_ID` ASC) VISIBLE,
  INDEX `fk_rm_forecast_tree_node_downward_aggregation_targetTreeTem_idx` (`SOURCE_TREE_TEMPLATE_ID` ASC) VISIBLE,
  INDEX `fk_rm_forecast_tree_node_downward_aggregation_sourceNodeId_idx` (`SOURCE_NODE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_ftnda_targetNodeId`
    FOREIGN KEY (`TARGET_NODE_ID`)
    REFERENCES `fasp`.`rm_tree_template_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ftnda_sourceTreeTemplateId`
    FOREIGN KEY (`SOURCE_TREE_TEMPLATE_ID`)
    REFERENCES `fasp`.`rm_tree_template` (`TREE_TEMPLATE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ftnda_sourceNodeId`
    FOREIGN KEY (`SOURCE_NODE_ID`)
    REFERENCES `fasp`.`rm_tree_template_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
