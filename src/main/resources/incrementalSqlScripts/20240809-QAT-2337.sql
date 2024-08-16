ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` ADD COLUMN `ONE_TIME_DISPENSING` TINYINT(3) UNSIGNED DEFAULT '1' NOT NULL AFTER `ONE_TIME_USAGE`; 

ALTER TABLE `fasp`.`rm_tree_template_node_data_fu` ADD COLUMN `ONE_TIME_DISPENSING` TINYINT(3) UNSIGNED DEFAULT '1' NOT NULL AFTER `ONE_TIME_USAGE`; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.oneTimeDispensing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculate Using One Time Dispensing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculer en utilisant une distribution unique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calcular utilizando una dispensación única');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calcular usando dispensação única');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.oneTimeDispensing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes, QAT will frontload the total forecasted quantity to the node\'s start month. If no, QAT will calculate the quantity discretely month-by-month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, QAT appliquera la quantité totale prévue au mois de début du nœud. Si non, QAT calculera la quantité discrètement mois par mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, QAT anticipará la cantidad total prevista al mes de inicio del nodo. En caso negativo, QAT calculará la cantidad discretamente mes a mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, o QAT antecipará a quantidade total prevista para o mês de início do nó. Se não, o QAT calculará a quantidade discretamente mês a mês');-- pr