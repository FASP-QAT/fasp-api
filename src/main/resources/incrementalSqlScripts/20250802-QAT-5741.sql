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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'By default, the users are given access based on the selected Procurement Agent(s) and Funder(s) in the Program Info screen above. Additional users may be added manually as requested by a Program Admin. If you have concerns or requests regarding user access, please submit a ticket via the QAT Help Desk.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par défaut, les utilisateurs bénéficient d\'un accès en fonction des agents d\'approvisionnement et des bailleurs de fonds sélectionnés dans l\'écran « Informations sur le programme » ci-dessus. Des utilisateurs supplémentaires peuvent être ajoutés manuellement à la demande d\'un administrateur de programme. Pour toute question ou demande concernant l\'accès des utilisateurs, veuillez soumettre un ticket via le service d\'assistance QAT.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'De forma predeterminada, los usuarios reciben acceso según el/los Agente(s) de Adquisiciones y el/los Financiador(es) seleccionados en la pantalla "Información del Programa" (arriba). Se pueden agregar usuarios adicionales manualmente si lo solicita un administrador del programa. Si tiene alguna pregunta o solicitud sobre el acceso de los usuarios, envíe un ticket a través del servicio de asistencia de QAT.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por padrão, os usuários recebem acesso com base no(s) Agente(s) de Compras e Financiador(es) selecionados na tela de Informações do Programa acima. Usuários adicionais podem ser adicionados manualmente, conforme solicitado pelo Administrador do Programa. Se você tiver dúvidas ou solicitações relacionadas ao acesso de usuários, envie um ticket pelo Help Desk do QAT.');-- pr

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

    IF @varProgramTypeId = 1 THEN
		SELECT group_concat(DISTINCT rbf.ROLE_ID) INTO @varRoleIdList FROM us_role_business_function rbf WHERE rbf.BUSINESS_FUNCTION_ID in ('ROLE_BF_EDIT_PROGRAM', 'ROLE_BF_UPDATE_PROGRAM', 'ROLE_BF_LIST_PROGRAM', 'ROLE_BF_SET_UP_PROGRAM', 'ROLE_BF_CREATE_A_PROGRAM', 'ROLE_BF_SUPPLY_PLANNING_MODULE');
        SELECT u.USER_ID, u.USERNAME, u.ORG_AND_COUNTRY, r.ROLE_ID, l.LABEL_ID, l.LABEL_EN, l.LABEL_FR, l.LABEL_SP, l.LABEL_PR 
        FROM us_user u LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID LEFT JOIN us_role r ON ur.ROLE_ID=r.ROLE_ID LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID
		WHERE 
			u.ACTIVE
			AND u.REALM_ID=@varRealmId
            AND FIND_IN_SET(acl.ROLE_ID, @varRoleIdList) AND acl.ROLE_ID != 'ROLE_APPLICATION_ADMIN'
			AND (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=@varRealmCountryId)
			AND (acl.HEALTH_AREA_ID IS NULL OR FIND_IN_SET(acl.HEALTH_AREA_ID, @varHealthAreaId))
			AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=@varOrganisationId)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=@varProgramId)
			AND (acl.FUNDING_SOURCE_ID IS NULL OR FIND_IN_SET(acl.FUNDING_SOURCE_ID, @varFundingSourceId))
			AND (acl.PROCUREMENT_AGENT_ID IS NULL OR FIND_IN_SET(acl.PROCUREMENT_AGENT_ID, @varProcurementAgentId));
	ELSEIF @varProgramTypeId = 2 THEN
		SELECT group_concat(DISTINCT rbf.ROLE_ID) INTO @varRoleIdList FROM us_role_business_function rbf WHERE rbf.BUSINESS_FUNCTION_ID in ('ROLE_BF_ADD_DATASET', 'ROLE_BF_EDIT_DATASET', 'ROLE_BF_LIST_DATASET', 'ROLE_BF_COMMIT_DATASET', 'ROLE_BF_IMPORT_DATASET', 'ROLE_BF_EXPORT_DATASET', 'ROLE_BF_LOAD_DELETE_DATASET', 'ROLE_BF_FORECASTING_MODULE');
		SELECT u.USER_ID, u.USERNAME, u.ORG_AND_COUNTRY, r.ROLE_ID, l.LABEL_ID, l.LABEL_EN, l.LABEL_FR, l.LABEL_SP, l.LABEL_PR  
        FROM us_user u LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID LEFT JOIN us_role r ON ur.ROLE_ID=r.ROLE_ID LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID
		WHERE 
			u.ACTIVE
            AND u.REALM_ID=@varRealmId
			AND FIND_IN_SET(acl.ROLE_ID, @varRoleIdList) AND acl.ROLE_ID != 'ROLE_APPLICATION_ADMIN'
			AND (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=@varRealmCountryId)
			AND (acl.HEALTH_AREA_ID IS NULL OR FIND_IN_SET(acl.HEALTH_AREA_ID, @varHealthAreaId))
			AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=@varOrganisationId)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=@varProgramId);
	END IF;
END$$

DELIMITER ;
;




INSERT INTO ap_security VALUES (null, '1', '/api/program/userList/{programId}', 'ROLE_BF_EDIT_PROGRAM');
INSERT INTO ap_security VALUES (null, '1', '/api/program/userList/{programId}', 'ROLE_BF_EDIT_DATASET');