UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='If you delink a shipment, the manual QAT shipment will retain the linked details. If the linked shipment has not been uploaded to the server, it will revert to the original manual shipment`s details.' where ap_static_label.LABEL_CODE='static.mt.reminders4A' 
and ap_static_label_languages.LANGUAGE_ID=1;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Si vous supprimez le lien d`une expédition, l`expédition manuelle QAT conservera les détails liés. Si l`expédition liée n`a pas été téléchargée sur le serveur, elle reviendra aux détails de l`expédition manuelle d`origine.' where ap_static_label.LABEL_CODE='static.mt.reminders4A' 
and ap_static_label_languages.LANGUAGE_ID=2;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Si desvincula un envío, el envío manual de QAT conservará los detalles vinculados. Si el envío vinculado no se ha cargado al servidor, volverá a los detalles del envío manual original.' where ap_static_label.LABEL_CODE='static.mt.reminders4A' 
and ap_static_label_languages.LANGUAGE_ID=3;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Se desvincular uma remessa, a remessa QAT manual manterá os dados vinculados. Se a remessa vinculada não tiver sido carregada no servidor, os detalhes da remessa manual original serão revertidos.' where ap_static_label.LABEL_CODE='static.mt.reminders4A' 
and ap_static_label_languages.LANGUAGE_ID=4;
