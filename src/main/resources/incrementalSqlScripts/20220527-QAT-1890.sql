/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 27-May-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.rootNodeInfoMissing','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please update the root node info');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez mettre à jour les informations du nœud racine');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualice la información del nodo raíz.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualize as informações do nó raiz');-- pr