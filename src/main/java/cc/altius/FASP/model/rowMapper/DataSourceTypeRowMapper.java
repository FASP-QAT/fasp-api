/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class DataSourceTypeRowMapper implements RowMapper<DataSourceType> {

    @Override
    public DataSourceType mapRow(ResultSet rs, int i) throws SQLException {
        DataSourceType dt = new DataSourceType();
        dt.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        dt.setActive(rs.getBoolean("ACTIVE"));
        dt.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
        return dt;
    }

}
