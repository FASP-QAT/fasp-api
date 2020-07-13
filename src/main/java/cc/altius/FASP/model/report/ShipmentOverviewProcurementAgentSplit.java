/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author akil
 */
public class ShipmentOverviewProcurementAgentSplit implements Serializable {

    private SimpleObject planningUnit;
    private int multiplier;
    private Map<String, Integer> procurementAgentQty;
    private int total;

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public Map<String, Integer> getProcurementAgentQty() {
        return procurementAgentQty;
    }

    public void setProcurementAgentQty(Map<String, Integer> procurementAgentQty) {
        this.procurementAgentQty = procurementAgentQty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
