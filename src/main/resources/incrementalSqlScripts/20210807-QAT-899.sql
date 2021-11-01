SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ct_supply_plan_problem_report`;
DROP TABLE IF EXISTS `ct_supply_plan_problem_report_trans`;
DROP TABLE IF EXISTS `ct_supply_plan_shipment_batch_info`;
DROP TABLE IF EXISTS `ct_supply_plan_shipment`;
DROP TABLE IF EXISTS `ct_supply_plan_inventory_batch_info`;
DROP TABLE IF EXISTS `ct_supply_plan_inventory`;
DROP TABLE IF EXISTS `ct_supply_plan_consumption_batch_info`;
DROP TABLE IF EXISTS `ct_supply_plan_consumption`;
DROP TABLE IF EXISTS `ct_supply_plan_commit_request`;

CREATE TABLE `fasp`.`ct_supply_plan_commit_request` (
  `COMMIT_REQUEST_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `PROGRAM_ID` INT(10) UNSIGNED NOT NULL,
  `COMMITTED_VERSION_ID` INT(10) UNSIGNED NOT NULL,
  `VERSION_TYPE_ID` int(10) unsigned NULL,
  `NOTES` text COLLATE utf8_bin,  
  `SAVE_DATA`  TINYINT UNSIGNED NOT NULL,
  `CREATED_BY` INT(10) UNSIGNED NOT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `STATUS` INT(10) UNSIGNED NOT NULL,
  `COMPLETED_DATE` DATETIME NULL,
  `STARTED_DATE` DATETIME NULL, 
  `VERSION_ID` INT(10) NULL,
  `FAILED_REASON` TEXT NULL,
  PRIMARY KEY (`COMMIT_REQUEST_ID`),
  INDEX `fk_ct_supply_plan_commit_request_programId_idx` (`PROGRAM_ID` ASC),
  INDEX `fk_ct_supply_plan_commit_request_createdBy_idx` (`CREATED_BY` ASC),
  INDEX `fk_ct_sp_commit_request_ap_version_type1_idx` (`VERSION_TYPE_ID`),
  CONSTRAINT `fk_ct_supply_plan_commit_request_programId`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `fasp`.`rm_program` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_supply_plan_commit_request_createdBy`
    FOREIGN KEY (`CREATED_BY`)
    REFERENCES `fasp`.`us_user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_commit_request_ap_version_type1_idx` 
    FOREIGN KEY (`VERSION_TYPE_ID`) 
    REFERENCES `ap_version_type` (`VERSION_TYPE_ID`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION);

CREATE TABLE `ct_supply_plan_consumption` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `CONSUMPTION_ID` INT UNSIGNED NULL, 
  `REGION_ID` INT(10) UNSIGNED NOT NULL, 
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, 
  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, 
  `CONSUMPTION_DATE` DATE NOT NULL, 
  `ACTUAL_FLAG` TINYINT UNSIGNED NOT NULL, 
  `RCPU_QTY` DOUBLE UNSIGNED NOT NULL, 
  `QTY` DOUBLE UNSIGNED NOT NULL, 
  `DAYS_OF_STOCK_OUT` INT UNSIGNED NOT NULL, 
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, 
  `NOTES` TEXT NULL, 
  `CREATED_BY` INT UNSIGNED NOT NULL, 
  `CREATED_DATE` DATETIME NOT NULL, 
  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, 
  `LAST_MODIFIED_DATE` DATETIME NOT NULL, 
  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, 
  `VERSION_ID` INT(10) NULL, 
  PRIMARY KEY (`ID`), 
  INDEX `fk_ct_sp_consumption_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_consumption_1_idx` (`CONSUMPTION_ID` ASC), 
  INDEX `fk_ct_sp_consumption_2_idx` (`REGION_ID` ASC), 
  INDEX `fk_ct_sp_consumption_3_idx` (`PLANNING_UNIT_ID` ASC), 
  INDEX `fk_ct_sp_consumption_4_idx` (`DATA_SOURCE_ID` ASC),
  INDEX `fk_ct_sp_consumption_5_idx` (`VERSION_ID` ASC),
  CONSTRAINT `fk_ct_sp_consumption_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_consumption_1`
    FOREIGN KEY (`CONSUMPTION_ID`)
    REFERENCES `fasp`.`rm_consumption` (`CONSUMPTION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_consumption_2`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `fasp`.`rm_region` (`REGION_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_consumption_3`
    FOREIGN KEY (`PLANNING_UNIT_ID`)
    REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_consumption_4`
    FOREIGN KEY (`DATA_SOURCE_ID`)
    REFERENCES `fasp`.`rm_data_source` (`DATA_SOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `ct_supply_plan_consumption_batch_info` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `PARENT_ID` INT(10) UNSIGNED NOT NULL, 
  `CONSUMPTION_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, 
  `CONSUMPTION_TRANS_ID` INT(10) UNSIGNED NULL, 
  `BATCH_ID` INT(10) NOT NULL, 
  `BATCH_NO` VARCHAR(26) NOT NULL,
  `AUTO_GENERATED` TINYINT(1) UNSIGNED NOT NULL,
  `BATCH_CREATED_DATE` DATETIME NOT NULL,
  `EXPIRY_DATE` DATE NOT NULL,  
  `BATCH_QTY` DECIMAL(24,4) UNSIGNED NOT NULL, 
  PRIMARY KEY (`ID`), 
  INDEX `fk_ct_sp_cbi_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_cbi_2_idx` (`CONSUMPTION_TRANS_BATCH_INFO_ID` ASC), 
  INDEX `fk_ct_sp_cbi_3_idx` (`BATCH_ID` ASC),
  CONSTRAINT `fk_ct_sp_cbi_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `ct_supply_plan_inventory` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `INVENTORY_ID` INT UNSIGNED NULL, 
  `INVENTORY_DATE` DATE NOT NULL, 
  `REGION_ID` INT(10) UNSIGNED NULL, 
  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, 
  `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL, 
  `ADJUSTMENT_QTY` BIGINT(20) NULL, 
  `EXPECTED_BAL` BIGINT(20) NULL, 
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, 
  `NOTES` TEXT NULL, 
  `CREATED_BY` INT UNSIGNED NOT NULL, 
  `CREATED_DATE` DATETIME NOT NULL, 
  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, 
  `LAST_MODIFIED_DATE` DATETIME NOT NULL, 
  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, 
  `VERSION_ID` INT(10) NULL, 
  PRIMARY KEY (`ID`), 
  INDEX `fk_ct_sp_inventory_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_supply_planinventory_1_idx` (`INVENTORY_ID` ASC), 
  INDEX `fk_ct_sp_inventory_2_idx` (`REGION_ID` ASC), 
  INDEX `fk_ct_sp_inventory_3_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID` ASC), 
  INDEX `fk_ct_sp_inventory_4_idx` (`DATA_SOURCE_ID` ASC), 
  INDEX `fk_ct_sp_inventory_5_idx` (`VERSION_ID` ASC),
  CONSTRAINT `fk_ct_sp_inventory_commitRequestId`
  FOREIGN KEY (`COMMIT_REQUEST_ID`)
  REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);


CREATE TABLE `ct_supply_plan_inventory_batch_info` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
`COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `PARENT_ID` INT(10) UNSIGNED NOT NULL, 
  `INVENTORY_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, 
  `INVENTORY_TRANS_ID` INT(10) UNSIGNED NULL, 
  `BATCH_ID` INT(10) NOT NULL, 
  `BATCH_NO` VARCHAR(26) NOT NULL,
  `AUTO_GENERATED` TINYINT(1) UNSIGNED NOT NULL,
  `BATCH_CREATED_DATE` DATETIME NOT NULL,
  `EXPIRY_DATE` DATE NOT NULL,  
  `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL, 
  `ADJUSTMENT_QTY` BIGINT(20) NULL, 
  PRIMARY KEY (`ID`), 
    INDEX `fk_ct_sp_ibi_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_ibi_1_idx` (`INVENTORY_TRANS_ID` ASC), 
  INDEX `fk_ct_sp_ibi_2_idx` (`INVENTORY_TRANS_BATCH_INFO_ID` ASC), 
  INDEX `fk_ct_sp_ibi_3_idx` (`BATCH_ID` ASC),
    CONSTRAINT `fk_ct_sp_ibi_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
CREATE TABLE `ct_supply_plan_shipment` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
`COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `SHIPMENT_ID` INT(10) UNSIGNED NULL, 
  `PARENT_SHIPMENT_ID` INT(10) UNSIGNED NULL, 
  `SUGGESTED_QTY` BIGINT(20) UNSIGNED NULL, 
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NULL, 
  `FUNDING_SOURCE_ID` INT(10) UNSIGNED NULL, 
  `BUDGET_ID` INT(10) UNSIGNED NULL, 
  `ACCOUNT_FLAG` TINYINT(1) UNSIGNED NULL, 
  `ERP_FLAG` TINYINT(1) UNSIGNED NULL, 
  `CURRENCY_ID` INT(10) UNSIGNED NULL, 
  `CONVERSION_RATE_TO_USD` DECIMAL(12,2) UNSIGNED NULL, 
  `EMERGENCY_ORDER` TINYINT(1) UNSIGNED NOT NULL, 
  `LOCAL_PROCUREMENT` TINYINT(1) UNSIGNED NOT NULL, 
  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, 
  `EXPECTED_DELIVERY_DATE` DATE NOT NULL, 
  `PROCUREMENT_UNIT_ID` INT(10) UNSIGNED NULL, 
  `SUPPLIER_ID` INT(10) UNSIGNED NULL, 
  `SHIPMENT_QTY` BIGINT(20) UNSIGNED NULL, 
  `RATE` DECIMAL(12,4) NOT NULL, 
  `PRODUCT_COST` DECIMAL(24,4) UNSIGNED NOT NULL, 
  `SHIPMENT_MODE` VARCHAR(4) NOT NULL, 
  `FREIGHT_COST` DECIMAL(24,4) UNSIGNED NOT NULL, 
  `PLANNED_DATE` DATE NULL, 
  `SUBMITTED_DATE` DATE NULL, 
  `APPROVED_DATE` DATE NULL, 
  `SHIPPED_DATE` DATE NULL, 
  `ARRIVED_DATE` DATE NULL, 
  `RECEIVED_DATE` DATE NULL, 
  `SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL, 
  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, 
  `NOTES` TEXT NULL, 
  `ORDER_NO` VARCHAR(50) NULL, 
  `PRIME_LINE_NO` VARCHAR(10) NULL, 
  `CREATED_BY` INT UNSIGNED NOT NULL, 
  `CREATED_DATE` DATETIME NOT NULL, 
  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, 
  `LAST_MODIFIED_DATE` DATETIME NOT NULL, 
  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1, 
  `VERSION_ID` INT(10) NULL, 
  PRIMARY KEY (`ID`), 
INDEX `fk_ct_sp_shipment_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_shipment_1_idx` (`SHIPMENT_ID` ASC), 
  INDEX `fk_ct_sp_shipment_2_idx` (`PLANNING_UNIT_ID` ASC), 
  INDEX `fk_ct_sp_shipment_3_idx` (`PROCUREMENT_UNIT_ID` ASC), 
  INDEX `fk_ct_sp_shipment_4_idx` (`SUPPLIER_ID` ASC), 
  INDEX `fk_ct_sp_shipment_5_idx` (`SHIPMENT_STATUS_ID` ASC), 
  INDEX `fk_ct_sp_shipment_6_idx` (`ORDER_NO` ASC), 
  INDEX `fk_ct_sp_shipment_7_idx` (`PRIME_LINE_NO` ASC), 
  INDEX `fk_ct_sp_shipment_8_idx` (`DATA_SOURCE_ID` ASC), 
  INDEX `fk_ct_sp_shipment_9_idx` (`VERSION_ID` ASC),
  INDEX `fk_ct_sp_shipment_10_idx` (`PROCUREMENT_AGENT_ID` ASC), 
  INDEX `fk_ct_sp_shipment_11_idx` (`FUNDING_SOURCE_ID` ASC), 
  INDEX `fk_ct_sp_shipment_12_idx` (`BUDGET_ID` ASC) ,
CONSTRAINT `fk_ct_sp_shipment_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE `ct_supply_plan_shipment_batch_info` ( 
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `PARENT_ID` INT(10) UNSIGNED NOT NULL, 
  `SHIPMENT_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, 
  `SHIPMENT_TRANS_ID` INT(10) UNSIGNED NULL, 
  `BATCH_ID` INT(10) NOT NULL, 
  `BATCH_NO` VARCHAR(26) NOT NULL,
  `AUTO_GENERATED` TINYINT(1) UNSIGNED NOT NULL,
  `BATCH_CREATED_DATE` DATETIME NOT NULL,
  `EXPIRY_DATE` DATE NOT NULL,  
  `BATCH_SHIPMENT_QTY` BIGINT(20) UNSIGNED NOT NULL, 
  PRIMARY KEY (`ID`), 
INDEX `fk_ct_sp_sbi_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_sbi_1_idx` (`SHIPMENT_TRANS_ID` ASC), 
  INDEX `fk_ct_sp_sbi_2_idx` (`SHIPMENT_TRANS_BATCH_INFO_ID` ASC), 
  INDEX `fk_ct_sp_sbi_3_idx` (`BATCH_ID` ASC),
CONSTRAINT `fk_ct_sp_sbi_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `ct_supply_plan_problem_report` (
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `PROBLEM_REPORT_ID` int(10) unsigned NULL,
  `REALM_PROBLEM_ID` int(10) unsigned NOT NULL,
  `PROGRAM_ID` int(10) unsigned NOT NULL,
  `VERSION_ID` int(10) unsigned NOT NULL,
  `PROBLEM_TYPE_ID` int(10) unsigned NOT NULL,
  `PROBLEM_STATUS_ID` int(10) unsigned NOT NULL,
  `DATA1` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DATA2` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DATA3` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DATA4` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DATA5` text CHARACTER SET utf8,
  `REVIEWED` TINYINT(1) UNSIGNED NOT NULL,
  `REVIEW_NOTES` TEXT NULL,
  `REVIEWED_DATE` DATETIME NULL,
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int(10) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_ct_sp_problem_report_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_problem_report_rm_realm_problem1_idx` (`REALM_PROBLEM_ID`),
  INDEX `fk_ct_sp_problem_report_rm_program1_idx` (`PROGRAM_ID`),
  INDEX `fk_ct_sp_problem_report_ap_problem_type1_idx` (`PROBLEM_TYPE_ID`),
  INDEX `fk_ct_sp_problem_report_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  INDEX `fk_ct_sp_problem_report_us_user1_idx` (`CREATED_BY`),
  INDEX `fk_ct_sp_problem_report_us_user2_idx` (`LAST_MODIFIED_BY`),
CONSTRAINT `fk_ct_sp_problem_report_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_problem_type1` FOREIGN KEY (`PROBLEM_TYPE_ID`) REFERENCES `ap_problem_type` (`PROBLEM_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_rm_program1` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_rm_realm_problem1` FOREIGN KEY (`REALM_PROBLEM_ID`) REFERENCES `rm_realm_problem` (`REALM_PROBLEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_us_user2` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `ct_supply_plan_problem_report_trans` (
  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `PARENT_ID` INT(10) UNSIGNED NOT NULL, 
  `COMMIT_REQUEST_ID` INT UNSIGNED NOT NULL, 
  `PROBLEM_REPORT_TRANS_ID` int(10) unsigned NULL,
  `NOTES` TEXT NULL, 
  `PROBLEM_STATUS_ID` int(10) unsigned NOT NULL,
  `REVIEWED` TINYINT(1) UNSIGNED NOT NULL,
  `CREATED_BY` int(10) unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_ct_sp_problem_report_trans_commitRequestId_idx` (`COMMIT_REQUEST_ID` ASC), 
  INDEX `fk_ct_sp_problem_report_trans_ap_problem_status1_idx` (`PROBLEM_STATUS_ID`),
  INDEX `fk_ct_sp_problem_report_trans_us_user1_idx` (`CREATED_BY`),
CONSTRAINT `fk_ct_sp_problem_report_trans_commitRequestId`
    FOREIGN KEY (`COMMIT_REQUEST_ID`)
    REFERENCES `fasp`.`ct_supply_plan_commit_request` (`COMMIT_REQUEST_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_trans_ap_problem_status1` FOREIGN KEY (`PROBLEM_STATUS_ID`) REFERENCES `ap_problem_status` (`PROBLEM_STATUS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ct_sp_problem_report_trans_us_user1` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
SET FOREIGN_KEY_CHECKS=1;

ALTER TABLE `fasp`.`ct_supply_plan_commit_request` CHANGE `COMMITTED_VERSION_ID` `COMMITTED_VERSION_ID` INT(10) NOT NULL; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.readonly','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Commit currently in process. Data entry frozen until commit is completed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : validation en cours. La saisie des données est gelée jusqu`à ce que la validation soit terminée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Confirmar actualmente en proceso. La entrada de datos se congela hasta que se completa la confirmación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: Confirmação atualmente em andamento. Entrada de dados congelada até que a confirmação seja concluída.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.compareData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Compare Data');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comparer les données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Comparar datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Comparar dados');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.sendLocalToServer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Send local version to server');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envoyer la version locale au serveur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Enviar la versión local al servidor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Envie a versão local para o servidor');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.serverProcessing','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Server Processing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Traitement du serveur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Procesamiento del servidor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Processamento de Servidor');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.upgradeLocalToLatest','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Upgrade local to latest version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettre à niveau local vers la dernière version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar local a la última versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualize local para a versão mais recente');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.sendingDataToServer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sending data to server');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envoi de données au serveur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envío de datos al servidor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enviando dados para o servidor');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.commitFailed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'An error occured while processing the supply plan. Please try again');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une erreur s`est produite lors du traitement du plan d`approvisionnement. Veuillez réessayer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocurrió un error al procesar el plan de suministros. Inténtalo de nuevo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocorreu um erro ao processar o plano de abastecimento. Por favor, tente novamente');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.resolvedConflictsSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conflicts resolved successfully.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conflits résolus avec succès.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conflictos resueltos con éxito.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conflitos resolvidos com sucesso.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.sendLocalToServerCompleted','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sending local version to server completed successfully. Server processing started.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`envoi de la version locale au serveur s`est terminé avec succès. Le traitement du serveur a commencé.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El envío de la versión local al servidor se completó correctamente. Se inició el procesamiento del servidor.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O envio da versão local para o servidor foi concluído com sucesso. O processamento do servidor foi iniciado.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.commitVersion.serverProcessingCompleted','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Server processing completed successfully. Upgrading the local version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le traitement du serveur s`est terminé avec succès. Mise à niveau de la version locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El procesamiento del servidor se completó correctamente. Actualización de la versión local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O processamento do servidor foi concluído com sucesso. Atualizando a versão local');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.syncProgram.laterVersionAvailable','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For program {{programCode}}, there is later version available do you want to load the lastest version and delete this version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour le programme {{programCode}}, une version ultérieure est disponible voulez-vous charger la dernière version et supprimer cette version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el programa {{programCode}}, hay una versión posterior disponible. ¿Desea cargar la última versión y eliminar esta versión?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o programa {{programCode}}, há uma versão posterior disponível. Você deseja carregar a última versão e excluir esta versão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.syncProgram.programwithsameversion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For program {{programCode}}, the latest version number already exists in the local machine. Do you want to overwrite that program with the new data?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour le programme {{programCode}}, le dernier numéro de version existe déjà sur la machine locale. Voulez-vous écraser ce programme avec les nouvelles données ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el programa {{programCode}}, el número de versión más reciente ya existe en la máquina local. ¿Quiere sobrescribir ese programa con los nuevos datos?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o programa {{programCode}}, o número da versão mais recente já existe na máquina local. Você deseja sobrescrever esse programa com os novos dados?');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.syncProgram.confirmLoad','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For program {{programCode}}, You have modified the local latest version of this program.\n\nLocal changes not committed to the server will not be saved. Are you sure you want to overwrite local program?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour le programme {{programCode}}, vous avez modifié la dernière version locale de ce programme.\n\nLes modifications locales non validées sur le serveur ne seront pas enregistrées. Voulez-vous vraiment écraser le programme local ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el programa {{programCode}}, ha modificado la última versión local de este programa.\n\nLos cambios locales que no se hayan confirmado en el servidor no se guardarán. ¿Está seguro de que desea sobrescribir el programa local?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o programa {{programCode}}, você modificou a última versão local deste programa.\n\nAs alterações locais não confirmadas para o servidor não serão salvas. Tem certeza de que deseja substituir o programa local?');-- pr