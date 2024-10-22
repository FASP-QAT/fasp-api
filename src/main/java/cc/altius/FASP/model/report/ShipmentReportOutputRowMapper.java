/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentReportOutputRowMapper implements RowMapper<ShipmentReportOutput> {

    @Override
    public ShipmentReportOutput mapRow(ResultSet rs, int i) throws SQLException {
        return new ShipmentReportOutput(
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)),
                rs.getDouble("QTY"),
                rs.getDouble("PRODUCT_COST"),
                rs.getDouble("FREIGHT_PERC"),
                rs.getDouble("FREIGHT_COST"));
    }

}
