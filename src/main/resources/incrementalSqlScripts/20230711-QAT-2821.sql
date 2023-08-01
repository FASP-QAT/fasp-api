DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` INT UNSIGNED NOT NULL,
  `CALCULATOR_FIRST_MONTH` VARCHAR(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_idx` (`NODE_DATA_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_nodeDataId`
    FOREIGN KEY (`NODE_DATA_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data` (`NODE_DATA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator_data`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_annualTargetCalculatorId`
    FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` INT UNSIGNED NOT NULL,
  `CALCULATOR_FIRST_MONTH` VARCHAR(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_idx` (`NODE_DATA_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_nodeDataId`
    FOREIGN KEY (`NODE_DATA_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data` (`NODE_DATA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator_data`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_annualTargetCalculatorId`
    FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
