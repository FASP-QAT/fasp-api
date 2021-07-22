/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 19-Jul-2021
 */

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Organization Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type dOrganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de organização');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypeName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Organization Type Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do tipo de organização');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypetext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Organization Type Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le nom du type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el nombre del tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o nome do tipo de organização');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Organization Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez le type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o tipo de organização');

