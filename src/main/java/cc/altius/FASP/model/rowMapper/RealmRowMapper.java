/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RealmRowMapper implements RowMapper<Realm> {

    @Override
    public Realm mapRow(ResultSet rs, int i) throws SQLException {
        Realm r = new Realm();
        r.setRealmId(rs.getInt("REALM_ID"));
        r.setRealmCode(rs.getString("REALM_CODE"));
        r.setMonthInPastForAmc(rs.getInt("MONTHS_IN_PAST_FOR_AMC"));
        r.setMonthInFutureForAmc(rs.getInt("MONTHS_IN_FUTURE_FOR_AMC"));
        r.setOrderFrequency(rs.getInt("ORDER_FREQUENCY"));
        r.setDefaultRealm(rs.getBoolean("DEFAULT_REALM"));
        r.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        r.setLabel(new LabelRowMapper().mapRow(rs, i));
        return r;
    }

}
