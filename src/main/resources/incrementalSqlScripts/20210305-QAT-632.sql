/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 05-Mar-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.versionIsOutDated','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Loaded version is out dated. Please refresh the page.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La version chargée est obsolète. Veuillez actualiser la page.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La versión cargada está desactualizada. Actualice la página.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A versão carregada está desatualizada. Atualize a página.');-- pr