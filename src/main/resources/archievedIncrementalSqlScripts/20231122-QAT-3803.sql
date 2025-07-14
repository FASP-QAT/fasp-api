ALTER TABLE `fasp`.`us_role` 
CHANGE COLUMN `ROLE_ID` `ROLE_ID` VARCHAR(50) NOT NULL COMMENT 'Unique Role Id for every Role in the application' ;

ALTER TABLE `fasp`.`us_role_business_function` 
DROP FOREIGN KEY `fk_role_business_function_roleId`;
ALTER TABLE `fasp`.`us_role_business_function` 
CHANGE COLUMN `ROLE_ID` `ROLE_ID` VARCHAR(50) NOT NULL ;
ALTER TABLE `fasp`.`us_role_business_function` 
ADD CONSTRAINT `fk_role_business_function_roleId`
  FOREIGN KEY (`ROLE_ID`)
  REFERENCES `fasp`.`us_role` (`ROLE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `fasp`.`us_user_role` 
DROP FOREIGN KEY `fk_user_role_roleId`;
ALTER TABLE `fasp`.`us_user_role` 
CHANGE COLUMN `ROLE_ID` `ROLE_ID` VARCHAR(50) NOT NULL COMMENT 'Foriegn key for the Role Id' ;
ALTER TABLE `fasp`.`us_user_role` 
ADD CONSTRAINT `fk_user_role_roleId`
  FOREIGN KEY (`ROLE_ID`)
  REFERENCES `fasp`.`us_role` (`ROLE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
