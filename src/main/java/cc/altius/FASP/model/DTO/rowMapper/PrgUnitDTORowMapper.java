/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.UnitType;
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
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        unit.setLabel(label);
        unit.setUnitCode(rs.getString("UNIT_CODE"));
        unit.setUnitId(rs.getInt("UNIT_ID"));
        UnitType unitType = new UnitType();
        unitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        unit.setUnitType(unitType);
        unit.setActive(rs.getBoolean("ACTIVE"));
        return unit;
    }
    
}
