-- Priority
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Priority');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Priorité');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Prioridade');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Prioridad');-- sp


-- Priority Highest
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.highest','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Highest');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le plus élevé');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Altíssima');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mas alto');-- sp

-- Priority Desc Highest
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.highestDesc','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT is down or user cannot log in.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"QAT est en panne ou l'utilisateur ne peut pas se connecter.");-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O QAT está inativo ou o usuário não consegue fazer login.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT está inactivo o el usuario no puede iniciar sesión.');-- sp

-- Priority High
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.high','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'High');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Haut');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alto');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alto');-- sp

-- Priority Desc High
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.highDesc','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Problem involves significant inconveniences for users, such as not being able to complete their forecast or supply plan. (Affects a business-critical function and no workarounds exist).');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"Le problème entraîne des inconvénients importants pour les utilisateurs, tels que l'impossibilité de finaliser leurs prévisions ou leur plan d'approvisionnement. (Affecte une fonction critique pour l'entreprise et aucune solution de contournement n'existe).");-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"O problema envolve inconvenientes significativos para os usuários, como não conseguir completar sua previsão ou plano de fornecimento. (Afeta uma função crítica para os negócios e não existem soluções alternativas).");-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,"El problema implica importantes inconvenientes para los usuarios, como no poder completar su previsión o plan de suministro. (Afecta una función crítica para el negocio y no existen soluciones alternativas).");-- sp

-- Priority Medium
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.medium','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Medium');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Moyen');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Médio');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Medio');-- sp

-- Priority Desc Medium
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.mediumDesc','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User can wait for a resolution without causing substantial disruption (Affects a business-critical function, but a workaround exists).');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"L'utilisateur peut attendre une résolution sans provoquer de perturbations substantielles (affecte une fonction critique pour l'entreprise, mais une solution de contournement existe).");-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"O usuário pode aguardar uma resolução sem causar interrupções substanciais (afeta uma função crítica para os negócios, mas existe uma solução alternativa).");-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,"El usuario puede esperar una resolución sin causar una interrupción sustancial (afecta una función crítica para el negocio, pero existe una solución alternativa).");-- sp


-- Priority low
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.low','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Low');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Faible');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Baixo');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Bajo');-- sp

-- Priority Desc low
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.priority.lowDesc','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;


INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Problem is noticeable but not critical to daily operations and would be good to fix eventually. (Affects a non-business-critical function).');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,"Le problème est visible mais n’est pas critique pour les opérations quotidiennes et il serait bon de le résoudre éventuellement. (Affecte une fonction non critique pour l'entreprise).");-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,"O problema é perceptível, mas não é crítico para as operações diárias e seria bom resolvê-lo eventualmente. (Afeta uma função não crítica para os negócios).");-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El problema es notable pero no crítico para las operaciones diarias y sería bueno solucionarlo eventualmente. (Afecta a una función no crítica para el negocio).');-- sp
