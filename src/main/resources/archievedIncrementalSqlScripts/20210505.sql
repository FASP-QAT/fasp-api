ALTER TABLE `fasp`.`ap_label` 
DROP FOREIGN KEY `fk_label_createdBy`,
DROP FOREIGN KEY `fk_label_lastModifiedBy`;
ALTER TABLE `fasp`.`ap_label` 
DROP INDEX `fk_label_createdBy_idx` ,
ADD INDEX `fk_ap_label_createdBy_idx` (`CREATED_BY` ASC),
DROP INDEX `fk_label_lastModifiedBy_idx` ,
ADD INDEX `fk_ap_label_lastModifiedBy_idx` (`LAST_MODIFIED_BY` ASC),
DROP INDEX `idx_apLabe_labelEn` ,
ADD INDEX `idx_ap_label_labelEn` (`LABEL_EN` ASC);
ALTER TABLE `fasp`.`ap_label` 
ADD CONSTRAINT `fk_ap_label_label_createdBy`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ap_label_lastModifiedBy`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


UPDATE ap_label_source ls set ls.SOURCE_DESC='ap_shipment_status' where ls.SOURCE_ID=7;
