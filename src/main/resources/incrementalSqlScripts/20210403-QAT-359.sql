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