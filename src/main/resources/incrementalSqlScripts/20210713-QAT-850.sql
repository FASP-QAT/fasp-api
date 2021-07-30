CREATE TABLE `rm_organisation_type` (
  `ORGANISATION_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation type',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm this OrganisationType belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate the Label for this OrganisationType so we can get the labels in all languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this OrganisationType is Active. False indicates this OrganisationType has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate the User that Created this OrganisationType',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Date this OrganisationType was Created on',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate the User that LastModified this OrganisationType',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Date this OrganisationType was LastModified on',
  PRIMARY KEY (`ORGANISATION_TYPE_ID`),
  INDEX `fk_rm_organisation_type_realmId_idx` (`REALM_ID` ASC),
  INDEX `fk_rm_organisation_type_labelId_idx` (`LABEL_ID` ASC),
  INDEX `fk_rm_organisation_type_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_rm_organisation_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
  INDEX `idx_rm_organisation_type_lastModifiedDate` (`LAST_MODIFIED_DATE` ASC),
  CONSTRAINT `fk_rm_organisation_type_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_organisation_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_organisation_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_organisation_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `ap_label_source` (`SOURCE_ID`, `SOURCE_DESC`) VALUES ('37', 'rm_organisation_type');

INSERT INTO ap_label values (null, 'MOH', null, null, null, 1, now(), 1, now(), 37);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_organisation_type values (null, 1, @labelId, 1, 1, now(), 1, now());

ALTER TABLE `rm_organisation` ADD COLUMN `ORGANISATION_TYPE_ID` INT(10) UNSIGNED NOT NULL DEFAULT 1 AFTER `ORGANISATION_CODE`, ADD INDEX `fk_rm_organisation_organisationTypeId_idx` (`ORGANISATION_TYPE_ID` ASC);
ALTER TABLE `rm_organisation` ADD CONSTRAINT `fk_rm_organisation_organisationTypeId` FOREIGN KEY (`ORGANISATION_TYPE_ID`) REFERENCES `rm_organisation_type` (`ORGANISATION_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;


CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_organisation_type` AS
    SELECT 
        `o`.`ORGANISATION_TYPE_ID` AS `ORGANISATION_TYPE_ID`,
        `o`.`REALM_ID` AS `REALM_ID`,
        `o`.`LABEL_ID` AS `LABEL_ID`,
        `o`.`ACTIVE` AS `ACTIVE`,
        `o`.`CREATED_BY` AS `CREATED_BY`,
        `o`.`CREATED_DATE` AS `CREATED_DATE`,
        `o`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `o`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ol`.`LABEL_EN` AS `LABEL_EN`,
        `ol`.`LABEL_FR` AS `LABEL_FR`,
        `ol`.`LABEL_SP` AS `LABEL_SP`,
        `ol`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_organisation_type` `o`
        LEFT JOIN `ap_label` `ol` ON ((`o`.`LABEL_ID` = `ol`.`LABEL_ID`)));


CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_organisation` AS
    SELECT 
        `o`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `o`.`REALM_ID` AS `REALM_ID`,
        `o`.`LABEL_ID` AS `LABEL_ID`,
        `o`.`ORGANISATION_CODE` AS `ORGANISATION_CODE`,
        `o`.`ORGANISATION_TYPE_ID` AS `ORGANISATION_TYPE_ID`,
        `ot`.`LABEL_ID` `TYPE_LABEL_ID`,
        `ot`.`LABEL_EN` `TYPE_LABEL_EN`,
        `ot`.`LABEL_FR` `TYPE_LABEL_FR`,
        `ot`.`LABEL_SP` `TYPE_LABEL_SP`,
        `ot`.`LABEL_PR` `TYPE_LABEL_PR`,
        `o`.`ACTIVE` AS `ACTIVE`,
        `o`.`CREATED_BY` AS `CREATED_BY`,
        `o`.`CREATED_DATE` AS `CREATED_DATE`,
        `o`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `o`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `ol`.`LABEL_EN` AS `LABEL_EN`,
        `ol`.`LABEL_FR` AS `LABEL_FR`,
        `ol`.`LABEL_SP` AS `LABEL_SP`,
        `ol`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_organisation` `o`
        LEFT JOIN `ap_label` `ol` ON ((`o`.`LABEL_ID` = `ol`.`LABEL_ID`))
        LEFT JOIN `vw_organisation_type` `ot` ON ((`o`.`ORGANISATION_TYPE_ID`=`ot`.`ORGANISATION_TYPE_ID`))
        );




INSERT INTO `ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Add Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_ADD_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Edit Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_EDIT_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'List Organization Type',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_LIST_ORGANIZATION_TYPE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_REALM_ADMIN','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_APPLICATION_ADMIN','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_ADD_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_EDIT_ORGANIZATION_TYPE',1,NOW(),1,NOW());
INSERT INTO us_role_business_function VALUES(NULL,'ROLE_INTERNAL_USER','ROLE_BF_LIST_ORGANIZATION_TYPE',1,NOW(),1,NOW());


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Organization Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type dOrganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de organização');


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypeName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Organization Type Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do tipo de organização');


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypetext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Organization Type Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le nom du type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el nombre del tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o nome do tipo de organização');



INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.organisationType.organisationTypeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Organization Type');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez le type dorganisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar tipo de organización');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o tipo de organização');

