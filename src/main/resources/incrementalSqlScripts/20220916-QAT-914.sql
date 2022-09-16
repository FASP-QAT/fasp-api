INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.label.someSpecialCaseNotAllowed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter valid data. Some special characters are not allowed i.e.(\' \" :)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisissez des données valides. Certains caractères spéciaux ne sont pas autorisés, par exemple (\' \" :)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira dados válidos. Alguns caracteres especiais não são permitidos, por exemplo (\' \" :)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduce datos válidos. Algunos caracteres especiales no están permitidos, por ejemplo (\' \" :)');-- sp
