/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class DashboardTop implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private int activePlanningUnits;
    @JsonView(Views.ReportView.class)
    private int disabledPlanningUnits;
    @JsonView(Views.ReportView.class)
    private int countOfStockOutPU;
    @JsonView(Views.ReportView.class)
    private double valueOfExpiredPU;
    @JsonView(Views.ReportView.class)
    private int countOfOpenProblem;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date lastModifiedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date commitDate;
    @JsonView(Views.ReportView.class)
    private SimpleObject versionType;
    @JsonView(Views.ReportView.class)
    private SimpleObject versionStatus;
    @JsonView(Views.ReportView.class)
    private int versionId;
    @JsonView(Views.ReportView.class)
    private SimpleObject latestFinalVersionStatus;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date latestFinalVersionLastModifiedDate;

    public DashboardTop() {
    }

    public DashboardTop(SimpleCodeObject program, int activePlanningUnits, int disabledPlanningUnits, Date lastModifiedDate, Date commitDate, SimpleObject versionType, SimpleObject versionStatus, int countOfOpenProblem, int versionId, SimpleObject latestFinalVersionStatus, Date latestFinalVersionLastModifiedDate) {
        this.program = program;
        this.activePlanningUnits = activePlanningUnits;
        this.disabledPlanningUnits = disabledPlanningUnits;
        this.lastModifiedDate = lastModifiedDate;
        this.commitDate = commitDate;
        this.versionType = versionType;
        this.versionStatus = versionStatus;
        this.countOfOpenProblem = countOfOpenProblem;
        this.versionId = versionId;
        this.latestFinalVersionStatus = latestFinalVersionStatus;
        this.latestFinalVersionLastModifiedDate = latestFinalVersionLastModifiedDate;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public int getActivePlanningUnits() {
        return activePlanningUnits;
    }

    public void setActivePlanningUnits(int activePlanningUnits) {
        this.activePlanningUnits = activePlanningUnits;
    }

    public int getDisabledPlanningUnits() {
        return disabledPlanningUnits;
    }

    public void setDisabledPlanningUnits(int disabledPlanningUnits) {
        this.disabledPlanningUnits = disabledPlanningUnits;
    }

    public int getCountOfStockOutPU() {
        return countOfStockOutPU;
    }

    public void setCountOfStockOutPU(int countOfStockOutPU) {
        this.countOfStockOutPU = countOfStockOutPU;
    }

    public double getValueOfExpiredPU() {
        return valueOfExpiredPU;
    }

    public void setValueOfExpiredPU(double valueOfExpiredPU) {
        this.valueOfExpiredPU = valueOfExpiredPU;
    }

    public int getCountOfOpenProblem() {
        return countOfOpenProblem;
    }

    public void setCountOfOpenProblem(int countOfOpenProblem) {
        this.countOfOpenProblem = countOfOpenProblem;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public SimpleObject getVersionType() {
        return versionType;
    }

    public void setVersionType(SimpleObject versionType) {
        this.versionType = versionType;
    }

    public SimpleObject getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(SimpleObject versionStatus) {
        this.versionStatus = versionStatus;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersion(int versionId) {
        this.versionId = versionId;
    }

    public SimpleObject getLatestFinalVersionStatus() {
        return latestFinalVersionStatus;
    }

    public void setLatestFinalVersionStatus(SimpleObject latestFinalVersionStatus) {
        this.latestFinalVersionStatus = latestFinalVersionStatus;
    }

    public Date getLatestFinalVersionLastModifiedDate() {
        return latestFinalVersionLastModifiedDate;
    }

    public void setLatestFinalVersionLastModifiedDate(Date latestFinalVersionLastModifiedDate) {
        this.latestFinalVersionLastModifiedDate = latestFinalVersionLastModifiedDate;
    }

}
