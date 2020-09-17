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
        ssmo.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, i), rs.getString("UNIT_CODE")));
        ssmo.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ssmo.setReorderFrequency(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
        ssmo.setMultiplier(rs.getInt("MULTIPLIER"));
        ssmo.setJan(rs.getDouble("Jan"));
        ssmo.setFeb(rs.getDouble("Feb"));
        ssmo.setMar(rs.getDouble("Mar"));
        ssmo.setApr(rs.getDouble("Apr"));
        ssmo.setMay(rs.getDouble("May"));
        ssmo.setJun(rs.getDouble("Jun"));
        ssmo.setJul(rs.getDouble("Jul"));
        ssmo.setAug(rs.getDouble("Aug"));
        ssmo.setSep(rs.getDouble("Sep"));
        ssmo.setOct(rs.getDouble("Oct"));
        ssmo.setNov(rs.getDouble("Nov"));
        ssmo.setDec(rs.getDouble("Dec"));
        return ssmo;
    }

}
