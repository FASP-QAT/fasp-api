INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.times','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'times');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'fois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'veces');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'vezes');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.inTotal','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'In total, ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Au total, ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En total, ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'No total, ');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.eachTime','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'each time');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'chaque fois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'cada vez');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'cada vez');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.indefinitely','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'indefinitely');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'indéfiniment');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'indefinidamente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'indefinidamente');-- pr
