/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.DTO.PrgPlanningUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgLogisticsUnitDTORowMapper implements RowMapper<PrgLogisticsUnitDTO>{

    @Override
    public PrgLogisticsUnitDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgLogisticsUnitDTO lu=new PrgLogisticsUnitDTO();
        lu.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        PrgUnitDTO heightUnit=new PrgUnitDTO();
        heightUnit.setUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        lu.setHeightUnit(heightUnit);
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        lu.setLabel(label);
        lu.setLengthQty(rs.getDouble("LENGTH_QTY"));
        PrgUnitDTO lengthUnit=new PrgUnitDTO();
        lengthUnit.setUnitId(rs.getInt("LENGTH_UNIT_ID"));
        lu.setLengthUnit(lengthUnit);
        lu.setLogisticsUnitId(rs.getInt("LOGISTICS_UNIT_ID"));
        PrgManufacturerDTO manufacturer=new PrgManufacturerDTO();
        manufacturer.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        lu.setManufacturer(manufacturer);
        PrgPlanningUnitDTO planningUnit=new PrgPlanningUnitDTO();
        planningUnit.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        planningUnit.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        lu.setPlanningUnit(planningUnit);
        lu.setQtyInEuro1(rs.getDouble("QTY_IN_EURO1"));
        lu.setQtyInEuro2(rs.getDouble("QTY_IN_EURO2"));
        lu.setQtyOfPlanningUnits(rs.getDouble("QTY_OF_PLANNING_UNITS"));
        PrgUnitDTO unit=new PrgUnitDTO();
        unit.setUnitId(rs.getInt("UNIT_ID"));
        lu.setUnit(unit);
        lu.setVariant(rs.getString("VARIANT"));
        lu.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        PrgUnitDTO weightUnit=new PrgUnitDTO();
        weightUnit.setUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        lu.setWeightUnit(weightUnit);
        lu.setWidthQty(rs.getDouble("WIDTH_QTY"));
        PrgUnitDTO widthUnit=new PrgUnitDTO();
        widthUnit.setUnitId(rs.getInt("WIDTH_UNIT_ID"));
        lu.setWidthUnit(widthUnit);
        lu.setActive(rs.getBoolean("ACTIVE"));
        return lu;
    }
    
    
    
}
