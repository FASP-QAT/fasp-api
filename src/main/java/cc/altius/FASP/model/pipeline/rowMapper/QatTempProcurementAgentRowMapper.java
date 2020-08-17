/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.altius.FASP.model.pipeline.rowMapper;
import cc.altius.FASP.model.pipeline.QatTempProcurementAgent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class QatTempProcurementAgentRowMapper implements RowMapper<QatTempProcurementAgent> {
 @Override
    public QatTempProcurementAgent mapRow(ResultSet rs, int arg1) throws SQLException {
QatTempProcurementAgent q=new QatTempProcurementAgent();
q.setProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
q.setPipelineProcurementAgent(rs.getString("PIPELINE_PROCUREMENT_AGENT"));
q.setPipelineProcurementAgentId(rs.getString("PIPELINE_PROCUREMENT_AGENT_ID"));
return q;

}

}
