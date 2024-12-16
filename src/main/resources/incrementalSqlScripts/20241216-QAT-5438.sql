INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdate4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A month with a');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un mois avec un');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Un mes con un');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Um mês com');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdate41','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'symbol indicates that the current month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le symbole indique que le mois en cours');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El símbolo indica que el mes actual.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'símbolo indica que o mês atual');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdate42','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'does not contain forecast consumption, actual consumption, or both.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ne contient pas la consommation prévue, la consommation réelle, ou les deux.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'no contiene el consumo previsto, el consumo real o ambos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'não contém consumo previsto, consumo real ou ambos.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdate5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A month with the forecast error in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un mois avec une erreur de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Un mes con el error de previsión en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Um mês com o erro de previsão em');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdateredtext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'red text');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'texte rouge');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'texto rojo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'texto vermelho');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdateindicates','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'indicates that the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'indique que le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'indica que el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'indica que o');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdatecalculated','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'calculated forecast error is above the forecast error threshold percentage.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'l’erreur de prévision calculée est supérieure au pourcentage du seuil d’erreur de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico calculado está por encima del porcentaje del umbral de error de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'o erro de previsão calculado está acima da porcentagem do limite de erro de previsão.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormulaupdatethreshold','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This forecast error threshold can be updated per planning unit by the program administrator in the ""Update Planning Units"" screen. The default forecast error threshold is 50%.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce seuil d`erreur de prévision peut être mis à jour par unité de planification par l`administrateur du programme dans l`écran « Mettre à jour les unités de planification ». Le seuil d`erreur de prévision par défaut est de 50 %.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El administrador del programa puede actualizar este umbral de error de pronóstico por unidad de planificación en la pantalla ""Actualizar unidades de planificación"". El umbral de error de pronóstico predeterminado es del 50%.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este limite de erro de previsão pode ser atualizado por unidade de planejamento pelo administrador do programa na tela “Atualizar Unidades de Planejamento”. O limite de erro de previsão padrão é de 50%.');-- pr