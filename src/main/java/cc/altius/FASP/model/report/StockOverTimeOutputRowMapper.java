/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockOverTimeOutputRowMapper implements RowMapper<StockOverTimeOutput> {

    @Override
    public StockOverTimeOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockOverTimeOutput s = new StockOverTimeOutput();
        s.setDt(rs.getString("DT"));
        s.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        s.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        s.setStock(rs.getInt("STOCK"));
        s.setConsumptionQty(rs.getInt("CONSUMPTION_QTY"));
        s.setAmc(rs.getDouble("AMC"));
        s.setAmcMonthCount(rs.getInt("AMC_MONTH_COUNT"));
        s.setMos(rs.getDouble("MOS"));
        return s;
    }

}
