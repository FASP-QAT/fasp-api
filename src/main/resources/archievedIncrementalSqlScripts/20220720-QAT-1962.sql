/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 20-Jul-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.accesscontrol.duplicateAccessControl','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error: Duplicated access controls');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur : Contrôles d accès en double');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro: controles de acesso duplicados');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error: controles de acceso duplicados');-- sp