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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Newly Added Shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envoi nouvellement ajouté');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envío recién agregado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessa recém-adicionada');-- pr