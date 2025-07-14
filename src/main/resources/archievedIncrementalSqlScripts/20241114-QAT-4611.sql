update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Note: If you changed the forecast period, you will be redirected for tree forecast calculations.'
where l.LABEL_CODE='static.versionSettings.confirmUpdate' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Remarque : si vous avez modifié la période de prévision, vous serez redirigé vers les calculs de prévision des arbres.'
where l.LABEL_CODE='static.versionSettings.confirmUpdate' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Nota: Si cambió el período de pronóstico, será redirigido a los cálculos de pronóstico de árboles.'
where l.LABEL_CODE='static.versionSettings.confirmUpdate' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Observação: se você alterar o período de previsão, será redirecionado para os cálculos de previsão em árvore.'
where l.LABEL_CODE='static.versionSettings.confirmUpdate' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.treeSync','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree sync');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Synchronisation des arbres');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Sincronización de árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sincronização de árvore');-- pr