/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitProcurementAgentInput {

    private List<String> programIdList;
    private List<String> planningUnitIdList;

    public List<String> getProgramIdList() {
        return programIdList;
    }

    public void setProgramIdList(List<String> programIdList) {
        this.programIdList = programIdList;
    }

    public List<String> getPlanningUnitIdList() {
        return planningUnitIdList;
    }

    public void setPlanningUnitIdList(List<String> planningUnitIdList) {
        this.planningUnitIdList = planningUnitIdList;
    }

}
