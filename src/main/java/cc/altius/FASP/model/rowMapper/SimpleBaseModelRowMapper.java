/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleBaseModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleBaseModelRowMapper implements RowMapper<SimpleBaseModel> {

    @Override
    public SimpleBaseModel mapRow(ResultSet rs, int i) throws SQLException {
        SimpleBaseModel s = new SimpleBaseModel(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, i));
        s.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return s;
    }

}
