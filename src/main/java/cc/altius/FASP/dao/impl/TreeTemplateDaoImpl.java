/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastNode;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeNodeData;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.model.rowMapper.TreeNodeResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeTemplateRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private static final String treeTemplateSql = "SELECT  "
            + "    tt.TREE_TEMPLATE_ID, tt.LABEL_ID, tt.LABEL_EN, tt.LABEL_FR, tt.LABEL_SP, tt.LABEL_PR, tt.ACTIVE, tt.CREATED_DATE, tt.LAST_MODIFIED_DATE, "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `R_LABEL_ID`,  r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`, "
            + "    fm.FORECAST_METHOD_ID, fm.LABEL_ID `FM_LABEL_ID`, fm.LABEL_EN `FM_LABEL_EN`, fm.LABEL_FR `FM_LABEL_FR`, fm.LABEL_SP `FM_LABEL_SP`, fm.LABEL_PR `FM_LABEL_PR`, fm.FORECAST_METHOD_TYPE_ID, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` "
            + "FROM vw_tree_template tt  "
            + "LEFT JOIN vw_realm r ON tt.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_forecast_method fm ON tt.FORECAST_METHOD_ID=fm.FORECAST_METHOD_ID "
            + "LEFT JOIN us_user cb ON tt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON tt.LAST_MODIFIED_BY=lmb.USER_ID ";

    @Override
    public List<TreeTemplate> getTreeTemplateList(CustomUserDetails curUser) {
        String sql = treeTemplateSql + "ORDER BY tt.LABEL_EN";
        return this.namedParameterJdbcTemplate.query(sql, new TreeTemplateRowMapper());
    }

    @Override
    public ForecastTree<TreeNode> getTree(int treeTemplateId) {
        String sql = "SELECT "
                + "          ttn.NODE_ID, ttn.TREE_TEMPLATE_ID, ttn.PARENT_NODE_ID, ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS, "
                + "          ttn.LABEL_ID, ttn.LABEL_EN, ttn.LABEL_FR, ttn.LABEL_SP, ttn.LABEL_PR, "
                + "          nt.NODE_TYPE_ID `NODE_TYPE_ID`, nt.LABEL_ID `NT_LABEL_ID`, nt.LABEL_EN `NT_LABEL_EN`, nt.LABEL_FR `NT_LABEL_FR`, nt.LABEL_SP `NT_LABEL_SP`, nt.LABEL_PR `NT_LABEL_PR`, "
                + "          u.UNIT_ID `U_UNIT_ID`, u.UNIT_CODE `U_UNIT_CODE`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "          0 `SCENARIO_ID`, ttnd.NODE_DATA_ID, ttnd.MONTH, ttnd.DATA_VALUE, ttnd.NOTES, "
                + "          ttndf.NODE_DATA_FU_ID, ttndf.LAG_IN_MONTHS, ttndf.NO_OF_PERSONS, ttndf.FORECASTING_UNITS_PER_PERSON, "
                + "          fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "          fuu.UNIT_ID `FUU_UNIT_ID`, fuu.UNIT_CODE `FUU_UNIT_CODE`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, "
                + "          tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
                + "          ut.USAGE_TYPE_ID, ut.LABEL_ID `UT_LABEL_ID`, ut.LABEL_EN `UT_LABEL_EN`, ut.LABEL_FR `UT_LABEL_FR`, ut.LABEL_SP `UT_LABEL_SP`, ut.LABEL_PR `UT_LABEL_PR`, "
                + "          ttndf.ONE_TIME_USAGE, ttndf.USAGE_FREQUENCY, upf.USAGE_PERIOD_ID `UPF_USAGE_PERIOD_ID`, upf.CONVERT_TO_MONTH `UPF_CONVERT_TO_MONTH`, upf.LABEL_ID `UPF_LABEL_ID`, upf.LABEL_EN `UPF_LABEL_EN`, upf.LABEL_FR `UPF_LABEL_FR`, upf.LABEL_SP `UPF_LABEL_SP`, upf.LABEL_PR `UPF_LABEL_PR`, "
                + "          ttndf.REPEAT_COUNT, upr.USAGE_PERIOD_ID `UPR_USAGE_PERIOD_ID`, upr.CONVERT_TO_MONTH `UPR_CONVERT_TO_MONTH`, upr.LABEL_ID `UPR_LABEL_ID`, upr.LABEL_EN `UPR_LABEL_EN`, upr.LABEL_FR `UPR_LABEL_FR`, upr.LABEL_SP `UPR_LABEL_SP`, upr.LABEL_PR `UPR_LABEL_PR`, "
                + "          ttndp.NODE_DATA_PU_ID, ttndp.REFILL_MONTHS, ttndp.SHARE_PLANNING_UNIT, "
                + "          pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "          puu.UNIT_ID `PUU_UNIT_ID`, puu.UNIT_CODE `PUU_UNIT_CODE`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR` "
                + "      FROM vw_tree_template_node ttn "
                + "      LEFT JOIN vw_node_type nt ON ttn.NODE_TYPE_ID=nt.NODE_TYPE_ID "
                + "      LEFT JOIN vw_unit u ON ttn.UNIT_ID=u.UNIT_ID "
                + "      LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                + "      LEFT JOIN rm_tree_template_node_data_fu ttndf on ttndf.NODE_DATA_FU_ID=ttnd.NODE_DATA_FU_ID "
                + "      LEFT JOIN vw_forecasting_unit fu ON ttndf.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "      LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
                + "      LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
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
        return this.namedParameterJdbcTemplate.query(sql, params, new TreeNodeResultSetExtractor());
    }

    @Override
    public Map<String, Object> getConsumption(int treeTemplateId) {
        return null;
    }

    @Override
    public TreeTemplate getTreeTemplateById(int treeTemplateId, CustomUserDetails curUser) {
        String sql = treeTemplateSql + "WHERE tt.TREE_TEMPLATE_ID=:treeTemplateId ORDER BY tt.LABEL_EN";
        Map<String, Object> params = new HashMap<>();
        params.put("treeTemplateId", treeTemplateId);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new TreeTemplateRowMapper());
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
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", 1);
        int treeTemplateId = si.executeAndReturnKey(params).intValue();
        SimpleJdbcInsert ni = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node").usingGeneratedKeyColumns("TREE_TEMPLATE_NODE_ID");
        SimpleJdbcInsert nid = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data").usingGeneratedKeyColumns("NODE_DATA_ID");
        SimpleJdbcInsert nidf = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_fu").usingGeneratedKeyColumns("NODE_DATA_FU_ID");
        SimpleJdbcInsert nidp = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_pu").usingGeneratedKeyColumns("NODE_DATA_PU_ID");
        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            Map<String, Object> nodeParams = new HashMap<>();
            nodeParams.put("TREE_TEMPLATE_ID", treeTemplateId);
            nodeParams.put("SORT_ORDER", n.getSortOrder());
            nodeParams.put("LEVEL_NO", n.getLevel() + 1);
            nodeParams.put("NODE_TYPE_ID", n.getPayload().getNodeType().getId());
            nodeParams.put("UNIT_ID", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == null || n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
            nodeParams.put("MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS", n.getPayload().isManualChangeEffectsFutureMonths());
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
                    nodeParams.put("MONTH", tnd.getMonth());
                    nodeParams.put("DATA_VALUE", tnd.getDataValue());
                    nodeParams.put("NOTES", tnd.getNotes());
                    nodeParams.put("CREATED_BY", curUser.getUserId());
                    nodeParams.put("CREATED_DATE", curDate);
                    nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                    nodeParams.put("LAST_MODIFIED_DATE", curDate);
                    nodeParams.put("ACTIVE", 1);
                    int nodeDataId = nid.executeAndReturnKey(nodeParams).intValue();
                    nodeParams.clear();
                    if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { // ForecastingUnitNode
                        nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                        nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                        nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                        nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                        nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                        if (tnd.getFuNode().getUsageType().getId() == 2) {
                            // Discrete
                            nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                            if (!tnd.getFuNode().isOneTimeUsage()) {
                                nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                            } else {
                                // Continuous
                                nodeParams.put("ONE_TIME_USAGE", 0);
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
                        }
                    } else if (tnd.getPuNode() != null && n.getPayload().getNodeType().getId() == 5) { // PlanningUnit Node
                        nodeParams.put("PLANNING_UNIT_ID", tnd.getPuNode().getPlanningUnit().getId());
                        nodeParams.put("SHARE_PLANNING_UNIT", tnd.getPuNode().isSharePlanningUnit());
                        nodeParams.put("REFILL_MONTHS", tnd.getPuNode().getRefillMonths());
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
                }
            }
        }
        params.clear();
        params.put("treeTemplateId", treeTemplateId);
        this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node ttn LEFT JOIN rm_tree_template_node ttn2 ON ttn.TREE_TEMPLATE_ID=ttn2.TREE_TEMPLATE_ID AND left(ttn.SORT_ORDER, length(ttn.SORT_ORDER)-3)=ttn2.SORT_ORDER SET ttn.PARENT_NODE_ID=ttn2.NODE_ID WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId", params);
        return treeTemplateId;
    }

    @Override
    public int updateTreeTemplate(TreeTemplate tt, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        SimpleJdbcInsert ni = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node").usingGeneratedKeyColumns("TREE_TEMPLATE_NODE_ID");
        SimpleJdbcInsert nid = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data").usingGeneratedKeyColumns("NODE_DATA_ID");
        SimpleJdbcInsert nidf = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_fu").usingGeneratedKeyColumns("NODE_DATA_FU_ID");
        SimpleJdbcInsert nidp = new SimpleJdbcInsert(dataSource).withTableName("rm_tree_template_node_data_pu").usingGeneratedKeyColumns("NODE_DATA_PU_ID");
        Map<String, Object> params = new HashMap<>();
        String sql = "UPDATE rm_tree_template tt LEFT JOIN ap_label l ON l.LABEL_ID=tt.LABEL_ID "
                + "SET "
                + "l.LABEL_EN=:labelEn, "
                + "tt.FORECAST_METHOD_ID=:forecastMethod, "
                + "tt.LAST_MODIFIED_BY=:curUser, "
                + "tt.LAST_MODIFIED_DATE=:curDate, "
                + "tt.ACTIVE=:active "
                + "WHERE tt.TREE_TEMPLATE_ID=:treeTemplateId";
        params.put("labelEn", tt.getLabel().getLabel_en());
        params.put("forecastMethod", tt.getForecastMethod().getId());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("active", tt.isActive());
        params.put("treeTemplateId", tt.getTreeTemplateId());
        this.namedParameterJdbcTemplate.update(sql, params);
        params.clear();
        params.put("treeTemplateId", tt.getTreeTemplateId());
        // Delete NodeDataPU
        StringBuilder deleteList = new StringBuilder();
        tt.getTree().getFlatList().forEach(n -> {
            if (n.getPayload().getNodeType().getId() == 5
                    && n.getPayload().getNodeDataMap() != null
                    && n.getPayload().getNodeDataMap().get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0).getPuNode() != null
                    && n.getPayload().getNodeDataMap().get(0).get(0).getPuNode().getNodeDataPuId() != 0) {
                deleteList.append(n.getPayload().getNodeDataMap().get(0).get(0).getPuNode().getNodeDataPuId()).append(",");
            }
        });
        if (deleteList.length() > 0) {
            deleteList.setLength(deleteList.length() - 1);
            this.namedParameterJdbcTemplate.update("DELETE ttndp.* FROM rm_tree_template_node ttn "
                    + "LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                    + "LEFT JOIN rm_tree_template_node_data_pu ttndp ON ttnd.NODE_DATA_PU_ID=ttndp.NODE_DATA_PU_ID "
                    + "WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId AND ttndp.NODE_DATA_PU_ID IS NOT NULL AND ttndp.NODE_DATA_PU_ID NOT IN (" + deleteList.toString() + ")", params);
        }
        deleteList.setLength(0);
        // Delete NodeDataFu
        tt.getTree().getFlatList().forEach(n -> {
            if (n.getPayload().getNodeType().getId() == 4
                    && n.getPayload().getNodeDataMap() != null
                    && n.getPayload().getNodeDataMap().get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0).getFuNode() != null
                    && n.getPayload().getNodeDataMap().get(0).get(0).getFuNode().getNodeDataFuId() != 0) {
                deleteList.append(n.getPayload().getNodeDataMap().get(0).get(0).getFuNode().getNodeDataFuId()).append(",");
            }
        });
        if (deleteList.length() > 0) {
            deleteList.setLength(deleteList.length() - 1);
            this.namedParameterJdbcTemplate.update("DELETE ttndf.* FROM rm_tree_template_node ttn "
                    + "LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                    + "LEFT JOIN rm_tree_template_node_data_fu ttndf ON ttnd.NODE_DATA_FU_ID=ttndf.NODE_DATA_FU_ID "
                    + "WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId AND ttndf.NODE_DATA_FU_ID IS NOT NULL AND ttndf.NODE_DATA_FU_ID NOT IN (" + deleteList.toString() + ")", params);
        }
        deleteList.setLength(0);
        // Delete NodeData
        tt.getTree().getFlatList().forEach(n -> {
            if (n.getPayload().getNodeType().getId() != 4
                    && n.getPayload().getNodeType().getId() != 5
                    && n.getPayload().getNodeDataMap() != null
                    && n.getPayload().getNodeDataMap().get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0) != null
                    && n.getPayload().getNodeDataMap().get(0).get(0).getNodeDataId() != 0) {
                deleteList.append(n.getPayload().getNodeDataMap().get(0).get(0).getNodeDataId()).append(",");
            }
        });
        if (deleteList.length() > 0) {
            deleteList.setLength(deleteList.length() - 1);
            this.namedParameterJdbcTemplate.update("DELETE ttnd.* FROM rm_tree_template_node ttn "
                    + "LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                    + "WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId AND ttnd.NODE_DATA_ID IS NOT NULL AND ttnd.NODE_DATA_ID NOT IN (" + deleteList.toString() + ")", params);
        }
        deleteList.setLength(0);
        // Delete Node
        tt.getTree().getFlatList().forEach(n -> {
            if (n.getPayload().getNodeId() != 0) {
                deleteList.append(n.getPayload().getNodeId()).append(",");
            }
        });
        if (deleteList.length() > 0) {
            deleteList.setLength(deleteList.length() - 1);
            this.namedParameterJdbcTemplate.update("DELETE ttn.* FROM rm_tree_template_node ttn "
                    + "WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId AND ttn.NODE_ID NOT IN (" + deleteList.toString() + ")", params);
        }
        for (ForecastNode<TreeNode> n : tt.getTree().getFlatList()) {
            if (n.getPayload().getNodeId() == 0) {
                // New Node so Insert
                Map<String, Object> nodeParams = new HashMap<>();
                nodeParams.put("TREE_TEMPLATE_ID", tt.getTreeTemplateId());
                nodeParams.put("SORT_ORDER", n.getSortOrder());
                nodeParams.put("LEVEL_NO", n.getLevel() + 1);
                nodeParams.put("NODE_TYPE_ID", n.getPayload().getNodeType().getId());
                nodeParams.put("UNIT_ID", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == null || n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
                nodeParams.put("MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS", n.getPayload().isManualChangeEffectsFutureMonths());
                int nodeLabelId = this.labelDao.addLabel(n.getPayload().getLabel(), LabelConstants.RM_FORECAST_TREE_TEMPLATE_NODE, curUser.getUserId());
                nodeParams.put("LABEL_ID", nodeLabelId);
                nodeParams.put("CREATED_BY", curUser.getUserId());
                nodeParams.put("CREATED_DATE", curDate);
                nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                nodeParams.put("LAST_MODIFIED_DATE", curDate);
                nodeParams.put("ACTIVE", 1);
                int nodeId = ni.executeAndReturnKey(nodeParams).intValue();
                n.getPayload().setNodeId(nodeId);
                if (n.getPayload().getNodeDataMap().get(0) != null) {
                    TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                    if (tnd != null) {
                        nodeParams.put("NODE_ID", nodeId);
                        nodeParams.put("MONTH", tnd.getMonth());
                        nodeParams.put("DATA_VALUE", tnd.getDataValue());
                        nodeParams.put("NOTES", tnd.getNotes());
                        nodeParams.put("CREATED_BY", curUser.getUserId());
                        nodeParams.put("CREATED_DATE", curDate);
                        nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                        nodeParams.put("LAST_MODIFIED_DATE", curDate);
                        nodeParams.put("ACTIVE", 1);
                        int nodeDataId = nid.executeAndReturnKey(nodeParams).intValue();
                        nodeParams.clear();
                        if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { // ForecastingUnitNode
                            nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                            nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                            nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                            nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                            nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                            if (tnd.getFuNode().getUsageType().getId() == 2) {
                                // Discrete
                                nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                                if (!tnd.getFuNode().isOneTimeUsage()) {
                                    nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                    nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                    nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                    nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                                }
                            } else {
                                // Continuous
                                nodeParams.put("ONE_TIME_USAGE", 0);
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
                    }
                }
            } else {
                // Existing node so update
                sql = "UPDATE rm_tree_template_node ttn LEFT JOIN ap_label l ON ttn.LABEL_ID=l.LABEL_ID "
                        + "SET "
                        + "ttn.SORT_ORDER=:sortOrder, "
                        + "ttn.LEVEL_NO=:levelNo, "
                        + "ttn.NODE_TYPE_ID=:nodeTypeId, "
                        + "ttn.UNIT_ID=:unitId, "
                        + "ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS=:manualChangeEffectsFutureMonths, "
                        + "l.LABEL_EN=:labelEn, "
                        + "ttn.LAST_MODIFIED_BY=:curUser, "
                        + "ttn.LAST_MODIFIED_DATE=:curDate, "
                        + "ttn.ACTIVE=:active "
                        + "WHERE ttn.NODE_ID=:nodeId";
                params.put("sortOrder", n.getSortOrder());
                params.put("levelNo", n.getLevel() + 1);
                params.put("nodeTypeId", n.getPayload().getNodeType().getId());
                params.put("unitId", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
                params.put("manualChangeEffectsFutureMonths", n.getPayload().isManualChangeEffectsFutureMonths());
                params.put("labelEn", n.getPayload().getLabel().getLabel_en());
                params.put("curUser", curUser.getUserId());
                params.put("curDate", curDate);
                params.put("active", tt.isActive());
                params.put("nodeId", n.getPayload().getNodeId());
                this.namedParameterJdbcTemplate.update(sql, params);
                Map<String, Object> nodeParams = new HashMap<>();
                if (n.getPayload().getNodeDataMap().get(0) != null) {
                    TreeNodeData tnd = n.getPayload().getNodeDataMap().get(0).get(0);
                    if (tnd != null) {
                        if (tnd.getNodeDataId() == 0) {
                            // New Node Data so Insert
                            nodeParams.put("NODE_ID", n.getPayload().getNodeId());
                            nodeParams.put("MONTH", tnd.getMonth());
                            nodeParams.put("DATA_VALUE", tnd.getDataValue());
                            nodeParams.put("NOTES", tnd.getNotes());
                            nodeParams.put("CREATED_BY", curUser.getUserId());
                            nodeParams.put("CREATED_DATE", curDate);
                            nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                            nodeParams.put("LAST_MODIFIED_DATE", curDate);
                            nodeParams.put("ACTIVE", 1);
                            int nodeDataId = nid.executeAndReturnKey(nodeParams).intValue();
                            nodeParams.clear();
                            if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { // ForecastingUnitNode
                                nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                                nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                                nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                                nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                                nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                                if (tnd.getFuNode().getUsageType().getId() == 2) {//Discrete
                                    nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                                    if (!tnd.getFuNode().isOneTimeUsage()) {
                                        nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                        nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                        nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                        nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                                    }
                                } else {
                                    // Continuous
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
                        } else {
                            // Node Data Id exists so therefore do an update on NodeData
                            params.clear();
                            params.put("month", tnd.getMonth());
                            params.put("dataValue", tnd.getDataValue());
                            params.put("notes", tnd.getNotes());
                            params.put("curUser", curUser.getUserId());
                            params.put("curDate", curDate);
                            params.put("active", 1);
                            params.put("nodeDataId", tnd.getNodeDataId());
                            sql = "UPDATE rm_tree_template_node_data ttnd "
                                    + "SET "
                                    + "ttnd.MONTH=:month, "
                                    + "ttnd.DATA_VALUE=:dataValue, "
                                    + "ttnd.NOTES=:notes, "
                                    + "ttnd.LAST_MODIFIED_BY=:curUser, "
                                    + "ttnd.LAST_MODIFIED_DATE=:curDate "
                                    + "WHERE ttnd.NODE_DATA_ID=:nodeDataId";
                            this.namedParameterJdbcTemplate.update(sql, params);
                            if (tnd.getFuNode() != null && n.getPayload().getNodeType().getId() == 4) { //Forecasting Unit Node
                                if (tnd.getFuNode().getNodeDataFuId() == 0) {
                                    // New Forecasting Unit so insert
                                    nodeParams.clear();
                                    nodeParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                                    nodeParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                                    nodeParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                                    nodeParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                                    nodeParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                                    if (tnd.getFuNode().getUsageType().getId() == 2) {
                                        //Discrete
                                        nodeParams.put("ONE_TIME_USAGE", tnd.getFuNode().isOneTimeUsage());
                                        if (!tnd.getFuNode().isOneTimeUsage()) {
                                            nodeParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                                            nodeParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                            nodeParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                                            nodeParams.put("REPEAT_USAGE_PERIOD_ID", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                                        }
                                    } else {
                                        // Continuous
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
                                    nodeParams.put("nodeDataId", tnd.getNodeDataId());
                                    this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_FU_ID=:nodeFuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                                } else {
                                    // Existing Forecasting Unit so update
                                    StringBuilder sb = new StringBuilder();
                                    params.clear();
                                    sb.append("UPDATE rm_tree_template_node_data_fu f "
                                            + "SET "
                                            + "f.FORECASTING_UNIT_ID=:forecastingUnitId, "
                                            + "f.LAG_IN_MONTHS=:lagInMonths, "
                                            + "f.USAGE_TYPE_ID=:usageTypeId, "
                                            + "f.NO_OF_PERSONS=:noOfPersons, "
                                            + "f.FORECASTING_UNITS_PER_PERSON=:forecastingUnitsPerPerson ");
                                    params.put("forecastingUnitId", tnd.getFuNode().getForecastingUnit().getId());
                                    params.put("lagInMonths", tnd.getFuNode().getLagInMonths());
                                    params.put("usageTypeId", tnd.getFuNode().getUsageType().getId());
                                    params.put("noOfPersons", tnd.getFuNode().getNoOfPersons());
                                    params.put("forecastingUnitsPerPerson", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                                    if (tnd.getFuNode().getUsageType().getId() == 2) {
                                        //Discrete
                                        sb.append(", f.ONE_TIME_USAGE=:oneTimeUsage");
                                        params.put("oneTimeUsage", tnd.getFuNode().isOneTimeUsage());
                                        if (!tnd.getFuNode().isOneTimeUsage()) {
                                            // Not a one time usage
                                            sb.append(", f.USAGE_FREQUENCY=:usageFrequency"
                                                    + ", f.USAGE_FREQUENCY_USAGE_PERIOD_ID=:usageFrequencyUsagePeriodId"
                                                    + ", f.REPEAT_COUNT=:repeatCount"
                                                    + ", f.REPEAT_USAGE_PERIOD_ID=:repeatUsagePeriodId");
                                            params.put("usageFrequency", tnd.getFuNode().getUsageFrequency());
                                            params.put("usageFrequencyUsagePeriodId", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                            params.put("repeatCount", tnd.getFuNode().getRepeatCount());
                                            params.put("repeatUsagePeriodId", tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId());
                                        }
                                    } else {
                                        // Continuous
                                        sb.append(", f.ONE_TIME_USAGE=0"
                                                + ", f.USAGE_FREQUENCY=:usageFrequency"
                                                + ", f.USAGE_FREQUENCY_USAGE_PERIOD_ID=:usageFrequencyUsagePeriodId");
                                        params.put("usageFrequency", tnd.getFuNode().getUsageFrequency());
                                        params.put("usageFrequencyUsagePeriodId", tnd.getFuNode().getUsagePeriod().getUsagePeriodId());
                                    }
                                    sb.append(", f.LAST_MODIFIED_BY=:curUser"
                                            + ", f.LAST_MODIFIED_DATE=:curDate"
                                            + ", f.ACTIVE=:active"
                                            + " WHERE f.NODE_DATA_FU_ID=:nodeDataFuId");
                                    params.put("curUser", curUser.getUserId());
                                    params.put("curDate", curDate);
                                    params.put("active",1);
                                    params.put("nodeDataFuId", tnd.getFuNode().getNodeDataFuId());
                                    this.namedParameterJdbcTemplate.update(sb.toString(), params);
                                }
                            } else if (tnd.getPuNode() != null && n.getPayload().getNodeType().getId() == 5) { //Planning Unit Node
                                if (tnd.getPuNode().getNodeDataPuId() == 0) {
                                    // New Planning Unit so insert
                                    nodeParams.put("PLANNING_UNIT_ID", tnd.getPuNode().getPlanningUnit().getId());
                                    nodeParams.put("SHARE_PLANNING_UNIT", tnd.getPuNode().isSharePlanningUnit());
                                    nodeParams.put("REFILL_MONTHS", tnd.getPuNode().getRefillMonths());
                                    nodeParams.put("CREATED_BY", curUser.getUserId());
                                    nodeParams.put("CREATED_DATE", curDate);
                                    nodeParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                                    nodeParams.put("LAST_MODIFIED_DATE", curDate);
                                    nodeParams.put("ACTIVE", 1);
                                    int nodePuId = nidp.executeAndReturnKey(nodeParams).intValue();
                                    nodeParams.clear();
                                    nodeParams.put("nodePuId", nodePuId);
                                    nodeParams.put("nodeDataId", tnd.getNodeDataId());
                                    this.namedParameterJdbcTemplate.update("UPDATE rm_tree_template_node_data SET NODE_DATA_PU_ID=:nodePuId WHERE NODE_DATA_ID=:nodeDataId", nodeParams);
                                } else {
                                    // Existing Planning Unit so update
                                    sql = "UPDATE rm_tree_template_node_data_pu p "
                                            + "SET "
                                            + "p.PLANNING_UNIT_ID=:planningUnitId, "
                                            + "p.SHARE_PLANNING_UNIT=:sharePlanningUnit, "
                                            + "p.REFILL_MONTHS=:refillMonths, "
                                            + "p.LAST_MODIFIED_BY=:curUser, "
                                            + "p.LAST_MODIFIED_DATE=:curDate, "
                                            + "p.ACTIVE=:active "
                                            + "WHERE p.NODE_DATA_PU_ID=:nodeDataPuId";
                                    params.clear();
                                    params.put("planningUnitId", tnd.getPuNode().getPlanningUnit().getId());
                                    params.put("sharePlanningUnit", tnd.getPuNode().isSharePlanningUnit());
                                    params.put("refillMonths", tnd.getPuNode().getRefillMonths());
                                    params.put("curUser", curUser.getUserId());
                                    params.put("curDate", curDate);
                                    params.put("active",1);
                                    params.put("nodeDataPuId", tnd.getPuNode().getNodeDataPuId());
                                    this.namedParameterJdbcTemplate.update(sql, params);
                                }
                            }
                        }
                    }
                }
            }
        }
        return tt.getFlatList().length;
    }

    private String convertListToString(List<Integer> lst) {
        StringBuilder sb = new StringBuilder();
        lst.forEach(l -> {
            sb.append(l).append(",");
        });
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
