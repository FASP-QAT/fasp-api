/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class Product implements Serializable {

    private int productId;
    private Realm realm;
    private PrgProductCategoryDTO productCategory;
    private Label genericLabel;
    private Label label;
    private PrgUnitDTO forecastUnit;
    private boolean active;
    private User createdBy;
    private Date createdDate;
    private User lastModifiedBy;
    private Date lastModifiedDate;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public PrgProductCategoryDTO getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(PrgProductCategoryDTO productCategory) {
        this.productCategory = productCategory;
    }

    public Label getGenericLabel() {
        return genericLabel;
    }

    public void setGenericLabel(Label genericLabel) {
        this.genericLabel = genericLabel;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public PrgUnitDTO getForecastUnit() {
        return forecastUnit;
    }

    public void setForecastUnit(PrgUnitDTO forecastUnit) {
        this.forecastUnit = forecastUnit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
