/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.IntegrationView;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class IntegrationViewRowMapper implements RowMapper<IntegrationView>{

    @Override
    public IntegrationView mapRow(ResultSet rs, int i) throws SQLException {
        return new IntegrationView(rs.getInt("INTEGRATION_VIEW_ID"), rs.getString("INTEGRATION_VIEW_DESC"), rs.getString("INTEGRATION_VIEW_NAME"));
    }
    
}
