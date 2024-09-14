/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

/**
 *
 * @author akil
 */
public class DashboardActualConsumption {

    private int planningUnitId;
    private DashboardActualConsumptionDetails dacd;

    public DashboardActualConsumption() {
    }

    public DashboardActualConsumption(int planningUnitId, DashboardActualConsumptionDetails dacd) {
        this.planningUnitId = planningUnitId;
        this.dacd = dacd;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public DashboardActualConsumptionDetails getDacd() {
        return dacd;
    }

    public void setDacd(DashboardActualConsumptionDetails dacd) {
        this.dacd = dacd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.planningUnitId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DashboardActualConsumption other = (DashboardActualConsumption) obj;
        return this.planningUnitId == other.planningUnitId;
    }

}
