/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.ExtrapolationData;
import cc.altius.FASP.model.ExtrapolationDataReportingRate;
import cc.altius.FASP.model.ForecastNode;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.NodeDataExtrapolation;
import cc.altius.FASP.model.NodeDataExtrapolationOption;
import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.NodeDataMom;
import cc.altius.FASP.model.NodeDataOverride;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleUnitAndTracerObject;
import cc.altius.FASP.model.SimpleUnitObjectWithMultiplier;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeNodeData;
import cc.altius.FASP.model.TreeNodeDataFu;
import cc.altius.FASP.model.TreeNodeDataPu;
import cc.altius.FASP.model.UsagePeriod;
import cc.altius.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class TreeNodeResultSetExtractor implements ResultSetExtractor<ForecastTree<TreeNode>> {

    private final boolean isTemplate;
    private final Date curDate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TreeNodeResultSetExtractor(boolean isTemplate) {
        this.isTemplate = isTemplate;
        this.curDate = DateUtils.getStartOfMonthObject();
    }

    @Override
    public ForecastTree<TreeNode> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ForecastTree<TreeNode> tree = null;
        try {
            while (rs.next()) {
                Integer parentNodeId = rs.getInt("PARENT_NODE_ID");
                if (rs.wasNull()) {
                    parentNodeId = null;
                }
                int nodeId = rs.getInt("NODE_ID");
                TreeNode tn = getNode(nodeId, parentNodeId, rs, 1);
                ForecastNode<TreeNode> n = null;
                if (tree == null) {
                    // Tree is empty so Initialize the Tree
                    n = new ForecastNode(tn.getNodeId(), tn.getParentNodeId(), tn, tn.getNodeId());
                    tree = new ForecastTree<>(n);
                } else {
                    // Tree is not null so you should search if the Node already exists
                    n = tree.findNode(nodeId);
                    if (n == null) {
                        // Node was not found so this is a new Node you need to create it and add to the Tree
                        n = new ForecastNode(tn.getNodeId(), tn.getParentNodeId(), tn, tn.getNodeId());
                        tree.addNode(n);
                    } else {
                        // Node was found so just link tn
                        tn = n.getPayload();
                    }
                }
                // Load other data into Payload
                int scenarioId = rs.getInt("SCENARIO_ID");
                List<TreeNodeData> tndList = tn.getNodeDataMap().get(scenarioId);
                if (tndList == null) {
                    tndList = new LinkedList<>();
                    tn.getNodeDataMap().put(scenarioId, tndList);
                }
                addNodeData(rs, 1, tndList, tn);
            }
        } catch (Exception e) {
            throw new DataAccessResourceFailureException(e.getMessage());
        }
        return tree;
    }

    /**
     * n = new Node(count, null, tn, tn.getNodeId()); tree = new Tree<>(n); }
     * else { n = tree.findNodeByPayloadId(nodeId); if (n==null) { tn =
     * n.getPayload(); } }else { tn = getNode(nodeId, parentNodeId, rs, 1); } if
     * (isFirst) {
     *
     * isFirst = false; } else if (tn == null) { Node<TreeNode> parentNode =
     * tree.findNodeByPayloadId(tn.getParentNodeId()); n = new Node<>(count,
     * parentNode.getId(), tn, tn.getNodeId()); tree.addNode(n); }
     */
    private TreeNode getNode(int nodeId, Integer parentNodeId, ResultSet rs, int count) throws SQLException {
        TreeNode tn = new TreeNode(
                nodeId,
                parentNodeId,
                new NodeType(rs.getInt("NODE_TYPE_ID"), new LabelRowMapper("NT_").mapRow(rs, count), rs.getBoolean("MODELING_ALLOWED"), rs.getBoolean("EXTRAPOLATION_ALLOWED"), rs.getBoolean("TREE_TEMPLATE_ALLOWED"), rs.getBoolean("FORECAST_TREE_ALLOWED")),
                (isTemplate ? rs.getBoolean("IS_EXTRAPOLATION") : false),
                new SimpleCodeObject(rs.getInt("U_UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, count), rs.getString("U_UNIT_CODE")),
                 new LabelRowMapper().mapRow(rs, count)
        );
        return tn;
    }

    private TreeNodeData addNodeData(ResultSet rs, int count, List<TreeNodeData> tndList, TreeNode tn) throws SQLException {
        TreeNodeData tnd = new TreeNodeData(rs.getInt("NODE_DATA_ID"));
        int idx = tndList.indexOf(tnd);
        if (idx == -1) {
            // NodeData was not present so add the base values for NodeData
            if (this.isTemplate) {
                tnd.setMonthNo(rs.getInt("MONTH"));
                tnd.setMonth(sdf.format(DateUtils.addMonths(curDate, tnd.getMonthNo())));
                tnd.setManualChangesEffectFuture(rs.getBoolean("MANUAL_CHANGES_EFFECT_FUTURE"));
            } else {
                tnd.setMonth(rs.getString("MONTH"));
                tnd.setManualChangesEffectFuture(rs.getBoolean("MANUAL_CHANGES_EFFECT_FUTURE"));
            }
            tnd.setDataValue(rs.getDouble("DATA_VALUE"));
            tnd.setNotes(rs.getString("NOTES"));
            int nodeDataFuId = rs.getInt("NODE_DATA_FU_ID");
            if (!rs.wasNull()) {
                tnd.setFuNode(new TreeNodeDataFu());
                TreeNodeDataFu tndf = tnd.getFuNode();
                tndf.setNodeDataFuId(nodeDataFuId);
                tndf.setUsageType(new SimpleObject(rs.getInt("USAGE_TYPE_ID"), new LabelRowMapper("UT_").mapRow(rs, count)));
                tndf.setLagInMonths(rs.getInt("LAG_IN_MONTHS"));
                tndf.setForecastingUnit(new SimpleUnitAndTracerObject(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, count)), new SimpleCodeObject(rs.getInt("FUU_UNIT_ID"), new LabelRowMapper("FUU_").mapRow(rs, count), rs.getString("FUU_UNIT_CODE")), rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, count)));
                tndf.setNoOfPersons(rs.getInt("NO_OF_PERSONS"));
                tndf.setNoOfForecastingUnitsPerPerson(rs.getInt("FORECASTING_UNITS_PER_PERSON"));
                tndf.setOneTimeUsage(rs.getBoolean("ONE_TIME_USAGE"));
                if (tndf.isOneTimeUsage() != true) {
                    tndf.setUsageFrequency(rs.getDouble("USAGE_FREQUENCY"));
                    tndf.setUsagePeriod(new UsagePeriod(rs.getInt("UPF_USAGE_PERIOD_ID"), new LabelRowMapper("UPF_").mapRow(rs, count), rs.getDouble("UPF_CONVERT_TO_MONTH")));
                    if (tndf.getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_DISCRETE) { // Discrete
                        tndf.setRepeatCount(rs.getDouble("REPEAT_COUNT"));
                        tndf.setRepeatUsagePeriod(new UsagePeriod(rs.getInt("UPR_USAGE_PERIOD_ID"), new LabelRowMapper("UPR_").mapRow(rs, count), rs.getDouble("UPR_CONVERT_TO_MONTH")));
                    }
                }
            }
            int nodeDataPuId = rs.getInt("NODE_DATA_PU_ID");
            if (!rs.wasNull()) {
                tnd.setPuNode(new TreeNodeDataPu());
                TreeNodeDataPu tndp = tnd.getPuNode();
                tndp.setNodeDataPuId(nodeDataPuId);
                tndp.setRefillMonths(rs.getInt("REFILL_MONTHS"));
                if (rs.wasNull()) {
                    tndp.setRefillMonths(null);
                }
                tndp.setPuPerVisit(rs.getInt("PU_PER_VISIT"));
                if (rs.wasNull()) {
                    tndp.setPuPerVisit(null);
                }
                tndp.setSharePlanningUnit(rs.getBoolean("SHARE_PLANNING_UNIT"));
                tndp.setPlanningUnit(new SimpleUnitObjectWithMultiplier(new SimpleCodeObject(rs.getInt("PUU_UNIT_ID"), new LabelRowMapper("PUU_").mapRow(rs, count), rs.getString("PUU_UNIT_CODE")), rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, count), rs.getDouble("PU_MULTIPLIER")));
            }
            tnd.setNodeDataModelingList(new LinkedList<>()); // Initiate Modeling list
            tnd.setNodeDataOverrideList(new LinkedList<>()); // Initiate Override list
            tnd.setNodeDataMomList(new LinkedList<>()); // Initiate Mom list
            tndList.add(tnd);
        } else {
            // NodeData was already present so point tnd to the existing nodeData
            tnd = tndList.get(idx);
        }
        // Check if Modeling is already present
        // Should only be present if it is a Number, Percentage Node, FU Node or PU Node
        // TODO add a check to make sure the correct NodeType is there
        // Use tn to do the check
        if (tn.getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER || tn.getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE || tn.getNodeType().getId() == GlobalConstants.NODE_TYPE_FU || tn.getNodeType().getId() == GlobalConstants.NODE_TYPE_PU) {
            idx = -1;
            NodeDataModeling ndm = new NodeDataModeling(rs.getInt("NODE_DATA_MODELING_ID"));
            if (!rs.wasNull()) {
                idx = tnd.getNodeDataModelingList().indexOf(ndm);
                if (idx == -1) {
                    // Not found so add it
                    if (this.isTemplate) {
                        ndm.setStartDateNo(rs.getInt("MODELING_START_DATE"));
                        ndm.setStartDate(sdf.format(DateUtils.addMonths(curDate, ndm.getStartDateNo())));
                        ndm.setStopDateNo(rs.getInt("MODELING_STOP_DATE"));
                        ndm.setStopDate(sdf.format(DateUtils.addMonths(curDate, ndm.getStopDateNo())));
                    } else {
                        ndm.setStartDate(rs.getString("MODELING_START_DATE"));
                        ndm.setStopDate(rs.getString("MODELING_STOP_DATE"));
                    }
                    ndm.setDataValue(rs.getDouble("MODELING_DATA_VALUE"));
                    ndm.setIncreaseDecrease(rs.getInt("INCREASE_DECREASE"));
                    ndm.setTransferNodeDataId(rs.getInt("MODELING_TRANSFER_NODE_DATA_ID"));
                    if (rs.wasNull()) {
                        ndm.setTransferNodeDataId(null);
                    }
                    ndm.setNotes(rs.getString("MODELING_NOTES"));
                    ndm.setModelingType(new SimpleObject(rs.getInt("MODELING_TYPE_ID"), new LabelRowMapper("MODELING_TYPE_").mapRow(rs, 1)));
                    tnd.getNodeDataModelingList().add(ndm);
                }
            }
        }
        // Check if the Override is already present
        idx = -1;
        NodeDataOverride ndo = new NodeDataOverride(rs.getInt("NODE_DATA_OVERRIDE_ID"));
        if (!rs.wasNull()) {
            idx = tnd.getNodeDataOverrideList().indexOf(ndo);
            if (idx == -1) {
                // Not found so add it
                if (this.isTemplate) {
                    ndo.setMonthNo(rs.getInt("OVERRIDE_MONTH_NO"));
                    if (rs.wasNull()) {
                        ndo.setMonthNo(null);
                    }
                } else {
                    ndo.setMonth(rs.getDate("OVERRIDE_MONTH"));
                }
                ndo.setManualChange(rs.getDouble("OVERRIDE_MANUAL_CHANGE"));
                if (rs.wasNull()) {
                    ndo.setManualChange(null);
                }
                ndo.setSeasonalityPerc(rs.getDouble("OVERRIDE_SEASONALITY_PERC"));
                if (rs.wasNull()) {
                    ndo.setSeasonalityPerc(null);
                }
                tnd.getNodeDataOverrideList().add(ndo);
            }
        }
        // MOM only exists for Actual Tree not for Templates
        if (!isTemplate) {
            // Check if Mom exists
            idx = -1;
            NodeDataMom ndMom = new NodeDataMom(rs.getInt("NODE_DATA_MOM_ID"));
            if (!rs.wasNull()) {
                idx = tnd.getNodeDataMomList().indexOf(ndMom);
                // Not found so add it
                if (idx == -1) {
                    ndMom.setMonth(rs.getString("NDM_MONTH"));
                    ndMom.setStartValue(rs.getDouble("NDM_START_VALUE"));
                    if (rs.wasNull()) {
                        ndMom.setStartValue(null);
                    }
                    ndMom.setEndValue(rs.getDouble("NDM_END_VALUE"));
                    if (rs.wasNull()) {
                        ndMom.setEndValue(null);
                    }
                    ndMom.setCalculatedValue(rs.getDouble("NDM_CALCULATED_VALUE"));
                    if (rs.wasNull()) {
                        ndMom.setCalculatedValue(null);
                    }
                    ndMom.setCalculatedMmdValue(rs.getDouble("NDM_CALCULATED_MMD_VALUE"));
                    if (rs.wasNull()) {
                        ndMom.setCalculatedMmdValue(null);
                    }
                    ndMom.setDifference(rs.getDouble("NDM_DIFFERENCE"));
                    if (rs.wasNull()) {
                        ndMom.setDifference(null);
                    }
                    ndMom.setSeasonalityPerc(rs.getDouble("NDM_SEASONALITY_PERC"));
                    if (rs.wasNull()) {
                        ndMom.setSeasonalityPerc(null);
                    }
                    ndMom.setManualChange(rs.getDouble("NDM_MANUAL_CHANGE"));
                    if (rs.wasNull()) {
                        ndMom.setManualChange(null);
                    }
                    tnd.getNodeDataMomList().add(ndMom);
                }
            }

            // Check if Extrapolation exists
            idx = -1;
            NodeDataExtrapolation nde = new NodeDataExtrapolation(rs.getInt("NODE_DATA_EXTRAPOLATION_ID"));
            if (!rs.wasNull() && tnd.getNodeDataExtrapolation() == null) {
                // Not found so add it
                nde.setExtrapolationMethod(new SimpleObject(rs.getInt("EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EM_").mapRow(rs, 1)));
                nde.setNotes(rs.getString("EM_NOTES"));
                tnd.setNodeDataExtrapolation(nde);
            } else {
                nde = tnd.getNodeDataExtrapolation();
            }

            // Check if Extrapolation Data exists
            idx = -1;
            ExtrapolationDataReportingRate edrr = new ExtrapolationDataReportingRate(rs.getDate("EM_MONTH"));
            if (!rs.wasNull()) {
                idx = nde.getExtrapolationDataList().indexOf(edrr);
                if (idx == -1) {
                    // Not found so add it
                    edrr.setAmount(rs.getDouble("EM_AMOUNT"));
                    if (rs.wasNull()) {
                        edrr.setAmount(null);
                    }
                    edrr.setReportingRate(rs.getDouble("EM_REPORTING_RATE"));
                    if (rs.wasNull()) {
                        edrr.setReportingRate(null);
                    }
                    edrr.setManualChange(rs.getDouble("EM_MANUAL_CHANGE"));
                    if (rs.wasNull()) {
                        edrr.setManualChange(null);
                    }
                    nde.getExtrapolationDataList().add(edrr);
                }
            }

            // Check if Extrapolation Options exists
            idx = -1;
            NodeDataExtrapolationOption ndeo = new NodeDataExtrapolationOption(rs.getInt("NODE_DATA_EXTRAPOLATION_OPTION_ID"));
            if (!rs.wasNull()) {
                idx = tnd.getNodeDataExtrapolationOptionList().indexOf(ndeo);
                if (idx == -1) {
                    // Not found so add it
                    ndeo.setExtrapolationMethod(new SimpleObject(rs.getInt("EO_EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EO_").mapRow(rs, 1)));
                    ndeo.setJsonProperties(rs.getString("JSON_PROPERTIES"));
                    tnd.getNodeDataExtrapolationOptionList().add(ndeo);
                }
                idx = tnd.getNodeDataExtrapolationOptionList().indexOf(ndeo);
                ndeo = tnd.getNodeDataExtrapolationOptionList().get(idx);
                // Check if Extrapolation Data exists
                idx = -1;
                ExtrapolationData ed = new ExtrapolationData(rs.getDate("EO_MONTH"));
                if (!rs.wasNull()) {
                    idx = ndeo.getExtrapolationOptionDataList().indexOf(ed);
                    if (idx == -1) {
                        // Not found so add it
                        ed.setAmount(rs.getDouble("EO_AMOUNT"));
                        if (rs.wasNull()) {
                            ed.setAmount(null);
                        }
                        ndeo.getExtrapolationOptionDataList().add(ed);
                    }
                }
            }
        }

        return tnd;
    }

}
