/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class FundingSourceRowMapper implements RowMapper<FundingSource> {
    
    @Override
    public FundingSource mapRow(ResultSet rs, int i) throws SQLException {
        FundingSource m = new FundingSource();
        m.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        m.setFundingSourceCode(rs.getString("FUNDING_SOURCE_CODE"));
        m.setLabel(new LabelRowMapper().mapRow(rs, i));
        m.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        m.setAllowedInBudget(rs.getBoolean("ALLOWED_IN_BUDGET"));
        m.setFundingSourceType(new SimpleCodeObjectRowMapper("FST_").mapRow(rs, i));
        m.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return m;
    }
}
