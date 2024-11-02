/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandShipmentList implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date transDate;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject country;
    @JsonView(Views.ReportView.class)
    private double amount;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fundingSourceProcurementAgent;
    @JsonView(Views.ReportView.class)
    private SimpleObject shipmentStatus;

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public SimpleCodeObject getFundingSourceProcurementAgent() {
        return fundingSourceProcurementAgent;
    }

    public void setFundingSourceProcurementAgent(SimpleCodeObject fundingSourceProcurementAgent) {
        this.fundingSourceProcurementAgent = fundingSourceProcurementAgent;
    }

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
    
}
