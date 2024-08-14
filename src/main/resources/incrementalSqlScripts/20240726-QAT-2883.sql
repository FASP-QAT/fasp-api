USE `fasp`;
DROP procedure IF EXISTS `fundingSourceShipmentReport`;


DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `fundingSourceShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 15
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- fundingSourceIds is a list of the FundingSources that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fundingSourceIds = VAR_FUNDING_SOURCE_IDS;
    
    SELECT 
    	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
        fst.FUNDING_SOURCE_TYPE_ID, fst.FUNDING_SOURCE_TYPE_CODE, fst.LABEL_ID `FST_LABEL_ID`, fst.LABEL_EN `FST_LABEL_EN`, fst.LABEL_FR `FST_LABEL_FR`, fst.LABEL_SP `FST_LABEL_SP`, fst.LABEL_PR `FST_LABEL_PR`, 
    	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, 
    	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` 
    FROM 
    	(
    	SELECT 
            s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID 
    	FROM rm_shipment s 
    	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
    	WHERE 
            s.PROGRAM_ID=@programId 
            AND st.VERSION_ID<=@versionId 
            AND st.SHIPMENT_TRANS_ID IS NOT NULL 
    	GROUP BY s.SHIPMENT_ID 
    ) AS s 
    LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID 
    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID 
    LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID 
    WHERE 
    	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE  AND st.ACCOUNT_FLAG 
    	AND st.SHIPMENT_STATUS_ID != 8 
    	AND ((@includePlannedShipments=0 AND st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) 
    	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
       	AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID,@fundingSourceIds)) 
        AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID, @planningUnitIds)) 
    GROUP BY st.FUNDING_SOURCE_ID, st.PLANNING_UNIT_ID;
    
END$$

DELIMITER ;