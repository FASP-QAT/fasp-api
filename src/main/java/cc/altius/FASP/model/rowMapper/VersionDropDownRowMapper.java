/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class VersionDropDownRowMapper implements RowMapper<Version> {

    @Override
    public Version mapRow(ResultSet rs, int rowNum) throws SQLException {
        Version v = new Version();
        v.setVersionId(rs.getInt("VERSION_ID"));
        v.setVersionType(new SimpleObjectRowMapper("VERSION_TYPE_").mapRow(rs, 1));
        v.setVersionStatus(new SimpleObjectRowMapper("VERSION_STATUS_").mapRow(rs, 1));
        v.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        v.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
        v.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
        return v;
    }

}
