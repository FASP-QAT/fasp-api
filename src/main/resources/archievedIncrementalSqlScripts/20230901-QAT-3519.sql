INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.PUSettingList.notes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User does not have business function to modify this screen. Please consult program admin for assistance.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L\'utilisateur ne dispose pas de fonction métier pour modifier cet écran. Veuillez consulter l\'administrateur du programme pour obtenir de l\'aide.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El usuario no tiene función comercial para modificar esta pantalla. Consulte al administrador del programa para obtener ayuda.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O usuário não possui função empresarial para modificar esta tela. Consulte o administrador do programa para obter assistência.'); -- pr

