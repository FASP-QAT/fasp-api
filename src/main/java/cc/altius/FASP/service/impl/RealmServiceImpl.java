/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.RealmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class RealmServiceImpl implements RealmService {

    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<Realm> getRealmList(boolean active, CustomUserDetails curUser) {
        return this.realmDao.getRealmList(active, curUser);
    }

    @Override
    public int addRealm(Realm realm, CustomUserDetails curUser) {
        return this.realmDao.addRealm(realm, curUser);
    }

    @Override
    public int updateRealm(Realm realm, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, realm.getRealmId())) {
            return this.realmDao.updateRealm(realm, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Realm getRealmById(int realmId, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.realmDao.getRealmById(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }

    }

    @Override
    public List<Realm> getRealmListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.realmDao.getRealmListForSync(lastSyncDate, curUser);
    }

}
