INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ticket.uploadFile','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Upload File');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Télécharger le fichier');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cargar archivo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar arquivo');-- pr
