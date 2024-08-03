/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ProgramVersionTrans implements Serializable {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int versionId;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleObject versionStatus;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private String notes;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private BasicUser lastModifiedBy;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastModifiedDate;

    public ProgramVersionTrans(int versionId, SimpleObject versionStatus, String notes, BasicUser lastModifiedBy, Date lastModifiedDate) {
        this.versionId = versionId;
        this.versionStatus = versionStatus;
        this.notes = notes;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public ProgramVersionTrans() {
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public SimpleObject getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(SimpleObject versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

}
