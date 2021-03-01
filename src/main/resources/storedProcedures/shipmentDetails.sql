CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `shipmentDetails`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate
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
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	pu.MULTIPLIER, ");
    SET @sqlString = CONCAT(@sqlString, "	s.SHIPMENT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	st.SHIPMENT_QTY, st.ORDER_NO, st.LOCAL_PROCUREMENT, st.ERP_FLAG, st.EMERGENCY_ORDER, ");
    SET @sqlString = CONCAT(@sqlString, "	COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	(IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD + IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `TOTAL_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "	st.NOTES ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "	( ");
    SET @sqlString = CONCAT(@sqlString, "	SELECT ");
    SET @sqlString = CONCAT(@sqlString, "		s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "	FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "		s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "		AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString, "		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "	GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS s ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa on st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "	st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE AND st.SHIPMENT_STATUS_ID!=8 ");
    SET @sqlString = CONCAT(@sqlString, "	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
	SET @sqlString = CONCAT(@sqlString, "	AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY s.SHIPMENT_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END
