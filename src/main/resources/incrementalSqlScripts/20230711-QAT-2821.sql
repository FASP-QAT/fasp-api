ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` 
ADD COLUMN `CALCULATOR_FIRST_MONTH` VARCHAR(7) NULL AFTER `NOTES`,
ADD COLUMN `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NULL AFTER `CALCULATOR_FIRST_MONTH`;

ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` 
ADD COLUMN `CALCULATOR_FIRST_MONTH` VARCHAR(7) NULL AFTER `NOTES`,
ADD COLUMN `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NULL AFTER `CALCULATOR_FIRST_MONTH`;

DROP TABLE IF EXISTS `rm_forecast_tree_node_data_modeling_calculator`;
CREATE TABLE `rm_forecast_tree_node_data_modeling_calculator` (
  `NODE_DATA_MODELING_CALCULATOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_MODELING_ID` int unsigned NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_MODELING_CALCULATOR_ID`),
  KEY `fk_rm_forecast_tree_node_data_modeling_calculator_nodeDataM_idx` (`NODE_DATA_MODELING_ID`),
  CONSTRAINT `fk_rm_ftndmc_nodeDataModelingId` FOREIGN KEY (`NODE_DATA_MODELING_ID`) REFERENCES `rm_forecast_tree_node_data_modeling` (`NODE_DATA_MODELING_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `rm_tree_template_node_data_modeling_calculator`;
CREATE TABLE `rm_tree_template_node_data_modeling_calculator` (
  `NODE_DATA_MODELING_CALCULATOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `NODE_DATA_MODELING_ID` int unsigned NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_MODELING_CALCULATOR_ID`),
  KEY `fk_rm_tree_template_node_data_modeling_calculator_nodeDataM_idx` (`NODE_DATA_MODELING_ID`),
  CONSTRAINT `fk_rm_ttndmc_nodeDataModelingId` FOREIGN KEY (`NODE_DATA_MODELING_ID`) REFERENCES `rm_tree_template_node_data_modeling` (`NODE_DATA_MODELING_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;