/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class InventoryRowMapper implements RowMapper<Inventory> {

    @Override
    public Inventory mapRow(ResultSet rs, int i) throws SQLException {
        Inventory inv = new Inventory(
                rs.getInt("INVENTORY_ID"),
                rs.getDate("INVENTORY_DATE"),
                new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"), new LabelRowMapper("REALM_COUNTRY_PLANNING_UNIT_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)),
                rs.getDouble("MULTIPLIER"),
                rs.getDouble("ADJUSTMENT_QTY"),
                rs.getDouble("EXPECTED_BAL"),
                new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, i), rs.getString("UNIT_CODE")),
                new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, i)),
                rs.getInt("VERSION_ID")
        );
        Double actualQty = rs.getDouble("ACTUAL_QTY");
        if (rs.wasNull()) {
            inv.setActualQty(null);
        } else {
            inv.setActualQty(actualQty);
        }
        inv.setBatchNo(rs.getString("BATCH_NO"));
        inv.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        inv.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return inv;
    }

}
