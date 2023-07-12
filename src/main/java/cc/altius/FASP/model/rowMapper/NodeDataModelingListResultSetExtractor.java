/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ModelingCalculator;
import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class NodeDataModelingListResultSetExtractor implements ResultSetExtractor<List<NodeDataModeling>> {

    private final boolean isTemplate;
    private final Date curDate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public NodeDataModelingListResultSetExtractor(boolean isTemplate) {
        this.isTemplate = isTemplate;
        this.curDate = DateUtils.getStartOfMonthObject();
    }

    @Override
    public List<NodeDataModeling> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<NodeDataModeling> ndmList = new LinkedList<>();
        while (rs.next()) {
            ModelingCalculator mc = null;
            NodeDataModeling ndm = new NodeDataModeling(rs.getInt("NODE_DATA_MODELING_ID"));
            int idx = ndmList.indexOf(ndm);
            if (idx == -1) {
                if (this.isTemplate) {
                    // Tree template
                    ndm.setStartDateNo(rs.getInt("MODELING_START_DATE"));
                    ndm.setStartDate(sdf.format(DateUtils.addMonths(curDate, ndm.getStartDateNo())));
                    ndm.setStopDateNo(rs.getInt("MODELING_STOP_DATE"));
                    ndm.setStopDate(sdf.format(DateUtils.addMonths(curDate, ndm.getStopDateNo())));
                } else {
                    // Forecasting Tree
                    ndm.setStartDate(rs.getString("MODELING_START_DATE"));
                    ndm.setStopDate(rs.getString("MODELING_STOP_DATE"));
                }
                // Commong fields
                ndm.setDataValue(rs.getDouble("MODELING_DATA_VALUE"));
                ndm.setIncreaseDecrease(rs.getInt("INCREASE_DECREASE"));
                ndm.setTransferNodeDataId(rs.getInt("MODELING_TRANSFER_NODE_DATA_ID"));
                if (rs.wasNull()) {
                    ndm.setTransferNodeDataId(null);
                }
                ndm.setNotes(rs.getString("MODELING_NOTES"));
                ndm.setModelingType(new SimpleObject(rs.getInt("MODELING_TYPE_ID"), new LabelRowMapper("MODELING_TYPE_").mapRow(rs, 1)));
                int calculatorId = rs.getInt("NODE_DATA_MODELING_CALCULATOR_ID");
                if (!rs.wasNull()) {
                    mc = new ModelingCalculator();
                    mc.setNodeDataModelingCalculatorId(calculatorId);
                    mc.setFirstMonthOfTarget(rs.getString("CALCULATOR_FIRST_MONTH"));
                    mc.setYearsOfTarget(rs.getInt("CALCULATOR_YEARS_OF_TARGET"));
                }
                ndm.setModelingCalculator(mc);
            } else {
                // Already exists so therefore only need to add the list item
                ndm = ndmList.get(idx);
            }
            if (mc != null) {
                mc.getActualOrtargetValueList().add(rs.getInt("ACTUAL_OR_TARGET_VALUE"));
            }
        }
        return ndmList;
    }
}
