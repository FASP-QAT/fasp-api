CREATE TABLE `fasp`.`rm_integration_manual` (
  `MANUAL_INTEGRATION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT UNSIGNED NOT NULL,
  `VERSION_ID` INT UNSIGNED NOT NULL,
  `INTEGRATION_ID` INT UNSIGNED NOT NULL,
  `CREATED_BY` INT UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `COMPLETED_DATE` DATETIME NULL,
  PRIMARY KEY (`MANUAL_INTEGRATION_ID`),
  INDEX `fk_rm_integration_manual_programId_idx` (`PROGRAM_ID` ASC) ,
  INDEX `fk_rm_integration_manual_versionId_idx` (`PROGRAM_ID` ASC, `VERSION_ID` ASC) ,
  INDEX `fk_rm_integration_manual_integrationId_idx` (`INTEGRATION_ID` ASC) ,
  INDEX `fk_rm_integration_manual_createdBy_idx` (`CREATED_BY` ASC) ,
  CONSTRAINT `fk_rm_integration_manual_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_versionId`
    FOREIGN KEY (`PROGRAM_ID` , `VERSION_ID`)
    REFERENCES `fasp`.`rm_program_version` (`PROGRAM_ID` , `VERSION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_integrationId`
    FOREIGN KEY (`INTEGRATION_ID`)
    REFERENCES `fasp`.`ap_integration` (`INTEGRATION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_integration_manual_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


USE `fasp`;
DROP procedure IF EXISTS `getManualJsonPushReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getManualJsonPushReport`(VAR_START_DATE DATETIME, VAR_STOP_DATE DATETIME, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT)
BEGIN
	SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
          
    SELECT 	
		im.MANUAL_INTEGRATION_ID,     
		p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,     
		im.VERSION_ID, i.INTEGRATION_ID, i.INTEGRATION_NAME, iv.INTEGRATION_VIEW_ID, iv.INTEGRATION_VIEW_DESC, iv.INTEGRATION_VIEW_NAME, i.FOLDER_LOCATION, i.FILE_NAME,     
		cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, im.CREATED_DATE, im.COMPLETED_DATE 
	FROM rm_integration_manual im 
	LEFT JOIN vw_program p ON im.PROGRAM_ID=p.PROGRAM_ID 
	LEFT JOIN ap_integration i ON im.INTEGRATION_ID=i.INTEGRATION_ID 
	LEFT JOIN us_user cb ON im.CREATED_BY=cb.USER_ID 
	LEFT JOIN ap_integration_view iv ON i.INTEGRATION_VIEW_ID=iv.INTEGRATION_VIEW_ID  
	WHERE 
		im.CREATED_DATE BETWEEN @startDate AND @stopDate 
		AND (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) 
		AND (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds));
END$$

DELIMITER ;
;


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.manualProgramIntegration','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'JSON Generation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Génération JSON');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Generación JSON');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Geração JSON');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.manualIntegration.requester','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Requester');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demandeur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solicitante');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Solicitante');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.manualIntegration.jsonCreationDate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'JSON Requested Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date demandée JSON');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha solicitada de JSON');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data solicitada JSON');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.manualIntegration.addManualIntegration','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Request JSON');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande JSON');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solicitar JSON');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Solicitar JSON');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.manualIntegration.completed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Completed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Complété');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Terminado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Concluído');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.manualIntegration.requested','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Requested');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demandé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solicitado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Requeridos');-- pr

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Manual Integration',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'1');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_MANUAL_INTEGRATION',@MAX,'1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_MANUAL_INTEGRATION','1',NOW(),'1',NOW());