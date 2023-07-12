/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class NodeDataModeling implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataModelingId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String startDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int startDateNo;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String stopDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int stopDateNo;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject modelingType;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private double dataValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int increaseDecrease; // 1 for increase and -1 for decrease
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer transferNodeDataId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private ModelingCalculator modelingCalculator;

    public NodeDataModeling() {
        this.increaseDecrease = 1;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStartDateNo() {
        return startDateNo;
    }

    public void setStartDateNo(int startDateNo) {
        this.startDateNo = startDateNo;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
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

    public int getIncreaseDecrease() {
        return increaseDecrease;
    }

    public void setIncreaseDecrease(int increaseDecrease) {
        this.increaseDecrease = increaseDecrease;
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

    public ModelingCalculator getModelingCalculator() {
        return modelingCalculator;
    }

    public void setModelingCalculator(ModelingCalculator modelingCalculator) {
        this.modelingCalculator = modelingCalculator;
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
