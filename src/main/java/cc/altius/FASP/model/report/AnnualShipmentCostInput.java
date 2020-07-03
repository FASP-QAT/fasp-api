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
    private int procurementAgentId;
    private int fundingSourceId;
    private int shipmentStatusId;
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

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public int getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(int shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
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

}
