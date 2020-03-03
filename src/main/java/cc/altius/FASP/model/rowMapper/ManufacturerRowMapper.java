/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Manufacturer;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class ManufacturerRowMapper implements RowMapper<Manufacturer>{

    @Override
    public Manufacturer mapRow(ResultSet rs, int i) throws SQLException {
        Manufacturer m = new Manufacturer();
        m.setActive(rs.getBoolean("ACTIVE"));
        m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        m.setLabel(new LabelRowMapper().mapRow(rs, i));
        m.setRealm(new RealmRowMapper().mapRow(rs, i));
        
        return m;
    }
    
}
