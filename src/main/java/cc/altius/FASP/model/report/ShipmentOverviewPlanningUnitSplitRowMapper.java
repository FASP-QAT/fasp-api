/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentOverviewPlanningUnitSplitRowMapper implements RowMapper<ShipmentOverviewPlanningUnitSplit> {

    @Override
    public ShipmentOverviewPlanningUnitSplit mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentOverviewPlanningUnitSplit sopu = new ShipmentOverviewPlanningUnitSplit();
        sopu.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        sopu.setMultiplier(rs.getInt("MULTIPLIER"));
        sopu.setPlannedShipmentQty(rs.getDouble("PLANNED_SHIPMENT_QTY"));
        sopu.setOrderedShipmentQty(rs.getDouble("ORDERED_SHIPMENT_QTY"));
        return sopu;
    }

}
