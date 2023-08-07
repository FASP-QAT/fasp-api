/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ExtrapolationData;
import cc.altius.FASP.model.NodeDataExtrapolationOption;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class NodeDataExtrapolationOptionResultSetExtractor implements ResultSetExtractor<List<NodeDataExtrapolationOption>> {

    @Override
    public List<NodeDataExtrapolationOption> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<NodeDataExtrapolationOption> ndeoList = new LinkedList<>();
        while (rs.next()) {
            NodeDataExtrapolationOption ndeo = new NodeDataExtrapolationOption(rs.getInt("NODE_DATA_EXTRAPOLATION_OPTION_ID"));
            int idx = ndeoList.indexOf(ndeo);
            if (idx == -1) {
                ndeo.setExtrapolationMethod(new SimpleObject(rs.getInt("EO_EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EO_").mapRow(rs, 1)));
                ndeo.setJsonProperties(rs.getString("JSON_PROPERTIES"));
                ndeoList.add(ndeo);
            } else {
                ndeo = ndeoList.get(idx);
            }
            // Check if Extrapolation Data exists
            /*idx = -1;
            ExtrapolationData ed = new ExtrapolationData(rs.getDate("EO_MONTH"));
            idx = ndeo.getExtrapolationOptionDataList().indexOf(ed);
            if (idx == -1) {
                // Not found so add it
                ed.setAmount(rs.getDouble("EO_AMOUNT"));
                if (rs.wasNull()) {
                    ed.setAmount(null);
                }
                ed.setCi(rs.getDouble("EO_CI"));
                if (rs.wasNull()) {
                    ed.setCi(null);
                }
                ndeo.getExtrapolationOptionDataList().add(ed);
            }*/
        }
        return ndeoList;
    }

//    @Override
//    public NodeDataExtrapolationOption mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//        if (!rs.wasNull()) {
//            ndeo.setExtrapolationMethod(new SimpleObject(rs.getInt("EO_EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EO_").mapRow(rs, 1)));
//            ndeo.setJsonProperties(rs.getString("JSON_PROPERTIES"));
//            // Check if Extrapolation Data exists
//            int idx = -1;
//            ExtrapolationData ed = new ExtrapolationData(rs.getDate("EO_MONTH"));
//            if (!rs.wasNull()) {
//                idx = ndeo.getExtrapolationOptionDataList().indexOf(ed);
//                if (idx == -1) {
//                    // Not found so add it
//                    ed.setAmount(rs.getDouble("EO_AMOUNT"));
//                    if (rs.wasNull()) {
//                        ed.setAmount(null);
//                    }
//                    ed.setCi(rs.getDouble("EO_CI"));
//                    if (rs.wasNull()) {
//                        ed.setCi(null);
//                    }
//                    ndeo.getExtrapolationOptionDataList().add(ed);
//                }
//            }
//        }
//        return ndeo;
//    }
}
