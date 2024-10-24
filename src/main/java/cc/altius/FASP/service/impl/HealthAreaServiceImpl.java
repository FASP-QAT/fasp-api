/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.HealthAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class HealthAreaServiceImpl implements HealthAreaService {

    @Autowired
    private HealthAreaDao healthAreaDao;
    @Autowired
    private RealmCountryDao realmCountryDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public int addHealthArea(HealthArea h, CustomUserDetails curUser) throws AccessControlFailedException {
        for (RealmCountry realmCountry : h.getRealmCountryList()) {
            if (realmCountry != null && realmCountry.getRealmCountryId() != 0) {
                try {
                    if (this.realmCountryDao.getRealmCountryById(realmCountry.getRealmCountryId(), curUser) == null) {
                        throw new AccessControlFailedException();
                    }
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.healthAreaDao.addHealthArea(h, curUser);
    }

    @Override
    public int updateHealthArea(HealthArea h, CustomUserDetails curUser) throws AccessControlFailedException {
        HealthArea ha = this.getHealthAreaById(h.getHealthAreaId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ha.getRealm().getId())) {
            for (RealmCountry realmCountry : h.getRealmCountryList()) {
                if (realmCountry != null && realmCountry.getRealmCountryId() != 0) {
                    try {
                        if (this.realmCountryDao.getRealmCountryById(realmCountry.getRealmCountryId(), curUser) == null) {
                            throw new AccessControlFailedException();
                        }
                    } catch (EmptyResultDataAccessException e) {
                        throw new AccessControlFailedException();
                    }
                }
            }
            return this.healthAreaDao.updateHealthArea(h, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<HealthArea> getHealthAreaList(CustomUserDetails curUser) {
        return this.healthAreaDao.getHealthAreaList(curUser);
    }

    @Override
    public List<SimpleCodeObject> getHealthAreaDropdownList(int realmId, CustomUserDetails curUser) {
        return this.healthAreaDao.getHealthAreaDropdownList(realmId, curUser);
    }

    @Override
    public List<HealthArea> getHealthAreaListByRealmCountry(int realmCountryId, CustomUserDetails curUser) {
        RealmCountry rc = this.realmCountryDao.getRealmCountryById(realmCountryId, curUser);
        if (rc == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return this.healthAreaDao.getHealthAreaListByRealmCountry(realmCountryId, curUser);
    }

    @Override
    public List<SimpleCodeObject> getHealthAreaListByRealmCountryIds(String[] realmCountryIds, CustomUserDetails curUser) {
        return this.healthAreaDao.getHealthAreaListByRealmCountryIds(realmCountryIds, curUser);
    }

    @Override
    public List<HealthArea> getHealthAreaForActiveProgramsList(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId));
        return this.healthAreaDao.getHealthAreaForActiveProgramsList(realmId, curUser);
    }

    @Override
    public List<HealthArea> getHealthAreaListByRealmId(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.healthAreaDao.getHealthAreaListByRealmId(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public HealthArea getHealthAreaById(int healthAreaId, CustomUserDetails curUser) {
        HealthArea ha = this.healthAreaDao.getHealthAreaById(healthAreaId, curUser);
        if (ha != null) {
            return ha;
        } else {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public List<HealthArea> getHealthAreaListForProgramByRealmId(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.healthAreaDao.getHealthAreaListForProgramByRealmId(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        return this.healthAreaDao.getDisplayName(realmId, name, curUser);
    }

    @Override
    public List<HealthArea> getHealthAreaListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.healthAreaDao.getHealthAreaListForSync(lastSyncDate, curUser);
    }

}
