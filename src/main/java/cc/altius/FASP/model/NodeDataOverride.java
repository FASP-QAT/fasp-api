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
public class NodeDataOverride extends BaseModel implements Serializable {

    private int nodeDataOverrideId;
    private int month;
    private Double manualChange;
    private Double seasonalityPerc;

    public int getNodeDataOverrideId() {
        return nodeDataOverrideId;
    }

    public void setNodeDataOverrideId(int nodeDataOverrideId) {
        this.nodeDataOverrideId = nodeDataOverrideId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getManualChange() {
        return manualChange;
    }

    public void setManualChange(Double manualChange) {
        this.manualChange = manualChange;
    }

    public Double getSeasonalityPerc() {
        return seasonalityPerc;
    }

    public void setSeasonalityPerc(Double seasonalityPerc) {
        this.seasonalityPerc = seasonalityPerc;
    }

}
