SELECT l.STATIC_LABEL_ID INTO @MAX FROM ap_static_label l where l.LABEL_CODE='static.tooltip.UsageType' ;

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT` = 'Continuous usage: product will be used for an indeterminate amount of time. Discrete usage: product will be used for a finite period, as defined by the user.' WHERE (`STATIC_LABEL_ID` = @MAX and `LANGUAGE_ID`=1);

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT` = 'Utilisation continue : le produit sera utilisé pendant une durée indéterminée. Utilisation discrète : le produit sera utilisé pendant une période déterminée, définie par l`utilisateur.' WHERE (`STATIC_LABEL_ID` = @MAX and `LANGUAGE_ID`=2);

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT` = 'Uso continuo: el producto se utilizará por tiempo indeterminado. Uso discreto: el producto se utilizará durante un período finito, según lo defina el usuario.' WHERE (`STATIC_LABEL_ID` = @MAX and `LANGUAGE_ID`=3);

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT` = 'Uso contínuo: o produto será usado por tempo indeterminado. Uso discreto: o produto será usado por um período finito, conforme definido pelo usuário.' WHERE (`STATIC_LABEL_ID` = @MAX and `LANGUAGE_ID`=4);
