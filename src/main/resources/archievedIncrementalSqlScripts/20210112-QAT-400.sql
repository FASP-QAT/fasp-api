/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 12-Jan-2021
 */

update rm_realm_problem rrp set rrp.DATA1=1 , rrp.LAST_MODIFIED_DATE=now() where rrp.REALM_PROBLEM_ID in (10,15,16);

INSERT INTO ap_static_label VALUES (null, 'static.report.shipmentCost', 1);
INSERT INTO ap_static_label VALUES (null, 'static.common.max15digittext', 1);
INSERT INTO ap_static_label VALUES (null, 'static.common.max25digittext', 1);
INSERT INTO ap_static_label VALUES (null, 'static.budget.usedUSDAmount', 1);
INSERT INTO ap_static_label VALUES (null, 'static.ticket.changeRequest', 1);
INSERT INTO ap_static_label VALUES (null, 'static.common.max20digittext', 1);
INSERT INTO ap_static_label VALUES (null, 'static.shipment.totalCost', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlan.startMonth', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlanFormula.suggestShipmentEx1', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlanFormula.suggestShipmentEx2', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlanFormula.suggestShipmentEx3', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlanFormula.suggestShipmentEx4', 1);
INSERT INTO ap_static_label VALUES (null, 'static.supplyPlanFormula.suggestShipmentEx5', 1);

INSERT INTO ap_static_label_languages VALUES (null, 1596, 1, "Shipment Cost");
INSERT INTO ap_static_label_languages VALUES (null, 1597, 1, "Maximum of 15 characters");
INSERT INTO ap_static_label_languages VALUES (null, 1598, 1, "Maximum 25 characters only");
INSERT INTO ap_static_label_languages VALUES (null, 1599, 1, "Used Amount");
INSERT INTO ap_static_label_languages VALUES (null, 1600, 1, "Change Request");
INSERT INTO ap_static_label_languages VALUES (null, 1601, 1, "Maximum of 20 characters");
INSERT INTO ap_static_label_languages VALUES (null, 1602, 1, "Total Cost");
INSERT INTO ap_static_label_languages VALUES (null, 1603, 1, "Start Month");
INSERT INTO ap_static_label_languages VALUES (null, 1604, 1, "MoS for current month = 10");
INSERT INTO ap_static_label_languages VALUES (null, 1605, 1, "MoS for following 1st month = 9.4");
INSERT INTO ap_static_label_languages VALUES (null, 1606, 1, "MoS for following 2nd month = 8.4");
INSERT INTO ap_static_label_languages VALUES (null, 1607, 1, "Min MoS = 12");
INSERT INTO ap_static_label_languages VALUES (null, 1608, 1, "Since MoS for all the three months is less than Min MoS, therefore");

INSERT INTO ap_static_label_languages VALUES (null, 1596, 3, "Gastos de envío");
INSERT INTO ap_static_label_languages VALUES (null, 1597, 3, "Máximo de 15 caracteres");
INSERT INTO ap_static_label_languages VALUES (null, 1598, 3, "Máximo 25 caracteres solamente");
INSERT INTO ap_static_label_languages VALUES (null, 1599, 3, "Cantidad usada");
INSERT INTO ap_static_label_languages VALUES (null, 1600, 3, "Solicitud de cambio");
INSERT INTO ap_static_label_languages VALUES (null, 1601, 3, "Máximo de 20 caracteres");
INSERT INTO ap_static_label_languages VALUES (null, 1602, 3, "Coste total");
INSERT INTO ap_static_label_languages VALUES (null, 1603, 3, "Mes de inicio");
INSERT INTO ap_static_label_languages VALUES (null, 1604, 3, "MoS para el mes actual = 10");
INSERT INTO ap_static_label_languages VALUES (null, 1605, 3, "MoS para el primer mes siguiente = 9.4");
INSERT INTO ap_static_label_languages VALUES (null, 1606, 3, "MoS para el segundo mes siguiente = 8.4");
INSERT INTO ap_static_label_languages VALUES (null, 1607, 3, "Mínimo MoS = 12");
INSERT INTO ap_static_label_languages VALUES (null, 1608, 3, "Dado que MoS para los tres meses es menor que Min MoS, por lo tanto");

INSERT INTO ap_static_label_languages VALUES (null, 1596, 2, "Coût d`expédition");
INSERT INTO ap_static_label_languages VALUES (null, 1597, 2, "Maximum de 15 caractères");
INSERT INTO ap_static_label_languages VALUES (null, 1598, 2, "Maximum 25 caractères seulement");
INSERT INTO ap_static_label_languages VALUES (null, 1599, 2, "Montant utilisé");
INSERT INTO ap_static_label_languages VALUES (null, 1600, 2, "Changer de requête");
INSERT INTO ap_static_label_languages VALUES (null, 1601, 2, "Maximum de 20 caractères");
INSERT INTO ap_static_label_languages VALUES (null, 1602, 2, "Coût total");
INSERT INTO ap_static_label_languages VALUES (null, 1603, 2, "Mois de début");
INSERT INTO ap_static_label_languages VALUES (null, 1604, 2, "MoS pour le mois en cours = 10");
INSERT INTO ap_static_label_languages VALUES (null, 1605, 2, "MoS pour le 1er mois suivant = 9.4");
INSERT INTO ap_static_label_languages VALUES (null, 1606, 2, "MoS pour le 2ème mois suivant = 8.4");
INSERT INTO ap_static_label_languages VALUES (null, 1607, 2, "MoS minimum = 12");
INSERT INTO ap_static_label_languages VALUES (null, 1608, 2, "Étant donné que la MoS pour les trois mois est inférieure à la MoS minimale, par conséquent");

INSERT INTO ap_static_label_languages VALUES (null, 1596, 4, "Custo de transporte");
INSERT INTO ap_static_label_languages VALUES (null, 1597, 4, "Máximo de 15 caracteres");
INSERT INTO ap_static_label_languages VALUES (null, 1598, 4, "Máximo de 25 caracteres apenas");
INSERT INTO ap_static_label_languages VALUES (null, 1599, 4, "Quantidade Usada");
INSERT INTO ap_static_label_languages VALUES (null, 1600, 4, "Pedido de mudança");
INSERT INTO ap_static_label_languages VALUES (null, 1601, 4, "Máximo de 20 caracteres");
INSERT INTO ap_static_label_languages VALUES (null, 1602, 4, "Custo total");
INSERT INTO ap_static_label_languages VALUES (null, 1603, 4, "Mês de início");
INSERT INTO ap_static_label_languages VALUES (null, 1604, 4, "MoS para o mês atual = 10");
INSERT INTO ap_static_label_languages VALUES (null, 1605, 4, "MoS para o 1º mês seguinte = 9.4");
INSERT INTO ap_static_label_languages VALUES (null, 1606, 4, "MoS para o segundo mês seguinte = 8.4");
INSERT INTO ap_static_label_languages VALUES (null, 1607, 4, "MoS mínimo = 12");
INSERT INTO ap_static_label_languages VALUES (null, 1608, 4, "Como o MoS para todos os três meses é menor do que o Min MoS, portanto");

