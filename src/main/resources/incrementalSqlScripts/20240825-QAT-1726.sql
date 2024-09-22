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