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
public class ExtendedProductCategory extends ProductCategory {

    public ExtendedProductCategory(int productCategoryId, SimpleCodeObject realm, Label label, Integer parent, String sortOrder) {
        super(productCategoryId, realm, label, parent, sortOrder);
        this.expanded = true;
    }
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    
    

}
