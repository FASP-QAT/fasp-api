CREATE TABLE `rm_batch_inventory` (
  `BATCH_INVENTORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` int unsigned NOT NULL,
  `PLANNING_UNIT_ID` int unsigned NOT NULL,
  `INVENTORY_DATE` date NOT NULL,
  `MAX_VERSION_ID` int unsigned NOT NULL,
  `TMP_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`BATCH_INVENTORY_ID`),
  KEY `fk_rm_batch_inventory_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_batch_inventory_planningUnitId_idx` (`PLANNING_UNIT_ID`),
  KEY `idx_rm_batch_inventory_inventoryDate` (`INVENTORY_DATE`),
  KEY `idx_rm_batch_inventory_maxVersionId` (`MAX_VERSION_ID`),
  CONSTRAINT `fk_rm_batch_inventory_planningUnitId` FOREIGN KEY (`PLANNING_UNIT_ID`) REFERENCES `rm_planning_unit` (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_rm_batch_inventory_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `rm_batch_inventory_trans` (
  `BATCH_INVENTORY_TRANS_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `BATCH_INVENTORY_ID` int unsigned NOT NULL,
  `BATCH_ID` int unsigned NOT NULL,
  `QTY` int unsigned DEFAULT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `VERSION_ID` int unsigned NOT NULL,
  PRIMARY KEY (`BATCH_INVENTORY_TRANS_ID`),
  KEY `fk_rm_batch_inventory_trans_batchInventoryId_idx` (`BATCH_INVENTORY_ID`),
  KEY `fk_rm_batch_inventory_trans_batchId_idx` (`BATCH_ID`),
  KEY `fk_rm_batch_inventory_trans_lmb_idx` (`LAST_MODIFIED_BY`),
  KEY `idx_rm_batch_inventory_trans_versionId` (`VERSION_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_batchId` FOREIGN KEY (`BATCH_ID`) REFERENCES `rm_batch_info` (`BATCH_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_batchInventoryId` FOREIGN KEY (`BATCH_INVENTORY_ID`) REFERENCES `rm_batch_inventory` (`BATCH_INVENTORY_ID`),
  CONSTRAINT `fk_rm_batch_inventory_trans_lmb` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

