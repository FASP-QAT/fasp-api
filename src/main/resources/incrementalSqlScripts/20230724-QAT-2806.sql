INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.xAxisDisplay','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'X-axis display');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Affichage de l\'axe X');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'visualización del eje X');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibição do eixo X');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.yAxisDisplay','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Y-axis display');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Affichage de l\'axe Y');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'visualización del eje Y');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibição do eixo Y');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.levelUnit1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level (Unit)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Niveau (unité)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nivel (Unidad)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nível (Unidade)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.calendarYear','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calendar Year');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année civile');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año del calendario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano civil');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyJul','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Jul-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en juillet 22)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 22 de julio)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de julho)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyAug','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Aug-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence le 22 août)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (p. ej., el año fiscal 2023 comienza el 22 de agosto)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de agosto)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fySep','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Sep-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en septembre 22)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 22 de septiembre)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de setembro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyOct','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Oct-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en octobre 22)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 22 de octubre)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de Outubro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyNov','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Nov-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en novembre 22)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 22 de noviembre)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de novembro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyDec','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Dec-22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en décembre 22)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 22 de diciembre)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 22 de dezembro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyJan','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Jan-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en Janvier 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de Enero)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de Janeiro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyFeb','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Feb-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en février 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de febrero)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de fevereiro)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyMar','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Mar-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en mars 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de marzo)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de marchar)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyApr','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Apr-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en Avril 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de Abril)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de abril)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyMay','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in May-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en Peut 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de Puede)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de Poderia)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingValidation.fyJun','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Fiscal Year (e.g. FY2023 starts in Jun-23)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Année fiscale (par exemple, l\'exercice 2023 commence en Juin 23)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Año fiscal (por ejemplo, el año fiscal 2023 comienza el 23 de Junio)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ano fiscal (por exemplo, o ano fiscal de 2023 começa em 23 de Junho)');-- pr

