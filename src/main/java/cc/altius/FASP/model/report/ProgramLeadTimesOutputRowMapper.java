/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramLeadTimesOutputRowMapper implements RowMapper<ProgramLeadTimesOutput> {

    @Override
    public ProgramLeadTimesOutput mapRow(ResultSet rs, int i) throws SQLException {
        ProgramLeadTimesOutput plt = new ProgramLeadTimesOutput();
        plt.setCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        plt.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        plt.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        plt.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        plt.setPlannedToSubmittedLeadTime(rs.getDouble("PLANNED_TO_SUBMITTED_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setPlannedToSubmittedLeadTime(null);
        }
        plt.setSubmittedToApprovedLeadTime(rs.getDouble("SUBMITTED_TO_APPROVED_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setSubmittedToApprovedLeadTime(null);
        }
        plt.setApprovedToShippedLeadTime(rs.getDouble("APPROVED_TO_SHIPPED_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setApprovedToShippedLeadTime(null);
        }
        plt.setShippedToArrivedByAirLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setShippedToArrivedByAirLeadTime(null);
        }
        plt.setShippedToArrivedBySeaLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setShippedToArrivedBySeaLeadTime(null);
        }
        plt.setShippedToArrivedByRoadLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setShippedToArrivedByRoadLeadTime(null);
        }
        plt.setArrivedToDeliveredLeadTime(rs.getDouble("ARRIVED_TO_DELIVERED_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setArrivedToDeliveredLeadTime(null);
        }
        plt.setLocalProcurementAgentLeadTime(rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"));
        if (rs.wasNull()) {
            plt.setLocalProcurementAgentLeadTime(null);
        }
        return plt;
    }

}
