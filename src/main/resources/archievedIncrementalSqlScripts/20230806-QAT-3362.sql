USE `fasp`;
DROP procedure IF EXISTS `forecastMetricsComparision`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`forecastMetricsComparision`;
;

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
    -- INSERT INTO log VALUES (null, now(), "Starting SP");
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curHealthAreaId,-1),",p.HEALTH_AREA_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curOrganisationId,-1),",p.ORGANISATION_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    -- INSERT INTO log VALUES (null, now(), "Completed loop");
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");

    SET @varRealmId = VAR_REALM_ID;
    SET @varStopDate = VAR_START_DATE;
    SET @varStartDate = SUBDATE(VAR_START_DATE, INTERVAL VAR_PREVIOUS_MONTHS-1 MONTH);
    SET @varApprovedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @varRealmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varTracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @varPlanningUnitIds = VAR_PLANNING_UNIT_IDS;
    
    
    DROP TABLE IF EXISTS tmp_forecastMetrics1;
    CREATE TEMPORARY TABLE tmp_forecastMetrics1 (
        `PROGRAM_ID` int unsigned NOT NULL,
        `VERSION_ID` int unsigned NOT NULL,
        `PLANNING_UNIT_ID` int unsigned NOT NULL,
        `TRANS_DATE` date NOT NULL,
        `ACTUAL` tinyint(1) NOT NULL,
        `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
        `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
        PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

    DROP TABLE IF EXISTS tmp_forecastMetrics2;
    CREATE TEMPORARY TABLE tmp_forecastMetrics2 (
        `PROGRAM_ID` int unsigned NOT NULL,
        `VERSION_ID` int unsigned NOT NULL,
        `PLANNING_UNIT_ID` int unsigned NOT NULL,
        `TRANS_DATE` date NOT NULL,
        `ACTUAL` tinyint(1) NOT NULL,
        `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
        `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
        PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
    -- INSERT INTO log VALUES (null, now(), "Temporary tables created");
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "INSERT INTO tmp_forecastMetrics1 ");
	SET @sqlString = CONCAT(@sqlString, "SELECT p1.PROGRAM_ID, p1.MAX_VERSION, spa.PLANNING_UNIT_ID, spa.TRANS_DATE, spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY");
    SET @sqlString = CONCAT(@sqlString, "        FROM (");
    SET @sqlString = CONCAT(@sqlString, "            SELECT ");
    SET @sqlString = CONCAT(@sqlString, "                pv.PROGRAM_ID, MAX(pv.VERSION_ID) MAX_VERSION ");
	SET @sqlString = CONCAT(@sqlString, "	FROM rm_program_version pv ");
	SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID");
	SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "                TRUE ");
    SET @sqlString = CONCAT(@sqlString, "                AND (@varApprovedSupplyPlanOnly=0 OR (@varApprovedSupplyPlanOnly=1 AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2))");
    SET @sqlString = CONCAT(@sqlString, "                AND p.ACTIVE");
    SET @sqlString = CONCAT(@sqlString, "                AND (LENGTH(@varProgramIds)=0 OR FIND_IN_SET(pv.PROGRAM_ID, @varProgramIds))");
    SET @sqlString = CONCAT(@sqlString, "                AND (LENGTH(@varRealmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @varRealmCountryIds))");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "		GROUP BY pv.PROGRAM_ID");
    SET @sqlString = CONCAT(@sqlString, "        ) p1");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_supply_plan_amc spa ON p1.PROGRAM_ID=spa.PROGRAM_ID AND p1.MAX_VERSION=spa.VERSION_ID AND spa.TRANS_DATE BETWEEN @varStartDate AND @varStopDate");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            TRUE");
    SET @sqlString = CONCAT(@sqlString, "            AND spa.PLANNING_UNIT_ID IS NOT NULL");
	SET @sqlString = CONCAT(@sqlString, "			AND (LENGTH(@varPlanningUnitIds)=0 OR FIND_IN_SET(spa.PLANNING_UNIT_ID, @varPlanningUnitIds))");
	SET @sqlString = CONCAT(@sqlString, "			AND (LENGTH(@varTracerCategoryIds)=0 OR FIND_IN_SET(fu.TRACER_CATEGORY_ID, @varTracerCategoryIds))");
    PREPARE S2 FROM @sqlString;
    EXECUTE S2;
    -- INSERT INTO log VALUES (null, now(), "tmp_forecastMetrics1 completed");
    INSERT INTO tmp_forecastMetrics2 SELECT * FROM tmp_forecastMetrics1;
    -- INSERT INTO log VALUES (null, now(), "tmp_forecastMetrics2 completed");
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    fm.TRANS_DATE, ");
    SET @sqlString = CONCAT(@sqlString, "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR  `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    fm.ACTUAL, fm.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, fm.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, fm2.ACTUAL_CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(fm2.ACTUAL_CONSUMPTION_QTY-fm2.FORECASTED_CONSUMPTION_QTY), null)) `DIFF_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, 1, 0)) `MONTH_COUNT`, ");
    SET @sqlString = CONCAT(@sqlString, "	SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(fm2.ACTUAL_CONSUMPTION_QTY-fm2.FORECASTED_CONSUMPTION_QTY), null))*100/SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, fm2.ACTUAL_CONSUMPTION_QTY, null)) `FORECAST_ERROR` ");
    SET @sqlString = CONCAT(@sqlString, "FROM tmp_forecastMetrics1 fm ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON fm.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON fm.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN tmp_forecastMetrics2 fm2 ON fm.PROGRAM_ID=fm2.PROGRAM_ID AND fm.VERSION_ID=fm2.VERSION_ID AND fm.PLANNING_UNIT_ID=fm2.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE fm.TRANS_DATE=@varStopDate ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY fm.PROGRAM_ID, fm.VERSION_ID, fm.PLANNING_UNIT_ID;");
    PREPARE S3 FROM @sqlString;
    EXECUTE S3;
    -- INSERT INTO log VALUES (null, now(), "Main query completed");
END$$

DELIMITER ;
;

