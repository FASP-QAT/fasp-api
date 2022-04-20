INSERT IGNORE INTO us_role_business_function SELECT null, 'ROLE_INTERNAL_USER', r1.BUSINESS_FUNCTION_ID, 1, now(), 1, now() FROM us_role_business_function r1 where r1.ROLE_ID='ROLE_REALM_ADMIN' and r1.BUSINESS_FUNCTION_ID!='ROLE_BF_NOTIFICATION_CC_APPROVE';
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` CHANGE COLUMN `CALCULATED_MMD_VALUE` `CALCULATED_MMD_VALUE` DECIMAL(18,4) UNSIGNED NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` CHANGE COLUMN `DIFFERENCE` `DIFFERENCE` DECIMAL(18,4) NULL ,CHANGE COLUMN `SEASONALITY_PERC` `SEASONALITY_PERC` DECIMAL(6,2) NULL ,CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(18,4) NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_data` ADD COLUMN `MANUAL_CHANGE` DECIMAL(18,4) NULL AFTER `REPORTING_RATE`;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.usageTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'usage template placeholder');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'espace réservé au modèle d utilisation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'marcador de posición de plantilla de uso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'espaço reservado para modelo de uso');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.forecastSummary','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'forecast summary placeholder');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'espace réservé pour le récapitulatif des prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'marcador de posición de resumen de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'marcador de posição de resumo de previsão');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.usagePeriod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'usage period placeholder');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'espace réservé pour la période d utilisation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'marcador de posición del período de uso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'espaço reservado para período de uso');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.forecastMethod','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'forecast method placeholder');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'espace réservé pour la méthode de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'marcador de posición del método de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'marcador de posição do método de previsão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.modelingType','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'modeling type placeholder');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'espace réservé pour le type de modélisation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'marcador de posición de tipo de modelado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'espaço reservado do tipo de modelagem');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.placeholder.LagInMonthFUNode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this feature for phased product usage. For example, if the lag is 2, the product usage will begin 2 months after the parent node dates.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette fonction pour une utilisation progressive du produit. Par exemple, si le décalage est de 2, lutilisation du produit commencera 2 mois après les dates du nœud parent.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta función para el uso del producto por etapas. Por ejemplo, si el retraso es 2, el uso del producto comenzará 2 meses después de las fechas del nodo principal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esse recurso para uso do produto em fases. Por exemplo, se o atraso for 2, o uso do produto começará 2 meses após as datas do nó pai.');