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
public class UnaccountedConsumption implements Serializable {

    private Date transDate;
    private int consumptionQty;

    public UnaccountedConsumption() {
    }

    public UnaccountedConsumption(Date transDate, int consumptionQty) {
        this.transDate = transDate;
        this.consumptionQty = consumptionQty;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public int getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(int consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

}
