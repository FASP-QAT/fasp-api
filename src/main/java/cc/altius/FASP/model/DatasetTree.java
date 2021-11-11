/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class DatasetTree extends BaseModel {

    @JsonView(Views.InternalView.class)
    private int treeId;
    @JsonView(Views.InternalView.class)
    private Label label;
    @JsonView(Views.InternalView.class)
    private SimpleObjectWithType forecastMethod;
    @JsonView(Views.InternalView.class)
    private ForecastTree<TreeNode> tree;
    @JsonView(Views.InternalView.class)
    private List<SimpleObject> scenarioList;
    @JsonView(Views.InternalView.class)
    private List<SimpleObject> regionList;
    @JsonView(Views.InternalView.class)
    private String notes;

    public DatasetTree() {
        this.scenarioList = new LinkedList<>();
        this.regionList = new LinkedList<>();
    }

    public DatasetTree(int treeId) {
        this.treeId = treeId;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObjectWithType getForecastMethod() {
        return forecastMethod;
    }

    public void setForecastMethod(SimpleObjectWithType forecastMethod) {
        this.forecastMethod = forecastMethod;
    }

    public ForecastTree<TreeNode> getTree() {
        return tree;
    }

    public void setTree(ForecastTree<TreeNode> tree) {
        this.tree = tree;
    }

    public List<SimpleObject> getScenarioList() {
        return scenarioList;
    }

    public void setScenarioList(List<SimpleObject> scenarioList) {
        this.scenarioList = scenarioList;
    }

    public List<SimpleObject> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<SimpleObject> regionList) {
        this.regionList = regionList;
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
        hash = 53 * hash + this.treeId;
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
        final DatasetTree other = (DatasetTree) obj;
        if (this.treeId != other.treeId) {
            return false;
        }
        return true;
    }

}
