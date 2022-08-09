update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Please enter the root node info.'
where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Veuillez saisir les informations du nœud racine.'
where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ingrese la información del nodo raíz.'
where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Insira as informações do nó raiz.'
where l.LABEL_CODE='static.tree.rootNodeInfoMissing' and ll.LANGUAGE_ID=4;