/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 16-May-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.planningUnitSettings.desiredProgramLoadFirst','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you dont see the desired program(s), please load them first.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous ne voyez pas le(s) programme(s) souhaité(s), veuillez d abord les charger.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no ve los programas deseados, cárguelos primero.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você não vir o(s) programa(s) desejado(s), carregue-o(s) primeiro.');-- pr

-- ForecastOutput
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.enableUserView','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enable users to view the final (selected) forecasts for a single program, and displays the forecast by product and month. This output aggregates all regional forecasts into one total forecast. If you want to view disaggregated regional forecasts, you can do so by exporting the output as a CSV.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet aux utilisateurs d afficher les prévisions finales (sélectionnées) pour un seul programme et affiche les prévisions par produit et par mois. Cette sortie regroupe toutes les prévisions régionales en une seule prévision totale. Si vous souhaitez afficher des prévisions régionales désagrégées, vous pouvez le faire en exportant la sortie au format CSV.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permita que los usuarios vean los pronósticos finales (seleccionados) para un solo programa y muestre el pronóstico por producto y mes. Esta salida agrega todos los pronósticos regionales en un pronóstico total. Si desea ver pronósticos regionales desagregados, puede hacerlo exportando el resultado como un CSV.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permita que os usuários visualizem as previsões finais (selecionadas) para um único programa e exiba a previsão por produto e mês. Essa saída agrega todas as previsões regionais em uma previsão total. Se você quiser visualizar previsões regionais desagregadas, poderá fazê-lo exportando a saída como um CSV.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.onlySelectForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only selected forecasts will be shown in this output. To select a forecast for a product, go to the ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seules les prévisions sélectionnées seront affichées dans cette sortie. Pour sélectionner une prévision pour un produit, accédez à la ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo los pronósticos seleccionados se mostrarán en esta salida. Para seleccionar un pronóstico para un producto, vaya a la ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Somente as previsões selecionadas serão mostradas nesta saída. Para selecionar uma previsão para um produto, vá para ');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.totalForecastAcrossProduct','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' screen. To see total forecasts across products, go to the ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'filtrer. Pour voir les prévisions totales pour tous les produits, accédez à la ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla. Para ver las previsiones totales de todos los productos, vaya a ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela. Para ver as previsões totais entre os produtos, vá para ');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.screen','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'filtrer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.the','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.willBeUseToDetermine','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'will be used to determine the period of time that you would like to see in the graphical/tabular output and must be within the forecast period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'sera utilisé pour déterminer la période de temps que vous aimeriez voir dans la sortie graphique/tabulaire et doit être comprise dans la période de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'se usará para determinar el período de tiempo que le gustaría ver en la salida gráfica/tabular y debe estar dentro del período de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'será usado para determinar o período de tempo que você gostaria de ver na saída gráfica/tabular e deve estar dentro do período de previsão.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.useParameter','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use the parameters to view the graph/tabular data either by forecasting units or planning units as well as showing the y-axis in equivalency units.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez les paramètres pour afficher les données graphiques/tabulaires soit par unités de prévision, soit par unités de planification, ainsi que pour afficher l axe des ordonnées en unités d équivalence.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice los parámetros para ver los datos gráficos/tabulares, ya sea por unidades de previsión o unidades de planificación, así como para mostrar el eje y en unidades de equivalencia.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use os parâmetros para visualizar os dados gráficos/tabulares por unidades de previsão ou unidades de planejamento, bem como mostrar o eixo y em unidades de equivalência.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.allowUserToAggregate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(EUs) allow users to aggregate data across planning units or forecasting units that are different but have the same equivalency unit. For a further explanation of Equivalency Units, refer to the show guidance in the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(EU) permettent aux utilisateurs d agréger des données sur des unités de planification ou des unités de prévision qui sont différentes mais qui ont la même unité d équivalence. Pour une explication plus détaillée des unités d équivalence, reportez-vous aux instructions du');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(UE) permiten a los usuarios agregar datos a través de unidades de planificación o unidades de pronóstico que son diferentes pero tienen la misma unidad de equivalencia. Para obtener una explicación más detallada de las Unidades de equivalencia, consulte la guía de espectáculos en el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(EUs) permitem que os usuários agreguem dados entre unidades de planejamento ou unidades de previsão que são diferentes, mas têm a mesma unidade de equivalência. Para obter mais explicações sobre Unidades de Equivalência, consulte a orientação da demonstração no');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.xAxisAggrigateByYear','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The X-axis Aggregate By Year filter allows you to view the data aggregated by year instead of by month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le filtre "Agrégation de l axe X par année" vous permet d afficher les données agrégées par année plutôt que par mois.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El filtro Agregado del eje X por año le permite ver los datos agregados por año en lugar de por mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O filtro X-axis Aggregate By Year permite visualizar os dados agregados por ano em vez de por mês.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.forGraphView','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the graph view, there are 3 ways you can hide which products are being displayed:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour la vue graphique, vous pouvez masquer les produits affichés de 3 manières :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para la vista de gráfico, hay 3 formas de ocultar qué productos se muestran:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para a visualização de gráfico, existem 3 maneiras de ocultar quais produtos estão sendo exibidos:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.filterTopToSelectProduct','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'1. Use the filter at the top to select the product(s).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'1. Utilisez le filtre en haut pour sélectionner le(s) produit(s).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'1. Use el filtro en la parte superior para seleccionar los productos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'1. Use o filtro na parte superior para selecionar o(s) produto(s).');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.productInLegendOfGraph','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'2. Click on a product in the legend of the graph.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'2. Cliquez sur un produit dans la légende du graphique.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'2. Haga clic en un producto en la leyenda del gráfico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'2. Clique em um produto na legenda do gráfico.');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastOutput.uncheckDisplay','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'3. Uncheck the Display checkbox on the far left of the tabular view.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'3. Décochez la case "Afficher" à l extrême gauche de la vue tabulaire.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'3. Desmarque la casilla de verificación Mostrar en el extremo izquierdo de la vista tabular.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'3. Desmarque a caixa de seleção Exibir na extremidade esquerda da visualização tabular.');-- pr


-- forecastSummary


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.enableUserToSeeSummary','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enable users to see a summary of their final (selected) forecasts for the entire forecast period. To view the forecasts by month, use the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permettre aux utilisateurs de voir un résumé de leurs prévisions finales (sélectionnées) pour toute la période de prévision. Pour visualiser les prévisions par mois, utilisez le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permita que los usuarios vean un resumen de sus pronósticos finales (seleccionados) para todo el período de pronóstico. Para ver las previsiones por mes, utilice el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permita que os usuários vejam um resumo de suas previsões finais (selecionadas) para todo o período de previsão. Para visualizar as previsões por mês, use o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.seeForecastAcrossPlanningUnits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this view to see your forecast across planning units and regions. Directly update the forecast method for every region-planning unit combination and add notes in the table, if desired. Forecast selections can also be updated in the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette vue pour afficher vos prévisions dans les unités de planification et les régions. Mettez directement à jour la méthode de prévision pour chaque combinaison région-unité de planification et ajoutez des notes dans le tableau, si vous le souhaitez. Les sélections de prévisions peuvent également être mises à jour dans le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta vista para ver su previsión en unidades de planificación y regiones. Actualice directamente el método de pronóstico para cada combinación de unidad de planificación de región y agregue notas en la tabla, si lo desea. Las selecciones de pronóstico también se pueden actualizar en el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta visualização para ver sua previsão em todas as unidades e regiões de planejamento. Atualize diretamente o método de previsão para cada combinação de unidade de planejamento de região e adicione notas na tabela, se desejar. As seleções de previsão também podem ser atualizadas no');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.seeYourForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this view to see your forecast at a');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette vue pour voir vos prévisions à un');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Use esta vista para ver su pronóstico en un');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta visualização para ver sua previsão em um');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.nationalLevel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'national level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'niveau national');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'nivel nacional');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'nível naçional');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.evaluateYour','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'and evaluate your');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'et évaluez votre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'y evaluar su');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'e avalie seu');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.procurementSurplusGap','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'procurement surplus or gaps');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'surplus ou écarts d approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'excedentes o brechas de adquisiciones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'excedentes ou lacunas de compras');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.thisIsNotAFullSP','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This is not a full supply plan, but a high-level procurement surplus/gap analysis. We recommend importing your forecast into the supply planning module for granular supply planning.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Il ne s agit pas d un plan d approvisionnement complet, mais d une analyse de haut niveau des excédents/écarts d approvisionnement. Nous vous recommandons d importer vos prévisions dans le module de planification de l approvisionnement pour une planification granulaire de l approvisionnement.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este no es un plan de suministro completo, sino un análisis de brechas/superávit de adquisiciones de alto nivel. Recomendamos importar su pronóstico al módulo de planificación de suministro para una planificación de suministro granular.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este não é um plano de fornecimento completo, mas uma análise de excedentes/lacunas de compras de alto nível. Recomendamos importar sua previsão para o módulo de planejamento de fornecimento para um planejamento de fornecimento granular.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.forTheDataToDisplay','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the data to display here properly, enter the following data:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour que les données s affichent ici correctement, saisissez les données suivantes :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para que los datos se muestren aquí correctamente, ingrese los siguientes datos:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para que os dados sejam exibidos aqui corretamente, insira os seguintes dados:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.under','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Under');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En dessous de');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por debajo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Debaixo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.atBegining','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'at the beginning of your forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'au début de votre période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'al comienzo de su período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'no início do período de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.existingShipments','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Existing shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions existantes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos existentes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas existentes');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.duringYourForecastPeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'during your forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pendant votre période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'durante su período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'durante o período de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.desiredMonthOfStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Months of stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock souhaités');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses deseados de existencias');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque desejados');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.enOfForecastPeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'at the end of your forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'à la fin de votre période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'al final de su período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'no final do período de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.unitProces','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unit Prices');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prix ​​unitaires');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precios unitarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços unitários');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.updateVersionSettings','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update Version Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à jour les paramètres de version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar configuración de versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar configurações de versão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.freightCostCalculated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Freight cost is calculated as a percentage of product cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le coût du transport est calculé en pourcentage du coût du produit');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El costo del flete se calcula como un porcentaje del costo del producto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O custo do frete é calculado como uma porcentagem do custo do produto');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.calculateProcurementSurplus','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating the Procurement Surplus/Gap');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul de l excédent/déficit d approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo del excedente/brecha de adquisiciones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando o excedente/lacuna de aquisição');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.forecastFromJan2021','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For example, a forecast from Jan 2021 to Dec 2023 (36 month forecast)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par exemple, une prévision de janvier 2021 à décembre 2023 (prévision sur 36 mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por ejemplo, una previsión de enero de 2021 a diciembre de 2023 (previsión de 36 meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por exemplo, uma previsão de janeiro de 2021 a dezembro de 2023 (previsão de 36 meses)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.stockEndOfDec','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock(end of Dec 2020)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock (fin décembre 2020)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock (finales de diciembre de 2020)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque (final de dezembro de 2020)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.existingShipmentsJan2021','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Existing Shipments (Jan 2021 - Dec 2023)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions existantes (janvier 2021 - décembre 2023)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos existentes (enero de 2021 - diciembre de 2023)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas existentes (janeiro de 2021 - dezembro de 2023)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.stockEndOfDec2023','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock(end of Dec 2023)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock (fin décembre 2023)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock (finales de diciembre de 2023)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque (final de dezembro de 2023)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.desiredMonthOfStockEndOfDec2023','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Months of Stock (end of Dec 2023)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock souhaités (fin décembre 2023)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses deseados de existencias (finales de diciembre de 2023)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de Estoque Desejados (final de dezembro de 2023)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.desiredStockEndOfDec2023','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Desired Stock(end of Dec 2023)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock souhaité (fin décembre 2023)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Acción deseada (finales de diciembre de 2023)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque Desejado (final de dezembro de 2023)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.calculatingProcurementGap','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating the Procurement Costs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul des coûts d approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo de los costos de adquisición');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando os custos de aquisição');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.costIsOnlyCalculated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: the cost is only calculated if there is a procurement gap, not if there is a surplus');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : le coût n est calculé que s il y a un déficit d approvisionnement, pas s il y a un excédent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: el costo solo se calcula si hay una brecha de adquisición, no si hay un excedente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: o custo só é calculado se houver uma lacuna de aquisição, não se houver um excedente');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.forecastSummary.assumingUnitCostOf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the example above, assuming a unit cost of 0.10 USD and a freight % of 7%:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour l exemple ci-dessus, en supposant un coût unitaire de 0,10 USD et un % de fret de 7 % :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el ejemplo anterior, suponiendo un costo unitario de 0,10 USD y un % de flete del 7 %:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o exemplo acima, supondo um custo unitário de 0,10 USD e um % de frete de 7%:');-- pr



-- buildTreeComponent

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.validNumLessThan10Dig','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter a valid number having less then 10 digits.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un numéro valide comportant moins de 10 chiffres.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un número válido que tenga menos de 10 dígitos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número válido com menos de 10 dígitos.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectForecastigUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select forecasting unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner l unité de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione la unidad de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a unidade de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectPlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select planning unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner l unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a unidade de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.validNumLessThanEqual3Dig','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter a valid number having less then equal to 3 digit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un nombre valide inférieur à 3 chiffres.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un número válido que tenga menos de 3 dígitos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número válido com menos de 3 dígitos.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.valid10DigNum','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter a valid 10 digit number.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un numéro à 10 chiffres valide.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un número válido de 10 dígitos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número válido de 10 dígitos.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.Enter#ofPU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter # of pu per visit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nombre de pu par visite.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el número de pu por visita.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira # de pu por visita.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectATree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select a tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner un arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, selecione uma árvore.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectScenarioFirst','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select scenario first.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez d abord sélectionner le scénario.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el escenario primero.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o cenário primeiro.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.sureYouWantToDeleteScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Are you sure you want to delete this scenario.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous vraiment supprimer ce scénario ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Está seguro de que desea eliminar este escenario?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tem certeza de que deseja excluir este cenário.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.youCanNotDeleteScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You cant delete the default scenario.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous ne pouvez pas supprimer le scénario par défaut.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No puede eliminar el escenario predeterminado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você não pode excluir o cenário padrão.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectScenarioFirstLabel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select scenario first.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez d abord sélectionner le scénario.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el escenario primero.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o cenário primeiro.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.creatingANewNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You are creating a new node.Please submit the node data first and then apply modeling/transfer.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous créez un nouveau nœud. Veuillez d abord soumettre les données du nœud, puis appliquer la modélisation/le transfert.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Está creando un nuevo nodo. Envíe primero los datos del nodo y luego aplique el modelado/transferencia.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você está criando um novo nó. Envie os dados do nó primeiro e depois aplique a modelagem/transferência.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.InsertRow','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Insert Row');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Insérer une ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Insertar fila');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserir linha');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectModelingTypeBeforeProceeding','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select modeling type before proceeding.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner le type de modélisation avant de continuer.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el tipo de modelado antes de continuar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, selecione o tipo de modelagem antes de continuar.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.treeValidation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'TreeValidation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ArbreValidation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TreeValidation');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Validação de árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.failToCreateFileStream','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error: Failed to create file stream.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur : Échec de la création du flux de fichiers.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error: no se pudo crear la transmisión de archivos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro: Falha ao criar fluxo de arquivos.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.indefinately','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'indefinitely');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'indéfiniment');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'indefinidamente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'indefinidamente');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.calculatePUPerInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Calculated PU per interval per');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT PU calculé par intervalle par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT PU calculado por intervalo por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT Calculado PU por intervalo por');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.CalculatePuPerUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Calculated PU per usage per');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT PU calculé par utilisation par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT PU calculada por uso por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT Calculado PU por uso por');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.howManyPUPerInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many PU per interval per');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien de PU par intervalle par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántas PU por intervalo por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos PU por intervalo por');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.howManyPUPerUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many PU per usage per');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien de PU par utilisation par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántas PU por uso por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos PU por uso por');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.change%Points','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change (% points)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement (% points)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio (% puntos)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração (% pontos)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.targetChange%','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Target change (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement cible (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio objetivo (%)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração de meta (%)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.manualChangesAffect','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manual Change affects future month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le changement manuel affecte le mois à venir');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El cambio manual afecta el mes futuro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A mudança manual afeta o mês futuro');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.showSeasonalityAndmanualChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Seasonality & manual change');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher la saisonnalité et le changement manuel');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar estacionalidad y cambio manual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar sazonalidade e alteração manual');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectDateWithinForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select date within forecast range');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner une date dans la plage de prévisions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione la fecha dentro del rango de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione a data dentro do intervalo de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.sumOfChildren','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of children:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme des enfants :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de niñas:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma de filhos:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.sureYouWantToDeleteNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Are you sure you want to delete this node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous vraiment supprimer ce nœud ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Está seguro de que desea eliminar este nodo?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tem certeza de que deseja excluir este nó.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.treeList','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree List');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Liste des arbres');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lista de árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lista de árvores');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.addScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter un scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar cenário');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.editScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier le scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar cenário');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.deleteScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer le scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir cenário');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.DispalyDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date d affichage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar fecha');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de exibição');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.AddEditNodeModelingTransfer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/Edit Node - Modeling/Transfer');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/Modifier un nœud - Modélisation/Transfert');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar/Editar Nodo - Modelado/Transferencia');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar/editar nó - modelagem/transferência');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.nodeChangesOverTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If a node changes over time, a user can utilize the Modeling/Transfer tab to model growth/loss within a single node or a transfer from one node to another. Note that this functionality is only available for Number (#) Nodes and Percentage (%) Nodes (including Forecasting Units and Planning Units).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si un nœud change au fil du temps, un utilisateur peut utiliser l onglet Modélisation/Transfert pour modéliser la croissance/la perte au sein d un nœud unique ou un transfert d un nœud à un autre. Notez que cette fonctionnalité n est disponible que pour les nœuds de nombre (#) et les nœuds de pourcentage (%) (y compris les unités de prévision et les unités de planification).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si un nodo cambia con el tiempo, un usuario puede utilizar la pestaña Modelado/Transferencia para modelar el crecimiento/pérdida dentro de un solo nodo o una transferencia de un nodo a otro. Tenga en cuenta que esta funcionalidad solo está disponible para Nodos de Número (#) y Nodos de Porcentaje (%) (incluyendo Unidades de Pronóstico y Unidades de Planificación).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se um nó mudar ao longo do tempo, um usuário pode utilizar a guia Modelagem/Transferência para modelar o crescimento/perda em um único nó ou uma transferência de um nó para outro. Observe que essa funcionalidade só está disponível para nós de número (#) e nós de porcentagem (%) (incluindo unidades de previsão e unidades de planejamento).');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.usingThisTab','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Using this tab');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisation de cet onglet');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usando esta pestaña');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usando esta guia');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.modelingAndTranferHaveDifferentModelingType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling and transfer can have four different modeling types:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La modélisation et le transfert peuvent avoir quatre types de modélisation différents :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El modelado y la transferencia pueden tener cuatro tipos de modelado diferentes:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A modelagem e a transferência podem ter quatro tipos diferentes de modelagem:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.nodeCalculations','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Calculation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul de nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo de nodos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo do Nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.Linear','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linear');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Linéaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lineal');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Linear');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.staticNumEachMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'a static number each month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'un nombre fixe chaque mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'un número estático cada mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'um número estático a cada mês');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.staticPercentEachMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'a static percentage each month, calculated based on the starting month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'un pourcentage statique chaque mois, calculé en fonction du mois de départ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'un porcentaje estático cada mes, calculado en base al mes de inicio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'uma porcentagem estática a cada mês, calculada com base no mês inicial');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exponential','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Exponential');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exponentielle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Exponencial');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exponencial');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.percentetEachMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'a percentage each month, calculated on the previous month as a rolling percentage');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'un pourcentage chaque mois, calculé sur le mois précédent en pourcentage glissant');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'un porcentaje cada mes, calculado sobre el mes anterior como un porcentaje móvil');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'uma porcentagem a cada mês, calculada no mês anterior como uma porcentagem contínua');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.staticPercentEachMonthIfStartingMonthIs30%','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'a static percentage each month (e.g. if the starting month is 30% and the change is +1% each month, next month is 31%, the next is 32%, and so on)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'un pourcentage statique chaque mois (par exemple, si le mois de départ est de 30 % et que la variation est de +1 % chaque mois, le mois suivant est de 31 %, le suivant est de 32 %, etc.)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'un porcentaje estático cada mes (por ejemplo, si el mes inicial es 30 % y el cambio es +1 % cada mes, el próximo mes es 31 %, el siguiente es 32 %, y así sucesivamente)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'uma porcentagem estática a cada mês (por exemplo, se o mês inicial for 30% e a alteração for +1% a cada mês, o próximo mês for 31%, o próximo for 32% e assim por diante)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.qatModelingCalculationTool','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT has a modeling calculator tool for users should they need assistance in calculating the month-on-month change over time based on an ending target date & percentage or total percentage change over time. The modelling calculator is only available for a # node and a % node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT dispose d un outil de calcul de modélisation pour les utilisateurs s ils ont besoin d aide pour calculer la variation d un mois à l autre dans le temps en fonction d une date cible et d un pourcentage de fin ou d une variation totale en pourcentage dans le temps. Le calculateur de modélisation n est disponible que pour un nœud # et un nœud %.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT tiene una herramienta de calculadora de modelado para los usuarios en caso de que necesiten ayuda para calcular el cambio mensual a lo largo del tiempo en función de una fecha y un porcentaje objetivo final o un cambio porcentual total a lo largo del tiempo. La calculadora de modelado solo está disponible para un nodo # y un nodo %.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A QAT tem uma ferramenta de cálculo de modelagem para os usuários que precisam de ajuda para calcular a mudança mensal ao longo do tempo com base em uma data final e porcentagem ou alteração percentual total ao longo do tempo. A calculadora de modelagem está disponível apenas para um nó # e um nó %.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.additionallyClickingOnMonthlyData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Additionally, by clicking on “Show Monthly Data,” users can see how their modelling and transfer inputs have affected the monthly data in both a graphical and tabular form. In the tabular data, users may add a manual change for a specific month or input a seasonality index percentage (only available for % nodes), as needed. If a user checks “Manual Change affects future month,” the manual amount added to the end of the month will carry over to the beginning of the next month. If neither of these fields are relevant, users can uncheck “Show (seasonality &) manual change” to hide these columns.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'De plus, en cliquant sur "Afficher les données mensuelles", les utilisateurs peuvent voir comment leurs entrées de modélisation et de transfert ont affecté les données mensuelles sous forme graphique et tabulaire. Dans les données tabulaires, les utilisateurs peuvent ajouter une modification manuelle pour un mois spécifique ou saisir un pourcentage d indice de saisonnalité (uniquement disponible pour les nœuds %), selon les besoins. Si un utilisateur coche "La modification manuelle affecte le mois futur", le montant manuel ajouté à la fin du mois sera reporté au début du mois suivant. Si aucun de ces champs n est pertinent, les utilisateurs peuvent décocher "Afficher (saisonnalité et) modification manuelle" pour masquer ces colonnes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Además, al hacer clic en "Mostrar datos mensuales", los usuarios pueden ver cómo sus entradas de modelado y transferencia han afectado los datos mensuales tanto en forma gráfica como tabular. En los datos tabulares, los usuarios pueden agregar un cambio manual para un mes específico o ingresar un porcentaje de índice de estacionalidad (solo disponible para % de nodos), según sea necesario. Si un usuario marca "El cambio manual afecta el mes futuro", el monto manual agregado al final del mes se trasladará al comienzo del mes siguiente. Si ninguno de estos campos es relevante, los usuarios pueden desmarcar "Mostrar (estacionalidad y) cambio manual" para ocultar estas columnas.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Além disso, clicando em “Mostrar dados mensais”, os usuários podem ver como suas entradas de modelagem e transferência afetaram os dados mensais em forma gráfica e tabular. Nos dados tabulares, os usuários podem adicionar uma alteração manual para um mês específico ou inserir uma porcentagem do índice de sazonalidade (disponível apenas para % de nós), conforme necessário. Se um usuário marcar "A alteração manual afeta o mês futuro", o valor manual adicionado ao final do mês será transferido para o início do próximo mês. Se nenhum desses campos for relevante, os usuários poderão desmarcar "Mostrar (sazonalidade e) alteração manual" para ocultar essas colunas.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.rulesForTransferNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Rules for Transfer Nodes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Règles pour les nœuds de transfert');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Reglas para nodos de transferencia');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Regras para nós de transferência');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.NumberNodeCanTransfer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number node can transfer to another number only but they should be at the same level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nœud de numéro ne peut être transféré que vers un autre numéro, mais ils doivent être au même niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El nodo numérico solo puede transferirse a otro número, pero deben estar al mismo nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O nó do número pode transferir apenas para outro número, mas eles devem estar no mesmo nível');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.percentetFUPUNodes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage,FU & PU nodes can transfer to each othet but they should belog to the same parent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le pourcentage, les nœuds FU et PU peuvent être transférés les uns aux autres, mais ils doivent appartenir au même parent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los nodos de porcentaje, FU y PU pueden transferirse entre sí, pero deben pertenecer al mismo padre');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os nós de porcentagem, FU e PU podem ser transferidos entre si, mas devem pertencer ao mesmo pai');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.example','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Examples');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemples');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplos');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.simpleGrowth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Simple Growth');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Croissance simple');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crecimiento simple');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Crescimento Simples');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleBelowShowPopularGrowth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(linear #) - the example below shows a population growth each month by 500/month from January 2022 to December 2024.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(# linéaire) - l exemple ci-dessous montre une croissance démographique chaque mois de 500/mois de janvier 2022 à décembre 2024.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(n.º lineal): el siguiente ejemplo muestra un crecimiento de la población cada mes en 500/mes desde enero de 2022 hasta diciembre de 2024.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(linear #) - o exemplo abaixo mostra um crescimento populacional a cada mês em 500/mês de janeiro de 2022 a dezembro de 2024.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.simpleLoss','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Simple Loss');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Perte simple');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pérdida simple');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Perda Simples');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleBelowShowAttrition','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(linear #) - the example below shows attrition each month by 100/month from January 2022 to December 2024. QAT utilizes a negative number to denote a decrease or loss.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(# linéaire) - l exemple ci-dessous montre l attrition chaque mois de 100/mois de janvier 2022 à décembre 2024. QAT utilise un nombre négatif pour indiquer une diminution ou une perte.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(n.º lineal): el siguiente ejemplo muestra la deserción cada mes por 100/mes desde enero de 2022 hasta diciembre de 2024. QAT utiliza un número negativo para indicar una disminución o pérdida.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(linear #) - o exemplo abaixo mostra o atrito a cada mês em 100/mês de janeiro de 2022 a dezembro de 2024. QAT utiliza um número negativo para denotar uma diminuição ou perda.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleBelowShowSteady','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(linear %) – the example below shows a steady population growth each month by 2% from January 2022 to December 2024. QAT has calculated this change to be increasing the population by 108.64 each month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(% linéaire) - l exemple ci-dessous montre une croissance démographique régulière de 2 % chaque mois de janvier 2022 à décembre 2024. QAT a calculé que ce changement augmente la population de 108,64 chaque mois.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(% lineal): el siguiente ejemplo muestra un crecimiento constante de la población cada mes en un 2 % desde enero de 2022 hasta diciembre de 2024. QAT ha calculado que este cambio aumenta la población en 108,64 cada mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(%) linear – o exemplo abaixo mostra um crescimento populacional constante de 2% a cada mês de janeiro de 2022 a dezembro de 2024. A QAT calculou que essa mudança está aumentando a população em 108,64 a cada mês.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleBelowShowPopulation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(exponential %) - the example below shows a population growth each month by 1% from January 2022 to December 2024. Because the growth is exponential, the change differs each month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(exponentielle %) - l exemple ci-dessous montre une croissance démographique de 1 % chaque mois de janvier 2022 à décembre 2024. Parce que la croissance est exponentielle, le changement diffère chaque mois.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(% exponencial): el siguiente ejemplo muestra un crecimiento de la población cada mes del 1 % desde enero de 2022 hasta diciembre de 2024. Debido a que el crecimiento es exponencial, el cambio difiere cada mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(exponencial %) - o exemplo abaixo mostra um crescimento populacional de 1% a cada mês de janeiro de 2022 a dezembro de 2024. Como o crescimento é exponencial, a mudança é diferente a cada mês.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.QATCalculationChanges','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates this change to be 54.32 in Jan-22 month,');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule cette variation à 54,32 en janvier-22 mois,');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula este cambio en 54,32 en enero-22 mes,');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A QAT calcula que essa mudança seja 54,32 no mês de janeiro de 22,');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.QATCalculationChanges54.32','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates this change to be 54.86 in Feb-22 month, and');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule ce changement à 54,86 en février-22 mois, et');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula este cambio en 54,86 en el mes del 22 de febrero, y');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A QAT calcula que essa alteração seja de 54,86 no mês de fevereiro de 22 e');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.QATCalculatesChange55.41','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates this change to be 55.41 in Mar-22');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule ce changement à 55,41 en mars 22');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula este cambio en 55.41 en Mar-22');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A QAT calcula que essa mudança seja de 55,41 em Mar-22');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.multiYearLoss','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multi-year Loss');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Perte pluriannuelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pérdida de varios años');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Perda de vários anos');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleShowDifferentRates','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'the example below shows a different rate of attrition (loss) each year. Year 1 (January 2022 to December 2022) decreases the population by 1% or 54.32 each month, Year 2 (January 2023 to December 2023) decreases the population by 2% or 95.6 each month, etc. QAT utilizes a negative number to denote a decrease or loss.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'l exemple ci-dessous montre un taux d attrition (perte) différent chaque année. L année 1 (janvier 2022 à décembre 2022) diminue la population de 1 % ou 54,32 chaque mois, l année 2 (janvier 2023 à décembre 2023) diminue la population de 2 % ou 95,6 chaque mois, etc. QAT utilise un nombre négatif pour désigner un diminution ou perte.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'el siguiente ejemplo muestra una tasa diferente de deserción (pérdida) cada año. El año 1 (enero de 2022 a diciembre de 2022) reduce la población en un 1 % o 54,32 cada mes, el año 2 (enero de 2023 a diciembre de 2023) reduce la población en un 2 % o 95,6 cada mes, etc. QAT utiliza un número negativo para indicar un disminución o pérdida.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'o exemplo abaixo mostra uma taxa diferente de atrito (perda) a cada ano. O ano 1 (janeiro de 2022 a dezembro de 2022) diminui a população em 1% ou 54,32 a cada mês, o ano 2 (janeiro de 2023 a dezembro de 2023) diminui a população em 2% ou 95,6 a cada mês, etc. QAT utiliza um número negativo para denotar um diminuição ou perda.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.exampleShowTransfer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'the example below shows a transfer of 250 patients each month for one year, January 2022 to December 2022, from the current node (Adults 1st Line) to another node (Adults 2nd Line). This transfer will also appear on the other node (Adults 2nd Line) greyed-out to signify an non-editable change.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'l exemple ci-dessous montre un transfert de 250 patients chaque mois pendant un an, de janvier 2022 à décembre 2022, du nœud actuel (Adultes 1ère ligne) vers un autre nœud (Adultes 2e ligne). Ce transfert apparaîtra également sur l autre nœud (Adultes 2e ligne) grisé pour signifier un changement non modifiable.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'el siguiente ejemplo muestra una transferencia de 250 pacientes cada mes durante un año, de enero de 2022 a diciembre de 2022, desde el nodo actual (Adultos 1.ª línea) a otro nodo (Adultos 2.ª línea). Esta transferencia también aparecerá en el otro nodo (Adultos 2da línea) atenuada para indicar un cambio no editable.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'o exemplo abaixo mostra uma transferência de 250 pacientes por mês durante um ano, de janeiro de 2022 a dezembro de 2022, do nó atual (Adultos 1ª Linha) para outro nó (Adultos 2ª Linha). Esta transferência também aparecerá no outro nó (adultos 2ª linha) em cinza para significar uma alteração não editável.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.addEditNodeData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/Edit Node - Node Data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/Modifier un nœud - Données de nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar/Editar nodo - Datos de nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar/editar nó - dados do nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.allowUserToSpecifyTypeOfNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enable allows user to specify the type of node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Activer permet à l utilisateur de spécifier le type de nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Habilitar permite al usuario especificar el tipo de nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ativar permite que o usuário especifique o tipo de nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.value','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'stastatic.buildTreeComp.potentialChildrentic','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Potential Children');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Enfants potentiels');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Niñas potenciales');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Filhos em Potencial');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.advanceFunctionality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Advanced Functionality');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fonctionnalité avancée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Funcionalidad avanzada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Funcionalidade avançada');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.aggregation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Aggregation ∑');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agrégation ∑');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregación ∑');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agregação ∑');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.orNumber','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'or Number');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ou Numéro');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'o número');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'ou Número');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.definedAtThisNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Defined at this node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Défini à ce nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Definido en este nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Definido neste nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.definedAsAPercentage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Defined as a percentage of the parent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Défini comme un pourcentage du parent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Definido como un porcentaje del padre');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Definido como uma porcentagem do pai');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.definedAsAPercentageParentFU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Defined as a percentage of the parent and forecasting unit parameters');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Défini comme un pourcentage des paramètres de l unité mère et de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Definido como un porcentaje de los parámetros de la unidad principal y de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Definido como uma porcentagem dos parâmetros da unidade pai e de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.definedAsAPercentageParentPU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Defined as a percentage of the parent and planning unit parameters');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Défini comme un pourcentage des paramètres du parent et de l unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Definido como un porcentaje de los parámetros de la unidad principal y de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Definido como uma porcentagem dos parâmetros da unidade pai e de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.changesOverTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Changes Over Time');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changements au fil du temps');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambios a lo largo del tiempo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudanças ao longo do tempo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.treeStructureStayConstant','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'While the tree structure stays constant throughout time, node percentages and values can change over time. The three functionalities below are available in each node and allow the user to control how nodes change over time:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Bien que la structure arborescente reste constante dans le temps, les pourcentages et les valeurs des nœuds peuvent changer au fil du temps. Les trois fonctionnalités ci-dessous sont disponibles dans chaque nœud et permettent à l utilisateur de contrôler l évolution des nœuds dans le temps :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si bien la estructura de árbol se mantiene constante a lo largo del tiempo, los porcentajes y valores de los nodos pueden cambiar con el tiempo. Las tres funcionalidades a continuación están disponibles en cada nodo y permiten al usuario controlar cómo cambian los nodos con el tiempo:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Embora a estrutura da árvore permaneça constante ao longo do tempo, as porcentagens e os valores dos nós podem mudar ao longo do tempo. As três funcionalidades abaixo estão disponíveis em cada nó e permitem que o usuário controle como os nós mudam ao longo do tempo:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.modelingTranferTab','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling/Transfer Tab');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Onglet Modélisation/Transfert');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ficha Modelado/Transferencia');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Guia Modelagem/Transferência');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.allowUserToSpecifyExactRate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Allows user to specify the exact rate of change');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet à l utilisateur de spécifier le taux exact de changement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permite al usuario especificar la tasa exacta de cambio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permite que o usuário especifique a taxa exata de mudança');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.allowUserToLinkTwoNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Allows users to link two nodes together - so the decrease');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet aux utilisateurs de relier deux nœuds ensemble - donc la diminution');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permite a los usuarios vincular dos nodos, por lo que la disminución');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permite que os usuários vinculem dois nós - então a diminuição');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.extrapolationTab','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolation Tab');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Onglet Extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ficha Extrapolación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Aba Extrapolação');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.numberNodeOnly','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(number nodes only)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(numéroter les nœuds uniquement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(solo nodos numéricos)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(somente nós de número)');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.allowUserToUseHistoricalData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Allows users to use historical data to extrapolate future change. , Enable this feature by checking  Extrapolation  box. Note that if you do - the Modeling/Transfer Tab will be hidden as it is only possible to use one at a time. Any data previously entered in the Modeling/Transfer Tab will be cleared.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet aux utilisateurs d utiliser des données historiques pour extrapoler les changements futurs. , Activez cette fonctionnalité en cochant la case "Extrapolation". Notez que si vous le faites, l onglet Modélisation/Transfert sera masqué car il n est possible d en utiliser qu un à la fois. Toutes les données saisies précédemment dans l onglet Modélisation/Transfert seront effacées.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permite a los usuarios utilizar datos históricos para extrapolar cambios futuros. , Habilite esta función marcando la casilla  Extrapolación . Tenga en cuenta que si lo hace, la pestaña Modelado/Transferencia se ocultará ya que solo es posible usar una a la vez. Se borrarán todos los datos ingresados ​​previamente en la pestaña Modelado/Transferencia.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permite que os usuários usem dados históricos para extrapolar alterações futuras. , Habilite este recurso marcando a caixa  Extrapolação . Observe que se você fizer isso - a guia Modelagem/Transferência ficará oculta, pois só é possível usar uma de cada vez. Quaisquer dados inseridos anteriormente na guia Modelagem/Transferência serão apagados.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.noChangeOverTimeDesired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If no change over time is desired, simply do not use the Modeling/Transfer and Extrapolation tabs, and the node value will equal the value entered or calculated on the  Node Data tab.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si aucun changement dans le temps n est souhaité, il suffit de ne pas utiliser les onglets  Modélisation/Transfert et Extrapolation, et la valeur du nœud sera égale à la valeur saisie ou calculée dans l onglet Données du nœud.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no desea ningún cambio a lo largo del tiempo, simplemente no utilice las pestañas Modelado/Transferencia y Extrapolación, y el valor del nodo será igual al valor ingresado o calculado en la pestaña Datos del nodo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se nenhuma mudança ao longo do tempo for desejada, simplesmente não use as guias Modelagem/Transferência e Extrapolação, e o valor do nó será igual ao valor inserido ou calculado na guia Dados do nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.usingScenarios','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Using Scenarios');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utiliser des scénarios');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Uso de escenarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usando cenários');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.modelDifferentValues','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Scenarios are used to model different values for the same tree.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les scénarios sont utilisés pour modéliser différentes valeurs pour le même arbre.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los escenarios se utilizan para modelar diferentes valores para el mismo árbol.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cenários são usados ​​para modelar valores diferentes para a mesma árvore.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.fixedAllScenarios','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fixed for All Scenarios');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fixe pour tous les scénarios');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fijo para todos los escenarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Corrigido para todos os cenários');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.uniqueToAllScenarios','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unique to each Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unique à chaque scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Única para cada escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Único para cada cenário');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.treeStructure','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree structure');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Arborescence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estructura de árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estrutura de árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.nodeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.modelingTranferExtrapolation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling/Transfer/Extrapolation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modélisation/Transfert/Extrapolation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Modelado/Transferencia/Extrapolación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelagem/Transferência/Extrapolação');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.tipsForSpecificUse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tips for specific use cases:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conseils pour des cas d utilisation spécifiques :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consejos para casos de uso específicos:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dicas para casos de uso específicos:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.forecastingNodeType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasting Node Type');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type de nœud de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de nodo de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de nó de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.selectDiscrete','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select "discrete" if this product will be used for limited time, and "continuous" if it will be used indefinitely.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez "discret" si ce produit sera utilisé pendant un temps limité, et "continu" s il sera utilisé indéfiniment.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione "discreto" si este producto se usará por tiempo limitado y "continuo" si se usará indefinidamente.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione "discreto" se este produto for usado por tempo limitado e "contínuo" se for usado indefinidamente.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.delayedOrPhasedProduct','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delayed or phased product usage?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisation du produit retardée ou échelonnée ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Uso del producto retrasado o escalonado?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uso do produto atrasado ou faseado?');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.productConsumptionIsDelayed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sometimes, the product consumption is delayed in relation the other higher levels of the tree. In the relevant');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Parfois, la consommation du produit est retardée par rapport aux autres niveaux supérieurs de l arbre. Dans le cas pertinent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A veces, el consumo del producto se retrasa en relación a los otros niveles superiores del árbol. en lo pertinente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Às vezes, o consumo do produto é atrasado em relação aos demais níveis superiores da árvore. No relevante');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.nodeUse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'node, use the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'nœud, utilisez le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo, utiliza el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'nó, use o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.lag','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lag');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Décalage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Retraso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atraso');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.fieldIndicateDelayed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'field to indicate this delay.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'champ pour indiquer ce délai.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'campo para indicar este retraso.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'campo para indicar este atraso.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.productUsageBegain','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For example, if the product usage will begin 2 months after the parent node dates, enter "2" in this field.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par exemple, si l utilisation du produit commencera 2 mois après les dates du nœud parent, saisissez « 2 » dans ce champ.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por ejemplo, si el uso del producto comenzará 2 meses después de las fechas del nodo principal, ingrese "2" en este campo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por exemplo, se o uso do produto começar 2 meses após as datas do nó pai, insira "2" neste campo.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.fieldCanAlsoBeUsed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This field can also be used where the product switches over time for example, if forecasting units A, B, and C are used in secession for two months at a time, you can set up your tree with Forecasting Unit A (discrete for 2 months, lag=0), Forecasting Unit B (discrete for 2 months, lag=2), Forecasting Unit C (discrete for 2 months, lag = 4).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce champ peut également être utilisé lorsque le produit change dans le temps, par exemple, si les unités de prévision A, B et C sont utilisées en sécession pendant deux mois à la fois, vous pouvez configurer votre arbre avec l unité de prévision A (discrète pour 2 mois , décalage=0), Unité de prévision B (discrète sur 2 mois, décalage=2), Unité de prévision C (discrète sur 2 mois, décalage = 4).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este campo también se puede usar cuando el producto cambia con el tiempo, por ejemplo, si las unidades de pronóstico A, B y C se usan en secesión durante dos meses a la vez, puede configurar su árbol con la Unidad de pronóstico A (discreto para 2 meses). , lag=0), Unidad de Pronóstico B (discreta por 2 meses, lag=2), Unidad de Pronóstico C (discreta por 2 meses, lag = 4).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este campo também pode ser usado onde o produto muda ao longo do tempo, por exemplo, se as unidades de previsão A, B e C forem usadas em secessão por dois meses de cada vez, você pode configurar sua árvore com a Unidade de previsão A (discreta por 2 meses , lag=0), Unidade de previsão B (discreta para 2 meses, lag=2), Unidade de previsão C (discreta para 2 meses, lag = 4).');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.multiMonthConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multi-month Consumption Patterns?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des habitudes de consommation sur plusieurs mois ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Patrones de consumo de varios meses?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Padrões de consumo de vários meses?');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.HowOftenIsTheProduct','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How often is the product actually "consumed"? is it monthly or every three months? Consumption can be defined at different levels depending on your supply chain. In the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'À quelle fréquence le produit est-il réellement "consommé" ? c est mensuel ou tous les trois mois ? La consommation peut être définie à différents niveaux en fonction de votre chaîne d approvisionnement. Dans le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Con qué frecuencia se "consume" realmente el producto? es mensual o cada tres meses? El consumo se puede definir en diferentes niveles dependiendo de su cadena de suministro. En el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Com que frequência o produto é realmente "consumido"? é mensal ou a cada três meses? O consumo pode ser definido em diferentes níveis, dependendo da sua cadeia de suprimentos. No');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.planningUnitNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Unit Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud d unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo de unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó da Unidade de Planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.useThe','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'use the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'utilizar el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'use o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.consumptionInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Interval');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de consommation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Intervalo de consumo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de consumo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.fieldToIndicate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'field to indicate. For example, if the end user uses a product daily, but only picks up or buys the product every 2 months, enter "2" in the Consumption Interval field to account for a multi-month consumption pattern. Note that this is only available for Continuous nodes, as product is assumed to be “consumed” at the beginning of the usage period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'champ à indiquer. Par exemple, si l utilisateur final utilise un produit quotidiennement, mais ne récupère ou n achète le produit que tous les 2 mois, saisissez « 2 » dans le champ Intervalle de consommation pour tenir compte d un modèle de consommation sur plusieurs mois. Notez que cela n est disponible que pour les nœuds continus, car le produit est supposé être « consommé » au début de la période d utilisation.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'campo a indicar. Por ejemplo, si el usuario final usa un producto a diario, pero solo recoge o compra el producto cada 2 meses, ingrese "2" en el campo Intervalo de consumo para dar cuenta de un patrón de consumo de varios meses. Tenga en cuenta que esto solo está disponible para los nodos continuos, ya que se supone que el producto se "consume" al comienzo del período de uso.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'campo para indicar. Por exemplo, se o usuário final usar um produto diariamente, mas apenas pegar ou comprar o produto a cada 2 meses, insira "2" no campo Intervalo de consumo para contabilizar um padrão de consumo de vários meses. Observe que isso está disponível apenas para nós Contínuos, pois o produto é considerado “consumido” no início do período de uso.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.repetingForecastingUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Repeating Forecasting Usages?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisations répétées des prévisions ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Usos de pronóstico repetidos?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Repetindo Usos de Previsão?');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.multipleFUNodes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If multiple Forecasting Unit nodes share the same settings, consider using the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si plusieurs nœuds d unité de prévision partagent les mêmes paramètres, envisagez d utiliser le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si varios nodos de Unidad de pronóstico comparten la misma configuración, considere usar el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se vários nós da Unidade de previsão compartilharem as mesmas configurações, considere usar o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.saveYourCommonUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen to save your common usages, and then using the “Copy from Template” field to populate the fields in the forecasting unit nodes.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'écran pour enregistrer vos utilisations courantes, puis en utilisant le champ "Copier à partir du modèle" pour remplir les champs dans les nœuds d unité de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla para guardar sus usos comunes y luego usar el campo "Copiar de plantilla" para completar los campos en los nodos de la unidad de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela para salvar seus usos comuns e, em seguida, usando o campo "Copiar do modelo" para preencher os campos nos nós da unidade de previsão.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.manageTreeBuildTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage Tree - Build Trees');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer l arborescence - Construire des arborescences');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrar árbol - Construir árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar Árvore - Construir Árvores');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.enableUserToManageAndBuildForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enable users to manage and build forecast tree and scenarios, for any non-consumption forecasts (demographic, morbidity, services, etc). Note that more guidance is available after clicking into any specific node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permettre aux utilisateurs de gérer et de construire des arbres de prévision et des scénarios, pour toutes les prévisions de non-consommation (démographique, morbidité, services, etc.). Notez que plus de conseils sont disponibles après avoir cliqué sur un nœud spécifique.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permita que los usuarios administren y construyan árboles de pronóstico y escenarios, para cualquier pronóstico que no sea de consumo (demográfico, morbilidad, servicios, etc.). Tenga en cuenta que hay más orientación disponible después de hacer clic en cualquier nodo específico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permita que os usuários gerenciem e construam árvores e cenários de previsão, para quaisquer previsões de não consumo (demográficas, morbidade, serviços, etc). Observe que mais orientações estão disponíveis após clicar em qualquer nó específico.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.manageTheTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage the Tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer l arborescence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Administrar el árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerencie a árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.clickOnGearIcon','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on the gear icon');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur l icône d engrenage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en el icono de engranaje');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique no ícone de engrenagem');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.showEditHideForecastmethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'to show and edit or hide the forecast method, tree name, and region.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pour afficher et modifier ou masquer la méthode de prévision, le nom de l arbre et la région.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'para mostrar y editar u ocultar el método de pronóstico, el nombre del árbol y la región.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'para mostrar e editar ou ocultar o método de previsão, o nome da árvore e a região.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.buildingTheTree','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Building the Tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Construire l arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Construyendo el árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Construindo a árvore');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.forecastTreeIsBuildFromTopDown','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The forecast tree is built from the top down, using different types of “nodes”. See the Node Types and Node Actions below. Each forecast tree must include one or more Planning Unit Nodes, which form the forecast output, for that tree to be available in the Compare and Select screen. Each Planning Unit Node must stem from a Forecasting Unit Node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L arbre de prévision est construit de haut en bas, en utilisant différents types de « nœuds ». Voir les types de nœuds et les actions de nœud ci-dessous. Chaque arbre de prévision doit inclure un ou plusieurs nœuds d unité de planification, qui forment la sortie de prévision, pour que cet arbre soit disponible dans l écran « Comparer et sélectionner ». Chaque Nœud d Unité de Planification doit être issu d un Nœud d Unité de Prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El árbol de pronóstico se construye de arriba hacia abajo, utilizando diferentes tipos de "nodos". Consulte los tipos de nodos y las acciones de nodos a continuación. Cada árbol de pronóstico debe incluir uno o más nodos de unidades de planificación, que forman la salida del pronóstico, para que ese árbol esté disponible en la pantalla Comparar y seleccionar. Cada Nodo de Unidad de Planificación debe provenir de un Nodo de Unidad de Pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A árvore de previsão é construída de cima para baixo, usando diferentes tipos de “nós”. Consulte os tipos de nó e ações de nó abaixo. Cada árvore de previsão deve incluir um ou mais nós de unidade de planejamento, que formam a saída da previsão, para que essa árvore fique disponível na tela Comparar e selecionar. Cada Nó de Unidade de Planejamento deve originar-se de um Nó de Unidade de Previsão.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.nodeActions','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Actions');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Actions de nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Acciones de nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ações do nó');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.deleteSelectedNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete: Deletes the selected node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer : supprime le nœud sélectionné');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar: elimina el nodo seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir: Exclui o nó selecionado');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.duplicateSelectedNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate: Duplicates the selected node under the same parent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dupliquer : Duplique le nœud sélectionné sous le même parent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Duplicar: Duplica el nodo seleccionado bajo el mismo padre');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Duplicar: Duplica o nó selecionado sob o mesmo pai');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.addChildernToSelectedNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add: Adds a child to the selected node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter : ajoute un enfant au nœud sélectionné.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar: agrega una hija al nodo seleccionado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar: Adiciona um filho ao nó selecionado.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.changesOverTimeLabel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Changes Over Time');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changements au fil du temps');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambios a lo largo del tiempo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudanças ao longo do tempo');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.treeStructureStayConstantLabel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'While the tree structure stays constant throughout time, node percentages and values can change over time. Use the date dropdown to view the tree at any month. The three functionalities below are available in each node and allow the user to control how nodes change over time:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Bien que la structure arborescente reste constante dans le temps, les pourcentages et les valeurs des nœuds peuvent changer au fil du temps. Utilisez la liste déroulante des dates pour afficher l arbre à n importe quel mois. Les trois fonctionnalités ci-dessous sont disponibles dans chaque nœud et permettent à l utilisateur de contrôler l évolution des nœuds dans le temps :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si bien la estructura de árbol se mantiene constante a lo largo del tiempo, los porcentajes y valores de los nodos pueden cambiar con el tiempo. Use el menú desplegable de fechas para ver el árbol en cualquier mes. Las tres funcionalidades a continuación están disponibles en cada nodo y permiten al usuario controlar cómo cambian los nodos con el tiempo:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Embora a estrutura da árvore permaneça constante ao longo do tempo, as porcentagens e os valores dos nós podem mudar ao longo do tempo. Use o menu suspenso de data para visualizar a árvore em qualquer mês. As três funcionalidades abaixo estão disponíveis em cada nó e permitem que o usuário controle como os nós mudam ao longo do tempo:');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.linkTwoNodesTogether','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Allows users to link two nodes together – so the decrease from the source node is linked to the increase of the destination node. Useful for transitions.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet aux utilisateurs de relier deux nœuds ensemble - de sorte que la diminution du nœud source est liée à l augmentation du nœud de destination. Utile pour les transitions.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permite a los usuarios vincular dos nodos, por lo que la disminución del nodo de origen está vinculada al aumento del nodo de destino. Útil para transiciones.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permite que os usuários vinculem dois nós – de modo que a diminuição do nó de origem esteja vinculada ao aumento do nó de destino. Útil para transições.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.userToUseHistoricalData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Allows users to use historical data to extrapolate future change');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Permet aux utilisateurs d utiliser des données historiques pour extrapoler les changements futurs');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Permite a los usuarios utilizar datos históricos para extrapolar cambios futuros');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Permite que os usuários usem dados históricos para extrapolar alterações futuras');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.modelDifferentValuesScenarios','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Scenarios are used to model different values for the same tree. To add, edit or delete a scenario, use the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les scénarios sont utilisés pour modéliser différentes valeurs pour le même arbre. Pour ajouter, modifier ou supprimer un scénario, utilisez le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los escenarios se utilizan para modelar diferentes valores para el mismo árbol. Para agregar, editar o eliminar un escenario, use el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cenários são usados ​​para modelar valores diferentes para a mesma árvore. Para adicionar, editar ou excluir um cenário, use o');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.oiconNextToScenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'icon next to the scenario dropdown. Use the scenario dropdown to select which scenario to view and edit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'icône à côté de la liste déroulante des scénarios. Utilisez la liste déroulante des scénarios pour sélectionner le scénario à afficher et à modifier.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'junto al menú desplegable del escenario. Use el menú desplegable de escenarios para seleccionar qué escenario ver y editar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'ícone ao lado da lista suspensa do cenário. Use a lista suspensa de cenários para selecionar qual cenário visualizar e editar.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.fixedForAllScenarios','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fixed for All Scenarios');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fixe pour tous les scénarios');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fijo para todos los escenarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Corrigido para todos os cenários');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.buildTreeComp.point','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'point');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'indiquer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'punto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'apontar');-- pr


-- treeTemplate

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.treeTemplatePDF','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Template PDF');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.transferToFrom','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Transfer to/from');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Transfert vers/depuis');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Transferir a/desde');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Transferir de/para');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.startDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Start Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date de début');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha de inicio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de início');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.period','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Période');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Período');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Período');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.everyYear','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Every year');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Chaque année');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los años');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todo ano');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.every6Month','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Every 6 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous les 6 mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cada 6 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A cada 6 meses');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.everyQuater','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Every quarter');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Toute les quarts');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cada cuarto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A cada trimestre');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.everyMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Every month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Chaque mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cada mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todo mês');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.calculatedChangeForMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated change for month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement calculé pour le mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio calculado para el mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Variação calculada para o mês');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.returnToList','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Return To List');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Retour à la liste');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Volver a la lista');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Retornar à lista');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.needToAddInfo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Need to add info');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Besoin d ajouter des informations');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Necesito agregar información');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Precisa adicionar informações');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeTemplate.monthSector','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Month Selector');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélecteur de mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Selector de mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Seletor de mês');-- pr