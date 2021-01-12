ALTER TABLE `fasp`.`tr_data_source` 
ADD COLUMN `PROGRAM_ID` VARCHAR(45) NOT NULL AFTER `DATA_SOURCE_ID`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`DataSourceID`, `PROGRAM_ID`);

DROP TABLE IF EXISTS tr_procurement_agent;
CREATE TABLE `tr_procurement_agent` (
  `PipelineSupplier` VARCHAR(255) NOT NULL,
  `PROGRAM_ID` INT(10) NOT NULL,
  `PROCUREMENT_AGENT_ID` INT(10) NULL,
  PRIMARY KEY (`PipelineSupplier`, `PROGRAM_ID`));

DROP TABLE IF EXISTS tr_funding_source;
CREATE TABLE `tr_funding_source` (
  `PipelineFunder` VARCHAR(255) NOT NULL,
  `PROGRAM_ID` INT(10) NOT NULL,
  `FUNDING_SOURCE_ID` INT(10) NULL,
  PRIMARY KEY (`PipelineFunder`, `PROGRAM_ID`));

drop table IF EXISTS tr_product;
CREATE TABLE `fasp`.`tr_product` (
  `ProductID` VARCHAR(255) NOT NULL,
  `PROGRAM_ID` INT(10) NOT NULL,
  `Min` INT NULL,
  `Max` INT NULL,
  `AMC_Months_in_Past` INT NULL,
  `AMC_Months_in_Future` INT NULL,
  `LocalProcurement_leadTime` INT NULL,
  `ShelfLife` INT NULL,
  `DefaultPrice` DOUBLE(16,6) NULL,
  PRIMARY KEY (`ProductID`, `PROGRAM_ID`));

