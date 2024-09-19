CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardForecastConsumptionProblems`(VAR_PROGRAM_ID INT, VAR_CUR_DATE DATE)
BEGIN

    SET @varStartDate = VAR_CUR_DATE;
    SET @varStopDate = DATE_ADD(@varStartDate, INTERVAL 18 MONTH);
    SELECT p.CURRENT_VERSION_ID into @versionId FROM rm_program p WHERE p.PROGRAM_ID=VAR_PROGRAM_ID;
    
    SELECT p2.PU_COUNT, SUM(IF(FORECAST_COUNT=19*p2.REGION_COUNT,1,0)) `GOOD_COUNT` FROM 
        (
        SELECT p1.*, COUNT(ppu.PLANNING_UNIT_ID) `PU_COUNT` FROM 
            (
            SELECT 
                p.PROGRAM_ID, COUNT(pr.REGION_ID) `REGION_COUNT`  
            FROM vw_program p 
            LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID 
            WHERE p.PROGRAM_ID=VAR_PROGRAM_ID
            GROUP BY p.PROGRAM_ID
        ) p1 
        LEFT JOIN rm_program_planning_unit ppu ON p1.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE
        LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE
        GROUP BY p1.PROGRAM_ID
    ) p2 
    LEFT JOIN (SELECT 
            ct.PLANNING_UNIT_ID, SUM(IF(ct.ACTUAL_FLAG=0, IF(ct.CONSUMPTION_QTY is not null, 1, 0), 0)) `FORECAST_COUNT`
	FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (@versionId=-1 OR ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=VAR_PROGRAM_ID GROUP BY ct.CONSUMPTION_ID) tc 
	LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
	LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=VAR_PROGRAM_ID AND ppu.PLANNING_UNIT_ID=ct.PLANNING_UNIT_ID
    WHERE ppu.ACTIVE AND pu.ACTIVE AND ct.ACTIVE AND ct.CONSUMPTION_DATE BETWEEN @varStartDate and @varStopDate
    GROUP BY ct.PLANNING_UNIT_ID) c1 ON TRUE
    GROUP BY p2.PROGRAM_ID;
END