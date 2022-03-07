ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` ADD COLUMN `CALCULATED_MMD_VALUE` DECIMAL(16,2) UNSIGNED NOT NULL AFTER `CALCULATED_VALUE`;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_pu` ADD COLUMN `PU_PER_VISIT` INT UNSIGNED NOT NULL AFTER `REFILL_MONTHS`;
ALTER TABLE `fasp`.`rm_tree_template_node_data_pu` ADD COLUMN `PU_PER_VISIT` INT UNSIGNED NOT NULL AFTER `REFILL_MONTHS`;
UPDATE rm_tree_template_node_data_pu SET PU_PER_VISIT = 1;
UPDATE rm_forecast_tree_node_data_pu SET PU_PER_VISIT = 1;