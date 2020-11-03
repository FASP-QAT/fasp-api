/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ExportProgramDataDTORowMapper implements RowMapper<ExportProgramDataDTO> {

    @Override
    public ExportProgramDataDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ExportProgramDataDTO e = new ExportProgramDataDTO();
        e.setProgramId(rs.getInt("PROGRAM_ID"));
        e.setProgramCode(rs.getString("PROGRAM_CODE"));
        e.setProgramName(rs.getString("PROGRAM_NAME"));
        e.setCountryCode2(rs.getString("COUNTRY_CODE2"));
        e.setTechnicalArea(rs.getString("TECHNICAL_AREA_NAME"));
        e.setProgramActive(rs.getBoolean("ACTIVE"));
        e.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        return e;
    }

}
