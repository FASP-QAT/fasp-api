/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentOverviewInput implements Serializable {

    private int realmId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] planningUnitIds;
    private String[] fundingSourceIds;
    private String[] shipmentStatusIds;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public String[] getFundingSourceIds() {
        return fundingSourceIds;
    }

    public void setFundingSourceIds(String[] fundingSourceIds) {
        this.fundingSourceIds = fundingSourceIds;
    }

    public String[] getShipmentStatusIds() {
        return shipmentStatusIds;
    }

    public void setShipmentStatusIds(String[] shipmentStatusIds) {
        this.shipmentStatusIds = shipmentStatusIds;
    }

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.planningUnitIds);
            if (this.planningUnitIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getFundingSourceIdsString() {
        if (this.fundingSourceIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.fundingSourceIds);
            if (this.fundingSourceIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getShipmentStatusIdsString() {
        if (this.shipmentStatusIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.shipmentStatusIds);
            if (this.shipmentStatusIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
}
