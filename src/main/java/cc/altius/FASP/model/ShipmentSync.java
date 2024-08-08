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
public class ShipmentSync implements Serializable {

    private int programId;
    private int versionId;
    private int userId;
    private List<Shipment> shipmentList;
    private List<Batch> batchInfoList;
    private List<ProblemReport> problemReportList;
    private String versionNotes;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public List<Batch> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<Batch> batchInfoList) {
        this.batchInfoList = batchInfoList;
    }

    public List<ProblemReport> getProblemReportList() {
        return problemReportList;
    }

    public void setProblemReportList(List<ProblemReport> problemReportList) {
        this.problemReportList = problemReportList;
    }

    public String getVersionNotes() {
        return versionNotes;
    }

    public void setVersionNotes(String versionNotes) {
        this.versionNotes = versionNotes;
    }

}
