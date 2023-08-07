INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.editStatus.validCriticality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select Criticality');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner Criticité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione Criticidade');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione Criticidad');-- sp


UPDATE `fasp`.`ap_label` SET `LABEL_EN` = 'Other - Low', `LABEL_FR` = 'Autre - Faible', `LABEL_SP` = 'Otro - Bajo', `LABEL_PR` = 'Outros - Baixo' WHERE (`LABEL_ID` = '38866') AND (`LABEL_EN` = 'Other');

INSERT INTO fasp.ap_label (LABEL_ID, LABEL_EN, LABEL_FR, LABEL_SP, LABEL_PR, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, SOURCE_ID) VALUES (null, 'Other - Medium', 'Autre - Moyen', 'Otro - Medio', 'Outros - Médio', 1, now(), 1, now(), 34);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO fasp.ap_problem_category(PROBLEM_CATEGORY_ID,LABEL_ID)VALUES (5, @MAX);

INSERT INTO fasp.ap_label (LABEL_ID, LABEL_EN, LABEL_FR, LABEL_SP, LABEL_PR, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, SOURCE_ID) VALUES (null, 'Other - High', 'Autre - Élevé', 'Otro - Alto', 'Outros - Alto', 1, now(), 1, now(), 34);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO fasp.ap_problem_category(PROBLEM_CATEGORY_ID,LABEL_ID)VALUES (6, @MAX);

SELECT l.LABEL_ID into @MAX FROM ap_label l where l.LABEL_EN='Manual problem' and l.SOURCE_ID in (22) ;
SELECT l.LABEL_ID into @MAX2 FROM ap_label l where l.LABEL_EN='Manual problem' and l.SOURCE_ID in (23) ;

INSERT INTO `fasp`.`ap_problem` (
    `PROBLEM_ID`,`LABEL_ID`, `PROBLEM_CATEGORY_ID`, `ACTION_URL`, `ACTION_LABEL_ID`, `ACTUAL_CONSUMPTION_TRIGGER`,
    `FORECASTED_CONSUMPTION_TRIGGER`, `INVENTORY_TRIGGER`, `ADJUSTMENT_TRIGGER`, `SHIPMENT_TRIGGER`, `ACTIVE`,
    `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES (
    27,@MAX, 5, '',@MAX2, 0,
    0, 0, 0, 0, 1,
    1, now(), 1, now());

INSERT INTO `fasp`.`rm_realm_problem` (`REALM_PROBLEM_ID`,`REALM_ID`, `PROBLEM_ID`, `PROBLEM_TYPE_ID`, `CRITICALITY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('26', '1', '27', '2', '2', '1', '1', now(), '1', now());

INSERT INTO `fasp`.`ap_problem` (
    `PROBLEM_ID`,`LABEL_ID`, `PROBLEM_CATEGORY_ID`, `ACTION_URL`, `ACTION_LABEL_ID`, `ACTUAL_CONSUMPTION_TRIGGER`,
    `FORECASTED_CONSUMPTION_TRIGGER`, `INVENTORY_TRIGGER`, `ADJUSTMENT_TRIGGER`, `SHIPMENT_TRIGGER`, `ACTIVE`,
    `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES (
    28,@MAX, 6, '',@MAX2, 0,
    0, 0, 0, 0, 1,
    1, now(), 1, now());

INSERT INTO `fasp`.`rm_realm_problem` (`REALM_PROBLEM_ID`,`REALM_ID`, `PROBLEM_ID`, `PROBLEM_TYPE_ID`, `CRITICALITY_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES ('27', '1', '28', '2', '3', '1', '1', now(), '1', now());
