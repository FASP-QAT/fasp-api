CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `costOfInventory`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(11), VAR_DT DATE, INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 8
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	-- ProgramId cannot be -1 (All) must be a valid ProgramId
    -- Version Id can be -1 or a Valid Version Id. If it is -1 then the Most recent committed Version is automatically taken.
    -- Dt is the date that you want to run the report for
    -- Include Planned shipments = 1 means that Shipments that are in the Draft, Planned or Submitted stage will also be considered in the calculations
    -- Include Planned shipments = 0 means that Shipments that are in the Draft, Planned or Submitted stage will not be considered in the calculations
    -- Price per unit is taken from the ProgramPlanningUnit level
    -- Cost = Closing inventory for that Planning Unit x Catalog Price
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @startDate = VAR_DT;
    SET @includePlannedShipments = INCLUDE_PLANNED_SHIPMENTS;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
        ppu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
        ppu.CATALOG_PRICE, IFNULL(IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),0) STOCK, 
        IFNULL(IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),0)*ppu.CATALOG_PRICE `COST`, ppu.CATALOG_PRICE, IF(amc.REGION_COUNT>amc.REGION_COUNT_FOR_STOCK,1,0) CALCULATED
    FROM rm_program_planning_unit ppu
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_supply_plan_amc amc ON ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND amc.PROGRAM_ID=@programId AND amc.VERSION_ID=@versionId AND amc.TRANS_DATE=@startDate
    WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE;

END
