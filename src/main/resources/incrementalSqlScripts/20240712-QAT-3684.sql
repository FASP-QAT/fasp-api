INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentOverview.pieChartNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Percentage labels below 1% are not displayed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Les étiquettes de pourcentage inférieures à 1 % ne sont pas affichées.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Las etiquetas de porcentaje inferiores al 1% no se muestran');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: rótulos de porcentagem abaixo de 1% não são exibidos');-- pr
