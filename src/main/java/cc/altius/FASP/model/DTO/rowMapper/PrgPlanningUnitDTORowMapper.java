/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgPlanningUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgPlanningUnitDTORowMapper implements RowMapper<PrgPlanningUnitDTO> {
    
    @Override
    public PrgPlanningUnitDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgPlanningUnitDTO pu = new PrgPlanningUnitDTO();
        pu.setActive(rs.getBoolean("ACTIVE"));
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        pu.setLabel(label);
        pu.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        pu.setPrice(rs.getDouble("PRICE"));
        pu.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO unit = new PrgUnitDTO();
        unit.setUnitId(rs.getInt("UNIT_ID"));
        pu.setUnit(unit);
        return pu;
    }
    
}
