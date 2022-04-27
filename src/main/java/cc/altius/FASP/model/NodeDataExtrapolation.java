/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class NodeDataExtrapolation implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataExtapolationId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject extrapolationMethod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    List<ExtrapolationDataReportingRate> extrapolationDataList;

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
