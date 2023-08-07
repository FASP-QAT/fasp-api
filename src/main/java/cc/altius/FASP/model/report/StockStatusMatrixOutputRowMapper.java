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
public class StockStatusMatrixOutputRowMapper implements RowMapper<StockStatusMatrixOutput> {

    @Override
    public StockStatusMatrixOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusMatrixOutput ssmo = new StockStatusMatrixOutput();
        ssmo.setYear(rs.getInt("YR"));
        ssmo.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        ssmo.setTracerCategoryId(rs.getInt("TRACER_CATEGORY_ID"));
        ssmo.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, i), rs.getString("UNIT_CODE")));
        ssmo.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ssmo.setReorderFrequency(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
        ssmo.setMultiplier(rs.getInt("MULTIPLIER"));
        ssmo.setJan(rs.getDouble("Jan"));
        if (rs.wasNull()) {
            ssmo.setJan(null);
        }
        ssmo.setFeb(rs.getDouble("Feb"));
        if (rs.wasNull()) {
            ssmo.setFeb(null);
        }
        ssmo.setMar(rs.getDouble("Mar"));
        if (rs.wasNull()) {
            ssmo.setMar(null);
        }
        ssmo.setApr(rs.getDouble("Apr"));
        if (rs.wasNull()) {
            ssmo.setApr(null);
        }
        ssmo.setMay(rs.getDouble("May"));
        if (rs.wasNull()) {
            ssmo.setMay(null);
        }
        ssmo.setJun(rs.getDouble("Jun"));
        if (rs.wasNull()) {
            ssmo.setJun(null);
        }
        ssmo.setJul(rs.getDouble("Jul"));
        if (rs.wasNull()) {
            ssmo.setJul(null);
        }
        ssmo.setAug(rs.getDouble("Aug"));
        if (rs.wasNull()) {
            ssmo.setAug(null);
        }
        ssmo.setSep(rs.getDouble("Sep"));
        if (rs.wasNull()) {
            ssmo.setSep(null);
        }
        ssmo.setOct(rs.getDouble("Oct"));
        if (rs.wasNull()) {
            ssmo.setOct(null);
        }
        ssmo.setNov(rs.getDouble("Nov"));
        if (rs.wasNull()) {
            ssmo.setNov(null);
        }
        ssmo.setDec(rs.getDouble("Dec"));
        if (rs.wasNull()) {
            ssmo.setDec(null);
        }
        ssmo.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
        ssmo.setJanStock(rs.getLong("Jan Stock"));
        ssmo.setFebStock(rs.getLong("Feb Stock"));
        ssmo.setMarStock(rs.getLong("Mar Stock"));
        ssmo.setAprStock(rs.getLong("Apr Stock"));
        ssmo.setMayStock(rs.getLong("May Stock"));
        ssmo.setJunStock(rs.getLong("Jun Stock"));
        ssmo.setJulStock(rs.getLong("Jul Stock"));
        ssmo.setAugStock(rs.getLong("Aug Stock"));
        ssmo.setSepStock(rs.getLong("Sep Stock"));
        ssmo.setOctStock(rs.getLong("Oct Stock"));
        ssmo.setNovStock(rs.getLong("Nov Stock"));
        ssmo.setDecStock(rs.getLong("Dec Stock"));
        ssmo.setMaxStock(rs.getDouble("MAX_STOCK_QTY"));
        if (rs.wasNull()) {
            ssmo.setMaxStock(null);
        }
        return ssmo;
    }

}
