/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SupplyPlanCommitRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SupplyPlanCommitRequestRowMapper implements RowMapper<SupplyPlanCommitRequest>{

    @Override
    public SupplyPlanCommitRequest mapRow(ResultSet rs, int i) throws SQLException {
        SupplyPlanCommitRequest spcr = new SupplyPlanCommitRequest();
        spcr.setCommitRequestId(rs.getInt("COMMIT_REQUEST_ID"));
        spcr.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        spcr.setCommittedVersionId(rs.getInt("COMMITTED_VERSION_ID"));
        spcr.setVersionType(new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)));
        spcr.setNotes(rs.getString("NOTES"));
        spcr.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
        spcr.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        spcr.setCompletedDate(rs.getTimestamp("COMPLETED_DATE"));
        spcr.setStatus(rs.getInt("STATUS"));
        return spcr;
    }
    
}
