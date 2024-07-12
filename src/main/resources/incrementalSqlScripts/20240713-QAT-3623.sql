INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.changetheme','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change Theme');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Change le thème');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambiar de tema');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudar tema');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.lighttheme','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Light Theme');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Thème Lumière');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tema ligero');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tema claro');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.darktheme','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dark Theme');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Thème sombre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tema oscuro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tema escuro');-- pr