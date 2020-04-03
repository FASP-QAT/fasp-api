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
public class ProgramPlanningUnit implements Serializable {

    private int programId;
    private Label label;
    private PlanningUnitForProgramMapping[] planningUnits;
    private List<PlanningUnitForProgramMapping> planningUnitList;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public PlanningUnitForProgramMapping[] getPlanningUnits() {
        return planningUnits;
    }

    public void setPlanningUnits(PlanningUnitForProgramMapping[] planningUnits) {
        this.planningUnits = planningUnits;
    }

    public List<PlanningUnitForProgramMapping> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<PlanningUnitForProgramMapping> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }
    
}
