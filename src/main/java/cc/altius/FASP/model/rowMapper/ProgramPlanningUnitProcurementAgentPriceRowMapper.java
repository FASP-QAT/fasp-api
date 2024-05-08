/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitProcurementAgentPriceRowMapper implements RowMapper<ProgramPlanningUnitProcurementAgentPrice> {

    @Override
    public ProgramPlanningUnitProcurementAgentPrice mapRow(ResultSet rs, int i) throws SQLException {
        ProgramPlanningUnitProcurementAgentPrice ppupa = new ProgramPlanningUnitProcurementAgentPrice();
        ppupa.setProgramPlanningUnitProcurementAgentId(rs.getInt("PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID"));
        ppupa.setProgramPlanningUnitId(rs.getInt("PROGRAM_PLANNING_UNIT_ID"));
        ppupa.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        ppupa.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        ppupa.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        ppupa.setPrice(rs.getDouble("PROGRAM_PRICE"));
        ppupa.setSeaFreightPerc(rs.getDouble("SEA_FREIGHT_PERC"));
        if (rs.wasNull()) {
            ppupa.setSeaFreightPerc(null);
        }
        ppupa.setAirFreightPerc(rs.getDouble("AIR_FREIGHT_PERC"));
        if (rs.wasNull()) {
            ppupa.setAirFreightPerc(null);
        }
        ppupa.setRoadFreightPerc(rs.getDouble("ROAD_FREIGHT_PERC"));
        if (rs.wasNull()) {
            ppupa.setRoadFreightPerc(null);
        }
        ppupa.setPlannedToSubmittedLeadTime(rs.getDouble("PLANNED_TO_SUBMITTED_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setPlannedToSubmittedLeadTime(null);
        }
        ppupa.setSubmittedToApprovedLeadTime(rs.getDouble("SUBMITTED_TO_APPROVED_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setSubmittedToApprovedLeadTime(null);
        }
        ppupa.setApprovedToShippedLeadTime(rs.getDouble("APPROVED_TO_SHIPPED_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setApprovedToShippedLeadTime(null);
        }
        ppupa.setShippedToArrivedByAirLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setShippedToArrivedByAirLeadTime(null);
        }
        ppupa.setShippedToArrivedBySeaLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setShippedToArrivedBySeaLeadTime(null);
        }
        ppupa.setShippedToArrivedByRoadLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setShippedToArrivedByRoadLeadTime(null);
        }
        ppupa.setArrivedToDeliveredLeadTime(rs.getDouble("ARRIVED_TO_DELIVERED_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setArrivedToDeliveredLeadTime(null);
        }
        ppupa.setLocalProcurementLeadTime(rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"));
        if (rs.wasNull()) {
            ppupa.setLocalProcurementLeadTime(null);
        }
        ppupa.setBaseModel(new BaseModelRowMapper("PPUPA_").mapRow(rs, i));
        return ppupa;
    }

}
