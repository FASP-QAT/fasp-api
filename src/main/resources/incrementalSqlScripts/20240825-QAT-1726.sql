INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.totalAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajustement total');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste total');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste Total');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.manualAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manual Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réglage manuel');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste manual');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste manual');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.nationalAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Automatic Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réglage automatique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste automático');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste automático');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.projectedQuantity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Projected Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité projetée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad proyectada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade projetada');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.actualQuantity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité réelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade real');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qtyNotAvailable','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Batch Quantity Not available');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité du lot Non disponible');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de lote No disponible');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade do lote Não disponível');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.actualInventoryNote1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click white cells to see data entry rows. Click on the `Final Inventory` row to edit batch details.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur les cellules blanches pour voir les lignes de saisie de données. Cliquez sur la ligne « Inventaire final » pour modifier les détails du lot.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en las celdas blancas para ver las filas de entrada de datos. Haga clic en la fila ""Inventario final"" para editar los detalles del lote.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique nas células brancas para ver as linhas de entrada de dados. Clique na linha `Inventário Final` para editar os detalhes do lote.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.actualInventoryNote2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Right click to add/remove a batch, or to see the batch ledger for that batch');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez avec le bouton droit pour ajouter/supprimer un lot ou pour voir le grand livre de lots pour ce lot');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic con el botón derecho para agregar o eliminar un lote, o para ver el libro mayor de lotes correspondiente a ese lote');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique com o botão direito para adicionar/remover um lote ou para ver o livro-razão do lote para esse lote');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.batchTotal','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Batch Total');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Total du lot');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Total del lote');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Total do lote');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.inventoryTotal','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory Total');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Total de l`inventaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Total del inventario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Total do estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.batchQtyNotAvailable','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Batch Total does not match Inventory Total');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le total du lot ne correspond pas au total de l`inventaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El total del lote no coincide con el total del inventario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O total do lote não corresponde ao total do estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.combinationNotAllowed','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You cannot have combination of QAT auto-calculation and other batch details');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous ne pouvez pas avoir de combinaison de calcul automatique du QAT et d`autres détails du lot');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No puede tener una combinación del cálculo automático de QAT y otros detalles del lote');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você não pode ter a combinação de cálculo automático de QAT e outros detalhes do lote');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qatAutocalculations','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT auto-calculation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul automatique du QAT');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo automático de QAT');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo automático de QAT');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.consumptionFEFO','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Calculation (FEFO)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul du QAT (FEFO)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo de QAT (FEFO)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo de QAT (FEFO)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.adjustmentFEFO','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Calculation (Best Case Scenario)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul du QAT (scénario du meilleur cas)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo de QAT (mejor escenario posible)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo de QAT (Melhor cenário)');-- pr