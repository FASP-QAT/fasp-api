CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `inventoryTurns`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 9
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId cannot be -1 (All) it must be a valid ProgramId
    -- versionId can be -1 or a valid VersionId for that Program. If it is -1 then the last committed Version is automatically taken.
    -- StartDate is the date that you want to run the report for
    -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
    -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
    -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period

	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @startDate = VAR_START_DATE;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
		ppu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
		SUM(s2.CONSUMPTION_QTY) `TOTAL_CONSUMPTION`, 
		AVG(s2.STOCK) `AVG_STOCK`,
		COUNT(s2.CONSUMPTION_QTY) `NO_OF_MONTHS`,
		SUM(s2.CONSUMPTION_QTY)/AVG(s2.STOCK) `INVENTORY_TURNS`
	FROM rm_program_planning_unit ppu 
	LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN 
        (
        SELECT 
            spa.TRANS_DATE, spa.PLANNING_UNIT_ID, 
            SUM(IF(spa.ACTUAL IS NULL, NULL, IF(spa.ACTUAL=1, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY))) `CONSUMPTION_QTY`,
            SUM(IF(@includePlannedShipments, spa.CLOSING_BALANCE, spa.CLOSING_BALANCE_WPS)) `STOCK`
        FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=@programId AND spa.VERSION_ID=@versionId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL 11 MONTH) AND @startDate
        GROUP BY spa.TRANS_DATE, spa.PLANNING_UNIT_ID
        HAVING `CONSUMPTION_QTY` IS NOT NULL
    ) s2 ON ppu.PLANNING_UNIT_ID=s2.PLANNING_UNIT_ID
	WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE 
	GROUP BY ppu.PLANNING_UNIT_ID;
END
