/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 22-Jul-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.fullMasterDataSync','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Full Master Data Sync');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Synchronisation complète des données de base');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Sincronización completa de datos maestros');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sincronização completa de dados mestre');-- pr