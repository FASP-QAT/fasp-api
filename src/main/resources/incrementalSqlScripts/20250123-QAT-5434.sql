INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumption','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consensus Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation consensuelle');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo de consenso');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo de consenso');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionTxt','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This consumption type is only used if there are multiple programs selected for aggregation. Consensus consumption is the sum of selected programs actual consumption values each month, if available, otherwise it will sum the forecasted consumption values. Thus, it may include both forecasted and actual values.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce type de consommation n\'est utilisé que si plusieurs programmes sont sélectionnés pour l\'agrégation. La consommation consensuelle est la somme des valeurs de consommation réelles des programmes sélectionnés chaque mois, si disponibles, sinon elle additionnera les valeurs de consommation prévues. Ainsi, il peut inclure à la fois des valeurs prévues et réelles.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este tipo de consumo só é utilizado se houver vários programas selecionados para agregação. O consumo de consenso é a soma dos valores de consumo real dos programas selecionados a cada mês, se disponível, caso contrário, somará os valores de consumo previstos. Assim, pode incluir valores previstos e reais.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este tipo de consumo solo se utiliza si hay varios programas seleccionados para la agregación. El consumo de consenso es la suma de los valores de consumo real de los programas seleccionados cada mes, si están disponibles; de lo contrario, sumará los valores de consumo previstos. Por lo tanto, puede incluir valores tanto pronosticados como reales.');-- sp



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx1','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program A Forecasted Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmer une consommation prévue');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa A Consumo Previsto');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programar un consumo previsto');-- sp



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx2','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program A Actual Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme A Consommation réelle');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa A Consumo Real');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programar un consumo real');-- sp



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx3','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program B Forecasted Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation prévue du programme B');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo previsto do Programa B');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa B Consumo previsto');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx4','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program B Actual Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme B Consommation réelle');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Real do Programa B');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa B Consumo real');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx5','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program C Forecasted Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation prévue du programme C');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo previsto do Programa C');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa C Consumo previsto');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionEx6','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program C Actual Consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme C Consommation réelle');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo Real do Programa C');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa C Consumo real');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.consensusConsumptionFormula','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'550 (Program A Actual) + 300 (Program B Actual) + 1,050 (Program C Forecast)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'550 (programme A réel) + 300 (programme B réel) + 1 050 (programme C prévu)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'550 (Real do Programa A) + 300 (Real do Programa B) + 1.050 (Previsão do Programa C)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'550 (Programa A real) + 300 (Programa B real) + 1050 (Programa C Pronóstico)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.expiredStockNoteP2','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, all expiries within the same month will be aggregated (summed) into a single total quantity.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport inclut plusieurs programmes ou unités de planification, toutes les expirations au cours du même mois seront regroupées (additionnées) en une seule quantité totale.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, todos os vencimentos no mesmo mês serão agregados (somados) em uma única quantidade total.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye varios programas o unidades de planificación, todos los vencimientos dentro del mismo mes se agregarán (sumarán) en una única cantidad total.');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.showFormula.endingBalancePI1','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= Opening Balance - Consumption + Shipments +/- Adjustments - Projected Expired Stock');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= Solde d\'ouverture - Consommation + Expéditions +/- Ajustements - Stock expiré projeté');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= Saldo inicial - Consumo + Remessas +/- Ajustes - Estoque expirado projetado');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= Saldo de Apertura - Consumo + Envíos +/- Ajustes - Stock Vencido Proyectado');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.showFormula.endingBalance3.1','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered inventory OR Max (Projected Inventory, 0)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Inventaire saisi par l\'utilisateur OU Max (Inventaire projeté, 0)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inventário inserido pelo usuário OU Máx. (Inventário projetado, 0)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Inventario ingresado por el usuario O Máx. (Inventario proyectado, 0)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.unmetDemandP1','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'When actual consumption is available = (Actual consumption * Days stocked out) / (Days in Month – Days Stocked out)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lorsque la consommation réelle est disponible = (Consommation réelle * Jours en rupture de stock) / (Jours du mois – Jours en rupture de stock)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quando o consumo real está disponível = (Consumo real * Dias sem estoque) / (Dias no mês – Dias sem estoque)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuando el consumo real está disponible = (Consumo real * Días desabastecidos) / (Días del mes – Días desabastecidos)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.unmetDemandP2','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'When no actual consumption is available = Projected Inventory* (if Projected Inventory is <0)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lorsqu\'aucune consommation réelle n\'est disponible = Inventaire projeté* (si l\'inventaire projeté est <0)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quando nenhum consumo real estiver disponível = Estoque projetado* (se o estoque projetado for <0)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuando no hay consumo real disponible = Inventario proyectado* (si el Inventario proyectado es <0)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.exampleActualConsumption','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Example with actual consumption');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple avec consommation réelle');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplo com consumo real');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo con consumo real');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.unmetDemandNote','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, any unmet demand within the same month will be aggregated (summed) into a single total quantity.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport inclut plusieurs programmes ou unités de planification, toute demande non satisfaite au cours du même mois sera regroupée (additionnée) en une seule quantité totale.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, qualquer demanda não atendida no mesmo mês será agregada (somada) em uma única quantidade total.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye múltiples programas o unidades de planificación, cualquier demanda no satisfecha dentro del mismo mes se agregará (sumará) en una única cantidad total.');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.amcNote2','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, the AMC of the selected program/planning units will be aggregated (summed) into a single total quantity.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport comprend plusieurs programmes ou unités de planification, l\'AMC des programmes/unités de planification sélectionnés sera agrégée (additionnée) en une seule quantité totale.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, o CMA do programa/unidades de planejamento selecionado será agregado (somado) em uma única quantidade total.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye varios programas o unidades de planificación, el AMC del programa/unidades de planificación seleccionados se agregará (sumará) en una única cantidad total.');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockMultiprogramPU','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months Of Stock for Multi-program/planning unit');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock pour multi-programme/unité de planification');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque para unidade multiprograma/planejamento');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses de stock para unidad de planificación/programa múltiple');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockMultiprogramPUFormulae','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months Of Stock = (sum of selected programs/planning units’ ending balance / sum of selected programs/planning units’ AMC)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock = (somme du solde final des programmes/unités de planification sélectionnés/somme des AMC des programmes/unités de planification sélectionnés)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque = (soma do saldo final dos programas/unidades de planejamento selecionados / soma do AMC dos programas/unidades de planejamento selecionados)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses de existencias = (suma del saldo final de los programas/unidades de planificación seleccionados/suma del AMC de los programas/unidades de planificación seleccionados)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt1','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program A Ending Balance = 10,000');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme A Solde de clôture = 10 000');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo Final do Programa A = 10.000');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa A Saldo final = 10,000');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt2','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program A AMC = 2,500');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme A AMC = 2 500');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa A AMC = 2.500');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa A AMC = 2500');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt3','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program B Ending Balance = 6,000');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde de fin du programme B = 6 000');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo Final do Programa B = 6.000');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final del programa B = 6.000');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt4','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program B AMC = 3,000');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme B AMC = 3 000');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa B AMC = 3.000');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa B AMC = 3.000');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt5','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program C Ending Balance = 750');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde de fin du programme C = 750');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo Final do Programa C = 750');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final del programa C = 750');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt6','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program C AMC = 250');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme C AMC = 250');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa C AMC = 250');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa CAMC = 250');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt7','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months Of Stock = (10,000 + 6,000 + 750) / (2,500 + 3,000 + 250)');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock = (10 000 + 6 000 + 750) / (2 500 + 3 000 + 250)');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque = (10.000 + 6.000 + 750) / (2.500 + 3.000 + 250)');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses de existencias = (10.000 + 6.000 + 750) / (2.500 + 3.000 + 250)');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.monthsOfStockEx5Txt8','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months Of Stock = 2.91');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois de stock = 2,91');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses de estoque = 2,91');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses de existencias = 2,91');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.minMonthsOfStockNotes','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, QAT will use the average of the selected program & planning units’ Min Month Of Stock.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport comprend plusieurs programmes ou unités de planification, QAT utilisera la moyenne du mois minimum de stock du programme et des unités de planification sélectionnés.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, o QAT usará a média do mês mínimo de estoque do programa e das unidades de planejamento selecionadas.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye varios programas o unidades de planificación, QAT utilizará el promedio del mes mínimo de stock de las unidades de planificación y programas seleccionados.');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.maxMonthOfStockNotes','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, QAT will use the average of the selected program & planning units’ Max Month Of Stock.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport comprend plusieurs programmes ou unités de planification, QAT utilisera la moyenne du mois de stock maximum du programme et des unités de planification sélectionnées.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, o QAT usará a média do mês máximo de estoque do programa e das unidades de planejamento selecionadas.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye varios programas o unidades de planificación, QAT utilizará el promedio del mes máximo de stock de las unidades de planificación y programas seleccionados.');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlan.maxQtyNotes','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the report includes multiple programs or planning units, QAT will use the average of the selected program & planning units’ Min/Max Quantity.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le rapport comprend plusieurs programmes ou unités de planification, QAT utilisera la moyenne des quantités min/max du programme et des unités de planification sélectionnées.');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o relatório incluir vários programas ou unidades de planejamento, o QAT usará a média da quantidade mínima/máxima do programa e das unidades de planejamento selecionadas.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el informe incluye varios programas o unidades de planificación, QAT utilizará el promedio de la cantidad mínima/máxima de las unidades de planificación y programas seleccionados.');-- sp