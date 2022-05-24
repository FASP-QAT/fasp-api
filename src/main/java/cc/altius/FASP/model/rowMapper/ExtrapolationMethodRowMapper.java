/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ExtrapolationMethod;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ExtrapolationMethodRowMapper implements RowMapper<ExtrapolationMethod>{

    @Override
    public ExtrapolationMethod mapRow(ResultSet rs, int i) throws SQLException {
        ExtrapolationMethod s = new ExtrapolationMethod(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, i), rs.getInt("SORT_ORDER"));
        s.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return s;
    }
    
}
