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
END