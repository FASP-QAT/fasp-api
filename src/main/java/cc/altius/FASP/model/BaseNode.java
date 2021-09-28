/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class BaseNode extends BaseModel implements Serializable {

    private int nodeId;
    private Integer parentNodeId;
    private SimpleObject nodeType;
    private SimpleObject nodeUnit;
    private Label label;
    private List<NodeData> nodeDataList;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public SimpleObject getNodeType() {
        return nodeType;
    }

    public void setNodeType(SimpleObject nodeType) {
        this.nodeType = nodeType;
    }

    public SimpleObject getNodeUnit() {
        return nodeUnit;
    }

    public void setNodeUnit(SimpleObject nodeUnit) {
        this.nodeUnit = nodeUnit;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<NodeData> getNodeDataList() {
        return nodeDataList;
    }

    public void setNodeDataList(List<NodeData> nodeDataList) {
        this.nodeDataList = nodeDataList;
    }

}
