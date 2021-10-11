/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ProblemReportTrans implements Serializable {

    @JsonView(Views.InternalView.class)
    private int problemReportTransId;
    @JsonView(Views.InternalView.class)
    private SimpleObject problemStatus;
    @JsonView(Views.InternalView.class)
    private String notes;
    @JsonView(Views.InternalView.class)
    private boolean reviewed;
    @JsonView(Views.InternalView.class)
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date createdDate;

    public int getProblemReportTransId() {
        return problemReportTransId;
    }

    public void setProblemReportTransId(int problemReportTransId) {
        this.problemReportTransId = problemReportTransId;
    }

    public SimpleObject getProblemStatus() {
        return problemStatus;
    }

    public void setProblemStatus(SimpleObject problemStatus) {
        this.problemStatus = problemStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
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

    }
