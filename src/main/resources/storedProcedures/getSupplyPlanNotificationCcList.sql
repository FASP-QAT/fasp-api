CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationCcList`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_STATUS_ID INT(10))
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
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID FROM us_user u WHERE u.USER_ID=@committedId;

    ELSEIF @statusId=2 OR @statusId=3 THEN
        -- Approved or Rejected
        -- toList
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID FROM us_user u WHERE u.USER_ID=@approverId;
    END IF; 
    
END