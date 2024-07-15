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

ALTER TABLE `fasp`.`rm_program_planning_unit_procurement_agent` CHANGE `PRICE` `PRICE` DECIMAL(14,4) NULL ; 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePU.noteText1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note : If you want to update procurement agent specific details, go to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Si vous souhaitez mettre à jour les détails spécifiques de l`agent d`approvisionnement, accédez à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Si desea actualizar los detalles específicos del agente de adquisiciones, vaya a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: se você quiser atualizar detalhes específicos do agente de compras, vá para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePU.noteText2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'écran');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePPUPA.noteText1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note : If you want to update program specific details, go to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Si vous souhaitez mettre à jour les détails spécifiques au programme, accédez à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: si desea actualizar detalles específicos del programa, vaya a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: Se desejar atualizar detalhes específicos do programa, vá para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePPUPA.noteText2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'écran');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pantalla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'tela');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.updatePPUPA.atleastOneValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter atleast one of the value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez au moins une des valeurs');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese al menos uno de los valores');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira pelo menos um dos valores');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.ppupaAlreadyExists','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program, planning unit and procurement agent mapping already exists');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La cartographie des programmes, des unités de planification et des agents d’approvisionnement existe déjà');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ya existe un mapeo del programa, la unidad de planificación y los agentes de adquisiciones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O mapeamento do programa, da unidade de planejamento e do agente de compras já existe');-- pr