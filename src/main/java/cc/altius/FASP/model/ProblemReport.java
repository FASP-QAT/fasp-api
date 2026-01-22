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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProblemReport implements Serializable {

    @JsonView(Views.InternalView.class)
    private int problemReportId;
    @JsonView(Views.InternalView.class)
    private SimpleCodeObject program;
    @JsonView(Views.InternalView.class)
    private int versionId;
    @JsonView(Views.InternalView.class)
    private RealmProblem realmProblem;
    @JsonView(Views.InternalView.class)
    private String dt;                  // data1
    @JsonView(Views.InternalView.class)
    private SimpleObject region;        // data2
    @JsonView(Views.InternalView.class)
    private SimpleObject planningUnit;  // data3
    @JsonView(Views.InternalView.class)
    private Integer shipmentId;         // data4
    @JsonView(Views.InternalView.class)
    private Integer tempShipmentId;
    @JsonView(Views.InternalView.class)
    private String data5;               // suggestion + description
    @JsonView(Views.InternalView.class)
    private SimpleObject problemCategory;
    @JsonView(Views.InternalView.class)
    private SimpleObject problemStatus;
    @JsonView(Views.InternalView.class)
    private SimpleObject problemType;
    @JsonView(Views.InternalView.class)
    private boolean reviewed;
    @JsonView(Views.InternalView.class)
    private String reviewNotes;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date reviewedDate;
    @JsonView(Views.InternalView.class)
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date createdDate;
    @JsonView(Views.InternalView.class)
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date lastModifiedDate;
    @JsonView(Views.InternalView.class)
    private List<ProblemReportTrans> problemTransList;

    public ProblemReport() {
        this.problemTransList = new LinkedList<>();
    }

    public int getProblemReportId() {
        return problemReportId;
    }

    public void setProblemReportId(int problemReportId) {
        this.problemReportId = problemReportId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public Integer getTempShipmentId() {
        return tempShipmentId;
    }

    public void setTempShipmentId(Integer tempShipmentId) {
        this.tempShipmentId = tempShipmentId;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public SimpleObject getProblemStatus() {
        return problemStatus;
    }

    public void setProblemStatus(SimpleObject problemStatus) {
        this.problemStatus = problemStatus;
    }

    public SimpleObject getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(SimpleObject problemCategory) {
        this.problemCategory = problemCategory;
    }

    public SimpleObject getProblemType() {
        return problemType;
    }

    public void setProblemType(SimpleObject problemType) {
        this.problemType = problemType;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    public Date getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
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

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<ProblemReportTrans> getProblemTransList() {
        return problemTransList;
    }

    public void setProblemTransList(List<ProblemReportTrans> problemTransList) {
        this.problemTransList = problemTransList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.problemReportId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProblemReport other = (ProblemReport) obj;
        if (this.problemReportId != other.problemReportId) {
            return false;
        }
        return true;
    }

}
