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
public class TreeAndScenario implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int treeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int scenarioId;

    public TreeAndScenario() {
    }

    public TreeAndScenario(int treeId, int scenarioId) {
        this.treeId = treeId;
        this.scenarioId = scenarioId;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.treeId;
        hash = 67 * hash + this.scenarioId;
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
        final TreeAndScenario other = (TreeAndScenario) obj;
        if (this.treeId != other.treeId) {
            return false;
        }
        return this.scenarioId == other.scenarioId;
    }

}
