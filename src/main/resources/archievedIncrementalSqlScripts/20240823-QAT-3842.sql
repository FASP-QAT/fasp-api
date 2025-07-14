CREATE TABLE IF NOT EXISTS `rm_procurement_agent_forecasting_unit` ( 
  `FORECASTING_UNIT_PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `FORECASTING_UNIT_ID` INT(10) UNSIGNED NOT NULL, 
  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NOT NULL, 
  `SKU_CODE` VARCHAR(9) NOT NULL, 
  PRIMARY KEY (`FORECASTING_UNIT_PROCUREMENT_AGENT_ID`), 
  KEY `FK_rm_procurement_agent_forecasting_unit_fu` (`FORECASTING_UNIT_ID`), 
  KEY `FK_rm_procurement_agent_forecasting_unit_pa` (`PROCUREMENT_AGENT_ID`), 
  CONSTRAINT `FK_rm_procurement_agent_forecasting_unit_fu` FOREIGN KEY (`FORECASTING_UNIT_ID`) REFERENCES `rm_forecasting_unit` (`FORECASTING_UNIT_ID`), 
  CONSTRAINT `FK_rm_procurement_agent_forecasting_unit_pa` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` 
CHANGE COLUMN `FORECASTING_UNIT_PROCUREMENT_AGENT_ID` `PROCUREMENT_AGENT_FORECASTING_UNIT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT ;

ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` 
ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NOT NULL AFTER `SKU_CODE`,
ADD COLUMN `CREATED_BY` INT UNSIGNED NOT NULL AFTER `ACTIVE`,
ADD COLUMN `CREATED_DATE` DATETIME NOT NULL AFTER `CREATED_BY`,
ADD COLUMN `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL AFTER `CREATED_DATE`,
ADD COLUMN `LAST_MODIFIED_DATE` DATETIME NOT NULL AFTER `LAST_MODIFIED_BY`,
ADD INDEX `fk_rm_procurement_agent_forecasting_unit_cb_idx` (`CREATED_BY` ASC) VISIBLE,
ADD INDEX `fk_rm_procurement_agent_forecasting_unit_lmb_idx` (`LAST_MODIFIED_BY` ASC) VISIBLE;
;
ALTER TABLE `fasp`.`rm_procurement_agent_forecasting_unit` 
ADD CONSTRAINT `fk_rm_procurement_agent_forecasting_unit_cb`
  FOREIGN KEY (`CREATED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rm_procurement_agent_forecasting_unit_lmb`
  FOREIGN KEY (`LAST_MODIFIED_BY`)
  REFERENCES `fasp`.`us_user` (`USER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

insert into ap_label values(null,"Map Forecasting Unit","","","",1,now(),1,now(),24);
select max(l.LABEL_ID) into @max1 from ap_label l ;
insert into us_business_function values ('ROLE_BF_MAP_FORECASTING_UNIT',@max1,1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_INTERNAL_USER','ROLE_BF_MAP_FORECASTING_UNIT',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_REALM_ADMIN','ROLE_BF_MAP_FORECASTING_UNIT',1,now(),1,now());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.mapForecastingUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Map Forecasting Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de prévision cartographique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de pronóstico de mapas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Map Forecasting Unit');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.procurementAgentForecastingUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Forecasting Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unité de prévision de l`agent d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidad de pronóstico del agente de adquisiciones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidade de Previsão do Agente de Compras');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.planningUnit.duplicateForecastingUnit','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate Forecasting Unit Details Found');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails de l`unité de prévision en double trouvés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se encontraron detalles duplicados de la unidad de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes da Unidade de Previsão Duplicados Encontrados');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.pafu.noNewChangesFound','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No new changes found');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun nouveau changement trouvé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se encontraron cambios nuevos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma nova alteração encontrada');-- pr