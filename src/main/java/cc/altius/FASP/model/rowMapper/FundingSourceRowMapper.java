/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSource;
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
        m.setActive(rs.getBoolean("ACTIVE"));
        m.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        m.setLabel(new LabelRowMapper().mapRow(rs, i));
        m.setRealm(new RealmRowMapper().mapRow(rs, i));

        return m;
    }
}
