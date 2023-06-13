/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 14-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.duplicateToDifferentProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate tree to different program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dupliquer l`arborescence dans un programme différent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Árbol duplicado a un programa diferente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Árvore duplicada para programa diferente');-- pr