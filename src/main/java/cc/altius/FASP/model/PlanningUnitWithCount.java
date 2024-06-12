/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class PlanningUnitWithCount extends PlanningUnit {

    @JsonView(Views.ReportView.class)
    private int countOfPrograms;

    public int getCountOfPrograms() {
        return countOfPrograms;
    }

    public void setCountOfPrograms(int countOfPrograms) {
        this.countOfPrograms = countOfPrograms;
    }

    public PlanningUnitWithCount() {
        super();
    }

    public PlanningUnitWithCount(int planningUnitId, Label label) {
        super(planningUnitId, label);
    }

    public PlanningUnitWithCount(int planningUnitId, ForecastingUnit forecastingUnit, Label label, SimpleCodeObject unit, double multiplier, boolean active) {
        super(planningUnitId, forecastingUnit, label, unit, multiplier, active);
        super.setActive(active);
    }
}
