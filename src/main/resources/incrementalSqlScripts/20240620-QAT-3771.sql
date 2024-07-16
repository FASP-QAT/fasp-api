INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated change is determined by the modeling table above.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le changement calculé est déterminé par le tableau de modélisation ci-dessus.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El cambio calculado está determinado por la tabla de modelado anterior.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A mudança calculada é determinada pela tabela de modelagem acima.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Previous Month\'s %) + (Calculated Change +/- %)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(%) du mois précédent + (variation calculée +/- %)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(% del mes anterior) + (cambio calculado +/-%)'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(% do mês anterior) + (alteração calculada +/-%)'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter a positive % for an increased value in this month or a negative value for a decreased value.  For example, 5% indicates that this month\'s value is 5% over the non-seasonal value (or 105%), whereas -5% indicates that it is 5% under the non-seasonal value (or 95%).'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez un % positif pour une valeur augmentée ce mois-ci ou une valeur négative pour une valeur diminuée.  Par exemple, 5 % indique que la valeur de ce mois est de 5 % au-dessus de la valeur non saisonnière (ou 105 %), tandis que -5 % indique qu\'elle est de 5 % en dessous de la valeur non saisonnière (ou 95 %).'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese un % positivo para un valor aumentado en este mes o un valor negativo para un valor disminuido.  Por ejemplo, 5% indica que el valor de este mes está un 5% por encima del valor no estacional (o 105%), mientras que -5% indica que está un 5% por debajo del valor no estacional (o 95%).'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira uma % positiva para um valor aumentado neste mês ou um valor negativo para um valor diminuído.  Por exemplo, 5% indica que o valor deste mês está 5% acima do valor não sazonal (ou 105%), enquanto -5% indica que está 5% abaixo do valor não sazonal (ou 95%).'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter any changes not captured by the modeling table or seasonality index. If the checkbox "Manual change affects future month" is checked, this change will affect future months. If unchecked, this change will only affect the current month'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisissez toutes les modifications non capturées par la table de modélisation ou l\'indice de saisonnalité. Si la case « La modification manuelle affecte les mois suivants » est cochée, cette modification affectera les mois suivants. Si cette case n\'est pas cochée, ce changement n\'affectera que le mois en cours.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese cualquier cambio no capturado por la tabla de modelado o el índice de estacionalidad. Si está marcada la casilla "El cambio manual afecta a los meses futuros", este cambio afectará a los meses futuros. Si no está marcado, este cambio solo afectará al mes actual.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira quaisquer alterações não capturadas pela tabela de modelagem ou índice de sazonalidade. Se a caixa de seleção "A alteração manual afeta o mês futuro" estiver marcada, esta alteração afetará os meses futuros. Se desmarcada, esta alteração afetará apenas o mês atual'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(% of Population (No Seasonality))*(1+Seasonality Index)+(Manual Change). In other words, the final % for this node.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(% de la population (pas de saisonnalité))*(1+Indice de saisonnalité)+(Changement manuel). En d\'autres termes, le % final pour ce nœud.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(% de la población (sin estacionalidad))*(1+Índice de estacionalidad)+(Cambio manual). En otras palabras, el % final para este nodo.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(% da População (Sem Sazonalidade))*(1+Índice de Sazonalidade)+(Alteração Manual). Em outras palavras, a% final para este nó.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip6','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Value of the parent node.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud parent.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor del nodo padre.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó pai.'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.momper.tooltip7','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(% of Parent) * (Parent Value). In other words, the forecasted value for this node is the product of the two previous columns.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(% du parent) * (Valeur parent). Autrement dit, la valeur prévue pour ce nœud est le produit des deux colonnes précédentes.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(% de la matriz) * (Valor de la matriz). En otras palabras, el valor previsto para este nodo es el producto de las dos columnas anteriores.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(% do pai) * (valor pai). Em outras palavras, o valor previsto para este nó é o produto das duas colunas anteriores.'); -- pr