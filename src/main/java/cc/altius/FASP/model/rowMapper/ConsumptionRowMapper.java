/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ConsumptionRowMapper implements  RowMapper<Consumption>{

    @Override
    public Consumption mapRow(ResultSet rs, int i) throws SQLException {
        Consumption c = new Consumption(
                rs.getInt("CONSUMPTION_ID"),
                new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)),
                rs.getDate("START_DATE"),
                rs.getDate("STOP_DATE"),
                rs.getBoolean("ACTUAL_FLAG"),
                rs.getDouble("CONSUMPTION_QTY"),
                rs.getInt("DAYS_OF_STOCK_OUT"),
                new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, i)),
                rs.getString("NOTES"),
                rs.getInt("VERSION_ID")
        );
        c.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return c;
    }
    
}
