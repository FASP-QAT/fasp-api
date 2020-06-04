/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentRowMapper implements RowMapper<ProcurementAgent> {

    @Override
    public ProcurementAgent mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgent pa = new ProcurementAgent(
                rs.getInt("PROCUREMENT_AGENT_ID"),
                //                new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")), 
                new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                new LabelRowMapper().mapRow(rs, rowNum),
                rs.getString("PROCUREMENT_AGENT_CODE"),
                rs.getDouble("SUBMITTED_TO_APPROVED_LEAD_TIME"),
                rs.getBoolean("LOCAL_PROCUREMENT_AGENT")
        );
        pa.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return pa;
    }

}
