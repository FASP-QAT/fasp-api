/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author akil
 */
public class SupplyPlanCommitRequest {

    private int commitRequestId;
    private SimpleCodeObject program;
    private int committedVersionId;
    private SimpleObject versionType;
    private String notes;
    private boolean saveData;
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.IgnoreView.class)
    private Date createdDate;
    private int status;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.IgnoreView.class)
    private Date completedDate;

    public int getCommitRequestId() {
        return commitRequestId;
    }

    public void setCommitRequestId(int commitRequestId) {
        this.commitRequestId = commitRequestId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public int getCommittedVersionId() {
        return committedVersionId;
    }

    public void setCommittedVersionId(int committedVersionId) {
        this.committedVersionId = committedVersionId;
    }

    public SimpleObject getVersionType() {
        return versionType;
    }

    public void setVersionType(SimpleObject versionType) {
        this.versionType = versionType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isSaveData() {
        return saveData;
    }

    public void setSaveData(boolean saveData) {
        this.saveData = saveData;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

}
