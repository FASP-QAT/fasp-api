/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Realm;
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
        rc.setCountry(new Country(rs.getInt("COUNTRY_ID"), rs.getString("COUNTRY_CODE"), new LabelRowMapper("COUNTRY_").mapRow(rs, i)));
        rc.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        rc.setDefaultCurrency(new Currency(rs.getInt("CURRENCY_ID"), rs.getString("CURRENCY_CODE"), new LabelRowMapper("CURRENCY_").mapRow(rs, i), rs.getDouble("CONVERSION_RATE_TO_USD")));
        rc.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return rc;
    }
    
}
