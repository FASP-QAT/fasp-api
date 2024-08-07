INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.spvr.rightClickNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note : Right click to view notes history');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : cliquez avec le bouton droit pour afficher l`historique des notes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Haga clic derecho para ver el historial de notas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: Clique com o botão direito para visualizar o histórico de notas');-- pr