/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentDetailsMonthRowMapper implements RowMapper<ShipmentDetailsMonth> {

    @Override
    public ShipmentDetailsMonth mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentDetailsMonth sdm = new ShipmentDetailsMonth();
        sdm.setDt(rs.getDate("MONTH"));
        sdm.setPlannedCost(rs.getDouble("PLANNED_COST"));
        sdm.setSubmittedCost(rs.getDouble("SUBMITTED_COST"));
        sdm.setApprovedCost(rs.getDouble("APPROVED_COST"));
        sdm.setShippedCost(rs.getDouble("SHIPPED_COST"));
        sdm.setArrivedCost(rs.getDouble("ARRIVED_COST"));
        sdm.setReceivedCost(rs.getDouble("RECEIVED_COST"));
        sdm.setOnholdCost(rs.getDouble("ONHOLD_COST"));
        return sdm;
    }

}
