/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramProductCatalogOutputRowMapper implements RowMapper<ProgramProductCatalogOutput> {

    @Override
    public ProgramProductCatalogOutput mapRow(ResultSet rs, int i) throws SQLException {
        ProgramProductCatalogOutput ppc = new ProgramProductCatalogOutput();
        ppc.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        ppc.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, i)));
        ppc.setProductCategory(new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, i)));
        ppc.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, i)));
        ppc.setfUnit(new SimpleCodeObject(rs.getInt("FUNIT_ID"), new LabelRowMapper("FUNIT_").mapRow(rs, i), rs.getString("FUNIT_CODE")));
        ppc.setGenericName(new LabelRowMapper("GENERIC_").mapRow(rs, i));
        ppc.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        ppc.setpUnit(new SimpleCodeObject(rs.getInt("PUNIT_ID"), new LabelRowMapper("PUNIT_").mapRow(rs, i), rs.getString("PUNIT_CODE")));
        ppc.setForecastingtoPlanningUnitMultiplier(rs.getInt("FORECASTING_TO_PLANNING_UNIT_MULTIPLIER"));
        ppc.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ppc.setReorderFrequencyInMonths(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
        ppc.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
        ppc.setShelfLife(rs.getInt("SHELF_LIFE"));
        ppc.setActive(rs.getBoolean("ACTIVE"));
        return ppc;
    }

}
