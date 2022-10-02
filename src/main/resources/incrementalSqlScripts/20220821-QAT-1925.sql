DELETE rbf.* FROM us_role_business_function rbf where rbf.BUSINESS_FUNCTION_ID like '%NOTIFICATION%';
DELETE FROM `us_business_function` WHERE (`BUSINESS_FUNCTION_ID` = 'ROLE_BF_NOTIFICATION_CC_APPROVE');
DELETE FROM `us_business_function` WHERE (`BUSINESS_FUNCTION_ID` = 'ROLE_BF_NOTIFICATION_CC_COMMIT');
DELETE FROM `us_business_function` WHERE (`BUSINESS_FUNCTION_ID` = 'ROLE_BF_NOTIFICATION_CC_REJECT');
DELETE FROM `us_business_function` WHERE (`BUSINESS_FUNCTION_ID` = 'ROLE_BF_NOTIFICATION_TO_APPROVE');
DELETE FROM `us_business_function` WHERE (`BUSINESS_FUNCTION_ID` = 'ROLE_BF_NOTIFICATION_TO_REJECT');

INSERT INTO ap_label values (null, 'SP - Notification Bcc approve', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO us_business_function values ('ROLE_BF_NOTIFICATION_BCC_APPROVE', @labelId, 1, now(), 1, now());

INSERT INTO ap_label values (null, 'SP - Notification Bcc reject', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO us_business_function values ('ROLE_BF_NOTIFICATION_BCC_REJECT', @labelId, 1, now(), 1, now());

INSERT INTO ap_label values (null, 'SP - Notification Bcc commit', null, null, null, 1, now(), 1, now(), 24);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO us_business_function values ('ROLE_BF_NOTIFICATION_BCC_COMMIT', @labelId, 1, now(), 1, now());

INSERT INTO us_role_business_function VALUES (null, 'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_TO_COMMIT', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_BCC_COMMIT', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_BCC_APPROVE', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_BCC_REJECT', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_PROGRAM_USER','ROLE_BF_NOTIFICATION_BCC_REJECT', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_SUPPLY_PLAN_REVIEWER','ROLE_BF_NOTIFICATION_BCC_REJECT', 1, now(), 1, now());
INSERT INTO us_role_business_function VALUES (null, 'ROLE_REALM_ADMIN','ROLE_BF_NOTIFICATION_BCC_REJECT', 1, now(), 1, now());


USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationToList`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getSupplyPlanNotificationToList`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationToList`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_STATUS_ID INT(10))
BEGIN
    -- This SP is only called for Final Supply Plan submissions not for Draft
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @statusId = VAR_STATUS_ID;
    -- statusTypeId = 1 -> Final Submitted
    -- statusTypeId = 2 -> Final Approved
    -- statusTypeID = 3 -> Final Rejected
    SELECT pv.CREATED_BY `COMMITTED_BY`, pvt.LAST_MODIFIED_BY INTO @committedId, @approverId FROM rm_program_version pv LEFT JOIN rm_program_version_trans pvt ON pv.PROGRAM_VERSION_ID=pvt.PROGRAM_VERSION_ID AND pvt.VERSION_STATUS_ID=@statusId WHERE pv.PROGRAM_ID=@programId and pv.VERSION_ID=@versionId;
    
    IF @statusId=1 THEN
        -- Final submitted
        -- toList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR FIND_IN_SET(acl.HEALTH_AREA_ID,p.HEALTH_AREA_ID))
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_TO_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE
        GROUP BY u.USER_ID;

    ELSEIF @statusId=2 OR @statusId=3 THEN
        -- Approved or Rejected
        -- toList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID FROM us_user u WHERE u.USER_ID=@committedId;
    END IF; 
    
END$$

DELIMITER ;
;


USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationCcList`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getSupplyPlanNotificationCcList`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationCcList`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_STATUS_ID INT(10))
BEGIN
    -- This SP is only called for Final Supply Plan submissions not for Draft
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @statusId = VAR_STATUS_ID;
    -- statusId = 1 -> Final Submitted
    -- statusId = 2 -> Final Approved
    -- statusId = 3 -> Final Rejected
    SELECT pv.CREATED_BY `COMMITTED_BY`, pvt.LAST_MODIFIED_BY INTO @committedId, @approverId FROM rm_program_version pv LEFT JOIN rm_program_version_trans pvt ON pv.PROGRAM_VERSION_ID=pvt.PROGRAM_VERSION_ID AND pvt.VERSION_STATUS_ID=@statusId WHERE pv.PROGRAM_ID=@programId and pv.VERSION_ID=@versionId;
    
    IF @statusId=1 THEN
        -- Final submitted
        -- toList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID FROM us_user u WHERE u.USER_ID=@committedId;

    ELSEIF @statusId=2 OR @statusId=3 THEN
        -- Approved or Rejected
        -- toList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID FROM us_user u WHERE u.USER_ID=@approverId;
    END IF; 
    
END$$

DELIMITER ;
;



USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationBccList`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getSupplyPlanNotificationBccList`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationBccList`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_STATUS_ID INT(10))
BEGIN 
    -- This SP is only called for Final Supply Plan submissions not for Draft
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @statusId = VAR_STATUS_ID;
    -- statusId = 1 -> Final Submitted
    -- statusId = 2 -> Final Approved
    -- statusId = 3 -> Final Rejected
    SELECT pv.CREATED_BY `COMMITTED_BY`, pvt.LAST_MODIFIED_BY INTO @committedId, @approverId FROM rm_program_version pv LEFT JOIN rm_program_version_trans pvt ON pv.PROGRAM_VERSION_ID=pvt.PROGRAM_VERSION_ID AND pvt.VERSION_STATUS_ID=@statusId WHERE pv.PROGRAM_ID=@programId and pv.VERSION_ID=@versionId;
    
    IF @statusId=1 THEN
        -- Final submitted
        -- bccList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR FIND_IN_SET(acl.HEALTH_AREA_ID,p.HEALTH_AREA_ID))
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID IN ('ROLE_BF_NOTIFICATION_BCC_COMMIT') AND p.PROGRAM_ID=@programId AND u.ACTIVE 
        AND u.USER_ID NOT IN (
            SELECT u.USER_ID
            FROM vw_program p 
            LEFT JOIN us_user_acl acl ON 
                (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
                AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
                AND (acl.HEALTH_AREA_ID is null OR FIND_IN_SET(acl.HEALTH_AREA_ID,p.HEALTH_AREA_ID))
                AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
            LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
            LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
            LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
            WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID IN ('ROLE_BF_NOTIFICATION_TO_COMMIT') AND p.PROGRAM_ID=@programId AND u.ACTIVE
            GROUP BY u.USER_ID
        )
        GROUP BY u.USER_ID;

    ELSEIF @statusId=2 THEN
        -- Approved
        -- bccList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR FIND_IN_SET(acl.HEALTH_AREA_ID,p.HEALTH_AREA_ID))
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID IN ('ROLE_BF_NOTIFICATION_BCC_APPROVE') AND p.PROGRAM_ID=@programId AND u.ACTIVE AND u.USER_ID!=@committedId
        GROUP BY u.USER_ID;
    
    ELSEIF @statusId=3 THEN
        -- Rejected
        -- bccList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM vw_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR FIND_IN_SET(acl.HEALTH_AREA_ID,p.HEALTH_AREA_ID))
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID IN ('ROLE_BF_NOTIFICATION_BCC_REJECT') AND p.PROGRAM_ID=@programId AND u.ACTIVE AND u.USER_ID!=@committedId
        GROUP BY u.USER_ID;
    END IF; 
    
END$$

DELIMITER ;
;

