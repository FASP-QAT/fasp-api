/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

/**
 *
 * @author akil
 */
public class DropdownsForStockStatusVerticalOutput {

    @JsonView(Views.ReportView.class)
    List<SimpleObject> planningUnitList;
    @JsonView(Views.ReportView.class)
    List<RealmCountryPlanningUnit> realmCountryPlanningUnitList;
    @JsonView(Views.ReportView.class)
    List<EquivalencyUnitMapping> equivalencyUnitList;

    public List<SimpleObject> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<SimpleObject> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitList() {
        return realmCountryPlanningUnitList;
    }

    public void setRealmCountryPlanningUnitList(List<RealmCountryPlanningUnit> realmCountryPlanningUnitList) {
        this.realmCountryPlanningUnitList = realmCountryPlanningUnitList;
    }

    public List<EquivalencyUnitMapping> getEquivalencyUnitList() {
        return equivalencyUnitList;
    }

    public void setEquivalencyUnitList(List<EquivalencyUnitMapping> equivalencyUnitList) {
        this.equivalencyUnitList = equivalencyUnitList;
    }

}
