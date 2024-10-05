/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardTopExpiriesForLoadProgramResultSetExtractor implements ResultSetExtractor<Integer> {

    private final DashboardForLoadProgram db;

    public DashboardTopExpiriesForLoadProgramResultSetExtractor(DashboardForLoadProgram db) {
        this.db = db;
    }

    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        int rows = 0;
        while (rs.next()) {
            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
            if (!db.getTopPuData().containsKey(planningUnitId)) {
                db.getTopPuData().put(planningUnitId, new DashboardTopData());
            }
            double expiredStockQty = rs.getDouble("EXPIRED_STOCK");
            double rate = rs.getDouble("RATE");
            db.getTopPuData().get(planningUnitId).setValueOfExpiredStock(db.getTopPuData().get(planningUnitId).getValueOfExpiredStock() + expiredStockQty * rate);
            rows++;
        }
        return rows;
    }

}
