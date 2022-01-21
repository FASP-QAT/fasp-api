/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class TreeNodeDataPu implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataPuId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleUnitObjectWithMultiplier planningUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean sharePlanningUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer refillMonths;

    public int getNodeDataPuId() {
        return nodeDataPuId;
    }

    public void setNodeDataPuId(int nodeDataPuId) {
        this.nodeDataPuId = nodeDataPuId;
    }

    public SimpleUnitObjectWithMultiplier getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleUnitObjectWithMultiplier planningUnit) {
        this.planningUnit = planningUnit;
    }

    public boolean isSharePlanningUnit() {
        return sharePlanningUnit;
    }

    public void setSharePlanningUnit(boolean sharePlanningUnit) {
        this.sharePlanningUnit = sharePlanningUnit;
    }

    public Integer getRefillMonths() {
        return refillMonths;
    }

    public void setRefillMonths(Integer refillMonths) {
        this.refillMonths = refillMonths;
    }

    @Override
    public String toString() {
        return "TreeNodeDataPu{" + "nodeDataPuId=" + nodeDataPuId + '}';
    }

}
