/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleObject {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class})
    private Integer id;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.ExportApiView.class})
    private Label label;

    public SimpleObject() {
    }

    public SimpleObject(Integer id, Label label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getIdString() {
        return Integer.toString(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @JsonView({Views.DropDown3View.class})
    public String getName() {
        return this.label.getLabel_en() + " | " + this.getId();
    }

    @Override
    public String toString() {
        return "SimpleObject{" + "id=" + id + ", label=" + label + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
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
        final SimpleObject other = (SimpleObject) obj;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
