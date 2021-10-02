/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author akil
 */
public class TreeNodeData extends BaseModel implements Serializable {

    private int nodeDataId;
    private Date month;
    private Double dataValue;
    private TreeNodeDataFu fuNode;
    private TreeNodeDataPu puNode;
    private String notes;
    private List<NodeDataModeling> nodeDataModelingList;
    private List<NodeDataOverride> nodeDataOverrideList;

    public int getNodeDataId() {
        return nodeDataId;
    }

    public void setNodeDataId(int nodeDataId) {
        this.nodeDataId = nodeDataId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Double getDataValue() {
        return dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public TreeNodeDataFu getFuNode() {
        return fuNode;
    }

    public void setFuNode(TreeNodeDataFu fuNode) {
        this.fuNode = fuNode;
    }

    public TreeNodeDataPu getPuNode() {
        return puNode;
    }

    public void setPuNode(TreeNodeDataPu puNode) {
        this.puNode = puNode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<NodeDataModeling> getNodeDataModelingList() {
        return nodeDataModelingList;
    }

    public void setNodeDataModelingList(List<NodeDataModeling> nodeDataModelingList) {
        this.nodeDataModelingList = nodeDataModelingList;
    }

    public List<NodeDataOverride> getNodeDataOverrideList() {
        return nodeDataOverrideList;
    }

    public void setNodeDataOverrideList(List<NodeDataOverride> nodeDataOverrideList) {
        this.nodeDataOverrideList = nodeDataOverrideList;
    }
    
    

}
