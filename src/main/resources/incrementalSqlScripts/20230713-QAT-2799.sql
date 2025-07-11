/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  rohit
 * Created: Jun 15, 2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.procurementAgentProcurementUnit.mapProcurementAgent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Map Procurement Agent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Carte de l agent d approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agente de Adquisiciones de Mapas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mapa Agente de Compras');-- pr

INSERT INTO `fasp`.`ap_label` values (null, 'SP - Map Procurement Agent', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;

INSERT INTO `fasp`.`us_business_function` values ('ROLE_BF_MAP_PROCUREMENT_AGENT', @labelId, 1, now(), 1, now());

INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_REALM_ADMIN','ROLE_BF_MAP_PROCUREMENT_AGENT',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_PROGRAM_ADMIN','ROLE_BF_MAP_PROCUREMENT_AGENT',1,now(),1,now());
INSERT INTO `fasp`.`us_role_business_function` VALUES(null,'ROLE_INTERNAL_USER','ROLE_BF_MAP_PROCUREMENT_AGENT',1,now(),1,now());