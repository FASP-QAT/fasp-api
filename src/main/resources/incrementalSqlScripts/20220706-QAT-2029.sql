update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alpha'
where l.LABEL_CODE='static.extrapolation.alpha' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alpha'
where l.LABEL_CODE='static.extrapolation.alpha' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alfa'
where l.LABEL_CODE='static.extrapolation.alpha' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alfa'
where l.LABEL_CODE='static.extrapolation.alpha' and ll.LANGUAGE_ID=4;


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Beta'
where l.LABEL_CODE='static.extrapolation.beta' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Bêta'
where l.LABEL_CODE='static.extrapolation.beta' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Beta'
where l.LABEL_CODE='static.extrapolation.beta' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Beta'
where l.LABEL_CODE='static.extrapolation.beta' and ll.LANGUAGE_ID=4;


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Gamma'
where l.LABEL_CODE='static.extrapolation.gamma' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Gamma'
where l.LABEL_CODE='static.extrapolation.gamma' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Gama'
where l.LABEL_CODE='static.extrapolation.gamma' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Gama'
where l.LABEL_CODE='static.extrapolation.gamma' and ll.LANGUAGE_ID=4;
