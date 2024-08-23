/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastNode;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.NodeDataOverride;
import cc.altius.FASP.model.TreeLevel;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeNodeData;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.model.rowMapper.TreeNodeResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeTemplateListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeTemplateResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akil
 */
@Repository
public class TreeTemplateDaoImpl implements TreeTemplateDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String treeTemplateSql = "SELECT  "
            + "    tt.TREE_TEMPLATE_ID, tt.LABEL_ID, tt.LABEL_EN, tt.LABEL_FR, tt.LABEL_SP, tt.LABEL_PR, tt.ACTIVE, tt.CREATED_DATE, tt.LAST_MODIFIED_DATE, tt.MONTHS_IN_PAST, tt.MONTHS_IN_FUTURE, tt.`NOTES`, "
            + "    nt.NODE_TYPE_ID,nt.LABEL_ID AS NODE_TYPE_LABEL_ID,nt.LABEL_EN AS `NODE_TYPE_LABEL_EN`,nt.LABEL_FR AS `NODE_TYPE_LABEL_FR`,nt.LABEL_SP AS `NODE_TYPE_LABEL_SP`,nt.LABEL_PR AS `NODE_TYPE_LABEL_PR`, "
            + "    tl.TREE_TEMPLATE_LEVEL_ID `LEVEL_ID`, tl.LEVEL_NO, tl.LABEL_ID `TL_LABEL_ID`, tl.LABEL_EN `TL_LABEL_EN`, tl.LABEL_FR `TL_LABEL_FR`, tl.LABEL_SP `TL_LABEL_SP`, tl.LABEL_PR `TL_LABEL_PR`, "
            + "    u.UNIT_ID, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, u.`UNIT_CODE`, "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `R_LABEL_ID`,  r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`, "
            + "    fm.FORECAST_METHOD_ID, fm.LABEL_ID `FM_LABEL_ID`, fm.LABEL_EN `FM_LABEL_EN`, fm.LABEL_FR `FM_LABEL_FR`, fm.LABEL_SP `FM_LABEL_SP`, fm.LABEL_PR `FM_LABEL_PR`, fm.FORECAST_METHOD_TYPE_ID, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` "
            + "FROM vw_tree_template tt  "
            + "LEFT JOIN rm_tree_template_node ttn on ttn.TREE_TEMPLATE_ID=tt.TREE_TEMPLATE_ID "
            + "LEFT JOIN vw_node_type nt on nt.NODE_TYPE_ID=ttn.NODE_TYPE_ID "
            + "LEFT JOIN vw_tree_template_level tl ON tt.TREE_TEMPLATE_ID=tl.TREE_TEMPLATE_ID "
            + "LEFT JOIN vw_unit u ON tl.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN vw_realm r ON tt.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_forecast_method fm ON tt.FORECAST_METHOD_ID=fm.FORECAST_METHOD_ID "
            + "LEFT JOIN us_user cb ON tt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON tt.LAST_MODIFIED_BY=lmb.USER_ID "
            + "where ttn.SORT_ORDER=0 ";

    @Override
    public List<TreeTemplate> getTreeTemplateList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlBuilder = new StringBuilder(treeTemplateSql);
        if (active) {
            sqlBuilder.append(" AND tt.ACTIVE ");
        }
        sqlBuilder.append(" ORDER BY tt.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), new TreeTemplateListResultSetExtractor());
    }

    // To change this query to match with the new method of extraction
    @Override
    public ForecastTree<TreeNode> getTree(int treeTemplateId) {
        String sql = "SELECT "
                + "          ttn.NODE_ID, ttn.TREE_TEMPLATE_ID, ttn.PARENT_NODE_ID, 0 `IS_EXTRAPOLATION`, ttn.COLLAPSED, "
                + "          ttn.LABEL_ID, ttn.LABEL_EN, ttn.LABEL_FR, ttn.LABEL_SP, ttn.LABEL_PR, "
                + "          nt.NODE_TYPE_ID `NODE_TYPE_ID`, nt.MODELING_ALLOWED, nt.EXTRAPOLATION_ALLOWED, nt.TREE_TEMPLATE_ALLOWED, nt.FORECAST_TREE_ALLOWED, nt.LABEL_ID `NT_LABEL_ID`, nt.LABEL_EN `NT_LABEL_EN`, nt.LABEL_FR `NT_LABEL_FR`, nt.LABEL_SP `NT_LABEL_SP`, nt.LABEL_PR `NT_LABEL_PR`, "
                + "          u.UNIT_ID `U_UNIT_ID`, u.UNIT_CODE `U_UNIT_CODE`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "          0 `SCENARIO_ID`, ttnd.NODE_DATA_ID, ttnd.MONTH, ttnd.DATA_VALUE, ttnd.NOTES, ttnd.MANUAL_CHANGES_EFFECT_FUTURE, "
                + "          ttndf.NODE_DATA_FU_ID, ttndf.LAG_IN_MONTHS, ttndf.NO_OF_PERSONS, ttndf.FORECASTING_UNITS_PER_PERSON, "
                + "          fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "          fuu.UNIT_ID `FUU_UNIT_ID`, fuu.UNIT_CODE `FUU_UNIT_CODE`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, "
                + "          tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
                + "          pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, "
                + "          ut.USAGE_TYPE_ID, ut.LABEL_ID `UT_LABEL_ID`, ut.LABEL_EN `UT_LABEL_EN`, ut.LABEL_FR `UT_LABEL_FR`, ut.LABEL_SP `UT_LABEL_SP`, ut.LABEL_PR `UT_LABEL_PR`, "
                + "          ttndf.ONE_TIME_USAGE, ttndf.ONE_TIME_DISPENSING, ttndf.USAGE_FREQUENCY, upf.USAGE_PERIOD_ID `UPF_USAGE_PERIOD_ID`, upf.CONVERT_TO_MONTH `UPF_CONVERT_TO_MONTH`, upf.LABEL_ID `UPF_LABEL_ID`, upf.LABEL_EN `UPF_LABEL_EN`, upf.LABEL_FR `UPF_LABEL_FR`, upf.LABEL_SP `UPF_LABEL_SP`, upf.LABEL_PR `UPF_LABEL_PR`, "
                + "          ttndf.REPEAT_COUNT, upr.USAGE_PERIOD_ID `UPR_USAGE_PERIOD_ID`, upr.CONVERT_TO_MONTH `UPR_CONVERT_TO_MONTH`, upr.LABEL_ID `UPR_LABEL_ID`, upr.LABEL_EN `UPR_LABEL_EN`, upr.LABEL_FR `UPR_LABEL_FR`, upr.LABEL_SP `UPR_LABEL_SP`, upr.LABEL_PR `UPR_LABEL_PR`, "
                + "          ttndp.NODE_DATA_PU_ID, ttndp.REFILL_MONTHS, ttndp.PU_PER_VISIT, ttndp.SHARE_PLANNING_UNIT, "
                + "          pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER `PU_MULTIPLIER`, "
                + "          puu.UNIT_ID `PUU_UNIT_ID`, puu.UNIT_CODE `PUU_UNIT_CODE`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR` "
                + "      FROM vw_tree_template_node ttn "
                + "      LEFT JOIN vw_node_type nt ON ttn.NODE_TYPE_ID=nt.NODE_TYPE_ID "
                + "      LEFT JOIN vw_unit u ON ttn.UNIT_ID=u.UNIT_ID "
                + "      LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                + "      LEFT JOIN rm_tree_template_node_data_fu ttndf on ttndf.NODE_DATA_FU_ID=ttnd.NODE_DATA_FU_ID "
                + "      LEFT JOIN vw_forecasting_unit fu ON ttndf.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "      LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
                + "      LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "      LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "      LEFT JOIN vw_usage_type ut ON ttndf.USAGE_TYPE_ID=ut.USAGE_TYPE_ID "
                + "      LEFT JOIN vw_usage_period upf ON ttndf.USAGE_FREQUENCY_USAGE_PERIOD_ID=upf.USAGE_PERIOD_ID "
                + "      LEFT JOIN vw_usage_period upr ON ttndf.REPEAT_USAGE_PERIOD_ID=upr.USAGE_PERIOD_ID "
                + "      LEFT JOIN rm_tree_template_node_data_pu ttndp ON ttndp.NODE_DATA_PU_ID=ttnd.NODE_DATA_PU_ID "
                + "      LEFT JOIN vw_planning_unit pu ON ttndp.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "      LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID "
                + "      WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId "
                + "      ORDER BY ttn.SORT_ORDER, ttnd.NODE_DATA_ID";
        Map<String, Object> params = new HashMap<>();
        params.put("treeTemplateId", treeTemplateId);
        return this.namedParameterJdbcTemplate.query(sql, params, new TreeNodeResultSetExtractor(true));
    }

    @Override
    public Map<String, Object> getConsumption(int treeTemplateId) {
        return null;
    }

    @Override
    public TreeTemplate getTreeTemplateById(int treeTemplateId, CustomUserDetails curUser) {
        String sql = treeTemplateSql + "AND tt.TREE_TEMPLATE_ID=:treeTemplateId ORDER BY tt.LABEL_EN";
        Map<String, Object> params = new HashMap<>();
        params.put("treeTemplateId", treeTemplateId);
        return this.namedParameterJdbcTemplate.query(sql, params, new TreeTemplateResultSetExtractor());
    }

    @Override
    @Transactional
    public int addTreeTemplate(TreeTemplate tt, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template").usingGeneratedKeyColumns("TREE_TEMPLATE_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(tt.getLabel(), LabelConstants.RM_FORECAST_TREE_TEMPLATE, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("FORECAST_METHOD_ID", tt.getForecastMethod().getId());
        params.put("MONTHS_IN_PAST", tt.getMonthsInPast());
        params.put("MONTHS_IN_FUTURE", tt.getMonthsInFuture());
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", 1);
        params.put("NOTES", tt.getNotes());
        int treeTemplateId = si.executeAndReturnKey(params).intValue();
        SimpleJdbcInsert ttl = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_level").usingGeneratedKeyColumns("TREE_TEMPLATE_LEVEL_ID");
        SimpleJdbcInsert ni = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node").usingGeneratedKeyColumns("TREE_TEMPLATE_NODE_ID");
        SimpleJdbcInsert nid = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data").usingGeneratedKeyColumns("NODE_DATA_ID");
        SimpleJdbcInsert nidf = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_fu").usingGeneratedKeyColumns("NODE_DATA_FU_ID");
        SimpleJdbcInsert nidp = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_pu").usingGeneratedKeyColumns("NODE_DATA_PU_ID");
        SimpleJdbcInsert nidm = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_modeling").usingGeneratedKeyColumns("NODE_DATA_MODELING_ID");
        SimpleJdbcInsert nidatc = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_annual_target_calculator").usingGeneratedKeyColumns("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID");
        SimpleJdbcInsert nidatcd = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_annual_target_calculator_data");
        SimpleJdbcInsert nido = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_override");
        final List<SqlParameterSource> batchList = new ArrayList<>();
        Map<Integer, Integer> nodeDataIdMap = new HashMap<>();
        for (TreeLevel tl : tt.getLevelList()) {
            params.put("TREE_TEMPLATE_ID", treeTemplateId);
            int treeLabelId = this.labelDao.addLabel(tl.getLabel(), LabelConstants.RM_TREE_TEMPLATE_LEVEL, curUser.getUserId());
            params.put("LABEL_ID", treeLabelId);
            params.put("LEVEL_NO", tl.getLevelNo());
            params.put("UNIT_ID", (tl.getUnit() == null || tl.getUnit().getId() == null || tl.getUnit().getId() == 0 ? null : tl.getUnit().getId()));
            ttl.executeAndReturnKey(params);
        }
        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            Map<String, Object> nodeParams = new HashMap<>();
            nodeParams.put("TREE_TEMPLATE_ID", treeTemplateId);
            nodeParams.put("SORT_ORDER", n.getSortOrder());
            nodeParams.put("LEVEL_NO", n.getLevel() + 1);
            nodeParams.put("COLLAPSED", n.getPayload().isCollapsed());
            nodeParams.put("NODE_TYPE_ID", n.getPayload().getNodeType().getId());
            nodeParams.put("UNIT_ID", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == null || n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
            int nodeLabelId = this.labelDao.addLabel(n.getPayload().getLabel(), LabelConstants.RM_FORECAST_TREE_TEMPLATE_NODE, curUser.getUserId());
            nodeParams.put("LABEL_ID", nodeLabelId);
            nodeParams.put("CREATED_BY", curUser.getUserId());
            nodeParams.put("CREATED_DATE", curDate);
            nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
            nodeParams.put("LAST_MODIFIED_DATE", curDate);
            nodeParams.put("ACTIVE", 1);
            int nodeId = ni.executeAndReturnKey(nodeParams).intValue();
            nodeParams.clear();
            if (n.getPayload().getNodeDataMap().get(0) != null) {
                TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                if (tnd != null) {
                    nodeParams.put("NODE_ID", nodeId);
                    nodeParams.put("MONTH", tnd.getMonthNo());
                    nodeParams.put("DATA_VALUE", tnd.getDataValue());
                    nodeParams.put("MANUAL_CHANGES_EFFECT_FUTURE", tnd.isManualChangesEffectFuture());
                    nodeParams.put("NOTES", tnd.getNotes());
                    nodeParams.put("CREATED_BY", curUser.getUserId());
                    nodeParams.put("CREATED_DATE", curDate);
                    nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                    nodeParams.put("LAST_MODIFIED_DATE", curDate);
                    nodeParams.put("ACTIVE", 1);
                    int nodeDataId = nid.executeAndReturnKey(nodeParams).intValue();
                    nodeDataIdMap.put(tnd.getNodeDataId(), nodeDataId);
                    nodeParams.clear();
                    if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { // ForecastingUnitNode
                        nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                        nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                        nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                        nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                        nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                        if (tnd.getFuNode().getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_DISCRETE) {
                            // Discrete
                            nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                            nodeParams.put("ONE_TIME_DISPENSING",tnd.getFuNode().getOneTimeDispensing()==null?true:tnd.getFuNode().getOneTimeDispensing());
                            if (!tnd.getFuNode().isOneTimeUsage()) {
                                nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                            }
                        } else {
                            // Continuous
                            nodeParams.put("ONE_TIME_DISPENSING",true);
                            nodeParams.put("ONE_TIME_USAGE", 0); // Always false
                            nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                            nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                        }
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        int nodeFuId = nidf.executeAndReturnKey(nodeParams).intValue();
                        nodeParams.clear();
                        nodeParams.put("nodeFuId", nodeFuId);
                        nodeParams.put("nodeDataId", nodeDataId);
                        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_FU_ID=:nodeFuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                    } else if (tnd.getPuNode() != null && n.getPayload().getNodeType().getId() == 5) { // PlanningUnit Node
                        nodeParams.put("PLANNING_UNIT_ID", tnd.getPuNode().getPlanningUnit().getId());
                        nodeParams.put("SHARE_PLANNING_UNIT", tnd.getPuNode().isSharePlanningUnit());
                        nodeParams.put("REFILL_MONTHS", tnd.getPuNode().getRefillMonths());
                        nodeParams.put("PU_PER_VISIT", tnd.getPuNode().getPuPerVisit());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        int nodePuId = nidp.executeAndReturnKey(nodeParams).intValue();
                        nodeParams.clear();
                        nodeParams.put("nodePuId", nodePuId);
                        nodeParams.put("nodeDataId", nodeDataId);
                        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_PU_ID=:nodePuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                    }
                    for (NodeDataOverride ndo : tnd.getNodeDataOverrideList()) {
                        nodeParams.clear();
                        nodeParams.put("NODE_DATA_ID", nodeDataId);
                        nodeParams.put("MONTH_NO", ndo.getMonthNo());
                        nodeParams.put("MANUAL_CHANGE", ndo.getManualChange());
                        nodeParams.put("SEASONALITY_PERC", ndo.getSeasonalityPerc());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        nido.execute(nodeParams);
                    }
                }
            }
        }

        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            Map<String, Object> nodeParams = new HashMap<>();
            if (n.getPayload().getNodeDataMap().get(0) != null) {
                TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                if (tnd != null) {
                    for (NodeDataModeling ndm : tnd.getNodeDataModelingList()) {
                        nodeParams.clear();
                        nodeParams.put("NODE_DATA_ID", nodeDataIdMap.get(tnd.getNodeDataId()));
                        nodeParams.put("START_DATE", ndm.getStartDateNo());
                        nodeParams.put("STOP_DATE", ndm.getStopDateNo());
                        nodeParams.put("MODELING_TYPE_ID", ndm.getModelingType().getId());
                        nodeParams.put("DATA_VALUE", ndm.getDataValue());
                        nodeParams.put("INCREASE_DECREASE", ndm.getIncreaseDecrease());
                        nodeParams.put("TRANSFER_NODE_DATA_ID", nodeDataIdMap.get(ndm.getTransferNodeDataId()));
                        nodeParams.put("NOTES", ndm.getNotes());
                        nodeParams.put("MODELING_SOURCE", ndm.getModelingSource());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        ndm.setNodeDataModelingId(nidm.executeAndReturnKey(nodeParams).intValue());
                    }
                    if (tnd.getAnnualTargetCalculator() != null && !tnd.getAnnualTargetCalculator().getActualOrTargetValueList().isEmpty()) {
                        nodeParams.clear();
                        nodeParams.put("NODE_DATA_ID", nodeDataIdMap.get(tnd.getNodeDataId()));
                        nodeParams.put("CALCULATOR_FIRST_MONTH", tnd.getAnnualTargetCalculator().getFirstMonthOfTarget());
                        nodeParams.put("CALCULATOR_YEARS_OF_TARGET", tnd.getAnnualTargetCalculator().getYearsOfTarget());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
//                        tnd.getAnnualTargetCalculator().setNodeDataAnnualTargetCalculatorId(nidatc.executeAndReturnKey(nodeParams).intValue());
                        int nodeDataAnnualTargetCalculatorId = nidatc.executeAndReturnKey(nodeParams).intValue();

                        batchList.clear();
                        for (int actualOrTargetValue : tnd.getAnnualTargetCalculator().getActualOrTargetValueList()) {
                            Map<String, Object> batchParams = new HashMap<>();
                            batchParams.put("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID", nodeDataAnnualTargetCalculatorId);
                            batchParams.put("ACTUAL_OR_TARGET_VALUE", actualOrTargetValue);
                            batchList.add(new MapSqlParameterSource(batchParams));
                        }
                        SqlParameterSource[] batchArray = new SqlParameterSource[batchList.size()];
                        nidatcd.executeBatch(batchList.toArray(batchArray));
                    }
                }
            }
        }
        params.clear();
        params.put("treeTemplateId", treeTemplateId);
        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node ttn LEFT JOIN rm_tree_template_node ttn2 ON ttn.TREE_TEMPLATE_ID=ttn2.TREE_TEMPLATE_ID AND left(ttn.SORT_ORDER, length(ttn.SORT_ORDER)-3)=ttn2.SORT_ORDER SET ttn.PARENT_NODE_ID=ttn2.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        return treeTemplateId;
    }

    @Override
    @Transactional
    public int updateTreeTemplate(TreeTemplate tt, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        String sql = "UPDATE rm_tree_template tt LEFT JOIN ap_label l ON l.LABEL_ID=tt.LABEL_ID "
                + "SET "
                + "l.LABEL_EN=:labelEn, "
                + "tt.FORECAST_METHOD_ID=:forecastMethod, "
                + "tt.MONTHS_IN_PAST=:monthsInPast, "
                + "tt.MONTHS_IN_FUTURE=:monthsInFuture, "
                + "tt.LAST_MODIFIED_BY=:curUser, "
                + "tt.LAST_MODIFIED_DATE=:curDate, "
                + "tt.ACTIVE=:active, "
                + "tt.NOTES=:notes "
                + "WHERE tt.TREE_TEMPLATE_ID=:treeTemplateId";
        params.put("labelEn", tt.getLabel().getLabel_en());
        params.put("forecastMethod", tt.getForecastMethod().getId());
        params.put("monthsInPast", tt.getMonthsInPast());
        params.put("monthsInFuture", tt.getMonthsInFuture());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("active", tt.isActive());
        params.put("notes", tt.getNotes());
        params.put("treeTemplateId", tt.getTreeTemplateId());
        int treeTemplateId = tt.getTreeTemplateId();
        this.namedParameterJdbcTemplate.update(sql, params);
        params.clear();
        params.put("treeTemplateId", tt.getTreeTemplateId());
        this.namedParameterJdbcTemplate.update("DELETE ttndm.* FROM rm_tree_template_node_data_modeling ttndm LEFT JOIN rm_tree_template_node_data ttnd ON ttndm.NODE_DATA_ID=ttnd.NODE_DATA_ID LEFT JOIN rm_tree_template_node ttn ON ttnd.NODE_ID=ttn.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        this.namedParameterJdbcTemplate.update("DELETE ttndo.* FROM rm_tree_template_node_data_override ttndo LEFT JOIN rm_tree_template_node_data ttnd ON ttndo.NODE_DATA_ID=ttnd.NODE_DATA_ID LEFT JOIN rm_tree_template_node ttn ON ttnd.NODE_ID=ttn.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        this.namedParameterJdbcTemplate.update("DELETE ttndatcd.*  FROM rm_tree_template_node_data_annual_target_calculator_data ttndatcd LEFT JOIN rm_tree_template_node_data_annual_target_calculator ttndatc ON ttndatcd.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID=ttndatc.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID LEFT JOIN rm_tree_template_node_data ttnd ON ttndatc.NODE_DATA_ID=ttnd.NODE_DATA_ID LEFT JOIN rm_tree_template_node ttn ON ttnd.NODE_ID=ttn.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        this.namedParameterJdbcTemplate.update("DELETE ttndatc.*  FROM rm_tree_template_node_data_annual_target_calculator ttndatc LEFT JOIN rm_tree_template_node_data_annual_target_calculator_data ttndatcd ON ttndatcd.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID=ttndatc.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID LEFT JOIN rm_tree_template_node_data ttnd ON ttndatc.NODE_DATA_ID=ttnd.NODE_DATA_ID LEFT JOIN rm_tree_template_node ttn ON ttnd.NODE_ID=ttn.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        this.namedParameterJdbcTemplate.update("DELETE ttnd.* FROM rm_tree_template_node_data ttnd LEFT JOIN rm_tree_template_node ttn ON ttnd.NODE_ID=ttn.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        this.namedParameterJdbcTemplate.update("DELETE ttndp.* FROM rm_tree_template_node_data_pu ttndp LEFT JOIN rm_tree_template_node_data ttnd ON ttndp.NODE_DATA_PU_ID=ttnd.NODE_DATA_PU_ID WHERE ttnd.NODE_DATA_PU_ID IS NULL", params);
        this.namedParameterJdbcTemplate.update("DELETE ttndf.* FROM rm_tree_template_node_data_fu ttndf LEFT JOIN rm_tree_template_node_data ttnd ON ttndf.NODE_DATA_FU_ID=ttnd.NODE_DATA_FU_ID WHERE ttnd.NODE_DATA_FU_ID IS NULL", params);
        this.namedParameterJdbcTemplate.update("DELETE ttl.* FROM rm_tree_template_level ttl WHERE ttl.TREE_TEMPLATE_ID=:treeTemplateId", params);
        List<Integer> levelList = this.namedParameterJdbcTemplate.queryForList("SELECT LEVEL_NO FROM rm_tree_template_node ttn WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId GROUP BY LEVEL_NO ORDER BY LEVEL_NO DESC", params, Integer.class);
        params.put("levelNo", 0);
        for (int l : levelList) {
            params.replace("levelNo", l);
            this.namedParameterJdbcTemplate.update("DELETE ttn.* FROM rm_tree_template_node ttn WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId AND ttn.LEVEL_NO=:levelNo", params);
        }

        SimpleJdbcInsert ttl = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_level").usingGeneratedKeyColumns("TREE_TEMPLATE_LEVEL_ID");
        SimpleJdbcInsert ni = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node").usingGeneratedKeyColumns("TREE_TEMPLATE_NODE_ID");
        SimpleJdbcInsert nid = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data").usingGeneratedKeyColumns("NODE_DATA_ID");
        SimpleJdbcInsert nidf = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_fu").usingGeneratedKeyColumns("NODE_DATA_FU_ID");
        SimpleJdbcInsert nidp = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_pu").usingGeneratedKeyColumns("NODE_DATA_PU_ID");
        SimpleJdbcInsert nidm = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_modeling");
        SimpleJdbcInsert nido = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_override");
        SimpleJdbcInsert nidatc = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_annual_target_calculator").usingGeneratedKeyColumns("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID");
        SimpleJdbcInsert nidatcd = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_annual_target_calculator_data");
        Map<Integer, Integer> nodeDataIdMap = new HashMap<>();
        for (TreeLevel tl : tt.getLevelList()) {
            params.put("TREE_TEMPLATE_ID", treeTemplateId);
            int treeLabelId = this.labelDao.addLabel(tl.getLabel(), LabelConstants.RM_TREE_TEMPLATE_LEVEL, curUser.getUserId());
            params.put("LABEL_ID", treeLabelId);
            params.put("LEVEL_NO", tl.getLevelNo());
            params.put("UNIT_ID", (tl.getUnit() == null || tl.getUnit().getId() == null || tl.getUnit().getId() == 0 ? null : tl.getUnit().getId()));
            ttl.executeAndReturnKey(params);
        }
        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            Map<String, Object> nodeParams = new HashMap<>();
            nodeParams.put("TREE_TEMPLATE_ID", treeTemplateId);
            nodeParams.put("SORT_ORDER", n.getSortOrder());
            nodeParams.put("LEVEL_NO", n.getLevel() + 1);
            nodeParams.put("COLLAPSED", n.getPayload().isCollapsed());
            nodeParams.put("NODE_TYPE_ID", n.getPayload().getNodeType().getId());
            nodeParams.put("UNIT_ID", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == null || n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
            int nodeLabelId = this.labelDao.addLabel(n.getPayload().getLabel(), LabelConstants.RM_FORECAST_TREE_TEMPLATE_NODE, curUser.getUserId());
            nodeParams.put("LABEL_ID", nodeLabelId);
            nodeParams.put("CREATED_BY", curUser.getUserId());
            nodeParams.put("CREATED_DATE", curDate);
            nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
            nodeParams.put("LAST_MODIFIED_DATE", curDate);
            nodeParams.put("ACTIVE", 1);
            int nodeId = ni.executeAndReturnKey(nodeParams).intValue();
            nodeParams.clear();
            if (n.getPayload().getNodeDataMap().get(0) != null) {
                TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                if (tnd != null) {
                    nodeParams.put("NODE_ID", nodeId);
                    nodeParams.put("MONTH", tnd.getMonthNo());
                    nodeParams.put("DATA_VALUE", tnd.getDataValue());
                    nodeParams.put("NOTES", tnd.getNotes());
                    nodeParams.put("MANUAL_CHANGES_EFFECT_FUTURE", tnd.isManualChangesEffectFuture());
                    nodeParams.put("CREATED_BY", curUser.getUserId());
                    nodeParams.put("CREATED_DATE", curDate);
                    nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                    nodeParams.put("LAST_MODIFIED_DATE", curDate);
                    nodeParams.put("ACTIVE", 1);
                    int nodeDataId = nid.executeAndReturnKey(nodeParams).intValue();
                    nodeDataIdMap.put(tnd.getNodeDataId(), nodeDataId);
                    nodeParams.clear();
                    if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { // ForecastingUnitNode
                        nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                        nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                        nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                        nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                        nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                        if (tnd.getFuNode().getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_DISCRETE) {
                            // Discrete
                            nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                            nodeParams.put("ONE_TIME_DISPENSING",tnd.getFuNode().getOneTimeDispensing()==null?true:tnd.getFuNode().getOneTimeDispensing());
                            if (!tnd.getFuNode().isOneTimeUsage()) {
                                nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                            }
                        } else {
                            // Continuous
                            nodeParams.put("ONE_TIME_DISPENSING",true);
                            nodeParams.put("ONE_TIME_USAGE", 0); // Always false
                            nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                            nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                        }
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        int nodeFuId = nidf.executeAndReturnKey(nodeParams).intValue();
                        nodeParams.clear();
                        nodeParams.put("nodeFuId", nodeFuId);
                        nodeParams.put("nodeDataId", nodeDataId);
                        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_FU_ID=:nodeFuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                    } else if (tnd.getPuNode() != null && n.getPayload().getNodeType().getId() == 5) { // PlanningUnit Node
                        nodeParams.put("PLANNING_UNIT_ID", tnd.getPuNode().getPlanningUnit().getId());
                        nodeParams.put("SHARE_PLANNING_UNIT", tnd.getPuNode().isSharePlanningUnit());
                        nodeParams.put("REFILL_MONTHS", tnd.getPuNode().getRefillMonths());
                        nodeParams.put("PU_PER_VISIT", tnd.getPuNode().getPuPerVisit());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        int nodePuId = nidp.executeAndReturnKey(nodeParams).intValue();
                        nodeParams.clear();
                        nodeParams.put("nodePuId", nodePuId);
                        nodeParams.put("nodeDataId", nodeDataId);
                        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_PU_ID=:nodePuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                    }
                    for (NodeDataOverride ndo : tnd.getNodeDataOverrideList()) {
                        nodeParams.clear();
                        nodeParams.put("NODE_DATA_ID", nodeDataId);
                        nodeParams.put("MONTH_NO", ndo.getMonthNo());
                        nodeParams.put("MANUAL_CHANGE", ndo.getManualChange());
                        nodeParams.put("SEASONALITY_PERC", ndo.getSeasonalityPerc());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        nido.execute(nodeParams);
                    }
                }
            }
        }
        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            Map<String, Object> nodeParams = new HashMap<>();
            if (n.getPayload().getNodeDataMap().get(0) != null) {
                TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                if (tnd != null) {
                    for (NodeDataModeling ndm : tnd.getNodeDataModelingList()) {
                        nodeParams.clear();
                        nodeParams.put("NODE_DATA_ID", nodeDataIdMap.get(tnd.getNodeDataId()));
                        nodeParams.put("START_DATE", ndm.getStartDateNo());
                        nodeParams.put("STOP_DATE", ndm.getStopDateNo());
                        nodeParams.put("MODELING_TYPE_ID", ndm.getModelingType().getId());
                        nodeParams.put("DATA_VALUE", ndm.getDataValue());
                        nodeParams.put("INCREASE_DECREASE", ndm.getIncreaseDecrease());
                        nodeParams.put("TRANSFER_NODE_DATA_ID", nodeDataIdMap.get(ndm.getTransferNodeDataId()));
                        nodeParams.put("NOTES", ndm.getNotes());
                        nodeParams.put("MODELING_SOURCE", ndm.getModelingSource());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        nidm.execute(nodeParams);
                    }
                }
                if (tnd.getAnnualTargetCalculator() != null && !tnd.getAnnualTargetCalculator().getActualOrTargetValueList().isEmpty()) {
                    nodeParams.clear();
                    nodeParams.put("NODE_DATA_ID", nodeDataIdMap.get(tnd.getNodeDataId()));
                    nodeParams.put("CALCULATOR_FIRST_MONTH", tnd.getAnnualTargetCalculator().getFirstMonthOfTarget());
                    nodeParams.put("CALCULATOR_YEARS_OF_TARGET", tnd.getAnnualTargetCalculator().getYearsOfTarget());
                    nodeParams.put("CREATED_BY", curUser.getUserId());
                    nodeParams.put("CREATED_DATE", curDate);
                    nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                    nodeParams.put("LAST_MODIFIED_DATE", curDate);
//                    tnd.getAnnualTargetCalculator().setNodeDataAnnualTargetCalculatorId(nidatc.executeAndReturnKey(nodeParams).intValue());
                    int atcId = nidatc.executeAndReturnKey(nodeParams).intValue();
                    for (int actualOrTargetValue : tnd.getAnnualTargetCalculator().getActualOrTargetValueList()) {
                        Map<String, Object> batchParams = new HashMap<>();
                        batchParams.put("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID", atcId);
                        batchParams.put("ACTUAL_OR_TARGET_VALUE", actualOrTargetValue);
                        nidatcd.execute(batchParams);
                    }
                }
            }
        }
        params.clear();
        params.put("treeTemplateId", treeTemplateId);
        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node ttn LEFT JOIN rm_tree_template_node ttn2 ON ttn.TREE_TEMPLATE_ID=ttn2.TREE_TEMPLATE_ID AND left(ttn.SORT_ORDER, length(ttn.SORT_ORDER)-3)=ttn2.SORT_ORDER SET ttn.PARENT_NODE_ID=ttn2.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        return treeTemplateId;
    }

    @Override
    public List<TreeTemplate> getTreeTemplateListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sql = treeTemplateSql + " AND tt.LAST_MODIFIED_DATE>=:lastSyncDate ORDER BY tt.LABEL_EN";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sql, params, new TreeTemplateListResultSetExtractor());
    }

}
