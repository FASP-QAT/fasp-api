/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class CommitRequestInput implements Serializable {

    private String startDate;
    private String stopDate;
    private String[] programIds;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public String getProgramIdsString() {
        if (this.programIds == null) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }

}
