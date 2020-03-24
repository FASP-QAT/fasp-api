/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class DataSourceRowMapper implements RowMapper<DataSource> {

    @Override
    public DataSource mapRow(ResultSet rs, int i) throws SQLException {
        DataSource ds = new DataSource(
                rs.getInt("DATA_SOURCE_ID"), 
                new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")),
                new LabelRowMapper("").mapRow(rs, i),
                new DataSourceType(rs.getInt("DATA_SOURCE_TYPE_ID"), new LabelRowMapper("DATA_SOURCE_TYPE_").mapRow(rs, i))
        );
        ds.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return ds;
    }

}
