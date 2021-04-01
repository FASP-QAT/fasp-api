CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockOverTime`(PROGRAM_ID INT(10), PLANNING_UNIT_ID INT(10), START_DATE VARCHAR(10), STOP_DATE VARCHAR(10), MOS_PAST INT(10), MOS_FUTURE INT(10))
BEGIN
	-- Only Month and Year will be considered for StartDate and StopDate
    -- mosPast indicates the number of months that we need to go into the past to calculate AMC
    -- mosFuture indicated the number of months that we need to go into the future to calculate AMC
    -- current month is always included in AMC
    SET @programId =  PROGRAM_ID;
	SET @versionId = -1;
	SET @planningUnitId= PLANNING_UNIT_ID;
	SET @startDate = START_DATE;
	SET @stopDate = STOP_DATE;
	SET @mosPast = MOS_PAST;
	SET @mosFuture = MOS_FUTURE;
	SET @includePlannedShipments=true;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
		mn.MONTH `DT`, 
		p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, 
		COUNT(s2.CONSUMPTION_QTY) `AMC_MONTH_COUNT`, AVG(s2.CONSUMPTION_QTY) `AMC`, s3.CONSUMPTION_QTY, s3.STOCK, s3.STOCK/AVG(s2.CONSUMPTION_QTY) `MOS`
    FROM mn 
    LEFT JOIN vw_program p ON p.PROGRAM_ID=@programId
    LEFT JOIN vw_planning_unit pu ON pu.PLANNING_UNIT_ID=@planningUnitId
	LEFT JOIN 
		(
		SELECT 
			sp.TRANS_DATE, sp.PLANNING_UNIT_ID, 
            SUM(IF(sp.ACTUAL, sp.ACTUAL_CONSUMPTION_QTY, sp.FORECASTED_CONSUMPTION_QTY)) `CONSUMPTION_QTY`
		FROM rm_supply_plan_batch_info sp 
		WHERE sp.PROGRAM_ID=@programId AND sp.VERSION_ID=@versionId AND sp.PLANNING_UNIT_ID=@planningUnitId AND sp.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @mosPast MONTH) AND ADDDATE(@stopDate, INTERVAL @mosFuture MONTH)
        GROUP BY sp.PLANNING_UNIT_ID, sp.TRANS_DATE
	) s2 ON s2.TRANS_DATE BETWEEN SUBDATE(mn.MONTH, INTERVAL @mosPast MONTH) AND ADDDATE(mn.MONTH, INTERVAL @mosFuture MONTH)
    LEFT JOIN 
		(
		SELECT 
			sp.TRANS_DATE, sp.PLANNING_UNIT_ID, 
            SUM(IF(sp.ACTUAL, sp.ACTUAL_CONSUMPTION_QTY, sp.FORECASTED_CONSUMPTION_QTY)) `CONSUMPTION_QTY`,
            SUM(IF(@includePlannedShipments, sp.FINAL_CLOSING_BALANCE, sp.FINAL_CLOSING_BALANCE_WPS)) `STOCK`
		FROM rm_supply_plan_batch_info sp 
		WHERE sp.PROGRAM_ID=@programId AND sp.VERSION_ID=@versionId AND sp.PLANNING_UNIT_ID=@planningUnitId AND sp.TRANS_DATE between @startDate AND @stopDate
        GROUP BY sp.PLANNING_UNIT_ID, sp.TRANS_DATE
	) s3 ON s3.TRANS_DATE = mn.MONTH
	WHERE mn.MONTH BETWEEN @startDate AND @stopDate
    GROUP BY mn.MONTH;
END
