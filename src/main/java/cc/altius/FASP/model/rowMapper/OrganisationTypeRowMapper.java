/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.OrganisationType;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class OrganisationTypeRowMapper implements RowMapper<OrganisationType> {

    @Override
    public OrganisationType mapRow(ResultSet rs, int i) throws SQLException {
        OrganisationType ot = new OrganisationType(rs.getInt("ORGANISATION_TYPE_ID"), new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")), new LabelRowMapper().mapRow(rs, i));
        ot.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return ot;
    }
}
