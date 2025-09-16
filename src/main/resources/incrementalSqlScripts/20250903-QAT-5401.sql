CREATE TABLE `tmp_rm_forecast_tree_node_downward_aggregation` (
  `NODE_DOWNWARD_AGGREGATION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TARGET_NODE_ID` int(10) unsigned NOT NULL,
  `TARGET_SCENARIO_ID` int(10) unsigned NOT NULL,
  `SOURCE_TREE_ID` int(10) unsigned NOT NULL,
  `SOURCE_SCENARIO_ID` int(10) unsigned NOT NULL,
  `SOURCE_NODE_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`NODE_DOWNWARD_AGGREGATION_ID`),
  KEY `fk_rm_ftndownward_agg_nodeId_idx` (`TARGET_NODE_ID`),
  KEY `fk_rm_ftn_downward_agg_scenarioId_idx` (`SOURCE_SCENARIO_ID`),
  KEY `fk_rm_ftn_downward_agg_sourceNodeId_idx` (`SOURCE_NODE_ID`),
  KEY `fk_rm_ftn_downward_agg_treeId_idx` (`SOURCE_TREE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tmp_rm_forecast_tree_node_downward_aggregation SELECT t.NODE_DOWNWARD_AGGREGATION_ID,t.TARGET_NODE_ID,MIN(s.SCENARIO_ID),t.SOURCE_TREE_ID,t.SOURCE_SCENARIO_ID,t.SOURCE_NODE_ID 
FROM rm_forecast_tree_node_downward_aggregation t
LEFT JOIN rm_forecast_tree_node n ON n.NODE_ID=t.TARGET_NODE_ID
LEFT JOIN rm_scenario s ON s.TREE_ID=n.TREE_ID 
GROUP BY t.NODE_DOWNWARD_AGGREGATION_ID;

ALTER TABLE `fasp`.`rm_forecast_tree_node_downward_aggregation` ADD COLUMN `TARGET_SCENARIO_ID` INT UNSIGNED NULL AFTER `TARGET_NODE_ID`; 

UPDATE rm_forecast_tree_node_downward_aggregation da
LEFT JOIN tmp_rm_forecast_tree_node_downward_aggregation t ON da.NODE_DOWNWARD_AGGREGATION_ID=t.NODE_DOWNWARD_AGGREGATION_ID
SET da.TARGET_SCENARIO_ID=t.TARGET_SCENARIO_ID;

INSERT INTO rm_forecast_tree_node_downward_aggregation SELECT null,t.TARGET_NODE_ID,MAX(s.SCENARIO_ID),t.SOURCE_TREE_ID,t.SOURCE_SCENARIO_ID,t.SOURCE_NODE_ID FROM rm_forecast_tree_node_downward_aggregation t
LEFT JOIN rm_forecast_tree_node n ON n.NODE_ID=t.TARGET_NODE_ID
LEFT JOIN rm_scenario s ON s.TREE_ID=n.TREE_ID 
GROUP BY t.NODE_DOWNWARD_AGGREGATION_ID HAVING COUNT(*)>1;

ALTER TABLE `fasp`.`rm_forecast_tree_node_downward_aggregation` CHANGE `TARGET_SCENARIO_ID` `TARGET_SCENARIO_ID` INT(10) UNSIGNED NOT NULL; 

ALTER TABLE `fasp`.`rm_forecast_tree_node_downward_aggregation` ADD CONSTRAINT `FK_rm_forecast_tree_node_downward_aggregation_targetScenarioId` FOREIGN KEY (`TARGET_SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`);

DROP TABLE tmp_rm_forecast_tree_node_downward_aggregation;