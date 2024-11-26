ALTER TABLE `fasp`.`us_user_role` ADD UNIQUE INDEX `unq_userRole` (`USER_ID` ASC, `ROLE_ID` ASC) VISIBLE;

USE `fasp`;
DROP procedure IF EXISTS `buildUserAcl`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`buildUserAcl`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `buildUserAcl`()
BEGIN
	DECLARE VAR_FINISHED INTEGER DEFAULT 0;
	DECLARE VAR_USER_ID INTEGER DEFAULT 0;
    DECLARE VAR_ROLE_ID VARCHAR(50) DEFAULT ""; 

	
	DEClARE curUser CURSOR FOR 
		SELECT ur.USER_ID, ur.ROLE_ID FROM us_user_role ur;

	
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET VAR_FINISHED = 1;
	
     DROP TABLE IF EXISTS us_user_acl_old;
     CREATE TABLE `us_user_acl_old` (
      `USER_ACL_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique User Access Control List Id',
      `USER_ID` int unsigned NOT NULL COMMENT 'Foriegn key for the User Id',
      `REALM_COUNTRY_ID` int unsigned DEFAULT NULL COMMENT 'Foriegn key for the Country. If this is null it indicates the user has access to all the Countries',
      `HEALTH_AREA_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Health Area. If this is null it indicates the user has access to all the Health Areas',
      `ORGANISATION_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Organisation. If this is null it indicates the user has access to all the Organisations.',
      `PROGRAM_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
      `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
      `CREATED_DATE` datetime NOT NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
      `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
      `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
      PRIMARY KEY (`USER_ACL_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Access control lists for the application\nNote: Multiple rows for each user. Each row indicates what he has access to.';

    INSERT INTO us_user_acl_old SELECT acl.USER_ACL_ID, acl.USER_ID, acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID, acl.CREATED_BY, acl.CREATED_DATE, acl.LAST_MODIFIED_BY, acl.LAST_MODIFIED_DATE FROM us_user_acl acl;
    TRUNCATE TABLE us_user_acl;

    OPEN curUser;
    INSERT INTO log VALUES (null, now(), "Starting User loop");
    getUserList: LOOP
		FETCH curUser INTO VAR_USER_ID, VAR_ROLE_ID;
		IF VAR_FINISHED = 1 THEN 
			LEAVE getUserList;
		END IF;
        INSERT INTO log VALUES (null, now(), CONCAT("Starting loop for ",VAR_USER_ID, " and ", VAR_ROLE_ID));
		
        INSERT INTO us_user_acl SELECT null, VAR_USER_ID, VAR_ROLE_ID, acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID, acl.CREATED_BY, acl.CREATED_DATE, acl.LAST_MODIFIED_BY, acl.LAST_MODIFIED_DATE FROM us_user_acl_old acl WHERE acl.USER_ID=VAR_USER_ID;
        
		INSERT INTO log VALUES (null, now(), CONCAT(ROW_COUNT(), " - Inserts done for ", VAR_USER_ID," AND ",VAR_ROLE_ID));
        
	END LOOP getUserList;
END$$

DELIMITER ;
;

CALL buildUserAcl();

DROP TABLE IF EXISTS us_user_acl_old;
