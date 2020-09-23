/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleBatchQuantity;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SimplifiedSupplyPlanResultSetExtractor implements ResultSetExtractor<List<SimplifiedSupplyPlan>> {

    @Override
    public List<SimplifiedSupplyPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SimplifiedSupplyPlan> spList = new LinkedList<>();
        while (rs.next()) {
            SimplifiedSupplyPlan sp = new SimplifiedSupplyPlan(rs.getInt("PROGRAM_ID"), rs.getInt("VERSION_ID"), rs.getInt("PLANNING_UNIT_ID"), rs.getString("TRANS_DATE"));
            int idx = spList.indexOf(sp);
            if (idx == -1) {
                sp.setOpeningBalance(rs.getInt("OPENING_BALANCE"));
                sp.setOpeningBalanceWps(rs.getInt("OPENING_BALANCE_WPS"));
                sp.setActualFlag(rs.getBoolean("ACTUAL_FLAG"));
                if (rs.wasNull()) {
                    sp.setActualFlag(null);
                }
                sp.setConsumptionQty(rs.getInt("CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    sp.setConsumptionQty(null);
                }
                sp.setAdjustmentQty(rs.getInt("ADJUSTMENT_MULTIPLIED_QTY"));
                sp.setStockQty(rs.getInt("STOCK_MULTIPLIED_QTY"));
                sp.setRegionCount(rs.getInt("REGION_COUNT"));
                sp.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
                sp.setPlannedShipmentsTotalData(rs.getInt("MANUAL_PLANNED_SHIPMENT_QTY"));
                sp.setSubmittedShipmentsTotalData(rs.getInt("MANUAL_SUBMITTED_SHIPMENT_QTY"));
                sp.setApprovedShipmentsTotalData(rs.getInt("MANUAL_APPROVED_SHIPMENT_QTY"));
                sp.setShippedShipmentsTotalData(rs.getInt("MANUAL_SHIPPED_SHIPMENT_QTY"));
                sp.setReceivedShipmentsTotalData(rs.getInt("MANUAL_RECEIVED_SHIPMENT_QTY"));
                sp.setOnholdShipmentsTotalData(rs.getInt("MANUAL_ONHOLD_SHIPMENT_QTY"));
                sp.setPlannedErpShipmentsTotalData(rs.getInt("ERP_PLANNED_SHIPMENT_QTY"));
                sp.setSubmittedErpShipmentsTotalData(rs.getInt("ERP_SUBMITTED_SHIPMENT_QTY"));
                sp.setApprovedErpShipmentsTotalData(rs.getInt("ERP_APPROVED_SHIPMENT_QTY"));
                sp.setShippedErpShipmentsTotalData(rs.getInt("ERP_SHIPPED_SHIPMENT_QTY"));
                sp.setReceivedErpShipmentsTotalData(rs.getInt("ERP_RECEIVED_SHIPMENT_QTY"));
                sp.setOnholdErpShipmentsTotalData(rs.getInt("ERP_ONHOLD_SHIPMENT_QTY"));
                sp.setExpiredStock(rs.getInt("EXPIRED_STOCK"));
                sp.setExpiredStockWps(rs.getInt("EXPIRED_STOCK_WPS"));
                sp.setClosingBalance(rs.getInt("CLOSING_BALANCE"));
                sp.setClosingBalanceWps(rs.getInt("CLOSING_BALANCE_WPS"));
                sp.setUnmetDemand(rs.getInt("UNMET_DEMAND"));
                sp.setUnmetDemandWps(rs.getInt("UNMET_DEMAND_WPS"));
                sp.setNationalAdjustment(rs.getInt("NATIONAL_ADJUSTMENT"));
                sp.setNationalAdjustmentWps(rs.getInt("NATIONAL_ADJUSTMENT_WPS"));
                sp.setAmc(rs.getDouble("AMC"));
                sp.setAmcCount(rs.getInt("AMC_COUNT"));
                sp.setMos(rs.getDouble("MOS"));
                sp.setMosWps(rs.getDouble("MOS_WPS"));
                sp.setMinStockMoS(rs.getDouble("MIN_STOCK_MOS"));
                sp.setMinStock(rs.getDouble("MIN_STOCK_QTY"));
                sp.setMaxStockMoS(rs.getDouble("MAX_STOCK_MOS"));
                sp.setMaxStock(rs.getDouble("MAX_STOCK_QTY"));
                spList.add(sp);
            }
            idx = spList.indexOf(sp);
            sp = spList.get(idx);
            Integer batchId = rs.getInt("BATCH_ID");
            if (!rs.wasNull()) {
                SimpleBatchQuantity sb = new SimpleBatchQuantity(batchId, rs.getString("BATCH_NO"), rs.getDate("EXPIRY_DATE"), rs.getBoolean("AUTO_GENERATED"), rs.getInt("BATCH_CLOSING_BALANCE"), rs.getInt("BATCH_CLOSING_BALANCE_WPS"), rs.getInt("BATCH_EXPIRED_STOCK"), rs.getInt("BATCH_EXPIRED_STOCK_WPS"), rs.getDate("BATCH_CREATED_DATE"));
                sp.getBatchDetails().add(sb);
            }
        }
        return spList;
    }

}
