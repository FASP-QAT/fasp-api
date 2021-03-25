/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Integration;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class IntegrationRowMapper implements RowMapper<Integration> {

    @Override
    public Integration mapRow(ResultSet rs, int i) throws SQLException {
        Integration integration = new Integration();
        integration.setIntegrationId(rs.getInt("INTEGRATION_ID"));
        integration.setIntegrationName(rs.getString("INTEGRATION_NAME"));
        integration.setFolderLocation(rs.getString("FOLDER_LOCATION"));
        integration.setFileName(rs.getString("FILE_NAME"));
        integration.setRealm(new SimpleObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i)));
        integration.setIntegrationView(new IntegrationViewRowMapper().mapRow(rs, i));
        return integration;
    }
    
}
