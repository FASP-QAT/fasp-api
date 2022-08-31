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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ProcurementAgentResultSetExtractor implements ResultSetExtractor<ProcurementAgent> {

    @Override
    public ProcurementAgent extractData(ResultSet rs) throws SQLException, DataAccessException {
        ProcurementAgent pa = null;
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                pa = new ProcurementAgent(
                        rs.getInt("PROCUREMENT_AGENT_ID"),
                        new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")),
                        new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_TYPE_ID"), new LabelRowMapper("PAT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_TYPE_CODE")),
                        new LabelRowMapper().mapRow(rs, 1),
                        rs.getString("PROCUREMENT_AGENT_CODE"),
                        rs.getDouble("SUBMITTED_TO_APPROVED_LEAD_TIME"),
                        rs.getDouble("APPROVED_TO_SHIPPED_LEAD_TIME")
                );
                pa.setColorHtmlCode(rs.getString("COLOR_HTML_CODE"));
                pa.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                isFirst = false;
            }
            pa.getProgramList().add(new SimpleCodeObjectRowMapper("P_").mapRow(rs, 1));
        }
        return pa;
    }

}
