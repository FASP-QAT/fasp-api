/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class NodeDataModelingListRowMapper implements RowMapper<NodeDataModeling> {

    private final boolean isTemplate;
    private final Date curDate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public NodeDataModelingListRowMapper(boolean isTemplate) {
        this.isTemplate = isTemplate;
        this.curDate = DateUtils.getStartOfMonthObject();
    }

    @Override
    public NodeDataModeling mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeDataModeling ndm = new NodeDataModeling(rs.getInt("NODE_DATA_MODELING_ID"));
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
        ndm.setModelingSource(rs.getInt("MODELING_SOURCE"));
        ndm.setModelingType(new SimpleObject(rs.getInt("MODELING_TYPE_ID"), new LabelRowMapper("MODELING_TYPE_").mapRow(rs, 1)));
        return ndm;
    }
}
