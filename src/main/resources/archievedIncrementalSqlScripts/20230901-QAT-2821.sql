ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling`  ADD COLUMN `MODELING_SOURCE` INT UNSIGNED NOT NULL COMMENT '0 for manual entry or old calculator, 1 for annual target calculator' AFTER `NOTES`;
ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling`  ADD COLUMN `MODELING_SOURCE` INT UNSIGNED NOT NULL COMMENT '0 for manual entry or old calculator, 1 for annual target calculator' AFTER `NOTES`;
UPDATE rm_forecast_tree_node_data_modeling ndm SET ndm.MODELING_SOURCE=0;
UPDATE rm_tree_template_node_data_modeling ndm SET ndm.MODELING_SOURCE=0;