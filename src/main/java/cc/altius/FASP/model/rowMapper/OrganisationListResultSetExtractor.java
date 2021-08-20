/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class OrganisationListResultSetExtractor implements ResultSetExtractor<List<Organisation>> {

    @Override
    public List<Organisation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Organisation> oList = new LinkedList<>();
        int oldOrganisationId = 0, newOrganisationId;
        Organisation o = new Organisation();
        while (rs.next()) {
            newOrganisationId = rs.getInt("ORGANISATION_ID");
            if (oldOrganisationId != newOrganisationId) {
                if (oldOrganisationId != 0) {
                    o.setRealmCountryArray(new String[o.getRealmCountryList().size()]);
                    int x = 0;
                    for (RealmCountry rc : o.getRealmCountryList()) {
                        o.getRealmCountryArray()[x] = Integer.toString(rc.getRealmCountryId());
                        x++;
                    }
                    oList.add(o);
                }
                o = new Organisation();

                o.setOrganisationCode(rs.getString("ORGANISATION_CODE"));
                o.setOrganisationId(rs.getInt("ORGANISATION_ID"));
                o.setOrganisationType(new SimpleObject(rs.getInt("ORGANISATION_TYPE_ID"), new LabelRowMapper("TYPE_").mapRow(rs, 1)));
                o.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                o.setLabel(new LabelRowMapper().mapRow(rs, 1));
                o.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                o.setRealmCountryList(new LinkedList<>());
            }
            int realmCountryId = rs.getInt("REALM_COUNTRY_ID");
            if (!rs.wasNull()) {
                RealmCountry rc = new RealmCountry();
                rc.setRealmCountryId(realmCountryId);
                rc.setCountry(new Country(rs.getInt("COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)));
                if (o.getRealmCountryList().indexOf(rc) == -1) {
                    o.getRealmCountryList().add(rc);
                }
            }
            oldOrganisationId = newOrganisationId;
        }
        if (o.getOrganisationId() != 0) {
            o.setRealmCountryArray(new String[o.getRealmCountryList().size()]);
            int x = 0;
            for (RealmCountry rc : o.getRealmCountryList()) {
                o.getRealmCountryArray()[x] = Integer.toString(rc.getRealmCountryId());
                x++;
            }
            oList.add(o);
        }
        return oList;
    }

}
