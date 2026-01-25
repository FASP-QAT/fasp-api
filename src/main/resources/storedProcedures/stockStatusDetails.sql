CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_REMOVE_PLANNED_SHIPMENTS_THAT_FAIL_LEAD_TIME TINYINT(1), VAR_FUNDING_SOURCE_IDS TEXT, VAR_PROCUREMENT_AGENT_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18a Part 2
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- planningUnitId is the list of Planning Units that you want to include in the report
    -- empty means you want to see the report for all Planning Units
    -- startDate and stopDate are the period for which you want to run the report
    -- removePlannedShipments = 1 means that you want to remove the shipments that are in the Planned stage while running this report.
    -- removePlannedShipments = 0 means that you want to retain the shipments that are in the Planned stage while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 1 means that you want to remove the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- removePlannedShipmentsThatFailLeadTime = 0 means that you want to retain the shipments that are in the Planned stage and fail the Lead Time while running this report.
    -- fundingSourceIds are those specific FundingSources that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- procurementAgentIds are those specific ProcurementAgents that should be removed for the removePlannedShipmentsThatFailLeadTime flag; Empty means all should be removed
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    
    SET @varVersionId = VAR_VERSION_ID;
    IF @varVersionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;
   
    SELECT
        amc.TRANS_DATE, 
        amc.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR,
        IF(amc.ACTUAL, amc.ACTUAL_CONSUMPTION_QTY, amc.FORECASTED_CONSUMPTION_QTY) `CONSUMPTION_QTY`, amc.ACTUAL,
        amc.STOCK_MULTIPLIED_QTY, 
        ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_QTY `MIN_STOCK_QTY`, amc.MAX_STOCK_QTY, 
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.CLOSING_BALANCE_WPS, amc.CLOSING_BALANCE) `CLOSING_BALANCE`,
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, amc.MOS_WPS, amc.MOS) `MOS`, amc.AMC, 
        IF(VAR_REMOVE_PLANNED_SHIPMENTS, (amc.SHIPMENT_QTY - amc.MANUAL_PLANNED_SHIPMENT_QTY - amc.ERP_PLANNED_SHIPMENT_QTY), amc.SHIPMENT_QTY)  `SHIPPED_QTY`
    FROM rm_supply_plan_amc amc
    LEFT JOIN rm_program_planning_unit ppu ON amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.PROGRAM_ID=ppu.PROGRAM_ID
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  
    WHERE
        amc.PROGRAM_ID=VAR_PROGRAM_ID
        AND amc.VERSION_ID=@varVersionId
        AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
        AND ppu.ACTIVE AND pu.ACTIVE
        AND (VAR_PLANNING_UNIT_IDS='' OR FIND_IN_SET(amc.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS));
END