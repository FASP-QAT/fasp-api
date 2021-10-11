/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class HealthAreaListResultSetExtractor implements ResultSetExtractor<List<HealthArea>> {

    @Override
    public List<HealthArea> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<HealthArea> haList = new LinkedList<>();
        while (rs.next()) {
            HealthArea ha = new HealthArea(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper().mapRow(rs, 1));
            int idx = haList.indexOf(ha);
            if (idx != -1) {
                ha = haList.get(idx);
            } else {
                ha.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                ha.setHealthAreaCode(rs.getString("HEALTH_AREA_CODE"));
                ha.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                ha.setRealmCountryList(new LinkedList<>());
                haList.add(ha);
            }
            int realmCountryId = rs.getInt("REALM_COUNTRY_ID");
            if (!rs.wasNull()) {
                RealmCountry rc = new RealmCountry(realmCountryId, new Country(rs.getInt("COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)));
                if (ha.getRealmCountryList().indexOf(rc) == -1) {
                    ha.getRealmCountryList().add(rc);
                }
            }
        }
        haList.forEach(ha -> {
            ha.setRealmCountryArray(ha.getRealmCountryList().stream().map(rc -> Integer.toString(rc.getRealmCountryId())).toArray(String[]::new));
        });
        return haList;
    }
}
