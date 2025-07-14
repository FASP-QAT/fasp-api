INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.planningUnit.conversionFactor','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max 10 digit number and 2 digits after decimal are allowed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un nombre maximum de 10 chiffres et 2 chiffres après la virgule sont autorisés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se permiten un número máximo de 10 dígitos y 2 dígitos después del decimal.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'São permitidos no máximo 10 dígitos e 2 dígitos após o decimal.');-- pr
