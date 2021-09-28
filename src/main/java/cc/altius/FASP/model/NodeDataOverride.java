/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class NodeDataOverride extends BaseModel implements Serializable {

    private int nodeDataOverrideId;
    private int month;
    private Date manualChange;
    private Date seasonalityPerc;

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

    public Date getManualChange() {
        return manualChange;
    }

    public void setManualChange(Date manualChange) {
        this.manualChange = manualChange;
    }

    public Date getSeasonalityPerc() {
        return seasonalityPerc;
    }

    public void setSeasonalityPerc(Date seasonalityPerc) {
        this.seasonalityPerc = seasonalityPerc;
    }

}
