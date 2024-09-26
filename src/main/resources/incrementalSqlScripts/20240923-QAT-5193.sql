INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.scenarioPlanning.noShipmentsInThatRange','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No shipments in that range. Proceed to selecting `Re-Planned Shipment settings`.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune expédition dans cette plage. Passez à la sélection de « Paramètres d`expédition replanifiés ».');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay envíos en ese rango. Proceda a seleccionar ""Configuración de envíos replanificados"".');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma remessa nesse intervalo. Prossiga selecionando `Re-Planned Shipment settings`.');-- pr