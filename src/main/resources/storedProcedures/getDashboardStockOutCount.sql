CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardStockOutCount`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT, VAR_VERSION_ID INT)
BEGIN
    IF VAR_VERSION_ID = -1 THEN
        SELECT p.CURRENT_VERSION_ID INTO @versionId FROM rm_program p WHERE p.PROGRAM_ID=VAR_PROGRAM_ID;
    ELSE
        SET @versionId = VAR_VERSION_ID;
    END IF;
    
    SELECT 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
        SUM(IF(ROUND(amc.MOS,1)=0,1,0)) `COUNT` 
    FROM rm_supply_plan_amc amc 
    LEFT JOIN rm_program_planning_unit ppu ON amc.PROGRAM_ID=ppu.PROGRAM_ID AND amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.ACTIVE
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE
    WHERE amc.PROGRAM_ID=VAR_PROGRAM_ID AND amc.VERSION_ID=@versionId AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE AND ppu.ACTIVE AND pu.ACTIVE
    GROUP BY pu.PLANNING_UNIT_ID;
END