INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.noData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'There is no available shipment data for this planning unit during the selected report period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune donnée d\'expédition n\'est disponible pour cette unité de planification au cours de la période de rapport sélectionnée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay datos de envío disponibles para esta unidad de planificación durante el período del informe seleccionado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não há dados de embarque disponíveis para esta unidade de planejamento durante o período de relatório selecionado.');-- pr