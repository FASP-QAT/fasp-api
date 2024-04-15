INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (NULL, 'No Review Needed', 'Aucun examen nécessaire', 'No se necesita revisión', 'Nenhuma revisão necessária', '1', NOW(), '1', NOW(), '6'); 

SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`ap_version_status` (`VERSION_STATUS_ID`, `LABEL_ID`) VALUES (NULL, @MAX);
