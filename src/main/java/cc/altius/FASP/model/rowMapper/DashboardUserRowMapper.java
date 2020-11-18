/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class DashboardUserRowMapper implements RowMapper<DashboardUser> {

    @Override
    public DashboardUser mapRow(ResultSet rs, int arg1) throws SQLException {
        DashboardUser d = new DashboardUser();
        d.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
        d.setCount(rs.getInt("COUNT"));
        return d;
    }

}
