
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `adb_commodityprice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_commodityprice` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `dtmEffective` datetime DEFAULT NULL,
  `UnitPrice` double(24,4) DEFAULT NULL,
  `dtmChanged` datetime DEFAULT NULL,
  `User` varchar(35) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `fUserDefined` tinyint(1) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_commodityprice_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_commodityprice_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_consumption` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ConsStartYear` smallint DEFAULT NULL,
  `ConsStartMonth` smallint DEFAULT NULL,
  `ConsActualFlag` tinyint(1) DEFAULT NULL,
  `ConsNumMonths` smallint DEFAULT NULL,
  `ConsAmount` double(24,4) DEFAULT NULL,
  `ConsDataSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ConsIflator` double(24,4) DEFAULT NULL,
  `ConsNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ConsDateChanged` datetime DEFAULT NULL,
  `ConsID` int DEFAULT NULL,
  `ConsDisplayNote` tinyint(1) DEFAULT NULL,
  `Old_consumption` double(24,4) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_consumption_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_consumption_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_datasource` (
  `DataSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DataSourceName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DataSourceTypeID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  `QATDataSourceLabel` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  KEY `fk_adb_datasource_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_datasource_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_fundingsource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_fundingsource` (
  `FundingSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FundingSourceName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FundingNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_fundingsource_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_fundingsource_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_inventory` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Period` datetime DEFAULT NULL,
  `InvAmount` double(24,4) DEFAULT NULL,
  `InvTransferFlag` tinyint(1) DEFAULT NULL,
  `InvNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `InvDateChanged` datetime DEFAULT NULL,
  `ctrIndex` int DEFAULT NULL,
  `InvDisplayNote` tinyint(1) DEFAULT NULL,
  `InvDataSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `fImported` tinyint(1) DEFAULT NULL,
  `Old_Inventory` double(24,4) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_inventory_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_inventory_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_method` (
  `MethodID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `MethodName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CYPFactor` double(24,4) DEFAULT NULL,
  `MethodNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ParentID` int DEFAULT NULL,
  `CategoryID` int DEFAULT NULL,
  `fRollup` tinyint(1) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_method_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_method_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_monthlystockarchive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_monthlystockarchive` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `EOYBalance` double(24,4) DEFAULT NULL,
  `StockYear` smallint DEFAULT NULL,
  `StockMonth` smallint DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_monthlystockarchive_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_monthlystockarchive_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_paste_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_paste_errors` (
  `F1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `F2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `F3` double(24,4) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_paste_errors_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_paste_errors_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_pipeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_pipeline` (
  `PIPELINE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `FILE_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `STATUS` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PIPELINE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_product` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductMinMonths` smallint DEFAULT NULL,
  `ProductMaxMonths` smallint DEFAULT NULL,
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `MethodID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductActiveFlag` tinyint(1) DEFAULT NULL,
  `ProductActiveDate` datetime DEFAULT NULL,
  `DefaultCaseSize` int DEFAULT NULL,
  `ProductNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ProdCMax` smallint DEFAULT NULL,
  `ProdCMin` smallint DEFAULT NULL,
  `ProdDesStock` smallint DEFAULT NULL,
  `txtInnovatorDrugName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `dblLowestUnitQty` double(24,4) DEFAULT NULL,
  `txtLowestUnitMeasure` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `txtSubstitutionList` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `fPermittedInCountry` tinyint(1) DEFAULT NULL,
  `memAvailabilityNotes` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `fAvailabilityStatus` tinyint(1) DEFAULT NULL,
  `fUserDefined` tinyint(1) DEFAULT NULL,
  `strImportSource` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `BUConversion` int DEFAULT NULL,
  `txtPreferenceNotes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `lngAMCStart` int DEFAULT NULL,
  `lngAMCMonths` int DEFAULT NULL,
  `fAMCChanged` tinyint(1) DEFAULT NULL,
  `txtMigrationStatus` int DEFAULT NULL,
  `txtMigrationStatusDate` datetime DEFAULT NULL,
  `strType` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `OldProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `OldProductName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `lngBatch` int DEFAULT NULL,
  `OldMethodID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_product_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_product_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_productfreightcost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_productfreightcost` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FreightCost` double(24,4) DEFAULT NULL,
  `dtmChanged` datetime DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_productfreightcost_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_productfreightcost_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_productsuppliercasesize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_productsuppliercasesize` (
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `dtmEffective` datetime DEFAULT NULL,
  `intCaseSize` int DEFAULT NULL,
  `dtmChanged` datetime DEFAULT NULL,
  `User` varchar(35) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_productsuppliercasesize_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_productsuppliercasesize_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_programinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_programinfo` (
  `ProgramName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DataDirectory` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Language` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DefaultLeadTimePlan` double(24,4) DEFAULT NULL,
  `DefaultLeadTimeOrder` double(24,4) DEFAULT NULL,
  `DefaultLeadTimeShip` double(24,4) DEFAULT NULL,
  `DefaultShipCost` double(24,4) DEFAULT NULL,
  `ProgramContact` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Telephone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Fax` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CountryCode` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CountryName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `IsCurrent` tinyint(1) DEFAULT NULL,
  `Note` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ProgramCode` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `IsActive` tinyint(1) DEFAULT NULL,
  `StartSize` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `IsDefault` tinyint(1) DEFAULT NULL,
  `ArchiveDate` datetime DEFAULT NULL,
  `ArchiveYear` int DEFAULT NULL,
  `ArchiveInclude` tinyint(1) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_programinfo_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_programinfo_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_shipment` (
  `ShipmentID` int DEFAULT NULL,
  `ProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipDataSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipAmount` double(24,4) DEFAULT NULL,
  `ShipPlannedDate` datetime DEFAULT NULL,
  `ShipOrderedDate` datetime DEFAULT NULL,
  `ShipShippedDate` datetime DEFAULT NULL,
  `ShipReceivedDate` datetime DEFAULT NULL,
  `ShipStatusCode` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ShipDateChanged` datetime DEFAULT NULL,
  `ShipFreightCost` double(24,4) DEFAULT NULL,
  `ShipValue` double(24,4) DEFAULT NULL,
  `ShipCaseLot` int DEFAULT NULL,
  `ShipDisplayNote` tinyint(1) DEFAULT NULL,
  `ShipPO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Old_Shipment` double(24,4) DEFAULT NULL,
  `ShipFundingSourceID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_SHIPMENT_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_SHIPMENT_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_shipmentstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_shipmentstatus` (
  `PipelineShipmentStatusCode` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `QAT_SHIPMENT_STATUS_ID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_source` (
  `SupplierID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierLeadTimePlan` double(24,4) DEFAULT NULL,
  `SupplierLeadTimeOrder` double(24,4) DEFAULT NULL,
  `SupplierLeadTimeShip` double(24,4) DEFAULT NULL,
  `SupplierNote` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `Freight` double(24,4) DEFAULT NULL,
  `DefaultSupplier` tinyint(1) DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_source_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_source_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_tblbe_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_tblbe_version` (
  `sBE_Version` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `dtmUpdated` datetime DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_tblbe_version_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_tblbe_version_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_tblimportproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_tblimportproducts` (
  `strProductID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `strName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `strDose` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `lngCYP` double(24,4) DEFAULT NULL,
  `dtmExport` datetime DEFAULT NULL,
  `fProcessed` tinyint(1) DEFAULT NULL,
  `lngID` int DEFAULT NULL,
  `strSource` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `strMapping` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_tblimportproducts_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_tblimportproducts_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `adb_tblimportrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adb_tblimportrecords` (
  `strProductID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `dtmPeriod` datetime DEFAULT NULL,
  `lngconsumption` int DEFAULT NULL,
  `lngAdjustment` int DEFAULT NULL,
  `dblDataInterval` double(24,4) DEFAULT NULL,
  `lngParentID` int DEFAULT NULL,
  `PIPELINE_ID` int unsigned NOT NULL,
  KEY `fk_adb_tblimportrecords_1_idx` (`PIPELINE_ID`),
  CONSTRAINT `fk_adb_tblimportrecords_1` FOREIGN KEY (`PIPELINE_ID`) REFERENCES `adb_pipeline` (`PIPELINE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_country` (
  `COUNTRY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Country',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `COUNTRY_CODE` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Code for each country. Will take the data from the ISO Country list',
  `CURRENCY_ID` int unsigned NOT NULL COMMENT 'Local Currency used by this country',
  `COUNTRY_CODE2` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this Country is Active. False indicates this Country has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`COUNTRY_ID`),
  UNIQUE KEY `unq_countryCode` (`COUNTRY_CODE`) COMMENT 'Unique Country Code ',
  KEY `fk_country_labelId_idx` (`LABEL_ID`),
  KEY `fk_country_currencyId_idx` (`CURRENCY_ID`),
  KEY `fk_country_createdBy_idx` (`CREATED_BY`),
  KEY `fk_country_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_country_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_country_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_country_currencyId` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `ap_currency` (`CURRENCY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_country_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_country_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Countries inside a Realm\nNote: This is a master list of Countries stored at the Application level, cannot be created and edited by Realm admins';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_currency` (
  `CURRENCY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Currency',
  `CURRENCY_CODE` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Unique Code for each Currency',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CONVERSION_RATE_TO_USD` decimal(14,4) unsigned NOT NULL COMMENT 'Latest conversion rate to USD',
  `IS_SYNC` tinyint unsigned NOT NULL DEFAULT '1',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT '			',
  PRIMARY KEY (`CURRENCY_ID`),
  UNIQUE KEY `CURRENCY_CODE_UNIQUE` (`CURRENCY_CODE`),
  UNIQUE KEY `unq_currencyCode` (`CURRENCY_CODE`) COMMENT 'Currency Code musy be unique',
  KEY `fk_currency_labelId_idx` (`LABEL_ID`),
  KEY `fk_currency_createdBy_idx` (`CREATED_BY`),
  KEY `fk_currency_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_currencyLastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_currency_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_currency_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_currency_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Currencies in the application\nNote: A Currency cannot be created it is one of a Fixed Master';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_currency_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_currency_history` (
  `CURRENCY_HISTORY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Currency Transaction that we have pulled',
  `CURRENCY_ID` int unsigned NOT NULL COMMENT 'The Currency id that this data is for',
  `CONVERSION_RATE_TO_USD` decimal(14,4) NOT NULL COMMENT 'Conversion rate to USD\n',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`CURRENCY_HISTORY_ID`),
  KEY `fk_currency_history_currencyId_idx` (`CURRENCY_ID`),
  CONSTRAINT `fk_currency_history_currencyId` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `ap_currency` (`CURRENCY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=783 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the historical values of Exchange rate for each currency\nNote: Updated automatically by the application';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_dimension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_dimension` (
  `DIMENSION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`DIMENSION_ID`),
  KEY `fk_dimension_labelId_idx` (`LABEL_ID`),
  KEY `fk_ap_dimension_us_user1_idx` (`CREATED_BY`),
  KEY `fk_ap_dimension_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_dimension_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_ap_dimension_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_dimension_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_dimension_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Dimensions of measure used across the Application\nNote: Are based on the Application and can only be administered by an Applicaton level Admin. For e.g. Length, Weight, Volume, Area, Items etc';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_export`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_export` (
  `EXPORT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ERP_CODE` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `JOB_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LAST_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`EXPORT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_extrapolation_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_extrapolation_method` (
  `EXTRAPOLATION_METHOD_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `SORT_ORDER` int unsigned NOT NULL,
  PRIMARY KEY (`EXTRAPOLATION_METHOD_ID`),
  KEY `fk_ap_extrapolation_method_labelId_idx` (`LABEL_ID`),
  KEY `fk_ap_extrapolation_method_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_extrapolation_method_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_ap_extrapolation_method_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_extrapolation_method_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_extrapolation_method_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_forecast_method_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_forecast_method_type` (
  `FORECAST_METHOD_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each foreacastMethodType 1-Tree, 2-Consumption',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`FORECAST_METHOD_TYPE_ID`),
  UNIQUE KEY `FORECAST_METHOD_TYPE_ID_UNIQUE` (`FORECAST_METHOD_TYPE_ID`),
  KEY `fk_ap_forecast_method_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_forecast_method_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_forecast_method_type_labelId` (`LABEL_ID`),
  CONSTRAINT `fk_ap_forecast_method_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_forecast_method_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_forecast_method_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_integration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_integration` (
  `INTEGRATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTEGRATION_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REALM_ID` int unsigned NOT NULL,
  `FOLDER_LOCATION` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `FILE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `INTEGRATION_VIEW_ID` int unsigned NOT NULL,
  PRIMARY KEY (`INTEGRATION_ID`),
  KEY `fk_ap_integration_realmId_idx` (`REALM_ID`),
  KEY `fk_ap_integration_integrationViewId_idx` (`INTEGRATION_VIEW_ID`),
  CONSTRAINT `fk_ap_integration_integrationViewId` FOREIGN KEY (`INTEGRATION_VIEW_ID`) REFERENCES `ap_integration_view` (`INTEGRATION_VIEW_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_integration_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_integration_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_integration_view` (
  `INTEGRATION_VIEW_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTEGRATION_VIEW_DESC` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `INTEGRATION_VIEW_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`INTEGRATION_VIEW_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_label` (
  `LABEL_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Label',
  `LABEL_EN` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'Label in English, cannot be Null since it is language the system will default to',
  `LABEL_FR` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Label in French',
  `LABEL_SP` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Label in Spanish',
  `LABEL_PR` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Label in Pourtegese',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified By',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified Date\n',
  `SOURCE_ID` int unsigned NOT NULL,
  PRIMARY KEY (`LABEL_ID`),
  KEY `fk_ap_label_labelSource_idx` (`SOURCE_ID`),
  KEY `fk_ap_label_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_label_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_ap_label_labelEn` (`LABEL_EN`),
  CONSTRAINT `fk_ap_label_label_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_label_labelSource` FOREIGN KEY (`SOURCE_ID`) REFERENCES `ap_label_source` (`SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_label_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=342439 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Languages availale in the Application\nNote: A Language cannot be created it is one of a Fixed Master';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_label_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_label_source` (
  `SOURCE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SOURCE_DESC` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `SOURCE_TEXT` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`SOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_language` (
  `LANGUAGE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Language that we support, this includes Db languages and UI languages',
  `LANGUAGE_CODE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LABEL_ID` int NOT NULL COMMENT 'Language name, no need for a Label here since the Language name will be in the required language',
  `COUNTRY_CODE` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Language is Active. False indicates this Language has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified By',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '2 char language code that is used in the translations',
  PRIMARY KEY (`LANGUAGE_ID`),
  UNIQUE KEY `unqLanguageName` (`LABEL_ID`) COMMENT 'Language name should be unique',
  UNIQUE KEY `unqLanguageCode` (`LANGUAGE_CODE`),
  KEY `fk_language_createdBy_idx` (`CREATED_BY`),
  KEY `fk_language_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_language_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_language_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_language_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Languages availale in the Application\nNote: A Language cannot be created or administered it is at the Application level';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_modeling_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_modeling_type` (
  `MODELING_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each modeling type 1.Target (#), 2. Linear (#), 3. Linear (%), 4. Exponential (%)',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`MODELING_TYPE_ID`),
  UNIQUE KEY `MODELING_TYPE_ID_UNIQUE` (`MODELING_TYPE_ID`),
  KEY `fk_ap_modeling_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_modeling_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_modeling_type_labelId` (`LABEL_ID`),
  CONSTRAINT `fk_ap_modeling_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_modeling_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_modeling_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_node_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_node_type` (
  `NODE_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node type id, 1-Aggregation, 2-Number, 3-Percentage, 4-Forecasting Unit, 5-Planning Unit',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `MODELING_ALLOWED` tinyint unsigned NOT NULL,
  `EXTRAPOLATION_ALLOWED` tinyint unsigned NOT NULL,
  `TREE_TEMPLATE_ALLOWED` tinyint unsigned NOT NULL,
  `FORECAST_TREE_ALLOWED` tinyint unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`NODE_TYPE_ID`),
  UNIQUE KEY `NODE_TYPE_ID_UNIQUE` (`NODE_TYPE_ID`),
  KEY `fk_ap_node_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_node_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_node_type_labelId` (`LABEL_ID`),
  CONSTRAINT `fk_ap_node_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_node_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_node_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_node_type_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_node_type_rule` (
  `NODE_TYPE_RULE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_TYPE_ID` int unsigned NOT NULL,
  `CHILD_NODE_TYPE_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`NODE_TYPE_RULE_ID`),
  KEY `fk_nodeTypeRule_nodeTypeId_idx` (`NODE_TYPE_ID`),
  KEY `fk_nodeTypeRule_childNodeTypeId_idx` (`CHILD_NODE_TYPE_ID`),
  CONSTRAINT `fk_nodeTypeRule_childNodeTypeId` FOREIGN KEY (`CHILD_NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_nodeTypeRule_nodeTypeId` FOREIGN KEY (`NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_notification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_notification_type` (
  `NOTIFICATION_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  PRIMARY KEY (`NOTIFICATION_TYPE_ID`),
  KEY `fk_ap_notification_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_ap_notification_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_notification_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_ap_notification_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_notification_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_notification_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_problem` (
  `PROBLEM_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `PROBLEM_CATEGORY_ID` int unsigned NOT NULL,
  `ACTION_URL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACTION_LABEL_ID` int unsigned NOT NULL,
  `ACTUAL_CONSUMPTION_TRIGGER` tinyint(1) NOT NULL DEFAULT '0',
  `FORECASTED_CONSUMPTION_TRIGGER` tinyint(1) NOT NULL DEFAULT '0',
  `INVENTORY_TRIGGER` tinyint(1) NOT NULL DEFAULT '0',
  `ADJUSTMENT_TRIGGER` tinyint(1) NOT NULL DEFAULT '0',
  `SHIPMENT_TRIGGER` tinyint(1) NOT NULL DEFAULT '0',
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROBLEM_ID`),
  KEY `fk_ap_problem_us_user1_idx` (`CREATED_BY`),
  KEY `fk_ap_problem_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_problem_ap_label1_idx` (`LABEL_ID`),
  KEY `fk_ap_problem_problemCategoryId_idx` (`PROBLEM_CATEGORY_ID`),
  CONSTRAINT `fk_ap_problem_ap_label1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_problem_problemCategoryId` FOREIGN KEY (`PROBLEM_CATEGORY_ID`) REFERENCES `ap_problem_category` (`PROBLEM_CATEGORY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_problem_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_problem_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_problem_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_problem_category` (
  `PROBLEM_CATEGORY_ID` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  PRIMARY KEY (`PROBLEM_CATEGORY_ID`),
  KEY `fk_ap_problem_category_labelId_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_problem_category_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_problem_criticality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_problem_criticality` (
  `CRITICALITY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `COLOR_HTML_CODE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`CRITICALITY_ID`),
  KEY `fk_ap_problem_criticality_ap_label1_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_problem_criticality_ap_label1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_problem_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_problem_status` (
  `PROBLEM_STATUS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  `USER_MANAGED` tinyint unsigned NOT NULL,
  PRIMARY KEY (`PROBLEM_STATUS_ID`),
  KEY `fk_ap_problem_status_ap_label1_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_problem_status_ap_label1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_problem_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_problem_type` (
  `PROBLEM_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  PRIMARY KEY (`PROBLEM_TYPE_ID`),
  KEY `fk_ap_problem_type_ap_label1_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_problem_type_ap_label1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_reorder_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_reorder_master` (
  `NO_OF_MONTHS_FOR_REORDER` int unsigned NOT NULL,
  `TOTAL_MONTHS_OF_PLANNED_CONSUMPTION` int unsigned NOT NULL,
  PRIMARY KEY (`NO_OF_MONTHS_FOR_REORDER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_security` (
  `SECURITY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `METHOD` int NOT NULL,
  `URL` varchar(100) NOT NULL,
  `BF` varchar(50) NOT NULL,
  PRIMARY KEY (`SECURITY_ID`),
  UNIQUE KEY `index2` (`METHOD`,`URL`,`BF`)
) ENGINE=InnoDB AUTO_INCREMENT=588 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_shipment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_shipment_status` (
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Shipment Status',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SHIPMENT_STATUS_ID`),
  KEY `fk_shipment_status_createdBy_idx` (`CREATED_BY`),
  KEY `fk_shipment_status_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_shipment_status_labelId_idx` (`LABEL_ID`),
  KEY `idx_shipmentStatus_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_shipment_status_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_status_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_status_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store which Shipping status is logically allowed';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_shipment_status_allowed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_shipment_status_allowed` (
  `SHIPMENT_STATUS_ALLOWED_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Level - Program mapping',
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL COMMENT 'Ship status Id',
  `NEXT_SHIPMENT_STATUS_ID` int unsigned NOT NULL COMMENT 'Next allowed Shipment Status Id',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SHIPMENT_STATUS_ALLOWED_ID`),
  KEY `fk_shipment_status_allowed_shipmentStatusId_idx` (`SHIPMENT_STATUS_ID`),
  KEY `fk_shipment_status_allowed_nextShipmentStatusId_idx` (`NEXT_SHIPMENT_STATUS_ID`),
  KEY `fk_shipment_status_allowed_createdBy_idx` (`CREATED_BY`),
  KEY `fk_shipment_status_allowed_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_shipment_status_allowed_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_status_allowed_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_status_allowed_nextShipmentStatusId` FOREIGN KEY (`NEXT_SHIPMENT_STATUS_ID`) REFERENCES `ap_shipment_status` (`SHIPMENT_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_status_allowed_shipmentStatusId` FOREIGN KEY (`SHIPMENT_STATUS_ID`) REFERENCES `ap_shipment_status` (`SHIPMENT_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_static_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_static_label` (
  `STATIC_LABEL_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_CODE` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`STATIC_LABEL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3610 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Static labels to be used across the application';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_static_label_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_static_label_languages` (
  `STATIC_LABEL_LANGUAGE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `STATIC_LABEL_ID` int unsigned NOT NULL COMMENT 'Static Label that this Text is for',
  `LANGUAGE_ID` int unsigned NOT NULL COMMENT 'Language that this text is for',
  `LABEL_TEXT` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Text in the Language to be displayed',
  PRIMARY KEY (`STATIC_LABEL_LANGUAGE_ID`),
  KEY `fk_ap_static_label_languages_ap_static_label1_idx` (`STATIC_LABEL_ID`),
  KEY `fk_ap_static_label_languages_ap_language1_idx` (`LANGUAGE_ID`),
  CONSTRAINT `fk_ap_static_label_languages_ap_language1` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `ap_language` (`LANGUAGE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_static_label_languages_ap_static_label1` FOREIGN KEY (`STATIC_LABEL_ID`) REFERENCES `ap_static_label` (`STATIC_LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=286259 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Languages for each of the Static Labels';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_unit` (
  `UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Unit of measure',
  `DIMENSION_ID` int unsigned NOT NULL COMMENT 'Foreign key that points to what type of Unit of measure this is ',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Notification for this Unit of measure',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`UNIT_ID`),
  UNIQUE KEY `fk_unit_unitCode` (`UNIT_CODE`),
  KEY `fk_unit_dimension_idx` (`DIMENSION_ID`),
  KEY `fk_unit_createdBy_idx` (`CREATED_BY`),
  KEY `fk_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_unit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_unit_dimension` FOREIGN KEY (`DIMENSION_ID`) REFERENCES `ap_dimension` (`DIMENSION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_unit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Units of measure used across the Application\nNote: Are based on the Application and can only be administered by an Applicaton level Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_usage_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_usage_period` (
  `USAGE_PERIOD_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage set',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CONVERT_TO_MONTH` decimal(13,8) NOT NULL COMMENT 'Multiply by this to convert the UsagePeriod to Months',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`USAGE_PERIOD_ID`),
  UNIQUE KEY `USAGE_PERIOD_ID_UNIQUE` (`USAGE_PERIOD_ID`),
  KEY `fk_ap_usage_period_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_usage_period_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_usage_period_labelId` (`LABEL_ID`),
  CONSTRAINT `fk_ap_usage_period_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_usage_period_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_usage_period_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_usage_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_usage_type` (
  `USAGE_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usageType, 1-For Discrete, 2-For Continuous',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`USAGE_TYPE_ID`),
  UNIQUE KEY `USAGE_TYPE_ID_UNIQUE` (`USAGE_TYPE_ID`),
  KEY `fk_ap_usage_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ap_usage_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ap_usage_type_labelId` (`LABEL_ID`),
  CONSTRAINT `fk_ap_usage_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_usage_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ap_usage_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_version_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_version_status` (
  `VERSION_STATUS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  PRIMARY KEY (`VERSION_STATUS_ID`),
  KEY `fk_ap_version_status_1_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_version_status_1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ap_version_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_version_type` (
  `VERSION_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_ID` int unsigned NOT NULL,
  PRIMARY KEY (`VERSION_TYPE_ID`),
  KEY `fk_ap_version_type_1_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ap_version_type_1` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_commit_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_commit_request` (
  `COMMIT_REQUEST_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `COMMITTED_VERSION_ID` int NOT NULL,
  `VERSION_TYPE_ID` int unsigned DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `SAVE_DATA` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `STATUS` int unsigned NOT NULL,
  `COMPLETED_DATE` datetime DEFAULT NULL,
  `STARTED_DATE` datetime DEFAULT NULL,
  `VERSION_ID` int DEFAULT NULL,
  `FAILED_REASON` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `JSON` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  PRIMARY KEY (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_supply_plan_commit_request_programId_idx` (`PROGRAM_ID`),
  KEY `fk_ct_supply_plan_commit_request_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ct_sp_commit_request_ap_version_type1_idx` (`VERSION_TYPE_ID`),
  CONSTRAINT `fk_ct_sp_commit_request_ap_version_type1_idx` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_supply_plan_commit_request_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_supply_plan_commit_request_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_consumption` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `CONSUMPTION_ID` int unsigned DEFAULT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `CONSUMPTION_DATE` date NOT NULL,
  `ACTUAL_FLAG` tinyint unsigned NOT NULL,
  `RCPU_QTY` double unsigned NOT NULL,
  `QTY` double unsigned NOT NULL,
  `DAYS_OF_STOCK_OUT` int unsigned NOT NULL,
  `DATA_SOURCE_ID` int unsigned NOT NULL,
  `NOTES` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  `VERSION_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_consumption_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_consumption_1_idx` (`CONSUMPTION_ID`),
  KEY `fk_ct_sp_consumption_2_idx` (`REGION_ID`),
  KEY `fk_ct_sp_consumption_3_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_ct_sp_consumption_4_idx` (`DATA_SOURCE_ID`),
  KEY `fk_ct_sp_consumption_5_idx` (`VERSION_ID`),
  CONSTRAINT `fk_ct_sp_consumption_1` FOREIGN KEY (`CONSUMPTION_ID`) REFERENCES `rm_consumption` (`CONSUMPTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_consumption_2` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_consumption_3` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_consumption_4` FOREIGN KEY (`DATA_SOURCE_ID`) REFERENCES `rm_data_source` (`DATA_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_consumption_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_consumption_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_consumption_batch_info` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `PARENT_ID` int unsigned NOT NULL,
  `CONSUMPTION_TRANS_BATCH_INFO_ID` int unsigned DEFAULT NULL,
  `CONSUMPTION_TRANS_ID` int unsigned DEFAULT NULL,
  `BATCH_ID` int NOT NULL,
  `BATCH_NO` varchar(26) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `AUTO_GENERATED` tinyint unsigned NOT NULL,
  `BATCH_CREATED_DATE` datetime NOT NULL,
  `EXPIRY_DATE` date NOT NULL,
  `BATCH_QTY` decimal(24,4) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_cbi_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_cbi_2_idx` (`CONSUMPTION_TRANS_BATCH_INFO_ID`),
  KEY `fk_ct_sp_cbi_3_idx` (`BATCH_ID`),
  CONSTRAINT `fk_ct_sp_cbi_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_inventory` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `INVENTORY_ID` int unsigned DEFAULT NULL,
  `INVENTORY_DATE` date NOT NULL,
  `REGION_ID` int unsigned DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `ACTUAL_QTY` bigint unsigned DEFAULT NULL,
  `ADJUSTMENT_QTY` bigint DEFAULT NULL,
  `EXPECTED_BAL` bigint DEFAULT NULL,
  `DATA_SOURCE_ID` int unsigned NOT NULL,
  `NOTES` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  `VERSION_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_inventory_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_supply_planinventory_1_idx` (`INVENTORY_ID`),
  KEY `fk_ct_sp_inventory_2_idx` (`REGION_ID`),
  KEY `fk_ct_sp_inventory_3_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID`),
  KEY `fk_ct_sp_inventory_4_idx` (`DATA_SOURCE_ID`),
  KEY `fk_ct_sp_inventory_5_idx` (`VERSION_ID`),
  CONSTRAINT `fk_ct_sp_inventory_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_inventory_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_inventory_batch_info` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `PARENT_ID` int unsigned NOT NULL,
  `INVENTORY_TRANS_BATCH_INFO_ID` int unsigned DEFAULT NULL,
  `INVENTORY_TRANS_ID` int unsigned DEFAULT NULL,
  `BATCH_ID` int NOT NULL,
  `BATCH_NO` varchar(26) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `AUTO_GENERATED` tinyint unsigned NOT NULL,
  `BATCH_CREATED_DATE` datetime NOT NULL,
  `EXPIRY_DATE` date NOT NULL,
  `ACTUAL_QTY` bigint unsigned DEFAULT NULL,
  `ADJUSTMENT_QTY` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_ibi_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_ibi_1_idx` (`INVENTORY_TRANS_ID`),
  KEY `fk_ct_sp_ibi_2_idx` (`INVENTORY_TRANS_BATCH_INFO_ID`),
  KEY `fk_ct_sp_ibi_3_idx` (`BATCH_ID`),
  CONSTRAINT `fk_ct_sp_ibi_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_problem_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_problem_report` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `PROBLEM_REPORT_ID` int unsigned DEFAULT NULL,
  `REALM_PROBLEM_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PROBLEM_TYPE_ID` int unsigned NOT NULL,
  `PROBLEM_STATUS_ID` int unsigned NOT NULL,
  `DATA1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA5` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `REVIEWED` tinyint unsigned NOT NULL,
  `REVIEW_NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `REVIEWED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_problem_report_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_problem_report_rm_realm_problem1_idx` (`REALM_PROBLEM_ID`),
  KEY `fk_ct_sp_problem_report_rm_program1_idx` (`PROGRAM_ID`),
  KEY `fk_ct_sp_problem_report_ap_problem_type1_idx` (`PROBLEM_TYPE_ID`),
  KEY `fk_ct_sp_problem_report_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  KEY `fk_ct_sp_problem_report_us_user1_idx` (`CREATED_BY`),
  KEY `fk_ct_sp_problem_report_us_user2_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_ct_sp_problem_report_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_problem_type1` FOREIGN KEY (`PROBLEM_TYPE_ID`) REFERENCES `ap_problem_type` (`PROBLEM_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_rm_realm_problem1` FOREIGN KEY (`REALM_PROBLEM_ID`) REFERENCES `rm_realm_problem` (`REALM_PROBLEM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_problem_report_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_problem_report_trans` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PARENT_ID` int unsigned NOT NULL,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `PROBLEM_REPORT_TRANS_ID` int unsigned DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `PROBLEM_STATUS_ID` int unsigned NOT NULL,
  `REVIEWED` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_problem_report_trans_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_problem_report_trans_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  KEY `fk_ct_sp_problem_report_trans_us_user1_idx` (`CREATED_BY`),
  CONSTRAINT `fk_ct_sp_problem_report_trans_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_trans_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ct_sp_problem_report_trans_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_shipment` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `SHIPMENT_ID` int unsigned DEFAULT NULL,
  `PARENT_SHIPMENT_ID` int unsigned DEFAULT NULL,
  `SUGGESTED_QTY` bigint unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned DEFAULT NULL,
  `FUNDING_SOURCE_ID` int unsigned DEFAULT NULL,
  `BUDGET_ID` int unsigned DEFAULT NULL,
  `ACCOUNT_FLAG` tinyint unsigned DEFAULT NULL,
  `ERP_FLAG` tinyint unsigned DEFAULT NULL,
  `CURRENCY_ID` int unsigned DEFAULT NULL,
  `CONVERSION_RATE_TO_USD` decimal(12,2) unsigned DEFAULT NULL,
  `EMERGENCY_ORDER` tinyint unsigned NOT NULL,
  `LOCAL_PROCUREMENT` tinyint unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `EXPECTED_DELIVERY_DATE` date NOT NULL,
  `PROCUREMENT_UNIT_ID` int unsigned DEFAULT NULL,
  `SUPPLIER_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_QTY` bigint unsigned DEFAULT NULL,
  `RATE` decimal(12,4) NOT NULL,
  `PRODUCT_COST` decimal(24,4) unsigned NOT NULL,
  `SHIPMENT_MODE` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `FREIGHT_COST` decimal(24,4) unsigned NOT NULL,
  `PLANNED_DATE` date DEFAULT NULL,
  `SUBMITTED_DATE` date DEFAULT NULL,
  `APPROVED_DATE` date DEFAULT NULL,
  `SHIPPED_DATE` date DEFAULT NULL,
  `ARRIVED_DATE` date DEFAULT NULL,
  `RECEIVED_DATE` date DEFAULT NULL,
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL,
  `DATA_SOURCE_ID` int unsigned NOT NULL,
  `NOTES` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ORDER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRIME_LINE_NO` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  `VERSION_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_shipment_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_shipment_1_idx` (`SHIPMENT_ID`),
  KEY `fk_ct_sp_shipment_2_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_ct_sp_shipment_3_idx` (`PROCUREMENT_UNIT_ID`),
  KEY `fk_ct_sp_shipment_4_idx` (`SUPPLIER_ID`),
  KEY `fk_ct_sp_shipment_5_idx` (`SHIPMENT_STATUS_ID`),
  KEY `fk_ct_sp_shipment_6_idx` (`ORDER_NO`),
  KEY `fk_ct_sp_shipment_7_idx` (`PRIME_LINE_NO`),
  KEY `fk_ct_sp_shipment_8_idx` (`DATA_SOURCE_ID`),
  KEY `fk_ct_sp_shipment_9_idx` (`VERSION_ID`),
  KEY `fk_ct_sp_shipment_10_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_ct_sp_shipment_11_idx` (`FUNDING_SOURCE_ID`),
  KEY `fk_ct_sp_shipment_12_idx` (`BUDGET_ID`),
  CONSTRAINT `fk_ct_sp_shipment_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `ct_supply_plan_shipment_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_supply_plan_shipment_batch_info` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `COMMIT_REQUEST_ID` int unsigned NOT NULL,
  `PARENT_ID` int unsigned NOT NULL,
  `SHIPMENT_TRANS_BATCH_INFO_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_TRANS_ID` int unsigned DEFAULT NULL,
  `BATCH_ID` int NOT NULL,
  `BATCH_NO` varchar(26) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `AUTO_GENERATED` tinyint unsigned NOT NULL,
  `BATCH_CREATED_DATE` datetime NOT NULL,
  `EXPIRY_DATE` date NOT NULL,
  `BATCH_SHIPMENT_QTY` bigint unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ct_sp_sbi_commitRequestId_idx` (`COMMIT_REQUEST_ID`),
  KEY `fk_ct_sp_sbi_1_idx` (`SHIPMENT_TRANS_ID`),
  KEY `fk_ct_sp_sbi_2_idx` (`SHIPMENT_TRANS_BATCH_INFO_ID`),
  KEY `fk_ct_sp_sbi_3_idx` (`BATCH_ID`),
  CONSTRAINT `fk_ct_sp_sbi_commitRequestId` FOREIGN KEY (`COMMIT_REQUEST_ID`) REFERENCES `ct_commit_request` (`COMMIT_REQUEST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `em_email_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `em_email_template` (
  `EMAIL_TEMPLATE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL_DESC` varchar(150) DEFAULT NULL,
  `SUBJECT` varchar(250) NOT NULL,
  `SUBJECT_PARAM` varchar(100) DEFAULT NULL,
  `EMAIL_BODY` text,
  `EMAIL_BODY_PARAM` varchar(500) DEFAULT NULL,
  `CC_TO` varchar(150) DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT '0',
  `TO_SEND` text,
  `BCC` text,
  PRIMARY KEY (`EMAIL_TEMPLATE_ID`),
  KEY `fk_em_email_template_us_user1_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_em_email_template_us_user2_idx` (`CREATED_BY`),
  CONSTRAINT `fk_em_email_template_us_user1` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_em_email_template_us_user2` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `em_emailer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `em_emailer` (
  `EMAILER_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TO_SEND` longtext,
  `SUBJECT` longtext,
  `BODY` longtext,
  `CC_SEND_TO` longtext,
  `CREATED_DATE` datetime DEFAULT NULL,
  `TO_SEND_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ATTEMPTS` int unsigned DEFAULT '0',
  `STATUS` int unsigned DEFAULT '0' COMMENT '0-New,1-Sent,2-failed',
  `REASON` text,
  `BCC` text,
  PRIMARY KEY (`EMAILER_ID`),
  KEY `idxStatus` (`STATUS`),
  KEY `idxCreatedDt` (`CREATED_DATE`),
  KEY `idxToSendDate` (`TO_SEND_DATE`)
) ENGINE=InnoDB AUTO_INCREMENT=2195 DEFAULT CHARSET=latin1 COMMENT='Table that stores all the emails that have been sent from the system';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `em_emailer_filepath_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `em_emailer_filepath_mapping` (
  `EMAILER_FILEPATH_MAPPING_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `EMAILER_ID` int unsigned NOT NULL,
  `FILE_ID` int unsigned NOT NULL,
  PRIMARY KEY (`EMAILER_FILEPATH_MAPPING_ID`),
  KEY `FK_emailer_filepath_mapping_emailer_id` (`EMAILER_ID`),
  KEY `FK_emailer_filepath_mapping_file_id` (`FILE_ID`),
  CONSTRAINT `fk_em_emailer_filepath_mapping_em_emailer1` FOREIGN KEY (`EMAILER_ID`) REFERENCES `em_emailer` (`EMAILER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_em_emailer_filepath_mapping_em_file_store` FOREIGN KEY (`FILE_ID`) REFERENCES `em_file_store` (`FILE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `em_file_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `em_file_store` (
  `FILE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `FILE_PATH` text NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `glb_consumption_all_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `glb_consumption_all_table` (
  `Product+Country+Level+PGroup` varchar(255) DEFAULT NULL,
  `ProductID` varchar(255) DEFAULT NULL,
  `ConsStartYear` varchar(255) DEFAULT NULL,
  `ConsStartMonth` varchar(255) DEFAULT NULL,
  `ConsActualFlag` varchar(255) DEFAULT NULL,
  `ConsNumMonths` varchar(255) DEFAULT NULL,
  `ConsAmount` varchar(255) DEFAULT NULL,
  `ConsDataSourceID` varchar(255) DEFAULT NULL,
  `ConsIflator` varchar(255) DEFAULT NULL,
  `ConsNote` text,
  `ConsDateChanged` varchar(255) DEFAULT NULL,
  `ConsID` varchar(255) DEFAULT NULL,
  `ConsDisplayNote` varchar(255) DEFAULT NULL,
  `Old Consumption` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Level` varchar(255) DEFAULT NULL,
  `Product_Group` varchar(255) DEFAULT NULL,
  `Quarter` varchar(255) DEFAULT NULL,
  `PSM SKU` varchar(255) DEFAULT NULL,
  `UOM` varchar(255) DEFAULT NULL,
  `Updated By` varchar(255) DEFAULT NULL,
  `Date Updated` varchar(255) DEFAULT NULL,
  `SKU Length` varchar(255) DEFAULT NULL,
  `Important Lab` varchar(255) DEFAULT NULL,
  `Michelle TO3 Prod Mapping` varchar(255) DEFAULT NULL,
  `Date` varchar(255) DEFAULT NULL,
  `USAID ARV Tier` varchar(255) DEFAULT NULL,
  `Person Indicator` varchar(255) DEFAULT NULL,
  `Primary Type` varchar(255) DEFAULT NULL,
  `Adjusted Person Indicator` varchar(255) DEFAULT NULL,
  `EQUIVALENCY MEASURES` varchar(255) DEFAULT NULL,
  `CYP` varchar(255) DEFAULT NULL,
  `Adjusted CYP` varchar(255) DEFAULT NULL,
  `Consumption_Person Indicator` varchar(255) DEFAULT NULL,
  `Consumption_Adjusted Person Indicator` varchar(255) DEFAULT NULL,
  `Consumption_EQUIVALENCY MEASURES` varchar(255) DEFAULT NULL,
  `Consumption_CYP` varchar(255) DEFAULT NULL,
  `Consumption_Adjusted CYP` varchar(255) DEFAULT NULL,
  `UOM Quantity` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `glb_inv_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `glb_inv_table` (
  `ProductID` varchar(255) DEFAULT NULL,
  `Period` varchar(255) DEFAULT NULL,
  `InvAmount` varchar(255) DEFAULT NULL,
  `InvTransferFlag` varchar(255) DEFAULT NULL,
  `InvNote` text,
  `InvDateChanged` varchar(255) DEFAULT NULL,
  `ctrIndex` varchar(255) DEFAULT NULL,
  `InvDisplayNote` text,
  `InvDataSourceID` varchar(255) DEFAULT NULL,
  `fImported` varchar(255) DEFAULT NULL,
  `Old Inventory` varchar(255) DEFAULT NULL,
  `QAT_Planning_Unit` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Product_Group` varchar(255) DEFAULT NULL,
  `Level` varchar(255) DEFAULT NULL,
  `Quarter` varchar(255) DEFAULT NULL,
  `File Year` varchar(255) DEFAULT NULL,
  `Product+Country+Level+PGroup` varchar(255) DEFAULT NULL,
  `Month` varchar(255) DEFAULT NULL,
  `Year` varchar(255) DEFAULT NULL,
  `InventoryID` varchar(255) DEFAULT NULL,
  `Old_Inventory` varchar(255) DEFAULT NULL,
  `Inventory ID` varchar(255) DEFAULT NULL,
  `PSM SKU` varchar(255) DEFAULT NULL,
  `UOM` varchar(255) DEFAULT NULL,
  `Updated By` varchar(255) DEFAULT NULL,
  `Date Updated` varchar(255) DEFAULT NULL,
  `SKU Length` varchar(255) DEFAULT NULL,
  `Important Lab` varchar(255) DEFAULT NULL,
  `Michelle TO3 Prod Mapping` varchar(255) DEFAULT NULL,
  `UOM Quantity` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `glb_price_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `glb_price_mapping` (
  `Country Code` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Product SKU` varchar(255) DEFAULT NULL,
  `Price` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `glb_product_psm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `glb_product_psm` (
  `ProductID` varchar(255) DEFAULT NULL,
  `ProductName` varchar(255) DEFAULT NULL,
  `ProductMinMonths` varchar(255) DEFAULT NULL,
  `ProductMaxMonths` varchar(255) DEFAULT NULL,
  `SupplierID` varchar(255) DEFAULT NULL,
  `MethodID` varchar(255) DEFAULT NULL,
  `ProductActiveFlag` varchar(255) DEFAULT NULL,
  `ProductActiveDate` varchar(255) DEFAULT NULL,
  `DefaultCaseSize` varchar(255) DEFAULT NULL,
  `ProductNote` text,
  `ProdCMax` varchar(255) DEFAULT NULL,
  `ProdCMin` varchar(255) DEFAULT NULL,
  `ProdDesStock` varchar(255) DEFAULT NULL,
  `txtInnovatorDrugName` varchar(255) DEFAULT NULL,
  `dblLowestUnitQty` varchar(255) DEFAULT NULL,
  `txtLowestUnitMeasure` varchar(255) DEFAULT NULL,
  `txtSubstitutionList` varchar(255) DEFAULT NULL,
  `fPermittedInCountry` varchar(255) DEFAULT NULL,
  `memAvailabilityNotes` varchar(255) DEFAULT NULL,
  `fAvailabilityStatus` varchar(255) DEFAULT NULL,
  `fUserDefined` varchar(255) DEFAULT NULL,
  `strImportSource` varchar(255) DEFAULT NULL,
  `BUConversion` varchar(255) DEFAULT NULL,
  `txtPreferenceNotes` varchar(255) DEFAULT NULL,
  `lngAMCStart` varchar(255) DEFAULT NULL,
  `lngAMCMonths` varchar(255) DEFAULT NULL,
  `fAMCChanged` varchar(255) DEFAULT NULL,
  `txtMigrationStatus` varchar(255) DEFAULT NULL,
  `txtMigrationStatusDate` varchar(255) DEFAULT NULL,
  `strType` varchar(255) DEFAULT NULL,
  `OldProductID` varchar(255) DEFAULT NULL,
  `OldProductName` varchar(255) DEFAULT NULL,
  `lngBatch` varchar(255) DEFAULT NULL,
  `OldMethodID` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Product_Group` varchar(255) DEFAULT NULL,
  `Level` varchar(255) DEFAULT NULL,
  `Quarter` varchar(255) DEFAULT NULL,
  `File Year` varchar(255) DEFAULT NULL,
  `Product+Country+Level+PGroup` varchar(255) DEFAULT NULL,
  `Placeholder1` varchar(255) DEFAULT NULL,
  `Placeholder2` varchar(255) DEFAULT NULL,
  `Placeholder3` varchar(255) DEFAULT NULL,
  `PSM SKU` varchar(255) DEFAULT NULL,
  `UOM` varchar(255) DEFAULT NULL,
  `Updated By` varchar(255) DEFAULT NULL,
  `Date Updated` varchar(255) DEFAULT NULL,
  `SKU Length` varchar(255) DEFAULT NULL,
  `Important Lab` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `glb_shipment_w_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `glb_shipment_w_orders` (
  `ShipmentID` varchar(255) DEFAULT NULL,
  `ProductID` varchar(255) DEFAULT NULL,
  `SupplierID` varchar(255) DEFAULT NULL,
  `ShipDataSourceID` varchar(255) DEFAULT NULL,
  `ShipAmount` varchar(255) DEFAULT NULL,
  `UOM` varchar(255) DEFAULT NULL,
  `UOM ShipAmount` varchar(255) DEFAULT NULL,
  `Estimated Lab GAD` varchar(255) DEFAULT NULL,
  `ShipPlannedDate` varchar(255) DEFAULT NULL,
  `ShipOrderedDate` varchar(255) DEFAULT NULL,
  `ShipShippedDate` varchar(255) DEFAULT NULL,
  `ShipReceivedDate` varchar(255) DEFAULT NULL,
  `ShipStatusCode` varchar(255) DEFAULT NULL,
  `ShipNote` text,
  `ShipDateChanged` varchar(255) DEFAULT NULL,
  `ShipFreightCost` varchar(255) DEFAULT NULL,
  `ShipValue` varchar(255) DEFAULT NULL,
  `ShipCaseLot` varchar(255) DEFAULT NULL,
  `ShipDisplayNote` text,
  `ShipPO` varchar(255) DEFAULT NULL,
  `ShipFundingSourceID` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Country Name` varchar(255) DEFAULT NULL,
  `Product_Group` varchar(255) DEFAULT NULL,
  `Level` varchar(255) DEFAULT NULL,
  `Quarter` varchar(255) DEFAULT NULL,
  `File Year` varchar(255) DEFAULT NULL,
  `File Name` varchar(255) DEFAULT NULL,
  `Product+Country+Level+PGroup` varchar(255) DEFAULT NULL,
  `Month` varchar(255) DEFAULT NULL,
  `Year` varchar(255) DEFAULT NULL,
  `Translated_Funder` varchar(255) DEFAULT NULL,
  `PSM SKU` varchar(255) DEFAULT NULL,
  `PipeLine Name` varchar(255) DEFAULT NULL,
  `ARTMIS Name` varchar(255) DEFAULT NULL,
  `Default Supplier` varchar(255) DEFAULT NULL,
  `Tracer Category` varchar(255) DEFAULT NULL,
  `SAID ARV Tier` varchar(255) DEFAULT NULL,
  `Intervention Type` varchar(255) DEFAULT NULL,
  `Function` varchar(255) DEFAULT NULL,
  `Tests` varchar(255) DEFAULT NULL,
  `Platform Supplier` varchar(255) DEFAULT NULL,
  `Incoterm` varchar(255) DEFAULT NULL,
  `Total # of Test` varchar(255) DEFAULT NULL,
  `Estimated Unit Price` varchar(255) DEFAULT NULL,
  `Estimated Line Price` varchar(255) DEFAULT NULL,
  `Commodity Council` varchar(255) DEFAULT NULL,
  `Task Order` varchar(255) DEFAULT NULL,
  `Max Leadtime (wk)` varchar(255) DEFAULT NULL,
  `Target Order Entry Date` varchar(255) DEFAULT NULL,
  `Fiscal Year` varchar(255) DEFAULT NULL,
  `MOP Year` varchar(255) DEFAULT NULL,
  `Michelle's TO3 Prod Mapping` varchar(255) DEFAULT NULL,
  `Update Date` varchar(255) DEFAULT NULL,
  `Update User Nam` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `LOG_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `UPDATED_DATE` datetime NOT NULL,
  `DESC` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=305162 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `logging_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event` (
  `timestmp` bigint NOT NULL,
  `formatted_message` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `logger_name` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `level_string` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `thread_name` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `reference_flag` smallint DEFAULT NULL,
  `arg0` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `arg1` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `arg2` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `arg3` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `caller_filename` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `caller_class` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `caller_method` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `caller_line` char(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `logging_event_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event_exception` (
  `event_id` bigint NOT NULL,
  `i` smallint NOT NULL,
  `trace_line` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`event_id`,`i`),
  CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `logging_event_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logging_event_property` (
  `event_id` bigint NOT NULL,
  `mapped_key` varchar(254) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `mapped_value` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  PRIMARY KEY (`event_id`,`mapped_key`),
  CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `mn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mn` (
  `MONTH` date NOT NULL,
  PRIMARY KEY (`MONTH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_ap_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_ap_label` (
  `LABEL_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL_EN` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LABEL_FR` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LABEL_SP` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LABEL_PR` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`LABEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_consumption` (
  `QAT_TEMP_CONSUMPTION_ID` int NOT NULL AUTO_INCREMENT,
  `REGION_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CONSUMPTION_DATE` date DEFAULT NULL,
  `PLANNING_UNIT_ID` varchar(225) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DAYS_OF_STOCK_OUT` int DEFAULT NULL,
  `DATA_SOURCE_ID` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `PIPELINE_ID` int DEFAULT NULL,
  `CONSUMPTION_QUANTITY` decimal(24,4) DEFAULT NULL,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTUAL_FLAG` tinyint DEFAULT NULL,
  `MULTIPLIER` decimal(12,2) DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int DEFAULT NULL,
  PRIMARY KEY (`QAT_TEMP_CONSUMPTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_data_source` (
  `QAT_DATASOURCE_ID` int NOT NULL AUTO_INCREMENT,
  `PIPELINE_DATA_SOURCE_TYPE` varchar(500) DEFAULT NULL,
  `PIPELINE_DATA_SOURCE_ID` varchar(500) DEFAULT NULL,
  `DATA_SOURCE_ID` int DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  PRIMARY KEY (`QAT_DATASOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_funding_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_funding_source` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `PIPELINE_FUNDING_SOURCE_ID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FUNDING_SOURCE_ID` int DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_inventory` (
  `QAT_TEMP_INVENTORY_ID` int NOT NULL AUTO_INCREMENT,
  `INVENTORY_DATE` date DEFAULT NULL,
  `REGION_ID` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PLANNING_UNIT_ID` varchar(225) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ACTUAL_QTY` bigint DEFAULT NULL,
  `ADJUSTMENT_QTY` bigint DEFAULT NULL,
  `DATA_SOURCE_ID` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  `MULTIPLIER` decimal(12,2) DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int DEFAULT NULL,
  PRIMARY KEY (`QAT_TEMP_INVENTORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_procurement_agent` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `PIPELINE_PROCUREMENT_AGENT_ID` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_program` (
  `PROGRAM_ID` int NOT NULL AUTO_INCREMENT,
  `REALM_COUNTRY_ID` int DEFAULT NULL,
  `ORGANISATION_ID` int DEFAULT NULL,
  `HEALTH_AREA_ID` int DEFAULT NULL,
  `PROGRAM_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROGRAM_MANAGER_USER_ID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROGRAM_NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `AIR_FREIGHT_PERC` decimal(12,2) DEFAULT NULL,
  `SEA_FREIGHT_PERC` decimal(12,2) DEFAULT NULL,
  `PLANNED_TO_SUBMITTED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SUBMITTED_TO_APPROVED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `DELIVERED_TO_RECEIVED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `MONTHS_IN_PAST_FOR_AMC` int DEFAULT NULL,
  `MONTHS_IN_FUTURE_FOR_AMC` int DEFAULT NULL,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LABEL_ID` int DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `ARRIVED_TO_DELIVERED_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SHELF_LIFE` decimal(12,0) DEFAULT NULL,
  `PROGRAM_CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_program_healthArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_program_healthArea` (
  `PROGRAM_HEALTH_AREA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `HEALTH_AREA_ID` int DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_HEALTH_AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_program_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_program_planning_unit` (
  `QAT_TEM_PLANNING_UNIT_ID` int NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int DEFAULT NULL,
  `PLANNING_UNIT_ID` varchar(225) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `MULTIPLIER` double(16,6) DEFAULT NULL,
  `REORDER_FREQUENCY_IN_MONTHS` int DEFAULT NULL,
  `MIN_MONTHS_OF_STOCK` int DEFAULT NULL,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  `PIPELINE_PRODUCT_ID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `LOCAL_PROCUREMENT_LEAD_TIME` decimal(12,2) DEFAULT NULL,
  `SHELF_LIFE` int DEFAULT NULL,
  `CATALOG_PRICE` decimal(12,2) DEFAULT NULL,
  `MONTHS_IN_FUTURE_FOR_AMC` int DEFAULT NULL,
  `MONTHS_IN_PAST_FOR_AMC` int DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`QAT_TEM_PLANNING_UNIT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_program_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_program_region` (
  `PROGRAM_REGION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REGION_ID` int DEFAULT NULL,
  `PIPELINE_ID` int DEFAULT NULL,
  `CREATED_BY` int DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_BY` int DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTIVE` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_REGION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `qat_temp_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qat_temp_shipment` (
  `SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PLANNING_UNIT_ID` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `EXPECTED_DELIVERY_DATE` date DEFAULT NULL,
  `SUGGESTED_QTY` decimal(24,4) unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `PROCUREMENT_UNIT_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `SUPPLIER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `QUANTITY` decimal(24,4) unsigned DEFAULT NULL,
  `RATE` decimal(24,4) DEFAULT NULL,
  `PRODUCT_COST` decimal(24,4) unsigned DEFAULT NULL,
  `SHIPPING_MODE` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FREIGHT_COST` decimal(24,4) unsigned DEFAULT NULL,
  `PLANNED_DATE` date DEFAULT NULL,
  `SUBMITTED_DATE` date DEFAULT NULL,
  `APPROVED_DATE` date DEFAULT NULL,
  `ARRIVED_DATE` date DEFAULT NULL,
  `ORDERED_DATE` date DEFAULT NULL,
  `SHIPPED_DATE` date DEFAULT NULL,
  `RECEIVED_DATE` date DEFAULT NULL,
  `SHIPMENT_STATUS_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `DATA_SOURCE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `FUNDING_SOURCE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `CREATED_BY` int NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `PIPELINE_ID` int NOT NULL,
  PRIMARY KEY (`SHIPMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_batch_info` (
  `BATCH_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `BATCH_NO` varchar(26) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXPIRY_DATE` date NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `TMP_ID` int DEFAULT NULL,
  `AUTO_GENERATED` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`BATCH_ID`),
  UNIQUE KEY `unq_batchNo` (`PROGRAM_ID`,`BATCH_NO`,`EXPIRY_DATE`),
  KEY `fk_rm_batch_info_1_idx` (`PROGRAM_ID`),
  KEY `fk_rm_batch_info_2_idx` (`PLANNING_UNIT_ID`),
  KEY `idx_batchNo` (`BATCH_NO`),
  KEY `idx_batchInfo_createdDt` (`CREATED_DATE`),
  CONSTRAINT `fk_rm_batch_info_1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_batch_info_2` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15833 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_batch_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_batch_inventory` (
  `BATCH_INVENTORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `INVENTORY_DATE` date NOT NULL,
  `MAX_VERSION_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `TMP_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`BATCH_INVENTORY_ID`),
  KEY `fk_rm_batch_inventory_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_batch_inventory_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `idx_rm_batch_inventory_inventoryDate` (`INVENTORY_DATE`),
  KEY `idx_rm_batch_inventory_maxVersionId` (`MAX_VERSION_ID`),
  KEY `fk_rm_batch_inventory_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_batch_inventory_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_batch_inventory_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_rm_batch_inventory_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_rm_batch_inventory_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_rm_batch_inventory_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_batch_inventory_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_batch_inventory_trans` (
  `BATCH_INVENTORY_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `BATCH_INVENTORY_ID` int unsigned NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `QTY` decimal(24,8) unsigned DEFAULT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1',
  `VERSION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`BATCH_INVENTORY_TRANS_ID`),
  KEY `fk_rm_batch_inventory_trans_batchInventoryId_idx` (`BATCH_INVENTORY_ID`),
  KEY `fk_rm_batch_inventory_trans_batchId_idx` (`BATCH_ID`),
  KEY `fk_rm_batch_inventory_trans_lmb_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_rm_batch_inventory_trans_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_batchId` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_batchInventoryId` FOREIGN KEY (`BATCH_INVENTORY_ID`) REFERENCES `rm_batch_inventory` (`BATCH_INVENTORY_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_lmb` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_budget` (
  `BUDGET_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Budget',
  `BUDGET_CODE` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realmthat this budget is for',
  `FUNDING_SOURCE_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Sub Funding Source this Budget belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CURRENCY_ID` int unsigned NOT NULL,
  `BUDGET_AMT` decimal(24,4) unsigned NOT NULL COMMENT 'The total Budget amt approved for this Budget',
  `CONVERSION_RATE_TO_USD` decimal(14,4) unsigned NOT NULL,
  `START_DATE` date DEFAULT NULL COMMENT 'Start date for the Budget',
  `STOP_DATE` date DEFAULT NULL COMMENT 'Stop date for the Budget',
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Budget is Active. False indicates this Budget has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`BUDGET_ID`),
  KEY `fk_budget_labelId1_idx` (`LABEL_ID`),
  KEY `fk_budget_userId1_idx` (`CREATED_BY`),
  KEY `fk_budget_userId2_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_budget_fundingSourceId_idx` (`FUNDING_SOURCE_ID`),
  KEY `fk_budget_currencyId_idx` (`CURRENCY_ID`),
  KEY `idx_budget_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_budget_realmId_idx` (`REALM_ID`),
  CONSTRAINT `fk_budget_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_budget_currencyId` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `ap_currency` (`CURRENCY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_budget_fundingSourceId` FOREIGN KEY (`FUNDING_SOURCE_ID`) REFERENCES `rm_funding_source` (`FUNDING_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_budget_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_budget_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_budget_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=592 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the budgets for a Sub-Funding Source\nNote: Are based on a Realm and can be created by a Realm level admin through a Ticket request';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_budget_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_budget_program` (
  `BUDGET_PROGRAM_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `BUDGET_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  PRIMARY KEY (`BUDGET_PROGRAM_ID`),
  UNIQUE KEY `unq_budget_program_budgetId_programId` (`BUDGET_ID`,`PROGRAM_ID`),
  KEY `fk_budget_program_budgetId_idx` (`BUDGET_ID`),
  KEY `fk_budget_program_programId_idx` (`PROGRAM_ID`),
  CONSTRAINT `fk_budget_program_budgetId` FOREIGN KEY (`BUDGET_ID`) REFERENCES `rm_budget` (`BUDGET_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_budget_program_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=660 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_consumption` (
  `CONSUMPTION_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Consumption that is entered',
  `PROGRAM_ID` int unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  `MAX_VERSION_ID` int unsigned NOT NULL,
  `TMP_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`CONSUMPTION_ID`),
  KEY `fk_consumption_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_consumption_createdBy_idx` (`CREATED_BY`),
  KEY `fk_consumption_programId_idx` (`PROGRAM_ID`),
  CONSTRAINT `fk_consumption_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumption_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumption_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=67691 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the Consumption of Products\nNote: Could be manually fed or can come from feeds from eLMIS';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_consumption_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_consumption_trans` (
  `CONSUMPTION_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CONSUMPTION_ID` int unsigned NOT NULL COMMENT 'Unique Id for each Consumption that is entered',
  `REGION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `CONSUMPTION_DATE` date NOT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `ACTUAL_FLAG` tinyint unsigned NOT NULL,
  `CONSUMPTION_QTY` decimal(24,8) NOT NULL,
  `CONSUMPTION_RCPU_QTY` decimal(24,8) NOT NULL,
  `DAYS_OF_STOCK_OUT` int unsigned NOT NULL,
  `DATA_SOURCE_ID` int unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ACTIVE` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  `VERSION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`CONSUMPTION_TRANS_ID`),
  UNIQUE KEY `unq_consumptionTrans_unq1` (`VERSION_ID`,`CONSUMPTION_ID`),
  KEY `fk_consumptionTrans_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_consumptionTrans_regionId_idx` (`REGION_ID`),
  KEY `fk_consumptionTrans_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_consumptionTrans_dataSourceId_idx` (`DATA_SOURCE_ID`),
  KEY `fk_consumptionTrans_consumptionId_idx` (`CONSUMPTION_ID`),
  KEY `idx_consumptionTrans_versionId` (`VERSION_ID`),
  KEY `fk_rm_consumption_trans_rm_realm_country_planning_unit1_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID`),
  CONSTRAINT `fk_consumptionTrans_consumptionId` FOREIGN KEY (`CONSUMPTION_ID`) REFERENCES `rm_consumption` (`CONSUMPTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumptionTrans_dataSourceId` FOREIGN KEY (`DATA_SOURCE_ID`) REFERENCES `rm_data_source` (`DATA_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumptionTrans_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumptionTrans_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_consumptionTrans_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_consumption_trans_rm_realm_country_planning_unit1` FOREIGN KEY (`REALM_COUNTRY_PLANNING_UNIT_ID`) REFERENCES `rm_realm_country_planning_unit` (`REALM_COUNTRY_PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=68298 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the Consumption of Products\nNote: Could be manually fed or can come from feeds from eLMIS';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_consumption_trans_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_consumption_trans_batch_info` (
  `CONSUMPTION_TRANS_BATCH_INFO_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CONSUMPTION_TRANS_ID` int unsigned NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `CONSUMPTION_QTY` decimal(24,8) NOT NULL,
  PRIMARY KEY (`CONSUMPTION_TRANS_BATCH_INFO_ID`),
  KEY `fk_rm_consumption_trans_batch_info_rm_consumption_trans1_idx` (`CONSUMPTION_TRANS_ID`),
  KEY `fk_rm_consumption_trans_batch_info_rm_batch_info1_idx` (`BATCH_ID`),
  CONSTRAINT `fk_rm_consumption_trans_batch_info_rm_batch_info1` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_consumption_trans_batch_info_rm_consumption_trans1` FOREIGN KEY (`CONSUMPTION_TRANS_ID`) REFERENCES `rm_consumption_trans` (`CONSUMPTION_TRANS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_data_source` (
  `DATA_SOURCE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Data source ',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Will always have a Realm. ',
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `DATA_SOURCE_TYPE_ID` int unsigned NOT NULL COMMENT 'Foreign key for Data Source Type Id',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'If this Data Source Type is created by a Program it remains local to that Program only.',
  PRIMARY KEY (`DATA_SOURCE_ID`),
  KEY `fk_data_source_dataSourceTypeId_idx` (`DATA_SOURCE_TYPE_ID`),
  KEY `fk_data_source_createdBy_idx` (`CREATED_BY`),
  KEY `fk_data_source_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_data_source_labelId_idx` (`LABEL_ID`),
  KEY `fk_ap_data_source_rm_realm1_idx` (`REALM_ID`),
  KEY `fk_rm_data_source_rm_program1_idx` (`PROGRAM_ID`),
  KEY `idx_dataSource_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_ap_data_source_rm_realm1` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_data_source_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_data_source_dataSourceTypeId` FOREIGN KEY (`DATA_SOURCE_TYPE_ID`) REFERENCES `rm_data_source_type` (`DATA_SOURCE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_data_source_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_data_source_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_data_source_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table to capture the Data Source\nNote: To be used in Shipments, Inventory and Consumption tables to identify where the data came from. Application level field so can only be administered by Application Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_data_source_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_data_source_type` (
  `DATA_SOURCE_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Data Source Type',
  `REALM_ID` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by	',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`DATA_SOURCE_TYPE_ID`),
  KEY `fk_data_source_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_data_source_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_data_source_type_rm_realm1_idx` (`REALM_ID`),
  KEY `idx_dataSourceType_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_data_source_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_data_source_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_data_source_type_rm_realm1` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Super Classification of data_source\nNote: There are 3 DataSourceTypes Inventory, Shipment and Consumption';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_dataset_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_dataset_planning_unit` (
  `PROGRAM_PLANNING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `CONSUMPTION_FORECAST` tinyint unsigned NOT NULL,
  `TREE_FORECAST` tinyint unsigned NOT NULL,
  `STOCK` int DEFAULT NULL,
  `EXISTING_SHIPMENTS` int DEFAULT NULL,
  `MONTHS_OF_STOCK` int unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned DEFAULT NULL,
  `PRICE` decimal(16,4) unsigned DEFAULT NULL,
  `HIGHER_THEN_CONSUMPTION_THRESHOLD` decimal(14,2) DEFAULT NULL,
  `LOWER_THEN_CONSUMPTION_THRESHOLD` decimal(14,2) DEFAULT NULL,
  `PLANNING_UNIT_NOTES` text,
  `CONSUMPTION_NOTES` text,
  `CONSUMPTION_DATA_TYPE_ID` int unsigned DEFAULT NULL COMMENT 'null=Not a Consumption Unit, 1=Forecast, 2=PlanningUnit, 3=Other Unit',
  `OTHER_LABEL_ID` int unsigned DEFAULT NULL,
  `OTHER_MULTIPLIER` decimal(16,4) unsigned DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`),
  KEY `fk_rm_dataset_planning_unit_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_dataset_planning_unit_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_dataset_planning_unit_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `idx_rm_dataset_planning_unit_versionId` (`VERSION_ID`),
  KEY `fk_rm_dataset_planning_unit_otherLabelId_idx` (`OTHER_LABEL_ID`),
  KEY `idx_rm_dataset_planning_unit_consumptionDataTypeId` (`CONSUMPTION_DATA_TYPE_ID`),
  KEY `fk_rm_dataset_planning_unit_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_rm_dataset_planning_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_otherLabelId` FOREIGN KEY (`OTHER_LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2036 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_dataset_planning_unit_selected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_dataset_planning_unit_selected` (
  `PROGRAM_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `CONSUMPTION_EXTRAPOLATION_ID` int unsigned DEFAULT NULL,
  `TOTAL_FORECAST` double(16,2) unsigned DEFAULT NULL,
  `NOTES` text,
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`,`REGION_ID`),
  KEY `fk_rm_dataset_planning_unit_selected_regionId_idx` (`REGION_ID`),
  KEY `fk_rm_dataset_planning_unit_selected_extrapolationSettingsI_idx` (`CONSUMPTION_EXTRAPOLATION_ID`),
  CONSTRAINT `fk_rm_dataset_planning_unit_selected_consumptionExtrapolationId` FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_ID`) REFERENCES `rm_forecast_consumption_extrapolation` (`CONSUMPTION_EXTRAPOLATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_selected_programPlanningUnitId` FOREIGN KEY (`PROGRAM_PLANNING_UNIT_ID`) REFERENCES `rm_dataset_planning_unit` (`PROGRAM_PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_dataset_planning_unit_selected_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_dataset_planning_unit_selected_tree_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_dataset_planning_unit_selected_tree_list` (
  `PROGRAM_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `TREE_ID` int unsigned NOT NULL,
  `SCENARIO_ID` int unsigned NOT NULL,
  KEY `fk_dpustl_programPlanningUnitId_idx` (`PROGRAM_PLANNING_UNIT_ID`),
  KEY `fk_dpustl_regionId_idx` (`REGION_ID`),
  KEY `fkdpustl_treeId_idx` (`TREE_ID`),
  KEY `fk_dpustl_scenarioId_idx` (`SCENARIO_ID`),
  CONSTRAINT `fk_dpustl_programPlanningUnitId` FOREIGN KEY (`PROGRAM_PLANNING_UNIT_ID`) REFERENCES `rm_dataset_planning_unit` (`PROGRAM_PLANNING_UNIT_ID`),
  CONSTRAINT `fk_dpustl_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`),
  CONSTRAINT `fk_dpustl_scenarioId` FOREIGN KEY (`SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`),
  CONSTRAINT `fkdpustl_treeId` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_equivalency_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_equivalency_unit` (
  `EQUIVALENCY_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each EquivalencyUnit',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Realm that this record belongs to',
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `NOTES` text,
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_ID`),
  UNIQUE KEY `EQUIVALENCY_UNIT_ID_UNIQUE` (`EQUIVALENCY_UNIT_ID`),
  KEY `fk_rm_equivalency_unit_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_equivalency_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_equivalency_unit_realmId` (`REALM_ID`),
  KEY `fk_rm_equivalency_unit_labelId` (`LABEL_ID`),
  KEY `fk_rm_equivalency_unit_programId_idx` (`PROGRAM_ID`),
  CONSTRAINT `fk_rm_equivalency_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_unit_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_unit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_unit_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_unit_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_equivalency_unit_health_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_equivalency_unit_health_area` (
  `EQUIVALENCY_UNIT_HEALTH_AREA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `EQUIVALENCY_UNIT_ID` int unsigned NOT NULL,
  `HEALTH_AREA_ID` int unsigned NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_HEALTH_AREA_ID`),
  KEY `fk_rm_equivalency_unit_health_area_equivalencyUnitId_idx` (`EQUIVALENCY_UNIT_ID`),
  KEY `fk_rm_equivalency_unit_health_area_healthAreaId_idx` (`HEALTH_AREA_ID`),
  CONSTRAINT `fk_rm_equivalency_unit_health_area_equivalencyUnitId` FOREIGN KEY (`EQUIVALENCY_UNIT_ID`) REFERENCES `rm_equivalency_unit` (`EQUIVALENCY_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_unit_health_area_healthAreaId` FOREIGN KEY (`HEALTH_AREA_ID`) REFERENCES `rm_health_area` (`HEALTH_AREA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_equivalency_unit_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_equivalency_unit_mapping` (
  `EQUIVALENCY_UNIT_MAPPING_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `EQUIVALENCY_UNIT_ID` int unsigned NOT NULL,
  `FORECASTING_UNIT_ID` int unsigned NOT NULL,
  `CONVERT_TO_EU` decimal(22,8) unsigned NOT NULL,
  `NOTES` text,
  `REALM_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`EQUIVALENCY_UNIT_MAPPING_ID`),
  UNIQUE KEY `unq_rm_equivalency_mapping_uniqueRule` (`REALM_ID`,`EQUIVALENCY_UNIT_ID`,`FORECASTING_UNIT_ID`,`PROGRAM_ID`),
  KEY `fk_rm_equivalency_mapping_equivalencyUnitId_idx` (`EQUIVALENCY_UNIT_ID`),
  KEY `fk_rm_equivalency_mapping_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_rm_equivalency_mapping_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_equivalency_mapping_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_equivalency_mapping_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_equivalency_mapping_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_equivalency_mapping_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_mapping_equivalencyUnitId` FOREIGN KEY (`EQUIVALENCY_UNIT_ID`) REFERENCES `rm_equivalency_unit` (`EQUIVALENCY_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_mapping_forecastingUnitId` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_mapping_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_equivalency_mapping_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_notification` (
  `NOTIFICATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_TYPE_ID` int unsigned NOT NULL COMMENT '1-SKU CHANGE 2-CANCELLED',
  `ADDRESSED` tinyint unsigned NOT NULL DEFAULT '0',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '0',
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `SHIPMENT_LINKING_ID` int unsigned NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`),
  KEY `fk_rm_erp_notification_notificationTypeId_idx` (`NOTIFICATION_TYPE_ID`),
  KEY `fk_rm_erp_notification_shipmentLinkingId_idx` (`SHIPMENT_LINKING_ID`),
  KEY `fk_rm_erp_notification_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_erp_notification_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_erp_notification_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_notification_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_notification_notificationTypeId` FOREIGN KEY (`NOTIFICATION_TYPE_ID`) REFERENCES `ap_notification_type` (`NOTIFICATION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_notification_shipmentLinkingId` FOREIGN KEY (`SHIPMENT_LINKING_ID`) REFERENCES `rm_shipment_linking` (`SHIPMENT_LINKING_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_notification_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_notification_old` (
  `NOTIFICATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_TYPE_ID` int NOT NULL COMMENT '1-SKU CHANGE 2-CANCELLED',
  `ADDRESSED` tinyint(1) NOT NULL DEFAULT '0',
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int NOT NULL,
  `NOTES` text,
  `CONVERSION_FACTOR` decimal(12,4) DEFAULT NULL,
  `ERP_ORDER_ID` int NOT NULL,
  `CHILD_SHIPMENT_ID` int NOT NULL,
  `MANUAL_TAGGING_ID` int NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_order` (
  `ERP_ORDER_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `RO_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `RO_PRIME_LINE_NO` int unsigned NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRIME_LINE_NO` int DEFAULT NULL,
  `ORDER_TYPE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `PARENT_RO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PARENT_CREATED_DATE` datetime DEFAULT NULL,
  `PLANNING_UNIT_SKU_CODE` varchar(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `QTY` bigint unsigned NOT NULL,
  `ORDERD_DATE` date DEFAULT NULL,
  `CURRENT_ESTIMATED_DELIVERY_DATE` date DEFAULT NULL,
  `REQ_DELIVERY_DATE` date DEFAULT NULL,
  `AGREED_DELIVERY_DATE` date DEFAULT NULL,
  `SUPPLIER_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRICE` decimal(14,4) NOT NULL,
  `SHIPPING_COST` decimal(14,4) DEFAULT NULL,
  `SHIP_BY` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `RECPIENT_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `RECPIENT_COUNTRY` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `FLAG` tinyint unsigned NOT NULL DEFAULT '0',
  `VERSION_ID` int DEFAULT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_ID` int unsigned DEFAULT NULL,
  `FILE_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`ERP_ORDER_ID`),
  KEY `idx_erp_order_orderNo` (`ORDER_NO`),
  KEY `idx_erp_order_primeLineNo` (`PRIME_LINE_NO`),
  KEY `idx_erp_order_planningUnitSkuCode` (`PLANNING_UNIT_SKU_CODE`),
  KEY `idx_erp_order_procurementUnitSkuCode` (`PROCUREMENT_UNIT_SKU_CODE`),
  KEY `idx_erp_order_recipientCountry` (`RECPIENT_COUNTRY`),
  KEY `idx_erp_order_status` (`STATUS`),
  KEY `fk_erp_order_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_erp_order_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_erp_order_shipmentId_idx` (`SHIPMENT_ID`),
  KEY `idx_erp_order_flag` (`FLAG`),
  KEY `idx_erp_order_fileName` (`FILE_NAME`),
  CONSTRAINT `fk_erp_order_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_order_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_order_shipmentId` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=612612 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_order_consolidated`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_order_consolidated` (
  `ERP_ORDER_ID` int unsigned NOT NULL,
  `RO_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `RO_PRIME_LINE_NO` int unsigned NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRIME_LINE_NO` int DEFAULT NULL,
  `ORDER_TYPE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `PARENT_RO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PARENT_CREATED_DATE` datetime DEFAULT NULL,
  `PLANNING_UNIT_SKU_CODE` varchar(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `QTY` bigint unsigned NOT NULL,
  `ORDERD_DATE` date DEFAULT NULL,
  `CURRENT_ESTIMATED_DELIVERY_DATE` date DEFAULT NULL,
  `REQ_DELIVERY_DATE` date DEFAULT NULL,
  `AGREED_DELIVERY_DATE` date DEFAULT NULL,
  `SUPPLIER_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRICE` decimal(14,4) NOT NULL,
  `SHIPPING_COST` decimal(14,4) DEFAULT NULL,
  `SHIP_BY` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `RECPIENT_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `RECPIENT_COUNTRY` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `VERSION_ID` int DEFAULT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_ID` int unsigned DEFAULT NULL,
  `FILE_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
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
  CONSTRAINT `fk_erp_order_consolidated_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_order_consolidated_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_erp_order_consolidated_shipmentId` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_shipment` (
  `ERP_SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ERP_ORDER_ID` int unsigned DEFAULT NULL,
  `KN_SHIPMENT_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PRIME_LINE_NO` int NOT NULL,
  `BATCH_NO` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXPIRY_DATE` date DEFAULT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SHIPPED_QTY` int DEFAULT NULL,
  `DELIVERED_QTY` bigint DEFAULT NULL,
  `ACTUAL_SHIPMENT_DATE` date DEFAULT NULL,
  `ACTUAL_DELIVERY_DATE` date DEFAULT NULL,
  `ARRIVAL_AT_DESTINATION_DATE` date DEFAULT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `FLAG` tinyint unsigned NOT NULL DEFAULT '0',
  `FILE_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`ERP_SHIPMENT_ID`),
  KEY `idx_artmisShipment_orderNo` (`ORDER_NO`),
  KEY `idx_artmisShipment_primeLineNo` (`PRIME_LINE_NO`),
  KEY `fk_rm_artmis_shipment_rm_erp_order1_idx` (`ERP_ORDER_ID`),
  KEY `idx_erp_shipment_flag` (`FLAG`),
  KEY `idx_erp_shipment_fileName` (`FILE_NAME`),
  CONSTRAINT `fk_rm_artmis_shipment_rm_erp_order1` FOREIGN KEY (`ERP_ORDER_ID`) REFERENCES `rm_erp_order` (`ERP_ORDER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=270528 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_shipment_consolidated`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_shipment_consolidated` (
  `ERP_SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ERP_ORDER_ID` int unsigned DEFAULT NULL,
  `KN_SHIPMENT_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PRIME_LINE_NO` int NOT NULL,
  `BATCH_NO` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXPIRY_DATE` date DEFAULT NULL,
  `PROCUREMENT_UNIT_SKU_CODE` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SHIPPED_QTY` int DEFAULT NULL,
  `DELIVERED_QTY` bigint DEFAULT NULL,
  `ACTUAL_SHIPMENT_DATE` date DEFAULT NULL,
  `ACTUAL_DELIVERY_DATE` date DEFAULT NULL,
  `ARRIVAL_AT_DESTINATION_DATE` date DEFAULT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CHANGE_CODE` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `FILE_NAME` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
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
  CONSTRAINT `fk_rm_erp_shipment_consolidated_erpOrderId` FOREIGN KEY (`ERP_ORDER_ID`) REFERENCES `rm_erp_order_consolidated` (`ERP_ORDER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=270528 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_erp_tab3_shipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_erp_tab3_shipments` (
  `TAB3_SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SHIPMENT_ID` int NOT NULL,
  `ACTIVE` tinyint NOT NULL DEFAULT '1',
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int NOT NULL,
  PRIMARY KEY (`TAB3_SHIPMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_actual_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_actual_consumption` (
  `ACTUAL_CONSUMPTION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `AMOUNT` decimal(16,4) unsigned DEFAULT NULL,
  `REPORTING_RATE` decimal(6,2) unsigned DEFAULT NULL,
  `DAYS_OF_STOCK_OUT` int unsigned DEFAULT NULL,
  `ADJUSTED_AMOUNT` decimal(16,4) unsigned DEFAULT NULL,
  `PU_AMOUNT` decimal(16,4) unsigned DEFAULT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ACTUAL_CONSUMPTION_ID`),
  KEY `fk_rm_forecast_consumption_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_forecast_consumption_regionId_idx` (`REGION_ID`),
  KEY `fk_rm_forecast_consumption_createdBy_idx` (`CREATED_BY`),
  KEY `idx_rm_forecast_consumption_versionId` (`VERSION_ID`),
  KEY `fk_rm_forecast_consumption_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_rm_forecast_consumption_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_consumption_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_consumption_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_consumption_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12730 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_consumption_extrapolation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_consumption_extrapolation` (
  `CONSUMPTION_EXTRAPOLATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `EXTRAPOLATION_METHOD_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `JSON_PROPERTIES` varchar(255) DEFAULT NULL,
  `NOTES` text,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CONSUMPTION_EXTRAPOLATION_ID`),
  KEY `fk_rm_fce_list_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_fce_list_set_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_fce_list_set_regionId_idx` (`REGION_ID`),
  KEY `idx_rm_fce_list_versionId` (`VERSION_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_extrapolationMetho_idx` (`EXTRAPOLATION_METHOD_ID`),
  KEY `fk_rm_fce_list_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_rm_fce_list_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_fce_list_extrapolationMethodId` FOREIGN KEY (`EXTRAPOLATION_METHOD_ID`) REFERENCES `ap_extrapolation_method` (`EXTRAPOLATION_METHOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_fce_list_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_fce_list_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_fce_list_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1826 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_consumption_extrapolation_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_consumption_extrapolation_data` (
  `CONSUMPTION_EXTRAPOLATION_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CONSUMPTION_EXTRAPOLATION_ID` int unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `AMOUNT` decimal(18,8) unsigned DEFAULT NULL,
  `CI` decimal(16,2) DEFAULT NULL,
  PRIMARY KEY (`CONSUMPTION_EXTRAPOLATION_DATA_ID`),
  KEY `fk_rm_forecast_consumption_extrapolation_data_consumptionEx_idx` (`CONSUMPTION_EXTRAPOLATION_ID`),
  CONSTRAINT `fk_rm_fced_consumptionExtrapolationDataId` FOREIGN KEY (`CONSUMPTION_EXTRAPOLATION_ID`) REFERENCES `rm_forecast_consumption_extrapolation` (`CONSUMPTION_EXTRAPOLATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=104090 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_method` (
  `FORECAST_METHOD_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usageType, 1-For Discrete, 2-For Continuous',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Realm that this record belongs to',
  `FORECAST_METHOD_TYPE_ID` int unsigned NOT NULL COMMENT 'ForecastMethodType for this ForecastMethod',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'Field that indicates if this record is active or not',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`FORECAST_METHOD_ID`),
  UNIQUE KEY `FORECAST_METHOD_ID_UNIQUE` (`FORECAST_METHOD_ID`),
  KEY `fk_rm_forecast_method_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_forecast_method_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_forecast_method_labelId` (`LABEL_ID`),
  KEY `fk_rm_forecast_method_realmId` (`REALM_ID`),
  KEY `fk_rm_forecast_method_forecastMethodType` (`FORECAST_METHOD_TYPE_ID`),
  CONSTRAINT `fk_rm_forecast_method_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_method_forecastMethodType` FOREIGN KEY (`FORECAST_METHOD_TYPE_ID`) REFERENCES `ap_forecast_method_type` (`FORECAST_METHOD_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_method_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_method_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_method_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree` (
  `TREE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique forecast tree template id',
  `TREE_ANCHOR_ID` int unsigned DEFAULT NULL,
  `PROGRAM_ID` int unsigned NOT NULL COMMENT 'Foreign key that maps to the Program this Tree is for',
  `VERSION_ID` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `FORECAST_METHOD_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Forecast method is used for this Tree',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `NOTES` text,
  PRIMARY KEY (`TREE_ID`),
  KEY `fk_forecastTree_programId_idx` (`PROGRAM_ID`),
  KEY `fk_forecastTree_labelId_idx` (`LABEL_ID`),
  KEY `fk_forecastTree_forecastMethodId_idx` (`FORECAST_METHOD_ID`),
  KEY `fk_forecastTree_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTree_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_forecastTree_versionId_idx` (`PROGRAM_ID`,`VERSION_ID`),
  KEY `fk_rm_forecast_tree_treeAnchorId_idx` (`TREE_ANCHOR_ID`),
  CONSTRAINT `fk_forecastTree_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTree_forecastMethodId_idx` FOREIGN KEY (`FORECAST_METHOD_ID`) REFERENCES `rm_forecast_method` (`FORECAST_METHOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTree_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTree_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTree_programId_idx` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTree_versionId_idx` FOREIGN KEY (`PROGRAM_ID`, `VERSION_ID`) REFERENCES `rm_program_version` (`PROGRAM_ID`, `VERSION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_tree_treeAnchorId` FOREIGN KEY (`TREE_ANCHOR_ID`) REFERENCES `rm_forecast_tree_anchor` (`TREE_ANCHOR_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1296 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_anchor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_anchor` (
  `TREE_ANCHOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `TREE_NAME` varchar(255) NOT NULL,
  `TREE_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`TREE_ANCHOR_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=843 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_level` (
  `TREE_LEVEL_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TREE_ID` int unsigned NOT NULL,
  `LEVEL_NO` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `UNIT_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`TREE_LEVEL_ID`),
  KEY `fk_rm_forecast_tree_level_treeId_idx` (`TREE_ID`),
  KEY `fk_rm_forecast_tree_level_unitId_idx` (`UNIT_ID`),
  KEY `fk_rm_forecast_tree_level_labelId_idx` (`LABEL_ID`),
  KEY `idx_rm_forecast_tree_level_levelNo` (`LEVEL_NO`),
  CONSTRAINT `fk_rm_forecast_tree_level_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_tree_level_treeId` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_tree_level_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6472 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node` (
  `NODE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node id for tree',
  `TREE_ID` int unsigned NOT NULL COMMENT 'TreeId that this Node belongs to',
  `PARENT_NODE_ID` int unsigned DEFAULT NULL COMMENT 'Node Id of the parent. Null if this is the root.',
  `SORT_ORDER` varchar(300) NOT NULL COMMENT 'Sort order of the Node in the tree',
  `LEVEL_NO` int unsigned NOT NULL COMMENT 'Level that this node appears on ',
  `NODE_TYPE_ID` int unsigned NOT NULL COMMENT 'What type of Node is this',
  `UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Indicates the Unit for this Node',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `COLLAPSED` tinyint NOT NULL DEFAULT '0',
  `DOWNWARD_AGGREGATION_ALLOWED` tinyint unsigned NOT NULL DEFAULT '0',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `fk_forecastTreeNode_treeId_idx` (`TREE_ID`),
  KEY `fk_forecastTreeNode_parentNodeId_idx` (`PARENT_NODE_ID`),
  KEY `idx_forecastTreeNode_sortOrder` (`SORT_ORDER`),
  KEY `fk_forecastTreeNode_nodeTypeId_idx` (`NODE_TYPE_ID`),
  KEY `fk_forecastTreeNode_unitId_idx` (`UNIT_ID`),
  KEY `fk_forecastTreeNode_labelId_idx` (`LABEL_ID`),
  KEY `fk_forecastTreeNode_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNode_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNode_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_nodeTypeId_idx` FOREIGN KEY (`NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_parentNodeId_idx` FOREIGN KEY (`PARENT_NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_treeId_idx` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNode_unitId_idx` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=22438 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data` (
  `NODE_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node data id for the node and scenario',
  `NODE_ID` int unsigned NOT NULL COMMENT 'node id ',
  `SCENARIO_ID` int unsigned NOT NULL COMMENT 'Scenario that this Node data is for',
  `MONTH` date NOT NULL COMMENT 'Indicates the month that this Data is for, Defaults to the StartDate of the Forecast Program. Cannot be later than start of Forecast Program',
  `DATA_VALUE` decimal(24,8) DEFAULT NULL COMMENT 'Based on the forecast_tree_node.NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent',
  `NODE_DATA_FU_ID` int unsigned DEFAULT NULL,
  `NODE_DATA_PU_ID` int unsigned DEFAULT NULL,
  `IS_EXTRAPOLATION` tinyint unsigned NOT NULL,
  `NOTES` text COMMENT 'Notes that describe the Node',
  `MANUAL_CHANGES_EFFECT_FUTURE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeData_nodeId_idx` (`NODE_ID`),
  KEY `fk_forecastTreeNodeData_scenarioId_idx` (`SCENARIO_ID`),
  KEY `fk_forecastTreeNodeData_nodeDataFuId_idx` (`NODE_DATA_FU_ID`),
  KEY `fk_forecastTreeNodeData_nodeDataPuId_idx` (`NODE_DATA_PU_ID`),
  KEY `fk_forecastTreeNodeData_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeData_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeData_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeData_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeData_nodeDataFuId_idx` FOREIGN KEY (`NODE_DATA_FU_ID`) REFERENCES `rm_forecast_tree_node_data_fu` (`NODE_DATA_FU_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeData_nodeDataPuId_idx` FOREIGN KEY (`NODE_DATA_PU_ID`) REFERENCES `rm_forecast_tree_node_data_pu` (`NODE_DATA_PU_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeData_nodeId_idx` FOREIGN KEY (`NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeData_scenarioId_idx` FOREIGN KEY (`SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=41902 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` int unsigned NOT NULL,
  `CALCULATOR_FIRST_MONTH` varchar(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` int unsigned NOT NULL,
  `CREATED_BY` int unsigned DEFAULT NULL,
  `CREATED_DATE` varchar(45) DEFAULT NULL,
  `LAST_MODIFIED_BY` int unsigned DEFAULT NULL,
  `LAST_MODIFIED_DATE` varchar(45) DEFAULT NULL,
  `ACTIVE` tinyint unsigned DEFAULT '1',
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  KEY `fk_rm_ftnd_annual_target_calculator_idx` (`NODE_DATA_ID`),
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_nodeDataId` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` int unsigned NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  KEY `fk_rm_ftnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_annualTargetCalculatorId` FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`) REFERENCES `rm_forecast_tree_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_extrapolation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_extrapolation` (
  `NODE_DATA_EXTRAPOLATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` int unsigned NOT NULL,
  `EXTRAPOLATION_METHOD_ID` int unsigned NOT NULL,
  `NOTES` text,
  `START_DATE` date DEFAULT NULL,
  `STOP_DATE` date DEFAULT NULL,
  PRIMARY KEY (`NODE_DATA_EXTRAPOLATION_ID`),
  KEY `fk_rm_ftnde_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_rm_ftnde_extrapolationMethodId_idx` (`EXTRAPOLATION_METHOD_ID`),
  CONSTRAINT `fk_rm_ftnde_extrapolationMethodId` FOREIGN KEY (`EXTRAPOLATION_METHOD_ID`) REFERENCES `ap_extrapolation_method` (`EXTRAPOLATION_METHOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_ftnde_nodeDataId` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=649 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_extrapolation_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_extrapolation_data` (
  `NODE_DATA_EXTRAPOLATION_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_EXTRAPOLATION_ID` int unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `AMOUNT` decimal(18,8) DEFAULT NULL,
  `REPORTING_RATE` decimal(6,2) DEFAULT NULL,
  `MANUAL_CHANGE` decimal(18,4) DEFAULT NULL,
  PRIMARY KEY (`NODE_DATA_EXTRAPOLATION_DATA_ID`),
  KEY `fk_rm_ftnded_nodeDataExtrapolationDataId_idx` (`NODE_DATA_EXTRAPOLATION_ID`),
  CONSTRAINT `fk_rm_ftnded_nodeDataExtrapolationId` FOREIGN KEY (`NODE_DATA_EXTRAPOLATION_ID`) REFERENCES `rm_forecast_tree_node_data_extrapolation` (`NODE_DATA_EXTRAPOLATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=32187 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_extrapolation_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_extrapolation_option` (
  `NODE_DATA_EXTRAPOLATION_OPTION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` int unsigned NOT NULL,
  `EXTRAPOLATION_METHOD_ID` int unsigned NOT NULL,
  `JSON_PROPERTIES` varchar(255) NOT NULL,
  PRIMARY KEY (`NODE_DATA_EXTRAPOLATION_OPTION_ID`),
  KEY `fk_rm_ftndeo_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_rm_ftndeo_extrapolationMethod_idx` (`EXTRAPOLATION_METHOD_ID`),
  CONSTRAINT `fk_rm_ftndeo_extrapolationMethodId` FOREIGN KEY (`EXTRAPOLATION_METHOD_ID`) REFERENCES `ap_extrapolation_method` (`EXTRAPOLATION_METHOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_ftndeo_nodeDataId` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2157 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_extrapolation_option_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_extrapolation_option_data` (
  `NODE_DATA_EXTRAPOLATION_OPTION_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_EXTRAPOLATION_OPTION_ID` int unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `AMOUNT` decimal(18,8) DEFAULT NULL,
  `CI` decimal(18,4) DEFAULT NULL,
  PRIMARY KEY (`NODE_DATA_EXTRAPOLATION_OPTION_DATA_ID`),
  KEY `fk_rm_ftndeo_nodeDataExtrapolationOptionId_idx` (`NODE_DATA_EXTRAPOLATION_OPTION_ID`),
  CONSTRAINT `fk_rm_ftnded_nodeDataExtrapolationOptionId` FOREIGN KEY (`NODE_DATA_EXTRAPOLATION_OPTION_ID`) REFERENCES `rm_forecast_tree_node_data_extrapolation_option` (`NODE_DATA_EXTRAPOLATION_OPTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_fu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_fu` (
  `NODE_DATA_FU_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',
  `FORECASTING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate forecasting id',
  `LAG_IN_MONTHS` int unsigned NOT NULL COMMENT '# of months to wait before using',
  `USAGE_TYPE_ID` int unsigned NOT NULL COMMENT '1 for Discrete, 2 for Continuous',
  `NO_OF_PERSONS` int unsigned NOT NULL COMMENT '# of Patients this usage will be used for',
  `FORECASTING_UNITS_PER_PERSON` decimal(18,4) unsigned NOT NULL COMMENT '# of Forecasting Units ',
  `ONE_TIME_USAGE` tinyint unsigned NOT NULL,
  `ONE_TIME_DISPENSING` tinyint unsigned NOT NULL DEFAULT '1',
  `USAGE_FREQUENCY` decimal(18,4) unsigned DEFAULT NULL COMMENT '# of times the Forecasting Unit is given per Usage',
  `USAGE_FREQUENCY_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',
  `REPEAT_COUNT` decimal(18,4) unsigned DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type',
  `REPEAT_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_FU_ID`),
  KEY `fk_forecastTreeNodeDataFu_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_forecastTreeNodeDataFu_usageTypeId_idx` (`USAGE_TYPE_ID`),
  KEY `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID`),
  KEY `fk_forecastTreeNodeDataFu_repeatUsagePeriodId_idx` (`REPEAT_USAGE_PERIOD_ID`),
  KEY `fk_forecastTreeNodeDataFu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataFu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataFu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataFu_forecastingUnitId_idx` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataFu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataFu_repeatUsagePeriodId_idx` FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataFu_usageFrequencyUsagePeriodId_idx` FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataFu_usageTypeId_idx` FOREIGN KEY (`USAGE_TYPE_ID`) REFERENCES `ap_usage_type` (`USAGE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13215 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_modeling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_modeling` (
  `NODE_DATA_MODELING_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for NodeScaleUp',
  `NODE_DATA_ID` int unsigned NOT NULL COMMENT 'Node that this ScaleUp referrs to',
  `START_DATE` date NOT NULL COMMENT 'Start date that the Scale up is applicable from. Startes from the Forecast Program Start',
  `STOP_DATE` date NOT NULL COMMENT 'Stop date that the Scale up is applicable from. Defaults to Forecast Program End but user can override',
  `MODELING_TYPE_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate scale type id',
  `DATA_VALUE` decimal(18,4) NOT NULL COMMENT 'Data value could be a number of a % based on the ScaleTypeId',
  `INCREASE_DECREASE` int NOT NULL DEFAULT '1',
  `TRANSFER_NODE_DATA_ID` int unsigned DEFAULT NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.',
  `NOTES` text COMMENT 'Notes to desribe this scale up',
  `MODELING_SOURCE` int unsigned NOT NULL COMMENT '0 for manual entry or old calculator, 1 for annual target calculator',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_MODELING_ID`),
  KEY `fk_forecastTreeNodeDataModeling_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeDataModeling_modelingTypeId_idx` (`MODELING_TYPE_ID`),
  KEY `fk_forecastTreeNodeDataModeling_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataModeling_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_forecastTreeNodeDataModeling_transferNodeDataId_idx_idx` (`TRANSFER_NODE_DATA_ID`),
  CONSTRAINT `fk_forecastTreeNodeDataModeling_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_modelingTypeId_idx` FOREIGN KEY (`MODELING_TYPE_ID`) REFERENCES `ap_modeling_type` (`MODELING_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataModeling_transferNodeDataId_idx` FOREIGN KEY (`TRANSFER_NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2489 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_mom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_mom` (
  `NODE_DATA_MOM_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` int unsigned NOT NULL,
  `MONTH` date NOT NULL,
  `START_VALUE` decimal(24,8) unsigned NOT NULL,
  `END_VALUE` decimal(24,8) unsigned NOT NULL,
  `CALCULATED_VALUE` decimal(24,8) unsigned NOT NULL,
  `CALCULATED_MMD_VALUE` decimal(24,8) unsigned DEFAULT NULL,
  `DIFFERENCE` decimal(24,8) DEFAULT NULL,
  `SEASONALITY_PERC` decimal(6,2) DEFAULT NULL,
  `MANUAL_CHANGE` decimal(24,8) DEFAULT NULL,
  PRIMARY KEY (`NODE_DATA_MOM_ID`),
  KEY `fk_rm_forecast_tree_node_data_mom_nodeDataId_idx` (`NODE_DATA_ID`),
  CONSTRAINT `fk_rm_forecast_tree_node_data_mom_nodeDataId` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1144213 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_override`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_override` (
  `NODE_DATA_OVERRIDE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Override Id',
  `NODE_DATA_ID` int unsigned NOT NULL COMMENT 'The Node Data Id that this Override is for',
  `MONTH` date NOT NULL COMMENT 'Month that this Override is for',
  `MANUAL_CHANGE` decimal(18,4) DEFAULT NULL COMMENT 'The manual change value',
  `SEASONALITY_PERC` decimal(18,4) DEFAULT NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_OVERRIDE_ID`),
  KEY `fk_forecastTreeNodeDataOverride_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_forecastTreeNodeDataOverride_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataOverride_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataOverride_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataOverride_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataOverride_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_forecast_tree_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1538 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_data_pu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_data_pu` (
  `NODE_DATA_PU_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each PU Conversion',
  `PLANNING_UNIT_ID` int unsigned NOT NULL COMMENT 'What Palnning Unit does this Node convert to',
  `SHARE_PLANNING_UNIT` tinyint unsigned NOT NULL COMMENT 'If 1 that means this Planning Unit is to be shared with others and therefore maintain the decimal, if it is not shared you need to round to 1',
  `REFILL_MONTHS` decimal(16,4) unsigned DEFAULT NULL COMMENT '# of moths over which refulls are taken',
  `PU_PER_VISIT` decimal(20,8) unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_PU_ID`),
  KEY `fk_forecastTreeNodeDataPu_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_forecastTreeNodeDataPu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastTreeNodeDataPu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_forecastTreeNodeDataPu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataPu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastTreeNodeDataPu_planningUnitId_idx` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13951 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_node_downward_aggregation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` int unsigned NOT NULL,
  `SOURCE_TREE_ID` int unsigned NOT NULL,
  `SOURCE_SCENARIO_ID` int unsigned NOT NULL,
  `SOURCE_NODE_ID` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  KEY `fk_rm_ftndownward_agg_nodeId_idx` (`TARGET_NODE_ID`),
  KEY `fk_rm_ftn_downward_agg_scenarioId_idx` (`SOURCE_SCENARIO_ID`),
  KEY `fk_rm_ftn_downward_agg_sourceNodeId_idx` (`SOURCE_NODE_ID`),
  KEY `fk_rm_ftn_downward_agg_treeId_idx` (`SOURCE_TREE_ID`),
  CONSTRAINT `fk_rm_ftn_downward_agg_nodeId` FOREIGN KEY (`TARGET_NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`),
  CONSTRAINT `fk_rm_ftn_downward_agg_scenarioId` FOREIGN KEY (`SOURCE_SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`),
  CONSTRAINT `fk_rm_ftn_downward_agg_sourceNodeId` FOREIGN KEY (`SOURCE_NODE_ID`) REFERENCES `rm_forecast_tree_node` (`NODE_ID`),
  CONSTRAINT `fk_rm_ftn_downward_agg_treeId` FOREIGN KEY (`SOURCE_TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecast_tree_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecast_tree_region` (
  `FORECAST_TREE_REGION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TREE_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`FORECAST_TREE_REGION_ID`),
  KEY `fk_rm_forecast_tree_region_treeId_idx` (`TREE_ID`),
  KEY `fk_rm_forecast_tree_region_regionId_idx` (`REGION_ID`),
  CONSTRAINT `fk_rm_forecast_tree_region_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecast_tree_region_treeId` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1296 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_forecasting_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_forecasting_unit` (
  `FORECASTING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realm this Product belongs to',
  `PRODUCT_CATEGORY_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Product Category for this product',
  `TRACER_CATEGORY_ID` int unsigned DEFAULT NULL,
  `GENERIC_LABEL_ID` int unsigned DEFAULT NULL COMMENT 'Generic name for the Product, also called the INN',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Product is Active. False indicates this Product has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created date',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`FORECASTING_UNIT_ID`),
  KEY `fk_forecastingUnit_genericLabelId_idx` (`GENERIC_LABEL_ID`),
  KEY `fk_forecastingUnit_labelId_idx` (`LABEL_ID`),
  KEY `fk_forecastingUnit_createdBy_idx` (`CREATED_BY`),
  KEY `fk_forecastingUnit_lastModifedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_forecastingUnit_productCategoryId_idx` (`PRODUCT_CATEGORY_ID`),
  KEY `fk_rm_forecasting_unit_rm_tracer_category1_idx` (`TRACER_CATEGORY_ID`),
  KEY `fk_forecastingUnit_unitId_idx` (`UNIT_ID`),
  KEY `idx_forecastingUnit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_forecastingUnit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastingUnit_genericLabelId` FOREIGN KEY (`GENERIC_LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastingUnit_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastingUnit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastingUnit_productCategoryId` FOREIGN KEY (`PRODUCT_CATEGORY_ID`) REFERENCES `rm_product_category` (`PRODUCT_CATEGORY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_forecastingUnit_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_forecasting_unit_rm_tracer_category1` FOREIGN KEY (`TRACER_CATEGORY_ID`) REFERENCES `rm_tracer_category` (`TRACER_CATEGORY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7292 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the Products\nNote: Are based on a Realm and can be created or administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_funding_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_funding_source` (
  `FUNDING_SOURCE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Funding source',
  `FUNDING_SOURCE_CODE` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realm this Funding Source belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `ALLOWED_IN_BUDGET` tinyint unsigned NOT NULL,
  `FUNDING_SOURCE_TYPE_ID` int unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`FUNDING_SOURCE_ID`),
  UNIQUE KEY `unq_fundingSourceCode` (`FUNDING_SOURCE_CODE`,`REALM_ID`),
  KEY `fk_funding_source_realmId_idx` (`REALM_ID`),
  KEY `fk_funding_source_labelId_idx` (`LABEL_ID`),
  KEY `fk_funding_source_createdBy_idx` (`CREATED_BY`),
  KEY `fk_funding_source_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_fundingSource_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_funding_source_fundingSourceTypeId_idx` (`FUNDING_SOURCE_TYPE_ID`),
  CONSTRAINT `fk_funding_source_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_funding_source_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_funding_source_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_funding_source_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_fundingSourceTypeId` FOREIGN KEY (`FUNDING_SOURCE_TYPE_ID`) REFERENCES `rm_funding_source_type` (`FUNDING_SOURCE_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_funding_source_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_funding_source_type` (
  `FUNDING_SOURCE_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_ID` int unsigned NOT NULL,
  `FUNDING_SOURCE_TYPE_CODE` varchar(10) NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`FUNDING_SOURCE_TYPE_ID`),
  UNIQUE KEY `unq_rm_funding_source_type_codeRealm` (`REALM_ID`,`FUNDING_SOURCE_TYPE_CODE`),
  KEY `fk_rm_funding_source_type_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_funding_source_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_funding_source_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_funding_source_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_funding_source_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_funding_source_type_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_health_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_health_area` (
  `HEALTH_AREA_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Health Area',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm and Country this Health Area belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `HEALTH_AREA_CODE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`HEALTH_AREA_ID`),
  UNIQUE KEY `unq_healthAreaCode` (`REALM_ID`,`HEALTH_AREA_CODE`),
  KEY `fk_health_area_labelId_idx` (`LABEL_ID`),
  KEY `fk_health_area_createdBy_idx` (`CREATED_BY`),
  KEY `fk_health_area_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_health_area_rm_realm1_idx` (`REALM_ID`),
  KEY `idx_healthArea_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_health_area_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_health_area_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_health_area_user1` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_health_area_rm_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the health_areas for each Realm\nNote: A Health Area can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_health_area_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_health_area_country` (
  `HEALTH_AREA_COUNTRY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Health Area - Country mapping',
  `HEALTH_AREA_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Health Area this mapping belongs to',
  `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this mapping belongs to',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this mapping is Active. False indicates this Health-Area Country has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`HEALTH_AREA_COUNTRY_ID`),
  UNIQUE KEY `unqHealthAreaIdCountryId` (`HEALTH_AREA_ID`,`REALM_COUNTRY_ID`) COMMENT 'Unique key to map Health area and Country mapping',
  KEY `fk_health_area_country_healthAreaId_idx` (`HEALTH_AREA_ID`),
  KEY `fk_health_area_country_realmCountryId_idx` (`REALM_COUNTRY_ID`),
  KEY `fk_health_area_country_createdBy_idx` (`CREATED_BY`),
  KEY `fk_health_area_country_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_health_area_country_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_health_area_country_healthAreaId` FOREIGN KEY (`HEALTH_AREA_ID`) REFERENCES `rm_health_area` (`HEALTH_AREA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_health_area_country_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_health_area_country_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2092 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map which health_areas are available for which country\nNote: This is to be administered at the Realm level only by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_integration_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_integration_manual` (
  `MANUAL_INTEGRATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `INTEGRATION_ID` int unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `COMPLETED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`MANUAL_INTEGRATION_ID`),
  KEY `fk_rm_integration_manual_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_integration_manual_versionId_idx` (`PROGRAM_ID`,`VERSION_ID`),
  KEY `fk_rm_integration_manual_integrationId_idx` (`INTEGRATION_ID`),
  KEY `fk_rm_integration_manual_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_rm_integration_manual_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_manual_integrationId` FOREIGN KEY (`INTEGRATION_ID`) REFERENCES `ap_integration` (`INTEGRATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_manual_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_manual_versionId` FOREIGN KEY (`PROGRAM_ID`, `VERSION_ID`) REFERENCES `rm_program_version` (`PROGRAM_ID`, `VERSION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_integration_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_integration_program` (
  `INTEGRATION_PROGRAM_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTEGRATION_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_TYPE_ID` int unsigned NOT NULL COMMENT 'null for all versionTypes, for value for actual',
  `VERSION_STATUS_ID` int unsigned NOT NULL COMMENT 'null for all versionStatuses, for value for actual',
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`INTEGRATION_PROGRAM_ID`),
  KEY `fk_rm_integration_program_integrationId_idx` (`INTEGRATION_ID`),
  KEY `fk_rm_integration_program_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_integration_program_versionTypeId_idx` (`VERSION_TYPE_ID`),
  KEY `fk_rm_integration_program_versionStatusId_idx` (`VERSION_STATUS_ID`),
  KEY `fk_rm_integration_program_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_rm_integration_program_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_integrationId` FOREIGN KEY (`INTEGRATION_ID`) REFERENCES `ap_integration` (`INTEGRATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_versionStatusId` FOREIGN KEY (`VERSION_STATUS_ID`) REFERENCES `ap_version_status` (`VERSION_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_versionTypeId` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_integration_program_completed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_integration_program_completed` (
  `PROGRAM_VERSION_TRANS_ID` int unsigned NOT NULL,
  `INTEGRATION_ID` int unsigned NOT NULL,
  `INTEGRATION_PROGRAM_ID` int unsigned DEFAULT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `COMPLETED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_VERSION_TRANS_ID`,`INTEGRATION_ID`),
  KEY `fk_rm_integration_program_completed_IntegrationId_idx` (`INTEGRATION_ID`),
  CONSTRAINT `fk_rm_integration_program_completed_IntegrationId` FOREIGN KEY (`INTEGRATION_ID`) REFERENCES `ap_integration` (`INTEGRATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_completed_programVersionTransId` FOREIGN KEY (`PROGRAM_VERSION_TRANS_ID`) REFERENCES `rm_program_version_trans` (`PROGRAM_VERSION_TRANS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_integration_program_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_integration_program_history` (
  `INTEGRATION_PROGRAM_HISTORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTEGRATION_PROGRAM_ID` int unsigned NOT NULL,
  `INTEGRATION_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_TYPE_ID` int unsigned NOT NULL COMMENT 'null for all versionTypes, for value for actual',
  `VERSION_STATUS_ID` int unsigned NOT NULL COMMENT 'null for all versionStatuses, for value for actual',
  `ACTIVE` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`INTEGRATION_PROGRAM_HISTORY_ID`),
  KEY `fk_rm_integration_program_history_integrationProgramId_idx` (`INTEGRATION_PROGRAM_ID`),
  KEY `fk_rm_integration_program_history_integrationId_idx` (`INTEGRATION_ID`),
  KEY `fk_rm_integration_program_history_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_integration_program_history_versionTypeId_idx` (`VERSION_TYPE_ID`),
  KEY `fk_rm_integration_program_history_versionStatusId_idx` (`VERSION_STATUS_ID`),
  CONSTRAINT `fk_rm_integration_program_history_integrationId` FOREIGN KEY (`INTEGRATION_ID`) REFERENCES `ap_integration` (`INTEGRATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_history_integrationProgramId` FOREIGN KEY (`INTEGRATION_PROGRAM_ID`) REFERENCES `rm_integration_program` (`INTEGRATION_PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_history_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_history_versionStatusId` FOREIGN KEY (`VERSION_STATUS_ID`) REFERENCES `ap_version_status` (`VERSION_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_integration_program_history_versionTypeId` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_inventory` (
  `INVENTORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  `MAX_VERSION_ID` int unsigned NOT NULL,
  `TMP_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`INVENTORY_ID`),
  KEY `fk_inventory_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_inventory_programId_idx` (`PROGRAM_ID`),
  KEY `fk_inventory_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_inventory_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventory_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventory_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14153 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the inventory of the Products\nNote: Could be manually fed or can come from feeds from eLMIS';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_inventory_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_inventory_trans` (
  `INVENTORY_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INVENTORY_ID` int unsigned NOT NULL,
  `INVENTORY_DATE` date NOT NULL COMMENT 'Date this Inventory record is for',
  `REGION_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key that indicates the Region that this record is for',
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned NOT NULL COMMENT 'Data will be entered in terms of RealmCountryPlanningUnit only',
  `ACTUAL_QTY` bigint unsigned DEFAULT NULL COMMENT 'If only an Adjustment Qty is given then this was not an Inventory count but an Adjustment to Inventory. If the Actual Qty is mentioned then the Adjustment Qty was derived',
  `ADJUSTMENT_QTY` bigint DEFAULT NULL COMMENT 'If only an Adjustment Qty is given then this was not an Inventory count but an Adjustment to Inventory. If the Actual Qty is mentioned then the Adjustment Qty was derived',
  `DATA_SOURCE_ID` int unsigned NOT NULL COMMENT 'Source of this data',
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ACTIVE` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  `VERSION_ID` int unsigned NOT NULL COMMENT 'This is the Version this record belongs to. If it updated at a later date a new Version of this Row will be entered.',
  PRIMARY KEY (`INVENTORY_TRANS_ID`),
  UNIQUE KEY `unq_inventoryTrans_inventoryIdVersionId` (`INVENTORY_ID`,`VERSION_ID`),
  KEY `fk_inventoryTrans_regionId_idx` (`REGION_ID`),
  KEY `fk_inventoryTrans_dataSourceId_idx` (`DATA_SOURCE_ID`),
  KEY `fk_inventoryTrans_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_inventoryTrans_realmCountryPlanningUnitId_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID`),
  KEY `fk_inventoryTrans_inventoryId` (`INVENTORY_ID`),
  KEY `idx_inventoryTrans_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_inventoryTrans_dataSourceId` FOREIGN KEY (`DATA_SOURCE_ID`) REFERENCES `rm_data_source` (`DATA_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventoryTrans_inventoryId` FOREIGN KEY (`INVENTORY_ID`) REFERENCES `rm_inventory` (`INVENTORY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventoryTrans_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventoryTrans_realmCountryPlanningUnitId` FOREIGN KEY (`REALM_COUNTRY_PLANNING_UNIT_ID`) REFERENCES `rm_realm_country_planning_unit` (`REALM_COUNTRY_PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inventoryTrans_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14193 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the inventory of the Products\nNote: Could be manually fed or can come from feeds from eLMIS';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_inventory_trans_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_inventory_trans_batch_info` (
  `INVENTORY_TRANS_BATCH_INFO_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INVENTORY_TRANS_ID` int unsigned NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `ACTUAL_QTY` bigint unsigned DEFAULT NULL,
  `ADJUSTMENT_QTY` bigint DEFAULT NULL,
  PRIMARY KEY (`INVENTORY_TRANS_BATCH_INFO_ID`),
  KEY `fk_rm_inventory_trans_batch_info_rm_inventory_trans1_idx` (`INVENTORY_TRANS_ID`),
  KEY `fk_rm_inventory_trans_batch_info_rm_batch_info1_idx` (`BATCH_ID`),
  CONSTRAINT `fk_rm_inventory_trans_batch_info_rm_batch_info1` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_inventory_trans_batch_info_rm_inventory_trans1` FOREIGN KEY (`INVENTORY_TRANS_ID`) REFERENCES `rm_inventory_trans` (`INVENTORY_TRANS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_manual_tagging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_manual_tagging` (
  `MANUAL_TAGGING_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PRIME_LINE_NO` int unsigned NOT NULL,
  `SHIPMENT_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CONVERSION_FACTOR` decimal(14,4) DEFAULT NULL,
  PRIMARY KEY (`MANUAL_TAGGING_ID`),
  UNIQUE KEY `uniqueOrder` (`ORDER_NO`,`PRIME_LINE_NO`,`SHIPMENT_ID`,`ACTIVE`),
  KEY `fk_rm_manual_tagging_rm_shipment1_idx` (`SHIPMENT_ID`),
  KEY `fk_rm_manual_tagging_us_user1_idx` (`CREATED_BY`),
  KEY `fk_rm_manual_tagging_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_rmManualTagging_orderNo` (`ORDER_NO`),
  KEY `idx_rmManualTagging_primeLineNo` (`PRIME_LINE_NO`),
  CONSTRAINT `fk_rm_manual_tagging_rm_shipment1` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_manual_tagging_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_manual_tagging_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_manual_tagging_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_manual_tagging_trans` (
  `MANUAL_TAGGING_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MANUAL_TAGGING_ID` int unsigned NOT NULL,
  `ORDER_NO` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PRIME_LINE_NO` int unsigned NOT NULL,
  `SHIPMENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CONVERSION_FACTOR` decimal(14,4) DEFAULT NULL,
  PRIMARY KEY (`MANUAL_TAGGING_TRANS_ID`),
  KEY `fk_rm_manual_tagging_rm_shipment1_idx` (`SHIPMENT_ID`),
  KEY `fk_rm_manual_tagging_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_rmManualTagging_orderNo` (`ORDER_NO`),
  KEY `idx_rmManualTagging_primeLineNo` (`PRIME_LINE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_organisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_organisation` (
  `ORGANISATION_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm this Organisation belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ORGANISATION_CODE` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '10 character Unique Code for each Organisation',
  `ORGANISATION_TYPE_ID` int unsigned NOT NULL DEFAULT '1',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '3 ',
  PRIMARY KEY (`ORGANISATION_ID`),
  UNIQUE KEY `unq_organisationCode` (`ORGANISATION_CODE`,`REALM_ID`) COMMENT 'Unique Organisation Code across the Realm',
  KEY `fk_organisation_realmId_idx` (`REALM_ID`),
  KEY `fk_organisation_labelId_idx` (`LABEL_ID`),
  KEY `fk_organisation_createdBy_idx` (`CREATED_BY`),
  KEY `fk_organisation_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_organisation_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_organisation_organisationTypeId_idx` (`ORGANISATION_TYPE_ID`),
  CONSTRAINT `fk_organisation_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_organisation_organisationTypeId` FOREIGN KEY (`ORGANISATION_TYPE_ID`) REFERENCES `rm_organisation_type` (`ORGANISATION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the organizations for each Realm\nNote: An Organisation can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_organisation_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_organisation_country` (
  `ORGANISATION_COUNTRY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation - Realm country mapping',
  `ORGANISATION_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Organisation this mapping belongs to',
  `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this mapping belongs to',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`ORGANISATION_COUNTRY_ID`),
  UNIQUE KEY `unqOrganisationIdCountryId` (`ORGANISATION_ID`,`REALM_COUNTRY_ID`) COMMENT 'Uniqe key for an Organisation - Country mapping',
  KEY `fk_organisation_country_organisationId_idx` (`ORGANISATION_ID`),
  KEY `fk_organisation_country_realmCountryId_idx` (`REALM_COUNTRY_ID`),
  KEY `fk_organisation_country_createdBy_idx` (`CREATED_BY`),
  KEY `fk_organisation_country_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_organisation_country_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_country_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_country_organisationId` FOREIGN KEY (`ORGANISATION_ID`) REFERENCES `rm_organisation` (`ORGANISATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_organisation_country_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map which organisations are available for which country\nNote: This is to be administered at the Realm level only by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_organisation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_organisation_type` (
  `ORGANISATION_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation type',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm this OrganisationType belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate the Label for this OrganisationType so we can get the labels in all languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this OrganisationType is Active. False indicates this OrganisationType has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Foreign key to indicate the User that Created this OrganisationType',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Date this OrganisationType was Created on',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Foreign key to indicate the User that LastModified this OrganisationType',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Date this OrganisationType was LastModified on',
  PRIMARY KEY (`ORGANISATION_TYPE_ID`),
  KEY `fk_rm_organisation_type_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_organisation_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_organisation_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_organisation_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_rm_organisation_type_lastModifiedDate` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_rm_organisation_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_organisation_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_organisation_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_organisation_type_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_planning_unit` (
  `PLANNING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Planning unit',
  `FORECASTING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key for the Product that this Planning unit represents',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` int unsigned NOT NULL COMMENT 'Unit of measure for this Planning unit',
  `MULTIPLIER` decimal(12,2) NOT NULL COMMENT 'Quantity of items in this unit versus the Forecasting Unit Id',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PLANNING_UNIT_ID`),
  KEY `fk_planning_unit_forecastingUnit_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_planning_unit_labelId_idx` (`LABEL_ID`),
  KEY `fk_planning_unit_unitId_idx` (`UNIT_ID`),
  KEY `fk_planning_unit_createdBy_idx` (`CREATED_BY`) COMMENT '\n',
  KEY `fk_planning_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_planningUnit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_planning_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_forecastingUnitId` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8723 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the Planning units that will be used to map a Product to a Program\nNote: Units are Realm level master and can only be Administered by a Realm level admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_planning_unit_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_planning_unit_capacity` (
  `PLANNING_UNIT_CAPACITY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Logistics Unit Capacity Id',
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `SUPPLIER_ID` int unsigned NOT NULL,
  `CAPACITY` decimal(12,2) unsigned NOT NULL COMMENT 'Global capacity level beyond which the manufacture cannot produce that GTIN',
  `START_DATE` date NOT NULL COMMENT 'Start period for the Capacity for this Logistics Unit',
  `STOP_DATE` date NOT NULL COMMENT 'Stop period for the Capacity for this Logistics Unit',
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PLANNING_UNIT_CAPACITY_ID`),
  KEY `fk_planning_unit_capacity_createdBy_idx` (`CREATED_BY`),
  KEY `fk_planning_unit_capacity_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_planning_unit_capacity_rm_supplier1_idx` (`SUPPLIER_ID`),
  KEY `fk_rm_planning_unit_capacity_rm_planning_unit1_idx` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_planning_unit_capacity_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_capacity_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_capacity_rm_planning_unit1` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_capacity_rm_supplier1` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `rm_supplier` (`SUPPLIER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Global capacity for each Planning unit\nNote: More than one record for each Planning unit, we need to check if the periods overlap an existing record and not allow a new entry if it does overlap.LOGISTICS_UNIT_ID';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_problem_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_problem_report` (
  `PROBLEM_REPORT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_PROBLEM_ID` int unsigned NOT NULL,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PROBLEM_TYPE_ID` int unsigned NOT NULL,
  `PROBLEM_STATUS_ID` int unsigned NOT NULL,
  `REVIEWED` tinyint unsigned NOT NULL,
  `REVIEW_NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `REVIEWED_DATE` datetime DEFAULT NULL,
  `DATA1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA5` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROBLEM_REPORT_ID`),
  KEY `fk_rm_problem_report_rm_realm_problem1_idx` (`REALM_PROBLEM_ID`),
  KEY `fk_rm_problem_report_rm_program1_idx` (`PROGRAM_ID`),
  KEY `fk_rm_problem_report_ap_problem_type1_idx` (`PROBLEM_TYPE_ID`),
  KEY `fk_rm_problem_report_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  KEY `fk_rm_problem_report_us_user1_idx` (`CREATED_BY`),
  KEY `fk_rm_problem_report_us_user2_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_problem_report_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_ap_problem_type1` FOREIGN KEY (`PROBLEM_TYPE_ID`) REFERENCES `ap_problem_type` (`PROBLEM_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_rm_program2` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_rm_realm_problem1` FOREIGN KEY (`REALM_PROBLEM_ID`) REFERENCES `rm_realm_problem` (`REALM_PROBLEM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=700 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_problem_report_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_problem_report_trans` (
  `PROBLEM_REPORT_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROBLEM_REPORT_ID` int unsigned NOT NULL,
  `PROBLEM_STATUS_ID` int unsigned NOT NULL,
  `REVIEWED` tinyint unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROBLEM_REPORT_TRANS_ID`),
  KEY `fk_rm_problem_report_trans_rm_problem_report1_idx` (`PROBLEM_REPORT_ID`),
  KEY `fk_rm_problem_report_trans_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  KEY `fk_rm_problem_report_trans_us_user1_idx` (`CREATED_BY`),
  CONSTRAINT `fk_rm_problem_report_trans_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_trans_rm_problem_report1` FOREIGN KEY (`PROBLEM_REPORT_ID`) REFERENCES `rm_problem_report` (`PROBLEM_REPORT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_problem_report_trans_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1630 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_agent` (
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Procurement agent',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realm this Procurement Agent belongs to',
  `PROCUREMENT_AGENT_TYPE_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_CODE` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Foreign key that determines the Program this Procurement Agent belongs to. If it is null then the record is a Realm level Procurement Agent. If it is not null then it is a Program level Procurement Agent.',
  `COLOR_HTML_CODE` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `COLOR_HTML_DARK_CODE` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `SUBMITTED_TO_APPROVED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Submitted to Approved status, this will be used only in the case the Procurement Agent is TBD',
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Procurement Agent is Active. False indicates this Procurement Agent has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, this will be used only in the case the Procurement Agent is TBD',
  PRIMARY KEY (`PROCUREMENT_AGENT_ID`),
  UNIQUE KEY `un_procurementAgentCode` (`PROCUREMENT_AGENT_CODE`,`REALM_ID`),
  KEY `fk_procurement_agent_realmId_idx` (`REALM_ID`),
  KEY `fk_procurement_agent_labelId_idx` (`LABEL_ID`),
  KEY `fk_procurement_agent_createdBy_idx` (`CREATED_BY`),
  KEY `fk_procurement_agent_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_procurementAgent_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_procurement_agent_procurementAgentType_idx` (`PROCUREMENT_AGENT_TYPE_ID`),
  KEY `fk_rm_procurement_agent_procurementAgentTypeId_idx` (`PROCUREMENT_AGENT_TYPE_ID`),
  CONSTRAINT `fk_procurement_agent_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_agent_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_agent_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_agent_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_procurement_agent_procurementAgentTypeId` FOREIGN KEY (`PROCUREMENT_AGENT_TYPE_ID`) REFERENCES `rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the procurement_agents for a Realm\nNote: Are based on a Realm and can be created by a Realm level admin through a Ticket request, you can also have Program level procurement agent which are also created and administered by the Program Admin via a Ticket request';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_agent_forecasting_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_agent_forecasting_unit` (
  `PROCUREMENT_AGENT_FORECASTING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `FORECASTING_UNIT_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `SKU_CODE` varchar(9) NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROCUREMENT_AGENT_FORECASTING_UNIT_ID`),
  KEY `FK_rm_procurement_agent_forecasting_unit_fu` (`FORECASTING_UNIT_ID`),
  KEY `FK_rm_procurement_agent_forecasting_unit_pa` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_procurement_agent_forecasting_unit_cb_idx` (`CREATED_BY`),
  KEY `fk_rm_procurement_agent_forecasting_unit_lmb_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_procurement_agent_forecasting_unit_cb` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `FK_rm_procurement_agent_forecasting_unit_fu` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`),
  CONSTRAINT `fk_rm_procurement_agent_forecasting_unit_lmb` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `FK_rm_procurement_agent_forecasting_unit_pa` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_agent_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_agent_planning_unit` (
  `PROCUREMENT_AGENT_PLANNING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `SKU_CODE` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CATALOG_PRICE` decimal(14,4) unsigned DEFAULT NULL,
  `MOQ` int unsigned DEFAULT NULL,
  `UNITS_PER_PALLET_EURO1` int unsigned DEFAULT NULL,
  `UNITS_PER_PALLET_EURO2` int unsigned DEFAULT NULL,
  `UNITS_PER_CONTAINER` int unsigned DEFAULT NULL,
  `VOLUME` decimal(18,6) unsigned DEFAULT NULL,
  `WEIGHT` decimal(18,6) unsigned DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROCUREMENT_AGENT_PLANNING_UNIT_ID`),
  UNIQUE KEY `unq_procurementAgentPlanningUnit_skuCode` (`PROCUREMENT_AGENT_ID`,`SKU_CODE`),
  KEY `fk_rm_planning_unit_procurement_agent_rm_planning_unit1_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_planning_unit_procurement_agent_rm_procurement_agent1_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_planning_unit_procurement_agent_us_user1_idx` (`CREATED_BY`),
  KEY `fk_rm_planning_unit_procurement_agent_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_procurementAgentPlanningUnit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `idx_procurementAgentPlanningUnit_skuCode` (`SKU_CODE`),
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_rm_planning_unit1` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_rm_procurement_agent1` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8069 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_agent_procurement_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_agent_procurement_unit` (
  `PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Procurement Agent SKU',
  `PROCUREMENT_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key that indicates which Logistics Unit Id this Procurement Sku maps to',
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Procuremnt Agent this Logistics Unit belongs to',
  `SKU_CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'The Sku code that the Procurement agent refers to this Sku as',
  `VENDOR_PRICE` decimal(14,4) DEFAULT NULL COMMENT 'Price that this Procurement agent is purchasing this Logistics Unit at. If null then default to the price set in for the Planning Unit',
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(4,2) unsigned NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, if we do not have this then take from SKU table',
  `GTIN` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Procurement Sku mapping is Active. False indicates this Procurement Sku has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`),
  UNIQUE KEY `unqProcurementUnitProcurementAgentId` (`PROCUREMENT_AGENT_ID`,`PROCUREMENT_UNIT_ID`) COMMENT 'Unique mapping for Procurement agent and Logistics Unit Id',
  UNIQUE KEY `unq_procurementAgentProcurementUnit_skuCode` (`PROCUREMENT_AGENT_ID`,`SKU_CODE`),
  KEY `fk_procurement_unit_procurement_agent_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_procurement_unit_procurement_agent_procurementUnitId_idx` (`PROCUREMENT_UNIT_ID`),
  KEY `fk_procurement_unit_procurement_agent_createdBy_idx` (`CREATED_BY`),
  KEY `fk_procurement_unit_procurement_agent_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_procurementAgentProcurementUnit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `idx_procurementAgentProcurementUnit_skuCode` (`SKU_CODE`),
  CONSTRAINT `fk_procurement_unit_procurement_agent_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_procurement_agent_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_procurement_agent_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_procurement_agent_procurementUnitId` FOREIGN KEY (`PROCUREMENT_UNIT_ID`) REFERENCES `rm_procurement_unit` (`PROCUREMENT_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17703 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map the Procurement Agent Sku to Procuremnt Unit\nNote: Can be administered at the Realm admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_agent_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_agent_type` (
  `PROCUREMENT_AGENT_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_TYPE_CODE` varchar(10) NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROCUREMENT_AGENT_TYPE_ID`),
  UNIQUE KEY `unq_rm_procurement_agent_type_codeRealm` (`REALM_ID`,`PROCUREMENT_AGENT_TYPE_CODE`),
  KEY `fk_rm_procurement_agent_type_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_procurement_agent_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_procurement_agent_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_procurement_agent_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_procurement_agent_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_procurement_agent_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_procurement_agent_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_procurement_agent_type_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_procurement_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_procurement_unit` (
  `PROCUREMENT_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Logistics unit',
  `PLANNING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key to point which Planning unit this maps to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` int unsigned NOT NULL COMMENT 'Unit of measure for this sku',
  `MULTIPLIER` decimal(12,2) unsigned NOT NULL COMMENT 'Quantity of items in this unit as per the Forecasting Unit Id',
  `SUPPLIER_ID` int unsigned NOT NULL COMMENT 'Foreign key to point which Manufacturer this Logistics unit is from',
  `WIDTH_QTY` decimal(16,6) unsigned DEFAULT NULL COMMENT 'Width',
  `HEIGHT_QTY` decimal(16,6) unsigned DEFAULT NULL COMMENT 'Height',
  `LENGTH_UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Unit Id for a the Length of the Logistics Unit',
  `LENGTH_QTY` decimal(16,6) unsigned DEFAULT NULL COMMENT 'Length',
  `WEIGHT_UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Unit Id for a the Weight of the Logistics Unit',
  `WEIGHT_QTY` decimal(16,6) unsigned DEFAULT NULL COMMENT 'Weight',
  `VOLUME_UNIT_ID` int unsigned DEFAULT NULL,
  `VOLUME_QTY` decimal(16,6) unsigned DEFAULT NULL,
  `UNITS_PER_CASE` decimal(12,2) unsigned DEFAULT NULL,
  `UNITS_PER_PALLET_EURO1` decimal(12,2) unsigned DEFAULT NULL,
  `UNITS_PER_PALLET_EURO2` decimal(12,2) unsigned DEFAULT NULL,
  `UNITS_PER_CONTAINER` decimal(12,2) unsigned DEFAULT NULL COMMENT 'No of Forecast units that fit in a Euro1 pallet',
  `LABELING` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'No of Forecast units that fit in a Euro2 pallet',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Logistics unit is Active. False indicates this Logistics unit has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Create by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROCUREMENT_UNIT_ID`),
  KEY `fk_procurement_unit_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_procurement_unit_labelId_idx` (`LABEL_ID`),
  KEY `fk_procurement_unit_unitId_idx` (`UNIT_ID`),
  KEY `fk_procurement_unit_lengthUnitId_idx` (`LENGTH_UNIT_ID`),
  KEY `fk_procurement_unit_weightUnitId_idx` (`WEIGHT_UNIT_ID`),
  KEY `fk_procurement_unit_createdBy_idx` (`CREATED_BY`),
  KEY `fk_procurement_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_procurement_unit_supplierId_idx` (`SUPPLIER_ID`),
  KEY `idx_procurement_unit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_procurement_unit_volumeUnitId_idx` (`VOLUME_UNIT_ID`),
  CONSTRAINT `fk_procurement_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_lengthUnitId` FOREIGN KEY (`LENGTH_UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_supplierId` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `rm_supplier` (`SUPPLIER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_volumeUnitId` FOREIGN KEY (`VOLUME_UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_procurement_unit_weightUnitId` FOREIGN KEY (`WEIGHT_UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17707 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list the SKU''s for a Product					\nNote: Are based on a Planning unit and can be created or administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_product_category` (
  `PRODUCT_CATEGORY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product Category',
  `REALM_ID` int unsigned DEFAULT NULL COMMENT 'Realm that this Product Category belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `PARENT_PRODUCT_CATEGORY_ID` int unsigned DEFAULT NULL,
  `SORT_ORDER` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Defines the sort level for the Product Category',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Product Category is Active. False indicates this Product Category has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by\n',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PRODUCT_CATEGORY_ID`),
  KEY `fk_product_category_labelId_idx` (`LABEL_ID`),
  KEY `fk_product_category_createdBy_idx` (`CREATED_BY`),
  KEY `fk_product_category_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_sortLevel` (`SORT_ORDER`),
  KEY `fk_rm_product_category_realmId_idx` (`REALM_ID`),
  KEY `idx_productCategory_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_program_category_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_category_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_category_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_product_category_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the product_categories for each health_area\nNote: A Product category can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program` (
  `PROGRAM_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Program',
  `PROGRAM_CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `REALM_ID` int unsigned NOT NULL,
  `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this Program belongs to',
  `ORGANISATION_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Organisation this Program belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `PROGRAM_MANAGER_USER_ID` int unsigned NOT NULL COMMENT 'Captures the person that is responsible for this Program',
  `PROGRAM_NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Program notes',
  `AIR_FREIGHT_PERC` decimal(5,2) unsigned DEFAULT NULL COMMENT 'Percentage of Order Qty when Mode = Air',
  `SEA_FREIGHT_PERC` decimal(5,2) unsigned DEFAULT NULL COMMENT 'Percentage of Order Qty when Mode = Sea',
  `ROAD_FREIGHT_PERC` decimal(5,2) unsigned DEFAULT NULL,
  `PLANNED_TO_SUBMITTED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Planed to Draft status',
  `SUBMITTED_TO_APPROVED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Submitted to Approved status, this will be used only in the case the Procurement Agent is TBD',
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Delivered to Received status',
  `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL,
  `ARRIVED_TO_DELIVERED_LEAD_TIME` decimal(4,2) unsigned DEFAULT NULL,
  `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` int unsigned DEFAULT NULL,
  `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` int unsigned DEFAULT NULL,
  `CURRENT_VERSION_ID` int unsigned DEFAULT NULL COMMENT 'The latest Version no of the Program. Inventory, Consumption and Shipment will all follow this Version no',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Program is Active. False indicates this Program is De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Create  by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, this will be used only in the case the Procurement Agent is TBD',
  `PROGRAM_TYPE_ID` int unsigned NOT NULL COMMENT 'Indicates what type of Program this is. 1 - SupplyPlan 2 - Forecast',
  PRIMARY KEY (`PROGRAM_ID`),
  UNIQUE KEY `unq_program_programCode` (`PROGRAM_CODE`,`REALM_ID`,`PROGRAM_TYPE_ID`),
  KEY `fk_program_realmCountryId` (`REALM_COUNTRY_ID`),
  KEY `fk_program_labelId_idx` (`LABEL_ID`),
  KEY `fk_program_createdBy_idx` (`CREATED_BY`),
  KEY `fk_program_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_program_organisationId_idx` (`ORGANISATION_ID`),
  KEY `idx_program_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_program_realmId_idx` (`REALM_ID`),
  CONSTRAINT `fk_program_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_organisationId` FOREIGN KEY (`ORGANISATION_ID`) REFERENCES `rm_organisation` (`ORGANISATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3077 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the program for each product category\nNote: A Program can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_funding_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_funding_source` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `FUNDING_SOURCE_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`FUNDING_SOURCE_ID`),
  KEY `fk_rm_program_funding_source_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_program_funding_source_fundingSourceId_idx` (`FUNDING_SOURCE_ID`),
  KEY `fk_rm_program_funding_source_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_program_funding_source_fundingSourceId` FOREIGN KEY (`FUNDING_SOURCE_ID`) REFERENCES `rm_funding_source` (`FUNDING_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_funding_source_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_funding_source_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_health_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_health_area` (
  `PROGRAM_HEALTH_AREA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `HEALTH_AREA_ID` int unsigned NOT NULL,
  PRIMARY KEY (`PROGRAM_HEALTH_AREA_ID`),
  KEY `fk_rm_program_health_area_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_program_health_area_healthAreaId_idx` (`HEALTH_AREA_ID`),
  CONSTRAINT `fk_rm_program_health_area_healthAreaId` FOREIGN KEY (`HEALTH_AREA_ID`) REFERENCES `rm_health_area` (`HEALTH_AREA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_health_area_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3966 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_planning_unit` (
  `PROGRAM_PLANNING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Program - Product',
  `PROGRAM_ID` int unsigned NOT NULL COMMENT 'Foreign key for the Program that this mapping refers to',
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REORDER_FREQUENCY_IN_MONTHS` int unsigned NOT NULL COMMENT 'Min number of months of stock that we should have before triggering a reorder',
  `MIN_MONTHS_OF_STOCK` int unsigned DEFAULT NULL,
  `LOCAL_PROCUREMENT_LEAD_TIME` decimal(4,2) unsigned NOT NULL,
  `SHELF_LIFE` int unsigned NOT NULL DEFAULT '0',
  `CATALOG_PRICE` decimal(12,2) unsigned NOT NULL,
  `MONTHS_IN_FUTURE_FOR_AMC` int unsigned NOT NULL,
  `MONTHS_IN_PAST_FOR_AMC` int unsigned NOT NULL,
  `PLAN_BASED_ON` int unsigned NOT NULL COMMENT '1- MoS , 2- Qty',
  `MIN_QTY` int unsigned DEFAULT NULL,
  `DISTRIBUTION_LEAD_TIME` double(6,2) unsigned DEFAULT NULL,
  `FORECAST_ERROR_THRESHOLD` decimal(10,4) DEFAULT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this mapping is Active. False indicates this mapping has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_ID`),
  UNIQUE KEY `unqProgramIdPlanningUnitId` (`PROGRAM_ID`,`PLANNING_UNIT_ID`) COMMENT 'Unique mapping for Program - Product mapping',
  KEY `fk_program_planning_unit_programId_idx` (`PROGRAM_ID`),
  KEY `fk_program_planning_unit_createdBy_idx` (`CREATED_BY`),
  KEY `fk_program_planning_unit_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_program_planning_unit_rm_planning_unit1_idx` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_program_planning_unit_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_planning_unit_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_planning_unit_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_planning_unit_rm_planning_unit1` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7572 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map the ForecastingUnits inside a Program\nNote: Allow the user to select a Product Category and therefore then select all the PlanningUnits that should be used in the Program';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_planning_unit_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_planning_unit_procurement_agent` (
  `PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `PRICE` decimal(14,4) DEFAULT NULL,
  `SEA_FREIGHT_PERC` decimal(5,2) DEFAULT NULL,
  `AIR_FREIGHT_PERC` decimal(5,2) DEFAULT NULL,
  `ROAD_FREIGHT_PERC` decimal(5,2) DEFAULT NULL,
  `PLANNED_TO_SUBMITTED_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `SUBMITTED_TO_APPROVED_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `APPROVED_TO_SHIPPED_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `ARRIVED_TO_DELIVERED_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `LOCAL_PROCUREMENT_LEAD_TIME` decimal(4,2) DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_papup_idx_PROCUREMENT_AGENT_ID` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_papup_idx_CREATED_BY` (`CREATED_BY`),
  KEY `fk_rm_papup_idx_LAST_MODIFIED_BY` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_papup_PROGRAM_ID_idx` (`PROGRAM_ID`),
  KEY `fk_rm_papup_PLANNING_UNIT_ID_idx` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_rm_papup_PLANNING_UNIT_ID` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_rm_papup_PROCUREMENT_AGENT_ID` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_papup_PROGRAM_ID` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`),
  CONSTRAINT `fk_rm_papup_us_user_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_papup_us_user_LAST_MODIFIED_BY` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_procurement_agent` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_program_procurement_agent_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_program_procurement_agent_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_program_procurement_agent_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_program_procurement_agent_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_procurement_agent_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_procurement_agent_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_region` (
  `PROGRAM_REGION_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Region - Program mapping',
  `PROGRAM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Program this Mapping belongs to',
  `REGION_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Region this Mapping belongs to',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Mapping is Active. False indicates this Mapping has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROGRAM_REGION_ID`),
  UNIQUE KEY `unqProgramIdRegionId` (`PROGRAM_ID`,`REGION_ID`) COMMENT 'Unique mapping for Program and Region mapping',
  KEY `fk_program_region_programId_idx` (`PROGRAM_ID`),
  KEY `fk_program_region_regionId_idx` (`REGION_ID`),
  KEY `fk_program_region_createdBy_idx` (`CREATED_BY`),
  KEY `fk_program_region_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_program_region_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_region_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_region_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_region_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_region_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=689 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map which Regions are used by a Program\nNote: Show the list of all Regions for that Country and let the Realm Admin select which Regions he wants to include in the Program. One Region for a Program is compulsory';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_version` (
  `PROGRAM_VERSION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `VERSION_TYPE_ID` int unsigned NOT NULL,
  `VERSION_STATUS_ID` int unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `SENT_TO_ARTMIS` tinyint unsigned NOT NULL COMMENT '1-This version is sent to ARTMIS \n0-Not sent',
  `FORECAST_START_DATE` datetime DEFAULT NULL,
  `FORECAST_STOP_DATE` datetime DEFAULT NULL,
  `DAYS_IN_MONTH` int unsigned DEFAULT NULL,
  `FREIGHT_PERC` decimal(12,2) DEFAULT NULL,
  `FORECAST_THRESHOLD_HIGH_PERC` decimal(12,2) DEFAULT NULL,
  `FORECAST_THRESHOLD_LOW_PERC` decimal(12,2) DEFAULT NULL,
  `VERSION_READY` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`PROGRAM_VERSION_ID`),
  UNIQUE KEY `UNIQUE_VERSION_ID` (`PROGRAM_ID`,`VERSION_ID`),
  KEY `fk_rm_program_version_ap_version_type1_idx` (`VERSION_TYPE_ID`),
  KEY `fk_rm_program_version_ap_version_status1_idx` (`VERSION_STATUS_ID`),
  KEY `fk_rm_program_version_1_idx` (`CREATED_BY`),
  KEY `fk_rm_program_version_2_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_idx_program_version_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_ap_version_status1` FOREIGN KEY (`VERSION_STATUS_ID`) REFERENCES `ap_version_status` (`VERSION_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_ap_version_type1` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=465 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_program_version_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_program_version_trans` (
  `PROGRAM_VERSION_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_VERSION_ID` int unsigned NOT NULL,
  `VERSION_TYPE_ID` int unsigned NOT NULL,
  `VERSION_STATUS_ID` int unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_VERSION_TRANS_ID`),
  KEY `fk_rm_program_version_trans_rm_program_version1_idx` (`PROGRAM_VERSION_ID`),
  KEY `fk_rm_program_version_trans_ap_version_type1_idx` (`VERSION_TYPE_ID`),
  KEY `fk_rm_program_version_trans_ap_version_status1_idx` (`VERSION_STATUS_ID`),
  KEY `fk_rm_program_version_trans_1_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_program_version_trans_1` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_trans_ap_version_status1` FOREIGN KEY (`VERSION_STATUS_ID`) REFERENCES `ap_version_status` (`VERSION_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_trans_ap_version_type1` FOREIGN KEY (`VERSION_TYPE_ID`) REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_program_version_trans_rm_program_version1` FOREIGN KEY (`PROGRAM_VERSION_ID`) REFERENCES `rm_program_version` (`PROGRAM_VERSION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=465 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_realm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_realm` (
  `REALM_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Realm',
  `REALM_CODE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Unique Code for each Realm, will be given at the time of creation and cannot be edited',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `DEFAULT_REALM` tinyint unsigned NOT NULL DEFAULT '0' COMMENT 'If True indicates this Realm is the Default Realm for the Application',
  `MIN_MOS_MIN_GAURDRAIL` int unsigned NOT NULL,
  `MIN_MOS_MAX_GAURDRAIL` int unsigned NOT NULL,
  `MAX_MOS_MAX_GAURDRAIL` int unsigned NOT NULL,
  `MIN_QPL_TOLERANCE` int unsigned NOT NULL,
  `MIN_QPL_TOLERANCE_CUT_OFF` int unsigned NOT NULL,
  `MAX_QPL_TOLERANCE` int unsigned NOT NULL,
  `ACTUAL_CONSUMPTION_MONTHS_IN_PAST` int NOT NULL DEFAULT '6',
  `FORECAST_CONSUMPTION_MONTH_IN_PAST` int NOT NULL DEFAULT '4',
  `INVENTORY_MONTHS_IN_PAST` int NOT NULL DEFAULT '6',
  `MIN_COUNT_FOR_MODE` int unsigned DEFAULT NULL,
  `MIN_PERC_FOR_MODE` decimal(10,2) unsigned DEFAULT NULL,
  `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` int unsigned NOT NULL DEFAULT '6',
  `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` int unsigned NOT NULL DEFAULT '18',
  `NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD` int unsigned NOT NULL DEFAULT '0',
  `NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD` int unsigned NOT NULL DEFAULT '18',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Realm is Active. False indicates this Realm has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '\n\n',
  PRIMARY KEY (`REALM_ID`),
  UNIQUE KEY `REALM_CODE_UNIQUE` (`REALM_CODE`),
  KEY `fk_realm_labelId_idx` (`LABEL_ID`),
  KEY `fk_realm_createdBy_idx` (`CREATED_BY`),
  KEY `fk_realm_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_realm_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_realm_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the different Realms using the application\nNote: A Realm cannot be created in the App but must be created on the Online portal only, Can be created only by Application Admins, but be administered by Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_realm_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_realm_country` (
  `REALM_COUNTRY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Realm - Country mapping',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realm this Mapping belongs to',
  `COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Country this Mapping belongs to',
  `DEFAULT_CURRENCY_ID` int unsigned NOT NULL COMMENT 'Currency Id that this Country should Default to',
  `PALLET_UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Default Pallet size that the country uses',
  `AIR_FREIGHT_PERC` decimal(10,2) unsigned DEFAULT NULL COMMENT 'Percentage of Order Qty when Mode = Air',
  `SEA_FREIGHT_PERC` decimal(10,2) unsigned DEFAULT NULL COMMENT 'Percentage of Order Qty when Mode = Sea',
  `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` decimal(10,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Shipped to Arrived status where mode = Air',
  `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` decimal(10,2) unsigned DEFAULT NULL COMMENT 'No of days for an Order to move from Shipped to Arrived status where mode = Sea',
  `ARRIVED_TO_DELIVERED_LEAD_TIME` decimal(10,2) unsigned DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Mapping is Active. False indicates this Mapping has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`REALM_COUNTRY_ID`),
  UNIQUE KEY `unqRealmIdCountryId` (`REALM_ID`,`COUNTRY_ID`) COMMENT 'Unique key for a Country in a Realm',
  KEY `fk_realm_country_realmId_idx` (`REALM_ID`),
  KEY `fk_realm_country_countryId_idx` (`COUNTRY_ID`),
  KEY `fk_realm_country_currencyId_idx` (`DEFAULT_CURRENCY_ID`),
  KEY `fk_realm_country_createdBy_idx` (`CREATED_BY`),
  KEY `fk_realm_country_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_realmCountry_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_realm_country_countryId` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `ap_country` (`COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_country_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_country_currencyId` FOREIGN KEY (`DEFAULT_CURRENCY_ID`) REFERENCES `ap_currency` (`CURRENCY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_country_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_realm_country_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map which Countries are used by the Realm\nNote: Show the list of all Countries and let the Realm Admin select which Country he wants to include in the Realm';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_realm_country_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_realm_country_planning_unit` (
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Country level SKU',
  `PLANNING_UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key that indicates which Planning unit Id this Country Sku maps to',
  `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key that determines the Realm-Country this Sku belongs to',
  `LABEL_ID` int unsigned NOT NULL,
  `SKU_CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Code that the Country uses to identify the SKU',
  `UNIT_ID` int unsigned NOT NULL COMMENT 'No of items of Planning Unit inside this Country Sku',
  `CONVERSION_METHOD` int unsigned NOT NULL COMMENT '1 for Multiply and 2 for Divide',
  `CONVERSION_NUMBER` decimal(16,6) unsigned NOT NULL,
  `GTIN` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Country Sku is Active. False indicates this Country Sku has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`REALM_COUNTRY_PLANNING_UNIT_ID`),
  UNIQUE KEY `unqCountryPlanningUnitAndSkuCode` (`SKU_CODE`,`REALM_COUNTRY_ID`) COMMENT 'Unique mapping for Country - Planning Unit and Unit Id mapping',
  KEY `fk_planning_unit_country_realmCountryId_idx` (`REALM_COUNTRY_ID`),
  KEY `fk_planning_unit_country_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_planning_unit_country_createdBy_idx` (`CREATED_BY`),
  KEY `fk_planning_unit_country_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_planning_unit_country_ap_unit1_idx` (`UNIT_ID`),
  KEY `idx_realmCountryPlanningUnit_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_planning_unit_country_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_country_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_country_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_planning_unit_country_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_planning_unit_country_ap_unit1` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to map the Country Sku to Planning Unit\nNote: Can be administered at the Realm admin.';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_realm_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_realm_problem` (
  `REALM_PROBLEM_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_ID` int unsigned NOT NULL,
  `PROBLEM_ID` int unsigned NOT NULL,
  `CRITICALITY_ID` int unsigned NOT NULL,
  `DATA1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DATA3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROBLEM_TYPE_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`REALM_PROBLEM_ID`),
  KEY `fk_rm_realm_problem_ap_problem_criticality1_idx` (`CRITICALITY_ID`),
  KEY `fk_rm_realm_problem_us_user1_idx` (`CREATED_BY`),
  KEY `fk_rm_realm_problem_us_user2_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_realm_problem_rm_realm1_idx` (`REALM_ID`),
  KEY `fk_rm_realm_problem_ap_problem1_idx` (`PROBLEM_ID`),
  KEY `fk_rm_realm_problem_ap_problem_type1_idx` (`PROBLEM_TYPE_ID`),
  KEY `idx_realmProblem_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_rm_realm_problem_ap_problem1` FOREIGN KEY (`PROBLEM_ID`) REFERENCES `ap_problem` (`PROBLEM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_realm_problem_ap_problem_criticality1` FOREIGN KEY (`CRITICALITY_ID`) REFERENCES `ap_problem_criticality` (`CRITICALITY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_realm_problem_ap_problem_type1` FOREIGN KEY (`PROBLEM_TYPE_ID`) REFERENCES `ap_problem_type` (`PROBLEM_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_realm_problem_rm_realm1` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_realm_problem_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_realm_problem_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_region` (
  `REGION_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Level',
  `REALM_ID` int unsigned NOT NULL,
  `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm and Country this Level belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CAPACITY_CBM` decimal(14,4) DEFAULT NULL COMMENT 'Cuibic meters of Warehouse capacity, not a compulsory field',
  `GLN` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Level is Active. False indicates this Level has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`REGION_ID`),
  UNIQUE KEY `unq_region_gln` (`GLN`,`REALM_ID`),
  KEY `fk_level_realmCountryId_idx` (`REALM_COUNTRY_ID`),
  KEY `fk_level_labelId_idx` (`LABEL_ID`),
  KEY `fk_level_createdBy_idx` (`CREATED_BY`),
  KEY `fk_level_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_region_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_region_realmId_idx` (`REALM_ID`),
  CONSTRAINT `fk_level_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_level_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_level_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_level_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_region_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the region for a Country for that Realm\nNote: Regions are Country - Realm specific and can only be created or administered by a Real admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_scenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_scenario` (
  `SCENARIO_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Scenario Id',
  `TREE_ID` int unsigned NOT NULL COMMENT 'Foreign key for the tree Id',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `NOTES` text,
  PRIMARY KEY (`SCENARIO_ID`),
  KEY `fk_scenario_treeId_idx` (`TREE_ID`),
  KEY `fk_scenario__idx` (`LABEL_ID`),
  KEY `fk_scenario_createdBy_idx` (`CREATED_BY`),
  KEY `fk_scenario_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_scenario__idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_scenario_treeId_idx` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2083 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment` (
  `SHIPMENT_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `SUGGESTED_QTY` bigint unsigned DEFAULT NULL,
  `CURRENCY_ID` int unsigned NOT NULL,
  `CONVERSION_RATE_TO_USD` decimal(12,2) unsigned NOT NULL,
  `PARENT_SHIPMENT_ID` int unsigned DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `MAX_VERSION_ID` int unsigned NOT NULL,
  `TMP_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`SHIPMENT_ID`),
  KEY `fk_shipment_createdBy_idx` (`CREATED_BY`),
  KEY `fk_shipment_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_shipment_1_idx` (`CURRENCY_ID`),
  KEY `fk_shipment_programId_idx` (`PROGRAM_ID`),
  KEY `idx_parentShipmentId` (`PARENT_SHIPMENT_ID`),
  CONSTRAINT `fk_rm_shipment_1` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `ap_currency` (`CURRENCY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipment_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=11898 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment_linking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment_linking` (
  `SHIPMENT_LINKING_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `PARENT_SHIPMENT_ID` int unsigned NOT NULL,
  `CHILD_SHIPMENT_ID` int unsigned NOT NULL,
  `RO_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `RO_PRIME_LINE_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CONVERSION_FACTOR` decimal(16,4) unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `ORIGINAL_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `ORDER_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PRIME_LINE_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `KN_SHIPMENT_NO` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `MAX_VERSION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`SHIPMENT_LINKING_ID`),
  KEY `fk_rm_shipment_linking_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_shipment_linking_shipmentId_idx` (`PARENT_SHIPMENT_ID`),
  KEY `fk_rm_shipment_linking_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_shipment_linking_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_shipment_linking_childShipmentId_idx` (`CHILD_SHIPMENT_ID`),
  KEY `fk_rm_shipment_linking_programId_idx` (`PROGRAM_ID`),
  CONSTRAINT `fk_rm_shipment_linking_childShipment` FOREIGN KEY (`CHILD_SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_parentShipment` FOREIGN KEY (`PARENT_SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment_linking_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment_linking_trans` (
  `SHIPMENT_LINKING_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SHIPMENT_LINKING_ID` int unsigned NOT NULL,
  `CONVERSION_FACTOR` decimal(16,4) unsigned NOT NULL,
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ACTIVE` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`SHIPMENT_LINKING_TRANS_ID`),
  KEY `fk_rm_shipment_linking_trans_shipmentLinkingId_idx` (`SHIPMENT_LINKING_ID`),
  KEY `fk_rm_shipment_linking_trans_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_shipment_linking_trans_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_linking_trans_shipmentLinkingId` FOREIGN KEY (`SHIPMENT_LINKING_ID`) REFERENCES `rm_shipment_linking` (`SHIPMENT_LINKING_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment_status_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment_status_mapping` (
  `SHIPMENT_STATUS_MAPPING_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `EXTERNAL_STATUS_STAGE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`SHIPMENT_STATUS_MAPPING_ID`),
  KEY `fk_rm_shipment_status_mapping_1_idx` (`SHIPMENT_STATUS_ID`),
  CONSTRAINT `fk_rm_shipment_status_mapping_1` FOREIGN KEY (`SHIPMENT_STATUS_ID`) REFERENCES `ap_shipment_status` (`SHIPMENT_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment_trans` (
  `SHIPMENT_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SHIPMENT_ID` int unsigned NOT NULL COMMENT 'Unique Shipment Id for each Shipment',
  `PLANNING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key that indicates which Product this record is for',
  `REALM_COUNTRY_PLANNING_UNIT_ID` int DEFAULT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned DEFAULT NULL,
  `FUNDING_SOURCE_ID` int unsigned DEFAULT NULL,
  `BUDGET_ID` int unsigned DEFAULT NULL,
  `EXPECTED_DELIVERY_DATE` date NOT NULL COMMENT 'Date that the System is suggesting we need to Plan the shipment based on Lead times',
  `PROCUREMENT_UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key that indicates which Logistics Id the shipment is for. This will be filled out once we have concluded on the order',
  `SUPPLIER_ID` int unsigned DEFAULT NULL,
  `SHIPMENT_QTY` double(24,8) unsigned DEFAULT NULL COMMENT 'Qty of Logistics Unit in the Shipment',
  `SHIPMENT_RCPU_QTY` double(16,4) DEFAULT NULL,
  `RATE` decimal(14,4) DEFAULT NULL,
  `PRODUCT_COST` decimal(24,4) unsigned DEFAULT NULL COMMENT 'Final price of the Shipment for the Goods',
  `SHIPMENT_MODE` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FREIGHT_COST` decimal(24,4) unsigned DEFAULT NULL COMMENT 'Cost of Freight for the Shipment',
  `PLANNED_DATE` date DEFAULT NULL,
  `SUBMITTED_DATE` date DEFAULT NULL,
  `APPROVED_DATE` date DEFAULT NULL COMMENT 'Date the Order was Placed',
  `SHIPPED_DATE` date DEFAULT NULL COMMENT 'Date the Order was Shipped',
  `ARRIVED_DATE` date DEFAULT NULL,
  `RECEIVED_DATE` date DEFAULT NULL COMMENT 'Date the Order was received into Inventory',
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL COMMENT 'Shipment Status Id',
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Notes for this Shipment',
  `ERP_FLAG` tinyint unsigned NOT NULL,
  `ORDER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `PRIME_LINE_NO` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ACCOUNT_FLAG` tinyint unsigned NOT NULL,
  `EMERGENCY_ORDER` tinyint unsigned NOT NULL,
  `LOCAL_PROCUREMENT` tinyint unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last Modified by',
  `DATA_SOURCE_ID` int unsigned NOT NULL COMMENT 'Source of the Inventory',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last Modified date',
  `VERSION_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `PARENT_LINKED_SHIPMENT_ID` int unsigned DEFAULT NULL,
  `AUTO_GENERATED_FREIGHT` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`SHIPMENT_TRANS_ID`),
  UNIQUE KEY `unq_shipmentTrans_shipmentIdVersionId` (`SHIPMENT_ID`,`VERSION_ID`),
  KEY `fk_shipmentTrans_dataSourceId_idx` (`DATA_SOURCE_ID`),
  KEY `fk_shipmentTrans_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_shipmentTrans_shipmentStatusId_idx` (`SHIPMENT_STATUS_ID`),
  KEY `fk_rm_shipment_trans_rm_shipment1_idx` (`SHIPMENT_ID`),
  KEY `fk_shipmentTrans_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_shipmentTrans_supplierId_idx` (`SUPPLIER_ID`),
  KEY `fk_shipmentTrans_procurementUnitId_idx` (`PROCUREMENT_UNIT_ID`),
  KEY `fk_shipmentTrans_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_shipmentTrans_fundingSourceId_idx` (`FUNDING_SOURCE_ID`),
  KEY `fk_shipmentTrans_budgetId_idx` (`BUDGET_ID`),
  KEY `idx_shipmentTrans_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_shipmentTrans_parentLinkedShipmentId_idx` (`PARENT_LINKED_SHIPMENT_ID`),
  CONSTRAINT `fk_shipmentTrans_budgetId` FOREIGN KEY (`BUDGET_ID`) REFERENCES `rm_budget` (`BUDGET_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_dataSourceId` FOREIGN KEY (`DATA_SOURCE_ID`) REFERENCES `rm_data_source` (`DATA_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_fundingSourceId` FOREIGN KEY (`FUNDING_SOURCE_ID`) REFERENCES `rm_funding_source` (`FUNDING_SOURCE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_parentLinkedShipmentId` FOREIGN KEY (`PARENT_LINKED_SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_procurementUnitId` FOREIGN KEY (`PROCUREMENT_UNIT_ID`) REFERENCES `rm_procurement_unit` (`PROCUREMENT_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_shipmentId` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `rm_shipment` (`SHIPMENT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_shipmentStatusId` FOREIGN KEY (`SHIPMENT_STATUS_ID`) REFERENCES `ap_shipment_status` (`SHIPMENT_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shipmentTrans_supplierId` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `rm_supplier` (`SUPPLIER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12277 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to list all the Shipments\nNote: Complete Shipment dump, data can be manually fed or can come for ARTMIS or the Procurement Agent system';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_shipment_trans_batch_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_shipment_trans_batch_info` (
  `SHIPMENT_TRANS_BATCH_INFO_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SHIPMENT_TRANS_ID` int unsigned NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `BATCH_SHIPMENT_QTY` bigint unsigned NOT NULL,
  PRIMARY KEY (`SHIPMENT_TRANS_BATCH_INFO_ID`),
  KEY `fk_rm_shipment_trans_batch_info_rm_shipment_trans1_idx` (`SHIPMENT_TRANS_ID`),
  KEY `fk_rm_shipment_trans_batch_info_rm_batch_info1_idx` (`BATCH_ID`),
  CONSTRAINT `fk_rm_shipment_trans_batch_info_rm_batch_info1` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_shipment_trans_batch_info_rm_shipment_trans1` FOREIGN KEY (`SHIPMENT_TRANS_ID`) REFERENCES `rm_shipment_trans` (`SHIPMENT_TRANS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12351 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_supplier` (
  `SUPPLIER_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Manufacturer',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm this Manufacturer belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SUPPLIER_ID`),
  KEY `fk_supplier_realmId_idx` (`REALM_ID`),
  KEY `fk_supplier_labelId_idx` (`LABEL_ID`),
  KEY `fk_supplier_createdBy_idx` (`CREATED_BY`),
  KEY `fk_supplier_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_supplier_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `fk_supplier_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_supplier_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_supplier_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_supplier_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=460 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the manufacturers for each Realm\nNote: A Supplier can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_supply_plan_amc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_supply_plan_amc` (
  `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `AMC` decimal(24,8) DEFAULT NULL,
  `AMC_COUNT` int DEFAULT NULL,
  `MOS` decimal(24,8) DEFAULT NULL,
  `MOS_WPS` decimal(24,8) DEFAULT NULL,
  `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL,
  `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL,
  `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL,
  `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL,
  `OPENING_BALANCE` decimal(24,8) DEFAULT NULL,
  `OPENING_BALANCE_WPS` decimal(24,8) DEFAULT NULL,
  `MANUAL_PLANNED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `MANUAL_SUBMITTED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `MANUAL_APPROVED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `MANUAL_SHIPPED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `MANUAL_RECEIVED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `MANUAL_ONHOLD_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_PLANNED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_SUBMITTED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_APPROVED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_SHIPPED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_RECEIVED_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `ERP_ONHOLD_SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `SHIPMENT_QTY` decimal(24,8) DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
  `ACTUAL_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
  `ADJUSTED_CONSUMPTION_QTY` decimal(24,8) DEFAULT NULL,
  `ACTUAL` tinyint(1) DEFAULT NULL,
  `ADJUSTMENT_MULTIPLIED_QTY` decimal(24,8) DEFAULT NULL,
  `STOCK_MULTIPLIED_QTY` decimal(24,8) DEFAULT NULL,
  `REGION_COUNT` int unsigned NOT NULL,
  `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL,
  `EXPIRED_STOCK` decimal(24,8) DEFAULT NULL,
  `EXPIRED_STOCK_WPS` decimal(24,8) DEFAULT NULL,
  `CLOSING_BALANCE` decimal(24,8) DEFAULT NULL,
  `CLOSING_BALANCE_WPS` decimal(24,8) DEFAULT NULL,
  `UNMET_DEMAND` decimal(24,8) DEFAULT NULL,
  `UNMET_DEMAND_WPS` decimal(24,8) DEFAULT NULL,
  `NATIONAL_ADJUSTMENT` decimal(24,8) DEFAULT NULL,
  `NATIONAL_ADJUSTMENT_WPS` decimal(24,8) DEFAULT NULL,
  PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`),
  KEY `fk_rm_supply_plan_amc_rm_program1_idx` (`PROGRAM_ID`),
  KEY `fk_rm_supply_plan_amc_rm_planning_unit1_idx` (`PLANNING_UNIT_ID`),
  KEY `idx_rm_supply_plan_amc_transDate` (`TRANS_DATE`),
  KEY `idx_rm_supply_plan_amc_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_supply_plan_amc_rm_planning_unit1` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_supply_plan_amc_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=189280 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_supply_plan_batch_qty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_supply_plan_batch_qty` (
  `SUPPLY_PLAN_BATCH_QTY_ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `EXPIRY_DATE` date NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` double(24,8) unsigned DEFAULT NULL,
  `ACTUAL` tinyint unsigned DEFAULT NULL,
  `SHIPMENT_QTY` double(24,8) unsigned NOT NULL,
  `SHIPMENT_QTY_WPS` double(24,8) unsigned NOT NULL,
  `ADJUSTMENT_MULTIPLIED_QTY` double(24,8) DEFAULT NULL,
  `STOCK_MULTIPLIED_QTY` double(24,8) DEFAULT NULL,
  `ALL_REGIONS_REPORTED_STOCK` tinyint(1) DEFAULT NULL,
  `USE_ADJUSTMENT` tinyint(1) DEFAULT NULL,
  `OPENING_BALANCE` double(24,8) DEFAULT NULL,
  `OPENING_BALANCE_WPS` double(24,8) DEFAULT NULL,
  `EXPIRED_STOCK` double(24,8) DEFAULT NULL,
  `EXPIRED_STOCK_WPS` double(24,8) DEFAULT NULL,
  `CALCULATED_CONSUMPTION` double(24,8) DEFAULT NULL,
  `CALCULATED_CONSUMPTION_WPS` double(24,8) DEFAULT NULL,
  `CLOSING_BALANCE` double(24,8) DEFAULT NULL,
  `CLOSING_BALANCE_WPS` double(24,8) DEFAULT NULL,
  PRIMARY KEY (`SUPPLY_PLAN_BATCH_QTY_ID`),
  KEY `fk_rm_supply_plan_batch_qty_rm_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_supply_plan_batch_qty_rm_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_rm_supply_plan_batch_qty_rm_batchId_idx` (`BATCH_ID`),
  KEY `idx_versionId` (`VERSION_ID`),
  KEY `idx_actual` (`ACTUAL`),
  KEY `idx_transDate` (`TRANS_DATE`),
  KEY `idx_expiredStock` (`EXPIRED_STOCK`),
  KEY `idx_expiredStockWps` (`EXPIRED_STOCK_WPS`),
  CONSTRAINT `fk_rm_supply_plan_batch_qty_rm_batchId_idx` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_supply_plan_batch_qty_rm_planningUnitId_idx` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_supply_plan_batch_qty_rm_programId_idx` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=304102 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tracer_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tracer_category` (
  `TRACER_CATEGORY_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product Category',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Realm that this Product Category belongs to',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `HEALTH_AREA_ID` int unsigned DEFAULT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'If True indicates this Product Category is Active. False indicates this Product Category has been De-activated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by\n',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TRACER_CATEGORY_ID`),
  KEY `fk_tracer_category_labelId_idx` (`LABEL_ID`),
  KEY `fk_tracer_category_createdBy_idx` (`CREATED_BY`),
  KEY `fk_tracer_category_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_tracer_category_realmId_idx` (`REALM_ID`),
  KEY `idx_tracer_category_lastModifiedDt` (`LAST_MODIFIED_DATE`),
  KEY `fk_rm_tracer_category_healthAreaId_idx` (`HEALTH_AREA_ID`),
  CONSTRAINT `fk_rm_tracer_category_healthAreaId` FOREIGN KEY (`HEALTH_AREA_ID`) REFERENCES `rm_health_area` (`HEALTH_AREA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_tracer_category_realmId0` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tracer_category_createdBy0` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tracer_category_labelId0` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tracer_category_lastModifiedBy0` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to define the product_categories for each health_area\nNote: A Product category can only be created and administered by a Realm Admin';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template` (
  `TREE_TEMPLATE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique forecast tree template id',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key that maps to the Realm this Template is for',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `FORECAST_METHOD_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Forecast method is used for this Tree',
  `MONTHS_IN_PAST` int unsigned DEFAULT NULL,
  `MONTHS_IN_FUTURE` int unsigned DEFAULT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `NOTES` text,
  PRIMARY KEY (`TREE_TEMPLATE_ID`),
  KEY `fk_treeTemplate_realmId_idx` (`REALM_ID`),
  KEY `fk_treeTemplate_labelId_idx` (`LABEL_ID`),
  KEY `fk_treeTemplate_forecastMethodId_idx` (`FORECAST_METHOD_ID`),
  KEY `fk_treeTemplate_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplate_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplate_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplate_forecastMethodId_idx` FOREIGN KEY (`FORECAST_METHOD_ID`) REFERENCES `rm_forecast_method` (`FORECAST_METHOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplate_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplate_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplate_realmId_idx` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_level` (
  `TREE_TEMPLATE_LEVEL_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TREE_TEMPLATE_ID` int unsigned NOT NULL,
  `LEVEL_NO` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `UNIT_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`TREE_TEMPLATE_LEVEL_ID`),
  KEY `fk_rm_tree_template_level_treeTemplateId_idx` (`TREE_TEMPLATE_ID`),
  KEY `fk_rm_tree_template_level_unitId_idx` (`UNIT_ID`),
  KEY `fk_rm_tree_template_level_labelId_idx` (`LABEL_ID`),
  KEY `idx_rm_tree_template_level_levelNo` (`LEVEL_NO`),
  CONSTRAINT `fk_rm_tree_template_level_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_tree_template_level_treeTemplateId` FOREIGN KEY (`TREE_TEMPLATE_ID`) REFERENCES `rm_tree_template` (`TREE_TEMPLATE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_tree_template_level_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5331 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node` (
  `NODE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node id for tree',
  `TREE_TEMPLATE_ID` int unsigned NOT NULL COMMENT 'TreeTemplateId that this Node belongs to',
  `PARENT_NODE_ID` int unsigned DEFAULT NULL COMMENT 'Node Id of the parent. Null if this is the root.',
  `SORT_ORDER` varchar(300) NOT NULL COMMENT 'Sort order of the Node in the tree',
  `LEVEL_NO` int unsigned NOT NULL COMMENT 'Level that this node appears on ',
  `NODE_TYPE_ID` int unsigned NOT NULL COMMENT 'What type of Node is this',
  `UNIT_ID` int unsigned DEFAULT NULL COMMENT 'Indicates the Unit for this Node',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `COLLAPSED` tinyint NOT NULL DEFAULT '0',
  `DOWNWARD_AGGREGATION_ALLOWED` tinyint unsigned NOT NULL DEFAULT '0',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `fk_treeTemplateNode_treeTemplateId_idx` (`TREE_TEMPLATE_ID`),
  KEY `fk_treeTemplateNode_parentNodeId_idx` (`PARENT_NODE_ID`),
  KEY `idx_treeTemplateNode_sortOrder` (`SORT_ORDER`),
  KEY `fk_treeTemplateNode_nodeTypeId_idx` (`NODE_TYPE_ID`),
  KEY `fk_treeTemplateNode_unitId_idx` (`UNIT_ID`),
  KEY `fk_treeTemplateNode_labelId_idx` (`LABEL_ID`),
  KEY `fk_treeTemplateNode_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNode_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplateNode_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_labelId_idx` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_nodeTypeId_idx` FOREIGN KEY (`NODE_TYPE_ID`) REFERENCES `ap_node_type` (`NODE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_parentNodeId_idx` FOREIGN KEY (`PARENT_NODE_ID`) REFERENCES `rm_tree_template_node` (`NODE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_treeTemplateId_idx` FOREIGN KEY (`TREE_TEMPLATE_ID`) REFERENCES `rm_tree_template` (`TREE_TEMPLATE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNode_unitId_idx` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=33597 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data` (
  `NODE_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique node data id for the node and scenario',
  `NODE_ID` int unsigned NOT NULL COMMENT 'node id ',
  `MONTH` int NOT NULL COMMENT 'Indicates the month that this Data is for, is always a +ve number starting from 0 which is for the Start month. Cannot be greater than the MonthsInPast+MonthsInFuture+1',
  `DATA_VALUE` decimal(24,8) DEFAULT NULL COMMENT 'Based on the NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent',
  `NODE_DATA_FU_ID` int unsigned DEFAULT NULL,
  `NODE_DATA_PU_ID` int unsigned DEFAULT NULL,
  `NOTES` text COMMENT 'Notes that describe the Node',
  `MANUAL_CHANGES_EFFECT_FUTURE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ID`),
  KEY `fk_treeTemplateNodeData_nodeId_idx` (`NODE_ID`),
  KEY `fk_treeTemplateNodeData_nodeDataFuId_idx` (`NODE_DATA_FU_ID`),
  KEY `fk_treeTemplateNodeData_nodeDataPuId_idx` (`NODE_DATA_PU_ID`),
  KEY `fk_treeTemplateNodeData_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNodeData_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplateNodeData_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeData_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeData_nodeDataFuId_idx` FOREIGN KEY (`NODE_DATA_FU_ID`) REFERENCES `rm_tree_template_node_data_fu` (`NODE_DATA_FU_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeData_nodeDataPuId_idx` FOREIGN KEY (`NODE_DATA_PU_ID`) REFERENCES `rm_tree_template_node_data_pu` (`NODE_DATA_PU_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeData_nodeId_idx` FOREIGN KEY (`NODE_ID`) REFERENCES `rm_tree_template_node` (`NODE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=33583 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_annual_target_calculator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` int unsigned NOT NULL,
  `CALCULATOR_FIRST_MONTH` varchar(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` int unsigned NOT NULL,
  `CREATED_BY` int unsigned DEFAULT NULL,
  `CREATED_DATE` varchar(45) DEFAULT NULL,
  `LAST_MODIFIED_BY` int unsigned DEFAULT NULL,
  `LAST_MODIFIED_DATE` varchar(45) DEFAULT NULL,
  `ACTIVE` tinyint unsigned DEFAULT '1',
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  KEY `fk_rm_ttnd_annual_target_calculator_idx` (`NODE_DATA_ID`),
  CONSTRAINT `fk_rm_ttnd_annual_target_calculator_nodeDataId` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_annual_target_calculator_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` int unsigned NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  KEY `fk_rm_ttnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  CONSTRAINT `fk_rm_ttnd_annual_target_calculator_annualTargetCalculatorId` FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`) REFERENCES `rm_tree_template_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_fu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_fu` (
  `NODE_DATA_FU_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',
  `FORECASTING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate forecasting id',
  `LAG_IN_MONTHS` int unsigned NOT NULL COMMENT '# of months to wait before using',
  `USAGE_TYPE_ID` int unsigned NOT NULL COMMENT '1 for Discrete, 2 for Continuous',
  `NO_OF_PERSONS` int unsigned NOT NULL COMMENT '# of Patients this usage will be used for',
  `FORECASTING_UNITS_PER_PERSON` decimal(18,4) unsigned NOT NULL COMMENT '# of Forecasting Units ',
  `ONE_TIME_USAGE` tinyint unsigned NOT NULL,
  `ONE_TIME_DISPENSING` tinyint unsigned NOT NULL DEFAULT '1',
  `USAGE_FREQUENCY` decimal(18,4) unsigned DEFAULT NULL COMMENT '# of times the Forecasting Unit is given per Usage',
  `USAGE_FREQUENCY_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',
  `REPEAT_COUNT` decimal(18,4) unsigned DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type',
  `REPEAT_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_FU_ID`),
  KEY `fk_treeTemplateNodeDataFu_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_treeTemplateNodeDataFu_usageTypeId_idx` (`USAGE_TYPE_ID`),
  KEY `fk_treeTemplateNodeDataFu_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID`),
  KEY `fk_treeTemplateNodeDataFu_repeatUsagePeriodId_idx` (`REPEAT_USAGE_PERIOD_ID`),
  KEY `fk_treeTemplateNodeDataFu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNodeDataFu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplateNodeDataFu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataFu_forecastingUnitId_idx` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataFu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataFu_repeatUsagePeriodId_idx` FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataFu_usageFrequencyUsagePeriodId_idx` FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataFu_usageTypeId_idx` FOREIGN KEY (`USAGE_TYPE_ID`) REFERENCES `ap_usage_type` (`USAGE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=11425 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_modeling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_modeling` (
  `NODE_DATA_MODELING_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for NodeScaleUp',
  `NODE_DATA_ID` int unsigned NOT NULL COMMENT 'Node that this ScaleUp referrs to',
  `START_DATE` int NOT NULL COMMENT 'Start date that the Modeling is applicable from. Starts from the Forecast Program Start',
  `STOP_DATE` int NOT NULL COMMENT 'Stop date that the Modeling is applicable from. Defaults to Forecast Program End but user can override',
  `MODELING_TYPE_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate scale type id',
  `DATA_VALUE` decimal(18,4) DEFAULT NULL COMMENT 'Data value could be a number of a % based on the ModelingTypeId',
  `INCREASE_DECREASE` int NOT NULL DEFAULT '1',
  `TRANSFER_NODE_DATA_ID` int unsigned DEFAULT NULL COMMENT 'Indicates the Node that this data Scale gets transferred to. If null then it does not get transferred.',
  `NOTES` text COMMENT 'Notes to desribe this scale up',
  `MODELING_SOURCE` int unsigned NOT NULL COMMENT '0 for manual entry or old calculator, 1 for annual target calculator',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_MODELING_ID`),
  KEY `fk_treeTemplateNodeDataModeling_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_treeTemplateNodeDataModeling_modelingTypeId_idx` (`MODELING_TYPE_ID`),
  KEY `fk_treeTemplateNodeDataModeling_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNodeDataModeling_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_treeTemplateNodeDataModeling_transferNodeId_idx_idx` (`TRANSFER_NODE_DATA_ID`),
  CONSTRAINT `fk_treeTemplateNodeDataModeling_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataModeling_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataModeling_modelingTypeId_idx` FOREIGN KEY (`MODELING_TYPE_ID`) REFERENCES `ap_modeling_type` (`MODELING_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataModeling_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataModeling_transferNodeId_idx` FOREIGN KEY (`TRANSFER_NODE_DATA_ID`) REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_override`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_override` (
  `NODE_DATA_OVERRIDE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Override Id',
  `NODE_DATA_ID` int unsigned NOT NULL COMMENT 'The Node Data Id that this Override is for',
  `MONTH_NO` int NOT NULL COMMENT 'Month that this Override is for',
  `MANUAL_CHANGE` decimal(18,4) NOT NULL COMMENT 'The manual change value',
  `SEASONALITY_PERC` decimal(18,4) DEFAULT NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_OVERRIDE_ID`),
  KEY `fk_treeTemplateNodeDataOverride_nodeDataId_idx` (`NODE_DATA_ID`),
  KEY `fk_treeTemplateNodeDataOverride_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNodeDataOverride_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplateNodeDataOverride_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataOverride_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataOverride_nodeDataId_idx` FOREIGN KEY (`NODE_DATA_ID`) REFERENCES `rm_tree_template_node_data` (`NODE_DATA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_data_pu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_data_pu` (
  `NODE_DATA_PU_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each PU Conversion',
  `PLANNING_UNIT_ID` int unsigned NOT NULL COMMENT 'What Palnning Unit does this Node convert to',
  `SHARE_PLANNING_UNIT` tinyint unsigned NOT NULL COMMENT 'If 1 that means this Planning Unit is to be shared with others and therefore maintain the decimal, if it is not shared you need to round to 1',
  `REFILL_MONTHS` decimal(16,4) unsigned DEFAULT NULL COMMENT '# of moths over which refulls are taken',
  `PU_PER_VISIT` decimal(20,8) unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_PU_ID`),
  KEY `fk_treeTemplateNodeDataPu_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `fk_treeTemplateNodeDataPu_createdBy_idx` (`CREATED_BY`),
  KEY `fk_treeTemplateNodeDataPu_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_treeTemplateNodeDataPu_createdBy_idx` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataPu_lastModifiedBy_idx` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_treeTemplateNodeDataPu_planningUnitId_idx` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=11587 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_tree_template_node_downward_aggregation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_tree_template_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` int unsigned NOT NULL,
  `SOURCE_TREE_TEMPLATE_ID` int unsigned NOT NULL,
  `SOURCE_NODE_ID` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  KEY `fk_rm_forecast_tree_node_downward_aggregation_targetNodeId_idx` (`TARGET_NODE_ID`),
  KEY `fk_rm_forecast_tree_node_downward_aggregation_targetTreeTem_idx` (`SOURCE_TREE_TEMPLATE_ID`),
  KEY `fk_rm_forecast_tree_node_downward_aggregation_sourceNodeId_idx` (`SOURCE_NODE_ID`),
  CONSTRAINT `fk_ftnda_sourceNodeId` FOREIGN KEY (`SOURCE_NODE_ID`) REFERENCES `rm_tree_template_node` (`NODE_ID`),
  CONSTRAINT `fk_ftnda_sourceTreeTemplateId` FOREIGN KEY (`SOURCE_TREE_TEMPLATE_ID`) REFERENCES `rm_tree_template` (`TREE_TEMPLATE_ID`),
  CONSTRAINT `fk_ftnda_targetNodeId` FOREIGN KEY (`TARGET_NODE_ID`) REFERENCES `rm_tree_template_node` (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `rm_usage_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rm_usage_template` (
  `USAGE_TEMPLATE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each usage',
  `REALM_ID` int unsigned NOT NULL COMMENT 'Foreign key of Realm Id that the Program belongs to',
  `PROGRAM_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key to indicate which Program Id',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` int unsigned NOT NULL,
  `FORECASTING_UNIT_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate forecasting id',
  `LAG_IN_MONTHS` int unsigned NOT NULL COMMENT '# of months to wait before using',
  `USAGE_TYPE_ID` int unsigned NOT NULL COMMENT '1 for Discrete, 2 for Continuous',
  `NO_OF_PATIENTS` int unsigned NOT NULL COMMENT '# of Patients this usage will be used for',
  `NO_OF_FORECASTING_UNITS` decimal(16,4) unsigned NOT NULL COMMENT '# of Forecasting Units ',
  `ONE_TIME_USAGE` tinyint unsigned NOT NULL,
  `USAGE_FREQUENCY_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc)',
  `USAGE_FREQUENCY_COUNT` decimal(16,4) unsigned DEFAULT NULL COMMENT '# of UsagePeriod that this is given over',
  `REPEAT_USAGE_PERIOD_ID` int unsigned DEFAULT NULL COMMENT 'Foreign Key that points to the UsagePeriod (every day, week, month etc) for Repeat Count',
  `REPEAT_COUNT` decimal(16,4) unsigned DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type',
  `NOTES` text,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  PRIMARY KEY (`USAGE_TEMPLATE_ID`),
  KEY `fk_rm_usage_template_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_usage_template_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_usage_template_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_usage_template_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `fk_rm_usage_template_usageTypeId_idx` (`USAGE_TYPE_ID`),
  KEY `fk_rm_usage_template_usageFrequencyUsagePeriodId_idx` (`USAGE_FREQUENCY_USAGE_PERIOD_ID`),
  KEY `fk_rm_usage_template_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_usage_template_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_rm_usage_template_repeatUsagePeriodId` (`REPEAT_USAGE_PERIOD_ID`),
  KEY `fk_rm_usage_template_unitId_idx` (`UNIT_ID`),
  CONSTRAINT `fk_rm_usage_template_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_forecastingUnitId` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_repeatUsagePeriodId` FOREIGN KEY (`REPEAT_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_usageFrequencyUsagePeriodId` FOREIGN KEY (`USAGE_FREQUENCY_USAGE_PERIOD_ID`) REFERENCES `ap_usage_period` (`USAGE_PERIOD_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rm_usage_template_usageTypeId` FOREIGN KEY (`USAGE_TYPE_ID`) REFERENCES `ap_usage_type` (`USAGE_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `temp_security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temp_security` (
  `SECURITY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `METHOD` int NOT NULL,
  `URL_LIST` text NOT NULL,
  `BF_LIST` text NOT NULL,
  PRIMARY KEY (`SECURITY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=422 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tk_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tk_ticket` (
  `TICKET_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket',
  `TICKET_TYPE_ID` int unsigned NOT NULL COMMENT 'Ticket type that this Ticket is of',
  `REFFERENCE_ID` int unsigned NOT NULL COMMENT 'Foreign key that points to the Primary key of the Table that the Ticket is for',
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Notes that the creator wants to include in the request for the Ticket',
  `TICKET_STATUS_ID` int unsigned NOT NULL COMMENT 'Status for the Ticket',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_ID`),
  KEY `fk_ticket_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ticket_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ticket_ticket_statusId_idx` (`TICKET_STATUS_ID`),
  KEY `fk_ticket_ticket_typeId_idx` (`TICKET_TYPE_ID`),
  CONSTRAINT `fk_ticket_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_ticket_statusId` FOREIGN KEY (`TICKET_STATUS_ID`) REFERENCES `tk_ticket_status` (`TICKET_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_ticket_typeId` FOREIGN KEY (`TICKET_TYPE_ID`) REFERENCES `tk_ticket_type` (`TICKET_TYPE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the tickets received for different changes to Masters\nNote: Would only be for a specific Realm';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tk_ticket_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tk_ticket_status` (
  `TICKET_STATUS_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket Status	',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this Ticket Status is Active. False indicates this Ticket Status has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_STATUS_ID`),
  KEY `fk_ticket_status_createdBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ticket_status_lastModifiedBy_idx` (`CREATED_BY`),
  KEY `fk_ticket_status_labelId_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ticket_status_createdBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_status_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_status_lastModifiedBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Ticket statuses';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tk_ticket_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tk_ticket_trans` (
  `TICKET_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Transaction id',
  `TICKET_ID` int unsigned NOT NULL COMMENT 'Ticket Id that this transaction is for',
  `TICKET_STATUS_ID` int unsigned NOT NULL COMMENT 'Status for the Ticket',
  `NOTES` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Notes from the transaction',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_TRANS_ID`),
  KEY `fk_ticket_trans_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ticket_trans_ticketId_idx` (`TICKET_ID`),
  KEY `fk_ticket_trans_ticket_statusId_idx` (`TICKET_STATUS_ID`),
  CONSTRAINT `fk_ticket_trans_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_trans_ticket_statusId` FOREIGN KEY (`TICKET_STATUS_ID`) REFERENCES `tk_ticket_status` (`TICKET_STATUS_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_trans_ticketId` FOREIGN KEY (`TICKET_ID`) REFERENCES `tk_ticket` (`TICKET_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Transaction table for the Tickets';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tk_ticket_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tk_ticket_type` (
  `TICKET_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket Type',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `TICKET_LEVEL` int unsigned NOT NULL COMMENT 'The Level the ticket is for. 1 - Application level, 2 - Realm level, 3 - Program level',
  `ACTIVE` tinyint unsigned NOT NULL COMMENT 'If True indicates this Ticket Type is Active. False indicates this Ticket Type has been Deactivated',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_TYPE_ID`),
  KEY `fk_ticket_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_ticket_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_ticket_type_labelId_idx` (`LABEL_ID`),
  CONSTRAINT `fk_ticket_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ticket_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Ticket types\nNote: The Level the ticket is for. 1 - Application level, 2 - Realm level, 3 - Program level';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_consumption_all_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_consumption_all_table` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ProductID` varchar(200) DEFAULT NULL,
  `ConsActualFlag` tinyint DEFAULT NULL,
  `ConsAmount` decimal(26,6) DEFAULT NULL,
  `ConsDataSourceID` varchar(200) DEFAULT NULL,
  `ConsNote` text,
  `Country` varchar(200) DEFAULT NULL,
  `Level` varchar(200) DEFAULT NULL,
  `Product_Group` varchar(200) DEFAULT NULL,
  `PSM_SKU` varchar(200) DEFAULT NULL,
  `UOM` decimal(26,6) DEFAULT NULL,
  `Date` varchar(200) DEFAULT NULL,
  `UOM_Quantity` decimal(26,6) DEFAULT NULL,
  `PROGRAM_ID` int DEFAULT NULL,
  `PLANNING_UNIT_ID` int DEFAULT NULL,
  `CONSUMPTION_ID` int DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idxProductID` (`ProductID`),
  KEY `idxProgramId` (`PROGRAM_ID`),
  KEY `idxPlanningUnitId` (`PLANNING_UNIT_ID`),
  KEY `idxRealmCountryPlanningUnitId` (`REALM_COUNTRY_PLANNING_UNIT_ID`),
  KEY `idxConsumptionId` (`CONSUMPTION_ID`),
  KEY `idxPsmSku` (`PSM_SKU`)
) ENGINE=InnoDB AUTO_INCREMENT=4587 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_erp_delinked_programs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_erp_delinked_programs` (
  `PROGRAM_ID` int NOT NULL,
  PRIMARY KEY (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_8E0L`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_8E0L` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_ATUJ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_ATUJ` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_EJ4Q`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_EJ4Q` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_KHHL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_KHHL` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_M26D`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_M26D` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_REFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_REFO` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecastingMetric_S1I7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecastingMetric_S1I7` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `ACTUAL` tinyint(1) NOT NULL,
  `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
  `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_forecasting_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_forecasting_unit` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `LABEL` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LABEL_ID` int unsigned DEFAULT NULL,
  `GENERIC_LABEL` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `GENERIC_LABEL_ID` int unsigned DEFAULT NULL,
  `UNIT_LABEL_EN` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `COMMODITY_COUNCIL` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SUB_CATEGORY` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `TRACER_CATEGORY` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `FORECASTING_UNIT_ID` int unsigned DEFAULT NULL,
  `FOUND` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idxLabel` (`LABEL`),
  KEY `idxGenericLabel` (`GENERIC_LABEL`),
  KEY `idxForecastingUnit_forecastingUnitId_idx` (`FORECASTING_UNIT_ID`),
  KEY `idxForecastingUnit_labelId_idx` (`LABEL_ID`),
  KEY `idxForecastingUnit_unitId_idx` (`UNIT_LABEL_EN`),
  KEY `idxForecastingUnit_genericLabelId_idx` (`GENERIC_LABEL_ID`),
  KEY `idxForecastingUnit_commodityCouncil_idx` (`COMMODITY_COUNCIL`),
  KEY `idxForecastingUnit_subCategory_idx` (`SUB_CATEGORY`),
  KEY `idxForecastingUnit_tracerCategoryId_idx` (`TRACER_CATEGORY`),
  KEY `idxForecastingUnitFound` (`FOUND`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_inv_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_inv_table` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ProductID` varchar(45) DEFAULT NULL,
  `Period` varchar(45) DEFAULT NULL,
  `InvAmount` varchar(50) DEFAULT NULL,
  `InvTransferFlag` varchar(20) DEFAULT NULL,
  `InvNote` text,
  `InvDataSourceID` varchar(45) DEFAULT NULL,
  `Country` varchar(10) DEFAULT NULL,
  `Product_Group` varchar(45) DEFAULT NULL,
  `Level` varchar(45) DEFAULT NULL,
  `PSMSKU` varchar(45) DEFAULT NULL,
  `UOM` varchar(45) DEFAULT NULL,
  `UOMQuantity` varchar(45) DEFAULT NULL,
  `PROGRAM_ID` int unsigned DEFAULT NULL,
  `PLANNING_UNIT_ID` int DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int unsigned DEFAULT NULL,
  `NOT_TO_BE_USED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1574 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_nsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_nsp` (
  `TMP_NSP_ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `TRANS_DATE` date NOT NULL,
  `REGION_ID` int unsigned DEFAULT NULL,
  `FORECASTED_CONSUMPTION` decimal(24,8) unsigned DEFAULT NULL,
  `ACTUAL_CONSUMPTION` decimal(24,8) unsigned DEFAULT NULL,
  `ADJUSTED_CONSUMPTION` decimal(24,8) unsigned DEFAULT NULL,
  `USE_ACTUAL_CONSUMPTION` tinyint unsigned DEFAULT NULL,
  `ADJUSTMENT` decimal(24,8) DEFAULT NULL,
  `STOCK` decimal(24,8) unsigned DEFAULT NULL,
  `REGION_STOCK_COUNT` int unsigned DEFAULT NULL,
  `REGION_COUNT` int unsigned NOT NULL,
  `SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `SHIPMENT_WPS` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_PLANNED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_SUBMITTED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_APPROVED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_SHIPPED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_RECEIVED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `MANUAL_ONHOLD_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_PLANNED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_SUBMITTED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_APPROVED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_SHIPPED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_RECEIVED_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  `ERP_ONHOLD_SHIPMENT` decimal(24,8) unsigned DEFAULT NULL,
  PRIMARY KEY (`TMP_NSP_ID`),
  UNIQUE KEY `unq_tmp_nsp_record` (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`,`REGION_ID`),
  KEY `rm_tmp_nsp_programId_idx` (`PROGRAM_ID`),
  KEY `rm_tmp_nsp_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `rm_tmp_nsp_versionId_idx` (`VERSION_ID`),
  KEY `rm_tmp_nsp_transDate_idx` (`TRANS_DATE`),
  KEY `fk_rm_nsp_regionId_idx` (`REGION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_price_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_price_mapping` (
  `COUNTRY_CODE` varchar(3) NOT NULL,
  `COUNTRY_NAME` varchar(50) NOT NULL,
  `PRODUCT_SKU` varchar(15) NOT NULL,
  `PRICE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_product_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_product_catalog` (
  `TaskOrder` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CommodityCouncil` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Subcategory` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `TracerCategory` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductActive` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductIDNoPack` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductNameNoPack` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductID` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ProductName` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `OrderUOM` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PackSize` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `NoofBaseUnits` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `BaseUnit` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `L5DataTrusteeCode` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UNSPSC` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `INN` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Controlled` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Route` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Form` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `QACategory` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `QACriteria` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug1Name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug1Abbr` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug1Qty` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug1Meas` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug1Unit` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug2Name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug2Abbr` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug2Qty` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug2Meas` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug2Unit` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug3Name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug3Abbr` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug3Qty` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug3Meas` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug3Unit` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug4Name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug4Abbr` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug4Qty` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug4Meas` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Drug4Unit` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `USAIDARVTier` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PlanningUnitMOQ` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PlanningUnitsperPallet` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PlanningUnitsperContainer` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PlanningUnitVolumem3` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PlanningUnitWeightkg` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ItemID` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ItemName` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Supplier` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `WeightUOM` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Weight` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `HeightUOM` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Height` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Length` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Width` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `GTIN` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Labeling` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ItemAvailable` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UnitsperCase` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UnitsperPallet` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UnitsperContainer` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `EstPrice` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Euro1` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Euro2` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  KEY `idxProductNameNoPack` (`ProductNameNoPack`),
  KEY `idxProductName` (`ProductName`),
  KEY `idxItemName` (`ItemName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_product_psm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_product_psm` (
  `ProductID` varchar(250) DEFAULT NULL,
  `ProductName` varchar(250) DEFAULT NULL,
  `ProductMinMonths` varchar(250) DEFAULT NULL,
  `ProductMaxMonths` varchar(250) DEFAULT NULL,
  `SupplierID` varchar(250) DEFAULT NULL,
  `ProductActiveFlag` varchar(250) DEFAULT NULL,
  `ProductActiveDate` varchar(250) DEFAULT NULL,
  `ProductNote` text,
  `ProdCMax` varchar(250) DEFAULT NULL,
  `ProdCMin` varchar(250) DEFAULT NULL,
  `ProdDesStock` varchar(250) DEFAULT NULL,
  `Country` varchar(250) DEFAULT NULL,
  `Product_Group` varchar(250) DEFAULT NULL,
  `Level` varchar(250) DEFAULT NULL,
  `PSM_SKU` varchar(250) DEFAULT NULL,
  `UOM` decimal(26,6) DEFAULT NULL,
  `PLANNING_UNIT_ID` int DEFAULT NULL,
  `PLANNING_UNIT_LABEL_ID` int DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_ID` int DEFAULT NULL,
  `REALM_COUNTRY_PLANNING_UNIT_LABEL_ID` int DEFAULT NULL,
  `PROGRAM_ID` int DEFAULT NULL,
  KEY `idxProductID` (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_shipment_w_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_shipment_w_orders` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ProductID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `SupplierID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipDataSourceID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipAmount` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UOM` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UOM_ShipAmount` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipPlannedDate` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipOrderedDate` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipShippedDate` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipReceivedDate` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipStatusCode` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipNote` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `ShipPO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipFreightCost` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipValue` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipDisplayNote` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ShipFundingSourceID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Country` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Product_Group` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Level` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Translated_Funder` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PSM_SKU` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Estimated_Unit_Price` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `Estimated_Line_Price` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROGRAM_ID` int DEFAULT NULL,
  `PLANNING_UNIT_ID` int DEFAULT NULL,
  `BUDGET_ID` int DEFAULT NULL,
  `LOCAL` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=802 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tmp_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmp_year` (
  `YR` int unsigned NOT NULL,
  PRIMARY KEY (`YR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_artmis_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_artmis_country` (
  `RECEPIENT_NAME` varchar(200) NOT NULL,
  `REALM_COUNTRY_ID` int unsigned NOT NULL,
  PRIMARY KEY (`RECEPIENT_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_data_source` (
  `DataSourceID` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `DATA_SOURCE_ID` int unsigned NOT NULL,
  `PROGRAM_ID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`DataSourceID`,`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_funding_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_funding_source` (
  `PipelineFunder` varchar(255) NOT NULL,
  `PROGRAM_ID` int NOT NULL,
  `FUNDING_SOURCE_ID` int DEFAULT NULL,
  PRIMARY KEY (`PipelineFunder`,`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_planning_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_planning_unit` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `ProductID` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PLANNING_UNIT_ID` int DEFAULT NULL,
  `PSM_SKU_CODE` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `UOM` decimal(14,4) DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_procurement_agent` (
  `PipelineSupplier` varchar(255) NOT NULL,
  `PROGRAM_ID` int NOT NULL,
  `PROCUREMENT_AGENT_ID` int DEFAULT NULL,
  `LOCAL` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PipelineSupplier`,`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_product` (
  `ProductID` varchar(255) NOT NULL,
  `PROGRAM_ID` int NOT NULL,
  `Min` int DEFAULT NULL,
  `Max` int DEFAULT NULL,
  `AMC_Months_in_Past` int DEFAULT NULL,
  `AMC_Months_in_Future` int DEFAULT NULL,
  `LocalProcurement_leadTime` decimal(4,2) DEFAULT NULL,
  `ShelfLife` int DEFAULT NULL,
  `DefaultPrice` double(16,6) DEFAULT NULL,
  `NewAruName` varchar(255) DEFAULT NULL,
  `PsmSku` varchar(15) DEFAULT NULL,
  `UOM` decimal(16,6) DEFAULT NULL,
  PRIMARY KEY (`ProductID`,`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_product_procurement_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_product_procurement_agent` (
  `ProductID` varchar(255) NOT NULL,
  `PROGRAM_ID` int NOT NULL,
  `PROCUREMENT_AGENT_ID` int NOT NULL,
  `Price` decimal(16,6) NOT NULL,
  PRIMARY KEY (`ProductID`,`PROGRAM_ID`,`PROCUREMENT_AGENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_program` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `REALM_COUNTRY_ID` int unsigned NOT NULL,
  `Product_Group` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PATH` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `PROGRAM_VERSION_TYPE_ID` int DEFAULT NULL,
  `CUT_OFF_DATE` date DEFAULT NULL,
  `LEVEL` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `REGION_ID` int DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`),
  UNIQUE KEY `unqIndexForRealmCountryAndProductGroup` (`REALM_COUNTRY_ID`,`Product_Group`,`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_region` (
  `Level` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REGION_LABEL` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`Level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_shipment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_shipment_status` (
  `ShipStatusCode` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `SHIPMENT_STATUS_ID` int unsigned NOT NULL,
  PRIMARY KEY (`ShipStatusCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tr_translatated_funder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tr_translatated_funder` (
  `Translated_Funder` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROCUREMENT_AGENT_ID` int NOT NULL,
  `FUNDING_SOURCE_ID` int NOT NULL,
  PRIMARY KEY (`Translated_Funder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_business_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_business_function` (
  `BUSINESS_FUNCTION_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Unique Business function Id for every Business functoin in the application',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`BUSINESS_FUNCTION_ID`),
  KEY `fk_business_function_labelId_idx` (`LABEL_ID`),
  KEY `fk_business_function_createdBy_idx` (`CREATED_BY`),
  KEY `fk_business_function_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_business_function_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_business_function_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_business_function_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used for lisiting all the Business Functions that are available in the application';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_can_create_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_can_create_role` (
  `ROLE_ID` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CAN_CREATE_ROLE` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`CAN_CREATE_ROLE`),
  KEY `fk_us_can_create_role_us_role2_idx` (`CAN_CREATE_ROLE`),
  CONSTRAINT `fk_us_can_create_role_us_role1` FOREIGN KEY (`ROLE_ID`) REFERENCES `us_role` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_us_can_create_role_us_role2` FOREIGN KEY (`CAN_CREATE_ROLE`) REFERENCES `us_role` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_forgot_password_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_forgot_password_token` (
  `FORGOT_PASSWORD_TOKEN_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Token',
  `USER_ID` int unsigned NOT NULL COMMENT 'User Id that this token is generated for',
  `TOKEN` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Random Token string consisting of a-z, A-Z and 0-9 that is generated for the request',
  `TOKEN_GENERATION_DATE` datetime NOT NULL COMMENT 'Date that the request was generated',
  `TOKEN_TRIGGERED_DATE` datetime DEFAULT NULL COMMENT 'Date that the token was triggered by clicking on the link. If not triggered the value is null.',
  `TOKEN_COMPLETION_DATE` datetime DEFAULT NULL COMMENT 'Date that the token was completed by reseting the password. If not completed the value is null.',
  PRIMARY KEY (`FORGOT_PASSWORD_TOKEN_ID`),
  UNIQUE KEY `unq_token` (`TOKEN`) COMMENT 'Token must be unique',
  KEY `fk_us_forgot_password_token_us_user1_idx` (`USER_ID`),
  CONSTRAINT `fk_us_forgot_password_token_us_user1` FOREIGN KEY (`USER_ID`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6072 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Tokens generated for a forgot password request';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_role` (
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Unique Role Id for every Role in the application',
  `LABEL_ID` int unsigned NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`ROLE_ID`),
  KEY `fk_role_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_role_labelId_idx` (`LABEL_ID`),
  KEY `fk_role_createdBy_idx` (`CREATED_BY`),
  CONSTRAINT `fk_role_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used for lisiting all the Roles that users can use in the application';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_role_business_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_role_business_function` (
  `ROLE_BUSINESS_FUNCTION_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `BUSINESS_FUNCTION_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ROLE_BUSINESS_FUNCTION_ID`),
  UNIQUE KEY `uqRoleIdBusinessFunctionId` (`ROLE_ID`,`BUSINESS_FUNCTION_ID`) COMMENT 'Unique key for Role Id and Business Function Id combination',
  KEY `fk_role_business_function_roleId_idx` (`ROLE_ID`),
  KEY `fk_role_business_function_businessFunctionId_idx` (`BUSINESS_FUNCTION_ID`),
  KEY `fk_role_business_function_createdBy_idx` (`CREATED_BY`),
  KEY `fk_role_business_function_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_role_business_function_businessFunctionId` FOREIGN KEY (`BUSINESS_FUNCTION_ID`) REFERENCES `us_business_function` (`BUSINESS_FUNCTION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_business_function_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_business_function_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_business_function_roleId` FOREIGN KEY (`ROLE_ID`) REFERENCES `us_role` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8025 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Role and Business function map\nNote: Mapping table for Role and Business function';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_token_logout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_token_logout` (
  `TOKEN_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement Id for the Tokens',
  `TOKEN` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Field to store the Token',
  `LOGOUT_DATE` datetime NOT NULL COMMENT 'Date that the Logout happened',
  PRIMARY KEY (`TOKEN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=59615 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='A Store of the Tokens that have been logged out';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_user` (
  `USER_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique User Id for each User',
  `REALM_ID` int unsigned DEFAULT NULL COMMENT 'Realm Id that the User belongs to',
  `USERNAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'Username used to login',
  `PASSWORD` tinyblob NOT NULL COMMENT 'Encrypted password for the User\nOffline notes: Password cannot be updated when a User is offline',
  `EMAIL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ORG_AND_COUNTRY` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `LANGUAGE_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL DEFAULT '1' COMMENT 'True indicates the User is actvie and False indicates the User has been De-activated\nOffline notes: Even if a User has been De-activated Online, he will still be able to use the Offline version until he reaches his Last Sync by date',
  `FAILED_ATTEMPTS` tinyint unsigned NOT NULL DEFAULT '0' COMMENT 'Number of failed attempts that have been made to login to the application from this User Id\nOffline notes: Number will be incremented in the Offline mode as well and the only way to unlock it in Offline mode after it hits 3 attempts is to go online and then sync it with the Live Db where a Realm level Admin will be able to reset the password for you\n',
  `EXPIRES_ON` datetime NOT NULL COMMENT 'Date the Password for the User expires on. User will be forced to enter a new Password',
  `SYNC_EXPIRES_ON` datetime NOT NULL COMMENT 'Date after which the User will have to run a Sync to continue using the Offline version',
  `LAST_LOGIN_DATE` datetime DEFAULT NULL COMMENT 'Date the user last logged into the application. Null if no login has been done as yet.',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  `AGREEMENT_ACCEPTED` tinyint unsigned NOT NULL DEFAULT '0',
  `JIRA_ACCOUNT_ID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `DEFAULT_MODULE_ID` int unsigned NOT NULL DEFAULT '2',
  `DEFAULT_THEME_ID` int unsigned NOT NULL DEFAULT '1' COMMENT '1- Light Mode, 2- Dark Mode',
  `SHOW_DECIMALS` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `EMAIL_ID_UNIQUE` (`EMAIL_ID`),
  KEY `fk_user_createdBy_idx` (`CREATED_BY`),
  KEY `fk_user_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_user_languageId_idx` (`LANGUAGE_ID`),
  KEY `fk_user_realmId_idx` (`REALM_ID`),
  CONSTRAINT `fk_user_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_languageId` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `ap_language` (`LANGUAGE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2100 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used for lisiting all the Users that are going to access the system';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_user_acl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_user_acl` (
  `USER_ACL_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique User Access Control List Id',
  `USER_ID` int unsigned NOT NULL COMMENT 'Foriegn key for the User Id',
  `ROLE_ID` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `REALM_COUNTRY_ID` int unsigned DEFAULT NULL COMMENT 'Foriegn key for the Country. If this is null it indicates the user has access to all the Countries',
  `HEALTH_AREA_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Health Area. If this is null it indicates the user has access to all the Health Areas',
  `ORGANISATION_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Organisation. If this is null it indicates the user has access to all the Organisations.',
  `PROGRAM_ID` int unsigned DEFAULT NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`USER_ACL_ID`),
  KEY `fk_user_acl_userId_idx` (`USER_ID`),
  KEY `fk_user_acl_createdBy_idx` (`CREATED_BY`),
  KEY `fk_user_acl_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  KEY `fk_user_acl_realmCountryId_idx` (`REALM_COUNTRY_ID`),
  KEY `fk_user_acl_healthAreaId_idx` (`HEALTH_AREA_ID`),
  KEY `fk_user_acl_organisationId_idx` (`ORGANISATION_ID`),
  CONSTRAINT `fk_user_acl_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_acl_healthAreaId` FOREIGN KEY (`HEALTH_AREA_ID`) REFERENCES `rm_health_area` (`HEALTH_AREA_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_acl_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_acl_organisationId` FOREIGN KEY (`ORGANISATION_ID`) REFERENCES `rm_organisation` (`ORGANISATION_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_acl_realmCountryId` FOREIGN KEY (`REALM_COUNTRY_ID`) REFERENCES `rm_realm_country` (`REALM_COUNTRY_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_acl_userId` FOREIGN KEY (`USER_ID`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4234 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table used to store the Access control lists for the application\nNote: Multiple rows for each user. Each row indicates what he has access to.';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `us_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_user_role` (
  `USER_ROLE_ID` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique User-Role mapping id',
  `USER_ID` int unsigned NOT NULL COMMENT 'Foriegn key for the User Id',
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'Foriegn key for the Role Id',
  `CREATED_BY` int unsigned NOT NULL COMMENT 'Created by',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` int unsigned NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`USER_ROLE_ID`),
  UNIQUE KEY `unq_userRole` (`USER_ID`,`ROLE_ID`),
  KEY `fk_user_role_userId_idx` (`USER_ID`),
  KEY `fk_user_role_roleId_idx` (`ROLE_ID`),
  KEY `fk_user_role_createdBy_idx` (`CREATED_BY`),
  KEY `fk_user_role_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_user_role_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_lastModifeidBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_roleId` FOREIGN KEY (`ROLE_ID`) REFERENCES `us_role` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_userId` FOREIGN KEY (`USER_ID`) REFERENCES `us_user` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8274 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='User Role mapping table\nNote: Mapping table for User and Roles. While we expect one User will have only one Role it is possible that the User can have multiple Roles.';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `vw_all_program`;
/*!50001 DROP VIEW IF EXISTS `vw_all_program`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_all_program` AS SELECT 
 1 AS `PROGRAM_ID`,
 1 AS `PROGRAM_CODE`,
 1 AS `REALM_COUNTRY_ID`,
 1 AS `HEALTH_AREA_ID`,
 1 AS `ORGANISATION_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `PROGRAM_TYPE_ID`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `CURRENT_VERSION_ID`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_budget`;
/*!50001 DROP VIEW IF EXISTS `vw_budget`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_budget` AS SELECT 
 1 AS `BUDGET_ID`,
 1 AS `BUDGET_CODE`,
 1 AS `REALM_ID`,
 1 AS `FUNDING_SOURCE_ID`,
 1 AS `LABEL_ID`,
 1 AS `CURRENCY_ID`,
 1 AS `BUDGET_AMT`,
 1 AS `CONVERSION_RATE_TO_USD`,
 1 AS `START_DATE`,
 1 AS `STOP_DATE`,
 1 AS `NOTES`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_country`;
/*!50001 DROP VIEW IF EXISTS `vw_country`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_country` AS SELECT 
 1 AS `COUNTRY_ID`,
 1 AS `LABEL_ID`,
 1 AS `COUNTRY_CODE`,
 1 AS `COUNTRY_CODE2`,
 1 AS `CURRENCY_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_currency`;
/*!50001 DROP VIEW IF EXISTS `vw_currency`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_currency` AS SELECT 
 1 AS `CURRENCY_ID`,
 1 AS `CURRENCY_CODE`,
 1 AS `LABEL_ID`,
 1 AS `CONVERSION_RATE_TO_USD`,
 1 AS `IS_SYNC`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_data_source`;
/*!50001 DROP VIEW IF EXISTS `vw_data_source`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_data_source` AS SELECT 
 1 AS `DATA_SOURCE_ID`,
 1 AS `REALM_ID`,
 1 AS `PROGRAM_ID`,
 1 AS `DATA_SOURCE_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_data_source_type`;
/*!50001 DROP VIEW IF EXISTS `vw_data_source_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_data_source_type` AS SELECT 
 1 AS `DATA_SOURCE_TYPE_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_dataset`;
/*!50001 DROP VIEW IF EXISTS `vw_dataset`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_dataset` AS SELECT 
 1 AS `PROGRAM_ID`,
 1 AS `PROGRAM_CODE`,
 1 AS `REALM_COUNTRY_ID`,
 1 AS `HEALTH_AREA_ID`,
 1 AS `ORGANISATION_ID`,
 1 AS `LABEL_ID`,
 1 AS `PROGRAM_MANAGER_USER_ID`,
 1 AS `PROGRAM_NOTES`,
 1 AS `AIR_FREIGHT_PERC`,
 1 AS `SEA_FREIGHT_PERC`,
 1 AS `ROAD_FREIGHT_PERC`,
 1 AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
 1 AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
 1 AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
 1 AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
 1 AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
 1 AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
 1 AS `CURRENT_VERSION_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `PROGRAM_TYPE_ID`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_dimension`;
/*!50001 DROP VIEW IF EXISTS `vw_dimension`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_dimension` AS SELECT 
 1 AS `DIMENSION_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_equivalency_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_equivalency_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_equivalency_unit` AS SELECT 
 1 AS `EQUIVALENCY_UNIT_ID`,
 1 AS `REALM_ID`,
 1 AS `PROGRAM_ID`,
 1 AS `HEALTH_AREA_ID`,
 1 AS `LABEL_ID`,
 1 AS `NOTES`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_extrapolation_method`;
/*!50001 DROP VIEW IF EXISTS `vw_extrapolation_method`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_extrapolation_method` AS SELECT 
 1 AS `EXTRAPOLATION_METHOD_ID`,
 1 AS `SORT_ORDER`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_forecast_method`;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_method`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_forecast_method` AS SELECT 
 1 AS `FORECAST_METHOD_ID`,
 1 AS `REALM_ID`,
 1 AS `FORECAST_METHOD_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_forecast_method_type`;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_method_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_forecast_method_type` AS SELECT 
 1 AS `FORECAST_METHOD_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_forecast_tree`;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_tree`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_forecast_tree` AS SELECT 
 1 AS `TREE_ID`,
 1 AS `TREE_ANCHOR_ID`,
 1 AS `PROGRAM_ID`,
 1 AS `VERSION_ID`,
 1 AS `LABEL_ID`,
 1 AS `FORECAST_METHOD_ID`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `NOTES`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_forecast_tree_node`;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_tree_node`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_forecast_tree_node` AS SELECT 
 1 AS `NODE_ID`,
 1 AS `TREE_ID`,
 1 AS `COLLAPSED`,
 1 AS `DOWNWARD_AGGREGATION_ALLOWED`,
 1 AS `PARENT_NODE_ID`,
 1 AS `SORT_ORDER`,
 1 AS `LEVEL_NO`,
 1 AS `NODE_TYPE_ID`,
 1 AS `UNIT_ID`,
 1 AS `LABEL_ID`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_forecasting_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_forecasting_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_forecasting_unit` AS SELECT 
 1 AS `FORECASTING_UNIT_ID`,
 1 AS `REALM_ID`,
 1 AS `PRODUCT_CATEGORY_ID`,
 1 AS `TRACER_CATEGORY_ID`,
 1 AS `GENERIC_LABEL_ID`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_funding_source`;
/*!50001 DROP VIEW IF EXISTS `vw_funding_source`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_funding_source` AS SELECT 
 1 AS `FUNDING_SOURCE_ID`,
 1 AS `FUNDING_SOURCE_CODE`,
 1 AS `FUNDING_SOURCE_TYPE_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `ALLOWED_IN_BUDGET`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_funding_source_type`;
/*!50001 DROP VIEW IF EXISTS `vw_funding_source_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_funding_source_type` AS SELECT 
 1 AS `FUNDING_SOURCE_TYPE_ID`,
 1 AS `REALM_ID`,
 1 AS `FUNDING_SOURCE_TYPE_CODE`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_health_area`;
/*!50001 DROP VIEW IF EXISTS `vw_health_area`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_health_area` AS SELECT 
 1 AS `HEALTH_AREA_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `HEALTH_AREA_CODE`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_modeling_type`;
/*!50001 DROP VIEW IF EXISTS `vw_modeling_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_modeling_type` AS SELECT 
 1 AS `MODELING_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_node_type`;
/*!50001 DROP VIEW IF EXISTS `vw_node_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_node_type` AS SELECT 
 1 AS `NODE_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `MODELING_ALLOWED`,
 1 AS `EXTRAPOLATION_ALLOWED`,
 1 AS `TREE_TEMPLATE_ALLOWED`,
 1 AS `FORECAST_TREE_ALLOWED`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_notification_type`;
/*!50001 DROP VIEW IF EXISTS `vw_notification_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_notification_type` AS SELECT 
 1 AS `NOTIFICATION_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_DATE`,
 1 AS `CREATED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_organisation`;
/*!50001 DROP VIEW IF EXISTS `vw_organisation`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_organisation` AS SELECT 
 1 AS `ORGANISATION_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `ORGANISATION_CODE`,
 1 AS `ORGANISATION_TYPE_ID`,
 1 AS `TYPE_LABEL_ID`,
 1 AS `TYPE_LABEL_EN`,
 1 AS `TYPE_LABEL_FR`,
 1 AS `TYPE_LABEL_SP`,
 1 AS `TYPE_LABEL_PR`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_organisation_type`;
/*!50001 DROP VIEW IF EXISTS `vw_organisation_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_organisation_type` AS SELECT 
 1 AS `ORGANISATION_TYPE_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_planning_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_planning_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_planning_unit` AS SELECT 
 1 AS `PLANNING_UNIT_ID`,
 1 AS `FORECASTING_UNIT_ID`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_ID`,
 1 AS `MULTIPLIER`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_problem`;
/*!50001 DROP VIEW IF EXISTS `vw_problem`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_problem` AS SELECT 
 1 AS `PROBLEM_ID`,
 1 AS `PROBLEM_CATEGORY_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTION_URL`,
 1 AS `ACTION_LABEL_ID`,
 1 AS `ACTUAL_CONSUMPTION_TRIGGER`,
 1 AS `FORECASTED_CONSUMPTION_TRIGGER`,
 1 AS `INVENTORY_TRIGGER`,
 1 AS `ADJUSTMENT_TRIGGER`,
 1 AS `SHIPMENT_TRIGGER`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `ACTION_LABEL_EN`,
 1 AS `ACTION_LABEL_FR`,
 1 AS `ACTION_LABEL_SP`,
 1 AS `ACTION_LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_problem_category`;
/*!50001 DROP VIEW IF EXISTS `vw_problem_category`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_problem_category` AS SELECT 
 1 AS `PROBLEM_CATEGORY_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_problem_criticality`;
/*!50001 DROP VIEW IF EXISTS `vw_problem_criticality`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_problem_criticality` AS SELECT 
 1 AS `CRITICALITY_ID`,
 1 AS `LABEL_ID`,
 1 AS `COLOR_HTML_CODE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_problem_status`;
/*!50001 DROP VIEW IF EXISTS `vw_problem_status`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_problem_status` AS SELECT 
 1 AS `PROBLEM_STATUS_ID`,
 1 AS `USER_MANAGED`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_problem_type`;
/*!50001 DROP VIEW IF EXISTS `vw_problem_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_problem_type` AS SELECT 
 1 AS `PROBLEM_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_procurement_agent`;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_agent`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_procurement_agent` AS SELECT 
 1 AS `PROCUREMENT_AGENT_ID`,
 1 AS `REALM_ID`,
 1 AS `PROCUREMENT_AGENT_TYPE_ID`,
 1 AS `PROCUREMENT_AGENT_CODE`,
 1 AS `COLOR_HTML_CODE`,
 1 AS `COLOR_HTML_DARK_CODE`,
 1 AS `LABEL_ID`,
 1 AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
 1 AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_procurement_agent_type`;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_agent_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_procurement_agent_type` AS SELECT 
 1 AS `PROCUREMENT_AGENT_TYPE_ID`,
 1 AS `REALM_ID`,
 1 AS `PROCUREMENT_AGENT_TYPE_CODE`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_procurement_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_procurement_unit` AS SELECT 
 1 AS `PROCUREMENT_UNIT_ID`,
 1 AS `PLANNING_UNIT_ID`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_ID`,
 1 AS `MULTIPLIER`,
 1 AS `SUPPLIER_ID`,
 1 AS `WIDTH_QTY`,
 1 AS `HEIGHT_QTY`,
 1 AS `LENGTH_UNIT_ID`,
 1 AS `LENGTH_QTY`,
 1 AS `WEIGHT_QTY`,
 1 AS `WEIGHT_UNIT_ID`,
 1 AS `VOLUME_QTY`,
 1 AS `VOLUME_UNIT_ID`,
 1 AS `UNITS_PER_CASE`,
 1 AS `UNITS_PER_PALLET_EURO1`,
 1 AS `UNITS_PER_PALLET_EURO2`,
 1 AS `UNITS_PER_CONTAINER`,
 1 AS `LABELING`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_product_category`;
/*!50001 DROP VIEW IF EXISTS `vw_product_category`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_product_category` AS SELECT 
 1 AS `PRODUCT_CATEGORY_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `PARENT_PRODUCT_CATEGORY_ID`,
 1 AS `SORT_ORDER`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_program`;
/*!50001 DROP VIEW IF EXISTS `vw_program`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_program` AS SELECT 
 1 AS `PROGRAM_ID`,
 1 AS `PROGRAM_CODE`,
 1 AS `REALM_COUNTRY_ID`,
 1 AS `HEALTH_AREA_ID`,
 1 AS `ORGANISATION_ID`,
 1 AS `LABEL_ID`,
 1 AS `PROGRAM_MANAGER_USER_ID`,
 1 AS `PROGRAM_NOTES`,
 1 AS `AIR_FREIGHT_PERC`,
 1 AS `SEA_FREIGHT_PERC`,
 1 AS `ROAD_FREIGHT_PERC`,
 1 AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
 1 AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
 1 AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
 1 AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
 1 AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
 1 AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
 1 AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
 1 AS `CURRENT_VERSION_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `PROGRAM_TYPE_ID`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_realm`;
/*!50001 DROP VIEW IF EXISTS `vw_realm`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_realm` AS SELECT 
 1 AS `REALM_ID`,
 1 AS `REALM_CODE`,
 1 AS `LABEL_ID`,
 1 AS `DEFAULT_REALM`,
 1 AS `MIN_MOS_MIN_GAURDRAIL`,
 1 AS `MIN_MOS_MAX_GAURDRAIL`,
 1 AS `MAX_MOS_MAX_GAURDRAIL`,
 1 AS `MIN_QPL_TOLERANCE`,
 1 AS `MIN_QPL_TOLERANCE_CUT_OFF`,
 1 AS `MAX_QPL_TOLERANCE`,
 1 AS `ACTUAL_CONSUMPTION_MONTHS_IN_PAST`,
 1 AS `FORECAST_CONSUMPTION_MONTH_IN_PAST`,
 1 AS `INVENTORY_MONTHS_IN_PAST`,
 1 AS `MIN_COUNT_FOR_MODE`,
 1 AS `MIN_PERC_FOR_MODE`,
 1 AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
 1 AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
 1 AS `NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD`,
 1 AS `NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_realm_country_planning_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_realm_country_planning_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_realm_country_planning_unit` AS SELECT 
 1 AS `REALM_COUNTRY_PLANNING_UNIT_ID`,
 1 AS `PLANNING_UNIT_ID`,
 1 AS `REALM_COUNTRY_ID`,
 1 AS `LABEL_ID`,
 1 AS `SKU_CODE`,
 1 AS `UNIT_ID`,
 1 AS `CONVERSION_METHOD`,
 1 AS `CONVERSION_NUMBER`,
 1 AS `GTIN`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_region`;
/*!50001 DROP VIEW IF EXISTS `vw_region`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_region` AS SELECT 
 1 AS `REGION_ID`,
 1 AS `REALM_COUNTRY_ID`,
 1 AS `LABEL_ID`,
 1 AS `CAPACITY_CBM`,
 1 AS `GLN`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_scenario`;
/*!50001 DROP VIEW IF EXISTS `vw_scenario`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_scenario` AS SELECT 
 1 AS `SCENARIO_ID`,
 1 AS `TREE_ID`,
 1 AS `LABEL_ID`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `NOTES`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_shipment_status`;
/*!50001 DROP VIEW IF EXISTS `vw_shipment_status`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_shipment_status` AS SELECT 
 1 AS `SHIPMENT_STATUS_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_supplier`;
/*!50001 DROP VIEW IF EXISTS `vw_supplier`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_supplier` AS SELECT 
 1 AS `SUPPLIER_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_tracer_category`;
/*!50001 DROP VIEW IF EXISTS `vw_tracer_category`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_tracer_category` AS SELECT 
 1 AS `TRACER_CATEGORY_ID`,
 1 AS `REALM_ID`,
 1 AS `HEALTH_AREA_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_tree_level`;
/*!50001 DROP VIEW IF EXISTS `vw_tree_level`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_tree_level` AS SELECT 
 1 AS `TREE_LEVEL_ID`,
 1 AS `TREE_ID`,
 1 AS `LEVEL_NO`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_tree_template`;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_tree_template` AS SELECT 
 1 AS `TREE_TEMPLATE_ID`,
 1 AS `REALM_ID`,
 1 AS `LABEL_ID`,
 1 AS `FORECAST_METHOD_ID`,
 1 AS `MONTHS_IN_PAST`,
 1 AS `MONTHS_IN_FUTURE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`,
 1 AS `NOTES`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_tree_template_level`;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template_level`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_tree_template_level` AS SELECT 
 1 AS `TREE_TEMPLATE_LEVEL_ID`,
 1 AS `TREE_TEMPLATE_ID`,
 1 AS `LEVEL_NO`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_tree_template_node`;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template_node`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_tree_template_node` AS SELECT 
 1 AS `NODE_ID`,
 1 AS `TREE_TEMPLATE_ID`,
 1 AS `COLLAPSED`,
 1 AS `DOWNWARD_AGGREGATION_ALLOWED`,
 1 AS `PARENT_NODE_ID`,
 1 AS `SORT_ORDER`,
 1 AS `LEVEL_NO`,
 1 AS `NODE_TYPE_ID`,
 1 AS `UNIT_ID`,
 1 AS `LABEL_ID`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `ACTIVE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_unit`;
/*!50001 DROP VIEW IF EXISTS `vw_unit`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_unit` AS SELECT 
 1 AS `UNIT_ID`,
 1 AS `DIMENSION_ID`,
 1 AS `LABEL_ID`,
 1 AS `UNIT_CODE`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_usage_period`;
/*!50001 DROP VIEW IF EXISTS `vw_usage_period`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_usage_period` AS SELECT 
 1 AS `USAGE_PERIOD_ID`,
 1 AS `LABEL_ID`,
 1 AS `CONVERT_TO_MONTH`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_usage_template`;
/*!50001 DROP VIEW IF EXISTS `vw_usage_template`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_usage_template` AS SELECT 
 1 AS `USAGE_TEMPLATE_ID`,
 1 AS `REALM_ID`,
 1 AS `PROGRAM_ID`,
 1 AS `UNIT_ID`,
 1 AS `FORECASTING_UNIT_ID`,
 1 AS `LAG_IN_MONTHS`,
 1 AS `USAGE_TYPE_ID`,
 1 AS `NO_OF_PATIENTS`,
 1 AS `NO_OF_FORECASTING_UNITS`,
 1 AS `ONE_TIME_USAGE`,
 1 AS `USAGE_FREQUENCY_USAGE_PERIOD_ID`,
 1 AS `USAGE_FREQUENCY_COUNT`,
 1 AS `REPEAT_USAGE_PERIOD_ID`,
 1 AS `REPEAT_COUNT`,
 1 AS `NOTES`,
 1 AS `ACTIVE`,
 1 AS `CREATED_DATE`,
 1 AS `CREATED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_usage_type`;
/*!50001 DROP VIEW IF EXISTS `vw_usage_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_usage_type` AS SELECT 
 1 AS `USAGE_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `ACTIVE`,
 1 AS `CREATED_BY`,
 1 AS `CREATED_DATE`,
 1 AS `LAST_MODIFIED_BY`,
 1 AS `LAST_MODIFIED_DATE`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_version_status`;
/*!50001 DROP VIEW IF EXISTS `vw_version_status`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_version_status` AS SELECT 
 1 AS `VERSION_STATUS_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
DROP TABLE IF EXISTS `vw_version_type`;
/*!50001 DROP VIEW IF EXISTS `vw_version_type`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_version_type` AS SELECT 
 1 AS `VERSION_TYPE_ID`,
 1 AS `LABEL_ID`,
 1 AS `LABEL_EN`,
 1 AS `LABEL_FR`,
 1 AS `LABEL_SP`,
 1 AS `LABEL_PR`*/;
SET character_set_client = @saved_cs_client;
/*!50001 DROP VIEW IF EXISTS `vw_all_program`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_all_program` AS select `p`.`PROGRAM_ID` AS `PROGRAM_ID`,`p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,`p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,group_concat(`pha`.`HEALTH_AREA_ID` separator ',') AS `HEALTH_AREA_ID`,`p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,`p`.`LABEL_ID` AS `LABEL_ID`,`pl`.`LABEL_EN` AS `LABEL_EN`,`pl`.`LABEL_FR` AS `LABEL_FR`,`pl`.`LABEL_SP` AS `LABEL_SP`,`pl`.`LABEL_PR` AS `LABEL_PR`,`p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`,`p`.`CREATED_BY` AS `CREATED_BY`,`p`.`CREATED_DATE` AS `CREATED_DATE`,`p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`p`.`ACTIVE` AS `ACTIVE`,`p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID` from ((`rm_program` `p` left join `rm_program_health_area` `pha` on((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`))) left join `ap_label` `pl` on((`p`.`LABEL_ID` = `pl`.`LABEL_ID`))) group by `p`.`PROGRAM_ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_budget`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_budget` AS select `b`.`BUDGET_ID` AS `BUDGET_ID`,`b`.`BUDGET_CODE` AS `BUDGET_CODE`,`b`.`REALM_ID` AS `REALM_ID`,`b`.`FUNDING_SOURCE_ID` AS `FUNDING_SOURCE_ID`,`b`.`LABEL_ID` AS `LABEL_ID`,`b`.`CURRENCY_ID` AS `CURRENCY_ID`,`b`.`BUDGET_AMT` AS `BUDGET_AMT`,`b`.`CONVERSION_RATE_TO_USD` AS `CONVERSION_RATE_TO_USD`,`b`.`START_DATE` AS `START_DATE`,`b`.`STOP_DATE` AS `STOP_DATE`,`b`.`NOTES` AS `NOTES`,`b`.`ACTIVE` AS `ACTIVE`,`b`.`CREATED_BY` AS `CREATED_BY`,`b`.`CREATED_DATE` AS `CREATED_DATE`,`b`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`b`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`bl`.`LABEL_EN` AS `LABEL_EN`,`bl`.`LABEL_FR` AS `LABEL_FR`,`bl`.`LABEL_SP` AS `LABEL_SP`,`bl`.`LABEL_PR` AS `LABEL_PR` from (`rm_budget` `b` left join `ap_label` `bl` on((`b`.`LABEL_ID` = `bl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_country`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_country` AS select `c`.`COUNTRY_ID` AS `COUNTRY_ID`,`c`.`LABEL_ID` AS `LABEL_ID`,`c`.`COUNTRY_CODE` AS `COUNTRY_CODE`,`c`.`COUNTRY_CODE2` AS `COUNTRY_CODE2`,`c`.`CURRENCY_ID` AS `CURRENCY_ID`,`c`.`ACTIVE` AS `ACTIVE`,`c`.`CREATED_BY` AS `CREATED_BY`,`c`.`CREATED_DATE` AS `CREATED_DATE`,`c`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`c`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`cl`.`LABEL_EN` AS `LABEL_EN`,`cl`.`LABEL_FR` AS `LABEL_FR`,`cl`.`LABEL_SP` AS `LABEL_SP`,`cl`.`LABEL_PR` AS `LABEL_PR` from (`ap_country` `c` left join `ap_label` `cl` on((`c`.`LABEL_ID` = `cl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_currency`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_currency` AS select `c`.`CURRENCY_ID` AS `CURRENCY_ID`,`c`.`CURRENCY_CODE` AS `CURRENCY_CODE`,`c`.`LABEL_ID` AS `LABEL_ID`,`c`.`CONVERSION_RATE_TO_USD` AS `CONVERSION_RATE_TO_USD`,`c`.`IS_SYNC` AS `IS_SYNC`,`c`.`CREATED_BY` AS `CREATED_BY`,`c`.`CREATED_DATE` AS `CREATED_DATE`,`c`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`c`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`c`.`ACTIVE` AS `ACTIVE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_currency` `c` left join `ap_label` `l` on((`c`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_data_source`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_data_source` AS select `ds`.`DATA_SOURCE_ID` AS `DATA_SOURCE_ID`,`ds`.`REALM_ID` AS `REALM_ID`,`ds`.`PROGRAM_ID` AS `PROGRAM_ID`,`ds`.`DATA_SOURCE_TYPE_ID` AS `DATA_SOURCE_TYPE_ID`,`ds`.`LABEL_ID` AS `LABEL_ID`,`ds`.`ACTIVE` AS `ACTIVE`,`ds`.`CREATED_BY` AS `CREATED_BY`,`ds`.`CREATED_DATE` AS `CREATED_DATE`,`ds`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ds`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`dsl`.`LABEL_EN` AS `LABEL_EN`,`dsl`.`LABEL_FR` AS `LABEL_FR`,`dsl`.`LABEL_SP` AS `LABEL_SP`,`dsl`.`LABEL_PR` AS `LABEL_PR` from (`rm_data_source` `ds` left join `ap_label` `dsl` on((`ds`.`LABEL_ID` = `dsl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_data_source_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_data_source_type` AS select `dst`.`DATA_SOURCE_TYPE_ID` AS `DATA_SOURCE_TYPE_ID`,`dst`.`REALM_ID` AS `REALM_ID`,`dst`.`LABEL_ID` AS `LABEL_ID`,`dst`.`ACTIVE` AS `ACTIVE`,`dst`.`CREATED_BY` AS `CREATED_BY`,`dst`.`CREATED_DATE` AS `CREATED_DATE`,`dst`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`dst`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_data_source_type` `dst` left join `ap_label` `l` on((`dst`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_dataset`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_dataset` AS select `p`.`PROGRAM_ID` AS `PROGRAM_ID`,`p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,`p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,group_concat(`pha`.`HEALTH_AREA_ID` separator ',') AS `HEALTH_AREA_ID`,`p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,`p`.`LABEL_ID` AS `LABEL_ID`,`p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,`p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,`p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,`p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,`p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,`p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,`p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,`p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,`p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,`p`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,`p`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,`p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,`p`.`ACTIVE` AS `ACTIVE`,`p`.`CREATED_BY` AS `CREATED_BY`,`p`.`CREATED_DATE` AS `CREATED_DATE`,`p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pl`.`LABEL_EN` AS `LABEL_EN`,`pl`.`LABEL_FR` AS `LABEL_FR`,`pl`.`LABEL_SP` AS `LABEL_SP`,`pl`.`LABEL_PR` AS `LABEL_PR`,`p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID` from ((`rm_program` `p` left join `rm_program_health_area` `pha` on((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`))) left join `ap_label` `pl` on((`p`.`LABEL_ID` = `pl`.`LABEL_ID`))) where (`p`.`PROGRAM_TYPE_ID` = 2) group by `p`.`PROGRAM_ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_dimension`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_dimension` AS select `d`.`DIMENSION_ID` AS `DIMENSION_ID`,`d`.`LABEL_ID` AS `LABEL_ID`,`d`.`ACTIVE` AS `ACTIVE`,`d`.`CREATED_BY` AS `CREATED_BY`,`d`.`CREATED_DATE` AS `CREATED_DATE`,`d`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`d`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_dimension` `d` left join `ap_label` `l` on((`d`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_equivalency_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_equivalency_unit` AS select `ut`.`EQUIVALENCY_UNIT_ID` AS `EQUIVALENCY_UNIT_ID`,`ut`.`REALM_ID` AS `REALM_ID`,`ut`.`PROGRAM_ID` AS `PROGRAM_ID`,group_concat(`euha`.`HEALTH_AREA_ID` separator ',') AS `HEALTH_AREA_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`NOTES` AS `NOTES`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from ((`rm_equivalency_unit` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) left join `rm_equivalency_unit_health_area` `euha` on((`ut`.`EQUIVALENCY_UNIT_ID` = `euha`.`EQUIVALENCY_UNIT_ID`))) group by `ut`.`EQUIVALENCY_UNIT_ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_extrapolation_method`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_extrapolation_method` AS select `em`.`EXTRAPOLATION_METHOD_ID` AS `EXTRAPOLATION_METHOD_ID`,`em`.`SORT_ORDER` AS `SORT_ORDER`,`em`.`LABEL_ID` AS `LABEL_ID`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR`,`em`.`ACTIVE` AS `ACTIVE`,`em`.`CREATED_BY` AS `CREATED_BY`,`em`.`CREATED_DATE` AS `CREATED_DATE`,`em`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`em`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE` from (`ap_extrapolation_method` `em` left join `ap_label` `l` on((`em`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_method`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_forecast_method` AS select `ut`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,`ut`.`REALM_ID` AS `REALM_ID`,`ut`.`FORECAST_METHOD_TYPE_ID` AS `FORECAST_METHOD_TYPE_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_forecast_method` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_method_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_forecast_method_type` AS select `ut`.`FORECAST_METHOD_TYPE_ID` AS `FORECAST_METHOD_TYPE_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_forecast_method_type` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_tree`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_forecast_tree` AS select `ft`.`TREE_ID` AS `TREE_ID`,`ft`.`TREE_ANCHOR_ID` AS `TREE_ANCHOR_ID`,`ft`.`PROGRAM_ID` AS `PROGRAM_ID`,`ft`.`VERSION_ID` AS `VERSION_ID`,`ft`.`LABEL_ID` AS `LABEL_ID`,`ft`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,`ft`.`CREATED_BY` AS `CREATED_BY`,`ft`.`CREATED_DATE` AS `CREATED_DATE`,`ft`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ft`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ft`.`ACTIVE` AS `ACTIVE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR`,`ft`.`NOTES` AS `NOTES` from (`rm_forecast_tree` `ft` left join `ap_label` `l` on((`ft`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_forecast_tree_node`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_forecast_tree_node` AS select `tn`.`NODE_ID` AS `NODE_ID`,`tn`.`TREE_ID` AS `TREE_ID`,`tn`.`COLLAPSED` AS `COLLAPSED`,`tn`.`DOWNWARD_AGGREGATION_ALLOWED` AS `DOWNWARD_AGGREGATION_ALLOWED`,`tn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,`tn`.`SORT_ORDER` AS `SORT_ORDER`,`tn`.`LEVEL_NO` AS `LEVEL_NO`,`tn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,`tn`.`UNIT_ID` AS `UNIT_ID`,`tn`.`LABEL_ID` AS `LABEL_ID`,`tn`.`CREATED_BY` AS `CREATED_BY`,`tn`.`CREATED_DATE` AS `CREATED_DATE`,`tn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`tn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`tn`.`ACTIVE` AS `ACTIVE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_forecast_tree_node` `tn` left join `ap_label` `l` on((`tn`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_forecasting_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_forecasting_unit` AS select `fu`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,`fu`.`REALM_ID` AS `REALM_ID`,`fu`.`PRODUCT_CATEGORY_ID` AS `PRODUCT_CATEGORY_ID`,`fu`.`TRACER_CATEGORY_ID` AS `TRACER_CATEGORY_ID`,`fu`.`GENERIC_LABEL_ID` AS `GENERIC_LABEL_ID`,`fu`.`LABEL_ID` AS `LABEL_ID`,`fu`.`UNIT_ID` AS `UNIT_ID`,`fu`.`ACTIVE` AS `ACTIVE`,`fu`.`CREATED_BY` AS `CREATED_BY`,`fu`.`CREATED_DATE` AS `CREATED_DATE`,`fu`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`fu`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ful`.`LABEL_EN` AS `LABEL_EN`,`ful`.`LABEL_FR` AS `LABEL_FR`,`ful`.`LABEL_SP` AS `LABEL_SP`,`ful`.`LABEL_PR` AS `LABEL_PR` from (`rm_forecasting_unit` `fu` left join `ap_label` `ful` on((`fu`.`LABEL_ID` = `ful`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_funding_source`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_funding_source` AS select `fs`.`FUNDING_SOURCE_ID` AS `FUNDING_SOURCE_ID`,`fs`.`FUNDING_SOURCE_CODE` AS `FUNDING_SOURCE_CODE`,`fs`.`FUNDING_SOURCE_TYPE_ID` AS `FUNDING_SOURCE_TYPE_ID`,`fs`.`REALM_ID` AS `REALM_ID`,`fs`.`LABEL_ID` AS `LABEL_ID`,`fs`.`ACTIVE` AS `ACTIVE`,`fs`.`ALLOWED_IN_BUDGET` AS `ALLOWED_IN_BUDGET`,`fs`.`CREATED_BY` AS `CREATED_BY`,`fs`.`CREATED_DATE` AS `CREATED_DATE`,`fs`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`fs`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`fsl`.`LABEL_EN` AS `LABEL_EN`,`fsl`.`LABEL_FR` AS `LABEL_FR`,`fsl`.`LABEL_SP` AS `LABEL_SP`,`fsl`.`LABEL_PR` AS `LABEL_PR` from (`rm_funding_source` `fs` left join `ap_label` `fsl` on((`fs`.`LABEL_ID` = `fsl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_funding_source_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_funding_source_type` AS select `pat`.`FUNDING_SOURCE_TYPE_ID` AS `FUNDING_SOURCE_TYPE_ID`,`pat`.`REALM_ID` AS `REALM_ID`,`pat`.`FUNDING_SOURCE_TYPE_CODE` AS `FUNDING_SOURCE_TYPE_CODE`,`pat`.`LABEL_ID` AS `LABEL_ID`,`pat`.`ACTIVE` AS `ACTIVE`,`pat`.`CREATED_BY` AS `CREATED_BY`,`pat`.`CREATED_DATE` AS `CREATED_DATE`,`pat`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pat`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_funding_source_type` `pat` left join `ap_label` `l` on((`pat`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_health_area`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_health_area` AS select `ha`.`HEALTH_AREA_ID` AS `HEALTH_AREA_ID`,`ha`.`REALM_ID` AS `REALM_ID`,`ha`.`LABEL_ID` AS `LABEL_ID`,`ha`.`HEALTH_AREA_CODE` AS `HEALTH_AREA_CODE`,`ha`.`ACTIVE` AS `ACTIVE`,`ha`.`CREATED_BY` AS `CREATED_BY`,`ha`.`CREATED_DATE` AS `CREATED_DATE`,`ha`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ha`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`hal`.`LABEL_EN` AS `LABEL_EN`,`hal`.`LABEL_FR` AS `LABEL_FR`,`hal`.`LABEL_SP` AS `LABEL_SP`,`hal`.`LABEL_PR` AS `LABEL_PR` from (`rm_health_area` `ha` left join `ap_label` `hal` on((`ha`.`LABEL_ID` = `hal`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_modeling_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_modeling_type` AS select `ut`.`MODELING_TYPE_ID` AS `MODELING_TYPE_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_modeling_type` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_node_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_node_type` AS select `ut`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`MODELING_ALLOWED` AS `MODELING_ALLOWED`,`ut`.`EXTRAPOLATION_ALLOWED` AS `EXTRAPOLATION_ALLOWED`,`ut`.`TREE_TEMPLATE_ALLOWED` AS `TREE_TEMPLATE_ALLOWED`,`ut`.`FORECAST_TREE_ALLOWED` AS `FORECAST_TREE_ALLOWED`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_node_type` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_notification_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_notification_type` AS select `nt`.`NOTIFICATION_TYPE_ID` AS `NOTIFICATION_TYPE_ID`,`nt`.`LABEL_ID` AS `LABEL_ID`,`nt`.`ACTIVE` AS `ACTIVE`,`nt`.`CREATED_DATE` AS `CREATED_DATE`,`nt`.`CREATED_BY` AS `CREATED_BY`,`nt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`nt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_notification_type` `nt` left join `ap_label` `l` on((`nt`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_organisation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_organisation` AS select `o`.`ORGANISATION_ID` AS `ORGANISATION_ID`,`o`.`REALM_ID` AS `REALM_ID`,`o`.`LABEL_ID` AS `LABEL_ID`,`o`.`ORGANISATION_CODE` AS `ORGANISATION_CODE`,`o`.`ORGANISATION_TYPE_ID` AS `ORGANISATION_TYPE_ID`,`ot`.`LABEL_ID` AS `TYPE_LABEL_ID`,`ot`.`LABEL_EN` AS `TYPE_LABEL_EN`,`ot`.`LABEL_FR` AS `TYPE_LABEL_FR`,`ot`.`LABEL_SP` AS `TYPE_LABEL_SP`,`ot`.`LABEL_PR` AS `TYPE_LABEL_PR`,`o`.`ACTIVE` AS `ACTIVE`,`o`.`CREATED_BY` AS `CREATED_BY`,`o`.`CREATED_DATE` AS `CREATED_DATE`,`o`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`o`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ol`.`LABEL_EN` AS `LABEL_EN`,`ol`.`LABEL_FR` AS `LABEL_FR`,`ol`.`LABEL_SP` AS `LABEL_SP`,`ol`.`LABEL_PR` AS `LABEL_PR` from ((`rm_organisation` `o` left join `ap_label` `ol` on((`o`.`LABEL_ID` = `ol`.`LABEL_ID`))) left join `vw_organisation_type` `ot` on((`o`.`ORGANISATION_TYPE_ID` = `ot`.`ORGANISATION_TYPE_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_organisation_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_organisation_type` AS select `o`.`ORGANISATION_TYPE_ID` AS `ORGANISATION_TYPE_ID`,`o`.`REALM_ID` AS `REALM_ID`,`o`.`LABEL_ID` AS `LABEL_ID`,`o`.`ACTIVE` AS `ACTIVE`,`o`.`CREATED_BY` AS `CREATED_BY`,`o`.`CREATED_DATE` AS `CREATED_DATE`,`o`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`o`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ol`.`LABEL_EN` AS `LABEL_EN`,`ol`.`LABEL_FR` AS `LABEL_FR`,`ol`.`LABEL_SP` AS `LABEL_SP`,`ol`.`LABEL_PR` AS `LABEL_PR` from (`rm_organisation_type` `o` left join `ap_label` `ol` on((`o`.`LABEL_ID` = `ol`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_planning_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_planning_unit` AS select `pu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,`pu`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,`pu`.`LABEL_ID` AS `LABEL_ID`,`pu`.`UNIT_ID` AS `UNIT_ID`,`pu`.`MULTIPLIER` AS `MULTIPLIER`,`pu`.`ACTIVE` AS `ACTIVE`,`pu`.`CREATED_BY` AS `CREATED_BY`,`pu`.`CREATED_DATE` AS `CREATED_DATE`,`pu`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pu`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pul`.`LABEL_EN` AS `LABEL_EN`,`pul`.`LABEL_FR` AS `LABEL_FR`,`pul`.`LABEL_SP` AS `LABEL_SP`,`pul`.`LABEL_PR` AS `LABEL_PR` from (`rm_planning_unit` `pu` left join `ap_label` `pul` on((`pu`.`LABEL_ID` = `pul`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_problem`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_problem` AS select `pr`.`PROBLEM_ID` AS `PROBLEM_ID`,`pr`.`PROBLEM_CATEGORY_ID` AS `PROBLEM_CATEGORY_ID`,`pr`.`LABEL_ID` AS `LABEL_ID`,`pr`.`ACTION_URL` AS `ACTION_URL`,`pr`.`ACTION_LABEL_ID` AS `ACTION_LABEL_ID`,`pr`.`ACTUAL_CONSUMPTION_TRIGGER` AS `ACTUAL_CONSUMPTION_TRIGGER`,`pr`.`FORECASTED_CONSUMPTION_TRIGGER` AS `FORECASTED_CONSUMPTION_TRIGGER`,`pr`.`INVENTORY_TRIGGER` AS `INVENTORY_TRIGGER`,`pr`.`ADJUSTMENT_TRIGGER` AS `ADJUSTMENT_TRIGGER`,`pr`.`SHIPMENT_TRIGGER` AS `SHIPMENT_TRIGGER`,`pr`.`ACTIVE` AS `ACTIVE`,`pr`.`CREATED_BY` AS `CREATED_BY`,`pr`.`CREATED_DATE` AS `CREATED_DATE`,`pr`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pr`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`prl`.`LABEL_EN` AS `LABEL_EN`,`prl`.`LABEL_FR` AS `LABEL_FR`,`prl`.`LABEL_SP` AS `LABEL_SP`,`prl`.`LABEL_PR` AS `LABEL_PR`,`pral`.`LABEL_EN` AS `ACTION_LABEL_EN`,`pral`.`LABEL_FR` AS `ACTION_LABEL_FR`,`pral`.`LABEL_SP` AS `ACTION_LABEL_SP`,`pral`.`LABEL_PR` AS `ACTION_LABEL_PR` from ((`ap_problem` `pr` left join `ap_label` `prl` on((`pr`.`LABEL_ID` = `prl`.`LABEL_ID`))) left join `ap_label` `pral` on((`pr`.`ACTION_LABEL_ID` = `pral`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_problem_category`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_problem_category` AS select `pc`.`PROBLEM_CATEGORY_ID` AS `PROBLEM_CATEGORY_ID`,`l`.`LABEL_ID` AS `LABEL_ID`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_problem_category` `pc` left join `ap_label` `l` on((`pc`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_problem_criticality`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_problem_criticality` AS select `cr`.`CRITICALITY_ID` AS `CRITICALITY_ID`,`cr`.`LABEL_ID` AS `LABEL_ID`,`cr`.`COLOR_HTML_CODE` AS `COLOR_HTML_CODE`,`crl`.`LABEL_EN` AS `LABEL_EN`,`crl`.`LABEL_FR` AS `LABEL_FR`,`crl`.`LABEL_SP` AS `LABEL_SP`,`crl`.`LABEL_PR` AS `LABEL_PR` from (`ap_problem_criticality` `cr` left join `ap_label` `crl` on((`cr`.`LABEL_ID` = `crl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_problem_status`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_problem_status` AS select `ps`.`PROBLEM_STATUS_ID` AS `PROBLEM_STATUS_ID`,`ps`.`USER_MANAGED` AS `USER_MANAGED`,`ps`.`LABEL_ID` AS `LABEL_ID`,`psl`.`LABEL_EN` AS `LABEL_EN`,`psl`.`LABEL_FR` AS `LABEL_FR`,`psl`.`LABEL_SP` AS `LABEL_SP`,`psl`.`LABEL_PR` AS `LABEL_PR` from (`ap_problem_status` `ps` left join `ap_label` `psl` on((`ps`.`LABEL_ID` = `psl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_problem_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_problem_type` AS select `pt`.`PROBLEM_TYPE_ID` AS `PROBLEM_TYPE_ID`,`pt`.`LABEL_ID` AS `LABEL_ID`,`ptl`.`LABEL_EN` AS `LABEL_EN`,`ptl`.`LABEL_FR` AS `LABEL_FR`,`ptl`.`LABEL_SP` AS `LABEL_SP`,`ptl`.`LABEL_PR` AS `LABEL_PR` from (`ap_problem_type` `pt` left join `ap_label` `ptl` on((`pt`.`LABEL_ID` = `ptl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_agent`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_procurement_agent` AS select `pa`.`PROCUREMENT_AGENT_ID` AS `PROCUREMENT_AGENT_ID`,`pa`.`REALM_ID` AS `REALM_ID`,`pa`.`PROCUREMENT_AGENT_TYPE_ID` AS `PROCUREMENT_AGENT_TYPE_ID`,`pa`.`PROCUREMENT_AGENT_CODE` AS `PROCUREMENT_AGENT_CODE`,`pa`.`COLOR_HTML_CODE` AS `COLOR_HTML_CODE`,`pa`.`COLOR_HTML_DARK_CODE` AS `COLOR_HTML_DARK_CODE`,`pa`.`LABEL_ID` AS `LABEL_ID`,`pa`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,`pa`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,`pa`.`ACTIVE` AS `ACTIVE`,`pa`.`CREATED_BY` AS `CREATED_BY`,`pa`.`CREATED_DATE` AS `CREATED_DATE`,`pa`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pa`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pal`.`LABEL_EN` AS `LABEL_EN`,`pal`.`LABEL_FR` AS `LABEL_FR`,`pal`.`LABEL_SP` AS `LABEL_SP`,`pal`.`LABEL_PR` AS `LABEL_PR` from (`rm_procurement_agent` `pa` left join `ap_label` `pal` on((`pa`.`LABEL_ID` = `pal`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_agent_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_procurement_agent_type` AS select `pat`.`PROCUREMENT_AGENT_TYPE_ID` AS `PROCUREMENT_AGENT_TYPE_ID`,`pat`.`REALM_ID` AS `REALM_ID`,`pat`.`PROCUREMENT_AGENT_TYPE_CODE` AS `PROCUREMENT_AGENT_TYPE_CODE`,`pat`.`LABEL_ID` AS `LABEL_ID`,`pat`.`ACTIVE` AS `ACTIVE`,`pat`.`CREATED_BY` AS `CREATED_BY`,`pat`.`CREATED_DATE` AS `CREATED_DATE`,`pat`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pat`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_procurement_agent_type` `pat` left join `ap_label` `l` on((`pat`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_procurement_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_procurement_unit` AS select `pu`.`PROCUREMENT_UNIT_ID` AS `PROCUREMENT_UNIT_ID`,`pu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,`pu`.`LABEL_ID` AS `LABEL_ID`,`pu`.`UNIT_ID` AS `UNIT_ID`,`pu`.`MULTIPLIER` AS `MULTIPLIER`,`pu`.`SUPPLIER_ID` AS `SUPPLIER_ID`,`pu`.`WIDTH_QTY` AS `WIDTH_QTY`,`pu`.`HEIGHT_QTY` AS `HEIGHT_QTY`,`pu`.`LENGTH_UNIT_ID` AS `LENGTH_UNIT_ID`,`pu`.`LENGTH_QTY` AS `LENGTH_QTY`,`pu`.`WEIGHT_QTY` AS `WEIGHT_QTY`,`pu`.`WEIGHT_UNIT_ID` AS `WEIGHT_UNIT_ID`,`pu`.`VOLUME_QTY` AS `VOLUME_QTY`,`pu`.`VOLUME_UNIT_ID` AS `VOLUME_UNIT_ID`,`pu`.`UNITS_PER_CASE` AS `UNITS_PER_CASE`,`pu`.`UNITS_PER_PALLET_EURO1` AS `UNITS_PER_PALLET_EURO1`,`pu`.`UNITS_PER_PALLET_EURO2` AS `UNITS_PER_PALLET_EURO2`,`pu`.`UNITS_PER_CONTAINER` AS `UNITS_PER_CONTAINER`,`pu`.`LABELING` AS `LABELING`,`pu`.`ACTIVE` AS `ACTIVE`,`pu`.`CREATED_BY` AS `CREATED_BY`,`pu`.`CREATED_DATE` AS `CREATED_DATE`,`pu`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pu`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pul`.`LABEL_EN` AS `LABEL_EN`,`pul`.`LABEL_FR` AS `LABEL_FR`,`pul`.`LABEL_SP` AS `LABEL_SP`,`pul`.`LABEL_PR` AS `LABEL_PR` from (`rm_procurement_unit` `pu` left join `ap_label` `pul` on((`pu`.`LABEL_ID` = `pul`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_product_category`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_product_category` AS select `pc`.`PRODUCT_CATEGORY_ID` AS `PRODUCT_CATEGORY_ID`,`pc`.`REALM_ID` AS `REALM_ID`,`pc`.`LABEL_ID` AS `LABEL_ID`,`pc`.`PARENT_PRODUCT_CATEGORY_ID` AS `PARENT_PRODUCT_CATEGORY_ID`,`pc`.`SORT_ORDER` AS `SORT_ORDER`,`pc`.`ACTIVE` AS `ACTIVE`,`pc`.`CREATED_BY` AS `CREATED_BY`,`pc`.`CREATED_DATE` AS `CREATED_DATE`,`pc`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`pc`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pcl`.`LABEL_EN` AS `LABEL_EN`,`pcl`.`LABEL_FR` AS `LABEL_FR`,`pcl`.`LABEL_SP` AS `LABEL_SP`,`pcl`.`LABEL_PR` AS `LABEL_PR` from (`rm_product_category` `pc` left join `ap_label` `pcl` on((`pc`.`LABEL_ID` = `pcl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_program`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_program` AS select `p`.`PROGRAM_ID` AS `PROGRAM_ID`,`p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,`p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,group_concat(`pha`.`HEALTH_AREA_ID` separator ',') AS `HEALTH_AREA_ID`,`p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,`p`.`LABEL_ID` AS `LABEL_ID`,`p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,`p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,`p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,`p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,`p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,`p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,`p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,`p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,`p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,`p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,`p`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,`p`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,`p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,`p`.`ACTIVE` AS `ACTIVE`,`p`.`CREATED_BY` AS `CREATED_BY`,`p`.`CREATED_DATE` AS `CREATED_DATE`,`p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`pl`.`LABEL_EN` AS `LABEL_EN`,`pl`.`LABEL_FR` AS `LABEL_FR`,`pl`.`LABEL_SP` AS `LABEL_SP`,`pl`.`LABEL_PR` AS `LABEL_PR`,`p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID` from ((`rm_program` `p` left join `rm_program_health_area` `pha` on((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`))) left join `ap_label` `pl` on((`p`.`LABEL_ID` = `pl`.`LABEL_ID`))) where (`p`.`PROGRAM_TYPE_ID` = 1) group by `p`.`PROGRAM_ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_realm`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_realm` AS select `r`.`REALM_ID` AS `REALM_ID`,`r`.`REALM_CODE` AS `REALM_CODE`,`r`.`LABEL_ID` AS `LABEL_ID`,`r`.`DEFAULT_REALM` AS `DEFAULT_REALM`,`r`.`MIN_MOS_MIN_GAURDRAIL` AS `MIN_MOS_MIN_GAURDRAIL`,`r`.`MIN_MOS_MAX_GAURDRAIL` AS `MIN_MOS_MAX_GAURDRAIL`,`r`.`MAX_MOS_MAX_GAURDRAIL` AS `MAX_MOS_MAX_GAURDRAIL`,`r`.`MIN_QPL_TOLERANCE` AS `MIN_QPL_TOLERANCE`,`r`.`MIN_QPL_TOLERANCE_CUT_OFF` AS `MIN_QPL_TOLERANCE_CUT_OFF`,`r`.`MAX_QPL_TOLERANCE` AS `MAX_QPL_TOLERANCE`,`r`.`ACTUAL_CONSUMPTION_MONTHS_IN_PAST` AS `ACTUAL_CONSUMPTION_MONTHS_IN_PAST`,`r`.`FORECAST_CONSUMPTION_MONTH_IN_PAST` AS `FORECAST_CONSUMPTION_MONTH_IN_PAST`,`r`.`INVENTORY_MONTHS_IN_PAST` AS `INVENTORY_MONTHS_IN_PAST`,`r`.`MIN_COUNT_FOR_MODE` AS `MIN_COUNT_FOR_MODE`,`r`.`MIN_PERC_FOR_MODE` AS `MIN_PERC_FOR_MODE`,`r`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,`r`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,`r`.`NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD`,`r`.`NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD`,`r`.`ACTIVE` AS `ACTIVE`,`r`.`CREATED_BY` AS `CREATED_BY`,`r`.`CREATED_DATE` AS `CREATED_DATE`,`r`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`r`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`rl`.`LABEL_EN` AS `LABEL_EN`,`rl`.`LABEL_FR` AS `LABEL_FR`,`rl`.`LABEL_SP` AS `LABEL_SP`,`rl`.`LABEL_PR` AS `LABEL_PR` from (`rm_realm` `r` left join `ap_label` `rl` on((`r`.`LABEL_ID` = `rl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_realm_country_planning_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_realm_country_planning_unit` AS select `rcpu`.`REALM_COUNTRY_PLANNING_UNIT_ID` AS `REALM_COUNTRY_PLANNING_UNIT_ID`,`rcpu`.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`,`rcpu`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,`rcpu`.`LABEL_ID` AS `LABEL_ID`,`rcpu`.`SKU_CODE` AS `SKU_CODE`,`rcpu`.`UNIT_ID` AS `UNIT_ID`,`rcpu`.`CONVERSION_METHOD` AS `CONVERSION_METHOD`,`rcpu`.`CONVERSION_NUMBER` AS `CONVERSION_NUMBER`,`rcpu`.`GTIN` AS `GTIN`,`rcpu`.`ACTIVE` AS `ACTIVE`,`rcpu`.`CREATED_BY` AS `CREATED_BY`,`rcpu`.`CREATED_DATE` AS `CREATED_DATE`,`rcpu`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`rcpu`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`rcpul`.`LABEL_EN` AS `LABEL_EN`,`rcpul`.`LABEL_FR` AS `LABEL_FR`,`rcpul`.`LABEL_SP` AS `LABEL_SP`,`rcpul`.`LABEL_PR` AS `LABEL_PR` from (`rm_realm_country_planning_unit` `rcpu` left join `ap_label` `rcpul` on((`rcpu`.`LABEL_ID` = `rcpul`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_region`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_region` AS select `r`.`REGION_ID` AS `REGION_ID`,`r`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,`r`.`LABEL_ID` AS `LABEL_ID`,`r`.`CAPACITY_CBM` AS `CAPACITY_CBM`,`r`.`GLN` AS `GLN`,`r`.`ACTIVE` AS `ACTIVE`,`r`.`CREATED_BY` AS `CREATED_BY`,`r`.`CREATED_DATE` AS `CREATED_DATE`,`r`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`r`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`rl`.`LABEL_EN` AS `LABEL_EN`,`rl`.`LABEL_FR` AS `LABEL_FR`,`rl`.`LABEL_SP` AS `LABEL_SP`,`rl`.`LABEL_PR` AS `LABEL_PR` from (`rm_region` `r` left join `ap_label` `rl` on((`r`.`LABEL_ID` = `rl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_scenario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_scenario` AS select `s`.`SCENARIO_ID` AS `SCENARIO_ID`,`s`.`TREE_ID` AS `TREE_ID`,`s`.`LABEL_ID` AS `LABEL_ID`,`s`.`CREATED_BY` AS `CREATED_BY`,`s`.`CREATED_DATE` AS `CREATED_DATE`,`s`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`s`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`s`.`ACTIVE` AS `ACTIVE`,`s`.`NOTES` AS `NOTES`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_scenario` `s` left join `ap_label` `l` on((`s`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_shipment_status`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_shipment_status` AS select `ss`.`SHIPMENT_STATUS_ID` AS `SHIPMENT_STATUS_ID`,`ss`.`LABEL_ID` AS `LABEL_ID`,`ss`.`ACTIVE` AS `ACTIVE`,`ss`.`CREATED_BY` AS `CREATED_BY`,`ss`.`CREATED_DATE` AS `CREATED_DATE`,`ss`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ss`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`sl`.`LABEL_EN` AS `LABEL_EN`,`sl`.`LABEL_FR` AS `LABEL_FR`,`sl`.`LABEL_SP` AS `LABEL_SP`,`sl`.`LABEL_PR` AS `LABEL_PR` from (`ap_shipment_status` `ss` left join `ap_label` `sl` on((`ss`.`LABEL_ID` = `sl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_supplier`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_supplier` AS select `s`.`SUPPLIER_ID` AS `SUPPLIER_ID`,`s`.`REALM_ID` AS `REALM_ID`,`s`.`LABEL_ID` AS `LABEL_ID`,`s`.`ACTIVE` AS `ACTIVE`,`s`.`CREATED_BY` AS `CREATED_BY`,`s`.`CREATED_DATE` AS `CREATED_DATE`,`s`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`s`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`sl`.`LABEL_EN` AS `LABEL_EN`,`sl`.`LABEL_FR` AS `LABEL_FR`,`sl`.`LABEL_SP` AS `LABEL_SP`,`sl`.`LABEL_PR` AS `LABEL_PR` from (`rm_supplier` `s` left join `ap_label` `sl` on((`s`.`LABEL_ID` = `sl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_tracer_category`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tracer_category` AS select `tc`.`TRACER_CATEGORY_ID` AS `TRACER_CATEGORY_ID`,`tc`.`REALM_ID` AS `REALM_ID`,`tc`.`HEALTH_AREA_ID` AS `HEALTH_AREA_ID`,`tc`.`LABEL_ID` AS `LABEL_ID`,`tcl`.`LABEL_EN` AS `LABEL_EN`,`tcl`.`LABEL_FR` AS `LABEL_FR`,`tcl`.`LABEL_SP` AS `LABEL_SP`,`tcl`.`LABEL_PR` AS `LABEL_PR`,`tc`.`ACTIVE` AS `ACTIVE`,`tc`.`CREATED_BY` AS `CREATED_BY`,`tc`.`CREATED_DATE` AS `CREATED_DATE`,`tc`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`tc`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE` from (`rm_tracer_category` `tc` left join `ap_label` `tcl` on((`tc`.`LABEL_ID` = `tcl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_tree_level`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tree_level` AS select `tl`.`TREE_LEVEL_ID` AS `TREE_LEVEL_ID`,`tl`.`TREE_ID` AS `TREE_ID`,`tl`.`LEVEL_NO` AS `LEVEL_NO`,`tl`.`LABEL_ID` AS `LABEL_ID`,`tl`.`UNIT_ID` AS `UNIT_ID`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_forecast_tree_level` `tl` left join `ap_label` `l` on((`tl`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tree_template` AS select `tt`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,`tt`.`REALM_ID` AS `REALM_ID`,`tt`.`LABEL_ID` AS `LABEL_ID`,`tt`.`FORECAST_METHOD_ID` AS `FORECAST_METHOD_ID`,`tt`.`MONTHS_IN_PAST` AS `MONTHS_IN_PAST`,`tt`.`MONTHS_IN_FUTURE` AS `MONTHS_IN_FUTURE`,`tt`.`CREATED_BY` AS `CREATED_BY`,`tt`.`CREATED_DATE` AS `CREATED_DATE`,`tt`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`tt`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`tt`.`ACTIVE` AS `ACTIVE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR`,`tt`.`NOTES` AS `NOTES` from (`rm_tree_template` `tt` left join `ap_label` `l` on((`tt`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template_level`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tree_template_level` AS select `tl`.`TREE_TEMPLATE_LEVEL_ID` AS `TREE_TEMPLATE_LEVEL_ID`,`tl`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,`tl`.`LEVEL_NO` AS `LEVEL_NO`,`tl`.`LABEL_ID` AS `LABEL_ID`,`tl`.`UNIT_ID` AS `UNIT_ID`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_tree_template_level` `tl` left join `ap_label` `l` on((`tl`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_tree_template_node`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tree_template_node` AS select `ttn`.`NODE_ID` AS `NODE_ID`,`ttn`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,`ttn`.`COLLAPSED` AS `COLLAPSED`,`ttn`.`DOWNWARD_AGGREGATION_ALLOWED` AS `DOWNWARD_AGGREGATION_ALLOWED`,`ttn`.`PARENT_NODE_ID` AS `PARENT_NODE_ID`,`ttn`.`SORT_ORDER` AS `SORT_ORDER`,`ttn`.`LEVEL_NO` AS `LEVEL_NO`,`ttn`.`NODE_TYPE_ID` AS `NODE_TYPE_ID`,`ttn`.`UNIT_ID` AS `UNIT_ID`,`ttn`.`LABEL_ID` AS `LABEL_ID`,`ttn`.`CREATED_BY` AS `CREATED_BY`,`ttn`.`CREATED_DATE` AS `CREATED_DATE`,`ttn`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ttn`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ttn`.`ACTIVE` AS `ACTIVE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_tree_template_node` `ttn` left join `ap_label` `l` on((`ttn`.`LABEL_ID` = `l`.`LABEL_ID`))) order by `ttn`.`TREE_TEMPLATE_ID`,`ttn`.`SORT_ORDER` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_unit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_unit` AS select `u`.`UNIT_ID` AS `UNIT_ID`,`u`.`DIMENSION_ID` AS `DIMENSION_ID`,`u`.`LABEL_ID` AS `LABEL_ID`,`u`.`UNIT_CODE` AS `UNIT_CODE`,`u`.`ACTIVE` AS `ACTIVE`,`u`.`CREATED_BY` AS `CREATED_BY`,`u`.`CREATED_DATE` AS `CREATED_DATE`,`u`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`u`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ul`.`LABEL_EN` AS `LABEL_EN`,`ul`.`LABEL_FR` AS `LABEL_FR`,`ul`.`LABEL_SP` AS `LABEL_SP`,`ul`.`LABEL_PR` AS `LABEL_PR` from (`ap_unit` `u` left join `ap_label` `ul` on((`u`.`LABEL_ID` = `ul`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_usage_period`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_usage_period` AS select `ut`.`USAGE_PERIOD_ID` AS `USAGE_PERIOD_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`CONVERT_TO_MONTH` AS `CONVERT_TO_MONTH`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_usage_period` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_usage_template`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_usage_template` AS select `ut`.`USAGE_TEMPLATE_ID` AS `USAGE_TEMPLATE_ID`,`ut`.`REALM_ID` AS `REALM_ID`,`ut`.`PROGRAM_ID` AS `PROGRAM_ID`,`ut`.`UNIT_ID` AS `UNIT_ID`,`ut`.`FORECASTING_UNIT_ID` AS `FORECASTING_UNIT_ID`,`ut`.`LAG_IN_MONTHS` AS `LAG_IN_MONTHS`,`ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,`ut`.`NO_OF_PATIENTS` AS `NO_OF_PATIENTS`,`ut`.`NO_OF_FORECASTING_UNITS` AS `NO_OF_FORECASTING_UNITS`,`ut`.`ONE_TIME_USAGE` AS `ONE_TIME_USAGE`,`ut`.`USAGE_FREQUENCY_USAGE_PERIOD_ID` AS `USAGE_FREQUENCY_USAGE_PERIOD_ID`,`ut`.`USAGE_FREQUENCY_COUNT` AS `USAGE_FREQUENCY_COUNT`,`ut`.`REPEAT_USAGE_PERIOD_ID` AS `REPEAT_USAGE_PERIOD_ID`,`ut`.`REPEAT_COUNT` AS `REPEAT_COUNT`,`ut`.`NOTES` AS `NOTES`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`l`.`LABEL_ID` AS `LABEL_ID`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`rm_usage_template` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_usage_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_usage_type` AS select `ut`.`USAGE_TYPE_ID` AS `USAGE_TYPE_ID`,`ut`.`LABEL_ID` AS `LABEL_ID`,`ut`.`ACTIVE` AS `ACTIVE`,`ut`.`CREATED_BY` AS `CREATED_BY`,`ut`.`CREATED_DATE` AS `CREATED_DATE`,`ut`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,`ut`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,`l`.`LABEL_EN` AS `LABEL_EN`,`l`.`LABEL_FR` AS `LABEL_FR`,`l`.`LABEL_SP` AS `LABEL_SP`,`l`.`LABEL_PR` AS `LABEL_PR` from (`ap_usage_type` `ut` left join `ap_label` `l` on((`ut`.`LABEL_ID` = `l`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_version_status`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_version_status` AS select `vs`.`VERSION_STATUS_ID` AS `VERSION_STATUS_ID`,`vs`.`LABEL_ID` AS `LABEL_ID`,`vsl`.`LABEL_EN` AS `LABEL_EN`,`vsl`.`LABEL_FR` AS `LABEL_FR`,`vsl`.`LABEL_SP` AS `LABEL_SP`,`vsl`.`LABEL_PR` AS `LABEL_PR` from (`ap_version_status` `vs` left join `ap_label` `vsl` on((`vs`.`LABEL_ID` = `vsl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!50001 DROP VIEW IF EXISTS `vw_version_type`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`faspUser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_version_type` AS select `vt`.`VERSION_TYPE_ID` AS `VERSION_TYPE_ID`,`vt`.`LABEL_ID` AS `LABEL_ID`,`vtl`.`LABEL_EN` AS `LABEL_EN`,`vtl`.`LABEL_FR` AS `LABEL_FR`,`vtl`.`LABEL_SP` AS `LABEL_SP`,`vtl`.`LABEL_PR` AS `LABEL_PR` from (`ap_version_type` `vt` left join `ap_label` `vtl` on((`vt`.`LABEL_ID` = `vtl`.`LABEL_ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


SET foreign_key_checks = 0;
INSERT INTO `us_user`
(`USER_ID`, `REALM_ID`, `USERNAME`, `PASSWORD`, `EMAIL_ID`, `ORG_AND_COUNTRY`, `LANGUAGE_ID`, `ACTIVE`, `FAILED_ATTEMPTS`, `EXPIRES_ON`, `SYNC_EXPIRES_ON`, `LAST_LOGIN_DATE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `AGREEMENT_ACCEPTED`, `JIRA_ACCOUNT_ID`, `DEFAULT_MODULE_ID`, `DEFAULT_THEME_ID`, `SHOW_DECIMALS`)
VALUES
    (1, 1, 'Alexio Danje', '$2a$10$4PlC61faEmVXr28xkAi3w.zUBq3cJ6OOWtcJTLZucWm6.Xby8whyG', 'alexiodanje@gmail.com', '96371396638', 1, 1, 0, '2025-07-08 11:37:02', '2024-07-08 11:37:27', '2024-07-08 11:37:27', 1, '2020-02-12 12:00:00', 1, '2024-07-08 11:38:38', 1, 'qm:98503734-59b7-46ea-bb68-137900ca2d4b:702316ba-5743-4c6b-8eb3-56334b63cc34', 2, 1, 0);

INSERT INTO `ap_language` (`LANGUAGE_ID`, `LANGUAGE_CODE`, `LABEL_ID`, `COUNTRY_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES (1, 'en', 29207, 'us', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

INSERT INTO `us_user_acl`
(`USER_ACL_ID`, `USER_ID`, `ROLE_ID`, `REALM_COUNTRY_ID`, `HEALTH_AREA_ID`, `ORGANISATION_ID`, `PROGRAM_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES
    (1, 1, 'ROLE_APPLICATION_ADMIN', NULL, NULL, NULL, NULL, 1, '2022-07-05 07:08:38', 1, '2022-07-05 07:08:38');

INSERT INTO `us_user_role`
(`USER_ROLE_ID`, `USER_ID`, `ROLE_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES
    (1, 1, 'ROLE_APPLICATION_ADMIN', 1, '2022-07-05 07:08:38', 1, '2022-07-05 07:08:38');

INSERT INTO `us_role`
(`ROLE_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES
    ('ROLE_APPLICATION_ADMIN', 25, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

INSERT INTO `us_can_create_role` (`ROLE_ID`, `CAN_CREATE_ROLE`)
VALUES
    ('ROLE_APPLICATION_ADMIN', 'ROLE_APPLICATION_ADMIN'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_DATASET_ADMIN'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_DATASET_USER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_FORECAST_PROGRAM_ADMIN_(NO_UPLOAD)'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_FORECAST_VIEWER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_GUEST_USER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_INTERNAL_USER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_PROGRAM_ADMIN'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_PROGRAM_USER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_REALM_ADMIN'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_REPORT_USER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_SP_PROGRAM_LEVEL_ADMIN_(NO_UPLOAD)'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_SUPPLY_PLAN_REVIEWER'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_TRAINER_ADMIN'),
    ('ROLE_APPLICATION_ADMIN', 'ROLE_VIEW_DATA_ENTRY');

SET foreign_key_checks = 0;
INSERT INTO `us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES
    ('ROLE_BF_ADD_COUNTRY', 29473, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_ADD_CURRENCY', 29475, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_ADD_DIMENSION', 29477, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_ADD_LANGUAGE', 29479, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_ADD_ROLE', 29481, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_ADD_UNIT', 29485, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_ADD_USAGE_PERIOD', 36728, 1, '2022-05-15 10:57:35', 1, '2022-05-15 10:57:35'),
    ('ROLE_BF_ADD_USER', 29483, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_APPLICATION_DASHBOARD', 44, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_CREATE_REALM', 53, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_DATABASE_TRANSLATION', 54, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_DROPDOWN_FC', 342434, 1, '2024-11-28 09:40:04', 1, '2024-11-28 09:40:04'),
    ('ROLE_BF_DROPDOWN_SP', 310171, 1, '2024-02-24 05:44:53', 1, '2024-02-24 05:44:53'),
    ('ROLE_BF_EDIT_COUNTRY', 29474, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_EDIT_CURRENCY', 29476, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_EDIT_DIMENSION', 29478, 1, '2021-03-16 06:54:53', 1, '2021-03-16 06:54:53'),
    ('ROLE_BF_EDIT_LANGUAGE', 29480, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_EDIT_REALM', 55, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_EDIT_ROLE', 29482, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_EDIT_UNIT', 29486, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_EDIT_USAGE_PERIOD', 36729, 1, '2022-05-15 10:57:35', 1, '2022-05-15 10:57:35'),
    ('ROLE_BF_EDIT_USER', 29484, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_FORECASTING_MODULE', 36830, 1, '2022-05-15 10:57:57', 1, '2022-05-15 10:57:57'),
    ('ROLE_BF_LABEL_TRANSLATIONS', 45, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_LIST_COUNTRY', 29524, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_CURRENCY', 29525, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_DIMENSION', 29526, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_LANGUAGE', 29527, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_MASTER_DATA', 342433, 1, '2024-11-28 09:40:04', 1, '2024-11-28 09:40:04'),
    ('ROLE_BF_LIST_REALM', 105, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00'),
    ('ROLE_BF_LIST_ROLE', 29528, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_UNIT', 29530, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LIST_USAGE_PERIOD', 36730, 1, '2022-05-15 10:57:35', 1, '2022-05-15 10:57:35'),
    ('ROLE_BF_LIST_USER', 29529, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54'),
    ('ROLE_BF_LOGGED_IN', 342435, 1, '2024-11-28 09:40:04', 1, '2024-11-28 09:40:04'),
    ('ROLE_BF_MAP_ACCESS_CONTROL', 29488, 1, '2021-03-16 06:54:54', 1, '2021-03-16 06:54:54');

INSERT INTO `temp_security` (`SECURITY_ID`, `METHOD`, `URL_LIST`, `BF_LIST`)
VALUES
    (1, 1, '/api/logout', 'ROLE_BF_LOGGED_IN'),
    (2, 2, '/api/user/agreement', 'ROLE_BF_LOGGED_IN'),
    (3, 2, '/api/user/changePassword', 'ROLE_BF_LOGGED_IN'),
    (4, 1, '/api/user/details', 'ROLE_BF_LOGGED_IN'),
    (5, 2, '/api/user/language', 'ROLE_BF_LOGGED_IN'),
    (6, 2, '/api/user/module/*', 'ROLE_BF_LOGGED_IN'),
    (7, 2, '/api/user/theme/*', 'ROLE_BF_LOGGED_IN'),
    (8, 1, '/file/*', 'ROLE_BF_LOGGED_IN'),
    (9, 2, '/api/ticket/addIssue', 'ROLE_BF_LOGGED_IN'),
    (10, 2, '/api/ticket/addIssueAttachment/*', 'ROLE_BF_LOGGED_IN'),
    (11, 1, '/api/ticket/openIssues', 'ROLE_BF_LOGGED_IN'),
    (12, 1, '/api/locales/**', 'ROLE_BF_LOGGED_IN'),
    (13, 1, '/api/sync/language/*', 'ROLE_BF_LOGGED_IN'),
    (14, 2, '/api/sync/allMasters/forPrograms/*', 'ROLE_BF_LOGGED_IN'),
    (15, 2, '/api/sync/treeAnchor', 'ROLE_BF_LOGGED_IN'),
    (22, 1, '/api/businessFunction', 'ROLE_BF_ADD_ROLE'),
    (24, 2, '/api/country', 'ROLE_BF_ADD_COUNTRY'),
    (25, 3, '/api/country', 'ROLE_BF_EDIT_COUNTRY'),
    (26, 1, '/api/country/*', 'ROLE_BF_LIST_COUNTRY'),
    (28, 2, '/api/currency', 'ROLE_BF_ADD_CURRENCY'),
    (30, 3, '/api/currency', 'ROLE_BF_EDIT_CURRENCY'),
    (31, 1, '/api/currency/*', 'ROLE_BF_LIST_CURRENCY'),
    (35, 1, '/api/dashboard/realmLevel/**', 'ROLE_BF_APPLICATION_DASHBOARD'),
    (36, 1, '/api/dashboard/realmLevel/userList', 'ROLE_BF_APPLICATION_DASHBOARD'),
    (53, 2, '/api/dimension', 'ROLE_BF_ADD_DIMENSION'),
    (54, 3, '/api/dimension', 'ROLE_BF_EDIT_DIMENSION'),
    (56, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION'),
    (57, 1, '/api/dimension/*', 'ROLE_BF_LIST_DIMENSION'),
    (113, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (114, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (115, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (116, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (117, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (118, 1, '/api/master/*', 'ROLE_BF_LIST_MASTER_DATA'),
    (191, 2, '/api/realm', 'ROLE_BF_CREATE_REALM'),
    (192, 3, '/api/realm', 'ROLE_BF_EDIT_REALM'),
    (194, 1, '/api/realm/*', 'ROLE_BF_LIST_REALM'),
    (210, 2, '/api/role', 'ROLE_BF_ADD_ROLE'),
    (211, 3, '/api/role', 'ROLE_BF_ADD_ROLE'),
    (212, 1, '/api/role/*', 'ROLE_BF_ADD_ROLE'),
    (226, 2, '/api/unit', 'ROLE_BF_ADD_UNIT'),
    (227, 3, '/api/unit', 'ROLE_BF_EDIT_UNIT'),
    (231, 2, '/api/usagePeriod', 'ROLE_BF_ADD_USAGE_PERIOD'),
    (237, 1, '/api/user', 'ROLE_BF_LIST_USER'),
    (238, 2, '/api/user', 'ROLE_BF_ADD_USER'),
    (239, 3, '/api/user', 'ROLE_BF_EDIT_USER'),
    (240, 1, '/api/user/accessControls', 'ROLE_BF_LIST_USER'),
    (241, 3, '/api/user/accessControls', 'ROLE_BF_LIST_USER'),
    (248, 1, '/api/dataset', 'ROLE_BF_DROPDOWN_FC'),
    (258, 1, '/api/dropdown/program/dataset/realm/*', 'ROLE_BF_DROPDOWN_FC'),
    (259, 1, '/api/dropdown/program/supplyPlan/realm/*', 'ROLE_BF_DROPDOWN_SP'),
    (261, 1, '/api/dropdown/program/sp/expanded/realm/*', 'ROLE_BF_DROPDOWN_SP'),
    (262, 1, '/api/dropdown/program/fc/expanded/realm/*', 'ROLE_BF_DROPDOWN_FC'),
    (263, 2, '/api/dropdown/program/sp/filter/healthAreaAndRealmCountry/realm/*', 'ROLE_BF_DROPDOWN_SP'),
    (264, 2, '/api/dropdown/program/fc/filter/healthAreaAndRealmCountry/realm/*', 'ROLE_BF_DROPDOWN_FC'),
    (265, 2, '/api/dropdown/program/sp/filter/multipleRealmCountry', 'ROLE_BF_DROPDOWN_SP'),
    (266, 2, '/api/dropdown/program/fc/filter/multipleRealmCountry', 'ROLE_BF_DROPDOWN_FC'),
    (280, 1, '/api/dropdown/fundingSource', 'ROLE_BF_DROPDOWN_SP'),
    (281, 1, '/api/dropdown/procurementAgent', 'ROLE_BF_DROPDOWN_SP'),
    (282, 2, '/api/dropdown/procurementAgent/filter/multiplePrograms', 'ROLE_BF_DROPDOWN_SP'),
    (285, 2, '/api/dropdown/planningUnit/program/filter/multipleProgramAndTracerCategory', 'ROLE_BF_DROPDOWN_SP'),
    (286, 2, '/api/dropdown/planningUnit/dataset/filter/programAndVersion', 'ROLE_BF_DROPDOWN_FC'),
    (287, 2, '/api/dropdown/budget/filter/multipleFundingSources', 'ROLE_BF_DROPDOWN_SP'),
    (288, 1, '/api/dropdown/budget/program/*', 'ROLE_BF_DROPDOWN_SP'),
    (289, 1, '/api/dropdown/version/filter/sp/programId/*', 'ROLE_BF_DROPDOWN_SP'),
    (290, 1, '/api/dropdown/version/filter/fc/programId/*', 'ROLE_BF_DROPDOWN_FC'),
    (291, 2, '/api/dropdown/version/filter/sp/programs', 'ROLE_BF_DROPDOWN_SP'),
    (292, 2, '/api/dropdown/version/filter/fc/programs', 'ROLE_BF_DROPDOWN_FC'),
    (293, 1, '/api/dropdown/treeTemplate', 'ROLE_BF_DROPDOWN_FC');

INSERT INTO `ap_security` (`SECURITY_ID`, `METHOD`, `URL`, `BF`)
VALUES
    (8, 1, '/api/businessFunction', 'ROLE_BF_ADD_ROLE'),
    (129, 1, '/api/integrationProgram', 'ROLE_BF_ADD_INTEGRATION_PROGRAM'),
    (131, 1, '/api/integrationProgram/**', 'ROLE_BF_ADD_INTEGRATION_PROGRAM'),
    (271, 1, '/api/role', 'ROLE_BF_ADD_ROLE'),
    (276, 1, '/api/role/*', 'ROLE_BF_ADD_ROLE'),
    (1, 2, '/api/budget', 'ROLE_BF_ADD_BUDGET'),
    (12, 2, '/api/country', 'ROLE_BF_ADD_COUNTRY'),
    (18, 2, '/api/currency', 'ROLE_BF_ADD_CURRENCY'),
    (350, 2, '/api/dataset', 'ROLE_BF_ADD_DATASET'),
    (33, 2, '/api/dataSource', 'ROLE_BF_ADD_DATA_SOURCE'),
    (44, 2, '/api/dataSourceType', 'ROLE_BF_ADD_DATA_SOURCE_TYPE'),
    (54, 2, '/api/dimension', 'ROLE_BF_ADD_DIMENSION'),
    (64, 2, '/api/equivalencyUnit', 'ROLE_BF_ADD_EQUIVALENCY_UNIT'),
    (70, 2, '/api/equivalencyUnit/mapping', 'ROLE_BF_ADD_EQUIVALENCY_UNIT_MAPPING'),
    (72, 2, '/api/forecastingUnit', 'ROLE_BF_ADD_FORECASTING_UNIT'),
    (90, 2, '/api/forecastMethod', 'ROLE_BF_ADD_FORECAST_METHOD'),
    (97, 2, '/api/fundingSource', 'ROLE_BF_ADD_FUNDING_SOURCE'),
    (105, 2, '/api/fundingSourceType', 'ROLE_BF_ADD_FUNDING_SOURCE'),
    (111, 2, '/api/healthArea', 'ROLE_BF_ADD_HEALTH_AREA'),
    (125, 2, '/api/integration', 'ROLE_BF_ADD_INTEGRATION'),
    (462, 2, '/api/language', 'ROLE_BF_ADD_LANGUAGE'),
    (143, 2, '/api/modelingType', 'ROLE_BF_ADD_MODELING_TYPE'),
    (147, 2, '/api/organisation', 'ROLE_BF_ADD_ORGANIZATION'),
    (154, 2, '/api/organisationType', 'ROLE_BF_ADD_ORGANIZATION_TYPE'),
    (169, 2, '/api/planningUnit', 'ROLE_BF_ADD_PLANNING_UNIT'),
    (195, 2, '/api/procurementAgent', 'ROLE_BF_ADD_PROCUREMENT_AGENT'),
    (204, 2, '/api/procurementAgent/**', 'ROLE_BF_ADD_PROCUREMENT_AGENT'),
    (213, 2, '/api/procurementAgentType', 'ROLE_BF_ADD_PROCUREMENT_AGENT'),
    (220, 2, '/api/procurementUnit', 'ROLE_BF_ADD_PROCUREMENT_UNIT'),
    (274, 2, '/api/role', 'ROLE_BF_ADD_ROLE'),
    (277, 2, '/api/supplier', 'ROLE_BF_ADD_SUPPLIER'),
    (283, 2, '/api/tracerCategory', 'ROLE_BF_ADD_TRACER_CATEGORY'),
    (584, 2, '/api/treeTemplate', 'ROLE_BF_ADD_TREE_TEMPLATE'),
    (299, 2, '/api/unit', 'ROLE_BF_ADD_UNIT'),
    (310, 2, '/api/usagePeriod', 'ROLE_BF_ADD_USAGE_PERIOD'),
    (321, 2, '/api/user', 'ROLE_BF_ADD_USER'),
    (130, 3, '/api/integrationProgram', 'ROLE_BF_ADD_INTEGRATION_PROGRAM'),
    (529, 3, '/api/program/planningUnit', 'ROLE_BF_ADD_PROGRAM_PRODUCT'),
    (275, 3, '/api/role', 'ROLE_BF_ADD_ROLE');

INSERT INTO `ap_security` (`SECURITY_ID`, `METHOD`, `URL`, `BF`)
VALUES
    (354, 1, '/api/datasetData/programId/**', 'ROLE_BF_LOGGED_IN'),
    (467, 1, '/api/locales/**', 'ROLE_BF_LOGGED_IN'),
    (134, 1, '/api/logout', 'ROLE_BF_LOGGED_IN'),
    (233, 1, '/api/productCategory/**', 'ROLE_BF_LOGGED_IN'),
    (579, 1, '/api/sync/language/*', 'ROLE_BF_LOGGED_IN'),
    (450, 1, '/api/ticket/openIssues', 'ROLE_BF_LOGGED_IN'),
    (583, 1, '/api/treeTemplate', 'ROLE_BF_LOGGED_IN'),
    (587, 1, '/api/treeTemplate/*', 'ROLE_BF_LOGGED_IN'),
    (320, 1, '/api/user', 'ROLE_BF_LIST_USER'),
    (324, 1, '/api/user/*', 'ROLE_BF_LIST_USER'),
    (325, 1, '/api/user/accessControls', 'ROLE_BF_LIST_USER'),
    (323, 1, '/api/user/details', 'ROLE_BF_LOGGED_IN'),
    (333, 1, '/api/user/programId/*', 'ROLE_BF_LIST_USER'),
    (335, 1, '/api/user/realmId/*', 'ROLE_BF_LIST_USER'),
    (447, 1, '/file/*', 'ROLE_BF_LOGGED_IN'),
    (342, 2, '/api/commit/getCommitRequest/*', 'ROLE_BF_LOGGED_IN'),
    (422, 2, '/api/erpLinking/shipmentSync', 'ROLE_BF_LOGGED_IN'),
    (580, 2, '/api/sync/allMasters/forPrograms/*', 'ROLE_BF_LOGGED_IN'),
    (581, 2, '/api/sync/treeAnchor', 'ROLE_BF_LOGGED_IN'),
    (448, 2, '/api/ticket/addIssue', 'ROLE_BF_LOGGED_IN'),
    (449, 2, '/api/ticket/addIssueAttachment/*', 'ROLE_BF_LOGGED_IN'),
    (327, 2, '/api/user/agreement', 'ROLE_BF_LOGGED_IN'),
    (328, 2, '/api/user/changePassword', 'ROLE_BF_LOGGED_IN'),
    (330, 2, '/api/user/language', 'ROLE_BF_LOGGED_IN'),
    (331, 2, '/api/user/module/*', 'ROLE_BF_LOGGED_IN'),
    (332, 2, '/api/user/theme/*', 'ROLE_BF_LOGGED_IN'),
    (326, 3, '/api/user/accessControls', 'ROLE_BF_LIST_USER');


INSERT INTO `ap_security` (`SECURITY_ID`, `METHOD`, `URL`, `BF`)
VALUES
    (451, 1, '/api/getDatabaseLabelsListAll', 'ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS'),
    (453, 1, '/api/getDatabaseLabelsListAll', 'ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS'),
    (452, 1, '/api/getDatabaseLabelsListAll', 'ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS'),
    (464, 1, '/api/language/*', 'ROLE_BF_EDIT_LANGUAGE'),
    (357, 1, '/api/planningUnit/programId/**', 'ROLE_BF_EDIT_DATASET'),
    (268, 1, '/api/region/**', 'ROLE_BF_EDIT_PROGRAM'),
    (334, 1, '/api/user/programId/*', 'ROLE_BF_EDIT_PROGRAM'),
    (315, 2, '/api/usageTemplate', 'ROLE_BF_EDIT_USAGE_TEMPLATE'),
    (316, 2, '/api/usageTemplate', 'ROLE_BF_EDIT_USAGE_TEMPLATE_ALL'),
    (317, 2, '/api/usageTemplate', 'ROLE_BF_EDIT_USAGE_TEMPLATE_OWN'),
    (2, 3, '/api/budget', 'ROLE_BF_EDIT_BUDGET'),
    (13, 3, '/api/country', 'ROLE_BF_EDIT_COUNTRY'),
    (22, 3, '/api/currency', 'ROLE_BF_EDIT_CURRENCY'),
    (351, 3, '/api/dataset', 'ROLE_BF_EDIT_DATASET'),
    (37, 3, '/api/dataSource', 'ROLE_BF_EDIT_DATA_SOURCE'),
    (48, 3, '/api/dataSourceType', 'ROLE_BF_EDIT_DATA_SOURCE_TYPE'),
    (55, 3, '/api/dimension', 'ROLE_BF_EDIT_DIMENSION'),
    (73, 3, '/api/forecastingUnit', 'ROLE_BF_EDIT_FORECASTING_UNIT'),
    (98, 3, '/api/fundingSource', 'ROLE_BF_EDIT_FUNDING_SOURCE'),
    (106, 3, '/api/fundingSourceType', 'ROLE_BF_EDIT_FUNDING_SOURCE'),
    (112, 3, '/api/healthArea', 'ROLE_BF_EDIT_HEALTH_AREA'),
    (126, 3, '/api/integration', 'ROLE_BF_EDIT_INTEGRATION'),
    (463, 3, '/api/language', 'ROLE_BF_EDIT_LANGUAGE'),
    (148, 3, '/api/organisation', 'ROLE_BF_EDIT_ORGANIZATION'),
    (155, 3, '/api/organisationType', 'ROLE_BF_EDIT_ORGANIZATION_TYPE'),
    (170, 3, '/api/planningUnit', 'ROLE_BF_EDIT_PLANNING_UNIT'),
    (196, 3, '/api/procurementAgent', 'ROLE_BF_EDIT_PROCUREMENT_AGENT'),
    (205, 3, '/api/procurementAgent/**', 'ROLE_BF_EDIT_PROCUREMENT_AGENT'),
    (214, 3, '/api/procurementAgentType', 'ROLE_BF_EDIT_PROCUREMENT_AGENT'),
    (221, 3, '/api/procurementUnit', 'ROLE_BF_EDIT_PROCUREMENT_UNIT'),
    (237, 3, '/api/realm', 'ROLE_BF_EDIT_REALM'),
    (455, 3, '/api/saveDatabaseLabels', 'ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS'),
    (457, 3, '/api/saveDatabaseLabels', 'ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS'),
    (456, 3, '/api/saveDatabaseLabels', 'ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS'),
    (278, 3, '/api/supplier', 'ROLE_BF_EDIT_SUPPLIER'),
    (284, 3, '/api/tracerCategory', 'ROLE_BF_EDIT_TRACER_CATEGORY'),
    (585, 3, '/api/treeTemplate', 'ROLE_BF_EDIT_TREE_TEMPLATE'),
    (300, 3, '/api/unit', 'ROLE_BF_EDIT_UNIT'),
    (322, 3, '/api/user', 'ROLE_BF_EDIT_USER');

SET foreign_key_checks = 0;
INSERT INTO `us_role_business_function`
(`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES
    (5474, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_COUNTRY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5475, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_CURRENCY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5476, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_DIMENSION', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5477, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_LANGUAGE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5478, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_ROLE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5479, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_UNIT', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5480, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_USAGE_PERIOD', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5481, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ADD_USER', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5482, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_APPLICATION_DASHBOARD', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5483, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_CREATE_REALM', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5484, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_DATABASE_TRANSLATION', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (7978, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_DROPDOWN_FC', 1, '2024-11-28 09:40:04', 1, '2024-11-28 09:40:04'),
    (7543, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_DROPDOWN_SP', 1, '2024-02-24 05:44:53', 1, '2024-02-24 05:44:53'),
    (5485, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_COUNTRY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5486, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_CURRENCY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5487, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_DIMENSION', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5488, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_LANGUAGE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5489, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_REALM', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5490, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_ROLE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5491, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_UNIT', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5492, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_USAGE_PERIOD', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5493, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_EDIT_USER', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5494, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_FORECASTING_MODULE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5495, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LABEL_TRANSLATIONS', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5496, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_COUNTRY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5497, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_CURRENCY', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5498, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_DIMENSION', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5499, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_LANGUAGE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (7963, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_MASTER_DATA', 1, '2024-11-28 09:40:04', 1, '2024-11-28 09:40:04'),
    (5500, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_REALM', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5501, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_USER', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5502, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LOGGED_IN', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04'),
    (5503, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_LIST_ROLE', 1, '2023-01-26 15:32:04', 1, '2023-01-26 15:32:04');

SET foreign_key_checks = 1;


