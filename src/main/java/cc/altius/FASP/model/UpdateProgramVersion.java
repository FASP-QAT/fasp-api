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
public class UpdateProgramVersion implements Serializable {

    private List<ReviewedProblem> reviewedProblemList;
    private String notes;
    private boolean resetProblem;

    public List<ReviewedProblem> getReviewedProblemList() {
        return reviewedProblemList;
    }

    public void setReviewedProblemList(List<ReviewedProblem> reviewedProblemList) {
        this.reviewedProblemList = reviewedProblemList;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isResetProblem() {
        return resetProblem;
    }

    public void setResetProblem(boolean resetProblem) {
        this.resetProblem = resetProblem;
    }

}
