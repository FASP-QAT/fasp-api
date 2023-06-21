/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimpleCodeObject extends SimpleObject {

    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private String code;

    public SimpleCodeObject() {
        super();
    }

    public SimpleCodeObject(Integer id, Label label, String code) {
        super(id, label);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.code);
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
        final SimpleCodeObject other = (SimpleCodeObject) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SimpleCodeObject{" + "id=" + this.getId() + ", label=" + this.getLabel() + ", code=" + code + '}';
    }

}
