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
public class NodeDataOverride extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataOverrideId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Date month;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer monthNo;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double manualChange;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double seasonalityPerc;

    public NodeDataOverride() {
    }

    public NodeDataOverride(int nodeDataOverrideId) {
        this.nodeDataOverrideId = nodeDataOverrideId;
    }

    public int getNodeDataOverrideId() {
        return nodeDataOverrideId;
    }

    public void setNodeDataOverrideId(int nodeDataOverrideId) {
        this.nodeDataOverrideId = nodeDataOverrideId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(Integer monthNo) {
        this.monthNo = monthNo;
    }

    public Double getManualChange() {
        return manualChange;
    }

    public void setManualChange(Double manualChange) {
        this.manualChange = manualChange;
    }

    public Double getSeasonalityPerc() {
        return seasonalityPerc;
    }

    public void setSeasonalityPerc(Double seasonalityPerc) {
        this.seasonalityPerc = seasonalityPerc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.nodeDataOverrideId;
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
        final NodeDataOverride other = (NodeDataOverride) obj;
        if (this.nodeDataOverrideId != other.nodeDataOverrideId) {
            return false;
        }
        return true;
    }

}
