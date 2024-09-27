/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardStockOutAndExpiredRowMapper implements RowMapper<DashboardStockOutAndExpired> {

    @Override
    public DashboardStockOutAndExpired mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardStockOutAndExpired(rs.getInt("PRODUCTS_WITH_STOCK_OUT"), rs.getInt("PRODUCTS_WITH_STOCK_OUT"));
    }

}
