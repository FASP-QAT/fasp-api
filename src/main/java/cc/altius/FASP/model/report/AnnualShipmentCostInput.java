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
import java.util.Date;

/**
 *
 * @author ekta
 */
public class AnnualShipmentCostInput {

    private int programId;
    private int versionId;
    private int planningUnitId;
    private String[] procurementAgentIds;
    private String[] fundingSourceIds;
    private String[] shipmentStatusIds;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int reportBasedOn; // Shipped Date, Recevied Date

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String[] getProcurementAgentIds() {
        return procurementAgentIds;
    }

    public void setProcurementAgentIds(String[] procurementAgentIds) {
        this.procurementAgentIds = procurementAgentIds;
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

    public int getReportBasedOn() {
        return reportBasedOn;
    }

    public void setReportBasedOn(int reportBasedOn) {
        this.reportBasedOn = reportBasedOn;
    }

    public String getProcurementAgentIdString() {
        if (this.procurementAgentIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.procurementAgentIds);
            if (this.procurementAgentIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
    
    public String getFundingSourceIdString() {
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
    
    public String getShipmentStatusIdString() {
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
