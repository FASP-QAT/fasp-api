update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Select the forecast program and the desired review period. By default, QAT pre-selects a date range of 36 months prior to the start of the forecasting period. A non-editable table and all products related to this forecast program are displayed, along with their adjusted consumption data if you have previously entered that data;'
where l.LABEL_CODE='static.dataEntryAndAdjustments.DesiredReview' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Sélectionnez le programme prévisionnel et la période de révision souhaitée. Par défaut, QAT présélectionne une plage de dates de 36 mois avant le début de la période de prévision. Un tableau non modifiable et tous les produits liés à ce programme de prévisions sont affichés, ainsi que leurs données de consommation ajustées si vous avez déjà saisi ces données;'
where l.LABEL_CODE='static.dataEntryAndAdjustments.DesiredReview' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Seleccione el programa de pronóstico y el período de revisión deseado. De forma predeterminada, QAT preselecciona un rango de fechas de 36 meses antes del inicio del período de pronóstico. Se muestra una tabla no editable y todos los productos relacionados con este programa de previsión, junto con sus datos de consumo ajustados si ha ingresado previamente esos datos;'
where l.LABEL_CODE='static.dataEntryAndAdjustments.DesiredReview' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Selecione o programa de previsão e o período de revisão desejado. Por padrão, o QAT pré-seleciona um intervalo de datas de 36 meses antes do início do período de previsão. Uma tabela não editável e todos os produtos relacionados a este programa de previsão são exibidos, juntamente com seus dados de consumo ajustados, caso você tenha inserido esses dados anteriormente;'
where l.LABEL_CODE='static.dataEntryAndAdjustments.DesiredReview' and ll.LANGUAGE_ID=4;



update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Number nodes can only transfer to other number nodes and must belong to the same level.'
where l.LABEL_CODE='static.ModelingTransfer.NumberNode' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Les nœuds numériques ne peuvent transférer que vers dautres nœuds numériques et doivent appartenir au même niveau.'
where l.LABEL_CODE='static.ModelingTransfer.NumberNode' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Los nodos numéricos solo pueden transferirse a otros nodos numéricos y deben pertenecer al mismo nivel.'
where l.LABEL_CODE='static.ModelingTransfer.NumberNode' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Nós de número só podem ser transferidos para outros nós de número e devem pertencer ao mesmo nível.'
where l.LABEL_CODE='static.ModelingTransfer.NumberNode' and ll.LANGUAGE_ID=4;



update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Percentage nodes can only transfer to other percentage nodes and must belong to the same parent.'
where l.LABEL_CODE='static.ModelingTransfer.PercentageFUPU' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Les nœuds de pourcentage ne peuvent être transférés que vers dautres nœuds de pourcentage et doivent appartenir au même parent.'
where l.LABEL_CODE='static.ModelingTransfer.PercentageFUPU' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Los nodos de porcentaje solo pueden transferirse a otros nodos de porcentaje y deben pertenecer al mismo padre.'
where l.LABEL_CODE='static.ModelingTransfer.PercentageFUPU' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Os nós de porcentagem só podem ser transferidos para outros nós de porcentagem e devem pertencer ao mesmo pai.'
where l.LABEL_CODE='static.ModelingTransfer.PercentageFUPU' and ll.LANGUAGE_ID=4;




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ModelingTransfer.OrderOfOperations','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The order of operations for calculating a transfer occurs from the left to the right in the forecast tree. A transfer cannot be made from right to left, thus a user should be careful when designing their tree and determining where each node should be placed.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lordre des opérations de calcul dun transfert seffectue de gauche à droite dans larbre de prévision. Un transfert ne peut pas être effectué de droite à gauche, donc un utilisateur doit être prudent lors de la conception de son arbre et déterminer où chaque nœud doit être placé.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El orden de las operaciones para calcular una transferencia ocurre de izquierda a derecha en el árbol de pronóstico. No se puede realizar una transferencia de derecha a izquierda, por lo que el usuario debe tener cuidado al diseñar su árbol y determinar dónde se debe colocar cada nodo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A ordem das operações para calcular uma transferência ocorre da esquerda para a direita na árvore de previsão. Uma transferência não pode ser feita da direita para a esquerda, portanto, o usuário deve ter cuidado ao projetar sua árvore e determinar onde cada nó deve ser colocado.');-- pr


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ModelingTransfer.TransferDestination','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Transfers are always negative from the source node and positive to the destination node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les transferts sont toujours négatifs depuis le nœud source et positifs vers le nœud destination.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las transferencias son siempre negativas desde el nodo de origen y positivas hacia el nodo de destino.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'As transferências são sempre negativas do nó de origem e positivas para o nó de destino.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ModelingTransfer.ExtrapolationNotAllowed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Extrapolation is not allowed on a node that also has a transfer, whether that be to/from another node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lextrapolation nest pas autorisée sur un nœud qui a également un transfert, que ce soit vers/depuis un autre nœud.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se permite la extrapolación en un nodo que también tiene una transferencia, ya sea hacia o desde otro nodo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A extrapolação não é permitida em um nó que também tenha uma transferência, seja de/para outro nó.');-- pr

