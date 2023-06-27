/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
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
    private List<TreeLevel> levelList;
    @JsonView(Views.InternalView.class)
    private List<TreeScenario> scenarioList;
    // TODO -- Why does a Tree have many regions?
    @JsonView(Views.InternalView.class)
    private List<SimpleObject> regionList;
    // TODO -- Notes for the entire Tree
    @JsonView(Views.InternalView.class)
    private String notes;

    public DatasetTree() {
        this.levelList = new LinkedList<>();
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

    public List<TreeLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<TreeLevel> levelList) {
        this.levelList = levelList;
    }

    public List<TreeScenario> getScenarioList() {
        return scenarioList;
    }

    public void setScenarioList(List<TreeScenario> scenarioList) {
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
    @JsonView(Views.InternalView.class)
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    @JsonView(Views.InternalView.class)
    public void setActive(boolean active) {
        super.setActive(active);
    }

    @Override
    @JsonView(Views.InternalView.class)
    public BasicUser getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonView(Views.InternalView.class)
    public void setCreatedBy(BasicUser createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    @JsonView(Views.InternalView.class)
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    @JsonView(Views.InternalView.class)
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    @JsonView(Views.InternalView.class)
    public BasicUser getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    @JsonView(Views.InternalView.class)
    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        super.setLastModifiedBy(lastModifiedBy);
    }

    @Override
    @JsonView(Views.InternalView.class)
    public Date getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @Override
    @JsonView(Views.InternalView.class)
    public void setLastModifiedDate(Date lastModifiedDate) {
        super.setLastModifiedDate(lastModifiedDate);
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
