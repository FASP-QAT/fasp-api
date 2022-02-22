
CREATE TABLE `log` (
  `LOG_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `UPDATED_DATE` datetime NOT NULL,
  `DESC` varchar(200) NOT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `rm_erp_order_consolidated` (
  `ERP_ORDER_ID` int unsigned NOT NULL,
  `RO_NO` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `RO_PRIME_LINE_NO` int unsigned NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIME_LINE_NO` int DEFAULT NULL,
  `ORDER_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `PARENT_RO` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PARENT_CREATED_DATE` datetime DEFAULT NULL,
  `PLANNING_UNIT_SKU_CODE` varchar(13) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `QTY` bigint unsigned NOT NULL,
  `ORDERD_DATE` date DEFAULT NULL,
  `CURRENT_ESTIMATED_DELIVERY_DATE` date DEFAULT NULL,
  `REQ_DELIVERY_DATE` date DEFAULT NULL,
  `AGREED_DELIVERY_DATE` date DEFAULT NULL,
  `SUPPLIER_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRICE` decimal(14,4) NOT NULL,
  `SHIPPING_COST` decimal(14,4) DEFAULT NULL,
  `SHIP_BY` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RECPIENT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RECPIENT_COUNTRY` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `VERSION_ID` int DEFAULT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_ID` int unsigned DEFAULT NULL,
  `FILE_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`ERP_ORDER_ID`),
  UNIQUE KEY `unq_erp_order_consolidated_orderNo_primeLineNo` (`ORDER_NO`,`PRIME_LINE_NO`),
  KEY `idx_erp_order_consolidated_orderNo` (`ORDER_NO`),
  KEY `idx_erp_order_consolidated_primeLineNo` (`PRIME_LINE_NO`),
  KEY `idx_erp_order_consolidated_planningUnitSkuCode` (`PLANNING_UNIT_SKU_CODE`),
  KEY `idx_erp_order_consolidated_procurementUnitSkuCode` (`PROCUREMENT_UNIT_SKU_CODE`),
  KEY `idx_erp_order_consolidated_recipientCountry` (`RECPIENT_COUNTRY`),
  KEY `idx_erp_order_consolidated_status` (`STATUS`),
  KEY `fk_erp_order_consolidated_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_erp_order_consolidated_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_erp_order_consolidated_shipmentId_idx` (`SHIPMENT_ID`),
  KEY `idx_erp_order_consolidated_fileName` (`FILE_NAME`),
  KEY `idx_erp_order_consolidated_active` (`ACTIVE`),
  CONSTRAINT `fk_erp_order_consolidated_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`),
  CONSTRAINT `fk_rm_erp_order_consolidated_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`),
  CONSTRAINT `fk_rm_erp_order_consolidated_shipmentId` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

CREATE TABLE `rm_erp_shipment_consolidated` (
  `ERP_SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ERP_ORDER_ID` int unsigned DEFAULT NULL,
  `KN_SHIPMENT_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PRIME_LINE_NO` int NOT NULL,
  `BATCH_NO` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXPIRY_DATE` date DEFAULT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SHIPPED_QTY` int DEFAULT NULL,
  `DELIVERED_QTY` bigint DEFAULT NULL,
  `ACTUAL_SHIPMENT_DATE` date DEFAULT NULL,
  `ACTUAL_DELIVERY_DATE` date DEFAULT NULL,
  `ARRIVAL_AT_DESTINATION_DATE` date DEFAULT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `FILE_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`ERP_SHIPMENT_ID`),
  UNIQUE KEY `index9` (`KN_SHIPMENT_NO`,`ORDER_NO`,`PRIME_LINE_NO`,`BATCH_NO`),
  KEY `idx_erp_shipment_consolidated_active` (`ACTIVE`),
  KEY `idx_erp_shipment_consolidated_orderNo` (`ORDER_NO`),
  KEY `idx_erp_shipment_consolidated_primeLineNo` (`PRIME_LINE_NO`),
  KEY `idx_erp_shipment_consolidated_knShipmentNo` (`KN_SHIPMENT_NO`),
  KEY `idx_erp_shipment_consolidated_batchNo` (`BATCH_NO`),
  KEY `idx_erp_shipment_consolidated_erpOrderId_idx` (`ERP_ORDER_ID`),
  KEY `idx_erp_shipment_consolidated_fileName` (`FILE_NAME`),
  CONSTRAINT `fk_rm_erp_shipment_consolidated_erpOrderId` FOREIGN KEY (`ERP_ORDER_ID`) REFERENCES `rm_erp_order_consolidated` (`ERP_ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=146290 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;


USE `fasp`;
DROP procedure IF EXISTS `buildErpOrder`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildErpOrder`(VAR_DT VARCHAR(10))
BEGIN
	DECLARE VAR_FINISHED INTEGER DEFAULT 0;
	DECLARE VAR_FILE_NAME varchar(45) DEFAULT "";

	
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
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `buildErpShipment`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `buildErpShipment`(VAR_DT VARCHAR(10))
BEGIN
	DECLARE VAR_FINISHED INTEGER DEFAULT 0;
	DECLARE VAR_FILE_NAME varchar(45) DEFAULT "";

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
        UPDATE 
			rm_erp_shipment_consolidated eoc 
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
END$$

DELIMITER ;
