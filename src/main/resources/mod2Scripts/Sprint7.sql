/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Mar-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.placeholder.placeholder','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'[Placeholder]');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'[Espace réservé]');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'[Marcador de posición]');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'[Espaço reservado]');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.placeholder.monthlyForecastReport','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This report aggregates regional forecasts. For disaggregated regional forecasts, export CSV.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce rapport regroupe les prévisions régionales. Pour les prévisions régionales désagrégées, exportez CSV.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este informe agrega pronósticos regionales. Para pronósticos regionales desagregados, exporte CSV.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este relatório agrega previsões regionais. Para previsões regionais desagregadas, exporte CSV.');-- pr

USE `fasp`;
DROP procedure IF EXISTS `getMonthlyForecast`;

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
            dpus.TREE_ID, dpus.SCENARIO_ID, mom.MONTH, mom.CALCULATED_MMD_VALUE
        FROM rm_dataset_planning_unit dpu 
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN 
            (
            SELECT 
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID
            FROM vw_forecast_tree_node ftn     
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID 
            WHERE ftn.NODE_TYPE_ID=5
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND pu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH BETWEEN @startMonth AND @stopMonth 
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIdList);
    ELSEIF @reportView = 2 THEN
        SELECT 
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, mom.MONTH, mom.CALCULATED_MMD_VALUE
        FROM rm_dataset_planning_unit dpu 
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN 
            (
            SELECT 
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndfu.FORECASTING_UNIT_ID
            FROM vw_forecast_tree_node ftn     
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_fu ftndfu ON ftndfu.NODE_DATA_FU_ID=ftnd.NODE_DATA_FU_ID 
            WHERE ftn.NODE_TYPE_ID=4
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND fu.FORECASTING_UNIT_ID=tree.FORECASTING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH BETWEEN @startMonth AND @stopMonth 
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIdList);
    END IF;
END$$

DELIMITER ;

USE `fasp`;
DROP procedure IF EXISTS `getForecastSummary`;

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
	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
        fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
        tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`,
	r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
	COALESCE(t.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN)) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR)) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP)) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR)) `SF_LABEL_PR`,
        dpus.TOTAL_FORECAST, dpus.NOTES,
        dpu.STOCK, dpu.EXISTING_SHIPMENTS, dpu.MONTHS_OF_STOCK, dpu.PRICE,
        pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PA_LABEL_ID`, pa.LABEL_EN `PA_LABEL_EN`, pa.LABEL_FR `PA_LABEL_FR`, pa.LABEL_SP `PA_LABEL_SP`, pa.LABEL_PR `PA_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE
    FROM rm_dataset_planning_unit dpu 
    LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=@programId
    LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND pr.REGION_ID=dpus.REGION_ID
    LEFT JOIN 
        (
        SELECT 
            ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID
        FROM vw_forecast_tree_node ftn     
        LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
        LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID 
        WHERE ftn.NODE_TYPE_ID=5
    ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND dpu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
    LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
    LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID
    LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID
    LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
    LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
    LEFT JOIN vw_procurement_agent pa ON dpu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    WHERE dpu.PROGRAM_ID=@programId and dpu.VERSION_ID=@versionId
    GROUP BY pr.REGION_ID, dpu.PLANNING_UNIT_ID;
END$$

DELIMITER ;


SET foreign_key_checks = 0;

ALTER TABLE `fasp`.`rm_equivalency_unit_mapping` DROP FOREIGN KEY `fk_rm_equivalency_mapping_programId`;
ALTER TABLE `fasp`.`rm_equivalency_unit_mapping` DROP INDEX `fk_rm_equivalency_mapping_programId_idx` ,
ADD INDEX `fk_rm_equivalency_mapping_programId_idx` (`PROGRAM_ID` ASC);

UPDATE rm_equivalency_unit_mapping m SET m.PROGRAM_ID=0 where m.PROGRAM_ID is null;
ALTER TABLE `fasp`.`rm_equivalency_unit_mapping` DROP INDEX `fk_rm_equivalency_mapping_uniqueRule` ,
ADD UNIQUE INDEX `unq_rm_equivalency_mapping_uniqueRule` (`REALM_ID` ASC, `EQUIVALENCY_UNIT_ID` ASC, `FORECASTING_UNIT_ID` ASC, `PROGRAM_ID` ASC);

SET foreign_key_checks = 1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='(No seasonality)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='(Pas de saisonnalité)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='(Sin estacionalidad)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='(Sem sazonalidade)'
where l.LABEL_CODE='static.tree.monthlyEndNoSeasonality' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.clickOkMsg','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'* Click `OK` to load latest server version (overrides local version)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'* Cliquez sur `OK` pour charger la dernière version du serveur (remplace la version locale)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'* Haga clic en `Aceptar` para cargar la última versión del servidor (anula la versión local)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'* Clique em `OK` para carregar a versão mais recente do servidor (substitui a versão local)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.clickCancelMsg','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'* Click `Cancel` to keep your local version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'* Cliquez sur ""Annuler"" pour conserver votre version locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'* Haga clic en `Cancelar` para mantener su versión local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'* Clique em `Cancelar` para manter sua versão local');-- pr


-- Anisha label script start
SET FOREIGN_KEY_CHECKS=0;
DELETE FROM ap_static_label_languages WHERE STATIC_LABEL_ID IN (SELECT GROUP_CONCAT(l.`STATIC_LABEL_ID`) FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.tooltip.NodeType',
'static.tooltip.NodeValue',
'static.tooltip.ModelingTransferMonth',
'static.tooltip.Transfercloumn',
'static.tooltip.ModelingType',
'static.tooltip.ModelingCalculator',
'static.tooltip.CalculatorChangeforMonth',
'static.tooltip.TargetEndingValue',
'static.tooltip.TargetChangePercent',
'static.tooltip.TargetChangeHash',
'static.tooltip.CalculatedMonthOnMonthChnage',
'static.tooltip.Parent',
'static.tooltip.PercentageOfParent',
'static.tooltip.ParentValue',
'static.tooltip.MonthlyChange',
'static.tooltip.tracercategoryModelingType',
'static.tooltip.CopyFromTemplate',
'static.tooltip.TypeOfUse',
'static.tooltip.LagInMonth',
'static.tooltip.SingleUse',
'static.tooltip.TypeOfUsePU',
'static.tooltip.planningUnitNode',
'static.tooltip.Conversionfactor',
'static.tooltip.NoOfPUUsage',
'static.tooltip.QATEstimateForInterval',
'static.tooltip.ConsumptionIntervalEveryXMonths',
'static.tooltip.willClientsShareOnePU',
'static.tooltip.HowManyPUperIntervalPer',
'static.tooltip.planningProgramSetting',
'static.tooltip.Stock',
'static.tooltip.ExistingShipments', 
'static.tooltip.DesiredMonthsofStock',  
'static.tooltip.PriceType',  
'static.tooltip.yAxisInEquivalencyUnit',  
'static.tooltip.version',  
'static.tooltip.HashOfDaysInMonth',  
'static.tooltip.FreightPercent', 
'static.tooltip.ForecastThresholdHigh',  
'static.tooltip.ForecastThresholdLow', 
'static.tooltip.ForecastProgram', 
'static.tooltip.UsageName', 
'static.tooltip.LagInMonth', 
'static.tooltip.UsageType', 
'static.tooltip.Persons', 
'static.tooltip.PersonsUnit', 
'static.tooltip.FUPersonTime', 
'static.tooltip.OneTimeUsage', 
'static.tooltip.OfTimeFreqwency', 
'static.tooltip.Freqwency', 
'static.tooltip.UsagePeriod', 
'static.tooltip.PeriodUnit', 
'static.tooltip.OfFuRequired', 
'static.tooltip.UsageInWords',
'static.tooltip.Display',
'static.tooltip.CompareandSelectType',
'static.tooltip.Forecst',
'static.tooltip.SelectAsForecast',
'static.tooltip.TotalForecast',
'static.tooltip.ForecastError',
'static.tooltip.ForecastErrorMonthUsed',
'static.tooltip.ComparetoConsumptionForecast',
'static.tooltip.NumberNodeValue'));

DELETE FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.tooltip.NodeType',
'static.tooltip.NodeValue',
'static.tooltip.ModelingTransferMonth',
'static.tooltip.Transfercloumn',
'static.tooltip.ModelingType',
'static.tooltip.ModelingCalculator',
'static.tooltip.CalculatorChangeforMonth',
'static.tooltip.TargetEndingValue',
'static.tooltip.TargetChangePercent',
'static.tooltip.TargetChangeHash',
'static.tooltip.CalculatedMonthOnMonthChnage',
'static.tooltip.Parent',
'static.tooltip.PercentageOfParent',
'static.tooltip.ParentValue',
'static.tooltip.MonthlyChange',
'static.tooltip.tracercategoryModelingType',
'static.tooltip.CopyFromTemplate',
'static.tooltip.TypeOfUse',
'static.tooltip.LagInMonth',
'static.tooltip.SingleUse',
'static.tooltip.TypeOfUsePU',
'static.tooltip.planningUnitNode',
'static.tooltip.Conversionfactor',
'static.tooltip.NoOfPUUsage',
'static.tooltip.QATEstimateForInterval',
'static.tooltip.ConsumptionIntervalEveryXMonths',
'static.tooltip.willClientsShareOnePU',
'static.tooltip.HowManyPUperIntervalPer',
'static.tooltip.planningProgramSetting',
'static.tooltip.Stock',
'static.tooltip.ExistingShipments', 
'static.tooltip.DesiredMonthsofStock',  
'static.tooltip.PriceType',  
'static.tooltip.yAxisInEquivalencyUnit',  
'static.tooltip.version',  
'static.tooltip.HashOfDaysInMonth',  
'static.tooltip.FreightPercent', 
'static.tooltip.ForecastThresholdHigh',  
'static.tooltip.ForecastThresholdLow', 
'static.tooltip.ForecastProgram', 
'static.tooltip.UsageName', 
'static.tooltip.LagInMonth', 
'static.tooltip.UsageType', 
'static.tooltip.Persons', 
'static.tooltip.PersonsUnit', 
'static.tooltip.FUPersonTime', 
'static.tooltip.OneTimeUsage', 
'static.tooltip.OfTimeFreqwency', 
'static.tooltip.Freqwency', 
'static.tooltip.UsagePeriod', 
'static.tooltip.PeriodUnit', 
'static.tooltip.OfFuRequired', 
'static.tooltip.UsageInWords',
'static.tooltip.Display',
'static.tooltip.CompareandSelectType',
'static.tooltip.Forecst',
'static.tooltip.SelectAsForecast',
'static.tooltip.TotalForecast',
'static.tooltip.ForecastError',
'static.tooltip.ForecastErrorMonthUsed',
'static.tooltip.ComparetoConsumptionForecast',
'static.tooltip.NumberNodeValue');

DELETE FROM ap_static_label_languages  WHERE STATIC_LABEL_ID IN (SELECT GROUP_CONCAT(l.`STATIC_LABEL_ID`) FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.MovingAverages',
'static.tooltip.SemiAverages',
'static.tooltip.LinearRegression',
'static.tooltip.Tes',
'static.tooltip.confidenceLevel',
'static.tooltip.seasonality',
'static.tooltip.alpha',
'static.tooltip.beta',
'static.tooltip.gamma',
'static.tooltip.arima',
'static.tooltip.p',
'static.tooltip.d',
'static.tooltip.q',
'static.tooltip.ReportingRate',
'static.tooltip.errors',
'static.tooltip.ChooseMethod'));

DELETE FROM ap_static_label WHERE LABEL_CODE IN ('static.tooltip.MovingAverages',
'static.tooltip.SemiAverages',
'static.tooltip.LinearRegression',
'static.tooltip.Tes',
'static.tooltip.confidenceLevel',
'static.tooltip.seasonality',
'static.tooltip.alpha',
'static.tooltip.beta',
'static.tooltip.gamma',
'static.tooltip.arima',
'static.tooltip.p',
'static.tooltip.d',
'static.tooltip.q',
'static.tooltip.ReportingRate',
'static.tooltip.errors',
'static.tooltip.ChooseMethod')

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.scenario','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose scenario for which the data below applies');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez le scénario pour lequel les données ci-dessous sappliquent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija el escenario para el que se aplican los datos a continuación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o cenário para o qual os dados abaixo se aplicam');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeTitle','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The node title will show up on the Tree View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le titre du nœud apparaîtra dans larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El título del nodo aparecerá en la vista de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O título do nó aparecerá na visualização em árvore');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on Show Guidance for explanation on the node types.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur Show Guidance pour obtenir des explications sur les types de nœuds.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en Mostrar orientación para obtener una explicación sobre los tipos de nodos.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em Mostrar Orientação para explicação sobre os tipos de nós.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Value = [Parent Value] * [Node Percentage]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud = [Valeur parent] * [Pourcentage du nœud]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo = [Valor principal] * [Porcentaje de nodo]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó = [Valor pai] * [Porcentagem do nó]');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingTransferMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select month for the sample calculation in the Calculated Change column.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez le mois pour lexemple de calcul dans la colonne Changement calculé.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el mes para el cálculo de muestra en la columna Cambio calculado.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o mês para o cálculo da amostra na coluna Alteração calculada.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Transfercloumn','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Link the changes in this node to the changes in another node in the same level Transfers must occur from left to right');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lier les modifications de ce nœud aux modifications dun autre nœud du même niveau Les transferts doivent se faire de gauche à droite');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vincule los cambios en este nodo a los cambios en otro nodo en el mismo nivel Las transferencias deben ocurrir de izquierda a derecha');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Vincule as mudanças neste nó às mudanças em outro nó no mesmo nível As transferências devem ocorrer da esquerda para a direita');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How the node will change over time. 
* Exponential (%): Percent increase/decrease based on the preceeding months value
* Linear (%): Percent increase/decrease based on the starting months value
* Linear (#): Increase/decrease based on a fixed, monthly quantity
Click on Show terms and logic for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comment le nœud va changer au fil du temps.
* Exponentiel (%) : Pourcentage daugmentation/diminution basé sur la valeur des mois précédents
* Linéaire (%) : Pourcentage daugmentation/diminution basé sur la valeur des mois de départ
* Linéaire (#) : augmentation/diminution basée sur une quantité mensuelle fixe
Cliquez sur Afficher les termes et la logique pour en savoir plus.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cómo cambiará el nodo con el tiempo.
* Exponencial (%): Porcentaje de aumento/disminución basado en el valor del mes anterior
* Lineal (%): Porcentaje de aumento/disminución basado en el valor del mes inicial
* Lineal (#): Incremento/disminución basado en una cantidad mensual fija
Haga clic en Mostrar términos y lógica para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Como o nó mudará ao longo do tempo.
* Exponencial (%): Aumento/diminuição percentual com base no valor dos meses anteriores
* Linear (%): Porcentagem de aumento/diminuição com base no valor do mês inicial
* Linear (#): Aumenta/diminui com base em uma quantidade fixa mensal
Clique em Mostrar termos e lógica para saber mais.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingCalculator','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use the modeling calculator to interpolate to a target, or if you need help figuring out the monthly change.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez la calculatrice de modélisation pour interpoler vers une cible ou si vous avez besoin daide pour déterminer la variation mensuelle.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice la calculadora de modelado para interpolar a un objetivo, o si necesita ayuda para calcular el cambio mensual.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use a calculadora de modelagem para interpolar para um destino ou se precisar de ajuda para descobrir a mudança mensal.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CalculatorChangeforMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sample calculation for one month. Click View month by month data for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple de calcul pour un mois. Cliquez sur Afficher les données mois par mois pour en savoir plus.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo de cálculo para un mes. Haga clic en Ver datos mes a mes para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo de amostra para um mês. Clique em Ver dados mês a mês para obter mais informações.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetEndingValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending value at the target date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur finale à la date cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor final en la fecha objetivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor final na data prevista');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetChangePercent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total change (positive or negative, in percent) for the node over the period (from start date to target date).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement total (positif ou négatif, en pourcentage) pour le nœud sur la période (de la date de début à la date cible).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio total (positivo o negativo, en porcentaje) para el nodo durante el período (desde la fecha de inicio hasta la fecha de destino).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração total (positiva ou negativa, em porcentagem) para o nó durante o período (da data de início à data de destino).');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetChangeHash','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total change (positive or negative) for the node over the period (from start date to target date).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement total (positif ou négatif) pour le nœud sur la période (de la date de début à la date cible).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio total (positivo o negativo) para el nodo durante el período (desde la fecha de inicio hasta la fecha de destino).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração total (positiva ou negativa) para o nó durante o período (da data de início à data de destino).');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CalculatedMonthOnMonthChnage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Using the provided target  QAT calculates a monthly change which will be fed back into the previous screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lutilisation de la cible QAT fournie calcule un changement mensuel qui sera réinjecté dans lécran précédent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilizando el QAT objetivo proporcionado, se calcula un cambio mensual que se retroalimentará a la pantalla anterior');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O uso do QAT de destino fornecido calcula uma alteração mensal que será realimentada na tela anterior');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Parent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change the parent use drag and drop on the Tree View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour changer le parent, utilisez le glisser-déposer sur larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar el uso principal, arrastre y suelte en la vista de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar o pai use arrastar e soltar na Vista em Árvore');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PercentageOfParent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the starting percentage of the node. If this value changes over time, use the Modeling/Transfer tab.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le pourcentage de départ du nœud. Si cette valeur évolue dans le temps, utilisez longlet Modélisation/Transfert.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el porcentaje inicial del nodo. Si este valor cambia con el tiempo, utilice la pestaña Modelado/Transferencia.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a porcentagem inicial do nó. Se este valor mudar com o tempo, use a aba Modelagem/Transferência.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ParentValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change the parent value edit the parent node directly');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour modifier la valeur parent, modifiez directement le nœud parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar el valor principal, edite el nodo principal directamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar o valor pai, edite o nó pai diretamente');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.MonthlyChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change in percentage points per month. For example, if the starting node value is 60% of the parent, and the Monthly Change is 5%, then month 2 starts at 65%, month 3 starts at 70%, etc.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation en points de pourcentage par mois. Par exemple, si la valeur du nœud de départ est de 60 % du parent et que la variation mensuelle est de 5 %, le mois 2 commence à 65 %, le mois 3 à 70 %, etc.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio en puntos porcentuales por mes. Por ejemplo, si el valor del nodo inicial es el 60 % del principal y el cambio mensual es del 5 %, entonces el mes 2 comienza en el 65 %, el mes 3 comienza en el 70 %, etc.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Variação em pontos percentuais por mês. Por exemplo, se o valor do nó inicial for 60% do pai e a Alteração Mensal for 5%, o mês 2 começará em 65%, o mês 3 começará em 70% etc.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.tracercategoryModelingType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a tracer category to narrow down the list of forecasting units to select from');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une catégorie de traceur pour affiner la liste des unités de prévision à sélectionner');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una categoría de seguimiento para reducir la lista de unidades de pronóstico para seleccionar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma categoria de rastreador para restringir a lista de unidades de previsão para selecionar');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CopyFromTemplate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use a template to fill in all subsquent fields. Edit these in the Program Management > Usage Template screen.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez un modèle pour remplir tous les champs suivants. Modifiez-les dans lécran Gestion des programmes > Modèle dutilisation.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice una plantilla para completar todos los campos subsiguientes. Edítelos en la pantalla Gestión de programas > Plantilla de uso.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use um modelo para preencher todos os campos subsequentes. Edite-os na tela Gerenciamento de programas > Modelo de uso.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TypeOfUse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select discrete if this product will be used for limited time, and continuous if it will be used indefinitely.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez discret si ce produit sera utilisé pendant un temps limité, et continu sil sera utilisé indéfiniment.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione discreto si este producto se usará por tiempo limitado y continuo si se usará indefinidamente.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione discreto se este produto for usado por tempo limitado e contínuo se for usado indefinidamente.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.LagInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this feature for phased product usage. For example, if the lag is 2, the product usage will begin 2 months after the parent node dates.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette fonction pour une utilisation progressive du produit. Par exemple, si le décalage est de 2, lutilisation du produit commencera 2 mois après les dates du nœud parent.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta función para el uso del producto por etapas. Por ejemplo, si el retraso es 2, el uso del producto comenzará 2 meses después de las fechas del nodo principal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esse recurso para uso do produto em fases. Por exemplo, se o atraso for 2, o uso do produto começará 2 meses após as datas do nó pai.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.SingleUse','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes frequency and length of time are not required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, la fréquence et la durée ne sont pas requises');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, no se requiere frecuencia ni duración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, a frequência e o tempo não são necessários');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TypeOfUsePU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change edit from parent Forecasting Unit node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour modifier la modification à partir du nœud dunité de prévision parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar la edición del nodo Unidad de previsión principal');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar a edição do nó pai da unidade de previsão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningUnitNode','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a planning unit that corresponds to the selected forecasting unit Edit the parent node if the forecasting unit is incorrect');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une unité de planification qui correspond à lunité de prévision sélectionnée Modifiez le nœud parent si lunité de prévision est incorrecte');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una unidad de planificación que corresponda a la unidad de previsión seleccionada. Edite el nodo principal si la unidad de previsión es incorrecta.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma unidade de planejamento que corresponda à unidade de previsão selecionada Edite o nó pai se a unidade de previsão estiver incorreta');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Conversionfactor','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-populated based on conversion between forecasting unit (FU) and planning unit (PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rempli automatiquement en fonction de la conversion entre lunité de prévision (FU) et lunité de planification (PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Autocompletado basado en la conversión entre la unidad de pronóstico (FU) y la unidad de planificación (PU)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preenchido automaticamente com base na conversão entre unidade de previsão (FU) e unidade de planejamento (PU)');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NoOfPUUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The number of forecasting units (FU) divided by the conversion factor equals the number of planning units (PU) required.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nombre dunités de prévision (UF) divisé par le facteur de conversion est égal au nombre dunités de planification (UP) nécessaires.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El número de unidades de pronóstico (FU) dividido por el factor de conversión es igual al número de unidades de planificación (PU) requeridas.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O número de unidades de previsão (FU) dividido pelo fator de conversão é igual ao número de unidades de planejamento (PU) necessárias.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.QATEstimateForInterval','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates an estimated consumption interval by dividing conversion factor by the quantity required monthly');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule un intervalle de consommation estimé en divisant le facteur de conversion par la quantité requise mensuellement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula un intervalo de consumo estimado dividiendo el factor de conversión por la cantidad requerida mensualmente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT calcula um intervalo de consumo estimado dividindo o fator de conversão pela quantidade necessária mensalmente');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ConsumptionIntervalEveryXMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the purposes of your forecast, how often is the product actually consumed? Consumption can be defined at different levels depending on your supply chain. For example, if the end user uses a product daily, but only picks up the product every 2 months, enter 2 to account for a multimonth consmption pattern.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aux fins de vos prévisions, à quelle fréquence le produit est-il réellement consommé ? La consommation peut être définie à différents niveaux en fonction de votre chaîne dapprovisionnement. Par exemple, si lutilisateur final utilise un produit quotidiennement, mais ne récupère le produit que tous les 2 mois, entrez 2 pour tenir compte dun modèle de consommation sur plusieurs mois.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A los efectos de su pronóstico, ¿con qué frecuencia se consume realmente el producto? El consumo se puede definir en diferentes niveles dependiendo de su cadena de suministro. Por ejemplo, si el usuario final usa un producto a diario, pero solo lo recoge cada 2 meses, ingrese 2 para tener en cuenta un patrón de consumo de varios meses.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para fins de sua previsão, com que frequência o produto é realmente consumido? O consumo pode ser definido em diferentes níveis, dependendo da sua cadeia de suprimentos. Por exemplo, se o usuário final usa um produto diariamente, mas só pega o produto a cada 2 meses, digite 2 para contabilizar um padrão de consumo de vários meses.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.willClientsShareOnePU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes, this means end users will be sharing a planning unit (e.g. if PU can be split into smaller units for distribution).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, cela signifie que les utilisateurs finaux partageront une unité de planification (par exemple, si PU peut être divisé en unités plus petites pour la distribution).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, esto significa que los usuarios finales compartirán una unidad de planificación (por ejemplo, si la PU se puede dividir en unidades más pequeñas para la distribución).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, isso significa que os usuários finais compartilharão uma unidade de planejamento (por exemplo, se a PU puder ser dividida em unidades menores para distribuição).');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.HowManyPUperIntervalPer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated as # of planning units (PU) per month * consumption interval.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculé en nombre dunités de planification (UP) par mois * intervalle de consommation.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculado como número de unidades de planificación (PU) por mes * intervalo de consumo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculado como # de unidades de planejamento (PU) por mês * intervalo de consumo.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningProgramSetting','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program must be loaded first as Planning Unit Settings are version specific');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le programme doit être chargé en premier car les paramètres dunité de planification sont spécifiques à la version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El programa debe cargarse primero ya que la configuración de la unidad de planificación es específica de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O programa deve ser carregado primeiro, pois as configurações da unidade de planejamento são específicas da versão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Stock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter your actual or projected stock at the beginning of your forecast period This is used for calculating the procurement gap in the Forecast Summary screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez votre stock réel ou projeté au début de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese su stock real o proyectado al comienzo de su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira seu estoque real ou projetado no início do período de previsão Isso é usado para calcular a lacuna de suprimento na tela Resumo da previsão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ExistingShipments','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the total quantity of existing shipments during your forecast period This is used for calculating the procurement gap in the Forecast Summary screen Leave this blank to assume no existing shipments');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la quantité totale dexpéditions existantes au cours de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions Laissez ce champ vide pour supposer quil ny a pas dexpéditions existantes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la cantidad total de envíos existentes durante su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico Deje esto en blanco para asumir que no hay envíos existentes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Introduza a quantidade total de envios existentes durante o seu período de previsão Istoéusado para calcular a lacuna de aprovisionamento no ecrã Resumo da Previsão Deixe em branco para assumir que não há envios existentes');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.DesiredMonthsofStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter your desired Months of Stock at the end of your forecast period This is used for calculating the procurement gap in the Forecast Summary screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez vos mois de stock souhaités à la fin de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese los Meses de existencias deseados al final de su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira os Meses de Estoque desejados no final do período de previsão Isso é usado para calcular a lacuna de aquisição na tela Resumo da Previsão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PriceType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a procurement agent for your price type If PA proces are available QAT will populate the price You may override QAT provided prices with your own. If you dont see the desired procurement agent, select custom and indicate the procurement agent name in the notes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez un agent dapprovisionnement pour votre type de prix Si les processus PA sont disponibles, QAT remplira le prix Vous pouvez remplacer les prix fournis par QAT par les vôtres. Si vous ne voyez pas lagent dapprovisionnement souhaité, sélectionnez personnalisé et indiquez le nom de lagent dapprovisionnement dans les notes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un agente de adquisiciones para su tipo de precio. Si los procesos de PA están disponibles, QAT completará el precio. Puede anular los precios provistos por QAT con los suyos. Si no ve el agente de compras deseado, seleccione personalizado e indique el nombre del agente de compras en las notas.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione um agente de compras para seu tipo de preço Se os processos de PA estiverem disponíveis, QAT preencherá o preço Você pode substituir os preços fornecidos pela QAT pelos seus próprios. Se você não vir o agente de compras desejado, selecione personalizado e indique o nome do agente de compras nas notas.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.yAxisInEquivalencyUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes - Y-axis is displayed in equivalency units, which allows the forecast to be displayed in programmatic units and combined with other products that share the same equivalency units. Program Admins: Use the Equivalency Units screen to manage equivalency units.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui - laxe Y est affiché en unités déquivalence, ce qui permet dafficher la prévision en unités programmatiques et de la combiner avec dautres produits partageant les mêmes unités déquivalence. Administrateurs du programme : utilisez lécran Unités déquivalence pour gérer les unités déquivalence.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si la respuesta es sí, el eje Y se muestra en unidades de equivalencia, lo que permite que el pronóstico se muestre en unidades programáticas y se combine con otros productos que comparten las mismas unidades de equivalencia. Administradores del programa: use la pantalla Unidades de equivalencia para administrar las unidades de equivalencia.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim - o eixo Y é exibido em unidades de equivalência, o que permite que a previsão seja exibida em unidades programáticas e combinada com outros produtos que compartilham as mesmas unidades de equivalência. Administradores do programa: Use a tela Unidades de equivalência para gerenciar unidades de equivalência.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.version','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only local version settings are editable Please load program if you wish to edit version settings');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuls les paramètres de la version locale sont modifiables Veuillez charger le programme si vous souhaitez modifier les paramètres de la version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo se puede editar la configuración de la versión local Cargue el programa si desea editar la configuración de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Apenas as configurações da versão local são editáveis ​​Carregue o programa se desejar editar as configurações da versão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.HashOfDaysInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Consumption Forecasts for adjusting actual consumption against stock out days');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans les prévisions de consommation pour ajuster la consommation réelle par rapport aux jours de rupture de stock');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se utiliza en las previsiones de consumo para ajustar el consumo real frente a los días de desabastecimiento');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado em previsões de consumo para ajustar o consumo real em relação aos dias de falta de estoque');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.FreightPercent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Forecast Summary screen to calculate freight cost.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans l`écran `Résumé des prévisions pour calculer le coût du fret.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se utiliza en la pantalla Resumen del pronóstico para calcular el costo del flete.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Resumo da Previsão para calcular o custo do frete.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastThresholdHigh','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Compare and Select screen to compare tree forecasts againt consumption forecasts QAT will flag any tree forecasts that are this % above the highest consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans lécran Comparer et sélectionner pour comparer les prévisions darbre avec les prévisions de consommation QAT signalera toutes les prévisions darbre qui sont ce % au-dessus de la prévision de consommation la plus élevée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se usa en la pantalla Comparar y Seleccionar para comparar pronósticos de árbol con pronósticos de consumo. QAT marcará cualquier pronóstico de árbol que esté este % por encima del pronóstico de consumo más alto.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Comparar e Selecionar para comparar as previsões da árvore com as previsões de consumo O QAT sinalizará qualquer previsão de árvore que esteja esta % acima da previsão de consumo mais alta');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastThresholdLow','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Compare and Select screen to compare tree forecasts againt consumption forecasts QAT will flag any tree forecasts that are this % below the lowest consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans lécran Comparer et sélectionner pour comparer les prévisions darbre avec les prévisions de consommation QAT signalera toutes les prévisions darbre qui sont à ce % en dessous de la prévision de consommation la plus basse');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se usa en la pantalla Comparar y seleccionar para comparar pronósticos de árbol con pronósticos de consumo. QAT marcará cualquier pronóstico de árbol que esté este % por debajo del pronóstico de consumo más bajo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Comparar e Selecionar para comparar as previsões da árvore com as previsões de consumo O QAT sinalizará qualquer previsão de árvore que esteja essa % abaixo da previsão de consumo mais baixa');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Realm-Level templates are available to all users. However, program admins can create program-specific templates.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les modèles Realm-Level sont disponibles pour tous les utilisateurs. Cependant, les administrateurs du programme peuvent créer des modèles spécifiques au programme.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las plantillas de nivel de reino están disponibles para todos los usuarios. Sin embargo, los administradores del programa pueden crear plantillas específicas del programa.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os modelos de Nível de Realm estão disponíveis para todos os usuários. No entanto, os administradores do programa podem criar modelos específicos do programa.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'These Ugsage Names will appear when adding editing a forecasting tree node in the Copy from Template dropdown');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ces noms Ugsage apparaîtront lors de lajout de la modification dun nœud darbre de prévision dans la liste déroulante Copier à partir du modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estos nombres de uso aparecerán al agregar la edición de un nodo de árbol de pronóstico en el menú desplegable Copiar de plantilla');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esses nomes de Ugsage aparecerão ao adicionar a edição de um nó de árvore de previsão no menu suspenso Copiar do modelo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.LagInMonth','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'0 indicates immediate usage');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'0 indique une utilisation immédiate');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'0 indica uso inmediato');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'0 indica uso imediato');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Continuous usage product will be used for an indeterminate amount of time Discrete usage product will be used for a finite period as defined by the user');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le produit à usage continu sera utilisé pendant une durée indéterminée Le produit à usage discret sera utilisé pendant une période déterminée définie par lutilisateur');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El producto de uso continuo se utilizará durante un período de tiempo indeterminado El producto de uso discreto se utilizará durante un período finito definido por el usuario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O produto de uso contínuo será usado por um período indeterminado O produto de uso discreto será usado por um período finito conforme definido pelo usuário');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Persons','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If product is shared, indicate how many person(s) share this product. If a product is not shared, enter 1.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le produit est partagé, indiquez combien de personnes partagent ce produit. Si un produit nest pas partagé, entrez 1.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se comparte el producto, indique cuántas personas comparten este producto. Si un producto no se comparte, ingrese 1.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o produto for compartilhado, indique quantas pessoas compartilham este produto. Se um produto não for compartilhado, digite 1.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PersonsUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The desired unit for the # Person(s) column. Note: this is used in the Usage in Words column on this screen, but when the template is used, this unit does not carry over. Instead, the unit of the parent node is used.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lunité souhaitée pour la colonne # Personne(s). Remarque : ceci est utilisé dans la colonne « Utilisation en mots » sur cet écran, mais lorsque le modèle est utilisé, cette unité nest pas reportée. Au lieu de cela, lunité du nœud parent est utilisée.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La unidad deseada para la columna # Person(s). Nota: esto se usa en la columna Uso en palabras en esta pantalla, pero cuando se usa la plantilla, esta unidad no se transfiere. En su lugar, se utiliza la unidad del nodo principal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A unidade desejada para a coluna # pessoa(s). Nota: isso é usado na coluna Uso em palavras nesta tela, mas quando o modelo é usado, esta unidade não é transportada. Em vez disso, a unidade do nó pai é usada.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.FUPersonTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How much of the forecasting unit is consumed each time');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quelle quantité dunité de prévision est consommée à chaque fois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuánto de la unidad de pronóstico se consume cada vez');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quanto da unidade de previsão é consumida a cada vez');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OneTimeUsage','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Discrete only)) If Yes, the forecasting unit is only used once and subsequent fields will not be required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Discrète uniquement)) Si Oui, lunité de prévision nest utilisée quune seule fois et les champs suivants ne seront pas requis');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Solo discreto)) Si la respuesta es Sí, la unidad de pronóstico solo se usa una vez y los campos subsiguientes no serán obligatorios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Discreto apenas)) Se Sim, a unidade de previsão é usada apenas uma vez e os campos subsequentes não serão obrigatórios');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OfTimeFreqwency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How often the forecasting unit is used based on the time period in Frequency Unit. For example, if the product is used 4 times per year, enter 4 in this column and year in the next column.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fréquence dutilisation de lunité de prévision en fonction de la période dans Unité de fréquence. Par exemple, si le produit est utilisé 4 fois par an, inscrivez « 4 » dans cette colonne et « année » dans la colonne suivante.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Con qué frecuencia se usa la unidad de pronóstico según el período de tiempo en Unidad de frecuencia. Por ejemplo, si el producto se usa 4 veces al año, ingrese 4 en esta columna y año en la columna siguiente.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Com que frequência a unidade de previsão é usada com base no período de tempo em Unidade de frequência. Por exemplo, se o produto for usado 4 vezes por ano, insira 4 nesta coluna e ano na coluna seguinte.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Freqwency','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The time period referenced by the Frequency used in the previous column. For example, if the product is used 3 times per day, enter 3 in previous column and day in this column.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La période référencée par la Fréquence utilisée dans la colonne précédente. Par exemple, si le produit est utilisé 3 fois par jour, saisissez 3 dans la colonne précédente et jour dans cette colonne.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El período de tiempo al que hace referencia la Frecuencia utilizada en la columna anterior. Por ejemplo, si el producto se usa 3 veces al día, ingrese 3 en la columna anterior y día en esta columna.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O período de tempo referenciado pela Frequência usada na coluna anterior. Por exemplo, se o produto for usado 3 vezes por dia, insira 3 na coluna anterior e dia nesta coluna.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsagePeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The length of the usage period. For continuous usage, choose indefinitely');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La durée de la période dutilisation. Pour une utilisation continue, choisissez indéfiniment');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La duración del período de uso. Para uso continuo, elija indefinidamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A duração do período de uso. Para uso contínuo, escolha indefinidamente');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PeriodUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The desired unit for the Period column.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lunité souhaitée pour la colonne Période.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La unidad deseada para la columna Período.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A unidade desejada para a coluna Período.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OfFuRequired','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Discrete only) The total forecasting units required for the period. Equal to: (# of FU / Person / Time) / (# People) * (Usage Frequency, converted to frequency/month) * (Usage Period, converted to months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Discrète uniquement) Le nombre total dunités de prévision requises pour la période. Égal à : (# dUF / Personne / Temps) / (# Personnes) * (Fréquence dutilisation, convertie en fréquence/mois) * (Période dutilisation, convertie en mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Solo discreto) Las unidades de pronóstico totales requeridas para el período. Igual a: (# de FU/Persona/Tiempo) / (# de Personas) * (Frecuencia de Uso, convertida a frecuencia/mes) * (Período de Uso, convertido a meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Somente discreto) O total de unidades de previsão necessárias para o período. Igual a: (# de FU / Pessoa / Tempo) / (# Pessoas) * (Frequência de uso, convertida em frequência/mês) * (Período de uso, convertido em meses)');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageInWords','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This column combines all user inputs provided in previous columns Use this column to ensure your inputs are correct');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cette colonne combine toutes les entrées utilisateur fournies dans les colonnes précédentes Utilisez cette colonne pour vous assurer que vos entrées sont correctes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta columna combina todas las entradas del usuario proporcionadas en las columnas anteriores Use esta columna para asegurarse de que sus entradas sean correctas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta coluna combina todas as entradas do utilizador fornecidas nas colunas anteriores Use esta coluna para garantir que as suas entradas estão correctas');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Display','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select which forecasts to display in the graph and table below');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez les prévisions à afficher dans le graphique et le tableau ci-dessous');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione qué pronósticos mostrar en el gráfico y la tabla a continuación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione quais previsões serão exibidas no gráfico e na tabela abaixo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CompareandSelectType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree forecast or a Consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision darbre ou prévision de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico de árbol o un pronóstico de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão de árvore ou uma previsão de consumo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Forecst','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For Tree Forecasts this field displays tree and scenario name set by the user For Consumption Forecasts this field displays the names of the consumption forecast methods selected by the user Forecasts with missing data will be gray and non editable To add or update forecast data navigate to the Consumption or Tree forecast screens using the links in the upper left of the screen or on the lefthand side menu');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour les prévisions arborescentes, ce champ affiche le nom de larborescence et du scénario défini par lutilisateur. Pour les prévisions de consommation, ce champ affiche les noms des méthodes de prévision de consommation sélectionnées par lutilisateur. Les prévisions avec des données manquantes seront grisées et non modifiables. Pour ajouter ou mettre à jour les données de prévision, accédez au Écrans de prévision de consommation ou darborescence en utilisant les liens en haut à gauche de lécran ou dans le menu de gauche');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para pronósticos de árbol, este campo muestra el árbol y el nombre del escenario establecido por el usuario. Para pronósticos de consumo, este campo muestra los nombres de los métodos de pronóstico de consumo seleccionados por el usuario. Los pronósticos con datos faltantes aparecerán en gris y no se podrán editar. Pantallas de previsión de consumo o árbol utilizando los enlaces en la parte superior izquierda de la pantalla o en el menú del lado izquierdo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para Previsões de Árvore este campo exibe o nome da árvore e do cenário definido pelo usuário Para Previsões de Consumo este campo exibe os nomes dos métodos de previsão de consumo selecionados pelo usuário Previsões com dados ausentes serão cinza e não editáveis ​​Para adicionar ou atualizar dados de previsão navegue até o Telas de previsão de consumo ou árvore usando os links no canto superior esquerdo da tela ou no menu do lado esquerdo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.SelectAsForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select one forecast as the final forecast for each region and planning unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une prévision comme prévision finale pour chaque région et unité de planification');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un pronóstico como el pronóstico final para cada región y unidad de planificación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma previsão como a previsão final para cada região e unidade de planejamento');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TotalForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total forecasted quantity during the Forecast Period.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale prévue pendant la période de prévision.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total prevista durante el Período de previsión.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total prevista durante o período de previsão.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastError','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error calculated using Weighted Absolute Percentage Error (WAPE).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur de prévision calculée à laide du pourcentage derreur absolu pondéré (WAPE).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de pronóstico calculado utilizando el error de porcentaje absoluto ponderado (WAPE).');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de previsão calculado usando o erro de porcentagem absoluta ponderada (WAPE).');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastErrorMonthUsed','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of months used in the Forecast Error WAPE calculation.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois utilisés dans le calcul WAPE de lerreur de prévision.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses utilizados en el cálculo de WAPE de error de pronóstico.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses usados ​​no cálculo de WAPE de erro de previsão.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ComparetoConsumptionForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each Tree Forecast QAT will calculate the percentage above the highest or below the lowest Consumption Forecast The percentage will be highlighted in red text if it is outside of the threshold percentages set by the user in the Version Settings screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque arbre de prévision, QAT calculera le pourcentage au-dessus de la prévision de consommation la plus élevée ou en dessous de la plus basse Le pourcentage sera mis en surbrillance en rouge sil est en dehors des pourcentages de seuil définis par lutilisateur dans lécran Paramètres de version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada Pronóstico de árbol, QAT calculará el porcentaje por encima del más alto o por debajo del más bajo. Pronóstico de consumo El porcentaje se resaltará en texto rojo si está fuera de los porcentajes de umbral establecidos por el usuario en la pantalla Configuración de la versión.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada Tree Forecast o QAT calculará a porcentagem acima da mais alta ou abaixo da mais baixa Previsão de Consumo A porcentagem será destacada em texto vermelho se estiver fora das porcentagens limite definidas pelo usuário na tela de Configurações da Versão');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NumberNodeValue','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the starting value of the node. If this value changes over time, use the Modeling/Transfer tab.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la valeur de départ du nœud. Si cette valeur change dans le temps, utilisez longlet Modélisation/Transfert.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el valor inicial del nodo. Si este valor cambia con el tiempo, utilice la pestaña Modelado/Transferencia.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o valor inicial do nó. Se esse valor mudar com o tempo, use a guia Modelagem/Transferência.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.MovingAverages','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Moving average is an average that moves along time, dropping older data as it incorporates newer data. For QAT to calculate the moving average, enter the number months in the past that you would like to use in the calculation. See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La moyenne mobile est une moyenne qui évolue dans le temps, supprimant les données plus anciennes à mesure quelle intègre des données plus récentes. Pour que QAT calcule la moyenne mobile, entrez le nombre de mois passés que vous souhaitez utiliser dans le calcul. Voir Afficher le guidage pour plus dinformations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El promedio móvil es un promedio que se mueve a lo largo del tiempo, descartando datos más antiguos a medida que incorpora datos más nuevos. Para que QAT calcule el promedio móvil, ingrese el número de meses en el pasado que le gustaría usar en el cálculo. Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A média móvel é uma média que se move ao longo do tempo, descartando dados mais antigos à medida que incorpora dados mais recentes. Para QAT calcular a média móvel, insira o número de meses no passado que você gostaria de usar no cálculo. Consulte Mostrar orientação para obter mais informações.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.SemiAverages','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Semi-average estimates trends based on two halves of a series. QAT divides the actual data into two equal parts (halves) and the arithmetic mean of the values of each part (half) is calculated as the y values of two points on a line. The slope of the trend line is determined by the difference between these y values over time as defined by the midpoints of the two halves of the series or x values of the points. See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Estimations semi-moyennes des tendances basées sur les deux moitiés dune série. QAT divise les données réelles en deux parties égales (moitiés) et la moyenne arithmétique des valeurs de chaque partie (moitié) est calculée comme les valeurs y de deux points sur une ligne. La pente de la ligne de tendance est déterminée par la différence entre ces valeurs y dans le temps, telles que définies par les points médians des deux moitiés de la série ou les valeurs x des points. Voir Afficher le guidage pour plus dinformations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Semi-promedio estima tendencias basadas en dos mitades de una serie. QAT divide los datos reales en dos partes iguales (mitades) y la media aritmética de los valores de cada parte (mitad) se calcula como los valores y de dos puntos en una línea. La pendiente de la línea de tendencia está determinada por la diferencia entre estos valores y a lo largo del tiempo, según lo definido por los puntos medios de las dos mitades de la serie o los valores x de los puntos. Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A semi-média estima tendências com base em duas metades de uma série. QAT divide os dados reais em duas partes iguais (metades) e a média aritmética dos valores de cada parte (metade) é calculada como os valores de y de dois pontos em uma linha. A inclinação da linha de tendência é determinada pela diferença entre esses valores de y ao longo do tempo, conforme definido pelos pontos médios das duas metades da série ou valores de x dos pontos. Consulte Mostrar orientação para obter mais informações.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.LinearRegression','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linear regression models the relationship between two variables by fitting a linear equation to observed data. Confidence interval: between 0% and 100% (exclusive) e.g. 90% confidence level indicates 90% of possible future points are to fall within this radius from prediction represented by the regression line.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La régression linéaire modélise la relation entre deux variables en ajustant une équation linéaire aux données observées. Intervalle de confiance : entre 0% et 100% (exclusif) ex. Un niveau de confiance de 90 % indique que 90 % des points futurs possibles doivent se situer dans ce rayon de prédiction représenté par la ligne de régression.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La regresión lineal modela la relación entre dos variables ajustando una ecuación lineal a los datos observados. Intervalo de confianza: entre 0% y 100% (exclusivo) ej. El nivel de confianza del 90 % indica que el 90 % de los posibles puntos futuros caerán dentro de este radio de la predicción representada por la línea de regresión.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A regressão linear modela a relação entre duas variáveis ​​ajustando uma equação linear aos dados observados. Intervalo de confiança: entre 0% e 100% (exclusivo) ex. O nível de confiança de 90% indica que 90% dos possíveis pontos futuros estão dentro desse raio da previsão representada pela linha de regressão.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Tes','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Whereas a moving average weighs each data point equally, exponential smoothing uses older data at exponentially decreasing weights over time. Triple exponential smoothing applies smoothing to the level (alpha), trend (beta), and seasonality (gamma) - parameters are set between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values.
');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alors quune moyenne mobile pèse chaque point de données de manière égale, le lissage exponentiel utilise des données plus anciennes à des poids décroissants de manière exponentielle au fil du temps. Le triple lissage exponentiel applique le lissage au niveau (alpha), à la tendance (bêta) et à la saisonnalité (gamma) - les paramètres sont définis entre 0 et 1, les valeurs proches de 1 favorisant les valeurs récentes et les valeurs proches de 0 favorisant les valeurs passées.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mientras que un promedio móvil pondera cada punto de datos por igual, el suavizado exponencial utiliza datos más antiguos con ponderaciones que disminuyen exponencialmente a lo largo del tiempo. El suavizado exponencial triple aplica suavizado al nivel (alfa), la tendencia (beta) y la estacionalidad (gamma): los parámetros se establecen entre 0 y 1, con valores cercanos a 1 que favorecen los valores recientes y valores cercanos a 0 que favorecen los valores pasados.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enquanto uma média móvel pesa cada ponto de dados igualmente, a suavização exponencial usa dados mais antigos com pesos exponencialmente decrescentes ao longo do tempo. A suavização exponencial tripla aplica a suavização ao nível (alfa), tendência (beta) e sazonalidade (gama) - os parâmetros são definidos entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.confidenceLevel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'between 0% and 100% (exclusive) e.g. 90% confidence level indicates 90% of future points are to fall within this radius from prediction.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'entre 0% et 100% (exclusif) par ex. Un niveau de confiance de 90 % indique que 90 % des points futurs doivent se situer dans ce rayon de prédiction.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'entre 0% y 100% (exclusivo) p. El nivel de confianza del 90% indica que el 90% de los puntos futuros caerán dentro de este radio de predicción.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'entre 0% e 100% (exclusivo) e. O nível de confiança de 90% indica que 90% dos pontos futuros devem cair dentro desse raio de previsão.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.seasonality','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'the expected length (in months) of any repetitive pattern in the consumption');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'la durée prévue (en mois) de tout schéma répétitif dans la consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'la duración esperada (en meses) de cualquier patrón repetitivo en el consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'a duração esperada (em meses) de qualquer padrão repetitivo no consumo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.alpha','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Alpha (level) between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Alpha (niveau) compris entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Alfa (nivel) entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alfa (nível) entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.beta','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'beta (trend), between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'beta (tendance), entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'beta (tendencia), entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'beta (tendência), entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.gamma','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'gamma (seasonality): between 0 and 1, with values close to 1 favoring recent values and values close to 0 favoring past values. 
See Show Guidance for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'gamma (saisonnalité) : entre 0 et 1, avec des valeurs proches de 1 favorisant les valeurs récentes et des valeurs proches de 0 favorisant les valeurs passées.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'gamma (estacionalidad): entre 0 y 1, con valores cercanos a 1 favoreciendo valores recientes y valores cercanos a 0 favoreciendo valores pasados.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'gama (sazonalidade): entre 0 e 1, com valores próximos de 1 favorecendo valores recentes e valores próximos de 0 favorecendo valores passados.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.arima','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-regressive means each point is influenced by its previous values and moving average which is a linear combination of several points. Both parts are integrated together to fit a best model for the series.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voir Afficher le guidage pour plus dinformations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consulte Mostrar orientación para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consulte Mostrar orientação para obter mais informações.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.p','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'p or AR (lag order) the number of lag observations in the model');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'p ou AR (ordre des retards) le nombre dobservations de retard dans le modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'p o AR (orden de retraso) el número de observaciones de retraso en el modelo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'p ou AR (ordem de atraso)  o número de observações de atraso no modelo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.d','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'d or I (degree of differencing) the number of times that the raw observations are differenced.  This value is normally 1 (if there is a trend) or 0 (no trend).  Other higher values are possible but not expected
');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'d ou I (degré de différenciation) le nombre de fois que les observations brutes sont différenciées. Cette valeur est normalement 1 (sil y a une tendance) ou 0 (pas de tendance). Dautres valeurs plus élevées sont possibles mais pas attendues');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'d o I (grado de diferenciación) el número de veces que se diferencian las observaciones sin procesar. Este valor es normalmente 1 (si hay tendencia) o 0 (sin tendencia). Otros valores más altos son posibles pero no esperados');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'d ou I (grau de diferenciação) o número de vezes que as observações brutas são diferenciadas. Esse valor normalmente é 1 (se houver tendência) ou 0 (sem tendência). Outros valores mais altos são possíveis, mas não esperados');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.q','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'q or MA (order of the moving average) the size of the moving average window.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'q ou MA (ordre de la moyenne mobile) la taille de la fenêtre de la moyenne mobile.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'q o MA (orden de la media móvil) el tamaño de la ventana de la media móvil.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'q ou MA (ordem da média móvel) o tamanho da janela da média móvel.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ReportingRate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Percentage of all data that was reported. This number is used to adjust the historical data upwards to account for missing data.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de toutes les données qui ont été déclarées. Ce nombre est utilisé pour ajuster les données historiques vers le haut pour tenir compte des données manquantes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de todos los datos que se informó. Este número se utiliza para ajustar los datos históricos hacia arriba para tener en cuenta los datos que faltan.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem de todos os dados que foram relatados. Esse número é usado para ajustar os dados históricos para cima para contabilizar dados ausentes.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.errors','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'RMSE: Root Mean Square Error  MAPE: Mean Absolute Percentage Error; MSE: Mean Squared Error; WAPE: Weighted Absolute Percentage Error. See "Show Guidance" for more.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'RMSE : erreur quadratique moyenne ; MAPE : pourcentage derreur moyen absolu ; MSE : erreur quadratique moyenne ; WAPE : pourcentage derreur absolu pondéré. Voir "Afficher le guidage" pour plus dinformations.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'RMSE: Error cuadrático medio de la raíz; MAPE: Error Medio Absoluto Porcentual; MSE: Error cuadrático medio; WAPE: Error porcentual absoluto ponderado. Consulte "Mostrar orientación" para obtener más información.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'RMSE: Erro quadrático médio; MAPE: Erro Percentual Absoluto Médio; MSE: Erro Quadrado Médio; WAPE: Erro de porcentagem absoluta ponderada. Consulte "Mostrar orientação" para obter mais informações.');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ChooseMethod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select which extrapolation method is used for the node value.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez la méthode dextrapolation utilisée pour la valeur du nœud.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione qué método de extrapolación se utiliza para el valor del nodo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione qual método de extrapolação é usado para o valor do nó.');



-- Anisha label script end
INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.dataEnteredIn','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Check');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données saisies dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos ingresados ​​en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados inseridos');-- pr

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_data` CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(18,4) NULL DEFAULT NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_option_data` CHANGE COLUMN `AMOUNT` `AMOUNT` DECIMAL(18,4) NULL DEFAULT NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'Based on the forecast_tree_node.NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_mom` 
CHANGE COLUMN `START_VALUE` `START_VALUE` DECIMAL(18,4) UNSIGNED NOT NULL ,
CHANGE COLUMN `END_VALUE` `END_VALUE` DECIMAL(18,4) UNSIGNED NOT NULL ,
CHANGE COLUMN `CALCULATED_VALUE` `CALCULATED_VALUE` DECIMAL(18,4) UNSIGNED NOT NULL ,
CHANGE COLUMN `CALCULATED_MMD_VALUE` `CALCULATED_MMD_VALUE` DECIMAL(18,4) UNSIGNED NOT NULL ,
CHANGE COLUMN `DIFFERENCE` `DIFFERENCE` DECIMAL(18,4) NOT NULL ,
CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(18,4) NOT NULL ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_override` 
CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'The manual change value' ,
CHANGE COLUMN `SEASONALITY_PERC` `SEASONALITY_PERC` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_fu` 
CHANGE COLUMN `FORECASTING_UNITS_PER_PERSON` `FORECASTING_UNITS_PER_PERSON` DECIMAL(18,4) UNSIGNED NOT NULL COMMENT '# of Forecasting Units ' ,
CHANGE COLUMN `USAGE_FREQUENCY` `USAGE_FREQUENCY` DECIMAL(18,4) UNSIGNED NULL DEFAULT NULL COMMENT '# of times the Forecasting Unit is given per Usage' ,
CHANGE COLUMN `REPEAT_COUNT` `REPEAT_COUNT` DECIMAL(18,4) UNSIGNED NULL DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type' ;
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_modeling` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,4) NOT NULL COMMENT 'Data value could be a number of a % based on the ScaleTypeId' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'Based on the NODE_TYPE_ID this value will be used either as a direct value or as a Perc of the Parent' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data_fu` 
CHANGE COLUMN `FORECASTING_UNITS_PER_PERSON` `FORECASTING_UNITS_PER_PERSON` DECIMAL(18,4) UNSIGNED NOT NULL COMMENT '# of Forecasting Units ' ,
CHANGE COLUMN `USAGE_FREQUENCY` `USAGE_FREQUENCY` DECIMAL(18,4) UNSIGNED NULL DEFAULT NULL COMMENT '# of times the Forecasting Unit is given per Usage' ,
CHANGE COLUMN `REPEAT_COUNT` `REPEAT_COUNT` DECIMAL(18,4) UNSIGNED NULL DEFAULT NULL COMMENT '# of times it is repeated for the Discrete type' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data_modeling` CHANGE COLUMN `DATA_VALUE` `DATA_VALUE` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'Data value could be a number of a % based on the ModelingTypeId' ;
ALTER TABLE `fasp`.`rm_tree_template_node_data_override` CHANGE COLUMN `MANUAL_CHANGE` `MANUAL_CHANGE` DECIMAL(18,4) NOT NULL COMMENT 'The manual change value' ,CHANGE COLUMN `SEASONALITY_PERC` `SEASONALITY_PERC` DECIMAL(18,4) NULL DEFAULT NULL COMMENT 'Seasonality % value. Only applicable for Number nodes not for Percentage nodes, FU or PU nodes' ;


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingValidation.puLevel','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'PU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'PU');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingValidation.fuLevel','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'FU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'FU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'FU');-- pr