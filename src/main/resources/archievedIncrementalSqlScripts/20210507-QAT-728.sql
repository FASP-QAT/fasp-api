/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 07-May-2021
 */
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.True','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'True');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vrai');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cierto');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Verdadeiro');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataEntry.False','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'False');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Faux');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Falso');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Falso');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyplan.inventoryDataEntry','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory data entry');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie des données dinventaire');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Entrada de datos de inventario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada de dados de inventário');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyplan.adjustmentDataEntryTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjustment data entry');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie des données dajustement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Entrada de datos de ajuste');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada de dados de ajuste');
