/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplierAndActive;
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
            fso.setPlanningUnit(new SimpleObjectWithMultiplierAndActive(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1), rs.getDouble("MULTIPLIER"), rs.getBoolean("ACTIVE")));
            String notes = rs.getString("NOTES");
            if (notes != null && notes.isBlank()) {
                notes = null;
            }
            Label notesLabel = null;
            SimpleObject region = new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("R_").mapRow(rs, 1));
            if (this.reportView == 1) { // Regional View
                fso.setRegion(region);
                notesLabel = new Label(0, notes, notes, notes, notes);
            } else { // National 
                fso.setRegion(new SimpleObject(0, new Label()));
                if (notes != null) {
                    notesLabel = new Label(0,
                            region.getLabel().getLabel_en() + " : " + notes,
                            region.getLabel().getLabel_sp() + " : " + notes,
                            region.getLabel().getLabel_fr() + " : " + notes,
                            region.getLabel().getLabel_pr() + " : " + notes);
                }
            }
            int idx = fsList.indexOf(fso);
            Double totalForecast = rs.getDouble("TOTAL_FORECAST");
            if (rs.wasNull()) {
                totalForecast = null;
            }
            if (idx == -1) {
                // Not found this record therefore set to whatever we just read
                fso.setTotalForecast(totalForecast);
                fso.setNotes(notesLabel);
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
                if (fso.getNotes() == null) {
                    // If the current Notes is null therefore set to whatever we just read
                    fso.setNotes(notesLabel);
                } else if (notesLabel != null) {
                    // Since curent Notes is not null then if the new Notesis also not null then add the two otherwise the current one is correct
                    fso.getNotes().setLabel_en(fso.getNotes().getLabel_en() + " | " + notesLabel.getLabel_en());
                    fso.getNotes().setLabel_fr(fso.getNotes().getLabel_fr() + " | " + notesLabel.getLabel_fr());
                    fso.getNotes().setLabel_sp(fso.getNotes().getLabel_sp() + " | " + notesLabel.getLabel_sp());
                    fso.getNotes().setLabel_pr(fso.getNotes().getLabel_pr() + " | " + notesLabel.getLabel_pr());
                }
            }
            fso.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, 1)));
            fso.setSelectedForecast(new LabelRowMapper("SF_").mapRow(rs, 1));

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
            fso.setFreightPerc(rs.getDouble("FREIGHT_PERC"));
            if (rs.wasNull()) {
                fso.setFreightPerc(null);
            }
        }
        return fsList;
    }

}
