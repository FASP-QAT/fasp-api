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
public class ForecastingUnit extends BaseModel implements Serializable {

    private int forecastingUnitId;
    private Realm realm;
    private Label genericLabel;
    private Label label;
    private ProductCategory productCategory;
    private TracerCategory tracerCategory;

    public ForecastingUnit() {
    }

    public ForecastingUnit(int productId, Label genericLabel, Label label) {
        this.forecastingUnitId = productId;
        this.genericLabel = genericLabel;
        this.label = label;
    }

    public ForecastingUnit(int productId, Realm realm, Label genericLabel, Label label, ProductCategory productCategory, TracerCategory tracerCategory) {
        this.forecastingUnitId = productId;
        this.realm = realm;
        this.genericLabel = genericLabel;
        this.label = label;
        this.productCategory = productCategory;
        this.tracerCategory = tracerCategory;
    }

    public int getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(int forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public TracerCategory getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(TracerCategory tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.forecastingUnitId;
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
        final ForecastingUnit other = (ForecastingUnit) obj;
        if (this.forecastingUnitId != other.forecastingUnitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + forecastingUnitId + ", genericLabel=" + genericLabel + ", label=" + label + '}';
    }
}
