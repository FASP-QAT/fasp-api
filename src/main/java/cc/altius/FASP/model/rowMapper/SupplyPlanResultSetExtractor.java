/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.SupplyPlanBatchInfo;
import cc.altius.FASP.model.SupplyPlanDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SupplyPlanResultSetExtractor implements ResultSetExtractor<SupplyPlan> {

    @Override
    public SupplyPlan extractData(ResultSet rs) throws SQLException, DataAccessException {
        SupplyPlan sp = new SupplyPlan();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                sp.setProgramId(rs.getInt("PROGRAM_ID"));
                sp.setVersionId(rs.getInt("VERSION_ID"));
                sp.setSupplyPlanDateList(new LinkedList<>());
                isFirst = false;
            }
            SupplyPlanDate spd = new SupplyPlanDate(rs.getInt("PLANNING_UNIT_ID"), rs.getString("TRANS_DATE"));
            int idx = sp.getSupplyPlanDateList().indexOf(spd);
            if (idx == -1) {
                sp.getSupplyPlanDateList().add(spd);
            } else {
                spd = sp.getSupplyPlanDateList().get(idx);
            }
            SupplyPlanBatchInfo spbi = new SupplyPlanBatchInfo(rs.getInt("BATCH_ID"));
            idx = spd.getBatchList().indexOf(spbi);
            if (idx == -1) {
                spd.getBatchList().add(spbi);
            } else {
                spbi = spd.getBatchList().get(idx);
            }
            spbi.setSupplyPlanId(rs.getInt("SUPPLY_PLAN_BATCH_INFO_ID"));
            spbi.setExpiryDate(rs.getString("EXPIRY_DATE"));
            spbi.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
            spbi.setManualPlannedShipmentQty(rs.getInt("MANUAL_PLANNED_SHIPMENT_QTY"));
            spbi.setManualSubmittedShipmentQty(rs.getInt("MANUAL_SUBMITTED_SHIPMENT_QTY"));
            spbi.setManualApprovedShipmentQty(rs.getInt("MANUAL_APPROVED_SHIPMENT_QTY"));
            spbi.setManualShippedShipmentQty(rs.getInt("MANUAL_SHIPPED_SHIPMENT_QTY"));
            spbi.setManualReceivedShipmentQty(rs.getInt("MANUAL_RECEIVED_SHIPMENT_QTY"));
            spbi.setManualOnholdShipmentQty(rs.getInt("MANUAL_ONHOLD_SHIPMENT_QTY"));
            spbi.setErpPlannedShipmentQty(rs.getInt("ERP_PLANNED_SHIPMENT_QTY"));
            spbi.setErpSubmittedShipmentQty(rs.getInt("ERP_SUBMITTED_SHIPMENT_QTY"));
            spbi.setErpApprovedShipmentQty(rs.getInt("ERP_APPROVED_SHIPMENT_QTY"));
            spbi.setErpShippedShipmentQty(rs.getInt("ERP_SHIPPED_SHIPMENT_QTY"));
            spbi.setErpReceivedShipmentQty(rs.getInt("ERP_RECEIVED_SHIPMENT_QTY"));
            spbi.setErpOnholdShipmentQty(rs.getInt("ERP_ONHOLD_SHIPMENT_QTY"));
            spbi.setConsumption(rs.getInt("CONSUMPTION"));
            spbi.setAdjustment(rs.getInt("ADJUSTMENT_MULTIPLIED_QTY"));
            spbi.setStock(rs.getInt("STOCK_MULTIPLIED_QTY"));
        }
        return sp;
    }

}
