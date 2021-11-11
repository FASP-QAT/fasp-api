/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ActualConsumptionDataOutputRowMapper implements RowMapper<ActualConsumptionDataOutput> {

    @Override
    public ActualConsumptionDataOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActualConsumptionDataOutput acd = new ActualConsumptionDataOutput();
        acd.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        acd.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REG_").mapRow(rs, rowNum)));
        acd.setMonth(rs.getDate("CONSUMPTION_DATE"));
        acd.setActualConsumption(rs.getDouble("CONSUMPTION_QTY"));
        if (rs.wasNull()) {
            acd.setActualConsumption(null);
        }
        return acd;
    }

}
