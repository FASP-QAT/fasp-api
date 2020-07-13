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
public class StockStatusVerticalOutputRowMapper implements RowMapper<StockStatusVerticalOutput>{

    @Override
    public StockStatusVerticalOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusVerticalOutput ssv = new StockStatusVerticalOutput();
        ssv.setDt(rs.getDate("TRANS_DATE"));
        ssv.setOpeningBalance(rs.getInt("FINAL_OPENING_BALANCE"));
        ssv.setActualConsumption(rs.getBoolean("ACTUAL"));
        if(rs.wasNull()) {
            ssv.setActualConsumption(null);
        }
        ssv.setConsumptionQty(rs.getInt("CONSUMPTION_QTY"));
        ssv.setShipmentQty(rs.getInt("QTY"));
        ssv.setShipmentDetails(rs.getString("SHIPMENT_DETAILS"));
        ssv.setAdjustment(rs.getInt("ADJUSTMENT"));
        ssv.setExpiredStock(rs.getInt("EXPIRED_STOCK"));
        ssv.setClosingBalance(rs.getInt("FINAL_CLOSING_BALANCE"));
        ssv.setAmc(rs.getDouble("AMC"));
        ssv.setMos(rs.getDouble("MoS"));
        ssv.setMinMos(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ssv.setMaxMos(rs.getInt("MAX_MONTHS_OF_STOCK"));
        return ssv;
    }
    
}
