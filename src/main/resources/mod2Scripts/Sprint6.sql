INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.message.importSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import successful');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importation réussie');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importación exitosa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importação bem-sucedida');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.tracerCategoryInvalidSelection','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Selected Forcasting unit`s Tracer category does not match');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La catégorie Tracer de l`unité de prévision sélectionnée ne correspond pas');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La categoría Tracer de la unidad de pronóstico seleccionada no coincide');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A categoria Tracer da unidade de previsão selecionada não corresponde');-- pr


-- Seema's script
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.MovingAverages','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Moving average is an average that moves along time, dropping older data as it incorporates newer data. For QAT to calculate the moving average, enter the number months in the past that you would like to use in the calculation. See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La moyenne mobile est une moyenne qui évolue dans le temps, supprimant les données plus anciennes à mesure qu\'elle INTègre des données plus récentes. Pour que QAT calcule la moyenne mobile, entrez le nombre de mois passés que vous souhaitez utiliser dans le calcul. Voir Afficher le guidage pour plus d\'informations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El promedio móvil es un promedio que se mueve a lo largo del tiempo, descartando datos más antiguos a medida que incorpora datos más nuevos. Para que QAT calcule el promedio móvil, ingrese el número de meses en el pasado que le gustaría usar en el cálculo. Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A média móvel é uma média que se move ao longo do tempo, descartando dados mais antigos à medida que incorpora dados mais recentes. Para QAT calcular a média móvel, insira o número de meses no passado que você gostaria de usar no cálculo. Consulte Mostrar orientação para obter mais informações.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.SemiAverages','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Semi-average estimates trends based on two halves of a series. QAT divides the actual data into two equal parts (halves) and the arithmetic mean of the values of each part (half) is calculated as the y values of two points on a line. The slope of the trend line is determined by the difference between these y values over time as defined by the midpoints of the two halves of the series or x values of the points. See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Estimations semi-moyennes des tendances basées sur les deux moitiés d\'une série. QAT divise les données réelles en deux parties égales (moitiés) et la moyenne arithmétique des valeurs de chaque partie (moitié) est calculée comme les valeurs Y de deux points sur une ligne. La pente de la ligne de tendance est déterminée par la différence entre ces valeurs Y dans le temps, telles que définies par les points médians des deux moitiés de la série ou les valeurs X des points. Voir Afficher le guidage pour plus d\'informations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Semi-promedio estima tendencias basadas en dos mitades de una serie. QAT divide los datos reales en dos partes iguales (mitades) y la media aritmética de los valores de cada parte (mitad) se calcula como los valores y de dos puntos en una línea. La pendiente de la línea de tendencia está determinada por la diferencia entre estos valores y a lo largo del tiempo, según lo definido por los puntos medios de las dos mitades de la serie o los valores x de los puntos. Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A semi-média estima tendências com base em duas metades de uma série. QAT divide os dados reais em duas partes iguais (metades) e a média aritmética dos valores de cada parte (metade) é calculada como os valores de y de dois pontos em uma linha. A inclinação da linha de tendência é determinada pela diferença entre esses valores de y ao longo do tempo, conforme definido pelos pontos médios das duas metades da série ou valores de x dos pontos. Consulte Mostrar orientação para obter mais informações.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.LinearRegression','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linear regression models the relationship between two variables by fitting a linear equation to observed data');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La régression linéaire modélise la relation entre deux variables en ajustant une équation linéaire aux données observées');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La regresión lineal modela la relación entre dos variables ajustando una ecuación lineal a los datos observados');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A regressão linear modela a relação entre duas variáveis ​​ajustando uma equação linear aos dados observados');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.Tes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Whereas a moving average weighs each data point equally, exponential smoothing uses older data at exponentially decreasing weights over time. Triple exponential smoothing applies smoothing to the level (alpha), trend (beta), and seasonality (gamma) - parameters are set between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alors qu\une moyenne mobile pèse chaque point de données de manière égale, le lissage exponentiel utilise des données plus anciennes à des poids décroissants de manière exponentielle au fil du temps. Le triple lissage exponentiel applique le lissage au niveau (alpha), à la tendance (bêta) et à la saisonnalité (gamma) - les paramètres sont définis entre 0 et 1, les valeurs proches de 1 favorisant les valeurs récentes et les valeurs proches de 0 favorisant les valeurs passées.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mientras que un promedio móvil pondera cada punto de datos por igual, el suavizado exponencial utiliza datos más antiguos con ponderaciones que disminuyen exponencialmente a lo largo del tiempo. El suavizado exponencial triple aplica suavizado al nivel (alfa), la tendencia (beta) y la estacionalidad (gamma): los parámetros se establecen entre 0 y 1, con valores cercanos a 1 que favorecen los valores recientes y valores cercanos a 0 que favorecen los valores pasados.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enquanto uma média móvel pesa cada ponto de dados igualmente, a suavização exponencial usa dados mais antigos com pesos exponencialmente decrescentes ao longo do tempo. A suavização exponencial tripla aplica a suavização ao nível (alfa), tendência (beta) e sazonalidade (gama) - os parâmetros são definidos entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.confidenceLevel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'between 0% and 100% (exclusive) e.g. 90% confidence level indicates 90% of future points are to fall within this radius from prediction.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'entre 0% et 100% (exclusif) par ex. Un niveau de confiance de 90 % indique que 90 % des points futurs doivent se situer dans ce rayon de prédiction.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'entre 0% y 100% (exclusivo) p. El nivel de confianza del 90% indica que el 90% de los puntos futuros caerán dentro de este radio de predicción.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'entre 0% e 100% (exclusivo) e. O nível de confiança de 90% indica que 90% dos pontos futuros devem cair dentro desse raio de previsão.');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.seasonality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'the expected length (in months) of any repetitive pattern in the consumption');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'la durée prévue (en mois) de tout schéma répétitif dans la consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'la duración esperada (en meses) de cualquier patrón repetitivo en el consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'a duração esperada (em meses) de qualquer padrão repetitivo no consumo');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.alpha','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Alpha (level), beta (trend), gamma (seasonality) between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alpha (niveau), beta (tendance), gamma (saisonnalité)  entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.
Voir Afficher le guidage pour plus d\informations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alfa (nivel), beta (tendencia), gamma (estacionalidad) entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.
Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alfa (nível), beta (tendência), gama (sazonalidade) entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.
Consulte Mostrar orientação para obter mais informações.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.beta','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Alpha (level), beta (trend), gamma (seasonality) between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alpha (niveau), beta (tendance), gamma (saisonnalité)  entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.
Voir Afficher le guidage pour plus d\informations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alfa (nivel), beta (tendencia), gamma (estacionalidad) entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.
Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alfa (nível), beta (tendência), gama (sazonalidade) entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.
Consulte Mostrar orientação para obter mais informações.');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.gamma','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Alpha (level), beta (trend), gamma (seasonality): between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alpha (niveau), beta (tendance), gamma (saisonnalité) : entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.
Voir Afficher le guidage pour plus d\informations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alfa (nivel), beta (tendencia), gamma (estacionalidad): entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.
Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alfa (nível), beta (tendência), gama (sazonalidade): entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.
Consulte Mostrar orientação para obter mais informações.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.arima','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-regressive means each point is influenced by its previous values and moving average which is a linear combination of several points. Both parts are integrated together to fit a best model for the series.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Auto-régressif signifie que chaque point est influencé par ses valeurs précédentes et sa moyenne mobile qui est une combinaison linéaire de plusieurs points. Les deux parties sont intégrées ensemble pour s\adapter au meilleur modèle de la série.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Autorregresivo significa que cada punto está influenciado por sus valores anteriores y el promedio móvil, que es una combinación lineal de varios puntos. Ambas partes están integradas para adaptarse al mejor modelo de la serie.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Auto-regressivo significa que cada ponto é influenciado por seus valores anteriores e média móvel, que é uma combinação linear de vários pontos. Ambas as partes são integradas para encaixar o melhor modelo para a série.');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.p','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'p or AR (lag order) the number of lag observations in the model');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'p ou AR (ordre des retards) le nombre d\observations de retard dans le modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'p o AR (orden de retraso) el número de observaciones de retraso en el modelo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'p ou AR (ordem de atraso)  o número de observações de atraso no modelo');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.d','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'d or I (degree of differencing) the number of times that the raw observations are differenced.  This value is normally 1 (if there is a trend) or 0 (no trend).  Other higher values are possible but not expected');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'d ou I (degré de différenciation) le nombre de fois que les observations brutes sont différenciées. Cette valeur est normalement 1 (s\il Y a une tendance) ou 0 (pas de tendance). D\autres valeurs plus élevées sont possibles mais pas attendues');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'d o I (grado de diferenciación) el número de veces que se diferencian las observaciones sin procesar. Este valor es normalmente 1 (si hay tendencia) o 0 (sin tendencia). Otros valores más altos son posibles pero no esperados');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'d ou I (grau de diferenciação) o número de vezes que as observações brutas são diferenciadas. Esse valor normalmente é 1 (se houver tendência) ou 0 (sem tendência). Outros valores mais altos são possíveis, mas não esperados');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.q','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'q or MA (order of the moving average) the size of the moving average window.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'q ou MA (ordre de la moyenne mobile) la taille de la fenêtre de la moyenne mobile.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'q o MA (orden de la media móvil) el tamaño de la ventana de la media móvil.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'q ou MA (ordem da média móvel) o tamanho da janela da média móvel.');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.ReportingRate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage of all data that was reported. This number is used to adjust the historical data upwards to account for missing data. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de toutes les données qui ont été déclarées. Ce nombre est utilisé pour ajuster les données historiques vers le haut pour tenir compte des données manquantes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de todos los datos que se informó. Este número se utiliza para ajustar los datos históricos hacia arriba para tener en cuenta los datos que faltan.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem de todos os dados que foram relatados. Esse número é usado para ajustar os dados históricos para cima para contabilizar dados ausentes.');

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.errors','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'RMSE Root Mean Square Error  MAPE Mean Absolute Percentage Error MSE Mean Squared Error WAPE Weighted Absolute Percentage Error See Show Guidance for more');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'RMSE Erreur quadratique moyenne MAPE Erreur absolue moyenne en pourcentage MSE Erreur quadratique moyenne WAPE Erreur absolue en pourcentage pondérée Voir Afficher les conseils pour plus d\informations');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'RMSE Error cuadrático medio MAPE Error porcentual absoluto medio MSE Error cuadrático medio WAPE Error porcentual absoluto ponderado Consulte Mostrar guía para obtener más información');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro quadrático médio raiz RMSE Erro percentual médio absoluto MAPE Erro quadrático médio MSE Erro percentual absoluto ponderado WAPE Consulte Mostrar orientação para obter mais informações');


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.ChooseMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select which extrapolation method is used for the node value.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez la méthode d\extrapolation utilisée pour la valeur du nœud.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione qué método de extrapolación se utiliza para el valor del nodo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione qual método de extrapolação é usado para o valor do nó.');

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Change from previous month'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Changement par rapport au mois précédent'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Cambio del mes anterior'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Alteração do mês anterior'
where l.LABEL_CODE='static.tree.calculatedChange+-' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Node Forecast (No seasonality)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Prévision de nœud (pas de saisonnalité)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Pronóstico de Nodo (Sin estacionalidad)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Previsão de nós (sem sazonalidade)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.nodeForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Node Forecast');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Node Forecast');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Node Forecast');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.levelDetails','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level Details');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Détails du niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles del nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes do nível');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.levelName','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Level name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom du niveau');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del nivel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do nível');-- pr

update ap_static_label l set l.LABEL_CODE=TRIM(l.LABEL_CODE);