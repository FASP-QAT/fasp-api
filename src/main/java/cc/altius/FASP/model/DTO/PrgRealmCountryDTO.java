/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class PrgRealmCountryDTO implements Serializable {

    private int realmCountryId;
    private PrgRealmDTO realm;
    private PrgCountryDTO country;
    private PrgCurrencyDTO defaultCurrency;
    private PrgUnitDTO palletUnit;
    private double airFreightPerc;
    private double seaFreightPerc;
    private int shippedToArrivedAirLeadTime;
    private int shippedToArrivedSeaLeadTime;
    private int arrivedToDeliveredLeadTime;

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public PrgRealmDTO getRealm() {
        return realm;
    }

    public void setRealm(PrgRealmDTO realm) {
        this.realm = realm;
    }

    public PrgCountryDTO getCountry() {
        return country;
    }

    public void setCountry(PrgCountryDTO country) {
        this.country = country;
    }

    public PrgCurrencyDTO getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(PrgCurrencyDTO defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public PrgUnitDTO getPalletUnit() {
        return palletUnit;
    }

    public void setPalletUnit(PrgUnitDTO palletUnit) {
        this.palletUnit = palletUnit;
    }

    public double getAirFreightPerc() {
        return airFreightPerc;
    }

    public void setAirFreightPerc(double airFreightPerc) {
        this.airFreightPerc = airFreightPerc;
    }

    public double getSeaFreightPerc() {
        return seaFreightPerc;
    }

    public void setSeaFreightPerc(double seaFreightPerc) {
        this.seaFreightPerc = seaFreightPerc;
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

}
