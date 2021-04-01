CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `buildNewSupplyPlanRegionOld`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_REBUILD TINYINT(1))
BEGIN
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @rebuild = VAR_REBUILD;
    SELECT COUNT(*) INTO @currentCount FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId;
    -- Get the Region count for this Program
    SELECT count(*) INTO @regionCount FROM rm_program_region pr WHERE pr.PROGRAM_ID=@programId;
    
    IF @rebuild = 1 OR @currentCount = 0 THEN
    --    DELETE spbi.* FROM rm_supply_plan_batch_info spbi WHERE spbi.PROGRAM_ID=@programId AND spbi.VERSION_ID=@versionId;
        DELETE nspr.* FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId;
       
        -- Populate the nsp_region table with all the raw data that we have for Consumption, Inventory and Shipment per Region
        INSERT INTO rm_nsp_region (
            PROGRAM_ID, VERSION_ID, PLANNING_UNIT_ID, TRANS_DATE, REGION_ID, REGION_COUNT, 
            FORECASTED_CONSUMPTION, ACTUAL_CONSUMPTION, SHIPMENT, ADJUSTMENT, STOCK
        )
        SELECT 
            o.PROGRAM_ID, @versionId, o.PLANNING_UNIT_ID, DATE(CONCAT(o.TRANS_DATE,"-01")) `TRANS_DATE`, o.REGION_ID, @regionCount,
            SUM(o.FORECASTED_CONSUMPTION) `FORECASTED_CONSUMPTION`, SUM(o.ACTUAL_CONSUMPTION) `ACTUAL_CONSUMPTION`, 
            SUM(o.SHIPMENT) `SHIPMENT`, SUM(o.ADJUSTMENT) `ADJUSTMENT`, SUM(o.STOCK) `STOCK` 
        FROM (
            SELECT 
                tc.PROGRAM_ID, tc.CONSUMPTION_ID `TRANS_ID`, tc.PLANNING_UNIT_ID, LEFT(tc.CONSUMPTION_DATE, 7) `TRANS_DATE`, tc.REGION_ID, 
                SUM(tc.`FORECASTED_CONSUMPTION`) `FORECASTED_CONSUMPTION`, SUM(tc.`ACTUAL_CONSUMPTION`) `ACTUAL_CONSUMPTION`, 
                null `SHIPMENT`, null `ADJUSTMENT`, null  `STOCK` 
            FROM (
                SELECT 
                    c.PROGRAM_ID, c.CONSUMPTION_ID, ct.REGION_ID, ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, 
                    ct.ACTIVE, 
                    SUM(IF(ct.ACTUAL_FLAG=1, ct.CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION`, 
                    SUM(IF(ct.ACTUAL_FLAG=0, ct.CONSUMPTION_QTY, null)) `FORECASTED_CONSUMPTION`
                FROM (
                    SELECT c.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId AND ct.VERSION_ID<=@versionId AND ct.CONSUMPTION_TRANS_ID IS NOT NULL GROUP BY c.CONSUMPTION_ID
                ) tc
                LEFT JOIN rm_consumption c ON c.CONSUMPTION_ID=tc.CONSUMPTION_ID
                LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
                WHERE ct.ACTIVE
                GROUP BY c.PROGRAM_ID, ct.PLANNING_UNIT_ID, ct.CONSUMPTION_DATE, ct.REGION_ID
            ) tc 
            GROUP BY tc.PROGRAM_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.REGION_ID

            UNION

            SELECT 
                s.PROGRAM_ID, s.SHIPMENT_ID `TRANS_ID`, st.PLANNING_UNIT_ID, LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7) `TRANS_DATE`, null `REGION_ID`, 
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, SUM(st.SHIPMENT_QTY) `SHIPMENT`, null `ADJUSTMENT`, null `STOCK`
            FROM (
                SELECT s.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID
            ) ts
            LEFT JOIN rm_shipment s ON s.SHIPMENT_ID=ts.SHIPMENT_ID
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
            WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 
            GROUP BY s.PROGRAM_ID, st.PLANNING_UNIT_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)

            UNION

            SELECT 
                i.PROGRAM_ID, i.INVENTORY_ID `TRANS_ID`, rcpu.PLANNING_UNIT_ID, LEFT(it.INVENTORY_DATE,7) `TRANS_DATE`, it.REGION_ID,
                null `FORECASTED_CONSUMPTION`, null `ACTUAL_CONSUMPTION`, null `SHIPMENT_QTY`, SUM(it.ADJUSTMENT_QTY*rcpu.MULTIPLIER) `ADJUSTMENT`,  SUM(it.ACTUAL_QTY*rcpu.MULTIPLIER) `STOCK`
            FROM (
                SELECT i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID
            ) ti
            LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID
            LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID
            LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID
            WHERE it.ACTIVE
            GROUP BY i.PROGRAM_ID, rcpu.PLANNING_UNIT_ID, it.INVENTORY_DATE, it.REGION_ID
        ) AS o GROUP BY o.PROGRAM_ID, o.PLANNING_UNIT_ID, o.TRANS_DATE, o.REGION_ID;
           
        -- Update the UseActualConsumption field = 1 
        -- IF All Regions have reported Consumption or if Sum(ActualConsumption)>Sum(ForecastedConsumption)
        -- ELSE UseActualConsumption field = 0
        UPDATE rm_nsp_region nspr LEFT JOIN (SELECT nspr.PLANNING_UNIT_ID, nspr.TRANS_DATE, SUM(IF(nspr.ACTUAL_CONSUMPTION IS NOT NULL, 1,0)) `COUNT_OF_ACTUAL_CONSUMPTION`, SUM(nspr.ACTUAL_CONSUMPTION) `TOTAL_ACTUAL_CONSUMPTION`, SUM(nspr.FORECASTED_CONSUMPTION) `TOTAL_FORECASTED_CONSUMPTION` FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId AND nspr.REGION_ID IS NOT NULL GROUP BY nspr.PLANNING_UNIT_ID, nspr.TRANS_DATE, nspr.REGION_ID) rcount ON nspr.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND nspr.TRANS_DATE=rcount.TRANS_DATE SET nspr.USE_ACTUAL_CONSUMPTION=IF(rcount.COUNT_OF_ACTUAL_CONSUMPTION=@regionCount, 1, IF(rcount.TOTAL_ACTUAL_CONSUMPTION>rcount.TOTAL_FORECASTED_CONSUMPTION, 1, 0)) WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId AND nspr.REGION_ID IS NOT NULL;
        
        -- Update the RegionStockCount field based on the number of Regions that have reported Stock
        UPDATE rm_nsp_region nspr LEFT JOIN (SELECT nspr.PLANNING_UNIT_ID, nspr.TRANS_DATE, COUNT(nspr.STOCK) CNT FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId AND nspr.REGION_ID IS NOT NULL GROUP BY nspr.PLANNING_UNIT_ID, nspr.TRANS_DATE, nspr.REGION_ID) rcount ON nspr.PLANNING_UNIT_ID=rcount.PLANNING_UNIT_ID AND nspr.TRANS_DATE=rcount.TRANS_DATE SET nspr.REGION_STOCK_COUNT = rcount.CNT WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId AND nspr.REGION_ID IS NOT NULL;
        
        -- To get the range for AMC calculations
        -- SELECT MIN(sp.TRANS_DATE), ADDDATE(MAX(sp.TRANS_DATE), INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH) INTO @startMonth, @stopMonth  FROM rm_supply_plan sp LEFT JOIN rm_program_planning_unit ppu ON sp.PROGRAM_ID=ppu.PROGRAM_ID AND sp.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID WHERE sp.PROGRAM_ID=@programId and sp.VERSION_ID=@versionId;
    END IF;
    
    SELECT 
        nspr.PLANNING_UNIT_ID, nspr.TRANS_DATE, nspr.REGION_ID, nspr.FORECASTED_CONSUMPTION, nspr.ACTUAL_CONSUMPTION,
        nspr.USE_ACTUAL_CONSUMPTION, nspr.SHIPMENT, nspr.ADJUSTMENT, nspr.STOCK, nspr.REGION_STOCK_COUNT, 
        nspr.REGION_COUNT
    FROM rm_nsp_region nspr WHERE nspr.PROGRAM_ID=@programId AND nspr.VERSION_ID=@versionId;

END
