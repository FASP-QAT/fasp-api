/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class OrganisationRowMapper implements RowMapper<Organisation> {
    @Override
    public Organisation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Organisation o = new Organisation();
        o.setOrganisationId(rs.getInt("ORGANISATION_ID"));
        o.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")));
        o.setLabel(new LabelRowMapper().mapRow(rs, rowNum));
        o.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return o;
    }
    
}
