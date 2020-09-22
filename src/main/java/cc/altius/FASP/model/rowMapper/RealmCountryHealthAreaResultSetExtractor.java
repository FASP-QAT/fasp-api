/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.RealmCountryHealthArea;
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
public class RealmCountryHealthAreaResultSetExtractor implements ResultSetExtractor<List<RealmCountryHealthArea>> {

    @Override
    public List<RealmCountryHealthArea> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<RealmCountryHealthArea> rchaList = new LinkedList<>();
        while (rs.next()) {
            RealmCountryHealthArea rcha = new RealmCountryHealthArea();
            rcha.setRealmCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1), rs.getString("COUNTRY_CODE")));
            int idx = rchaList.indexOf(rcha);
            if (idx == -1) {
                rchaList.add(rcha);
            } else {
                rcha = rchaList.get(idx);
            }
            rcha.getHealthAreaList().add(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
        }
        return rchaList;
    }

}
