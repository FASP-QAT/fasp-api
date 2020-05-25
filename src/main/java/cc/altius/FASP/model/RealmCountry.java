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
public class RealmCountry extends BaseModel implements Serializable {

    private int realmCountryId;
    private Country country;
    private Realm realm;
    private Currency defaultCurrency;
    private Unit palletUnit;
    private double airFreightPercentage;
    private double seaFreightPercentage;
    private double shippedToDeliveredByAirLeadTime;
    private double shippedToDeliveredBySeaLeadTime;

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

    public Unit getPalletUnit() {
        return palletUnit;
    }

    public void setPalletUnit(Unit palletUnit) {
        this.palletUnit = palletUnit;
    }

    public double getAirFreightPercentage() {
        return airFreightPercentage;
    }

    public void setAirFreightPercentage(double airFreightPercentage) {
        this.airFreightPercentage = airFreightPercentage;
    }

    public double getSeaFreightPercentage() {
        return seaFreightPercentage;
    }

    public void setSeaFreightPercentage(double seaFreightPercentage) {
        this.seaFreightPercentage = seaFreightPercentage;
    }

    public double getShippedToDeliveredByAirLeadTime() {
        return shippedToDeliveredByAirLeadTime;
    }

    public void setShippedToDeliveredByAirLeadTime(double shippedToDeliveredByAirLeadTime) {
        this.shippedToDeliveredByAirLeadTime = shippedToDeliveredByAirLeadTime;
    }

    public double getShippedToDeliveredBySeaLeadTime() {
        return shippedToDeliveredBySeaLeadTime;
    }

    public void setShippedToDeliveredBySeaLeadTime(double shippedToDeliveredBySeaLeadTime) {
        this.shippedToDeliveredBySeaLeadTime = shippedToDeliveredBySeaLeadTime;
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
        return "RealmCountry{" + "realmCountryId=" + realmCountryId + ", country=" + country + ", realm=" + realm + ", airFreightPercentage=" + airFreightPercentage + ", seaFreightPercentage=" + seaFreightPercentage + ", shippedToDeliveredByAirLeadTime=" + shippedToDeliveredByAirLeadTime + ", shippedToDeliveredBySeaLeadTime=" + shippedToDeliveredBySeaLeadTime + '}';
    }

}
