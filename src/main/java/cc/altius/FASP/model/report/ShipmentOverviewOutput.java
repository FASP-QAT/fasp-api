/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public class ShipmentOverviewOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewPlanningUnitQuantity> planningUnitQuantity;
    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewFspaCostAndPerc> fspaCostAndPerc;
    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewFspaSplit> fspaProgramSplit;
    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewFspaSplit> fspaCountrySplit;

    public List<ShipmentOverviewPlanningUnitQuantity> getPlanningUnitQuantity() {
        return planningUnitQuantity;
    }

    public void setPlanningUnitQuantity(List<ShipmentOverviewPlanningUnitQuantity> planningUnitQuantity) {
        this.planningUnitQuantity = planningUnitQuantity;
    }

    public List<ShipmentOverviewFspaCostAndPerc> getFspaCostAndPerc() {
        return fspaCostAndPerc;
    }

    public void setFspaCostAndPerc(List<ShipmentOverviewFspaCostAndPerc> fspaCostAndPerc) {
        this.fspaCostAndPerc = fspaCostAndPerc;
        double totalCost = this.fspaCostAndPerc.stream()
                .mapToDouble(ShipmentOverviewFspaCostAndPerc::getCost)
                .sum();
        this.fspaCostAndPerc.stream().forEach(i -> {
            i.setPerc(i.getCost() / totalCost);
        });
    }

    public List<ShipmentOverviewFspaSplit> getFspaProgramSplit() {
        return fspaProgramSplit;
    }

    public void setFspaProgramSplit(List<ShipmentOverviewFspaSplit> fspaProgramSplit) {
        this.fspaProgramSplit = fspaProgramSplit;
        double totalCost = this.fspaProgramSplit.stream()
                .mapToDouble(ShipmentOverviewFspaSplit::getTotalCost)
                .sum();
        this.fspaProgramSplit.stream().forEach(i -> {
            i.setPerc(i.getTotalCost() / totalCost);
        });
    }

    public List<ShipmentOverviewFspaSplit> getFspaCountrySplit() {
        return fspaCountrySplit;
    }

    public void setFspaCountrySplit(List<ShipmentOverviewFspaSplit> fspaCountrySplit) {
        this.fspaCountrySplit = fspaCountrySplit;
        double totalCost = this.fspaCountrySplit.stream()
                .mapToDouble(ShipmentOverviewFspaSplit::getTotalCost)
                .sum();
        this.fspaCountrySplit.stream().forEach(i -> {
            i.setPerc(i.getTotalCost() / totalCost);
        });
    }

}
