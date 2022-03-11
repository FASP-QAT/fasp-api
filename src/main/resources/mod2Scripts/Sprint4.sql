INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculater','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculator');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculatrice de modélisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculadora de modelado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculadora de modelagem');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculaterTool','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculator Tool:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Outil de calcul de modélisation:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Herramienta de calculadora de modelado:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ferramenta Calculadora de Modelagem:');

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` DROP FOREIGN KEY `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx`;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` CHANGE COLUMN `USAGE_FREQUENCY` `USAGE_FREQUENCY` DECIMAL(16,4) UNSIGNED NULL COMMENT '# of times the Forecasting Unit is given per Usage' , CHANGE COLUMN `USAGE_FREQUENCY_USAGE_PERIOD_ID` `USAGE_FREQUENCY_USAGE_PERIOD_ID` INT UNSIGNED NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` ADD CONSTRAINT `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx`  FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`)  REFERENCES `fasp`.`ap_usage_period` (`USAGE_PERIOD_ID`);

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitTree.note','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Before committing, remember to a forecast for each planning unit in the `Compare and Select` screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Avant de vous engager, n`oubliez pas d`établir une prévision pour chaque unité de planification dans l`écran `Comparer et sélectionner`.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: antes de comprometerse, recuerde un pronóstico para cada unidad de planificación en la pantalla `Comparar y seleccionar`');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: antes de confirmar, lembre-se de uma previsão para cada unidade de planejamento na tela `Comparar e selecionar`');-- pr