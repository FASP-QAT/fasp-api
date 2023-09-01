/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-Aug-2023
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-Aug-2023
 */
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew1','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error is calculated using the Weighted Absolute Percentage Error (WAPE). WAPE is used over MAPE (Mean Absolute Percentage Error) as it can account for when consumption is intermittent or low.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico se calcula utilizando el error porcentual absoluto ponderado (WAPE). WAPE se utiliza sobre MAPE (error porcentual absoluto medio), ya que puede tener en cuenta cuando el consumo es intermitente o bajo.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O erro de previsão é calculado usando o erro percentual absoluto ponderado (WAPE). WAPE é usado em vez de MAPE (Erro percentual médio absoluto), pois pode contabilizar quando o consumo é intermitente ou baixo.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L’erreur de prévision est calculée à l’aide du pourcentage d’erreur absolu pondéré (WAPE). WAPE est utilisé par rapport à MAPE (Mean Absolute Percentage Error) car il peut tenir compte du moment où la consommation est intermittente ou faible.');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew2','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'where n = number of months selected in the ‘time window’, A = actual consumption (or adjusted actual consumption), F = forecasted consumption, t = time in months.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'donde n = número de meses seleccionados en la “ventana de tiempo”, A = consumo real (o consumo real ajustado), F = consumo previsto, t = tiempo en meses.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'onde n = número de meses selecionados na ‘janela temporal’, A = consumo real (ou consumo real ajustado), F = consumo previsto, t = tempo em meses.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'où n = nombre de mois sélectionnés dans la « fenêtre temporelle », A = consommation réelle (ou consommation réelle ajustée), F = consommation prévue, t = durée en mois.');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew3','1');
 
 SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The WAPE formula uses the previous 3-12 months of data depending on the selection in the ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fórmula WAPE utiliza los datos de los 3 a 12 meses anteriores dependiendo de la selección en el');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A fórmula WAPE usa dados dos 3 a 12 meses anteriores, dependendo da seleção no');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La formule WAPE utilise les 3 à 12 mois de données précédents en fonction de la sélection dans le');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew3a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'dropdown. For example, if the ‘Time Window’ selected is 6 months, then 6 months of actual consumption and 6 months of forecasted consumption is used: ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'desplegable. Por ejemplo, si la "Ventana de tiempo" seleccionada es 6 meses, entonces se utilizan 6 meses de consumo real y 6 meses de consumo previsto:');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'suspenso. Por exemplo, se a ‘janela de tempo’ selecionada for 6 meses, serão utilizados 6 meses de consumo real e 6 meses de consumo previsto:');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'dérouler. Par exemple, si la « Fenêtre de temps » sélectionnée est de 6 mois, alors 6 mois de consommation réelle et 6 mois de consommation prévisionnelle sont utilisés :');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew4a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A month labeled');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Un mes etiquetado');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Um mês rotulado');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un mois étiqueté');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew4b','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total actual consumption for last X months = 0');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real total de los últimos X meses = 0');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real total nos últimos X meses = 0');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle totale pour les X derniers mois = 0');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew4c','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'means that the summation of the actual consumption for the usable months in the selected time window was 0.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'significa que la suma del consumo real de los meses utilizables en la ventana de tiempo seleccionada fue 0.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'significa que a soma do consumo real dos meses utilizáveis ​​na janela de tempo selecionada foi 0.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'signifie que la somme de la consommation réelle pour les mois utilisables dans la fenêtre horaire sélectionnée était égale à 0.');
    
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew5a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No months in this period contain both forecast and actual consumption');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ningún mes de este período contiene tanto el consumo previsto como el real.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum mês neste período contém previsão e consumo real');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun mois de cette période ne contient à la fois une consommation prévisionnelle et réelle');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew5b','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'means that there are no months in the selected time window that have both actual and forecast records in the same month');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'significa que no hay meses en la ventana de tiempo seleccionada que tengan registros reales y pronosticados en el mismo mes');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'significa que não há meses na janela de tempo selecionada que tenham registros reais e previstos no mesmo mês');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'signifie qu`il n`y a aucun mois dans la fenêtre de temps sélectionnée qui ait à la fois des enregistrements réels et prévisionnels dans le même mois');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew6','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If “Show consumption adjusted for stock out?” checkbox is checked, QAT uses the adjusted actual consumption instead of the actual consumption. QAT calculates the total days in the month based on the calendar. ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si aparece "¿Mostrar consumo ajustado por falta de existencias?" La casilla de verificación está marcada, QAT utiliza el consumo real ajustado en lugar del consumo real. QAT calcula el total de días del mes según el calendario.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se “Mostrar consumo ajustado por ruptura de estoque?” caixa de seleção estiver marcada, o QAT usa o consumo real ajustado em vez do consumo real. O QAT calcula o total de dias do mês com base no calendário.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si « Afficher la consommation corrigée des ruptures de stock ? est cochée, QAT utilise la consommation réelle ajustée au lieu de la consommation réelle. QAT calcule le nombre total de jours dans le mois en fonction du calendrier.');
    
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew6a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stocked Out (days)/ (# of Days in Month)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Desabastecido (días)/(# de días en el mes)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estocado (dias)/(nº de dias no mês)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En rupture de stock (jours)/(nombre de jours dans le mois)');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew6b','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Consumption / Reporting Rate / (1 - Stock Out Rate)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo real/Tasa de informe/(1 - Tasa de desabastecimiento)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo real/Taxa de relatório/(1 - Taxa de ruptura de estoque)');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation réelle/Taux de déclaration/(1 - Taux de rupture de stock)');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew7','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If there are ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si hay');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se houver');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'S`il y a');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew7a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'multiple regions');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'múltiples regiones');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'múltiplas regiões');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'plusieurs régions');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew7b','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates WAPE as follows:');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula WAPE de la siguiente manera:');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT calcula WAPE da seguinte forma:');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule le WAPE comme suit :');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew8','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each month, adds up actual consumption (or adjusted actual consumption) across all regions, so that there is only one actual consumption figure per month. ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada mes, suma el consumo real (o el consumo real ajustado) en todas las regiones, de modo que solo hay una cifra de consumo real por mes.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada mês, soma o consumo real (ou consumo real ajustado) em todas as regiões, de modo que haja apenas um valor de consumo real por mês.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque mois, additionne la consommation réelle (ou la consommation réelle ajustée) dans toutes les régions, de sorte qu`il n`y ait qu`un seul chiffre de consommation réelle par mois.');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew9','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each month, adds up all the forecasted consumption across all regions, so that there is only one forecasted consumption figure per month. ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada mes, suma todo el consumo previsto en todas las regiones, de modo que solo haya una cifra de consumo previsto por mes.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada mês, soma todo o consumo previsto em todas as regiões, de modo que haja apenas um valor de consumo previsto por mês.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque mois, additionne toute la consommation prévue dans toutes les régions, de sorte qu`il n`y ait qu`un seul chiffre de consommation prévue par mois.');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew10','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Uses the above WAPE formula once using the aggregated numbers from step 1 and 2 ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utiliza la fórmula WAPE anterior una vez usando los números agregados de los pasos 1 y 2');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usa a fórmula WAPE acima uma vez usando os números agregados das etapas 1 e 2');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilise la formule WAPE ci-dessus une fois en utilisant les nombres agrégés des étapes 1 et 2');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew11a','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew11b','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Y-axis is in Equivalency Unit');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El eje Y está en la unidad de equivalencia');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O eixo Y está na unidade de equivalência');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`axe Y est en unité d`équivalence');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew12','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT identifies which forecasting or planning units are mapped to the selected equivalency unit');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT identifica qué unidades de previsión o planificación se asignan a la unidad de equivalencia seleccionada');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT identifica quais unidades de previsão ou planejamento estão mapeadas para a unidade de equivalência selecionada');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT identifie les unités de prévision ou de planification qui sont mappées à l`unité d`équivalence sélectionnée');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew13','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each month, adds up all actual consumption (or adjusted actual consumption) from all mapped forecasting or planning units and converts those totals into equivalency units, so that there is only one actual consumption figure per month. ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada mes, suma todo el consumo real (o consumo real ajustado) de todas las unidades de planificación o pronóstico asignadas y convierte esos totales en unidades de equivalencia, de modo que solo haya una cifra de consumo real por mes.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada mês, soma todo o consumo real (ou consumo real ajustado) de todas as unidades mapeadas de previsão ou planejamento e converte esses totais em unidades de equivalência, de modo que haja apenas um valor de consumo real por mês.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque mois, additionne toute la consommation réelle (ou la consommation réelle ajustée) de toutes les unités de prévision ou de planification cartographiées et convertit ces totaux en unités d`équivalence, de sorte qu`il n`Y ait qu`un seul chiffre de consommation réelle par mois.');
  
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew14','1');
  SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
 	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each month, adds up all the forecasted consumption from all mapped forecasting or planning units and converts those totals into equivalency units, so that there is only one forecasted consumption figure per month. ');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada mes, suma todo el consumo pronosticado de todas las unidades de planificación o pronóstico asignadas y convierte esos totales en unidades de equivalencia, de modo que solo haya una cifra de consumo pronosticado por mes.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada mês, soma todo o consumo previsto de todas as unidades de previsão ou planejamento mapeadas e converte esses totais em unidades de equivalência, de modo que haja apenas um valor de consumo previsto por mês.');
  	INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque mois, additionne toute la consommation prévue de toutes les unités de prévision ou de planification cartographiées et convertit ces totaux en unités d`équivalence, de sorte qu`il n`Y ait qu`un seul chiffre de consommation prévue par mois.');
  