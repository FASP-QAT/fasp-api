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
public class InventoryTurnsOutputRowMapper implements RowMapper<InventoryTurnsOutput>{

    @Override
    public InventoryTurnsOutput mapRow(ResultSet rs, int i) throws SQLException {
        return new InventoryTurnsOutput(
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, i)), 
                rs.getLong("TOTAL_CONSUMPTION"), 
                rs.getDouble("AVG_STOCK"),
                rs.getInt("NO_OF_MONTHS"),
                rs.getDouble("INVENTORY_TURNS"));
    }
    
}
