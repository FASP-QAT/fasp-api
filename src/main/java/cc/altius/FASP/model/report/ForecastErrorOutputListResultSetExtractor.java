/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ForecastErrorOutputListResultSetExtractor implements ResultSetExtractor<List<ForecastErrorOutput>> {

    @Override
    public List<ForecastErrorOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ForecastErrorOutput> feList = new LinkedList<>();
        while (rs.next()) {
            ForecastErrorOutput fe = new ForecastErrorOutput();
            fe.setMonth(rs.getDate("MONTH"));
            int idx1 = -1;
            idx1 = feList.indexOf(fe);
            if (idx1 == -1) {
                feList.add(fe);
            } else {
                fe = feList.get(idx1);
            }
            RegionForecastErrorOutput re = new RegionForecastErrorOutput(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper().mapRow(rs, 1)));
            re.setDaysOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));
            re.setActualQty(rs.getDouble("ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setActualQty(null);
            }
            re.setForecastQty(rs.getDouble("FORECAST_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setForecastQty(null);
            }
            re.setSumOfActual(rs.getDouble("TOTAL_ACTUAL"));
            if (rs.wasNull()) {
                re.setSumOfActual(null);
            }
            re.setSumOfForecastMinusActual(rs.getDouble("TOTAL_FORECAST_MINUS_ACTUAL"));
            if (rs.wasNull()) {
                re.setSumOfForecastMinusActual(null);
            }
            fe.addRegionData(re);
        }
        return feList;
    }

}
