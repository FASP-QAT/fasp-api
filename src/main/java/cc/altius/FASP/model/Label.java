/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class Label extends BaseModel {

    @JsonView({Views.InternalView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class, Views.UserListView.class})
    private Integer labelId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class, Views.UserListView.class})
    private String label_en;
    @JsonView({Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class, Views.UserListView.class})
    private String label_sp;
    @JsonView({Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class, Views.UserListView.class})
    private String label_fr;
    @JsonView({Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class, Views.DropDown2View.class, Views.DropDown3View.class, Views.ExportApiView.class, Views.UserListView.class})
    private String label_pr;

    public Label(Integer labelId, String label_en, String label_sp, String label_fr, String label_pr) {
        this.labelId = labelId;
        this.label_en = label_en;
        this.label_sp = label_sp;
        this.label_fr = label_fr;
        this.label_pr = label_pr;
        setActive(true);
    }

    public Label(Integer labelId) {
        this.labelId = labelId;
    }

    public Label() {
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabel_en() {
        return label_en;
    }

    public void setLabel_en(String label_en) {
        this.label_en = label_en;
    }

    public String getLabel_sp() {
        return label_sp;
    }

    public void setLabel_sp(String label_sp) {
        this.label_sp = label_sp;
    }

    public String getLabel_fr() {
        return label_fr;
    }

    public void setLabel_fr(String label_fr) {
        this.label_fr = label_fr;
    }

    public String getLabel_pr() {
        return label_pr;
    }

    public void setLabel_pr(String label_pr) {
        this.label_pr = label_pr;
    }

    @JsonIgnore
    public String getLabel() {
        return this.label_en;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.labelId);
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
        final Label other = (Label) obj;
        if (!Objects.equals(this.labelId, other.labelId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Label{" + "labelId=" + labelId + ", label_en=" + label_en + '}';
    }

    public void setLabel(Label l) {
        this.labelId = l.getLabelId();
        this.label_en = l.getLabel_en();
        this.label_fr = l.getLabel_fr();
        this.label_pr = l.getLabel_pr();
        this.label_sp = l.getLabel_sp();

    }
}
