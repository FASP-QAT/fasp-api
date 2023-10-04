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
public class ReviewedProblem implements Serializable {

    private int problemReportId;
    private boolean reviewed;
    private String notes;
    private SimpleObject problemStatus;

    // Will come in the case where problemReportId is 0 (which means this is a new problem)
    private RealmProblem realmProblem;
    private String dt;                  // data1
    private SimpleObject region;        // data2
    private SimpleObject planningUnit;  // data3
    private Integer shipmentId;         // data4
    private String data5;

    public int getProblemReportId() {
        return problemReportId;
    }

    public void setProblemReportId(int problemReportId) {
        this.problemReportId = problemReportId;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleObject getProblemStatus() {
        return problemStatus;
    }

    public void setProblemStatus(SimpleObject problemStatus) {
        this.problemStatus = problemStatus;
    }

    public RealmProblem getRealmProblem() {
        return realmProblem;
    }

    public void setRealmProblem(RealmProblem realmProblem) {
        this.realmProblem = realmProblem;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

}
