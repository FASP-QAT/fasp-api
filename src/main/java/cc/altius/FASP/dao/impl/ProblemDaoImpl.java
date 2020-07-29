/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProblemDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProblemReport;
import cc.altius.FASP.model.RealmProblem;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.ProblemReportResultSetExtractor;
import cc.altius.FASP.model.rowMapper.RealmProblemRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
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
    private static final String problemMasterSql = "SELECT  "
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
            + "WHERE rp.ACTIVE AND p.ACTIVE ";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<RealmProblem> getProblemListByRealmId(int realmId, CustomUserDetails curUser) {
        String sql = this.problemMasterSql + " AND rp.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        return this.namedParameterJdbcTemplate.query(sql, params, new RealmProblemRowMapper());
    }

    @Override
    public List<ProblemReport> getProblemReportList(int programId, int versionId, CustomUserDetails curUser) {
        String sql = "SELECT  "
                + "	prr.PROBLEM_REPORT_ID,  "
                + "     prog.PROGRAM_ID, prog.PROGRAM_CODE, prog.LABEL_ID `PROGRAM_LABEL_ID`, prog.LABEL_EN `PROGRAM_LABEL_EN`, prog.LABEL_FR `PROGRAM_LABEL_FR`, prog.LABEL_SP `PROGRAM_LABEL_SP`, prog.LABEL_PR `PROGRAM_LABEL_PR`,  "
                + "     prr.VERSION_ID, prr.DATA1, prr.DATA2, prr.DATA3, prr.DATA4, prr.DATA5, "
                + "	ps.PROBLEM_STATUS_ID, ps.LABEL_ID `PROBLEM_STATUS_LABEL_ID`, ps.LABEL_EN `PROBLEM_STATUS_LABEL_EN`, ps.LABEL_FR `PROBLEM_STATUS_LABEL_FR`, ps.LABEL_SP `PROBLEM_STATUS_LABEL_SP`, ps.LABEL_PR `PROBLEM_STATUS_LABEL_PR`, "
                + "     pt.PROBLEM_TYPE_ID, pt.LABEL_ID `PROBLEM_TYPE_LABEL_ID`, pt.LABEL_EN `PROBLEM_TYPE_LABEL_EN`, pt.LABEL_FR `PROBLEM_TYPE_LABEL_FR`, pt.LABEL_SP `PROBLEM_TYPE_LABEL_SP`, pt.LABEL_PR `PROBLEM_TYPE_LABEL_PR`, "
                + "     rp.REALM_PROBLEM_ID `RP_REALM_PROBLEM_ID`, r.REALM_ID `RP_REALM_ID`, r.REALM_CODE `RP_REALM_CODE`, r.LABEL_ID `RP_REALM_LABEL_ID`, r.LABEL_EN `RP_REALM_LABEL_EN`, r.LABEL_FR `RP_REALM_LABEL_FR`, r.LABEL_SP `RP_REALM_LABEL_SP`, r.LABEL_PR `RP_REALM_LABEL_PR`, "
                + "	p.PROBLEM_ID `RP_PROBLEM_ID`, p.LABEL_ID `RP_PROBLEM_LABEL_ID`, p.LABEL_EN `RP_PROBLEM_LABEL_EN`, p.LABEL_FR `RP_PROBLEM_LABEL_FR`, p.LABEL_SP `RP_PROBLEM_LABEL_SP`, p.LABEL_PR `RP_PROBLEM_LABEL_PR`, p.`ACTION_URL` `RP_ACTION_URL`, "
                + "     pc.CRITICALITY_ID `RP_CRITICALITY_ID`, pc.COLOR_HTML_CODE `RP_COLOR_HTML_CODE`, pc.LABEL_ID `RP_CRITICALITY_LABEL_ID`, pc.LABEL_EN `RP_CRITICALITY_LABEL_EN`, pc.LABEL_FR `RP_CRITICALITY_LABEL_FR`, pc.LABEL_SP `RP_CRITICALITY_LABEL_SP`, pc.LABEL_PR `RP_CRITICALITY_LABEL_PR`, "
                + "     rp.DATA1 `RP_DATA1`, rp.DATA2 `RP_DATA2`, rp.DATA3 `RP_DATA3`, "
                + "     cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, prr.CREATED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, prr.LAST_MODIFIED_DATE, "
                + "     prt.PROBLEM_REPORT_TRANS_ID, prt.NOTES, prt.CREATED_DATE `TRANS_CREATED_DATE`, cbt.USER_ID `CBT_USER_ID`, cbt.USERNAME `CBT_USERNAME`, "
                + "     pst.PROBLEM_STATUS_ID `PROBLEM_STATUS_TRANS_ID`, pst.LABEL_ID `PROBLEM_STATUS_TRANS_LABEL_ID`, pst.LABEL_EN `PROBLEM_STATUS_TRANS_LABEL_EN`, pst.LABEL_FR `PROBLEM_STATUS_TRANS_LABEL_FR`, pst.LABEL_SP `PROBLEM_STATUS_TRANS_LABEL_SP`, pst.LABEL_PR `PROBLEM_STATUS_TRANS_LABEL_PR` "
                + "FROM rm_problem_report prr "
                + "     LEFT JOIN rm_realm_problem rp ON prr.REALM_PROBLEM_ID=rp.REALM_PROBLEM_ID "
                + "     LEFT JOIN vw_problem p ON p.PROBLEM_ID=rp.PROBLEM_ID "
                + "     LEFT JOIN vw_problem_criticality pc ON pc.CRITICALITY_ID=rp.CRITICALITY_ID "
                + "     LEFT JOIN vw_problem_type pt ON prr.PROBLEM_TYPE_ID=pt.PROBLEM_TYPE_ID "
                + "     LEFT JOIN vw_problem_status ps ON prr.PROBLEM_STATUS_ID=ps.PROBLEM_STATUS_ID "
                + "	LEFT JOIN us_user cb ON prr.CREATED_BY=cb.USER_ID "
                + "     LEFT JOIN us_user lmb ON prr.LAST_MODIFIED_BY=lmb.USER_ID "
                + "     LEFT JOIN vw_program prog ON prr.PROGRAM_ID=prog.PROGRAM_ID "
                + "     LEFT JOIN vw_realm r ON rp.REALM_ID=r.REALM_ID "
                + "     LEFT JOIN rm_problem_report_trans prt ON prr.PROBLEM_REPORT_ID=prt.PROBLEM_REPORT_ID "
                + "     LEFT JOIN vw_problem_status pst ON prt.PROBLEM_STATUS_ID=pst.PROBLEM_STATUS_ID "
                + "     LEFT JOIN us_user cbt ON prt.CREATED_BY=cbt.USER_ID "
                + "WHERE prr.PROGRAM_ID=:programId AND prr.VERSION_ID<=:versionId "
                + "ORDER BY prr.PROBLEM_REPORT_ID, prt.PROBLEM_REPORT_TRANS_ID";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query(sql, params, new ProblemReportResultSetExtractor());
    }

    @Override
    public List<RealmProblem> getProblemListForSync(int realmId, String lastModifiedDate, CustomUserDetails curUser) {
        String sql = this.problemMasterSql + " AND rp.REALM_ID=:realmId AND rp.LAST_MODIFIED_DATE>:lastModifiedDate ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("lastModifiedDate", lastModifiedDate);
        return this.namedParameterJdbcTemplate.query(sql, params, new RealmProblemRowMapper());
    }

    @Override
    public List<SimpleObject> getProblemStatusForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.namedParameterJdbcTemplate.query("SELECT ps.PROBLEM_STATUS_ID `ID`, ps.LABEL_ID, ps.LABEL_EN, ps.LABEL_FR, ps.LABEL_SP, ps.LABEL_PR FROM vw_problem_status ps", new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getProblemCriticalityForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.namedParameterJdbcTemplate.query("SELECT ps.CRITICALITY_ID `ID`, ps.LABEL_ID, ps.LABEL_EN, ps.LABEL_FR, ps.LABEL_SP, ps.LABEL_PR FROM vw_problem_criticality ps", new SimpleObjectRowMapper());
    }

}
