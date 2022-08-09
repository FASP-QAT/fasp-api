/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 19-Jul-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.replanSupplyPlan','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Re-plan Supply Plan');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Replanifier le plan d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Replanificar plan de suministro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Replanejar o Plano de Fornecimento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.newlyAddedShipment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Re-Planned Shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expédition replanifiée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envío replanificado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessa Replanejada');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.currentShipmentSettings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Current Shipment Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres d`envoi actuels');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configuración de envío actual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configurações de envio atuais');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.replannedShipmentSettings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Re-Planned Shipment Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres d`envoi replanifié');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configuración de envíos replanificados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configurações de Remessa Replanejada');-- pr