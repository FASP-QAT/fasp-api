/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 05-Apr-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitTree.puThatDoesNotAppearOnSelectedForecastTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning units that does not appear on any selected tree');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Planning units that does not appear on any selected tree');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de planificación que no aparecen en ningún árbol seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de planejamento que não aparecem em nenhuma árvore selecionada');-- pr