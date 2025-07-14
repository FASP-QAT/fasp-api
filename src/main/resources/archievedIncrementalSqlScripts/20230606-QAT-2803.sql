INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.mt.roNoAndPrimeLineNo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'RO - Prime line No');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'RO - Ligne principale Non');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'RO - Linha Prime Não');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'RO - Línea principal No');-- sp

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Order No - Prime line No'
where l.LABEL_CODE='static.mt.orderNoAndPrimeLineNo' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='N° de commande - N° de ligne principale'
where l.LABEL_CODE='static.mt.orderNoAndPrimeLineNo' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Nº do pedido - Nº da linha principal'
where l.LABEL_CODE='static.mt.orderNoAndPrimeLineNo' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='N.º de pedido|N.º de línea principal'
where l.LABEL_CODE='static.mt.orderNoAndPrimeLineNo' and ll.LANGUAGE_ID=3;
