/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class TreeNodeDataPu extends BaseModel implements Serializable {

    private int nodeDataPuId;
    private SimpleUnitObject planningUnit;
    private boolean sharePlanningUnit;
    private int refillMonths;

    public int getNodeDataPuId() {
        return nodeDataPuId;
    }

    public void setNodeDataPuId(int nodeDataPuId) {
        this.nodeDataPuId = nodeDataPuId;
    }

    public SimpleUnitObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleUnitObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public boolean isSharePlanningUnit() {
        return sharePlanningUnit;
    }

    public void setSharePlanningUnit(boolean sharePlanningUnit) {
        this.sharePlanningUnit = sharePlanningUnit;
    }

    public int getRefillMonths() {
        return refillMonths;
    }

    public void setRefillMonths(int refillMonths) {
        this.refillMonths = refillMonths;
    }

}
