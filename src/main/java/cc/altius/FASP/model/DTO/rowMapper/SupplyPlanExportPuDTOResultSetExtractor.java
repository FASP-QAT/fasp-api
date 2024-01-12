/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.SupplyPlanExportDataDTO;
import cc.altius.FASP.model.DTO.SupplyPlanExportPuDTO;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SupplyPlanExportPuDTOResultSetExtractor implements ResultSetExtractor<List<SupplyPlanExportPuDTO>> {

    String getIds(List<SupplyPlanExportPuDTO> spPuList) {
        return spPuList
                .stream()
                .map(sp -> String.valueOf(sp.getPlanningUnit().getPlanningUnitId()))
                .collect(Collectors.joining(","));
    }

    @Override
    public List<SupplyPlanExportPuDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SupplyPlanExportPuDTO> spPuList = new LinkedList<>();
        while (rs.next()) {
            SupplyPlanExportPuDTO puDto = new SupplyPlanExportPuDTO();
            puDto.setPlanningUnit(new PlanningUnit(rs.getInt("PLANNING_UNIT_ID"), null));
            int idx = spPuList.indexOf(puDto);
            SupplyPlanExportPuDTO spPuObject;
            if (idx == -1) {
                spPuObject = new SupplyPlanExportPuDTO();
                spPuObject.setPlanningUnit(new PlanningUnit(
                        rs.getInt("PLANNING_UNIT_ID"),
                        new ForecastingUnit(
                                rs.getInt("FORECASTING_UNIT_ID"),
                                null,
                                null,
                                new LabelRowMapper("FU_").mapRow(rs, 1),
                                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PC_").mapRow(rs, 1)),
                                null
                        ),
                        new LabelRowMapper("PU_").mapRow(rs, 1),
                        new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, 1), null),
                        rs.getDouble("MULTIPLIER"),
                        true
                ));
                spPuList.add(spPuObject);
            } else {
                spPuObject = spPuList.get(idx);
            }
            SupplyPlanExportDataDTO spData = new SupplyPlanExportDataDTO();
            spData.setTransDate(rs.getString("TRANS_DATE"));
            spData.setRegionCount(rs.getInt("REGION_COUNT"));
            spData.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
            spData.setOpeningBalance(rs.getInt("OPENING_BALANCE"));
            spData.setActualConsumptionQty(rs.getInt("ACTUAL_CONSUMPTION_QTY"));
            if (rs.wasNull()) {
                spData.setActualConsumptionQty(null);
            }
            spData.setForecastedConsumptionQty(rs.getInt("FORECASTED_CONSUMPTION_QTY"));
            if (rs.wasNull()) {
                spData.setForecastedConsumptionQty(null);
            }
            spData.setNationalAdjustment(rs.getInt("NATIONAL_ADJUSTMENT"));
            spData.setManualPlannedShipmentQty(rs.getInt("MANUAL_PLANNED_SHIPMENT_QTY"));
            spData.setManualSubmittedShipmentQty(rs.getInt("MANUAL_SUBMITTED_SHIPMENT_QTY"));
            spData.setManualApprovedShipmentQty(rs.getInt("MANUAL_APPROVED_SHIPMENT_QTY"));
            spData.setManualOnholdShipmentQty(rs.getInt("MANUAL_ONHOLD_SHIPMENT_QTY"));
            spData.setManualShippedShipmentQty(rs.getInt("MANUAL_SHIPPED_SHIPMENT_QTY"));
            spData.setManualReceivedShipmentQty(rs.getInt("MANUAL_RECEIVED_SHIPMENT_QTY"));
            spData.setErpPlannedShipmentQty(rs.getInt("ERP_PLANNED_SHIPMENT_QTY"));
            spData.setErpSubmittedShipmentQty(rs.getInt("ERP_SUBMITTED_SHIPMENT_QTY"));
            spData.setErpApprovedShipmentQty(rs.getInt("ERP_APPROVED_SHIPMENT_QTY"));
            spData.setErpOnholdShipmentQty(rs.getInt("ERP_ONHOLD_SHIPMENT_QTY"));
            spData.setErpShippedShipmentQty(rs.getInt("ERP_SHIPPED_SHIPMENT_QTY"));
            spData.setErpReceivedShipmentQty(rs.getInt("ERP_RECEIVED_SHIPMENT_QTY"));
            spData.setExpiredStock(rs.getInt("EXPIRED_STOCK"));
            spData.setClosingBalance(rs.getInt("CLOSING_BALANCE"));
            spData.setMos(rs.getDouble("MOS"));
            spData.setAmc(rs.getDouble("AMC"));
            spData.setAmcCount(rs.getInt("AMC_COUNT"));
            spData.setUnmetDemand(rs.getInt("UNMET_DEMAND"));
            spData.setMinStockMos(rs.getDouble("MIN_STOCK_MOS"));
            spData.setMinStockQty(rs.getDouble("MIN_STOCK_QTY"));
            spData.setMaxStockMos(rs.getDouble("MAX_STOCK_MOS"));
            spData.setMaxStockQty(rs.getDouble("MAX_STOCK_QTY"));
            spPuObject.getSupplyPlanData().add(spData);
        }
        return spPuList;
    }

}
