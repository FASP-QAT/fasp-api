USE `fasp`;
DROP procedure IF EXISTS `globalConsumption`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`globalConsumption`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `globalConsumption`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REPORT_VIEW INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 3
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	-- realmId must be a valid realm that you want to run this Global report for
        -- RealmCountryIds is the list of Countries that you want to run the report for. Empty means all Countries
        -- ProgramIds is the list of Programs that you want to run the report for. Empty means all Programs
        -- PlanningUnitIds is the list of PlanningUnits that you want to run the report for. Empty means all Planning Units
        -- startDate and stopDate are the range between which you want to run the report for`
        -- reportView = 1 shows the Consumption in PlanningUnits
        -- reportView = 2 shows the Consumption in ForecastingUnits
	
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
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curHealthAreaId,-1),",p.HEALTH_AREA_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @reportView = VAR_REPORT_VIEW;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
	
    SET @sqlString = "";
    
    SET @sqlString = CONCAT(@sqlString, "SELECT sma.TRANS_DATE, rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`, SUM(IF(@reportView=1, sma.FORECASTED_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY*pu.MULTIPLIER)) `FORECASTED_CONSUMPTION`, SUM(IF(@reportView=1, sma.ACTUAL_CONSUMPTION_QTY, sma.ACTUAL_CONSUMPTION_QTY*pu.MULTIPLIER)) `ACTUAL_CONSUMPTION` ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_supply_plan_amc sma ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        pv.PROGRAM_ID, ");
    SET @sqlString = CONCAT(@sqlString, "        MAX(pv.VERSION_ID) MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "    FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE TRUE ");
    IF @approvedSupplyPlanOnly = 1 THEN
        SET @sqlString = CONCAT(@sqlString, "        AND pv.VERSION_TYPE_ID=2 ");
        SET @sqlString = CONCAT(@sqlString, "        AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND p.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND p.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS f ON sma.PROGRAM_ID=f.PROGRAM_ID AND sma.VERSION_ID=f.MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON sma.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON c.COUNTRY_ID=rc.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_planning_unit pu ON sma.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON sma.PROGRAM_ID=ppu.PROGRAM_ID AND sma.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    sma.TRANS_DATE BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "    AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "    AND f.PROGRAM_ID IS NOT NULL ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND sma.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY sma.TRANS_DATE, rc.REALM_COUNTRY_ID ");
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;

END$$

DELIMITER ;
;