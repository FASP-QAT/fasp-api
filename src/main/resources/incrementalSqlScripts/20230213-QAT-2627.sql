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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months less than 12 and greater than 6');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois inférieurs à 12 et supérieurs à 6');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses menores de 12 y mayores de 6');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses menores que 12 e maiores que 6');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.inventoryTurns.months6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Months less than 6 (Insufficient data to generate reliable turns)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mois inférieurs à 6 (Données insuffisantes pour générer des rotations fiables)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Meses menores a 6 (Datos insuficientes para generar turnos confiables)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meses menores que 6 (dados insuficientes para gerar turnos confiáveis)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory turns at lowest level (PU)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks au niveau le plus bas (PU)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones de inventario al nivel más bajo (PU)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giro de estoque no nível mais baixo (PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual IT = Total Consumption for the last 12 months / Average Stock on hand over last 12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = Consommation totale des 12 derniers mois / Stock moyen disponible des 12 derniers mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = Consumo total de los últimos 12 meses / Stock promedio disponible durante los últimos 12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = Consumo total dos últimos 12 meses / Estoque médio disponível nos últimos 12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned IT = Total Consumption over last 12 months / Average Stock during that period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT planifiée = Consommation totale au cours des 12 derniers mois / Stock moyen au cours de cette période');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI planificado = Consumo total en los últimos 12 meses / Stock promedio durante ese período');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI Planejada = Consumo Total nos últimos 12 meses / Estoque Médio nesse período');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory turns at program level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks au niveau du programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones de inventario a nivel de programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giro de estoque no nível do programa');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual IT = MODE(Actual inventory turns of all Planning units under a specific program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = MODE(Rotations réelles des stocks de toutes les unités de planification dans le cadre d\'un programme spécifique');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = MODO (giros de inventario reales de todas las unidades de planificación bajo un programa específico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = MODO(giros de estoque reais de todas as unidades de planejamento em um programa específico');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns2L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned IT = MODE(Planned inventory turns of all Planning units under a specific program)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT planifiée = MODE (tours d\'inventaire planifiés de toutes les unités de planification dans le cadre d\'un programme spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI planificado = MODO (giros de inventario planificados de todas las unidades de planificación bajo un programa específico)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI planejada = MODO(giros de estoque planejados de todas as unidades de planejamento em um programa específico)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory turns at country level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks au niveau du pays');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones de inventario a nivel de país');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giros de estoque em nível de país');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual IT = MODE(Actual inventory turns of all Planning units under a specific country)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = MODE (rotation réelle des stocks de toutes les unités de planification dans un pays spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = MODO (giros de inventario reales de todas las unidades de planificación en un país específico)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = MODO (giros de estoque reais de todas as unidades de planejamento em um país específico)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns3L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned IT = MODE(Planned inventory turns of all Planning units under a specific country)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = MODE (rotation réelle des stocks de toutes les unités de planification dans un pays spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = MODO (giros de inventario reales de todas las unidades de planificación en un país específico)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = MODO (giros de estoque reais de todas as unidades de planejamento em um país específico)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Inventory turns at PU category level');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rotation des stocks au niveau de la catégorie PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rotaciones de inventario a nivel de categoría de PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Giros de estoque no nível da categoria de PU');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual IT = MODE(Actual inventory turns of all Planning units under a specific PU category)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT réel = MODE (rotation des stocks réelle de toutes les unités de planification sous une catégorie PU spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI real = MODO (giros de inventario reales de todas las unidades de planificación en una categoría de PU específica)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI real = MODO (giros de estoque reais de todas as unidades de planejamento em uma categoria específica de PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns4L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned IT = MODE(Planned inventory turns of all Planning units under a specific PU category)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'IT planifiée = MODE (rotation des stocks planifiée de toutes les unités de planification sous une catégorie PU spécifique)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TI planificado = MODO (giros de inventario planificados de todas las unidades de planificación en una categoría de PU específica)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'TI planejada = MODO(giros de estoque planejados de todas as unidades de planejamento em uma categoria específica de PU)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns5','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error metrics (MAPE, MSE)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Métriques d\'erreur (MAPE, MSE)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Métricas de error (MAPE, MSE)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Métricas de erro (MAPE, MSE)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns5L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MAPE = (Actual inventory turn – Planned inventory turn) / Actual inventory turn');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MAPE = (Rotation réelle des stocks - Rotation planifiée des stocks) / Rotation réelle des stocks');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MAPE = (Rotación de inventario real - Rotación de inventario planificada) / Rotación de inventario real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MAPE = (Giro de estoque real – Giro de estoque planejado) / Giro de estoque real');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns5L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MSE = (Actual inventory turn – Planned inventory turn) * (Actual inventory turn – Planned inventory turn)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MSE = (Rotation réelle des stocks – Rotation planifiée des stocks) * (Rotation réelle des stocks – Rotation planifiée des stocks)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MSE = (Rotación de inventario real – Rotación de inventario planificada) * (Rotación de inventario real – Rotación de inventario planificada)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MSE = (Giro de estoque real – Giro de estoque planejado) * (Giro de estoque real – Giro de estoque planejado)');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns6','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total consumption and average stock');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation totale et stock moyen');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo total y stock medio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo total e estoque médio');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns6L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total consumption = Sum of all the Consumption over last 12 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation totale = Somme de toutes les consommations des 12 derniers mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo total = Suma de todo el consumo durante los últimos 12 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo total = Soma de todo o consumo nos últimos 12 meses');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns6L2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Average Stock = (Total months of planned consumption + (Min MOS *12)) / 12');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock moyen = (Total des mois de consommation planifiée + (Min MOS *12)) / 12');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock promedio = (Meses totales de consumo planificado + (Min MOS *12)) / 12');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque Médio = (Total de meses de consumo planejado + (Min MOS *12)) / 12');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns8','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reorder interval and Min MOS settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intervalle de commande et paramètres Min MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ajustes de intervalo de reordenamiento y MOS mínimo');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo de reordenar e configurações de Min MOS');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns8L1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Update the “plan by” setting in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mettez à jour le paramètre "planifier par" dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Actualice la configuración "planificar por" en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Atualize a configuração "planejar até" em');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurnsNote1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For Average calculation consider only non zero values.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour le calcul de la moyenne, ne considérez que les valeurs non nulles.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para el cálculo promedio, considere solo valores distintos de cero.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para o cálculo da média, considere apenas valores diferentes de zero.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurnsNote2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If maximum occurrence in calculation of MODE is less than 3 then system uses average for the calculation.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si l occurrence maximale dans le calcul de MODE est inférieure à 3, le système utilise la moyenne pour le calcul.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si la incidencia máxima en el cálculo de MODE es inferior a 3, el sistema utiliza el promedio para el cálculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se a ocorrência máxima no cálculo do MODO for menor que 3, o sistema usará a média para o cálculo.');-- pr

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

