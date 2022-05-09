/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 04-May-2022
 */

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Supply planning module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_SUPPLY_PLANNING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_FORECASTING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

update ap_label l set l.LABEL_EN='SP - QAT Forecast Import' where l.LABEL_EN='QAT Forecast Import';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Period' where l.LABEL_EN='Add Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Period' where l.LABEL_EN='Edit Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Period' where l.LABEL_EN='List Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Add Modeling Type' where l.LABEL_EN='Add Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Edit Modeling Type' where l.LABEL_EN='Edit Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - List Modeling Type' where l.LABEL_EN='List Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Method' where l.LABEL_EN='Add Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Method' where l.LABEL_EN='Edit Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Method' where l.LABEL_EN='List Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit' where l.LABEL_EN='Add Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit' where l.LABEL_EN='Edit Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit' where l.LABEL_EN='List Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit Mapping' where l.LABEL_EN='Add Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit Mapping' where l.LABEL_EN='Edit Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit Mapping' where l.LABEL_EN='List Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Program' where l.LABEL_EN='Add Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Program' where l.LABEL_EN='Edit Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Program' where l.LABEL_EN='List Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Template' where l.LABEL_EN='Add Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Template' where l.LABEL_EN='Edit Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Template' where l.LABEL_EN='List Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Import From QAT Supply Plan' where l.LABEL_EN='Add Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Edit Import From QAT Supply Plan' where l.LABEL_EN='Edit Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - List Import From QAT Supply Plan' where l.LABEL_EN='List Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Compare and Select Forecast' where l.LABEL_EN='Compare and Select Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Product Validation' where l.LABEL_EN='Product Validation';
update ap_label l set l.LABEL_EN='Forecasting - Modeling Validation' where l.LABEL_EN='Modeling Validation';
update ap_label l set l.LABEL_EN='Forecasting - Compare Version' where l.LABEL_EN='Compare Version';
update ap_label l set l.LABEL_EN='Forecasting - Extrapolation' where l.LABEL_EN='Extrapolation';
update ap_label l set l.LABEL_EN='Forecasting - Commit Dataset' where l.LABEL_EN='Commit Dataset';
update ap_label l set l.LABEL_EN='Forecasting - Consumption data entry and adjustment' where l.LABEL_EN='Consumption data entry and adjustment';
update ap_label l set l.LABEL_EN='Forecasting - Add Planning Unit Setting' where l.LABEL_EN='Add Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - Edit Planning Unit Setting' where l.LABEL_EN='Edit Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Planning Unit Setting' where l.LABEL_EN='List Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Monthly Forecast' where l.LABEL_EN='List Monthly Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Summary' where l.LABEL_EN='Edit Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Summary' where l.LABEL_EN='List Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Tree Template' where l.LABEL_EN='List Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree Template' where l.LABEL_EN='Add Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - List Tree' where l.LABEL_EN='List Tree';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree' where l.LABEL_EN='Add Tree';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree Template' where l.LABEL_EN='Edit Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree' where l.LABEL_EN='Edit Tree';


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
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu 
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
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
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND pu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH=mn.MONTH
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH=mn.MONTH
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIdList)
	ORDER BY pu.PLANNING_UNIT_ID, r.REGION_ID, mn.MONTH;
    ELSEIF @reportView = 2 THEN
        SELECT 
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
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
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH BETWEEN @startMonth AND @stopMonth
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIdList);
    END IF;
END$$

DELIMITER ;
;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.levelModelingValdation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'These are based on the various levels in the Forecast Tree, including an automated level which consolidates FUs or PUs, should they span across multiple levels. Users can choose to manually update the name of each level in the Forecast Tree screen, if desired, and it will update in this dropdown.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ceux-ci sont basés sur les différents niveaux de l arborescence des prévisions, y compris un niveau automatisé qui consolide les FU ou les PU, s ils s étendent sur plusieurs niveaux. Les utilisateurs peuvent choisir de mettre à jour manuellement le nom de chaque niveau dans l écran de l arborescence des prévisions, s ils le souhaitent, et il sera mis à jour dans cette liste déroulante.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estos se basan en los distintos niveles del árbol de pronóstico, incluido un nivel automatizado que consolida las FU o las PU, en caso de que abarquen varios niveles. Los usuarios pueden optar por actualizar manualmente el nombre de cada nivel en la pantalla Árbol de pronóstico, si lo desean, y se actualizará en este menú desplegable.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Eles são baseados nos vários níveis da Árvore de Previsão, incluindo um nível automatizado que consolida FUs ou PUs, caso eles se estendam por vários níveis. Os usuários podem optar por atualizar manualmente o nome de cada nível na tela Árvore de previsão, se desejarem, e isso será atualizado neste menu suspenso.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.TotalForecastedQuantity','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total forecasted quantity during the Forecast Period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale prévue pendant la période de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total prevista durante el Período de previsión.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total prevista durante o período de previsão.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.StockEndOfDec','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered in the Update Planning Units screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l utilisateur dans l écran Mettre à jour les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introducido por el usuario en la pantalla Actualizar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário na tela Atualizar unidades de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.ExistingShipments','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered in the Update Planning Units screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l utilisateur dans l écran Mettre à jour les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introducido por el usuario en la pantalla Actualizar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário na tela Atualizar unidades de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.StockorUnmetDemand','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Stock + Existing Shipments) - Total Forecasted Quantity. If this amount is below zero, QAT calculates an Unmet Demand quantity, which will appear in red text.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Stock + Livraisons existantes) - Quantité totale prévue. Si ce montant est inférieur à zéro, QAT calcule une quantité de demande non satisfaite, qui apparaîtra en texte rouge.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Stock + Envíos existentes) - Cantidad total pronosticada. Si esta cantidad es inferior a cero, QAT calcula una cantidad de Demanda no satisfecha, que aparecerá en texto rojo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Estoque + Remessas Existentes) - Quantidade Total Prevista. Se esse valor estiver abaixo de zero, o QAT calcula uma quantidade de Demanda não atendida, que aparecerá em texto vermelho.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.desiredMonthsOfStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered in the Update Planning Units screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l utilisateur dans l écran Mettre à jour les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introducido por el usuario en la pantalla Actualizar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário na tela Atualizar unidades de planejamento');-- pr




INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.DesiredStock','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Desired Months of Stock * Total Forecast Quantity) / Number of Months in Forecast Period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Mois de stock souhaités * Quantité totale prévue) / Nombre de mois dans la période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Meses deseados de existencias * Cantidad total prevista) / Número de meses en el período de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Meses de Estoque Desejados * Quantidade Total de Previsão) / Número de Meses no Período de Previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.ProcurementSurplusGap','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock or Unmet Demand - Desired Stock. If this amount is below zero, QAT calculates a Gap in procurement, which will appear in red text.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock ou demande non satisfaite - Stock souhaité. Si ce montant est inférieur à zéro, QAT calcule un écart dans l approvisionnement, qui apparaîtra en texte rouge.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock o Demanda Insatisfecha - Stock Deseado. Si este monto es inferior a cero, QAT calcula una brecha en la contratación, que aparecerá en texto rojo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque ou Demanda Insatisfeita - Estoque Desejado. Se esse valor estiver abaixo de zero, o QAT calcula um Gap nas compras, que aparecerá em texto vermelho.');-- pr



INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.forecastReportpriceType','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User-entered in the Update Planning Units screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisie par l utilisateur dans l écran Mettre à jour les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introducido por el usuario en la pantalla Actualizar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Inserido pelo usuário na tela Atualizar unidades de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.forecastReportUnitPrice','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Automatically calculated from the Price Type or User-entered in the Update Planning Units screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculé automatiquement à partir du type de prix ou saisi par l utilisateur dans l écran Mettre à jour les unités de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculado automáticamente a partir del tipo de precio o ingresado por el usuario en la pantalla Actualizar unidades de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculado automaticamente a partir do Tipo de preço ou inserido pelo usuário na tela Atualizar unidades de planejamento');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.Tooltip.ProcurementsNeeded','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If there is a Procurement Gap, QAT will calculate the amount (in USD) needed to cover that gap using the Unit Price.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'S il y a un écart d approvisionnement, QAT calculera le montant (en USD) nécessaire pour couvrir cet écart en utilisant le prix unitaire.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si hay una Brecha de Adquisiciones, QAT calculará la cantidad (en USD) necesaria para cubrir esa brecha usando el Precio Unitario.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se houver uma lacuna de aquisição, a QAT calculará o valor (em dólares) necessário para cobrir essa lacuna usando o preço unitário.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.StartValueModelingTool','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you wish to create multiple rows of modeling in which the ending value of the first row should be the start value of the second row and you are using the modeling calculator, you will need to click the update button before creating the second row, otherwise the start value will be the same as the first row');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous souhaitez créer plusieurs lignes de modélisation dans lesquelles la valeur de fin de la première ligne doit être la valeur de début de la deuxième ligne et que vous utilisez la calculatrice de modélisation, vous devrez cliquer sur le bouton de mise à jour avant de créer la deuxième ligne, sinon la valeur de départ sera la même que la première ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si desea crear varias filas de modelado en las que el valor final de la primera fila debe ser el valor inicial de la segunda fila y está utilizando la calculadora de modelado, deberá hacer clic en el botón Actualizar antes de crear la segunda fila, de lo contrario el valor inicial será el mismo que el de la primera fila');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você deseja criar várias linhas de modelagem nas quais o valor final da primeira linha deve ser o valor inicial da segunda linha e você está usando a calculadora de modelagem, você precisará clicar no botão atualizar antes de criar a segunda linha, caso contrário o valor inicial será o mesmo da primeira linha');-- pr

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_option_data` ADD COLUMN `CI` DECIMAL(18,4) NULL AFTER `AMOUNT`;
ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` ADD COLUMN `CI` DECIMAL(16,2) NULL AFTER `AMOUNT`;
DELETE fced.* FROM rm_forecast_consumption_extrapolation fce LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID WHERE em.ACTIVE=0;
DELETE fce.* FROM rm_forecast_consumption_extrapolation fce LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID WHERE em.ACTIVE=0;

SET FOREIGN_KEY_CHECKS=0;
DELETE l.*, em.* FROM ap_extrapolation_method em LEFT JOIN ap_label l ON em.LABEL_ID=l.LABEL_ID where em.ACTIVE=0;
SET FOREIGN_KEY_CHECKS=1;

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
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH,mn.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
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
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND pu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH=mn.MONTH
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH=mn.MONTH
        WHERE
            dpu.PROGRAM_ID=@programId
            AND dpu.VERSION_ID=@versionId
            AND FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIdList)
ORDER BY pu.PLANNING_UNIT_ID, r.REGION_ID, mn.MONTH;
    ELSEIF @reportView = 2 THEN
        SELECT
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
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
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH BETWEEN @startMonth AND @stopMonth
        LEFT JOIN rm_program_version pv ON pv.PROGRAM_ID=@programId and pv.VERSION_ID=@versionId
        WHERE
            dpu.PROGRAM_ID=@programId
            AND dpu.VERSION_ID=@versionId
            AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIdList)
            and mn.MONTH between pv.FORECAST_START_DATE and pv.FORECAST_STOP_DATE;
    END IF;
END$$

DELIMITER ;
;

