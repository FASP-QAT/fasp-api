INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.positiveAdjustmentFEFO','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Longest shelf life (+ adj)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Durée de conservation la plus longue (+ adj)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vida útil más larga (+ adj)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Maior vida útil (+ adj)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.negativeAdjustmentFEFO','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shortest shelf life (- adj)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Durée de conservation la plus courte (- adj)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vida útil más corta (- adj)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Menor vida útil (- adj)');-- pr