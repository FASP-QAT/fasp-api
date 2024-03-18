update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Select at least one program to download'
where l.LABEL_CODE='static.loadDelDataset.selectAtleastOneDataset' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ordre #'
where l.LABEL_CODE='Sélectionnez au moins un programme à télécharger' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Seleccione al menos un programa para descargar'
where l.LABEL_CODE='static.loadDelDataset.selectAtleastOneDataset' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Selecione pelo menos um programa para baixar'
where l.LABEL_CODE='static.loadDelDataset.selectAtleastOneDataset' and ll.LANGUAGE_ID=4;