INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentOverview.pieChartNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Percentages lower than 1% are not shown on the chart');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : les pourcentages inférieurs à 1 % ne sont pas affichés sur le graphique.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Los porcentajes inferiores al 1% no se muestran en el gráfico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: Percentuais inferiores a 1% não são apresentados no gráfico');-- pr
