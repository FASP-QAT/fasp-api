/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramLeadTimesOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject country;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject procurementAgent;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private Double plannedToSubmittedLeadTime;
    @JsonView(Views.ReportView.class)
    private Double submittedToApprovedLeadTime;
    @JsonView(Views.ReportView.class)
    private Double approvedToShippedLeadTime;
    @JsonView(Views.ReportView.class)
    private Double shippedToArrivedByAirLeadTime;
    @JsonView(Views.ReportView.class)
    private Double shippedToArrivedBySeaLeadTime;
    @JsonView(Views.ReportView.class)
    private Double shippedToArrivedByRoadLeadTime;
    @JsonView(Views.ReportView.class)
    private Double arrivedToDeliveredLeadTime;
    @JsonView(Views.ReportView.class)
    private Double localProcurementAgentLeadTime;

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Double getPlannedToSubmittedLeadTime() {
        return plannedToSubmittedLeadTime;
    }

    public void setPlannedToSubmittedLeadTime(Double plannedToSubmittedLeadTime) {
        this.plannedToSubmittedLeadTime = plannedToSubmittedLeadTime;
    }

    public Double getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(Double submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

    public Double getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(Double approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public Double getShippedToArrivedByAirLeadTime() {
        return shippedToArrivedByAirLeadTime;
    }

    public void setShippedToArrivedByAirLeadTime(Double shippedToArrivedByAirLeadTime) {
        this.shippedToArrivedByAirLeadTime = shippedToArrivedByAirLeadTime;
    }

    public Double getShippedToArrivedBySeaLeadTime() {
        return shippedToArrivedBySeaLeadTime;
    }

    public void setShippedToArrivedBySeaLeadTime(Double shippedToArrivedBySeaLeadTime) {
        this.shippedToArrivedBySeaLeadTime = shippedToArrivedBySeaLeadTime;
    }

    public Double getShippedToArrivedByRoadLeadTime() {
        return shippedToArrivedByRoadLeadTime;
    }

    public void setShippedToArrivedByRoadLeadTime(Double shippedToArrivedByRoadLeadTime) {
        this.shippedToArrivedByRoadLeadTime = shippedToArrivedByRoadLeadTime;
    }

    public Double getArrivedToDeliveredLeadTime() {
        return arrivedToDeliveredLeadTime;
    }

    public void setArrivedToDeliveredLeadTime(Double arrivedToDeliveredLeadTime) {
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
    }

    public Double getLocalProcurementAgentLeadTime() {
        return localProcurementAgentLeadTime;
    }

    public void setLocalProcurementAgentLeadTime(Double localProcurementAgentLeadTime) {
        this.localProcurementAgentLeadTime = localProcurementAgentLeadTime;
    }

    @JsonView(Views.ReportView.class)
    public Double getTotalSeaLeadTime() {
        double d = (this.plannedToSubmittedLeadTime == null ? 0 : this.plannedToSubmittedLeadTime)
                + (this.submittedToApprovedLeadTime == null ? 0 : this.submittedToApprovedLeadTime)
                + (this.approvedToShippedLeadTime == null ? 0 : this.approvedToShippedLeadTime)
                + (this.shippedToArrivedBySeaLeadTime == null ? 0 : this.shippedToArrivedBySeaLeadTime)
                + (this.arrivedToDeliveredLeadTime == null ? 0 : this.arrivedToDeliveredLeadTime);
        if (d == 0) {
            return null;
        } else {
            return d;
        }
    }

    @JsonView(Views.ReportView.class)
    public Double getTotalAirLeadTime() {
        double d = (this.plannedToSubmittedLeadTime != null ? this.plannedToSubmittedLeadTime : 0)
                + (this.submittedToApprovedLeadTime != null ? this.submittedToApprovedLeadTime : 0)
                + (this.approvedToShippedLeadTime != null ? this.approvedToShippedLeadTime : 0)
                + (this.shippedToArrivedByAirLeadTime != null ? this.shippedToArrivedByAirLeadTime : 0)
                + (this.arrivedToDeliveredLeadTime != null ? this.arrivedToDeliveredLeadTime : 0
        );
        if (d == 0) {
            return null;
        } else {
            return d;
        }
    }
    
    @JsonView(Views.ReportView.class)
    public Double getTotalRoadLeadTime() {
        double d = (this.plannedToSubmittedLeadTime != null ? this.plannedToSubmittedLeadTime : 0)
                + (this.submittedToApprovedLeadTime != null ? this.submittedToApprovedLeadTime : 0)
                + (this.approvedToShippedLeadTime != null ? this.approvedToShippedLeadTime : 0)
                + (this.shippedToArrivedByRoadLeadTime != null ? this.shippedToArrivedByRoadLeadTime : 0)
                + (this.arrivedToDeliveredLeadTime != null ? this.arrivedToDeliveredLeadTime : 0
        );
        if (d == 0) {
            return null;
        } else {
            return d;
        }
    }

    @Override
    public String toString() {
        return "ProgramLeadTimesOutput{" + "country=" + country + ", program=" + program + ", procurementAgent=" + procurementAgent + ", planningUnit=" + planningUnit + ", plannedToSubmittedLeadTime=" + plannedToSubmittedLeadTime + ", submittedToApprovedLeadTime=" + submittedToApprovedLeadTime + ", approvedToShippedLeadTime=" + approvedToShippedLeadTime + ", shippedToArrivedByAirLeadTime=" + shippedToArrivedByAirLeadTime + ", shippedToArrivedBySeaLeadTime=" + shippedToArrivedBySeaLeadTime + ", arrivedToDeliveredLeadTime=" + arrivedToDeliveredLeadTime + ", localProcurementAgentLeadTime=" + localProcurementAgentLeadTime + '}';
    }

}
