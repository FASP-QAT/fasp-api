ALTER TABLE `fasp`.`rm_realm` 
ADD COLUMN `ACTUAL_CONSUMPTION_MONTHS_IN_PAST` INT NOT NULL DEFAULT 6 AFTER `MAX_QPL_TOLERANCE`,
ADD COLUMN `FORECAST_CONSUMPTION_MONTH_IN_PAST` INT NOT NULL DEFAULT 4 AFTER `ACTUAL_CONSUMPTION_MONTHS_IN_PAST`,
ADD COLUMN `INVENTORY_MONTHS_IN_PAST` INT NOT NULL DEFAULT 6 AFTER `FORECAST_CONSUMPTION_MONTH_IN_PAST`;



USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
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
        `r`.`ACTUAL_CONSUMPTION_MONTHS_IN_PAST` AS `ACTUAL_CONSUMPTION_MONTHS_IN_PAST`,
		`r`.`FORECAST_CONSUMPTION_MONTH_IN_PAST` AS `FORECAST_CONSUMPTION_MONTH_IN_PAST`,
		`r`.`INVENTORY_MONTHS_IN_PAST` AS `INVENTORY_MONTHS_IN_PAST`,
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

UPDATE `fasp`.`rm_realm` r SET r.LAST_MODIFIED_DATE=now();

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.restrictionActualConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data entry restriction for Actual Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Restriction de saisie de données pour la consommation réelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Restricción de entrada de datos para consumo real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Restrição de entrada de dados para Consumo Real');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.restrictionForecastConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data entry restriction for Forecast Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Restriction de saisie de données pour la consommation prévisionnelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Restricción de entrada de datos para consumo de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Restrição de entrada de dados para previsão de consumo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.restrictionInventory','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data entry restriction for Inventory');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Restriction de saisie de données pour l`inventaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Restricción de entrada de datos para Inventario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Restrição de entrada de dados para Inventário');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionActualConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter data entry restriction for Actual Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez entrer une restriction de saisie de données pour la consommation réelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la restricción de entrada de datos para el consumo real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a restrição de entrada de dados para consumo real');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionForecastConsumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter data entry restriction for Forecast Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir une restriction de saisie de données pour la consommation prévue');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la restricción de entrada de datos para el consumo de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a restrição de entrada de dados para a previsão de consumo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionInventory','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter data entry restriction for Inventory');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez entrer la restriction de saisie de données pour l`inventaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la restricción de entrada de datos para el inventario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a restrição de entrada de dados para Inventário');-- pr