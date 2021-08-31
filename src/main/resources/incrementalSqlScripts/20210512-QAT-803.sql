/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 12-May-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatusOverTime.noteBelowGraph','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: The y-axis (months of stock) is capped at 50 months. See table below for the full list of values.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque: l`axe des y (mois de stock) est plafonné à 50 mois. Voir le tableau ci-dessous pour la liste complète des valeurs.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: El eje y (meses de existencias) tiene un límite de 50 meses. Consulte la tabla a continuación para obtener la lista completa de valores.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: O eixo y (meses de estoque) é limitado a 50 meses. Consulte a tabela abaixo para obter a lista completa de valores.');-- pr