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
public class ShipmentLinkedToOtherProgramOutput implements Serializable {

    private SimpleCodeObject program;
    private BasicUser programManager;
    private int shipmentId;
    private String parentLinkedShipmentId;
    private String roNo;
    private String roPrimeLineNo;
    private double conversionFactor;
    private double shipmentQty;
    private SimpleObject planningUnit;
    private SimpleObjectWithMultiplier realmCountryPlanningUnit;
    private BasicUser lastModifiedBy;
    private Date lastModifiedDate;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public BasicUser getProgramManager() {
        return programManager;
    }

    public void setProgramManager(BasicUser programManager) {
        this.programManager = programManager;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getParentLinkedShipmentId() {
        return parentLinkedShipmentId;
    }

    public void setParentLinkedShipmentId(String parentLinkedShipmentId) {
        this.parentLinkedShipmentId = parentLinkedShipmentId;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(String roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObjectWithMultiplier getRealmCountryPlanningUnit() {
        return realmCountryPlanningUnit;
    }

    public void setRealmCountryPlanningUnit(SimpleObjectWithMultiplier realmCountryPlanningUnit) {
        this.realmCountryPlanningUnit = realmCountryPlanningUnit;
    }

    public double getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(double shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
