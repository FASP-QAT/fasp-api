/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Criticality;
import cc.altius.FASP.model.Problem;
import cc.altius.FASP.model.RealmProblem;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class RealmProblemRowMapper implements RowMapper<RealmProblem> {

    @Override
    public RealmProblem mapRow(ResultSet rs, int i) throws SQLException {
        RealmProblem rp = new RealmProblem();
        rp.setRealmProblemId(rs.getInt("REALM_PROBLEM_ID"));
        rp.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        rp.setProblem(new Problem(rs.getInt("PROBLEM_ID"), new LabelRowMapper("PROBLEM_").mapRow(rs, i), rs.getString("ACTION_URL")));
        rp.setCriticality(new Criticality(rs.getInt("CRITICALITY_ID"), new LabelRowMapper("CRITICALITY_").mapRow(rs, i), rs.getString("COLOR_HTML_CODE")));
        rp.setData1(rs.getString("DATA1"));
        rp.setData2(rs.getString("DATA2"));
        rp.setData3(rs.getString("DATA3"));
        rp.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return rp;
    }

}
