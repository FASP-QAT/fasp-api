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
public class StockStatusOverTimeOutputRowMapper implements RowMapper<StockStatusOverTimeOutput> {

    @Override
    public StockStatusOverTimeOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusOverTimeOutput s = new StockStatusOverTimeOutput();
        s.setDt(rs.getString("MONTH"));
        s.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        s.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        s.setStock(rs.getLong("FINAL_CLOSING_BALANCE"));
        if(rs.wasNull()) {
            s.setStock(null);
        }
        s.setConsumptionQty(rs.getLong("CONSUMPTION_QTY"));
        if(rs.wasNull()) {
            s.setConsumptionQty(null);
        }
        s.setActualConsumption(rs.getBoolean("ACTUAL"));
        if (rs.wasNull()) {
            s.setActualConsumption(null);
        }
        s.setAmc(rs.getDouble("AMC"));
        if(rs.wasNull()) {
            s.setAmc(null);
        }
        s.setAmcMonthCount(rs.getInt("AMC_COUNT"));
        s.setMos(rs.getDouble("MoS"));
        if(rs.wasNull()) {
            s.setMos(null);
        }
        s.setMosFuture(rs.getInt("MONTHS_IN_FUTURE_FOR_AMC"));
        s.setMosPast(rs.getInt("MONTHS_IN_PAST_FOR_AMC"));
        return s;
    }

}
