INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.loadDelDataset.keepLatestVersionDeleteOldVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Keep latest version and delete older versions.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Conserver la dernière version et supprimer les anciennes versions.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mantenga la última versión y elimine las versiones anteriores.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mantenha a versão mais recente e exclua as versões mais antigas.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.loadDelDataset.deleteVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete the version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer la version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar la versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir a versão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.loadProgramFirst','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you dont see the desired program(s), please load them first.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous ne voyez pas le(s) programme(s) souhaité(s), veuillez d`abord les charger.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no ve los programas deseados, cárguelos primero.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você não vir o(s) programa(s) desejado(s), carregue-o(s) primeiro.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.encryptData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to encrypt data?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous chiffrer des données ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres cifrar datos?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Deseja criptografar dados?');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareVersion.selectedForecastTitle','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast method that was selected for the final forecast. Forecasts are selected in the Compare and Select Forecast screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Méthode de prévision sélectionnée pour la prévision finale. Les prévisions sont sélectionnées dans l`écran Comparer et sélectionner les prévisions.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Método de pronóstico que se seleccionó para el pronóstico final. Los pronósticos se seleccionan en la pantalla Comparar y seleccionar pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Método de previsão que foi selecionado para a previsão final. As previsões são selecionadas na tela Comparar e Selecionar Previsão.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareVersion.selectedForecastQtyTitle','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Quantity forecast for the entire forecast period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision de quantité pour toute la période de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico de cantidad para todo el período de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade prevista para todo o período de previsão.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoSP.versionTitle','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the last month of your forecast is more than 6 months old, it will not appear in the version dropdown. Please consider importing forecast data for future months.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le dernier mois de votre prévision date de plus de 6 mois, il n`apparaîtra pas dans la liste déroulante des versions. Veuillez envisager d`importer les données de prévision pour les mois à venir.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el último mes de su pronóstico tiene más de 6 meses, no aparecerá en el menú desplegable de versiones. Considere importar datos de pronóstico para meses futuros.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o último mês da sua previsão tiver mais de 6 meses, ele não aparecerá na lista suspensa de versões. Considere importar dados de previsão para meses futuros.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.refreshPage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Refresh page');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rafraîchir la page');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizar página');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizar a página');-- pr
