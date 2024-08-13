/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class FundingSourceListResultSetExtractor implements ResultSetExtractor<List<FundingSource>> {

    @Override
    public List<FundingSource> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<FundingSource> fsList = new LinkedList<>();
        while (rs.next()) {
            FundingSource fs = new FundingSource();
            fs.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
            fs.setFundingSourceCode(rs.getString("FUNDING_SOURCE_CODE"));
            fs.setLabel(new LabelRowMapper().mapRow(rs, 1));
            fs.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
            fs.setAllowedInBudget(rs.getBoolean("ALLOWED_IN_BUDGET"));
            fs.setFundingSourceType(new SimpleCodeObjectRowMapper("FST_").mapRow(rs, 1));
            fs.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            int idx = -1;
            idx = fsList.indexOf(fs);
            if (idx == -1) {
                fsList.add(fs);
            } else {
                fs = fsList.get(idx);
            }
            idx = -1;
            SimpleCodeObject prog = new SimpleCodeObjectRowMapper("P_").mapRow(rs, 1);
            idx = fs.getProgramList().indexOf(prog);
            if (idx == -1) {
                fs.getProgramList().add(prog);
            }
        }
        return fsList;
    }

}
