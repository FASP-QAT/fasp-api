update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Based on chosen report period for selected program'
where l.LABEL_CODE='static.dashboard.shipmentsHeaderTooltip' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='En fonction de la période de rapport choisie pour le programme sélectionné'
where l.LABEL_CODE='static.dashboard.shipmentsHeaderTooltip' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Basado en el período de informe elegido para el programa seleccionado'
where l.LABEL_CODE='static.dashboard.shipmentsHeaderTooltip' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Com base no período de relatório escolhido para o programa selecionado.'
where l.LABEL_CODE='static.dashboard.shipmentsHeaderTooltip' and ll.LANGUAGE_ID=4;