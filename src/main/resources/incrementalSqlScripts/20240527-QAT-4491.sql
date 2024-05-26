INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.budgetWarning1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The budget');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le budget');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El presupuesto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O orçamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.budgetWarning2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,') is going over budget by $');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,') dépasse le budget de $');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,') está excediendo el presupuesto en $');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,') está ultrapassando o orçamento em $');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.budgetWarning3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(please see shipment ID');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(veuillez consulter l'ID d'expédition');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(consulte el ID de envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(consulte o ID da remessa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.budgetWarning4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to continue?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous continuer?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres continuar?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você quer continuar?');-- pr