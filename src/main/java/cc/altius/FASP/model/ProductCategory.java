/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProductCategory extends BaseModel implements Serializable {

    private int productCategoryId;
    private SimpleCodeObject realm;
    private Label label;
    @JsonIgnore
    private Integer parentProductCategoryId; // Only Root parent can be null
//    @JsonIgnore
    private String sortOrder;

    public ProductCategory() {
    }

    public ProductCategory(int productCategoryId, Label label) {
        this.productCategoryId = productCategoryId;
        this.label = label;
    }

    public ProductCategory(int productCategoryId, SimpleCodeObject realm, Label label, Integer parentProductCategoryId, String sortOrder) {
        this.productCategoryId = productCategoryId;
        this.realm = realm;
        this.label = label;
        this.parentProductCategoryId = parentProductCategoryId;
        this.sortOrder = sortOrder;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Integer getParentProductCategoryId() {
        return parentProductCategoryId;
    }

    public void setParentProductCategoryId(Integer parentProductCategoryId) {
        this.parentProductCategoryId = parentProductCategoryId;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.productCategoryId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductCategory other = (ProductCategory) obj;
        if (this.productCategoryId != other.productCategoryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return label.getLabel_en();
    }

}
