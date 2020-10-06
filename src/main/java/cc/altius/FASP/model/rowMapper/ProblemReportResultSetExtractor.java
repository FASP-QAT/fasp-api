/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ProblemReport;
import cc.altius.FASP.model.ProblemReportTrans;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
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
public class ProblemReportResultSetExtractor implements ResultSetExtractor<List<ProblemReport>> {

    @Override
    public List<ProblemReport> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProblemReport> prList = new LinkedList<>();
        while (rs.next()) {
            ProblemReport pr = new ProblemReport();
            pr.setProblemReportId(rs.getInt("PROBLEM_REPORT_ID"));
            int idx = prList.indexOf(pr);
            if (idx == -1) {
                pr.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")));
                pr.setVersionId(rs.getInt("VERSION_ID"));
                pr.setDt(rs.getString("DT")); // Date
                pr.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1))); // Region
                if (pr.getRegion().getId() == 0) {
                    pr.getRegion().setId(null);
                }
                pr.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1))); // Planning Unit

                if (pr.getPlanningUnit().getId() == 0) {
                    pr.getPlanningUnit().setId(null);
                }
                pr.setShipmentId(rs.getInt("SHIPMENT_ID")); // Shipment Id
                if (rs.wasNull()) {
                    pr.setShipmentId(null);
                }
                pr.setData5(rs.getString("DATA5"));
                pr.setProblemStatus(new SimpleObject(rs.getInt("PROBLEM_STATUS_ID"), new LabelRowMapper("PROBLEM_STATUS_").mapRow(rs, 1)));
                pr.setProblemType(new SimpleObject(rs.getInt("PROBLEM_TYPE_ID"), new LabelRowMapper("PROBLEM_TYPE_").mapRow(rs, 1)));
                pr.setRealmProblem(new RealmProblemRowMapper("RP_", true).mapRow(rs, 1));
                pr.setReviewed(rs.getBoolean("REVIEWED"));
                pr.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
                pr.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                pr.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
                pr.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
                prList.add(pr);
            } else {
                pr = prList.get(idx);
            }
            ProblemReportTrans prt = new ProblemReportTrans();
            prt.setProblemReportTransId(rs.getInt("PROBLEM_REPORT_TRANS_ID"));
            prt.setNotes(rs.getString("NOTES"));
            prt.setCreatedBy(new BasicUser(rs.getInt("CBT_USER_ID"), rs.getString("CBT_USERNAME")));
            prt.setCreatedDate(rs.getTimestamp("TRANS_CREATED_DATE"));
            prt.setProblemStatus(new SimpleObject(rs.getInt("PROBLEM_STATUS_TRANS_ID"), new LabelRowMapper("PROBLEM_STATUS_TRANS_").mapRow(rs, 1)));
            prt.setReviewed(rs.getBoolean("PROBLEM_REPORT_TRANS_REVIEWED"));
            pr.getProblemTransList().add(prt);
        }
        return prList;
    }

}
