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
import java.util.List;

/**
 *
 * @author akil
 */
public class TreeNodeData implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date month;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double dataValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private TreeNodeDataFu fuNode;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private TreeNodeDataPu puNode;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private List<NodeDataModeling> nodeDataModelingList;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
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
