/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
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
public class Version implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject versionType;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject versionStatus;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private String notes;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private BasicUser createdBy;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView({Views.InternalView.class, Views.DropDownView.class, Views.ReportView.class})
    private Date createdDate;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Date lastModifiedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private Date forecastStartDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private Date forecastStopDate;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Integer daysInMonth;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Double freightPerc;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Double forecastThresholdHighPerc;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Double forecastThresholdLowPerc;

    public Version() {
    }

    public Version(int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, BasicUser createdBy, Date createdDate, BasicUser lastModifiedBy, Date lastModifiedDate) {
        this.versionId = versionId;
        this.versionType = versionType;
        this.versionStatus = versionStatus;
        this.notes = notes;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Version(int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, int createdByUserId, Date createdDate, int lastModifiedByUserId, Date lastModifiedDate) {
        this.versionId = versionId;
        this.versionType = versionType;
        this.versionStatus = versionStatus;
        this.notes = notes;
        this.createdBy = new BasicUser(createdByUserId, "");
        this.createdDate = createdDate;
        this.lastModifiedBy = new BasicUser(lastModifiedByUserId, "");
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getForecastStartDate() {
        return forecastStartDate;
    }

    public void setForecastStartDate(Date forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
    }

    public Date getForecastStopDate() {
        return forecastStopDate;
    }

    public void setForecastStopDate(Date forecastStopDate) {
        this.forecastStopDate = forecastStopDate;
    }

    public Integer getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(Integer daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public Double getFreightPerc() {
        return freightPerc;
    }

    public void setFreightPerc(Double freightPerc) {
        this.freightPerc = freightPerc;
    }

    public Double getForecastThresholdHighPerc() {
        return forecastThresholdHighPerc;
    }

    public void setForecastThresholdHighPerc(Double forecastThresholdHighPerc) {
        this.forecastThresholdHighPerc = forecastThresholdHighPerc;
    }

    public Double getForecastThresholdLowPerc() {
        return forecastThresholdLowPerc;
    }

    public void setForecastThresholdLowPerc(Double forecastThresholdLowPerc) {
        this.forecastThresholdLowPerc = forecastThresholdLowPerc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.versionId;
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
        final Version other = (Version) obj;
        if (this.versionId != other.versionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Version{" + "versionId=" + versionId + ", versionType=" + versionType + ", versionStatus=" + versionStatus + ", notes=" + notes + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
