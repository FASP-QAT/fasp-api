/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class LoadVersion implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String versionId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject versionType;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject versionStatus;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date forecastStartDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date forecastStopDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Date createdDate;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
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
