/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgDataSourceTypeDTORowMapper implements RowMapper<PrgDataSourceTypeDTO>{

    @Override
    public PrgDataSourceTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgDataSourceTypeDTO dst=new PrgDataSourceTypeDTO();
        dst.setActive(rs.getBoolean("ACTIVE"));
        dst.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        dst.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        return dst;
    }
    
    
    
}
