/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockOverTimeOutputRowMapper implements RowMapper<StockStatusOverTimeOutput> {

    @Override
    public StockStatusOverTimeOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusOverTimeOutput s = new StockStatusOverTimeOutput();
        s.setDt(rs.getString("MONTH"));
        s.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        s.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        s.setStock(rs.getInt("FINAL_CLOSING_BALANCE"));
        s.setConsumptionQty(rs.getInt("CONSUMPTION_QTY"));
        s.setActualConsumption(rs.getBoolean("ACTUAL"));
        if (rs.wasNull()) {
            s.setActualConsumption(null);
        }
        s.setAmc(rs.getDouble("AMC"));
        s.setAmcMonthCount(rs.getInt("AMC_COUNT"));
        s.setMos(rs.getDouble("MoS"));
        return s;
    }

}
