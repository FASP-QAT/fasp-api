/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class OrganisationCountry implements Serializable {

    private Integer organisationCountryId;
    private RealmCountry realmCountry;

    public OrganisationCountry() {
    }

    public OrganisationCountry(Integer organisationCountryId, RealmCountry realmCountry) {
        this.organisationCountryId = organisationCountryId;
        this.realmCountry = realmCountry;
    }

    public Integer getOrganisationCountryId() {
        return organisationCountryId;
    }

    public void setOrganisationCountryId(Integer organisationCountryId) {
        this.organisationCountryId = organisationCountryId;
    }

    public RealmCountry getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(RealmCountry realmCountry) {
        this.realmCountry = realmCountry;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.organisationCountryId);
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
        final OrganisationCountry other = (OrganisationCountry) obj;
        if (!Objects.equals(this.organisationCountryId, other.organisationCountryId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrganisationCountry{" + "organisationCountryId=" + organisationCountryId + ", realmCountry=" + realmCountry + '}';
    }

}
