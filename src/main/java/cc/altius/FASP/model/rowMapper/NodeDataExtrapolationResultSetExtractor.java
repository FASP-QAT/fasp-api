/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ExtrapolationDataReportingRate;
import cc.altius.FASP.model.NodeDataExtrapolation;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class NodeDataExtrapolationResultSetExtractor implements ResultSetExtractor<NodeDataExtrapolation> {

    @Override
    public NodeDataExtrapolation extractData(ResultSet rs) throws SQLException, DataAccessException {

        NodeDataExtrapolation nde = null;
        boolean isFirst = true;
        int idx = -1;
        while (rs.next()) {
            if (isFirst) {
                nde = new NodeDataExtrapolation(rs.getInt("NODE_DATA_EXTRAPOLATION_ID"));
                nde.setExtrapolationMethod(new SimpleObject(rs.getInt("EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EM_").mapRow(rs, 1)));
                nde.setNotes(rs.getString("EM_NOTES"));
                nde.setStartDate(rs.getDate("EXTRAPOLATION_START_DATE"));
                nde.setStopDate(rs.getDate("EXTRAPOLATION_STOP_DATE"));
                isFirst = false;
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
        }
        return nde;
    }

}
