/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleForecastingUnitWithUnitObject extends SimpleObject {

    @JsonView({Views.ReportView.class, Views.InternalView.class, Views.DropDownView.class})
    SimpleCodeObject unit;

    public SimpleForecastingUnitWithUnitObject() {
        super();
    }

    public SimpleForecastingUnitWithUnitObject(Integer id, Label label, SimpleCodeObject unit) {
        super(id, label);
        this.unit = unit;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }
}
