USE `fasp`;
DROP procedure IF EXISTS `getShipmentBudgetAmtData`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getShipmentBudgetAmtData`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentBudgetAmtData`(PROGRAM_ID INT(10), VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @sql1 = "";	
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SELECT
        s.SHIPMENT_ID, (st.PRODUCT_COST+st.FREIGHT_COST) `SHIPMENT_AMT`, s.CURRENCY_ID, s.CONVERSION_RATE_TO_USD, st.BUDGET_ID
    FROM (
        SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE (@versionId=-1 OR st.VERSION_ID<=@versionId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
    ) ts 
    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID
    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=s.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID
    WHERE st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID!=8 AND ppu.ACTIVE;
END$$

DELIMITER ;
;

