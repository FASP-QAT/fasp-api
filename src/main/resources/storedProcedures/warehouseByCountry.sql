USE `fasp`;
DROP procedure IF EXISTS `warehouseByCountryReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `warehouseByCountryReport`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_REALM_COUNTRY_IDS TEXT)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no  
    -- %%%%%%%%%%%%%%%%%%%%%
    
    -- RealmId must be the Realm that the user is from so that we can select all the Countries for that Realm
    -- RealmCountryIds are a list of RealmCountries that you want to run the report for 
    -- RealmCountrIds blank means you want to run it for all the RealmCountries
    -- List of all the Regions for the Programs selected and their capacity
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (rc.REALM_COUNTRY_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR rc.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");

    SET @realmId = VAR_REALM_ID;
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	r.REGION_ID, r.LABEL_ID `REGION_LABEL_ID`, r.LABEL_EN `REGION_LABEL_EN`, r.LABEL_FR `REGION_LABEL_FR`, r.LABEL_SP `REGION_LABEL_SP`, r.LABEL_PR `REGION_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	r.GLN, r.CAPACITY_CBM, r.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_realm_country rc ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_region r ON rc.REALM_COUNTRY_ID=r.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE rc.REALM_ID=@realmId ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN 
	SET @sqlString = CONCAT(@sqlString, " AND rc.REALM_COUNTRY_ID IN (" , VAR_REALM_COUNTRY_IDS , ")");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "ORDER BY c.COUNTRY_CODE, r.REGION_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
   	
END$$

DELIMITER ;



