/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 01-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.countrySpecificPrices.countrySpecificPrices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Specific Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prix ​​spécifiques au programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precios específicos del programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços específicos do programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.countrySpecificPrices.addCountrySpecificPrices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Program Specific Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter des prix spécifiques au programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar precios específicos del programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar preços específicos do programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.price.prices','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Prices');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des prix');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Precios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços');