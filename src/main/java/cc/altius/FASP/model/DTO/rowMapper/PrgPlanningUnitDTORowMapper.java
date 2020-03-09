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
        pu.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        pu.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        pu.setPrice(rs.getDouble("PRICE"));
        pu.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        pu.setProductId(rs.getInt("PRODUCT_ID"));
        PrgUnitDTO unit = new PrgUnitDTO();
        unit.setUnitId(rs.getInt("UNIT_ID"));
        pu.setUnit(unit);
        return pu;
    }
    
}
