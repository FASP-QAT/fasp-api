/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SubFundingSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class SubFundingSourceRowMapper implements RowMapper<SubFundingSource> {

    @Override
    public SubFundingSource mapRow(ResultSet rs, int i) throws SQLException {
        SubFundingSource s = new SubFundingSource();
        s.setSubFundingSourceId(rs.getInt("SUB_FUNDING_SOURCE_ID"));
        s.setLabel(new LabelRowMapper().mapRow(rs, i));
        s.setFundingSource(
                new FundingSource(
                        rs.getInt("FUNDING_SOURCE_ID"),
                        new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i),
                        new SimpleCodeObject(
                                rs.getInt("REALM_ID"),
                                new LabelRowMapper("REALM_").mapRow(rs, i),
                                rs.getString("REALM_CODE")
                        )
                )
        );
        s.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return s;

    }
}
