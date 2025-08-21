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
    SELECT u.USER_ID, u.USERNAME, u.ORG_AND_COUNTRY, r.ROLE_ID, l.LABEL_ID, l.LABEL_EN, l.LABEL_FR, l.LABEL_SP, l.LABEL_PR 
    FROM us_user u 
    LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID 
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID AND FIND_IN_SET (ur.ROLE_ID, @varRoleIdList)
    LEFT JOIN us_role r ON ur.ROLE_ID=r.ROLE_ID 
    LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID
    WHERE 
        u.ACTIVE
	AND u.REALM_ID=@varRealmId 
        AND FIND_IN_SET(acl.ROLE_ID, @varRoleIdList)
	AND (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=@varRealmCountryId)
	AND (acl.HEALTH_AREA_ID IS NULL OR FIND_IN_SET(acl.HEALTH_AREA_ID, @varHealthAreaId))
	AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=@varOrganisationId)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=@varProgramId)
	AND (acl.FUNDING_SOURCE_ID IS NULL OR FIND_IN_SET(acl.FUNDING_SOURCE_ID, @varFundingSourceId))
	AND (acl.PROCUREMENT_AGENT_ID IS NULL OR FIND_IN_SET(acl.PROCUREMENT_AGENT_ID, @varProcurementAgentId));
END