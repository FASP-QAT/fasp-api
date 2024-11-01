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
public class StockStatusForProgramOutputRowMapper implements RowMapper<StockStatusForProgramOutput>{

    @Override
    public StockStatusForProgramOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusForProgramOutput sspo = new StockStatusForProgramOutput();
        sspo.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        sspo.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, i)));
        sspo.setMinMos(rs.getDouble("MIN_MONTHS_OF_STOCK"));
        if (rs.wasNull()) {
            sspo.setMinMos(null);
        }
        sspo.setMaxMos(rs.getDouble("MAX_MONTHS_OF_STOCK"));
        if (rs.wasNull()) {
            sspo.setMaxMos(null);
        }
        sspo.setMos(rs.getDouble("MoS"));
        if (rs.wasNull()) {
            sspo.setMos(null);
        }
        sspo.setStock(rs.getDouble("STOCK"));
        if (rs.wasNull()) {
            sspo.setStock(null);
        }
        sspo.setAmc(rs.getDouble("AMC"));
        if (rs.wasNull()) {
            sspo.setAmc(null);
        }
        sspo.setLastStockCount(rs.getDate("STOCK_COUNT_DATE"));
        sspo.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
        return sspo;
    }
    
}
