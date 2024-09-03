INSERT INTO ap_label VALUES (null, 'Downward aggregation', 'Agrégation vers le bas', 'Agregación descendente', 'Agregação descendente', 1, now(), 1, now(), 39);
INSERT INTO ap_node_type VALUES (null, LAST_INSERT_ID(), 0, 0, 1, 1, 1, 1, now(), 1, now());

CREATE TABLE `fasp`.`rm_forecast_tree_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_TREE_ID` INT UNSIGNED NOT NULL,
  `SOURCE_SCENARIO_ID` INT UNSIGNED NOT NULL,
  `SOURCE_NODE_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  INDEX `fk_rm_ftndownward_agg_nodeId_idx` (`TARGET_NODE_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_scenarioId_idx` (`SOURCE_SCENARIO_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_sourceNodeId_idx` (`SOURCE_NODE_ID` ASC) VISIBLE,
  INDEX `fk_rm_ftn_downward_agg_treeId_idx` (`SOURCE_TREE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_rm_ftn_downward_agg_nodeId`
    FOREIGN KEY (`TARGET_NODE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_treeId`
    FOREIGN KEY (`SOURCE_TREE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree` (`TREE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_scenarioId`
    FOREIGN KEY (`SOURCE_SCENARIO_ID`)
    REFERENCES `fasp`.`rm_scenario` (`SCENARIO_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_ftn_downward_agg_sourceNodeId`
    FOREIGN KEY (`SOURCE_NODE_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node` (`NODE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
