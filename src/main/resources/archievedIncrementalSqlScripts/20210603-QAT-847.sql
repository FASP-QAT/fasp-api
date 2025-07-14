/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 03-Jun-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.outdatedsync','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your master data sync is outdated. Redirecting to master data sync.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La synchronisation de vos données de base est obsolète. Redirection vers la synchronisation des données principales.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Su sincronización de datos maestros está desactualizada. Redirigiendo a la sincronización de datos maestros.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sua sincronização de dados mestre está desatualizada. Redirecionando para sincronização de dados mestre.');