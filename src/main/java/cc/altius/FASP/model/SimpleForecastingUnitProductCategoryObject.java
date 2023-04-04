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
public class SimpleForecastingUnitProductCategoryObject extends SimpleObject {

    @JsonView(Views.InternalView.class)
    SimpleObject productCategory;

    public SimpleForecastingUnitProductCategoryObject() {
        super();
    }

    public SimpleForecastingUnitProductCategoryObject(Integer id, Label label, SimpleObject productCategory) {
        super(id, label);
        this.productCategory = productCategory;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

}
