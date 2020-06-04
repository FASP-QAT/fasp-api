/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.QatTempInventory;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatInventoryRowMapper implements RowMapper<QatTempInventory>{

    @Override
    public QatTempInventory mapRow(ResultSet rs, int arg1) throws SQLException {
        QatTempInventory i = new QatTempInventory();
        i.setDataSourceId(rs.getString("DATA_SOURCE_ID"));
        i.setInventoryDate(rs.getString("INVENTORY_DATE"));
        i.setManualAdjustment(rs.getInt("ADJUSTMENT_QTY"));
        i.setNotes(rs.getString("NOTES"));
        i.setPlanningUnitId(rs.getString("REALM_COUNTRY_PLANNING_UNIT_ID"));
        i.setRegionId(rs.getString("REGION_ID"));
        return i;
    }
    
}
