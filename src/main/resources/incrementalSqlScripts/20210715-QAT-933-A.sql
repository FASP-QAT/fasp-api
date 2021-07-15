/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 15-Jul-2021
 */

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `shipmentDetailsFundingSource`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsFundingSource`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_FUNDING_SOURCE_IDS TEXT, VAR_BUDGET_IDS TEXT, VAR_REPORT_VIEW INT(10))
BEGIN
    
    
    
    
    
    
    
    
    
    
    
    
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @reportView = VAR_REPORT_VIEW;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @fundingSourceIds = VAR_FUNDING_SOURCE_IDS;
    SET @budgetIds = VAR_BUDGET_IDS;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SELECT 
    	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
    	COUNT(st.SHIPMENT_ID) `ORDER_COUNT`, 
    	IF(@reportView=1, SUM(st.SHIPMENT_QTY), SUM(st.SHIPMENT_QTY*pu.MULTIPLIER)) `QUANTITY`, 
    	SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST` 
    FROM 
    	( 
    	SELECT 
            s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD 
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
    LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID 
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID 
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID 
    WHERE 
    	st.ACTIVE AND st.ACCOUNT_FLAG  AND ppu.ACTIVE AND pu.ACTIVE 
    	AND st.SHIPMENT_STATUS_ID!=8 
    	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
	AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) 
        AND (LENGTH(@fundingSourceIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceIds))
        AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))
    GROUP BY st.FUNDING_SOURCE_ID;
    
END$$

DELIMITER ;