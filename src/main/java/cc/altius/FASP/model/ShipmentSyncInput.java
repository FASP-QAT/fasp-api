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
public class ShipmentSyncInput implements Serializable {

    private int programId;
    private String lastSyncDate;
    private List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(String lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public List<RoAndRoPrimeLineNo> getRoAndRoPrimeLineNoList() {
        return roAndRoPrimeLineNoList;
    }

    public void setRoAndRoPrimeLineNoList(List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList) {
        this.roAndRoPrimeLineNoList = roAndRoPrimeLineNoList;
    }

}
