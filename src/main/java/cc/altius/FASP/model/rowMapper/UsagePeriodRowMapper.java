/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UsagePeriod;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UsagePeriodRowMapper implements RowMapper<UsagePeriod> {

    @Override
    public UsagePeriod mapRow(ResultSet rs, int i) throws SQLException {
        UsagePeriod up = new UsagePeriod(rs.getInt("USAGE_PERIOD_ID"), new LabelRowMapper().mapRow(rs, i), rs.getDouble("CONVERT_TO_MONTH"));
        up.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return up;
    }

}
