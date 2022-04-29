/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 26-Apr-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.report.versionFinal*','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version (* indicates Final)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version (* indique Finale)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión (* indica Final)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão (* indica Final)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.person','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Person');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Personne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Persona');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pessoa');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.forecastFinalVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast version (Final Versions Only)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version prévisionnelle (versions finales uniquement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão de previsão (somente versões finais)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión de previsión (solo versiones finales)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.importIntoQATSupplyPlan','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Forecast Import');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importation des prévisions QAT');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importación de pronóstico QAT');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados do plano de fornecimento importados com sucesso');-- pr

ALTER TABLE `fasp`.`rm_forecast_tree_node_data` ADD COLUMN `IS_EXTRAPOLATION` TINYINT(1) UNSIGNED NOT NULL AFTER `NODE_DATA_PU_ID`;
UPDATE rm_forecast_tree_node_data ftnd LEFT JOIN rm_forecast_tree_node ftn ON ftnd.NODE_ID=ftn.NODE_ID SET ftnd.IS_EXTRAPOLATION=ftn.IS_EXTRAPOLATION;
ALTER TABLE `fasp`.`rm_forecast_tree_node` DROP COLUMN `IS_EXTRAPOLATION`;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_forecast_tree_node` AS
    SELECT 
        `tn`.`NODE_ID` AS `NODE_ID`,
        `tn`.`TREE_ID` AS `TREE_ID`,
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
