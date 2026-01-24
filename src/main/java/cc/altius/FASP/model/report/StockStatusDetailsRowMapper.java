/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockStatusDetailsRowMapper implements RowMapper<StockStatusDetails> {

    @Override
    public StockStatusDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        StockStatusDetails ssd = new StockStatusDetails();
        ssd.setMonth(rs.getString("TRANS_DATE"));
        ssd.setPlanningUnit(new SimpleObjectRowMapper().mapRow(rs, rowNum));
        ssd.setConsumptionQty(rs.getInt("CONSUMPTION_QTY"));
        ssd.setActualConsumption(rs.getBoolean("ACTUAL"));
        ssd.setClosingBalance(rs.getInt("CLOSING_BALANCE"));
        Integer stockQty = rs.getInt("STOCK_MULTIPLIED_QTY");
        if (rs.wasNull()) {
            stockQty = null;
        }
        ssd.setActualStock((stockQty != null));
        ssd.setAmc(rs.getDouble("AMC"));
        if (rs.wasNull()) {
            ssd.setAmc(null);
        }
        ssd.setMos(rs.getDouble("MOS"));
        if (rs.wasNull()) {
            ssd.setMos(null);
        }
        ssd.setStockStatusId(
                AmcAndQty.getStockStatusId(
                        rs.getInt("PLAN_BASED_ON"),
                        rs.getInt("MIN_MONTHS_OF_STOCK"),
                        rs.getInt("REORDER_FREQUENCY_IN_MONTHS"),
                        rs.getInt("MIN_STOCK_QTY"),
                        rs.getInt("MAX_STOCK_QTY"),
                        ssd.getMos(),
                        rs.getInt("CLOSING_BALANCE")
                )
        );
        return ssd;
    }

}
