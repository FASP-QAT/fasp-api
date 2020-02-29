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
public class Organisation extends BaseModel implements Serializable {

    private int organisationId;
    private OrganisationCountry organisationCountry;
    private Realm realm;
    private Label label;

    public Organisation() {
    }

    public Organisation(int organisationId, OrganisationCountry organisationCountry, Realm realm, Label label) {
        this.organisationId = organisationId;
        this.organisationCountry = organisationCountry;
        this.realm = realm;
        this.label = label;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public OrganisationCountry getOrganisationCountry() {
        return organisationCountry;
    }

    public void setOrganisationCountry(OrganisationCountry organisationCountry) {
        this.organisationCountry = organisationCountry;
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
        return "Organisation{" + "organisationId=" + organisationId + ", organisationCountry=" + organisationCountry + ", realm=" + realm + ", label=" + label + '}';
    }

}
