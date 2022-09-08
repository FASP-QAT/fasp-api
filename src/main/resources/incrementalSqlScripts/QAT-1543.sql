/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Aug-2022
 */


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programLoadedAndmasterDataSync.success','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program loaded and master data sync successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme chargé et données de base synchronisées avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa cargado y datos maestros sincronizados con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa carregado e dados mestre sincronizados com sucesso');-- pr

