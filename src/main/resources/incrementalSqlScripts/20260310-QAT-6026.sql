INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.scorecard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Scorecard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tableau de bord du plan d''approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuadro de mando del plan de suministro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quadro de pontuação do plano de abastecimento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.totalScore','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score total');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación total');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação total');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.overallScore','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Overall Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score global');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación general');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação geral');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.overallScoreTitle','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Overall Supply Plan Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score global du plan d''approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación general del plan de suministro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação geral do plano de abastecimento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qualityScore','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Quality Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score de qualité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación de calidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação de qualidade');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.score','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.stockStatusScore','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock Status Score');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score de l''état des stocks');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación del estado de las existencias');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação do status do estoque');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.countryXprogram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Country x Program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pays x Programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'País x Programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'País x Programa');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.viewBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ver por');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.scoreBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plan Score by');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Score du plan d''approvisionnement par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Puntuación del plan de suministro por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pontuação do plano de abastecimento por');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.showDetail','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show Detail');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les détails');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar detalles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar detalhes');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.latestVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Latest Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dernière version');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Última versión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Última versão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.activePUs','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Active PUs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'PUs actives');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'PUs activas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'PUs ativas');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.actualInventory','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual Inventory');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Inventaire réel');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Inventario real');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque real');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qualityScoreTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This score represents the average percent compliance across all data quality metrics - forecasted consumption, actual consumption, actual inventory, and shipments. Supply plans that are more complete and up‑to‑date earn higher scores.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce score représente le pourcentage moyen de conformité pour toutes les mesures de qualité des données - consommation prévue, consommation réelle, inventaire réel et expéditions. Les plans d''approvisionnement qui sont plus complets et à jour obtiennent des scores plus élevés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta puntuación representa el porcentaje promedio de cumplimiento en todas las métricas de calidad de datos: consumo previsto, consumo real, inventario real y envíos. Los planes de suministro que están más completos y actualizados obtienen puntuaciones más altas.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta pontuação representa a porcentagem média de conformidade em todas as métricas de qualidade de dados - consumo previsto, consumo real, estoque real e remessas. Planos de abastecimento mais completos e atualizados recebem pontuações mais altas.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.stockStatusScoreTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This score reflects the average number of months in which all planning units are Stocked to Plan. Supply plans that adhere to MIN/MAX parameters and remain Stocked to Plan earn higher scores.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce score reflète le nombre moyen de mois pendant lesquels toutes les unités de planification sont approvisionnées selon le plan. Les plans d''approvisionnement qui respectent les paramètres MIN/MAX et restent approvisionnés selon le plan obtiennent des scores plus élevés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta puntuación refleja el número promedio de meses en los que todas las unidades de planificación están abastecidas según el plan. Los planes de suministro que se adhieren a los parámetros MIN/MAX y permanecen abastecidos según el plan obtienen puntuaciones más altas.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta pontuação reflete o número médio de meses em que todas as unidades de planejamento estão estocadas de acordo com o plano. Planos de abastecimento que aderem aos parâmetros MIN/MAX e permanecem estocados de acordo com o plano recebem pontuações mais altas.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.totalScoreTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This score is calculated as the average of the Quality Score and Stock Status Score. Higher scores indicate stronger overall supply plan performance across data quality and stock status.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce score est calculé comme la moyenne du score de qualité et du score de l''état des stocks. Des scores plus élevés indiquent une meilleure performance globale du plan d''approvisionnement en termes de qualité des données et d''état des stocks.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta puntuación se calcula como el promedio de la puntuación de calidad y la puntuación del estado de las existencias. Las puntuaciones más altas indican un mejor rendimiento general del plan de suministro en la calidad de los datos y el estado de las existencias.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta pontuação é calculada como a média da Pontuação de Qualidade e da Pontuação do Status do Estoque. Pontuações mais altas indicam um desempenho geral mais forte do plano de abastecimento em termos de qualidade de dados e status do estoque.');-- pr


ALTER TABLE `fasp`.`rm_realm` ADD COLUMN `SUPPLY_PLAN_SCORE_THRESHOLD_PERC` DECIMAL(4,3) NOT NULL DEFAULT '0.70' AFTER `NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD`;

USE `fasp`;
CREATE  OR REPLACE 
    ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_realm` AS
    SELECT 
        `r`.`REALM_ID` AS `REALM_ID`,
        `r`.`REALM_CODE` AS `REALM_CODE`,
        `r`.`LABEL_ID` AS `LABEL_ID`,
        `r`.`DEFAULT_REALM` AS `DEFAULT_REALM`,
        `r`.`MIN_MOS_MIN_GAURDRAIL` AS `MIN_MOS_MIN_GAURDRAIL`,
        `r`.`MIN_MOS_MAX_GAURDRAIL` AS `MIN_MOS_MAX_GAURDRAIL`,
        `r`.`MAX_MOS_MAX_GAURDRAIL` AS `MAX_MOS_MAX_GAURDRAIL`,
        `r`.`MIN_QPL_TOLERANCE` AS `MIN_QPL_TOLERANCE`,
        `r`.`MIN_QPL_TOLERANCE_CUT_OFF` AS `MIN_QPL_TOLERANCE_CUT_OFF`,
        `r`.`MAX_QPL_TOLERANCE` AS `MAX_QPL_TOLERANCE`,
        `r`.`ACTUAL_CONSUMPTION_MONTHS_IN_PAST` AS `ACTUAL_CONSUMPTION_MONTHS_IN_PAST`,
        `r`.`FORECAST_CONSUMPTION_MONTH_IN_PAST` AS `FORECAST_CONSUMPTION_MONTH_IN_PAST`,
        `r`.`INVENTORY_MONTHS_IN_PAST` AS `INVENTORY_MONTHS_IN_PAST`,
        `r`.`MIN_COUNT_FOR_MODE` AS `MIN_COUNT_FOR_MODE`,
        `r`.`MIN_PERC_FOR_MODE` AS `MIN_PERC_FOR_MODE`,
        `r`.`NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_BOTTOM_DASHBOARD`,
        `r`.`NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_BOTTOM_DASHBOARD`,
        `r`.`NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD` AS `NO_OF_MONTHS_IN_PAST_FOR_TOP_DASHBOARD`,
        `r`.`NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD` AS `NO_OF_MONTHS_IN_FUTURE_FOR_TOP_DASHBOARD`,
        `r`.`SUPPLY_PLAN_SCORE_THRESHOLD_PERC` AS `SUPPLY_PLAN_SCORE_THRESHOLD_PERC`,
        `r`.`ACTIVE` AS `ACTIVE`,
        `r`.`CREATED_BY` AS `CREATED_BY`,
        `r`.`CREATED_DATE` AS `CREATED_DATE`,
        `r`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `r`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `rl`.`LABEL_EN` AS `LABEL_EN`,
        `rl`.`LABEL_FR` AS `LABEL_FR`,
        `rl`.`LABEL_SP` AS `LABEL_SP`,
        `rl`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`rm_realm` `r`
        LEFT JOIN `ap_label` `rl` ON ((`r`.`LABEL_ID` = `rl`.`LABEL_ID`)));


USE `fasp`;
DROP procedure IF EXISTS `getDashboardStockStatus`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getDashboardStockStatus`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getDashboardStockStatus`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT)
BEGIN
    
    SELECT COUNT(mn.MONTH) INTO @monthCount FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE AND VAR_STOP_DATE;
    SELECT COUNT(*) INTO @puCount FROM rm_program_planning_unit ppu LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID where ppu.PROGRAM_ID=VAR_PROGRAM_ID AND ppu.ACTIVE AND pu.ACTIVE;
    
    SELECT 
	SUM(IFNULL(p2.`COUNT_OF_STOCK_OUT`,0)) `COUNT_OF_STOCK_OUT`, 
        SUM(IFNULL(p2.`COUNT_OF_UNDER_STOCK`,0)) `COUNT_OF_UNDER_STOCK`, 
        SUM(IFNULL(p2.`COUNT_OF_ADEQUATE_STOCK`,0)) `COUNT_OF_ADEQUATE_STOCK`, 
        SUM(IFNULL(p2.`COUNT_OF_OVER_STOCK`,0)) `COUNT_OF_OVER_STOCK`, 
        @puCount*@monthCount - (SUM(IFNULL(p2.`COUNT_OF_STOCK_OUT`,0))+SUM(IFNULL(p2.`COUNT_OF_UNDER_STOCK`,0))+SUM(IFNULL(p2.`COUNT_OF_ADEQUATE_STOCK`,0))+SUM(IFNULL(p2.`COUNT_OF_OVER_STOCK`,0))) `COUNT_OF_NA`,
        @puCount*@monthCount `COUNT_OF_TOTAL`
    FROM vw_program p 
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE 
    LEFT JOIN (
        SELECT 
            p1.PLANNING_UNIT_ID, 
            SUM(IF(p1.STOCK_CONDITION=0, 1, 0)) `COUNT_OF_STOCK_OUT`, 
            SUM(IF(STOCK_CONDITION=1, 1, 0)) `COUNT_OF_UNDER_STOCK`, 
            SUM(IF(STOCK_CONDITION=2, 1, 0)) `COUNT_OF_ADEQUATE_STOCK`, 
            SUM(IF(STOCK_CONDITION=3, 1, 0)) `COUNT_OF_OVER_STOCK`, 
            SUM(IF(STOCK_CONDITION=4, 1, 0)) `COUNT_OF_NA`,
            SUM(IF(STOCK_CONDITION=5, 1, 0)) `COUNT_OF_UNK`
        FROM ( 
        SELECT 
            amc.PLANNING_UNIT_ID, amc.`TRANS_DATE`,
            CASE
                WHEN amc.MOS is null THEN 4 
                WHEN amc.MOS=0 THEN 0 
                WHEN amc.MOS < IF(ppu.PLAN_BASED_ON=1,ppu.MIN_MONTHS_OF_STOCK,ppu.MIN_QTY) THEN 1 
                WHEN amc.MOS <= IF(ppu.PLAN_BASED_ON=1,(ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS),ROUND(amc.MAX_STOCK_QTY)) THEN 2 
                WHEN amc.MOS > IF(ppu.PLAN_BASED_ON=1,(ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS),ROUND(amc.MAX_STOCK_QTY)) THEN 3 
                ELSE 5 
            END `STOCK_CONDITION` 
        FROM vw_program p 
        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID
        LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
        LEFT JOIN rm_supply_plan_amc amc ON p.PROGRAM_ID=amc.PROGRAM_ID AND p.CURRENT_VERSION_ID=amc.VERSION_ID AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID
        WHERE p.PROGRAM_ID=VAR_PROGRAM_ID AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE and ppu.ACTIVE AND pu.ACTIVE
        ) p1 GROUP BY p1.PLANNING_UNIT_ID) p2 ON pu.PLANNING_UNIT_ID=p2.PLANNING_UNIT_ID
    WHERE p.PROGRAM_ID = VAR_PROGRAM_ID
    GROUP BY p.PROGRAM_ID; 
END$$

DELIMITER ;
;

