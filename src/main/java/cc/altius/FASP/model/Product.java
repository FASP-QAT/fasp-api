/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class Product extends BaseModel implements Serializable {

    private int productId;
    private Realm realm;
    private Label genericLabel;
    private Label label;
    private Unit forecastingUnit;

    public Product() {
    }

    public Product(int productId, Label genericLabel, Label label) {
        this.productId = productId;
        this.genericLabel = genericLabel;
        this.label = label;
    }

    public Product(int productId, Realm realm, Label genericLabel, Label label, Unit forecastingUnit) {
        this.productId = productId;
        this.realm = realm;
        this.genericLabel = genericLabel;
        this.label = label;
        this.forecastingUnit = forecastingUnit;
    }
    
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

    public Unit getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(Unit forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.productId;
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
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", genericLabel=" + genericLabel + ", label=" + label + '}';
    }
}
