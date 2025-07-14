INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.deleteScenario','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete Scenario');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer le scénario');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar escenario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir cenário');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.showOnlyActive','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Only Active');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher uniquement les actifs');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar solo activa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar apenas ativo');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.showInactive','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Inactive');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher inactif');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar inactiva');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar inativo');-- pr
