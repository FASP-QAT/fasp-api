/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class VersionRowMapper implements RowMapper<Version> {

    @Override
    public Version mapRow(ResultSet rs, int i) throws SQLException {
        Version v = new Version(
                rs.getInt("VERSION_ID"),
                new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VERSION_STATUS_").mapRow(rs, i)),
                rs.getString("NOTES"),
                new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")),
                rs.getTimestamp("CREATED_DATE"),
                new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")),
                rs.getTimestamp("LAST_MODIFIED_DATE"));
        v.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
        v.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
        return v;
    }

}
