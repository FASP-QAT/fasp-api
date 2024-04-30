/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitProcurementAgentPrice extends BaseModel implements Serializable {

    private int programPlanningUnitProcurementAgentId;
    private int programPlanningUnitId;
    private SimpleCodeObject procurementAgent;
    private SimpleObject planningUnit;
    private SimpleObject program;
    private Double price;
    private Double seaFreightPerc;
    private Double airFreightPerc;
    private Double roadFreightPerc;
    private Double localProcurementLeadTime;

    public int getProgramPlanningUnitProcurementAgentId() {
        return programPlanningUnitProcurementAgentId;
    }

    public void setProgramPlanningUnitProcurementAgentId(int programPlanningUnitProcurementAgentId) {
        this.programPlanningUnitProcurementAgentId = programPlanningUnitProcurementAgentId;
    }

    public int getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(int programPlanningUnitId) {
        this.programPlanningUnitId = programPlanningUnitId;
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

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSeaFreightPerc() {
        return seaFreightPerc;
    }

    public void setSeaFreightPerc(Double seaFreightPerc) {
        this.seaFreightPerc = seaFreightPerc;
    }

    public Double getAirFreightPerc() {
        return airFreightPerc;
    }

    public void setAirFreightPerc(Double airFreightPerc) {
        this.airFreightPerc = airFreightPerc;
    }

    public Double getRoadFreightPerc() {
        return roadFreightPerc;
    }

    public void setRoadFreightPerc(Double roadFreightPerc) {
        this.roadFreightPerc = roadFreightPerc;
    }

    public Double getLocalProcurementLeadTime() {
        return localProcurementLeadTime;
    }

    public void setLocalProcurementLeadTime(Double localProcurementLeadTime) {
        this.localProcurementLeadTime = localProcurementLeadTime;
    }

}
