/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProductCategoryAndTracerCategoryDTO implements Serializable {

    private String productCategorySortOrder;
    private Integer tracerCategoryId;

    public String getProductCategorySortOrder() {
        return productCategorySortOrder;
    }

    public void setProductCategorySortOrder(String productCategorySortOrder) {
        this.productCategorySortOrder = productCategorySortOrder;
    }

    public Integer getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(Integer tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

}
