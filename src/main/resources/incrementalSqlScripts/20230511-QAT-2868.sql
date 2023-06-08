INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.report.overspentBudget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Overspent Budget');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Budget dépassé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Orçamento gasto em excesso');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Presupuesto gastado en exceso');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.report.budgetRemaining','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Budget Remaining');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Budget restant');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Orçamento restante');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Presupuesto restante');-- sp
