/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramIntegrationDTORowMapper implements RowMapper<ProgramIntegrationDTO>{

    @Override
    public ProgramIntegrationDTO mapRow(ResultSet rs, int i) throws SQLException {
        ProgramIntegrationDTO pi = new ProgramIntegrationDTO();
        pi.setProgramVersionTransId(rs.getInt("PROGRAM_VERSION_TRANS_ID"));
        pi.setProgramId(rs.getInt("PROGRAM_ID"));
        pi.setProgramCode(rs.getString("PROGRAM_CODE"));
        pi.setVersionId(rs.getInt("VERSION_ID"));
        pi.setVersionTypeId(rs.getInt("VERSION_TYPE_ID"));
        pi.setVersionStatusId(rs.getInt("VERSION_STATUS_ID"));
        pi.setIntegrationProgramId(rs.getInt("INTEGRATION_PROGRAM_ID"));
        pi.setIntegrationId(rs.getInt("INTEGRATION_ID"));
        pi.setIntegrationName(rs.getString("INTEGRATION_NAME"));
        pi.setFileName(rs.getString("FILE_NAME"));
        pi.setFolderName(rs.getString("FOLDER_LOCATION"));
        pi.setIntegrationViewId(rs.getInt("INTEGRATION_VIEW_ID"));
        pi.setIntegrationViewName(rs.getString("INTEGRATION_VIEW_NAME"));
        return pi;
    }
    
}
