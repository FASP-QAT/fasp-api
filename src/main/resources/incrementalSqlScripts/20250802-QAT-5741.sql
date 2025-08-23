/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  akil
 * Created: Aug 3, 2025
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.editProgram.userListHeader','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Users who have access to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les utilisateurs qui ont accès à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usuarios que tienen acceso a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usuários que têm acesso a');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.editProgram.userListSubHeader','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'By default, the users are given access based on the selected Procurement Agent(s) and Funder(s) in the Program Info screen above. Additional users may be added manually as requested by a Program Admin. If you have concerns or requests regarding user access, please submit a ticket via the ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par défaut, les utilisateurs bénéficient d\'un accès en fonction des agents d\'approvisionnement et des bailleurs de fonds sélectionnés dans l\'écran « Informations sur le programme » ci-dessus. Des utilisateurs supplémentaires peuvent être ajoutés manuellement à la demande d\'un administrateur de programme. Pour toute question ou demande concernant l\'accès des utilisateurs, veuillez soumettre un ticket via le service ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'De forma predeterminada, los usuarios reciben acceso según el/los Agente(s) de Adquisiciones y el/los Financiador(es) seleccionados en la pantalla "Información del Programa" (arriba). Se pueden agregar usuarios adicionales manualmente si lo solicita un administrador del programa. Si tiene alguna pregunta o solicitud sobre el acceso de los usuarios, envíe un ticket a través del servicio de ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por padrão, os usuários recebem acesso com base no(s) Agente(s) de Compras e Financiador(es) selecionados na tela de Informações do Programa acima. Usuários adicionais podem ser adicionados manualmente, conforme solicitado pelo Administrador do Programa. Se você tiver dúvidas ou solicitações relacionadas ao acesso de usuários, envie um ticket pelo ');-- pr

ALTER TABLE `fasp`.`us_user_acl` 
ADD COLUMN `FUNDING_SOURCE_ID` INT UNSIGNED NULL AFTER `PROGRAM_ID`,
ADD COLUMN `PROCUREMENT_AGENT_ID` INT UNSIGNED NULL AFTER `FUNDING_SOURCE_ID`,
ADD INDEX `fk_us_user_acl_fundingSourceId_idx` (`FUNDING_SOURCE_ID` ASC) VISIBLE,
ADD INDEX `fk_us_user_acl_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID` ASC) VISIBLE;
;
ALTER TABLE `fasp`.`us_user_acl` 
ADD CONSTRAINT `fk_us_user_acl_fundingSourceId`
  FOREIGN KEY (`FUNDING_SOURCE_ID`)
  REFERENCES `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_us_user_acl_procurementAgentId`
  FOREIGN KEY (`PROCUREMENT_AGENT_ID`)
  REFERENCES `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


USE `fasp`;
CREATE  OR REPLACE 
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(DISTINCT `pha`.`HEALTH_AREA_ID` SEPARATOR ',') AS `HEALTH_AREA_ID`,
        GROUP_CONCAT(DISTINCT `pfs`.`FUNDING_SOURCE_ID` SEPARATOR ',') AS `FUNDING_SOURCE_ID`,
        GROUP_CONCAT(DISTINCT `ppa`.`PROCUREMENT_AGENT_ID` SEPARATOR ',') AS `PROCUREMENT_AGENT_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
        `p`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`
    FROM
        `rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON `p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`
        LEFT JOIN `rm_program_funding_source` `pfs` ON `p`.`PROGRAM_ID` = `pfs`.`PROGRAM_ID`
        LEFT JOIN `rm_program_procurement_agent` `ppa` ON `p`.`PROGRAM_ID` = `ppa`.`PROGRAM_ID`
        LEFT JOIN `ap_label` `pl` ON `p`.`LABEL_ID` = `pl`.`LABEL_ID`
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 1)
    GROUP BY `p`.`PROGRAM_ID`;


USE `fasp`;
CREATE  OR REPLACE 
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_dataset` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(DISTINCT `pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
	GROUP_CONCAT(DISTINCT `pfs`.`FUNDING_SOURCE_ID`
            SEPARATOR ',') AS `FUNDING_SOURCE_ID`,
        GROUP_CONCAT(DISTINCT `ppa`.`PROCUREMENT_AGENT_ID`
            SEPARATOR ',') AS `PROCUREMENT_AGENT_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
        `p`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`
    FROM
        ((((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `rm_program_funding_source` `pfs` ON ((`p`.`PROGRAM_ID` = `pfs`.`PROGRAM_ID`)))
        LEFT JOIN `rm_program_procurement_agent` `ppa` ON ((`p`.`PROGRAM_ID` = `ppa`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 2)
    GROUP BY `p`.`PROGRAM_ID`;


USE `fasp`;
CREATE  OR REPLACE 
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_all_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(DISTINCT `pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
	GROUP_CONCAT(DISTINCT `pfs`.`FUNDING_SOURCE_ID`
            SEPARATOR ',') AS `FUNDING_SOURCE_ID`,
        GROUP_CONCAT(DISTINCT `ppa`.`PROCUREMENT_AGENT_ID`
            SEPARATOR ',') AS `PROCUREMENT_AGENT_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`
    FROM
        ((((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `rm_program_funding_source` `pfs` ON ((`p`.`PROGRAM_ID` = `pfs`.`PROGRAM_ID`)))
        LEFT JOIN `rm_program_procurement_agent` `ppa` ON ((`p`.`PROGRAM_ID` = `ppa`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    GROUP BY `p`.`PROGRAM_ID`;


-- For Testing
-- SELECT * FROM vw_funding_source fs WHERE FIND_IN_SET(fs.FUNDING_SOURCE_ID, "1,2,3,4,5,6,7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31");
-- INSERT INTO `fasp`.`ap_security` (`METHOD`, `URL`, `BF`) VALUES ('2', '/api/test/aclTest', 'ROLE_BF_LIST_USER');
-- DELETE pfs.* FROM rm_program_funding_source pfs WHERE pfs.PROGRAM_ID = 2535 AND pfs.FUNDING_SOURCE_ID=4;
-- DELETE pfs.* FROM rm_program_funding_source pfs WHERE pfs.PROGRAM_ID = 2537 AND pfs.FUNDING_SOURCE_ID in (1,2,15);
-- DELETE ppa.* FROM rm_program_procurement_agent ppa WHERE ppa.PROGRAM_ID=2535 AND ppa.PROCUREMENT_AGENT_ID IN (2);


USE `fasp`;
DROP procedure IF EXISTS `getUserListWithAccessToProgramId`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getUserListWithAccessToProgramId`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getUserListWithAccessToProgramId`(VAR_PROGRAM_ID INT)
BEGIN
    SET @varProgramId = VAR_PROGRAM_ID;
    SELECT rc.REALM_ID, p.REALM_COUNTRY_ID, p.HEALTH_AREA_ID, p.ORGANISATION_ID, p.FUNDING_SOURCE_ID, p.PROCUREMENT_AGENT_ID, p.PROGRAM_TYPE_ID into @varRealmId, @varRealmCountryId, @varHealthAreaId, @varOrganisationId, @varFundingSourceId, @varProcurementAgentId, @varProgramTypeId FROM vw_all_program p LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID where p.PROGRAM_ID=@varProgramId;
    SET @skipRoleList = "ROLE_APPLICATION_ADMIN,ROLE_REALM_ADMIN,ROLE_INTERNAL_USER";
    SET @spAccessBfList = "ROLE_BF_EDIT_PROGRAM,ROLE_BF_UPDATE_PROGRAM,ROLE_BF_LIST_PROGRAM,ROLE_BF_SET_UP_PROGRAM,ROLE_BF_CREATE_A_PROGRAM,ROLE_BF_SUPPLY_PLANNING_MODULE";
    SET @fcAccessBfList = "ROLE_BF_ADD_DATASET,ROLE_BF_EDIT_DATASET,ROLE_BF_LIST_DATASET,ROLE_BF_COMMIT_DATASET,ROLE_BF_IMPORT_DATASET,ROLE_BF_EXPORT_DATASET,ROLE_BF_LOAD_DELETE_DATASET,ROLE_BF_FORECASTING_MODULE";
    IF @varProgramTypeId = 1 THEN
		SET @accessBfList = @spAccessBfList;
    ELSEIF @varProgramTypeId = 2 THEN
		SET @accessBfList = @fcAccessBfList;
    END IF;
	
    SELECT group_concat(DISTINCT rbf.ROLE_ID) INTO @varRoleIdList FROM us_role_business_function rbf WHERE FIND_IN_SET(rbf.BUSINESS_FUNCTION_ID,@accessBfList) AND NOT FIND_IN_SET(rbf.ROLE_ID, @skipRoleList);
    
    SELECT 
        u.USER_ID, u.USERNAME, u.ORG_AND_COUNTRY, acl.ROLE_ID, l.LABEL_ID, l.LABEL_EN, l.LABEL_FR, l.LABEL_SP, l.LABEL_PR, 
        acl.REALM_COUNTRY_ID, c.LABEL_ID C_LABEL_ID, c.LABEL_EN C_LABEL_EN, c.LABEL_FR C_LABEL_FR, c.LABEL_SP C_LABEL_SP, c.LABEL_PR C_LABEL_PR,
        acl.HEALTH_AREA_ID, ha.LABEL_ID HA_LABEL_ID, ha.LABEL_EN HA_LABEL_EN, ha.LABEL_FR HA_LABEL_FR, ha.LABEL_SP HA_LABEL_SP, ha.LABEL_PR HA_LABEL_PR,
        acl.ORGANISATION_ID, o.LABEL_ID O_LABEL_ID, o.LABEL_EN O_LABEL_EN, o.LABEL_FR O_LABEL_FR, o.LABEL_SP O_LABEL_SP, o.LABEL_PR O_LABEL_PR,
        acl.FUNDING_SOURCE_ID, fs.LABEL_ID FS_LABEL_ID, fs.LABEL_EN FS_LABEL_EN, fs.LABEL_FR FS_LABEL_FR, fs.LABEL_SP FS_LABEL_SP, fs.LABEL_PR fs_LABEL_PR,
        acl.PROCUREMENT_AGENT_ID, pa.LABEL_ID PA_LABEL_ID, pa.LABEL_EN PA_LABEL_EN, pa.LABEL_FR PA_LABEL_FR, pa.LABEL_SP PA_LABEL_SP, pa.LABEL_PR PA_LABEL_PR,
        acl.PROGRAM_ID, p.LABEL_ID P_LABEL_ID, p.LABEL_EN P_LABEL_EN, p.LABEL_FR P_LABEL_FR, p.LABEL_SP P_LABEL_SP, p.LABEL_PR P_LABEL_PR, p.PROGRAM_CODE
    FROM us_user_acl acl
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_role r ON acl.ROLE_ID=r.ROLE_ID 
    LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID
    LEFT JOIN rm_realm_country rc ON acl.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
    LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
    LEFT JOIN vw_health_area ha ON acl.HEALTH_AREA_ID=ha.HEALTH_AREA_ID
    LEFT JOIN vw_organisation o ON acl.ORGANISATION_ID=o.ORGANISATION_ID
    LEFT JOIN vw_funding_source fs ON acl.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_procurement_agent pa ON acl.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_all_program p ON acl.PROGRAM_ID=p.PROGRAM_ID
    WHERE 
        FIND_IN_SET (acl.ROLE_ID, @varRoleIdList)
        AND u.REALM_ID=@varRealmId 
        AND FIND_IN_SET(acl.ROLE_ID, @varRoleIdList)
        AND (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=@varRealmCountryId)
        AND (acl.HEALTH_AREA_ID IS NULL OR FIND_IN_SET(acl.HEALTH_AREA_ID, @varHealthAreaId))
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=@varOrganisationId)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=@varProgramId)
        AND (acl.FUNDING_SOURCE_ID IS NULL OR FIND_IN_SET(acl.FUNDING_SOURCE_ID, @varFundingSourceId))
        AND (acl.PROCUREMENT_AGENT_ID IS NULL OR FIND_IN_SET(acl.PROCUREMENT_AGENT_ID, @varProcurementAgentId));
END$$

DELIMITER ;
;




INSERT INTO ap_security VALUES (null, '1', '/api/program/userList/{programId}', 'ROLE_BF_EDIT_PROGRAM');
INSERT INTO ap_security VALUES (null, '1', '/api/program/userList/{programId}', 'ROLE_BF_EDIT_DATASET');