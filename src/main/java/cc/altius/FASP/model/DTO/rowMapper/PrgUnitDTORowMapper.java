/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgUnitDTORowMapper implements RowMapper<PrgUnitDTO> {
    
    @Override
    public PrgUnitDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgUnitDTO unit = new PrgUnitDTO();
        unit.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        unit.setUnitCode(rs.getString("UNIT_CODE"));
        unit.setUnitId(rs.getInt("UNIT_ID"));
        PrgUnitTypeDTO unitType = new PrgUnitTypeDTO();
        unitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        unit.setUnitType(unitType);
        unit.setActive(rs.getBoolean("ACTIVE"));
        return unit;
    }
    
}
