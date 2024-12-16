INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only months that have both a forecast and an actual consumption record are included in the forecast error calculation. The column to the right of the Forecast Error column details how many months were included in that calculation.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuls les mois pour lesquels il existe à la fois un enregistrement de consommation prévue et un enregistrement de consommation réelle sont inclus dans le calcul de l`erreur de prévision. La colonne à droite de la colonne Erreur de prévision détaille le nombre de mois inclus dans ce calcul.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Sólo los meses que tienen un registro de consumo real y de pronóstico se incluyen en el cálculo del error de pronóstico. La columna a la derecha de la columna Error de pronóstico detalla cuántos meses se incluyeron en ese cálculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Somente os meses que possuem um registro de consumo previsto e real são incluídos no cálculo do erro de previsão. A coluna à direita da coluna Erro de previsão detalha quantos meses foram incluídos nesse cálculo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula6nomonth','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A Forecast Error labeled ‘No months in this period contain both forecast and actual consumption’ indicates that there are no months in the selected time window that have both actual and forecast records in the same month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une erreur de prévision intitulée « Aucun mois dans cette période ne contient à la fois une consommation prévue et une consommation réelle » indique qu`aucun mois dans la fenêtre de temps sélectionnée n`a à la fois des enregistrements réels et prévisionnels dans le même mois.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Un error de pronóstico etiquetado como ""Ningún mes en este período contiene consumo real y pronosticado"" indica que no hay meses en la ventana de tiempo seleccionada que tengan registros tanto reales como pronosticados en el mismo mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Um erro de previsão denominado ‘Nenhum mês neste período contém consumo previsto e real’ indica que não há meses na janela de tempo selecionada que tenham registros reais e previstos no mesmo mês.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula7consumption','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The Forecast Error can go above 100% and the higher the %, the less accurate the forecast is compared to the consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`erreur de prévision peut dépasser 100 % et plus le %, moins la prévision est précise par rapport à la consommation.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico puede superar el 100% y cuanto mayor sea el porcentaje, menos preciso será el pronóstico en comparación con el consumo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O Erro de Previsão pode ultrapassar 100% e quanto maior o %, menos precisa é a previsão em relação ao consumo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula4row','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'A row in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une rangée dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'una fila en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uma fileira em');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula4redtext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'red text');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'texte rouge');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'texto rojo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'texto vermelho');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula4indicatesthat','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'indicates that the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'indique que le');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'indica que el');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'indica que o');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula4thresholdper','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'calculated forecast error is above the forecast error threshold percentage.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'l’erreur de prévision calculée est supérieure au pourcentage du seuil d’erreur de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico calculado está por encima del porcentaje del umbral de error de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'o erro de previsão calculado está acima da porcentagem do limite de erro de previsão.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.forecastErrorMonthlyFormula4UpdatePlanningFe','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This forecast error threshold can be updated per planning unit by the program administrator in the “Update Planning Units” screen. The default forecast error threshold is 50%.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce seuil d`erreur de prévision peut être mis à jour par unité de planification par l`administrateur du programme dans l`écran « Mettre à jour les unités de planification ». Le seuil d`erreur de prévision par défaut est de 50 %.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El administrador del programa puede actualizar este umbral de error de pronóstico por unidad de planificación en la pantalla ""Actualizar unidades de planificación"". El umbral de error de pronóstico predeterminado es del 50%.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este limite de erro de previsão pode ser atualizado por unidade de planejamento pelo administrador do programa na tela “Atualizar Unidades de Planejamento”. O limite de erro de previsão padrão é de 50%.');-- pr