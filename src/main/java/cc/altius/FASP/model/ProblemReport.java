/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
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

    private int problemReportId;
    private SimpleCodeObject program;
    private int versionId;
    private RealmProblem realmProblem;
    private String dt;
    private SimpleObject region;
    private SimpleObject planningUnit;
    private Integer shipmentId;
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;
    private SimpleObject problemStatus;
    private SimpleObject problemType;
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date lastModifiedDate;
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

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
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

    public SimpleObject getProblemType() {
        return problemType;
    }

    public void setProblemType(SimpleObject problemType) {
        this.problemType = problemType;
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
