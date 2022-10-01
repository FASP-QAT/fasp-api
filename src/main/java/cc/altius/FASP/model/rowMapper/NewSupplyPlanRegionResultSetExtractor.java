/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NewSupplyPlan;
import cc.altius.FASP.model.RegionData;
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
public class NewSupplyPlanRegionResultSetExtractor implements ResultSetExtractor<List<NewSupplyPlan>> {

    @Override
    public List<NewSupplyPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<NewSupplyPlan> nspList = new LinkedList<>();
        while (rs.next()) {
            NewSupplyPlan nsp = new NewSupplyPlan(rs.getInt("PLANNING_UNIT_ID"), rs.getString("TRANS_DATE"), rs.getInt("SHELF_LIFE"));
            int idx = nspList.indexOf(nsp);
            if (idx == -1) {
                nspList.add(nsp);
            } else {
                nsp = nspList.get(idx);
            }
            RegionData rd = new RegionData();
            rd.setRegionId(rs.getInt("REGION_ID"));
            if (rs.wasNull()) {
                nsp.addPlannedShipmentsTotalData(rs.getLong("MANUAL_PLANNED_SHIPMENT"));
                nsp.addSubmittedShipmentsTotalData(rs.getLong("MANUAL_SUBMITTED_SHIPMENT"));
                nsp.addApprovedShipmentsTotalData(rs.getLong("MANUAL_APPROVED_SHIPMENT"));
                nsp.addShippedShipmentsTotalData(rs.getLong("MANUAL_SHIPPED_SHIPMENT"));
                nsp.addReceivedShipmentsTotalData(rs.getLong("MANUAL_RECEIVED_SHIPMENT"));
                nsp.addOnholdShipmentsTotalData(rs.getLong("MANUAL_ONHOLD_SHIPMENT"));
                nsp.addPlannedErpShipmentsTotalData(rs.getLong("ERP_PLANNED_SHIPMENT"));
                nsp.addSubmittedErpShipmentsTotalData(rs.getLong("ERP_SUBMITTED_SHIPMENT"));
                nsp.addApprovedErpShipmentsTotalData(rs.getLong("ERP_APPROVED_SHIPMENT"));
                nsp.addShippedErpShipmentsTotalData(rs.getLong("ERP_SHIPPED_SHIPMENT"));
                nsp.addReceivedErpShipmentsTotalData(rs.getLong("ERP_RECEIVED_SHIPMENT"));
                nsp.addOnholdErpShipmentsTotalData(rs.getLong("ERP_ONHOLD_SHIPMENT"));
                nsp.setRegionCountForStock(rs.getInt("REGION_STOCK_COUNT"));
                nsp.setRegionCount(rs.getInt("REGION_COUNT"));
            } else {
                nsp.setActualConsumptionFlag(rs.getBoolean("USE_ACTUAL_CONSUMPTION"));
                nsp.setRegionCountForStock(rs.getInt("REGION_STOCK_COUNT"));
                nsp.setRegionCount(rs.getInt("REGION_COUNT"));
                rd.setForecastedConsumption(rs.getLong("FORECASTED_CONSUMPTION"));
                if (rs.wasNull()) {
                    rd.setForecastedConsumption(null);
                }
                nsp.addForecastedConsumptionQty(rd.getForecastedConsumption());
                rd.setActualConsumption(rs.getLong("ACTUAL_CONSUMPTION"));
                if (rs.wasNull()) {
                    rd.setActualConsumption(null);
                }
                nsp.addActualConsumptionQty(rd.getActualConsumption());
                rd.setAdjustedConsumption(rs.getLong("ADJUSTED_CONSUMPTION"));
                if (rs.wasNull()) {
                    rd.setAdjustedConsumption(null);
                }
                nsp.addAdjustedConsumptionQty(rd.getAdjustedConsumption());
                nsp.addFinalConsumptionQty(nsp.isActualConsumptionFlag() ? rd.getActualConsumption() : rd.getForecastedConsumption());
                rd.setStock(rs.getLong("STOCK"));
                if (rs.wasNull()) {
                    rd.setStock(null);
                }
                nsp.addStockQty(rd.getStock());
                rd.setAdjustment(rs.getLong("ADJUSTMENT"));
                if (rs.wasNull()) {
                    rd.setAdjustment(null);
                }
                nsp.addAdjustmentQty(rd.getAdjustment());
                nsp.getRegionDataList().add(rd);
            }
        }
        return nspList;
    }

}
