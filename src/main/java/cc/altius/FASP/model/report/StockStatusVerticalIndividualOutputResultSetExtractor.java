/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class StockStatusVerticalIndividualOutputResultSetExtractor implements ResultSetExtractor<StockStatusVerticalIndividualOutput> {

    @Override
    public StockStatusVerticalIndividualOutput extractData(ResultSet rs) throws SQLException, DataAccessException {
        StockStatusVerticalIndividualOutput ssv = null;
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                ssv = new StockStatusVerticalIndividualOutput(
                        new SimpleObject(rs.getInt("RU_ID"), new LabelRowMapper("RU_").mapRow(rs, 1)),
                        rs.getInt("REORDER_FREQUENCY_IN_MONTHS"),
                        rs.getInt("MIN_MONTHS_OF_STOCK"),
                        rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"),
                        rs.getInt("SHELF_LIFE"),
                        rs.getInt("MONTHS_IN_FUTURE_FOR_AMC"),
                        rs.getInt("MONTHS_IN_PAST_FOR_AMC"),
                        rs.getInt("PLAN_BASED_ON"),
                        rs.getInt("MIN_QTY"),
                        rs.getDouble("DISTRIBUTION_LEAD_TIME"),
                        rs.getString("NOTES")
                );
                isFirst = false;
            }
            StockStatusVertical ssvo = new StockStatusVertical(rs.getDate("TRANS_DATE"));
            int index = ssv.getStockStatusVertical().indexOf(ssvo);
            if (index == -1) {
                ssvo.setOpeningBalance(rs.getLong("FINAL_OPENING_BALANCE"));
                ssvo.setActualConsumption(rs.getBoolean("ACTUAL"));
                if (rs.wasNull()) {
                    ssvo.setActualConsumption(null);
                }
                ssvo.setActualConsumptionQty(rs.getLong("ACTUAL_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssvo.setActualConsumptionQty(null);
                }
                ssvo.setForecastedConsumptionQty(rs.getLong("FORECASTED_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssvo.setForecastedConsumptionQty(null);
                }
                ssvo.setFinalConsumptionQty(rs.getLong("FINAL_CONSUMPTION_QTY"));
                if (rs.wasNull()) {
                    ssvo.setFinalConsumptionQty(null);
                }
                ssvo.setShipmentQty(rs.getLong("SQTY"));
                if (rs.wasNull()) {
                    ssvo.setShipmentQty(null);
                }
                ssvo.setAdjustment(rs.getLong("ADJUSTMENT"));
                if (rs.wasNull()) {
                    ssvo.setAdjustment(null);
                }
                ssvo.setNationalAdjustment(rs.getLong("NATIONAL_ADJUSTMENT"));
                if (rs.wasNull()) {
                    ssvo.setNationalAdjustment(null);
                }
                ssvo.setExpiredStock(rs.getLong("EXPIRED_STOCK"));
                if (rs.wasNull()) {
                    ssvo.setExpiredStock(null);
                }
                ssvo.setClosingBalance(rs.getLong("FINAL_CLOSING_BALANCE"));
                if (rs.wasNull()) {
                    ssvo.setClosingBalance(null);
                }
                ssvo.setAmc(rs.getDouble("AMC"));
                if (rs.wasNull()) {
                    ssvo.setAmc(null);
                }
                ssvo.setMos(rs.getDouble("MOS"));
                if (rs.wasNull()) {
                    ssvo.setMos(null);
                }
                ssvo.setMinMos(rs.getDouble("MIN_STOCK_MOS"));
                if (rs.wasNull()) {
                    ssvo.setMinMos(null);
                }
                ssvo.setMaxMos(rs.getDouble("MAX_STOCK_MOS"));
                if (rs.wasNull()) {
                    ssvo.setMinMos(null);
                }
                ssvo.setUnmetDemand(rs.getLong("UNMET_DEMAND"));
                if (rs.wasNull()) {
                    ssvo.setUnmetDemand(null);
                }
                ssvo.setMinStock(rs.getDouble("MIN_STOCK_QTY"));
                if (rs.wasNull()) {
                    ssvo.setMinStock(null);
                }
                ssvo.setMaxStock(rs.getDouble("MAX_STOCK_QTY"));
                if (rs.wasNull()) {
                    ssvo.setMaxStock(null);
                }
                ssvo.setRegionCount(rs.getInt("REGION_COUNT"));
                ssvo.setRegionCountForStock(rs.getInt("REGION_COUNT_FOR_STOCK"));
                ssv.getStockStatusVertical().add(ssvo);
            } else {
                ssvo = ssv.getStockStatusVertical().get(index);
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
                if (ssvo.getShipmentInfo().indexOf(si) == -1) {
                    ssvo.getShipmentInfo().add(si);
                }
            }
        }
        return ssv;
    }

}
