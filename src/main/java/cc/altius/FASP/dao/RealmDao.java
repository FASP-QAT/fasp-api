/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmDao {

    public List<Realm> getRealmList(boolean active, CustomUserDetails curUser);

//    public List<RealmCountry> getRealmCountryList(boolean active);
//    public List<RealmCountry> getRealmCountryListByRealmId(int realmId);
    public int addRealm(Realm realm, CustomUserDetails curUser);

    public int updateRealm(Realm realm, CustomUserDetails curUser);

    public Realm getRealmById(int realmId, CustomUserDetails curUser);
}
