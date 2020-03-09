/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProductCategory extends BaseModel implements Serializable {

    private int productCategoryId;
    private Realm realm;
    private Label label;
    private String sortOrder;
    private int level; // indicates the level that the Product Category is on. Root starts from 1

    public ProductCategory() {
    }

    public ProductCategory(int productCategoryId, Label label) {
        this.productCategoryId = productCategoryId;
        this.label = label;
    }

    public ProductCategory(int productCategoryId, Realm realm, Label label, int level, String sortOrder) {
        this.productCategoryId = productCategoryId;
        this.realm = realm;
        this.label = label;
        this.level = level;
        this.sortOrder = sortOrder;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        return "ProductCategory{" + "productCategoryId=" + productCategoryId + ", label=" + label + ", sortOrder=" + sortOrder + ", level=" + level + '}';
    }

}
