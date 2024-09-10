INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.treeTable','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree Table');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Table d\'arbre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mesa de árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mesa de árvore');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.in','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'em');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.numberOrPercentage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number / Percentage Node Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre/Pourcentage Valeur du nœud');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número/valor de nodo porcentual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó número/porcentagem');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.numberOrPercentageTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the value or percentage of parent at the time of the Start Month. If this value changes over time, update in the Add/Edit Node, Modeling/Transfer tab.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la valeur ou le pourcentage du parent au moment du mois de début. Si cette valeur évolue au fil du temps, mettez à jour dans l\'onglet Ajouter/Modifier un nœud, Modélisation/Transfert.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el valor o porcentaje del padre en el momento del mes de inicio. Si este valor cambia con el tiempo, actualícelo en la pestaña Agregar/Editar nodo, Modelado/Transferencia.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o valor ou porcentagem do pai no momento do Mês de Início. Se esse valor mudar ao longo do tempo, atualize na guia Adicionar/Editar Nó, Modelagem/Transferência.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.parentValueTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Value is calculated based on the Display Date. To change the Parent Value, edit the Parent Node directly.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La valeur est calculée en fonction de la date d\'affichage. Pour modifier la valeur parent, modifiez directement le nœud parent.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El valor se calcula en función de la fecha de visualización. Para cambiar el valor principal, edite el nodo principal directamente.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O valor é calculado com base na Data de Exibição. Para alterar o valor pai, edite o nó pai diretamente.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.nodeValueTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Value is calculated based on the Display Date.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La valeur est calculée en fonction de la date d\'affichage.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El valor se calcula en función de la fecha de visualización.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O valor é calculado com base na Data de Exibição.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.numberNodeValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number Node Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud numérique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo numérico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó numérico');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.percentageNodeValue','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage Node Value');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud en pourcentage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo porcentual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor percentual do nó');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.conversionFactor','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.PUReference','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# PU / Interval / Patient (Reference)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# PU / Intervalle / Patient (Référence)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# PU / Intervalo / Paciente (Referencia)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# PU/Intervalo/Paciente (Referência)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.PUInterval','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# PU / Interval / Patient');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# PU / Intervalle / Patient');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# PU / Intervalo / Paciente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# PU/Intervalo/Paciente');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.oneTimeDispensing','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'One Time Dispensing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Distribution unique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Dispensación única');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dispensação única');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.period','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Période');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Período');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Período');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.tab1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Aggregation/Number/Percentage Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud Agrégation/Nombre/Pourcentage');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo Agregación/Número/Porcentaje');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó de agregação/número/porcentagem');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.tab2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FU/PU Node');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nœud FU/PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nodo FU/PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nó FU/PU');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.editIn','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar em');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.updateNotes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Calculated values in gray cells only recalculate after clicking `Update`.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : les valeurs calculées dans les cellules grises ne sont recalculées qu`après avoir cliqué sur « Mettre à jour ».');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Los valores calculados en celdas grises solo se recalculan después de hacer clic en `Actualizar`.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: os valores calculados nas células cinzas só serão recalculados após clicar em `Atualizar`.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTable.rightClickNotes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'right click on any row and click `Open this node for editing` to open that specific node in a separate QAT window');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'faites un clic droit sur n`importe quelle ligne et cliquez sur « Ouvrir ce nœud pour l`édition » pour ouvrir ce nœud spécifique dans une fenêtre QAT distincte');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic derecho en cualquier fila y haga clic en `Abrir este nodo para editar` para abrir ese nodo específico en una ventana QAT separada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'clique com o botão direito em qualquer linha e clique em `Abrir este nó para edição` para abrir esse nó específico em uma janela QAT separada');-- pr
