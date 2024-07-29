/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SecurityRequestMatcher;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SecurityRequestMatcherRowMapper implements RowMapper<SecurityRequestMatcher> {

    @Override
    public SecurityRequestMatcher mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SecurityRequestMatcher(rs.getInt("SECURITY_ID"), rs.getInt("METHOD"), rs.getString("URL_LIST"), rs.getString("BF_LIST"));
    }

}
