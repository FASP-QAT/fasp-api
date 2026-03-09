/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ShipmentOverviewFspaCostAndPerc implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fspa;
    @JsonView(Views.ReportView.class)
    private double cost;
    @JsonView(Views.ReportView.class)
    private double perc;

    public SimpleCodeObject getFspa() {
        return fspa;
    }

    public void setFspa(SimpleCodeObject fspa) {
        this.fspa = fspa;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.fspa);
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
        final ShipmentOverviewFspaCostAndPerc other = (ShipmentOverviewFspaCostAndPerc) obj;
        return Objects.equals(this.fspa, other.fspa);
    }

}
