/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DatasetVersionListInput implements Serializable {

    @JsonView(Views.InternalView.class)
    private String[] programIds;  // empty for none
    @JsonView(Views.InternalView.class)
    private int versionTypeId;  // -1 for all
    @JsonView(Views.InternalView.class)
    private String startDate;
    @JsonView(Views.InternalView.class)
    private String stopDate;

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public int getVersionTypeId() {
        return versionTypeId;
    }

    public void setVersionTypeId(int versionTypeId) {
        this.versionTypeId = versionTypeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || startDate.isBlank()) {
            this.startDate = "";
        } else {
            this.startDate = startDate + " 00:00:00";
        }
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        if (stopDate == null || stopDate.isBlank()) {
            this.stopDate = "";
        } else {
            this.stopDate = stopDate + " 00:00:00";
        }
    }

    @Override
    public String toString() {
        return "DatasetVersionListInput{" + "programIds=" + programIds + ", versionTypeId=" + versionTypeId + ", startDate=" + startDate + ", stopDate=" + stopDate + '}';
    }

}
