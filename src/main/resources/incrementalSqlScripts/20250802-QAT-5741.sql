/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  akil
 * Created: Aug 3, 2025
 */

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