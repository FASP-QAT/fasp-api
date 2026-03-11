/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
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
public class StockStatusMatrixGlobalRowMapper implements RowMapper<StockStatusMatrixGlobal> {

    private final LocalDate startDate;
    private final LocalDate stopDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StockStatusMatrixGlobalRowMapper(Date startDate, Date stopDate) {
        this.startDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.stopDate = stopDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public StockStatusMatrixGlobal mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusMatrixGlobal ssmgo = new StockStatusMatrixGlobal(new SimpleCodeObject(rs.getInt("ID"), new LabelRowMapper("").mapRow(rs, 1), rs.getString("CODE")));

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
            dataMap.put(dt, new AmcAndQty(
                    rs.getString("PLANNING_UNIT_IDS_" + dt),
                    mos,
                    rs.getDouble("CLOSING_BALANCE_" + dt),
                    (stockQty != null),
                    rs.getInt("SHIPMENT_QTY_" + dt),
                    rs.getInt("EXPIRED_STOCK_QTY_" + dt),
                    rs.getDouble("AMC_" + dt),
                    rs.getInt("STOCK_STATUS_ID_" + dt)
            )
            );
            current = current.plusMonths(1);
        }
        ssmgo.setDataMap(dataMap);
        return ssmgo;
    }

}
