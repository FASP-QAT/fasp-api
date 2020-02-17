/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
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
        Label l = new Label();
        l.setLabelId(rs.getInt("LABEL_ID"));
        l.setEngLabel(rs.getString("LABEL_EN"));
        l.setSpaLabel(rs.getString("LABEL_SP"));
        l.setFreLabel(rs.getString("LABEL_FR"));
        l.setPorLabel(rs.getString("LABEL_PR"));
        r.setLabel(l);
        r.setRealmCode(rs.getString("REALM_CODE"));
        return r;
    }

}
