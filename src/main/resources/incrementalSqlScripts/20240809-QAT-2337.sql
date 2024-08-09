ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` ADD COLUMN `ONE_TIME_DISPENSING` TINYINT(3) UNSIGNED DEFAULT '1' NOT NULL AFTER `ONE_TIME_USAGE`; 

ALTER TABLE `fasp`.`rm_tree_template_node_data_fu` ADD COLUMN `ONE_TIME_DISPENSING` TINYINT(3) UNSIGNED DEFAULT '1' NOT NULL AFTER `ONE_TIME_USAGE`; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.oneTimeDispensing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculate Using One Time Dispensing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculer en utilisant une distribution unique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calcular utilizando una dispensación única');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calcular usando dispensação única');-- pr