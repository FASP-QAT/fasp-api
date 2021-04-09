/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 03-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDataEntry.suggestedShipmentQtyConfirm','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on ok to update the order qty with suggested shipment qty');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur OK pour mettre à jour la quantité de commande avec la quantité d`expédition suggérée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en Aceptar para actualizar la cantidad del pedido con la cantidad de envío sugerida');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em ok para atualizar a quantidade do pedido com a quantidade de envio sugerida');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.planningUnitSettings','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Unit Settings');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres de l`unité de planification');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configuración de la unidad de planificación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configurações da unidade de planejamento');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.amcPastOrFuture','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'AMC (months in past/future)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'AMC (mois passés / futurs)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'AMC (meses en pasado / futuro)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'AMC (meses no passado / futuro)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.reorderInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reorder Interval (months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de commande (mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Intervalo de reorden (meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de reordenar (meses)');


