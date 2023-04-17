/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.RoAndRoPrimeLineNo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ErpAutoCompleteDTO implements Serializable {

    private String roPo;
    private int programId;
    private int erpPlanningUnitId;
    private int qatPlanningUnitId;
    private List<RoAndRoPrimeLineNo> delinkedList;

    public String getRoPo() {
        return roPo;
    }

    public void setRoPo(String roPo) {
        this.roPo = roPo;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getErpPlanningUnitId() {
        return erpPlanningUnitId;
    }

    public void setErpPlanningUnitId(int erpPlanningUnitId) {
        this.erpPlanningUnitId = erpPlanningUnitId;
    }

    public int getQatPlanningUnitId() {
        return qatPlanningUnitId;
    }

    public void setQatPlanningUnitId(int qatPlanningUnitId) {
        this.qatPlanningUnitId = qatPlanningUnitId;
    }

    public List<RoAndRoPrimeLineNo> getDelinkedList() {
        return delinkedList;
    }

    public void setDelinkedList(List<RoAndRoPrimeLineNo> delinkedList) {
        this.delinkedList = delinkedList;
    }

}
