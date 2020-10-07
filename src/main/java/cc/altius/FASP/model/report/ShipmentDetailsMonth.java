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
public class ShipmentDetailsMonth implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private double plannedCost;
    private double submittedCost;
    private double approvedCost;
    private double shippedCost;
    private double arrivedCost;
    private double receivedCost;
    private double onholdCost;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public double getPlannedCost() {
        return plannedCost;
    }

    public void setPlannedCost(double plannedCost) {
        this.plannedCost = plannedCost;
    }

    public double getSubmittedCost() {
        return submittedCost;
    }

    public void setSubmittedCost(double submittedCost) {
        this.submittedCost = submittedCost;
    }

    public double getApprovedCost() {
        return approvedCost;
    }

    public void setApprovedCost(double approvedCost) {
        this.approvedCost = approvedCost;
    }

    public double getShippedCost() {
        return shippedCost;
    }

    public void setShippedCost(double shippedCost) {
        this.shippedCost = shippedCost;
    }

    public double getArrivedCost() {
        return arrivedCost;
    }

    public void setArrivedCost(double arrivedCost) {
        this.arrivedCost = arrivedCost;
    }

    public double getReceivedCost() {
        return receivedCost;
    }

    public void setReceivedCost(double receivedCost) {
        this.receivedCost = receivedCost;
    }

    public double getOnholdCost() {
        return onholdCost;
    }

    public void setOnholdCost(double onholdCost) {
        this.onholdCost = onholdCost;
    }

}
