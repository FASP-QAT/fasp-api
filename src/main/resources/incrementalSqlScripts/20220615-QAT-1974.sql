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

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.LagInMonthFUNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this feature for phased product usage. For example, if the lag is 2, the product usage will begin 2 months after the parent node dates');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette fonction pour une utilisation progressive du produit. Par exemple, si le décalage est de 2, lutilisation du produit commencera 2 mois après les dates du nœud parent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta función para el uso del producto por etapas. Por ejemplo, si el retraso es 2, el uso del producto comenzará 2 meses después de las fechas del nodo principal.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esse recurso para uso do produto em fases. Por exemplo, se o atraso for 2, o uso do produto começará 2 meses após as datas do nó pai');-- pr


