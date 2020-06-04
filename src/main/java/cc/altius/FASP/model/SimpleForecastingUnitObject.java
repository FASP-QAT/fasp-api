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
public class SimpleForecastingUnitObject extends SimpleObject {

    SimpleObject productCategory;

    public SimpleForecastingUnitObject() {
        super();
    }

    public SimpleForecastingUnitObject(Integer id, Label label, SimpleObject productCategory) {
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
