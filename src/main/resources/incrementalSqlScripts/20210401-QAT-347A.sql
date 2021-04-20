/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 01-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.countrySpecificPrices.countrySpecificPrices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program/Procurement Agent Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prix ​​des agents de programme / dapprovisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precios del programa / agente de adquisiciones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços do agente de compras / programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.countrySpecificPrices.addCountrySpecificPrices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Program/Procurement Agent Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter les prix des agents de programme / dapprovisionnement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar precios de programa / agente de adquisiciones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar preços de agente de compras / programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.price.prices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des prix');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.procurementAgentAlreadExists','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent already exists');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L agent d approvisionnement existe déjà');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El agente de adquisiciones ya existe');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agente de compras já existe');