INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.reports.forecastMetrics.tooMuchData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You have selected too much data for QAT to display properly. Please try limiting the data using the dropdowns.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez sélectionné trop de données pour que QAT s''affiche correctement. Veuillez essayer de limiter les données à l''aide des listes déroulantes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ha seleccionado demasiados datos para que QAT los muestre correctamente. Intente limitar los datos usando los menús desplegables.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você selecionou muitos dados para o QAT exibir corretamente. Tente limitar os dados usando as listas suspensas.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.reports.forecastMetrics.takingLonger','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Due to the large amount of data, the QAT server is taking longer to process this request. If you don''t want to wait, please limit the data using the dropdowns.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En raison de la grande quantité de données, le serveur QAT prend plus de temps pour traiter cette demande. Si vous ne voulez pas attendre, veuillez limiter les données à l''aide des listes déroulantes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Debido a la gran cantidad de datos, el servidor QAT está tardando más en procesar esta solicitud. Si no quiere esperar, limite los datos usando los menús desplegables.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Devido à grande quantidade de dados, o servidor QAT está demorando mais para processar esta solicitação. Se você não quiser esperar, limite os dados usando as listas suspensas.');-- pr