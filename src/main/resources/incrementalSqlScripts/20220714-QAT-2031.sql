CREATE TABLE `fasp`.`coreui_version`( `VERSION_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT , `VERSION_NO` INT UNSIGNED NOT NULL , `LAST_MODIFIED_DATE` DATETIME NOT NULL , `LAST_MODIFIED_BY` INT NOT NULL , PRIMARY KEY (`VERSION_ID`) ); 

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.coreui.oldVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You are using an older version of the application.Please do CTRL + F5 to get the latest version of application.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous utilisez une ancienne version de lapplication. Veuillez faire CTRL + F5 pour obtenir la dernière version de lapplication.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Está utilizando una versión anterior de la aplicación. Haga CTRL + F5 para obtener la última versión de la aplicación.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você está usando uma versão mais antiga do aplicativo. Faça CTRL + F5 para obter a versão mais recente do aplicativo.');