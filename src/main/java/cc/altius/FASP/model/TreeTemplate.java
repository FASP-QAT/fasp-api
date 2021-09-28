/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.TreeUtils.Tree;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class TreeTemplate extends BaseModel implements Serializable {

    private int treeTemplateId;
    private SimpleCodeObject realm;
    private Label label;
    private SimpleObject forecastMethod;
    private Tree<BaseNode> nodeList;
    private List<SimpleObject> scenarioList;

    public int getTreeTemplateId() {
        return treeTemplateId;
    }

    public void setTreeTemplateId(int treeTemplateId) {
        this.treeTemplateId = treeTemplateId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObject getForecastMethod() {
        return forecastMethod;
    }

    public void setForecastMethod(SimpleObject forecastMethod) {
        this.forecastMethod = forecastMethod;
    }

    public Tree<BaseNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(Tree<BaseNode> nodeList) {
        this.nodeList = nodeList;
    }

    public List<SimpleObject> getScenarioList() {
        return scenarioList;
    }

    public void setScenarioList(List<SimpleObject> scenarioList) {
        this.scenarioList = scenarioList;
    }

}
