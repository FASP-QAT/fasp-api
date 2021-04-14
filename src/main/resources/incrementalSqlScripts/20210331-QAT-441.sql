UPDATE rm_realm_problem SET `DATA3`='2,5,2' WHERE `REALM_PROBLEM_ID`='22';
update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now();

ALTER TABLE `rm_realm` 
ADD COLUMN `MIN_QPL_TOLERANCE` INT(10) UNSIGNED NOT NULL AFTER `MAX_MOS_MAX_GAURDRAIL`,
ADD COLUMN `MIN_QPL_TOLERANCE_CUT_OFF` INT(10) UNSIGNED NOT NULL AFTER `MIN_QPL_TOLERANCE`,
ADD COLUMN `MAX_QPL_TOLERANCE` INT(10) UNSIGNED NOT NULL AFTER `MIN_QPL_TOLERANCE_CUT_OFF`;

UPDATE rm_realm SET MIN_QPL_TOLERANCE=2, MIN_QPL_TOLERANCE_CUT_OFF=5, MAX_QPL_TOLERANCE=2 ,LAST_MODIFIED_DATE=now() WHERE REALM_ID=1;

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_realm` AS
    SELECT 
        `r`.`REALM_ID` AS `REALM_ID`,
        `r`.`REALM_CODE` AS `REALM_CODE`,
        `r`.`LABEL_ID` AS `LABEL_ID`,
        `r`.`DEFAULT_REALM` AS `DEFAULT_REALM`,
        `r`.`MIN_MOS_MIN_GAURDRAIL` AS `MIN_MOS_MIN_GAURDRAIL`,
        `r`.`MIN_MOS_MAX_GAURDRAIL` AS `MIN_MOS_MAX_GAURDRAIL`,
        `r`.`MAX_MOS_MAX_GAURDRAIL` AS `MAX_MOS_MAX_GAURDRAIL`,
        `r`.`MIN_QPL_TOLERANCE` AS `MIN_QPL_TOLERANCE`,
        `r`.`MIN_QPL_TOLERANCE_CUT_OFF` AS `MIN_QPL_TOLERANCE_CUT_OFF`,
        `r`.`MAX_QPL_TOLERANCE` AS `MAX_QPL_TOLERANCE`,
        `r`.`ACTIVE` AS `ACTIVE`,
        `r`.`CREATED_BY` AS `CREATED_BY`,
        `r`.`CREATED_DATE` AS `CREATED_DATE`,
        `r`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `r`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `rl`.`LABEL_EN` AS `LABEL_EN`,
        `rl`.`LABEL_FR` AS `LABEL_FR`,
        `rl`.`LABEL_SP` AS `LABEL_SP`,
        `rl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_realm` `r`
        LEFT JOIN `ap_label` `rl` ON ((`r`.`LABEL_ID` = `rl`.`LABEL_ID`)));



INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.minQplTolerance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qpl Tolerance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tolérance QPL minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tolerancia mínima de QPL');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tolerância mínima de QPL');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.minQplToleranceCutOff','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qpl Tolerance CutOff');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuil de tolérance minimum QPL');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Límite mínimo de tolerancia QPL');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Corte de tolerância mínima de QPL');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.maxQplTolerance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qpl Tolerance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tolérance QPL max');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tolerancia máxima de QPL');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tolerância máxima de QPL');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.minQplTolerance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter min qpl tolerance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir la tolérance minimale de qpl');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la tolerancia mínima de qpl');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira a tolerância mínima qpl');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.minQplToleranceCutOff','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter max qpl tolerance cutoff');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le seuil de tolérance qpl max.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el límite máximo de tolerancia de qpl');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira o limite máximo de tolerância qpl');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.maxQplTolerance','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter max qpl tolerance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir la tolérance qpl max.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la tolerancia máxima de qpl');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira a tolerância máxima qpl');-- pr
