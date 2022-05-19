CREATE TABLE `rm_tree_template_level` (
  `TREE_TEMPLATE_LEVEL_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `TREE_TEMPLATE_ID` int unsigned NOT NULL,
  `LEVEL_NO` int unsigned NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `UNIT_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`TREE_TEMPLATE_LEVEL_ID`),
  KEY `fk_rm_tree_template_level_treeTemplateId_idx` (`TREE_TEMPLATE_ID`),
  KEY `fk_rm_tree_template_level_unitId_idx` (`UNIT_ID`),
  KEY `fk_rm_tree_template_level_labelId_idx` (`LABEL_ID`),
  KEY `idx_rm_tree_template_level_levelNo` (`LEVEL_NO`),
  CONSTRAINT `fk_rm_tree_template_level_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`),
  CONSTRAINT `fk_rm_tree_template_level_treeTemplateId` FOREIGN KEY (`TREE_TEMPLATE_ID`) REFERENCES `rm_tree_template` (`TREE_TEMPLATE_ID`),
  CONSTRAINT `fk_rm_tree_template_level_unitId` FOREIGN KEY (`UNIT_ID`) REFERENCES `ap_unit` (`UNIT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


USE `fasp`;
CREATE  OR REPLACE VIEW `vw_tree_template_level` AS
SELECT 
        `tl`.`TREE_TEMPLATE_LEVEL_ID` AS `TREE_TEMPLATE_LEVEL_ID`,
        `tl`.`TREE_TEMPLATE_ID` AS `TREE_TEMPLATE_ID`,
        `tl`.`LEVEL_NO` AS `LEVEL_NO`,
        `tl`.`LABEL_ID` AS `LABEL_ID`,
        `tl`.`UNIT_ID` AS `UNIT_ID`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_tree_template_level` `tl`
        LEFT JOIN `ap_label` `l` ON ((`tl`.`LABEL_ID` = `l`.`LABEL_ID`)));


INSERT INTO ap_label_source values (null, 'rm_tree_template_level');
INSERT INTO ap_label values (null, 'Level 0' , null, null, null, 1, now(), 1, now(), 54);
SELECT LAST_INSERT_ID() INTO @labelId;
insert into rm_tree_template_level values (null, 1, 0, @labelId, 91);
INSERT INTO ap_label values (null, 'Level 1' , null, null, null, 1, now(), 1, now(), 54);
SELECT LAST_INSERT_ID() INTO @labelId;
insert into rm_tree_template_level values (null, 1, 1, @labelId, 91);