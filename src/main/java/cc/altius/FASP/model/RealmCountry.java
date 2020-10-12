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
 * @author altius
 */
public class RealmCountry extends BaseModel implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.InternalView.class})
    private int realmCountryId;
    @JsonView({Views.ArtmisView.class, Views.InternalView.class})
    private Country country;
    @JsonView(Views.InternalView.class)
    private Realm realm;
    @JsonView(Views.InternalView.class)
    private Currency defaultCurrency;

    public RealmCountry() {
    }

    public RealmCountry(int realmCountryId, Country country) {
        this.realmCountryId = realmCountryId;
        this.country = country;
    }

    public RealmCountry(int realmCountryId, Country country, Realm realm) {
        this.realmCountryId = realmCountryId;
        this.country = country;
        this.realm = realm;
    }

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.realmCountryId;
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
        final RealmCountry other = (RealmCountry) obj;
        if (this.realmCountryId != other.realmCountryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RealmCountry{" + "realmCountryId=" + realmCountryId + ", country=" + country + ", realm=" + realm + ", defaultCurrency=" + defaultCurrency + '}';
    }

}
