/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.BaseModelRowMapper;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class FundingSourceResultSetExtractor implements ResultSetExtractor<FundingSource> {

    @Override
    public FundingSource extractData(ResultSet rs) throws SQLException, DataAccessException {
        FundingSource fs = null;
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                fs = new FundingSource();
                fs.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
                fs.setFundingSourceCode(rs.getString("FUNDING_SOURCE_CODE"));
                fs.setLabel(new LabelRowMapper().mapRow(rs, 1));
                fs.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                fs.setAllowedInBudget(rs.getBoolean("ALLOWED_IN_BUDGET"));
                fs.setFundingSourceType(new SimpleCodeObjectRowMapper("FST_").mapRow(rs, 1));
                fs.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));

                isFirst = false;
            }
            fs.getProgramList().add(new SimpleCodeObjectRowMapper("P_").mapRow(rs, 1));
        }
        return fs;
    }

}
