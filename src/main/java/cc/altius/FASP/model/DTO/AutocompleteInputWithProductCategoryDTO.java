/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.AutoCompleteInput;

/**
 *
 * @author akil
 */
public class AutocompleteInputWithProductCategoryDTO extends AutoCompleteInput {

    private String productCategorySortOrder;

    public String getProductCategorySortOrder() {
        return productCategorySortOrder;
    }

    public void setProductCategorySortOrder(String productCategorySortOrder) {
        this.productCategorySortOrder = productCategorySortOrder;
    }

}
