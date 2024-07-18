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
public class ForecastingUnitWithCount extends ForecastingUnit {

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

    public ForecastingUnitWithCount() {
    }

    public ForecastingUnitWithCount(int productId, Label genericLabel, Label label) {
        super(productId, genericLabel, label);
    }

    public ForecastingUnitWithCount(int productId, SimpleCodeObject realm, Label genericLabel, Label label, SimpleObject productCategory, SimpleObject tracerCategory) {
        super(productId, realm, genericLabel, label, productCategory, tracerCategory);
    }

    public ForecastingUnitWithCount(int productId, SimpleCodeObject realm, Label genericLabel, Label label, SimpleCodeObject unit, SimpleObject productCategory, SimpleObject tracerCategory) {
        super(productId, realm, genericLabel, label, unit, productCategory, tracerCategory);
    }
}
