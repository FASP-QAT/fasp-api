/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitDTO {

    private int programId;
    private int planningUnitId;

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

    public ProgramPlanningUnitDTO() {
    }

    public ProgramPlanningUnitDTO(int programId, int planningUnitId) {
        this.programId = programId;
        this.planningUnitId = planningUnitId;
    }

}
