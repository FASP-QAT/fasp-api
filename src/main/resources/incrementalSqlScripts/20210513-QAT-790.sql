USE `fasp`;
DROP procedure IF EXISTS `forecastMetricsComparision`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `forecastMetricsComparision`(
    VAR_USER_ID INT(10), 
    VAR_REALM_ID INT(10), 
    VAR_START_DATE DATE, 
    VAR_REALM_COUNTRY_IDS TEXT, 
    VAR_PROGRAM_IDS TEXT, 
    VAR_TRACER_CATEGORY_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_PREVIOUS_MONTHS INT(10), 
    VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 5
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- realmId since it is a Global report need to include Realm
    -- startDate - date that the report is to be run for
    -- realmCountryIds list of countries that we need to run the report for
    -- programIds is the list of programs that we need to run the report for
    -- planningUnitIds is the list of planningUnits that we need to run the report for
    -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
    -- current month is always included in the calculation
    -- only consider those months that have both a Forecasted and Actual consumption
    -- WAPE Formulae
    -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months)) 

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
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @previousMonths = VAR_PREVIOUS_MONTHS;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @tracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    
    SET @sqlString = "";
    
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    spa.TRANS_DATE, ");
    SET @sqlString = CONCAT(@sqlString, "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR  `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, spa.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION`, ");
    SET @sqlString = CONCAT(@sqlString, "    c2.ACTUAL_CONSUMPTION_TOTAL, c2.DIFF_CONSUMPTION_TOTAL, c2.MONTH_COUNT, c2.DIFF_CONSUMPTION_TOTAL*100/c2.ACTUAL_CONSUMPTION_TOTAL FORECAST_ERROR ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "           WHERE TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "               AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "               AND spa.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "           GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE=@startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        @startDate `TRANS_DATE`, p.PROGRAM_ID, pu.PLANNING_UNIT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, spa.ACTUAL_CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(spa.ACTUAL_CONSUMPTION_QTY-spa.FORECASTED_CONSUMPTION_QTY), null)) `DIFF_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, 1, 0)) `MONTH_COUNT` ");
    SET @sqlString = CONCAT(@sqlString, "    FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "        ( ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "            AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(@programIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "            AND FIND_IN_SET(spa.PROGRAM_ID, @programIds) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    ) f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE ");
    SET @sqlString = CONCAT(@sqlString, "        TRUE ");
    IF LENGTH(@programIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(ppu.PROGRAM_ID, @programIds) ");
    END IF;
    IF LENGTH(@realmCountryIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds) ");
    END IF;
    IF LENGTH(@planningUnitIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, @planningUnitIds) ");
    END IF;
    IF LENGTH(@tracerCategoryIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, @tracerCategoryIds) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY ppu.PROGRAM_ID, ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") c2 ON spa.PROGRAM_ID=c2.PROGRAM_ID AND spa.TRANS_DATE=c2.TRANS_DATE AND spa.PLANNING_UNIT_ID=c2.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    TRUE AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(@programIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(ppu.PROGRAM_ID, @programIds) ");
    END IF;
    IF LENGTH(@realmCountryIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds) ");
    END IF;
    IF LENGTH(@planningUnitIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, @planningUnitIds) ");
    END IF;
    IF LENGTH(@tracerCategoryIds)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, @tracerCategoryIds) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


