INSERT IGNORE INTO us_role_business_function SELECT null, 'ROLE_INTERNAL_USER', r1.BUSINESS_FUNCTION_ID, 1, now(), 1, now() FROM us_role_business_function r1 where r1.ROLE_ID='ROLE_REALM_ADMIN' and r1.BUSINESS_FUNCTION_ID!='ROLE_BF_NOTIFICATION_CC_APPROVE';
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` CHANGE COLUMN `CALCULATED_MMD_VALUE` `CALCULATED_MMD_VALUE` DECIMAL(18,4) UNSIGNED NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` CHANGE COLUMN `DIFFERENCE` `DIFFERENCE` DECIMAL(18,4) NULL ,CHANGE COLUMN `SEASONALITY_PERC` `SEASONALITY_PERC` DECIMAL(6,2) NULL ,CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(18,4) NULL ;


/*[4:02:36 PM][ 3 ms]*/ INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Services',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'43');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`rm_forecast_method`(`FORECAST_METHOD_ID`,`REALM_ID`,`FORECAST_METHOD_TYPE_ID`,`LABEL_ID`,`ACTIVE`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'1','1',@MAX,'1','1',NOW(),'1',NOW());  

INSERT INTO ap_label VALUES (null, 'QAT Forecast Import', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `us_business_function`VALUES ('ROLE_BF_SUPPLY_PLAN_IMPORT', @labelId, 1, now(), 1, now());


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.pleaseSelectForecastProgramVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select forecast program version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner la version du programme de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione la versión del programa de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a versão do programa de previsão');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.importIntoQATSupplyPlanSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan data imported successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données du plan d`approvisionnement importées avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos del Plan de Abastecimiento importados con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados do plano de fornecimento importados com sucesso');-- pr

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ('ROLE_REALM_ADMIN','ROLE_BF_SUPPLY_PLAN_IMPORT',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ('ROLE_INTERNAL_USER','ROLE_BF_SUPPLY_PLAN_IMPORT',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ('ROLE_PROGRAM_ADMIN','ROLE_BF_SUPPLY_PLAN_IMPORT',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function`(`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ('ROLE_PROGRAM_USER','ROLE_BF_SUPPLY_PLAN_IMPORT',1,now(),1,now());

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

INSERT INTO ap_label VALUES (null, 'QAT', null, null, null, 1, now(), 1, now(), 15);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`,`REALM_ID`,`PROGRAM_ID`,`DATA_SOURCE_TYPE_ID`,`LABEL_ID`,`ACTIVE`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES (null,1,null,2,@labelId,0,1,now(),1,now());

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Display forecasts in months with actual consumption.'
where l.LABEL_CODE='static.extrapolations.showFits' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Affichez les prévisions en mois avec la consommation réelle.'
where l.LABEL_CODE='static.extrapolations.showFits' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Mostrar previsiones en meses con consumo real.'
where l.LABEL_CODE='static.extrapolations.showFits' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Exiba previsões em meses com consumo real.'
where l.LABEL_CODE='static.extrapolations.showFits' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='These Usage Names will appear when adding/editing a forecasting tree node in the `Copy from Template` dropdown.'
where l.LABEL_CODE='static.tooltip.UsageName' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ces noms d`utilisation apparaîtront lors de l`ajout/de la modification d`un nœud d`arbre de prévision dans la liste déroulante `Copier à partir du modèle`.'
where l.LABEL_CODE='static.tooltip.UsageName' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Estos nombres de uso aparecerán al agregar/editar un nodo de árbol de pronóstico en el menú desplegable `Copiar de plantilla`.'
where l.LABEL_CODE='static.tooltip.UsageName' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Esses nomes de uso aparecerão ao adicionar/editar um nó de árvore de previsão no menu suspenso `Copiar do modelo`.'
where l.LABEL_CODE='static.tooltip.UsageName' and ll.LANGUAGE_ID=4;


-- Added on 22nd Apr 2022
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.treeExtrapolationSave','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you changed any inputs, please click the extrapolate button again to update calculations. Then click save.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous avez modifié des entrées, veuillez cliquer à nouveau sur le bouton dextrapolation pour mettre à jour les calculs. Cliquez ensuite sur enregistrer.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si cambió alguna entrada, vuelva a hacer clic en el botón Extrapolar para actualizar los cálculos. Luego haga clic en guardar.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você alterou alguma entrada, clique no botão extrapolar novamente para atualizar os cálculos. Em seguida, clique em salvar.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.minDataRequiredToExtrapolate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'NOTE: The minimum values needed to get correct graphs and reports for the various features are as under:\n1) ARIMA : This needs at least 14 months of data\n2) TES will need at least 24 months of data\n3) Other(including things like Moving averages etc) will need at least 3 months of data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'REMARQUE : les valeurs minimales nécessaires pour obtenir des graphiques et des rapports corrects pour les différentes fonctionnalités sont les suivantes :\n1) ARIMA : cela nécessite au moins 14 mois de données\n2) TES nécessite au moins 24 mois de données\n3) Autre( y compris des éléments tels que les moyennes mobiles, etc.) nécessiteront au moins 3 mois de données');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'NOTA: Los valores mínimos necesarios para obtener gráficos e informes correctos para las diversas funciones son los siguientes:\n1) ARIMA: esto necesita al menos 14 meses de datos\n2) TES necesitará al menos 24 meses de datos\n3) Otro( incluyendo cosas como promedios móviles, etc.) necesitará al menos 3 meses de datos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'NOTA: Os valores mínimos necessários para obter gráficos e relatórios corretos para os vários recursos são os seguintes:\n1) ARIMA: Isso precisa de pelo menos 14 meses de dados\n2) TES precisará de pelo menos 24 meses de dados\n3) Outros( incluindo coisas como médias móveis, etc) precisará de pelo menos 3 meses de dados');