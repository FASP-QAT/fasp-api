update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='If checked, this displays the shipment and expiry icons. There is a shipment icon for every month with shipment(s), including all shipment statuses except cancelled, on-hold and any inactive shipments. There is an expiry icon for every month with a projected expiry.'
where l.LABEL_CODE='static.report.showIconTooltip' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Si cette option est cochée, les icônes d\'expédition et d\'expiration s\'affichent. Une icône d\'expédition est présente pour chaque mois comportant une ou plusieurs expéditions — incluant tous les statuts d\'expédition, à l\'exception des expéditions annulées, en attente ou inactives. Une icône d\'expiration est présente pour chaque mois faisant l\'objet d\'une expiration prévisionnelle.'
where l.LABEL_CODE='static.report.showIconTooltip' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Si se marca esta opción, se muestran los iconos de envío y de vencimiento. Se muestra un icono de envío por cada mes que contenga envíos, abarcando todos los estados de envío, a excepción de los cancelados, los puestos en espera y cualquier envío inactivo. Asimismo, se muestra un icono de vencimiento por cada mes en el que se prevea un vencimiento.'
where l.LABEL_CODE='static.report.showIconTooltip' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Se selecionada, esta opção apresenta os ícones de envio e validade. Existe um ícone de envio para cada mês com envios, incluindo todos os estados de envio, exceto cancelado, em espera e envios inativos. Existe um ícone de validade para cada mês com data de validade prevista.'
where l.LABEL_CODE='static.report.showIconTooltip' and ll.LANGUAGE_ID=4;