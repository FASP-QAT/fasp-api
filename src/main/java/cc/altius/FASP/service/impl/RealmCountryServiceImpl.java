/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.service.RealmCountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class RealmCountryServiceImpl implements RealmCountryService{
    
    @Autowired
    private RealmCountryDao realmCountryDao;

    @Override
    public int addRealmCountry(RealmCountry realmCountry, int curUser) {
        return this.realmCountryDao.addRealmCountry(realmCountry,curUser);
    }

    @Override
    public int updateRealmCountry(RealmCountry realmCountry, int curUser) {
        return this.realmCountryDao.updateRealmCountry(realmCountry,curUser);
    }

    @Override
    public List<RealmCountry> getRealmCountryList() {
        return this.realmCountryDao.getRealmCountryList();
    }

    @Override
    public RealmCountry getRealmCountryById(int organisationId) {
        return this.realmCountryDao.getRealmCountryById(organisationId);
    }
    
}
