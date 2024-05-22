/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  akil
 * Created: 20-May-2024
 */

USE `fasp`;
DROP procedure IF EXISTS `stockStatusOverTime`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusOverTime`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusOverTime`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 17
	-- %%%%%%%%%%%%%%%%%%%%%
    -- Only Month and Year will be considered for StartDate and StopDate
    -- mosPast indicates the number of months that we need to go into the past to calculate AMC
    -- mosFuture indicated the number of months that we need to go into the future to calculate AMC
    -- current month is always included in AMC
    -- Only a single ProgramId can be selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program
	
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
        
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @sqlString = "";
    
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	mn.MONTH, p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 	");
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	d.`CONSUMPTION_QTY`, d.`ACTUAL`, d.`FINAL_CLOSING_BALANCE`, amc.`AMC`, (d.`FINAL_CLOSING_BALANCE`/amc.`AMC`) `MoS`, amc.`AMC_COUNT`, amc.`MONTHS_IN_FUTURE_FOR_AMC`, amc.`MONTHS_IN_PAST_FOR_AMC` ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
        SET @sqlString = CONCAT(@sqlString, "AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT ");
    SET @sqlString = CONCAT(@sqlString, "		spa.TRANS_DATE, spa.PROGRAM_ID, spa.PLANNING_UNIT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "		SUM(IF(spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY)) `CONSUMPTION_QTY`, spa.ACTUAL, ");
    SET @sqlString = CONCAT(@sqlString, "		SUM(spa.CLOSING_BALANCE) `FINAL_CLOSING_BALANCE` ");
    SET @sqlString = CONCAT(@sqlString, "	FROM rm_supply_plan_amc spa ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId AND spa.TRANS_DATE BETWEEN @startDate and @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND spa.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, ") d ON mn.MONTH=d.TRANS_DATE AND ppu.PLANNING_UNIT_ID=d.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT a.TRANS_DATE, a.PLANNING_UNIT_ID, AVG(a1.CONSUMPTION_QTY) AMC, COUNT(a1.CONSUMPTION_QTY) AMC_COUNT, a.MONTHS_IN_FUTURE_FOR_AMC, a.MONTHS_IN_PAST_FOR_AMC ");
    SET @sqlString = CONCAT(@sqlString, "	FROM ");
    SET @sqlString = CONCAT(@sqlString, "		( ");
    SET @sqlString = CONCAT(@sqlString, "		SELECT mn.MONTH `TRANS_DATE`, ppu.PLANNING_UNIT_ID, ppu.MONTHS_IN_FUTURE_FOR_AMC, ppu.MONTHS_IN_PAST_FOR_AMC ");
    SET @sqlString = CONCAT(@sqlString, "		FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "		LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
        SET @sqlString = CONCAT(@sqlString, "		AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "		WHERE mn.MONTH BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "	) a ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "		( ");
    SET @sqlString = CONCAT(@sqlString, "		SELECT ");
    SET @sqlString = CONCAT(@sqlString, "		spa.TRANS_DATE, spa.PLANNING_UNIT_ID, SUM(IF(spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY)) `CONSUMPTION_QTY`, spa.ACTUAL ");
    SET @sqlString = CONCAT(@sqlString, "		FROM rm_supply_plan_amc spa ");
    SET @sqlString = CONCAT(@sqlString, "		LEFT JOIN rm_program_planning_unit ppu ON spa.PROGRAM_ID=ppu.PROGRAM_ID AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "		WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, interval ppu.MONTHS_IN_PAST_FOR_AMC MONTH) and ADDDATE(@stopDate, interval IF(ppu.MONTHS_IN_FUTURE_FOR_AMC>0,ppu.MONTHS_IN_FUTURE_FOR_AMC-1,ppu.MONTHS_IN_FUTURE_FOR_AMC) MONTH) AND spa.ACTUAL IS NOT NULL ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
        SET @sqlString = CONCAT(@sqlString, "			AND spa.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "		GROUP BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, "	) a1 ON a1.TRANS_DATE BETWEEN SUBDATE(a.TRANS_DATE, interval a.MONTHS_IN_PAST_FOR_AMC MONTH) AND ADDDATE(a.TRANS_DATE, interval IF(a.MONTHS_IN_FUTURE_FOR_AMC>0,a.MONTHS_IN_FUTURE_FOR_AMC-1,a.MONTHS_IN_FUTURE_FOR_AMC) MONTH) AND a.PLANNING_UNIT_ID=a1.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY a.TRANS_DATE, a.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") amc ON mn.MONTH=amc.TRANS_DATE AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "ORDER BY ppu.PLANNING_UNIT_ID, mn.MONTH ");
	
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;

END$$

DELIMITER ;
;

