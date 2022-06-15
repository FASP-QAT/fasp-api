INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.NodeTitle','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The node title will show up on the Tree View');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le titre du nœud apparaîtra dans larborescence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El título del nodo aparecerá en la vista de árbol');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O título do nó aparecerá na visualização em árvore');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.scenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose scenario for which the data below applies');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez le scénario pour lequel les données ci-dessous sappliquent.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija el escenario para el que se aplican los datos a continuación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o cenário para o qual os dados abaixo se aplicam.');-- pr