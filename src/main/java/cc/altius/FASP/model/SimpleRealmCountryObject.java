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
public class SimpleRealmCountryObject implements Serializable {

    @JsonView(Views.ReportView.class)
    private int realmCountryId;
    @JsonView(Views.ReportView.class)
    private String countryCode;
    private String countryCode2;
    @JsonView(Views.ReportView.class)
    private Label label;

    public SimpleRealmCountryObject() {
    }

    public SimpleRealmCountryObject(int realmCountryId, String countryCode, String countryCode2, Label label) {
        this.realmCountryId = realmCountryId;
        this.countryCode = countryCode;
        this.countryCode2 = countryCode2;
        this.label = label;
    }

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
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
        hash = 47 * hash + this.realmCountryId;
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
        final SimpleRealmCountryObject other = (SimpleRealmCountryObject) obj;
        if (this.realmCountryId != other.realmCountryId) {
            return false;
        }
        return true;
    }

}
