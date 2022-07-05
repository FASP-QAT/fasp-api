CREATE TABLE `rm_procurement_agent_type` (
  `PROCUREMENT_AGENT_TYPE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `REALM_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_TYPE_CODE` varchar(10) NOT NULL,
  `LABEL_ID` int unsigned NOT NULL,
  `ACTIVE` tinyint unsigned NOT NULL,
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROCUREMENT_AGENT_TYPE_ID`),
  UNIQUE KEY `unq_rm_procurement_agent_type_codeRealm` (`REALM_ID`,`PROCUREMENT_AGENT_TYPE_CODE`),
  KEY `fk_rm_procurement_agent_type_realmId_idx` (`REALM_ID`),
  KEY `fk_rm_procurement_agent_type_labelId_idx` (`LABEL_ID`),
  KEY `fk_rm_procurement_agent_type_createdBy_idx` (`CREATED_BY`),
  KEY `fk_rm_procurement_agent_type_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_procurement_agent_type_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_rm_procurement_agent_type_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`),
  CONSTRAINT `fk_rm_procurement_agent_type_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_rm_procurement_agent_type_realmId` FOREIGN KEY (`REALM_ID`) REFERENCES `rm_realm` (`REALM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_procurement_agent_type` AS
    SELECT 
        `pat`.`PROCUREMENT_AGENT_TYPE_ID` AS `PROCUREMENT_AGENT_TYPE_ID`,
        `pat`.`REALM_ID` AS `REALM_ID`,
        `pat`.`PROCUREMENT_AGENT_TYPE_CODE` AS `PROCUREMENT_AGENT_TYPE_CODE`,
        `pat`.`LABEL_ID` AS `LABEL_ID`,
        `pat`.`ACTIVE` AS `ACTIVE`,
        `pat`.`CREATED_BY` AS `CREATED_BY`,
        `pat`.`CREATED_DATE` AS `CREATED_DATE`,
        `pat`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `pat`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_procurement_agent_type` `pat`
        LEFT JOIN `ap_label` `l` ON ((`pat`.`LABEL_ID` = `l`.`LABEL_ID`)));


ALTER TABLE `fasp`.`rm_procurement_agent` ADD COLUMN `PROCUREMENT_AGENT_TYPE_ID` INT UNSIGNED NULL AFTER `REALM_ID`, ADD INDEX `fk_rm_procurement_agent_procurementAgentType_idx` (`PROCUREMENT_AGENT_TYPE_ID` ASC);

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_procurement_agent` AS
    SELECT 
        `pa`.`PROCUREMENT_AGENT_ID` AS `PROCUREMENT_AGENT_ID`,
        `pa`.`REALM_ID` AS `REALM_ID`,
        `pa`.`PROCUREMENT_AGENT_TYPE_ID` AS `PROCUREMENT_AGENT_TYPE_ID`,
        `pa`.`PROCUREMENT_AGENT_CODE` AS `PROCUREMENT_AGENT_CODE`,
        `pa`.`COLOR_HTML_CODE` AS `COLOR_HTML_CODE`,
        `pa`.`LABEL_ID` AS `LABEL_ID`,
        `pa`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `pa`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `pa`.`ACTIVE` AS `ACTIVE`,
        `pa`.`CREATED_BY` AS `CREATED_BY`,
        `pa`.`CREATED_DATE` AS `CREATED_DATE`,
        `pa`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `pa`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pal`.`LABEL_EN` AS `LABEL_EN`,
        `pal`.`LABEL_FR` AS `LABEL_FR`,
        `pal`.`LABEL_SP` AS `LABEL_SP`,
        `pal`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_procurement_agent` `pa`
        LEFT JOIN `ap_label` `pal` ON ((`pa`.`LABEL_ID` = `pal`.`LABEL_ID`)));

INSERT INTO `fasp`.`ap_label_source` (`SOURCE_ID`, `SOURCE_DESC`, `SOURCE_TEXT`) VALUES (55, 'rm_procurement_agent_type', 'Procurement Agent Type');


-- TODO
-- Add Scripts to add the Procurement Agent Types
insert into ap_label values (null, 'Faith based donors', null, null, null, 1, now(), 1, now(), 55);
INSERT INTO rm_procurement_agent_type VALUES (null, 1, 'FBD', LAST_INSERT_ID(), 1, 1, now(), 1, now() );


ALTER TABLE `fasp`.`rm_procurement_agent` ADD INDEX `fk_rm_procurement_agent_procurementAgentTypeId_idx` (`PROCUREMENT_AGENT_TYPE_ID` ASC) ;

ALTER TABLE `fasp`.`rm_procurement_agent` 
ADD CONSTRAINT `fk_rm_procurement_agent_procurementAgentTypeId`
  FOREIGN KEY (`PROCUREMENT_AGENT_TYPE_ID`)
  REFERENCES `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- TODO
-- Match up the Procurement Agents to the Types
UPDATE rm_procurement_agent p set p.PROCUREMENT_AGENT_TYPE_ID=1;
ALTER TABLE `fasp`.`rm_procurement_agent` CHANGE COLUMN `PROCUREMENT_AGENT_TYPE_ID` `PROCUREMENT_AGENT_TYPE_ID` INT UNSIGNED NOT NULL;

CREATE TABLE `rm_program_procurement_agent` (
  `PROGRAM_ID` int unsigned NOT NULL,
  `PROCUREMENT_AGENT_ID` int unsigned NOT NULL,
  `LAST_MODIFIED_BY` int unsigned NOT NULL,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PROGRAM_ID`,`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_program_procurement_agent_programId_idx` (`PROGRAM_ID`),
  KEY `fk_rm_program_procurement_agent_procurementAgentId_idx` (`PROCUREMENT_AGENT_ID`),
  KEY `fk_rm_program_procurement_agent_lastModifiedBy_idx` (`LAST_MODIFIED_BY`),
  CONSTRAINT `fk_rm_program_procurement_agent_lastModifiedBy` FOREIGN KEY (`LAST_MODIFIED_BY`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_rm_program_procurement_agent_procurementAgentId` FOREIGN KEY (`PROCUREMENT_AGENT_ID`) REFERENCES `rm_procurement_agent` (`PROCUREMENT_AGENT_ID`),
  CONSTRAINT `fk_rm_program_procurement_agent_programId` FOREIGN KEY (`PROGRAM_ID`) REFERENCES `rm_program` (`PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dashboard.procurementagentType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type');-- en

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dashboard.procurementagenttypeheader','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type d`agent d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de Agente de Aquisições');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de agente de compras');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dashboard.procurementagenttype','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Type d`agent d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tipo de Agente de Aquisições');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tipo de agente de compras');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementagenttype.codetext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type display name is required');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nom à afficher du type d`agent d`approvisionnement est requis');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O nome de exibição do tipo de agente de compras é obrigatório');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El nombre para mostrar del tipo de agente de adquisiciones es obligatorio');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementAgenTtype.procurementagenttypenametext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type name is required');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nom du type d`agent d`approvisionnement est requis');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O nome do tipo de agente de aquisição é obrigatório');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El nombre del tipo de agente de compras es obligatorio');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementagenttype.procurementtypename','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du type d`agent d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do tipo de agente de aquisição');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del tipo de agente de compras');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementagenttype.procurementagenttypecode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type display name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom d`affichage du type d`agent d`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome de exibição do tipo de agente de compras');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre para mostrar del tipo de agente de compras');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementagent.procurementagenttypetext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Type is required');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le type d`agent d`approvisionnement est requis');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O tipo de agente de aquisição é obrigatório');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se requiere el tipo de agente de compras');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementagent.programtext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program is required');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le programme est requis');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O programa é obrigatório');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se requiere programa');-- sp

INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'GHSC-PSM', 109, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'PPM-GF', 110, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'UNDP', 111, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'GHSC-RTK', 451, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'Govt', 452, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'UNFPA', 454, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'UNICEF', 455, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'Other', 456, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'TBD', 457, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'Unitaid', 1104, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'CDC', 1105, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'Donation', 28281, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'PSI', 28666, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'ISOLUTION', 28711, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'GDF', 28712, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'UNOPS', 29018, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'SCMS-USAID', 29019, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'DELIVER', 29165, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'MSF', 29470, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'WVI', 29471, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'CMS', 29472, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'WHO', 29672, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'CHAZ', 30105, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'CBCHS', 32096, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'ICAP', 32097, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'JMS', 33628, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'NMS', 34049, 1, 1, now(), 1, now()); 
INSERT INTO `fasp`.`rm_procurement_agent_type` (`PROCUREMENT_AGENT_TYPE_ID`, `REALM_ID`, `PROCUREMENT_AGENT_TYPE_CODE`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (null, 1, 'AURUM', 34796, 1, 1, now(), 1, now()); 

UPDATE `fasp`.`rm_procurement_agent` pa
    LEFT JOIN fasp.`rm_procurement_agent_type` gm ON gm.`PROCUREMENT_AGENT_TYPE_CODE`=pa.`PROCUREMENT_AGENT_CODE` 
    SET pa.`PROCUREMENT_AGENT_TYPE_ID` = gm.`PROCUREMENT_AGENT_TYPE_ID`;

