/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 16-Jun-2022
 */


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.createOrSelect','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create or Select');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer ou sélectionner');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear o Seleccionar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar ou Selecionar');-- pr