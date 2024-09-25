CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardForecastConsumptionProblemsForLoadProgram`(VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_CUR_DATE DATE)
BEGIN

    SET @varStartDate = VAR_CUR_DATE;
    SET @varStopDate = DATE_ADD(@varStartDate, INTERVAL 18 MONTH);
    SELECT COUNT(*) into @regionCount FROM rm_program_region pr LEFT JOIN rm_region r ON pr.REGION_ID=r.REGION_ID where pr.PROGRAM_ID=VAR_PROGRAM_ID AND pr.ACTIVE AND r.ACTIVE;
    
    SELECT 
        ct.PLANNING_UNIT_ID, 
        19*@regionCount `REQUIRED_COUNT`,
        SUM(IF(ct.ACTUAL_FLAG=0, IF(ct.CONSUMPTION_QTY is not null, 1, 0), 0)) `FORECAST_COUNT`
    FROM (
        SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID 
        FROM rm_consumption c 
        LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID 
        WHERE ct.VERSION_ID<=VAR_VERSION_ID AND c.PROGRAM_ID=VAR_PROGRAM_ID 
        GROUP BY ct.CONSUMPTION_ID
    ) tc 
    LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
    LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=cons.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=ct.PLANNING_UNIT_ID
    WHERE ppu.ACTIVE AND pu.ACTIVE AND ct.ACTIVE AND ct.CONSUMPTION_DATE BETWEEN @varStartDate and @varStopDate
    GROUP BY ct.PLANNING_UNIT_ID;
END