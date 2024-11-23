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
public class DashboardStockStatusRowMapper implements RowMapper<DashboardStockStatus> {

    @Override
    public DashboardStockStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardStockStatus(
                rs.getInt("COUNT_OF_STOCK_OUT"),
                rs.getInt("COUNT_OF_UNDER_STOCK"),
                rs.getInt("COUNT_OF_ADEQUATE_STOCK"),
                rs.getInt("COUNT_OF_OVER_STOCK"),
                rs.getInt("COUNT_OF_NA"),
                rs.getInt("COUNT_OF_TOTAL")
        );
    }

}
