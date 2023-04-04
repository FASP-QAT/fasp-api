/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastMethod;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForecastMethodRowMapper implements RowMapper<ForecastMethod> {

    @Override
    public ForecastMethod mapRow(ResultSet rs, int i) throws SQLException {
        ForecastMethod fm = new ForecastMethod(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper().mapRow(rs, i), rs.getInt("FORECAST_METHOD_TYPE_ID"));
        fm.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return fm;
    }

}
