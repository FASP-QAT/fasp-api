INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (null, 'Other', 'Autre', 'Otra', 'Outra', 1, now(), 1, now(), 34);

SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;

INSERT INTO `fasp`.`ap_problem_category`(`PROBLEM_CATEGORY_ID`,`LABEL_ID`)VALUES (4, @MAX);
