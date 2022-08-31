/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NodeDataOverride;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class NodeDataOverrideRowMapper implements RowMapper<NodeDataOverride> {

    private final boolean isTemplate;

    public NodeDataOverrideRowMapper(boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    @Override
    public NodeDataOverride mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeDataOverride ndo = new NodeDataOverride(rs.getInt("NODE_DATA_OVERRIDE_ID"));
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
        return ndo;
    }

}
