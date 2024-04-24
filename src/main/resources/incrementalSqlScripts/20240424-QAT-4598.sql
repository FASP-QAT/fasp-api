INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.allitemsselected','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All items are selected'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous les éléments sont sélectionnés'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los artículos están seleccionados'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todos os itens são selecionados'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.forecastingProgramName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter forecasting program name'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisissez le nom du programme de prévision'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el nombre del programa de pronóstico'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o nome do programa de previsão'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.forecastPeriodInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter forecast period (months)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la période de prévision (mois)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el período de pronóstico (meses)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o período de previsão (meses)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataSet.forecastperiodInMonthRequired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast period should not contain negative number, decimal numbers, characters & special symbols'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La période de prévision ne doit pas contenir de nombres négatifs, de nombres décimaux, de caractères et de symboles spéciaux.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El período de pronóstico no debe contener números negativos, números decimales, caracteres ni símbolos especiales.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O período de previsão não deve conter números negativos, números decimais, caracteres e símbolos especiais'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.target','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Target'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cible'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Objetivo'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alvo'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.annualTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Annual Target'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Objectif annuel'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meta Anual'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meta Anual'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.endingValueTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending Value Target / Change'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Objectif de valeur finale/Modification'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor final objetivo/cambio'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Finalizando a meta/mudança de valor'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.increase','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Increase'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Augmenter'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Aumentar'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Aumentar'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.decrease','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Decrease'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Diminuer'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Disminuir'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Diminuir'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.discription','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Monthly change for {{startDate}} - {{stopDate}};\nConsiders: {{dateRange}} Entered Target = {{enteredTarget}}\nCalculated Target = {{calculatedTarget}}'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement mensuel pour {{startDate}} - {{stopDate}} ;\nConsidére : {{dateRange}} Cible saisie = {{enteredTarget}}\nCible calculée = {{calculatedTarget}}'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio mensual para {{startDate}} - {{stopDate}};\nConsidera: {{dateRange}} Objetivo ingresado = {{enteredTarget}}\nObjetivo calculado = {{calculatedTarget}}'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração mensal para {{startDate}} - {{stopDate}};\nConsiderações: {{dateRange}} Meta inserida = {{enteredTarget}}\nMeta calculada = {{calculatedTarget}}'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.monthSelector','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Month Selector'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélecteur de mois'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Selector de mes'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Seletor de mês'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.consumptionInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Interval (Reference)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de consommation (référence)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Intervalo de consumo (Referencia)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de Consumo (Referência)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.returnToList','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Return To List'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Retour à la liste'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Volver a la lista'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Retornar à lista'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.targetChangePer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Target change (%)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement cible (%)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio de objetivo (%)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração de meta (%)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.changePerPoints','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change (% points)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation (points de %)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio (puntos porcentuales)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração (% pontos)'); -- pr
