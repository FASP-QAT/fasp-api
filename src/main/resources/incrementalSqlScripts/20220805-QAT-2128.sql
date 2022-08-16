-- update ap_static_label l 
-- left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
-- set ll.LABEL_TEXT='Please enter the root node info.'
-- where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=1;
-- 
-- update ap_static_label l 
-- left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
-- set ll.LABEL_TEXT='Veuillez saisir les informations du nœud racine.'
-- where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=2;
-- 
-- update ap_static_label l 
-- left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
-- set ll.LABEL_TEXT='Ingrese la información del nodo raíz.'
-- where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=3;
-- 
-- update ap_static_label l 
-- left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
-- set ll.LABEL_TEXT='Insira as informações do nó raiz.'
-- where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=4;


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.rootNodeInfoMissing','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter the root node info.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir les informations du nœud racine.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la información del nodo raíz.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira as informações do nó raiz.');