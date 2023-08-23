USE `fasp`;
DROP procedure IF EXISTS `getForecastSummary`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getForecastSummary`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getForecastSummary`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10))
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
        COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
        ROUND(SUM(COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT)),0) `TOTAL_FORECAST`, dpus.NOTES, pv.FREIGHT_PERC,
        dpu.STOCK, dpu.EXISTING_SHIPMENTS, dpu.MONTHS_OF_STOCK, dpu.PRICE,
        pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PA_LABEL_ID`, pa.LABEL_EN `PA_LABEL_EN`, pa.LABEL_FR `PA_LABEL_FR`, pa.LABEL_SP `PA_LABEL_SP`, pa.LABEL_PR `PA_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE
    FROM rm_dataset_planning_unit dpu 
    LEFT JOIN rm_program_version pv ON dpu.PROGRAM_ID=pv.PROGRAM_ID AND dpu.VERSION_ID=pv.VERSION_ID
    LEFT JOIN rm_program_region pr ON pr.PROGRAM_ID=dpu.PROGRAM_ID
    LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID AND pr.REGION_ID=dpus.REGION_ID
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
    ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND dpu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
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

