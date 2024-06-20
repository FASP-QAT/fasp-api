INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated change is determined by the modeling table above.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le changement calculé est déterminé par le tableau de modélisation ci-dessus.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El cambio calculado está determinado por la tabla de modelado anterior.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A mudança calculada é determinada pela tabela de modelagem acima.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Previous Month\'s %) + (Calculated Change +/- %)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (%) du mois précédent + (variation calculée +/- %)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= (% del mes anterior) + (cambio calculado +/-%)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= (% do mês anterior) + (alteração calculada +/-%)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'% over or under the average value. Enter a positive % for an increased value in this month or a negative value for a decreased value'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'% supérieur ou inférieur à la valeur moyenne. Entrez un % positif pour une valeur augmentée ce mois-ci ou une valeur négative pour une valeur diminuée'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'% por encima o por debajo del valor medio. Ingrese un % positivo para un valor aumentado en este mes o un valor negativo para un valor disminuido'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'% acima ou abaixo do valor médio. Insira uma % positiva para um valor aumentado neste mês ou um valor negativo para um valor diminuído'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter any changes not captured by the modeling table or seasonality index. If the checkbox "Manual change affects future month" is checked, this change will affect future months. If unchecked, this change will only affect the current month'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisissez toutes les modifications non capturées par la table de modélisation ou l\'indice de saisonnalité. Si la case « La modification manuelle affecte les mois suivants » est cochée, cette modification affectera les mois suivants. Si cette case n\'est pas cochée, ce changement n\'affectera que le mois en cours.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese cualquier cambio no capturado por la tabla de modelado o el índice de estacionalidad. Si está marcada la casilla "El cambio manual afecta a los meses futuros", este cambio afectará a los meses futuros. Si no está marcado, este cambio solo afectará al mes actual.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira quaisquer alterações não capturadas pela tabela de modelagem ou índice de sazonalidade. Se a caixa de seleção "A alteração manual afeta o mês futuro" estiver marcada, esta alteração afetará os meses futuros. Se desmarcada, esta alteração afetará apenas o mês atual'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The final % for this node = (% of Population (No Seasonality))*(1+Seasonality Index)+(Manual Change)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le % final pour ce nœud = (% de la population (pas de saisonnalité))*(1+indice de saisonnalité)+(Changement manuel)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El % final para este nodo = (% de la población (sin estacionalidad))*(1+Índice de estacionalidad)+(Cambio manual)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A% final para este nó = (% da população (sem sazonalidade))*(1+Índice de sazonalidade)+(Mudança manual)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip6','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This is the value of the parent node'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'C\'est la valeur du nœud parent'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este es el valor del nodo padre'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este é o valor do nó pai'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip7','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The forecasted value for this node = (% of Parent) * (Parent Value). In other words, it is is the product of the two previous columns.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La valeur prévue pour ce nœud = (% du parent) * (Valeur parent). Autrement dit, c’est le produit des deux colonnes précédentes.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El valor previsto para este nodo = (% del padre) * (valor principal). En otras palabras, es el producto de las dos columnas anteriores.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O valor previsto para este nó = (% do pai) * (valor pai). Em outras palavras, é o produto das duas colunas anteriores.'); -- pr