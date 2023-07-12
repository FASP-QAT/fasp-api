/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.RoAndRoPrimeLineNo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class AutoCompletePuDTO implements Serializable {

    private int planningUnitId;
    private String puName;
    private int programId;
    private List<RoAndRoPrimeLineNo> delinkedList;

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String getPuName() {
        return puName;
    }

    public void setPuName(String puName) {
        this.puName = puName;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public List<RoAndRoPrimeLineNo> getDelinkedList() {
        return delinkedList;
    }

    public void setDelinkedList(List<RoAndRoPrimeLineNo> delinkedList) {
        this.delinkedList = delinkedList;
    }

    
}
