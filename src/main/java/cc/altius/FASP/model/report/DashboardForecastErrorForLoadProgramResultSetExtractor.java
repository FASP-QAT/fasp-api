/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomPuData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardForecastErrorForLoadProgramResultSetExtractor implements ResultSetExtractor<Integer> {

    private final DashboardBottomForLoadProgram db;

    public DashboardForecastErrorForLoadProgramResultSetExtractor(DashboardBottomForLoadProgram db) {
        this.db = db;
    }

    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        int rows = 0;
        while (rs.next()) {
            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
            if (!db.getPuData().containsKey(planningUnitId)) {
                db.getPuData().put(planningUnitId, new DashboardBottomPuData());
            }
            Double error = rs.getDouble("ERROR_PERC");
            if (rs.wasNull()) {
                error = null;
            }
            db.getPuData().get(planningUnitId).setForecastError(error);
            rows++;
        }
        return rows;
    }

}
