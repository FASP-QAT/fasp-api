ALTER TABLE `fasp`.`rm_forecast_tree_node_data_pu` 
CHANGE COLUMN `REFILL_MONTHS` `REFILL_MONTHS` DECIMAL(16,4) UNSIGNED NULL DEFAULT NULL COMMENT '# of moths over which refulls are taken' ,
CHANGE COLUMN `PU_PER_VISIT` `PU_PER_VISIT` DECIMAL(16,4) UNSIGNED NOT NULL ;

ALTER TABLE `fasp`.`rm_tree_template_node_data_pu` CHANGE `REFILL_MONTHS` `REFILL_MONTHS` DECIMAL(16,4) UNSIGNED NULL COMMENT '# of moths over which refulls are taken'; 
ALTER TABLE `fasp`.`rm_tree_template_node_data_pu` CHANGE `PU_PER_VISIT` `PU_PER_VISIT` DECIMAL(16,4) UNSIGNED NOT NULL; 