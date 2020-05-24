/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class TempProgramVersion {

    private int programId;
    private int erpOrderId;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(int erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

}
