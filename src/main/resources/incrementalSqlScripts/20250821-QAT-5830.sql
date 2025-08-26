update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Do you want to aggregate selected products and/or programs?'
where l.LABEL_CODE='static.stockStatus.doYouWantToAggregate' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Souhaitez-vous regrouper des produits et/ou programmes sélectionnés ?'
where l.LABEL_CODE='static.stockStatus.doYouWantToAggregate' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='¿Quieres agregar productos y/o programas seleccionados?'
where l.LABEL_CODE='static.stockStatus.doYouWantToAggregate' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Você deseja agregar produtos e/ou programas selecionados?'
where l.LABEL_CODE='static.stockStatus.doYouWantToAggregate' and ll.LANGUAGE_ID=4;