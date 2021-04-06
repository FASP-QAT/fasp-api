/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.IntegrationProgram;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class IntegrationProgramRowMapper implements RowMapper<IntegrationProgram> {

    @Override
    public IntegrationProgram mapRow(ResultSet rs, int i) throws SQLException {
        IntegrationProgram ip = new IntegrationProgram();
        ip.setIntegrationProgramId(rs.getInt("INTEGRATION_PROGRAM_ID"));
        ip.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        ip.setIntegration(new IntegrationRowMapper().mapRow(rs, i));
        ip.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        ip.setVersionType(new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)));
        ip.setVersionStatus(new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VERSION_STATUS_").mapRow(rs, i)));
        return ip;
    }
    
}
