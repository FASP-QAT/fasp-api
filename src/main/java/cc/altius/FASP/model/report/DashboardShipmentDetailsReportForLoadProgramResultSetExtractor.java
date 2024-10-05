/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomData;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardShipmentDetailsReportForLoadProgramResultSetExtractor implements ResultSetExtractor<Integer> {

    private final DashboardForLoadProgram db;
    private final int reportBy;

    public DashboardShipmentDetailsReportForLoadProgramResultSetExtractor(DashboardForLoadProgram db, int reportBy) {
        this.db = db;
        this.reportBy = reportBy;
    }

    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        int rows = 0;
        while (rs.next()) {
            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
            if (!db.getBottomPuData().containsKey(planningUnitId)) {
                db.getBottomPuData().put(planningUnitId, new DashboardBottomData());
            }
            DashboardShipmentDetailsReportBy sd = new DashboardShipmentDetailsReportBy();
            sd.setReportBy(new SimpleCodeObject(rs.getInt("REPORT_BY_ID"), new LabelRowMapper("RB_").mapRow(rs, 1), rs.getString("REPORT_BY_CODE")));
            sd.setOrderCount(rs.getInt("ORDER_COUNT"));
            sd.setQuantity(rs.getDouble("QUANTITY"));
            sd.setCost(rs.getDouble("COST"));
            switch (reportBy) {
                case 1 -> {
                    db.getBottomPuData().get(planningUnitId).getShipmentDetailsByFundingSource().add(sd);
                    if (sd.getReportBy().getId() == 8) { // Funding Source = TBD
                        db.getBottomPuData().get(planningUnitId).incrementCountOfTbdFundingSource();
                    }
                }
                case 2 ->
                    db.getBottomPuData().get(planningUnitId).getShipmentDetailsByProcurementAgent().add(sd);
                case 3 ->
                    db.getBottomPuData().get(planningUnitId).getShipmentDetailsByShipmentStatus().add(sd);
            }
            rows++;
        }
        return rows;
    }
}
