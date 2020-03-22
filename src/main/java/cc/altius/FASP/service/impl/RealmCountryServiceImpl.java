/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.RealmCountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Service
public class RealmCountryServiceImpl implements RealmCountryService {

    @Autowired
    private RealmCountryDao realmCountryDao;
    @Autowired
    private AclService aclService;

    /*
    * 
     */
    @Override
    @Transactional
    public int addRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser) {
        int rows = 0;
        for (RealmCountry realmCountry : realmCountryList) {
            RealmCountry rc = null;
            try {
                rc = this.realmCountryDao.getRealmCountryByRealmAndCountry(realmCountry.getRealm().getRealmId(), realmCountry.getCountry().getCountryId(), curUser);
            } catch (IncorrectResultSizeDataAccessException i) {

            }
            if (rc != null) {
                if (this.aclService.checkRealmAccessForUser(curUser, realmCountry.getRealm().getRealmId())) {
                    realmCountry.setRealmCountryId(rc.getRealmCountryId());
                    rows += this.realmCountryDao.updateRealmCountry(realmCountry, curUser);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            } else {
                if (this.aclService.checkRealmAccessForUser(curUser, realmCountry.getRealm().getRealmId())) {
                    rows += this.realmCountryDao.addRealmCountry(realmCountry, curUser);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser) {
        int rows = 0;
        for (RealmCountry realmCountry : realmCountryList) {
            RealmCountry rc = this.realmCountryDao.getRealmCountryById(realmCountry.getRealmCountryId(), curUser);
            if (this.aclService.checkRealmAccessForUser(curUser, rc.getRealm().getRealmId())) {
                rows += this.realmCountryDao.updateRealmCountry(realmCountry, curUser);
            } else {
                throw new AccessDeniedException("Access denied");
            }
        }
        return rows;
    }

    @Override
    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser) {
        return this.realmCountryDao.getRealmCountryList(curUser);
    }

    @Override
    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser) {
        RealmCountry rc = this.realmCountryDao.getRealmCountryById(realmCountryId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, rc.getRealm().getRealmId())) {
            return rc;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.realmCountryDao.getRealmCountryListByRealmId(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
