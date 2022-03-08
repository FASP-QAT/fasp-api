INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculater','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculator');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculatrice de modélisation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculadora de modelado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculadora de modelagem');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.modelingCalculaterTool','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Modeling Calculator Tool:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Outil de calcul de modélisation:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Herramienta de calculadora de modelado:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ferramenta Calculadora de Modelagem:');