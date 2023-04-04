/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
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
public class ManualIntegration implements Serializable {

    @JsonView(Views.ReportView.class)
    private int manualIntegrationId;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private int versionId;
    @JsonView(Views.ReportView.class)
    private int integrationId;
    @JsonView(Views.ReportView.class)
    private String integrationName;
    @JsonView(Views.ReportView.class)
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date createdDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date completedDate;

    public ManualIntegration() {
    }

    public ManualIntegration(int manualIntegrationId, SimpleCodeObject program, int versionId, int integrationId, String integrationName, BasicUser createdBy, Date createdDate, Date completedDate) {
        this.manualIntegrationId = manualIntegrationId;
        this.program = program;
        this.versionId = versionId;
        this.integrationId = integrationId;
        this.integrationName = integrationName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }

    public int getManualIntegrationId() {
        return manualIntegrationId;
    }

    public void setManualIntegrationId(int manualIntegrationId) {
        this.manualIntegrationId = manualIntegrationId;
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

    public int getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(int integrationId) {
        this.integrationId = integrationId;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
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

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

}
