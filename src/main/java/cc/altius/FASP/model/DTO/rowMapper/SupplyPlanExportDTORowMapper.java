/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SupplyPlanExportDTORowMapper implements RowMapper<SupplyPlanExportDTO> {
    
    @Override
    public SupplyPlanExportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SupplyPlanExportDTO s = new SupplyPlanExportDTO();
        s.setTransDate(rs.getString("TRANS_DATE"));
        s.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        s.setRegionCount(rs.getInt("REGION_COUNT"));
        s.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
        s.setOpeningBalanceWps(rs.getInt("OPENING_BALANCE_WPS"));
        s.setActualConsumptionQty(rs.getInt("ACTUAL_CONSUMPTION_QTY"));
        if (rs.wasNull()) {
            s.setActualConsumptionQty(null);
        }
        s.setForecastedConsumptionQty(rs.getInt("FORECASTED_CONSUMPTION_QTY"));
        if (rs.wasNull()) {
            s.setForecastedConsumptionQty(null);
        }
        s.setNationalAdjustmentWps(rs.getInt("NATIONAL_ADJUSTMENT_WPS"));
        s.setManualPlannedShipmentQty(rs.getInt("MANUAL_PLANNED_SHIPMENT_QTY"));
        s.setManualSubmittedShipmentQty(rs.getInt("MANUAL_SUBMITTED_SHIPMENT_QTY"));
        s.setManualApprovedShipmentQty(rs.getInt("MANUAL_APPROVED_SHIPMENT_QTY"));
        s.setManualOnholdShipmentQty(rs.getInt("MANUAL_ONHOLD_SHIPMENT_QTY"));
        s.setManualShippedShipmentQty(rs.getInt("MANUAL_SHIPPED_SHIPMENT_QTY"));
        s.setManualReceivedShipmentQty(rs.getInt("MANUAL_RECEIVED_SHIPMENT_QTY"));
        s.setErpPlannedShipmentQty(rs.getInt("ERP_PLANNED_SHIPMENT_QTY"));
        s.setErpSubmittedShipmentQty(rs.getInt("ERP_SUBMITTED_SHIPMENT_QTY"));
        s.setErpApprovedShipmentQty(rs.getInt("ERP_APPROVED_SHIPMENT_QTY"));
        s.setErpOnholdShipmentQty(rs.getInt("ERP_ONHOLD_SHIPMENT_QTY"));
        s.setErpShippedShipmentQty(rs.getInt("ERP_SHIPPED_SHIPMENT_QTY"));
        s.setErpReceivedShipmentQty(rs.getInt("ERP_RECEIVED_SHIPMENT_QTY"));
        s.setExpiredStockWps(rs.getInt("EXPIRED_STOCK_WPS"));
        s.setClosingBalanceWps(rs.getInt("CLOSING_BALANCE_WPS"));
        s.setMosWps(rs.getDouble("MOS_WPS"));
        s.setAmc(rs.getDouble("AMC"));
        s.setAmcCount(rs.getInt("AMC_COUNT"));
        s.setUnmetDemandWps(rs.getInt("UNMET_DEMAND_WPS"));
        s.setMinStockMos(rs.getDouble("MIN_STOCK_MOS"));
        s.setMinStockQty(rs.getDouble("MIN_STOCK_QTY"));
        s.setMaxStockMos(rs.getDouble("MAX_STOCK_MOS"));
        s.setMaxStockQty(rs.getDouble("MAX_STOCK_QTY"));
        return s;
    }
    
}
