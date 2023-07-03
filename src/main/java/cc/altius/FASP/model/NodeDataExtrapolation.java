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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class NodeDataExtrapolation implements Serializable {

    // TODO -- This is actual Consumption data that is used in the Extrapolation
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataExtapolationId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    // selected Extrapolation method out of all the options
    private SimpleObject extrapolationMethod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    // Actual data that is used in Extrapolation
    List<ExtrapolationDataReportingRate> extrapolationDataList;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Date stopDate;

    public NodeDataExtrapolation() {
    }

    public NodeDataExtrapolation(int nodeDataExtapolationId) {
        this.nodeDataExtapolationId = nodeDataExtapolationId;
        this.extrapolationDataList = new LinkedList<>();
    }

    public int getNodeDataExtapolationId() {
        return nodeDataExtapolationId;
    }

    public void setNodeDataExtapolationId(int nodeDataExtapolationId) {
        this.nodeDataExtapolationId = nodeDataExtapolationId;
    }

    public SimpleObject getExtrapolationMethod() {
        return extrapolationMethod;
    }

    public void setExtrapolationMethod(SimpleObject extrapolationMethod) {
        this.extrapolationMethod = extrapolationMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ExtrapolationDataReportingRate> getExtrapolationDataList() {
        return extrapolationDataList;
    }

    public void setExtrapolationDataList(List<ExtrapolationDataReportingRate> extrapolationDataList) {
        this.extrapolationDataList = extrapolationDataList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.nodeDataExtapolationId;
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
        final NodeDataExtrapolation other = (NodeDataExtrapolation) obj;
        if (this.nodeDataExtapolationId != other.nodeDataExtapolationId) {
            return false;
        }
        return true;
    }

}
