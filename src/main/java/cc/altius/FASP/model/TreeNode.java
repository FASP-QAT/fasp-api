/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public class TreeNode implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer parentNodeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private NodeType nodeType;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleCodeObject nodeUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Label label;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean collapsed;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    // Key is Scenario Id in the case of TreeTemplate the Scenario is 0
    private Map<Integer, List<TreeNodeData>> nodeDataMap; 

    public TreeNode() {
        this.nodeDataMap = new HashMap<>();
    }

    public TreeNode(int nodeId, Integer parentNodeId, NodeType nodeType, SimpleCodeObject nodeUnit, Label label, boolean collapsed) {
        this.nodeId = nodeId;
        this.parentNodeId = parentNodeId;
        this.nodeType = nodeType;
        this.nodeUnit = nodeUnit;
        this.label = label;
        this.collapsed = collapsed;
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

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
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

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
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

    @Override
    public String toString() {
        return "TreeNode{" + "nodeId=" + nodeId + ", parentNodeId=" + parentNodeId + ", label=" + label + ", nodeDataMap=" + nodeDataMap + '}';
    }

}
