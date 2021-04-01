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
public class Problem extends BaseModel implements Serializable {

    @JsonView(Views.InternalView.class)
    private int problemId;
    @JsonView(Views.InternalView.class)
    private Label label;
    @JsonView(Views.InternalView.class)
    private SimpleObject problemCategory;
    @JsonView(Views.InternalView.class)
    private String actionUrl;
    @JsonView(Views.InternalView.class)
    private Label actionLabel;
    @JsonView(Views.InternalView.class)
    private boolean actualConsumptionTrigger;
    @JsonView(Views.InternalView.class)
    private boolean forecastedConsumptionTrigger;
    @JsonView(Views.InternalView.class)
    private boolean inventoryTrigger;
    @JsonView(Views.InternalView.class)
    private boolean adjustmentTrigger;
    @JsonView(Views.InternalView.class)
    private boolean shipmentTrigger;

    public Problem() {
    }

    public Problem(int problemId, Label label, SimpleObject problemCategory, String actionUrl, Label actionLabel, boolean actualConsumptionTrigger, boolean forecastedConsumptionTrigger, boolean inventoryTrigger, boolean adjustmentTrigger, boolean shipmentTrigger) {
        this.problemId = problemId;
        this.label = label;
        this.problemCategory = problemCategory;
        this.actionUrl = actionUrl;
        this.actionLabel = actionLabel;
        setActive(true);
        this.actualConsumptionTrigger = actualConsumptionTrigger;
        this.forecastedConsumptionTrigger = forecastedConsumptionTrigger;
        this.inventoryTrigger = inventoryTrigger;
        this.adjustmentTrigger = adjustmentTrigger;
        this.shipmentTrigger = shipmentTrigger;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObject getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(SimpleObject problemCategory) {
        this.problemCategory = problemCategory;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public Label getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(Label actionLabel) {
        this.actionLabel = actionLabel;
    }

    public boolean isActualConsumptionTrigger() {
        return actualConsumptionTrigger;
    }

    public void setActualConsumptionTrigger(boolean actualConsumptionTrigger) {
        this.actualConsumptionTrigger = actualConsumptionTrigger;
    }

    public boolean isForecastedConsumptionTrigger() {
        return forecastedConsumptionTrigger;
    }

    public void setForecastedConsumptionTrigger(boolean forecastedConsumptionTrigger) {
        this.forecastedConsumptionTrigger = forecastedConsumptionTrigger;
    }

    public boolean isInventoryTrigger() {
        return inventoryTrigger;
    }

    public void setInventoryTrigger(boolean inventoryTrigger) {
        this.inventoryTrigger = inventoryTrigger;
    }

    public boolean isAdjustmentTrigger() {
        return adjustmentTrigger;
    }

    public void setAdjustmentTrigger(boolean adjustmentTrigger) {
        this.adjustmentTrigger = adjustmentTrigger;
    }

    public boolean isShipmentTrigger() {
        return shipmentTrigger;
    }

    public void setShipmentTrigger(boolean shipmentTrigger) {
        this.shipmentTrigger = shipmentTrigger;
    }

}
