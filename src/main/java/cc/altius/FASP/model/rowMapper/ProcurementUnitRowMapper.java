/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ProcurementUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementUnitRowMapper implements RowMapper<ProcurementUnit> {

    @Override
    public ProcurementUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementUnit pu = new ProcurementUnit(
                rs.getInt("PROCUREMENT_UNIT_ID"), 
                new PlanningUnit(
                    rs.getInt("PLANNING_UNIT_ID"),
                    new ForecastingUnit(
                        rs.getInt("FORECASTING_UNIT_ID"),
                        new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                        new LabelRowMapper("GENERIC_").mapRow(rs, rowNum),
                        new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, rowNum),
                        new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, rowNum)),
                        new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, rowNum))
                    ),
                    new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rowNum),
                    new SimpleObject(rs.getInt("PLANNING_UNIT_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_UNIT_").mapRow(rs, rowNum)),
                    rs.getDouble("PLANNING_UNIT_MULTIPLIER")
                ), 
                new LabelRowMapper().mapRow(rs, rowNum),
                new SimpleObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, rowNum)),
                rs.getDouble("MULTIPLIER"));
        pu.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        pu.setHeightUnit(new SimpleObject(rs.getInt("HEIGHT_UNIT_ID"), new LabelRowMapper("HEIGHT_UNIT_").mapRow(rs, rowNum)));
        pu.setWidthQty(rs.getDouble("WIDTH_QTY"));
        pu.setWidthUnit(new SimpleObject(rs.getInt("WIDTH_UNIT_ID"), new LabelRowMapper("WIDTH_UNIT_").mapRow(rs, rowNum)));
        pu.setLengthQty(rs.getDouble("LENGTH_QTY"));
        pu.setLengthUnit(new SimpleObject(rs.getInt("LENGTH_UNIT_ID"), new LabelRowMapper("LENGTH_UNIT_").mapRow(rs, rowNum)));
        pu.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        pu.setWeightUnit(new SimpleObject(rs.getInt("WEIGHT_UNIT_ID"), new LabelRowMapper("WEIGHT_UNIT_").mapRow(rs, rowNum)));
        pu.setUnitsPerContainer(rs.getDouble("UNITS_PER_CONTAINER"));
        pu.setLabeling(rs.getString("LABELING"));
        pu.setSupplier(new SimpleObject(rs.getInt("SUPPLIER_ID"), new LabelRowMapper("SUPPLIER_").mapRow(rs, rowNum)));
        pu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return pu;
    }
    
}
