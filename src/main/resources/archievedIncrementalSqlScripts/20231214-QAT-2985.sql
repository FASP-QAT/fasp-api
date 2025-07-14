INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeTypeChanged','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You have changed the node type of the node, so all the child nodes will be deleted. Are you sure you want to continue?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez modifié le type de nœud du nœud, tous les nœuds enfants seront donc supprimés. Es-tu sur de vouloir continuer?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ha cambiado el tipo de nodo, por lo que se eliminarán todos los nodos secundarios. Estás seguro de que quieres continuar?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você alterou o tipo de nó do nó, portanto, todos os nós filhos serão excluídos. Você tem certeza que quer continuar?');-- pr