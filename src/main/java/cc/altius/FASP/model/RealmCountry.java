/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class RealmCountry {

    private int realmCountryId;
    private Country country;
    private Realm realm;

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

}
