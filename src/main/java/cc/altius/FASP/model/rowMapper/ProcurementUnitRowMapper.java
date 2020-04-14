/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ProcurementUnit;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Supplier;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.Unit;
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
                        new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                        new LabelRowMapper("GENERIC_").mapRow(rs, rowNum),
                        new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, rowNum),
                        new ProductCategory(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, rowNum)),
                        new TracerCategory(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, rowNum))
                    ),
                    new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rowNum),
                    new Unit(rs.getInt("PLANNING_UNIT_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_UNIT_").mapRow(rs, rowNum), rs.getString("PLANNING_UNIT_UNIT_CODE")),
                    rs.getDouble("PLANNING_UNIT_MULTIPLIER")
                ), 
                new LabelRowMapper().mapRow(rs, rowNum),
                new Unit(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, rowNum), rs.getString("UNIT_CODE")),
                rs.getDouble("MULTIPLIER"));
        pu.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        pu.setHeightUnit(new Unit(rs.getInt("HEIGHT_UNIT_ID"), new LabelRowMapper("HEIGHT_UNIT_").mapRow(rs, rowNum), rs.getString("HEIGHT_UNIT_CODE")));
        pu.setWidthQty(rs.getDouble("WIDTH_QTY"));
        pu.setWidthUnit(new Unit(rs.getInt("WIDTH_UNIT_ID"), new LabelRowMapper("WIDTH_UNIT_").mapRow(rs, rowNum), rs.getString("WIDTH_UNIT_CODE")));
        pu.setLengthQty(rs.getDouble("LENGTH_QTY"));
        pu.setLengthUnit(new Unit(rs.getInt("LENGTH_UNIT_ID"), new LabelRowMapper("LENGTH_UNIT_").mapRow(rs, rowNum), rs.getString("LENGTH_UNIT_CODE")));
        pu.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        pu.setWeightUnit(new Unit(rs.getInt("WEIGHT_UNIT_ID"), new LabelRowMapper("WEIGHT_UNIT_").mapRow(rs, rowNum), rs.getString("WEIGHT_UNIT_CODE")));
        pu.setUnitsPerContainer(rs.getDouble("UNITS_PER_CONTAINER"));
        pu.setLabeling(rs.getString("LABELING"));
        pu.setSupplier(new Supplier(rs.getInt("SUPPLIER_ID"), new LabelRowMapper("SUPPLIER_").mapRow(rs, rowNum)));
        pu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return pu;
    }
    
}
