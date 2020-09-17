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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class AnnualShipmentCostOutputRowMapper implements RowMapper<AnnualShipmentCostOutput> {

    @Override
    public AnnualShipmentCostOutput mapRow(ResultSet rs, int i) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        AnnualShipmentCostOutput asco = new AnnualShipmentCostOutput();
        asco.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        asco.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_CODE")));
        asco.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        asco.setShipmentAmt(new HashMap<>());
        for (int x = 1; x <= metaData.getColumnCount(); x++) {
            if(metaData.getColumnName(x).startsWith("YR-")) {
                asco.getShipmentAmt().put(metaData.getColumnName(x), rs.getDouble(x));
            }
        }
        return asco;
    }

}
