INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.moveCopy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Move/Copy Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Déplacer/Copier le nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mover/copiar nodo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mover/Copiar Nó');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.copy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Copy');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Copie');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Copiar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'cópia de');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.move','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Move');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Se déplacer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mover');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mover');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.parentLevel','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Parent Level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Niveau parental');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel de padres');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível pai');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.parentNode','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Parent Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud parent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo principal');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó pai');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.destination','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Destination');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Destination');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Destino');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Destino');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.moveCopyNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: The entire branch (including all child nodes) will be moved/copied');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : La branche entière (y compris tous les nœuds enfants) sera déplacée/copiée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Se moverá/copiará toda la rama (incluidos todos los nodos secundarios)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: toda a ramificação (incluindo todos os nós filhos) será movida/copiada');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.copyModeling','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use same modeling information');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utiliser les mêmes informations de modélisation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice la misma información de modelado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use as mesmas informações de modelagem');-- pr