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
    private int defaultCurrencyId;
    private int palletUnitId;
    private int airFrightPercentage;
    private int seaFrightPercentage;
    private int shippedToArrivedAirLeadTime;
    private int shippedToArrivedSeaLeadTime;
    private int arrivedToDeliveredLeadTime;

    public RealmCountry() {
    }

    public RealmCountry(int realmCountryId, Country country, Realm realm, int defaultCurrencyId, int palletUnitId, int airFrightPercentage, int seaFrightPercentage, int shippedToArrivedAirLeadTime, int shippedToArrivedSeaLeadTime, int arrivedToDeliveredLeadTime) {
        this.realmCountryId = realmCountryId;
        this.country = country;
        this.realm = realm;
        this.defaultCurrencyId = defaultCurrencyId;
        this.palletUnitId = palletUnitId;
        this.airFrightPercentage = airFrightPercentage;
        this.seaFrightPercentage = seaFrightPercentage;
        this.shippedToArrivedAirLeadTime = shippedToArrivedAirLeadTime;
        this.shippedToArrivedSeaLeadTime = shippedToArrivedSeaLeadTime;
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
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

    public int getDefaultCurrencyId() {
        return defaultCurrencyId;
    }

    public void setDefaultCurrencyId(int defaultCurrencyId) {
        this.defaultCurrencyId = defaultCurrencyId;
    }

    public int getPalletUnitId() {
        return palletUnitId;
    }

    public void setPalletUnitId(int palletUnitId) {
        this.palletUnitId = palletUnitId;
    }

    public int getAirFrightPercentage() {
        return airFrightPercentage;
    }

    public void setAirFrightPercentage(int airFrightPercentage) {
        this.airFrightPercentage = airFrightPercentage;
    }

    public int getSeaFrightPercentage() {
        return seaFrightPercentage;
    }

    public void setSeaFrightPercentage(int seaFrightPercentage) {
        this.seaFrightPercentage = seaFrightPercentage;
    }

    public int getShippedToArrivedAirLeadTime() {
        return shippedToArrivedAirLeadTime;
    }

    public void setShippedToArrivedAirLeadTime(int shippedToArrivedAirLeadTime) {
        this.shippedToArrivedAirLeadTime = shippedToArrivedAirLeadTime;
    }

    public int getShippedToArrivedSeaLeadTime() {
        return shippedToArrivedSeaLeadTime;
    }

    public void setShippedToArrivedSeaLeadTime(int shippedToArrivedSeaLeadTime) {
        this.shippedToArrivedSeaLeadTime = shippedToArrivedSeaLeadTime;
    }

    public int getArrivedToDeliveredLeadTime() {
        return arrivedToDeliveredLeadTime;
    }

    public void setArrivedToDeliveredLeadTime(int arrivedToDeliveredLeadTime) {
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
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
        return "RealmCountry{" + "realmCountryId=" + realmCountryId + ", country=" + country + ", realm=" + realm + ", defaultCurrencyId=" + defaultCurrencyId + ", palletUnitId=" + palletUnitId + ", airFrightPercentage=" + airFrightPercentage + ", seaFrightPercentage=" + seaFrightPercentage + ", shippedToArrivedAirLeadTime=" + shippedToArrivedAirLeadTime + ", shippedToArrivedSeaLeadTime=" + shippedToArrivedSeaLeadTime + ", arrivedToDeliveredLeadTime=" + arrivedToDeliveredLeadTime + '}';
    }

}
