CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildErpShipment`(VAR_DT VARCHAR(10))
BEGIN
	DECLARE VAR_FINISHED INTEGER DEFAULT 0;
	DECLARE VAR_FILE_NAME varchar(45) DEFAULT "";

        -- Change Code 1 = Create
        -- Change Code 2 = Delete
        -- Change Code 3 = Update

	DEClARE curErpShipment CURSOR FOR 
            SELECT DISTINCT eo.FILE_NAME FROM rm_erp_shipment eo WHERE eo.FILE_NAME!='QATDefault' AND eo.FILE_NAME>=CONCAT('shipment_data_',VAR_DT,'.xml') group by eo.FILE_NAME;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET VAR_FINISHED = 1;
	OPEN curErpShipment;
    INSERT INTO log VALUES (null, now(), "Starting buildErpShipment");
    getFileName: LOOP
	FETCH curErpShipment INTO VAR_FILE_NAME;
	IF VAR_FINISHED = 1 THEN 
            LEAVE getFileName;
	END IF;
        
        INSERT INTO log VALUES (null, now(), CONCAT("Starting loop for ",VAR_FILE_NAME));
	-- Do work
        -- First complete all Inserts
        INSERT IGNORE INTO rm_erp_shipment_consolidated 
            (`ERP_SHIPMENT_ID`, `ERP_ORDER_ID`, `KN_SHIPMENT_NO`, `ORDER_NO`, `PRIME_LINE_NO`, 
            `BATCH_NO`, `EXPIRY_DATE`, `PROCUREMENT_UNIT_SKU_CODE`, `SHIPPED_QTY`, `DELIVERED_QTY`, 
            `ACTUAL_SHIPMENT_DATE`, `ACTUAL_DELIVERY_DATE`, `ARRIVAL_AT_DESTINATION_DATE`, `STATUS`, `CHANGE_CODE`, 
            `LAST_MODIFIED_DATE`, `FILE_NAME`, `ACTIVE`)
	SELECT 
            es.`ERP_SHIPMENT_ID`, eoc.`ERP_ORDER_ID`, es.`KN_SHIPMENT_NO`, es.`ORDER_NO`, es.`PRIME_LINE_NO`, 
            es.`BATCH_NO`, es.`EXPIRY_DATE`, es.`PROCUREMENT_UNIT_SKU_CODE`, es.`SHIPPED_QTY`, es.`DELIVERED_QTY`, 
            es.`ACTUAL_SHIPMENT_DATE`, es.`ACTUAL_DELIVERY_DATE`, es.`ARRIVAL_AT_DESTINATION_DATE`, es.`STATUS`, es.`CHANGE_CODE`, 
            es.`LAST_MODIFIED_DATE`, es.`FILE_NAME`, 1
	FROM rm_erp_shipment es LEFT JOIN rm_erp_order_consolidated eoc ON es.ORDER_NO=eoc.ORDER_NO and es.PRIME_LINE_NO=eoc.PRIME_LINE_NO WHERE es.FILE_NAME=VAR_FILE_NAME;

	INSERT INTO log VALUES (null, now(), CONCAT(ROW_COUNT(), "-Inserts done"));
        -- Now Update all the Updates
        UPDATE rm_erp_shipment_consolidated eoc 
        LEFT JOIN (SELECT eo2.* FROM (SELECT MAX(ERP_SHIPMENT_ID) `ERP_SHIPMENT_ID` FROM rm_erp_shipment WHERE FILE_NAME=VAR_FILE_NAME group by ORDER_NO, PRIME_LINE_NO, KN_SHIPMENT_NO, BATCH_NO) AS eo1 LEFT JOIN rm_erp_shipment eo2 ON eo1.ERP_SHIPMENT_ID=eo2.ERP_SHIPMENT_ID) AS eo ON eo.ORDER_NO=eoc.ORDER_NO and eo.PRIME_LINE_NO=eoc.PRIME_LINE_NO and eo.KN_SHIPMENT_NO=eoc.KN_SHIPMENT_NO and eo.BATCH_NO=eoc.BATCH_NO
            SET 
            eoc.`EXPIRY_DATE`=eo.`EXPIRY_DATE`,
            eoc.`PROCUREMENT_UNIT_SKU_CODE`=eo.`PROCUREMENT_UNIT_SKU_CODE`, 
            eoc.`SHIPPED_QTY`=eo.`SHIPPED_QTY`, 
            eoc.`DELIVERED_QTY`=eo.`DELIVERED_QTY`,
            eoc.`ACTUAL_SHIPMENT_DATE`=eo.`ACTUAL_SHIPMENT_DATE`, 
            eoc.`ACTUAL_DELIVERY_DATE`=eo.`ACTUAL_DELIVERY_DATE`, 
            eoc.`ARRIVAL_AT_DESTINATION_DATE`=eo.`ARRIVAL_AT_DESTINATION_DATE`, 
            eoc.`STATUS`=eo.`STATUS`, 
            eoc.`CHANGE_CODE`=eo.`CHANGE_CODE`, 
            eoc.`LAST_MODIFIED_DATE`=eo.`LAST_MODIFIED_DATE`, 
            eoc.`FILE_NAME`=eo.`FILE_NAME`, 
            eoc.`ACTIVE`=IF(eo.`CHANGE_CODE`=2,0,1)
            WHERE eo.ERP_SHIPMENT_ID IS NOT NULL;
        INSERT INTO log VALUES (null, now(), CONCAT(ROW_COUNT(), "-Updates and Deletes done"));
	END LOOP getFileName;
END
