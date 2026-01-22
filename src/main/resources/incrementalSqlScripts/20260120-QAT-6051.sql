USE `fasp`;
DROP procedure IF EXISTS `globalConsumption`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`globalConsumption`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `globalConsumption`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_REALM_COUNTRY_IDS TEXT, VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PROGRAM_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_VIEW_BY INT(10))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 3
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	-- realmId must be a valid realm that you want to run this Global report for
        -- RealmCountryIds is the list of Countries that you want to run the report for. Empty means all Countries
        -- ProgramIds is the list of Programs that you want to run the report for. Empty means all Programs
        -- EquivalencyUnitId 0 means Run based on Planning Units, a non 0 value means sum for Equivalency Units
        -- PlanningUnitIds Only when the Equvalency Unit Id is non 0 then the list of PlanningUnits that you want to run the report for must be provided. If Equivalency Unit Id is 0 then only one PU can be selected and the first item in the list is taken
        -- startDate and stopDate are the range between which you want to run the report for`
        -- viewBy = 1 shows the report grouped by Country
        -- viewBy = 2 shows the report grouped by Program
	
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
    
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varRealmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmId = VAR_REALM_ID;
    SET @viewBy = VAR_VIEW_BY;
    SET @equivalencyUnitId = VAR_EQUIVALENCY_UNIT_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SELECT LOCATE(",", @planningUnitIds) INTO @commaLocation;
    IF @equivalencyUnitId = 0 && @commaLocation != 0 THEN
        SET @planningUnitIds = LEFT(@planningUnitIds, @commaLocation-1);
    END IF;
    
    
    SET @sqlString = "";
    SET @initialSelect = "";
    SET @groupBy = "";
    IF @viewBy = 1 THEN -- View by Country
        SET @initialSelect = CONCAT(@sqlString, "SELECT sma.TRANS_DATE, rc.REALM_COUNTRY_ID `ID`, c.COUNTRY_CODE `CODE`, c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_PR, c.LABEL_SP, SUM(IF(@equivalencyUnitId=0, sma.FORECASTED_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY*pu.MULTIPLIER/eum.CONVERT_TO_EU)) `FORECASTED_CONSUMPTION`, SUM(IF(@equivalencyUnitId=0, sma.ACTUAL_CONSUMPTION_QTY, sma.ACTUAL_CONSUMPTION_QTY*pu.MULTIPLIER/eum.CONVERT_TO_EU)) `ACTUAL_CONSUMPTION` ");
        SET @groupBy = "GROUP BY sma.TRANS_DATE, rc.REALM_COUNTRY_ID";
    ELSE  -- View by Program
        SET @initialSelect = CONCAT(@sqlString, "SELECT sma.TRANS_DATE, p.PROGRAM_ID `ID`, p.PROGRAM_CODE `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP, SUM(IF(@equivalencyUnitId=0, sma.FORECASTED_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY*pu.MULTIPLIER/eum.CONVERT_TO_EU)) `FORECASTED_CONSUMPTION`, SUM(IF(@equivalencyUnitId=0, sma.ACTUAL_CONSUMPTION_QTY, sma.ACTUAL_CONSUMPTION_QTY*pu.MULTIPLIER/eum.CONVERT_TO_EU)) `ACTUAL_CONSUMPTION` ");
        SET @groupBy = "GROUP BY sma.TRANS_DATE, sma.PROGRAM_ID";
    END IF;
    
    SET @sqlString = CONCAT(@sqlString, @initialSelect);
    SET @sqlString = CONCAT(@sqlString, "FROM rm_supply_plan_amc sma ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        p.PROGRAM_ID, p.CURRENT_VERSION_ID MAX_VERSION FROM vw_program p WHERE p.ACTIVE ");
    IF LENGTH(@varProgramIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND p.PROGRAM_ID in (",@varProgramIds,") ");
    END IF;
    IF LENGTH(@varRealmCountryIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "		AND p.REALM_COUNTRY_ID in (",@varRealmCountryIds,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS f ON sma.PROGRAM_ID=f.PROGRAM_ID AND sma.VERSION_ID=f.MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON sma.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON c.COUNTRY_ID=rc.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_planning_unit pu ON sma.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON sma.PROGRAM_ID=ppu.PROGRAM_ID AND sma.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit_mapping eum ON pu.FORECASTING_UNIT_ID=eum.FORECASTING_UNIT_ID AND eum.ACTIVE AND eum.EQUIVALENCY_UNIT_ID=@equivalencyUnitId ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_equivalency_unit eu ON eu.EQUIVALENCY_UNIT_ID=eum.EQUIVALENCY_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    sma.TRANS_DATE BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "    AND ppu.ACTIVE AND pu.ACTIVE");
    SET @sqlString = CONCAT(@sqlString, "    AND f.PROGRAM_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "    AND sma.PLANNING_UNIT_ID IN (",@planningUnitIds,") ");
    SET @sqlString = CONCAT(@sqlString, @groupBy);
    SET @sqlString = CONCAT(@sqlString, " ORDER BY `CODE`, `TRANS_DATE`");
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;

END$$

DELIMITER ;
;

