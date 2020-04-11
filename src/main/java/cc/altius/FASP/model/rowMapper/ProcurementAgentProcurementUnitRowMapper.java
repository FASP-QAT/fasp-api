/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentProcurementUnitRowMapper implements RowMapper<ProcurementAgentProcurementUnit>{

    @Override
    public ProcurementAgentProcurementUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgentProcurementUnit papu = new ProcurementAgentProcurementUnit(
                rs.getInt("PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID"),
                new SimpleObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("PROCUREMENT_UNIT_ID"), new LabelRowMapper("PROCUREMENT_UNIT_").mapRow(rs, rowNum)),
                rs.getString("SKU_CODE"),
                rs.getDouble("VENDOR_PRICE"),
                rs.getInt("APPROVED_TO_SHIPPED_LEAD_TIME"),
                rs.getString("GTIN"));
        papu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return papu;
    }
    
}
