INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.onlyDownloadedProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show only downloaded programs'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher uniquement les programmes téléchargés'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar solo programas descargados'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar apenas programas baixados'); -- pr
