ALTER TABLE `fasp`.`rm_tree_template` 
ADD COLUMN `BRANCH` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `NOTES`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
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
        `tt`.`NOTES` AS `NOTES`,
        `tt`.`BRANCH` AS `BRANCH`
    FROM
        (`rm_tree_template` `tt`
        LEFT JOIN `ap_label` `l` ON ((`tt`.`LABEL_ID` = `l`.`LABEL_ID`)));


ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation` 
ADD COLUMN `START_DATE` DATE NULL AFTER `NOTES`,
ADD COLUMN `STOP_DATE` DATE NULL AFTER `START_DATE`;

UPDATE rm_forecast_tree_node_data_extrapolation nde LEFT JOIN rm_forecast_tree_node_data nd ON nde.NODE_DATA_ID=nd.NODE_DATA_ID SET nde.START_DATE=DATE_SUB(nd.MONTH, INTERVAL 24 MONTH), nde.STOP_DATE=DATE_SUB(nd.MONTH, INTERVAL 1 MONTH);


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.BranchTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Branch Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle de branche');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de rama');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de Filial');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.createBranchTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create Branch Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer un modèle de branche');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear plantilla de sucursal');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar modelo de filial');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.selectBranchTreeTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez un modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar plantilla');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecionar modelo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.addBranchTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Branch Template Added Successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle de branche ajouté avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de rama añadida con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de filial adicionado com sucesso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.editBranchTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Branch Template Updated Successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modèle de branche mis à jour avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla de sucursal actualizada con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo de filial atualizado com sucesso');-- pr