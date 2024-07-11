ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` 
CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(16,8) UNSIGNED NULL DEFAULT NULL ;

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_data` 
CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(18,8) NULL DEFAULT NULL ;

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_option_data` 
CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(18,8) NULL DEFAULT NULL ;
