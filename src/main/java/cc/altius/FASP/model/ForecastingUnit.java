/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class ForecastingUnit extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class, Views.ExportApiView.class})
    private int forecastingUnitId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleCodeObject realm;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Label genericLabel;
    @JsonView({Views.ReportView.class, Views.InternalView.class, Views.ExportApiView.class})
    private Label label;
    @JsonView({Views.ReportView.class, Views.InternalView.class, Views.ExportApiView.class})
    private SimpleObject productCategory;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject tracerCategory;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleCodeObject unit;

    public ForecastingUnit() {
    }

    public ForecastingUnit(int productId, Label genericLabel, Label label) {
        this.forecastingUnitId = productId;
        this.genericLabel = genericLabel;
        this.label = label;
    }

    public ForecastingUnit(int productId, SimpleCodeObject realm, Label genericLabel, Label label, SimpleObject productCategory, SimpleObject tracerCategory) {
        this.forecastingUnitId = productId;
        this.realm = realm;
        this.genericLabel = genericLabel;
        this.label = label;
        this.productCategory = productCategory;
        this.tracerCategory = tracerCategory;
    }

    public ForecastingUnit(int productId, SimpleCodeObject realm, Label genericLabel, Label label, SimpleCodeObject unit, SimpleObject productCategory, SimpleObject tracerCategory) {
        this.forecastingUnitId = productId;
        this.realm = realm;
        this.genericLabel = genericLabel;
        this.label = label;
        this.unit = unit;
        this.productCategory = productCategory;
        this.tracerCategory = tracerCategory;
    }

    public int getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(int forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
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

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    public boolean isActive() {
        return super.isActive();
    }

    @JsonView(Views.ReportView.class)
    public Date getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @JsonView(Views.ReportView.class)
    public BasicUser getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    public void setForecastingUnit(ForecastingUnit fu) {
        this.forecastingUnitId = fu.getForecastingUnitId();
        this.realm = fu.getRealm();
        this.genericLabel = fu.getGenericLabel();
        this.label = fu.getLabel();
        this.productCategory = fu.getProductCategory();
        this.tracerCategory = fu.getTracerCategory();
        this.unit = fu.getUnit();
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
