/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentOverviewProcurementAgentSplitRowMapper implements RowMapper<ShipmentOverviewProcurementAgentSplit> {

    @Override
    public ShipmentOverviewProcurementAgentSplit mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentOverviewProcurementAgentSplit sopa = new ShipmentOverviewProcurementAgentSplit();
        sopa.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        sopa.setMultiplier(rs.getInt("MULTIPLIER"));
        sopa.setTotal(rs.getDouble("SHIPMENT_QTY"));
        sopa.setProcurementAgentQty(new HashMap<>());
        ResultSetMetaData md = rs.getMetaData();
        for (int x=1 ; x<=md.getColumnCount(); x++) {
            String colName = md.getColumnName(x);
            if (colName.startsWith("PA_")) {
                sopa.getProcurementAgentQty().put(colName.substring(3), rs.getDouble(x));
            }
        }
        return sopa;
    }

}
