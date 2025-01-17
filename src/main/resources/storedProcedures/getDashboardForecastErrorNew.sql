CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardForecastErrorNew`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT)
BEGIN

    SELECT p.CURRENT_VERSION_ID INTO @varVersionId FROM vw_program p WHERE p.PROGRAM_ID=VAR_PROGRAM_ID;
    SELECT GROUP_CONCAT(pu.PLANNING_UNIT_ID) INTO @varPuList
    FROM vw_program p 
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID
    LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    WHERE p.PROGRAM_ID=VAR_PROGRAM_ID AND ppu.ACTIVE AND pu.ACTIVE;
    SELECT COUNT(pr.REGION_ID) into @varRegionCount FROM rm_program_region pr WHERE pr.PROGRAM_ID=VAR_PROGRAM_ID;


    SELECT 
        c4.PLANNING_UNIT_ID, c4.LABEL_ID, c4.LABEL_EN, c4.LABEL_FR, c4.LABEL_SP, c4.LABEL_PR, AVG(FORECAST_ERROR) `ERROR_PERC`, COUNT(c4.ACTUAL_CONSUMPTION) `NO_OF_MONTHS`, c4.FORECAST_ERROR_THRESHOLD
    FROM (
        SELECT 
            mn.MONTH, pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, c2.CONSUMPTION_DATE, c2.FORECAST_CONSUMPTION, c2.ACTUAL_CONSUMPTION, 
            IF(c2.FORECAST_CONSUMPTION IS NOT NULL AND c2.ACTUAL_CONSUMPTION IS NOT NULL, SUM(ABS(c3.`FORECAST_CONSUMPTION`-c3.`ACTUAL_CONSUMPTION`))/SUM(c3.`ACTUAL_CONSUMPTION`), null) `FORECAST_ERROR`, ppu.FORECAST_ERROR_THRESHOLD
        FROM mn 
        LEFT JOIN vw_program p ON p.PROGRAM_ID=VAR_PROGRAM_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID
        LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN (
            SELECT c1.PLANNING_UNIT_ID, c1.CONSUMPTION_DATE, COUNT(c1.REGION_ID) `REGION_COUNT`, SUM(c1.`FORECAST_CONSUMPTION`) `FORECAST_CONSUMPTION`, SUM(c1.`ACTUAL_CONSUMPTION`) `ACTUAL_CONSUMPTION` 
            FROM (
                SELECT ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE, SUM(IF(ct.ACTUAL_FLAG, ct.CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION`, SUM(IF(ct.ACTUAL_FLAG=0, ct.CONSUMPTION_QTY, null)) `FORECAST_CONSUMPTION`
                FROM (
                    SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE ct.VERSION_ID<=@varVersionId AND c.PROGRAM_ID=VAR_PROGRAM_ID GROUP BY ct.CONSUMPTION_ID
                ) tc 
                LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.`CONSUMPTION_ID`
                LEFT JOIN rm_consumption_trans ct ON cons.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
                WHERE ct.CONSUMPTION_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @varPuList) AND ct.ACTIVE
                GROUP BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE
    --            ORDER BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE
            ) c1 GROUP BY c1.PLANNING_UNIT_ID, c1.CONSUMPTION_DATE HAVING REGION_COUNT=@varRegionCount
        ) c2 ON pu.PLANNING_UNIT_ID=c2.PLANNING_UNIT_ID AND mn.MONTH=c2.CONSUMPTION_DATE
        LEFT JOIN (
            SELECT ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, SUM(IF(ct.ACTUAL_FLAG=0, ct.CONSUMPTION_QTY, null)) `FORECAST_CONSUMPTION`, SUM(IF(ct.ACTUAL_FLAG, ct.CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION`
            FROM (
                SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE ct.VERSION_ID<=@varVersionId AND c.PROGRAM_ID=VAR_PROGRAM_ID GROUP BY ct.CONSUMPTION_ID
            ) tc 
            LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.`CONSUMPTION_ID`
            LEFT JOIN rm_consumption_trans ct ON cons.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
            WHERE ct.CONSUMPTION_DATE BETWEEN SUBDATE(VAR_START_DATE, INTERVAL 5 MONTH) AND VAR_STOP_DATE AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @varPuList) AND ct.ACTIVE
            GROUP BY ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE
        ) c3 ON c3.CONSUMPTION_DATE BETWEEN SUBDATE(mn.`MONTH`, INTERVAL 5 MONTH) AND mn.`MONTH` AND c3.PLANNING_UNIT_ID=c2.PLANNING_UNIT_ID
        WHERE mn.MONTH BETWEEN VAR_START_DATE AND VAR_STOP_DATE AND p.PROGRAM_ID=VAR_PROGRAM_ID AND pu.ACTIVE
        GROUP BY pu.PLANNING_UNIT_ID, mn.`MONTH`
    --    ORDER BY pu.PLANNING_UNIT_ID, mn.`MONTH`
    ) c4 GROUP BY c4.PLANNING_UNIT_ID ORDER BY `ERROR_PERC` DESC;
END