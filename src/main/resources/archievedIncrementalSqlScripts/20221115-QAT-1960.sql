INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.planningUnitSetting.offlineMsg','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: User must be online to add new products');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : l`utilisateur doit être en ligne pour ajouter de nouveaux produits');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: El usuario debe estar en línea para agregar nuevos productos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: o usuário deve estar online para adicionar novos produtos');-- pr
