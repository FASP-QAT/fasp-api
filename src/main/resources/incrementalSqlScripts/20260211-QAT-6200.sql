INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.budget.duplicateBudget','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Budget code already exists for following program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un code budgétaire existe déjà pour le programme suivant');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ya existe el código de presupuesto para el siguiente programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O código orçamentário já existe para o seguinte programa.');-- pr