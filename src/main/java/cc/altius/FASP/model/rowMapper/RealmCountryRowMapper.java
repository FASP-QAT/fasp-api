/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.RealmCountry;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RealmCountryRowMapper implements RowMapper<RealmCountry> {
    
    @Override
    public RealmCountry mapRow(ResultSet rs, int i) throws SQLException {
        RealmCountry rc = new RealmCountry();
        rc.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
        rc.setCountry(new CountryRowMapper().mapRow(rs, i));
        rc.setRealm(new RealmRowMapper().mapRow(rs, i));
        return rc;
    }
    
}
