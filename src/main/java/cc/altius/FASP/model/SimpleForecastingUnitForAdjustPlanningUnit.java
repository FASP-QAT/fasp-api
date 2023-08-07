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
public class SimpleForecastingUnitForAdjustPlanningUnit extends SimpleObject {

    @JsonView({Views.InternalView.class})
    private SimpleObject productCategory;

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public SimpleForecastingUnitForAdjustPlanningUnit(SimpleObject productCategory, Integer id, Label label) {
        super(id, label);
        this.productCategory = productCategory;
    }

}
