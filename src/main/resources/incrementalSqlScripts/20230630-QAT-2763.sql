/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 30-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.createTreeFromTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create tree from template');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créer un arbre à partir d`un modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Crear árbol a partir de plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Criar árvore a partir do modelo');-- pr