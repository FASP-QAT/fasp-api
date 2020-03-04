/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author altius
 */
public class Organisation extends BaseModel implements Serializable {

    private int organisationId;
    private String organisationCode;
    private Realm realm;
    private Label label;
    @JsonIgnore
    List<RealmCountry> realmCountryList;
    String[] realmCountryArray;

    public Organisation() {
    }

    public Organisation(int organisationId, String organisationCode, Realm realm, Label label) {
        this.organisationId = organisationId;
        this.organisationCode = organisationCode;
        this.realm = realm;
        this.label = label;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
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

    public List<RealmCountry> getRealmCountryList() {
        return realmCountryList;
    }

    public void setRealmCountryList(List<RealmCountry> realmCountryList) {
        this.realmCountryList = realmCountryList;
    }

    public String[] getRealmCountryArray() {
        return realmCountryArray;
    }

    public void setRealmCountryArray(String[] realmCountryArray) {
        this.realmCountryArray = realmCountryArray;
    }

    public String getOrganisationCode() {
        return organisationCode;
    }

    public void setOrganisationCode(String organisationCode) {
        this.organisationCode = organisationCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.organisationId;
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
        final Organisation other = (Organisation) obj;
        if (this.organisationId != other.organisationId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organisation{" + "organisationId=" + organisationId + ", organisationCode=" + organisationCode + ", realm=" + realm + ", label=" + label + ", realmCountryList=" + realmCountryList + ", realmCountryArray=" + realmCountryArray + '}';
    }

}
