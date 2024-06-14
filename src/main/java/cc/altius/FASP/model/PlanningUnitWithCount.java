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
    private int countOfSpPrograms;
    @JsonView(Views.ReportView.class)
    private int countOfFcPrograms;

    public int getCountOfSpPrograms() {
        return countOfSpPrograms;
    }

    public void setCountOfSpPrograms(int countOfSpPrograms) {
        this.countOfSpPrograms = countOfSpPrograms;
    }

    public int getCountOfFcPrograms() {
        return countOfFcPrograms;
    }

    public void setCountOfFcPrograms(int countOfFcPrograms) {
        this.countOfFcPrograms = countOfFcPrograms;
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
