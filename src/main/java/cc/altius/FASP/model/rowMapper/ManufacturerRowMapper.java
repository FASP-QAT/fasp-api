/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Manufacturer;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class ManufacturerRowMapper implements RowMapper<Manufacturer> {

    @Override
    public Manufacturer mapRow(ResultSet rs, int i) throws SQLException {
        Manufacturer m = new Manufacturer(rs.getInt("MANUFACTURER_ID"), new LabelRowMapper().mapRow(rs, i), new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        m.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return m;
    }

}
