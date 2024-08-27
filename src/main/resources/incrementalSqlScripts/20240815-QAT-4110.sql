CREATE TABLE `rm_dataset_planning_unit_selected_tree_list` (
  `PROGRAM_PLANNING_UNIT_ID` int unsigned NOT NULL,
  `REGION_ID` int unsigned NOT NULL,
  `TREE_ID` int unsigned NOT NULL,
  `SCENARIO_ID` int unsigned NOT NULL,
  KEY `fk_dpustl_programPlanningUnitId_idx` (`PROGRAM_PLANNING_UNIT_ID`),
  KEY `fk_dpustl_regionId_idx` (`REGION_ID`),
  KEY `fkdpustl_treeId_idx` (`TREE_ID`),
  KEY `fk_dpustl_scenarioId_idx` (`SCENARIO_ID`),
  CONSTRAINT `fk_dpustl_programPlanningUnitId` FOREIGN KEY (`PROGRAM_PLANNING_UNIT_ID`) REFERENCES `rm_dataset_planning_unit` (`PROGRAM_PLANNING_UNIT_ID`),
  CONSTRAINT `fk_dpustl_regionId` FOREIGN KEY (`REGION_ID`) REFERENCES `rm_region` (`REGION_ID`),
  CONSTRAINT `fk_dpustl_scenarioId` FOREIGN KEY (`SCENARIO_ID`) REFERENCES `rm_scenario` (`SCENARIO_ID`),
  CONSTRAINT `fkdpustl_treeId` FOREIGN KEY (`TREE_ID`) REFERENCES `rm_forecast_tree` (`TREE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO rm_dataset_planning_unit_selected_tree_list SELECT dpus.PROGRAM_PLANNING_UNIT_ID, dpus.REGION_ID, dpus.TREE_ID, dpus.SCENARIO_ID FROM rm_dataset_planning_unit_selected dpus WHERE dpus.TREE_ID IS NOT NULL AND dpus.SCENARIO_ID IS NOT NULL;

-- Change for TreeId
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP COLUMN `TREE_ID`;

ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP FOREIGN KEY `fk_rm_dataset_planning_unit_selected_scenarioId`;
ALTER TABLE `fasp`.`rm_dataset_planning_unit_selected` DROP COLUMN `SCENARIO_ID`, DROP INDEX `fk_rm_dataset_planning_unit_selected_scenarioId_idx` ;


USE `fasp`;
DROP procedure IF EXISTS `getForecastSummary`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getForecastSummary`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getForecastSummary`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Mod 2 Report no 2 - ForecastSummary
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- programId must be a single Program
    -- versionId must be the actual version 

    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    
    SELECT 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER, dpu.ACTIVE, 
        fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
        tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
        0 `SF_LABEL_ID`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_EN,'-',s.LABEL_EN)), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_FR,'-',s.LABEL_FR)), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_SP,'-',s.LABEL_SP)), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_PR,'-',s.LABEL_PR)), em.LABEL_PR) `SF_LABEL_PR`,
        ROUND(SUM(COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT)),0) `TOTAL_FORECAST`, dpus.NOTES, pv.FREIGHT_PERC,
        dpu.STOCK, dpu.EXISTING_SHIPMENTS, dpu.MONTHS_OF_STOCK, dpu.PRICE,
        pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PA_LABEL_ID`, pa.LABEL_EN `PA_LABEL_EN`, pa.LABEL_FR `PA_LABEL_FR`, pa.LABEL_SP `PA_LABEL_SP`, pa.LABEL_PR `PA_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE
    FROM rm_dataset_planning_unit dpu 
    LEFT JOIN rm_program_version pv ON dpu.PROGRAM_ID=pv.PROGRAM_ID AND dpu.VERSION_ID=pv.VERSION_ID
    LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=dpu.PROGRAM_ID
    LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND pr.REGION_ID=dpus.REGION_ID
    LEFT JOIN rm_dataset_planning_unit_selected_tree_list dpustl ON dpustl.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND dpus.REGION_ID=dpustl.REGION_ID
    LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
    LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID
    LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID
    LEFT JOIN
	(
        SELECT
            ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID
        FROM vw_forecast_tree_node ftn    
        LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
        LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID
        WHERE ftn.NODE_TYPE_ID=5
    ) tree ON dpustl.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpustl.SCENARIO_ID AND dpu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID 
    LEFT JOIN vw_forecast_tree t ON tree.TREE_ID=t.TREE_ID
    LEFT JOIN vw_scenario s ON tree.SCENARIO_ID=s.SCENARIO_ID
    LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH BETWEEN pv.FORECAST_START_DATE AND pv.FORECAST_STOP_DATE
    LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
    LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH BETWEEN pv.FORECAST_START_DATE AND pv.FORECAST_STOP_DATE
    LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
    LEFT JOIN vw_procurement_agent pa ON dpu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    WHERE dpu.PROGRAM_ID=@programId and dpu.VERSION_ID=@versionId
    GROUP BY pr.REGION_ID, dpu.PLANNING_UNIT_ID; 
END$$

DELIMITER ;
;



USE `fasp`;
DROP procedure IF EXISTS `getMonthlyForecast`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getMonthlyForecast`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getMonthlyForecast`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_MONTH DATE, VAR_STOP_MONTH DATE, VAR_REPORT_VIEW INT, VAR_UNIT_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Mod 2 Report no 1 - MonthlyForecast
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- programId must be a single Program
    -- versionId must be the actual version
    -- startMonth is the month from which you want the consumption data
    -- stopMonth is the month till which you want the consumption data
    -- reportView 1 = PU view, 2 = FU View
    -- unitIdsList -- List of the PU Id's or FU Id's that you want the report for
    
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID; 
    SET @startMonth = VAR_START_MONTH;
    SET @stopMonth = VAR_STOP_MONTH;
    SET @reportView = VAR_REPORT_VIEW;
    SET @unitIdList = VAR_UNIT_IDS;
   
    IF @reportView = 1 THEN
        SELECT
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            0 `SF_LABEL_ID`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_EN,'-',s.LABEL_EN)), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_FR,'-',s.LABEL_FR)), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_SP,'-',s.LABEL_SP)), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_PR,'-',s.LABEL_PR)), em.LABEL_PR) `SF_LABEL_PR`,
            GROUP_CONCAT(DISTINCT CONCAT(dpustl.TREE_ID,'-',dpustl.SCENARIO_ID))`TREE_SCENARIO`, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH,mn.MONTH) `MONTH`, SUM(COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT)) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN rm_dataset_planning_unit_selected_tree_list dpustl ON dpustl.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND dpus.REGION_ID=dpustl.REGION_ID
        LEFT JOIN rm_program_version pv ON pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN mn ON mn.MONTH BETWEEN @startMonth AND @stopMonth
        LEFT JOIN
            (
            SELECT
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID
            FROM vw_forecast_tree_node ftn    
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID
            WHERE ftn.NODE_TYPE_ID=5
        ) tree ON dpustl.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpustl.SCENARIO_ID AND dpu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID 
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH=mn.MONTH
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpustl.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpustl.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH=mn.MONTH
        WHERE
            dpu.PROGRAM_ID=@programId
            AND dpu.VERSION_ID=@versionId
            AND FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIdList)
        GROUP BY pu.PLANNING_UNIT_ID, r.REGION_ID, mn.MONTH
        ORDER BY pu.PLANNING_UNIT_ID, r.REGION_ID, mn.MONTH;
    ELSEIF @reportView = 2 THEN
        SELECT
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            0 `SF_LABEL_ID`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_EN,'-',s.LABEL_EN)), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_FR,'-',s.LABEL_FR)), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_SP,'-',s.LABEL_SP)), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(GROUP_CONCAT(DISTINCT CONCAT(t.LABEL_PR,'-',s.LABEL_PR)), em.LABEL_PR) `SF_LABEL_PR`,
            GROUP_CONCAT(DISTINCT CONCAT(dpustl.TREE_ID, '-',dpustl.SCENARIO_ID))`TREE_SCENARIO`, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH, mn.MONTH) `MONTH`, SUM(COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT)) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN rm_dataset_planning_unit_selected_tree_list dpustl ON dpustl.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND dpus.REGION_ID=dpustl.REGION_ID
        LEFT JOIN rm_program_version pv ON pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN mn ON mn.MONTH BETWEEN @startMonth AND @stopMonth
        LEFT JOIN
            (
            SELECT
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndfu.FORECASTING_UNIT_ID
            FROM vw_forecast_tree_node ftn    
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_fu ftndfu ON ftndfu.NODE_DATA_FU_ID=ftnd.NODE_DATA_FU_ID
            WHERE ftn.NODE_TYPE_ID=4
        ) tree ON dpustl.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpustl.SCENARIO_ID AND fu.FORECASTING_UNIT_ID=tree.FORECASTING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH=mn.MONTH
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpustl.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpustl.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH=mn.MONTH
        WHERE
            dpu.PROGRAM_ID=@programId
            AND dpu.VERSION_ID=@versionId
            AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIdList)
        GROUP BY fu.FORECASTING_UNIT_ID, r.REGION_ID, mn.MONTH;
    END IF;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.selectMultipleTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select Multiple Trees');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionner plusieurs arbres');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar varios árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione várias árvores');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.selectOneOrMore','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select one or more forecasts for');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une ou plusieurs prévisions pour');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione uno o más pronósticos para');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma ou mais previsões para');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastSummary.eitherTreeOrConsumptionValidation','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multi-select is only available for tree forecasts');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La sélection multiple n`est disponible que pour les prévisions d`arbres');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La selección múltiple solo está disponible para pronósticos de árboles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A seleção múltipla está disponível apenas para previsões de árvores');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.selectMultipleTree','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, user can select multiple tree forecasts (not consumption) as their final forecast, which will aggregate the monthly forecast quantities.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, l`utilisateur peut sélectionner plusieurs prévisions d`arbres (pas de consommation) comme prévision finale, ce qui regroupera les quantités prévues mensuellement.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se marca esta opción, el usuario puede seleccionar múltiples pronósticos de árboles (no de consumo) como su pronóstico final, lo que agregará las cantidades de pronóstico mensual.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcada, o usuário pode selecionar várias previsões de árvores (não consumo) como sua previsão final, que agregará as quantidades previstas mensais.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.totalAggregated','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Selected Forecast');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Total des prévisions sélectionnées');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico total seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão total selecionada');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.note','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Graph and data table only update after submitting');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : le graphique et le tableau de données ne sont mis à jour qu`après la soumission');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: El gráfico y la tabla de datos solo se actualizan después del envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: O gráfico e a tabela de dados só são atualizados após o envio');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastSummaryTooltip.selectForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User can select multiple tree forecasts (not consumption) as their final forecast, which will aggregate the total forecast quantities.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`utilisateur peut sélectionner plusieurs prévisions d`arbres (pas de consommation) comme prévision finale, ce qui regroupera les quantités totales prévues.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El usuario puede seleccionar múltiples pronósticos de árboles (no de consumo) como su pronóstico final, lo que agregará las cantidades totales de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O usuário pode selecionar várias previsões de árvores (não de consumo) como sua previsão final, o que agregará as quantidades totais previstas.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.compareAndSelect.topNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Users can select multiple tree forecasts as their final forecast, which will aggregate the monthly forecast quantities. Users cannot select multiple consumption forecasts or a combination of tree and consumption forecasts as their final forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les utilisateurs peuvent sélectionner plusieurs prévisions d`arbres comme prévision finale, ce qui regroupera les quantités prévues mensuellement. Les utilisateurs ne peuvent pas sélectionner plusieurs prévisions de consommation ou une combinaison de prévisions d`arbres et de consommation comme prévision finale.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los usuarios pueden seleccionar múltiples pronósticos de árboles como pronóstico final, lo que agregará las cantidades pronosticadas mensuales. Los usuarios no pueden seleccionar múltiples pronósticos de consumo o una combinación de pronósticos de árboles y consumo como pronóstico final.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os usuários podem selecionar várias previsões de árvore como sua previsão final, o que agregará as quantidades de previsão mensal. Os usuários não podem selecionar várias previsões de consumo ou uma combinação de previsões de árvore e consumo como sua previsão final.');-- pr


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Select one or more forecasts as the final forecast for each Planning Unit and Region'
where l.LABEL_CODE='static.tooltip.SelectAsForecast' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Sélectionnez une ou plusieurs prévisions comme prévision finale pour chaque unité de planification et région'
where l.LABEL_CODE='static.tooltip.SelectAsForecast' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Seleccione uno o más pronósticos como pronóstico final para cada unidad de planificación y región'
where l.LABEL_CODE='static.tooltip.SelectAsForecast' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Selecione uma ou mais previsões como previsão final para cada Unidade de Planejamento e Região'
where l.LABEL_CODE='static.tooltip.SelectAsForecast' and ll.LANGUAGE_ID=4;