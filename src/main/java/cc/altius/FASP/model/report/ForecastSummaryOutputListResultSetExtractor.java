/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ForecastSummaryOutputListResultSetExtractor implements ResultSetExtractor<List<ForecastSummaryOutput>> {

    private int reportView;

    public ForecastSummaryOutputListResultSetExtractor(int reportView) {
        this.reportView = reportView;
    }

    @Override
    public List<ForecastSummaryOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ForecastSummaryOutput> fsList = new LinkedList<>();
        while (rs.next()) {
            ForecastSummaryOutput fso = new ForecastSummaryOutput();
            fso.setPlanningUnit(new SimpleObjectWithMultiplier(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1), rs.getDouble("MULTIPLIER")));
            if (this.reportView == 1) {
                fso.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("R_").mapRow(rs, 1)));
            } else {
                fso.setRegion(new SimpleObject(0, new Label()));
            }
            int idx = fsList.indexOf(fso);
            Double totalForecast = rs.getDouble("TOTAL_FORECAST");
            if (rs.wasNull()) {
                totalForecast = null;
            }
            if (idx == -1) {
                // Not found this record therefore set to whatever we just read
                fso.setTotalForecast(totalForecast);
                fsList.add(fso);
            } else {
                fso = fsList.get(idx);
                if (fso.getTotalForecast() == null) {
                    // If the current Consumption Qty is null therefore set to whatever we just read
                    fso.setTotalForecast(totalForecast);
                } else if (totalForecast != null) {
                    // Since curent Consumption Qty is not null then if the new Consumption Qty is also not null then add the two otherwise the current one is correct
                    fso.setTotalForecast(fso.getTotalForecast() + totalForecast);
                }
            }
            fso.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, 1)));
            fso.setSelectedForecast(new SimpleObject(0, new LabelRowMapper("SF_").mapRow(rs, 1)));
            fso.setNotes(rs.getString("NOTES"));
            fso.setStock(rs.getInt("STOCK"));
            if (rs.wasNull()) {
                fso.setStock(null);
            }
            fso.setExistingShipments(rs.getInt("EXISTING_SHIPMENTS"));
            if (rs.wasNull()) {
                fso.setExistingShipments(null);
            }
            fso.setMonthsOfStock(rs.getInt("MONTHS_OF_STOCK"));
            if (rs.wasNull()) {
                fso.setMonthsOfStock(null);
            }
            fso.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")));
            fso.setPrice(rs.getDouble("PRICE"));
            if (rs.wasNull()) {
                fso.setPrice(null);
            }
        }
        return fsList;
    }

}
