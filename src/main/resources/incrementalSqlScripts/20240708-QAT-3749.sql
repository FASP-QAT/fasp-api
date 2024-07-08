INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeltr','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Position (L to R)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Position du nœud (de gauche à droite)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Posición del nodo (de izquierda a derecha)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Posição do nó (L a R)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Name'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du nœud'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del nodo'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do nó'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.shiftUp','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shift Up'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Décale vers le haut'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambiar hacia arriba'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança para cima'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.shiftDown','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shift Down'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rétrograder'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambiar hacia abajo'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Deslocar para baixo'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.levelChangeNote','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use numbers to indicate the desired node order from left to right.  Only nodes in this level are shown.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez des chiffres pour indiquer l’ordre des nœuds souhaité de gauche à droite.  Seuls les nœuds de ce niveau sont affichés.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice números para indicar el orden de nodos deseado de izquierda a derecha.  Sólo se muestran los nodos de este nivel.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use números para indicar a ordem dos nós desejada, da esquerda para a direita.  Somente nós neste nível são mostrados.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.seeChildrenOf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'See Children of'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voir Enfants de'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver hijos de'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Veja Filhos de'); -- pr