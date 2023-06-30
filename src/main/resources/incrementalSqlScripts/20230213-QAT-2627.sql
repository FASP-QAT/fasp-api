DROP TABLE IF EXISTS `ap_reorder_master`;
CREATE TABLE `ap_reorder_master` (
  `NO_OF_MONTHS_FOR_REORDER` INT UNSIGNED NOT NULL,
  `TOTAL_MONTHS_OF_PLANNED_CONSUMPTION` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NO_OF_MONTHS_FOR_REORDER`));

# Static data provided by FASP
INSERT INTO ap_reorder_master VALUES ('12', '78'), ('11', '72'), ('10', '63'), ('9', '55'), ('8', '48'), ('7', '44'), ('6', '42'), ('5', '37'), ('4', '28'), ('3', '23'), ('2', '18'), ('1', '12');

#Static Labels
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.actual','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Inventory Turns');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotations réelles des stocks');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotación de inventario real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giros de estoque reais');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.planned','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned Inventory Turns');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks planifiée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones planificadas de inventario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giro de estoque planejado');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.display','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display report by');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher le rapport par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar informe por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibir relatório por');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.noofplanningunits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No. of PUs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de unidades de planejamento');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.noofmonths','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No. of months in calculation');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de unidades de planejamento');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.months12','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'between 6-12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'entre 6 et 12 mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'entre 6-12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'entre 6-12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.months6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'less than 6 months (Insufficient data to generate reliable turns)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'moins de 6 mois (Données insuffisantes pour générer des rotations fiables)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Menos de 6 meses (Datos insuficientes para generar turnos confiables)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'menos de 6 meses (dados insuficientes para gerar turnos confiáveis)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.months13','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'12 or more months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'12 mois ou plus');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'12 o más meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'12 ou mais meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned Inventory Turns (IT)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks planifiée (IT)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Turnos de inventario planificados (IT)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giro de Estoque Planejado (TI)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual IT = Total Consumption for the last 12 months / Average Stock on hand over last 12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = Consommation totale des 12 derniers mois / Stock moyen disponible des 12 derniers mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = Consumo total de los últimos 12 meses / Stock promedio disponible durante los últimos 12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = Consumo total dos últimos 12 meses / Estoque médio disponível nos últimos 12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MODE(Planned inventory turns of all Planning units under a specific program)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MODE (tours d\'inventaire planifiés de toutes les unités de planification dans le cadre d\'un programme spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MODE (giros de inventario planificados de todas las unidades de planificación bajo un programa específico)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MODE(giros de estoque planejados de todas as unidades de planejamento em um programa específico)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MODE(Planned inventory turns of all Planning units under a specific country / PU Category)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MODE (rotation des stocks planifiée de toutes les unités de planification sous un pays / une catégorie PU spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MODE (giros de inventario planificados de todas las unidades de planificación en un país específico / categoría de PU)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MODE (giros de estoque planejados de todas as unidades de planejamento em um país específico / categoria de PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Inventory Turns (IT)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation réelle des stocks (IT)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones reales de inventario (IT)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giros reais de estoque (TI)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'At PU level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Au niveau de l\'UP');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A nivel de PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'No nível PU');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Consumption for the last 12 months / Average Stock on hand over last 12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation totale des 12 derniers mois / Stock moyen disponible des 12 derniers mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo total de los últimos 12 meses / Stock promedio disponible de los últimos 12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo total nos últimos 12 meses / Estoque médio disponível nos últimos 12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'At Program level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Au niveau du programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A nivel de Programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'No nível do programa');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'First option is to look for a mode.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La première option consiste à rechercher un mode.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La primera opción es buscar un modo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A primeira opção é procurar um modo.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the count of MODE Value < 3 (variable setting), QAT uses AVG, if the count of the MODE value is  > 3 (variable setting), then');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le décompte de la valeur MODE < 3 (réglage variable), QAT utilise AVG, si le décompte de la valeur MODE est > 3 (réglage variable), alors');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el conteo del valor MODE < 3 (configuración variable), QAT usa AVG, si el conteo del valor MODE es > 3 (configuración variable), entonces');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se a contagem do valor MODE < 3 (configuração variável), QAT usa AVG, se a contagem do valor MODE for > 3 (configuração variável), então');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L6','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The mode will be determined if the value occurs in at least 20% (variable setting), of the program\'s total number of planning units. (Example: program contains 100 PUs, 20 of those have turns = 1.2 and the rest all have different turns; so the turns for the program will be 1.2)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le mode sera déterminé si la valeur apparaît dans au moins 20 % (réglage variable) du nombre total d\'unités de planification du programme. (Exemple : le programme contient 100 UP, dont 20 ont des tours = 1,2 et les autres ont tous des tours différents ; les tours du programme seront donc de 1,2)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La moda se determinará si el valor se presenta en al menos el 20% (configuración variable), del número total de unidades de planificación del programa. (Ejemplo: el programa contiene 100 PU, 20 de ellos tienen vueltas = 1,2 y el resto tienen vueltas diferentes, por lo que las vueltas del programa serán 1,2)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A moda será determinada se o valor ocorrer em pelo menos 20% (configuração variável), do número total de unidades de planejamento do programa. (Exemplo: o programa contém 100 PUs, 20 deles têm voltas = 1,2 e o restante tem voltas diferentes; portanto, as voltas para o programa serão 1,2)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L7','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If above condition (20%) is found multiple times in the dataset, QAT to use the LOWEST inv. turns mode of the set (Example:, Program contains 100 PUs; 60 PUs have different turns; 20 PUs have inv turns = 1.2, another 20 PUs have inv. turns = 2.5; QAT will display 1.2  as the inv. turns for that program)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si la condition ci-dessus (20 %) est trouvée plusieurs fois dans l\'ensemble de données, QAT pour utiliser l\'inv. la PLUS BASSE. mode tours de l\'ensemble (Exemple :, le programme contient 100 PU ; 60 PU ont des tours différents ; 20 PU ont des tours inv. = 1,2, 20 autres PU ont des tours inv. = 2,5 ; QAT affichera 1,2 comme les tours inv. pour ce programme )');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si la condición anterior (20 %) se encuentra varias veces en el conjunto de datos, QAT debe usar la inversión MÁS BAJA. modo de giros del conjunto (Ejemplo: El programa contiene 100 PU; 60 PU tienen giros diferentes; 20 PU tienen giros inv. = 1,2, otras 20 PU tienen giros inv. = 2,5; QAT mostrará 1,2 como giros inv. para ese programa )');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se a condição acima (20%) for encontrada várias vezes no conjunto de dados, QAT para usar o LOWEST inv. modo de voltas do conjunto (Exemplo:, Programa contém 100 PUs; 60 PUs têm voltas diferentes; 20 PUs têm voltas inv. = 1,2, outros 20 PUs têm voltas inv. = 2,5; QAT exibirá 1,2 como voltas inv. para esse programa )');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L8','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If no definitive mode can be found based on above rules, then Program level inv turns will be the average of all PU inventory turns under that program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si aucun mode définitif ne peut être trouvé sur la base des règles ci-dessus, les rotations d\'inventaire au niveau du programme seront la moyenne de toutes les rotations d\'inventaire PU dans le cadre de ce programme.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no se puede encontrar un modo definitivo basado en las reglas anteriores, entonces los giros de inversión a nivel de programa serán el promedio de todos los giros de inventario de PU bajo ese programa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se nenhum modo definitivo puder ser encontrado com base nas regras acima, os giros de estoque de nível do programa serão a média de todos os giros de estoque de PU sob esse programa');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L9','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'At Country and/or PU category level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Au niveau du pays et/ou de la catégorie PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A nivel de categoría de país y/o PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'No nível de país e/ou categoria de PU');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L10','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'First option is to look for a mode.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La première option consiste à rechercher un mode.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La primera opción es buscar un modo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A primeira opção é procurar um modo.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L11','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the count of MODE Value < 3 (variable setting), QAT uses AVG, if the count of the MODE value is  > 3 (variable setting), then');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le décompte de la valeur MODE < 3 (réglage variable), QAT utilise AVG, si le décompte de la valeur MODE est > 3 (réglage variable), alors');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el conteo del valor MODE < 3 (configuración variable), QAT usa AVG, si el conteo del valor MODE es > 3 (configuración variable), entonces');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se a contagem do valor MODE < 3 (configuração variável), QAT usa AVG, se a contagem do valor MODE for > 3 (configuração variável), então');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L12','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The mode will be determined if the value occurs in at least 20% (variable setting),  of the that country\'s (or PU category) total number planning units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le mode sera déterminé si la valeur se produit dans au moins 20 % (réglage variable) du nombre total d\'unités de planification de ce pays (ou de cette catégorie d\'UP)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La moda se determinará si el valor se produce en al menos el 20 % (configuración variable) del número total de unidades de planificación de ese país (o categoría de PU).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O modo será determinado se o valor ocorrer em pelo menos 20% (configuração variável) do número total de unidades de planejamento desse país (ou categoria de PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L13','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Rule: If above condition is found multiple times in the dataset, QAT to use the LOWEST inv. turns mode of the bunch (e.g., Country contains 1000 PUs; 600 PUs have different turns; 200 PUs have inv turns = 1.6, another 200 PUs have inv. turns = 2.2; QAT will display 1.6 as the inv. turns for that Country/PU category)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Règle : si la condition ci-dessus est trouvée plusieurs fois dans l\'ensemble de données, QAT utilise l\'inv. la PLUS BASSE. mode tours du groupe (par exemple, le pays contient 1000 UP ; 600 UP ont des tours différents ; 200 UP ont des tours inv. = 1,6, 200 autres UP ont des tours inv. = 2,2 ; QAT affichera 1,6 comme tours inv. pour ce pays/ catégorie PU)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Regla: si la condición anterior se encuentra varias veces en el conjunto de datos, QAT debe usar la inversión MÁS BAJA. modo de giros del grupo (p. ej., el país contiene 1000 UP; 600 UP tienen giros diferentes; 200 UP tienen giros inv. = 1,6, otras 200 UP tienen giros inv. = 2,2; QAT mostrará 1,6 como giros inv. para ese país/ categoría PU)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Regra: Se a condição acima for encontrada várias vezes no conjunto de dados, QAT para usar o LOWEST inv. modo de voltas do grupo (por exemplo, País contém 1.000 PUs; 600 PUs têm voltas diferentes; 200 PUs têm voltas inv. = 1,6, outros 200 PUs têm voltas inv. = 2,2; QAT exibirá 1,6 como as voltas inv. para esse país/ categoria PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L14','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Rule: If no definitive mode can be found based on above rules, then Country/PU category level inv turns will be the average of all PU inventory turns under that country/PU category');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Règle : si aucun mode définitif ne peut être trouvé sur la base des règles ci-dessus, les rotations d\'inventaire au niveau de la catégorie Pays/PU seront la moyenne de toutes les rotations d\'inventaire PU sous cette catégorie de pays/PU.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Regla: si no se puede encontrar un modo definitivo basado en las reglas anteriores, entonces los giros de inversión a nivel de categoría de país/PU serán el promedio de todos los giros de inventario de PU bajo esa categoría de país/PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Regra: Se nenhum modo definitivo puder ser encontrado com base nas regras acima, então os giros de inventário de nível de categoria de país/PU serão a média de todos os giros de estoque de PU sob aquele país/categoria de PU');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error metrics (MAPE, MSE)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Métriques d\'erreur (MAPE, MSE)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Métricas de error (MAPE, MSE)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Métricas de erro (MAPE, MSE)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MAPE = (Actual inventory turn – Planned inventory turn) / Actual inventory turn');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MAPE = (Rotation des stocks réelle - Rotation des stocks planifiée) / Rotation des stocks réelle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MAPE = (Rotación de inventario real - Rotación de inventario planificada) / Rotación de inventario real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MAPE = (Giro de estoque real – Giro de estoque planejado) / Giro de estoque real');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MSE = (Actual inventory turn – Planned inventory turn) * (Actual inventory turn – Planned inventory turn)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MSE = (Rotation réelle des stocks – Rotation planifiée des stocks) * (Rotation réelle des stocks – Rotation planifiée des stocks)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MSE = (Rotación de inventario real – Rotación de inventario planificada) * (Rotación de inventario real – Rotación de inventario planificada)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MSE = (Giro de estoque real – Giro de estoque planejado) * (Giro de estoque real – Giro de estoque planejado)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total consumption and average stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation totale et stock moyen');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo total y stock medio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo total e estoque médio');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total consumption = Sum of all the Consumption over last 12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation totale = Somme de toutes les consommations des 12 derniers mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo total = Suma de todo el consumo durante los últimos 12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo total = Soma de todo o consumo nos últimos 12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Average Stock = (Total months of planned consumption + (Min MOS *12)) / 12');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock moyen = (Total des mois de consommation planifiée + (Min MOS *12)) / 12');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock promedio = (Meses totales de consumo planificado + (Min MOS *12)) / 12');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque Médio = (Total de meses de consumo planejado + (Min MOS *12)) / 12');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reorder interval and Min MOS settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de commande et paramètres Min MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajustes de intervalo de reordenamiento y MOS mínimo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de reordenar e configurações de Min MOS');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns5L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update the “plan by” setting in ');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettez à jour le paramètre "planifier par" dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualice la configuración "planificar por" en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualize a configuração "planejar até" em');-- pr

USE `fasp`;
DROP procedure IF EXISTS `inventoryTurns`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`inventoryTurns`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `inventoryTurns`(VAR_START_DATE DATE, VAR_VIEW_BY INT, VAR_PROGRAM_IDS TEXT, VAR_PRODUCT_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS BOOLEAN, VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 9
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- StartDate is the date that you want to run the report for
    -- ViewBy = 1 View by RealmCountry, ViewBy = 2 View by ProductCategory
    -- ProgramIds is the list of ProgramIds that should be included in the final output, cannot be empty you must pass the ProgramIds that you want to view it by
    -- ProductCategoryIds is the list of ProductCategoryIds that should be included in the final output, cannot be empty if you want to select all pass '0'
    -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
    -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
    -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period

    DECLARE curProgramId INTEGER DEFAULT 0;
    DECLARE curVersionId INTEGER DEFAULT 0;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE program_cursor CURSOR FOR 
        SELECT p.PROGRAM_ID, MAX(pv.VERSION_ID) VERSION_ID
           FROM vw_program p 
           LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND (VAR_APPROVED_SUPPLY_PLAN_ONLY=0 OR (pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2))
           WHERE FIND_IN_SET(p.PROGRAM_ID, VAR_PROGRAM_IDS) AND p.ACTIVE 
           GROUP BY p.PROGRAM_ID
           HAVING MAX(pv.VERSION_ID) IS NOT NULL;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    SET @startDate = VAR_START_DATE;
    SET @viewBy = VAR_VIEW_BY;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
	SELECT GROUP_CONCAT(pc.PRODUCT_CATEGORY_ID) INTO @finalProductCategoryIds FROM rm_product_category pc LEFT JOIN (SELECT CONCAT(pc1.SORT_ORDER,'%') `SO` FROM rm_product_category pc1 WHERE FIND_IN_SET(pc1.PRODUCT_CATEGORY_ID, VAR_PRODUCT_CATEGORY_IDS)) pc2 ON pc.SORT_ORDER LIKE pc2.SO WHERE pc2.SO IS NOT NULL;
    
	DROP TABLE IF EXISTS tmp_inventory_turns;
	CREATE TEMPORARY TABLE `fasp`.`tmp_inventory_turns` (
		`PROGRAM_ID` INT NOT NULL,
        `PLANNING_UNIT_ID` INT NOT NULL,
        `TOTAL_CONSUMPTION` DOUBLE(16,2) NULL,
        `AVG_STOCK` DOUBLE(16,2) NULL,
        `NO_OF_MONTHS` INT NULL,
        PRIMARY KEY (`PROGRAM_ID`, `PLANNING_UNIT_ID`)
    );
    OPEN program_cursor;
    nextProgramVersionLoop: LOOP FETCH program_cursor INTO curProgramId, curVersionId;
	IF finished = 1 THEN 
		LEAVE nextProgramVersionLoop;
	END IF;
    INSERT INTO log VALUES (null, now(), CONCAT("programId:",curProgramId, ", versionId:",curVersionId));
	INSERT INTO tmp_inventory_turns 
		SELECT 
			ppu.PROGRAM_ID,
            ppu.PLANNING_UNIT_ID, 
            SUM(s2.CONSUMPTION_QTY) `TOTAL_CONSUMPTION`, 
            AVG(s2.STOCK) `AVG_STOCK`,
            COUNT(s2.CONSUMPTION_QTY) `NO_OF_MONTHS`
        FROM rm_program_planning_unit ppu 
        LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN 
			(
            SELECT 
				spa.TRANS_DATE, spa.PLANNING_UNIT_ID, 
                SUM(IF(spa.ACTUAL IS NULL, NULL, IF(spa.ACTUAL=1, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY))) `CONSUMPTION_QTY`,
                SUM(IF(@includePlannedShipments, spa.CLOSING_BALANCE, spa.CLOSING_BALANCE_WPS)) `STOCK`
			FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=curProgramId AND spa.VERSION_ID=curVersionId AND spa.TRANS_DATE BETWEEN SUBDATE(@startDate, INTERVAL 11 MONTH) AND @startDate
            GROUP BY spa.TRANS_DATE, spa.PLANNING_UNIT_ID
            HAVING `CONSUMPTION_QTY` IS NOT NULL
		) s2 ON ppu.PLANNING_UNIT_ID=s2.PLANNING_UNIT_ID
        WHERE ppu.PROGRAM_ID=curProgramId AND ppu.ACTIVE AND pu.ACTIVE AND FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds)
        GROUP BY ppu.PLANNING_UNIT_ID;
	INSERT INTO log VALUES (null, now(), "Completed insert");
    END LOOP nextProgramVersionLoop;

    INSERT INTO log VALUES (null, now(), "Going to run main query");
    IF @viewBy = 1 THEN
		INSERT INTO log VALUES (null, now(), "View 1");
        SELECT 
            rc.REALM_COUNTRY_ID, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, 
            it.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, 
            it.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
            pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, 
            it.TOTAL_CONSUMPTION, it.AVG_STOCK, it.NO_OF_MONTHS, it.TOTAL_CONSUMPTION/it.AVG_STOCK `INVENTORY_TURNS`,
            ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION, 144/(rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION+ppu.MIN_MONTHS_OF_STOCK*12) `PLANNED_INVENTORY_TURNS`
        FROM tmp_inventory_turns it 
        LEFT JOIN vw_program p ON it.PROGRAM_ID=p.PROGRAM_ID
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
        LEFT JOIN vw_planning_unit pu ON it.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
        LEFT JOIN ap_reorder_master rm ON ppu.REORDER_FREQUENCY_IN_MONTHS=rm.NO_OF_MONTHS_FOR_REORDER
        ORDER BY c.LABEL_EN, p.PROGRAM_CODE, pu.LABEL_EN
        ;
    ELSEIF @viewBy = 2 THEN
		INSERT INTO log VALUES (null, now(), "View 2");
        SELECT 
            rc.REALM_COUNTRY_ID, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, 
            it.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, 
            it.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, 
            pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, 
            it.TOTAL_CONSUMPTION, it.AVG_STOCK, it.NO_OF_MONTHS, it.TOTAL_CONSUMPTION/it.AVG_STOCK `INVENTORY_TURNS`,
            ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION, 144/(rm.TOTAL_MONTHS_OF_PLANNED_CONSUMPTION+ppu.MIN_MONTHS_OF_STOCK*12) `PLANNED_INVENTORY_TURNS`
        FROM tmp_inventory_turns it 
        LEFT JOIN vw_program p ON it.PROGRAM_ID=p.PROGRAM_ID
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
        LEFT JOIN vw_planning_unit pu ON it.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
        LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
        LEFT JOIN ap_reorder_master rm ON ppu.REORDER_FREQUENCY_IN_MONTHS=rm.NO_OF_MONTHS_FOR_REORDER
        ORDER BY pc.LABEL_EN, p.PROGRAM_CODE, pu.LABEL_EN
        ;
    END IF;
END$$

DELIMITER ;
;

