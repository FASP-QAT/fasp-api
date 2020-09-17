/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import com.sun.source.doctree.SerialDataTree;
import java.io.Serializable;

/**
 *
 * @author altius
 */
public class ArtmisShipmentDTO implements Serializable {

    private int shipmentId;
    private int fundingSourceId;
    private int budgetId;
    private boolean accountFlag;
    private boolean emergencyOrder;

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public boolean isAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(boolean accountFlag) {
        this.accountFlag = accountFlag;
    }

    public boolean isEmergencyOrder() {
        return emergencyOrder;
    }

    public void setEmergencyOrder(boolean emergencyOrder) {
        this.emergencyOrder = emergencyOrder;
    }

    @Override
    public String toString() {
        return "ArtmisShipmentDTO{" + "shipmentId=" + shipmentId + ", fundingSourceId=" + fundingSourceId + ", budgetId=" + budgetId + ", accountFlag=" + accountFlag + ", emergencyOrder=" + emergencyOrder + '}';
    }

}
