ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
ADD COLUMN `PROGRAM_ID` INT UNSIGNED NULL AFTER `LAST_MODIFIED_DATE`,
ADD COLUMN `PLANNING_UNIT_ID` INT UNSIGNED NULL AFTER `PROGRAM_ID`;

ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
CHANGE COLUMN `PROGRAM_ID` `PROGRAM_ID` INT UNSIGNED NULL DEFAULT NULL AFTER `PROGRAM_PLANNING_UNIT_ID`,
CHANGE COLUMN `PLANNING_UNIT_ID` `PLANNING_UNIT_ID` INT UNSIGNED NULL DEFAULT NULL AFTER `PROGRAM_ID`;

UPDATE rm_program_planning_unit_procurement_agent ppupa LEFT JOIN rm_program_planning_unit ppu ON ppupa.PROGRAM_PLANNING_UNIT_ID=ppu.PROGRAM_PLANNING_UNIT_ID SET ppupa.PROGRAM_ID=ppu.PROGRAM_ID, ppupa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID;

ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
CHANGE COLUMN `PROGRAM_ID` `PROGRAM_ID` INT UNSIGNED NOT NULL ;

ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
ADD INDEX `fk_rm_papup_PROGRAM_ID_idx` (`PROGRAM_ID` ASC),
ADD INDEX `fk_rm_papup_PLANNING_UNIT_ID_idx` (`PLANNING_UNIT_ID` ASC);

ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
ADD CONSTRAINT `fk_rm_papup_PROGRAM_ID`
  FOREIGN KEY (`PROGRAM_ID`)
  REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_papup_PLANNING_UNIT_ID`
  FOREIGN KEY (`PLANNING_UNIT_ID`)
  REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
DROP FOREIGN KEY `fk_rm_papup_PROGRAM_PLANNING_UNIT_ID`;
ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` 
DROP COLUMN `PROGRAM_PLANNING_UNIT_ID`,
DROP INDEX `fk_rm_papup_idx_PROGRAM_PLANNING_UNIT_ID` ,
DROP INDEX `unq_rm_papu_PROGRAM_PLANNING_UNIT_ID_PROCUREMENT_AGENT_ID` ;
;
