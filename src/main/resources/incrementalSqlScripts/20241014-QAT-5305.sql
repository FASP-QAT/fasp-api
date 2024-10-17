/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 14-Oct-2024
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastMonthlyErrorReport.showConsumption','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show consumption adjusted for stock out?'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher la consommation ajustée aux ruptures de stock ?'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Mostrar el consumo ajustado por desabastecimiento?'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar consumo ajustado por ruptura de estoque?'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.allitemsselected','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All items are selected'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous les éléments sont sélectionnés'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los artículos están seleccionados'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todos os itens são selecionados'); -- pr