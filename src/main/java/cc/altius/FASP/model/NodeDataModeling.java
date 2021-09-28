/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class NodeDataModeling extends BaseModel implements Serializable {

    private int nodeDataModelingId;
    private Date startDate;
    private Date stopDate;
    private ModelingType modelingType;
    private Double dataValue;
    private int transferNodeId;
    private String notes;

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

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public ModelingType getModelingType() {
        return modelingType;
    }

    public void setModelingType(ModelingType modelingType) {
        this.modelingType = modelingType;
    }

    public Double getDataValue() {
        return dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public int getTransferNodeId() {
        return transferNodeId;
    }

    public void setTransferNodeId(int transferNodeId) {
        this.transferNodeId = transferNodeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
}
