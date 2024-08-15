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

INSERT INTO rm_dataset_planning_unit_selected_tree_list SELECT dpus.PROGRAM_PLANNING_UNIT_ID, dpus.REGION_ID, dpus.TREE_ID, dpus.SCENARIO_ID FROM rm_dataset_planning_unit_selected dpus WHERE dpus.TREE_ID IS NOT NULL AND dpus.SCENARIO_ID IS NOT NULL;

-- Change for TreeId
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP COLUMN `TREE_ID`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP FOREIGN KEY `fk_rm_dataset_planning_unit_selected_scenarioId`;
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP COLUMN `SCENARIO_ID`, DROP INDEX `fk_rm_dataset_planning_unit_selected_scenarioId_idx` ;