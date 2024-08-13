/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleEquivalencyUnit;
import cc.altius.FASP.model.SimpleObjectWithFu;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

/**
 *
 * @author akil
 */
public class DropdownsForStockStatusVerticalOutput {

    @JsonView(Views.ReportView.class)
    List<SimpleObjectWithFu> planningUnitList;
    @JsonView(Views.ReportView.class)
    List<SimpleObjectWithFu> realmCountryPlanningUnitList;
    @JsonView(Views.ReportView.class)
    List<SimpleEquivalencyUnit> equivalencyUnitList;

    public List<SimpleObjectWithFu> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<SimpleObjectWithFu> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<SimpleObjectWithFu> getRealmCountryPlanningUnitList() {
        return realmCountryPlanningUnitList;
    }

    public void setRealmCountryPlanningUnitList(List<SimpleObjectWithFu> realmCountryPlanningUnitList) {
        this.realmCountryPlanningUnitList = realmCountryPlanningUnitList;
    }

    public List<SimpleEquivalencyUnit> getEquivalencyUnitList() {
        return equivalencyUnitList;
    }

    public void setEquivalencyUnitList(List<SimpleEquivalencyUnit> equivalencyUnitList) {
        this.equivalencyUnitList = equivalencyUnitList;
    }

}
