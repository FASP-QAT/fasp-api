/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import cc.altius.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class MonthlyForecastOutputListResultSetExtractor implements ResultSetExtractor<List<MonthlyForecastOutput>> {

    private boolean aggregateByYear;

    public MonthlyForecastOutputListResultSetExtractor(boolean aggregateByYear) {
        this.aggregateByYear = aggregateByYear;
    }

    @Override
    public List<MonthlyForecastOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<MonthlyForecastOutput> mfList = new LinkedList<>();
        while (rs.next()) {
            MonthlyForecastOutput mfo = new MonthlyForecastOutput();
            mfo.setPlanningUnit(new SimpleObjectWithMultiplier(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1), rs.getDouble("MULTIPLIER")));
            mfo.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("R_").mapRow(rs, 1)));
            int idx = mfList.indexOf(mfo);
            if (idx == -1) {
                mfo.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, 1)));
                mfList.add(mfo);
            } else {
                mfo = mfList.get(idx);
            }
            MonthlyForecastData mfd = new MonthlyForecastData();
            Calendar cMn = Calendar.getInstance(new Locale(DateUtils.EST));
            Date mn = rs.getDate("MONTH");
            if (mn != null) {
                cMn.setTime(mn);
                if (this.aggregateByYear) {
                    // Aggregate by Year
                    cMn.set(Calendar.DAY_OF_MONTH, 1);
                    cMn.set(Calendar.MONTH, 0);
                    mfd.setMonth(cMn.getTime());
                } else {
                    // Not aggregate by year
                    mfd.setMonth(mn);
                }
                mfo.setSelectedForecast(new LabelRowMapper("SF_").mapRow(rs, 1));
                idx = mfo.getMonthlyForecastData().indexOf(mfd);
                if (idx == -1) {
                    mfd.setConsumptionQty(rs.getDouble("CALCULATED_MMD_VALUE"));
                    if (rs.wasNull()) {
                        mfd.setConsumptionQty(null);
                    }
                    mfo.getMonthlyForecastData().add(mfd);
                } else {
                    mfd = mfo.getMonthlyForecastData().get(idx);
                    Double calculatedMMDValue = rs.getDouble("CALCULATED_MMD_VALUE");
                    if (rs.wasNull()) {
                        calculatedMMDValue = null;
                    }
                    mfd.setConsumptionQty(mfd.getConsumptionQty() == null ? 0 : mfd.getConsumptionQty() + (calculatedMMDValue == null ? 0 : calculatedMMDValue));
                }
            }
        }
        return mfList;
    }

}
