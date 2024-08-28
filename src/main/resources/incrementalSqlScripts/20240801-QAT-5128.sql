INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPlanningUnit.forecastErrorTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in the Forecast Error (Monthly) report to signify that a specific month’s forecast error is above this desired threshold value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans le rapport Erreur de prévision (mensuelle) pour signifier que l`erreur de prévision d`un mois spécifique est supérieure à cette valeur seuil souhaitée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se utiliza en el informe de error de pronóstico (mensual) para indicar que el error de pronóstico de un mes específico está por encima de este valor de umbral deseado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado no relatório Erro de previsão (mensal) para indicar que o erro de previsão de um mês específico está acima deste valor limite desejado');-- pr