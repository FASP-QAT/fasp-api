INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.emailNotTriggered','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User created successfully but email not triggered');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisateur créé avec succès mais e-mail non déclenché');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usuario creado exitosamente pero el correo electrónico no se activó');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usuário criado com sucesso, mas e-mail não disparado');-- pr
