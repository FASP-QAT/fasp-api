update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT = 'Enter valid data. Some special characters are not allowed i.e.('' ")'
where l.LABEL_CODE='static.label.someSpecialCaseNotAllowed' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Saisissez des données valides. Certains caractères spéciaux ne sont pas autorisés, par exemple ('' ")'
where l.LABEL_CODE='static.label.someSpecialCaseNotAllowed' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Introduce datos válidos. Algunos caracteres especiales no están permitidos, por ejemplo ('' ")'
where l.LABEL_CODE='static.label.someSpecialCaseNotAllowed' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Insira dados válidos. Alguns caracteres especiais não são permitidos, por exemplo ('' ")'
where l.LABEL_CODE='static.label.someSpecialCaseNotAllowed' and ll.LANGUAGE_ID=4;