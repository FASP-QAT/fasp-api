/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatTempConsumptionRowMapper implements RowMapper<QatTempConsumption> {
    
    @Override
    public QatTempConsumption mapRow(ResultSet rs, int arg1) throws SQLException {
        QatTempConsumption c = new QatTempConsumption();
        c.setActualFlag(rs.getBoolean("ACTUAL_FLAG"));
        c.setConsumptionDate(rs.getDate("CONSUMPTION_DATE"));
        c.setConsumptionId(rs.getInt("QAT_TEMP_CONSUMPTION_ID"));
        
        SimpleObject s = new SimpleObject();
        s.setId(rs.getInt("REGION_ID"));
        c.setRegion(s);
        
        SimpleObject d= new SimpleObject();
        d.setId(rs.getInt("DATA_SOURCE_ID"));
        c.setDataSource(d);
        
        SimpleObject p = new SimpleObject();
        p.setId(rs.getInt("PLANNING_UNIT_ID"));
        c.setPlanningUnit(p);
        
        c.setNotes(rs.getString("NOTES"));
        c.setDayOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));
        c.setConsumptionQty(rs.getDouble("CONSUMPTION_QUANTITY"));
        
        return c;
    }
    
}
