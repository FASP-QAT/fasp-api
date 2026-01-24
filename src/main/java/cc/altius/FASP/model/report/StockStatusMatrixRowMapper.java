/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockStatusMatrixRowMapper implements RowMapper<StockStatusMatrix> {

    private LocalDate startDate;
    private LocalDate stopDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StockStatusMatrixRowMapper(Date startDate, Date stopDate) {
        this.startDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.stopDate = stopDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public StockStatusMatrix mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusMatrix ssmo = new StockStatusMatrix(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1)));
        ssmo.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
        ssmo.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ssmo.setReorderFrequency(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
        ssmo.setMaxStock(rs.getInt("MAX_STOCK_QTY"));
        ssmo.setMinStock(rs.getInt("MIN_STOCK_QTY"));
        ssmo.setNotes(rs.getString("NOTES"));
        Map<String, AmcAndQty> dataMap = new HashMap<>();
        LocalDate current = this.startDate;
        while (!current.isAfter(this.stopDate)) {
            String dt = formatter.format(current);
            Double mos = rs.getDouble("MOS_" + dt);
            if (rs.wasNull()) {
                mos = null;
            }
            Integer stockQty = rs.getInt("STOCK_QTY_" + dt);
            if (rs.wasNull()) {
                stockQty = null;
            }
            int closingBalance = rs.getInt("CLOSING_BALANCE_" + dt);
            double amc = rs.getDouble("AMC_" + dt);
            dataMap.put(dt, new AmcAndQty(
                    mos,
                    rs.getDouble("CLOSING_BALANCE_" + dt),
                    (stockQty != null),
                    rs.getInt("SHIPMENT_QTY_" + dt),
                    rs.getInt("EXPIRED_STOCK_QTY_" + dt),
                    amc,
                    AmcAndQty.getStockStatusId(
                            ssmo.getPlanBasedOn(), 
                            ssmo.getMinMonthsOfStock(), 
                            ssmo.getReorderFrequency(), 
                            ssmo.getMinStock(), 
                            ssmo.getMaxStock(), 
                            mos, 
                            closingBalance)
            )
            );
            current = current.plusMonths(1);
        }
        ssmo.setDataMap(dataMap);
        return ssmo;
    }
}
