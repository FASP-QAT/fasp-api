/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentType;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentTypeRowMapper implements RowMapper<ProcurementAgentType> {

    @Override
    public ProcurementAgentType mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgentType pa = new ProcurementAgentType(
                rs.getInt("PROCUREMENT_AGENT_TYPE_ID"),
                new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                new LabelRowMapper().mapRow(rs, rowNum),
                rs.getString("PROCUREMENT_AGENT_TYPE_CODE")
        );
        pa.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return pa;
    }

}
