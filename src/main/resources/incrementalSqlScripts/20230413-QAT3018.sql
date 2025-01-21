/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 13-Apr-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= Opening balance + Adjustments + Shipments - Consumption – Projected Expired Stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= Solde d`ouverture + Ajustements + Expéditions - Consommation - Stock périmé projeté');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= Saldo inicial + Ajustes + Embarques - Consumo – Stock Vencido Proyectado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= Saldo inicial + Ajustes + Remessas - Consumo – Estoque expirado projetado');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(use actual consumption if available, else use forecast)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(utiliser la consommation réelle si disponible, sinon utiliser les prévisions)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(use el consumo real si está disponible, de lo contrario use el pronóstico)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(use o consumo real, se disponível, caso contrário, use a previsão)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= Max (Projected Inventory, 0)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= Max (Inventaire projeté, 0)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= Max (Inventario Proyectado, 0)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= Max (Inventário projetado, 0)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Demand during Stockout (0 if forecast)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande pendant la rupture de stock (0 si prévu)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda durante el desabastecimiento (0 si se pronostica)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda durante a falta de estoque (0 se for previsto)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Actual consumption * Days stocked out) / (Days in Month – Days Stocked out)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Consommation réelle * Jours de rupture de stock) / (Jours du mois – Jours de rupture de stock)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= (Consumo real * Días desabastecidos) / (Días en mes – Días desabastecidos)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= (Consumo real * Dias sem estoque) / (Dias no mês – Dias sem estoque)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= 0 – Projected Inventory*  – Demand during Stockout (* if Projected Inventory is <0)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= 0 - Inventaire prévu* - Demande pendant la rupture de stock (* si l`inventaire prévu est <0)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= 0 – Inventario Proyectado* – Demanda durante el desabastecimiento (* si el Inventario Proyectado es <0)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= 0 – Inventário projetado* – Demanda durante falta de estoque (* se o Estoque projetado for <0)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance7','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stockout days');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Jours de rupture de stock');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Días de desabastecimiento');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dias de falta de estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance8','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Days in Month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Jours du mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Días en Mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dias no Mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.showFormula.endingBalance9','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjusted Consumption = (Actual consumption * Days in month) / (Days in Month – Days Stocked out)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,3138,2,'Consommation ajustée = (Consommation réelle * Jours dans le mois) / (Jours dans le mois – Jours de rupture de stock)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,3138,3,'Consumo ajustado = (Consumo real * Días del mes) / (Días del mes – Días de agotamiento de existencias)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,3138,4,'Consumo Ajustado = (Consumo real * Dias do mês) / (Dias do mês – Dias sem stock)');-- pr