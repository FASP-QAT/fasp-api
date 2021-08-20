CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationToList`( 
    VAR_PROGRAM_ID INT(10), 
    VAR_VERSION_ID INT(10), 
    VAR_STATUS_TYPE INT(10)
)
BEGIN

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
SET @statusType = VAR_STATUS_TYPE;
-- statusTypeId = 1 -> Final Submitted
-- statusTypeId = 2 -> Final Approved
-- statusTypeID = 3 -> Final Rejected

IF @statusType=1 THEN
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

ELSEIF @statusType=2 OR @statusType=3 THEN
    -- Approved or Rejected
    -- toList
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID 
    FROM rm_program_version pv 
    LEFT JOIN us_user u ON pv.CREATED_BY=u.USER_ID 
    WHERE pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId;
END IF; 
    
END