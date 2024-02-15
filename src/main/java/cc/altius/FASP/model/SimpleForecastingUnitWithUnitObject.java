/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author rohit
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