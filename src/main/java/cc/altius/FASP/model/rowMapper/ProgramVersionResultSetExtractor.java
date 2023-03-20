/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
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
public class ProgramVersionResultSetExtractor implements ResultSetExtractor<List<ProgramVersion>> {

    @Override
    public List<ProgramVersion> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProgramVersion> pvList = new LinkedList<>();
        while (rs.next()) {
            ProgramVersion pv = new ProgramVersion(rs.getInt("PROGRAM_VERSION_ID"),
                    new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                    new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1), rs.getString("COUNTRY_CODE")),
                    new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")),
                    new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1), rs.getString("ORGANISATION_CODE")),
                    rs.getInt("VERSION_ID"),
                    new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, 1)),
                    new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VERSION_STATUS_").mapRow(rs, 1)),
                    rs.getString("NOTES"),
                    new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")),
                    rs.getTimestamp("CREATED_DATE"),
                    new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")),
                    rs.getTimestamp("LAST_MODIFIED_DATE"));
            pv.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
            pv.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
            pv.setDaysInMonth(rs.getInt("DAYS_IN_MONTH"));
            if (rs.wasNull()) {
                pv.setDaysInMonth(null);
            }
            pv.setFreightPerc(rs.getDouble("FREIGHT_PERC"));
            if (rs.wasNull()) {
                pv.setFreightPerc(null);
            }
            pv.setForecastThresholdHighPerc(rs.getDouble("FORECAST_THRESHOLD_HIGH_PERC"));
            if (rs.wasNull()) {
                pv.setForecastThresholdHighPerc(null);
            }
            pv.setForecastThresholdLowPerc(rs.getDouble("FORECAST_THRESHOLD_LOW_PERC"));
            if (rs.wasNull()) {
                pv.setForecastThresholdLowPerc(null);
            }
            int idx = pvList.indexOf(pv);
            if (idx == -1) {
                pvList.add(pv);
            } else {
                pv = pvList.get(idx);
                pv.addHeathArea(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
            }
        }
        return pvList;
    }

}
