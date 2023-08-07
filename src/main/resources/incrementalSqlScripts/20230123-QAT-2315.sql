update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Remarques'
where l.LABEL_CODE='static.ManageTree.Notes' and ll.LANGUAGE_ID=2;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.hideActionButtons','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide Action Buttons');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer les boutons d`action');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar Botões de Ação');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar botones de acción');-- sp
