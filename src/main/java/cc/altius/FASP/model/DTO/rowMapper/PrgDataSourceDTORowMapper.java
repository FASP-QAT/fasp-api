/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgDataSourceDTORowMapper implements RowMapper<PrgDataSourceDTO>{

    @Override
    public PrgDataSourceDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgDataSourceDTO ds=new PrgDataSourceDTO();
        ds.setDataSourceId(rs.getInt("DATA_SOURCE_ID"));
        PrgDataSourceTypeDTO dataSourceType=new PrgDataSourceTypeDTO();
        dataSourceType.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        ds.setDataSourceType(dataSourceType);
        ds.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        ds.setActive(rs.getBoolean("ACTIVE"));
        return ds;
    }
    
    
    
}
