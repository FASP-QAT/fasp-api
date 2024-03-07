/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DTO.UserAclDTO;
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
public class UserAclDTOListResultSetExtractor implements ResultSetExtractor<List<UserAclDTO>> {

    @Override
    public List<UserAclDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<UserAclDTO> userAclList = new LinkedList<>();
        while (rs.next()) {
            UserAclDTO userAcl = new UserAclDTO();
            userAcl.setUser(new BasicUserRowMapper().mapRow(rs, 0));
            int idx;
            idx = userAclList.indexOf(userAcl);
            if (idx >= 0) {
                userAcl = userAclList.get(idx);
            } else {
                userAclList.add(userAcl);
            }
            userAcl.addProperty("realmCountry", rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, 1));
            userAcl.addProperty("healthArea", rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1));
            userAcl.addProperty("organisation", rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1));
            userAcl.addProperty("program", rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1));
        }
        return userAclList;
    }

}
