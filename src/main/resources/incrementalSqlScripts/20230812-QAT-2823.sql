ALTER TABLE `fasp`.`rm_forecast_tree` ADD COLUMN `TREE_ANCHOR_ID` INT UNSIGNED NULL AFTER `TREE_ID`;

CREATE TABLE `rm_forecast_tree_anchor` (
  `TREE_ANCHOR_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `TREE_ID` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`TREE_ANCHOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

UPDATE rm_forecast_tree ft SET ft.TREE_ANCHOR_ID=null;
ALTER TABLE `fasp`.`rm_forecast_tree` ADD INDEX `fk_rm_forecast_tree_treeAnchorId_idx` (`TREE_ANCHOR_ID` ASC) VISIBLE;

ALTER TABLE `fasp`.`rm_forecast_tree` 
ADD CONSTRAINT `fk_rm_forecast_tree_treeAnchorId`
  FOREIGN KEY (`TREE_ANCHOR_ID`)
  REFERENCES `fasp`.`rm_forecast_tree_anchor` (`TREE_ANCHOR_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `fasp`.`rm_forecast_tree_anchor` ADD COLUMN `TREE_NAME` VARCHAR(255) NOT NULL AFTER `PROGRAM_ID`;

INSERT INTO rm_forecast_tree_anchor SELECT NULL, ft.PROGRAM_ID, ft.LABEL_EN, MIN(ft.TREE_ID), MIN(ft.CREATED_DATE) FROM vw_forecast_tree ft group by ft.PROGRAM_ID, ft.LABEL_EN ORDER by ft.CREATED_DATE;
UPDATE vw_forecast_tree t LEFT JOIN rm_forecast_tree_anchor ta ON t.PROGRAM_ID=ta.PROGRAM_ID AND ta.TREE_NAME=t.LABEL_EN SET t.TREE_ANCHOR_ID=ta.TREE_ANCHOR_ID;