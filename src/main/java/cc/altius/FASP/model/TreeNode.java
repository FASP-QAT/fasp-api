/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public class TreeNode extends BaseModel implements Serializable {

    private int nodeId;
    private Integer parentNodeId;
    private SimpleObject nodeType;
    private SimpleCodeObject nodeUnit;
    private Label label;
    private Map<Integer, List<TreeNodeData>> nodeDataMap; // Key is Scenario Id in the case of TreeTemplate the Scenario is 0

    public TreeNode() {
        this.nodeDataMap = new HashMap<>();
    }

    public TreeNode(int nodeId, Integer parentNodeId, SimpleObject nodeType, SimpleCodeObject nodeUnit, Label label) {
        this.nodeId = nodeId;
        this.parentNodeId = parentNodeId;
        this.nodeType = nodeType;
        this.nodeUnit = nodeUnit;
        this.label = label;
        this.nodeDataMap = new HashMap<>();
    }

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

    public SimpleCodeObject getNodeUnit() {
        return nodeUnit;
    }

    public void setNodeUnit(SimpleCodeObject nodeUnit) {
        this.nodeUnit = nodeUnit;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Map<Integer, List<TreeNodeData>> getNodeDataMap() {
        return nodeDataMap;
    }

    public void setNodeDataMap(Map<Integer, List<TreeNodeData>> nodeDataMap) {
        this.nodeDataMap = nodeDataMap;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.nodeId;
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
        final TreeNode other = (TreeNode) obj;
        if (this.nodeId != other.nodeId) {
            return false;
        }
        return true;
    }

}
