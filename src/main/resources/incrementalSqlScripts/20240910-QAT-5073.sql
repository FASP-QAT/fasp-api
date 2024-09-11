USE `fasp`;
DROP procedure IF EXISTS `annualShipmentCost`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`annualShipmentCost`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `annualShipmentCost`(VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PROCUREMENT_AGENT_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_FUNDING_SOURCE_IDS TEXT, VAR_SHIPMENT_STATUS_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REPORT_BASED_ON INT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 22 
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- reportBasedOn = 1 means the report will be run using startDate and stopDate based on Shipped Date
    -- reportBasedOn = 2 means the report will be run using startDate and stopDate based on Delivered date if available or Expected Delivery Date
    -- If ProcurementAgent has not been selected as yet in the Shipment, that Shipment will be excluded
    -- 
    -- 
    SET @programId = VAR_PROGRAM_ID;
    SET @startDate = CONCAT(VAR_START_DATE, ' 00:00:00');
    SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
    SET @reportBasedOn=VAR_REPORT_BASED_ON; -- 1 = Shipped and 2 = RECEIVED
    SET @versionId = VAR_VERSION_ID;
    DROP TABLE IF EXISTS `tmp_year`;
	CREATE TABLE `tmp_year` (`YR` INT(10) UNSIGNED NOT NULL,PRIMARY KEY (`YR`)) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @dtPtr = @startDate;
    SET @sql2 = "";
    SET @isFirst = TRUE;
    REPEAT
	INSERT INTO tmp_year VALUES (YEAR(@dtPtr));
        SET @sql2 = CONCAT(@sql2, ", SUM(IF(b1.SHIP_YR=",YEAR(@dtPtr),", (IFNULL(b1.PRODUCT_COST,0)+IFNULL(b1.FREIGHT_COST,0)), 0)) `YR-",YEAR(@dtPtr),"` ");
        SET @dtPtr = DATE_ADD(@dtPtr, INTERVAL 1 YEAR);
    UNTIL YEAR(@dtPtr)>YEAR(@stopDate) END REPEAT;
    
    SET @sql1 = "";
    SET @sql1 = CONCAT(@sql1, "	SELECT ");
    SET @sql1 = CONCAT(@sql1, "		b1.PROCUREMENT_AGENT_ID, b1.PROCUREMENT_AGENT_CODE, b1.PROCUREMENT_AGENT_LABEL_ID, b1.PROCUREMENT_AGENT_LABEL_EN, b1.PROCUREMENT_AGENT_LABEL_FR, b1.PROCUREMENT_AGENT_LABEL_SP, b1.PROCUREMENT_AGENT_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.FUNDING_SOURCE_ID, b1.FUNDING_SOURCE_CODE, b1.FUNDING_SOURCE_LABEL_ID, b1.FUNDING_SOURCE_LABEL_EN, b1.FUNDING_SOURCE_LABEL_FR, b1.FUNDING_SOURCE_LABEL_SP, b1.FUNDING_SOURCE_LABEL_PR, ");
    SET @sql1 = CONCAT(@sql1, "		b1.PLANNING_UNIT_ID, b1.PLANNING_UNIT_LABEL_ID, b1.PLANNING_UNIT_LABEL_EN, b1.PLANNING_UNIT_LABEL_FR, b1.PLANNING_UNIT_LABEL_SP, b1.PLANNING_UNIT_LABEL_PR ");
    SET @sql1 = CONCAT(@sql1, "		",@sql2);
    SET @sql1 = CONCAT(@sql1, " FROM tmp_year y ");
    SET @sql1 = CONCAT(@sql1, " LEFT JOIN ( ");
    SET @sql1 = CONCAT(@sql1, " 	SELECT ");
    IF @reportBasedOn=1 THEN 
	SET @sql1 = CONCAT(@sql1, " 	YEAR(st.PLANNED_DATE) SHIP_YR, ");
    ELSE
	SET @sql1 = CONCAT(@sql1, " 	YEAR(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE)) SHIP_YR, ");
    END IF;
    SET @sql1 = CONCAT(@sql1, " 		pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sql1 = CONCAT(@sql1, " 		st.PRODUCT_COST * s.CONVERSION_RATE_TO_USD `PRODUCT_COST`, st.FREIGHT_COST * s.CONVERSION_RATE_TO_USD `FREIGHT_COST` ");
    SET @sql1 = CONCAT(@sql1, " 	FROM ");
    SET @sql1 = CONCAT(@sql1, "			(");
    SET @sql1 = CONCAT(@sql1, "				SELECT ");
    SET @sql1 = CONCAT(@sql1, "					s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
    SET @sql1 = CONCAT(@sql1, "				FROM rm_shipment s ");
    SET @sql1 = CONCAT(@sql1, "				LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, "				WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
    SET @sql1 = CONCAT(@sql1, "				GROUP BY s.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, "		) s ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_shipment_trans  st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_funding_source fs on fs.FUNDING_SOURCE_ID=st.FUNDING_SOURCE_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_procurement_agent pa on pa.PROCUREMENT_AGENT_ID=st.PROCUREMENT_AGENT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN vw_planning_unit pu on pu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID ");
    SET @sql1 = CONCAT(@sql1, " 	LEFT JOIN rm_program_planning_unit ppu on ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sql1 = CONCAT(@sql1, " 	WHERE ");
    SET @sql1 = CONCAT(@sql1, " 		st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sql1 = CONCAT(@sql1, " 		AND (st.SHIPMENT_STATUS_ID != 8) ");
    SET @sql1 = CONCAT(@sql1, " 		AND (@programId = -1 OR s.PROGRAM_ID = @programId) ");
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    IF LENGTH(VAR_SHIPMENT_STATUS_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND st.SHIPMENT_STATUS_ID IN (",VAR_SHIPMENT_STATUS_IDS,") ");
    END IF;
    
    -- If you want on Order Date change here
    IF @reportBasedOn=1 THEN 
	SET @sql1 = CONCAT(@sql1, " 	AND st.PLANNED_DATE BETWEEN @startDate AND @stopDate ");
    ELSE
	SET @sql1 = CONCAT(@sql1, " 	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    END IF;
    IF LENGTH(VAR_FUNDING_SOURCE_IDS) > 0 THEN
        SET @sql1 = CONCAT(@sql1, " 		AND b.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")   ");
    END IF;
    
    SET @sql1 = CONCAT(@sql1, " 	GROUP BY s.SHIPMENT_ID ");
    SET @sql1 = CONCAT(@sql1, " ) as b1 ON y.YR=b1.SHIP_YR ");
    SET @sql1 = CONCAT(@sql1, " GROUP BY b1.PROCUREMENT_AGENT_ID, b1.FUNDING_SOURCE_ID, b1.PLANNING_UNIT_ID ");
    SET @sql1 = CONCAT(@sql1, " HAVING PROCUREMENT_AGENT_ID IS NOT NULL ");
    -- select @sql1;
    PREPARE s1 FROM @sql1;
    EXECUTE s1;
END$$

DELIMITER ;
;

