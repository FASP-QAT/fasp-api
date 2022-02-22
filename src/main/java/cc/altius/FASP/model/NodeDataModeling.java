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
public class NodeDataModeling implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataModelingId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int startDateNo;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int stopDateNo;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject modelingType;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private double dataValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer transferNodeDataId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;

    public NodeDataModeling() {
    }

    public NodeDataModeling(int nodeDataModelingId) {
        this.nodeDataModelingId = nodeDataModelingId;
    }

    public int getNodeDataModelingId() {
        return nodeDataModelingId;
    }

    public void setNodeDataModelingId(int nodeDataModelingId) {
        this.nodeDataModelingId = nodeDataModelingId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStartDateNo() {
        return startDateNo;
    }

    public void setStartDateNo(int startDateNo) {
        this.startDateNo = startDateNo;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public int getStopDateNo() {
        return stopDateNo;
    }

    public void setStopDateNo(int stopDateNo) {
        this.stopDateNo = stopDateNo;
    }

    public SimpleObject getModelingType() {
        return modelingType;
    }

    public void setModelingType(SimpleObject modelingType) {
        this.modelingType = modelingType;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }

    public Integer getTransferNodeDataId() {
        return transferNodeDataId;
    }

    public void setTransferNodeDataId(Integer transferNodeDataId) {
        this.transferNodeDataId = transferNodeDataId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.nodeDataModelingId;
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
        final NodeDataModeling other = (NodeDataModeling) obj;
        if (this.nodeDataModelingId != other.nodeDataModelingId) {
            return false;
        }
        return true;
    }
    
    
}
