CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsMonth`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 c
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
    SET @sqlString = CONCAT(@sqlString, "   mn.MONTH, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`PLANNED_COST`,0)) `PLANNED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SUBMITTED_COST`,0)) `SUBMITTED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`APPROVED_COST`,0)) `APPROVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SHIPPED_COST`,0)) `SHIPPED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ARRIVED_COST`,0)) `ARRIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`RECEIVED_COST`,0)) `RECEIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ONHOLD_COST`,0)) `ONHOLD_COST` ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "   ( ");
    SET @sqlString = CONCAT(@sqlString, "   SELECT ");
    SET @sqlString = CONCAT(@sqlString, "       CONCAT(LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7),'-01') `DT`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=1, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `PLANNED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=3, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SUBMITTED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=4, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `APPROVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=5, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SHIPPED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=6, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ARRIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=7, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `RECEIVED_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=8, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ONHOLD_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "       s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    FROM ");
    SET @sqlString = CONCAT(@sqlString, "        ( ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT ");
    SET @sqlString = CONCAT(@sqlString, "            s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "        FROM rm_shipment s ");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            s.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "            AND st.VERSION_ID<=@versionId ");
    SET @sqlString = CONCAT(@sqlString, "            AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    ) AS s ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE ");
    SET @sqlString = CONCAT(@sqlString, "        st.ACTIVE  AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "        AND st.SHIPMENT_STATUS_ID!=8 ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS) > 0 THEN
		SET @sqlString = CONCAT(@sqlString, "	    AND (st.PLANNING_UNIT_ID in (",VAR_PLANNING_UNIT_IDS,")) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "        AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, ") AS s1 ON mn.MONTH =s1.DT ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY mn.MONTH");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END
