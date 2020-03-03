/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.rest.controller.RealmRestController;
import cc.altius.FASP.service.RealmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class RealmServiceImpl implements RealmService {

    @Autowired
    private RealmDao realmDao;

    @Override
    public List<Realm> getRealmList(boolean active) {
        return this.realmDao.getRealmList(active);
    }

    @Override
    public List<RealmCountry> getRealmCountryList(boolean active) {
        return this.realmDao.getRealmCountryList(active);
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId) {
        return this.realmDao.getRealmCountryListByRealmId(realmId);
    }

    @Override
    public int addRealm(Realm realm, int curUser) {
        return this.realmDao.addRealm(realm,curUser);
    }

    @Override
    public int updateRealm(Realm realm, int curUser) {
        return this.realmDao.updateRealm(realm,curUser);
    }

    @Override
    public Realm getRealmById(int realmId) {
        return this.realmDao.getRealmById(realmId);
    }

}
