INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.editStatus.problemDescText','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Problem Description');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la description du problème');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a descrição do problema');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la descripción del problema');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.editStatus.problemSuggestionText','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter Problem Suggestion');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la suggestion de problème');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Digite a sugestão de problema');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introducir sugerencia de problema');-- sp
