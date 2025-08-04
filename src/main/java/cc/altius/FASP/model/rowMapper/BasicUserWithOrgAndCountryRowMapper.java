/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUserWithOrgAndCountry;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class BasicUserWithOrgAndCountryRowMapper implements RowMapper<BasicUserWithOrgAndCountry> {

    @Override
    public BasicUserWithOrgAndCountry mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BasicUserWithOrgAndCountry(rs.getString("ORG_AND_COUNTRY"), rs.getInt("USER_ID"), rs.getString("USERNAME"));
    }

}
