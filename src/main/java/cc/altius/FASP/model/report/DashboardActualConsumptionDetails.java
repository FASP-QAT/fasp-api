/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class DashboardActualConsumptionDetails {

    private Date consumptionDate;
    private int actualCount;

    public DashboardActualConsumptionDetails() {
    }

    public DashboardActualConsumptionDetails(Date consumptionDate, int actualCount) {
        this.consumptionDate = consumptionDate;
        this.actualCount = actualCount;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public int getActualCount() {
        return actualCount;
    }

    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.consumptionDate);
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
        final DashboardActualConsumptionDetails other = (DashboardActualConsumptionDetails) obj;
        return Objects.equals(this.consumptionDate, other.consumptionDate);
    }

}
