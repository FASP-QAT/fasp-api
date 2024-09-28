/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class TracerCategory extends BaseModel implements Serializable {

    @JsonView(Views.InternalView.class)
    private int tracerCategoryId;
    private SimpleCodeObject realm;
    private SimpleCodeObject healthArea;
    @JsonView(Views.InternalView.class)
    private Label label;

    public TracerCategory() {
    }

    public TracerCategory(int tracerCategoryId, Label label) {
        this.tracerCategoryId = tracerCategoryId;
        this.label = label;
    }

    public TracerCategory(int tracerCategoryId, SimpleCodeObject realm, SimpleCodeObject healthArea, Label label) {
        this.tracerCategoryId = tracerCategoryId;
        this.realm = realm;
        this.healthArea = healthArea;
        this.label = label;
    }

    public int getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(int tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public SimpleCodeObject getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(SimpleCodeObject healthArea) {
        this.healthArea = healthArea;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.tracerCategoryId;
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
        final TracerCategory other = (TracerCategory) obj;
        if (this.tracerCategoryId != other.tracerCategoryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TracerCategory{" + "tracerCategoryId=" + tracerCategoryId + ", label=" + label + '}';
    }

}
