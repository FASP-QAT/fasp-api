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
public class ProgramInitialize extends Program implements Serializable {

    private ProgramPlanningUnit[] programPlanningUnits;
    private int[] fundingSources;
    private int[] procurementAgents;

    public ProgramPlanningUnit[] getProgramPlanningUnits() {
        return programPlanningUnits;
    }

    public void setProgramPlanningUnits(ProgramPlanningUnit[] programPlanningUnits) {
        this.programPlanningUnits = programPlanningUnits;
    }

    public int[] getFundingSources() {
        return fundingSources;
    }

    public void setFundingSources(int[] fundingSources) {
        this.fundingSources = fundingSources;
    }

    public int[] getProcurementAgents() {
        return procurementAgents;
    }

    public void setProcurementAgents(int[] procurementAgents) {
        this.procurementAgents = procurementAgents;
    }

}
