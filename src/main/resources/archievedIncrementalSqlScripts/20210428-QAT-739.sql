/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.jexcel.consumptionQuantity','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'with consumption quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'avec quantité de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'con cantidad de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'com quantidade de consumo');