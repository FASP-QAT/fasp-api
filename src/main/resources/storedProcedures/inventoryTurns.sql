CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `inventoryTurns`(VAR_START_DATE DATE, VAR_VIEW_BY INT, VAR_PROGRAM_IDS TEXT, VAR_PRODUCT_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS BOOLEAN)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 9
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- StartDate is the date that you want to run the report for
    -- ViewBy = 1 View by RealmCountry, ViewBy = 2 View by ProductCategory
    -- ProgramIds is the list of ProgramIds that should be included in the final output, cannot be empty you must pass the ProgramIds that you want to view it by
    -- ProductCategoryIds is the list of ProductCategoryIds that should be included in the final output, cannot be empty if you want to select all pass '0'
    -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
    -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
    -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period

    DECLARE curProgramId INTEGER DEFAULT 0;
    DECLARE curVersionId INTEGER DEFAULT 0;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE program_cursor CURSOR FOR 
        SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID
           FROM vw_program p 
           LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2
           WHERE FIND_IN_SET(p.PROGRAM_ID, VAR_PROGRAM_IDS) AND p.ACTIVE 
           GROUP BY p.PROGRAM_ID
           HAVING MAX(pv.VERSION_ID) IS NOT NULL;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    SET @startDate = VAR_START_DATE;
    SET @viewBy = VAR_VIEW_BY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
	SELECT GROUP_CONCAT(pc.PRODUCT_CATEGORY_ID) INTO @finalProductCategoryIds FROM rm_product_category pc LEFT JOIN (SELECT CONCAT(pc1.SORT_ORDER,'%') `SO` FROM rm_product_category pc1 WHERE FIND_IN_SET(pc1.PRODUCT_CATEGORY_ID, VAR_PRODUCT_CATEGORY_IDS)) pc2 ON pc.SORT_ORDER LIKE pc2.SO WHERE pc2.SO IS NOT NULL;
    
	DROP TABLE IF EXISTS tmp_inventory_turns;
	CREATE TEMPORARY TABLE `fasp`.`tmp_inventory_turns` (
		`PROGRAM_ID` INT NOT NULL,
        `PLANNING_UNIT_ID` INT NOT NULL,
        `TOTAL_CONSUMPTION` DOUBLE(16,2) NULL,
        `AVG_STOCK` DOUBLE(16,2) NULL,
        `NO_OF_MONTHS` INT NULL,
        PRIMARY KEY (`PROGRAM_ID`, `PLANNING_UNIT_ID`)
    );
    OPEN program_cursor;
    nextProgramVersionLoop: LOOP FETCH program_cursor INTO curProgramId, curVersionId;
	IF finished = 1 THEN 
		LEAVE nextProgramVersionLoop;
	END IF;
    INSERT INTO log VALUES (null, now(), CONCAT("programId:",curProgramId, ", versionId:",curVersionId));
	INSERT INTO tmp_inventory_turns 
		SELECT 
			ppu.PROGRAM_ID,
            ppu.PLANNING_UNIT_ID, 
            SUM(s2.CONSUMPTION_QTY) `TOTAL_CONSUMPTION`, 
            AVG(s2.STOCK) `AVG_STOCK`,
            COUNT(s2.CONSUMPTION_QTY) `NO_OF_MONTHS`
        FROM rm_program_planning_unit ppu 
        LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN 
			(
            SELECT 
				spa.TRANS_DATE, spa.PLANNING_UNIT_ID, 
                SUM(IF(spa.ACTUAL IS NULL, NULL, IF(spa.ACTUAL=1, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY))) `CONSUMPTION_QTY`,
                SUM(IF(@includePlannedShipments, spa.CLOSING_BALANCE, spa.CLOSING_BALANCE_WPS)) `STOCK`
			FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=curProgramId AND spa.VERSION_ID=curVersionId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL 11 MONTH) AND @startDate
            GROUP BY spa.TRANS_DATE, spa.PLANNING_UNIT_ID
            HAVING `CONSUMPTION_QTY` IS NOT NULL
		) s2 ON ppu.PLANNING_UNIT_ID=s2.PLANNING_UNIT_ID
        WHERE ppu.PROGRAM_ID=curProgramId AND ppu.ACTIVE AND pu.ACTIVE AND FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds)
        GROUP BY ppu.PLANNING_UNIT_ID;
	INSERT INTO log VALUES (null, now(), "Completed insert");
    END LOOP nextProgramVersionLoop;

    INSERT INTO log VALUES (null, now(), "Going to run main query");
    IF @viewBy = 1 THEN
		INSERT INTO log VALUES (null, now(), "View 1");
        SELECT 
            rc.REALM_COUNTRY_ID, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, 
            it.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, 
            it.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
            pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, 
            it.TOTAL_CONSUMPTION, it.AVG_STOCK, it.NO_OF_MONTHS, it.TOTAL_CONSUMPTION/it.AVG_STOCK `INVENTORY_TURNS`,
            ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION, 144/(rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION+ppu.MIN_MONTHS_OF_STOCK*12) `PLANNED_INVENTORY_TURNS`
        FROM tmp_inventory_turns it 
        LEFT JOIN vw_program p ON it.PROGRAM_ID=p.PROGRAM_ID
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
        LEFT JOIN vw_planning_unit pu ON it.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
        LEFT JOIN ap_reorder_master rm ON ppu.REORDER_FREQUENCY_IN_MONTHS=rm.NO_OF_MONTHS_FOR_REORDER
        ORDER BY c.LABEL_EN, p.PROGRAM_CODE, pu.LABEL_EN
        ;
    ELSEIF @viewBy = 2 THEN
		INSERT INTO log VALUES (null, now(), "View 2");
        SELECT 
            rc.REALM_COUNTRY_ID, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, 
            it.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, 
            it.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
            pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, 
            it.TOTAL_CONSUMPTION, it.AVG_STOCK, it.NO_OF_MONTHS, it.TOTAL_CONSUMPTION/it.AVG_STOCK `INVENTORY_TURNS`,
            ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION, 144/(rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION+ppu.MIN_MONTHS_OF_STOCK*12) `PLANNED_INVENTORY_TURNS`
        FROM tmp_inventory_turns it 
        LEFT JOIN vw_program p ON it.PROGRAM_ID=p.PROGRAM_ID
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
        LEFT JOIN vw_planning_unit pu ON it.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
        LEFT JOIN ap_reorder_master rm ON ppu.REORDER_FREQUENCY_IN_MONTHS=rm.NO_OF_MONTHS_FOR_REORDER
        ORDER BY pc.LABEL_EN, p.PROGRAM_CODE, pu.LABEL_EN
        ;
    END IF;
END