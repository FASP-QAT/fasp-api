/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgUnitTypeDTORowMapper implements RowMapper<PrgUnitTypeDTO>{

    @Override
    public PrgUnitTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgUnitTypeDTO unitType=new PrgUnitTypeDTO();
        unitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));        
        unitType.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        return unitType;
    }

    
    
    
    
}
