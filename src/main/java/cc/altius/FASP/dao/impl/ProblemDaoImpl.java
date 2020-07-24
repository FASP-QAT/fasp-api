/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProblemDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmProblem;
import cc.altius.FASP.model.rowMapper.RealmProblemRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class ProblemDaoImpl implements ProblemDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<RealmProblem> getProblemListByRealmId(int realmId, CustomUserDetails curUser) {
        String sql = "SELECT  "
                + "     rp.REALM_PROBLEM_ID, r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, "
                + "     p.PROBLEM_ID, p.LABEL_ID `PROBLEM_LABEL_ID`, p.LABEL_EN `PROBLEM_LABEL_EN`, p.LABEL_FR `PROBLEM_LABEL_FR`, p.LABEL_SP `PROBLEM_LABEL_SP`, p.LABEL_PR `PROBLEM_LABEL_PR`, "
                + "     p.ACTION_URL, rp.DATA1, rp.DATA2, rp.DATA3, "
                + "	pc.CRITICALITY_ID, pc.COLOR_HTML_CODE, pc.LABEL_ID `CRITICALITY_LABEL_ID`, pc.LABEL_EN `CRITICALITY_LABEL_EN`, pc.LABEL_FR `CRITICALITY_LABEL_FR`, pc.LABEL_SP `CRITICALITY_LABEL_SP`, pc.LABEL_PR `CRITICALITY_LABEL_PR`, "
                + "     rp.ACTIVE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, rp.CREATED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, rp.LAST_MODIFIED_DATE "
                + "FROM rm_realm_problem rp  "
                + "    LEFT JOIN vw_problem p ON p.PROBLEM_ID=rp.PROBLEM_ID "
                + "    LEFT JOIN vw_problem_criticality pc ON pc.CRITICALITY_ID=rp.CRITICALITY_ID "
                + "	LEFT JOIN us_user cb ON rp.CREATED_BY=cb.USER_ID "
                + "    LEFT JOIN us_user lmb ON rp.LAST_MODIFIED_BY=lmb.USER_ID "
                + "    LEFT JOIN vw_realm r ON rp.REALM_ID=r.REALM_ID "
                + "WHERE rp.REALM_ID=:realmId AND rp.ACTIVE AND p.ACTIVE";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        return this.namedParameterJdbcTemplate.query(sql, params, new RealmProblemRowMapper());
    }

}
