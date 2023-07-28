ALTER TABLE `fasp`.`rm_forecast_tree_node` ADD COLUMN `COLLAPSED` TINYINT NOT NULL DEFAULT 0 AFTER `LABEL_ID`;
ALTER TABLE `fasp`.`rm_tree_template_node` ADD COLUMN `COLLAPSED` TINYINT NOT NULL DEFAULT 0 AFTER `LABEL_ID`;

USE `fasp`;
CREATE
    OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_tree_template_node` AS
    SELECT 
        `ttn`.`NODE_ID` AS `NODE_ID`,
        `ttn`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `ttn`.`COLLAPSED` AS `COLLAPSED`,
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

USE `fasp`;
CREATE 
    OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree_node` AS
    SELECT 
        `tn`.`NODE_ID` AS `NODE_ID`,
        `tn`.`TREE_ID` AS `TREE_ID`,
        `tn`.`COLLAPSED` AS `COLLAPSED`,
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

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.collapseTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Collapse Tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réduire l\'arborescence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Colapsar árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Recolher árvore');-- pr
