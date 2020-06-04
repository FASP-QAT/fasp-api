/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.QatTempPlanningUnitInventoryCount;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatTempPlanningUnitInventoryCountMapper implements RowMapper<QatTempPlanningUnitInventoryCount>{

    @Override
    public QatTempPlanningUnitInventoryCount mapRow(ResultSet rs, int arg1) throws SQLException {
        QatTempPlanningUnitInventoryCount pi = new QatTempPlanningUnitInventoryCount();
        pi.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        pi.setInventory(rs.getDouble("finalInventory"));
        pi.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
        return pi;
    }
    
}
