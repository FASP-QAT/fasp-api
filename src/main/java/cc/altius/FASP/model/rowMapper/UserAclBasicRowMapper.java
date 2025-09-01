/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class UserAclBasicRowMapper implements RowMapper<UserAcl> {

    @Override
    public UserAcl mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserAcl(0, rs.getString("ROLE_ID"), rs.getInt("REALM_COUNTRY_ID"), rs.getInt("HEALTH_AREA_ID"), rs.getInt("ORGANISATION_ID"), rs.getInt("PROGRAM_ID"), rs.getInt("FUNDING_SOURCE_ID"), rs.getInt("PROCUREMENT_AGENT_ID"), null);
    }

}
