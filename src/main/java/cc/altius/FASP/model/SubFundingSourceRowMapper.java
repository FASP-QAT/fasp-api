/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.model.rowMapper.LabelRowMapper;
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
        s.setActive(rs.getBoolean("ACTIVE"));
        s.setLabel(new LabelRowMapper("SF_").mapRow(rs, i));
        return s;

    }
}
