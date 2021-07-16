CREATE TABLE IF NOT EXISTS `rm_program_health_area` (
  `PROGRAM_HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL,
  `HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`PROGRAM_HEALTH_AREA_ID`),
  INDEX `fk_rm_program_health_area_programId_idx` (`PROGRAM_ID` ASC),
  INDEX `fk_rm_program_health_area_healthAreaId_idx` (`HEALTH_AREA_ID` ASC),
  CONSTRAINT `fk_rm_program_health_area_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_program_health_area_healthAreaId`
    FOREIGN KEY (`HEALTH_AREA_ID`)
    REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO rm_program_health_area SELECT null, p.PROGRAM_ID, p.HEALTH_AREA_ID FROM rm_program p;

ALTER TABLE `fasp`.`rm_program` DROP FOREIGN KEY `fk_program_healthAreaId`;
ALTER TABLE `rm_program` DROP INDEX `fk_program_healthAreaId_idx`,DROP COLUMN `HEALTH_AREA_ID` ;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`, 
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    GROUP BY `p`.`PROGRAM_ID`;

ALTER TABLE `fasp`.`rm_program` 
CHANGE COLUMN `PROGRAM_CODE` `PROGRAM_CODE` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL ;

CREATE TABLE `fasp`.`qat_temp_program_healthArea` (
  `PROGRAM_HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL,
  `HEALTH_AREA_ID` INT(11) NULL,
  `PROGRAM_ID` INT(11) NULL,
  PRIMARY KEY (`PROGRAM_HEALTH_AREA_ID`));

ALTER TABLE `fasp`.`qat_temp_program_healthArea` 
CHANGE COLUMN `PROGRAM_ID` `PIPELINE_ID` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `fasp`.`qat_temp_program_healthArea` 
CHANGE COLUMN `PROGRAM_HEALTH_AREA_ID` `PROGRAM_HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ;

USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationCcList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationCcList`( 
    VAR_PROGRAM_ID INT(10), 
    VAR_VERSION_ID INT(10), 
    VAR_STATUS_TYPE INT(10)
)
BEGIN

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
SET @statusType = VAR_STATUS_TYPE;




IF @statusType=1 THEN
    
    
    SELECT u2.USER_ID, u2.USERNAME, u2.EMAIL_ID 
    FROM (
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
                (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
                AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
                AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
                AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE

        UNION

        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID 
        FROM rm_program_version pv 
        LEFT JOIN us_user u ON pv.CREATED_BY=u.USER_ID 
        WHERE pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId
    ) u2 
    LEFT JOIN (
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_TO_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE
        GROUP BY u.USER_ID
        ) toList ON u2.USER_ID=toList.USER_ID
    WHERE toList.USER_ID IS NULL
    GROUP BY u2.USER_ID;

ELSEIF @statusType=3 THEN
    
    
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM vw_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND (rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_REJECT') AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;

ELSEIF @statusType=2 THEN
    
    
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM vw_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND (rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_APPROVE') AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;
END IF; 
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationToList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationToList`( 
    VAR_PROGRAM_ID INT(10), 
    VAR_VERSION_ID INT(10), 
    VAR_STATUS_TYPE INT(10)
)
BEGIN

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
SET @statusType = VAR_STATUS_TYPE;




IF @statusType=1 THEN
    
    
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM vw_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_TO_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;

ELSEIF @statusType=2 OR @statusType=3 THEN
    
    
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID 
    FROM rm_program_version pv 
    LEFT JOIN us_user u ON pv.CREATED_BY=u.USER_ID 
    WHERE pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId;
END IF; 
    
END$$

DELIMITER ;




