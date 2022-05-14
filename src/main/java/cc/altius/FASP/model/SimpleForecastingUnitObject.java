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
public class SimpleForecastingUnitObject extends SimpleUnitObject {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleObject tracerCategory;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleObject productCategory;

    public SimpleForecastingUnitObject() {
    }

    public SimpleForecastingUnitObject(SimpleCodeObject unit, Integer id, Label label, SimpleObject tracerCategory, SimpleObject productCategory) {
        super(unit, id, label);
        this.tracerCategory = tracerCategory;
        this.productCategory = productCategory;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

}
