/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramAndPlanningUnit implements Serializable {

    @JsonView(Views.ReportView.class)
    private int programId;
    @JsonView(Views.ReportView.class)
    private int planningUnitId;

    public ProgramAndPlanningUnit(int programId, int planningUnitId) {
        this.programId = programId;
        this.planningUnitId = planningUnitId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

}
