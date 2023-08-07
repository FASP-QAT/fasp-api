CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildErpOrder`(VAR_DT VARCHAR(10))
BEGIN
	DECLARE VAR_FINISHED INTEGER DEFAULT 0;
	DECLARE VAR_FILE_NAME varchar(45) DEFAULT "";

        -- Change Code 1 = Create
        -- Change Code 2 = Delete
        -- Change Code 3 = Update
	
	DEClARE curErpOrder CURSOR FOR 
            SELECT DISTINCT eo.FILE_NAME FROM rm_erp_order eo WHERE eo.FILE_NAME!='QATDefault' AND eo.FILE_NAME>=CONCAT('order_data_',VAR_DT,'.xml') group by eo.FILE_NAME;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET VAR_FINISHED = 1;
	OPEN curErpOrder;
        INSERT INTO log VALUES (null, now(), "Starting buildErpOrder");
        getFileName: LOOP
            FETCH curErpOrder INTO VAR_FILE_NAME;
            IF VAR_FINISHED = 1 THEN 
		LEAVE getFileName;
            END IF;
            INSERT INTO log VALUES (null, now(), CONCAT("Starting loop for ",VAR_FILE_NAME));
            -- Do work
            -- First complete all Inserts
            INSERT IGNORE INTO rm_erp_order_consolidated 
		(`ERP_ORDER_ID`, `RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, 
                `ORDER_TYPE`, `CREATED_DATE`, `PARENT_RO`, `PARENT_CREATED_DATE`, `PLANNING_UNIT_SKU_CODE`,
                `PROCUREMENT_UNIT_SKU_CODE`, `QTY`, `ORDERD_DATE`, `CURRENT_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`,
                `AGREED_DELIVERY_DATE`, `SUPPLIER_NAME`, `PRICE`, `SHIPPING_COST`, `SHIP_BY`, 
                `RECPIENT_NAME`, `RECPIENT_COUNTRY`, `STATUS`, `CHANGE_CODE`, `PROCUREMENT_AGENT_ID`, 
                `LAST_MODIFIED_DATE`, `FILE_NAME`, `ACTIVE`)
            SELECT 
		`ERP_ORDER_ID`, `RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, 
		`ORDER_TYPE`, `CREATED_DATE`, `PARENT_RO`, `PARENT_CREATED_DATE`, `PLANNING_UNIT_SKU_CODE`,
		`PROCUREMENT_UNIT_SKU_CODE`, `QTY`, `ORDERD_DATE`, `CURRENT_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`,
		`AGREED_DELIVERY_DATE`, `SUPPLIER_NAME`, `PRICE`, `SHIPPING_COST`, `SHIP_BY`, 
		`RECPIENT_NAME`, `RECPIENT_COUNTRY`, `STATUS`, `CHANGE_CODE`, `PROCUREMENT_AGENT_ID`, 
		`LAST_MODIFIED_DATE`, `FILE_NAME`, 1 
            FROM rm_erp_order WHERE FILE_NAME=VAR_FILE_NAME;
            
            INSERT INTO log VALUES (null, now(), CONCAT(ROW_COUNT(), "-Inserts done"));
            -- Now Update all the Updates
            UPDATE 
                rm_erp_order_consolidated eoc 
            LEFT JOIN (SELECT eo2.* FROM (SELECT MAX(ERP_ORDER_ID) `ERP_ORDER_ID` FROM rm_erp_order WHERE FILE_NAME=VAR_FILE_NAME group by ORDER_NO, PRIME_LINE_NO) AS eo1 LEFT JOIN rm_erp_order eo2 ON eo1.ERP_ORDER_ID=eo2.ERP_ORDER_ID) AS eo ON eo.ORDER_NO=eoc.ORDER_NO and eo.PRIME_LINE_NO=eoc.PRIME_LINE_NO
            SET 
		eoc.`RO_NO`=eo.`RO_NO`, 
                eoc.`RO_PRIME_LINE_NO`=eo.`RO_PRIME_LINE_NO`, 
                eoc.`ORDER_NO`=eo.`ORDER_NO`, 
                eoc.`PRIME_LINE_NO`=eo.`PRIME_LINE_NO`, 
		eoc.`ORDER_TYPE`=eo.`ORDER_TYPE`, 
                eoc.`CREATED_DATE`=eo.`CREATED_DATE`, 
                eoc.`PARENT_RO`=eo.`PARENT_RO`, 
                eoc.`PARENT_CREATED_DATE`=eo.`PARENT_CREATED_DATE`, 
                eoc.`PLANNING_UNIT_SKU_CODE`=eo.`PLANNING_UNIT_SKU_CODE`,
                eoc.`PROCUREMENT_UNIT_SKU_CODE`=eo.`PROCUREMENT_UNIT_SKU_CODE`, 
                eoc.`QTY`=eo.`QTY`, 
                eoc.`ORDERD_DATE`=eo.`ORDERD_DATE`, 
                eoc.`CURRENT_ESTIMATED_DELIVERY_DATE`=eo.`CURRENT_ESTIMATED_DELIVERY_DATE`, 
                eoc.`REQ_DELIVERY_DATE`=eo.`REQ_DELIVERY_DATE`,
                eoc.`AGREED_DELIVERY_DATE`=eo.`AGREED_DELIVERY_DATE`, 
                eoc.`SUPPLIER_NAME`=eo.`SUPPLIER_NAME`, 
                eoc.`PRICE`=eo.`PRICE`, 
                eoc.`SHIPPING_COST`=eo.`SHIPPING_COST`, 
                eoc.`SHIP_BY`=eo.`SHIP_BY`, 
                eoc.`RECPIENT_NAME`=eo.`RECPIENT_NAME`, 
                eoc.`RECPIENT_COUNTRY`=eo.`RECPIENT_COUNTRY`, 
                eoc.`STATUS`=eo.`STATUS`, 
                eoc.`CHANGE_CODE`=eo.`CHANGE_CODE`, 
                eoc.`PROCUREMENT_AGENT_ID`=eo.`PROCUREMENT_AGENT_ID`, 
                eoc.`LAST_MODIFIED_DATE`=eo.`LAST_MODIFIED_DATE`, 
                eoc.`FILE_NAME`=eo.`FILE_NAME`,
                eoc.ACTIVE=IF(eo.`CHANGE_CODE`=2,0,1)
            WHERE eo.ERP_ORDER_ID IS NOT NULL;
            
            INSERT INTO log VALUES (null, now(), CONCAT(ROW_COUNT(), "-Updates and Deletes done"));
	END LOOP getFileName;
END