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

    private String prefix;
    private boolean skipBasicModel;

    public RealmProblemRowMapper() {
        this.prefix = "";
        this.skipBasicModel = false;
    }

    public RealmProblemRowMapper(String prefix, boolean skipBasicModel) {
        this.prefix = prefix;
        this.skipBasicModel = skipBasicModel;
    }

    @Override
    public RealmProblem mapRow(ResultSet rs, int i) throws SQLException {
        RealmProblem rp = new RealmProblem();
        rp.setRealmProblemId(rs.getInt(prefix + "REALM_PROBLEM_ID"));
        rp.setRealm(new SimpleCodeObject(rs.getInt(prefix + "REALM_ID"), new LabelRowMapper(prefix + "REALM_").mapRow(rs, i), rs.getString(prefix + "REALM_CODE")));
        rp.setProblem(new Problem(rs.getInt(prefix + "PROBLEM_ID"), new LabelRowMapper(prefix + "PROBLEM_").mapRow(rs, i), rs.getString(prefix + "ACTION_URL"), new LabelRowMapper(prefix + "ACTION_").mapRow(rs, i)));
        rp.setCriticality(new Criticality(rs.getInt(prefix + "CRITICALITY_ID"), new LabelRowMapper(prefix + "CRITICALITY_").mapRow(rs, i), rs.getString(prefix + "COLOR_HTML_CODE")));
        rp.setData1(rs.getString(prefix + "DATA1"));
        rp.setData2(rs.getString(prefix + "DATA2"));
        rp.setData3(rs.getString(prefix + "DATA3"));
        if (!this.skipBasicModel) {
            rp.setBaseModel(new BaseModelRowMapper(prefix).mapRow(rs, i));
        } else {
            rp.setActive(true);
        }
        return rp;
    }

}
