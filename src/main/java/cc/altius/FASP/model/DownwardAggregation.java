/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DownwardAggregation implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int treeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int scenarioId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int targetScenarioId;

    public DownwardAggregation() {
    }

    public DownwardAggregation(int treeId, int scenarioId, int nodeId, int targetScenarioId) {
        this.treeId = treeId;
        this.scenarioId = scenarioId;
        this.nodeId = nodeId;
        this.targetScenarioId = targetScenarioId;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getTargetScenarioId() {
        return targetScenarioId;
    }

    public void setTargetScenarioId(int targetScenarioId) {
        this.targetScenarioId = targetScenarioId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.treeId;
        hash = 37 * hash + this.scenarioId;
        hash = 37 * hash + this.nodeId;
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
        final DownwardAggregation other = (DownwardAggregation) obj;
        if (this.treeId != other.treeId) {
            return false;
        }
        if (this.scenarioId != other.scenarioId) {
            return false;
        }
        return this.nodeId == other.nodeId;
    }

    @Override
    public String toString() {
        return "DownwardAggregation{" + "treeId=" + treeId + ", scenarioId=" + scenarioId + ", nodeId=" + nodeId + '}';
    }

}
