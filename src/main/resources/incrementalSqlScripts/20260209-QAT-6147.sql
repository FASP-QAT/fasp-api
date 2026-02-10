INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.linkingNotificationNote1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'is only available for local versions. If you don’t see the program or version you need, please'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'n\'est disponible que pour les versions locales. Si vous ne trouvez pas le programme ou la version dont vous avez besoin, veuillez'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo está disponible para versiones locales. Si no encuentra el programa o la versión que necesita, por favor'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Está disponível apenas para versões locais. Se você não encontrar o programa ou a versão que precisa, por favor,'); -- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.linkingNotificationNote2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'your program first.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Votre programme d\'abord.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Primero tu programa.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Seu programa primeiro.'); -- pr

