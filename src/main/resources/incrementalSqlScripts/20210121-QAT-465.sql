/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 21-Jan-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.suggestedOrderQtyEx6','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unmet Demand = 120');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande non satisfaite = 120');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda insatisfecha = 120');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda não atendida = 120');


UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Suggested Order Qty = (Max Stock - Ending balance + Unmet Demand)' WHERE `STATIC_LABEL_LANGUAGE_ID`='6321';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Cantidad de pedido sugerida = (Stock máximo - Saldo final + Demanda insatisfecha)' WHERE `STATIC_LABEL_LANGUAGE_ID`='6322';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantité de commande suggérée = (Stock max - solde final + demande non satisfaite)' WHERE `STATIC_LABEL_LANGUAGE_ID`='6323';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantidade sugerida do pedido = (estoque máximo - saldo final + demanda não atendida)' WHERE `STATIC_LABEL_LANGUAGE_ID`='6324';

UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Suggested Order Qty = 44,744 - 25,568 + 120' WHERE `STATIC_LABEL_LANGUAGE_ID`='6325';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Cantidad de pedido sugerida = 44,744 - 25,568 + 120' WHERE `STATIC_LABEL_LANGUAGE_ID`='6326';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantité de commande suggérée = 44744 - 25568 + 120' WHERE `STATIC_LABEL_LANGUAGE_ID`='6327';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantidade sugerida do pedido = 44.744 - 25.568 + 120' WHERE `STATIC_LABEL_LANGUAGE_ID`='6328';

UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Suggested Order Qty = 19,296' WHERE `STATIC_LABEL_LANGUAGE_ID`='6329';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Cantidad de pedido sugerida = 19,296' WHERE `STATIC_LABEL_LANGUAGE_ID`='6330';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantité de commande suggérée = 19,296' WHERE `STATIC_LABEL_LANGUAGE_ID`='6331';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Quantidade de pedido sugerida = 19,296' WHERE `STATIC_LABEL_LANGUAGE_ID`='6332';





