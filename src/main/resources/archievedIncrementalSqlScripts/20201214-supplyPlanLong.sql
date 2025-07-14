
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS adb_pipeline;
CREATE TABLE `adb_pipeline` (
  `PIPELINE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `FILE_NAME` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`PIPELINE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_commodityprice`;
CREATE TABLE `adb_commodityprice` ( 
`ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL, 
`SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL, 
`dtmEffective` DATETIME COLLATE utf8_bin DEFAULT NULL, 
`UnitPrice` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL, 
`dtmChanged` DATETIME COLLATE utf8_bin DEFAULT NULL, 
`User` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL, 
`Note` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL, 
`fUserDefined` BOOLEAN COLLATE utf8_bin DEFAULT NULL, 
`PIPELINE_ID` int(10) unsigned NOT NULL, 
KEY `fk_adb_commodityprice_1_idx` (`PIPELINE_ID`), 
CONSTRAINT `fk_adb_commodityprice_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_consumption`;
CREATE TABLE `adb_consumption` ( 
`ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL, 
`ConsStartYear` SMALLINT COLLATE utf8_bin DEFAULT NULL, 
`ConsStartMonth` SMALLINT COLLATE utf8_bin DEFAULT NULL, 
`ConsActualFlag` BOOLEAN COLLATE utf8_bin DEFAULT NULL, 
`ConsNumMonths` SMALLINT COLLATE utf8_bin DEFAULT NULL, 
`ConsAmount` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL, 
`ConsDataSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL, 
`ConsIflator` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL, 
`ConsNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL, 
`ConsDateChanged` DATETIME COLLATE utf8_bin DEFAULT NULL, 
`ConsID` INTEGER COLLATE utf8_bin DEFAULT NULL, 
`ConsDisplayNote` BOOLEAN COLLATE utf8_bin DEFAULT NULL, 
`Old_consumption` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL, 
`PIPELINE_ID` int(10) unsigned NOT NULL, 
KEY `fk_adb_consumption_1_idx` (`PIPELINE_ID`), 
CONSTRAINT `fk_adb_consumption_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_datasource`;
CREATE TABLE `adb_datasource` ( 
 `DataSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `DataSourceName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `DataSourceTypeID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_datasource_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_datasource_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_fundingsource`;
CREATE TABLE `adb_fundingsource` ( 
 `FundingSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `FundingSourceName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `FundingNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_fundingsource_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_fundingsource_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_inventory`;
CREATE TABLE `adb_inventory` ( 
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `Period` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `InvAmount` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `InvTransferFlag` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `InvNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `InvDateChanged` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ctrIndex` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `InvDisplayNote` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `InvDataSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `fImported` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `Old_Inventory` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_inventory_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_inventory_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_method`;
CREATE TABLE `adb_method` ( 
 `MethodID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `MethodName` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
 `CYPFactor` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `MethodNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `ParentID` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `CategoryID` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `fRollup` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_method_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_method_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_monthlystockarchive`;
CREATE TABLE `adb_monthlystockarchive` ( 
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `EOYBalance` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `StockYear` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `StockMonth` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_monthlystockarchive_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_monthlystockarchive_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_paste_errors`;
CREATE TABLE `adb_paste_errors` ( 
 `F1` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `F2` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `F3` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_paste_errors_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_paste_errors_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_product`;
CREATE TABLE `adb_product` ( 
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `ProductName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `ProductMinMonths` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `ProductMaxMonths` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `MethodID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `ProductActiveFlag` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `ProductActiveDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `DefaultCaseSize` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `ProductNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `ProdCMax` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `ProdCMin` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `ProdDesStock` SMALLINT COLLATE utf8_bin DEFAULT NULL,
 `txtInnovatorDrugName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `dblLowestUnitQty` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `txtLowestUnitMeasure` VARCHAR(25) COLLATE utf8_bin DEFAULT NULL,
 `txtSubstitutionList` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `fPermittedInCountry` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `memAvailabilityNotes` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `fAvailabilityStatus` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `fUserDefined` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `strImportSource` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `BUConversion` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `txtPreferenceNotes` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `lngAMCStart` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `lngAMCMonths` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `fAMCChanged` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `txtMigrationStatus` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `txtMigrationStatusDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `strType` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `OldProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `OldProductName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `lngBatch` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `OldMethodID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_product_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_product_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_productfreightcost`;
CREATE TABLE `adb_productfreightcost` ( 
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `FreightCost` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `dtmChanged` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_productfreightcost_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_productfreightcost_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_productsuppliercasesize`;
CREATE TABLE `adb_productsuppliercasesize` ( 
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `dtmEffective` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `intCaseSize` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `dtmChanged` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `User` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
 `Note` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_productsuppliercasesize_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_productsuppliercasesize_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_programinfo`;
CREATE TABLE `adb_programinfo` ( 
 `ProgramName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `DataDirectory` VARCHAR(250) COLLATE utf8_bin DEFAULT NULL,
 `Language` VARCHAR(3) COLLATE utf8_bin DEFAULT NULL,
 `DefaultLeadTimePlan` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `DefaultLeadTimeOrder` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `DefaultLeadTimeShip` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `DefaultShipCost` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `ProgramContact` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `Telephone` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `Fax` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `Email` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `CountryCode` VARCHAR(2) COLLATE utf8_bin DEFAULT NULL,
 `CountryName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `IsCurrent` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `Note` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `ProgramCode` VARCHAR(12) COLLATE utf8_bin DEFAULT NULL,
 `IsActive` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `StartSize` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `IsDefault` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `ArchiveDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ArchiveYear` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `ArchiveInclude` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_programinfo_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_programinfo_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_shipment`;
CREATE TABLE `adb_shipment` ( 
 `ShipmentID` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `ProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `ShipDataSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `ShipAmount` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `ShipPlannedDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ShipOrderedDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ShipShippedDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ShipReceivedDate` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ShipStatusCode` VARCHAR(1) COLLATE utf8_bin DEFAULT NULL,
 `ShipNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `ShipDateChanged` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `ShipFreightCost` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `ShipValue` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `ShipCaseLot` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `ShipDisplayNote` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `ShipPO` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `Old_Shipment` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `ShipFundingSourceID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_SHIPMENT_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_SHIPMENT_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_source`;
CREATE TABLE `adb_source` ( 
 `SupplierID` VARCHAR(10) COLLATE utf8_bin DEFAULT NULL,
 `SupplierName` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `SupplierLeadTimePlan` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `SupplierLeadTimeOrder` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `SupplierLeadTimeShip` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `SupplierNote` TEXT(16777216) COLLATE utf8_bin DEFAULT NULL,
 `Freight` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `DefaultSupplier` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_source_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_source_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_tblbe_version`;
CREATE TABLE `adb_tblbe_version` ( 
 `sBE_Version` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `dtmUpdated` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_tblbe_version_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_tblbe_version_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_tblimportproducts`;
CREATE TABLE `adb_tblimportproducts` ( 
 `strProductID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
 `strName` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
 `strDose` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
 `lngCYP` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `dtmExport` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `fProcessed` BOOLEAN COLLATE utf8_bin DEFAULT NULL,
 `lngID` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `strSource` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
 `strMapping` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_tblimportproducts_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_tblimportproducts_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `adb_tblimportrecords`;
CREATE TABLE `adb_tblimportrecords` ( 
 `strProductID` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL,
 `dtmPeriod` DATETIME COLLATE utf8_bin DEFAULT NULL,
 `lngconsumption` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `lngAdjustment` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `dblDataInterval` DOUBLE(24,4) COLLATE utf8_bin DEFAULT NULL,
 `lngParentID` INTEGER COLLATE utf8_bin DEFAULT NULL,
 `PIPELINE_ID` int(10) unsigned NOT NULL,
 KEY `fk_adb_tblimportrecords_1_idx` (`PIPELINE_ID`),
 CONSTRAINT `fk_adb_tblimportrecords_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_consumption`;
CREATE TABLE `qat_temp_consumption` (
  `QAT_TEMP_CONSUMPTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REGION_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CONSUMPTION_DATE` date DEFAULT NULL,
  `PLANNING_UNIT_ID` varchar(225) COLLATE utf8_bin DEFAULT NULL,
  `DAYS_OF_STOCK_OUT` int(11) DEFAULT NULL,
  `DATA_SOURCE_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `NOTES` text COLLATE utf8_bin,
  `PIPELINE_ID` int(11) DEFAULT NULL,
  `CONSUMPTION_QUANTITY` decimal(24,4) DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTUAL_FLAG` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`QAT_TEMP_CONSUMPTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_inventory`;
CREATE TABLE `qat_temp_inventory` (
  `QAT_TEMP_INVENTORY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INVENTORY_DATE` date DEFAULT NULL,
  `REGION_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` varchar(225) COLLATE utf8_bin DEFAULT NULL,
  `ACTUAL_QTY` BIGINT(20) DEFAULT NULL,
  `ADJUSTMENT_QTY` BIGINT(20) DEFAULT NULL,
  `DATA_SOURCE_ID` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `NOTES` text COLLATE utf8_bin,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`QAT_TEMP_INVENTORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_program`;
CREATE TABLE `qat_temp_program` (
  `PROGRAM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REALM_COUNTRY_ID` int(11) DEFAULT NULL,
  `ORGANISATION_ID` int(11) DEFAULT NULL,
  `HEALTH_AREA_ID` int(11) DEFAULT NULL,
  `PROGRAM_NAME` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `PROGRAM_MANAGER_USER_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `PROGRAM_NOTES` TEXT CHARACTER SET utf8 DEFAULT NULL,
  `AIR_FREIGHT_PERC` decimal(12,2) DEFAULT NULL,
  `PLANNED_TO_DRAFT_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SEA_FREIGHT_PERC` decimal(12,2) DEFAULT NULL,
  `DRAFT_TO_SUBMITTED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SUBMITTED_TO_APPROVED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `DELIVERED_TO_RECEIVED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `MONTHS_IN_PAST_FOR_AMC` int(11) DEFAULT NULL,
  `MONTHS_IN_FUTURE_FOR_AMC` int(11) DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `LABEL_ID` int(11) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `ARRIVED_TO_DELIVERED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_program_planning_unit`;
CREATE TABLE `qat_temp_program_planning_unit` (
  `QAT_TEM_PLANNING_UNIT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int(11) DEFAULT NULL,
  `PLANNING_UNIT_ID` varchar(225) COLLATE utf8_bin DEFAULT NULL,
  `REORDER_FREQUENCY_IN_MONTHS` int(11) DEFAULT NULL,
  `MIN_MONTHS_OF_STOCK` int(11) DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` int(11) DEFAULT NULL,
  `PIPELINE_PRODUCT_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`QAT_TEM_PLANNING_UNIT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_program_region`;
CREATE TABLE `qat_temp_program_region` (
  `PROGRAM_REGION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `REGION_ID` int(11) DEFAULT NULL,
  `PROGRAM_ID` int(11) DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTIVE` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_REGION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_shipment`;
CREATE TABLE `qat_temp_shipment` (
  `SHIPMENT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PLANNING_UNIT_ID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `EXPECTED_DELIVERY_DATE` date DEFAULT NULL,
  `SUGGESTED_QTY` decimal(24,4) unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `PROCUREMENT_UNIT_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `SUPPLIER_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `QUANTITY` decimal(24,4) unsigned DEFAULT NULL,
  `RATE` decimal(24,4) DEFAULT NULL,
  `PRODUCT_COST` decimal(24,4) unsigned DEFAULT NULL,
  `SHIPPING_MODE` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `FREIGHT_COST` decimal(24,4) unsigned DEFAULT NULL,
  `ORDERED_DATE` date DEFAULT NULL,
  `SHIPPED_DATE` date DEFAULT NULL,
  `RECEIVED_DATE` date DEFAULT NULL,
  `SHIPMENT_STATUS_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `NOTES` text COLLATE utf8_bin,
  `DATA_SOURCE_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `FUNDING_SOURCE_ID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint(3) unsigned NOT NULL,
  `PIPELINE_ID` int(10) NOT NULL,
  PRIMARY KEY (`SHIPMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `qat_temp_ap_label`;
CREATE TABLE `qat_temp_ap_label` (
  `LABEL_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_EN` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `LABEL_FR` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `LABEL_SP` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `LABEL_PR` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int(11) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`LABEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


ALTER TABLE `qat_temp_program_planning_unit` ADD COLUMN `LOCAL_PROCUREMENT_LEAD_TIME` DECIMAL(12,2) NULL AFTER `PIPELINE_PRODUCT_ID`;

ALTER TABLE `qat_temp_program_planning_unit` ADD COLUMN `SHELF_LIFE` INT(10) NULL AFTER `LOCAL_PROCUREMENT_LEAD_TIME`, ADD COLUMN `CATALOG_PRICE` DECIMAL(12,2) NULL AFTER `SHELF_LIFE`;

ALTER TABLE `qat_temp_inventory` ADD COLUMN `MULTIPLIER` DECIMAL(12,2) NULL AFTER `PIPELINE_ID`;

ALTER TABLE `qat_temp_inventory` CHANGE COLUMN `REALM_COUNTRY_PLANNING_UNIT_ID` `PLANNING_UNIT_ID` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL ;

ALTER TABLE `qat_temp_inventory` ADD COLUMN `REALM_COUNTRY_PLANNING_UNIT_ID` INT(11) NULL AFTER `MULTIPLIER`;


ALTER TABLE `adb_pipeline` ADD COLUMN `STATUS` TINYINT(1) NULL AFTER `FILE_NAME`;

ALTER TABLE `qat_temp_program_planning_unit` ADD COLUMN `MONTHS_IN_FUTURE_FOR_AMC` INT(11) NULL AFTER `CATALOG_PRICE`, ADD COLUMN `MONTHS_IN_PAST_FOR_AMC` INT(11) NULL AFTER `MONTHS_IN_FUTURE_FOR_AMC`; 


ALTER TABLE `qat_temp_program` DROP COLUMN `PLANNED_TO_DRAFT_LEAD_TIME`, CHANGE `DRAFT_TO_SUBMITTED_LEAD_TIME` `PLANNED_TO_SUBMITTED_LEAD_TIME` DECIMAL(12,2) NULL ; 
ALTER TABLE `qat_temp_program_planning_unit` ADD COLUMN `MULTIPLIER` INT(11) NULL AFTER `PLANNING_UNIT_ID`; 
ALTER TABLE `qat_temp_program_region` CHANGE `PROGRAM_ID` `PIPELINE_ID` INT(11) NULL ; 

Drop table if exists `qat_temp_data_source`;
CREATE TABLE `qat_temp_data_source`( `QAT_DATASOURCE_ID` INT(11) , `PIPELINE_DATA_SOURCE_TYPE` VARCHAR(500) , `PIPELINE_DATA_SOURCE_ID` VARCHAR(500) , `DATA_SOURCE_ID` INT(11) ); 
ALTER TABLE `qat_temp_data_source` CHANGE `QAT_DATASOURCE_ID` `QAT_DATASOURCE_ID` INT(11) NOT NULL AUTO_INCREMENT, ADD PRIMARY KEY(`QAT_DATASOURCE_ID`); 
ALTER TABLE `qat_temp_data_source` ADD COLUMN `PIPELINE_ID` INT(11) NULL AFTER `DATA_SOURCE_ID`; 

ALTER TABLE `qat_temp_program_planning_unit` CHANGE `MULTIPLIER` `MULTIPLIER` DOUBLE(12,2) NULL ; 
 ALTER TABLE `qat_temp_shipment` ADD COLUMN `PLANNED_DATE` DATE NULL AFTER `FREIGHT_COST`, ADD COLUMN `SUBMITTED_DATE` DATE NULL AFTER `PLANNED_DATE`, ADD COLUMN `APPROVED_DATE` DATE NULL AFTER `SUBMITTED_DATE`, ADD COLUMN `ARRIVED_DATE` DATE NULL AFTER `APPROVED_DATE`;
ALTER TABLE `qat_temp_shipment` CHANGE `SHIPPING_MODE` `SHIPPING_MODE` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_bin NULL ; 
ALTER TABLE `qat_temp_program` ADD COLUMN `SHELF_LIFE` DECIMAL(12.2) NULL AFTER `ARRIVED_TO_DELIVERED_LEAD_TIME`;  

Drop table if exists `qat_temp_funding_source`;
CREATE TABLE `qat_temp_funding_source` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `PIPELINE_FUNDING_SOURCE_ID` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
  `FUNDING_SOURCE_ID` INT(11) DEFAULT NULL,
  `PIPELINE_ID` INT(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

Drop table if exists `qat_temp_procurement_agent`;
CREATE TABLE `qat_temp_procurement_agent` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `PIPELINE_PROCUREMENT_AGENT_ID` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` INT(11) DEFAULT NULL,
  `PIPELINE_ID` INT(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

Drop table if exists `adb_shipmentstatus`;
CREATE TABLE `adb_shipmentstatus` (
  `PipelineShipmentStatusCode` VARCHAR(2) COLLATE utf8_bin DEFAULT NULL,
  `QAT_SHIPMENT_STATUS_ID` INT(11) DEFAULT NULL
) ENGINE=INNODB DEFAULT	 CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `adb_shipmentstatus`(`PipelineShipmentStatusCode`,`QAT_SHIPMENT_STATUS_ID`) VALUES ( 'A','6'); 
INSERT INTO `adb_shipmentstatus`(`PipelineShipmentStatusCode`,`QAT_SHIPMENT_STATUS_ID`) VALUES ( 'P','1'); 
INSERT INTO `adb_shipmentstatus`(`PipelineShipmentStatusCode`,`QAT_SHIPMENT_STATUS_ID`) VALUES ( 'R','7'); 
INSERT INTO `adb_shipmentstatus`(`PipelineShipmentStatusCode`,`QAT_SHIPMENT_STATUS_ID`) VALUES ( 'S','5'); 
INSERT INTO `adb_shipmentstatus`(`PipelineShipmentStatusCode`,`QAT_SHIPMENT_STATUS_ID`) VALUES ( 'O','3'); 

ALTER TABLE `qat_temp_consumption` ADD COLUMN `MULTIPLIER` DECIMAL(12,2) NULL AFTER `ACTUAL_FLAG`, ADD COLUMN `REALM_COUNTRY_PLANNING_UNIT_ID` INT(11) NULL AFTER `MULTIPLIER`; 
ALTER TABLE `qat_temp_program_planning_unit` ADD COLUMN `ACTIVE` TINYINT(1) NULL AFTER `MONTHS_IN_PAST_FOR_AMC`; 

ALTER TABLE `adb_datasource` ADD COLUMN `QATDataSourceLabel` VARCHAR(50) NULL AFTER `PIPELINE_ID`;
UPDATE adb_datasource ds SET ds.QATDataSourceLabel='Actual Consumption' WHERE ds.DataSourceTypeID='ACTCON';
UPDATE adb_datasource ds SET ds.QATDataSourceLabel='Forecasted Consumption' WHERE ds.DataSourceTypeID='FORCON';
UPDATE adb_datasource ds SET ds.QATDataSourceLabel='Inventory' WHERE ds.DataSourceTypeID='INV';
UPDATE adb_datasource ds SET ds.QATDataSourceLabel='Shipment' WHERE ds.DataSourceTypeID='SHIP';

ALTER TABLE `qat_temp_program` ADD COLUMN `PROGRAM_CODE` VARCHAR(45) NULL AFTER `SHELF_LIFE`;

ALTER TABLE `rm_budget` CHANGE COLUMN `BUDGET_AMT` `BUDGET_AMT` DECIMAL(24,4) UNSIGNED NOT NULL COMMENT 'The total Budget amt approved for this Budget' ;

SET FOREIGN_KEY_CHECKS=1;


DELIMITER $$

DROP PROCEDURE IF EXISTS `stockStatusReportVertical`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusReportVertical`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_ID INT(10))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%
        -- Report no 16
	-- %%%%%%%%%%%%%%%%%%%%%
	
        SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;

	IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
        mn.MONTH `TRANS_DATE`, 
        sma.OPENING_BALANCE `FINAL_OPENING_BALANCE`, 
        sma.ACTUAL_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY, 
        sma.ACTUAL,
        sma.SHIPMENT_QTY SQTY,
        sma.ADJUSTMENT_MULTIPLIED_QTY `ADJUSTMENT`,
        sma.EXPIRED_STOCK,
        sma.CLOSING_BALANCE `FINAL_CLOSING_BALANCE`,
        sma.AMC,
        sma.MOS `MoS`,
        sma.MIN_STOCK_MOS `MIN_MONTHS_OF_STOCK`,
        sma.MAX_STOCK_MOS `MAX_MONTHS_OF_STOCK`,
        sh.SHIPMENT_ID, sh.SHIPMENT_QTY, 
        fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, 
        ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_Fr `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`
    FROM
        mn 
        LEFT JOIN rm_supply_plan_amc sma ON 
            mn.MONTH=sma.TRANS_DATE 
            AND sma.PROGRAM_ID = @programId
            AND sma.VERSION_ID = @versionId
            AND sma.PLANNING_UNIT_ID = @planningUnitId
        LEFT JOIN 
            (
            SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, st.SHIPMENT_QTY , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID
            FROM 
                (
                SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID 
            ) AS s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
            WHERE 
                st.ACTIVE 
                AND st.SHIPMENT_STATUS_ID != 8 
                AND st.ACCOUNT_FLAG
                AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
                AND st.PLANNING_UNIT_ID =@planningUnitId
        ) sh ON LEFT(sma.TRANS_DATE,7)=LEFT(sh.EDD,7)
        LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
    WHERE
        mn.MONTH BETWEEN @startDate AND @stopDate
    ORDER BY mn.MONTH;
    
END$$

DELIMITER ;


DELIMITER $$

DROP PROCEDURE IF EXISTS `forecastMetricsComparision`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `forecastMetricsComparision`(VAR_USER_ID INT(10), VAR_REALM_ID INT(10), VAR_START_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_PLANNING_UNIT_IDS TEXT, VAR_PREVIOUS_MONTHS INT(10), VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN

	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 5
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- realmId since it is a Global report need to include Realm
        -- startDate - date that the report is to be run for
        -- realmCountryIds list of countries that we need to run the report for
        -- programIds is the list of programs that we need to run the report for
        -- planningUnitIds is the list of planningUnits that we need to run the report for
        -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
        -- current month is always included in the calculation
        -- only consider those months that have both a Forecasted and Actual consumption
        -- WAPE Formulae
        -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months)) 

	DECLARE curRealmCountryId INT;
        DECLARE curHealthAreaId INT;
        DECLARE curOrganisationId INT;
        DECLARE curProgramId INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
        SET @aclSqlString = CONCAT("       AND (FALSE ");
        OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR p.HEALTH_AREA_ID="  ,IFNULL(curHealthAreaId,-1)  ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");
    
    SET @realmId = VAR_REALM_ID;
    SET @startDate = VAR_START_DATE;
    SET @previousMonths = VAR_PREVIOUS_MONTHS;
    SET @approvedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    
    SET @sqlString = "";
    
	SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    spa.TRANS_DATE, ");
    SET @sqlString = CONCAT(@sqlString, "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR  `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, spa.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION`, ");
--    SET @sqlString = CONCAT(@sqlString, "    c2.ACTUAL_CONSUMPTION_TOTAL, c2.DIFF_CONSUMPTION_TOTAL, c2.MONTH_COUNT, IF(spa.ACTUAL=1 AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, c2.DIFF_CONSUMPTION_TOTAL*100/c2.ACTUAL_CONSUMPTION_TOTAL, null) FORECAST_ERROR ");
    SET @sqlString = CONCAT(@sqlString, "    c2.ACTUAL_CONSUMPTION_TOTAL, c2.DIFF_CONSUMPTION_TOTAL, c2.MONTH_COUNT, c2.DIFF_CONSUMPTION_TOTAL*100/c2.ACTUAL_CONSUMPTION_TOTAL FORECAST_ERROR ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "           WHERE TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "               AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "               AND spa.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "           GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, ") f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE=@startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "    ( ");
    SET @sqlString = CONCAT(@sqlString, "    SELECT ");
    SET @sqlString = CONCAT(@sqlString, "        @startDate `TRANS_DATE`, p.PROGRAM_ID, pu.PLANNING_UNIT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, spa.ACTUAL_CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(spa.ACTUAL_CONSUMPTION_QTY-spa.FORECASTED_CONSUMPTION_QTY), null)) `DIFF_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "        SUM(IF(spa.ACTUAL AND spa.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND spa.FORECASTED_CONSUMPTION_QTY IS NOT NULL, 1, 0)) `MONTH_COUNT` ");
    SET @sqlString = CONCAT(@sqlString, "    FROM rm_program_planning_unit ppu ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN ");
    SET @sqlString = CONCAT(@sqlString, "        ( ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT spa.PROGRAM_ID, MAX(spa.VERSION_ID) MAX_VERSION FROM rm_supply_plan_amc spa LEFT JOIN rm_program_version pv ON spa.PROGRAM_ID=pv.PROGRAM_ID AND spa.VERSION_ID=pv.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE ");
    SET @sqlString = CONCAT(@sqlString, "            TRUE ");
    IF @approvedSupplyPlanOnly THEN
        SET @sqlString = CONCAT(@sqlString, "            AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2 ");
    END IF;
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "            AND spa.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY spa.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    ) f ON ppu.PROGRAM_ID=f.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_supply_plan_amc spa ON spa.PROGRAM_ID=f.PROGRAM_ID AND spa.VERSION_ID=f.MAX_VERSION AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL @previousMonths MONTH) AND @startDate AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE ");
    SET @sqlString = CONCAT(@sqlString, "        TRUE ");
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND p.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    
    SET @sqlString = CONCAT(@sqlString, "    GROUP BY ppu.PROGRAM_ID, ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") c2 ON spa.PROGRAM_ID=c2.PROGRAM_ID AND spa.TRANS_DATE=c2.TRANS_DATE AND spa.PLANNING_UNIT_ID=c2.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    TRUE ");
    IF LENGTH(VAR_PROGRAM_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PROGRAM_ID IN (",VAR_PROGRAM_IDS,") ");
    END IF;
    IF LENGTH(VAR_REALM_COUNTRY_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND p.REALM_COUNTRY_ID IN (",VAR_REALM_COUNTRY_IDS,") ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "       AND ppu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;


ALTER TABLE `rm_consumption_trans`
CHANGE COLUMN `CONSUMPTION_QTY` `CONSUMPTION_QTY` DECIMAL(24,4) NOT NULL,
CHANGE COLUMN `CONSUMPTION_RCPU_QTY` `CONSUMPTION_RCPU_QTY` DECIMAL(24,4) NOT NULL ;

ALTER TABLE `rm_consumption_trans_batch_info`
CHANGE COLUMN `CONSUMPTION_QTY` `CONSUMPTION_QTY` DECIMAL(24,4) NOT NULL ;

ALTER TABLE `rm_inventory_trans` CHANGE `ACTUAL_QTY` `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL COMMENT 'If only an Adjustment Qty is given then this was not an Inventory count but an Adjustment to Inventory. If the Actual Qty is mentioned then the Adjustment Qty was derived', CHANGE `ADJUSTMENT_QTY` `ADJUSTMENT_QTY` BIGINT(20) NULL COMMENT 'If only an Adjustment Qty is given then this was not an Inventory count but an Adjustment to Inventory. If the Actual Qty is mentioned then the Adjustment Qty was derived'; 

ALTER TABLE `rm_inventory_trans_batch_info` CHANGE `ACTUAL_QTY` `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL , CHANGE `ADJUSTMENT_QTY` `ADJUSTMENT_QTY` BIGINT(20) NULL ; 

ALTER TABLE `rm_shipment_trans` CHANGE `SHIPMENT_QTY` `SHIPMENT_QTY` BIGINT(20) UNSIGNED NULL COMMENT 'Qty of Logistics Unit in the Shipment'; 

ALTER TABLE `rm_shipment_trans_batch_info` CHANGE `BATCH_SHIPMENT_QTY` `BATCH_SHIPMENT_QTY` BIGINT(20) UNSIGNED NOT NULL; 

ALTER TABLE `tmp_nsp`
CHANGE COLUMN `FORECASTED_CONSUMPTION` `FORECASTED_CONSUMPTION` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ACTUAL_CONSUMPTION` `ACTUAL_CONSUMPTION` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ADJUSTMENT` `ADJUSTMENT` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `STOCK` `STOCK` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `SHIPMENT` `SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `SHIPMENT_WPS` `SHIPMENT_WPS` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_PLANNED_SHIPMENT` `MANUAL_PLANNED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_SUBMITTED_SHIPMENT` `MANUAL_SUBMITTED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_APPROVED_SHIPMENT` `MANUAL_APPROVED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_SHIPPED_SHIPMENT` `MANUAL_SHIPPED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_RECEIVED_SHIPMENT` `MANUAL_RECEIVED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_ONHOLD_SHIPMENT` `MANUAL_ONHOLD_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_PLANNED_SHIPMENT` `ERP_PLANNED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_SUBMITTED_SHIPMENT` `ERP_SUBMITTED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_APPROVED_SHIPMENT` `ERP_APPROVED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_SHIPPED_SHIPMENT` `ERP_SHIPPED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_RECEIVED_SHIPMENT` `ERP_RECEIVED_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_ONHOLD_SHIPMENT` `ERP_ONHOLD_SHIPMENT` BIGINT(20) UNSIGNED NULL DEFAULT NULL ;

ALTER TABLE `rm_supply_plan_amc`
CHANGE COLUMN `OPENING_BALANCE` `OPENING_BALANCE` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `OPENING_BALANCE_WPS` `OPENING_BALANCE_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_PLANNED_SHIPMENT_QTY` `MANUAL_PLANNED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_SUBMITTED_SHIPMENT_QTY` `MANUAL_SUBMITTED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_APPROVED_SHIPMENT_QTY` `MANUAL_APPROVED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_SHIPPED_SHIPMENT_QTY` `MANUAL_SHIPPED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_RECEIVED_SHIPMENT_QTY` `MANUAL_RECEIVED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `MANUAL_ONHOLD_SHIPMENT_QTY` `MANUAL_ONHOLD_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_PLANNED_SHIPMENT_QTY` `ERP_PLANNED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_SUBMITTED_SHIPMENT_QTY` `ERP_SUBMITTED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_APPROVED_SHIPMENT_QTY` `ERP_APPROVED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_SHIPPED_SHIPMENT_QTY` `ERP_SHIPPED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_RECEIVED_SHIPMENT_QTY` `ERP_RECEIVED_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ERP_ONHOLD_SHIPMENT_QTY` `ERP_ONHOLD_SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `SHIPMENT_QTY` `SHIPMENT_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `FORECASTED_CONSUMPTION_QTY` `FORECASTED_CONSUMPTION_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ACTUAL_CONSUMPTION_QTY` `ACTUAL_CONSUMPTION_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `ADJUSTMENT_MULTIPLIED_QTY` `ADJUSTMENT_MULTIPLIED_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `STOCK_MULTIPLIED_QTY` `STOCK_MULTIPLIED_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `EXPIRED_STOCK` `EXPIRED_STOCK` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `EXPIRED_STOCK_WPS` `EXPIRED_STOCK_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CLOSING_BALANCE` `CLOSING_BALANCE` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CLOSING_BALANCE_WPS` `CLOSING_BALANCE_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `UNMET_DEMAND` `UNMET_DEMAND` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `UNMET_DEMAND_WPS` `UNMET_DEMAND_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `NATIONAL_ADJUSTMENT` `NATIONAL_ADJUSTMENT` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `NATIONAL_ADJUSTMENT_WPS` `NATIONAL_ADJUSTMENT_WPS` BIGINT(20) NULL DEFAULT NULL ;

ALTER TABLE `rm_supply_plan_batch_qty`
CHANGE COLUMN `ACTUAL_CONSUMPTION_QTY` `ACTUAL_CONSUMPTION_QTY` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
CHANGE COLUMN `SHIPMENT_QTY` `SHIPMENT_QTY` BIGINT(20) UNSIGNED NOT NULL ,
CHANGE COLUMN `SHIPMENT_QTY_WPS` `SHIPMENT_QTY_WPS` BIGINT(20) UNSIGNED NOT NULL ,
CHANGE COLUMN `ADJUSTMENT_MULTIPLIED_QTY` `ADJUSTMENT_MULTIPLIED_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `STOCK_MULTIPLIED_QTY` `STOCK_MULTIPLIED_QTY` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `OPENING_BALANCE` `OPENING_BALANCE` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `OPENING_BALANCE_WPS` `OPENING_BALANCE_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `EXPIRED_STOCK` `EXPIRED_STOCK` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `EXPIRED_STOCK_WPS` `EXPIRED_STOCK_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CALCULATED_CONSUMPTION` `CALCULATED_CONSUMPTION` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CALCULATED_CONSUMPTION_WPS` `CALCULATED_CONSUMPTION_WPS` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CLOSING_BALANCE` `CLOSING_BALANCE` BIGINT(20) NULL DEFAULT NULL ,
CHANGE COLUMN `CLOSING_BALANCE_WPS` `CLOSING_BALANCE_WPS` BIGINT(20) NULL DEFAULT NULL ;

ALTER TABLE `rm_erp_order` CHANGE `QTY` `QTY` BIGINT(20) UNSIGNED NOT NULL; 

ALTER TABLE `rm_erp_shipment` CHANGE `DELIVERED_QTY` `DELIVERED_QTY` BIGINT(20) NULL ; 

ALTER TABLE `rm_shipment` CHANGE `SUGGESTED_QTY` `SUGGESTED_QTY` BIGINT(20) UNSIGNED NULL ; 

ALTER TABLE `rm_shipment_trans` CHANGE `PRODUCT_COST` `PRODUCT_COST` DECIMAL(24,4) UNSIGNED NULL COMMENT 'Final price of the Shipment for the Goods', CHANGE `FREIGHT_COST` `FREIGHT_COST` DECIMAL(24,4) UNSIGNED NULL COMMENT 'Cost of Freight for the Shipment'; 

ALTER TABLE `rm_supply_plan_amc` CHANGE `AMC` `AMC` DECIMAL(24,4) NULL , CHANGE `MOS` `MOS` DECIMAL(24,4) NULL , CHANGE `MOS_WPS` `MOS_WPS` DECIMAL(24,4) NULL , CHANGE `MIN_STOCK_QTY` `MIN_STOCK_QTY` DECIMAL(24,4) NULL , CHANGE `MIN_STOCK_MOS` `MIN_STOCK_MOS` DECIMAL(24,4) NULL , CHANGE `MAX_STOCK_QTY` `MAX_STOCK_QTY` DECIMAL(24,4) NULL , CHANGE `MAX_STOCK_MOS` `MAX_STOCK_MOS` DECIMAL(24,4) NULL ; 

ALTER TABLE `rm_budget` CHANGE `BUDGET_AMT` `BUDGET_AMT` DECIMAL(22,2) UNSIGNED NOT NULL COMMENT 'The total Budget amt approved for this Budget'; 