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
public class InventoryInfoRowMapper implements RowMapper<InventoryInfo> {

    @Override
    public InventoryInfo mapRow(ResultSet rs, int i) throws SQLException {
        return new InventoryInfo(
                rs.getInt("INVENTORY_ID"),
                rs.getDate("INVENTORY_DATE"),
                new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, i)),
                rs.getString("NOTES")
        );
    }

}
