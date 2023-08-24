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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error is calculated using the Weighted Absolute Percentage Error (WAPE). WAPE is used over MAPE (Mean Absolute Percentage Error) as it can account for when consumption is intermittent or low.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L\'erreur de prévision est calculée à l\'aide du pourcentage d\'erreur absolu pondéré (WAPE). WAPE est utilisé sur MAPE (Mean Absolute Percentage Error) car il peut tenir compte du moment où la consommation est intermittente ou faible.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico se calcula utilizando el error porcentual absoluto ponderado (WAPE). WAPE se usa sobre MAPE (Error porcentual absoluto medio) ya que puede tener en cuenta cuando el consumo es intermitente o bajo.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O erro de previsão é calculado usando o erro percentual absoluto ponderado (WAPE). O WAPE é usado sobre o MAPE (erro percentual médio absoluto), pois pode explicar quando o consumo é intermitente ou baixo.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The above formula is the example for the 6 month window.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La formule ci-dessus est l\'exemple pour la fenêtre de 6 mois.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fórmula anterior es el ejemplo para la ventana de 6 meses.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A fórmula acima é o exemplo para a janela de 6 meses.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If “Show consumption adjusted for stock out?” is checked, then the error is calculated using the Adjusted Actual (Consumption) instead of Actual (Consumption).'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si « Afficher la consommation ajustée pour rupture de stock ? » est cochée, l\'erreur est calculée à l\'aide de la valeur réelle ajustée (consommation) au lieu de la valeur réelle (consommation).'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si “¿Mostrar consumo ajustado por desabastecimiento?” está marcado, luego el error se calcula utilizando el (consumo) real ajustado en lugar del (consumo) real.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se “Mostrar consumo ajustado para falta de estoque?” estiver marcado, então o erro é calculado usando o Real Ajustado (Consumo) em vez do Real (Consumo).'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew3a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjusted Actual = Actual consumption/ (total days in month - stocked out days) * total days in month'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réel ajusté = Consommation réelle/(jours totaux du mois - jours de rupture de stock) * jours totaux du mois'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Real ajustado = Consumo real/(total de días en el mes - días sin existencias) * total de días en el mes'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Real ajustado = Consumo real/ (total de dias no mês - dias sem estoque) * total de dias no mês'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew3b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT automatically calculates the total days in the month based on the calendar'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule automatiquement le nombre total de jours dans le mois en fonction du calendrier'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula automáticamente el total de días del mes según el calendario'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT calcula automaticamente o total de dias no mês com base no calendário'); -- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If there are multiple regions, QAT adds up all actual consumption (or adjusted actual consumption) from all regions, and all the forecasted consumption from all regions for a given month before using the above formula.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'S\'il y a plusieurs régions, QAT additionne toute la consommation réelle (ou la consommation réelle ajustée) de toutes les régions et toute la consommation prévue de toutes les régions pour un mois donné avant d\'utiliser la formule ci-dessus.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si hay varias regiones, QAT suma todo el consumo real (o el consumo real ajustado) de todas las regiones y todo el consumo previsto de todas las regiones para un mes determinado antes de usar la fórmula anterior.'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se houver várias regiões, QAT soma todo o consumo real (ou consumo real ajustado) de todas as regiões e todo o consumo previsto de todas as regiões para um determinado mês antes de usar a fórmula acima.'); -- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaNew5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If Y-axis is in Equivalency Unit, then QAT adds up all actual consumption (or adjusted actual consumption) from all selected forecasting or planning units, and all the forecasted consumption from all selected forecasting or planning units for a given month before using the above formula.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si l\'axe Y est dans l\'unité d\'équivalence, alors QAT additionne toute la consommation réelle (ou la consommation réelle ajustée) de toutes les unités de prévision ou de planification sélectionnées, et toute la consommation prévue de toutes les unités de prévision ou de planification sélectionnées pour un mois donné avant d\'utiliser ce qui précède formule.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el eje Y está en Unidad de equivalencia, QAT suma todo el consumo real (o el consumo real ajustado) de todas las unidades de previsión o planificación seleccionadas, y todo el consumo previsto de todas las unidades de previsión o planificación seleccionadas para un mes determinado antes de usar lo anterior. fórmula.'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o eixo Y estiver na unidade de equivalência, QAT soma todo o consumo real (ou consumo real ajustado) de todas as unidades de previsão ou planejamento selecionadas e todo o consumo previsto de todas as unidades de previsão ou planejamento selecionadas para um determinado mês antes de usar o acima Fórmula.'); -- pr
