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
public class ProcurementAgentPlanningUnitProgramPrice extends BaseModel implements Serializable {

    private int procurementAgentPlanningUnitProgramId;
    private int procurementAgentPlanningUnitId;
    private SimpleCodeObject procurementAgent;
    private SimpleObject planningUnit;
    private SimpleObject program;
    private Double price;

    public int getProcurementAgentPlanningUnitProgramId() {
        return procurementAgentPlanningUnitProgramId;
    }

    public void setProcurementAgentPlanningUnitProgramId(int procurementAgentPlanningUnitProgramId) {
        this.procurementAgentPlanningUnitProgramId = procurementAgentPlanningUnitProgramId;
    }

    public int getProcurementAgentPlanningUnitId() {
        return procurementAgentPlanningUnitId;
    }

    public void setProcurementAgentPlanningUnitId(int procurementAgentPlanningUnitId) {
        this.procurementAgentPlanningUnitId = procurementAgentPlanningUnitId;
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

}
