INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.totalAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Adjustment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajustement total');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste total');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste Total');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.manualAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adjustment (manual)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajustement (manuel)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste (manual)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste (manual)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.nationalAdjustment','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'National Adjustment (auto)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajustement national (auto)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajuste nacional (automático)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ajuste Nacional (automático)');-- pr