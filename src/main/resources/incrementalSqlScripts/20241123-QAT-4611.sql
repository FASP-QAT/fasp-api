INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.updateForecastTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Updating Forecast Trees');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mise à jour des arbres de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualización de árboles de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualizando Árvores de Previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.noteInTreeSync','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'We are automatically updating the forecast trees for your selected program and version based on the new forecast period. This may take a moment.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nous mettons automatiquement à jour les arbres de prévision pour le programme et la version sélectionnés en fonction de la nouvelle période de prévision. Cette opération peut prendre un moment.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualizaremos automáticamente los árboles de pronóstico para el programa y la versión seleccionados según el nuevo período de pronóstico. Esto puede demorar un momento.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estamos a atualizar automaticamente as árvores de previsão para o programa e versão selecionados com base no novo período de previsão. Isto pode demorar um pouco.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.noteInTreeSyncPart2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Once the process is complete, no further action is required. Please wait while the system updates the data.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une fois le processus terminé, aucune autre action n`est requise. Veuillez patienter pendant que le système met à jour les données.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Una vez finalizado el proceso, no es necesario realizar ninguna acción adicional. Espere mientras el sistema actualiza los datos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Depois de o processo estar concluído, nenhuma ação adicional será necessária. Aguarde enquanto o sistema atualiza os dados.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.noteInTreeSyncPart3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Thank you for your patience!');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Merci pour votre patience !');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¡Gracias por su paciencia!');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Obrigado pela sua paciência!');-- pr