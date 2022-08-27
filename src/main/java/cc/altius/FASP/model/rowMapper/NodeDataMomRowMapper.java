/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NodeDataMom;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class NodeDataMomRowMapper implements RowMapper<NodeDataMom> {

    @Override
    public NodeDataMom mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeDataMom ndMom = new NodeDataMom(rs.getInt("NODE_DATA_MOM_ID"));
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
        return ndMom;
    }

}
