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
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class StockStatusVerticalAggregateOutputRowMapper implements ResultSetExtractor<List<StockStatusVerticalAggregateOutput>> {

    @Override
    public List<StockStatusVerticalAggregateOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<StockStatusVerticalAggregateOutput> ssvList = new LinkedList<>();
        while (rs.next()) {
            StockStatusVerticalAggregateOutput ssv = new StockStatusVerticalAggregateOutput();
            ssv.setDt(rs.getDate("TRANS_DATE"));
            ssv.setReportingUnit(new SimpleObject(rs.getInt("RU_ID"), new LabelRowMapper("RU_").mapRow(rs, 1)));
            int index = ssvList.indexOf(ssv);
            if (index == -1) {
                ssv.setOpeningBalance(rs.getLong("FINAL_OPENING_BALANCE"));
                ssv.setActualConsumption(rs.getBoolean("ACTUAL"));
                if (rs.wasNull()) {
                    ssv.setActualConsumption(null);
                }
                ssv.setActualConsumptionQty(rs.getLong("ACTUAL_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssv.setActualConsumptionQty(null);
                }
                ssv.setForecastedConsumptionQty(rs.getLong("FORECASTED_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssv.setForecastedConsumptionQty(null);
                }
                ssv.setFinalConsumptionQty(rs.getLong("FINAL_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssv.setFinalConsumptionQty(null);
                }
                ssv.setShipmentQty(rs.getLong("SQTY"));
                if (rs.wasNull()) {
                    ssv.setShipmentQty(null);
                }
                ssv.setAdjustment(rs.getLong("ADJUSTMENT"));
                if (rs.wasNull()) {
                    ssv.setAdjustment(null);
                }
                ssv.setNationalAdjustment(rs.getLong("NATIONAL_ADJUSTMENT"));
                if (rs.wasNull()) {
                    ssv.setNationalAdjustment(null);
                }
                ssv.setExpiredStock(rs.getLong("EXPIRED_STOCK"));
                if (rs.wasNull()) {
                    ssv.setExpiredStock(null);
                }
                ssv.setClosingBalance(rs.getLong("FINAL_CLOSING_BALANCE"));
                if (rs.wasNull()) {
                    ssv.setClosingBalance(null);
                }
                ssv.setAmc(rs.getDouble("AMC"));
                if (rs.wasNull()) {
                    ssv.setAmc(null);
                }
                ssv.setMos(rs.getDouble("MOS"));
                if (rs.wasNull()) {
                    ssv.setMos(null);
                }
                ssv.setMinStockMos(rs.getDouble("MIN_STOCK_MOS"));
                if (rs.wasNull()) {
                    ssv.setMinStockMos(null);
                }
                ssv.setMaxStockMos(rs.getDouble("MAX_STOCK_MOS"));
                if (rs.wasNull()) {
                    ssv.setMaxStockMos(null);
                }
                ssv.setMinStockQty(rs.getDouble("MIN_STOCK_QTY"));
                if (rs.wasNull()) {
                    ssv.setMinStockQty(null);
                }
                ssv.setMaxStockQty(rs.getDouble("MAX_STOCK_QTY"));
                if (rs.wasNull()) {
                    ssv.setMaxStockQty(null);
                }
                ssv.setUnmetDemand(rs.getLong("UNMET_DEMAND"));
                if (rs.wasNull()) {
                    ssv.setUnmetDemand(null);
                }
                ssv.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
                ssv.setRegionCount(rs.getInt("REGION_COUNT"));
                ssv.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
                ssvList.add(ssv);
            } else {
                ssv = ssvList.get(index);
            }
            rs.getInt("SHIPMENT_ID");
            if (!rs.wasNull()) {
                ShipmentInfo si = new ShipmentInfo(
                        rs.getInt("SHIPMENT_ID"),
                        rs.getLong("SHIPMENT_QTY"), // in terms of ReportingUnit
                        new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, 1), rs.getString("FUNDING_SOURCE_CODE")),
                        new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")),
                        new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                        new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1)),
                        new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)),
                        rs.getString("NOTES"),
                        rs.getString("ORDER_NO"),
                        rs.getString("PRIME_LINE_NO"),
                        rs.getString("RO_NO"),
                        rs.getString("RO_PRIME_LINE_NO"),
                        rs.getDate("EDD"),
                        new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, 1))
                );
                if (ssv.getShipmentInfo().indexOf(si) == -1) {
                    ssv.getShipmentInfo().add(si);
                }
            }
        }
        return ssvList;
    }

}
