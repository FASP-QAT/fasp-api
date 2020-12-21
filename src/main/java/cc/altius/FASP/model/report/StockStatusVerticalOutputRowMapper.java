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
public class StockStatusVerticalOutputRowMapper implements ResultSetExtractor<List<StockStatusVerticalOutput>> {

    @Override
    public List<StockStatusVerticalOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<StockStatusVerticalOutput> ssvList = new LinkedList<>();
        while (rs.next()) {
            StockStatusVerticalOutput ssv = new StockStatusVerticalOutput();
            ssv.setDt(rs.getDate("TRANS_DATE"));
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
                ssv.setShipmentQty(rs.getLong("SQTY"));
                if (rs.wasNull()) {
                    ssv.setShipmentQty(null);
                }
                ssv.setAdjustment(rs.getLong("ADJUSTMENT"));
                if (rs.wasNull()) {
                    ssv.setAdjustment(null);
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
                ssv.setMos(rs.getDouble("MoS"));
                if (rs.wasNull()) {
                    ssv.setMos(null);
                }
                ssv.setMinMos(rs.getInt("MIN_MONTHS_OF_STOCK"));
                ssv.setMaxMos(rs.getInt("MAX_MONTHS_OF_STOCK"));
                ssvList.add(ssv);
            } else {
                ssv = ssvList.get(index);
            }
            rs.getInt("SHIPMENT_ID");
            if (!rs.wasNull()) {
                ShipmentInfo si = new ShipmentInfo(
                        rs.getInt("SHIPMENT_ID"),
                        rs.getLong("SHIPMENT_QTY"),
                        new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, index), rs.getString("FUNDING_SOURCE_CODE")),
                        new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, index), rs.getString("PROCUREMENT_AGENT_CODE")),
                        new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, index))
                );
                ssv.getShipmentInfo().add(si);
            }
        }
        return ssvList;
    }

}
