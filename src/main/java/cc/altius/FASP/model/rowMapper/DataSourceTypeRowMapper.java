/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.SimpleCodeObject;
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
        DataSourceType dst = new DataSourceType(rs.getInt("DATA_SOURCE_TYPE_ID"), new LabelRowMapper().mapRow(rs, i), new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        dst.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return dst;
    }

}
