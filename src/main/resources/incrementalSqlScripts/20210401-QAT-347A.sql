/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 01-Apr-2021
 */

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Program Specific Prices',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES','1',NOW(),'1',NOW());


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