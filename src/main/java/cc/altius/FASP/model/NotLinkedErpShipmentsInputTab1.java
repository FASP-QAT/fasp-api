/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author akil
 */
public class NotLinkedErpShipmentsInputTab1 {

    private int programId;
    private int versionId;
    private int shipmentPlanningUnitId;
    private String roNo;
    private int filterPlanningUnitId;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getShipmentPlanningUnitId() {
        return shipmentPlanningUnitId;
    }

    public void setShipmentPlanningUnitId(int shipmentPlanningUnitId) {
        this.shipmentPlanningUnitId = shipmentPlanningUnitId;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public int getFilterPlanningUnitId() {
        return filterPlanningUnitId;
    }

    public void setFilterPlanningUnitId(int filterPlanningUnitId) {
        this.filterPlanningUnitId = filterPlanningUnitId;
    }

    @Override
    public String toString() {
        return "NotLinkedErpShipmentsInputTab1{" + "programId=" + programId + ", versionId=" + versionId + ", shipmentPlanningUnitId=" + shipmentPlanningUnitId + ", roNo=" + roNo + ", filterPlanningUnitId=" + filterPlanningUnitId + '}';
    }

}
