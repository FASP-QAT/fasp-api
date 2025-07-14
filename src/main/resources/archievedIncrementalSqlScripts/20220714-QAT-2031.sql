INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.coreui.oldVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You might have an older version. You should control+F5 now.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez peut-être une version plus ancienne. Vous devriez contrôler + F5 maintenant.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Es posible que tenga una versión anterior. Deberías controlar+F5 ahora.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você pode ter uma versão mais antiga. Você deve controlar + F5 agora.');