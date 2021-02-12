ALTER TABLE `fasp`.`rm_realm_country_planning_unit` 
DROP INDEX `unqCountryPlanningUnitAndUnitId` ,
ADD UNIQUE INDEX `unqCountryPlanningUnitAndSkuCode` (`SKU_CODE` ASC, `REALM_COUNTRY_ID` ASC)  COMMENT 'Unique mapping for Country - Planning Unit and Unit Id mapping';

