/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Realm;
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
        int oldHealthAreaId = 0, newHealthAreaId;
        HealthArea ha = new HealthArea();
        while (rs.next()) {
            newHealthAreaId = rs.getInt("HEALTH_AREA_ID");
            if (oldHealthAreaId != newHealthAreaId) {
                if (oldHealthAreaId != 0) {
                    ha.setRealmCountryArray(new String[ha.getRealmCountryList().size()]);
                    int x = 0;
                    for (RealmCountry rc : ha.getRealmCountryList()) {
                        ha.getRealmCountryArray()[x] = Integer.toString(rc.getRealmCountryId());
                        x++;
                    }
                    haList.add(ha);
                }
                ha = new HealthArea();
                ha.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
                ha.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                ha.setLabel(new LabelRowMapper().mapRow(rs, 1));
                ha.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                ha.setRealmCountryList(new LinkedList<>());
            }
            RealmCountry rc = new RealmCountry();
            rc.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
            rc.setCountry(new Country(rs.getInt("COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)));
            if (ha.getRealmCountryList().indexOf(rc) == -1) {
                ha.getRealmCountryList().add(rc);
            }
            oldHealthAreaId = newHealthAreaId;
        }
        if (ha.getHealthAreaId() != 0) {
            ha.setRealmCountryArray(new String[ha.getRealmCountryList().size()]);
            int x = 0;
            for (RealmCountry rc : ha.getRealmCountryList()) {
                ha.getRealmCountryArray()[x] = Integer.toString(rc.getRealmCountryId());
                x++;
            }
            haList.add(ha);
        }
        return haList;
    }
}
