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

}
