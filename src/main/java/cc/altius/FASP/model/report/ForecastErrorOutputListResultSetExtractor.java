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
            fe.setActualQty(rs.getDouble("NTL_ADJUSTED_ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                fe.setActualQty(null);
            }
            fe.setForecastQty(rs.getDouble("NTL_FORECAST_CONSUMPTION"));
            if (rs.wasNull()) {
                fe.setForecastQty(null);
            }
            fe.setSumOfActual(rs.getDouble("NTL_TOTAL_ADJUSTED_ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                fe.setSumOfActual(null);
            }
            fe.setSumOfForecast((rs.getDouble("NTL_TOTAL_FORECAST_CONSUMPTION")));
            if (rs.wasNull()) {
                fe.setSumOfForecast(null);
            }            
            fe.setSumOfAbsDiff(rs.getDouble("NTL_TOTAL_ABS_DIFF_CONSUMPTION"));
            if (rs.wasNull()) {
                fe.setSumOfAbsDiff(null);
            }
            int idx1 = -1;
            idx1 = feList.indexOf(fe);
            if (idx1 == -1) {
                feList.add(fe);
            } else {
                fe = feList.get(idx1);
            }
            RegionForecastErrorOutput re = new RegionForecastErrorOutput(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper().mapRow(rs, 1)));
            re.setActualQty(rs.getDouble("ADJUSTED_ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setActualQty(null);
            }
            re.setForecastQty(rs.getDouble("FORECAST_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setForecastQty(null);
            }
            re.setSumOfActual(rs.getDouble("TOTAL_ADJUSTED_ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setSumOfActual(null);
            }
            re.setSumOfForecast(rs.getDouble("TOTAL_FORECAST_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setSumOfForecast(null);
            }
            re.setSumOfAbsDiff(rs.getDouble("TOTAL_ABS_DIFF_CONSUMPTION"));
            if (rs.wasNull()) {
                re.setSumOfAbsDiff(null);
            }
            fe.addRegionData(re);
        }
        return feList;
    }
    
}
