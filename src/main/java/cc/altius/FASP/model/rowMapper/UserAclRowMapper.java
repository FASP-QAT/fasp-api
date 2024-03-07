/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UserAcl;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UserAclRowMapper implements RowMapper<UserAcl> {

    @Override
    public UserAcl mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAcl userAcl = new UserAcl();
        userAcl.setUserId(rs.getInt("USER_ID"));
        userAcl.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
        userAcl.setCountryName(new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, rowNum));
        userAcl.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
        userAcl.setHealthAreaName(new LabelRowMapper("HEALTH_AREA_").mapRow(rs, rowNum));
        userAcl.setOrganisationId(rs.getInt("ORGANISATION_ID"));
        userAcl.setOrganisationName(new LabelRowMapper("ORGANISATION_").mapRow(rs, rowNum));
        userAcl.setProgramId(rs.getInt("PROGRAM_ID"));
        userAcl.setProgramName(new LabelRowMapper("PROGRAM_").mapRow(rs, rowNum));
        return userAcl;
    }

}
