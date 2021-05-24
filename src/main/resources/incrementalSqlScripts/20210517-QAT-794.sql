/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-May-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.shipmentNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipment Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Notes d`expédition');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de envío');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de Remessa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.saveShipmentNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Save Shipment Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Enregistrer les notes d`expédition');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Guardar notas de envío');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Salvar notas de remessa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.consumption.consumptionNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Notes de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de Consumo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.saveConsumptionNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Save Consumption Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Enregistrer les notes de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Guardar notas de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Salvar notas de consumo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.inventory.inventoryNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Notes d`inventaire');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de inventario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas de Estoque');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.saveInventoryNotes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Save Inventory Notes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Enregistrer les notes d`inventaire');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Guardar notas de inventario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Salvar notas de estoque');