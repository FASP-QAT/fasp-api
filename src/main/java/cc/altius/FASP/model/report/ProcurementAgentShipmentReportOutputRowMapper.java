/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentShipmentReportOutputRowMapper implements RowMapper<ProcurementAgentShipmentReportOutput> {

    @Override
    public ProcurementAgentShipmentReportOutput mapRow(ResultSet rs, int i) throws SQLException {
        ProcurementAgentShipmentReportOutput paro = new ProcurementAgentShipmentReportOutput();
        paro.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        paro.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        paro.setQty(rs.getInt("QTY"));
        paro.setFreightCost(rs.getDouble("FREIGHT_COST"));
        paro.setFreightPerc(rs.getDouble("FREIGHT_PERC"));
        paro.setProductCost(rs.getDouble("PRODUCT_COST"));
        return paro;
    }

}
