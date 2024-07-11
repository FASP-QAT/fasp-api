INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.erpFlag','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-checked if this shipment is linked using the Link ERP Shipment screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vérification automatique si cet envoi est lié à l`aide de l`écran Lier l`envoi ERP');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se verifica automáticamente si este envío está vinculado mediante la pantalla Vincular envío de ERP');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Verificado automaticamente se esta remessa está vinculada usando a tela Vincular remessa do ERP');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.qatShipmentId','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Automatically assigned by QAT when supply plan is uploaded to the server');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Attribué automatiquement par QAT lorsque le plan d`approvisionnement est téléchargé sur le serveur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT lo asigna automáticamente cuando el plan de suministro se carga en el servidor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atribuído automaticamente pelo QAT quando o plano de fornecimento é carregado no servidor');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.procurementAgent','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/remove procurement agents in the Update Program Info screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/supprimer des agents d`approvisionnement dans l`écran Mettre à jour les informations sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar/eliminar agentes de adquisiciones en la pantalla Actualizar información del programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar/remover agentes de compras na tela Atualizar informações do programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.localProcurementAgent','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, QAT will use the user-entered local procurement agent lead time in the Update Planning Unit screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, QAT utilisera le délai de livraison de l`agent d`approvisionnement local saisi par l`utilisateur dans l`écran Mettre à jour l`unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcado, QAT utilizará el tiempo de entrega del agente de adquisiciones local ingresado por el usuario en la pantalla Actualizar unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcada, o QAT usará o lead time do agente de compras local inserido pelo usuário na tela Atualizar unidade de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.aru','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered for each country in the Alternate Reporting Unit screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l`utilisateur pour chaque pays dans l`écran Autre unité de déclaration');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingresado por el usuario para cada país en la pantalla Unidad de informes alternativa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário para cada país na tela Unidade de Relatório Alternativa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.conversionFactor','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-enetered in the Alternate Reporting Unit screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l`utilisateur dans l`écran Autre unité de déclaration');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingresado por el usuario en la pantalla Unidad de informes alternativa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário na tela Unidade de relatório alternativa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.emergencyShipment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, shipment will be highlighted red');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si coché, l`envoi sera surligné en rouge');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcado, el envío se resaltará en rojo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcada, a remessa será destacada em vermelho');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.fundingSource','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add/remove funding sources in the Update Program Info screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter/supprimer des sources de financement dans l`écran Mettre à jour les informations sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar/eliminar fuentes de financiación en la pantalla Actualizar información del programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar/remover fontes de financiamento na tela Atualizar informações do programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.budget','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Each shipment beyond the planned stage requires a budget. Add or update budget information in the Budget screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Chaque expédition au-delà du stade prévu nécessite un budget. Ajouter ou mettre à jour les informations budgétaires dans l`écran Budget');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cada envío más allá de la etapa planificada requiere un presupuesto. Agregar o actualizar información del presupuesto en la pantalla Presupuesto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cada remessa além da etapa planejada requer um orçamento. Adicione ou atualize informações de orçamento na tela Orçamento');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.pricePerPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Provided based on the following order: program-specific PA PU price (user entered) -> realm-level PA PU price -> program-specific PU price (user entered)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fourni selon l`ordre suivant : prix PU PU spécifique au programme (saisi par l`utilisateur) -> prix PU PU PA au niveau du domaine -> prix PU spécifique au programme (saisi par l`utilisateur)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se proporciona según el siguiente orden: precio de PU de PA específico del programa (ingresado por el usuario) -> precio de PU de PA a nivel de dominio -> precio de PU de PA específico del programa (ingresado por el usuario)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fornecido com base na seguinte ordem: preço do PA PU específico do programa (inserido pelo usuário) -> preço do PA PU no nível da região -> preço do PU específico do programa (inserido pelo usuário)');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.puCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Order Qty (PU) * Price per PU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité commandée (UE) * Prix par UE');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de pedido (PU) * Precio por PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade do pedido (PU) * Preço por PU');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.freightCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated using the user-entered freight percentage in the Update Program Info screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculé à l`aide du pourcentage de fret saisi par l`utilisateur dans l`écran Mettre à jour les informations sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculado utilizando el porcentaje de flete ingresado por el usuario en la pantalla Actualizar información del programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculado usando a porcentagem de frete inserida pelo usuário na tela Atualizar informações do programa');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.total','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'PU Cost + Freight Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût PU + coût de transport');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo de PU + Costo de flete');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo PU + Custo de Frete');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentTooltip.orderQtyPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Order Qty (ARU) * Conversion Factor');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité commandée (ARU) * Facteur de conversion');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de pedido (ARU) * Factor de conversión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade do pedido (ARU) * Fator de conversão');-- pr