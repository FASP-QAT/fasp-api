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

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.hideMonthlyData','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Hide monthly data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Masquer les données mensuelles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ocultar datos mensuales');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ocultar dados mensais');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.decimalValidation12&2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter a valid number having max 12 digits before decimal and max 2 digit after decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un nombre valide comportant au maximum 12 chiffres avant la virgule et au maximum 2 chiffres après la virgule.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un número válido que tenga un máximo de 12 dígitos antes del decimal y un máximo de 2 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número válido com no máximo 12 dígitos antes do decimal e no máximo 2 dígitos após o decimal.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.decimalValidation10&2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter a valid number having max 10 digits before decimal and max 2 digit after decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir un nombre valide comportant au maximum 10 chiffres avant la virgule et au maximum 2 chiffres après la virgule.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un número válido que tenga un máximo de 10 dígitos antes del decimal y un máximo de 2 dígitos después del decimal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira um número válido com no máximo 10 dígitos antes do decimal e no máximo 2 dígitos após o decimal.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.templateNameRequired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter template name.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nom du modèle.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el nombre de la plantilla.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o nome do modelo.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.duplicateTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Duplicate Template');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dupliquer le modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plantilla duplicada');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Modelo duplicado');