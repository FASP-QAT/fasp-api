/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.LoadVersion;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class LoadProgramVersionRowMapper implements RowMapper<LoadVersion> {

    @Override
    public LoadVersion mapRow(ResultSet rs, int i) throws SQLException {
        LoadVersion lv = new LoadVersion();
        lv.setVersionId(rs.getString("VERSION_ID"));
        lv.setVersionType(new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)));
        lv.setVersionStatus(new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VERSION_STATUS_").mapRow(rs, i)));
        lv.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
        lv.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
        lv.setCreatedBy(new BasicUser(rs.getInt("USER_ID"), rs.getString("USERNAME")));
        lv.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        return lv;
    }

}
