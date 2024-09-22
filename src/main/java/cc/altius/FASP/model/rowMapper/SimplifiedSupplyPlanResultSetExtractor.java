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
            SimplifiedSupplyPlan sp = new SimplifiedSupplyPlan(rs.getInt("SUPPLY_PLAN_ID"), rs.getInt("PROGRAM_ID"), rs.getInt("VERSION_ID"), rs.getInt("PLANNING_UNIT_ID"), rs.getString("TRANS_DATE"));
            sp.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
            int idx = spList.indexOf(sp);
            if (idx == -1) {
                sp.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
                sp.setOpeningBalanceWps(rs.getDouble("OPENING_BALANCE_WPS"));
                sp.setActualFlag(rs.getBoolean("ACTUAL_FLAG"));
                if (rs.wasNull()) {
                    sp.setActualFlag(null);
                }
                sp.setConsumptionQty(rs.getDouble("CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    sp.setConsumptionQty(null);
                }
                sp.setAdjustmentQty(rs.getDouble("ADJUSTMENT_MULTIPLIED_QTY"));
                if (rs.wasNull()) {
                    sp.setAdjustmentQty(null);
                }
                sp.setStockQty(rs.getDouble("STOCK_MULTIPLIED_QTY"));
                if (rs.wasNull()) {
                    sp.setStockQty(null);
                }
                sp.setRegionCount(rs.getInt("REGION_COUNT"));
                sp.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
                sp.setPlannedShipmentsTotalData(rs.getDouble("MANUAL_PLANNED_SHIPMENT_QTY"));
                sp.setSubmittedShipmentsTotalData(rs.getDouble("MANUAL_SUBMITTED_SHIPMENT_QTY"));
                sp.setApprovedShipmentsTotalData(rs.getDouble("MANUAL_APPROVED_SHIPMENT_QTY"));
                sp.setShippedShipmentsTotalData(rs.getDouble("MANUAL_SHIPPED_SHIPMENT_QTY"));
                sp.setReceivedShipmentsTotalData(rs.getDouble("MANUAL_RECEIVED_SHIPMENT_QTY"));
                sp.setOnholdShipmentsTotalData(rs.getDouble("MANUAL_ONHOLD_SHIPMENT_QTY"));
                sp.setPlannedErpShipmentsTotalData(rs.getDouble("ERP_PLANNED_SHIPMENT_QTY"));
                sp.setSubmittedErpShipmentsTotalData(rs.getDouble("ERP_SUBMITTED_SHIPMENT_QTY"));
                sp.setApprovedErpShipmentsTotalData(rs.getDouble("ERP_APPROVED_SHIPMENT_QTY"));
                sp.setShippedErpShipmentsTotalData(rs.getDouble("ERP_SHIPPED_SHIPMENT_QTY"));
                sp.setReceivedErpShipmentsTotalData(rs.getDouble("ERP_RECEIVED_SHIPMENT_QTY"));
                sp.setOnholdErpShipmentsTotalData(rs.getDouble("ERP_ONHOLD_SHIPMENT_QTY"));
                sp.setExpiredStock(rs.getDouble("EXPIRED_STOCK"));
                sp.setExpiredStockWps(rs.getDouble("EXPIRED_STOCK_WPS"));
                sp.setClosingBalance(rs.getDouble("CLOSING_BALANCE"));
                sp.setClosingBalanceWps(rs.getDouble("CLOSING_BALANCE_WPS"));
                sp.setUnmetDemand(rs.getDouble("UNMET_DEMAND"));
                sp.setUnmetDemandWps(rs.getDouble("UNMET_DEMAND_WPS"));
                sp.setNationalAdjustment(rs.getDouble("NATIONAL_ADJUSTMENT"));
                sp.setNationalAdjustmentWps(rs.getDouble("NATIONAL_ADJUSTMENT_WPS"));
                sp.setAmc(rs.getDouble("AMC"));
                if (rs.wasNull()) {
                    sp.setAmc(null);
                }
                sp.setAmcCount(rs.getInt("AMC_COUNT"));
                sp.setMos(rs.getDouble("MOS"));
                if (rs.wasNull()) {
                    sp.setMos(null);
                }
                sp.setMosWps(rs.getDouble("MOS_WPS"));
                if (rs.wasNull()) {
                    sp.setMosWps(null);
                }
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
                SimpleBatchQuantity sb = new SimpleBatchQuantity(
                        batchId,
                        rs.getString("BATCH_NO"),
                        rs.getDate("EXPIRY_DATE"),
                        rs.getBoolean("AUTO_GENERATED"),
                        rs.getDouble("BATCH_CLOSING_BALANCE"),
                        rs.getDouble("BATCH_CLOSING_BALANCE_WPS"),
                        rs.getDouble("BATCH_EXPIRED_STOCK"),
                        rs.getDouble("BATCH_EXPIRED_STOCK_WPS"),
                        rs.getDate("BATCH_CREATED_DATE"),
                        rs.getDouble("BATCH_SHIPMENT_QTY"),
                        rs.getDouble("BATCH_SHIPMENT_QTY_WPS"));
                sb.setOpeningBalance(rs.getDouble("BATCH_OPENING_BALANCE"));
                sb.setOpeningBalanceWps(rs.getDouble("BATCH_OPENING_BALANCE_WPS"));
                sb.setConsumptionQty(rs.getDouble("BATCH_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    sb.setConsumptionQty(null);
                }
                sb.setStockQty(rs.getDouble("BATCH_STOCK_MULTIPLIED_QTY"));
                if (rs.wasNull()) {
                    sb.setStockQty(null);
                }
                sb.setAdjustmentQty(rs.getDouble("BATCH_ADJUSTMENT_MULTIPLIED_QTY"));
                if (rs.wasNull()) {
                    sb.setAdjustmentQty(null);
                }
                sb.setUnallocatedQty(rs.getDouble("BATCH_CALCULATED_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    sb.setUnallocatedQty(null);
                }
                sb.setUnallocatedQtyWps(rs.getDouble("BATCH_CALCULATED_CONSUMPTION_QTY_WPS"));
                if (rs.wasNull()) {
                    sb.setUnallocatedQtyWps(null);
                }
                sp.getBatchDetails().add(sb);
            }
        }
        return spList;
    }

}
