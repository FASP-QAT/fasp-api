INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.allInFirstMonth','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All in the First Month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tout au long du premier mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todo en el primer mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tudo no primeiro mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.monthByMonth','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Month-by-Month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois par mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mes a mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mês a mês');-- pr