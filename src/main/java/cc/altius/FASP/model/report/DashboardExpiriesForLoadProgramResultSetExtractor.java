/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomPuData;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardExpiriesForLoadProgramResultSetExtractor implements ResultSetExtractor<Integer> {

    private final DashboardBottomForLoadProgram db;

    public DashboardExpiriesForLoadProgramResultSetExtractor(DashboardBottomForLoadProgram db) {
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
            db.getPuData().get(planningUnitId).getExpiriesList().add(
                    new DashboardExpiredPu(
                            new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, 1)),
                            rs.getDate("EXPIRY_DATE"),
                            rs.getInt("BATCH_ID"), rs.getString("BATCH_NO"), rs.getBoolean("AUTO_GENERATED"),
                            rs.getDouble("EXPIRED_STOCK"),
                            rs.getDouble("RATE"))
            );
            rows++;
        }
        return rows;
    }

}
