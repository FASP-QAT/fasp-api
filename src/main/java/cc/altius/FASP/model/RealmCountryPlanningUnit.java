/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class RealmCountryPlanningUnit implements Serializable {

    private int realmCountryId;
    private Label label; // name of Country
    private PlanningUnitForRealmCountryMapping[] planningUnits;
    private List<PlanningUnitForRealmCountryMapping> planningUnitList;

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public PlanningUnitForRealmCountryMapping[] getPlanningUnits() {
        return planningUnits;
    }

    public void setPlanningUnits(PlanningUnitForRealmCountryMapping[] planningUnits) {
        this.planningUnits = planningUnits;
    }

    public List<PlanningUnitForRealmCountryMapping> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<PlanningUnitForRealmCountryMapping> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }
    
}
