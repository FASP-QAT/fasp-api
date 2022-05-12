update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='The minimum values needed for the various features are below:\n* TES, Holt-Winters: At least 24 months of historical data\n* ARIMA: With seasonality : At least 13 months historical data required.Without seasonality at least 2 months historical data required.\n* Moving Average, Semi-Averages, and Linear Regression: At least 3 months of historical data'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Les valeurs minimales nécessaires pour les différentes fonctionnalités sont les suivantes :\n* TES, Holt-Winters : au moins 24 mois de données historiques\n* ARIMA : avec saisonnalité : au moins 13 mois de données historiques requises. Sans saisonnalité, au moins 2 mois dhistorique données requises.\n* Moyenne mobile, semi-moyennes et régression linéaire : au moins 3 mois de données historiques'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Los valores mínimos necesarios para las distintas características son los siguientes:\n* TES, Holt-Winters: al menos 24 meses de datos históricos\n* ARIMA: con estacionalidad: se requieren al menos 13 meses de datos históricos. Sin estacionalidad, al menos 2 meses de historial datos requeridos.\n* Promedio móvil, semipromedios y regresión lineal: al menos 3 meses de datos históricos'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Os valores mínimos necessários para os vários recursos estão abaixo:\n* TES, Holt-Winters: pelo menos 24 meses de dados históricos\n* ARIMA: com sazonalidade: pelo menos 13 meses de dados históricos necessários.Sem sazonalidade pelo menos 2 meses de histórico dados necessários.\n* Média móvel, semi-médias e regressão linear: pelo menos três meses de dados históricos'
where l.LABEL_CODE='static.tree.minDataRequiredToExtrapolate' and ll.LANGUAGE_ID=4;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.minDataRequiredToExtrapolateNote1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'NOTE: You have ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'REMARQUE : Vous avez ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'NOTA: Tienes ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'NOTA: Você tem ');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.minDataRequiredToExtrapolateNote2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' month(s) of historical data.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' mois de données historiques.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' mes(es) de datos históricos.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' mês(s) de dados históricos.');-- pr