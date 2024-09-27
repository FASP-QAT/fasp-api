/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardStockStatusForLoadProgramResultSetExtractor implements ResultSetExtractor<Integer> {

    private final DashboardForLoadProgram db;

    public DashboardStockStatusForLoadProgramResultSetExtractor(DashboardForLoadProgram db) {
        this.db = db;
    }

    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        int rows = 0;
        while (rs.next()) {
            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
            if (!db.getBottomPuData().containsKey(planningUnitId)) {
                db.getBottomPuData().put(planningUnitId, new DashboardBottomData());
            }
            db.getBottomPuData().get(planningUnitId).setStockStatus(
                    new DashboardStockStatus(
                            rs.getInt("COUNT_OF_STOCK_OUT"),
                            rs.getInt("COUNT_OF_UNDER_STOCK"),
                            rs.getInt("COUNT_OF_ADEQUATE_STOCK"),
                            rs.getInt("COUNT_OF_OVER_STOCK"),
                            rs.getInt("COUNT_OF_NA"),
                            rs.getInt("COUNT_OF_TOTAL")
                    )
            );
            rows++;
        }
        return rows;
    }

}
