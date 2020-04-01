-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema fasp
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `fasp` ;

-- -----------------------------------------------------
-- Schema fasp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fasp` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `fasp` ;

-- -----------------------------------------------------
-- Table `fasp`.`ap_language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_language` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_language` (
  `LANGUAGE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Language that we support, this includes Db languages and UI languages',
  `LANGUAGE_CODE` VARCHAR(2) NOT NULL,
  `LANGUAGE_NAME` VARCHAR(100) NOT NULL COMMENT 'Language name, no need for a Label here since the Language name will be in the required language',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Language is Active. False indicates this Language has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified By',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT '2 char language code that is used in the translations',
  PRIMARY KEY (`LANGUAGE_ID`),
  CONSTRAINT `fk_language_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Languages availale in the Application\nNote: A Language cannot be created or administered it is at the Application level';

CREATE INDEX `fk_language_createdBy_idx` ON `fasp`.`ap_language` (`CREATED_BY` ASC);

CREATE INDEX `fk_language_lastModifiedBy_idx` ON `fasp`.`ap_language` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqLanguageName` ON `fasp`.`ap_language` (`LANGUAGE_NAME` ASC)  COMMENT 'Language name should be unique';

CREATE UNIQUE INDEX `unqLanguageCode` ON `fasp`.`ap_language` (`LANGUAGE_CODE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_label`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_label` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_label` (
  `LABEL_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Label',
  `LABEL_EN` VARCHAR(255) NOT NULL COMMENT 'Label in English, cannot be Null since it is language the system will default to',
  `LABEL_FR` VARCHAR(255) NULL COMMENT 'Label in French',
  `LABEL_SP` VARCHAR(255) NULL COMMENT 'Label in Spanish',
  `LABEL_PR` VARCHAR(255) NULL COMMENT 'Label in Pourtegese',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified By',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified Date\n',
  PRIMARY KEY (`LABEL_ID`),
  CONSTRAINT `fk_label_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_label_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Languages availale in the Application\nNote: A Language cannot be created it is one of a Fixed Master';

CREATE INDEX `fk_label_createdBy_idx` ON `fasp`.`ap_label` (`CREATED_BY` ASC);

CREATE INDEX `fk_label_lastModifiedBy_idx` ON `fasp`.`ap_label` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_realm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_realm` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_realm` (
  `REALM_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Realm',
  `REALM_CODE` VARCHAR(6) NOT NULL COMMENT 'Unique Code for each Realm, will be given at the time of creation and cannot be edited',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `MONTHS_IN_PAST_FOR_AMC` INT(10) UNSIGNED NOT NULL COMMENT 'No of months that we should go back in the past to calculate AMC. Default to be used when we create a Program',
  `MONTHS_IN_FUTURE_FOR_AMC` INT(10) UNSIGNED NOT NULL COMMENT 'No of months that we should go into the future to calculate AMC. Default to be used when we create a Program',
  `ORDER_FREQUENCY` INT(10) UNSIGNED NOT NULL COMMENT 'In how many months do you want to place orders. Default to be used when we create a Program',
  `DEFAULT_REALM` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'If True indicates this Realm is the Default Realm for the Application',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Realm is Active. False indicates this Realm has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT '\n\n',
  PRIMARY KEY (`REALM_ID`),
  CONSTRAINT `fk_realm_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Realms using the application\nNote: A Realm cannot be created in the App but must be created on the Online portal only, Can be created only by Application Admins, but be administered by Realm Admin';

CREATE INDEX `fk_realm_labelId_idx` ON `fasp`.`rm_realm` (`LABEL_ID` ASC);

CREATE INDEX `fk_realm_createdBy_idx` ON `fasp`.`rm_realm` (`CREATED_BY` ASC);

CREATE INDEX `fk_realm_lastModifiedBy_idx` ON `fasp`.`rm_realm` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `REALM_CODE_UNIQUE` ON `fasp`.`rm_realm` (`REALM_CODE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_user` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_user` (
  `USER_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique User Id for each User',
  `REALM_ID` INT(10) UNSIGNED NULL COMMENT 'Realm Id that the User belongs to',
  `USERNAME` VARCHAR(25) NOT NULL COMMENT 'Username used to login',
  `PASSWORD` TINYBLOB NOT NULL COMMENT 'Encrypted password for the User\nOffline notes: Password cannot be updated when a User is offline',
  `EMAIL_ID` VARCHAR(50) NULL,
  `PHONE` VARCHAR(15) NULL,
  `LANGUAGE_ID` INT(10) UNSIGNED NOT NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'True indicates the User is actvie and False indicates the User has been De-activated\nOffline notes: Even if a User has been De-activated Online, he will still be able to use the Offline version until he reaches his Last Sync by date',
  `FAILED_ATTEMPTS` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Number of failed attempts that have been made to login to the application from this User Id\nOffline notes: Number will be incremented in the Offline mode as well and the only way to unlock it in Offline mode after it hits 3 attempts is to go online and then sync it with the Live Db where a Realm level Admin will be able to reset the password for you\n',
  `EXPIRES_ON` DATETIME NOT NULL COMMENT 'Date the Password for the User expires on. User will be forced to enter a new Password',
  `SYNC_EXPIRES_ON` DATETIME NOT NULL COMMENT 'Date after which the User will have to run a Sync to continue using the Offline version',
  `LAST_LOGIN_DATE` DATETIME NULL COMMENT 'Date the user last logged into the application. Null if no login has been done as yet.',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`USER_ID`),
  CONSTRAINT `fk_user_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_languageId`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `fasp`.`ap_language` (`LANGUAGE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Table used for lisiting all the Users that are going to access the system';

CREATE UNIQUE INDEX `USERNAME_UNIQUE` ON `fasp`.`us_user` (`USERNAME` ASC);

CREATE INDEX `fk_user_createdBy_idx` ON `fasp`.`us_user` (`CREATED_BY` ASC);

CREATE INDEX `fk_user_lastModifiedBy_idx` ON `fasp`.`us_user` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_user_languageId_idx` ON `fasp`.`us_user` (`LANGUAGE_ID` ASC);

CREATE INDEX `fk_user_realmId_idx` ON `fasp`.`us_user` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_currency` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_currency` (
  `CURRENCY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Currency',
  `CURRENCY_CODE` VARCHAR(4) NOT NULL COMMENT 'Unique Code for each Currency',
  `CURRENCY_SYMBOL` VARCHAR(3) NULL COMMENT 'Currency symbol',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CONVERSION_RATE_TO_USD` DECIMAL(14,4) UNSIGNED NOT NULL COMMENT 'Latest conversion rate to USD',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT '			',
  PRIMARY KEY (`CURRENCY_ID`),
  CONSTRAINT `fk_currency_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_currency_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_currency_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Currencies in the application\nNote: A Currency cannot be created it is one of a Fixed Master';

CREATE UNIQUE INDEX `CURRENCY_CODE_UNIQUE` ON `fasp`.`ap_currency` (`CURRENCY_CODE` ASC);

CREATE INDEX `fk_currency_labelId_idx` ON `fasp`.`ap_currency` (`LABEL_ID` ASC);

CREATE INDEX `fk_currency_createdBy_idx` ON `fasp`.`ap_currency` (`CREATED_BY` ASC);

CREATE INDEX `fk_currency_lastModifiedBy_idx` ON `fasp`.`ap_currency` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unq_currencyCode` ON `fasp`.`ap_currency` (`CURRENCY_CODE` ASC)  COMMENT 'Currency Code musy be unique';


-- -----------------------------------------------------
-- Table `fasp`.`ap_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_country` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_country` (
  `COUNTRY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Country',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `COUNTRY_CODE` VARCHAR(3) NOT NULL COMMENT 'Code for each country. Will take the data from the ISO Country list',
  `CURRENCY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Local Currency used by this country',
  `LANGUAGE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Default Language used by this Country',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this Country is Active. False indicates this Country has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`COUNTRY_ID`),
  CONSTRAINT `fk_country_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country_currencyId`
    FOREIGN KEY (`CURRENCY_ID`)
    REFERENCES `fasp`.`ap_currency` (`CURRENCY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country_languageId`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `fasp`.`ap_language` (`LANGUAGE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Countries inside a Realm\nNote: This is a master list of Countries stored at the Application level, cannot be created and edited by Realm admins';

CREATE INDEX `fk_country_labelId_idx` ON `fasp`.`ap_country` (`LABEL_ID` ASC);

CREATE INDEX `fk_country_currencyId_idx` ON `fasp`.`ap_country` (`CURRENCY_ID` ASC);

CREATE INDEX `fk_country_languageId_idx` ON `fasp`.`ap_country` (`LANGUAGE_ID` ASC);

CREATE INDEX `fk_country_createdBy_idx` ON `fasp`.`ap_country` (`CREATED_BY` ASC);

CREATE INDEX `fk_country_lastModifiedBy_idx` ON `fasp`.`ap_country` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unq_countryCode` ON `fasp`.`ap_country` (`COUNTRY_CODE` ASC)  COMMENT 'Unique Country Code ';


-- -----------------------------------------------------
-- Table `fasp`.`ap_currency_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_currency_history` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_currency_history` (
  `CURRENCY_HISTORY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Currency Transaction that we have pulled',
  `CURRENCY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'The Currency id that this data is for',
  `CONVERSION_RATE_TO_USD` DECIMAL(14,4) NOT NULL COMMENT 'Conversion rate to USD\n',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`CURRENCY_HISTORY_ID`),
  CONSTRAINT `fk_currency_history_currencyId`
    FOREIGN KEY (`CURRENCY_ID`)
    REFERENCES `fasp`.`ap_currency` (`CURRENCY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the historical values of Exchange rate for each currency\nNote: Updated automatically by the application';

CREATE INDEX `fk_currency_history_currencyId_idx` ON `fasp`.`ap_currency_history` (`CURRENCY_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_realm_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_realm_country` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_realm_country` (
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Realm - Country mapping',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realm this Mapping belongs to',
  `COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Country this Mapping belongs to',
  `DEFAULT_CURRENCY_ID` INT UNSIGNED NOT NULL COMMENT 'Currency Id that this Country should Default to',
  `PALLET_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Default Pallet size that the country uses',
  `AIR_FREIGHT_PERC` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Percentage of Order Qty when Mode = Air',
  `SEA_FREIGHT_PERC` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Percentage of Order Qty when Mode = Sea',
  `SHIPPED_TO_ARRIVED_AIR_LEAD_TIME` INT(10) UNSIGNED NULL COMMENT 'No of days for an Order to move from Shipped to Arrived status where mode = Air',
  `SHIPPED_TO_ARRIVED_SEA_LEAD_TIME` INT(10) UNSIGNED NULL COMMENT 'No of days for an Order to move from Shipped to Arrived status where mode = Sea',
  `ARRIVED_TO_DELIVERED_LEAD_TIME` INT(10) UNSIGNED NULL COMMENT 'No of days for an Order to move from Arrived to Delivered status',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Mapping is Active. False indicates this Mapping has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`REALM_COUNTRY_ID`),
  CONSTRAINT `fk_realm_country_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_country_countryId`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `fasp`.`ap_country` (`COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_country_currencyId`
    FOREIGN KEY (`DEFAULT_CURRENCY_ID`)
    REFERENCES `fasp`.`ap_currency` (`CURRENCY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_country_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realm_country_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map which Countries are used by the Realm\nNote: Show the list of all Countries and let the Realm Admin select which Country he wants to include in the Realm';

CREATE INDEX `fk_realm_country_realmId_idx` ON `fasp`.`rm_realm_country` (`REALM_ID` ASC);

CREATE INDEX `fk_realm_country_countryId_idx` ON `fasp`.`rm_realm_country` (`COUNTRY_ID` ASC);

CREATE INDEX `fk_realm_country_currencyId_idx` ON `fasp`.`rm_realm_country` (`DEFAULT_CURRENCY_ID` ASC);

CREATE INDEX `fk_realm_country_createdBy_idx` ON `fasp`.`rm_realm_country` (`CREATED_BY` ASC);

CREATE INDEX `fk_realm_country_lastModifiedBy_idx` ON `fasp`.`rm_realm_country` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqRealmIdCountryId` ON `fasp`.`rm_realm_country` (`REALM_ID` ASC, `COUNTRY_ID` ASC)  COMMENT 'Unique key for a Country in a Realm';


-- -----------------------------------------------------
-- Table `fasp`.`rm_health_area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_health_area` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_health_area` (
  `HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Health Area',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm and Country this Health Area belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`HEALTH_AREA_ID`),
  CONSTRAINT `fk_health_area_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_health_area_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_health_area_user1`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_health_area_rm_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the health_areas for each Realm\nNote: A Health Area can only be created and administered by a Realm Admin';

CREATE INDEX `fk_health_area_labelId_idx` ON `fasp`.`rm_health_area` (`LABEL_ID` ASC);

CREATE INDEX `fk_health_area_createdBy_idx` ON `fasp`.`rm_health_area` (`CREATED_BY` ASC);

CREATE INDEX `fk_health_area_lastModifiedBy_idx` ON `fasp`.`rm_health_area` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_rm_health_area_rm_realm1_idx` ON `fasp`.`rm_health_area` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_product_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_product_category` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_product_category` (
  `PRODUCT_CATEGORY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product Category',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Realm that this Product Category belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `LEVEL` INT(10) UNSIGNED NOT NULL,
  `SORT_ORDER` VARCHAR(15) NOT NULL COMMENT 'Defines the sort level for the Product Category',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Product Category is Active. False indicates this Product Category has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by\n',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PRODUCT_CATEGORY_ID`),
  CONSTRAINT `fk_program_category_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_category_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_category_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_product_category_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the product_categories for each health_area\nNote: A Product category can only be created and administered by a Realm Admin';

CREATE INDEX `fk_product_category_labelId_idx` ON `fasp`.`rm_product_category` (`LABEL_ID` ASC);

CREATE INDEX `fk_product_category_createdBy_idx` ON `fasp`.`rm_product_category` (`CREATED_BY` ASC);

CREATE INDEX `fk_product_category_lastModifiedBy_idx` ON `fasp`.`rm_product_category` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `idx_sortLevel` ON `fasp`.`rm_product_category` (`SORT_ORDER` ASC);

CREATE INDEX `fk_rm_product_category_realmId_idx` ON `fasp`.`rm_product_category` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_organisation` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_organisation` (
  `ORGANISATION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm this Organisation belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ORGANISATION_CODE` VARCHAR(4) NOT NULL COMMENT '4 character Unique Code for each Organisation',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT '3 ',
  PRIMARY KEY (`ORGANISATION_ID`),
  CONSTRAINT `fk_organisation_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the organizations for each Realm\nNote: An Organisation can only be created and administered by a Realm Admin';

CREATE INDEX `fk_organisation_realmId_idx` ON `fasp`.`rm_organisation` (`REALM_ID` ASC);

CREATE INDEX `fk_organisation_labelId_idx` ON `fasp`.`rm_organisation` (`LABEL_ID` ASC);

CREATE INDEX `fk_organisation_createdBy_idx` ON `fasp`.`rm_organisation` (`CREATED_BY` ASC);

CREATE INDEX `fk_organisation_lastModifiedBy_idx` ON `fasp`.`rm_organisation` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unq_organisationCode` ON `fasp`.`rm_organisation` (`ORGANISATION_CODE` ASC, `REALM_ID` ASC)  COMMENT 'Unique Organisation Code across the Realm';


-- -----------------------------------------------------
-- Table `fasp`.`rm_program`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_program` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_program` (
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Program',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this Program belongs to',
  `ORGANISATION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Organisation this Program belongs to',
  `HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Health Area this Program belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `PROGRAM_MANAGER_USER_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Captures the person that is responsible for this Program',
  `PROGRAM_NOTES` TEXT NULL COMMENT 'Program notes',
  `AIR_FREIGHT_PERC` DECIMAL(12,2) NULL COMMENT 'Percentage of Order Qty when Mode = Air',
  `SEA_FREIGHT_PERC` DECIMAL(12,2) NOT NULL COMMENT 'Percentage of Order Qty when Mode = Sea',
  `PLANNED_TO_DRAFT_LEAD_TIME` INT(10) NOT NULL COMMENT 'No of days for an Order to move from Planed to Draft status',
  `DRAFT_TO_SUBMITTED_LEAD_TIME` INT(10) NOT NULL COMMENT 'No of days for an Order to move from Draft to Submitted status',
  `SUBMITTED_TO_APPROVED_LEAD_TIME` INT(10) NOT NULL COMMENT 'No of days for an Order to move from Submitted to Approved status, this will be used only in the case the Procurement Agent is TBD',
  `APPROVED_TO_SHIPPED_LEAD_TIME` INT(10) NOT NULL,
  `DELIVERED_TO_RECEIVED_LEAD_TIME` INT(10) NOT NULL COMMENT 'No of days for an Order to move from Delivered to Received status',
  `MONTHS_IN_PAST_FOR_AMC` INT(10) NOT NULL COMMENT 'No of months that we should go back in the past to calculate AMC',
  `MONTHS_IN_FUTURE_FOR_AMC` INT(10) NOT NULL COMMENT 'No of months that we should go into the future to calculate AMC',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Program is Active. False indicates this Program is De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Create  by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, this will be used only in the case the Procurement Agent is TBD',
  PRIMARY KEY (`PROGRAM_ID`),
  CONSTRAINT `fk_program_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_organisationId`
    FOREIGN KEY (`ORGANISATION_ID`)
    REFERENCES `fasp`.`rm_organisation` (`ORGANISATION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_healthAreaId`
    FOREIGN KEY (`HEALTH_AREA_ID`)
    REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the program for each product category\nNote: A Program can only be created and administered by a Realm Admin';

CREATE INDEX `fk_program_labelId_idx` ON `fasp`.`rm_program` (`LABEL_ID` ASC);

CREATE INDEX `fk_program_createdBy_idx` ON `fasp`.`rm_program` (`CREATED_BY` ASC);

CREATE INDEX `fk_program_lastModifiedBy_idx` ON `fasp`.`rm_program` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_program_organisationId_idx` ON `fasp`.`rm_program` (`ORGANISATION_ID` ASC);

CREATE INDEX `fk_program_healthAreaId_idx` ON `fasp`.`rm_program` (`HEALTH_AREA_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_region`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_region` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_region` (
  `REGION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Level',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm and Country this Level belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CAPACITY_CBM` DECIMAL(14,4) NULL COMMENT 'Cuibic meters of Warehouse capacity, not a compulsory field',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Level is Active. False indicates this Level has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`REGION_ID`),
  CONSTRAINT `fk_level_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_level_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_level_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_level_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the region for a Country for that Realm\nNote: Regions are Country - Realm specific and can only be created or administered by a Real admin';

CREATE INDEX `fk_level_realmCountryId_idx` ON `fasp`.`rm_region` (`REALM_COUNTRY_ID` ASC);

CREATE INDEX `fk_level_labelId_idx` ON `fasp`.`rm_region` (`LABEL_ID` ASC);

CREATE INDEX `fk_level_createdBy_idx` ON `fasp`.`rm_region` (`CREATED_BY` ASC);

CREATE INDEX `fk_level_lastModifiedBy_idx` ON `fasp`.`rm_region` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_role` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_role` (
  `ROLE_ID` VARCHAR(30) NOT NULL COMMENT 'Unique Role Id for every Role in the application',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`ROLE_ID`),
  CONSTRAINT `fk_role_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used for lisiting all the Roles that users can use in the application';

CREATE INDEX `fk_role_lastModifiedBy_idx` ON `fasp`.`us_role` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_role_labelId_idx` ON `fasp`.`us_role` (`LABEL_ID` ASC);

CREATE INDEX `fk_role_createdBy_idx` ON `fasp`.`us_role` (`CREATED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_business_function`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_business_function` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_business_function` (
  `BUSINESS_FUNCTION_ID` VARCHAR(50) NOT NULL COMMENT 'Unique Business function Id for every Business functoin in the application',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`BUSINESS_FUNCTION_ID`),
  CONSTRAINT `fk_business_function_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_business_function_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_business_function_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used for lisiting all the Business Functions that are available in the application';

CREATE INDEX `fk_business_function_labelId_idx` ON `fasp`.`us_business_function` (`LABEL_ID` ASC);

CREATE INDEX `fk_business_function_createdBy_idx` ON `fasp`.`us_business_function` (`CREATED_BY` ASC);

CREATE INDEX `fk_business_function_lastModifiedBy_idx` ON `fasp`.`us_business_function` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_user_role` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_user_role` (
  `USER_ROLE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique User-Role mapping id',
  `USER_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foriegn key for the User Id',
  `ROLE_ID` VARCHAR(30) NOT NULL COMMENT 'Foriegn key for the Role Id',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`USER_ROLE_ID`),
  CONSTRAINT `fk_user_role_userId`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_roleId`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `fasp`.`us_role` (`ROLE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_lastModifeidBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'User Role mapping table\nNote: Mapping table for User and Roles. While we expect one User will have only one Role it is possible that the User can have multiple Roles.';

CREATE INDEX `fk_user_role_userId_idx` ON `fasp`.`us_user_role` (`USER_ID` ASC);

CREATE INDEX `fk_user_role_roleId_idx` ON `fasp`.`us_user_role` (`ROLE_ID` ASC);

CREATE INDEX `fk_user_role_createdBy_idx` ON `fasp`.`us_user_role` (`CREATED_BY` ASC);

CREATE INDEX `fk_user_role_lastModifiedBy_idx` ON `fasp`.`us_user_role` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_role_business_function`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_role_business_function` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_role_business_function` (
  `ROLE_BUSINESS_FUNCTION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ROLE_ID` VARCHAR(30) NOT NULL,
  `BUSINESS_FUNCTION_ID` VARCHAR(50) NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`ROLE_BUSINESS_FUNCTION_ID`),
  CONSTRAINT `fk_role_business_function_roleId`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `fasp`.`us_role` (`ROLE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_business_function_businessFunctionId`
    FOREIGN KEY (`BUSINESS_FUNCTION_ID`)
    REFERENCES `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_business_function_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_business_function_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Role and Business function map\nNote: Mapping table for Role and Business function';

CREATE INDEX `fk_role_business_function_roleId_idx` ON `fasp`.`us_role_business_function` (`ROLE_ID` ASC);

CREATE INDEX `fk_role_business_function_businessFunctionId_idx` ON `fasp`.`us_role_business_function` (`BUSINESS_FUNCTION_ID` ASC);

CREATE INDEX `fk_role_business_function_createdBy_idx` ON `fasp`.`us_role_business_function` (`CREATED_BY` ASC);

CREATE INDEX `fk_role_business_function_lastModifiedBy_idx` ON `fasp`.`us_role_business_function` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `uqRoleIdBusinessFunctionId` ON `fasp`.`us_role_business_function` (`ROLE_ID` ASC, `BUSINESS_FUNCTION_ID` ASC)  COMMENT 'Unique key for Role Id and Business Function Id combination';


-- -----------------------------------------------------
-- Table `fasp`.`us_user_acl`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_user_acl` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_user_acl` (
  `USER_ACL_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique User Access Control List Id',
  `USER_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foriegn key for the User Id',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NULL COMMENT 'Foriegn key for the Country. If this is null it indicates the user has access to all the Countries',
  `HEALTH_AREA_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key for the Health Area. If this is null it indicates the user has access to all the Health Areas',
  `ORGANISATION_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key for the Organisation. If this is null it indicates the user has access to all the Organisations.',
  `PROGRAM_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Foreign key for the Programs. If this is null it indicates the user has access to all the Programs.',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`USER_ACL_ID`),
  CONSTRAINT `fk_user_acl_userId`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_acl_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_acl_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_acl_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_acl_healthAreaId`
    FOREIGN KEY (`HEALTH_AREA_ID`)
    REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_acl_organisationId`
    FOREIGN KEY (`ORGANISATION_ID`)
    REFERENCES `fasp`.`rm_organisation` (`ORGANISATION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the Access control lists for the application\nNote: Multiple rows for each user. Each row indicates what he has access to.';

CREATE INDEX `fk_user_acl_userId_idx` ON `fasp`.`us_user_acl` (`USER_ID` ASC);

CREATE INDEX `fk_user_acl_createdBy_idx` ON `fasp`.`us_user_acl` (`CREATED_BY` ASC);

CREATE INDEX `fk_user_acl_lastModifiedBy_idx` ON `fasp`.`us_user_acl` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_user_acl_realmCountryId_idx` ON `fasp`.`us_user_acl` (`REALM_COUNTRY_ID` ASC);

CREATE INDEX `fk_user_acl_healthAreaId_idx` ON `fasp`.`us_user_acl` (`HEALTH_AREA_ID` ASC);

CREATE INDEX `fk_user_acl_organisationId_idx` ON `fasp`.`us_user_acl` (`ORGANISATION_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_data_source_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_data_source_type` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_data_source_type` (
  `DATA_SOURCE_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for Data Source Type',
  `REALM_ID` INT(10) UNSIGNED NOT NULL,
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by	',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`DATA_SOURCE_TYPE_ID`),
  CONSTRAINT `fk_data_source_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_data_source_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_data_source_type_rm_realm1`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Super Classification of data_source\nNote: There are 3 DataSourceTypes Inventory, Shipment and Consumption';

CREATE INDEX `fk_data_source_type_createdBy_idx` ON `fasp`.`rm_data_source_type` (`CREATED_BY` ASC);

CREATE INDEX `fk_data_source_type_lastModifiedBy_idx` ON `fasp`.`rm_data_source_type` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_rm_data_source_type_rm_realm1_idx` ON `fasp`.`rm_data_source_type` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_data_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_data_source` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_data_source` (
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Data source ',
  `REALM_ID` INT(10) UNSIGNED NOT NULL,
  `DATA_SOURCE_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key for Data Source Type Id',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` VARCHAR(50) NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`DATA_SOURCE_ID`),
  CONSTRAINT `fk_data_source_dataSourceTypeId`
    FOREIGN KEY (`DATA_SOURCE_TYPE_ID`)
    REFERENCES `fasp`.`rm_data_source_type` (`DATA_SOURCE_TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_data_source_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_data_source_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_data_source_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_data_source_rm_realm1`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table to capture the Data Source\nNote: To be used in Shipments, Inventory and Consumption tables to identify where the data came from. Application level field so can only be administered by Application Admin';

CREATE INDEX `fk_data_source_dataSourceTypeId_idx` ON `fasp`.`rm_data_source` (`DATA_SOURCE_TYPE_ID` ASC);

CREATE INDEX `fk_data_source_createdBy_idx` ON `fasp`.`rm_data_source` (`CREATED_BY` ASC);

CREATE INDEX `fk_data_source_lastModifiedBy_idx` ON `fasp`.`rm_data_source` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_data_source_labelId_idx` ON `fasp`.`rm_data_source` (`LABEL_ID` ASC);

CREATE INDEX `fk_ap_data_source_rm_realm1_idx` ON `fasp`.`rm_data_source` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_shipment_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_shipment_status` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_shipment_status` (
  `SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Shipment Status',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SHIPMENT_STATUS_ID`),
  CONSTRAINT `fk_shipment_status_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_status_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_status_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store which Shipping status is logically allowed';

CREATE INDEX `fk_shipment_status_createdBy_idx` ON `fasp`.`ap_shipment_status` (`CREATED_BY` ASC);

CREATE INDEX `fk_shipment_status_lastModifiedBy_idx` ON `fasp`.`ap_shipment_status` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_shipment_status_labelId_idx` ON `fasp`.`ap_shipment_status` (`LABEL_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_shipment_status_allowed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_shipment_status_allowed` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_shipment_status_allowed` (
  `SHIPMENT_STATUS_ALLOWED_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Level - Program mapping',
  `SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Ship status Id',
  `NEXT_SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Next allowed Shipment Status Id',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SHIPMENT_STATUS_ALLOWED_ID`),
  CONSTRAINT `fk_shipment_status_allowed_shipmentStatusId`
    FOREIGN KEY (`SHIPMENT_STATUS_ID`)
    REFERENCES `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_status_allowed_nextShipmentStatusId`
    FOREIGN KEY (`NEXT_SHIPMENT_STATUS_ID`)
    REFERENCES `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_status_allowed_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_status_allowed_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_shipment_status_allowed_shipmentStatusId_idx` ON `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ID` ASC);

CREATE INDEX `fk_shipment_status_allowed_nextShipmentStatusId_idx` ON `fasp`.`ap_shipment_status_allowed` (`NEXT_SHIPMENT_STATUS_ID` ASC);

CREATE INDEX `fk_shipment_status_allowed_createdBy_idx` ON `fasp`.`ap_shipment_status_allowed` (`CREATED_BY` ASC);

CREATE INDEX `fk_shipment_status_allowed_lastModifiedBy_idx` ON `fasp`.`ap_shipment_status_allowed` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_supplier` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_supplier` (
  `SUPPLIER_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Manufacturer',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm this Manufacturer belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SUPPLIER_ID`),
  CONSTRAINT `fk_supplier_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the manufacturers for each Realm\nNote: A Supplier can only be created and administered by a Realm Admin';

CREATE INDEX `fk_supplier_realmId_idx` ON `fasp`.`rm_supplier` (`REALM_ID` ASC);

CREATE INDEX `fk_supplier_labelId_idx` ON `fasp`.`rm_supplier` (`LABEL_ID` ASC);

CREATE INDEX `fk_supplier_createdBy_idx` ON `fasp`.`rm_supplier` (`CREATED_BY` ASC);

CREATE INDEX `fk_supplier_lastModifiedBy_idx` ON `fasp`.`rm_supplier` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_organisation_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_organisation_country` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_organisation_country` (
  `ORGANISATION_COUNTRY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Organisation - Realm country mapping',
  `ORGANISATION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Organisation this mapping belongs to',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this mapping belongs to',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Organisation is Active. False indicates this Organisation has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`ORGANISATION_COUNTRY_ID`),
  CONSTRAINT `fk_organisation_country_organisationId`
    FOREIGN KEY (`ORGANISATION_ID`)
    REFERENCES `fasp`.`rm_organisation` (`ORGANISATION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_country_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_country_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_country_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map which organisations are available for which country\nNote: This is to be administered at the Realm level only by a Realm Admin';

CREATE INDEX `fk_organisation_country_organisationId_idx` ON `fasp`.`rm_organisation_country` (`ORGANISATION_ID` ASC);

CREATE INDEX `fk_organisation_country_realmCountryId_idx` ON `fasp`.`rm_organisation_country` (`REALM_COUNTRY_ID` ASC);

CREATE INDEX `fk_organisation_country_createdBy_idx` ON `fasp`.`rm_organisation_country` (`CREATED_BY` ASC);

CREATE INDEX `fk_organisation_country_lastModifiedBy_idx` ON `fasp`.`rm_organisation_country` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqOrganisationIdCountryId` ON `fasp`.`rm_organisation_country` (`ORGANISATION_ID` ASC, `REALM_COUNTRY_ID` ASC)  COMMENT 'Uniqe key for an Organisation - Country mapping';


-- -----------------------------------------------------
-- Table `fasp`.`rm_health_area_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_health_area_country` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_health_area_country` (
  `HEALTH_AREA_COUNTRY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Health Area - Country mapping',
  `HEALTH_AREA_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Health Area this mapping belongs to',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this mapping belongs to',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this mapping is Active. False indicates this Health-Area Country has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`HEALTH_AREA_COUNTRY_ID`),
  CONSTRAINT `fk_health_area_country_healthAreaId`
    FOREIGN KEY (`HEALTH_AREA_ID`)
    REFERENCES `fasp`.`rm_health_area` (`HEALTH_AREA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_health_area_country_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_health_area_country_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_health_area_country_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map which health_areas are available for which country\nNote: This is to be administered at the Realm level only by a Realm Admin';

CREATE INDEX `fk_health_area_country_healthAreaId_idx` ON `fasp`.`rm_health_area_country` (`HEALTH_AREA_ID` ASC);

CREATE INDEX `fk_health_area_country_realmCountryId_idx` ON `fasp`.`rm_health_area_country` (`REALM_COUNTRY_ID` ASC);

CREATE INDEX `fk_health_area_country_createdBy_idx` ON `fasp`.`rm_health_area_country` (`CREATED_BY` ASC);

CREATE INDEX `fk_health_area_country_lastModifiedBy_idx` ON `fasp`.`rm_health_area_country` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqHealthAreaIdCountryId` ON `fasp`.`rm_health_area_country` (`HEALTH_AREA_ID` ASC, `REALM_COUNTRY_ID` ASC)  COMMENT 'Unique key to map Health area and Country mapping';


-- -----------------------------------------------------
-- Table `fasp`.`rm_program_region`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_program_region` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_program_region` (
  `PROGRAM_REGION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Region - Program mapping',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Program this Mapping belongs to',
  `REGION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Region this Mapping belongs to',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Mapping is Active. False indicates this Mapping has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROGRAM_REGION_ID`),
  CONSTRAINT `fk_program_region_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_region_regionId`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_region_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_region_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map which Regions are used by a Program\nNote: Show the list of all Regions for that Country and let the Realm Admin select which Regions he wants to include in the Program. One Region for a Program is compulsory';

CREATE INDEX `fk_program_region_programId_idx` ON `fasp`.`rm_program_region` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_program_region_regionId_idx` ON `fasp`.`rm_program_region` (`REGION_ID` ASC);

CREATE INDEX `fk_program_region_createdBy_idx` ON `fasp`.`rm_program_region` (`CREATED_BY` ASC);

CREATE INDEX `fk_program_region_lastModifiedBy_idx` ON `fasp`.`rm_program_region` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqProgramIdRegionId` ON `fasp`.`rm_program_region` (`PROGRAM_ID` ASC, `REGION_ID` ASC)  COMMENT 'Unique mapping for Program and Region mapping';


-- -----------------------------------------------------
-- Table `fasp`.`rm_funding_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_funding_source` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_funding_source` (
  `FUNDING_SOURCE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Funding source',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realm this Funding Source belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`FUNDING_SOURCE_ID`),
  CONSTRAINT `fk_funding_source_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funding_source_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funding_source_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funding_source_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_funding_source_realmId_idx` ON `fasp`.`rm_funding_source` (`REALM_ID` ASC);

CREATE INDEX `fk_funding_source_labelId_idx` ON `fasp`.`rm_funding_source` (`LABEL_ID` ASC);

CREATE INDEX `fk_funding_source_createdBy_idx` ON `fasp`.`rm_funding_source` (`CREATED_BY` ASC);

CREATE INDEX `fk_funding_source_lastModifiedBy_idx` ON `fasp`.`rm_funding_source` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_sub_funding_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_sub_funding_source` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_sub_funding_source` (
  `SUB_FUNDING_SOURCE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Sub-funding source mapping',
  `FUNDING_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Funding Source this Sub Funding Source belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Sub Funding Source is Active. False indicates this Sub Funding Source has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SUB_FUNDING_SOURCE_ID`),
  CONSTRAINT `fk_sub_funding_source_fundingSourceId`
    FOREIGN KEY (`FUNDING_SOURCE_ID`)
    REFERENCES `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub_funding_source_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub_funding_source_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub_funding_source_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the sub_funding_sources for a Realm					\nNote: Are based on a Realm and can be created by a Realm level admin through a Ticket request';

CREATE INDEX `fk_sub_funding_source_fundingSourceId_idx` ON `fasp`.`rm_sub_funding_source` (`FUNDING_SOURCE_ID` ASC);

CREATE INDEX `fk_sub_funding_source_labelId_idx` ON `fasp`.`rm_sub_funding_source` (`LABEL_ID` ASC);

CREATE INDEX `fk_sub_funding_source_createdBy_idx` ON `fasp`.`rm_sub_funding_source` (`CREATED_BY` ASC);

CREATE INDEX `fk_sub_funding_source_lastModifiedBy_idx` ON `fasp`.`rm_sub_funding_source` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_budget`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_budget` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_budget` (
  `BUDGET_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Budget',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Program that this budget is for',
  `SUB_FUNDING_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Sub Funding Source this Budget belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `BUDGET_AMT` DECIMAL(14,4) UNSIGNED NOT NULL COMMENT 'The total Budget amt approved for this Budget',
  `START_DATE` DATE NOT NULL COMMENT 'Start date for the Budget',
  `STOP_DATE` DATE NOT NULL COMMENT 'Stop date for the Budget',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Budget is Active. False indicates this Budget has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`BUDGET_ID`),
  CONSTRAINT `fk_budget_subFundingSourceId`
    FOREIGN KEY (`SUB_FUNDING_SOURCE_ID`)
    REFERENCES `fasp`.`rm_sub_funding_source` (`SUB_FUNDING_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budget_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budget_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budget_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budget_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the budgets for a Sub-Funding Source\nNote: Are based on a Realm and can be created by a Realm level admin through a Ticket request';

CREATE INDEX `fk_budget_sub_funding_sourceId1_idx` ON `fasp`.`rm_budget` (`SUB_FUNDING_SOURCE_ID` ASC);

CREATE INDEX `fk_budget_programId1_idx` ON `fasp`.`rm_budget` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_budget_labelId1_idx` ON `fasp`.`rm_budget` (`LABEL_ID` ASC);

CREATE INDEX `fk_budget_userId1_idx` ON `fasp`.`rm_budget` (`CREATED_BY` ASC);

CREATE INDEX `fk_budget_userId2_idx` ON `fasp`.`rm_budget` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_procurement_agent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_procurement_agent` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_procurement_agent` (
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Procurement agent',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realm this Procurement Agent belongs to',
  `PROCUREMENT_AGENT_CODE` VARCHAR(6) NULL COMMENT 'Foreign key that determines the Program this Procurement Agent belongs to. If it is null then the record is a Realm level Procurement Agent. If it is not null then it is a Program level Procurement Agent.',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `SUBMITTED_TO_APPROVED_LEAD_TIME` INT(10) UNSIGNED NOT NULL COMMENT 'No of days for an Order to move from Submitted to Approved status, this will be used only in the case the Procurement Agent is TBD',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Procurement Agent is Active. False indicates this Procurement Agent has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, this will be used only in the case the Procurement Agent is TBD',
  PRIMARY KEY (`PROCUREMENT_AGENT_ID`),
  CONSTRAINT `fk_procurement_agent_realmId`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_agent_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_agent_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_agent_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the procurement_agents for a Realm\nNote: Are based on a Realm and can be created by a Realm level admin through a Ticket request, you can also have Program level procurement agent which are also created and administered by the Program Admin via a Ticket request';

CREATE INDEX `fk_procurement_agent_realmId_idx` ON `fasp`.`rm_procurement_agent` (`REALM_ID` ASC);

CREATE INDEX `fk_procurement_agent_labelId_idx` ON `fasp`.`rm_procurement_agent` (`LABEL_ID` ASC);

CREATE INDEX `fk_procurement_agent_createdBy_idx` ON `fasp`.`rm_procurement_agent` (`CREATED_BY` ASC);

CREATE INDEX `fk_procurement_agent_lastModifiedBy_idx` ON `fasp`.`rm_procurement_agent` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `un_procurementAgentCode` ON `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_CODE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_tracer_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_tracer_category` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_tracer_category` (
  `TRACER_CATEGORY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product Category',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Realm that this Product Category belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Product Category is Active. False indicates this Product Category has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by\n',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TRACER_CATEGORY_ID`),
  CONSTRAINT `fk_tracer_category_labelId0`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tracer_category_createdBy0`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tracer_category_lastModifiedBy0`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_tracer_category_realmId0`
    FOREIGN KEY (`REALM_ID`)
    REFERENCES `fasp`.`rm_realm` (`REALM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to define the product_categories for each health_area\nNote: A Product category can only be created and administered by a Realm Admin';

CREATE INDEX `fk_tracer_category_labelId_idx` ON `fasp`.`rm_tracer_category` (`LABEL_ID` ASC);

CREATE INDEX `fk_tracer_category_createdBy_idx` ON `fasp`.`rm_tracer_category` (`CREATED_BY` ASC);

CREATE INDEX `fk_tracer_category_lastModifiedBy_idx` ON `fasp`.`rm_tracer_category` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_rm_tracer_category_realmId_idx` ON `fasp`.`rm_tracer_category` (`REALM_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_forecasting_unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_forecasting_unit` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_forecasting_unit` (
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Product',
  `REALM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realm this Product belongs to',
  `PRODUCT_CATEGORY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Product Category for this product',
  `TRACER_CATEGORY_ID` INT(10) UNSIGNED NULL,
  `GENERIC_LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Generic name for the Product, also called the INN',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Product is Active. False indicates this Product has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created date',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`FORECASTING_UNIT_ID`),
  CONSTRAINT `fk_forecastingUnit_genericLabelId`
    FOREIGN KEY (`GENERIC_LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastingUnit_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastingUnit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastingUnit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_forecastingUnit_productCategoryId`
    FOREIGN KEY (`PRODUCT_CATEGORY_ID`)
    REFERENCES `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_forecasting_unit_rm_tracer_category1`
    FOREIGN KEY (`TRACER_CATEGORY_ID`)
    REFERENCES `fasp`.`rm_tracer_category` (`TRACER_CATEGORY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the Products\nNote: Are based on a Realm and can be created or administered by a Realm Admin';

CREATE INDEX `fk_forecastingUnit_genericLabelId_idx` ON `fasp`.`rm_forecasting_unit` (`GENERIC_LABEL_ID` ASC);

CREATE INDEX `fk_forecastingUnit_labelId_idx` ON `fasp`.`rm_forecasting_unit` (`LABEL_ID` ASC);

CREATE INDEX `fk_forecastingUnit_createdBy_idx` ON `fasp`.`rm_forecasting_unit` (`CREATED_BY` ASC);

CREATE INDEX `fk_forecastingUnit_lastModifedBy_idx` ON `fasp`.`rm_forecasting_unit` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_forecastingUnit_productCategoryId_idx` ON `fasp`.`rm_forecasting_unit` (`PRODUCT_CATEGORY_ID` ASC);

CREATE INDEX `fk_rm_forecasting_unit_rm_tracer_category1_idx` ON `fasp`.`rm_forecasting_unit` (`TRACER_CATEGORY_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_dimension`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_dimension` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_dimension` (
  `DIMENSION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `LABEL_ID` INT(10) UNSIGNED NOT NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`DIMENSION_ID`),
  CONSTRAINT `fk_dimension_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_dimension_us_user1`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_dimension_us_user2`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Dimensions of measure used across the Application\nNote: Are based on the Application and can only be administered by an Applicaton level Admin. For e.g. Length, Weight, Volume, Area, Items etc';

CREATE INDEX `fk_dimension_labelId_idx` ON `fasp`.`ap_dimension` (`LABEL_ID` ASC);

CREATE INDEX `fk_ap_dimension_us_user1_idx` ON `fasp`.`ap_dimension` (`CREATED_BY` ASC);

CREATE INDEX `fk_ap_dimension_us_user2_idx` ON `fasp`.`ap_dimension` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`ap_unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_unit` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_unit` (
  `UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Unit of measure',
  `DIMENSION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that points to what type of Unit of measure this is ',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_CODE` VARCHAR(20) NOT NULL COMMENT 'Notification for this Unit of measure',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`UNIT_ID`),
  CONSTRAINT `fk_unit_dimension`
    FOREIGN KEY (`DIMENSION_ID`)
    REFERENCES `fasp`.`ap_dimension` (`DIMENSION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the different Units of measure used across the Application\nNote: Are based on the Application and can only be administered by an Applicaton level Admin';

CREATE INDEX `fk_unit_dimension_idx` ON `fasp`.`ap_unit` (`DIMENSION_ID` ASC);

CREATE INDEX `fk_unit_createdBy_idx` ON `fasp`.`ap_unit` (`CREATED_BY` ASC);

CREATE INDEX `fk_unit_lastModifiedBy_idx` ON `fasp`.`ap_unit` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `fk_unit_unitCode` ON `fasp`.`ap_unit` (`UNIT_CODE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_planning_unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_planning_unit` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_planning_unit` (
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Planning unit',
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key for the Product that this Planning unit represents',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Unit of measure for this Planning unit',
  `MULTIPLIER` DECIMAL(12,2) NOT NULL COMMENT 'Quantity of items in this unit versus the Forecasting Unit Id',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this Funding Source is Active. False indicates this Funding Source has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PLANNING_UNIT_ID`),
  CONSTRAINT `fk_planning_unit_forecastingUnitId`
    FOREIGN KEY (`FORECASTING_UNIT_ID`)
    REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_unitId`
    FOREIGN KEY (`UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the Planning units that will be used to map a Product to a Program\nNote: Units are Realm level master and can only be Administered by a Realm level admin';

CREATE INDEX `fk_planning_unit_forecastingUnit_idx` ON `fasp`.`rm_planning_unit` (`FORECASTING_UNIT_ID` ASC);

CREATE INDEX `fk_planning_unit_labelId_idx` ON `fasp`.`rm_planning_unit` (`LABEL_ID` ASC);

CREATE INDEX `fk_planning_unit_unitId_idx` ON `fasp`.`rm_planning_unit` (`UNIT_ID` ASC);

CREATE INDEX `fk_planning_unit_createdBy_idx` ON `fasp`.`rm_planning_unit` (`CREATED_BY` ASC)  COMMENT '\n';

CREATE INDEX `fk_planning_unit_lastModifiedBy_idx` ON `fasp`.`rm_planning_unit` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_procurement_unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_procurement_unit` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_procurement_unit` (
  `PROCUREMENT_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Logistics unit',
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to point which Planning unit this maps to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Unit of measure for this sku',
  `MULTIPLIER` DECIMAL(12,2) UNSIGNED NOT NULL COMMENT 'Quantity of items in this unit as per the Forecasting Unit Id',
  `SUPPLIER_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key to point which Manufacturer this Logistics unit is from',
  `WIDTH_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Unit Id for a the Width of the Logistics Unit',
  `WIDTH_QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Width',
  `HEIGHT_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Unit Id for a the Height of the Logistics Unit',
  `HEIGHT_QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Height',
  `LENGTH_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Unit Id for a the Length of the Logistics Unit',
  `LENGTH_QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Length',
  `WEIGHT_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Unit Id for a the Weight of the Logistics Unit',
  `WEIGHT_QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Weight',
  `UNITS_PER_CONTAINER` DECIMAL(12,2) UNSIGNED NULL COMMENT 'No of Forecast units that fit in a Euro1 pallet',
  `LABELING` VARCHAR(200) NULL COMMENT 'No of Forecast units that fit in a Euro2 pallet',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Logistics unit is Active. False indicates this Logistics unit has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Create by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROCUREMENT_UNIT_ID`),
  CONSTRAINT `fk_procurement_unit_planningUnitId`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_unitId`
    FOREIGN KEY (`UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_widthUnitId`
    FOREIGN KEY (`WIDTH_UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_heightUnitId`
    FOREIGN KEY (`HEIGHT_UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_lengthUnitId`
    FOREIGN KEY (`LENGTH_UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_weightUnitId`
    FOREIGN KEY (`WEIGHT_UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_supplierId`
    FOREIGN KEY (`SUPPLIER_ID`)
    REFERENCES `fasp`.`rm_supplier` (`SUPPLIER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the SKU\'s for a Product					\nNote: Are based on a Planning unit and can be created or administered by a Realm Admin';

CREATE INDEX `fk_procurement_unit_planningUnitId_idx` ON `fasp`.`rm_procurement_unit` (`PLANNING_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_labelId_idx` ON `fasp`.`rm_procurement_unit` (`LABEL_ID` ASC);

CREATE INDEX `fk_procurement_unit_unitId_idx` ON `fasp`.`rm_procurement_unit` (`UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_widthUnitId_idx` ON `fasp`.`rm_procurement_unit` (`WIDTH_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_heightUnitId_idx` ON `fasp`.`rm_procurement_unit` (`HEIGHT_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_lengthUnitId_idx` ON `fasp`.`rm_procurement_unit` (`LENGTH_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_weightUnitId_idx` ON `fasp`.`rm_procurement_unit` (`WEIGHT_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_createdBy_idx` ON `fasp`.`rm_procurement_unit` (`CREATED_BY` ASC);

CREATE INDEX `fk_procurement_unit_lastModifiedBy_idx` ON `fasp`.`rm_procurement_unit` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_procurement_unit_supplierId_idx` ON `fasp`.`rm_procurement_unit` (`SUPPLIER_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_procurement_unit_procurement_agent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_procurement_unit_procurement_agent` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_procurement_unit_procurement_agent` (
  `PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Procurement Agent SKU',
  `PROCUREMENT_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Logistics Unit Id this Procurement Sku maps to',
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Procuremnt Agent this Logistics Unit belongs to',
  `SKU_CODE` VARCHAR(50) NOT NULL COMMENT 'The Sku code that the Procurement agent refers to this Sku as',
  `VENDOR_PRICE` DECIMAL(14,4) NOT NULL COMMENT 'Price that this Procurement agent is purchasing this Logistics Unit at. If null then default to the price set in for the Planning Unit',
  `APPROVED_TO_SHIPPED_LEAD_TIME` INT(10) NOT NULL COMMENT 'No of days for an Order to move from Approved to Shipped, if we do not have this then take from SKU table',
  `GTIN` VARCHAR(45) NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Procurement Sku mapping is Active. False indicates this Procurement Sku has been De-activated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`),
  CONSTRAINT `fk_procurement_unit_procurement_agent_procurementAgentId`
    FOREIGN KEY (`PROCUREMENT_AGENT_ID`)
    REFERENCES `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_procurement_agent_procurementUnitId`
    FOREIGN KEY (`PROCUREMENT_UNIT_ID`)
    REFERENCES `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_procurement_agent_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_procurement_unit_procurement_agent_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map the Procurement Agent Sku to Procuremnt Unit\nNote: Can be administered at the Realm admin';

CREATE INDEX `fk_procurement_unit_procurement_agent_procurementAgentId_idx` ON `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_ID` ASC);

CREATE INDEX `fk_procurement_unit_procurement_agent_procurementUnitId_idx` ON `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_UNIT_ID` ASC);

CREATE INDEX `fk_procurement_unit_procurement_agent_createdBy_idx` ON `fasp`.`rm_procurement_unit_procurement_agent` (`CREATED_BY` ASC);

CREATE INDEX `fk_procurement_unit_procurement_agent_lastModifiedBy_idx` ON `fasp`.`rm_procurement_unit_procurement_agent` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqProcurementUnitProcurementAgentId` ON `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_ID` ASC, `PROCUREMENT_UNIT_ID` ASC)  COMMENT 'Unique mapping for Procurement agent and Logistics Unit Id';


-- -----------------------------------------------------
-- Table `fasp`.`rm_planning_unit_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_planning_unit_country` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_planning_unit_country` (
  `PLANNING_UNIT_COUNTRTY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Country level SKU',
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Planning unit Id this Country Sku maps to',
  `REALM_COUNTRY_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that determines the Realm-Country this Sku belongs to',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL,
  `SKU_CODE` VARCHAR(50) NOT NULL COMMENT 'Code that the Country uses to identify the SKU',
  `UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'No of items of Planning Unit inside this Country Sku',
  `MULTIPLIER` DECIMAL(12,2) UNSIGNED NOT NULL,
  `GTIN` VARCHAR(45) NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Country Sku is Active. False indicates this Country Sku has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PLANNING_UNIT_COUNTRTY_ID`),
  CONSTRAINT `fk_planning_unit_country_realmCountryId`
    FOREIGN KEY (`REALM_COUNTRY_ID`)
    REFERENCES `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_country_planningUnitId`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_country_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_country_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_country_ap_unit1`
    FOREIGN KEY (`UNIT_ID`)
    REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map the Country Sku to Planning Unit\nNote: Can be administered at the Realm admin.';

CREATE INDEX `fk_planning_unit_country_realmCountryId_idx` ON `fasp`.`rm_planning_unit_country` (`REALM_COUNTRY_ID` ASC);

CREATE INDEX `fk_planning_unit_country_planningUnitId_idx` ON `fasp`.`rm_planning_unit_country` (`PLANNING_UNIT_ID` ASC);

CREATE INDEX `fk_planning_unit_country_createdBy_idx` ON `fasp`.`rm_planning_unit_country` (`CREATED_BY` ASC);

CREATE INDEX `fk_planning_unit_country_lastModifiedBy_idx` ON `fasp`.`rm_planning_unit_country` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqCountryPlanningUnitAndUnitId` ON `fasp`.`rm_planning_unit_country` (`REALM_COUNTRY_ID` ASC, `PLANNING_UNIT_ID` ASC)  COMMENT 'Unique mapping for Country - Planning Unit and Unit Id mapping';

CREATE INDEX `fk_rm_planning_unit_country_ap_unit1_idx` ON `fasp`.`rm_planning_unit_country` (`UNIT_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_planning_unit_capacity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_planning_unit_capacity` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_planning_unit_capacity` (
  `PLANNING_UNIT_CAPACITY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Logistics Unit Capacity Id',
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `SUPPLIER_ID` INT(10) UNSIGNED NOT NULL,
  `CAPACITY` DECIMAL(12,2) UNSIGNED NOT NULL COMMENT 'Global capacity level beyond which the manufacture cannot produce that GTIN',
  `START_DATE` DATE NOT NULL COMMENT 'Start period for the Capacity for this Logistics Unit',
  `STOP_DATE` DATE NOT NULL COMMENT 'Stop period for the Capacity for this Logistics Unit',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PLANNING_UNIT_CAPACITY_ID`),
  CONSTRAINT `fk_planning_unit_capacity_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planning_unit_capacity_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_capacity_rm_supplier1`
    FOREIGN KEY (`SUPPLIER_ID`)
    REFERENCES `fasp`.`rm_supplier` (`SUPPLIER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_capacity_rm_planning_unit1`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the Global capacity for each Planning unit\nNote: More than one record for each Planning unit, we need to check if the periods overlap an existing record and not allow a new entry if it does overlap.LOGISTICS_UNIT_ID';

CREATE INDEX `fk_planning_unit_capacity_createdBy_idx` ON `fasp`.`rm_planning_unit_capacity` (`CREATED_BY` ASC);

CREATE INDEX `fk_planning_unit_capacity_lastModifiedBy_idx` ON `fasp`.`rm_planning_unit_capacity` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_rm_planning_unit_capacity_rm_supplier1_idx` ON `fasp`.`rm_planning_unit_capacity` (`SUPPLIER_ID` ASC);

CREATE INDEX `fk_rm_planning_unit_capacity_rm_planning_unit1_idx` ON `fasp`.`rm_planning_unit_capacity` (`PLANNING_UNIT_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_program_forecasting_unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_program_forecasting_unit` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_program_forecasting_unit` (
  `PROGRAM_FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Program - Product',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key for the Program that this mapping refers to',
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key for the Planning Unit that this mapping refers to',
  `MIN_MONTHS` INT(10) UNSIGNED NOT NULL COMMENT 'Min number of months of stock that we should have before triggering a reorder',
  `MAX_MONTHS` INT(10) UNSIGNED NULL COMMENT 'Max number of months of stock that is recommended',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this mapping is Active. False indicates this mapping has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`PROGRAM_FORECASTING_UNIT_ID`),
  CONSTRAINT `fk_program_forecasting_unit_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_forecasting_unit_forecastingUnitId`
    FOREIGN KEY (`FORECASTING_UNIT_ID`)
    REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_forecasting_unit_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_program_forecasting_unit_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to map the ForecastingUnits inside a Program\nNote: Allow the user to select a Product Category and therefore then select all the ForecastingUnits that should be used in the Program';

CREATE INDEX `fk_program_forecasting_unit_programId_idx` ON `fasp`.`rm_program_forecasting_unit` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_program_forecasting_unit_forecastingUnitId_idx` ON `fasp`.`rm_program_forecasting_unit` (`FORECASTING_UNIT_ID` ASC);

CREATE INDEX `fk_program_forecasting_unit_createdBy_idx` ON `fasp`.`rm_program_forecasting_unit` (`CREATED_BY` ASC);

CREATE INDEX `fk_program_forecasting_unit_lastModifiedBy_idx` ON `fasp`.`rm_program_forecasting_unit` (`LAST_MODIFIED_BY` ASC);

CREATE UNIQUE INDEX `unqProgramIdForecastingUnitId` ON `fasp`.`rm_program_forecasting_unit` (`PROGRAM_ID` ASC, `FORECASTING_UNIT_ID` ASC)  COMMENT 'Unique mapping for Program - Product mapping';


-- -----------------------------------------------------
-- Table `fasp`.`rm_inventory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_inventory` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_inventory` (
  `INVENTORY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Inventory Id for each Inventory record',
  `INVENTORY_DATE` DATE NOT NULL COMMENT 'Date this Inventory record is for',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Program this record is for',
  `REGION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates the Region that this record is for',
  `LOGISTICS_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Logistics Id the Inventory is for',
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Planning Id this Inventory maps to',
  `PACK_SIZE` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Quantity of items in this unit versus the Forecasting Unit Id',
  `ACTUAL_QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Inventory could be in two ways actual count or adjustment to running balance. If Actual Qty is provided use that or else do a running balance on Adjustment Qty',
  `ADJUSTMENT_QTY` DECIMAL(12,2) NULL COMMENT 'Inventory could be in two ways actual count or adjustment to running balance. If Actual Qty is provided use that or else do a running balance on Adjustment Qty',
  `BATCH_NO` VARCHAR(25) NULL COMMENT 'Batch no of the record that the data is for, can only be provided when a full stock count is being taken',
  `EXPIRY_DATE` DATE NULL COMMENT 'Expiry date of that Batch, again can only be provided when a full stock count is being taken',
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Source of the Inventory',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`INVENTORY_ID`),
  CONSTRAINT `fk_inventory_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_regionId`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_logisticsUnitId`
    FOREIGN KEY (`LOGISTICS_UNIT_ID`)
    REFERENCES `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_planningUnitId`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_dataSourceId`
    FOREIGN KEY (`DATA_SOURCE_ID`)
    REFERENCES `fasp`.`rm_data_source` (`DATA_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the inventory of the Products\nNote: Could be manually fed or can come from feeds from eLMIS';

CREATE INDEX `fk_inventory_programId_idx` ON `fasp`.`rm_inventory` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_inventory_regionId_idx` ON `fasp`.`rm_inventory` (`REGION_ID` ASC);

CREATE INDEX `fk_inventory_logisticsUnitId_idx` ON `fasp`.`rm_inventory` (`LOGISTICS_UNIT_ID` ASC);

CREATE INDEX `fk_inventory_planningUnitId_idx` ON `fasp`.`rm_inventory` (`PLANNING_UNIT_ID` ASC);

CREATE INDEX `fk_inventory_dataSourceId_idx` ON `fasp`.`rm_inventory` (`DATA_SOURCE_ID` ASC);

CREATE INDEX `fk_inventory_createdBy_idx` ON `fasp`.`rm_inventory` (`CREATED_BY` ASC);

CREATE INDEX `fk_inventory_lastModifiedBy_idx` ON `fasp`.`rm_inventory` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_consumption`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_consumption` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_consumption` (
  `CONSUMPTION_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Consumption that is entered',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Program this record is for',
  `REGION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates the Region that this record is for',
  `LOGISTICS_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Logistics Id the Inventory is for',
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Planning Id this Consumption maps to',
  `PACK_SIZE` DECIMAL(12,2) NULL COMMENT 'Quantity of items in this unit versus the Forecasting Unit Id',
  `CONSUMPTION_QTY` DECIMAL(12,2) NOT NULL COMMENT 'Consumption qty',
  `START_DATE` DATE NOT NULL COMMENT 'Consumption start date',
  `STOP_DATE` DATE NOT NULL COMMENT 'Consumption stop date',
  `DAYS_OF_STOCK_OUT` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Days that we were out of stock in the particular Region for this Product',
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Source of the Consumption, Could be Forecasted or Actual',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`CONSUMPTION_ID`),
  CONSTRAINT `fk_consumption_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_regionId`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_planningUnitId`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_logisticsUnitId`
    FOREIGN KEY (`LOGISTICS_UNIT_ID`)
    REFERENCES `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_dataSourceId`
    FOREIGN KEY (`DATA_SOURCE_ID`)
    REFERENCES `fasp`.`rm_data_source` (`DATA_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumption_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list the Consumption of Products\nNote: Could be manually fed or can come from feeds from eLMIS';

CREATE INDEX `fk_consumption_programId_idx` ON `fasp`.`rm_consumption` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_consumption_regionId_idx` ON `fasp`.`rm_consumption` (`REGION_ID` ASC);

CREATE INDEX `fk_consumption_planningUnitId_idx` ON `fasp`.`rm_consumption` (`PLANNING_UNIT_ID` ASC);

CREATE INDEX `fk_consumption_logisticsUnitId_idx` ON `fasp`.`rm_consumption` (`LOGISTICS_UNIT_ID` ASC);

CREATE INDEX `fk_consumption_dataSourceId_idx` ON `fasp`.`rm_consumption` (`DATA_SOURCE_ID` ASC);

CREATE INDEX `fk_consumption_createdBy_idx` ON `fasp`.`rm_consumption` (`CREATED_BY` ASC);

CREATE INDEX `fk_consumption_lastModifiedBy_idx` ON `fasp`.`rm_consumption` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_shipment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_shipment` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_shipment` (
  `SHIPMENT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Shipment Id for each Shipment',
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Program this record is for',
  `REGION_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates the Region that this record is for',
  `PRODUCT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Product this record is for',
  `SUGGESTED_PLANNED_DATE` DATE NOT NULL COMMENT 'Date that the System is suggesting we need to Plan the shipment based on Lead times',
  `SUGGESTED_QTY` DECIMAL(12,2) UNSIGNED NOT NULL COMMENT 'Suggested qty for this Shipment, in terms of Forecasting unit',
  `LOGISTICS_UNIT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Logistics Id the shipment is for. This will be filled out once we have concluded on the order',
  `QTY` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Qty of Logistics Unit in the Shipment',
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Procurement Agent this shipment belongs to',
  `PO_RO_NUMBER` VARCHAR(50) NULL COMMENT 'PO / RO number for the shipment',
  `SHIPMENT_PRICE` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Final price of the Shipment for the Goods',
  `FREIGHT_PRICE` DECIMAL(12,2) UNSIGNED NULL COMMENT 'Cost of Freight for the Shipment',
  `ORDER_DATE` DATE NULL COMMENT 'Date the Order was Placed',
  `SHIP_DATE` DATE NULL COMMENT 'Date the Order was Shipped',
  `ARRIVE_DATE` DATE NULL COMMENT 'Date the Order arrived in the Country',
  `RECEIVE_DATE` DATE NULL COMMENT 'Date the Order was received into Inventory',
  `SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Shipment Status Id',
  `NOTES` TEXT(255) NOT NULL COMMENT 'Notes for this Shipment',
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Source of the Inventory',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last Modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last Modified date',
  PRIMARY KEY (`SHIPMENT_ID`),
  CONSTRAINT `fk_shipment_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_regionId`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_logisticsUnitId`
    FOREIGN KEY (`LOGISTICS_UNIT_ID`)
    REFERENCES `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_dataSourceId`
    FOREIGN KEY (`DATA_SOURCE_ID`)
    REFERENCES `fasp`.`rm_data_source` (`DATA_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_procurementAgentId`
    FOREIGN KEY (`PROCUREMENT_AGENT_ID`)
    REFERENCES `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_shipmentStatusId`
    FOREIGN KEY (`SHIPMENT_STATUS_ID`)
    REFERENCES `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to list all the Shipments\nNote: Complete Shipment dump, data can be manually fed or can come for ARTMIS or the Procurement Agent system';

CREATE INDEX `fk_shipment_programId_idx` ON `fasp`.`rm_shipment` (`PROGRAM_ID` ASC);

CREATE INDEX `fk_shipment_regionId_idx` ON `fasp`.`rm_shipment` (`REGION_ID` ASC);

CREATE INDEX `fk_shipment_logisticsUnitId_idx` ON `fasp`.`rm_shipment` (`LOGISTICS_UNIT_ID` ASC);

CREATE INDEX `fk_shipment_dataSourceId_idx` ON `fasp`.`rm_shipment` (`DATA_SOURCE_ID` ASC);

CREATE INDEX `fk_shipment_createdBy_idx` ON `fasp`.`rm_shipment` (`CREATED_BY` ASC);

CREATE INDEX `fk_shipment_lastModifiedBy_idx` ON `fasp`.`rm_shipment` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_shipment_procurementAgentId_idx` ON `fasp`.`rm_shipment` (`PROCUREMENT_AGENT_ID` ASC);

CREATE INDEX `fk_shipment_shipmentStatusId_idx` ON `fasp`.`rm_shipment` (`SHIPMENT_STATUS_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_shipment_budget`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_shipment_budget` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_shipment_budget` (
  `SHIPMENT_BUDGET_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Shipment Budget',
  `SHIPMENT_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Shipment that this record refers to ',
  `FUNDING_SOURCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that indicates which Funding source this shipment belongs to, cannot be null. If we do not know it will be TBD',
  `SUB_FUNDING_SOURCE_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Sub Funding source this shipment belongs to. Can be null initially and updated later',
  `BUDGET_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key that indicates which Budget this shipment belongs to. Can by null initially and updated later',
  `BUDGET_AMT` DECIMAL(12,2) UNSIGNED NOT NULL COMMENT 'Amt of Shipment cost taken from this record. total for a Shipment should match with the Shipment cost',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'If True indicates this Record is Active. False indicates this Record has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`SHIPMENT_BUDGET_ID`),
  CONSTRAINT `fk_shipment_budget_shipmentId`
    FOREIGN KEY (`SHIPMENT_ID`)
    REFERENCES `fasp`.`rm_shipment` (`SHIPMENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_budget_fundingSourceId`
    FOREIGN KEY (`FUNDING_SOURCE_ID`)
    REFERENCES `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_budget_subFundingSource`
    FOREIGN KEY (`SUB_FUNDING_SOURCE_ID`)
    REFERENCES `fasp`.`rm_sub_funding_source` (`SUB_FUNDING_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_budget_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipment_budget_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to track the Budget that the Shipment is being funded by\nNote: When a Shipment is planned at that time there could be just a simple TBD in the Funding Source and entire Shipment towards the Budget Amt, with everything else as null.';

CREATE INDEX `fk_shipment_budget_shipmentId_idx` ON `fasp`.`rm_shipment_budget` (`SHIPMENT_ID` ASC);

CREATE INDEX `fk_shipment_budget_fundingSourceId_idx` ON `fasp`.`rm_shipment_budget` (`FUNDING_SOURCE_ID` ASC);

CREATE INDEX `fk_shipment_budget_subFundingSourceId_idx` ON `fasp`.`rm_shipment_budget` (`SUB_FUNDING_SOURCE_ID` ASC);

CREATE INDEX `fk_shipment_budget_createdBy_idx` ON `fasp`.`rm_shipment_budget` (`CREATED_BY` ASC);

CREATE INDEX `fk_shipment_budget_lastModifiedBy_idx` ON `fasp`.`rm_shipment_budget` (`LAST_MODIFIED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`tk_ticket_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`tk_ticket_type` ;

CREATE TABLE IF NOT EXISTS `fasp`.`tk_ticket_type` (
  `TICKET_TYPE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket Type',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `TICKET_LEVEL` INT(10) UNSIGNED NOT NULL COMMENT 'The Level the ticket is for. 1 - Application level, 2 - Realm level, 3 - Program level',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this Ticket Type is Active. False indicates this Ticket Type has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_TYPE_ID`),
  CONSTRAINT `fk_ticket_type_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_type_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_type_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the Ticket types\nNote: The Level the ticket is for. 1 - Application level, 2 - Realm level, 3 - Program level';

CREATE INDEX `fk_ticket_type_createdBy_idx` ON `fasp`.`tk_ticket_type` (`CREATED_BY` ASC);

CREATE INDEX `fk_ticket_type_lastModifiedBy_idx` ON `fasp`.`tk_ticket_type` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_ticket_type_labelId_idx` ON `fasp`.`tk_ticket_type` (`LABEL_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`tk_ticket_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`tk_ticket_status` ;

CREATE TABLE IF NOT EXISTS `fasp`.`tk_ticket_status` (
  `TICKET_STATUS_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket Status	',
  `LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Label Id that points to the label table so that we can get the text in different languages',
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL COMMENT 'If True indicates this Ticket Status is Active. False indicates this Ticket Status has been Deactivated',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_STATUS_ID`),
  CONSTRAINT `fk_ticket_status_createdBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_status_lastModifiedBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_status_labelId`
    FOREIGN KEY (`LABEL_ID`)
    REFERENCES `fasp`.`ap_label` (`LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the Ticket statuses';

CREATE INDEX `fk_ticket_status_createdBy_idx` ON `fasp`.`tk_ticket_status` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_ticket_status_lastModifiedBy_idx` ON `fasp`.`tk_ticket_status` (`CREATED_BY` ASC);

CREATE INDEX `fk_ticket_status_labelId_idx` ON `fasp`.`tk_ticket_status` (`LABEL_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`tk_ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`tk_ticket` ;

CREATE TABLE IF NOT EXISTS `fasp`.`tk_ticket` (
  `TICKET_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Ticket',
  `TICKET_TYPE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Ticket type that this Ticket is of',
  `REFFERENCE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Foreign key that points to the Primary key of the Table that the Ticket is for',
  `NOTES` TEXT NOT NULL COMMENT 'Notes that the creator wants to include in the request for the Ticket',
  `TICKET_STATUS_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Status for the Ticket',
  `CREATED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Created by',
  `CREATED_DATE` DATETIME NOT NULL COMMENT 'Created date',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_ID`),
  CONSTRAINT `fk_ticket_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_ticket_statusId`
    FOREIGN KEY (`TICKET_STATUS_ID`)
    REFERENCES `fasp`.`tk_ticket_status` (`TICKET_STATUS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_ticket_typeId`
    FOREIGN KEY (`TICKET_TYPE_ID`)
    REFERENCES `fasp`.`tk_ticket_type` (`TICKET_TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the tickets received for different changes to Masters\nNote: Would only be for a specific Realm';

CREATE INDEX `fk_ticket_createdBy_idx` ON `fasp`.`tk_ticket` (`CREATED_BY` ASC);

CREATE INDEX `fk_ticket_lastModifiedBy_idx` ON `fasp`.`tk_ticket` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_ticket_ticket_statusId_idx` ON `fasp`.`tk_ticket` (`TICKET_STATUS_ID` ASC);

CREATE INDEX `fk_ticket_ticket_typeId_idx` ON `fasp`.`tk_ticket` (`TICKET_TYPE_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`tk_ticket_trans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`tk_ticket_trans` ;

CREATE TABLE IF NOT EXISTS `fasp`.`tk_ticket_trans` (
  `TICKET_TRANS_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Transaction id',
  `TICKET_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Ticket Id that this transaction is for',
  `TICKET_STATUS_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Status for the Ticket',
  `NOTES` TEXT NOT NULL COMMENT 'Notes from the transaction',
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL COMMENT 'Last modified by',
  `LAST_MODIFIED_DATE` DATETIME NOT NULL COMMENT 'Last modified date',
  PRIMARY KEY (`TICKET_TRANS_ID`),
  CONSTRAINT `fk_ticket_trans_lastModifiedBy`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_trans_ticketId`
    FOREIGN KEY (`TICKET_ID`)
    REFERENCES `fasp`.`tk_ticket` (`TICKET_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_trans_ticket_statusId`
    FOREIGN KEY (`TICKET_STATUS_ID`)
    REFERENCES `fasp`.`tk_ticket_status` (`TICKET_STATUS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Transaction table for the Tickets';

CREATE INDEX `fk_ticket_trans_lastModifiedBy_idx` ON `fasp`.`tk_ticket_trans` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_ticket_trans_ticketId_idx` ON `fasp`.`tk_ticket_trans` (`TICKET_ID` ASC);

CREATE INDEX `fk_ticket_trans_ticket_statusId_idx` ON `fasp`.`tk_ticket_trans` (`TICKET_STATUS_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`em_email_template`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`em_email_template` ;

CREATE TABLE IF NOT EXISTS `fasp`.`em_email_template` (
  `EMAIL_TEMPLATE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `EMAIL_DESC` VARCHAR(150) NULL DEFAULT NULL,
  `SUBJECT` VARCHAR(250) NOT NULL,
  `SUBJECT_PARAM` VARCHAR(100) NULL DEFAULT NULL,
  `EMAIL_BODY` TEXT NULL DEFAULT NULL,
  `EMAIL_BODY_PARAM` VARCHAR(500) NULL DEFAULT NULL,
  `CC_TO` VARCHAR(150) NULL DEFAULT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NULL DEFAULT NULL,
  `ACTIVE` TINYINT(1) NULL DEFAULT '0',
  `TO_SEND` TEXT NULL DEFAULT NULL,
  `BCC` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`EMAIL_TEMPLATE_ID`),
  CONSTRAINT `fk_em_email_template_us_user1`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_em_email_template_us_user2`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `fk_em_email_template_us_user1_idx` ON `fasp`.`em_email_template` (`LAST_MODIFIED_BY` ASC);

CREATE INDEX `fk_em_email_template_us_user2_idx` ON `fasp`.`em_email_template` (`CREATED_BY` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`em_emailer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`em_emailer` ;

CREATE TABLE IF NOT EXISTS `fasp`.`em_emailer` (
  `EMAILER_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `TO_SEND` LONGTEXT NULL DEFAULT NULL,
  `SUBJECT` LONGTEXT NULL DEFAULT NULL,
  `BODY` LONGTEXT NULL DEFAULT NULL,
  `CC_SEND_TO` LONGTEXT NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `TO_SEND_DATE` DATETIME NULL DEFAULT NULL,
  `LAST_MODIFIED_DATE` DATETIME NULL DEFAULT NULL,
  `ATTEMPTS` INT(10) UNSIGNED NULL DEFAULT '0',
  `STATUS` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '0-New,1-Sent,2-failed',
  `REASON` TEXT NULL DEFAULT NULL,
  `BCC` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`EMAILER_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1
COMMENT = 'Table that stores all the emails that have been sent from the system';

CREATE INDEX `idxStatus` ON `fasp`.`em_emailer` (`STATUS` ASC);

CREATE INDEX `idxCreatedDt` ON `fasp`.`em_emailer` (`CREATED_DATE` ASC);

CREATE INDEX `idxToSendDate` ON `fasp`.`em_emailer` (`TO_SEND_DATE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`em_file_store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`em_file_store` ;

CREATE TABLE IF NOT EXISTS `fasp`.`em_file_store` (
  `FILE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FILE_PATH` TEXT NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`FILE_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `fasp`.`em_emailer_filepath_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`em_emailer_filepath_mapping` ;

CREATE TABLE IF NOT EXISTS `fasp`.`em_emailer_filepath_mapping` (
  `EMAILER_FILEPATH_MAPPING_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `EMAILER_ID` INT(10) UNSIGNED NOT NULL,
  `FILE_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`EMAILER_FILEPATH_MAPPING_ID`),
  CONSTRAINT `fk_em_emailer_filepath_mapping_em_file_store`
    FOREIGN KEY (`FILE_ID`)
    REFERENCES `fasp`.`em_file_store` (`FILE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_em_emailer_filepath_mapping_em_emailer1`
    FOREIGN KEY (`EMAILER_ID`)
    REFERENCES `fasp`.`em_emailer` (`EMAILER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `FK_emailer_filepath_mapping_emailer_id` ON `fasp`.`em_emailer_filepath_mapping` (`EMAILER_ID` ASC);

CREATE INDEX `FK_emailer_filepath_mapping_file_id` ON `fasp`.`em_emailer_filepath_mapping` (`FILE_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_forgot_password_token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_forgot_password_token` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_forgot_password_token` (
  `FORGOT_PASSWORD_TOKEN_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique Id for each Token',
  `USER_ID` INT(10) UNSIGNED NOT NULL COMMENT 'User Id that this token is generated for',
  `TOKEN` VARCHAR(25) NOT NULL COMMENT 'Random Token string consisting of a-z, A-Z and 0-9 that is generated for the request',
  `TOKEN_GENERATION_DATE` DATETIME NOT NULL COMMENT 'Date that the request was generated',
  `TOKEN_TRIGGERED_DATE` DATETIME NULL COMMENT 'Date that the token was triggered by clicking on the link. If not triggered the value is null.',
  `TOKEN_COMPLETION_DATE` DATETIME NULL COMMENT 'Date that the token was completed by reseting the password. If not completed the value is null.',
  PRIMARY KEY (`FORGOT_PASSWORD_TOKEN_ID`),
  CONSTRAINT `fk_us_forgot_password_token_us_user1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store the Tokens generated for a forgot password request';

CREATE INDEX `fk_us_forgot_password_token_us_user1_idx` ON `fasp`.`us_forgot_password_token` (`USER_ID` ASC);

CREATE UNIQUE INDEX `unq_token` ON `fasp`.`us_forgot_password_token` (`TOKEN` ASC)  COMMENT 'Token must be unique';


-- -----------------------------------------------------
-- Table `fasp`.`us_token_logout`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_token_logout` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_token_logout` (
  `TOKEN_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement Id for the Tokens',
  `TOKEN` TEXT NOT NULL COMMENT 'Field to store the Token',
  `LOGOUT_DATE` DATETIME NOT NULL COMMENT 'Date that the Logout happened',
  PRIMARY KEY (`TOKEN_ID`))
ENGINE = InnoDB
COMMENT = 'A Store of the Tokens that have been logged out';


-- -----------------------------------------------------
-- Table `fasp`.`ap_static_label`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_static_label` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_static_label` (
  `STATIC_LABEL_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `LABEL_CODE` VARCHAR(100) NOT NULL,
  `ACTIVE` TINYINT(1) NOT NULL,
  PRIMARY KEY (`STATIC_LABEL_ID`))
ENGINE = InnoDB
COMMENT = 'Static labels to be used across the application';


-- -----------------------------------------------------
-- Table `fasp`.`ap_static_label_languages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`ap_static_label_languages` ;

CREATE TABLE IF NOT EXISTS `fasp`.`ap_static_label_languages` (
  `STATIC_LABEL_LANGUAGE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `STATIC_LABEL_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Static Label that this Text is for',
  `LANGUAGE_ID` INT(10) UNSIGNED NOT NULL COMMENT 'Language that this text is for',
  `LABEL_TEXT` VARCHAR(255) NOT NULL COMMENT 'Text in the Language to be displayed',
  PRIMARY KEY (`STATIC_LABEL_LANGUAGE_ID`),
  CONSTRAINT `fk_ap_static_label_languages_ap_static_label1`
    FOREIGN KEY (`STATIC_LABEL_ID`)
    REFERENCES `fasp`.`ap_static_label` (`STATIC_LABEL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ap_static_label_languages_ap_language1`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `fasp`.`ap_language` (`LANGUAGE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Languages for each of the Static Labels';

CREATE INDEX `fk_ap_static_label_languages_ap_static_label1_idx` ON `fasp`.`ap_static_label_languages` (`STATIC_LABEL_ID` ASC);

CREATE INDEX `fk_ap_static_label_languages_ap_language1_idx` ON `fasp`.`ap_static_label_languages` (`LANGUAGE_ID` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`us_can_create_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`us_can_create_role` ;

CREATE TABLE IF NOT EXISTS `fasp`.`us_can_create_role` (
  `ROLE_ID` VARCHAR(100) NOT NULL,
  `CAN_CREATE_ROLE` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ROLE_ID`, `CAN_CREATE_ROLE`),
  CONSTRAINT `fk_us_can_create_role_us_role1`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `fasp`.`us_role` (`ROLE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_us_can_create_role_us_role2`
    FOREIGN KEY (`CAN_CREATE_ROLE`)
    REFERENCES `fasp`.`us_role` (`ROLE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_us_can_create_role_us_role2_idx` ON `fasp`.`us_can_create_role` (`CAN_CREATE_ROLE` ASC);


-- -----------------------------------------------------
-- Table `fasp`.`rm_planning_unit_procurement_agent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fasp`.`rm_planning_unit_procurement_agent` ;

CREATE TABLE IF NOT EXISTS `fasp`.`rm_planning_unit_procurement_agent` (
  `PLANNING_UNIT_PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL,
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL,
  `SKU_CODE` VARCHAR(25) NOT NULL,
  `CATALOG_PRICE` DECIMAL(12,2) UNSIGNED NULL,
  `MOQ` INT(10) UNSIGNED NULL,
  `UNITS_PER_PALLET` INT(10) UNSIGNED NULL,
  `UNITS_PER_CONTAINER` INT(10) UNSIGNED NULL,
  `VOLUME` DECIMAL(12,2) UNSIGNED NULL,
  `WEIGHT` DECIMAL(12,2) UNSIGNED NULL,
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `LAST_MODIFIED_BY` INT(10) UNSIGNED NOT NULL,
  `LAST_MODIFIED_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`PLANNING_UNIT_PROCUREMENT_AGENT_ID`),
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_rm_planning_unit1`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_rm_procurement_agent1`
    FOREIGN KEY (`PROCUREMENT_AGENT_ID`)
    REFERENCES `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_us_user1`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rm_planning_unit_procurement_agent_us_user2`
    FOREIGN KEY (`LAST_MODIFIED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_rm_planning_unit_procurement_agent_rm_planning_unit1_idx` ON `fasp`.`rm_planning_unit_procurement_agent` (`PLANNING_UNIT_ID` ASC);

CREATE INDEX `fk_rm_planning_unit_procurement_agent_rm_procurement_agent1_idx` ON `fasp`.`rm_planning_unit_procurement_agent` (`PROCUREMENT_AGENT_ID` ASC);

CREATE INDEX `fk_rm_planning_unit_procurement_agent_us_user1_idx` ON `fasp`.`rm_planning_unit_procurement_agent` (`CREATED_BY` ASC);

CREATE INDEX `fk_rm_planning_unit_procurement_agent_us_user2_idx` ON `fasp`.`rm_planning_unit_procurement_agent` (`LAST_MODIFIED_BY` ASC);

USE `fasp` ;

-- -----------------------------------------------------
-- procedure generateForgotPasswordToken
-- -----------------------------------------------------

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`generateForgotPasswordToken`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateForgotPasswordToken`(`VAR_USER_ID` INT(10), `VAR_TOKEN_DATE` DATETIME)
BEGIN
	SET @userId = null;
	SELECT USER_ID INTO @userId FROM us_user WHERE USER_ID=VAR_USER_ID;
    IF @userId IS NOT NULL THEN 
		SET @rowCnt = 1;
		SET @token = '';
		WHILE (@rowCnt != 0) DO
			SET @token = '';
			SET @allowedChars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
			SET @allowedCharsLen = LENGTH(@allowedChars);
			SET @tokenLen = 25;
			SET @i = 0;
			WHILE (@i < @tokenLen) DO
				SET @token = CONCAT(@token, substring(@allowedChars, FLOOR(RAND() * @allowedCharsLen + 1), 1));
				SET @i = @i + 1;
			END WHILE;
			SELECT count(*) INTO @rowCnt FROM us_forgot_password_token WHERE TOKEN=@token;
		END WHILE;
		INSERT INTO us_forgot_password_token (USER_ID, TOKEN, TOKEN_GENERATION_DATE) VALUES (@userId, @token, `VAR_TOKEN_DATE`);
        SELECT @token;
	ELSE 
		select null;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- Data for table `fasp`.`ap_language`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_language` (`LANGUAGE_ID`, `LANGUAGE_CODE`, `LANGUAGE_NAME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 'en', 'English', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_language` (`LANGUAGE_ID`, `LANGUAGE_CODE`, `LANGUAGE_NAME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 'fr', 'French', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_language` (`LANGUAGE_ID`, `LANGUAGE_CODE`, `LANGUAGE_NAME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 'sp', 'Spanish', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_language` (`LANGUAGE_ID`, `LANGUAGE_CODE`, `LANGUAGE_NAME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 'pr', 'Pourtegese', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_label`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 'United States of America', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 'Kenya', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 'Malawi', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 'US Aid', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 'US Dollar', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 'Length', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 'Weight', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 'Area', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 'Volume', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 'Items or Pieces', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (11, 'Meters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (12, 'Centimeters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (13, 'Millimeters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (14, 'Feet', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (15, 'Inches', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (16, 'Planned', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (17, 'Draft', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (18, 'Submitted', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (19, 'Approved', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (20, 'Shipped', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (21, 'Delivered', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (22, 'Received', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (23, 'Pound Sterling', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (24, 'Euro', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (25, 'Update Application Masters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (26, 'Create Realm', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (27, 'Update Realm Masters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (28, 'Create Users', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (29, 'Update Program Masters', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (30, 'Update Program Data', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (31, 'View Reports', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (32, 'Application level Admin', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (33, 'Realm level Admin', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (34, 'Program level Admin', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (35, 'Program User', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (36, 'HIV / AIDS', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (37, 'Family Planning', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (38, 'Malaria', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (39, 'Ministry of Health (MoH)', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (40, 'National', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (41, 'National level', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (42, 'North', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (43, 'South', '', '', '', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (44, 'All Categories', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (45, 'Reproductive Health', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (46, 'Male Condom', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (47, 'Female Condom', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (48, 'Oral Hormonal Contraceptive', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (49, 'Subdermal Implant', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (50, 'Injectables', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (51, 'Intra Uterine Device', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (52, 'Diagnostic Test Kits', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (53, 'HIV RDT Kits', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (54, 'Malaria RDT Kits', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (55, 'All other RDT kits', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (56, 'All Introvirals', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (57, 'Essential drugs', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (58, 'Anti-Malarials', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (59, 'HIV - tablets', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (60, 'Lab Equipments and Supplies', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (61, 'Other Commodities', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (62, 'Malaria commodities', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (63, 'All commodities', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (64, 'Bed nets', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (65, 'Male Condom - Strawbery', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (66, 'Abacavir 300mg', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (67, 'Male Condom - Banana', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (68, 'Male Condom - No label', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (69, 'TLD', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (70, 'Kilograms', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (71, 'Grams', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (72, 'Pounds', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (73, 'Ounces', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (74, 'Tons', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (75, 'Square feet', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (76, 'Square meters', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (77, 'Liters', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (78, 'US Gallons', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (79, 'Milliliters', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (80, 'Pieces', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (81, 'Items', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (82, 'Each', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (83, 'Box', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (84, 'Case', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (85, 'Euro 1 Pallet', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (86, 'Euro 2 Pallet', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (87, '40 ft container', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (88, '20 ft container', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (89, 'Tablet', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (90, 'Bottle', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (91, 'Bubble strip', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (92, 'Pack', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (93, 'No label Male Condom - Pack of 6', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (94, 'Strawberry falvored Male Condom - Pack of 6', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (95, 'No label Male Condom - Pack of 5', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (96, 'Abacavir 300mg - Bottle of 60', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (97, 'Abacavir 300mg - Strip of 12', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (98, 'TLD - 90', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (99, 'TLD - 30', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (100, 'TLD - 90 - 1 Bottle', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (101, 'TLD - 90 - 20 Bottles', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (102, 'TLD - 90 - 4000 Bottles', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (103, 'Strawberry falvored Male Condom - Case of 288', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (104, 'Strawberry falvored Male Condom - Euro 1 Pallet of 28800', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (105, 'Abacavir 300mg - Pack of 36', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (106, 'Abacavir 300mg - Case of 3600', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (107, 'Pink colored Male Condom - Case of 300', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (108, 'pallet', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (109, 'PSM', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (110, 'GF', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (111, 'PEPFAR', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (112, 'Glaxo', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (113, 'Flazo', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (114, 'Cloned Consumption', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (115, 'Facility Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (116, 'Demographic Goal', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (117, 'Interpolate', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (118, 'LMIS Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (119, 'MSD Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (120, 'Physical Inventory Count', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (121, 'SDP Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (122, 'Stock Cards', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (123, 'Supplier Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (124, 'Projected Trend', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (125, 'Warehouse Reports', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (126, 'ACTCON', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (127, 'FORCON', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (128, 'INV', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (129, 'SHIP', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (130, 'USAID', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (131, 'UNFPA', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (132, 'PEPFAR', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (133, 'Gates Foundation', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (134, 'HIV / Aids - Kenya - Ministry of Health', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (135, 'Malaria - Kenya - National', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (136, 'Family planning - Malawi - National', '', '', '', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (137, 'Kenya - 2020 H1', '', '', '', 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (138, 'Malaria - 2020', '', '', '', 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (139, 'Family Planning - Malawi 2020', '', '', '', 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (140, 'HIV/AIDS', '', '', '', 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (141, 'Malaria', '', '', '', 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (142, 'Family Planning - Gates Found', '', '', '', 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_realm`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_realm` (`REALM_ID`, `REALM_CODE`, `LABEL_ID`, `MONTHS_IN_PAST_FOR_AMC`, `MONTHS_IN_FUTURE_FOR_AMC`, `ORDER_FREQUENCY`, `DEFAULT_REALM`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 'UAID', 4, 3, 6, 4, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_user` (`USER_ID`, `REALM_ID`, `USERNAME`, `PASSWORD`, `EMAIL_ID`, `PHONE`, `LANGUAGE_ID`, `ACTIVE`, `FAILED_ATTEMPTS`, `EXPIRES_ON`, `SYNC_EXPIRES_ON`, `LAST_LOGIN_DATE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, NULL, 'anchal', 0x24326124313024645875672E42784E52575748656F4A7746596C593575706E6E68397A4C56596A43616E615569395073494753566241577A507A7757, 'anchal.c@altius.cc', '96371396638', 1, 1, 0, '2020-03-10 12:00:00', '2020-03-10 12:00:00', NULL, 1, '2020-02-12 12:00:00', 1, '2020-02-12 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_currency`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_currency` (`CURRENCY_ID`, `CURRENCY_CODE`, `CURRENCY_SYMBOL`, `LABEL_ID`, `CONVERSION_RATE_TO_USD`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (1, 'USD', '$', 5, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00', 1);
INSERT INTO `fasp`.`ap_currency` (`CURRENCY_ID`, `CURRENCY_CODE`, `CURRENCY_SYMBOL`, `LABEL_ID`, `CONVERSION_RATE_TO_USD`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (2, 'GBP', '£', 20, 1.29, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00', 1);
INSERT INTO `fasp`.`ap_currency` (`CURRENCY_ID`, `CURRENCY_CODE`, `CURRENCY_SYMBOL`, `LABEL_ID`, `CONVERSION_RATE_TO_USD`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (3, 'EUR', '€', 21, 1.08, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_country`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_country` (`COUNTRY_ID`, `LABEL_ID`, `COUNTRY_CODE`, `CURRENCY_ID`, `LANGUAGE_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 'USA', 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_country` (`COUNTRY_ID`, `LABEL_ID`, `COUNTRY_CODE`, `CURRENCY_ID`, `LANGUAGE_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 'KEN', 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_country` (`COUNTRY_ID`, `LABEL_ID`, `COUNTRY_CODE`, `CURRENCY_ID`, `LANGUAGE_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 3, 'MWI', 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_realm_country`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`, `REALM_ID`, `COUNTRY_ID`, `DEFAULT_CURRENCY_ID`, `PALLET_UNIT_ID`, `AIR_FREIGHT_PERC`, `SEA_FREIGHT_PERC`, `SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, `SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, `ARRIVED_TO_DELIVERED_LEAD_TIME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 2, 1, 1, .20, .08, 10, 90, 15, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_realm_country` (`REALM_COUNTRY_ID`, `REALM_ID`, `COUNTRY_ID`, `DEFAULT_CURRENCY_ID`, `PALLET_UNIT_ID`, `AIR_FREIGHT_PERC`, `SEA_FREIGHT_PERC`, `SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, `SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, `ARRIVED_TO_DELIVERED_LEAD_TIME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 3, 1, 1, .23, .10, 12, 80, 10, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_health_area`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_health_area` (`HEALTH_AREA_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 36, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_health_area` (`HEALTH_AREA_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 37, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_health_area` (`HEALTH_AREA_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 38, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_product_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 44, 1, '0', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 45, 2, '0.1', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 46, 3, '0.1.1', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 47, 3, '0.1.2', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 1, 48, 3, '0.1.3', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 1, 49, 3, '0.1.4', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 1, 50, 3, '0.1.5', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 1, 51, 3, '0.1.6', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 1, 52, 2, '0.2', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 1, 53, 3, '0.2.1', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (11, 1, 54, 3, '0.2.2', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (12, 1, 55, 3, '0.2.3', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (13, 1, 56, 2, '0.3', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (14, 1, 57, 2, '0.4', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (15, 1, 58, 3, '0.4.1', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (16, 1, 59, 3, '0.4.2', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (17, 1, 60, 2, '0.5', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (18, 1, 61, 2, '0.6', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (19, 1, 62, 3, '0.6.1', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (20, 1, 63, 3, '0.6.2', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`, `REALM_ID`, `LABEL_ID`, `LEVEL`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (21, 1, 64, 2, '0.7', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_organisation`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_organisation` (`ORGANISATION_ID`, `REALM_ID`, `LABEL_ID`, `ORGANISATION_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 39, 'MOH', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_organisation` (`ORGANISATION_ID`, `REALM_ID`, `LABEL_ID`, `ORGANISATION_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 40, 'NATL', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_program`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_program` (`PROGRAM_ID`, `REALM_COUNTRY_ID`, `ORGANISATION_ID`, `HEALTH_AREA_ID`, `LABEL_ID`, `PROGRAM_MANAGER_USER_ID`, `PROGRAM_NOTES`, `AIR_FREIGHT_PERC`, `SEA_FREIGHT_PERC`, `PLANNED_TO_DRAFT_LEAD_TIME`, `DRAFT_TO_SUBMITTED_LEAD_TIME`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `DELIVERED_TO_RECEIVED_LEAD_TIME`, `MONTHS_IN_PAST_FOR_AMC`, `MONTHS_IN_FUTURE_FOR_AMC`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 1, 134, 1, '', .25, .08, 20, 15, 45, 60, 10, 3, 6, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_program` (`PROGRAM_ID`, `REALM_COUNTRY_ID`, `ORGANISATION_ID`, `HEALTH_AREA_ID`, `LABEL_ID`, `PROGRAM_MANAGER_USER_ID`, `PROGRAM_NOTES`, `AIR_FREIGHT_PERC`, `SEA_FREIGHT_PERC`, `PLANNED_TO_DRAFT_LEAD_TIME`, `DRAFT_TO_SUBMITTED_LEAD_TIME`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `DELIVERED_TO_RECEIVED_LEAD_TIME`, `MONTHS_IN_PAST_FOR_AMC`, `MONTHS_IN_FUTURE_FOR_AMC`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 2, 3, 135, 1, '', .23, .07, 20, 15, 45, 60, 10, 3, 6, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_program` (`PROGRAM_ID`, `REALM_COUNTRY_ID`, `ORGANISATION_ID`, `HEALTH_AREA_ID`, `LABEL_ID`, `PROGRAM_MANAGER_USER_ID`, `PROGRAM_NOTES`, `AIR_FREIGHT_PERC`, `SEA_FREIGHT_PERC`, `PLANNED_TO_DRAFT_LEAD_TIME`, `DRAFT_TO_SUBMITTED_LEAD_TIME`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `DELIVERED_TO_RECEIVED_LEAD_TIME`, `MONTHS_IN_PAST_FOR_AMC`, `MONTHS_IN_FUTURE_FOR_AMC`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 2, 2, 2, 136, 1, '', .20, .12, 30, 20, 50, 60, 10, 3, 6, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_region`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_region` (`REGION_ID`, `REALM_COUNTRY_ID`, `LABEL_ID`, `CAPACITY_CBM`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 41, 40000, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_region` (`REGION_ID`, `REALM_COUNTRY_ID`, `LABEL_ID`, `CAPACITY_CBM`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 42, 18000, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_region` (`REGION_ID`, `REALM_COUNTRY_ID`, `LABEL_ID`, `CAPACITY_CBM`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 2, 43, 13500, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_role` (`ROLE_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_APPL_ADMIN', 32, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role` (`ROLE_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_REALM_ADMIN', 33, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role` (`ROLE_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_PROG_ADMIN', 34, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role` (`ROLE_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_USER', 35, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_business_function`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_UPDATE_APPL_MASTER', 25, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_CREATE_REALM', 26, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_UPDATE_REALM_MASTER', 27, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_CREATE_USER', 28, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_UPDATE_PROGRAM_PROPERTIES', 29, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_UPDATE_PROGRAM_DATA', 30, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('ROLE_BF_VIEW_REPORTS', 31, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_user_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_user_role` (`USER_ROLE_ID`, `USER_ID`, `ROLE_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 'ROLE_APPL_ADMIN', 1, '2020-03-10 12:00:00', 1, '2020-03-10 12:00:00');
INSERT INTO `fasp`.`us_user_role` (`USER_ROLE_ID`, `USER_ID`, `ROLE_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 'ROLE_REALM_ADMIN', 1, '2020-03-10 12:00:00', 1, '2020-03-10 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_role_business_function`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 'ROLE_APPL_ADMIN', 'ROLE_BF_UPDATE_APPL_MASTER', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 'ROLE_APPL_ADMIN', 'ROLE_BF_CREATE_REALM', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 'ROLE_APPL_ADMIN', 'ROLE_BF_CREATE_USER', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 'ROLE_REALM_ADMIN', 'ROLE_BF_UPDATE_REALM_MASTER', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 'ROLE_REALM_ADMIN', 'ROLE_BF_CREATE_USER', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 'ROLE_PROG_ADMIN', 'ROLE_BF_UPDATE_PROGRAM_PROPERTIES', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 'ROLE_PROG_ADMIN', 'ROLE_BF_UPDATE_PROGRAM_DATA', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 'ROLE_PROG_ADMIN', 'ROLE_BF_VIEW_REPORTS', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 'ROLE_USER', 'ROLE_BF_UPDATE_PROGRAM_DATA', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 'ROLE_USER', 'ROLE_BF_VIEW_REPORTS', 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`us_user_acl`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`us_user_acl` (`USER_ACL_ID`, `USER_ID`, `REALM_COUNTRY_ID`, `HEALTH_AREA_ID`, `ORGANISATION_ID`, `PROGRAM_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, NULL, NULL, NULL, NULL, 1, '2020-03-10 12:00:00', 1, '2020-03-10 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_data_source_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_data_source_type` (`DATA_SOURCE_TYPE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 126, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source_type` (`DATA_SOURCE_TYPE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 127, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source_type` (`DATA_SOURCE_TYPE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 128, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source_type` (`DATA_SOURCE_TYPE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 129, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_data_source`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 2, 114, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 3, 115, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 2, 116, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 2, 117, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 1, 1, 118, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 1, 1, 119, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 1, 3, 120, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 1, 1, 121, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 1, 3, 122, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 1, 4, 123, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (11, 1, 2, 124, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_data_source` (`DATA_SOURCE_ID`, `REALM_ID`, `DATA_SOURCE_TYPE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (12, 1, 4, 125, '1', 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_shipment_status`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 16, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 17, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 18, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 19, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 20, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 21, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status` (`SHIPMENT_STATUS_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 22, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_shipment_status_allowed`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 2, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 3, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 2, 2, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 2, 2, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 3, 3, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 3, 4, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 4, 4, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 4, 5, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 4, 6, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (11, 4, 7, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (12, 5, 5, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (13, 5, 6, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (14, 5, 7, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (15, 6, 6, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (16, 6, 7, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_shipment_status_allowed` (`SHIPMENT_STATUS_ALLOWED_ID`, `SHIPMENT_STATUS_ID`, `NEXT_SHIPMENT_STATUS_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (17, 7, 7, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_supplier`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_supplier` (`SUPPLIER_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 112, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_supplier` (`SUPPLIER_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 113, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_organisation_country`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_organisation_country` (`ORGANISATION_COUNTRY_ID`, `ORGANISATION_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_organisation_country` (`ORGANISATION_COUNTRY_ID`, `ORGANISATION_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_organisation_country` (`ORGANISATION_COUNTRY_ID`, `ORGANISATION_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 2, 2, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_health_area_country`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_health_area_country` (`HEALTH_AREA_COUNTRY_ID`, `HEALTH_AREA_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_health_area_country` (`HEALTH_AREA_COUNTRY_ID`, `HEALTH_AREA_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 3, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_health_area_country` (`HEALTH_AREA_COUNTRY_ID`, `HEALTH_AREA_ID`, `REALM_COUNTRY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 2, 2, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_program_region`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_program_region` (`PROGRAM_REGION_ID`, `PROGRAM_ID`, `REGION_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_program_region` (`PROGRAM_REGION_ID`, `PROGRAM_ID`, `REGION_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 1, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_program_region` (`PROGRAM_REGION_ID`, `PROGRAM_ID`, `REGION_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 3, 2, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`rm_program_region` (`PROGRAM_REGION_ID`, `PROGRAM_ID`, `REGION_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 3, 3, 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_funding_source`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 130, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 131, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 132, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_funding_source` (`FUNDING_SOURCE_ID`, `REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 133, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_sub_funding_source`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_sub_funding_source` (`SUB_FUNDING_SOURCE_ID`, `FUNDING_SOURCE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 140, 1, 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');
INSERT INTO `fasp`.`rm_sub_funding_source` (`SUB_FUNDING_SOURCE_ID`, `FUNDING_SOURCE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 141, 1, 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');
INSERT INTO `fasp`.`rm_sub_funding_source` (`SUB_FUNDING_SOURCE_ID`, `FUNDING_SOURCE_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 3, 142, 1, 1, '2020-03-02 10:00:00', 1, '2020-03-02 10:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_budget`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_budget` (`BUDGET_ID`, `PROGRAM_ID`, `SUB_FUNDING_SOURCE_ID`, `LABEL_ID`, `BUDGET_AMT`, `START_DATE`, `STOP_DATE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 137, 450000, '2020-01-01', '2020-06-30', 1, 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');
INSERT INTO `fasp`.`rm_budget` (`BUDGET_ID`, `PROGRAM_ID`, `SUB_FUNDING_SOURCE_ID`, `LABEL_ID`, `BUDGET_AMT`, `START_DATE`, `STOP_DATE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 2, 138, 2500000, '2020-01-01', '2020-12-31', 1, 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');
INSERT INTO `fasp`.`rm_budget` (`BUDGET_ID`, `PROGRAM_ID`, `SUB_FUNDING_SOURCE_ID`, `LABEL_ID`, `BUDGET_AMT`, `START_DATE`, `STOP_DATE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 3, 3, 139, 1000000, '2020-01-01', '2020-12-31', 1, 1, '2020-03-01 10:00:00', 1, '2020-03-01 10:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_procurement_agent`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`, `REALM_ID`, `PROCUREMENT_AGENT_CODE`, `LABEL_ID`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 'PSM', 109, 0, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`, `REALM_ID`, `PROCUREMENT_AGENT_CODE`, `LABEL_ID`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 'GF', 110, 0, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_agent` (`PROCUREMENT_AGENT_ID`, `REALM_ID`, `PROCUREMENT_AGENT_CODE`, `LABEL_ID`, `SUBMITTED_TO_APPROVED_LEAD_TIME`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 'PEPFAR', 111, 0, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_forecasting_unit`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`, `REALM_ID`, `PRODUCT_CATEGORY_ID`, `TRACER_CATEGORY_ID`, `GENERIC_LABEL_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 3, NULL, 65, 65, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`, `REALM_ID`, `PRODUCT_CATEGORY_ID`, `TRACER_CATEGORY_ID`, `GENERIC_LABEL_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 15, NULL, 66, 66, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`, `REALM_ID`, `PRODUCT_CATEGORY_ID`, `TRACER_CATEGORY_ID`, `GENERIC_LABEL_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 3, NULL, 67, 67, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`, `REALM_ID`, `PRODUCT_CATEGORY_ID`, `TRACER_CATEGORY_ID`, `GENERIC_LABEL_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 3, NULL, 68, 68, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`, `REALM_ID`, `PRODUCT_CATEGORY_ID`, `TRACER_CATEGORY_ID`, `GENERIC_LABEL_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 1, 16, NULL, 69, 69, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_dimension`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_dimension` (`DIMENSION_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 6, 1, 1, '2020-01-01 10:00:00', 1, '2020-01-01 10:00:00');
INSERT INTO `fasp`.`ap_dimension` (`DIMENSION_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 7, 1, 1, '2020-01-01 10:00:00', 1, '2020-01-01 10:00:00');
INSERT INTO `fasp`.`ap_dimension` (`DIMENSION_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 8, 1, 1, '2020-01-01 10:00:00', 1, '2020-01-01 10:00:00');
INSERT INTO `fasp`.`ap_dimension` (`DIMENSION_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 9, 1, 1, '2020-01-01 10:00:00', 1, '2020-01-01 10:00:00');
INSERT INTO `fasp`.`ap_dimension` (`DIMENSION_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 10, 1, 1, '2020-01-01 10:00:00', 1, '2020-01-01 10:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_unit`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 11, 'm', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 1, 12, 'cm', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 1, 13, 'mm', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 14, 'ft', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 1, 15, 'in', 1, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 2, 70, 'kg', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 2, 71, 'gm', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 2, 72, 'lbs', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (9, 2, 73, 'oz', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (10, 2, 74, 'ton', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (11, 3, 75, 'sq ft', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (12, 3, 76, 'sq m', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (13, 4, 77, 'lt', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (14, 4, 78, 'us gal', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (15, 4, 79, 'ml', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (16, 5, 80, 'peice', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (17, 5, 81, 'item', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (18, 5, 82, 'each', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (19, 5, 83, 'box', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (20, 5, 84, 'case', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (21, 5, 85, 'Euro 1 Pallet', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (22, 5, 86, 'Euro 2 Pallet', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (23, 5, 87, '40ft Container', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (24, 5, 88, '20ft Container', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (25, 5, 89, 'Tablet', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (26, 5, 90, 'bottle', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (27, 5, 91, 'bubble strip', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (28, 5, 92, 'Pack', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`ap_unit` (`UNIT_ID`, `DIMENSION_ID`, `LABEL_ID`, `UNIT_CODE`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (29, 5, 108, 'pallet', 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_planning_unit`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 4, 93, 92, 6, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 94, 92, 6, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 2, 95, 92, 5, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 4, 96, 90, 60, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 4, 97, 91, 12, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 5, 98, 90, 90, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`, `FORECASTING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 5, 99, 90, 30, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_procurement_unit`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 6, 100, 26, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 6, 101, 20, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 6, 102, 29, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 2, 103, 20, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 2, 104, 21, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 5, 105, 28, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 4, 106, 20, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit` (`PROCUREMENT_UNIT_ID`, `PLANNING_UNIT_ID`, `LABEL_ID`, `UNIT_ID`, `MULTIPLIER`, `SUPPLIER_ID`, `WIDTH_UNIT_ID`, `WIDTH_QTY`, `HEIGHT_UNIT_ID`, `HEIGHT_QTY`, `LENGTH_UNIT_ID`, `LENGTH_QTY`, `WEIGHT_UNIT_ID`, `WEIGHT_QTY`, `UNITS_PER_CONTAINER`, `LABELING`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (8, 3, 107, 20, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`rm_procurement_unit_procurement_agent`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, 1, 1, 'ARTMIS 15 code', 2.35, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (2, 2, 1, 'ARTMIS 15 code', 2.13, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (3, 3, 1, 'ARTMIS 15 code', 2.13, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (4, 1, 2, 'G1021', 2.38, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (5, 2, 2, 'G1022', 3.12, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (6, 3, 2, 'G1023', 97.2, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');
INSERT INTO `fasp`.`rm_procurement_unit_procurement_agent` (`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`, `PROCUREMENT_UNIT_ID`, `PROCUREMENT_AGENT_ID`, `SKU_CODE`, `VENDOR_PRICE`, `APPROVED_TO_SHIPPED_LEAD_TIME`, `GTIN`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (7, 1, 3, 'C1021G5011F1', 0.00, 0, NULL, 1, 1, '2020-02-25 12:00:00', 1, '2020-02-25 12:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`em_email_template`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`em_email_template` (`EMAIL_TEMPLATE_ID`, `EMAIL_DESC`, `SUBJECT`, `SUBJECT_PARAM`, `EMAIL_BODY`, `EMAIL_BODY_PARAM`, `CC_TO`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`, `TO_SEND`, `BCC`) VALUES (1, 'Password reset link(Forgot password)', 'Your FASP password', NULL, 'Dear Sir/Mam,<br/>\r\nGreetings From FASP.<br/>\r\nWe received a request to reset the password for your account.<br/>\r\nPlease find below a temporary link that you can use to reset your password.<br/>\r\n<br/>\r\n<a href=\"<%HOST_URL%>/<%PASSWORD_RESET_URL%>?username=<%USERNAME%>&token=<%TOKEN%>0\">Reset password link</a></br><br/>\n\r\n<br/>FASP Team<br/>', 'HOST_URL,PASSWORD_RESET_URL,USERNAME,TOKEN', NULL, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00', 1, NULL, NULL);
INSERT INTO `fasp`.`em_email_template` (`EMAIL_TEMPLATE_ID`, `EMAIL_DESC`, `SUBJECT`, `SUBJECT_PARAM`, `EMAIL_BODY`, `EMAIL_BODY_PARAM`, `CC_TO`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`, `TO_SEND`, `BCC`) VALUES (2, 'New user creation', 'FASP login credentials', NULL, 'Dear Sir/Mam,<br/>\r\nGreetings From FASP.<br/>\r\nYour new fasp account has been created.<br/>\r\nPlease find below a temporary link that you can use to reset your password.<br/>\r\n<br/>\n<a href=\"<%HOST_URL%>/<%PASSWORD_RESET_URL%>?username=<%USERNAME%>&token=<%TOKEN%>0\">Reset password link</a></br><br/>\r\nYou can login using below Credentials: <br/>\r\n<b>Username=<%USERNAME%></b><br/><br/>\r\n\r\n<br/>FASP Team<br/>', 'HOST_URL,PASSWORD_RESET_URL,USERNAME,TOKEN', NULL, 1, '2020-02-20 12:00:00', 1, '2020-02-20 12:00:00', 1, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_static_label`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_static_label` (`STATIC_LABEL_ID`, `LABEL_CODE`, `ACTIVE`) VALUES (1, 'static.realm.realmName', 1);
INSERT INTO `fasp`.`ap_static_label` (`STATIC_LABEL_ID`, `LABEL_CODE`, `ACTIVE`) VALUES (2, 'static.realm.realmCode', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `fasp`.`ap_static_label_languages`
-- -----------------------------------------------------
START TRANSACTION;
USE `fasp`;
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (1, 1, 1, 'Realm name');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (2, 1, 2, 'Nom de domaine');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (3, 1, 3, 'Nombre del reino');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (4, 1, 4, 'Nome da região');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (5, 2, 1, 'Realm code');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (6, 2, 2, 'Code de domaine');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (7, 2, 3, 'Código de reino');
INSERT INTO `fasp`.`ap_static_label_languages` (`STATIC_LABEL_LANGUAGE_ID`, `STATIC_LABEL_ID`, `LANGUAGE_ID`, `LABEL_TEXT`) VALUES (8, 2, 4, 'Código da região');

COMMIT;

USE `fasp`;

DELIMITER $$

USE `fasp`$$
DROP TRIGGER IF EXISTS `fasp`.`ap_currency_AFTER_INSERT` $$
USE `fasp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `fasp`.`ap_currency_AFTER_INSERT` AFTER INSERT ON `ap_currency` FOR EACH ROW
BEGIN
	INSERT INTO ap_currency_history (CURRENCY_ID, CONVERSION_RATE_TO_USD, LAST_MODIFIED_DATE) 
    VALUES(new.`CURRENCY_ID`,new.`CONVERSION_RATE_TO_USD`,new.`LAST_MODIFIED_DATE`);
END$$


USE `fasp`$$
DROP TRIGGER IF EXISTS `fasp`.`ap_currency_AFTER_UPDATE` $$
USE `fasp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `fasp`.`ap_currency_AFTER_UPDATE` AFTER UPDATE ON `ap_currency` FOR EACH ROW
BEGIN
	INSERT INTO ap_currency_history (CURRENCY_ID, CONVERSION_RATE_TO_USD, LAST_MODIFIED_DATE) 
    VALUES(new.`CURRENCY_ID`,new.`CONVERSION_RATE_TO_USD`,new.`LAST_MODIFIED_DATE`);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- begin attached script 'script'
SET GLOBAL event_scheduler = ON;
DELIMITER $$

CREATE DEFINER=`faspUser`@`localhost` EVENT `tokenLogoutCleanup` ON SCHEDULE EVERY 1 DAY STARTS '2020-02-01 03:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	DELETE tl.* FROM rm_token_logout tl WHERE timestampdiff(DAY, tl.LOGOUT_DATE,  now())>=2;
END$$

DELIMITER ;


-- end attached script 'script'
-- begin attached script 'script1'
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ap_static_label;
TRUNCATE TABLE ap_static_label_languages;

INSERT INTO ap_static_label VALUES (null, 'static.login.login', true);	INSERT INTO ap_static_label_languages VALUES (null, 1, 1, 'Login');
INSERT INTO ap_static_label VALUES (null, 'static.login.signintext', true);	INSERT INTO ap_static_label_languages VALUES (null, 2, 1, 'Sign in to your account');
INSERT INTO ap_static_label VALUES (null, 'static.login.username', true);	INSERT INTO ap_static_label_languages VALUES (null, 3, 1, 'Username');
INSERT INTO ap_static_label VALUES (null, 'static.login.password', true);	INSERT INTO ap_static_label_languages VALUES (null, 4, 1, 'Password');
INSERT INTO ap_static_label VALUES (null, 'static.login.forgotpassword', true);	INSERT INTO ap_static_label_languages VALUES (null, 5, 1, 'Forgot Password');
INSERT INTO ap_static_label VALUES (null, 'static.common.select', true);	INSERT INTO ap_static_label_languages VALUES (null, 6, 1, 'Please Select');
INSERT INTO ap_static_label VALUES (null, 'static.common.submit', true);	INSERT INTO ap_static_label_languages VALUES (null, 7, 1, 'Submit');
INSERT INTO ap_static_label VALUES (null, 'static.common.cancel', true);	INSERT INTO ap_static_label_languages VALUES (null, 8, 1, 'Cancel');
INSERT INTO ap_static_label VALUES (null, 'static.common.clear', true);	INSERT INTO ap_static_label_languages VALUES (null, 9, 1, 'Clear');
INSERT INTO ap_static_label VALUES (null, 'static.common.reset', true);	INSERT INTO ap_static_label_languages VALUES (null, 10, 1, 'Reset');
INSERT INTO ap_static_label VALUES (null, 'static.common.active', true);	INSERT INTO ap_static_label_languages VALUES (null, 11, 1, 'Active');
INSERT INTO ap_static_label VALUES (null, 'static.common.disabled', true);	INSERT INTO ap_static_label_languages VALUES (null, 12, 1, 'Disabled');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.fundingsourceaddttext', true);	INSERT INTO ap_static_label_languages VALUES (null, 13, 1, 'Add Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.fundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 14, 1, 'Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.realm', true);	INSERT INTO ap_static_label_languages VALUES (null, 15, 1, 'Realm');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.fundingsourcelisttext', true);	INSERT INTO ap_static_label_languages VALUES (null, 16, 1, 'Funding Source List');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.errorrealm', true);	INSERT INTO ap_static_label_languages VALUES (null, 17, 1, 'Please select realm');
INSERT INTO ap_static_label VALUES (null, 'static.fundingsource.fundingsourceedittext', true);	INSERT INTO ap_static_label_languages VALUES (null, 18, 1, 'Update Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.common.status', true);	INSERT INTO ap_static_label_languages VALUES (null, 19, 1, 'Status');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetlist', true);	INSERT INTO ap_static_label_languages VALUES (null, 20, 1, 'Budget List');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 21, 1, 'Add Budget');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 22, 1, 'Update Budget');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budget', true);	INSERT INTO ap_static_label_languages VALUES (null, 23, 1, 'Budget');
INSERT INTO ap_static_label VALUES (null, 'static.budget.program', true);	INSERT INTO ap_static_label_languages VALUES (null, 24, 1, 'Program');
INSERT INTO ap_static_label VALUES (null, 'static.budget.subfundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 25, 1, 'Sub Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetamount', true);	INSERT INTO ap_static_label_languages VALUES (null, 26, 1, 'Budget Amount');
INSERT INTO ap_static_label VALUES (null, 'static.common.startdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 27, 1, 'Start Date');
INSERT INTO ap_static_label VALUES (null, 'static.common.stopdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 28, 1, 'End Date');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetamountdesc', true);	INSERT INTO ap_static_label_languages VALUES (null, 29, 1, 'Budget amount in USD');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetstartdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 30, 1, 'Start Date of Budget');
INSERT INTO ap_static_label VALUES (null, 'static.budget.budgetstopdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 31, 1, 'End Date of Budget');
INSERT INTO ap_static_label VALUES (null, 'static.manufacturer.manufacturerlist', true);	INSERT INTO ap_static_label_languages VALUES (null, 32, 1, 'Manufacturer List');
INSERT INTO ap_static_label VALUES (null, 'static.manufacturer.manufactureradd', true);	INSERT INTO ap_static_label_languages VALUES (null, 33, 1, 'Add Manufacturer');
INSERT INTO ap_static_label VALUES (null, 'static.manufacturer.manufactureredit', true);	INSERT INTO ap_static_label_languages VALUES (null, 34, 1, 'Update Manufacturer');
INSERT INTO ap_static_label VALUES (null, 'static.manufacturer.realm', true);	INSERT INTO ap_static_label_languages VALUES (null, 35, 1, 'Realm');
INSERT INTO ap_static_label VALUES (null, 'static.manufacturer.manufacturer', true);	INSERT INTO ap_static_label_languages VALUES (null, 36, 1, 'Manufacturer');
INSERT INTO ap_static_label VALUES (null, 'static.budget.fundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 37, 1, 'Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.language.languageadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 38, 1, 'Add Language');
INSERT INTO ap_static_label VALUES (null, 'static.language.languageedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 39, 1, 'Update Language');
INSERT INTO ap_static_label VALUES (null, 'static.language.languagelist', true);	INSERT INTO ap_static_label_languages VALUES (null, 40, 1, 'Language List');
INSERT INTO ap_static_label VALUES (null, 'static.language.language', true);	INSERT INTO ap_static_label_languages VALUES (null, 41, 1, 'Language');
INSERT INTO ap_static_label VALUES (null, 'static.country.countryadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 42, 1, 'Add Country');
INSERT INTO ap_static_label VALUES (null, 'static.country.countryedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 43, 1, 'Update Country');
INSERT INTO ap_static_label VALUES (null, 'static.country.countrylist', true);	INSERT INTO ap_static_label_languages VALUES (null, 44, 1, 'Country List');
INSERT INTO ap_static_label VALUES (null, 'static.country.country', true);	INSERT INTO ap_static_label_languages VALUES (null, 45, 1, 'Country Name (English)');
INSERT INTO ap_static_label VALUES (null, 'static.country.countrycode', true);	INSERT INTO ap_static_label_languages VALUES (null, 46, 1, 'Country Code');
INSERT INTO ap_static_label VALUES (null, 'static.country.language', true);	INSERT INTO ap_static_label_languages VALUES (null, 47, 1, 'Language');
INSERT INTO ap_static_label VALUES (null, 'static.country.currency', true);	INSERT INTO ap_static_label_languages VALUES (null, 48, 1, 'Currency');
INSERT INTO ap_static_label VALUES (null, 'static.datasource.datasourceadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 49, 1, 'Add Data Source');
INSERT INTO ap_static_label VALUES (null, 'static.datasource.datasourceedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 50, 1, 'Update Data Source');
INSERT INTO ap_static_label VALUES (null, 'static.datasource.datasourcelist', true);	INSERT INTO ap_static_label_languages VALUES (null, 51, 1, 'Data Source List');
INSERT INTO ap_static_label VALUES (null, 'static.datasource.datasource', true);	INSERT INTO ap_static_label_languages VALUES (null, 52, 1, 'Data Source');
INSERT INTO ap_static_label VALUES (null, 'static.datasource.datasourcetype', true);	INSERT INTO ap_static_label_languages VALUES (null, 53, 1, 'Data Source Type');
INSERT INTO ap_static_label VALUES (null, 'static.datasourcetype.datasourcetypeadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 54, 1, 'Add Data Source Type');
INSERT INTO ap_static_label VALUES (null, 'static.datasourcetype.datasourcetypeedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 55, 1, 'Update Data Source Type');
INSERT INTO ap_static_label VALUES (null, 'static.datasourcetype.datasourcetypelist', true);	INSERT INTO ap_static_label_languages VALUES (null, 56, 1, 'Data Source Type List');
INSERT INTO ap_static_label VALUES (null, 'static.datasourcetype.datasourcetype', true);	INSERT INTO ap_static_label_languages VALUES (null, 57, 1, 'Data Source Type');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currencyadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 58, 1, 'Add Currency');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currencyedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 59, 1, 'Update Currency');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currencylist', true);	INSERT INTO ap_static_label_languages VALUES (null, 60, 1, 'Currency List');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currency', true);	INSERT INTO ap_static_label_languages VALUES (null, 61, 1, 'Currency (English)');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currencycode', true);	INSERT INTO ap_static_label_languages VALUES (null, 62, 1, 'Currency Code');
INSERT INTO ap_static_label VALUES (null, 'static.currency.conversionrateusd', true);	INSERT INTO ap_static_label_languages VALUES (null, 63, 1, 'Conversion Rate to USD');
INSERT INTO ap_static_label VALUES (null, 'static.currency.currencysymbol', true);	INSERT INTO ap_static_label_languages VALUES (null, 64, 1, 'Currency Symbol');
INSERT INTO ap_static_label VALUES (null, 'static.message.budget.addFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 65, 1, 'Failed to add Budget "${budget}"');
INSERT INTO ap_static_label VALUES (null, 'static.actionCancelled', true);	INSERT INTO ap_static_label_languages VALUES (null, 66, 1, 'Action cancelled');
INSERT INTO ap_static_label VALUES (null, 'static.language.listFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 67, 1, 'Could not get Language list');
INSERT INTO ap_static_label VALUES (null, 'static.language.addSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 68, 1, 'Language "${language}" was sucessfully added');
INSERT INTO ap_static_label VALUES (null, 'static.language.addFailure', true);	INSERT INTO ap_static_label_languages VALUES (null, 69, 1, 'Failed to add Language "${language}"');
INSERT INTO ap_static_label VALUES (null, 'static.language.alreadyExists', true);	INSERT INTO ap_static_label_languages VALUES (null, 70, 1, 'Language "${language}" already exists');
INSERT INTO ap_static_label VALUES (null, 'static.language.updateSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 71, 1, 'Language "${language}" was sucessfully updated');
INSERT INTO ap_static_label VALUES (null, 'static.language.updateFailure', true);	INSERT INTO ap_static_label_languages VALUES (null, 72, 1, 'Failed to update Language "${language}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.budget.addSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 73, 1, 'Budget "${budget}" was successfully added');
INSERT INTO ap_static_label VALUES (null, 'static.message.budget.notFound', true);	INSERT INTO ap_static_label_languages VALUES (null, 74, 1, 'Could not find Budget "${budget}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.budget.updateFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 75, 1, 'Failed to update Budget "${budget}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.budget.updateSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 76, 1, 'Budget "${budget}" was successfully updated');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.addFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 77, 1, 'Failed to add Funding Source "${fundingSource}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.addSucccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 78, 1, 'Funding Source "${funding_source}" was successfully updated');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.listFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 79, 1, 'Could not get Funding Source list');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.listNotFound', true);	INSERT INTO ap_static_label_languages VALUES (null, 80, 1, 'No Funding Source found');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.updateFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 81, 1, 'Failed to update Funding Source "${fundingSource}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.fundingSource.updateSucccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 82, 1, 'Successfully updated Funding Source "${fundingSource}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.disabled', true);	INSERT INTO ap_static_label_languages VALUES (null, 83, 1, 'User account "${username}" is disabled');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.incorrectEmailUser', true);	INSERT INTO ap_static_label_languages VALUES (null, 84, 1, 'Incorrect email or username');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.invalidCredentials', true);	INSERT INTO ap_static_label_languages VALUES (null, 85, 1, 'Incorrect login or password');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.locked', true);	INSERT INTO ap_static_label_languages VALUES (null, 86, 1, 'User account is locked');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.noUser', true);	INSERT INTO ap_static_label_languages VALUES (null, 87, 1, 'Incorrect login or password');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.passwordExpired', true);	INSERT INTO ap_static_label_languages VALUES (null, 88, 1, 'Your password expired');
INSERT INTO ap_static_label VALUES (null, 'static.message.login.unauthorized', true);	INSERT INTO ap_static_label_languages VALUES (null, 89, 1, 'You don`t have permission to perform this operation or access this resource. Please contact the realm admin if you need access to this resource.');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.addedSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 90, 1, 'Role "${role}" added successfully');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.couldNotAddRole', true);	INSERT INTO ap_static_label_languages VALUES (null, 91, 1, 'Failed to add Role "${role}"');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.couldNotUpdateRole', true);	INSERT INTO ap_static_label_languages VALUES (null, 92, 1, 'Could not update Role');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.failedBfList', true);	INSERT INTO ap_static_label_languages VALUES (null, 93, 1, 'Could not get Business function list');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.failedRoleList', true);	INSERT INTO ap_static_label_languages VALUES (null, 94, 1, 'Could not get Role list');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.roleAlreadyExists', true);	INSERT INTO ap_static_label_languages VALUES (null, 95, 1, 'Role already exists');
INSERT INTO ap_static_label VALUES (null, 'static.message.role.updatedSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 96, 1, 'Role updated successfully');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.accountUnlocked', true);	INSERT INTO ap_static_label_languages VALUES (null, 97, 1, 'Account unlocked successfully and new password is sent to the registered email address');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.couldNotBeUnlocked', true);	INSERT INTO ap_static_label_languages VALUES (null, 98, 1, 'User could not be unlocked');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.disabled', true);	INSERT INTO ap_static_label_languages VALUES (null, 99, 1, 'User is disabled');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.failedPasswordUpdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 100, 1, 'Could not update password');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.failedUserAdd', true);	INSERT INTO ap_static_label_languages VALUES (null, 101, 1, 'Failed to add User');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.failedUserList', true);	INSERT INTO ap_static_label_languages VALUES (null, 102, 1, 'Failed to get user list');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.failedUserUpdate', true);	INSERT INTO ap_static_label_languages VALUES (null, 103, 1, 'Failed to update User');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.forgotPasswordSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 104, 1, 'Email with password reset link sent');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.forgotPasswordTokenError', true);	INSERT INTO ap_static_label_languages VALUES (null, 105, 1, 'Could not confirm Token');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.forgotPasswordTokenExpired', true);	INSERT INTO ap_static_label_languages VALUES (null, 106, 1, 'Token expired');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.forgotPasswordTokenFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 107, 1, 'Error while generating Token for forgot password');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.forgotPasswordTokenUsed', true);	INSERT INTO ap_static_label_languages VALUES (null, 108, 1, 'Token already used');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.incorrectPassword', true);	INSERT INTO ap_static_label_languages VALUES (null, 109, 1, 'Password is incorrect');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.logoutFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 110, 1, 'Could not logout');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.newUserTokenFailed', true);	INSERT INTO ap_static_label_languages VALUES (null, 111, 1, 'Could not generate a Token for User');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.notExist', true);	INSERT INTO ap_static_label_languages VALUES (null, 112, 1, 'User does not exists');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.passwordSame', true);	INSERT INTO ap_static_label_languages VALUES (null, 113, 1, 'New password cannot be the same as current password');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.passwordSuccess', true);	INSERT INTO ap_static_label_languages VALUES (null, 114, 1, 'Password updated successfully');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.tokenNotGenerated', true);	INSERT INTO ap_static_label_languages VALUES (null, 115, 1, 'User could not be unlocked as Token could not be generated');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.userCreated', true);	INSERT INTO ap_static_label_languages VALUES (null, 116, 1, 'User has been created and credentials link sent on email');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.usernameExists', true);	INSERT INTO ap_static_label_languages VALUES (null, 117, 1, 'Username already exists');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.userNotFound', true);	INSERT INTO ap_static_label_languages VALUES (null, 118, 1, 'User not found');
INSERT INTO ap_static_label VALUES (null, 'static.message.user.userUpdated', true);	INSERT INTO ap_static_label_languages VALUES (null, 119, 1, 'User updated successfully');
INSERT INTO ap_static_label VALUES (null, 'static.product.product', true);	INSERT INTO ap_static_label_languages VALUES (null, 120, 1, 'Product');
INSERT INTO ap_static_label VALUES (null, 'static.product.productadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 121, 1, 'Add Product');
INSERT INTO ap_static_label VALUES (null, 'static.product.productcategory', true);	INSERT INTO ap_static_label_languages VALUES (null, 122, 1, 'Product Category');
INSERT INTO ap_static_label VALUES (null, 'static.product.productedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 123, 1, 'Update Product');
INSERT INTO ap_static_label VALUES (null, 'static.product.productgenericname', true);	INSERT INTO ap_static_label_languages VALUES (null, 124, 1, 'Generic Name');
INSERT INTO ap_static_label VALUES (null, 'static.product.productlist', true);	INSERT INTO ap_static_label_languages VALUES (null, 125, 1, 'Product list');
INSERT INTO ap_static_label VALUES (null, 'static.product.realm', true);	INSERT INTO ap_static_label_languages VALUES (null, 126, 1, 'Realm');
INSERT INTO ap_static_label VALUES (null, 'static.product.unit', true);	INSERT INTO ap_static_label_languages VALUES (null, 127, 1, 'Forcasting Unit');
INSERT INTO ap_static_label VALUES (null, 'static.program.airfreightperc', true);	INSERT INTO ap_static_label_languages VALUES (null, 128, 1, 'Air Freight Percentage');
INSERT INTO ap_static_label VALUES (null, 'static.program.airfreightperctext', true);	INSERT INTO ap_static_label_languages VALUES (null, 129, 1, 'Air Freight Percentage');
INSERT INTO ap_static_label VALUES (null, 'static.program.approvetoshipleadtime', true);	INSERT INTO ap_static_label_languages VALUES (null, 130, 1, 'Approve To Shipped Lead Time');
INSERT INTO ap_static_label VALUES (null, 'static.program.approvetoshiptext', true);	INSERT INTO ap_static_label_languages VALUES (null, 131, 1, 'Enter approved to shipped lead time');
INSERT INTO ap_static_label VALUES (null, 'static.program.delivedtoreceivedleadtime', true);	INSERT INTO ap_static_label_languages VALUES (null, 132, 1, 'Delivered To Recived Lead Time');
INSERT INTO ap_static_label VALUES (null, 'static.program.delivertoreceivetext', true);	INSERT INTO ap_static_label_languages VALUES (null, 133, 1, 'Enter delivered to received lead time');
INSERT INTO ap_static_label VALUES (null, 'static.program.download', true);	INSERT INTO ap_static_label_languages VALUES (null, 134, 1, 'Download Program Data');
INSERT INTO ap_static_label VALUES (null, 'static.program.draftleadtext', true);	INSERT INTO ap_static_label_languages VALUES (null, 135, 1, 'Enter plan to draft lead time');
INSERT INTO ap_static_label VALUES (null, 'static.program.draftleadtime', true);	INSERT INTO ap_static_label_languages VALUES (null, 136, 1, 'Plan Draft Lead Time');
INSERT INTO ap_static_label VALUES (null, 'static.program.drafttosubmitleadtime', true);	INSERT INTO ap_static_label_languages VALUES (null, 137, 1, 'Draft To Submitted Lead Time');
INSERT INTO ap_static_label VALUES (null, 'static.program.drafttosubmittext', true);	INSERT INTO ap_static_label_languages VALUES (null, 138, 1, 'Enter draft to submitted lead time');
INSERT INTO ap_static_label VALUES (null, 'static.program.export', true);	INSERT INTO ap_static_label_languages VALUES (null, 139, 1, 'Export Program Data');
INSERT INTO ap_static_label VALUES (null, 'static.program.fileinput', true);	INSERT INTO ap_static_label_languages VALUES (null, 140, 1, 'File Input');
INSERT INTO ap_static_label VALUES (null, 'static.program.healtharea', true);	INSERT INTO ap_static_label_languages VALUES (null, 141, 1, 'Health Area');
INSERT INTO ap_static_label VALUES (null, 'static.program.import', true);	INSERT INTO ap_static_label_languages VALUES (null, 142, 1, 'Import Program Data');
INSERT INTO ap_static_label VALUES (null, 'static.program.monthfutureamc', true);	INSERT INTO ap_static_label_languages VALUES (null, 143, 1, 'Month In Future For AMC');
INSERT INTO ap_static_label VALUES (null, 'static.program.monthfutureamctext', true);	INSERT INTO ap_static_label_languages VALUES (null, 144, 1, 'Enter month in future for AMC');
INSERT INTO ap_static_label VALUES (null, 'static.program.monthpastamc', true);	INSERT INTO ap_static_label_languages VALUES (null, 145, 1, 'Month In Past For AMC');
INSERT INTO ap_static_label VALUES (null, 'static.program.monthpastamctext', true);	INSERT INTO ap_static_label_languages VALUES (null, 146, 1, 'Enter month in Past for AMC');
INSERT INTO ap_static_label VALUES (null, 'static.program.notes', true);	INSERT INTO ap_static_label_languages VALUES (null, 147, 1, 'Program Notes');
INSERT INTO ap_static_label VALUES (null, 'static.program.organisation', true);	INSERT INTO ap_static_label_languages VALUES (null, 148, 1, 'Organisation');
INSERT INTO ap_static_label VALUES (null, 'static.program.program', true);	INSERT INTO ap_static_label_languages VALUES (null, 149, 1, 'Program Name');
INSERT INTO ap_static_label VALUES (null, 'static.program.programadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 150, 1, 'Add Program');
INSERT INTO ap_static_label VALUES (null, 'static.program.programedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 151, 1, 'Update Program');
INSERT INTO ap_static_label VALUES (null, 'static.program.programlist', true);	INSERT INTO ap_static_label_languages VALUES (null, 152, 1, 'Program List');
INSERT INTO ap_static_label VALUES (null, 'static.program.programmanager', true);	INSERT INTO ap_static_label_languages VALUES (null, 153, 1, 'Program Manager');
INSERT INTO ap_static_label VALUES (null, 'static.program.programtext', true);	INSERT INTO ap_static_label_languages VALUES (null, 154, 1, 'Enter program name');
INSERT INTO ap_static_label VALUES (null, 'static.program.realm', true);	INSERT INTO ap_static_label_languages VALUES (null, 155, 1, 'Realm');
INSERT INTO ap_static_label VALUES (null, 'static.program.realmcountry', true);	INSERT INTO ap_static_label_languages VALUES (null, 156, 1, 'Country');
INSERT INTO ap_static_label VALUES (null, 'static.program.region', true);	INSERT INTO ap_static_label_languages VALUES (null, 157, 1, 'Region');
INSERT INTO ap_static_label VALUES (null, 'static.program.seafreightperc', true);	INSERT INTO ap_static_label_languages VALUES (null, 158, 1, 'Sea Freight Percentage');
INSERT INTO ap_static_label VALUES (null, 'static.program.seafreightperctext', true);	INSERT INTO ap_static_label_languages VALUES (null, 159, 1, 'Sea Freight Percentage');
INSERT INTO ap_static_label VALUES (null, 'static.program.submittoapproveleadtime', true);	INSERT INTO ap_static_label_languages VALUES (null, 160, 1, 'Submitted To Approved Lead Time');
INSERT INTO ap_static_label VALUES (null, 'static.program.submittoapprovetext', true);	INSERT INTO ap_static_label_languages VALUES (null, 161, 1, 'Enter submited to approved lead time');
INSERT INTO ap_static_label VALUES (null, 'static.region.country', true);	INSERT INTO ap_static_label_languages VALUES (null, 162, 1, 'Country');
INSERT INTO ap_static_label VALUES (null, 'static.region.region', true);	INSERT INTO ap_static_label_languages VALUES (null, 163, 1, 'Region');
INSERT INTO ap_static_label VALUES (null, 'static.region.regionadd', true);	INSERT INTO ap_static_label_languages VALUES (null, 164, 1, 'Add Region');
INSERT INTO ap_static_label VALUES (null, 'static.region.regionedit', true);	INSERT INTO ap_static_label_languages VALUES (null, 165, 1, 'Update Region');
INSERT INTO ap_static_label VALUES (null, 'static.region.regionlist', true);	INSERT INTO ap_static_label_languages VALUES (null, 166, 1, 'Region List');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.errorfundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 167, 1, 'Please select Funding source');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.fundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 168, 1, 'Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.subfundingsource', true);	INSERT INTO ap_static_label_languages VALUES (null, 169, 1, 'Sub Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.subfundingsourceaddttext', true);	INSERT INTO ap_static_label_languages VALUES (null, 170, 1, 'Add Sub Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.subfundingsourceedittext', true);	INSERT INTO ap_static_label_languages VALUES (null, 171, 1, 'Update Funding Source');
INSERT INTO ap_static_label VALUES (null, 'static.subfundingsource.subfundingsourcelisttext', true);	INSERT INTO ap_static_label_languages VALUES (null, 172, 1, 'Sub Funding Source List');
INSERT INTO ap_static_label VALUES (null, 'static.unkownError', true);	INSERT INTO ap_static_label_languages VALUES (null, 173, 1, 'Unkown error occurred');


SET FOREIGN_KEY_CHECKS = 1;
-- end attached script 'script1'
