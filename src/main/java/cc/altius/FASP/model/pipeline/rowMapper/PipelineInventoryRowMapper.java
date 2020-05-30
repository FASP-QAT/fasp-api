/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import cc.altius.FASP.model.pipeline.PplInventory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author altius
 */
public class PipelineInventoryRowMapper implements RowMapper<PplInventory> {
    
    @Override
    public PplInventory mapRow(ResultSet rs, int arg1) throws SQLException {
        PplInventory i = new PplInventory();
        i.setInvamount(rs.getDouble("InvAmount"));
        i.setInvdatasourceid(rs.getString("dataSource"));
//        i.setPeriod(rs.getDate("inventoryDate"));
        i.setInventoryDate(rs.getString("inventoryDate"));
        i.setInvnote(rs.getString("InvNote"));
        i.setProductid(rs.getString("PLANNING_UNIT_ID"));
        return i;
    }
    
}
