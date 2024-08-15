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
public class ProgramInitialize extends Program implements Serializable {

    private ProgramPlanningUnit[] programPlanningUnits;
    private List<Integer> fundingSources;
    private List<Integer> procurementAgents;

    public ProgramPlanningUnit[] getProgramPlanningUnits() {
        return programPlanningUnits;
    }

    public void setProgramPlanningUnits(ProgramPlanningUnit[] programPlanningUnits) {
        this.programPlanningUnits = programPlanningUnits;
    }

    public List<Integer> getFundingSources() {
        return fundingSources;
    }

    public void setFundingSources(List<Integer> fundingSources) {
        this.fundingSources = fundingSources;
    }

    public List<Integer> getProcurementAgents() {
        return procurementAgents;
    }

    public void setProcurementAgents(List<Integer> procurementAgents) {
        this.procurementAgents = procurementAgents;
    }

}
