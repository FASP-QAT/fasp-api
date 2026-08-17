INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.helpdesk','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Helpdesk');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Service d`assistance technique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Servicio de asistencia técnica');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Central de Ajuda');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.documentation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Documentation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Documentation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Documentación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Documentação');-- pr
