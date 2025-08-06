/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUserWithOrgAndCountry;
import cc.altius.FASP.model.SimpleObjectStringId;
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
public class BasicUserWithOrgAndCountryResultSetExtractor implements ResultSetExtractor<List<BasicUserWithOrgAndCountry>> {

    @Override
    public List<BasicUserWithOrgAndCountry> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BasicUserWithOrgAndCountry> userList = new LinkedList<>();
        while (rs.next()) {
            BasicUserWithOrgAndCountry b = new BasicUserWithOrgAndCountry();
            b.setUserId(rs.getInt("USER_ID"));
            int idx = userList.indexOf(b);
            if (idx == -1) {
                b.setUsername(rs.getString("USERNAME"));
                b.setOrgAndCountry(rs.getString("ORG_AND_COUNTRY"));
                userList.add(b);
            } else {
                b = userList.get(idx);
            }
            SimpleObjectStringId role = new SimpleObjectStringId(rs.getString("ROLE_ID"), new LabelRowMapper().mapRow(rs, 0));
            idx = b.getRoleList().indexOf(role);
            if (idx == -1) {
                b.getRoleList().add(role);
            }
        }
        return userList;
    }

}
