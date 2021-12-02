/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author akil
 */
public class SimpleForecastingUnitTracerCategoryObject extends SimpleObject {

    SimpleObject tracerCategory;

    public SimpleForecastingUnitTracerCategoryObject() {
        super();
    }

    public SimpleForecastingUnitTracerCategoryObject(Integer id, Label label, SimpleObject tracerCategory) {
        super(id, label);
        this.tracerCategory = tracerCategory;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

}
