/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.Region;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RegionRowMapper implements RowMapper<Region> {
    
    @Override
    public Region mapRow(ResultSet rs, int i) throws SQLException {
        Region r = new Region(
                rs.getInt("REGION_ID"), 
                new LabelRowMapper().mapRow(rs, i), 
                new RealmCountry(
                        rs.getInt("REALM_COUNTRY_ID"), 
                        new Country(rs.getInt("COUNTRY_ID"), rs.getString("COUNTRY_CODE"), new LabelRowMapper("COUNTRY_").mapRow(rs, i)), 
                        new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE"))
                )
        );
        r.setGln(rs.getString("GLN"));
        r.setCapacityCbm(rs.getDouble("CAPACITY_CBM"));
        r.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return r;
    }
    
}
