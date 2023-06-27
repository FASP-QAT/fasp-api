/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 16-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.pleaseSelectNodeType','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select node type before proceeding.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Please select node type before proceeding.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el tipo de nodo antes de continuar.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o tipo de nó antes de prosseguir.');-- pr