/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.rest.controller.RealmRestController;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmService {

    public List<Realm> getRealmList(boolean active);

    public List<RealmCountry> getRealmCountryList(boolean active);

    public List<RealmCountry> getRealmCountryListByRealmId(int realmId);

    public int addRealm(Realm realm, int curUser);

    public int updateRealm(Realm realm, int curUser);

    public Realm getRealmById(int realmId);
}
