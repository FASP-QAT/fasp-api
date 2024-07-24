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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use arrows to indicate the desired node order from left to right.  Only nodes in this level are shown.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez les flèches pour indiquer l ordre des nœuds souhaité de gauche à droite.  Seuls les nœuds de ce niveau sont affichés.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice flechas para indicar el orden de nodos deseado de izquierda a derecha.  Sólo se muestran los nodos de este nivel.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use as setas para indicar a ordem dos nós desejada da esquerda para a direita.  Somente nós neste nível são mostrados.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.seeChildrenOf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'See children of'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voir Enfants de'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver hijos de'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Veja Filhos de'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.editLevelName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit Level Name'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier le nom du niveau'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar nombre de nivel'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar nome do nível'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.levelReorderNodeUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Displayed in the Y-axis in the Modeling Validation screen.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Affiché sur l\'axe Y dans l\'écran Validation de la modélisation.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se muestra en el eje Y en la pantalla Validación de modelado.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibido no eixo Y na tela Validação de Modelagem.'); -- pr