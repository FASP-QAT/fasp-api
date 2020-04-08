/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProcurementAgentPlanningUnit implements Serializable {
    
    private int procurementAgentId;
    private Label label; // name of ProcurementAgent
    private PlanningUnitForProcurementAgentMapping[] planningUnits;
    private List<PlanningUnitForProcurementAgentMapping> planningUnitList;

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public PlanningUnitForProcurementAgentMapping[] getPlanningUnits() {
        return planningUnits;
    }

    public void setPlanningUnits(PlanningUnitForProcurementAgentMapping[] planningUnits) {
        this.planningUnits = planningUnits;
    }

    public List<PlanningUnitForProcurementAgentMapping> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<PlanningUnitForProcurementAgentMapping> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }
    
    
}
