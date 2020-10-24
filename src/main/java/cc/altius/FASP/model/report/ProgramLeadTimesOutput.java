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
    private double plannedToSubmittedLeadTime;
    @JsonView(Views.ReportView.class)
    private double submittedToApprovedLeadTime;
    @JsonView(Views.ReportView.class)
    private double approvedToShippedLeadTime;
    @JsonView(Views.ReportView.class)
    private double shippedToArrivedByAirLeadTime;
    @JsonView(Views.ReportView.class)
    private double shippedToArrivedBySeaLeadTime;
    @JsonView(Views.ReportView.class)
    private double arrivedToDeliveredLeadTime;
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

    public double getPlannedSubmittedLeadTime() {
        return plannedToSubmittedLeadTime;
    }

    public void setPlannedToSubmittedLeadTime(double plannedToSubmittedLeadTime) {
        this.plannedToSubmittedLeadTime = plannedToSubmittedLeadTime;
    }

    public double getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(double submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

    public double getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(double approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public double getShippedToArrivedByAirLeadTime() {
        return shippedToArrivedByAirLeadTime;
    }

    public void setShippedToArrivedByAirLeadTime(double shippedToArrivedByAirLeadTime) {
        this.shippedToArrivedByAirLeadTime = shippedToArrivedByAirLeadTime;
    }

    public double getShippedToArrivedBySeaLeadTime() {
        return shippedToArrivedBySeaLeadTime;
    }

    public void setShippedToArrivedBySeaLeadTime(double shippedToArrivedBySeaLeadTime) {
        this.shippedToArrivedBySeaLeadTime = shippedToArrivedBySeaLeadTime;
    }

    public double getArrivedToDeliveredLeadTime() {
        return arrivedToDeliveredLeadTime;
    }

    public void setArrivedToDeliveredLeadTime(double arrivedToDeliveredLeadTime) {
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
    }

    public Double getLocalProcurementAgentLeadTime() {
        return localProcurementAgentLeadTime;
    }

    public void setLocalProcurementAgentLeadTime(Double localProcurementAgentLeadTime) {
        this.localProcurementAgentLeadTime = localProcurementAgentLeadTime;
    }

    public double getTotalSeaLeadTime() {
        return this.plannedToSubmittedLeadTime + this.submittedToApprovedLeadTime + this.approvedToShippedLeadTime + this.shippedToArrivedBySeaLeadTime + this.arrivedToDeliveredLeadTime;
    }

    public double getTotalAirLeadTime() {
        return this.plannedToSubmittedLeadTime + this.submittedToApprovedLeadTime + this.approvedToShippedLeadTime + this.shippedToArrivedByAirLeadTime + this.arrivedToDeliveredLeadTime;
    }
}
