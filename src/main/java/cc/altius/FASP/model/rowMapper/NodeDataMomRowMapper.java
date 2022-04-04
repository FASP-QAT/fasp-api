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

    private String prefix;

    public NodeDataMomRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public NodeDataMom mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeDataMom ndm = new NodeDataMom();
        ndm.setMonth(rs.getString(prefix + "MONTH"));
        ndm.setStartValue(rs.getDouble(prefix + "START_VALUE"));
        if (rs.wasNull()) {
            ndm.setStartValue(null);
        }
        ndm.setEndValue(rs.getDouble(prefix + "END_VALUE"));
        if (rs.wasNull()) {
            ndm.setEndValue(null);
        }
        ndm.setCalculatedValue(rs.getDouble(prefix + "CALCULATED_VALUE"));
        if (rs.wasNull()) {
            ndm.setCalculatedValue(null);
        }
        return ndm;
    }

}
