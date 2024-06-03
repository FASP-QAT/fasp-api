INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.erpComboboxError','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fill either ERP planning unit or RO/Order No. to populate data'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remplissez soit l\'unité de planification ERP, soit le numéro de commande/RO pour renseigner les données.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Complete la unidad de planificación ERP o RO/N. de pedido para completar los datos'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preencha a unidade de planejamento ERP ou RO/N do pedido para preencher os dados'); -- pr