/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardShipmentDetailsReportByRowMapper implements RowMapper<DashboardShipmentDetailsReportBy> {

    @Override
    public DashboardShipmentDetailsReportBy mapRow(ResultSet rs, int i) throws SQLException {
        DashboardShipmentDetailsReportBy sdfs = new DashboardShipmentDetailsReportBy();
        sdfs.setReportBy(new SimpleCodeObject(rs.getInt("REPORT_BY_ID"), new LabelRowMapper("RB_").mapRow(rs, i), rs.getString("REPORT_BY_CODE")));
        sdfs.setOrderCount(rs.getInt("ORDER_COUNT"));
        sdfs.setQuantity(rs.getDouble("QUANTITY"));
        sdfs.setCost(rs.getDouble("COST"));
        return sdfs;
    }

}
